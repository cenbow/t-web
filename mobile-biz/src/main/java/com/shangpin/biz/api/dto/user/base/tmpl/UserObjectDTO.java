package com.shangpin.biz.api.dto.user.base.tmpl;
import com.shangpin.biz.api.dto.user.base.UserBaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** 
 * 返回用户对象数据<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
public class UserObjectDTO<T> extends UserBaseDTO<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6822843270112622262L;

	public UserObjectDTO(T obj){
		setContent(obj);
	}
}
