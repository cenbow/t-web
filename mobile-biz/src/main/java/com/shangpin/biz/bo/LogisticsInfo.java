package com.shangpin.biz.bo;

import java.io.Serializable;
/**
 * 物流公司信息
 * @author wh
 *
 */
public class LogisticsInfo implements Serializable {
	private static final long serialVersionUID = 4305374828766081854L;
	private String DeliveryCompanyName;
	private String DeliveryCompanyNo;
	private String CompanyCode;
	public String getCompanyCode() {
		return CompanyCode;
	}
	public void setCompanyCode(String companyCode) {
		CompanyCode = companyCode;
	}
	public String getDeliveryCompanyName() {
		return DeliveryCompanyName;
	}
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		DeliveryCompanyName = deliveryCompanyName;
	}
	public String getDeliveryCompanyNo() {
		return DeliveryCompanyNo;
	}
	public void setDeliveryCompanyNo(String deliveryCompanyNo) {
		DeliveryCompanyNo = deliveryCompanyNo;
	}
}
