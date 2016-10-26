package com.shangpin.web.controller.user.right;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shangpin.base.pay.Gateway;
import com.shangpin.base.pay.PayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shangpin.base.pay.api.TradeResultDTO;
import com.shangpin.base.pay.api.request.OrderPayWebDTO;
import com.shangpin.biz.api.dto.pay.PayAPIConfig;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.biz.common.SequenceService;
import com.shangpin.biz.service.user.right.UserRightService;
import com.shangpin.core.constant.OrderStatus.VipOrderStatus;
import com.shangpin.core.entity.order.VipOrder;
import com.shangpin.core.service.IVipOrderService;
import com.shangpin.utils.ServletUtils;
import com.shangpin.utils.StringUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.PropertyUtil;
import com.shangpin.wechat.bo.base.AccessToken;
import com.shangpin.wechat.service.WeChatPublicService;

import lombok.extern.slf4j.Slf4j;


/** 
 * vip权益页面<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Slf4j
@Controller
@RequestMapping("/right-vip")
public class VipOrderController extends BaseController {
	private static final String ORDER_VIP_QUERY_URL = PropertyUtil.getString("order.vip.query_url");
	private static final String ORDER_VIP_RETURN_URL = PropertyUtil.getString("order.vip.return_url");
	private static final String ORDER_VIP_NOTIFY_URL = PropertyUtil.getString("order.vip.notify_url");
	private static final String VIP_ORDER_NO="VIP_ORDER_NO";
	@Autowired
	IVipOrderService orderSrv;
	@Autowired
	PayService paySrv;
	@Autowired
	UserRightService usrRightSrv;
	@Autowired
	private WeChatPublicService weChatPublicService;
	/**
	 * vip购买 
	 * <br/>
	 * @param gateway 支付网关
	 * @param //level 购买级别
	 * @param request
	 */
	@RequestMapping(value="/order",method={RequestMethod.POST})
	public String buyVip(@RequestParam("gateway")Gateway gateway, HttpServletRequest request, HttpServletResponse resp, HttpSession session, Model model){
		VipOrder order = new VipOrder();
		User u = getSessionUser(request);
		UserLevelInfo ul = getUserLevelInfo(request);
		if(u.isVip() && !(ul!=null && ul.isTryVip())){
			return bought(model);
		}
		order.setUserId(getUserId(request));
		order.setAmount(getLevelAmount(1));
		order.setLevel(1);
		order.setGateway(gateway.name());
		order.setPlantform(1);
		order.setStatus(VipOrderStatus.WAIT);
		order.setOrderNo(SequenceService.getVipOrderNo());
		order = orderSrv.createVipOrder(order);
		if(order.isPayed()){
			if(!order.levelLogd()){
				usrRightSrv.updateUserLevel(order.getUserId(),order.getOrderNo());
			}
			u.updateLevelVip();
			addSessionUser(request,u);
			return bought(model);
		}
		log.info("{} 购买vip下单数据:{}",order.getUserId(),order);
		session.setAttribute(VIP_ORDER_NO, order.getOrderNo());
		if(gateway.isWeiXinPub()){
			String openId = getOpenId(session);
			Map<String,String> weixinPayParam = getWeixinPayParam(order,ServletUtils.getIpAddr(request),openId);
			model.addAttribute("payParam", weixinPayParam);
			model.addAttribute("orderId", order.getOrderNo());
			model.addAttribute("backUrl", ORDER_VIP_QUERY_URL);
			return "payment/wxpub/wxpub_pay";
		}else{
			String url=getPayUrl(order,ServletUtils.getIpAddr(request),(String)session.getAttribute(Constants.WX_ID_NAME));
			try {
				resp.sendRedirect(url);
			} catch (IOException e) {
			}			
		}
		return null;
	}
	private static final String USER_VIP_BUY_RESULT = "user/right/vip_pay_result";
	@RequestMapping(value="/order/query")
	public String vipOrderQuery(HttpServletRequest request,HttpServletResponse resp,
			HttpSession session,Model model){
		String orderNo = (String)session.getAttribute(VIP_ORDER_NO);
		log.info("查询vip订单:{}",orderNo);
		model.addAttribute("isSuccess", false);
		if(!StringUtils.isEmpty(orderNo)){
			VipOrder order= queryOrderStatu(getUserId(request),orderNo);
			if(order!=null && order.isPayed()){
				User u = getSessionUser(request);
				u.updateLevelVip();
				request.getSession().removeAttribute(Constants.SESSION_USER_LEVEL);
				addSessionUser(request,u);
				model.addAttribute("isSuccess", true);
			}
		}
		return USER_VIP_BUY_RESULT;
	}
	private VipOrder queryOrderStatu(String userId, String orderNo) {
		VipOrder order=orderSrv.getVipOrder(userId,orderNo);
		if(order!=null && !order.isPayed()){
			//<2小时
			if(System.currentTimeMillis()-order.getUpdateDate().getTime()<7200000){
				TradeResultDTO result=paySrv.payQuery(orderNo, Gateway.valueOf(order.getGateway()));
				if(result.isPaySuccess()){
					order.setStatus(VipOrderStatus.SUCCESS);
					updateOrderAndLevel(orderNo,userId);
				}
			}
		}
		return order;
	}
	/**
	 * 获取openId 
	 * <br/>
	 * @param session
	 * @return
	 */
	protected String getOpenId(HttpSession session) {
		String openId = (String) session.getAttribute(Constants.WX_ID_NAME);
		if(StringUtils.isBlank(openId)){
			String code =(String) session.getAttribute(Constants.VIP_WX_PUB_CODE);
			log.info("vip buy weixinpub code :{}"+code);
			//1.获取access_token的票据code
			if(!StringUtils.isEmpty(code)) {
				//2.通过code获取网页授权的access_token
				AccessToken token = weChatPublicService.getAccessTokenObj(code);
				//3.根据网页授权的access_token和openid获取用户的基本信息
				openId = token.getOpenid();
				session.setAttribute(Constants.WX_ID_NAME,openId);
			}
		}
		return openId;
	}
	/**
	 * 已经购买返回数据 
	 * <br/>
	 * @param model
	 * @return
	 */
	protected String bought(Model model) {
		model.addAttribute("msg", "您已经购买，无需再次购买");
		model.addAttribute("isSuccess", true);
		return "user/right/vip-memberBtn";
	}
	protected boolean updateOrderAndLevel(String orderNo, String userId) {
		boolean updated=orderSrv.updatePaySuccess(orderNo);
		if(updated)
			return usrRightSrv.updateUserLevel(userId, orderNo);
		return false;
	}
	private String getPayUrl(VipOrder order,String ip,String openId) {
		OrderPayWebDTO payweb = convertPayWebOrder(order, openId);
		Map<String,String> reqparams=paySrv.encrypt4PayWeb(payweb);
		return PayAPIConfig.payWebApi+"?"+StringUtil.toQueryString(reqparams);
	}
	private Map<String,String> getWeixinPayParam(VipOrder order,String ip,String openId) {
		OrderPayWebDTO payOrder = convertPayWebOrder(order,openId);
		return paySrv.encrypt4WeiXinPub(payOrder);
	}
	protected OrderPayWebDTO convertPayWebOrder(VipOrder order, String openId) {
		Float totalFee=order.getAmount();
		if (PropertyUtil.devModel) {// 判断是否为测试订单
			totalFee=0.02f;//海外的会比较大
		}
		OrderPayWebDTO payweb = new OrderPayWebDTO();
		payweb.setTotalFee(new BigDecimal(totalFee));
		payweb.setGateWay(Gateway.valueOf(order.getGateway()));
		payweb.setExtend("userId:"+order.getUserId());
		payweb.setOrderNo(order.getOrderNo());
		payweb.setSubject("VIP Order:"+order.getOrderNo());
		String returnUrl=StringUtil.format(ORDER_VIP_RETURN_URL,order.getGateway(),order.getOrderNo());
		payweb.setReturnUrl(returnUrl);
		payweb.setOpenId(openId);
		String notifyUrl=StringUtil.format(ORDER_VIP_NOTIFY_URL,order.getGateway(),order.getOrderNo());
		payweb.setNotifyUrl(notifyUrl);
		return payweb;
	}
	private Float getLevelAmount(Integer level) {
		return 299f;
	}
}
