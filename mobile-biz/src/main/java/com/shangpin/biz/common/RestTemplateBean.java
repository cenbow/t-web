package com.shangpin.biz.common;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;


/** 
 * rest<br/>
 * @date    2016年8月10日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@Configuration
public class RestTemplateBean {
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate rest = new RestTemplate();
		HttpComponentsClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory();
		fac.setHttpClient(httpClient());
		rest.setRequestFactory(fac);
		rest.setMessageConverters(converters());
		return rest;
	}
	
	private List<HttpMessageConverter<?>> converters(){
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		StringHttpMessageConverter stringConvert = new StringHttpMessageConverter();
		MappingJackson2HttpMessageConverter jsonConvert=new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		jsonConvert.setObjectMapper(mapper);
		converters.add(stringConvert);
		converters.add(jsonConvert);
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new AllEncompassingFormHttpMessageConverter());
		return converters;
	}
	public HttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(300);
		connectionManager.setMaxTotal(1000);
		SocketConfig socketConfig = SocketConfig.custom()
				.setTcpNoDelay(true)
				.setSoKeepAlive(true).setSoTimeout(30000)
				.build();
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
		
		connectionManager.setDefaultConnectionConfig(connectionConfig);
		connectionManager.setDefaultSocketConfig(socketConfig);
		connectionManager.closeIdleConnections(30000, TimeUnit.SECONDS);
		return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
	}
}
