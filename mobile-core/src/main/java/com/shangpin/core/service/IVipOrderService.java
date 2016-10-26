package com.shangpin.core.service;

import org.springframework.data.domain.Page;

import com.shangpin.core.entity.order.VipOrder;

/** 
 * vip订单数据服务<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
public interface IVipOrderService {
	/**
	 * 用户下单vip订单 
	 * <br/>
	 * @param userId 用户id
	 * @return vip订单数据
	 * @throws RuntimeException 订单重复，参数有误异常
	 */
	VipOrder createVipOrder(VipOrder order);
	/**
	 * 获取用户订单 
	 * <br/>
	 * @param userId 用户编号
	 * @param orderNo 订单编号
	 * @return
	 */
	VipOrder getVipOrder(String userId,String orderNo);
	/**
	 * 根据订单获取vip订单 
	 * <br/>
	 * @param orderNo
	 * @return
	 */
	VipOrder getVipOrder(String orderNo);
	/**
	 * 订单支付成功 
	 * <br/>
	 * @param userId 用户id
	 * @param orderNo 订单编号
	 * @return 成功与否状态 
	 */
	boolean updatePaySuccess(String orderNo);
	/**
	 * 获取某种状态的订单 
	 * <br/>
	 * @param status 0待支付，1支付成功
	 * @param page 0开始
	 * @param pageSize 1开始
	 * @return 分页的订单
	 */
	Page<VipOrder> getOrders(int status,int page,int pageSize);
	
	/**
	 * 记录订单对应的用户权益等级是否更新 
	 * <br/>
	 * @param orderNo 订单号
	 * @return 更新了true
	 */
	boolean logLevelUpdated(String orderNo);
}
