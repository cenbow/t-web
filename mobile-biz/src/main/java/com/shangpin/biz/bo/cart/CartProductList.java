package com.shangpin.biz.bo.cart;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartProductList implements Serializable{
	/**
	 * 购物车
	 */
	private static final long serialVersionUID = 8097072573200383066L;

	private String cartDetailId;
	private String spu;
	private String name;
	private String brand;
	private String sku;
	private String mark;
	private String price;
	private String priceTag;
	private String priceColor;//价格颜色
	private String priceType;//"价格类型。0尚品价，1促销价，2会员价",
	private String shangPinPrice;//尚品价。此字段为空就不显示
	private String shangPinPriceColor;//#ffffff，尚品价颜色
	private String isShowDeleteLine;//尚品价上是否有删除线
	private String pic;
	private String quantity;
	private String isChecked;
	private String count;
	private String valid;
	private String msgType;
	private String msg;
	private String postArea;
	private String countryPic;
	private String countryDesc;
	private List<CartProductAttribute> attribute;
	
}
