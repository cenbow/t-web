<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
     
<c:choose>
	<c:when test="${giftCardRecord!=null && fn:length(giftCardRecord.list)>0 }">
	<input type="hidden" id="isHaveMore${recordType }${pageNo }" value="${isHaveMore }"/>
	          	<c:forEach var="record" items="${giftCardRecord.list }">
			     <li class="clr" id="geft_cell"> 
			        <span>${record.fmtDate }</span>
			        <span style="width:35%">${record.childOrderId }</span>
			        <span style="width:20%">¥${record.expenseAmount }</span>
			        <span style="width:20%">${record.statusDesc }</span>
			     </li>
				</c:forEach>
	</c:when>
	<c:otherwise>
	<div class="setup" >
					   <div class="setup_img"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/card/card_null.png"></div>
					   <p>暂时没有记录哦</p>
	</div>
	</c:otherwise>
</c:choose>
        