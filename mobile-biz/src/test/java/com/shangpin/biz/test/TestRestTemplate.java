package com.shangpin.biz.test;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.biz.api.dto.user.base.tmpl.UserMapDTO;
import com.shangpin.biz.common.RestTemplateBean;
import com.shangpin.biz.service.user.right.RightApiConfig;
import com.shangpin.biz.utils.RestEntityUtil;
import com.shangpin.utils.JsonUtil;

import lombok.Getter;
import lombok.Setter;


/** 
 * TODO 该类的作用<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class TestRestTemplate {
	RestTemplate rest ;
	String rightUrl="http://192.168.20.98:8080/api/user/updateUserLevel";
	@Before
	public void init(){
		RestTemplateBean b = new RestTemplateBean();
		rest=b.restTemplate();
	}
	@Test
	public void restTemplate() {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("userId", "2833d7dbe1661392ae029917324449e1");
		param.add("orderNo", "20160811194453453972");
		param.add("plat", GlobalConstants.MOBILE_PLAT);
		param.add("version", GlobalConstants.MOBILE_VERSION);
		UserMapDTO umap=rest.postForObject(RightApiConfig.rightUpdate,
				RestEntityUtil.urlFormEntity(param), UserMapDTO.class);
		System.out.println(JsonUtil.toJson(umap));
	}
	@Test
	public void restTemplateHeader() {
		Map<String, String> param = new HashMap<>();
		param.put("userId", "2833d7dbe1661392ae029917324449e1");
		param.put("orderNo", "20160811194453453972");
		param.put("plat", GlobalConstants.MOBILE_PLAT);
		param.put("version", GlobalConstants.MOBILE_VERSION);
		MultiValueMap<String, String> header=new LinkedMultiValueMap<String, String>();
		header.add("Referer", "aa");
		HttpEntity<MultiValueMap<String, String>> urlFormEntity = RestEntityUtil.urlFormEntity(param,header);
		UserMapDTO umap=rest.postForObject(RightApiConfig.rightUpdate,
				urlFormEntity, UserMapDTO.class);
		System.out.println(JsonUtil.toJson(umap));
	}
	
}
