package com.shangpin.biz.api.dto.user.base.tmpl;
import com.shangpin.biz.api.dto.user.base.UserBaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** 
 * 返回数据content是List类型的数据<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
public class UserListDTO<T> extends UserBaseDTO<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
