package com.shangpin.biz.test;
import java.math.BigDecimal;

import com.shangpin.base.pay.Gateway;
import com.shangpin.base.pay.api.request.OrderPayWebDTO;

import lombok.Getter;
import lombok.Setter;


/** 
 * @date    2016年8月10日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class TestPayService {

	public static void main(String[] args) {
		OrderPayWebDTO order  = new OrderPayWebDTO();
		order.setOrderNo("111");
		order.setSubject("2222");
		order.setGateWay(Gateway.ALIWAP);
		order.setTotalFee(new BigDecimal(0.01));
		order.setOpenId("dalfdlafdsalfdsa");
		order.setTimeOut(12);
		
		System.out.println(order.validate());
		System.out.println(order.sortedValue());
		System.out.println(order.requestMap());
	}
}
