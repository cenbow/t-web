package com.shangpin.biz.bo.orderUnion;

import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @auther liushaoqing
 * @date 2016/8/3
 */
@ToString
public class SettleRefreshV3 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * isLoad : 是否加载。1是，0否
     * receivedId : 收货地址id
     */

    private ReceivedBean received;
    /**
     * isLoad : 是否加载。1是，0否
     * couponNo : 534623803849优惠券编号
     * canUseAmount : 100
     */

    private CouponBean coupon;
    /**
     * isLoad : 是否加载。1是，0否
     * isUsed : 是否使用礼品卡，1是，0否
     * balance : 余额1450
     * canUseAmount : 能用金额350
     */

    private GiftCardBean giftCard;
    /**
     * price : [{"type":"1商品金额(海外商品+关税)2满减（满减优惠）3优惠4礼品卡5运费6运费减免7应付金额）","amount":"2000","title":"商品金额"}]
     * totalSettlement : 结算数量
     * totalAmount : 需支付现金金额12499
     */

    private PriceShowV3 priceShow;
    /**
     * received : {"isLoad":"是否加载。1是，0否","receivedId":"收货地址id"}
     * coupon : {"isLoad":"是否加载。1是，0否","couponNo":"534623803849优惠券编号","canUseAmount":"100"}
     * giftCard : {"isLoad":"是否加载。1是，0否","isUsed":"是否使用礼品卡，1是，0否","balance":"余额1450","canUseAmount":"能用金额350"}
     * priceShow : {"price":[{"type":"1商品金额(海外商品+关税)2满减（满减优惠）3优惠4礼品卡5运费6运费减免7应付金额）","amount":"2000","title":"商品金额"}],"totalSettlement":"结算数量","totalAmount":"需支付现金金额12499"}
     * isProductCod : 商品是否支持货到付款。0:否，1是。
     * payCategory : 1:国内，2：国外，3：支付宝分帐
     * buyIds : ["220234230013","22023423001","3220234230013"]
     */

    private String isProductCod;
    private String payCategory;
    private List<String> buyIds;

    private String requestFrom;//请求来源
    private String versionNo;//版本号

    public ReceivedBean getReceived() {
        return received;
    }

    public void setReceived(ReceivedBean received) {
        this.received = received;
    }

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public GiftCardBean getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(GiftCardBean giftCard) {
        this.giftCard = giftCard;
    }

    public PriceShowV3 getPriceShow() {
        return priceShow;
    }

    public void setPriceShow(PriceShowV3 priceShow) {
        this.priceShow = priceShow;
    }

    public String getIsProductCod() {
        return isProductCod;
    }

    public void setIsProductCod(String isProductCod) {
        this.isProductCod = isProductCod;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public List<String> getBuyIds() {
        return buyIds;
    }

    public void setBuyIds(List<String> buyIds) {
        this.buyIds = buyIds;
    }

    @ToString
    public static class ReceivedBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private String isLoad;
        private String receivedId;
        private String provinceId;

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getReceivedId() {
            return receivedId;
        }

        public void setReceivedId(String receivedId) {
            this.receivedId = receivedId;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }
    }
    @ToString
    public static class CouponBean implements Serializable{

        private static final long serialVersionUID = 1L;
        private String isLoad;
        private String type;
        private String count;
        private String couponNo;
        private String canUseAmount;


        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public String getCanUseAmount() {
            return canUseAmount;
        }

        public void setCanUseAmount(String canUseAmount) {
            this.canUseAmount = canUseAmount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
    @ToString
    public static class GiftCardBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private String isLoad;
        private String isUsed;
        private String balance;
        private String canUseAmount;

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(String isUsed) {
            this.isUsed = isUsed;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCanUseAmount() {
            return canUseAmount;
        }

        public void setCanUseAmount(String canUseAmount) {
            this.canUseAmount = canUseAmount;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
