package com.shangpin.biz.common;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import lombok.Getter;
import lombok.Setter;


/** 
 * 序列化服务<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class SequenceService {
	public static String ymdhms="yMMddHHmmss"; 
	/**
	 * 生成一个vip订单的编号 
	 * <br/>TODO 实现订单编号接口
	 * @return 17位长度数字字符串
	 */
	public static String getVipOrderNo(){
		String ymd=DateFormatUtils.format(System.currentTimeMillis(), ymdhms);
		return ymd+RandomStringUtils.randomNumeric(6);
	}
}
