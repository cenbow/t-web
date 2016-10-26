package com.shangpin.biz.service.user.right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.biz.api.dto.user.base.tmpl.UserMapDTO;
import com.shangpin.biz.utils.RestEntityUtil;
import com.shangpin.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;


/** 
 * 用户权益相关的服务<br/>
 * @date    2016年8月11日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Slf4j
@Service
public class UserRightService {

	@Autowired
	RestTemplate rest;
	 
	/**
	 * 更新用户权益vip等级
	 * <br/>
	 * @param userId 用户id
	 * @param orderNo 订单编号
	 * @return
	 */
	public boolean updateUserLevel(String userId,String orderNo){
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("userId", userId);
		param.add("orderNo", orderNo);
		param.add("plat", GlobalConstants.MOBILE_PLAT);
		param.add("version", GlobalConstants.MOBILE_VERSION);
		try{
			UserMapDTO umap=rest.postForObject(RightApiConfig.rightUpdate, 
					RestEntityUtil.urlFormEntity(param), UserMapDTO.class);			
			log.info("修改用户{}权益返回参数：{}",userId,JsonUtil.toJson(umap));
			return umap.isSuccess();
		}catch(Exception e){
			log.error("更新用户{}订单{}权益为vip：{}",userId,orderNo,e);
		}
		return false;
	}
	
}
