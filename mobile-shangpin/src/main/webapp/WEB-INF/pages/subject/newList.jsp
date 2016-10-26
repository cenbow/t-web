<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<%--此页面还有活动促销标未加，大伟说先不加 --%>
<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/201502brand/brand.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/meet/share.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/201502brand/swiper.min.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
			loader = SP.core
			.install("${cdn:js(pageContext.request)}/styles/shangpin/js/zepto.1.1.3.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/iscroll.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
			.excute()
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/tabslider.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js?${ver}")
			.excute()
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/weixin/WXShareM.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/activity.js${ver}")
			.excute();
	</script>
	<c:if test="${headPic!=null && headPic.js!=null}">
		${headPic.js}`
	</c:if>
	<c:if test="${headPic!=null && headPic.css!=null}">
		${headPic.css}
	</c:if>
</rapid:override >

<%-- 浏览器标题 --%>
<rapid:override name="title">
	${activityIndex.operat.head.name}
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	${activityIndex.operat.head.name}
</rapid:override>
<rapid:override name="content">
	<form id="search_form" action="${ctx}/subject/product/list_${searchConditions.topicId}#navLine" method="post">
		<input type="hidden" id="price" name="price" value="${searchConditions.price}"/>
		<input type="hidden" id="size" name="size" value="${searchConditions.size}"/>
		<input type="hidden" id="color" name="color" value="${searchConditions.color}"/>
		<input type="hidden" id="colorName" name="colorName" value="${searchConditions.colorName}"/>
		<input type="hidden" id="categoryNo" name="categoryNo" value="${searchConditions.categoryNo}"/>
		<input type="hidden" id="categoryName" name="categoryName" value="${searchConditions.categoryName}"/>
		<input type="hidden" id="brandNo" name="brandNo" value="${searchConditions.brandNo}"/>
		<input type="hidden" id="brandName" name="brandName" value="${searchConditions.brandName}"/>
		<input type="hidden" id="gender" name="gender" value="${searchConditions.gender}"/>
		<input type="hidden" id="order" name="order" value="${searchConditions.order}"/>
		<input type="hidden" id="postArea" name="postArea" value="${searchConditions.postArea}"/>
		<input type="hidden" id="start" name="start" value="1"/>
		<input type="hidden" id="pageNo" name="pageNo" value="1"/>
	<%--	<input type="hidden" id="topicId" name="topicId" value="${searchConditions.topicId}"/> --%>
	</form>
	<input type="hidden" id="topicId" name="topicId" value="${searchConditions.topicId}"/>
	<input type="hidden" id="priceTag" name="priceTag" value="${activityIndex.operat.activity.priceTag}"/>
	<input type="hidden" id="priceDesc" name="priceDesc" value="${activityIndex.operat.activity.priceDesc}"/>
	<input type="hidden" id="startTime" name="startTime" value="${activityIndex.operat.activity.startTime}"/>
	<input type="hidden" id="sysTime" name="sysTime" value="${activityIndex.sysTime}"/>
	<input type="hidden" id="datepreTime" name="datepreTime" value="${activityIndex.operat.activity.datepreTime}"/>
	<input type="hidden" id="isTopicId" name="isTopicId" value="${isTopicId}"/>
	<input type="hidden" id="isLogin" name="isLogin" value="${isLogin}"/>
	<input type="hidden" id="isBuy" name="isBuy" value="${isBuy}"/>
	<input type="hidden" id="toLoginUrl"  value="${toLoginUrl}"/>

	<!-- 头图 -->
	 <%-- 分享文案 --%> 
	 <c:if test="${activityIndex.operat.head.share!=null }">
	 		<input type="hidden" name="_shareUrl"  id="_shareUrl"  value="${activityIndex.operat.head.share.url}"/>
	 		<input type="hidden" name="_mainTitle"  id="_mainTitle"  value="${activityIndex.operat.head.share.title}"/>
			 <input type="hidden" name="_mainDesc"  id="_mainDesc"  value="${activityIndex.operat.head.share.title}"/>
			 <input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="${fn:replace(activityIndex.operat.head.share.pic,'-10-10','-640-256') }"/>
		 <c:if test="${checkAPP}">
			 <div class="shang_share" >
				 <a href="shangpinapp://phone.shangpin/actiongoshare?title=${fn:replace(activityIndex.operat.head.share.title,'&', '＆')}&desc=${fn:replace(activityIndex.operat.head.share.desc,'&', '＆')}&url=http://m.shangpin.com/subject/product/list?topicId=${searchConditions.topicId}" style="height:50px;"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/b_share.png" width="40" style="width:40px"></a>
			 </div>
		 </c:if>
	 </c:if>
	<c:choose>
		<c:when test="${headPic!=null&&headPic.html!=null}">
			${headPic.html}
		</c:when>
		<c:otherwise>
			<c:if test="${activityIndex.operat!=null && activityIndex.operat.head!=null}">
				<p class="top_banner">
					<img src="${fn:substring(activityIndex.operat.head.logo,0,fn:indexOf(activityIndex.operat.head.logo,'-'))}-640-256.jpg" />
				</p>
			</c:if>
		</c:otherwise>
	</c:choose>
	 
	
	<c:if test="${fn:length(activityIndex.operat.coupon) > 0}">
		<!-- 优惠券 --> 
		<ul class="coupon-list clr">
			<c:forEach var="coupon" items="${activityIndex.operat.coupon}" varStatus="status">
				<c:if test="${coupon.isValid=='0'}">
					<li id="${coupon.code}"><img src="${fn:substring(coupon.pic,0,fn:indexOf(coupon.pic,'-'))}-200-95.jpg" /></li>
				</c:if>
			</c:forEach>
		</ul>
	</c:if>
	<div class="line"><span id="menuLine"></span></div>

	 <!-- 滑块列表 --> 
	<div class="tabSlider" id="navLine"> 
		<div class="menu-nav">
			<ul class="tabSlider-hd" id="list_menu">
				<li id="defaultOrder"  onClick="orderChange('')"><a ctag='{"id":"SJ7","sid":"${searchConditions.topicId }","sort":"default"}' >默认</a></li>
				<li id="orderOne"  onClick="orderChange('3')"><a ctag='{"id":"SJ7","sid":"${searchConditions.topicId }","sort":"newest"}'>新品</a></li>
				<%-- <c:if test="${!checkWX}">
					<c:if test="${searchResult.abroad=='1'}">
						<li id="abroadOrder"  onClick="orderChange('9')"><a>海外购</a></li>
					</c:if>
				</c:if> --%>
				<li id="orderTwo" onClick="orderChange('5')"><a ctag='{"id":"SJ7","sid":"${searchConditions.topicId }","sort":"sales"}' >销量</a></li>
				<li id="orderThree" class="price-btn" ><a href="" ctag='{"id":"SJ7","sid":"${searchConditions.topicId }","sort":"price"}'>价格</a></li>
			</ul>
<!-- 			<a href="javascript:;" class="fillBtn">筛选</a> -->
		</div>
	
		<div class="list-box">
			<div class="prod_list clr">
				<c:forEach var="product" items="${searchResult.productList}" varStatus="status">
					<div class="list_box">
						
					<c:choose>
						<c:when test="${checkAPP}">
								<a href="shangPinApp://phone.shangpin/actiongodetail?productid=${product.productId}&topicId=${searchConditions.topicId}">
						</c:when>
						<c:otherwise>
								<a href="<c:url value='/product/detail?productNo=${product.productId}&topicId=${searchConditions.topicId}&picNo=${product.picNo}'/>"  onclick="_smq.push(['custom',${product.productId},${searchConditions.categoryName},${product.productName}]);">
						</c:otherwise>
					</c:choose>
                            <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif"  lazy="${fn:substring(product.pic,0,fn:indexOf(product.pic,'-'))}-320-426.jpg" style="opacity: 1;"  ctag='{"id":"SJ1","sid":"${searchConditions.topicId }","spu":"${product.productId}"}'/>
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
			<c:choose>
					<c:when test="${fn:length(searchResult.productList) == 0 && searchConditions.postArea == '2'}">
						<p class="brand_noresults">暂无海外购商品</p>
					</c:when>
					<c:when test="${fn:length(searchResult.productList) == 0 && searchConditions.postArea != '2'}">
						<p class="brand_noresults">没有筛选结果，重新筛选下吧</p>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
		</div>
	</div>
		
</rapid:override> 
<%--筛选弹出层  --%>
<rapid:override name="filter_layer">
	<div id="filter_layer">
		<div id="filter_box">
			<h2 class="filter_menu"><a href="#" class="close_Btn" id="filter_close"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/201502brand/sx_icon.png" height="20"  /></a><p>筛选</p></h2>
			<div class="filter_content">
				<div class="category-box"  id="categoryItem">
					<h3>品类</h3>
					<ul>
						<c:forEach var="category" items="${activityIndex.result.categoryList}" varStatus="status">
							<c:choose>
								<c:when test="${status.index==0}">
									<li id ="categoryFirst" onclick="searchCategory('','${category.id}','${category.name}');" idFlag="${category.id}" nameFlag="${category.name}"  ctag='{"id":"SJ3","sid":"${searchConditions.topicId}","cid":"${category.id}"}'>${category.name}</li>
								</c:when>
								<c:otherwise>
									<li id ="${category.id}" onclick="searchCategory('','${category.id}','${category.name}');" ctag='{"id":"SJ3","sid":"${searchConditions.topicId}","cid":"${category.id}"}'>${category.name}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
				
				<div class="category-box"  id="brandItem">
					<h3>品牌</h3>
					<ul>
					</ul>
				</div>
				
				<div class="category-box"  id="sizeItem">
					<h3>尺码</h3>
					<ul>
					</ul>
				</div>
				
				<div class="color-box" id="colorItem">
					<h3>颜色</h3>
					<ul>
					</ul>
				</div>
				
				<div class="category-box" id="priceItem">
					<h3>价格区间</h3>
					<ul>
						<li id="0-500"  onclick="searchKey('price','0-500');" ctag='{"id":"SJ4","sid":"${searchConditions.topicId}","price":"0-500"}'>0-500</li>
						<li id="500-1000" onclick="searchKey('price','500-1000');" ctag='{"id":"SJ4","sid":"${searchConditions.topicId}","price":"500-1000"}'>500-1000</li>
						<li id="1000-2000" onclick="searchKey('price','1000-2000');" ctag='{"id":"SJ4","sid":"${searchConditions.topicId}","price":"1000-2000"}'>1000-2000</li>
						<li id="2000-5000" onclick="searchKey('price','2000-5000');" ctag='{"id":"SJ4","sid":"${searchConditions.topicId}","price":"2000-5000"}'>2000-5000</li>
						<li id="5000-10000" onclick="searchKey('price','5000-10000');" ctag='{"id":"SJ4","sid":"${searchConditions.topicId}","price":"5000-10000"}'>5000-10000</li>
						<li id="10000"  onclick="searchKey('price','10000');" ctag='{"id":"SJ4","sid":"${searchConditions.topicId}","price":">10000"}'>>10000</li>
					</ul>
				</div>
				<a href="javacript:;" id="finishBtn">确定</a>
				<input type="hidden" id="hasPageNum" name="hasPageNum" value="${hasPageNum }"/>
				<input type="hidden" id="checkApp" name="checkApp" value="${checkAPP }"/>	
			</div>
		</div>
		</div>
			<div id="hiddenValue2">
		<input type="hidden" id="oldPrice" name="oldPrice" cname="price" value="${searchConditions.price}"/>
		<input type="hidden" id="oldSize" name="oldSize"  cname="size" value="${searchConditions.size}"/>
		<input type="hidden" id="oldColor" name="oldColor" cname="color" value="${searchConditions.color}"/>
		<input type="hidden" id="oldColorName" name="oldColorName" cname="colorName" value="${searchConditions.colorName}"/>
		<input type="hidden" id="oldCategoryNo" name="oldCategoryNo" cname="categoryName" value="${searchConditions.categoryNo}"/>
		<input type="hidden" id="oldCategoryName" name="oldCategoryName" cname="categoryName" value="${searchConditions.categoryName}"/>
		<input type="hidden" id="oldBrandNo" name="oldBrandNo"  cname="brandNo"  value="${searchConditions.brandNo}"/>
		<input type="hidden" id="oldBrandName" name="oldBrandName" cname="brandName"  value="${searchConditions.brandName}"/>
		<input type="hidden" id="oldGender" name="oldGender" cname="gender" value="${searchConditions.gender}"/>
		<input type="hidden" id="oldOrder" name="oldOrder" cname="order" value="${searchConditions.order}"/>
		<input type="hidden" id="oldPostArea" name="oldPostArea" cname="postArea" value="${searchConditions.postArea}"/>
		<input type="hidden" id="oldStart" name="oldStart" cname="start" value="1"/>
		<input type="hidden" id="oldPageNo" name="oldPageNo" cname="pageNo"  value="1"/>
		<input type="hidden" id="oldTopicId" name="oldTopicId" cname="topicId" value="${searchConditions.topicId}"/>
		</div>
		
</rapid:override>

<%--优惠券弹层  --%>
<rapid:override name="popup_layer">
	<div class="overlay" id="overlay">
		<section class="modal modal-test">
			<div class="modal-hd">成功领取优惠券</div>
			<div class="modal-bd">
				<p>领取成功<br/>
				   您可以在<strong>“我的”</strong>的页面<strong>“优惠券”</strong>中查看
				</p>
			</div>
			<div class="modal-ft">
				<span class="btn-modal">知道了</span>
			</div>
		</section>		
	</div>
</rapid:override>

<rapid:override name="remind_Layer">
	<div id="remind_Layer" class="public_bg">
	    <div class="public_info">
	        <p class="public_info_title">提醒</p>
	        <p class='public_info_content public_center' >PRIME 金牌会员专享活动</p>
	        <p class='public_info_content public_center' >成为  PRIME 金牌会员后才可浏览</p>
	        <a href="${ctx}/user/right/infopro?topicId=${searchConditions.topicId}" class="remind_info_button">申请试用</a>
	    </div>
    </div>
</rapid:override>

<rapid:override name="login_Layer">
	<div id="login_Layer" class="public_bg">
	    <div class="public_info">
	        <p class="public_info_title">提醒</p>
	        <c:choose>
              	<c:when test="${checkAPP}">
			         <p class='public_info_content public_center' >PRIME 金牌会员专享活动</p>
			         <p class='public_info_content public_center' >去个人中心登录后查看</p>
                	 <a href="shangpinapp://phone.shangpin/actiongoindexpage" class="user_info_button">确认</a>
                </c:when>
                <c:otherwise>
                    <p class='public_info_content public_center' >PRIME 金牌会员专享，请登陆查看</p>
                	 <a href="javascript:;" class="login_info_button">确认</a>
                </c:otherwise>
           </c:choose>
	    </div>
    </div>
</rapid:override>

<rapid:override name="userInfo_Layer">
	<div id="userInfo_Layer" class="public_bg">
	    <div class="public_info">
	        <p class="public_info_title">提醒</p>
	        <p class='public_info_content public_center' >PRIME 金牌会员专享活动</p>
	        <p class='public_info_content public_center' >去个人中心升级为金牌会员</p>
	        <c:choose>
              	<c:when test="${checkAPP}">
                	 <a href="shangpinapp://phone.shangpin/actiongoindexpage" class="user_info_button">确认</a>
                </c:when>
                <c:otherwise>
                	    <a href="${ctx}/index" class="user_info_button">确认</a>
                </c:otherwise>
           </c:choose>
	    </div>
    </div>
</rapid:override>

<%@ include file="/WEB-INF/pages/common/common_mall_banner.jsp" %> 

