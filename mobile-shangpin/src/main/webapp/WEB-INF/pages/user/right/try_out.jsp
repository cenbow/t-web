<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}"
		rel="stylesheet" />
	<%-- <link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/swiper.min.css"
		rel="stylesheet" /> --%>
	<link rel="stylesheet"
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/font-css/font-awesome.min.css" />
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/vip/vip2016.css${ver}"
		rel="stylesheet" />
	<!--页面专用CSS-->

	<script
		src="${cdn:css(pageContext.request)}/styles/shangpin/js/core.js"
		type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript" charset="utf-8">
		var ver = Math.random();
		loader = SP.core
				.install(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
				//jquery库文件
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
				//图片懒加载
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js${ver}")
				.excute()
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				//图片懒加载
				.excute()
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/fClub/fClub.js${ver}") //页面专用JS
				.excute();
	</script>
<style>
	#begin_img {
		position: relative;
		left: 0px;
		top:0px
	}
	
	.begin_btn {
		position: absolute;
		bottom: 4%;
		width: 90%;
		line-height: 44px;
		left: 5%;
		font-size: 18px;
		background: #0700c5;
		color: #fff;
		text-align: center;
	}
</style>
</rapid:override>
<%-- 浏览器标题 --%>
<rapid:override name="title">
	尚品会员
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	尚品会员
</rapid:override>
<rapid:override name="content">
    <div id="begin_img">
    	 <div class="begin_btn" onclick="javascript:window.location.href='${ctx}/user/right/infopro'">开启试用</div>
    </div>
<script>
	window.onload=function(){
		var oBegin=document.getElementById('begin_img');
		oBegin.style.width=document.documentElement.clientWidth+'px';
		oBegin.style.height=document.documentElement.clientHeight-45+'px';
		oBegin.style.background='url("${cdn:pic(pageContext.request)}/styles/shangpin/images/member/begin-img.png") no-repeat center top';
		oBegin.style.backgroundSize='100% 100%';
	}
</script>
</rapid:override>
<rapid:override name="footer">

</rapid:override>

<%@ include file="/WEB-INF/pages/common/common.jsp"%>