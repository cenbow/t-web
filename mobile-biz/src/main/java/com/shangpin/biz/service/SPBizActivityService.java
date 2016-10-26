package com.shangpin.biz.service;

import com.shangpin.biz.bo.ActivityHead;
import com.shangpin.biz.bo.ActivityIndex;
import com.shangpin.biz.bo.BrandActivityHead;
import com.shangpin.biz.bo.SearchResult;

import java.util.Map;

public interface SPBizActivityService {
	ActivityIndex searchActivityIndex(String userId, String subjectNo, String start, String end, String tagId, String brandId, String price, String colorId, String size, String categoyId, String order, String gender, String userLv, String postArea, String imei) throws Exception;

	SearchResult searchActivityProduct(String subjectNO, String start, String productListEnd, String tagId, String brandNo, String price, String color, String size, String categoryNo, String order, String gender, String userLv, String postArea, String imei, ActivityHead activity);

	BrandActivityHead getBrandActivityHead(String userId, String subjectNo);

	Map<String, Object> getSubjectFloorInfo(String topicId,ActivityHead activityHead);

	ActivityIndex getSearchActivityIndexWithoutSearch(String userId, String topicId);
	/**
	 * 关注活动提醒时间
	 * @param phoneNum
	 * @param actityId
	 * @param time
	 * @return
	 */
	Map<String, Object> activityStartRemind(String phoneNum, String actityId, String time);
}
