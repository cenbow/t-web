package com.shangpin.biz.bo.user;

/**
 * @auther liushaoqing
 * @date 2016/8/19
 * 通过orderbuyInfo 接口获取用户购物车等的
 */
public class UserBuyInfo {

    /**
     * cartgoodscount : 0
     * waitpaycount : 0
     * preparegoodscount : 0
     * delivergoodscount : 0
     * logisticsstatus : 0
     * curlevel : 0001
     * curleveldesc : 普通会员
     * curleveldiscount : 生日时尚品网会赠送您一份生日红包。
     * nextlevel : 0002
     * nextleveldesc : 黄金会员
     * nextleveldiscount : 1、生日时尚品网会赠送您一份生日红包。2、升级黄金会员时，尚品网已经赠送您一份300元的红包。3、在尚品网购物享受9.5折优惠，少量特殊商品除外（您购物时会有相应的说明）
     * cardcount : 0
     * http://wiki.shangpin.com/pages/viewpage.action?pageId=2327216
     */

    private String cartgoodscount;
    private String waitpaycount;
    private String preparegoodscount;
    private String delivergoodscount;
    private String logisticsstatus;
    private String curlevel;
    private String curleveldesc;
    private String curleveldiscount;
    private String nextlevel;
    private String nextleveldesc;
    private String nextleveldiscount;
    private String cardcount;

    public String getCartgoodscount() {
        return cartgoodscount;
    }

    public void setCartgoodscount(String cartgoodscount) {
        this.cartgoodscount = cartgoodscount;
    }

    public String getWaitpaycount() {
        return waitpaycount;
    }

    public void setWaitpaycount(String waitpaycount) {
        this.waitpaycount = waitpaycount;
    }

    public String getPreparegoodscount() {
        return preparegoodscount;
    }

    public void setPreparegoodscount(String preparegoodscount) {
        this.preparegoodscount = preparegoodscount;
    }

    public String getDelivergoodscount() {
        return delivergoodscount;
    }

    public void setDelivergoodscount(String delivergoodscount) {
        this.delivergoodscount = delivergoodscount;
    }

    public String getLogisticsstatus() {
        return logisticsstatus;
    }

    public void setLogisticsstatus(String logisticsstatus) {
        this.logisticsstatus = logisticsstatus;
    }

    public String getCurlevel() {
        return curlevel;
    }

    public void setCurlevel(String curlevel) {
        this.curlevel = curlevel;
    }

    public String getCurleveldesc() {
        return curleveldesc;
    }

    public void setCurleveldesc(String curleveldesc) {
        this.curleveldesc = curleveldesc;
    }

    public String getCurleveldiscount() {
        return curleveldiscount;
    }

    public void setCurleveldiscount(String curleveldiscount) {
        this.curleveldiscount = curleveldiscount;
    }

    public String getNextlevel() {
        return nextlevel;
    }

    public void setNextlevel(String nextlevel) {
        this.nextlevel = nextlevel;
    }

    public String getNextleveldesc() {
        return nextleveldesc;
    }

    public void setNextleveldesc(String nextleveldesc) {
        this.nextleveldesc = nextleveldesc;
    }

    public String getNextleveldiscount() {
        return nextleveldiscount;
    }

    public void setNextleveldiscount(String nextleveldiscount) {
        this.nextleveldiscount = nextleveldiscount;
    }

    public String getCardcount() {
        return cardcount;
    }

    public void setCardcount(String cardcount) {
        this.cardcount = cardcount;
    }
}
