package com.shangpin.biz.service;

import com.shangpin.biz.bo.*;

import java.util.List;

public interface SPBizBrandActivityService {
	
	public BrandActivityHead headInfo(String userid, String id, String type);

	/**
	 *
	 * @Title:couponInfoObj
	 * @Description:品牌、活动优惠券信息接口
	 * @param userid 用户表示id，非必填
	 * @param id 活动、品牌id ， 必填
	 * @param type 类型 0表示品牌；1表示活动，必填
	 * @return
	 * @author qinyingchun
	 * @date 2015年1月12日
	 */
	public List<BrandActivityCoupon> couponInfoObj(String userId, String id, String type);

	/**
	 *
	 * @Title:headInfoObj
	 * @Description:品牌、活动头部信息接口
	 * @param userid 用户表示ID
	 * @param id 品牌、活动id
	 * @param type 类型，0表示品牌，1表示活动
	 * @return
	 * @author qinyingchun
	 * @date 2015年1月12日
	 */
	public BrandActivityHead headInfoObj(String userid, String id, String type);

	/**
	 * 查询活动楼层信息及是否开启楼层
	 * @param topicId 活动Id
	 */
	public String subjectFloorInfo(String topicId);

	/**
	 *
	 * @Title:promotionsObj
	 * @Description:品牌、活动推广位
	 * @param id 品牌、活动id， 必填
	 * @param type 类型 0表示品牌；1表示活动，必填
	 * @return
	 * @author qinyingchun
	 * @date 2015年1月12日
	 */
	public List<BrandActivityPromotions> promotionsObj(String id, String type);
	/**
	 *
	 * @Title:modelOneObj
	 * @Description:品牌、活动模板广告
	 * @param id 品牌、活动id， 必填
	 * @param type 类型 0表示品牌；1表示活动，必填
	 * @param version 客户端版本，非必传
	 * @return
	 * @author ZRS
	 * @date 2015年11月6日18:19:18
	 */
	public BrandActivityModelOne modelOneObj(String id, String type, String version);
	/**
	 *
	 * @Title:queryGalleryList
	 * @Description:品牌、活动轮播图
	 * @param frames
	 * @param type 4品牌轮播
	 * @param brandId
	 * @return
	 * @author ZRS
	 * @date 2015年11月6日18:19:18
	 */
	public List<Gallery> queryGalleryList(String type, String frames, String brandId,String isSizePic);
}
