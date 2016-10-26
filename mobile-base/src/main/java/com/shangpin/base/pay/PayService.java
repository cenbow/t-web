package com.shangpin.base.pay;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.ValidationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.shangpin.base.config.PayAPIConfig;
import com.shangpin.base.pay.api.TradeResultDTO;
import com.shangpin.base.pay.api.request.OrderPayApiDTO;
import com.shangpin.base.pay.api.request.OrderPayWebDTO;
import com.shangpin.base.pay.api.response.PayParameterDTO;
import com.shangpin.base.utils.RestEntityUtil;
import com.shangpin.utils.JsonUtil;
import com.shangpin.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

/** 
 * 调用支付服务<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Service
@Slf4j
public class PayService {
	private static final String REFERER_SHANGPIN = "http://m.shangpin.com";
	@Autowired
	RestTemplate rest;
	/**
	 * 通知类型
	 */
	public static final String notifyType="__NOTIFY_TYPE";
	
	/**
	 * 通知类型值：前台通知
	 */
	public static final String frontNotify="__FRONT";
	
	/**
	 * 请求支付的api接口，访问后得到请求支付企业的参数， 
	 * <br/>然后进行页面跳转或者走post<br/>
	 * @param order
	 * @return 请求支付接口的参数，可
	 */
	public PayParameterDTO requestPayApi(OrderPayApiDTO order){
		if(!order.validate()){
			throw new ValidationException("订单数据校验有误");
		}
		return null;
	}
	
	/**
	 * 支付平台回调通知，判断是否正确 
	 * <br/>
	 * @param dto 回调通知参数
	 * @return true 参数无修改，false修改了
	 */
	public boolean verifyCallback(TradeResultDTO dto){
		String enstr=getEncryptStr(dto,PayAPIConfig.payCallBackKey);
		enstr=DigestUtils.md5Hex(enstr);
		return enstr.equalsIgnoreCase(dto.getAckMsg());
	}
	
	/**
	 * 支付页面通知解释 
	 * <br/>将前台通知的queryString解释成key-value对的map<br/>
	 * 然后调用支付平台的接口解释支付结果，前端页面根据结果展示不同的页面
	 * @param returnParam 页面参数
	 */
	public TradeResultDTO parsePayReturn(Map<String,String> returnParam,Gateway gateway){
		if(returnParam==null||returnParam.size()<=0){
			log.warn("{}通知结果参数为空！",gateway);
			return null;
		}
		returnParam.put(notifyType,frontNotify);
		log.info("{}支付通知参数:{}",gateway,JsonUtil.toJson(returnParam));
		TradeResultDTO dto =  payNotify(returnParam, gateway);
		return dto;
	}
	
	
	/**
	 * 支付订单查询 
	 * <br/>
	 * @param orderNo
	 */
	public TradeResultDTO payQuery(String orderNo,Gateway gateway){
		TradeResultDTO dto=null;
		try{
			String url = StringUtil.format(PayAPIConfig.payQueryUrl,orderNo);
			dto=rest.getForObject(url, TradeResultDTO.class);
			log.info("支付查询结果:{}",JsonUtil.toJson(dto));
		}catch(Exception e){
			dto = new TradeResultDTO();
			dto.setAckStatus("1");
			dto.setAckMsg(e.getMessage());
		}
		return dto;
	}
	/**
	 * 请求跳转到支付平台web界面的请求参数 
	 * <br/>
	 * @param order 请求支付的订单信息
	 * @return
	 */
	public Map<String,String> encrypt4PayWeb(OrderPayWebDTO order){
		return encrypt4PayWeb(order, PayAPIConfig.payRequestKey);
	}
	/**
	 * 请求跳转到支付平台web界面的请求参数 
	 * <br/>
	 * @param order 请求支付的订单信息
	 * @param signKey md5 加密的sign key
	 * @return
	 */
	public Map<String,String> encrypt4PayWeb(OrderPayWebDTO order,String signKey){
		if(!order.validate()){
			throw new ValidationException("订单数据校验有误");
		}
		String sign=DigestUtils.md5Hex(order.sortedValue()+signKey);
		Map<String, String> params = order.requestMap();
		params.put("sign",sign);
		return params;
	}
	
	/**
	 * 请求跳转到支付平台返回微信公众号支付的参数 ,
	 * <br/>openId必须有
	 * @param order 请求支付的订单信息
	 * @return 请求微信公众号支付的参数,前端使用这些参数调起微信既可
	 */
	public Map<String,String> encrypt4WeiXinPub(OrderPayWebDTO order){

		return encrypt4WeiXinPub(order, PayAPIConfig.payRequestKey);
	}
	/**
	 * 请求跳转到支付平台返回微信公众号支付的参数 ,
	 * <br/>openId必须有
	 * @param order 请求支付的订单信息
	 * @param signKey md5 加密的sign key
	 * @return 请求微信公众号支付的参数,前端使用这些参数调起微信既可
	 */
	public Map<String,String> encrypt4WeiXinPub(OrderPayWebDTO order,String signKey){
		if(!order.getGateWay().isWeiXinPub()){
			throw new RuntimeException("请求微信公众号网关错误");
		}
		Map<String,String> params=encrypt4PayWeb(order,signKey);
		MultiValueMap<String, String> header=new LinkedMultiValueMap<String, String>(1);
		header.add("Referer", REFERER_SHANGPIN);
		HttpEntity<MultiValueMap<String, String>> requestBody = RestEntityUtil.urlFormEntity(params,header);
		log.info("微信公众号支付请求数据：{}",params);
		String data = rest.postForObject(PayAPIConfig.payWebApi,requestBody,String.class);
		PayParameterDTO result= JsonUtil.fromJson(data,PayParameterDTO.class);
		log.info("微信公众号支付请求返回数据：{}",result);
		if(!result.ackSuccess()){
			log.error("微信支付请求有误：{}",result.getAckMsg());
		}
		return result.getRequestParams();
	}
	
	protected TradeResultDTO payNotify(Map<String, String> notifyParam, Gateway gateway) {
		TradeResultDTO dto=null;
		try {
			String url = StringUtil.format(PayAPIConfig.payNotifyParseUrl, gateway.name());
			dto = rest.postForEntity(url, notifyParam, TradeResultDTO.class).getBody();
			log.info("{}支付通知解释结果:{}",gateway,JsonUtil.toJson(dto));
		} catch (RestClientException e) {
			log.error("{}通知处理调用出错：{}",gateway,e.getMessage());
		}
		return dto;
	}
	/**
	 * 有序的加密对比数据 
	 * <br/>
	 * @param tradeRecord 支付平台回调支付结果
	 * @param key 加密key
	 * @return
	 */
	private String getEncryptStr(TradeResultDTO tradeRecord,String key) {
		StringBuffer sb = new StringBuffer();
		sb.append(tradeRecord.getGateway()).append("|");
		sb.append(tradeRecord.getOrderNo()).append("|");
		sb.append(tradeRecord.getPayNo()).append("|");
		sb.append(tradeRecord.getPayStatus()).append("|");
		sb.append(tradeRecord.getTotalFee().setScale(2, BigDecimal.ROUND_HALF_UP));
		sb.append(key);
		return sb.toString();
	}
}
