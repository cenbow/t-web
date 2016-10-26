package com.shangpin.base.config;

import com.shangpin.base.utils.PropertyUtil;

import lombok.Getter;
import lombok.Setter;


/** 
 * 支付服务配置参数<br/>
 * @date    2016年8月24日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class PayAPIConfig {
	/**
	 * 请求支付web的url(支付web）
	 * @see #payRequestKey
	 */
	public static String payWebApi=PropertyUtil.getString("payApiUrl");
	/**
	 * 支付服务，通知解释的url（仅限于前台通知）
	 */
	public static String payNotifyParseUrl=PropertyUtil.getString("payNotifyParseUrl");
	/**
	 * 支付服务的查询订单状态的url（支付服务facade）
	 */
	public static String payQueryUrl=PropertyUtil.getString("payQueryUrl");
	/**
	 * 支付回调验证用的key（用于验证支付平台通知前端的数据是否正确）
	 */
	public static String payCallBackKey=PropertyUtil.getString("payCallBackKey");
	/**
	 * 支付请求用的key（用于跳转支付web url时的数据加密）
	 */
	public static String payRequestKey=PropertyUtil.getString("payRequestKey");
}
