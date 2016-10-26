package com.shangpin.base.pay.api.response;
import java.util.Map;

import com.shangpin.base.pay.api.PayApiBaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/** 
 * 调用支付api接口返回的数据<br/>
 * @date    2016年8月9日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class PayParameterDTO extends PayApiBaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7036506024950878655L;
	public final static String GET="get"; 
	public final static String POST="post"; 
	
	/**
     * 支付网关地址
     */
    private String gatewayUrl;

    /**
     * 支付需要的参数
     */
    private Map<String,String> requestParams;
    /**app支付时可能用到的json串，实际可以放在requestparams里面的
    private String jsonStr;
     * */
    private String method=GET;
}
