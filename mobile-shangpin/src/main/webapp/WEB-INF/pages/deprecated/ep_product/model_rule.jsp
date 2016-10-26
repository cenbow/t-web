<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
	<c:choose>
		<c:when test="${model.type == '1'}">
			<a href="${ctx}/subject/product/list?topicId=${model.refContent}&postArea=0">
		</c:when>
		<c:when test="${model.type == '2'}">
			<a href="${ctx}/category/product/list?categoryNo=${model.refContent}&postArea=0">
		</c:when>
		<c:when test="${model.type == '3'}">
			<a href="${ctx}/brand/product/list?brandNo=${model.refContent}&postArea=0&WWWWWWWWW">
		</c:when>
		<c:when test="${model.type == '4'}">
			<a href="${ctx}/product/detail?productNo=${model.refContent}">
		</c:when>
		<c:when test="${model.type == '5'}">
			<a href="${model.refContent}">
		</c:when>
		<c:when test="${model.type=='6'}">
	 	   <c:choose>
				<c:when test="${model.refContent=='1'}">
					<a href="${ctx}/coupon/list">
				</c:when>
				<c:when test="${model.refContent=='3'}">
					<a href="${ctx}/order/list-1">
				</c:when>
				<c:when test="${model.refContent=='4'}">
					<a href="${ctx}/collect/product/list?pageIndex=1&pageSize=20&shopType=1">
				</c:when>
				<c:when test="${model.refContent=='2'}">
					<a href="${ctx}/giftCard/productList">
				</c:when>
				<c:when test="${model.refContent=='7'}">
					<a href="http://m.aolai.com">
				</c:when>
				<c:otherwise>
				<a href="#">
				</c:otherwise>
		  </c:choose>
      </c:when>
	  <c:when test="${model.type == '9'}">
			<a href="${ctx}/lable/product/list?tagId=${model.refContent}">
	  </c:when>
	   <c:otherwise>
			<a href="#">
	   </c:otherwise>
	</c:choose> 
