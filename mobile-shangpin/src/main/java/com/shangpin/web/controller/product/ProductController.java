package com.shangpin.web.controller.product;

import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.product.ProductPrice;
import com.shangpin.biz.enums.PriceColor;
import com.shangpin.biz.service.*;
import com.shangpin.biz.utils.ClientUtil;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.SearchUtil;
import org.apache.commons.collections.CollectionUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @ClassName: ProductController 
* @Description:商品详情页
* @author qinyingchun
* @date 2014年11月3日
* @version 1.0 
*/
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{
	
	private static final Logger logger  = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private SPBizSearchService searchService;
	@Autowired
	SPBizProductService spBizProductService;
	@Autowired
	SPBizProductSizeService spBizProductSizeService;
	@Autowired
	SPBizProductTemplateService spBizProductTemplateService;
	@Autowired
	SPBizProductCommentService spBizProductCommentService;
	@Autowired
	SPBizRecProductService spBizRecProductService;
	@Autowired
	private SPBizUserService userService;
	@Autowired
    private SPBizSendInfoService bizSendInfoService;

	private static final String PRODUCT_DETAIL = "product/index";
	private static final String PRODUCT_SIZE = "product/size";
	private static final String NEWLIST = "product/new_list";
	private static final String NEWINDEX = "product/new_index";
	private static final String GUESSLIKE = "product/guessLike";
	private static final String SERVICE = "product/service";
	private static final String APPGUESSLIKE = "app_product/guessLike";
	private static final String APPINDEX = "app_product/index";
	private static final String APP_GIFTCARD_INDEX = "app_product/giftcard_index";
	private static final String PRODUCT_COMMENT = "comment/index";
	private static int HASPAGENUM =0;
	private static final String COMMENT_PAGE_INDEX ="20";
	private static Map<String, SizeInfo> sizeInfo = new HashMap<String, SizeInfo>();
	//买赠分享详情页
	private static final String PRODUCT_FREEBIEDETAIL = "product/freebie/index";
	
	
	/**
	 * 
	 * @Title: detail
	 * @Description:详情页数据
	 * @param productNo
	 * @param topicId
	 * @param model
	 * @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@SuppressWarnings("unused")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(@RequestParam String productNo, String topicId,String picNo, String ch,Model model, HttpServletRequest request) {
		model.addAttribute("productNo", productNo);
		model.addAttribute("topicId", topicId);
		//解决cookie刷新不及时的问题，只有微信浦发银行时用到
		model.addAttribute("ch", ch);
		User user = getSessionUser(request);
		String userId = user != null ? user.getUserId() : "";
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		ProductDetail productDetail = spBizProductService.findProductDetail(topicId, productNo, userId,picNo);
		List<RecProduct> guessLikeList = spBizRecProductService.findRecProductObj(userId, Constants.GUESSLIKE_RECPRODUCT_TYPE, Constants.SHANGPIN_SHOPTYPE, productNo, Constants.GUESSLIKE_RECPRODUCT_START, Constants.GUESSLIKE_RECPRODUCT_END);
		//搜索添加市场价
		List<Product> guessLikeListProduct = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(guessLikeList))	{
			for (RecProduct recProduct : guessLikeList) {
				guessLikeListProduct.add(com.shangpin.biz.utils.SearchUtil.getPromionProduct(recProduct,null,null,null));
			}			
		}
		ProductComment productComment = spBizProductCommentService.queryProductComment(productNo, "0", "2","0");
		String ua = request.getHeader("User-Agent").toLowerCase();
		SiteType siteType =ClientUtil.getSiteType(request);
		ProductTemplate productTemplate = spBizProductTemplateService.findProductTemplate("5", productNo,siteType);
		model.addAttribute("productTemplate", productTemplate);
		model.addAttribute("productDetail", getPriceColor(productDetail,user!=null&&user.isVip()));
		model.addAttribute("guessLikeList", guessLikeListProduct);
		model.addAttribute("productComment", productComment);
		model.addAttribute("userLv", userLv);
		return PRODUCT_DETAIL;
	}
    /***
     * init 到商品评论页 
     * @param productNo
     * @param topicId
     * @param picNo
     * @param ch
     * @param model
     * @param tagId
     * @param request
     * @return
     */
	@RequestMapping(value = "/prCommentList", method = RequestMethod.GET)
    public String prCommentList(@RequestParam String productNo, String topicId,String picNo, String ch,Model model, String tagId,HttpServletRequest request) {
        model.addAttribute("productNo", productNo);
        //解决cookie刷新不及时的问题，只有微信浦发银行时用到
        model.addAttribute("ch", ch);
        User user = getSessionUser(request);
        String userId = user != null ? user.getUserId() : "";
        String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
        ProductDetail productDetail = spBizProductService.findProductDetail(topicId, productNo, userId,picNo);
        ProductComment productComment = spBizProductCommentService.queryProductComment(productNo, "0", COMMENT_PAGE_INDEX,tagId);       
        model.addAttribute("seleTagId", tagId);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("productComment", productComment);
        model.addAttribute("userLv", userLv);
        model.addAttribute("productDetail", productDetail);
        return PRODUCT_COMMENT;
    }
	/**
	 * 
	 * @Title: getSize
	 * @Description:详情页尺码数据
	 * @param productNo
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "/getSize", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getSize(String productNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductSize productSize = spBizProductSizeService.findProductSize(productNo);
		int size=0;
		ProductDetailSizes productDetailSize=productSize.getProductDetailSize();
		if(productDetailSize!=null){
			List<ProductDetailSizeTable> table=productDetailSize.getTable();
			if(table!=null&&table.size()>0){
				List<String> value=table.get(0).getValue();
				if(value!=null){
					size=value.size();
				}
			}
		}
		List<Integer> groups = new ArrayList<Integer>();
		int group = size % 4 == 0 ? size / 4 : size / 4 + 1;
		for (int i = 0; i < group; i++) {
			groups.add(i);
		}
		map.put("groups", groups);
		map.put("productSize", productSize);
		return map;
	}
	/**
	 * 
	 * @Title: getTemplate
	 * @Description:详情页模板数据
	 * @param productNo
	 * @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "/getTemplate",method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getTemplate(String type, String productNo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		SiteType siteType =ClientUtil.getSiteType(request);
		ProductTemplate productTemplate = spBizProductTemplateService.findProductTemplate(type, productNo,siteType);
		map.put("productTemplate", productTemplate);
		return map;
	}
	/**
	 * 
	 * @Title: getComment
	 * @Description:详情页评论数据
	 * @param productNo
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "/getComment", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getComment(String productNo, String pageIndex, String pageSize,String tagId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductComment productComment = spBizProductCommentService.queryProductComment(productNo, pageIndex, pageSize,tagId);
		for (Tag tag : productComment.getTag()) {
            if (tag.getId().equals(tagId)) {
                HASPAGENUM= SearchUtil.hasPageNum(Integer.parseInt(tag.getCount()),Integer.parseInt(Constants.PRODUCT_LIST_END));
                break;
            }
        }
		map.put("pageIndex", pageIndex);
		map.put("hasMore", "0");
		if(productComment!=null&&productComment.getList()!=null){
		    if(productComment.getList().size()>=Integer.valueOf(COMMENT_PAGE_INDEX)){
		        map.put("hasMore", "1");
		    }
		}
        map.put("hasPageNum", HASPAGENUM);
        map.put("productComment", productComment);
		return map;
	}
	/**
	 * 
	 * @Title: guessLike
	 * @Description:详情页猜您喜欢数据
	 * @param productNo
	 * @param model
	 * @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "/guessLike", method = RequestMethod.GET)
	public String guessLike(String productNo,Model model, HttpServletRequest request) {
		User user = getSessionUser(request);
		String userId = user.getUserId();
		List<RecProduct> guessLikeList = spBizRecProductService.findRecProductObj(userId, Constants.GUESSLIKE_RECPRODUCT_TYPE, Constants.SHANGPIN_SHOPTYPE, productNo, Constants.GUESSLIKE_RECPRODUCT_START, Constants.GUESSLIKE_RECPRODUCT_END);
		model.addAttribute("guessLikeList", guessLikeList);
		return GUESSLIKE;
	}
	/**
	 * 
	 * @Title: template
	 * @Description:详情页模板数据
	 * @param productNo
	 * @param model
	 * @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public String template(String type, String productNo,Model model, HttpServletRequest request) {
		SiteType siteType =ClientUtil.getSiteType(request);
		ProductTemplate productTemplate = spBizProductTemplateService.findProductTemplate(type, productNo,siteType);
		model.addAttribute("productTemplate", productTemplate);
		if(type.equals(Constants.PRODUCT_TEMPLATE_AFTERSALE)){
			return SERVICE;
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: appDetail
	 * @Description:app详情页数据
	 * @param productNo
	 * @param model
	 * @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "/app/detail", method = RequestMethod.GET)
	public String appDetail(@RequestParam String type, String productNo,String topicId,String picNo,Model model, HttpServletRequest request) {

		User user = getSessionUser(request);
		String userId = user != null ? user.getUserId() : "";
		if(type.equals(Constants.APP_DETAIL_BASIC_TYPE)||type.equals(Constants.APP_DETAIL_SHOWPICS_TYPE)){
			ProductDetail productDetail = spBizProductService.findProductDetail(topicId, productNo, userId,picNo);
			model.addAttribute("productDetail", productDetail);
		}
		if(type.equals(Constants.APP_DETAIL_SIZE_TYPE)){
			ProductSize productSize = spBizProductSizeService.findProductSize(productNo);
			int size=0;
			ProductDetailSizes productDetailSize=productSize.getProductDetailSize();
			if(productDetailSize!=null){
				List<ProductDetailSizeTable> table=productDetailSize.getTable();
				if(table!=null&&table.size()>0){
					List<String> value=table.get(0).getValue();
					if(value!=null){
						size=value.size();
					}
				}
			}
			List<Integer> groups = new ArrayList<Integer>();
			int group = size % 4 == 0 ? size / 4 : size / 4 + 1;
			for (int i = 0; i < group; i++) {
				groups.add(i);
			}
			model.addAttribute("groups", groups);
			model.addAttribute("productSize", productSize);
		}
		if(type.equals(Constants.APP_DETAIL_TEMPLATE_TYPE)){
			SiteType siteType =ClientUtil.getSiteType(request);
			ProductTemplate productTemplate = spBizProductTemplateService.findProductTemplate(Constants.PRODUCT_DETAIL_TEMPLATE_TYPE, productNo,siteType);
			model.addAttribute("productTemplate", productTemplate);
		}
		model.addAttribute("type", type);
		model.addAttribute("productNo", productNo);
		  if(Constants.APP_DETAIL_GIFTCARD_TYPE.equals(type) || Constants.APP_DETAIL_GIFTCARD_E_TYPE.equals(type)){
			   return APP_GIFTCARD_INDEX;
			  }
		return APPINDEX;
	}
	/**
	 * 
	 * @Title: appGuessLike
	 * @Description:app详情页猜您喜欢数据
	 * @param productNo
	 * @param model
	 * @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @Create By liling
	 * @Create Date 2015年3月12日
	 */
	@RequestMapping(value = "app/guessLike", method = RequestMethod.GET)
	public String appGuessLike(String productNo,Model model, HttpServletRequest request) {
		User user = getSessionUser(request);
		String userId = user != null ? user.getUserId() : "";
		List<RecProduct> guessLikeList = spBizRecProductService.findRecProductObj(userId, Constants.GUESSLIKE_RECPRODUCT_TYPE, Constants.SHANGPIN_SHOPTYPE, productNo, Constants.GUESSLIKE_RECPRODUCT_START, Constants.GUESSLIKE_RECPRODUCT_END);
		model.addAttribute("guessLikeList", guessLikeList);
		return APPGUESSLIKE;
	}
	
	
	/**
	 * 
	* @Title: sizedesc 
	* @Description:商品尺码信息
	* @param 
	* @return String
	* @throws 
	* @Create By qinyingchun
	* @Create Date 2014年11月18日
	 */
	@RequestMapping(value = "/sizedesc", method = RequestMethod.GET)
	public String sizedesc(String productNo, Model model){
		SizeInfo sizeInf = sizeInfo.get(productNo);
		model.addAttribute("sizeInfo", sizeInf);
		return PRODUCT_SIZE;
	}
	/**
	 * 
	* @Title: newIndex 
	* @Description:商品尺码信息
	* @param 
	* @return String
	* @throws 
	* @Create By qinyingchun
	* @Create Date 2014年11月18日
	 */
	@RequestMapping(value = "/new/index", method = RequestMethod.GET)
	public String newIndex(){
		return NEWINDEX;
		
	}
	/**
	 * 
	* @Title: newList 
	* @Description:商品尺码信息
	* @param 
	* @return String
	* @throws 
	* @Create By qinyingchun
	* @Create Date 2014年11月18日
	 */
	@RequestMapping(value = "/new/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String newList(SearchConditions searchConditions, Model model, HttpServletRequest request){
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
//		SearchResult searchResult = brandService.brandProducts(null, searchConditions.getStart(), Constants.PRODUCT_LIST_END, searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), userLv);
//		String queryCondition = SearchUtil.initQueryConditions(searchConditions);
//		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()), Integer.parseInt(Constants.PRODUCT_LIST_END));
//		model.addAttribute("categories", categories);
//		model.addAttribute("sizes", sizes);
//		model.addAttribute("colors", colors);
//		model.addAttribute("searchResult", searchResult);
//		model.addAttribute("brandNo", searchConditions.getBrandNo());
//		model.addAttribute("start", searchConditions.getStart());
//		model.addAttribute("queryCondition", queryCondition);
//		model.addAttribute("searchConditions", searchConditions);
//		model.addAttribute("hasMore", hasMore);
//		model.addAttribute("userLv", userLv);
		SearchProductResult brandFilter=new SearchProductResult();
		SearchProductResult categoryFilter=new SearchProductResult();
		SearchProductResult otherFilter=new SearchProductResult();
		String categoryNo=searchConditions.getCategoryNo();
		String brandNo=searchConditions.getBrandNo();
		String start = ( StringUtils.isEmpty(searchConditions.getStart() )) ? "1" : searchConditions.getStart();
		SearchProductResult searchResult = searchService.newProductList(null,null, start, Constants.PRODUCT_LIST_END, brandNo, searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), categoryNo, searchConditions.getOrder(),searchConditions.getGender(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
	
		//品牌由品类控制
		if(StringUtils.isEmpty(categoryNo)||"A01".equals(categoryNo)||"A02".equals(categoryNo)){
			categoryFilter= searchService.newProductList(null,null, null,null, null, null, null, null, null,null, searchConditions.getGender(),userLv,SearchType.ALL_FILTER, searchConditions.getPostArea());
			brandFilter=categoryFilter;
		}else{
			categoryFilter = searchService.newProductList(null,null, null,null, null, null, null, null, null,null, searchConditions.getGender(),userLv,SearchType.CATEGORY_FILTER, searchConditions.getPostArea());
			brandFilter = searchService.newProductList(null,null, null,null, null, null, null, null, searchConditions.getCategoryNo(),null, searchConditions.getGender(),userLv,SearchType.BRAND_FILTER, searchConditions.getPostArea());
		}
		
		//筛选条件由品类 和品牌控制
		if((StringUtils.isEmpty(categoryNo)||"A01".equals(categoryNo)||"A02".equals(categoryNo))&&StringUtils.isEmpty(brandNo)){
			otherFilter=categoryFilter;
		}else{
			otherFilter = searchService.newProductList(null,null, null,null, brandNo, null, null, null, categoryNo,null, searchConditions.getGender(),userLv,SearchType.OTHER_FILTER, searchConditions.getPostArea());
		}
		String queryCondition = SearchUtil.initQueryConditions(searchConditions);
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()), Integer.parseInt(start),Integer.parseInt(Constants.PRODUCT_LIST_END));

		model.addAttribute("searchResult", searchResult);
		model.addAttribute("brandNo", searchConditions.getBrandNo());
		model.addAttribute("start", searchConditions.getStart());
		model.addAttribute("queryCondition", queryCondition);
		model.addAttribute("searchConditions", searchConditions);
		model.addAttribute("categoryFilter", categoryFilter);
		model.addAttribute("brandFilter", brandFilter);
		model.addAttribute("otherFilter", otherFilter);
		
		model.addAttribute("hasMore", hasMore);
		model.addAttribute("userLv", userLv);
		
		
		return NEWLIST;
	}
	
	@RequestMapping(value = "/new/list/more", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMore(SearchConditions searchConditions, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		String categoryNo=searchConditions.getCategoryNo();
		String brandNo=searchConditions.getBrandNo();
		String start = ( StringUtils.isEmpty(searchConditions.getStart() )) ? "1" : searchConditions.getStart();
		SearchProductResult searchResult =  searchService.newProductList(null,null, start, Constants.PRODUCT_LIST_END, brandNo, searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), categoryNo, searchConditions.getOrder(),searchConditions.getGender(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start), Integer.parseInt(Constants.PRODUCT_LIST_END));
		map.put("hasMore", hasMore);
		map.put("searchResult", searchResult);
		map.put("userLv", userLv);
		return map;
	}
    
    /**
     * 赠送人productNo,领取人spu
     * @param productNo
     * @param spu
     * @return
     */
   /* private boolean isFreeSpu(String productNo, String spu) {
		 List<Product> tjproducts= sPBiz520SellService.recommendProduct(productNo);
		 if(tjproducts!=null &&tjproducts.size()>0){
			for (Product product : tjproducts) {
				if (product.getProductId().equals(spu)) {
					return true;
				}
			}
		 }
		return false;
	}*/
    /////买赠分享推荐商品
    /**
     * 分享详情页面
     * @param //p
     * @param //model
     * @param request
     * @return
     */
    /*@RequestMapping(value = "/recommend", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> recommend(@RequestParam String p,Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        //1.验证参数
        Map<String,String> parameter= FreeBie520Util.decodeFreebieParam(p);
        if (parameter==null) {
            return map;
        }
        String productNo = parameter.get("spu");
        List<Product> products= sPBiz520SellService.recommendProduct(productNo);
        map.put("products", products);
        map.put("p", p);
        return map;
    }*/
    
    @RequestMapping(value = "/sendcode", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> login(String phoneNum,  HttpServletRequest request) {
        Map<String, Object> map ;
        // 一、点击获取验证码时判断该手机号是否已经注册尚品网
        QuickUser quickUser = userService.checkUser(phoneNum, Constants.CREATE_NEW_USER, String.valueOf("83"));
        String userId = quickUser.getUserId();
        User user = userService.findUserByUserId(userId);
        request.getSession().setAttribute(Constants.SESSION_USER, user);
        // 新用户下发验证码和注册信息
        if (Constants.IS_NEW_USER.equals(quickUser.getIsNewUser())) {
            String msgTemplate = "验证码:{$verifyCode$}，立即输入抢好友送出的同款撞衫！该号码已注册为尚品会员，密码为手机号后6位";
            map=bizSendInfoService.sendPhoneCode(phoneNum, phoneNum, msgTemplate);
            map.put("user", "1");
        } else {
            // 二、已注册的用户点击获取验证码时需判断该手机号是否已经抢过红包
            String msgTemplate = "验证码:{$verifyCode$}，立即输入抢好友送出的同款撞衫！";
            map=bizSendInfoService.sendPhoneCode(phoneNum, phoneNum, msgTemplate);
            map.put("user", "0");
           
        }
        return map;
    }

    public ProductDetail getPriceColor(ProductDetail productDetail,boolean isVip){
		if(productDetail==null){
			return productDetail;
		}
		//logger.info("productDetail:{}  ", JsonUtil.toJson(productDetail));
		String ceil="¥";

		ProductPrice productPrice = new ProductPrice();
		List<ProductFirstProps> firstProps = productDetail.getBasic().getFirstProps();
		if(firstProps==null || firstProps.isEmpty()){
			return productDetail;
		}
		for (ProductFirstProps firstProp : firstProps) {
			List<ProductSecondProps> secondProps = firstProp.getSecondProps();
			if(secondProps==null || secondProps.isEmpty()){
				return productDetail;
			}
			for (ProductSecondProps secondProp : secondProps) {
				String color= "";
				String priceDesc= "";
				String compare_color = PriceColor.NOMAL_COMPARE_PRICE.toString();
				String compare_hasLine="1";
				String compare_priceDesc= "";
				if("1".equals(secondProp.getIsPromotion())){//促销
					color=PriceColor.PROMOTION_PRICE.toString();
					priceDesc= secondProp.getTaxPromotionPrice();
					compare_priceDesc =secondProp.getTaxStandardPrice();
				}else if(isVip){//非促销，会员
					color = PriceColor.VIP_PRICE.toString();
					priceDesc= secondProp.getVipPrice();
					compare_priceDesc =secondProp.getTaxStandardPrice();
				}else {
					color=PriceColor.SHANGPIN_PRICE.toString();
					priceDesc= secondProp.getTaxStandardPrice();
				}
				productPrice.setColor(color);//主价格颜色
				productPrice.setPriceDesc(ceil + priceDesc);
				productPrice.setCompareColor(compare_color);//对比价颜色
				productPrice.setCompareHasLine(compare_hasLine);//是否有下划线
				productPrice.setCompareDesc(org.apache.commons.lang3.StringUtils.isBlank(compare_priceDesc)?"" :ceil+ compare_priceDesc);
				secondProp.setProductPrice(productPrice);
			}
		}
		return productDetail;
	}
	
}
