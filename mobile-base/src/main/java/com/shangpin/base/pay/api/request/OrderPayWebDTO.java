/**
 * 
 */
package com.shangpin.base.pay.api.request;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.shangpin.base.pay.Gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 跳转到支付平台web页的参数<br/>
 * url参数尽量不要传
 * @description 
 * @author 陈小峰
 * @date 2015年4月27日
 * @see OrderBasicDTO
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayWebDTO extends OrderBasicDTO {

	private static final long serialVersionUID = -5447925799743843268L;
	private Gateway gateWay;
	
	private String extend;
	private static Map<String,Field> fieldMap = new TreeMap<>();
	static {
		init();
	}
	
	
	/**
	 * 获取当前对象字典顺序排序后的值,排除为空的值<br/>
	 * 请求支付web界面进行加密时需要用到
	 * @return 值排序数据
	 * @see #requestMap() 请求的键值对
	 */
	public String sortedValue(){
		Map<String,String> mapValue=requestMap();
		Set<Entry<String,String>> set=mapValue.entrySet();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> entry : set) {
			sb.append(entry.getValue()).append("|");
		}
		return sb.toString().substring(0,sb.length()-1);
	}
	
	/**
	 * 请求调整支付web界面的请求参数 
	 * <br/>
	 * @return
	 */
	public Map<String, String> requestMap() {
		Map<String, String> map = new TreeMap<>();
		Set<String> fieldSet=fieldMap.keySet();
		for (String fieldName : fieldSet) {
			Object attrValue;
			try {
				Method m=BeanUtils.getPropertyDescriptor(this.getClass(), fieldName).getReadMethod();
				attrValue=m.invoke(this, new Object[0]);
				if(attrValue!=null && StringUtils.isNotBlank(attrValue.toString()) ){
					if(attrValue instanceof BigDecimal){
						String big=((BigDecimal) attrValue).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
						map.put(fieldName,big);
					}else if(attrValue instanceof Number){
						map.put(fieldName, df.format(attrValue));
					}else{
						map.put(fieldName,attrValue.toString());						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	private static void init(){
		List<Field> fields= new ArrayList<>(15);
		CollectionUtils.addAll(fields, OrderPayWebDTO.class.getSuperclass().getDeclaredFields());
		CollectionUtils.addAll(fields, OrderPayWebDTO.class.getDeclaredFields());
		for (Field field : fields) {
			if(field.getModifiers()==2){
				fieldMap.put(field.getName(),field);
			}
		}
	}
	
	
	public Gateway getGateWay(){
		return gateWay;
	}
	public Gateway getGateway(){
		return gateWay;
	}

	@Override
	protected void setExts(String ext) {
		setExtend(ext);
	}

}

