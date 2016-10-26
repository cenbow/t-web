 <%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
 
<div class="product_info">
	<div>
		<ul class="tab_info">
			<li class="curr" id="product_tab_1" ctag='{"id":"D3","pid":"${productNo}"}'><span >商品详情</span></li>
			<li id="product_tab_2" ctag='{"id":"D10","pid":"${productNo}"}'><span >图片详情</span></li>
			<c:if test="${productDetail.basic.isSize=='1'}">
				<li id="product_tab_3" ctag='{"id":"D4","pid":"${productNo}"}'><span >尺码及试穿</span></li>
			</c:if>
			<li id="product_tab_5" ctag='{"id":"D6","pid":"${productNo}"}'><span >保养售后</span></li>
		</ul>
	</div>
	<div class="content_info">
		<div class="content_detail content_list">
			<ul class="info_base">
				<c:forEach var="info" items="${productDetail.basic.info }">
					<c:choose>
						<c:when test="${fn:indexOf(info,':')>-1 }">
							<li><span><strong>${fn:substring(info,0,fn:indexOf(info,":")) }</strong>：</span>${fn:substring(info,fn:indexOf(info,":")+1,fn:length(info)) } &nbsp;</li>
						</c:when>
						<c:when test="${fn:indexOf(info,'：')>-1 }">
							<li><span><strong>${fn:substring(info,0,fn:indexOf(info,"：")) }</strong>：</span>${fn:substring(info,fn:indexOf(info,"：")+1,fn:length(info)) }&nbsp;</li>
						</c:when>
						<c:otherwise>
							<li><span><strong>${fn:substring(info,0,fn:indexOf(info,":")) }</strong>：</span>${fn:substring(info,fn:indexOf(info,":")+1,fn:length(info)) }&nbsp;</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="content_size content_list" id="tabs_txt1" style="display:none">
		<section>
			<header>${productDetail.basic.recommend }</header>
			<article>
				<c:forEach var="pic" items="${productDetail.basic.allPics}">
					<img src="${fn:replace(pic,'-10-10','-600-758')}">
				</c:forEach>
			</article>
		</section>
		</div>
		<div class="content_size content_list nobottom border_bottom" id="tabs_txt0" style="display:none"></div>
		<div class="content_rand content_list nobottom border_bottom" id="tabs_txt3" style="display:none">
			${productTemplate.html }
		</div>
		<!--保养及售后start-->
		<div class="content_rand content_list"  id="tabs_txt4" style="display:none">
			${productTemplate.html }
		</div>
	</div>
</div>
<!--保养及售后end-->

<!-- 品牌店入口 start -->
<div class="brand-shop-enter">
	<h3 class="brand-shop-name clr">
		<span>${productDetail.basic.brand.nameEN } 品牌店</span><a
			class="story-btn"
			href="<c:url value='/brand/product/list?brandNo=${productDetail.basic.brand.id }&brandName=${productDetail.basic.brand.nameEN }'/>" ctag='{"id":"D8","pid":"${productNo}","bid":"${productDetail.basic.brand.id }"}'>进店逛逛</a>
		<a class="enter-btn" href="<c:url value='/brandStory/index?id=${productDetail.basic.brand.id }&type=0'/>" ctag='{"id":"D7","pid":"${productNo}","bid":"${productDetail.basic.brand.id }"}'>品牌故事</a>
	</h3>
	<c:choose>
	<c:when test="${(productDetail.basic.brand.allNum  ne -1) && (productDetail.basic.brand.newestNum ne -1) && (productDetail.basic.brand.collections ne -1)}">
		<ul class="brand-shop-list">
			<li><em>${productDetail.basic.brand.allNum }</em> <span>全部商品</span>
			</li>
			<li><em>${productDetail.basic.brand.newestNum}</em> <span>上新</span>
			</li>
			<li><em>${productDetail.basic.brand.collections}</em> <span>喜欢人数</span>
			</li>
		</ul>
	</c:when>
	</c:choose>
</div>
<!-- 品牌店入口 end -->

<c:if test="${fn:length(guessLikeList) > 0}">
	<div class="list-box border_bott list-bottom-bg">
	<%--<h3>猜您喜欢</h3>--%>
		<div class="prod_list clr">
			<c:forEach var="product" items="${guessLikeList}" varStatus="status">
				<div class="list_box">
					<c:choose>
						<c:when test="${checkAPP}">
							<a  href="shangPinApp://phone.shangpin/actiongodetail?productid=${product.productId}">
						</c:when>
						<c:otherwise>
							<a href="<c:url value='/product/detail?productNo=${product.productId}' />" ctag='{"id":"D9","pid":"${productNo}","target":"${product.productId}"}'>
						</c:otherwise>
					</c:choose>
					<i class="item-country"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" 	lazy="${product.countryPic }"></i>
					<c:if test="${product.count eq '0'}">
						<i class="item-status">售罄</i>
					</c:if>
					<img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${fn:substring(product.pic,0,fn:indexOf(product.pic,'-'))}-306-407.jpg"  />
					</a>
					<div class="li_text">
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
	</div>
</c:if>

	<!--其他服务start-->
  <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/pic_service00.jpg" class="service_pic"/>

</div>
