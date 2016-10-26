 package com.shangpin.web.controller.pay;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import com.shangpin.base.pay.Gateway;
import com.shangpin.base.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shangpin.base.pay.api.TradeResultDTO;
import com.shangpin.biz.bo.OrderDetailResult;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.service.SPBizCouponService;
import com.shangpin.biz.service.SPBizOrderService;
import com.shangpin.biz.service.user.right.UserRightService;
import com.shangpin.core.entity.order.VipOrder;
import com.shangpin.core.service.IVipOrderService;
import com.shangpin.utils.ServletUtils;
import com.shangpin.web.controller.BaseController;

import lombok.extern.slf4j.Slf4j;


 /**
 * 针对需要wap端单独处理的情况回调的情况
 * @date    2016年8月10日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Slf4j
@Controller
@RequestMapping("/pay-callback")
public class PayCallBackController extends BaseController {

	/**电子卡支付成功页*/
	private static final String GIFTCARD_PAY_SUCCESS = "order/giftcard_pay_success";
	/** 支付成功页面 */
	private static final String PAY_SUCCESS = "order/payment_success";
	private static final String USER_VIP_BUY_RESULT = "user/right/vip_pay_result";
	/**
	 * vip 购买299，买后充券
	 */
	private static final String VIP_BUY_ACTIVATECODE="6762607798";
	@Autowired
	private PayService paySrv;
	@Autowired
	private IVipOrderService orderSrv;
	@Autowired
	private UserRightService usrRightSrv;
	@Autowired
	private SPBizOrderService orderService;
	@Autowired
    private SPBizCouponService bizCouponService;
	/**
	 * 订单支付完成回调
	 * <br/>
	 * @param gateway
	 * @param request
	 */
	@RequestMapping(value="/normal/{gateway}/{orderNo}")
	public String callback(@PathVariable("orderNo")String orderNo,
							  @PathVariable("gateway")String gateway,
							  HttpServletRequest request,Model model) throws Exception {

		Map<String,String> returnParams=ServletUtils.param2Map(request);
		if(returnParams==null||returnParams.size()<=0){
			return "redirect:/index";
		}
		log.info("支付,{}前台通知参数:{}",gateway,returnParams);
        User user = getSessionUser(request);
		Gateway gateWay = Gateway.valueOf(gateway);
		if(gateWay==null){
			throw new Exception("gateWay error");
		}
        TradeResultDTO dto = paySrv.parsePayReturn(returnParams,gateWay);
		if(dto!=null && dto.isPaySuccess()){
			ResultObjOne<OrderDetailResult> orderDetailV3 = orderService.findOrderDetailV3(user.getUserIds(), orderNo,"","");
			OrderDetailResult orderDetail = null;
			if(!orderDetailV3.isSuccess()||orderDetailV3.getContent()==null){
				throw new ValidationException("订单不存在");
			}else{
				orderDetail = orderDetailV3.getObj();
			}
			model.addAttribute("totalPrice", dto.getTotalFee());
			model.addAttribute("orderTime",orderDetail.getOrderTime());
			model.addAttribute("totalPrice",orderDetail.getPayAmount());
			model.addAttribute("payType",orderDetail.getSelectPayment().getDesc());
			model.addAttribute("orderId",orderDetail.getMainOrderId());
			String pic = orderDetail.hasGiftCard();
			if(!pic.equals("")) {
				model.addAttribute("pic",pic);
				return GIFTCARD_PAY_SUCCESS;
			}else{
				return PAY_SUCCESS;
			}
		}else{
			model.addAttribute("statusType","2");
			return "redirect:/order/list";
		}
	}


	/**
	 * 订单支付完成回调
	 * <br/>
	 * @param orderNo
	 * @param request
	 */
	@RequestMapping("/normal/query")
	public String callbackQuery(@RequestParam("orderId")String orderNo,@RequestParam("gateWay")String gateway, HttpServletRequest request,Model model) throws Exception {

		User user = getSessionUser(request);
		Gateway gateWay = Gateway.valueOf(gateway);
		if(gateWay==null){
			throw new Exception("gateWay error");
		}
		TradeResultDTO result=paySrv.payQuery(orderNo, gateWay);
		if(result.isPaySuccess()){
			ResultObjOne<OrderDetailResult> orderDetailV3 = orderService.findOrderDetailV3(user.getUserIds(), orderNo,"","");
			OrderDetailResult orderDetail = null;
			if(!orderDetailV3.isSuccess()||orderDetailV3.getContent()==null){
				throw new ValidationException("订单不存在");
			}else{
				orderDetail = orderDetailV3.getObj();
			}
			model.addAttribute("totalPrice", result.getTotalFee());
			model.addAttribute("orderTime",orderDetail.getOrderTime());
			model.addAttribute("totalPrice",orderDetail.getPayAmount());
			model.addAttribute("payType",orderDetail.getSelectPayment().getDesc());
			model.addAttribute("orderId",orderDetail.getMainOrderId());
			String pic = orderDetail.hasGiftCard();
			if(!pic.equals("")) {
				model.addAttribute("pic",pic);
				return GIFTCARD_PAY_SUCCESS;
			}else{
				return PAY_SUCCESS;
			}
		}else{
			model.addAttribute("statusType","2");
			return "redirect:/order/list";
		}
	}

	/**
	 * 购买前台通知 
	 * <br/>
	 * @param gateway
	 * @param request
	 */
	@RequestMapping(value="/vip/return/{gateway}/{orderNo}")
	public String vipcallback(@PathVariable("orderNo")String orderNo,
			@PathVariable("gateway")Gateway gateway,HttpServletRequest request,Model model){
		Map<String,String> returnParams=ServletUtils.param2Map(request);
		if(returnParams==null||returnParams.size()<=0){
			model.addAttribute("isSuccess", false);
			return USER_VIP_BUY_RESULT;
		}
		log.info("vip购买,{}前台通知参数:{}",gateway,returnParams);
		User u=getSessionUser(request);
		String userId=u.getUserId();
		if(userId==null){
			log.error("{}支付通知，当前用户信息不存在");
			return USER_VIP_BUY_RESULT;
		}
		VipOrder order = orderSrv.getVipOrder(userId, orderNo);
		if(order==null){
			log.error("{}购买vip支付通知，订单:{}不存在",gateway,orderNo);
			throw new ValidationException("订单不存在");
		}
		if(!order.getUserId().equals(userId)){//TODO 支付结果返回null
			return null;
		}
		if(order.isPayed()){
			log.info("vip订单通知时订单已支付：{}",order);
			try{
				if(!order.levelLogd()){
					usrRightSrv.updateUserLevel(userId, orderNo);					
				}
			}catch(Exception e){
				log.error("更新用户等级异常",e);
			}
			updateSessionUserLevel(request, u);
			model.addAttribute("isSuccess", true);
			return USER_VIP_BUY_RESULT;
		}
		TradeResultDTO dto = paySrv.parsePayReturn(returnParams,gateway);
		if(dto!=null && dto.isPaySuccess()){
			boolean updated=updateOrderAndLevel(order.getUserId(),orderNo);
			updateSessionUserLevel(request, u);
			model.addAttribute("isSuccess", updated);
		}
		model.addAttribute("isSuccess", true);
		return USER_VIP_BUY_RESULT;
	}


	protected void updateSessionUserLevel(HttpServletRequest request, User u) {
		u.updateLevelVip();
		addSessionUser(request, u);
	}
	/**
	 * 支付平台通知 
	 * <br/>
	 * @param orderNo
	 * @param gateway
	 * @param dto
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/vip/notify/{gateway}/{orderNo}")
	public void vippayCallback(@PathVariable("orderNo")String orderNo,
			@PathVariable("gateway")Gateway gateway,@RequestBody TradeResultDTO dto,
			HttpServletRequest request,HttpServletResponse response){
		log.info("vip购买,{}后台通知参数:{}",gateway,dto);
		boolean verify= paySrv.verifyCallback(dto);
		log.info("vip购买,{}后台通知验签:{}",gateway,verify);
		boolean succed=false;
		if(dto!=null && dto.isPaySuccess() && verify){
			VipOrder order=orderSrv.getVipOrder(orderNo);
			if(order==null){
				log.error("vip购买后台通知订单{}不存在",orderNo);
			}
			try{
				updateOrderAndLevel(order.getUserId(),orderNo);
				succed=true;
			}catch(Exception e){
				log.error("更新vip订单异常{}",e);
			}
		}
		responseNotify(gateway,response,succed);					
	}
	/**
	 * 充优惠券 
	 * <br/>
	 * @param userId 用户id
	 * @return
	 */
	private boolean rechargeCoupon(String userId) {
		log.info("用户：{}购买vip 299充券",userId);
		bizCouponService.sendCoupon(userId, "1", "coupon:" + VIP_BUY_ACTIVATECODE);
		return true;
	}

	/**
	 * 更新用户等级，只会更新一次 
	 * <br/>并充值优惠券1次
	 * @param userId 用户id
	 * @param orderNo 订单号
	 * @return
	 */
	protected boolean updateOrderAndLevel(String userId,String orderNo) {
		boolean updated= orderSrv.updatePaySuccess(orderNo);			
		if(updated){
			VipOrder o=orderSrv.getVipOrder(orderNo);
			if(!o.levelLogd()){
				boolean levelUpdated=usrRightSrv.updateUserLevel(o.getUserId(), orderNo);
				if(levelUpdated){//记录已经更新权益等级日志
					orderSrv.logLevelUpdated(orderNo);
				}
			}
			//充值优惠券
			rechargeCoupon(userId);
			return true; 
		}
		return false;
	}
	
	/**
     * 异步通知的响应处理，支付成功后的处理
	 * @param dto 通知处理后的结果
	 * @param response
	 * @return 是否直接输出到流了
	 */
	private boolean responseNotify(Gateway gateway, HttpServletResponse response,boolean success) {
		ServletUtils.output(success?"success":"fail",response);					
		return true;
	}
	
	
}
