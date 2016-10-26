<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<%--
  User: liushaoqing
  Date: 2016/7/29
  Time: 12:11
--%>
<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/wish_list.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
		loader = SP.core.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js?${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}").excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/j.dialogs.js${ver}").excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/collect.js${ver}");
	</script>
</rapid:override >

<rapid:override name="page_title">
	愿望清单
</rapid:override>
<rapid:override name="search_form">
</rapid:override>
<rapid:override name="content">
	<!-- 收藏列表 -->
	<input type="hidden" id="pageIndex" name="pageIndex" value="${pageIndex}"/>
	<input type="hidden" id="hasMore" name="hasMore" value="${hasMore}"/>
	<c:if test="${fn:length(products) == 0}">
		<p class="brand_noresults">你还没有收藏呢</p>
	</c:if>

	<div class="prod_list clr">
		<c:forEach var="product" items="${products}">
			<div class="list_box" data-id="${product.id}">
				<div class="prod_delete"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/member/clear-button.png${ver}" ctag='{"id":"WL2","pid":"${product.productId}"}' ></div>
				<a href="javascript:goDetail('${product.type }','${product.isShelve}','${product.productId}');" ctag='{"id":"WL1","pid":"${product.productId}"}'>
					<img src="/images/e.gif" lazy="${fn:substring(product.pic,0,fn:indexOf(product.pic,'-'))}-304-404.jpg" />
				</a>
				<div class="li_text">
					<%--<div class="symbol">
						<c:forEach var="flag" items="${product.flagList}">
						<i>${flag}</i>
					    </c:forEach>
					</div>--%>
					<h5>${product.brandNameEN}</h5>
					<p>${product.productName}</p>
					<span class="item-detail" style="color:${_isVip ? product.vip_ProductPrice.color : product.nomal_ProductPrice.color} ">
						${_isVip ? product.vip_ProductPrice.priceDesc : product.nomal_ProductPrice.priceDesc}
						<em class="${product.nomal_ProductPrice.compareHasLine eq '1' ? 'promotion-price-hasline' : 'promotion-price'}" style="color:${product.nomal_ProductPrice.compareColor}">${_isVip ? product.vip_ProductPrice.compareDesc : product.nomal_ProductPrice.compareDesc}</em>
					</span>
				</div>
			</div>
		</c:forEach>
	</div>
</rapid:override>

<%@ include file="/WEB-INF/pages/common/another_common_mall_banner.jsp" %>