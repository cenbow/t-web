package com.shangpin.base.pay.api;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shangpin.base.pay.Gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 订单状态查询DTO，支付平台回调通知结果
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeResultDTO extends PayApiBaseDTO{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 240869858373056161L;
	/**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付号
     */
    private String payNo;

    /**
     * 支付网关
     */
    private Gateway gateway;
    /**
     * 支付状态:SUCCESS,TRADEING,FAILED,
     */
    private String payStatus;
    /**
     * 银行交易时间
     * yyyyMMddHHmmss
     */
    private String tradeDate;
    /**
     * 交易创建时间
     * yyyyMMddHHmmss
     */
    private String createTime;
    /**
     * 银行交易流水
     */
    private String tradeNo;
    /**
     * 币种
    private CurrencyType currency;
     */

    /**
     * 总金额
     */
    private BigDecimal totalFee;

    /**
     * 银行列表
    private BankList secondLevel;
     */

    /**
     * 分期期数
     */
    private String thirdLevel;
    /**附加信息*/
    private String extend;
    /**
     * 是否支付成功 
     * <br/>
     * @return true成功，false 不成功
     */
    public boolean isPaySuccess(){
    	if(super.ackSuccess() && "SUCCESS".equals(payStatus)){
    		return true;
    	}
    	return false;
    }
}
