package com.shangpin.biz.bo;

import java.io.Serializable;
/**
 * 页面插件封装物流公司信息
 * @author 
 *
 */
public class LogisticsShow implements Serializable {
	private static final long serialVersionUID = 4305374828766081854L;
	private String value;
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
