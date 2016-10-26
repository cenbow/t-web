package com.shangpin.base.service;

import java.util.List;

public interface RecRuleService {
    //推荐规
	String doRecRuleProduct(String userId, String imei,String offset,String num) throws Exception;

	//推荐规则
	String doRecRuleCookie(String userId, String uvId,String offset,String num) throws Exception;

	public String findByProductNos(List<String> productNos);
	
	/**
	 * 商品详情页推荐商品
	 * @param userid 用户Id
	 * @param imei 移动平台设备号
	 * @param uvid cookie
	 * @param location 用户经纬度
	 * @param ip 用户Ip
	 * @param brandId 品牌ID
	 * @param categoryId 品类ID
	 * @param itemId 商品编号（spu）
	 * @param num 返回条数
	 * @param offset 分页偏移量
	 * @return
	 */
	public String productDetailRmd(String userid, String imei, String uvid, String location, String ip, String brandId, String categoryId, String itemId, String num, String offset);
	
	
}
