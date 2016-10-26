package com.shangpin.core.service.impl;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shangpin.core.constant.OrderStatus.VipOrderStatus;
import com.shangpin.core.dao.IVipOrderDAO;
import com.shangpin.core.entity.order.VipOrder;
import com.shangpin.core.service.IVipOrderService;

import lombok.Getter;
import lombok.Setter;


/**
 * vip订单数据服务<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@Service
public class VipOrderServiceImpl implements IVipOrderService {

	@Autowired
	IVipOrderDAO vipOrderDao;
	
	@Override
	@Transactional
	public VipOrder createVipOrder(final VipOrder orderx) {
		VipOrder order=null;
		List<VipOrder> orders= vipOrderDao.findByUserIdOrderByStatusDesc(orderx.getUserId());
		if(orders.size()>0)
			order = orders.get(0);
		if(order!=null ){//更新支付方式
			order.setGateway(orderx.getGateway());
			order.setUpdateDate(new Date());
			if(order.isPayed()){
				//throw new RuntimeException("订单已支付");			
			}
			return order;
		}
		order = new VipOrder();
		try {
			orderx.setCreateDate(new Date());
			orderx.setUpdateDate(orderx.getCreateDate());
			BeanUtils.copyProperties(order, orderx);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("订单数据有误",e);
		}
		order.setStatus(VipOrderStatus.WAIT);
		order=vipOrderDao.save(order);
		return order;
	}

	@Override
	public VipOrder getVipOrder(String userId, String orderNo) {
		VipOrder order=vipOrderDao.findByUserIdAndOrderNo(userId,orderNo);
		return order;
	}
	@Override
	public VipOrder getVipOrder(String orderNo) {
		VipOrder order=vipOrderDao.findByOrderNo(orderNo);
		return order;
	}

	@Override
	@Transactional
	public boolean updatePaySuccess(String orderNo) {
		VipOrder order=vipOrderDao.findByOrderNo(orderNo);
		if(order==null) return false;
//		if(order.isPayed()) return true;
		int x=vipOrderDao.updateStatus(orderNo, VipOrderStatus.SUCCESS);
		if(x>0)
			return true;
		return false;
		
	}

	@Override
	public Page<VipOrder> getOrders(int status, int page, int pageSize) {
		Pageable pgb = new PageRequest(page, pageSize);
		return vipOrderDao.findByStatus(status, pgb);
	}

	@Override
	@Transactional
	public boolean logLevelUpdated(String orderNo) {
		vipOrderDao.updateLogLevel(orderNo,VipOrder.LOGED);
		return true;
	}
	
	
}
