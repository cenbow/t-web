package com.shangpin.biz.service.impl;

import com.shangpin.base.service.AddressService;
import com.shangpin.base.service.CouponsService;
import com.shangpin.base.service.CustomerService;
import com.shangpin.base.service.SettlementService;
import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.biz.bo.Receive;
import com.shangpin.biz.bo.base.ResultObjList;
import com.shangpin.biz.bo.base.ResultObjMapList;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.orderUnion.*;
import com.shangpin.biz.domain.settlement.BuyNowDo;
import com.shangpin.biz.domain.settlement.RefreshDo;
import com.shangpin.biz.service.SPBizSettlementService;
import com.shangpin.biz.service.abstraction.AbstractBizSettlementService;
import com.shangpin.biz.utils.ObjectUtil;
import com.shangpin.biz.utils.StringUtil;
import com.shangpin.utils.HttpClientUtil;
import com.shangpin.utils.JSONUtils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liushaoqing
 * 结算页，结算，立即购买，刷新结算，订单提交，业务逻辑封装
 */
@Service
public class SpbizSettlementServiceImpl extends AbstractBizSettlementService implements SPBizSettlementService{

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    private StringBuilder submitOrderURL = new StringBuilder(GlobalConstants.BASE_URL_TRADE).append("order/ConfirmOrderV2");

    @Override
    public ResultObjOne<SettlementV3> settleV3(String userId, List<String> buyIds) {

        JSONObject reqObj = new JSONObject();
        reqObj.put("userId",userId);
        reqObj.put("buyIds",buyIds);
        reqObj.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
        reqObj.put("versionNo",GlobalConstants.ORDER_VERSION);
        String s = settlementService.settleV3(reqObj.toString());
        ResultObjOne<SettlementV3> obj = null;
        try {
            obj = JSONUtils.toGenericsCollection(s, ResultObjOne.class, SettlementV3.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public ResultObjOne<SettlementV3> refreshSettleV3(SettleRefreshV3 freshVo) {

        ResultObjOne<SettlementV3> obj = null;
        try {
            freshVo.setRequestFrom(GlobalConstants.ORDER_REQUEST_FRMO);
            freshVo.setVersionNo(GlobalConstants.ORDER_VERSION);
            String json = JSONUtils.obj2json(freshVo);
            String s = settlementService.settleRefreshV3(json);
            obj = JSONUtils.toGenericsCollection(s, ResultObjOne.class, SettlementV3.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public ResultObjOne<CouponsV3> couponsV3(String userId, List<String> buyId, String couponNo, String pageIndex, String pageSize) {
        JSONObject obj = new JSONObject();
        obj.element("userId",userId).element("buyIds",buyId).element("selectedCouponNo",couponNo)
                .element("pageIndex",pageIndex).element("pageSize",pageSize);
        obj.put("versionNo",GlobalConstants.ORDER_VERSION);
        obj.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
        ResultObjOne<CouponsV3> result= null;
        try {
            String s = couponsService.canUseCouponsV3(obj.toString());
            result = JSONUtils.toGenericsCollection(s, ResultObjOne.class, CouponsV3.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 购物车结算
     * @param userid             用户id
     * @param buyId              购物车id
     * @param isDefaultUseCoupon 1使用优惠券，0 不使用
     * @return
     */
    @Override
    public SettlementUnion cartSettlement(String userid, String buyId, String isDefaultUseCoupon) {
        try {
            ResultObjOne<SettlementUnion> obj= this.doCartSettlement(userid, buyId, isDefaultUseCoupon);
            if (obj != null && obj.isSuccess()) {
                return obj.getObj();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 立即购买结算
     *
     * @param buyNowDo BuyNowDo
     * @return
     */
    @Override
    public SettlementUnion buyNowSettlement(BuyNowDo buyNowDo) {

        try{
            ResultObjOne<SettlementUnion> obj = this.doBuyNowSettlement(buyNowDo);
            if (obj != null && obj.isSuccess()) {
                return obj.getObj();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 刷新结算页
     *
     * @param refreshDo@return
     */
    @Override
    public Map<String,Object> refreshSettlement(RefreshDo refreshDo) {

        Map<String,Object> map = new HashMap<>();
        try{
            ResultObjOne<SettlementUnion> obj = this.doRefreshSettlement(refreshDo);
            if (obj != null && obj.isSuccess()) {
                map.put("cartUnion",obj.getContent());
            }
                map.put("code",obj.getCode());
            map.put("msg",obj.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public CouponsWraper accessCoupons(String userId, String pageIndex, String pageSize, String buyId, String orderSource, String couponNo, String useCouponNo) {

        String json = couponsService.canUseCoupons(userId, pageIndex, pageSize, buyId, orderSource, couponNo, useCouponNo);
        logger.debug("调用base接口返回数据:" + json);
        if(!StringUtil.isNotEmpty(json)){
            return null;
        }else{
            ResultObjOne<CouponsWraper> obj = null;
            try {
                obj = JSONUtils.toGenericsCollection(json, ResultObjOne.class, CouponsWraper.class);
                if (obj != null && obj.isSuccess()) {
                    return obj.getObj();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public List<Receive> getAccessAddress(String userId, String buyId, String receiveId,String orderSourceId,String buyType) {

        String json = super.doaddresslist(userId,buyId,receiveId,orderSourceId,buyType);
        logger.debug("调用base接口返回数据:" + json);
        if(!StringUtil.isNotEmpty(json)){
            return null;
        }
        ResultObjMapList<Receive> obj = null;
        try {
            obj = JSONUtils.toGenericsCollection(json, ResultObjMapList.class,Receive.class);
            if (obj != null && obj.isSuccess()) {
                return obj.getList("list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String,Object> submitOrder(OrderSubmitParam orderSubmitParam) {

        Map<String,String> map_pa = new HashMap<>();
        ObjectUtil.beanToMap(orderSubmitParam, map_pa);
        String result = "";
        try {
            result = HttpClientUtil.doPost(submitOrderURL.toString(), map_pa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(result)){
            return null;
        }
        logger.debug("调用base接口返回数据:" + result);
        Map<String,Object> map = new HashMap<>();
        ResultObjOne<OrderSubmitResult> obj = null;
        try {
            obj = JSONUtils.toGenericsCollection(result, ResultObjOne.class, OrderSubmitResult.class);
            if (obj!=null && obj.isSuccess()) {
                map.put("code", "0");
                map.put("orderInfo", obj.getObj());
                return map;
            }else {
                map.put("code", obj.getCode());
                map.put("msg", obj.getMsg());
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Receive> accessAddressV3(String userId, List<String> buyids, String orderSource, String buyType, String receiveId, String isHasOutSidePro) {

        JSONObject queJson = new JSONObject();
        queJson.put("userId",userId);
        queJson.put("buyids",buyids);
        queJson.put("receiveId",receiveId);
        queJson.put("buytype",buyType);
        queJson.put("ordersourceId",orderSource);
        queJson.put("isHasOutSidePro",isHasOutSidePro);
        queJson.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
        queJson.put("versionNo",GlobalConstants.ORDER_VERSION);
        String s = addressService.accessAddress(queJson.toString());
        try {
            ResultObjMapList<Receive> list = JSONUtils.toGenericsCollection(s, ResultObjMapList.class, Receive.class);
            if(list.getCode().equals(ResultObjList.SUCCESS) && list.getList("list")!=null){
                return list.getList("list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public ResultObjOne<OrderSubmitResultV3> submitV3(OrderSubmitParamV3 orderSubmitParam) {

        try {
            orderSubmitParam.setVersionNo(GlobalConstants.ORDER_VERSION);
            String requestJson = JSONUtils.obj2json(orderSubmitParam);
            String result =  settlementService.submitV3(requestJson);
            ResultObjOne<OrderSubmitResultV3> submitResult = JSONUtils.toGenericsCollection(result, ResultObjOne.class, OrderSubmitResultV3.class);
           return  submitResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
