package com.shangpin.biz.service.abstraction;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.shangpin.base.service.CommonService;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.utils.Constants;
import com.shangpin.utils.JedisUtil;
import com.shangpin.utils.JsonUtil;


/**
 * 通用的服务，和业务没什么关系
 * @author qinyingchun
 *
 */
public class AbstractBizCommonHanderService {
	
	@Autowired
	private CommonService commonService;
	
	public boolean IsUseCombinePay(String member){
		return JedisUtil.getInstance().new Sets().sismember(Constants.IS_USE_COMBINE_PAY, member);
	}
	
	public Set<String> useCombinePay(){
		return JedisUtil.getInstance().new Sets().smembers(Constants.IS_USE_COMBINE_PAY);
	}
	
	public void add(String member){
		JedisUtil.getInstance().new Sets().sadd(Constants.IS_USE_COMBINE_PAY, member);
	}
	
	public void remove(String member){
		JedisUtil.getInstance().new Sets().srem(Constants.IS_USE_COMBINE_PAY, member);
	}
	
    public ResultBase sendMsgVerfyCode(String userId, String phoneNum, String type){
    	String json = commonService.sendMsgVerfyCode(userId, phoneNum, type);
    	if(StringUtils.isEmpty(json)){
    		return null;
    	}
    	ResultBase resultBase = JsonUtil.fromJson(json, ResultBase.class);
    	return resultBase;
    }

}
