package com.shangpin.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 
 * TODO 该类的作用<br/>
 * @date    2016年5月28日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
public class TestBean {

	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public static void main(String[] args) {
		System.out.println(new BigDecimal(0.01f).setScale(2,RoundingMode.HALF_UP).toString() );
	}
}
