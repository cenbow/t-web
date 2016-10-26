package com.shangpin.biz.bo.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @auther liushaoqing
 * @date 2016/8/18
 * @Description 未支付订单的订单信息
 * 主要包括各种价格,费用，分账等信息，用于支付
 */
@Getter
@Setter
@ToString
public class OrderPriceInfo {

      private String  canGiftCardPay;//": "1",
      private String  orderTime;//": "2016/08/08 16:47:08",
      private String  freight;//": "10",
      private String  isSupportCod;//": "1",
      private String  mainOrderNo;//": "20160616445334",
      private String  amount;
      private String  onlineAmount;//": "300",
      private String  payAmount;//: "100",
      private String  discountAmount;//": "200",
      private String  giftCardAmount;//": "10",
      private String  payTypeChildId;//": "31223434",
      private String  internalAmount;//": "200",
      private String  canPay;
      private String  pic;//": "1231231254277"
      private String  payCategory;//": "1",
      private String  isUseGiftcardPay;
      private String  receiveId;
      private String  sysTime;
      private String  expireTime;
      private String  customsPrice;
      private String  abroadFreight;
      private String  abroadNakedPrice;
      private String  giftCardBalance;
      private String  canUseGiftAmount;
      private String  promotionAmount;
      private String  isOnsiteDiscount;

}
