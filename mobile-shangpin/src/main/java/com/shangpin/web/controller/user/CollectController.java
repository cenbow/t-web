package com.shangpin.web.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.reflect.TypeToken;
import com.shangpin.base.service.SearchService;
import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.collect.CollectedProduct;
import com.shangpin.biz.utils.SearchUtil;
import com.shangpin.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.biz.service.SPBizCollectService;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;

/**
 * @ClassName: CollectController
 * @author qinyingchun
 * @date 2014年11月22日
 * @version 1.0
 */
@Controller
@RequestMapping("/collect")
public class CollectController extends BaseController {
	
	@Autowired
	private SPBizCollectService bizCollectService;
	
	private static final String COLLECT_LIST = "collect/activity_list";
	
	private static final String COLLECT_PRODUCT_LIST = "collect/product_list";
	
	private static final String COLLECT_BRANDS_LIST = "collect/brands_list";

	@Autowired
	private SearchService searchService;

	/**
	 *  收藏活动列表接口(collectedActivityList)
	 * @param model
	 * @param request
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request){
		String userId = getUserId(request);
		List<Collect> collects = bizCollectService.collectList(userId, Constants.COLLECT_PAGE_INDEX, Constants.COLLECT_PAGE_SIZE, Constants.SITE_NO_SP);
		model.addAttribute("collects", collects);
		return COLLECT_LIST;
	}
	
	/**
	 * 
	 * @Title: collectBrand
	 * @Description: 收藏品牌
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By liling
	 * @Create Date 2014年12月9日
	 */
	@RequestMapping(value = "/brand", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> collectBrand(HttpServletRequest request, @RequestParam("brandId") String brandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		 String code = "";
		User user = getSessionUser(request);
		if (user == null || StringUtils.isEmpty(user.getUserId())) {
			code = Constants.UNLOGIN;
			map.put("code", code);
			return map;
		}
		map = bizCollectService.collectBrand(user.getUserId(), brandId);
		return map;
	}
	/**
	 * 
	 * @Title: appCollectBrand
	 * @Description: 在app中收藏品牌
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By liling
	 * @Create Date 2014年12月9日
	 */
	@RequestMapping(value = "app/brand", method = { RequestMethod.GET, RequestMethod.POST })
	public String appCollectBrand(HttpServletRequest request, @RequestParam("brandId") String brandId, String back) {
		User user = getSessionUser(request);
		if (user == null || StringUtils.isEmpty(user.getUserId())) {
			  return "redirect:" + back;
		}
		bizCollectService.collectBrand(user.getUserId(), brandId);
		return "redirect:" + back;
	}
	/**
	 * 
	 * @Title:
	 * @Description: 取消收藏品牌
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By liling
	 * @Create Date 2014年12月9日
	 */
	@RequestMapping(value = "/cancle/brand", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> cancleCollectBrand(HttpServletRequest request, @RequestParam("brandId") String brandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		User user = getSessionUser(request);
		if (user == null || StringUtils.isEmpty(user.getUserId())) {
			code = Constants.UNLOGIN;
			map.put("code", code);
			return map;
		}
		map = bizCollectService.cancleCollectBrand(user.getUserId(), brandId);
		return map;
	}
	
	/**
	 * 
	 * @Title: collectProduct
	 * @Description: 收藏商品
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月18日
	 */
	@RequestMapping(value = "/product", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> collectProduct(HttpServletRequest request, @RequestParam("skuId") String skuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		User user = getSessionUser(request);
		if (user == null || StringUtils.isEmpty(user.getUserId())) {
			code = Constants.UNLOGIN;
			map.put("code", code);
			return map;
		}
		map = bizCollectService.collectProduct(Constants.SHANGPIN_SHOPTYPE, skuId, user.getUserId(), null, null, null, null);
		return map;
	}
	
	/**
	 * 
	 * @Title: cancleCollectProduct
	 * @Description: 取消收藏商品
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月18日
	 */
	@RequestMapping(value = "/product/cancel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> cancleCollectProduct(HttpServletRequest request, @RequestParam("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		User user = getSessionUser(request);
		if (user == null || StringUtils.isEmpty(user.getUserId())) {
			code = Constants.UNLOGIN;
			map.put("code", code);
			return map;
		}
		map = bizCollectService.cancleCollectProduct(Constants.SHANGPIN_SHOPTYPE, id, user.getUserId());
		return map;
	}
	
	/**
	 * 
	 * @Title:collectProducts
	 * @Description:单品收藏列表
	 * @param pageIndex
	 * @param pageSize
	 * @param shopType
	 * @param model
	 * @param request
	 * @return
	 * @author qinyingchun
	 * @date 2015年3月26日
	 */
	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public String collectProducts(String pageIndex, String pageSize, String shopType, String postArea, Model model, HttpServletRequest request){
		String userId = getUserId(request);
		List<CollectProduct> products = bizCollectService.collectProductList(userId, pageIndex, pageSize, shopType, "0");
		if(null == products){
			return COLLECT_PRODUCT_LIST;
		}
		List<CollectProduct> nextCollectProducts = bizCollectService.collectProductList(userId, String.valueOf(Integer.parseInt(pageIndex) + 1), pageSize, shopType, "0");
		String hasMore = "0";
		if(null != nextCollectProducts && nextCollectProducts.size() > 0){
			hasMore = "1";
		}
		List<CollectedProduct>  result = convertList(products);
		model.addAttribute("hasMore", hasMore);
		model.addAttribute("products",  result);
		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("pageSize", pageSize);
		return COLLECT_PRODUCT_LIST;
	}
	
	@RequestMapping(value = "/product/more", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectProductList(String pageIndex, String pageSize, String shopType, Model model, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = getUserId(request);
		String userLv = getSessionUser(request).getLv();
		List<CollectProduct> products = bizCollectService.collectProductList(userId, pageIndex, pageSize, shopType, "0");
		List<CollectProduct> nextCollectProducts = bizCollectService.collectProductList(userId, String.valueOf(Integer.parseInt(pageIndex) + 1), pageSize, shopType, "0");
		String hasMore = "0";
		if(null != nextCollectProducts && nextCollectProducts.size() > 0){
			hasMore = "1";
		}
		List<CollectedProduct> result = convertList(products);
		map.put("products",result);
		map.put("hasMore", hasMore);
		map.put("userLv", userLv);
		return map;
	}

	private List<CollectedProduct> convertList(List<CollectProduct> products){

		List<String> spuList = new ArrayList<>();
		Map<String,CollectProduct> colMap = new HashMap<>();

		List<CollectProduct> specialList = new ArrayList<>();
		for (CollectProduct product : products) {
			if(product.getType().equals(CollectProduct.TYPE_ECARD) || product.getType().equals(CollectProduct.TYPE_PCARD)){
				specialList.add(product);
			}else{
				spuList.add(product.getProductId());
				colMap.put(product.getProductId(),product);
			}
		}

		if (spuList.isEmpty()){
			return new ArrayList<>();
		}

		List<CollectedProduct>  result = new ArrayList<>();
		String data = searchService.searchProductList(spuList);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(data)) {
			SearchProductJson<ProductSreach> productJson = JsonUtil.fromJson(data, new TypeToken<SearchProductJson<ProductSreach>>(){}.getType());
			if(productJson!=null && productJson.getDocs()!=null){
				List<Product> productList = SearchUtil.initJsonProductList(productJson.getDocs(), "1", null, Constants.MOBILE_VERSION);
				if(!productList.isEmpty()){
					for (Product product : productList) {
						 product = SearchUtil.getPromionProduct(product,"",null,null);
						String spu = product.getProductId();
						CollectProduct productColl = colMap.get(spu);
						result.add(new CollectedProduct(productColl.getId(),productColl.getType(),productColl.getIsShelve(),product));
					}
				}
			}
		}
		//购物卡
		for (CollectProduct cpl : specialList) {
			if(CollectProduct.TYPE_ECARD.equals(cpl.getType())){
				cpl.setBrandNameCN("电子卡");
				cpl.setBrandNameEN("电子卡");
			}
			if(CollectProduct.TYPE_PCARD.equals(cpl.getType())){
				cpl.setBrandNameCN("实物卡");
				cpl.setBrandNameEN("实物卡");
			}
			CollectedProduct product = new CollectedProduct();
			BeanUtils.copyProperties(cpl, product);
			product.setProductName(cpl.getName());
			product.setTaxSellPrice(cpl.getMarketPrice());
			if( "1".equals(product.getIsPromotion()) ){
				product.setTaxSellPrice(cpl.getPromotionPrice());
			}else{
				product.setTaxSellPrice(cpl.getLimitedPrice());
			}
			product.setPriceDesc("");
			product.setPriceTitle("");
			product.setDescColor("");
			result.add(product);
		}
		return result;
	}

	/**
	* 收藏品牌列表接口
	* @Description request
	*/
	@RequestMapping(value = "/brand/list", method = RequestMethod.GET)
	public String collectBrands(String pageIndex, String pageSize, Model model, HttpServletRequest request){
		String userId = getUserId(request);
		List<Collect> brands = bizCollectService.collectBrands(userId, pageIndex, pageSize);
		if(null == brands){
			return COLLECT_BRANDS_LIST;
		}
		List<Collect> nextCollectBrands = bizCollectService.collectBrands(userId, String.valueOf(Integer.parseInt(pageIndex) + 1), pageSize);
		String hasMore = "0";
		if(null != nextCollectBrands && nextCollectBrands.size() > 0){
			hasMore = "1";
		}
		model.addAttribute("hasMore", hasMore);
		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("brands", brands);
		return COLLECT_BRANDS_LIST;
	}
	
	@RequestMapping(value = "/brand/more", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBrandsList(String pageIndex, String pageSize, Model model, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = getUserId(request);
		List<Collect> brands = bizCollectService.collectBrands(userId, pageIndex, pageSize);
		List<Collect> nextCollectBrands = bizCollectService.collectBrands(userId, String.valueOf(Integer.parseInt(pageIndex) + 1), pageSize);
		String hasMore = "0";
		if(null != nextCollectBrands && nextCollectBrands.size() > 0){
			hasMore = "1";
		}
		map.put("brands", brands);
		map.put("hasMore", hasMore);
		return map;
	}
}
