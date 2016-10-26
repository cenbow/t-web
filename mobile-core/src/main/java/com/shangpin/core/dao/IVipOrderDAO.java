package com.shangpin.core.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shangpin.core.entity.order.VipOrder;

/** 
 * vip订单接口<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
public interface IVipOrderDAO extends JpaRepository<VipOrder, Long>{
	/**
	 * 根据用户id查询其中最近一个订单 
	 * <br/>
	 * @param userId
	 * @return
	 */
	List<VipOrder> findByUserIdOrderByStatusDesc(String userId);
	/**
	 * 根据订单编号查询订单 
	 * <br/>
	 * @param orderNo 订单号
	 * @return
	 */
	VipOrder findByOrderNo(String orderNo);
	/**
	 * 根据用户id，订单编号获取订单数据 
	 * <br/>
	 * @param userId 用户id
	 * @param orderNo 订单编号
	 * @return
	 */
	VipOrder findByUserIdAndOrderNo(String userId,String orderNo);
	/**
	 * 根据用户id，支付状态查询订单 
	 * <br/>
	 * @param userId
	 * @param status 0待支付，1支付成功
	 * @return
	 */
	List<VipOrder> findByUserIdAndStatus(String userId, int status);
	
	Page<VipOrder> findByStatus(int status,Pageable pageable);
	/**
	 * 更新订单状态 
	 * <br/>
	 * @param userId 用户id
	 * @param orderNo 订单编号
	 * @param status 状态 0待支付，1成功
	 * @return 更新成功1，失败0
	 */
	@Modifying
	@Query("update VipOrder set status=?3 where userId=?1 and orderNo=?2 and status<>?3")
	int updateStatus(String userId,String orderNo,int status);
	/**
	 * 更新订单支付成功
	 * @return 更新成功1，失败0
	*/
	@Modifying
	@Query("update VipOrder set status=?2 where orderNo=?1 and status<>?2")
	int updateStatus(String orderNo,int status);
	/**
	 * 更新日志状态 
	 * <br/>
	 * @param orderNo
	 * @param logStatu
	 * @return
	 */
	@Modifying
	@Query("update VipOrder set logLevel=?2 where orderNo=?1 and logLevel<>?2")
	int updateLogLevel(String orderNo,byte logStatu);
}
