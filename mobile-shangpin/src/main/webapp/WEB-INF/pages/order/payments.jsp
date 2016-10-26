<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<div id="order_detail_payment" style="display: none;">
    <c:if test="${!checkAPP}">
        <div class="topFix" id="order_head">
            <section>
                <div class="topBack" >
                    <a href="javascript:;" class="backBtn">&nbsp;</a>
                    <span class="top-title">支付方式</span>
                    <ul class="alUser_icon clr">
                        <li><a href="<c:url value='/'/>"><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/home_icon.png${ver}" width="25" height="25" alt="首页"></a></li>
                    </ul>
                </div>
            </section>
        </div>
    </c:if>
    <%-- <div class="alContent" id="payment_content" style="min-height:550px;">
 </c:when>
 <c:otherwise>
     <div class="alContent" id="payment_content" style="margin-top: 0;">
 </c:otherwise>--%>

        <c:set var="payments" value="${payments}"></c:set>
        <ul class="payment_box clr cur">
            <c:forEach items="${payments}" var="pay">
                <li ${pay.isSelected==1 ? "class='checked'" : ""} clazz="${pay.clazz}" url="${pay.url}" mainId="${pay.id}" subId="${pay.subpayCode}" way="${pay.way}">
                    <em class="payment_icon ${pay.clazz}"></em>
                    <a href="javascript:;" >${pay.name}</a>
                </li>
            </c:forEach>
        </ul>
</div>
