package com.shangpin.biz.bo.orderUnion;

import com.shangpin.biz.bo.PriceShowVo;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @auther liushaoqing
 * @date 2016/8/16
 */
@ToString
public class PriceShowV3 implements Serializable{

    private static final long serialVersionUID = 812399650708L;

    private String isLoad;
    private String settlementCount;
    private String onlineAmount;
    /**
     * type : 1商品金额(海外商品+关税)2满减（满减优惠）3优惠4礼品卡5运费6运费减免7应付金额）
     * amount : 2000
     * title : 商品金额
     */

    private List<PriceShowVo> price;

    public String getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(String isLoad) {
        this.isLoad = isLoad;
    }

    public String getSettlementCount() {
        return settlementCount;
    }

    public void setSettlementCount(String settlementCount) {
        this.settlementCount = settlementCount;
    }

    public String getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(String onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public List<PriceShowVo> getPrice() {
        return price;
    }

    public void setPrice(List<PriceShowVo> price) {
        this.price = price;
    }
}
