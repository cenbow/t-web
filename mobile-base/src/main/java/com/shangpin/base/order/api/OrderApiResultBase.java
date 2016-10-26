package com.shangpin.base.order.api;
import lombok.Getter;
import lombok.Setter;


/** 
 * 订单系统返回的参数
 * @date    2016年9月22日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class OrderApiResultBase<T> {

	private String code;
	private String msg;
	private T content;
	/**
	 * 请求是否成功
	 * <br/>
	 * @return
	 */
	public boolean isSuccess(){
		return "0".equals(code);
	}
}
