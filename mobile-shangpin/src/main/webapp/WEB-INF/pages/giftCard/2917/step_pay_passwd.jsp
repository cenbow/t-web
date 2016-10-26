 <%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%> 
 
    <div class="setup">
	          	<div class="setup_img"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/un_setup.png"></div>
	            <p>你还没有设置礼品卡支付密码</p>
	          	<a class="setup_button" href="${ctx}/giftCard/stepPayPasswd?oneKeyToken=103">去设置</a>
	 </div>