package com.shangpin.web.controller.brand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shangpin.biz.bo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.biz.service.SPBizBrandShopService;
import com.shangpin.biz.service.SPBizBrandService;
import com.shangpin.biz.utils.SearchParamUtil;
import com.shangpin.core.entity.HotBrands;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.SearchUtil;

/** 
* @ClassName: BrandController 
* @Description:品牌相关方法控制层
* @author qinyingchun
* @date 2014年10月24日
* @version 1.0 
*/
@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	private static final String LIST = "brand/list";
//	private static final String BRAND_PRODUCT = "brand/brand_product";

	private static final String FLAGSHOP_BRAND_PRODUCT = "flagshop/brandProductList";

	private static final String BRAND_PRODUCT_NEW = "brand/brand_product";
	
	@Autowired
	private SPBizBrandService brandService;
	@Autowired
	private SPBizBrandShopService aspBizBrandShopService;
	/**
	 * 
	* @Title: list 
	* @Description: 品牌列表
	* @param @param gender
	* @param @param model
	* @param @return
	* @return String
	* @throws 
	* @Create By qinyingchun
	* @Create Date 2014年10月25日
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		logger.debug("BrandController list start!");
		Brands brands = brandService.brandList();
		//List<HotBrands> hotBrands = brandService.hotBrands(); 页面未引用
		model.addAttribute("brands", brands);
		//model.addAttribute("hotBrands", hotBrands);
		logger.debug("BrandController list end!");
		return LIST;
	}
	

	
	@RequestMapping(value = "/product/list/more", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMore(SearchConditions searchConditions, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		String start=searchConditions.getStart();
		SearchProductResult searchResult = brandService.brandProductList(null, start, Constants.PRODUCT_LIST_END, searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
		
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start), Integer.parseInt(Constants.PRODUCT_LIST_END));
		map.put("searchResult", searchResult);
		map.put("hasMore", hasMore);
		map.put("userLv", userLv);
		return map;
	}
	
	
	@RequestMapping(value = "/flagshopProduct/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String flagShopBrandProduct(SearchConditions searchConditions, Model model, HttpServletRequest request,String title,String imgUrl){
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		String start = ( StringUtils.isEmpty(searchConditions.getStart() )) ? "1" : searchConditions.getStart();
		SearchProductResult searchResult = brandService.brandProductList(null, start, Constants.PRODUCT_LIST_END, searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
		SearchProductResult filterCondition = brandService.brandProductList(null, null,null, searchConditions.getBrandNo(), null, null, null, null,null, userLv,SearchType.ALL_FILTER, searchConditions.getPostArea());
		String queryCondition = SearchUtil.initQueryConditions(searchConditions);
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()), Integer.parseInt(start),Integer.parseInt(Constants.PRODUCT_LIST_END));
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("brandNo", searchConditions.getBrandNo());
		model.addAttribute("start", searchConditions.getStart());
		model.addAttribute("queryCondition", queryCondition);
		model.addAttribute("searchConditions", searchConditions);
		model.addAttribute("filterCondition", filterCondition);
		
		model.addAttribute("hasMore", hasMore);
		model.addAttribute("userLv", userLv);
		model.addAttribute("title",title);
		model.addAttribute("imgUrl", imgUrl);
		return FLAGSHOP_BRAND_PRODUCT;
	}
	@RequestMapping(value = "/flagshopProduct/list/more", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getflagShopProductMore(SearchConditions searchConditions, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		String start=searchConditions.getStart();
		SearchProductResult searchResult = brandService.brandProductList(null, start, Constants.PRODUCT_LIST_END, searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start), Integer.parseInt(Constants.PRODUCT_LIST_END));
		map.put("hasMore", hasMore);
		map.put("searchResult", searchResult);
		map.put("userLv", userLv);
		return map;
	}
	/**
	 * 
	 * @param searchConditions
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product/list",  method = { RequestMethod.GET, RequestMethod.POST })
	public String brandProduct(SearchConditions searchConditions,Model model,String filters, HttpServletRequest request){
		BrandShop brandShop=null;
		SearchResult searchResult =null;
		try {
			User user = getSessionUser(request);
			String userId = getUserId(request);
			String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
			String start = (StringUtils.isEmpty(searchConditions.getStart())) ? "1" : searchConditions.getStart();
			SearchParam searchParam=new SearchParam();
			if (!StringUtils.isEmpty(filters)) {
				searchParam = SearchParamUtil.parse(filters, "", null);
				searchConditions.setBrandNo(searchParam.getBrandId());
				searchConditions.setCategoryNo(searchParam.getCategoryId());
				searchConditions.setChannnelType(searchParam.getChannnelType());
			}
			//筛选项
			brandShop = aspBizBrandShopService.queryBrandShop(userId, searchConditions.getBrandNo(), null, null, null, null, null, null, null,null, null, searchConditions.getPostArea(),"",searchConditions.getChannnelType());
			searchResult = aspBizBrandShopService.queryBrandShopProduct(searchConditions.getBrandNo(), start, Constants.PRODUCT_LIST_END, userLv,searchConditions.getPrice(), searchConditions.getSize(), searchConditions.getColor(), searchConditions.getTagId(),  searchConditions.getCategoryNo(), searchConditions.getOrder(), searchConditions.getPostArea(),"",searchConditions.getChannnelType());
			int hasPageNum = SearchUtil.hasPageNum(Integer.parseInt(searchResult.getCount()),Integer.parseInt(Constants.PRODUCT_LIST_END));
			if(StringUtils.isEmpty(searchParam.getChannnelType())){
	            model.addAttribute("statusSale", "2");
	        }else{
	        	model.addAttribute("statusSale", "0");
	        }
			model.addAttribute("brandShop", brandShop);
			model.addAttribute("searchConditions", searchConditions);
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("userLv", userLv);
			model.addAttribute("hasPageNum", hasPageNum);
			if(StringUtils.isEmpty(searchConditions.getOrder())){
				model.addAttribute("isHot", "1");
			}else{
				if(searchConditions.getOrder().equals("5")){
					model.addAttribute("isHot", "0");
				}else{
					model.addAttribute("isHot", "1");
				}
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return BRAND_PRODUCT_NEW;
	}
	

	/**
	 * 
	* @Title: getNewMore 
	* @Description:品牌商品列表加载更多
	* @param @return
	* @return String
	* @throws 
	* @Create By liling
	* @Create Date 2015年3月6日
	 */
	@ResponseBody
	@RequestMapping(value = "/new/more",  method = {RequestMethod.GET,RequestMethod.POST})
	public  Map<String, Object> getNewMore(SearchConditions searchConditions, HttpServletRequest request, String gender, Model model) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			
			User user = getSessionUser(request);
			String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
			String start=String.valueOf(Integer.parseInt(searchConditions.getStart())+1);
			SearchResult searchResult = aspBizBrandShopService.queryBrandShopProduct(searchConditions.getBrandNo(), start, Constants.PRODUCT_LIST_END, userLv,searchConditions.getPrice(), searchConditions.getSize(), searchConditions.getColor(), searchConditions.getTagId(),  searchConditions.getCategoryNo(), searchConditions.getOrder(), searchConditions.getPostArea(),"",searchConditions.getChannnelType());
						map.put("searchResult", searchResult);
			map.put("start", start);
			map.put("userLv", userLv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 
	* @Title: getSearchConditions 
	* @Description:品牌商品列表筛选条件
	* @param @return
	* @return String
	* @throws 
	* @Create By liling
	* @Create Date 2015年3月6日
	 */
	@ResponseBody
	@RequestMapping(value = "/search/conditions",  method = {RequestMethod.GET,RequestMethod.POST})
	public  Map<String, Object> getSearchConditions(SearchConditions searchConditions, HttpServletRequest request, String gender, Model model) {
		//SearchUtil.SystemConditions(searchConditions);
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			
			SearchResult searchResult = aspBizBrandShopService.queryBrandShopProduct(searchConditions.getBrandNo(), null, null, null,searchConditions.getPrice(), searchConditions.getSize(), searchConditions.getColor(), searchConditions.getTagId(),  searchConditions.getCategoryNo(), null, searchConditions.getPostArea(),"",searchConditions.getChannnelType());
			map.put("searchResult", searchResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
