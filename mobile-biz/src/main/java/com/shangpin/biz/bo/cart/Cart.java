package com.shangpin.biz.bo.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889070295059685101L;
	
	private String totalAmount;
	private String totalAmountDesc;
	private String spareAmount;
	private String maxQuantity;
	private String prompt;
	private String isCheckedAll;
	private String totalSettlement;
	private List<CartList> cartList;
	//用于埋点
	private String productIds;
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalAmountDesc() {
		return totalAmountDesc;
	}
	public void setTotalAmountDesc(String totalAmountDesc) {
		this.totalAmountDesc = totalAmountDesc;
	}
	public String getSpareAmount() {
		return spareAmount;
	}
	public void setSpareAmount(String spareAmount) {
		this.spareAmount = spareAmount;
	}
	public String getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getIsCheckedAll() {
		return isCheckedAll;
	}
	public void setIsCheckedAll(String isCheckedAll) {
		this.isCheckedAll = isCheckedAll;
	}
	public String getTotalSettlement() {
		return totalSettlement;
	}
	public void setTotalSettlement(String totalSettlement) {
		this.totalSettlement = totalSettlement;
	}
	public List<CartList> getCartList() {
		return cartList;
	}
	public void setCartList(List<CartList> cartList) {
		this.cartList = cartList;
	}

	public String getProductIds(){

		StringBuilder spus = new StringBuilder();
		for (CartList list : cartList) {
			for (CartProductList cartProductList : list.getProductList()) {
			    if("1".equals(cartProductList.getIsChecked())){
					spus.append(cartProductList.getSpu()+",");
                }
			}
		}
		if(spus.length()>0){
			return spus.substring(0,spus.length()-1);
		}
		return spus.toString();
	}

}
