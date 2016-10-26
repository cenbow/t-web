package com.shangpin.biz.service.impl;

import com.shangpin.biz.bo.*;
import com.shangpin.biz.service.SPBizBrandActivityService;
import com.shangpin.biz.service.SPBizBrandShopService;
import com.shangpin.biz.service.SPBizSearchService;
import com.shangpin.biz.utils.ApiBizData;
import com.shangpin.biz.utils.SearchParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SPBizBrandShopServiceImpl implements SPBizBrandShopService {

    private static final Logger logger = LoggerFactory.getLogger(SPBizBrandShopServiceImpl.class);

    @Autowired
    SPBizBrandActivityService aspBizBrandActivityService;
    @Autowired
    SPBizSearchService aspBizSerchService;

   
    public String queryShop(String userId, String brandId, String pageIndex, String pageSize, String userLv,String price,String size,String colorId,String tagId,String categoryId,String order, String postArea, String imei, String version) throws Exception {
        BrandShop brandShop=new BrandShop();
        OperatHeader operatHeader=new OperatHeader();
        BrandActivityHead brandActivityHead=new BrandActivityHead();
        brandActivityHead=aspBizBrandActivityService.headInfoObj(userId, brandId, "0");
        operatHeader.setActivity(brandActivityHead.getActivity());
        operatHeader.setHead(brandActivityHead.getHead());
        operatHeader.setCoupon(aspBizBrandActivityService.couponInfoObj(userId, brandId, "0"));
        operatHeader.setPromotion(aspBizBrandActivityService.promotionsObj(brandId, "0"));
        brandShop.setOperat(operatHeader);
        SearchResult searchResult=aspBizSerchService.queryBrandProductList("", pageIndex, pageSize, tagId, brandId, price, colorId, size, categoryId, order, userLv, postArea, imei, version,null);
        brandShop.setResult(searchResult);
        String json;
        try {
            json = ApiBizData.spliceData(brandShop, CodeMsgEnum.CODE_SUCCESS.getInfo(), CodeMsgEnum.MSG_SUCCESS.getInfo());
            logger.debug("调用api接口返回数据:" + json);
            return json;
        } catch (Exception e) {
            logger.error("调用api接口返回数据发生错误！");
            e.printStackTrace();
        }
        return ApiBizData.spliceData(CodeMsgEnum.CODE_ERROR.getInfo(), CodeMsgEnum.MSG_ERROR.getInfo());
    }

    /**
     * M站调用
     */
    @Override
    public BrandShop queryBrandShop(String userId, String brandId, String pageIndex, String pageSize, String userLv, String price, String size, String colorId, String tagId, String categoryId, String order, String postArea, String imei,String channnelType) throws Exception {
        BrandShop brandShop=new BrandShop();
        OperatHeader operatHeader=new OperatHeader();
        BrandActivityHead brandActivityHead=new BrandActivityHead();
        brandActivityHead=aspBizBrandActivityService.headInfoObj(userId, brandId, "0");
        operatHeader.setActivity(brandActivityHead.getActivity());
        operatHeader.setHead(brandActivityHead.getHead());
        //增加模板
        operatHeader.setModelOne(aspBizBrandActivityService.modelOneObj(brandId, "2", ""));
        //增加轮播图
        BrandActivityGallery brandActivityGallery = new BrandActivityGallery();
        brandActivityGallery.setList(aspBizBrandActivityService.queryGalleryList("4", "6", brandId,null));
        operatHeader.setGallery(brandActivityGallery);
        
        //分享如果有轮播图 分享轮播图第一张图片
        if( null!=brandActivityGallery&& brandActivityGallery.getList().size()>0){
            String [] pic= brandActivityGallery.getList().get(0).getPic().split("-");
            operatHeader.getHead().getShare().setPic(pic[0]+="-640-256.jsp");
        }
        
        operatHeader.setCoupon(aspBizBrandActivityService.couponInfoObj(userId, brandId, "0"));
        operatHeader.setPromotion(aspBizBrandActivityService.promotionsObj(brandId, "0"));
        brandShop.setOperat(operatHeader);
        SearchResult searchResult=aspBizSerchService.queryBrandProductList("", pageIndex, pageSize, tagId, brandId, price, colorId, size, categoryId, order, userLv, postArea, imei, "2.9.16",channnelType);
        brandShop.setResult(searchResult);
      
        return brandShop;
    }

    @Override
    public SearchResult queryBrandShopProduct(String brandId, String pageIndex, String pageSize, String userLv, String price, String size, String colorId, String tagId, String categoryId, String order, String postArea, String imei,String channnelType) throws Exception {
//        BrandShop brandShop=new BrandShop();
//        OperatHeader operatHeader=new OperatHeader();
//        BrandActivityHead brandActivityHead=new BrandActivityHead();
//        brandActivityHead=aspBizBrandActivityService.headInfoObj(userId, brandId, "0");
//        operatHeader.setActivity(brandActivityHead.getActivity());
//        operatHeader.setHead(brandActivityHead.getHead());
//        operatHeader.setCoupon(aspBizBrandActivityService.couponInfoObj(userId, brandId, "0"));
//        operatHeader.setPromotion(aspBizBrandActivityService.promotionsObj(brandId, "0"));
//        brandShop.setOperat(operatHeader);
        SearchResult searchResult=aspBizSerchService.queryBrandProductList("", pageIndex, pageSize, tagId, brandId, price, colorId, size, categoryId, order, userLv, postArea, imei, "2.9.16",channnelType);
//        brandShop.setResult(searchResult);
      
        return searchResult;
    }

    public String queryShopProduct(String userId, String pageIndex, String pageSize, String userLv, String tagId, String order, String filters,boolean isPromotion, String imei, String version) throws Exception {
        SearchParam searchParam= SearchParamUtil.parse(filters,"2");
        BrandShopNew brandShopNew=new BrandShopNew();
        OperatHeader operatHeader=new OperatHeader();
        ActivityHead actHead = new ActivityHead();
        HeadInfo headInfo = new HeadInfo();
        BrandActivityModelOne modelOne = new BrandActivityModelOne();
		List<BrandActivityCoupon> coupon = new ArrayList<BrandActivityCoupon>();
		List<BrandActivityPromotions> promotion = new ArrayList<BrandActivityPromotions>();
		BrandActivityHead brandActivityHead = new BrandActivityHead();
		brandActivityHead = aspBizBrandActivityService.headInfoObj(userId, searchParam.getBrandId(), "0");
		operatHeader.setActivity(actHead);
		operatHeader.setHead(headInfo);
		operatHeader.setCoupon(coupon);
		operatHeader.setPromotion(promotion);
		operatHeader.setModelOne(modelOne);
		
		if (brandActivityHead != null) {
			actHead = brandActivityHead.getActivity();
			headInfo = brandActivityHead.getHead();
			if (actHead != null) {
				operatHeader.setActivity(actHead);
			}
			if (headInfo != null) {
				operatHeader.setHead(headInfo);
			}
		}
		
		coupon = aspBizBrandActivityService.couponInfoObj(userId, searchParam.getBrandId(), "0");
		if (coupon != null) {
			operatHeader.setCoupon(coupon);
		}
		if (isPromotion) {
			promotion = aspBizBrandActivityService.promotionsObj(searchParam.getBrandId(), "0");
			if (promotion != null) {
				operatHeader.setPromotion(promotion);
			}
		}
		// 增加模板
		modelOne = aspBizBrandActivityService.modelOneObj(searchParam.getBrandId(), "2", "");
		if (modelOne != null) {
			operatHeader.setModelOne(modelOne);
		}
        //增加轮播图
        BrandActivityGallery brandActivityGallery = new BrandActivityGallery();
        brandActivityGallery.setList(aspBizBrandActivityService.queryGalleryList("4", "6", searchParam.getBrandId(),null));
        operatHeader.setGallery(brandActivityGallery);
        
        brandShopNew.setOperat(operatHeader);
        SearchResultApp searchResult=aspBizSerchService.queryBrandProductListNew("", pageIndex, pageSize, tagId, searchParam.getBrandId(), searchParam.getPriceId(), searchParam.getColorId(), searchParam.getSizeId(), searchParam.getCategoryId(), order, userLv, searchParam.getPostArea(), imei, version);
        brandShopNew.setResult(searchResult);
        String json;
        try {
            json = ApiBizData.spliceData(brandShopNew, CodeMsgEnum.CODE_SUCCESS.getInfo(), CodeMsgEnum.MSG_SUCCESS.getInfo());
            logger.debug("调用api接口返回数据:" + json);
            return json;
        } catch (Exception e) {
            logger.error("调用api接口返回数据发生错误！");
            e.printStackTrace();
        }
        return ApiBizData.spliceData(CodeMsgEnum.CODE_ERROR.getInfo(), CodeMsgEnum.MSG_ERROR.getInfo());
    }
    
    public String queryShopProduct(String userId, String pageIndex, String pageSize, String userLv, String tagId, String order, String filters,boolean isPromotion, String imei, String originalFilters, String dynamicFilters, String version) throws Exception {
        SearchParam searchParam= SearchParamUtil.parse(filters,"2");
        if (com.shangpin.utils.StringUtil.compareVersion("", "2.9.7", version) == 1) {
			searchParam = SearchParamUtil.parse(originalFilters, dynamicFilters, "2");
		} else {
			searchParam = SearchParamUtil.parse(filters, "2");
		}
        BrandShopNew brandShopNew=new BrandShopNew();
        OperatHeader operatHeader=new OperatHeader();
        ActivityHead actHead = new ActivityHead();
        HeadInfo headInfo = new HeadInfo();
        BrandActivityModelOne modelOne = new BrandActivityModelOne();
		List<BrandActivityCoupon> coupon = new ArrayList<BrandActivityCoupon>();
		List<BrandActivityPromotions> promotion = new ArrayList<BrandActivityPromotions>();
		BrandActivityHead brandActivityHead = new BrandActivityHead();
		brandActivityHead = aspBizBrandActivityService.headInfoObj(userId, searchParam.getBrandId(), "0");
		operatHeader.setActivity(actHead);
		operatHeader.setHead(headInfo);
		operatHeader.setCoupon(coupon);
		operatHeader.setPromotion(promotion);
		operatHeader.setModelOne(modelOne);
		
		if (brandActivityHead != null) {
			actHead = brandActivityHead.getActivity();
			headInfo = brandActivityHead.getHead();
			if (actHead != null) {
				operatHeader.setActivity(actHead);
			}
			if (headInfo != null) {
				operatHeader.setHead(headInfo);
			}
		}
		
		coupon = aspBizBrandActivityService.couponInfoObj(userId, searchParam.getBrandId(), "0");
		if (coupon != null) {
			operatHeader.setCoupon(coupon);
		}
		if (isPromotion) {
			promotion = aspBizBrandActivityService.promotionsObj(searchParam.getBrandId(), "0");
			if (promotion != null) {
				operatHeader.setPromotion(promotion);
			}
		}
		// 增加模板
		modelOne = aspBizBrandActivityService.modelOneObj(searchParam.getBrandId(), "2", version);
		if (modelOne != null) {
			operatHeader.setModelOne(modelOne);
		}
        //增加轮播图
        BrandActivityGallery brandActivityGallery = new BrandActivityGallery();
        brandActivityGallery.setList(aspBizBrandActivityService.queryGalleryList("4", "6", searchParam.getBrandId(),null));
        operatHeader.setGallery(brandActivityGallery);
        
        brandShopNew.setOperat(operatHeader);
        SearchResultApp searchResult = aspBizSerchService.queryBrandProductListNew("", pageIndex, pageSize, tagId, searchParam.getBrandId(), searchParam.getPriceId(), searchParam.getColorId(), searchParam.getSizeId(), searchParam.getCategoryId(), order, userLv, searchParam.getPostArea(), originalFilters, dynamicFilters, imei, version);
        brandShopNew.setResult(searchResult);
        String json;
        try {
            json = ApiBizData.spliceData(brandShopNew, CodeMsgEnum.CODE_SUCCESS.getInfo(), CodeMsgEnum.MSG_SUCCESS.getInfo());
            logger.debug("调用api接口返回数据:" + json);
            return json;
        } catch (Exception e) {
            logger.error("调用api接口返回数据发生错误！");
            e.printStackTrace();
        }
        return ApiBizData.spliceData(CodeMsgEnum.CODE_ERROR.getInfo(), CodeMsgEnum.MSG_ERROR.getInfo());
    }

	public String queryProductList(String userId, String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String imei, String version) throws Exception {
		SearchParam searchParam = SearchParamUtil.parse(filters, "2");
        SearchResultApp searchResultApp=new SearchResultApp();
        searchResultApp=aspBizSerchService.queryBrandProductListNew("", pageIndex, pageSize, tagId, searchParam.getBrandId(), searchParam.getPriceId(), searchParam.getColorId(), searchParam.getSizeId(), searchParam.getCategoryId(), order, userLv, searchParam.getPostArea(), imei, version);
        String json;
        try {
            json = ApiBizData.spliceData(searchResultApp, CodeMsgEnum.CODE_SUCCESS.getInfo(), CodeMsgEnum.MSG_SUCCESS.getInfo());
            logger.debug("调用api接口返回数据:" + json);
            return json;
        } catch (Exception e) {
            logger.error("调用api接口返回数据发生错误！");
            e.printStackTrace();
        }
        return ApiBizData.spliceData(CodeMsgEnum.CODE_ERROR.getInfo(), CodeMsgEnum.MSG_ERROR.getInfo());
	}
    

    public String queryProductList(String userId, String pageIndex, String pageSize, String userLv, String tagId, String order, String filters, String originalFilters, String dynamicFilters, String imei, String version) throws Exception {
    	SearchParam searchParam = new SearchParam();
		if (com.shangpin.utils.StringUtil.compareVersion("", "2.9.7", version) == 1) {
			searchParam = SearchParamUtil.parse(originalFilters, dynamicFilters, "2");
		} else {
			searchParam = SearchParamUtil.parse(filters, "2");
		}
        SearchResultApp searchResultApp=new SearchResultApp();
        searchResultApp=aspBizSerchService.queryBrandProductListNew("", pageIndex, pageSize, tagId, searchParam.getBrandId(), searchParam.getPriceId(), searchParam.getColorId(), searchParam.getSizeId(), searchParam.getCategoryId(), order, userLv, searchParam.getPostArea(), originalFilters, dynamicFilters, imei, version);
        String json;
        try {
            json = ApiBizData.spliceData(searchResultApp, CodeMsgEnum.CODE_SUCCESS.getInfo(), CodeMsgEnum.MSG_SUCCESS.getInfo());
            logger.debug("调用api接口返回数据:" + json);
            return json;
        } catch (Exception e) {
            logger.error("调用api接口返回数据发生错误！");
            e.printStackTrace();
        }
        return ApiBizData.spliceData(CodeMsgEnum.CODE_ERROR.getInfo(), CodeMsgEnum.MSG_ERROR.getInfo());
    }
}
