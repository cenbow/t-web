package com.shangpin.base.pay.api.request;

import com.shangpin.base.pay.Gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** 
 * 支付接口请求参数<br/>
 * @date    2016年8月9日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 * @see OrderBasicDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayApiDTO extends OrderBasicDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3103876628020867243L;
   
    /**
     * 一级分类,传入网关类型
     */
    private Gateway gateway;
  
    /**
     * 支付请求扩展字段 以"key:value,key:value,..."形式表示
     * 针对特殊需要如：wap，pc支付
     * */
    private String ext;

    /**
     * 用户支付IP,尽量都带上
     */
    private String customerIp;

	@Override
	protected void setExts(String ext) {
		setExt(ext);
	}
	
    
    
}
