package com.shangpin.biz.service;


import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.base.ResultSuggestion;
import org.dom4j.DocumentException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface SPBizSearchService {
	/**
	 * 
	 * @Title: searchProductList
	 * @Description:商品列表搜索
	 * @param
	 * @return SearchBrandResult
	 * @throws
	 * @Create By liling
	 * @Create Date 2014年12月23日
	 */
	SearchProductResult searchProductList(String keyword,String navId, String start, String end,String tagId, String brandNo, String price, String color, String size, String categoryNo, String order, String userLv,SearchType searchType, String postArea);
	/**
	 * 
	 * @Title: newProductList
	 * @Description:新品商品列表搜索
	 * @param
	 * @return SearchBrandResult
	 * @throws
	 * @Create By liling
	 * @Create Date 2014年12月23日
	 */
	SearchProductResult newProductList(String keyword,String navId, String start, String end, String brandNo, String price, String color, String size, String categoryNo, String order, String gender, String userLv, SearchType searchType, String postArea);
	/**
	 * @Title: queryLabels
	 * @Description:搜索标签列表
	 * @return SearchResultApp
	 * @Create By liling
	 * @Create Date 2015年10月29日
	 */
	//Map<String,Object> queryLabels(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String type) throws Exception;
	/**
	 * @Title: queryLabels
	 * @Description:搜索标签列表
	 * @return SearchResultApp
	 * @Create By liling
	 * @Create Date 2015年10月29日
	 */
	SearchResult queryLabels(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String type) throws Exception;
	
	public SearchKeyword getSearchKeyword() throws Exception;
	
	public ResultSuggestion suggestion(String keywords);
	/**
	 * 搜索列表页
	 * @param searchConditions
	 * @return
	 */
	public SearchResult searchList(SearchConditions searchConditions);

	/**
	 * 查询活动
	 *
	 * @return
	 * @author wangfeng
	 */
	public SearchResult queryActivityProductList(String subjectNo, String start, String end, String tagId, String brandId, String price, String colorId, String size,
												 String categoyId, String order, String gender, String userLv, String postArea, String isPre, ActivityHead activityHead, String imei, String version);

	/**
	 * 查询品牌
	 *
	 * @return
	 * @author wangfeng
	 */
	public SearchResult queryBrandProductList(String navId, String pageIndex, String pageSize, String tagId, String brandId, String price, String colorId, String size,
											  String categoyId, String order, String userLv, String postArea, String imei, String version,String channnelType);

	/**
	 * 联想词搜索
	 *
	 * @return
	 */
	String querySuggestion(String keyword, String userID, String userIP, String encode) throws Exception;

	/**
	 * 标签商品搜索接口
	 * 2.9.0版本以前客户端使用的旧接口
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 */
	String queryTagProductList(String pageIndex, String pageSize, String userLv, String price, String size, String color, String tagId, String categoryId, String postArea,
							   String brandId, String order) throws Exception;

	/**
	 * 查询品牌商品列表(新)
	 *
	 * @return
	 * @author wangfeng
	 */
	public SearchResultApp queryBrandProductListNew(String navId, String pageIndex, String pageSize, String tagId, String brandId, String price, String colorId, String size,
													String categoyId, String order, String userLv, String postArea, String imei, String version);

	SearchResultApp queryBrandProductListNew(String navId, String pageIndex, String pageSize, String tagId, String brandId, String price, String colorId, String size,
											 String categoyId, String order, String userLv, String postArea, String originalFilters, String dynamicFilters, String imei, String version);


	/**
	 * 查询活动商品列表页(新v2.9.0)
	 *
	 * @return
	 * @author wangfeng
	 */
	public SearchResultApp queryActivityProductListNew(String subjectNo, String start, String end, String tagId, String brandId, String price, String colorId, String size,
													   String categoyId, String order, String userLv, String postArea, String isPre, ActivityHead activityHead, String imei, String version);

	SearchResultApp queryActivityProductListNew(String subjectNo, String start, String end, String tagId, String brandId, String price, String colorId, String size,
												String categoyId, String order, String userLv, String postArea, String isPre, ActivityHead activityHead, String originalFilters, String dynamicFilters, String imei, String version);

	/**
	 * 查询标签商品列表页(新v2.9.0)
	 *
	 * @return
	 * @author wangfeng
	 * @throws Exception
	 */
	public String queryTagProductListNew(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String version) throws Exception;

	/**
	 * 查询关键字商品列表页(新v2.9.0)
	 *
	 * @return
	 * @author wangfeng
	 * @throws Exception
	 */
	public String queryKeyWordProductList(String keywords, String pageIndex, String pageSize, String tagId, String order, String userLv, String filters, String version) throws Exception;

	String queryKeyWordProductList(String keywords, String pageIndex, String pageSize, String tagId, String order, String userLv, String filters, String originalFilters, String dynamicFilters, String version) throws Exception;

	/**
	 * 查询分类商品列表页(新v2.9.0)
	 *
	 * @return
	 * @author wangfeng
	 * @throws Exception
	 */
	public String queryCategoryProductList(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String imei, String version) throws Exception;

	String queryCategoryProductList(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String originalFilters, String dynamicFilters, String imei, String version) throws Exception;

	/**
	 * 首页标签列表
	 *
	 * @throws Exception
	 */
	String queryLabels(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String type, String imei, String version) throws Exception;

	String queryLabels(String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String originalFilters, String dynamicFilters, String type, String imei, String version) throws Exception;
	/**
	 * 尚品客根据productNo获取价格和库存
	 * @param pageIndex
	 * @param pageSize
	 * @param minPrice
	 * @param maxPrice
	 * @param includeProductNo
	 * @return
	 */
	RecProductFor searchProductNos(String start, String end, String minPrice, String maxPrice, String postArea, String includeBrandNo, String excludeBrandNo, String includeCategoryNo, String excludeCategoryNo, String includeProductNo, String excludeProductNo, String userLv, String version);

	/**
	 * 根据productNo获取商品的列表
	 * @return
	 */
	public List<Product> getProductListFromProductNo(List<String> spus, Map<String, String> productAttMap, ActivityHead activityHead, String version);



}
