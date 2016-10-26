package com.shangpin.biz.service.user.right;
import com.shangpin.biz.utils.PropertyUtil;

import lombok.Getter;
import lombok.Setter;


/** 
 * 用户权益接口地址配置<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class RightApiConfig {

	public static String rightUpdate=PropertyUtil.getString("user.right.update");
	
}
