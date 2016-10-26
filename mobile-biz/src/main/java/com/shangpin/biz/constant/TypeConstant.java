package com.shangpin.biz.constant;
import lombok.Getter;
import lombok.Setter;


/** 
 * 类型静态字段集合<br/>
 * @date    2016年8月16日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public final class TypeConstant {
	/**
	 * 用户等级字段
	 * 普通：0001，vip:0005，eip:0006
	 * @date     2016年8月16日 <br/> 
	 * @author 陈小峰
	 * @since JDK7
	 */
	public static class UserLevelType{
		/**
		 * 普通
		 */
		public static final String NORMAL="0001";
		/**
		 * vip
		 */
		public static final String VIP="0005";
		/**
		 * eip
		 */
		public static final String EIP="0006";
	}
}
