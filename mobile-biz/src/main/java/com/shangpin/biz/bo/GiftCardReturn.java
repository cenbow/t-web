package com.shangpin.biz.bo;

import java.io.Serializable;

import lombok.ToString;

/** 
* @ClassName: GiftCardReturn 
* @Description: 礼品卡支付返回的数据实体类
* @author qinyingchun
* @date 2014年12月3日
* @version 1.0 
*/
@ToString
public class GiftCardReturn implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static final String PAY_RESULT_ALLPAY="2"; //全额支付
	private static final String PAY_RESULT_PAY="1"; //部分支付

	private String amount;
	private String payresult;//1为部分支付，订单有余额；2为完全支付；
	private String onlineamount ;//在线支付金额（第三方支付金额）
	private String payamount ; //总支付金额(包括礼品卡)
	private String discountamount ;//总优惠金额
	private String giftcardamount;//使用的礼品卡总金额
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayresult() {
		return payresult;
	}
	public void setPayresult(String payresult) {
		this.payresult = payresult;
	}
	public String getOnlineamount() {
		return onlineamount;
	}
	public void setOnlineamount(String onlineamount) {
		this.onlineamount = onlineamount;
	}
	public String getPayamount() {
		return payamount;
	}
	public void setPayamount(String payamount) {
		this.payamount = payamount;
	}
	public String getDiscountamount() {
		return discountamount;
	}
	public void setDiscountamount(String discountamount) {
		this.discountamount = discountamount;
	}
	public String getGiftcardamount() {
		return giftcardamount;
	}
	public void setGiftcardamount(String giftcardamount) {
		this.giftcardamount = giftcardamount;
	}

	//是否全部礼品卡支付
	public boolean isAllPay(){
		return payresult.equals(PAY_RESULT_ALLPAY);
	}

}
