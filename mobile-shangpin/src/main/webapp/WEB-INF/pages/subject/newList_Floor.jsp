<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/201502brand/brand.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/font-css/font-awesome.min.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/201502brand/swiper.min.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/201502brand/listFloor.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/meet/share.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
			loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js?${ver}" )
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js?${ver}" )
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js?${ver}" )
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/app_share_img.js?${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js?${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/fixed_public.js?${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js?${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/weixin/WXShareM.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/brandFloor.js?${ver}")
				.using("${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.excute();
	</script>
	
	<c:if test="${headPic!=null && headPic.js!=null}">
		${headPic.js}
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
    <div class="alContent">
      <!-- 头图 --> 
      <%-- 分享文案 --%> 
	 <c:if test="${activityIndex.operat.head.share!=null }">
	 		<input type="hidden" name="_shareUrl"  id="_shareUrl"  value="${activityIndex.operat.head.share.url}"/>
	 		<input type="hidden" name="_mainTitle"  id="_mainTitle"  value="${activityIndex.operat.head.share.title}"/>
			 <input type="hidden" name="_mainDesc"  id="_mainDesc"  value="${activityIndex.operat.head.share.title}"/>
			 <input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="${fn:replace(activityIndex.operat.head.share.pic,'-10-10','-640-256') }"/>
	 </c:if>
	 <c:if test="${checkAPP}">
		<div class="shang_share" >
			<a href="shangpinapp://phone.shangpin/actiongoshare?title=${fn:replace(activityIndex.operat.head.share.title,'&', '＆')}&desc=${fn:replace(activityIndex.operat.head.share.desc,'&', '＆')}&url=http://m.shangpin.com/subject/product/list?topicId=${searchConditions.topicId}" style="height:50px;"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/b_share.png" width="40" style="width:40px"></a>
		</div>
	 </c:if>
     <c:choose>
     	<c:when test="${headPic!=null&&headPic.html!=null}">
     		${headPic.html}
     	</c:when>
     	<c:otherwise>
     		<c:if test="${activityIndex.operat.head.share!=null }">
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
      <div class="tabSlider"> 
			
	      <!--导航-->
	      <div class="menu-nav"></div>
 		  <div class="list-box">
 		  <c:choose>
			<c:when test="${fn:length(floorList) == 0 }">
				<p class="brand_noresults">没有筛选结果，重新筛选下吧</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="subjectFloor" items="${floorList}" varStatus="searchStatus">
				 <input type="hidden" id="topicId" value="${searchConditions.topicId}">
	            	<h3 class="title_banner" data-t="${subjectFloor.title }">
	            		<c:if test="${subjectFloor.pic!=null &&subjectFloor.pic != ''}">
		                	<%-- <a href=""><img src="${subjectFloor.pic }" /></a> 楼层图片可点击跳转，前期定数据接口没有这个跳转地址，本次先取消跳转 --%>
		                	<img src="${subjectFloor.pic }" />
	            		</c:if>
	            		<c:if test="${subjectFloor.html!=null&&subjectFloor.html != ''}">
	            			${subjectFloor.html}
	            		<!-- 	<script type="text/javascript">
	            				${subjectFloor['js']}
	            			</script>
	            			<style type="text/css">
	            				${subjectFloor['css']}
	            			</style> -->
	            		</c:if>
	                </h3>
	                <div class="prod_list clr">
	                <c:forEach var="product" items="${subjectFloor.productList}" varStatus="status">
		                    <div class="list_box">
		                    	<c:choose>
									<c:when test="${checkAPP}">
										<a href="shangPinApp://phone.shangpin/actiongodetail?productid=${product.productId}&topicId=${searchConditions.topicId}">
									</c:when>
									<c:otherwise>
										<a href="<c:url value='/product/detail?productNo=${product.productId}&topicId=${searchConditions.topicId}&picNo=${product.picNo}'/>"  onclick="_smq.push(['custom',${product.productId},'',${product.productName}]);">
									</c:otherwise>
								</c:choose>
                                            <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif"  lazy="${fn:substring(product.pic,0,fn:indexOf(product.pic,'-'))}-320-426.jpg" style="opacity: 1;" ctag='{"id":"SJ1","sid":"${searchConditions.topicId }","spu":"${product.productId}"}'/>
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
										<em class="${product.nomal_ProductPrice.compareHasLine eq '1' ? 'promotion-price-hasline' : 'promotion-price'}" style="color:${product.nomal_ProductPrice.compareColor}">${product.nomal_ProductPrice.compareDesc}</em>
									</span>
		                          </div>
		                    </div>
	                </c:forEach>
                </div>
			</c:forEach>	 		  
			</c:otherwise>
	 	 </c:choose>
         </div>
	</div>
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
<%@ include file="/WEB-INF/pages/common/common_mall_home_banner.jsp" %> 

