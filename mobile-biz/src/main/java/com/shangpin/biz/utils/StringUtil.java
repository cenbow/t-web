package com.shangpin.biz.utils;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.utils.DateUtils;public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @Author zhouyu
	 * @CreateDate 2012-10-29
	 * @param str
	 *            被判断对象
	 * @Return
	 */
	public static boolean isNotEmpty(String... str) {
		int length = str.length;
		for (int i = 0; i < length; i++) {
			if (str[i] != null && !"".equals(str[i].trim())) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 截掉小数
	 * 
	 * @Author: wangfeng
	 * @CreateDate: 2014-07-18
	 * @param str
	 *            被判断对象
	 * @Return:
	 */
	public static String cutOffDecimal(String str) {
		if (str != null && !"".equals(str.trim()) && !"null".equals(str) && !"0".equals(str)) {
			if (str.indexOf(".") > 0) {
				str = str.substring(0, str.indexOf("."));
			}
		}
		return str;
	}

	/**
	 * 检查对象是否为空， null 以 "" 返回
	 * 
	 * @param value
	 * @return
	 */
	public static String checkNull(String value) {
		if (value != null && !"null".equals(value)) {
			return value;
		} else {
			return "";
		}
	}

	/**
	 * 取得字符串的boolean 值
	 * 
	 * @param value
	 * @return
	 */
	public static String checkTrueOrFalse(String value) {
		if (value != null && "true".equals(value)) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * 截取带小数点的数字的字符串 例如：123.00 截取为 123
	 * 
	 * @param doubleStr
	 * @return
	 * @author zghw
	 */
	public static String removePoint(String doubleStr) {
		int pos = doubleStr.indexOf(".");
		if (pos != -1) {
			return doubleStr.substring(0, pos);
		}
		return doubleStr;
	}
	
	
	
	/**
	 * 比较版本号
	 * 
	 * @param ver
	 *            版本号
	 * @param anotherVer
	 *            比较的版本号
	 * @return 0两者相等，1 ver>anotherVer, -1 ver<anotherVer
	 */
	public static int compareVer(String ver, String anotherVer) {
		if(StringUtils.isBlank(ver) && StringUtils.isNotBlank(anotherVer)){
			return -1;
		}
		if(StringUtils.isBlank(anotherVer) && StringUtils.isNotBlank(ver)){
			return 1;
		}
		int gtOrlt=0;//=是0，1是>，-1<
		String[] svs=ver.split("\\.");
		String[] ovs = anotherVer.split("\\.");
		int i=0;
		while(true){//先比较.位相同的
			if(i+1>ovs.length) break;
			Integer nextInt=getNextVer(ovs,i);
			gtOrlt=Integer.valueOf(getNextVer(svs,i)).compareTo(nextInt);
			if(gtOrlt!=0){
				break;
			}
			i++;
		}
		if(gtOrlt==0 ){
			if(ovs.length>svs.length){
				return -1;
			}else if(ovs.length<svs.length){
				return 1;
			}
		}
		return gtOrlt;
	}

	
/**
	 * 
	 * 比较版本大小；支持版本区间<br/>
	 * 比较某版本是否在版本区间内
	 * @param startVersion yyyy-MM-dd HH:mm:ss 开始区间
	 * @param endVersion yyyy-MM-dd HH:mm:ss 结束区间
	 * @param theVersion yyyy-MM-dd HH:mm:ss 比较的版本
	 * @return
	 * 		0：当前所在版本区间[xx,yy)，一般是【xx版本到yy版本，但不包含yy版本】需求未变动；<br/>
	 * 		1：最新版本[xx,~)；<br/>
	 * 		-1：老版本(~,xx)
	 */
	public static int compareDate(String startdate, String enddate, String thedate) {
		try{
			long sdate=DateUtils.strToDate(startdate, "yyyy-MM-dd HH:mm:ss").getTime();
			long edate=DateUtils.strToDate(enddate, "yyyy-MM-dd HH:mm:ss").getTime();
			long tdate=DateUtils.strToDate(thedate, "yyyy-MM-dd HH:mm:ss").getTime();
			if(tdate>=sdate && tdate<edate){
				return 0;
			}
			if(tdate<sdate){
				return -1;
			}
			if(edate<=tdate){
				return 1;
			}
			
		}catch(Exception e){}
		
		return 0;
	}
	
	/**
	 * 获取ovs下一个整型版本<br/>如果没有下一个则返回0
	 * @param ovs 2.3.23这种以.分隔的数组
	 * @param i 下标，从0开始
	 * @return
	 */
	private static Integer getNextVer(String[] ovs, int i) {
		if(ovs.length-1>=i)
			return Integer.valueOf(ovs[i].trim());
		return 0;
	}
	
	
}
