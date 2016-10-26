<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<div id="settle_payment" style="display: none;">
<c:choose>
    <c:when test="${!checkAPP}">
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
        <div class="alContent" id="payment_content" style="min-height:550px;">
    </c:when>
    <c:otherwise>
        <div class="alContent" id="payment_content" style="margin-top: 0;">
    </c:otherwise>
</c:choose>

        <c:set var="payments" value="${payments}"></c:set>
        <ul class="payment_box clr cur">
            <c:forEach items="${payments}" var="pay">
                <li ${pay.isSelected==1 ? "class='checked'" : ""} clazz="${pay.clazz}" url="${pay.url}" mainId="${pay.id}" subId="${pay.subpayCode}" way="${pay.way}">
                    <em class="payment_icon ${pay.clazz}"></em>
                    <a href="javascript:;" >${pay.name}</a>
                </li>
            </c:forEach>

           <%-- <li>
                <em class="payment_icon zhifubao"></em>
                <a href="javascript:;">支付宝</a>
            </li>
            <li>
                <em class="payment_icon wechat"></em>
                <a href="javascript:;">微信</a>
            </li>
            <li>
                <em class="payment_icon qq"></em>
                <a href="javascript:;">QQ钱包</a>
            </li>
            <li>
                <em class="payment_icon yinlian"></em>
                <a href="javascript:;">银联</a>
            </li>
            <li>
                <em class="payment_icon  cash"></em>
                <a href="javascript:;">货到付款</a>
            </li>--%>
        </ul>
</div>
</div>
