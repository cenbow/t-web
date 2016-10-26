 <%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%> 
 
    <div class="setup">
	          	<div class="setup_img"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/un_setup.png"></div>
	            <p>你还没有绑定手机</p>
	          	<a class="setup_button" href="${ctx}/giftCard/stepTel?oneKeyToken=103">去绑定</a>
	 </div>