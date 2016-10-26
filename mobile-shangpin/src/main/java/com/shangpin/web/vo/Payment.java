package com.shangpin.web.vo;

import com.shangpin.biz.bo.PayType;
import com.shangpin.web.enums.PaymentEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端页面支付方式封装
 * @author liushaoqing
 */
@ToString
@Getter
@Setter
public class Payment extends com.shangpin.biz.bo.orderUnion.Payment {

    /**
     *
     */
    private static final long serialVersionUID = 2173434535744160695L;


    private String gateway;

    private String clazz;//添加各个支付方式的样式名

    private String clazz2;

    private String url;

    private String way;

    private String bankId;

    private String payType;

    public static Map<String,Object> getPayments(PayType selectPayment, String payCategory, String isCod, String useragent){

        List<Payment> payments = PaymentEnum.getPayments(isCod, payCategory, useragent);
        Payment defaultP = null;
        //先判断是否包含这个支付方式
        boolean isHasPay = false;
        for (Payment payment : payments) {
            if(payment.getId().equals(selectPayment.getMainPayCode()) && payment.getSubpayCode().equals(selectPayment.getSubPayCode())){
                isHasPay = true;
            }
        }
        if(isHasPay){
            for (Payment payment : payments) {
                if(payment.getId().equals(selectPayment.getMainPayCode()) && payment.getSubpayCode().equals(selectPayment.getSubPayCode())){
                    defaultP = payment;
                    payment.setIsSelected("1");
                }else{
                    payment.setIsSelected("0");
                }
            }
        }else{
            for (Payment payment : payments) {
                if(payment.getIsSelected().equals("1")){
                    defaultP = payment;
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",payments);
        map.put("default",defaultP);

        return map;
    }

    /**
     * 通过一个已经给定的支付方式后去支付方式
     * @param subPayId
     * @param payCategory
     * @param isCod
     * @param useragent
     * @return
     */
    public static List<Payment> getPayments(String subPayId, String isCod, String payCategory, String useragent){

        List<Payment> payments = PaymentEnum.getPayments(isCod, payCategory, useragent);
        boolean isHasPay = false;
        for (Payment payment : payments) {
            if(payment.getSubpayCode().equals(subPayId)){
                isHasPay = true;
            }
        }
        if(isHasPay){
            for (Payment payment : payments) {
                if(payment.getSubpayCode().equals(subPayId)){
                    payment.setIsSelected("1");
                }else{
                    payment.setIsSelected("0");
                }
            }
        }else{
            return payments;
        }
        return payments;
    }

    /**
     * 获取可用支付方式
     * //支持线下支付方式 (货到付款) 1是 2否
     * //线上支付方式: 1:国内，2：国外，3：支付宝分帐
     * @return
     */
    public static List<Payment> getPayments(String isCod,String payCategory,String useragent){

        return PaymentEnum.getPayments(isCod,payCategory,useragent);
    }


}
