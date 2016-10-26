package com.shangpin.biz.service;

import com.shangpin.biz.bo.BrandShop;
import com.shangpin.biz.bo.SearchResult;

/**
 * 品牌店首页
 * 
 * @author wangfeng
 *
 */
public interface SPBizBrandShopService {

	/**
	 * M站调用
	 */
	BrandShop queryBrandShop(String userId, String brandId, String pageIndex, String pageSize, String userLv, String price, String size, String colorId, String tagId, String categoryId, String order, String postArea, String imei,String channnelType) throws Exception;
	/**
	 * M站调用
	 */
	SearchResult queryBrandShopProduct(String brandId, String pageIndex, String pageSize, String userLv, String price, String size, String colorId, String tagId, String categoryId, String order, String postArea, String imei,String channnelType) throws Exception;


}
