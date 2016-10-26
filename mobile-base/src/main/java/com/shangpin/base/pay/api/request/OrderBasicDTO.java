package com.shangpin.base.pay.api.request;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.base.pay.Gateway;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 
 * 支付接口请求参数<br/>
 * 必带：orderNo,subject,totalFee,gateway,customerIp<br/>
 * 按需：openId，在gateway为weixinpub,weixinpubsea支付的时候必带<br/>
 * 可选：returnUrl,timeOut,notifyUrl
 * @see Gateway
 * @date    2016年8月9日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class OrderBasicDTO implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4010222852112611227L;
	static final BigDecimal b001 = new BigDecimal(0.01);
	protected static DecimalFormat df = new DecimalFormat("0.00");
	/**
     * 订单号 最长32位
     */
    private String orderNo;

    /**
     * 交易名称，显示于第三方平台支付页面，如，“18K金项链等2件”，小于256字
     */
    private String subject;
    /**
     * 订单交易金额
     */
    private BigDecimal totalFee;
    
    /**
     * 微信支付中用户在商户appid下的唯一标识下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
     * <br/>企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换<br/>
     * 微信里面支付必须传
     */
    private String openId;
    /**
	 * 交易超时时间 分钟为单位*/
	private Integer timeOut;
	/**
     * 支付成功以后的跳转地址, 可为空<br/>
     * 该地址是支付企业（支付宝，微信）页面通知的地址，可以不填
     */
    private String returnUrl;
    /**
     * 该url为支付平台通知支付结果回调url
     * 若非必要不需要传，否则自己根据支付平台的通知结果进行处理<br/>
     */
    private String notifyUrl;
    /**
     * 校验数据是否正确 
     * <br/>
     * @return
     */
	public boolean validate(){
    	if(StringUtils.isBlank(orderNo)
    			||StringUtils.isBlank(subject)    			
    			||totalFee==null
    			||totalFee.compareTo(b001)<0
    			||getGateway()==null
    			){
    		return false;
    	}
    	//(Gateway.WEIXINPUBSEA.equals(getGateway())
    	if(Gateway.WEIXINPUB.equals(getGateway())
    			&& StringUtils.isBlank(openId)
    			){
    		return false;
    	}
    	return true;
    }
	/**
	 * 针对海外需要报关的商品设置：商品费用、关税、运费、国内分账费用 
	 * <br/>请子类实现<br/>
	 * 商品费用、关税、运费要么一起有，要么都没有；
	 * 分账金额可有可无<br/>
	 * @param goodsFee 裸价
	 * @param duty 关税
	 * @param transportFee 运费
	 * @param innerAmount 分账到国内的金额 nullable
	 */
	public void setCustomsFee(BigDecimal goodsFee,
			BigDecimal duty,BigDecimal transportFee,BigDecimal innerAmount){
		String fees=null;
		if(goodsFee!=null && duty!=null && transportFee!=null){
			fees="duty:"+df.format(duty)+",goodsFee:"+df.format(goodsFee)+",transportFee:"+df.format(transportFee);
		}
		if(innerAmount!=null){
			if(fees!=null) fees+=","; 
			fees+="splitAmount:"+df.format(innerAmount);
		}
		setExts(fees);
	}
	/**
	 * 支付网关 
	 * <br/>
	 * @return
	 */
	public abstract Gateway getGateway();
	/**
	 * 设置扩展信息 
	 * <br/>
	 * @param ext
	 */
	protected abstract void setExts(String ext);

}
