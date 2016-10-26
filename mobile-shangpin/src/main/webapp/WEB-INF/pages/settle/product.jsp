<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="header">

</rapid:override>

<rapid:override name="custum">

    <title>订单提交</title>
    <link href=${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/order_Logistics.css${ver}" rel="stylesheet" />
    <script type="text/javascript" charset="utf-8">
        loader = SP.core
                .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
                .using("${cdn:css(pageContext.request)}/styles/shangpin/js/core.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/j.dialogs.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/iscroll.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/settle/product.js${ver}")
                .excute();
    </script>

</rapid:override>

<rapid:override name="page_title">
</rapid:override>

<rapid:override name="content">
    <div id="settle_product">
    <c:set var="productAll" value="${settle.product}" />
    <c:choose>
        <c:when test="${!checkAPP}">
            <div class="topFix" id="order_head">
                <section>
                    <div class="topBack" >
                        <a href="javascript:history.back(-1);" class="backBtn">&nbsp;</a>
                        <span class="top-title">${productAll.title}</span>
                        <ul class="alUser_icon clr">
                            <li><a href="<c:url value='/'/>"><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/home_icon.png${ver}" width="25" height="25" alt="首页"></a></li>
                        </ul>
                    </div>
                </section>
            </div>
            <div class="alContent" id="order_content" style="min-height:550px;">
        </c:when>
        <c:otherwise>
            <div class="alContent" id="order_content" style="margin-top: 0;">
        </c:otherwise>
    </c:choose>
    <div class="paymet_block">
        <form>

            <div class="order-prompt">
                <span>运费合计：&yen;${productAll.freight}</span>
                <em>${productAll.detailDesc}</em>
            </div>

            <c:forEach var="productList" items="${productAll.list}">
            <fieldset class="order-box">
                <p class="selected order-type">${productList.title}</p>
                <c:forEach var="product" items="${productList.detail}">
                <p class="pord_info">
                    <a href="#" class="clr">
                        <img src="${product.pic}" width="56" height="67">
                        <ins>
                            <i>${product.brandNameEN}<br>${product.brandNameCN}</i>
                            <c:forEach var="item" items="${product.attribute}">
                            <em>${item.name}：${item.value}</em>
                                <c:if test=""></c:if>
                            </c:forEach><br>
                            <em class="price">&yen;${product.price}<b>x${product.quantity}</b> </em>
                            <!--<em>数量：1</em>-->
                            <c:if test="${product.isShowFreight != '0' }">
                                 <span class="freight_info" desc="${product.freightDesc}">
                                    运费：&yen;<strong>${product.freight}</strong>
                                    <i><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/order/prompt_img.png${ver}" width="14" height="14" style="margin-bottom: 2px"></i>
                                </span>
                            </c:if>
                        </ins>
                    </a>
                </p>
                </c:forEach>
                <c:if test="${productList.isShowFreight != '0'}">
                <p class="pay-model-p">
                    <span class="freight_info" desc="${productList.freightDesc}">
                        运费：&yen;<strong>${productList.freight}</strong>
                        <i><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/order/prompt_img.png${ver}" width="14" height="14" style="margin-bottom: 2px"></i>
                    </span>
                </p>
                </c:if>
                <%--<p class="pord_info">
                    <a href="#" class="clr">
                        <img src="${cdn:css(pageContext.request)}/styles/shangpin//images/order/image01.jpg${ver}" width="56" height="67">
                        <ins>
                            <i>POLO RALPH LAUREN<br>拉夫劳伦男士红拉夫劳伦男士红拉夫劳伦男士</i>
                            <em>颜色：红色</em>
                            <em>尺码：S</em><br>
                            <em class="price">¥1190 <b>x1</b> </em>
                        </ins>
                    </a>
                </p>--%>
                <%--<p class="pay-model-p">
                    <span class="freight_info">
                        运费：<strong>¥20</strong>
                        <i><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/order/prompt_img.png${ver}" width="14" height="14" style="margin-bottom: 2px"></i>
                    </span>
                </p>--%>
            </fieldset>
            </c:forEach>
            <%--<fieldset class="order-box">

                <p class="selected order-type">商品从意大利发货，预计10-15个工作日送达</p>

                <!--<h2>您在尚品香港购买的商品</h2>-->
                <p class="pord_info">
                    <a href="#" class="clr">
                        <img src="${cdn:css(pageContext.request)}/styles/shangpin/images/order/image01.jpg${ver}" width="56" height="67">
                        <ins>
                            <i>POLO RALPH LAUREN<br>拉夫劳伦男士红拉夫劳伦男士红拉夫劳伦男士</i>
                            <em>颜色：红色</em>
                            <em>尺码：S</em><br>
                            <em class="price">¥1190 <b>x1</b> </em>
                        <span class="freight_info">
                            运费：¥130
                            <i><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/order/prompt_img.png${ver}" width="14" height="14"></i>
                        </span>
                        </ins>
                    </a>
                </p>
            </fieldset>--%>
        </form>
    </div>
    </div>

    <div class="freight_bg">
        <div class="freight_box">
            <h3>关税运费说明</h3>
            <p>海外商品由于清关需求，需要单独收取关税和运费，商品展示价格中已包含税费，购买多件商品需要多次收取运费，请知晓，感谢~</p>
            <a>知道了</a>
        </div>
    </div>

</div>
</rapid:override>

<rapid:override name="footer">
</rapid:override>

<%@ include file="/WEB-INF/pages/common/another_common.jsp"%>



