package com.shangpin.web.utils;

import com.shangpin.biz.bo.User;
import com.shangpin.biz.utils.ClientUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.user.LoginController;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UserUtil {
	private static final Logger logger  = LoggerFactory.getLogger(UserUtil.class);

	/**
	 * 检测是否是app  ture:是; false:不是
	 * @param request
	 * @return
	 */
	public static boolean isApp(HttpServletRequest request){
		String ua1 = request.getHeader("User-Agent");//iso
    	String ua2 = request.getHeader("origin");//安卓
    	
    	if(org.apache.commons.lang3.StringUtils.isNotBlank(ua2)){
    		ua2 = ua2.toLowerCase();
    	}
    	
    	if(ClientUtil.CheckApp(ua1)||ClientUtil.CheckOrigin(ua2)){
    		return true;
    	}
    	return false;
	}
	
	
	/**
	 * 得到用户id  ture:是; false:不是
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request){
		boolean isApp = isApp(request);
		String userId;
		if(isApp){
			userId=request.getHeader(Constants.APP_COOKIE_NAME_UID);
		}else{
			userId =(String) request.getSession().getAttribute(Constants.SESSION_USERID);
		}
		return userId;
	}
	
	/**
	 * 
	 * getThridCookie:<br/>
	 * @param request
	 * @return
	 */
	public static Map<String, String> getThridCookie(HttpServletRequest request){
		Map<String, String> thirdMap = getThirdMap(request);
		return thirdMap==null? new HashMap<String,String>():thirdMap;
		//提交订单接口不需要尚品客信息，尚品客信息在购物车控制
	}
	/**
	 *
	 * getThridCookie:<br/>
	 * @param request
	 * @return
	 */
	public static Map<String, String> getThridBothCookie(HttpServletRequest request){
		String channelNo = (String) request.getSession().getAttribute(Constants.THRID_TOKEN_ChannelNo);
		String channelId = (String) request.getSession().getAttribute(Constants.THRID_TOKEN_ChannelId);
		Map<String, String> thirdMap = getThirdMap(request);
		if(thirdMap!=null){
			return thirdMap;
		}
		thirdMap = new HashMap<>();
		if(StringUtils.isNotBlank(channelNo)||StringUtils.isNotBlank(channelId)){
			thirdMap.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_SOURCE, channelNo);
			thirdMap.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_CAMPAIGN, channelId);
			thirdMap.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_PARAM, "");
			thirdMap.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_CHANNEL_TYPE, "0");//THRID_COOKIE_CHANNEL_TYPE 尚品客传 0 ;  网盟 传1
		}
		return thirdMap;
	}

	public static Map<String,String> getThirdMap(HttpServletRequest request){
		String channelNo = (String) request.getSession().getAttribute(Constants.THRID_TOKEN_ChannelNo);
		String channelId = (String) request.getSession().getAttribute(Constants.THRID_TOKEN_ChannelId);
		Map<String, String> map = new HashMap<>();
		if(StringUtils.isBlank(channelNo)||StringUtils.isBlank(channelId)){
			String wm_source=null;
			String wm_campaign=null;
			String wm_param=null;
			Cookie[] cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0){
				for (Cookie cookie : cookies) {
					if(wm_source==null ||wm_campaign==null ||wm_param==null){
						if(com.shangpin.biz.utils.Constants.THRID_COOKIE_SOURCE.equals(cookie.getName().trim())){
							wm_source=cookie.getValue();
							map.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_SOURCE, wm_source);
						}else if(com.shangpin.biz.utils.Constants.THRID_COOKIE_CAMPAIGN.equals(cookie.getName().trim())){
							wm_campaign=cookie.getValue();
							map.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_CAMPAIGN, wm_campaign);
						}else if(com.shangpin.biz.utils.Constants.THRID_COOKIE_PARAM.equals(cookie.getName().trim())){
							wm_param=cookie.getValue();
							map.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_PARAM, wm_param);
						}
					}
				}
			}
			if(wm_source!=null && wm_campaign!=null){
				map.put(com.shangpin.biz.utils.Constants.THRID_COOKIE_CHANNEL_TYPE, "1");
				return map;
			}
		}
		return null;
	}

	/**
	 * 为用户赋值默认头像
	 * @param user 用户对象
     */
	public static User setDefaultUserIcon(User user){
		String icon = user.getIcon();
		if(StringUtils.isBlank(icon)){
			//增加默认头像
			Map<String, String> iconMap = new HashMap<>();
			iconMap.put("普通会员", "http://pic6.shangpin.com/group1/M00/D7/05/rBQKaFaKCrKAE-1MAAATatXinY0856.png");
			iconMap.put("黄金会员", "http://pic6.shangpin.com/group1/M00/D7/05/rBQKaFaKCrCAR-m-AAAZs-ncDX8318.png");
			iconMap.put("白金会员", "http://pic5.shangpin.com/group1/M00/D7/05/rBQKaFaKCrOAZOYiAAAaEcJFeUk549.png");
			iconMap.put("钻石会员", "http://pic6.shangpin.com/group1/M00/D7/05/rBQKaFaKCrGAHGORAAAbrQ3CN4c900.png");
			String keyLv = user.getLevel().trim();
			icon = iconMap.get(keyLv);
			user.setIcon(icon==null?"":icon);
		}
		return user;
	}

	/**
	 * 绑定手机号成功后，用户放入session
	 * @param user 用户对象
	 * @param session session对象
     */
	public static String loginUserToSession(User user,  HttpSession session){
		//判断是否需要进行绑定
		if(!user.isBindPhone()){//需要去绑定手机号
			String type = (String)session.getAttribute(Constants.THRIDLOGIN_SESSION_TYPE);//2 邮箱  3 微信,4 QQ,5微博,6支付宝
			//将当前用户及back地址暂时存入session
			session.setAttribute(Constants.THRIDLOGIN_SESSION_USER,user);
			if(type==null){
				return "redirect:/login";
			}else if("2".equals(type)){
				return LoginController.MAIL_BINDPHONE_PAGE;//跳转到邮箱绑定手机号页面
			}else if("3".equals(type)||"4".equals(type)||"5".equals(type)){
				return LoginController.THIRD_BINDPHONE_PAGE;//第三方绑定邮箱页面
			}else {
				return "redirect:/login";
			}

		}
		logger.info("放入session");
		List<String> ids;
		if((ids = user.getUserIds())==null || ids.isEmpty()){
			ids = new ArrayList<>();
			ids.add(user.getUserId());
			user.setUserIds(ids);
		}
		session.setAttribute(Constants.SESSION_USER, user);
		logger.info("放入session成功");
		session.setAttribute(Constants.SESSION_USERID, user.getUserId());
		String back = (String)session.getAttribute(Constants.THRIDLOGIN_SESSION_BACK);
		if(StringUtils.isNotBlank(back)){
			back = back.replace("9uuuuu9","&");
			return "redirect:"+back;
		}else{
			return "redirect:/user/home";
		}
	}

	/**
	 * 校验用户属于邮箱用户还是手机用户
	 * @param userName 用户名
	 * @return 1：phone 2：email  null:other
     */
	public static String testPhoneOrEmail(String userName){
		//邮箱的正则规则
		String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		//手机的正则规则
		String MOBILE_REGEX = "^1\\d{10}$";
		if(Pattern.matches(MOBILE_REGEX,userName)){
			return "1";
		}else if(Pattern.matches(EMAIL_REGEX,userName)){
			return "2";
		}
		return null;
	}

}
