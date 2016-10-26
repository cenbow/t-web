package com.shangpin.web.controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import com.shangpin.base.pay.Gateway;
import com.shangpin.base.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.base.pay.api.request.OrderPayWebDTO;
import com.shangpin.biz.api.dto.pay.PayAPIConfig;
import com.shangpin.biz.bo.GiftCardReturn;
import com.shangpin.biz.bo.OrderDetailResult;
import com.shangpin.biz.bo.PriceShow;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.order.OrderPriceInfo;
import com.shangpin.biz.service.SPBizOrderService;
import com.shangpin.utils.StringUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.PropertyUtil;
import com.shangpin.web.vo.Payment;
import com.shangpin.wechat.bo.base.AccessToken;
import com.shangpin.wechat.service.WeChatPublicService;

import lombok.extern.slf4j.Slf4j;
import com.shangpin.web.enums.PaymentEnum;

/**
 * @auther liushaoqing
 * @date 2016/8/18
 * 订单支付控制器
 */
@Slf4j
@Controller
@RequestMapping("/order/pay")
public class OrderPayController extends BaseController{

    @Autowired
    private SPBizOrderService bizOrderService;

    @Autowired
    private PayService paySrv;

    @Autowired
    private WeChatPublicService weChatPublicService;

    private static final String ORDER_NORMAL_RETURN_URL = PropertyUtil.getString("order.normal.return_url");

    private static final String ORDER_NORMAL_QUERY_URL = PropertyUtil.getString("order.normal.query_url");
    /** 支付成功页面 */
    private static final String PAY_SUCCESS = "order/payment_success";

    /** 订单管理支付页面 礼品卡支付页面 */
    private static final String ORDER_PAY = "order/payment_normal_v3";

    /** 银联安装控件页面 */
    private static final String INSTALL_UNIONPAY = "order/install_unionpay";

    private static final String PayCategory_Split = "3";

    /**
     * 支付宝,微信，等支付入口
     * @param gateway
     * @param orderId
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{gateway}", method = RequestMethod.GET)
    public String weixinPay(@PathVariable("gateway")String gateway,
                            @RequestParam(value = "orderId") String orderId,
                            HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {


        Gateway gateWay = Gateway.valueOf(gateway);
        if(gateWay==null){
            throw new Exception("gateWay error");
        }
        User user = getSessionUser(request);
        ResultObjOne<OrderPriceInfo> orderPriceInfoWrapper = bizOrderService.findOrderPriceInfo(user.getUserId(), orderId);
        log.info("订单：{}，支付金额：{}，支付方式：{}",orderId,orderPriceInfoWrapper,gateway);
        OrderPriceInfo  orderPriceInfo = null;
        if(orderPriceInfoWrapper.isSuccess()){
            orderPriceInfo = orderPriceInfoWrapper.getObj();
        }
        HttpSession session = request.getSession();
        String openId = (String)session.getAttribute(Constants.WX_ID_NAME);
        //微信公众号特殊处理
        if(gateWay.isWeiXinPub()){
            openId = (String) session.getAttribute(Constants.WX_ID_NAME);
            if(org.apache.commons.lang3.StringUtils.isBlank(openId)){
                String code =(String) session.getAttribute(Constants.VIP_WX_PUB_CODE);
                log.info("orderId:{},weixinpub code:{}",orderId,code);
                //1.获取access_token的票据code
                if(!org.springframework.util.StringUtils.isEmpty(code)) {
                    //2.通过code获取网页授权的access_token
                    AccessToken token = weChatPublicService.getAccessTokenObj(code);
                    //3.根据网页授权的access_token和openid获取用户的基本信息
                    openId = token.getOpenid();
                    session.setAttribute(Constants.WX_ID_NAME,openId);
                }
            }

            Map<String,String> weixinPayParam = getOrderPayParam(orderPriceInfo, user.getUserId(),gateWay,openId);
            log.info("weixin pub 支付参数 :{}"+ weixinPayParam);
            model.addAttribute("payParam", weixinPayParam);
            model.addAttribute("backUrl", ORDER_NORMAL_QUERY_URL+"?orderId="+orderId+"&gateWay="+gateway.toString());
            return "payment/wxpub/wxpub_pay";
        }else {
            Map<String, String> PayParam = getOrderPayParam(orderPriceInfo, user.getUserId(), gateWay, openId);
            log.info("支付参数 :{}"+ PayParam);
                String url = PayAPIConfig.payWebApi + "?" + StringUtil.toQueryString(PayParam);
            log.info("支付url :{}"+ url);
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     *
     * @Title: giftcard
     * @Description:跳转支付页面
     * 礼品卡和普通商品均走这个方法
     * @param
     * @return String
     * @throws
     * @Create By liushaoqing
     */
    @RequestMapping(value = "/payments")
    public String giftcard(String orderId,Model model, HttpServletRequest request) {

        User user = getSessionUser(request);
        ResultObjOne<OrderPriceInfo> orderPriceInfoWrapper = bizOrderService.findOrderPriceInfo(user.getUserId(), orderId);
        OrderPriceInfo  orderPriceInfo = null;
        if(orderPriceInfoWrapper.isSuccess()){
            orderPriceInfo = orderPriceInfoWrapper.getObj();
        }
        String mobile = user.getMobile();
        log.info("未支付订单信息 :{}"+ orderPriceInfo);
        model.addAttribute("orderId", orderId);
        model.addAttribute("canUseGiftAmount", orderPriceInfo.getCanUseGiftAmount());//可用礼品卡金额
        model.addAttribute("phoneNum", mobile);
        model.addAttribute("date", orderPriceInfo.getOrderTime());
        model.addAttribute("isUseGiftCard", orderPriceInfo.getIsUseGiftcardPay());
        model.addAttribute("canUseGiftCard",orderPriceInfo.getCanGiftCardPay());
        model.addAttribute("giftCardBalance",orderPriceInfo.getGiftCardBalance());
        model.addAttribute("onlineAmount", orderPriceInfo.getOnlineAmount());

        int onLineAmountInt = Integer.parseInt(orderPriceInfo.getOnlineAmount());
        int canUseGiftInt = Integer.parseInt(orderPriceInfo.getCanUseGiftAmount());
        model.addAttribute("realPayAmount", onLineAmountInt-canUseGiftInt);

        String payCategory = orderPriceInfo.getPayCategory();
        String isCod = orderPriceInfo.getIsSupportCod();
        String userAgent = request.getHeader("User-Agent");
        model.addAttribute("payments", Payment.getPayments(orderPriceInfo.getPayTypeChildId(),isCod,payCategory,userAgent));
        return ORDER_PAY;
    }

    /**
     *
     * @Title: cardPay
     * @Description:礼品卡支付
     * @param
     * @return Map<String,Object>
     * @throws
     * @Create By liushaoqing
     * @Create Date 2016-08-22
     */
    @RequestMapping(value = "/giftCard/pay")
    @ResponseBody
    public Map<String, Object> cardPayV3(String orderId, String gateWay,String password, HttpServletRequest request) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String userId = getUserId(request);
        ResultObjOne<GiftCardReturn> giftCardReturnResultObjOne = bizOrderService.payGiftCards(userId, password, orderId);
        log.info("礼品卡支付订单:{} 结果 :{}",orderId, giftCardReturnResultObjOne);
        if (giftCardReturnResultObjOne!=null && giftCardReturnResultObjOne.isSuccess()) {
            GiftCardReturn giftCardRe = giftCardReturnResultObjOne.getObj();
            PaymentEnum paymentEnum = PaymentEnum.valueOf(gateWay);
            if(paymentEnum==null){
                throw new Exception("gateWay error");
            }
            //礼品卡部分支付修改支付类型修改支付类型
            if(!giftCardRe.isAllPay()){
                bizOrderService.updatePayType(userId, orderId, paymentEnum.getId(),paymentEnum.getSubId(),paymentEnum.getBankId());
            }
            map.put("code", Constants.SUCCESS);
            map.put("returnInfo", giftCardReturnResultObjOne.getObj());
        } else {
            map.put("code", Constants.FAILE);
            map.put("msg",giftCardReturnResultObjOne.getMsg());
        }
        return map;
    }

    /**
     *
     * @Title: cardSuccess
     * @Description:礼品卡全额支付成功跳转页面
     * @param
     * @return String
     * @throws
     * @Create By qinyingchun
     * @Create Date 2014年12月5日
     */
    @RequestMapping(value = "/giftCard/success", method = RequestMethod.GET)
    public String cardSuccess(String orderId, Model model, HttpServletRequest request) {
        User user = getSessionUser(request);
        ResultObjOne<OrderDetailResult> orderDetailV3 = bizOrderService.findOrderDetailV3(user.getUserIds(), orderId,"","");
        log.info("订单详情 :{}"+ orderDetailV3);
        OrderDetailResult orderDetail = null;
        if(!orderDetailV3.isSuccess()||orderDetailV3.getContent()==null){
            throw new ValidationException("订单不存在");
        }else{
            orderDetail = orderDetailV3.getObj();
        }
        List<PriceShow> priceShow = orderDetail.getPriceShow();
        String totalPrice = null;
        for (PriceShow show : priceShow) {
            if("4".equals(show.getType())){
                totalPrice = show.getAmount();
            }
        }
        if(totalPrice==null){
            throw new ValidationException("系统异常，请重试！");
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("payType", orderDetail.getSelectPayment().getDesc());
        model.addAttribute("orderTime",orderDetail.getOrderTime());
        model.addAttribute("orderId",orderId);
        return PAY_SUCCESS;
    }

    /**
     * @Description:跳转银联控件安装界面
     * @param
     * @return String
     * @throws
     * @Create By liushaoqing
     * @Create
     */
    @RequestMapping(value = "union_pay/install", method = RequestMethod.GET)
    public String install(String orderId, Model model, HttpServletRequest request) {

        String accept = request.getHeader("Accept");
        model.addAttribute("accept", accept);
        model.addAttribute("orderId", orderId);

        return INSTALL_UNIONPAY;
    }

    /**
     * 货到付款
     * @param orderId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/hdpay/success", method = RequestMethod.GET)
    public String hdpay(String orderId,Model model, HttpServletRequest request) {

        User user = getSessionUser(request);
        ResultObjOne<OrderDetailResult> orderDetailV3 = bizOrderService.findOrderDetailV3(user.getUserIds(), orderId, "", "");
        log.info("订单详情 :{}"+ orderDetailV3);
        if(orderDetailV3.isSuccess()){
            model.addAttribute("orderTime",orderDetailV3.getObj().getOrderTime());
            model.addAttribute("totalPrice",orderDetailV3.getObj().getPayAmount());
            model.addAttribute("payType", orderDetailV3.getObj().getSelectPayment().getDesc());
        }
        model.addAttribute("orderId", orderId);

        return PAY_SUCCESS;
    }

    private  Map<String,String> getOrderPayParam(OrderPriceInfo priceInfo,String userId, Gateway gateWay,String openId){

    	
        Float totalFee=Float.valueOf(priceInfo.getOnlineAmount());
        if (PropertyUtil.devModel) {// 判断是否为测试订单
            totalFee=0.02f;//海外的会比较大
        }
        OrderPayWebDTO payweb = new OrderPayWebDTO();
        payweb.setTotalFee(new BigDecimal(totalFee));
        payweb.setGateWay(gateWay);
        payweb.setExtend("userId:"+userId);
        //设置分账金额
        if(gateWay.equals(Gateway.ALIFZWAP) && priceInfo.getPayCategory().equals(PayCategory_Split)){
            BigDecimal abroadNakedPrice = new BigDecimal(priceInfo.getAbroadNakedPrice()).setScale(2);
            BigDecimal abroadFreight = new BigDecimal(priceInfo.getAbroadFreight()).setScale(2);
            BigDecimal customsPrice = new BigDecimal(priceInfo.getCustomsPrice()).setScale(2);
            BigDecimal innerAmount = new BigDecimal(priceInfo.getInternalAmount()).setScale(2);
            payweb.setCustomsFee(abroadNakedPrice,customsPrice,abroadFreight,innerAmount);
            if (PropertyUtil.devModel) {// 判断是否为测试订单
                payweb.setCustomsFee(new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0.01f));
            }
        }
        payweb.setOrderNo(priceInfo.getMainOrderNo());
        payweb.setSubject("Order:"+priceInfo.getMainOrderNo());
        String returnUrl= StringUtil.format(ORDER_NORMAL_RETURN_URL,gateWay.toString(),priceInfo.getMainOrderNo());
        payweb.setReturnUrl(returnUrl);
        payweb.setOpenId(openId);
        String notifyUrl="";
        payweb.setNotifyUrl(notifyUrl);
        if(gateWay.isWeiXinPub()){
            return paySrv.encrypt4WeiXinPub(payweb);
        }else{
            return paySrv.encrypt4PayWeb(payweb);
        }


    }

}
