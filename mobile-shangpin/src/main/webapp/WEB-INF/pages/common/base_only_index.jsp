<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<%--尚品网title --%>
<title><rapid:block name="title">
	        	尚品网_高端时尚购物平台_官网授权|海量新品|全场2折起
	   		</rapid:block></title>
<%--通用的meta设置 --%>
<rapid:block name="meta">
	<meta content-type="text/html" charset="utf-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
	<%-- 开启对web app程序的支持 --%>
	<meta name="apple-mobile-web-app-capable" content="yes">
	<%-- 全屏模式浏览 --%>
	<meta name="apple-touch-fullscreen" content="yes">
	<%-- 改变Safari状态栏的外观 --%>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<%-- 禁止自动识别5位以上数字为电话 --%>
	<meta name="format-detection" content="telephone=no">
</rapid:block>

<%--自定义引入css,jss等 --%>
<script
	src="${cdn:js(pageContext.request)}/styles/shangpin/js/core.js${ver}"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${cdn:js(pageContext.request)}/styles/shangpin/js/fixed_public.js${ver}"
	type="text/javascript" charset="utf-8"></script>
<link
	href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}"
	rel="stylesheet" />
<link
	href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/banner.css${ver}"
	rel="stylesheet" />
<script type="text/javascript" charset="utf-8">
	var _smq = _smq || [];
	_smq.push(['_setAccount', '1d17bef', new Date()]);
	_smq.push(['_setCustomVar', 1, "${sessionScope.mshangpin_user.userId}", 1]);
	_smq.push(['pageview']);


	(function() {
	var sm = document.createElement('script'); sm.type =
	'text/javascript'; sm.async = true;
	sm.src ='${cdn:js(pageContext.request)}/styles/shangpin/js/sitemaster/collect_shangpin.js${ver}';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(sm, s);
	})();
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?2988058a4ad690fc3a64b1d129f0eed8";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();

</script>

<%@ include file="/WEB-INF/pages/common/google.jsp"%>
<rapid:block name="custum">

</rapid:block>
</head>
<body>
	<div class="container">
		<%--下拉窗口 --%>
		<rapid:block name="appLayer">

		</rapid:block>
		<rapid:block name="downloadAppShowHead">
		   <c:if test="${!checkAPP&&!checkTCL}">
			<%@ include file="/WEB-INF/pages/common/download_app_show_head.jsp"%>
		   </c:if>
		</rapid:block>
		<%--头部 --%>
		<rapid:block name="header">
		 <c:if test="${!checkAPP && !shangpinKE }">
			 <div class="topFix">
				        <section id="topMenu">
				            <div class="topBack" >
				            <a class="category-trigger" ctag='{"id":"H6"}' href="${ctx}/category/page"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/class_icon.png" width="25" height="25" ctag='{"id":"H6"}'></a>
				            <a class="top-search"  href="${ctx}/search/index" ctag='{"id":"H4"}' ><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/search/search_icon_home.png" width="25" height="25" ></a>
				            <span class="top-title"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/splogo.png${ver}" width="46" height="21"></span>
				            <ul class="alUser_icon clr">
				                <li><a href="<c:url value='/index'/>"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/home_icon.png" width="25" height="25" alt="首页"></a></li>
								<li>
									<a href="<c:url value='/cart/list'/>" ctag='{"id":"H3"}'><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/pic_newshopping.png" width="26" height="26" alt="购物袋" >
										<em id="forCartCount" style="display:block">${mshangpin_user.cartCount}</em>
									</a>
								</li>
				                <li><a href="${ctx}/user/home"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/user_icon.png" width="25" height="25" alt="账户"></a></li>
				            </ul>
				            </div>
				        </section>
		     </div>
		</c:if>
		</rapid:block>

		<c:choose>
			<c:when test="${!checkAPP}">
				<div class="alContent">
			</c:when>
			<c:otherwise>
				<div class="alContent" style="margin-top: 0;">
			</c:otherwise>
		</c:choose>
		<%--下载框展示 --%>
			<rapid:block name="downloadAppShowBottom">
			   <c:if test="${!checkAPP}">
				<%@ include file="/WEB-INF/pages/common/download_app_show_bottom.jsp"%>
			   </c:if>
			</rapid:block>
		
		<input name="ctx" id="ctx" value="${pageContext.request.contextPath}"
			type="hidden" />
			<input id="_iswx" name="_iswx" type="hidden" value="${checkWX }"/>
			<input id="_isapp" name="_isapp" type="hidden" value="${checkAPP }"/>
			<input type="hidden" name="imei" id="imei" value="${imei }"/>
                <input name="isVip" id="isVip" value="${_isVip}" type="hidden"/>
		<%-- 页面的内容 --%>
		<rapid:block name="content">

		</rapid:block>
	</div>

	<%-- 页面的尾部 --%>
	<rapid:block name="footer">
		<c:if test="${!checkAPP}">
		 <%@ include file="/WEB-INF/pages/common/app_download.jsp"%>
		</c:if>
		<c:if test="${!checkWX&&!checkAPP}">
			<%@ include file="/WEB-INF/pages/common/index_footer.jsp"%>
		</c:if>
	</rapid:block>
	<%--针对品牌、品牌商品列表搜索弹出层 --%>
	<rapid:block name="filter_layer">
		
	</rapid:block>
	
	<%--首页特卖弹出层 --%>
	<rapid:block name="popup_layer">
		
	</rapid:block>
	
	</div>
	<rapid:block name="statistics">
			 <%@ include file="/WEB-INF/pages/common/statistics.jsp"%>
	</rapid:block>
		
</body>
</html>