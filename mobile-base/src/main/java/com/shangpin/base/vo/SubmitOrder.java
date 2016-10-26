package com.shangpin.base.vo;

import java.io.Serializable;

/**
 * 提交主站返回返回对象
 * 
 * @Author:wangfeng
 * @CreatDate: 2014-06-24
 */
public class SubmitOrder implements Serializable{
    private String orderid;
    private String date;
    private String amount;
    private String onlineamount;
    private String cod;
    private String codmsg;
    private String skucounts;
    private String objectcounts;
    private String giftcard;
    private String carriage;
    private String giftcardbalance;
    private String systime;
    private String expiretime;
    private String postarea;
    private String orderno;

    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getCod() {
        return cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public String getCodmsg() {
        return codmsg;
    }
    public void setCodmsg(String codmsg) {
        this.codmsg = codmsg;
    }
    public String getSkucounts() {
        return skucounts;
    }
    public void setSkucounts(String skucounts) {
        this.skucounts = skucounts;
    }
    public String getObjectcounts() {
        return objectcounts;
    }
    public void setObjectcounts(String objectcounts) {
        this.objectcounts = objectcounts;
    }
    public String getGiftcard() {
        return giftcard;
    }
    public void setGiftcard(String giftcard) {
        this.giftcard = giftcard;
    }
    public String getCarriage() {
        return carriage;
    }
    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }
    public String getGiftcardbalance() {
        return giftcardbalance;
    }
    public void setGiftcardbalance(String giftcardbalance) {
        this.giftcardbalance = giftcardbalance;
    }
	public String getOnlineamount() {
		return onlineamount;
	}
	public void setOnlineamount(String onlineamount) {
		this.onlineamount = onlineamount;
	}
    public String getSystime() {
        return systime;
    }
    public void setSystime(String systime) {
        this.systime = systime;
    }
    public String getExpiretime() {
        return expiretime;
    }
    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime;
    }
	public String getPostarea() {
		return postarea;
	}
	public void setPostarea(String postarea) {
		this.postarea = postarea;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
  
}
