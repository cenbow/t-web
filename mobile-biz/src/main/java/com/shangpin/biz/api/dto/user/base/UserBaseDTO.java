package com.shangpin.biz.api.dto.user.base;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/** 
 * 用户系统接口的返回参数基础类<br/>
 * @date    2016年8月12日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public abstract class UserBaseDTO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7558024759968699697L;
	private String code;
	private String msg;
	public T content;
	
	public boolean isSuccess(){
		return "0".equals(code);
	}
}
