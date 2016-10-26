package com.shangpin.base.pay;

import lombok.Getter;

/**
 * 支付平台网关
 */
@Getter
public enum Gateway {

	/** 支付宝WAP */
	ALIWAP("支付宝WAP", 8),
	/** 支付宝海外WAP 分账代替*/
	ALIWAPSEA("支付宝海外WAP", 9),
	/** 银联wap */
	UNWAP("银联wap", 39),

	/** 微信公众 */
	WEIXINPUB("微信公众", 41),

	/** 微信WAP */
	WEIXINWAP("微信WAP", 44),

	/** 微信公众 海外 */
	WEIXINPUBSEA("微信公众海外", 50),

	/** 支付宝分账wap **/
	ALIFZWAP("支付宝分账Wap", 53),

	/** 
	CMBWAP("招商银行WAP", 27),
	CMBALLWAP("招商一网通WAP", 55),
	SPABWAP("平安银行wap", 59),
	QQWAP("QQ wap", 59),
	 */

	/**
	 * qqwap境外
	 */
	QQWAPSEA("QQ 境外 wap", 61),
	;
	private String desc;
	private int idx;

	/**
	 * 根据网关的字面值获取网关<br/>
	 * 区分大小写
	 * 
	 * @param name
	 *            网关字面值
	 * @return
	 */
	public static Gateway getGateway(String name) {
		for (Gateway g : Gateway.values()) {
			if (g.name().equals(name))
				return g;
		}
		return null;
	}

	/**
	 * 根据网关的int值获取网关
	 * 
	 * @param value
	 *            网关预置值
	 * @return
	 */
	public static Gateway getGateway(int value) {
		for (Gateway g : Gateway.values()) {
			if (g.getIdx() == value)
				return g;
		}
		return null;
	}
	/**
	 * 是不是微信公众号支付 
	 * <br/>
	 * @return
	 */
	public boolean isWeiXinPub(){
		return this.equals(Gateway.WEIXINPUB)|| this.equals(Gateway.WEIXINPUBSEA);
	}
	/**
	 * @param name
	 * @param ordinal
	 */
	private Gateway(String name, int value) {
		this.desc = name;
		this.idx = value;
	}
}
