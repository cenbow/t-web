package com.shangpin.biz.api.dto.user.base.tmpl;
import java.util.Map;

import com.shangpin.biz.api.dto.user.base.UserBaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** 
 * 返回数据content是Map<String,String>类型的数据<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
public class UserMapDTO extends UserBaseDTO<Map<String,String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2244716987253905470L;

	public UserMapDTO(Map<String,String> map){
		setContent(map);
	}
}
