package com.shangpin.web.controller.subject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.web.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.biz.bo.ActivityIndex;
import com.shangpin.biz.bo.BrandActivityHead;
import com.shangpin.biz.bo.SearchConditions;
import com.shangpin.biz.bo.SearchResult;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.common.page.Page;
import com.shangpin.biz.service.SPBizActivityService;
import com.shangpin.biz.service.SPBizSubjectService;
import com.shangpin.pay.utils.common.StringUtil;
import com.shangpin.utils.JedisUtilFactory;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(SubjectController.class);
	private static final String NEWLIST = "subject/newList";
	private static final String LIST_FLOOR = "subject/newList_Floor";

	private static final String[] topicIdList=PropertyUtil.getStringArray("topicIds");

	@Autowired
	private SPBizSubjectService subjectBizService;
	@Autowired
	private SPBizActivityService spBizActivityService;
	@Autowired
	private SPBizUserService userService;

	/**
	 * 活动下商品列表获取更多
	 */
	@ResponseBody
	@RequestMapping(value = "/getMore", method = RequestMethod.POST)
	public Map<String, Object> getMore(HttpServletRequest request, @RequestParam("topicId") String topicId, String gender, @RequestParam String pageIndex) {


		String userId = null;
		User user = getSessionUser(request);
		if (user!=null) {
			userId = user.getUserId();
		}
		Map<String, Object> map = subjectBizService.getNewTopicProducts(topicId, gender, userId, pageIndex, String.valueOf(Page.DEFAULT_PAGE_SIZE));
		return map;
	}

	@RequestMapping(value = "/product/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchProductList(SearchConditions searchConditions, HttpServletRequest request, String gender, Model model,String OPT,String isAppIn) {

		String topicId=searchConditions.getTopicId();
		User user = getSessionUser(request);
		if(isDefinedTopic(topicId)){
			String toLoginUrl;
			boolean isApp = UserUtil.isApp(request);
			logger.info("isAppIn:"+isAppIn);
			if(isApp && "1".equals(isAppIn)){
				toLoginUrl= Constants.APP_NOT_LOGIN_URL+"?"+Constants.APP_NAME_CALLBACK_URL+"="+Constants.SHANGPIN_URL+"subject/product/list?topicId="+topicId+"&time="+Math.random();
				//如果是app 刷新session里面的用户
				logger.info("isApp:"+isApp);
				appSessionToM(request);
				user = getSessionUser(request);

			}else{
				//toLoginUrl = request.getContextPath()+"/login?back=/subject/product/list?topicId="+topicId+"&time="+Math.random();
				toLoginUrl = request.getContextPath()+"/login?back=/subject/product/list?topicId="+topicId+"&time="+Math.random();
			}
			logger.info("loginUrl:"+toLoginUrl);
			if (user==null)  {//没登陆
				model.addAttribute("isLogin", true);
			}else{
				//立享会员
				if(!user.isVip() && !user.canTryVip()){
					model.addAttribute("isBuy", true);
				}else if(!user.isVip()){//排除立享会员和金牌会员
					//包含活动id的活动列表
					model.addAttribute("isTopicId", true);
				}
			}
			model.addAttribute("toLoginUrl",toLoginUrl);
		}

		try {
			String userId = getUserId(request);
			String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
			String start = (StringUtils.isEmpty(searchConditions.getStart())) ? "1" : searchConditions.getStart();
			String postArea=(StringUtils.isEmpty(searchConditions.getPostArea())) ?"0" : searchConditions.getPostArea();
			searchConditions.setPostArea(postArea);
			if(org.apache.commons.lang3.StringUtils.isBlank(topicId)){
				return NEWLIST;
			}
			topicId=topicId.trim();
			/**
			 * 定义缓存策略
			 */
			String redisKey = "subject_key_floor:"+ topicId;
			String redisTime ="900";
			Jedis jedis = JedisUtilFactory.getDefaultJedisUtil().getJedis();
			if(OPT!=null){
				if("miss".equals(OPT)||"refresh".equals(OPT)){
					jedis.del(redisKey);
				}
			}
			model.addAttribute("userLv", userLv);
			Map<String, Object> checkMap = null;
			if(jedis.get(redisKey)!=null){
				String redisVal = jedis.get(redisKey);
				checkMap = JsonUtil.fromJson(redisVal, Map.class);
			}
			BrandActivityHead activityHead = spBizActivityService.getBrandActivityHead(userId, topicId);
			//判断是否开启楼层展示
			if(checkMap==null ||checkMap.isEmpty()){
				checkMap = spBizActivityService.getSubjectFloorInfo(topicId,activityHead.getActivity());
				checkMap.put("stor", "1");
			}
			String isFloor = (String) checkMap.get("isFloor");
			Map<String, String> headPic = (Map<String, String>) checkMap.get("headPic");
			model.addAttribute("headPic", headPic);//头图部分放入页面
			
			if(isFloor!=null&&"1".equals(isFloor)){
				//获取headinfo
				ActivityIndex activityIndex = spBizActivityService.getSearchActivityIndexWithoutSearch(userId,topicId);
				List<Map<String, Object>> floorList = (List<Map<String, Object>>) checkMap.get("floorList");
				if(checkMap.get("stor")!=null &&"1".equals(checkMap.get("stor"))){
					checkMap.remove("stor");
					jedis.set(redisKey, JsonUtil.toJson(checkMap));
					jedis.expire(redisKey, Integer.parseInt(redisTime));
				}
				model.addAttribute("floorList", floorList);
				model.addAttribute("activityIndex", activityIndex);
				model.addAttribute("searchConditions", searchConditions);
				return LIST_FLOOR;
			}else{
				ActivityIndex activityIndex=null;
				SearchResult searchResult=null;
				// 筛选项
				activityIndex = spBizActivityService.searchActivityIndex(userId, topicId, null, null, null, null, null, null, null, null, null, null, null, postArea,"");
				// 商品
				searchResult = spBizActivityService.searchActivityProduct(topicId, start, Constants.PRODUCT_LIST_END, searchConditions.getTagId(), searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), searchConditions.getGender(), userLv, postArea,"",activityIndex.getOperat().getActivity());
				int hasPageNum = SearchUtil.hasPageNum(Integer.parseInt(searchResult.getCount()), Integer.parseInt(Constants.PRODUCT_LIST_END));			
				model.addAttribute("activityIndex", activityIndex);
				model.addAttribute("searchConditions", searchConditions);
				model.addAttribute("searchResult", searchResult);
				
				model.addAttribute("hasPageNum", hasPageNum);
				searchConditions.setPostArea(postArea);
				return NEWLIST;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NEWLIST;
	}

	private boolean appSessionToM(HttpServletRequest request){

		String token = request.getHeader(Constants.APP_COOKIE_NAME_TOKEN);
		String userId = request.getHeader(Constants.APP_COOKIE_NAME_UID);
		boolean flag =false;
		try {
			 logger.info("token:"+token);
			 logger.info("userId:"+userId);
			 if(org.apache.commons.lang3.StringUtils.isBlank(userId)){
			 	 logger.info("user bl");
				 request.getSession().removeAttribute(Constants.SESSION_USER);
			 }else{
				 flag = SessionUtil.loginFromApp(token, userId, request.getSession(),userService);
				 if (!flag){
					 request.getSession().removeAttribute(Constants.SESSION_USER);
				 }
			 }
			 logger.info("flag:"+flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/***
	 * 2016/9/27  指定活动id只有金牌会员才可以购买
	 * @param topicId
	 * @return boolean
	 */
	private boolean isDefinedTopic(String topicId){
		List<String> topics=Arrays.asList(topicIdList);
		if(topics.contains(topicId)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @Title: getNewMore
	 * @Description:活动商品列表加载更多
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月6日
	 */
	@ResponseBody
	@RequestMapping(value = "/new/more", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getNewMore(SearchConditions searchConditions, HttpServletRequest request, String gender, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			User user = getSessionUser(request);
			String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
			String start = String.valueOf(Integer.valueOf(searchConditions.getStart()) + 1);
			String topicId=searchConditions.getTopicId();
			if(!StringUtil.isEmpty(topicId)){
				topicId=topicId.trim();
			}
			String userId=StringUtils.isEmpty(user) ? "" : user.getUserId();
			BrandActivityHead brandActivityHead = spBizActivityService.getBrandActivityHead(userId, topicId);
			SearchResult searchResult = spBizActivityService.searchActivityProduct(topicId, start, Constants.PRODUCT_LIST_END, searchConditions.getTagId(), searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), searchConditions.getGender(), userLv, searchConditions.getPostArea(),"",brandActivityHead.getActivity());
			// int hasMore =
			// SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start),
			// Integer.parseInt(Constants.PRODUCT_LIST_END));
			// int hasPageNum =
			// SearchUtil.hasPageNum(Integer.parseInt(searchResult.getCount()),Integer.parseInt(Constants.PRODUCT_LIST_END));
			map.put("start", start);
			map.put("searchResult", searchResult);
			map.put("userLv", userLv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 活动商品列表筛选条件
	 */

	@ResponseBody
	@RequestMapping(value = "/search/conditions", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getSearchConditions(SearchConditions searchConditions, HttpServletRequest request, String gender, Model model) {
		//SearchUtil.SystemConditions(searchConditions);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User user = getSessionUser(request);
			String topicId=searchConditions.getTopicId();
			if(!StringUtil.isEmpty(topicId)){
				topicId=topicId.trim();
			}
			String userId=StringUtils.isEmpty(user) ? "" : user.getUserId();
			BrandActivityHead brandActivityHead = spBizActivityService.getBrandActivityHead(userId, topicId);
			SearchResult searchResult = spBizActivityService.searchActivityProduct(searchConditions.getTopicId(), null, null, searchConditions.getTagId(), null, searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), searchConditions.getGender(), null, searchConditions.getPostArea(),"",brandActivityHead.getActivity());
			// int hasMore =
			// SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start),
			// Integer.parseInt(Constants.PRODUCT_LIST_END));
			// int hasPageNum =
			// SearchUtil.hasPageNum(Integer.parseInt(searchResult.getCount()),Integer.parseInt(Constants.PRODUCT_LIST_END));
			map.put("searchResult", searchResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
