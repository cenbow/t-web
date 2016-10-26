package com.shangpin.biz.bo.orderUnion;

import lombok.ToString;

/**
 * @auther liushaoqing
 * @date 2016/8/10
 */
@ToString
public class OrderSubmitResultV3 {

    /**
     * orderNo : 20160616445334
     * orderTime : 2016/08/08 16:47:08
     * payAmount : 1200
     * isSupportCod : 1
     * totalQuantity : 5
     * giftCardBalance : 300
     * isUseGiftcardPay : 1
     * freight : 10
     * sysTime : 31223434
     * expireTime : 12312312
     * internalAmount : 200
     * payCategory : 1
     */

    private String mainOrderNo;
    private String orderTime;
    private String payAmount;
    private String onlineAmount;
    private int isSupportCod;
    private int totalQuantity;
    private int giftCardBalance;
    private int isUseGiftcardPay;
    private int freight;
    private String sysTime;
    private String expireTime;
    private int internalAmount;
    private int payCategory;
    private String versionNo;
    private String requestFrom;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(String onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public int getIsSupportCod() {
        return isSupportCod;
    }

    public void setIsSupportCod(int isSupportCod) {
        this.isSupportCod = isSupportCod;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getGiftCardBalance() {
        return giftCardBalance;
    }

    public void setGiftCardBalance(int giftCardBalance) {
        this.giftCardBalance = giftCardBalance;
    }

    public int getIsUseGiftcardPay() {
        return isUseGiftcardPay;
    }

    public void setIsUseGiftcardPay(int isUseGiftcardPay) {
        this.isUseGiftcardPay = isUseGiftcardPay;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getInternalAmount() {
        return internalAmount;
    }

    public void setInternalAmount(int internalAmount) {
        this.internalAmount = internalAmount;
    }

    public int getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(int payCategory) {
        this.payCategory = payCategory;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }
}
