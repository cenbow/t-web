package com.shangpin.biz.enums;

import com.shangpin.biz.utils.PropertyUtil;

/**
 * Created by 文宇 on 2016/9/28.
 */
public enum  PriceColor {

    SHANGPIN_PRICE ("#000000"),//黑色

    PROMOTION_PRICE ("#c62026"),//#红色

    NOMAL_COMPARE_PRICE ("#888888"),//#灰色

    HOT_COMPARE_PRICE ("#c62026"),//#预热对比颜色红色

    VIP_PRICE ("#0700c5");// #蓝色

    private String color;

    PriceColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.color;
    }
}