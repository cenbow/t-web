package com.shangpin.biz.bo.orderUnion;

import lombok.ToString;

import java.io.Serializable;

/**
 * @auther liushaoqing
 * @date 2016/8/15
 */
@ToString
public class ListSkuDetailBeanV3 implements Serializable {

    private static final long serialVersionUID = 81239962345254L;
    private String skuNo;
    private String warehouseNo;
    private int quantity;

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
