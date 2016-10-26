package com.shangpin.biz.api.dto.pay;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付系统的接口<br/>
 * 
 * @date 2016年8月12日 <br/>
 * @author 陈小峰
 * @since JDK 7
 */
@Setter
@Getter
public class PayBaseDTO implements Serializable {
	private static final long serialVersionUID = -2971267018176754948L;
	// 0成功，1失败，看ackmsg
	private String ackStatus;
	private String ackMsg;
	/**
	 * 响应是否成功 
	 * <br/>
	 * @return
	 */
	public boolean ackSuccess() {
		return "0".equals(ackStatus);
	}
}
