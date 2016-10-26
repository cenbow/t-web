<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<%--此页面还有活动促销标未加，大伟说先不加 --%>
<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/201502brand/brand.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
			loader = SP.core
			.install("${cdn:js(pageContext.request)}/styles/shangpin/js/zepto.1.1.3.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/iscroll.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
			.excute()
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/tabslider.js${ver}")
			.excute()
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/weixin/WXShareM.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/lable.js${ver}");
	</script>
</rapid:override >

 <%-- 浏览器标题 --%>
<%--<rapid:override name="title">
	${activityIndex.operat.head.name}
</rapid:override>

页标题
<rapid:override name="page_title">
	${activityIndex.operat.head.name}
</rapid:override> --%>
<rapid:override name="content">
	<form id="search_form" action="${ctx}/lable/product/list?tagId=${searchConditions.tagId}#navLine" method="post">
		<input type="hidden" id="filters" name="filters" value="${searchConditions.filters}"/>
		<input type="hidden" id="type" name="type" value="${searchConditions.type}"/>
		<input type="hidden" id="order" name="order" value="${searchConditions.order}"/>
	
		<input type="hidden" id="start" name="start" value="1"/>
	</form>
		<input type="hidden" id="tagId" name="tagId" value="${searchConditions.tagId}"/>
	<div class="line"><span id="menuLine"></span></div>

	 <!-- 滑块列表 -->  
	<div class="tabSlider" id="navLine"> 
		<div class="menu-nav">
			<%--<div class="menu-nav-left"> 页面tab条不滑动注释掉 --%>
				<ul class="tabSlider-hd" id="list_menu">
					<li id="defaultOrder"  onClick="orderChange('')"><a ctag='{"id":"LC5","cid":"${searchConditions.tagId}","sort":"default"}' >默认</a></li>
					<li id="orderOne"  onClick="orderChange('3')"><a ctag='{"id":"LC5","cid":"${searchConditions.tagId}","sort":"newest"}'  >新品</a></li>
					<%-- <c:if test="${!checkWX}">
						<c:if test="${searchResult.abroad=='1'}">
							<li id="abroadOrder"  onClick="orderChange('9')"><a>海外购</a></li>
						</c:if>
					</c:if> --%>
					<li id="orderTwo" onClick="orderChange('5')"><a ctag='{"id":"LC5","cid":"${searchConditions.tagId}","sort":"sales"}' >销量</a></li>
					<li id="orderThree" class="price-btn" ><a href="" ctag='{"id":"LC5","cid":"${searchConditions.tagId}","sort":"price"}' >价格</a></a></li>
				</ul> 
			<%--</div>--%>
<!-- 			<a href="javascript:;" class="fillBtn">筛选</a> -->
		</div>
	
		<div class="list-box">
			<div class="prod_list clr">
				<c:forEach var="product" items="${searchResult.productList}" varStatus="status">
					<div class="list_box">
						
					<c:choose>
						<c:when test="${checkAPP}">
								<a href="shangPinApp://phone.shangpin/actiongodetail?productid=${product.productId}">
						</c:when>
						<c:otherwise>
								<a href="<c:url value='/product/detail?productNo=${product.productId}&picNo=${product.picNo}'/>"  onclick="_smq.push(['custom',${product.productId},'',${product.productName}]);">
						</c:otherwise>
					</c:choose>
                            <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif"  lazy="${fn:substring(product.pic,0,fn:indexOf(product.pic,'-'))}-320-426.jpg" style="opacity: 1;" ctag='{"id":"LC6","cid":"${searchConditions.tagId}","spu":"${product.productId}"}'/>
                        </a>
						<div class="li_text">
							<div class="symbol">
									<c:forEach var="flag" items="${product.flagList}">
										<i>${flag}</i>
									</c:forEach>
							</div>
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
		
					<c:if test="${fn:length(searchResult.productList) == 0}">
						<p class="brand_noresults">没有筛选结果，重新筛选下吧</p>
					</c:if>
				
	</div>
	</div>
</rapid:override> 

<rapid:override name="filter_layer">
	<div id="filter_layer">
		<div id="filter_box">
			<h2 class="filter_menu"><a href="#" class="close_Btn" id="filter_close"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/201502brand/sx_icon.png" height="20"  /></a><p>筛选</p></h2>
			<div class="filter_content">
			<div id="attributesItemFirst">
				<div class="category-box"  id="categoryItem">
					<h3>品类</h3>
					<ul id="category">
						<c:forEach var="category" items="${searchResultFliter.categoryList}" varStatus="status">
							<c:choose>
								<c:when test="${status.index==0}">
									<li id ="${category.id}" class="first_yes" firstFlag="yes" onclick="searchFilter('${category.id}','category','0');" idFlag="${category.id}" nameFlag="${category.name}" ctag='{"id":"LC1","cid":"${searchConditions.tagId}","tcid":"${category.id}"}'   >${category.name}</li>
								</c:when>
								<c:otherwise>
									<li id ="${category.id}" firstFlag="no" onclick="searchFilter('${category.id}','category','0');" ctag='{"id":"LC1","cid":"${searchConditions.tagId}","tcid":"${category.id}"}'  >${category.name}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div id="attributesItemNotf">	
				<div class="category-box"  id="brandItem">
					<h3>品牌</h3>
					<ul id="brand">
					</ul>
				</div>
			</div>	
			<div id="filtratesAll">
				<div class="category-box"  id="sizeItem">
					<h3>尺码</h3>
					<ul id="size">
					</ul>
				</div>
				
				<div class="color-box" id="colorItem">
					<h3>颜色</h3>
					<ul id="color">
					</ul>
				</div>
				
				<div class="category-box" id="priceItem">
					<h3>价格区间</h3>
					<ul id="price">
						<li id="0-500"  onclick="searchFilter('0-500','price','2');"  ctag='{"id":"LC4","cid":"${searchConditions.tagId}","price":"0-500"}'  >0-500</li>
						<li id="500-1000" onclick="searchFilter('500-1000','price','2');" ctag='{"id":"LC4","cid":"${searchConditions.tagId}","price":"500-1000"}'>500-1000</li>
						<li id="1000-2000" onclick="searchFilter('1000-2000','price','2');" ctag='{"id":"LC4","cid":"${searchConditions.tagId}","price":"1000-2000"}'>1000-2000</li>
						<li id="2000-5000" onclick="searchFilter('2000-5000','price','2');" ctag='{"id":"LC4","cid":"${searchConditions.tagId}","price":"2000-5000"}'>2000-5000</li>
						<li id="5000-10000" onclick="searchFilter('5000-10000','price','2');" ctag='{"id":"LC4","cid":"${searchConditions.tagId}","price":"5000-10000"}'>5000-10000</li>
						<li id="10000"  onclick="searchFilter('10000','price','2');" ctag='{"id":"LC4","cid":"${searchConditions.tagId}","price":">10000"}'>>10000</li>
					</ul>
				</div>
				<div class="category-box" id="postAreaItem">
					<h3>价格区间</h3>
					<ul id="postArea">
					
					</ul>
				</div>
			</div>
				<a href="javacript:;" id="finishBtn">确定</a>
				<input type="hidden" id="hasPageNum" name="hasPageNum" value="${hasPageNum }"/>
				<input type="hidden" id="checkApp" name="checkApp" value="${checkAPP }"/>	
			</div>
		</div>
		</div>
		<div id="hiddenValue2">
		<input type="hidden" id="oldFilters" name="oldFilters" value="${searchConditions.filters}"/>
		<input type="hidden" id="oldType" name="oldType" value="${searchConditions.type}"/>
		<input type="hidden" id="oldOrder" name="oldOrder" value="${searchConditions.order}"/>
		<input type="hidden" id="oldTagId" name="oldTagId" value="${searchConditions.tagId}"/>
		<input type="hidden" id="oldStart" name="oldStart" value="1"/>
		</div>
	</rapid:override>
<%@ include file="/WEB-INF/pages/common/common_mall_banner.jsp" %> 

