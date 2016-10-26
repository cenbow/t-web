<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<c:if test="${fn:length(recProductItem.list) >0}">
	
	<!-- 为您推荐 -->
	
	<div class="recommend-box">
		<div id="masonry" class="container-fluid recommend-list">
			<c:forEach var="product" items="${recProductItem.list}">
				<div class="waterfull-list">
					<c:choose>
						<c:when test="${checkAPP}">
							<a  href="shangPinApp://phone.shangpin/actiongodetail?productid=${product.productId}">
						</c:when>
						<c:otherwise>
							<a href="<c:url value='/product/detail?productNo=${product.productId}'/>"  ctag='{"id":"M6","spu":"${product.productId}"}' >
						</c:otherwise>
					</c:choose>
					<img src="${fn:substring(product.pic,0,fn:indexOf(product.pic,'-'))}-306-407.jpg"/>
					<div class="li_text">
						<div class="symbol">
							<c:forEach var="flag" items="${product.flagList}">
								<i>${flag}</i>
							</c:forEach>
						</div>
						<h5>${product.brandNameEN}</h5>
						<p>${product.productName}</p>
						<span class="item-detail">
							<strong class="refer-price" style="color:${_isVip ? product.vip_ProductPrice.color : product.nomal_ProductPrice.color}">
								${_isVip ? product.vip_ProductPrice.priceDesc : product.nomal_ProductPrice.priceDesc}
							</strong>
							<em class="${product.nomal_ProductPrice.compareHasLine eq '1' ? 'promotion-price-hasline' : 'promotion-price'}" style="color:${product.nomal_ProductPrice.compareColor}">${_isVip ? product.vip_ProductPrice.compareDesc : product.nomal_ProductPrice.compareDesc}</em>
						</span>
					</div>
					</a>
				</div>
	        </c:forEach>
			<input type="hidden" id="pageIndex" name="pageIndex" value="1"/>
			<input type="hidden" id="hasMore" name="hasMore" value="1"/>
		</div>
		
		<div class="loading"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/loading1.gif"><span>正在加载...</span></div><!---->
		<div id="navigation"><a href="${ctx }/index/exclusive/recommend/more?pageIndex=1"></a></div> <!---->
	</div>
</c:if>

