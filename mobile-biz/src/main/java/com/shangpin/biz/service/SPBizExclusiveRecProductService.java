package com.shangpin.biz.service;

import com.shangpin.biz.bo.RecNewProductFor;
import com.shangpin.biz.bo.RecProductFor;

public interface SPBizExclusiveRecProductService {
	
	RecProductFor queryRecProduct(String type, String userId, String imei, String coord, String ip, String pageIndex, String pageSize);

	/**
	 *
	 * @param type
	 * @param userId
	 * @param imei
	 * @param coord
	 * @param ip
	 * @param pageIndex
     * @param pageSize
     * @return
     */
	RecNewProductFor recProductFromRecomannder(String userId, String uvId,String pageIndex, String pageSize);
}
