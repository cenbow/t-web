package com.shangpin.base.utils;

/**
 * 
 * @author sunweiwei
 * @version v1.0
 */
public class GlobalConstants {

	// ------------ base api config begin------------------//

	public static final String BASE_URL_SHANGPIN_SHANGPIN = PropertyUtil.getStrValue("base.url_shangpin_shangpin");
	public static final String BASE_URL_SHANGPIN_AOLAI = PropertyUtil.getStrValue("base.url_shangpin_aolai");
	public static final String BASE_URL_AOLAI_AOLAI = PropertyUtil.getStrValue("base.url_aolai_aolai");
//	public static final String BASE_URL_AOLAI_SHANGPIN = PropertyUtil.getStrValue("base.url_aolai_shangpin");
	public static final String BASE_URL_SEARCH = PropertyUtil.getStrValue("base.url_search");
	public static final String BASE_URL_CUSTOM = PropertyUtil.getStrValue("base.url_custom");
	public static final String BASE_URL_TRADE = PropertyUtil.getStrValue("base.url_trade");
	public static final String BASE_URL_PRODUCT = PropertyUtil.getStrValue("base.url_product");
//	public static final String BASE_URL_SHANGPIN_LISTINGCATALOG = PropertyUtil.getStrValue("base.url_shangpin_ListingCatalog");
	public static final String BASE_URL_IOGORDER = PropertyUtil.getStrValue("base.url_iogOrder");
	public static final String BASE_URL_GETLOCKSUPPLIER =PropertyUtil.getStrValue("base.url_getOrderSupplier");
	
	public static final String BASE_URL_MESSAGE_CENTER =PropertyUtil.getStrValue("base.url_message_center");
	public static final String BASE_URL_SHANGPIN_M = PropertyUtil.getStrValue("base.url_shangpin_m");
	public static final String BASE_URL_ERP = PropertyUtil.getStrValue("base.url_erp");

	/**
	* @Date 2016/8/8
	* @Description 新添加接口V3(订单购物车相关服务)
	*/
	public static final String BASE_URL_ORDER = PropertyUtil.getStrValue("base.url_order");

	//用户系统
	public static final String MOBILE_VERSION = PropertyUtil.getStrValue("base.mobile.version");
	public static final String MOBILE_PLAT = PropertyUtil.getStrValue("base.mobile.plat");
	public static final String USER_SERVICE = PropertyUtil.getStrValue("base.url_user_center");
	public static final String USER_SERVICE_USER = USER_SERVICE+"api/user/";
	public static final String USER_SERVICE_ADDR = USER_SERVICE+"addr/";

	//订单系统调用
	public static final String ORDER_VERSION="M";
	public static final String ORDER_REQUEST_FRMO="M站";
	
	//推荐
	public static final String BASE_URL_REPCONMONED = PropertyUtil.getStrValue("base_url_repconmoned");
	
	/** 缓存标识头base */
	public static final String CACHE_BASE = "base";

	public static final String PAGE_NO_1_STR = "1";
	public static final String PAGE_SIZE_10_STR = "10";
	/** 商品列表图片高度 */
	public static final String MERCHANDISE_LIST_PICTURE_HEIGHT = "422";
	/** 商品列表图片宽度 */
	public static final String MERCHANDISE_LIST_PICTURE_WIDTH = "318";
	/** 小图片宽度 */
	public static final String SAMLL_PICTURE_WIDTH = "120";
	/** 小图片高度 */
	public static final String SAMLL_PICTURE_HEIGHT = "160";
	/** 主支付方式 */
	public static final String MAIN_PAY = "2";
	// ------------ base api config end------------------//
	
	

}
