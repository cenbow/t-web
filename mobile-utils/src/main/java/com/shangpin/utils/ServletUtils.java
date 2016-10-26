/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, shangpin.com
 * Filename:		com.shangpin.utils.ServletUtils.java
 * Class:			Servlets
 * Date:			2013-7-19
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.shangpin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * Version  3.1.0
 * @since   2013-7-19 下午2:25:31 
 */

public class ServletUtils {
	/**
	 * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		final String CONTENT_DISPOSITION = "Content-Disposition";
		
		try {
			String agent = request.getHeader("User-Agent");
			String encodedfileName = null;
	        if (null != agent) {  
	        	agent = agent.toLowerCase();  
	            if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {  
	    			encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
	            } else if (agent.contains("msie")) {  
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            } else if (agent.contains("opera")) {  
	            	encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
	            } else {
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            }
	        }
			
	        response.setHeader(CONTENT_DISPOSITION, "attachment; " + encodedfileName);
		} catch (UnsupportedEncodingException e) {
		}
	}
	
	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}
	
	/**
	 * 将请求参数转为key-value对，将数组用,号分隔
	 * @param request
	 * @return
	 */
	public static Map<String,String> param2Map(HttpServletRequest request) {
        Map<String,String> requestParams = new HashMap<>();
        @SuppressWarnings("unchecked")
		Map<String,String[]> map= (Map<String,String[]>)request.getParameterMap();
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = (String[])map.get(name);
            String valueStr = "";//String v="";
            for (int i = 0; i < values.length; i++) {
            	valueStr =valueStr + values[i].trim();
            	if(i==values.length-1 && i>0){
            		valueStr+=",";
            	}
            }
            requestParams.put(name, valueStr);
        }
        return requestParams;
    }
	
	public static final String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 读取http请求流里面的内容
	 * @param request
	 * @return http请求结果
	 */
	public static String readBody(HttpServletRequest request) {
		InputStream inputstream;
		StringBuffer sb = new StringBuffer();
		try {
			inputstream = request.getInputStream();
			byte[] buffer = new byte[1024];
			int idx=0;
			do{
				idx=inputstream.read(buffer,0,buffer.length);
				if(idx!=-1)
					sb.append(new String(buffer,0,idx));				
			}while(idx!=-1);  
		} catch (IOException e1) {
			return null;
		}  
		return sb.toString();
	}
	
	/**
	 * 输出字符串到响应流
	 * @param msg
	 * @param response
	 */
	public static void output(String msg, HttpServletResponse response) {
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8"); 
			response.getWriter().print(msg);
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}	
}
