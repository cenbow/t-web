package com.shangpin.web.enums;

import com.shangpin.biz.utils.ClientUtil;
import com.shangpin.web.vo.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  liushaoqing
 * M 站支付方式枚举汇总
 */
public enum PaymentEnum {

    //国内
    //微信app支付
    WEIXINPUB ("27", "58","21","微信支付", PaymentEnum.DOMESTIC_PAY,
            PaymentEnum.MWX_CHANNEL, PaymentEnum.WEY_ONLION,"wechat","weixinPay","/order/pay/WEIXINPUB"),
    //微信app 调微信海外
    WEIXINSEAWAP ("32", "111","21", "微信支付", PaymentEnum.ABOROAD_PAY,
            PaymentEnum.MWX_CHANNEL, PaymentEnum.WEY_ONLION,"wechat","weixinPay","/order/pay/WEIXINPUBSEA"),
    //微信wap页支付
    WEIXINWAP ("27", "117","21", "微信支付", PaymentEnum.DOMESTIC_PAY,
            PaymentEnum.M_CHANNEL, PaymentEnum.WEY_ONLION,"wechat","weixinPay","/order/pay/WEIXINWAP"),

    ALIWAP  ("20", "37", "20","支付宝支付", PaymentEnum.DOMESTIC_PAY,
            PaymentEnum.M_CHANNEL, PaymentEnum.WEY_ONLION,"zhifubao","alipay","/order/pay/ALIWAP"),
    //直接跳转到银联安装页面
    UNWAP    ("19", "49", "19","银联支付", PaymentEnum.DOMESTIC_PAY,
            PaymentEnum.M_CHANNEL, PaymentEnum.WEY_ONLION,"yinlian","unionPay","/order/pay/union_pay/install"),

    POS     ("2", "41","4" ,"货到付款POS机", PaymentEnum.DOMESTIC_PAY,
            PaymentEnum.M_CHANNEL,PaymentEnum.WEY_OUTLION,"pos","postPay","/order/pay/hdpay/success"),

    CASH    ("2", "41","4", "货到付款现金", PaymentEnum.DOMESTIC_PAY,
            PaymentEnum.M_CHANNEL,PaymentEnum.WEY_OUTLION,"cash","cashPay","/order/pay/hdpay/success"),

    /*   PUFABK  ("2", "41", "浦发银行", PaymentEnum.DOMESTIC_PAY,
               PaymentEnum.M_CHANNEL,PaymentEnum.WEY_OUTLION,"cashPay","/pay/hdpay/success"),*/
    //108
    ALISEA  ("30", "121","20" ,"支付宝支付", PaymentEnum.ABOROAD_PAY,
            PaymentEnum.M_CHANNEL, PaymentEnum.WEY_ONLION,"zhifubao","alipay","/order/pay/ALIWAPSEA"),
    //分账
    ALIFZ  ("30", "121", "20","支付宝支付", PaymentEnum.ALIPART_PAY,
            PaymentEnum.M_CHANNEL, PaymentEnum.WEY_ONLION,"zhifubao","alipay","/order/pay/ALIFZWAP");

    public static final String DOMESTIC_PAY ="1";//国内支付
    public static final String ABOROAD_PAY = "2";//国外支付
    public static final String ALIPART_PAY = "3";//阿里分账
    public static final String M_CHANNEL="0"; //M站
    public static final String MWX_CHANNEL="1";// M站微信
    public static final String WEY_ONLION = "0";//线上支付
    public static final String WEY_OUTLION = "1";//线下支付

    public static final PaymentEnum DOMESTIC_DEFAULT = WEIXINWAP ;
    public static final PaymentEnum ABOROASD_DEFAULT = ALIFZ;
    public static final PaymentEnum ALIPAERT_DEFAULT = ALIFZ;
    public static final PaymentEnum WEIXIN_DEFAULT = WEIXINPUB;

    PaymentEnum(String id,String subId, String bankId, String name, String type, String channel, String wey,String clazz,String clazz2,String url) {
        this.id = id;
        this.subId = subId;
        this.bankId = bankId;
        this.name = name;
        this.type = type;
        this.channel = channel;
        this.wey = wey;
        this.clazz = clazz;
        this.clazz2 = clazz2;
        this.url = url;
    }

    //支付id
    final private String id;
    //子的支付id
    final private String subId;
    //银行id 修改支付方式的时候会用到
    final private String bankId;
    //支付名称
    final private String name;
    //1国内 2海外 3分账
    private String type;
    //渠道(m站，和微信浏览器m站)
    final private String channel;
    //1 线上支付 2线下支付
    final private String wey;

    final private String clazz;//图标样式

    final private String clazz2;//第二种样式

    final private String url;


    public static List<com.shangpin.web.vo.Payment> getPayments(String isCod, String payCategory, String useragent) {

        boolean iscod = isCod.equals("1");
        List<com.shangpin.web.vo.Payment> payments = null;
        if (ClientUtil.CheckMircro(useragent)) {
            payments = PaymentEnum.getPayment(payCategory, PaymentEnum.MWX_CHANNEL, iscod);
        }else{
            payments = PaymentEnum.getPayment(payCategory, PaymentEnum.M_CHANNEL, iscod);
        }
        return payments;
    }

    public static Payment getDefault(String payCategory, String useragent) {

        List<com.shangpin.web.vo.Payment> payments = null;
        PaymentEnum defaultPay = null;
        if (ClientUtil.CheckMircro(useragent)) {
            defaultPay = getDefaultSelectPay(payCategory, PaymentEnum.MWX_CHANNEL);
        }else{
            defaultPay = PaymentEnum.getDefaultSelectPay(payCategory, PaymentEnum.M_CHANNEL);
        }
        Payment pay = new Payment();
        pay.setUrl(defaultPay.url);
        pay.setClazz(defaultPay.clazz);
        pay.setId(defaultPay.id);
        pay.setSubpayCode(defaultPay.subId);
        pay.setName(defaultPay.name);
        pay.setWay(defaultPay.wey);
        return pay;
    }
    /**
     * 通过类型和渠道,是否支持线下方式获取可用的支付方式
     * @param type
     * @param channel
     * @param wey
     * @return
     */
    private static List<Payment> getPayment(String type, String channel, boolean wey){

        List<Payment> payments = new ArrayList<Payment>();

        //获取默认的方式
        PaymentEnum defaultSelectPay = getDefaultSelectPay(type, channel);
        List<PaymentEnum> payEnumList = getPayEnumList(type, channel, wey);

        for (PaymentEnum payenum: payEnumList) {

            Payment pay = new Payment();
            pay.setId(payenum.id);
            pay.setSubpayCode(payenum.subId);
            pay.setName(payenum.name);
            pay.setWay(payenum.wey);
            pay.setBankId(payenum.bankId);
            pay.setGateway(payenum.toString());
            pay.setPayType(payenum.bankId+"_"+payenum.id+"_"+payenum.subId);
            //设置默认选中
            if(defaultSelectPay==payenum) {
                pay.setIsSelected("1");
            }else{
                pay.setIsSelected("0");
            }
            pay.setClazz(payenum.clazz);
            pay.setClazz2(payenum.clazz2);
            pay.setUrl(payenum.url);
            payments.add(pay);
        }
        return payments;
    }

    private static List<PaymentEnum> getPayEnumList(String type,String channel,boolean wey){

        List<PaymentEnum> lists = new ArrayList<PaymentEnum>();
        if(channel.equals(PaymentEnum.MWX_CHANNEL)){
            if(type.equals(DOMESTIC_PAY)){
                lists.add(WEIXINPUB);
            }else if(type.equals(ABOROAD_PAY)){
                lists.add(WEIXINSEAWAP);
            }
            return lists;
        }

        if(type.equals(PaymentEnum.DOMESTIC_PAY)){
            lists.add(WEIXINWAP);
            lists.add(ALIWAP);
            lists.add(PaymentEnum.UNWAP);
            if(wey){
                lists.add(CASH);
                lists.add(POS);
            }
        }else if(type.equals(PaymentEnum.ABOROAD_PAY)){
            lists.add(ALIFZ);//ali wap sea已经去掉
            //lists.add(WEIXINSEAWAP);//微信海外wap站不自此
        }else if(type.equals(PaymentEnum.ALIPART_PAY)){
            lists.add(ALIFZ);
        }
        return lists;
    }

    /**
     * 通过类型和渠道获取默认的支付方式
     * @param type
     * @param channel
     * @return
     */
    private static PaymentEnum getDefaultSelectPay(String type,String channel){

        if(type.equals(ALIPART_PAY)){
            return PaymentEnum.ALIPAERT_DEFAULT;
        }else{
            if(channel.equals(MWX_CHANNEL)){
                if(type.equals(DOMESTIC_PAY)){
                    return PaymentEnum.WEIXINPUB;
                }else{
                    return PaymentEnum.WEIXINSEAWAP;
                }
            }else if(type.equals(DOMESTIC_PAY) ){
                return PaymentEnum.DOMESTIC_DEFAULT;
            }else if(type.equals(ABOROAD_PAY)){
                return PaymentEnum.ABOROASD_DEFAULT;
            }else{
                return null;
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getSubId() {
        return subId;
    }

    public String getBankId() {
        return bankId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public String getWey() {
        return wey;
    }

    public String getClazz() {
        return clazz;
    }

    public String getClazz2() {
        return clazz2;
    }

    public String getUrl() {
        return url;
    }
}
