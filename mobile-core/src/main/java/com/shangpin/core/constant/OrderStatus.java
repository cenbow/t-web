package com.shangpin.core.constant;
import lombok.Getter;
import lombok.Setter;


/** 
 * 订单状态数据<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public final class OrderStatus {
	/**
	 * vip订单状态<br/>
	 * 0待支付，1支付成功
	 * @date:     2016年8月8日 <br/> 
	 * @author 陈小峰
	 * @since JDK7
	 */
	public final static class VipOrderStatus{
		/**0待支付*/
		public static final int WAIT=0;
		/**1支付成功*/
		public static final int SUCCESS=1;
		
	}
}
