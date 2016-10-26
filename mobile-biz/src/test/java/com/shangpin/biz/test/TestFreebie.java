package com.shangpin.biz.test;

import java.util.Map;

import com.shangpin.biz.utils.FreeBie520Util;

/** 
 * TODO 该类的作用<br/>
 * @date    2016年6月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
public class TestFreebie {


	public static void main(String[] args) {
		Map<String, String> param = FreeBie520Util.decodeFreebieParam("YGcu&t/ZCKIZIw6hHMIoLT0hGmd6D3X35ThhH2XU1dzRIBGTtKkNSjeBD53VNEJYEnhu/E6XQfQeINytNXBJtA==");
		System.out.println(param);
		
		String dep=FreeBie520Util.encodeFreebieUrlParam("4258F3B28E5E66E7D94EEFD49675BAC7","201605202675035","30282159","30282159002","1");
		System.out.println(dep);
	}
}
