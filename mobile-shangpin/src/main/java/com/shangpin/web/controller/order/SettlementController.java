package com.shangpin.web.controller.order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.biz.bo.Address;
import com.shangpin.biz.bo.Province;
import com.shangpin.biz.bo.Receive;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.cart.Cart;
import com.shangpin.biz.bo.orderUnion.CouponsV3;
import com.shangpin.biz.bo.orderUnion.CouponsWraper;
import com.shangpin.biz.bo.orderUnion.OrderSubmitParamV3;
import com.shangpin.biz.bo.orderUnion.OrderSubmitResultV3;
import com.shangpin.biz.bo.orderUnion.SettleRefreshV3;
import com.shangpin.biz.bo.orderUnion.SettlementV3;
import com.shangpin.biz.service.SPBizAddressService;
import com.shangpin.biz.service.SPBizCartService;
import com.shangpin.biz.service.SPBizSettlementService;
import com.shangpin.biz.utils.Constants;
import com.shangpin.utils.JSONUtils;
import com.shangpin.utils.StringUtil;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.enums.PaymentEnum;
import com.shangpin.web.utils.IDCard;
import com.shangpin.web.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:520结算controller
 * @author liushaoqing
 * @date 2016年04月23日
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/settlement")
public class SettlementController extends BaseController{

    private static final String session_key = "settle";

    private static final String PAYV3 = "settle/cart_pay";

    private static final String PRODUCTSV3 = "settle/product";
    @Autowired
    private SPBizCartService bizCartService;
    @Autowired
    private SPBizAddressService bizAddressService;
    @Autowired
    private SPBizSettlementService bizSettlementService;


    @RequestMapping(value = "/topay", method = RequestMethod.GET)
    public String toPayV3(@RequestParam String shopCartDetailIds, Model model, HttpServletRequest request) throws Exception {

        String userId = getUserId(request);
        List<String> buyids = Arrays.asList(shopCartDetailIds.split("\\|"));
        ResultObjOne<Cart> cart = bizCartService.doShowCartV3(userId, buyids);
        if (cart==null || !cart.isSuccess()) {
            model.addAttribute("msg", "");
            return "redirect:/cart/list";
        } else {

            ResultObjOne<SettlementV3> settle = bizSettlementService.settleV3(userId, buyids);

            if (settle.getCode().equals(ResultObjOne.SUCCESS)){
                SettlementV3 settleV3 = settle.getObj();
                log.info("settle topay result: {}",settleV3);
                List<com.shangpin.web.vo.Payment> payments = getPayments(settleV3,"", request);
                List<Province> provinces = bizAddressService.findProvinceListObj();
                //获取可送达接口地址 // 个人中心0 购物车为1 立即购买为2
                String orderSource= "1";//个人中心为0， 购物车为1， 立即购买为2 默认为0
                String buytype = "0";// 0商品  ，1 电子卡 ，2 实物卡  默认为0
                String receiveId = "";//已选中Id
                String isHasOutSidePro = settleV3.getIsNeedCardId();//是否包含海外商品1是  0否
                //获取可送达接口地址 // 个人中心0 购物车为1 立即购买为2
                List<Receive> addresses = bizSettlementService.accessAddressV3(userId,buyids,orderSource,buytype,receiveId,isHasOutSidePro);
                setCardId(addresses);
                String data = JSONUtils.obj2json(settleV3);
                request.getSession().setAttribute(session_key,settleV3);
                model.addAttribute("buyIds",shopCartDetailIds);
                model.addAttribute("settle", settleV3);
                model.addAttribute("addresses",addresses);
                model.addAttribute("provinces", provinces);
                model.addAttribute("payments",payments);
                model.addAttribute("orderSource","1"); //1 购物车 2，立即购买
                model.addAttribute("cardId",getCardId(settleV3));
                model.addAttribute("data", data);
                log.info("settle end");
                return PAYV3;
            }else{
                return "redirect:/cart/list";
            }
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String toPayV3(HttpServletRequest request) throws Exception {
        log.info("session de  value:{}",JSONUtils.obj2json(request.getSession().getAttribute(session_key)));
        boolean isnull = request.getSession().getAttribute(session_key)==null;
        log.info("isnull:"+isnull);
        return PRODUCTSV3;
    }

    /**
     * 用于选择地址，优惠券，礼品卡等，结算页面数据动态刷新
     * @param request
     * @return
     */
    @RequestMapping(value = "/freshV3", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> toPayFreshV3(@RequestParam String fresh,HttpServletRequest request) {

        SettleRefreshV3 settleRefresh = null;
        try {
            settleRefresh = JSONUtils.json2pojo(fresh, SettleRefreshV3.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userId = getUserId(request);
        settleRefresh.setUserId(userId);
        SettlementV3 settle_old = (SettlementV3)request.getSession().getAttribute(session_key);
        settleRefresh.setPriceShow(settle_old.getPriceShow());
        settleRefresh.setIsProductCod(settle_old.getIsProductCod());
        settleRefresh.setPayCategory(settle_old.getPayCategory());
        log.info("settle fresh param: {}",settleRefresh);
        ResultObjOne<SettlementV3> settleRep = bizSettlementService.refreshSettleV3(settleRefresh);
        log.info("settle fresh result: {}",settleRep);
        Map<String,Object> map = new HashMap<>();
        if (settleRep.getCode().equals(ResultObjOne.SUCCESS)){
            SettlementV3 sessionInSettle = settleConvert(settleRep.getObj(),request);
            map.put(session_key,settleRep.getObj());
            List<com.shangpin.web.vo.Payment> payments = getPayments(sessionInSettle,"", request);
            map.put("payments",payments);
            map.put("code","0");
        }else{
            map.put("error","inter face error");
        }
        return map;
    }

    /**
     *
     * @param isCod 地址是否支持货到付款
     * @param request
     * @return
     */
    @RequestMapping(value = "/addressFreshV3", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> getPayments(@RequestParam("isCod") String isCod,HttpServletRequest request) {

        SettlementV3 settle_old = (SettlementV3)request.getSession().getAttribute(session_key);
        List<com.shangpin.web.vo.Payment> payments = getPayments(settle_old, isCod, request);
        Map<String,Object> map = new HashMap<>();
        map.put("payments",payments);
        return map;
    }

    /**
     * 用于结算页面订单提交
     */
    @RequestMapping(value = "/submitV3", method = RequestMethod.POST)
    @ResponseBody
    public ResultObjOne<OrderSubmitResultV3> submitV3(String param, Model model, HttpServletRequest request) throws Exception {


        OrderSubmitParamV3 orderSubmitParam = null;
        try {
            orderSubmitParam = JSONUtils.json2pojo(param, OrderSubmitParamV3.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SettlementV3 settle_old = (SettlementV3)request.getSession().getAttribute("settle");
        orderSubmitParam.setUserId(getUserId(request));
        orderSubmitParam.setOrderFrom(OrderSubmitParamV3.ORDER_FROM_M);//订单来源
        orderSubmitParam.setOrderType(Integer.parseInt(Constants.SHOP_TYPE_SHANGPIN));//订单类型为尚品
        orderSubmitParam.setListSkuDetail(settle_old.getListSkuDetail());
        orderSubmitParam.setCartDetailIds(settle_old.getBuyIds());
        boolean validate = orderSubmitParam.validate();//校验参数
        //调用主站订单结算
        if(validate) {
            Map<String, String> cookieMap = UserUtil.getThridCookie(request);
            OrderSubmitParamV3.ChannelInfoBean channelInfoBean = new OrderSubmitParamV3.ChannelInfoBean();
            //增加推广链接的参数
            // channelNo 参数与网盟的source对应
            // channelId 参数与网盟的campaign对应
            if (cookieMap != null) {
                channelInfoBean.setChanelNo(cookieMap.get(Constants.THRID_COOKIE_SOURCE));
                channelInfoBean.setChanelId(cookieMap.get(Constants.THRID_COOKIE_CAMPAIGN));
                int channelType = 0;
                if(StringUtil.isNotEmpty(cookieMap.get(Constants.THRID_COOKIE_CHANNEL_TYPE))){
                    channelType = Integer.parseInt(cookieMap.get(Constants.THRID_COOKIE_CHANNEL_TYPE));
                }
                channelInfoBean.setChannelType(channelType);
                channelInfoBean.setParam(cookieMap.get(Constants.THRID_COOKIE_PARAM));
            } else {
                channelInfoBean.setChanelId("");
                channelInfoBean.setChanelNo("");
                channelInfoBean.setChannelType(0);
                channelInfoBean.setParam("");
            }
            orderSubmitParam.setChannelInfo(channelInfoBean);

            log.info("order submit param {}",orderSubmitParam);
            ResultObjOne<OrderSubmitResultV3> result = bizSettlementService.submitV3(orderSubmitParam);
            log.info("order submit result: {}",result);
            if(result!=null){
                return result;
            }
        }
        ResultObjOne<OrderSubmitResultV3> result = new ResultObjOne<>();
        result.setCode("-1");
        result.setMsg("param error");
        return result;
    }

    /**
     * 数据刷新修改session里面的对象
     * @param settle_change
     * @param request
     * @return
     */
    private SettlementV3 settleConvert(SettlementV3 settle_change,HttpServletRequest request){

        SettlementV3 settle_old = (SettlementV3)request.getSession().getAttribute("settle");
        if(settle_change.getReceived()!=null ){
            settle_old.setReceived(settle_change.getReceived());
        }
        if(settle_change.getProduct()!=null){
            settle_old.setProduct(settle_change.getProduct());
        }
        if(settle_change.getCoupon()!=null ){
            settle_old.setCoupon(settle_change.getCoupon());
        }
        if(settle_change.getInvoice()!=null ){
            settle_old.setInvoice(settle_change.getInvoice());
        }
        if(settle_change.getGiftCard()!=null){
            settle_old.setGiftCard(settle_change.getGiftCard());
        }
        if(settle_change.getPriceShow()!=null ){
            settle_old.setPriceShow(settle_change.getPriceShow());
        }
        if(settle_change.getIsProductCod()!=null){
            settle_old.setIsProductCod(settle_change.getIsProductCod());
        }
        if(settle_change.getPayCategory()!=null){
            settle_old.setPayCategory(settle_change.getPayCategory());
        }
        if(settle_change.getIsNeedCardId()!=null){
            settle_old.setIsNeedCardId(settle_change.getIsNeedCardId());
        }
        if(settle_change.getBuyIds()!=null){
            settle_old.setBuyIds(settle_change.getBuyIds());
        }
        if(settle_change.getListSkuDetail()!=null){
            settle_old.setListSkuDetail(settle_change.getListSkuDetail());
        }
        request.getSession().setAttribute("settle",settle_old);
        return settle_old;
    }

    /**
     * 提交订单页点击可选优惠券列表
     * @param buyId 购买ID
     * @param pageIndex 页码
     * @param pageSize 大小
     * @param couponNo 本次已选中的优惠券编号，可选
     * @param request
     * @return
     */
    @RequestMapping(value = "/couponsV3", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> canUseCoupons(String buyId, String pageIndex, String pageSize, String couponNo, HttpServletRequest request){

        String userId = getUserId(request);
        List<String> buyids = Arrays.asList(buyId.split("\\|"));
        //couponNo 如果没有传0
        ResultObjOne<CouponsV3> couponsResult = bizSettlementService.couponsV3(userId, buyids, couponNo, pageIndex, pageSize);
        Map<String,Object> map = new HashMap<>();
        if (couponsResult.getCode().equals(ResultObjOne.SUCCESS)){
            map.put("coupons",couponsResult.getObj());
        }else{
            map.put("error","inter face error");
        }
        return map;
    }

    /**
     *
     * @Title: orderAddress
     * @Description: 原来方式不能获取是否可用所以先添加再获取
     * @param
     * @throws
     * @Create By 刘少卿
     * @return String
     * @Create
     */
    @RequestMapping(value = "/address/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAddress(Address address,String buyId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = getSessionUser(request);
        List<Address> addresses = bizAddressService.addAddr(address, user.getUserId());
        if (null != addresses) {
            SettlementV3 settle_old = (SettlementV3)request.getSession().getAttribute(session_key);
            //获取可送达接口地址 // 个人中心0 购物车为1 立即购买为2
            String orderSource= "1";//个人中心为0， 购物车为1， 立即购买为2 默认为0
            String buytype = "0";// 0商品  ，1 电子卡 ，2 实物卡  默认为0
            String receiveId = "";//已选中Id
            String isHasOutSidePro = settle_old.getIsNeedCardId();//是否包含海外商品1是  0否
            List<Receive> addresses_result = bizSettlementService.accessAddressV3(user.getUserId(),settle_old.getBuyIds(),orderSource,buytype,receiveId,isHasOutSidePro);
            //解密身份证号
            setCardId(addresses_result);
            map.put("code", Constants.SUCCESS);
            map.put("addresses", addresses_result);
        } else {
            map.put("code", Constants.FAILE);
        }
        return map;
    }

    /**
     * 获取支付方式
     * @param cartUnion
     * @param request
     * @return
     */
    private List<com.shangpin.web.vo.Payment> getPayments(SettlementV3 cartUnion,String addIsCod,HttpServletRequest request){
        
        //支持线下支付方式 (货到付款) 1是 2否
        String isCod = cartUnion.getIsProductCod();

        //商品支持货到付款和地址支持货到付款同时判断

        if(!addIsCod.equals("")){
            isCod = addIsCod.equals("1") && isCod.equals("1")? "1" : "0";
        }else{

            if(cartUnion.getReceived().getCurrentAddress()!=null){
                String isd = cartUnion.getReceived().getCurrentAddress().getCod();
                isCod = isd.equals("1") && isCod.equals("1")? "1" : "0";
            }
        }

        //线上支付方式: 1:国内，2：国外，3：支付宝分帐
        String payCategory = cartUnion.getPayCategory();
        // 检测用户s是否来自微信
        String useragent = request.getHeader("User-Agent");
        
        return PaymentEnum.getPayments(isCod,payCategory,useragent);
    }

    /**
     * 获取解密后的身份证号码
     * @param cartUnion
     * @return
     */
    private String getCardId(SettlementV3 cartUnion){

        String result = "";
        if(cartUnion.getReceived()!=null && cartUnion.getReceived().getCurrentAddress()!=null){
            if(cartUnion.getReceived().getCurrentAddress().getCardID()!=null){
                result = cartUnion.getReceived().getCurrentAddress().getCardID();
                if(!"".equals(result)){
                    try {
                       // result = AESUtil.decrypt(result, AESUtil.IDCARD_KEY);
                        if(IDCard.IDCardValidate(result)){
                            result = result.substring(0,6)+"***"+result.substring(result.length()-4,result.length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 设置解密身份证号码
     * @param addresses
     */
    private void setCardId(List<Receive> addresses){

        if(addresses!=null){
            for (Receive address : addresses) {
                if(address.getCardID()!=null && !"".equals(address.getCardID())){
                    String temp= address.getCardID();
                    try {
                        if(IDCard.IDCardValidate(temp)){
                            temp = temp.substring(0,6)+"***"+temp.substring(temp.length()-4,temp.length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    address.setCardID(temp);
                }
            }
        }

    }

}
