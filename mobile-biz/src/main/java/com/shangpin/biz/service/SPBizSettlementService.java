package com.shangpin.biz.service;

import com.shangpin.biz.bo.Receive;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.orderUnion.*;
import com.shangpin.biz.domain.settlement.BuyNowDo;
import com.shangpin.biz.domain.settlement.RefreshDo;

import java.util.List;
import java.util.Map;

/**
 * 结算页面<br/>
 * Date: 2016/4/22<br/>
 *
 * @author liushaoqing
 * @since JDK7
 * 结算页面立即购买，结算，刷新结算数据，订单提交接口
 */
public interface SPBizSettlementService {


    /**
     * 结算V3
     * @param userId 用户id
     * @param buyIds 购物车id
     * @date 2016-08-02
     */
    ResultObjOne<SettlementV3> settleV3(String userId, List<String> buyIds);

    /**
     * 结算页刷新V3
     * @param freshVo 结算实体
     * @date 2016-08-02
     */
    ResultObjOne<SettlementV3> refreshSettleV3(SettleRefreshV3 freshVo);

    /**
     * 可用优惠券V3
     * @param userId
     * @param buyId
     * @param couponNo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ResultObjOne<CouponsV3> couponsV3(String userId, List<String> buyId, String couponNo, String pageIndex, String pageSize);

    /**
     * 结算
     * @param userid 用户id
     * @param buyId 购物车id
     * @param isDefaultUseCoupon 1使用优惠券，0 不使用
     * @return
     */
    SettlementUnion cartSettlement(String userid, String buyId, String isDefaultUseCoupon);

    /**
     * 立即购买结算
     * @param buyNowDo BuyNowDo
     * @return
     */
    SettlementUnion buyNowSettlement(BuyNowDo buyNowDo);

    /**
     * 刷新结算页
     * @param refreshDo RefreshDo
     * @return
     */
    Map<String,Object> refreshSettlement(RefreshDo refreshDo);

    /**
     * 可用优惠券
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @param buyId
     * @param orderSource
     * @param couponNo
     * @param useCouponNo
     * @return
     */
    CouponsWraper accessCoupons(String userId, String pageIndex, String pageSize, String buyId, String orderSource, String couponNo, String useCouponNo);

    /**
     * 可用送货地址
     * @param userId
     * @param shopCartDetailIds
     * @param s
     * @return
     */
    List<Receive> getAccessAddress(String userId, String shopCartDetailIds, String s,String orderSourceId,String buyType);

    /**
     * 结算订单提交
     * @param orderSubmitParam
     */
    Map<String,Object> submitOrder(OrderSubmitParam orderSubmitParam);

    /**
     *
     * @param userId
     * @param buyids
     * @param orderSource
     * @param buytype
     * @param receiveId
     * @param isHasOutSidePro
     * @return
     */
    List<Receive> accessAddressV3(String userId, List<String> buyids, String orderSource, String buytype, String receiveId, String isHasOutSidePro);

    /**
     * 提交订单V3
     * @param orderSubmitParam
     * @return
     */
    ResultObjOne<OrderSubmitResultV3> submitV3(OrderSubmitParamV3 orderSubmitParam);
}
