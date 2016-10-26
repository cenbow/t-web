package com.shangpin.biz.api.dto.user.base.tmpl;
import com.shangpin.biz.api.dto.user.base.UserBaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** 
 * 返回数据content是string类型的数据<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
public class UserStringDTO extends UserBaseDTO<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1577056497255127065L;

}
