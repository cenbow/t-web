package com.shangpin.biz.service;

import com.shangpin.biz.bo.OrderLogistics;
import com.shangpin.biz.bo.Track;

/**
 * @ClassName: LogisticeBizService
 * @Description:物流接口
 * @author liling
 * @date 2014年11月29日
 * @version 1.0
 */
public interface SPBizLogisticeService  {

	/**
	 * 获取物流信息
	 * 
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Track getNewLogistice(String orderId, String userId);

	/**
	 * 获取物流信息V3
	 * @param orderId
	 * @param userId
     * @return
     */
	OrderLogistics getLogisticV3(String orderId, String userId);
}
