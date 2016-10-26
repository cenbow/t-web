package com.shangpin.biz.bo.orderUnion;

import java.util.List;

/**
 * @auther liushaoqing
 * @date 2016/8/4
 */
public class CouponsV3 {

    /**
     * count : 可使用的券数量
     * list : [{"couponNo":"939980387403","name":"新用户专享","amount":"200面值","canUseAmount":"180能用的金额","expiryDate":"2015-05-04 00:00:00至2015-07-04 00:00:00","rule":"按商品30001258","type":"优惠券类型，0优惠券，1现金券","isSelected":"1"}]
     */

    private String count;

    /**
     * couponNo : 939980387403
     * name : 新用户专享
     * amount : 200面值
     * canUseAmount : 180能用的金额
     * expiryDate : 2015-05-04 00:00:00至2015-07-04 00:00:00
     * rule : 按商品30001258
     * type : 优惠券类型，0优惠券，1现金券
     * isSelected : 1
     */

    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String couponNo;
        private String name;
        private String amount;
        private String canUseAmount;
        private String expiryDate;
        private String rule;
        private String type;
        private String isSelected;

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCanUseAmount() {
            return canUseAmount;
        }

        public void setCanUseAmount(String canUseAmount) {
            this.canUseAmount = canUseAmount;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(String isSelected) {
            this.isSelected = isSelected;
        }
    }
}
