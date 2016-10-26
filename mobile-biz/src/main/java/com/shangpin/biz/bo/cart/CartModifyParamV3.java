package com.shangpin.biz.bo.cart;

import lombok.ToString;

import java.util.List;

/**
 * @auther liushaoqing
 * @date 2016/8/4
 */
@ToString
public class CartModifyParamV3 {

    /**
     * userId : 用户Id
     * cartItems : [{"shoppingCartId":"购物车Id","quantity":"商品数量"},{"shoppingCartId":"购物车Id","quantity":"商品数量"}]
     * isChecked : ["购物车Id","购物车Id"]
     * ChannelNo : 渠道号
     * ChannelId : 渠道Id 可为空
     */

    private String userId;
    private String ChannelNo;
    private String ChannelId;
    /**
     * shoppingCartId : 购物车Id
     * quantity : 商品数量
     */

    private List<CartItemsBean> cartItems;
    private List<String> isChecked;
    private String requestFrom;
    private String versionNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelNo() {
        return ChannelNo;
    }

    public void setChannelNo(String ChannelNo) {
        this.ChannelNo = ChannelNo;
    }

    public String getChannelId() {
        return ChannelId;
    }

    public void setChannelId(String ChannelId) {
        this.ChannelId = ChannelId;
    }

    public List<CartItemsBean> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemsBean> cartItems) {
        this.cartItems = cartItems;
    }

    public List<String> getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(List<String> isChecked) {
        this.isChecked = isChecked;
    }

    public static class CartItemsBean {
        private String shoppingCartId;
        private String quantity;

        public String getShoppingCartId() {
            return shoppingCartId;
        }

        public void setShoppingCartId(String shoppingCartId) {
            this.shoppingCartId = shoppingCartId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}
