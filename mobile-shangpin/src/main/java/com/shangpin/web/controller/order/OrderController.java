package com.shangpin.web.controller.order;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.biz.bo.MainOrder;
import com.shangpin.biz.bo.Order;
import com.shangpin.biz.bo.OrderDetailResult;
import com.shangpin.biz.bo.OrderItem;
import com.shangpin.biz.bo.OrderListResult;
import com.shangpin.biz.bo.PriceShow;
import com.shangpin.biz.bo.Product;
import com.shangpin.biz.bo.ProductDetail;
import com.shangpin.biz.bo.Province;
import com.shangpin.biz.bo.QuickOrderParam;
import com.shangpin.biz.bo.QuickResult;
import com.shangpin.biz.bo.QuickUser;
import com.shangpin.biz.bo.ReceiveRequest;
import com.shangpin.biz.bo.SubmitOrder;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.user.UserBuyInfo;
import com.shangpin.biz.deprecated.SPBiz520SellService;
import com.shangpin.biz.service.SPBizAddressService;
import com.shangpin.biz.service.SPBizOrderService;
import com.shangpin.biz.service.SPBizSendInfoService;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.biz.utils.FreeBie520Util;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.ActivifyUtil;
import com.shangpin.web.utils.CookieUtil;
import com.shangpin.web.utils.UserUtil;
import com.shangpin.web.vo.Payment;

/**
 * @ClassName: OrderController
 * @Description:订单管理相关方法控制层
 * @author zhongchao
 * @date 2014年10月30日
 * @version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	/** 跳转到订单列表页面 */
	private static final String ORDER_LIST = "order/order_list";
	/** 跳转到订单详情页面 */
	private static final String ORDER_DETAILV3 = "order/detailV3";
	/** 礼品卡支付找回密码 */
	private static final String GIFT_CARD_FORGET_PASSWORD = "order/forget_giftcard";

	/**客服虚拟系统代客下的礼品卡订单详情页*/
	private static final String GIFT_CARD_DETAIL = "order/giftcard_detail";
	/**客服虚拟系统代客下的礼品卡订单待支付除外的其他订单状态跳转的页面*/
	private static final String GIFT_CARD_INFO = "order/giftcard_info";

	@Autowired
	private SPBizOrderService bizOrderService;
	@Autowired
	private SPBizUserService userService;
	@Autowired
	private SPBizAddressService bizAddressService;
	@Autowired
	private SPBizSendInfoService bizSendInfoService;
	@Autowired
	private SPBizOrderService spBizOrderService;

	/**
	 * 1 全部订单 2 待支付 3 待收货 4 待发货
	 * @param statusType
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listV3(@RequestParam(value = "statusType" ,defaultValue = "1") String statusType,
							  HttpServletRequest request,Map<String, Object> map) {
		User user = getSessionUser(request);
		map.put("statusType", statusType);

		UserBuyInfo userBuyInfo = bizOrderService.getOrderCount(user.getUserId());
		OrderListResult result = bizOrderService.orderlist(user.getUserIds(), statusType);

		map.put("waitPayCount",userBuyInfo.getWaitpaycount());//待支付
		map.put("waitDeliverCount",userBuyInfo.getPreparegoodscount());//待发货
		map.put("waitFetchCount",userBuyInfo.getDelivergoodscount());//待收货

		if (result == null || result.getList()==null || result.getList().size()==0) {
			map.put("mainOrderlist", null);
			return ORDER_LIST;
		}

		map.put("currentTimes", System.currentTimeMillis());
		map.put("currentTimes", result.getSystime());
		map.put("statusType", statusType);
		map.put("pageIndex", result.getPageIndex());
		map.put("result", result);
		return ORDER_LIST;
	}

	/**
	 *
	 * @Title: getMore
	 * @Description: style搭配列表获取更多
	 * @param
	 * @return Map<String, Object>
	 * @throws
	 * @Create By liling
	 * @Create Date 2014年11月17日
	 */
	@ResponseBody
	@RequestMapping(value = "/getMore", method = RequestMethod.POST)
	public Map<String, Object> getMore(@RequestParam("statusType") String statusType,
			@RequestParam("pageIndex") String pageIndex, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		User user = getSessionUser(request);
		if (StringUtils.isEmpty(statusType)) {
			statusType = "2";
		}
		map.put("statusType", statusType);
		pageIndex=String.valueOf(Integer.valueOf(pageIndex)+1);
		OrderListResult result = bizOrderService.orderlist(user.getUserId(), statusType,pageIndex);
		if (result == null ) {
			return map;
		}
		List<MainOrder> mainOrderList =result.getList();
		if (mainOrderList==null) {
			return map;
		}
		map.put("code", "0");
		map.put("statusType", statusType);
		map.put("pageIndex", result.getPageIndex());
		map.put("result", result);
		return map;
	}

	/**
	 * @Title: order_detail
	 * @Description: 跳转到订单详情页面
	 * @param orderId  订单号
	 * @Create By liling
	 * @Create Date 2015年9月17日
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String toOrderDetailV3( String mainOrderId,String orderId,String isSplitOrder, Model model,HttpServletRequest request) {

		User user = getSessionUser(request);

		ResultObjOne<OrderDetailResult> orderDetailResult =spBizOrderService.findOrderDetailV3(user.getUserIds(), mainOrderId, orderId, isSplitOrder);
		if(orderDetailResult!=null && orderDetailResult.getCode().equals(ResultObjOne.SUCCESS)){

			OrderDetailResult orderDetail = orderDetailResult.getObj();
			//如果可支付状态下的订单 默认选中礼品卡需要特别处理,自己减去金额
			if(orderDetail.getCanPay().equals("1") && orderDetail.getIsUseGiftCard().equals("1")){

				for (PriceShow priceShow : orderDetail.getPriceShow()) {
					if(priceShow.getType().equals("4")){//礼品卡
						String giftAmount = priceShow.getAmount();
						if(org.apache.commons.lang3.StringUtils.isNotBlank(giftAmount)){
							if(Integer.parseInt(giftAmount)<=0) {
								priceShow.setAmount(orderDetail.getCanUseGiftAmount());
							}
						}
					}
					//总金额
					if(priceShow.getType().equals("7")){
						priceShow.setAmount(String.valueOf(Integer.parseInt(priceShow.getAmount())-Integer.parseInt(orderDetail.getCanUseGiftAmount())));
					}
				}
			}
			model.addAttribute("orderDetail",orderDetail );

			String payCategory = orderDetail.getPayCategory();
			String isCod = orderDetail.getIsCod();
			String useragent = request.getHeader("User-Agent");
			Map<String,Object> payMap = Payment.getPayments(orderDetail.getSelectPayment(), payCategory, isCod, useragent);
			logger.info("订单：{}，payCategory:{},可选支付方式：{}",orderDetail.getMainOrderId(),payCategory,payMap);

            model.addAttribute("payments",payMap.get("list"));
			model.addAttribute("pay",payMap.get("default"));
			model.addAttribute("currentTimes", orderDetail.getSystime());
			//目前是明文传输
			/*if(receive != null){
				String cardId = receive.getCardID();
				receive.setCardID(decrypt(cardId));
			}*/
			//List<Receive> receiveList = orderDetailResult.getObj().getReceiveList();
			//目前是明文传输
			/*for(Receive rec : receiveList){
				if(rec != null){
					String cardId = rec.getCardID();
					rec.setCardID(decrypt(cardId));
				}
			}*/
		}
		return ORDER_DETAILV3;
	}

	/**
	 * 客服虚拟系统客服代客下礼品卡够买的订单，订单中可以包含多张礼品卡
	 * @param userId
	 * @param mainOrderId
	 * @param orderId
	 * @param isSplitOrder
	 * @param model
	 * @param request
	 * @return
	 * @author qinyingchun
	 */
	@RequestMapping(value = "/giftcard/detail", method = RequestMethod.GET)
	public String giftCareDetail(String userId, String mainOrderId,String orderId,String isSplitOrder, Model model,HttpServletRequest request){
		OrderDetailResult orderResult=spBizOrderService.findOrderDetail(userId, mainOrderId, orderId, isSplitOrder);
		if(null == orderResult){
			return GIFT_CARD_DETAIL;
		}
		String status = orderResult.getStatus();
		if(!Constants.ORDER_STATUS_PAY_WAIT.equals(status)){
			model.addAttribute("status", status);
			return GIFT_CARD_INFO;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
	    if (!StringUtils.isEmpty(user)) {
	         session.removeAttribute(Constants.SESSION_USER);
	         session.removeAttribute(Constants.SESSION_USERID);
	    }
		User giftcardUser = userService.findUserByUserId(userId);
		request.getSession().setAttribute(Constants.SESSION_USERID, userId);
		request.getSession().setAttribute(Constants.SESSION_USER, giftcardUser);
		List<Province> provinces = bizAddressService.findProvinceListObj();
		model.addAttribute("provinces", provinces);
		model.addAttribute("orderResult", orderResult);
		return GIFT_CARD_DETAIL;
	}
	/**
	 * 修改订单信息
	 * @param orderId
	 * @param addrId
	 * @param express
	 * @param invoiceAddrId
	 * @param invoiceFlag
	 * @param invoiceType
	 * @param invoiceTitle
	 * @param invoiceContent
	 * @param isModifyInvoice
	 * @param request
	 * @return
	 * @author qinyingchun
	 */
	@RequestMapping(value = "/modify", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultBase modifyOrder(String orderId, String addrId, String express, String invoiceAddrId, String invoiceFlag, String invoiceType, String invoiceTitle, String invoiceContent, String isModifyInvoice, HttpServletRequest request){
		String userId = (String)request.getSession().getAttribute(Constants.SESSION_USERID);
		ResultBase resultBase = spBizOrderService.modifyOrder(userId, orderId, addrId, express, invoiceAddrId, invoiceFlag, invoiceType, invoiceTitle, invoiceContent, isModifyInvoice);
		return resultBase;
	}

	/**
	 * 确认收货
	 * @param mainOrderNo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public Map<String, Object> finishOrderV3(@RequestParam(value = "mainOrderNo") String mainOrderNo,
										   HttpServletRequest request) {
		User user = getSessionUser(request);
		String userId=user.getUserId();
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == user || StringUtils.isEmpty(userId)) {
			map.put("code", "2");
		}else{
			map = bizOrderService.finishedOrderV3(userId, mainOrderNo);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public Map<String, Object> cancelOrder(@RequestParam(value = "mainOrderNo") String mainOrderNo,HttpServletRequest request) {
		User user = getSessionUser(request);
		return bizOrderService.cancelOrder(user.getUserId(), mainOrderNo);
	}

	/**
	 *
	 * @Title: buyNow
	 * @Description:立即购买，调转到立即购买提交订单页
	 * @param
	 * @return String
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年11月20日
	 */
	/*@RequestMapping(value = "/now", method = RequestMethod.GET)
	public String buyNow(String sku, String productNo, String amount, Model model, HttpServletRequest request) {
		Cookie cookie = CookieUtil.getCookieByName(request, "quickPayUser");
		if (cookie != null) {
			String value;
			try {
				value = URLDecoder.decode(cookie.getValue(), Constants.DEFAULT_ENCODE);
				model.addAttribute("params", this.strToObj(value));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		List<Province> provinces = bizAddressService.findProvinceListObj();
		model.addAttribute("provinces", provinces);
		model.addAttribute("sku", sku);
		model.addAttribute("productNo", productNo);
		model.addAttribute("amount", amount);
		return ORDER_NOW;
	}*/

	/**
	 *
	 * @Title: submit
	 * @Description: 立即购买提交订单
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年11月20日
	 */
/*	@SuppressWarnings("unused")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submit(QuickOrderParam params, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String tel = params.getTel();
		Cookie cookie = CookieUtil.getCookieByName(request, params.getTel());
		if (cookie == null) {
			StringBuffer sb = this.objToStr(params);
			CookieUtil.addCookie(response, "quickPayUser", sb.toString(), Constants.COOKIE_TIME);
		}
		QuickUser user = userService.checkUser(tel, Constants.CREATE_NEW_USER);
		String userId = user.getUserId();
		User user2 = userService.findUserByUserId(userId);
		request.getSession().setAttribute("quickSubmitUID", userId);
		request.getSession().setAttribute(Constants.SESSION_USER, user2);
		params.setUserId(userId);
		params.setShopType(Constants.SKU_SP);
		String phone = params.getTel();
		// 13621 10 1904
		String lastNum = phone.substring(7);
		String pwd = phone.substring(5);
		String isNewUser = user.getIsNewUser();
		String msgTemplate = "";
		ResultObjOne<QuickResult> obj = bizOrderService.quickOrder(params);
		if (Constants.FAILE.equals(obj.getCode())) {
			map.put("code", Constants.FAILE);
			map.put("msg", obj.getMsg());
		} else {
			map.put("code", Constants.SUCCESS);
			map.put("orderId", obj.getObj().getOrderId());
			if (Constants.IS_NEW_USER.equals(isNewUser)) {
				msgTemplate = "太棒啦！你抢到了1元女神新装，使用你的手机号，登录密码：手机号后六位。点击链接t.cn/RvweXV1 下载尚品网APP查看订单";
			} else {
				msgTemplate = "太棒啦！你抢到了1元女神新装，使用你的手机号打开尚品网APP查看订单，若忘记密码，打开APP登录页面，点击‘手机快捷登录’。点击链接立刻查看t.cn/RvweXV1";
			}
			boolean flag = bizSendInfoService.sendInfo(userId, phone, msgTemplate);
			logger.debug("send info=========={}", flag);
		}
		return map;
	}*/

	/**
	 *
	 * @Title: submitOrder
	 * @Description:常规购物提交订单
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年12月3日
	 */
	/*@RequestMapping(value = "/common/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitOrder(SubmitOrder order, HttpServletRequest request) {
		String userid = getUserId(request);
		Map<String, String> cookieMap = UserUtil.getThridCookie(request);
    	Map<String, Object> map  = bizOrderService.submitCommonOrderMap(cookieMap,userid, order.getAddrid(),
    				order.getInvoiceaddrid(), order.getInvoiceflag(), order.getInvoicecontent(), order.getInvoicetype(),
    				order.getInvoicetitle(), order.getCouponflag(), order.getCoupon(), order.getExpress(),
    				order.getOrderfrom(), order.getBuysIds(), order.getPaytypeid(), order.getPaytypechildid(),
    				order.getOrdertype(), order.getIsUseGiftCardPay(), order.getOrderSource(), order.getPostArea());

		return map;
	}*/

	/**
	 *
	 * @Title: forgetCardPassword
	 * @Description:礼品卡找回密码页
	 * @param
	 * @return String
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年12月4日
	 */
	@RequestMapping(value = "/card/password", method = RequestMethod.GET)
	public String forgetCardPassword(String mobile, Model model, HttpServletRequest request) {
		String userid = getUserId(request);
		model.addAttribute("mobile", mobile);
		String msgTempl = "您的验证码是：{$verifyCode$}，请及时输入验证!";
		bizSendInfoService.sendInfo(userid, mobile, msgTempl);
		return GIFT_CARD_FORGET_PASSWORD;
	}

	/**
	 *
	 * @Title: sendPhoneCode
	 * @Description: 发送手机验证码
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年12月4日
	 */
	@RequestMapping(value = "/send/code", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendPhoneCode(String mobi, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userid = getUserId(request);
		String msgTempl = "您的验证码是：{$verifyCode$}，请及时输入验证!";
		boolean flag = bizSendInfoService.sendInfo(userid, mobi, msgTempl);
		if (flag) {
			map.put("code", Constants.SUCCESS);
		} else {
			map.put("code", Constants.FAILE);
			map.put("msg", "短信下发失败！");
		}
		return map;
	}

	/**
	 *
	 * @Title: verifycode
	 * @Description: 验证手机发送的验证码
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年12月4日
	 */
	@RequestMapping(value = "/code/verify", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> verifycode(String mobi, String verifycode, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userid = getUserId(request);
		ResultBase result = bizSendInfoService.verifyPhoneCode(userid, mobi, verifycode);
		String code = result.getCode();
		if (code.equals(Constants.SUCCESS)) {
			map.put("code", Constants.SUCCESS);
		} else {
			map.put("code", Constants.FAILE);
			map.put("msg", result.getMsg());
		}
		return map;
	}

	@RequestMapping(value = "/set/password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPassword(String password, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userid = getUserId(request);
		ResultBase result = bizOrderService.beSetGiftCardPassword(userid, password);
		String code = result.getCode();
		if (code.equals(Constants.SUCCESS)) {
			map.put("code", Constants.SUCCESS);
		} else {
			map.put("code", Constants.FAILE);
			map.put("msg", result.getMsg());
		}
		return map;
	}


	/*private StringBuffer objToStr(QuickOrderParam params) {
		StringBuffer sb = new StringBuffer();
		String name = params.getConsigneeName();
		String provinceId = params.getProvince();
		String provinceName = params.getProvinceName();
		String cityId = params.getCity();
		String cityName = params.getCityName();
		String areaId = params.getArea();
		String areaName = params.getAreaName();
		String townId = params.getTown();
		String townName = params.getTownName();
		String addr = params.getAddress();
		String tel = params.getTel();
		String postcode = params.getZip();
		String payTypeId = params.getPayTypeId();
		String payTypeChildId = params.getPayTypeChildId();
		sb.append(name);
		sb.append(":" + provinceId);
		sb.append(":" + provinceName);
		sb.append(":" + cityId);
		sb.append(":" + cityName);
		sb.append(":" + areaId);
		sb.append(":" + areaName);
		sb.append(":" + townId);
		sb.append(":" + townName);
		sb.append(":" + addr);
		sb.append(":" + tel);
		sb.append(":" + postcode);
		sb.append(":" + payTypeId);
		sb.append(":" + payTypeChildId);
		return sb;
	}*/

	/*private QuickOrderParam strToObj(String sb) {
		QuickOrderParam param = new QuickOrderParam();
		String[] strs = sb.split(":");
		param.setConsigneeName(strs[0]);
		param.setProvince(strs[1]);
		param.setProvinceName(strs[2]);
		param.setCity(strs[3]);
		param.setCityName(strs[4]);
		param.setArea(strs[5]);
		param.setAreaName(strs[6]);
		param.setTown(strs[7]);
		param.setTownName(strs[8]);
		param.setAddress(strs[9]);
		param.setTel(strs[10]);
		param.setZip(strs[11]);
		param.setPayTypeId(strs[12]);
		param.setPayTypeChildId(strs[13]);
		return param;
	}*/



	/***
	 *#2016-05-20 买赠分享列表页面
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "/freebie/share")
    public String freebieShare(HttpServletRequest request, Model model){
		if(ActivifyUtil.isfreeActivify()){
			String userId = UserUtil.getUserId(request);
			if (StringUtils.isEmpty(userId)) {
				return null;
			}
			List<Product>  oderFreeBieList=freebieService.initFreebieList(userId);
			model.addAttribute("orderList", oderFreeBieList);
			model.addAttribute("shareTitle",com.shangpin.biz.utils.Constants.FREEBIE_520_SHARE_M_TITLE);
			model.addAttribute("shareDesc",com.shangpin.biz.utils.Constants.FREEBIE_520_SHARE_DESC);
			model.addAttribute("sharePic",com.shangpin.biz.utils.Constants.FREEBIE_520_SHARE_PIC);
			return FREEBIE_SHARE;
		}else{
			return "redirect:/index";
		}
    }*/
	/***
	 * #2016-05-20买赠分享页面
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "/freebie")
    public String freebie(@RequestParam String p, String sharePlat,Model model,HttpServletRequest request){

			//解密参数获得页面去向;
			Map<String,String> parameter= FreeBie520Util.decodeFreebieParam(p);
			if (parameter==null) {
				return null;
			}
			model.addAttribute("p", p);
			if ((!StringUtils.isEmpty(sharePlat)) && ("2".equals(sharePlat))) {
				return "redirect:/product/freebieDetail";
			}
			String productNo = parameter.get("spu");
			ProductDetail productDetail = sPBiz520SellService.getProductDetail(productNo);
			model.addAttribute("productDetail", productDetail);
			//老虎机游戏
			return "activity/slot/index";
    }*/
    /**
     * 520买赠获取领取
     *
     * @param request
     * @return
     * @author wh
     */
  /*  @RequestMapping(value = "/receive")
    @ResponseBody
    public Map<String, Object> receive(ReceiveRequest receiveRequest, @RequestParam String p, HttpServletRequest request) {

    	Map<String, Object> map = new HashMap<String, Object>();
		if(!ActivifyUtil.isfreeActivify()){
			  map.put("code", "4");
              map.put("msg", "活动已结束!");
              return map;
		}
        User user = getSessionUser(request);
        if (user != null && !StringUtils.isEmpty(user.getUserId())) {
            // 解密参数获得页面去向;
            Map<String, String> parameter = FreeBie520Util.decodeFreebieParam(p);
            if (parameter == null) {
                map.put("code", "1");
                map.put("msg", "参数有误!");
                return map;
            }
            receiveRequest.setFspuSend(parameter.get("spu"));
            receiveRequest.setFuserId(parameter.get("userId"));
            receiveRequest.setForderId(parameter.get("orderId"));
            receiveRequest.setFspuId(parameter.get("sku"));
            receiveRequest.setFspuNo(parameter.get("sortNo"));
            receiveRequest.setPhone(user.getMobile());
            receiveRequest.setLuserId(user.getUserId());
            try {
				map = sPBizReceiveService.pickFreebie(receiveRequest);
			} catch (Exception e) {
				logger.error("调用SPBizReceiveService.pickFreebie方法发生异常.......");
				e.printStackTrace();
				throw new RuntimeException("调用SPBizReceiveService.pickFreebie方法发生异常....... ", e);
			}
        }
        return map;
    }*/
}
