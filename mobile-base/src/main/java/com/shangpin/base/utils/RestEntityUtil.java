package com.shangpin.base.utils;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.Getter;
import lombok.Setter;


/** 
 * RestTemplate 调用的时候,content-type不一样<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class RestEntityUtil {
	/**
	 * content-type是 application/x-www-form-urlencoded的请求体
	 * <br/>
	 * @param param 请求参数
	 * @return
	 */
	public static HttpEntity<MultiValueMap<String,String>> urlFormEntity(MultiValueMap<String, String> param ){
		MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		header.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		HttpEntity<MultiValueMap<String,String>> he = new HttpEntity<>(param,header);
		return he;
	}
	/**
	 * content-type是 application/x-www-form-urlencoded的请求体
	 * <br/>
	 * @param param 请求参数
	 * @return
	 */
	public static HttpEntity<MultiValueMap<String,String>> urlFormEntity(Map<String, String> param,
			MultiValueMap<String, String> headers){
		Set<Entry<String,String>> kvSet = param.entrySet();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		for (Entry<String, String> entry : kvSet) {
			body.add(entry.getKey(), entry.getValue());
		}
		MultiValueMap<String, String> header1 = new LinkedMultiValueMap<>();		
		header1.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		if(headers!=null)
			header1.putAll(headers);
		HttpEntity<MultiValueMap<String,String>> he = new HttpEntity<>(body,header1);
		return he;
	}
	
}
