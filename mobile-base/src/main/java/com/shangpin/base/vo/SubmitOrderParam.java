package com.shangpin.base.vo;

import java.io.Serializable;

public class SubmitOrderParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String addrId;
	private String invoiceAddrId;
	private String invoiceFlag;
	private String invoiceContent;
	private String invoiceType;
	private String invoiceTitle;
	private String couponFlag;
	private String coupon;
	private String express;
	private String orderFrom;
	private String buysIds;
	private String payTypeId;
	private String payTypeChildId;
	private String orderType;
	private String isUseGiftCardPay;
	private String skuId;
	private String orderSource;
	private String postArea;
	private String type;
	
	//网盟活动的参数(第三方推广)
	private String channelNo; //第三方的渠道号
	private String channelId; //第三方的渠道id
	private String param; //网盟的参数
	private String channelType; //网盟的参数
	//2.9.9新增结束
	
	
	public String getOrderSource() {
		return orderSource;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}


	public String getChannelType() {
		return channelType;
	}


	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}


	public String getChannelNo() {
		return channelNo;
	}


	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}


	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getPostArea() {
		return postArea;
	}

	public void setPostArea(String postArea) {
		this.postArea = postArea;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}

	public String getInvoiceAddrId() {
		return invoiceAddrId;
	}

	public void setInvoiceAddrId(String invoiceAddrId) {
		this.invoiceAddrId = invoiceAddrId;
	}

	public String getInvoiceFlag() {
		return invoiceFlag;
	}

	public void setInvoiceFlag(String invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getBuysIds() {
		return buysIds;
	}

	public void setBuysIds(String buysIds) {
		this.buysIds = buysIds;
	}

	public String getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	public String getPayTypeChildId() {
		return payTypeChildId;
	}

	public void setPayTypeChildId(String payTypeChildId) {
		this.payTypeChildId = payTypeChildId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getIsUseGiftCardPay() {
		return isUseGiftCardPay;
	}

	public void setIsUseGiftCardPay(String isUseGiftCardPay) {
		this.isUseGiftCardPay = isUseGiftCardPay;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}