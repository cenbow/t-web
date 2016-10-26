<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="header">

</rapid:override>
<rapid:override name="custum">

    <title>订单提交</title>
    <link href=${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}" rel="stylesheet" />
   <%-- <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/settlement/order_form160425.css${ver}" rel="stylesheet" />--%>
    <link href="${cdn:css(pageContext.request) }/styles/shangpin/css/page/order_address.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request) }/styles/shangpin/css/member.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request) }/styles/shangpin/css/page/order_result.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/payment_normal.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/addr.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/2016Mapp/payment.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/order_form160804.css${ver}" rel="stylesheet" />
    <script type="text/javascript" charset="utf-8">
        loader = SP.core
                .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
                .using("${cdn:css(pageContext.request)}/styles/shangpin/js/core.js${ver}")
                .using("${cdn:css(pageContext.request)}/styles/shangpin/js/comm.js?${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/j.dialogs.js${ver}")

                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/iscroll.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/overseas/validIDCard.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/settle/order_address.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/settle/pay_union.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/settle/invoice.js${ver}")
                .excute();
    </script>

</rapid:override>

<rapid:override name="page_title">
</rapid:override>

<rapid:override name="content">
    <%@include file="/WEB-INF/pages/settle/submit_order160425.jsp" %>
    <%@include file="/WEB-INF/pages/settle/address_list.jsp" %>
    <%@include file="/WEB-INF/pages/settle/address_add.jsp" %>
    <%@include file="/WEB-INF/pages/settle/coupons.jsp" %>
    <%@include file="/WEB-INF/pages/settle/invoice_info.jsp" %>
    <%@include file="/WEB-INF/pages/settle/payments.jsp" %>

    <input type="hidden" name="buyId" value="${buyIds}">
    <input type="hidden" name="payCategory" value="${settle.payCategory}">
    <input type="hidden" name="orderSource" value="${orderSource}">
    <input type="hidden" name="isContainOutside" value="${settle.isNeedCardId}">
    <input type="hidden" name="provinceId" value="${settle.received.currentAddress.province}">

    <input type="hidden" name="couponNo" value="${not empty settle.coupon.couponNo ? settle.coupon.couponNo : "0"}">
    <input type="hidden" name="coupon_canUseAmount" value="${settle.coupon.canUseAmount}">
    <input type="hidden" name="coupon_count" value="">
    <input type="hidden" name="coupon_type" value="${settle.coupon.type}">

    <input type="hidden" name="giftCard_balance" value="${settle.giftCard.balance}">
    <input type="hidden" name="giftCard_canUseAmount" value="${settle.giftCard.canUseAmount}">
    <%--页面临时保存发票信息--%>
    <input type="hidden" name="invoiceflag" value="0">
    <input type="hidden" name="invoicetype" value="1">
    <input type="hidden" name="invoicetitle" value="个人">
    <input type="hidden" name="invoicecontent" value="商品全称">
    <input type="hidden" name="invoiceemail" value="">
    <input type="hidden" name="invoicetel" value="">
    <input type="hidden" name="wx" value="${checkWX}">
    <input type="hidden" name="listSkuDetail" value="${settle.listSkuDetail}">

</rapid:override>

<rapid:override name="footer">
</rapid:override>

<%@ include file="/WEB-INF/pages/common/another_common.jsp"%>
