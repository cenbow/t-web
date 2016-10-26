<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<rapid:override name="custum">
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}"
		rel="stylesheet" />
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/vip/vip2016.css${ver}"
		rel="stylesheet" />
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
				.excute()
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				//图片懒加载
				.excute()
	</script>
</rapid:override>
<%-- 浏览器标题 --%>
<rapid:override name="title">
	购买 PRIME 金牌会员
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	购买 PRIME 金牌会员
</rapid:override>



<rapid:override name="content">
	<div class="container">
	
		<!--换img路径-->
		
		<!--支付失败-->
		<c:if test="${ !isSuccess }">
			<div class="purchase_box">
		        <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/member/error.png${ver}" width="94" height="94" alt="">
		        <p >支付失败！未能升级为 PRIME 金牌会员！</div>
		        <%-- <p class="vip_go"><a href="${ctx}/t/vip/buy">返回重试</a></div> --%>
		    </div>
		</c:if>
		
		<!--支付成功-->
		<c:if test="${isSuccess }">
			<div class="purchase_box">
		        <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/member/smile.png${ver}" width="94" height="94" alt="">
		        <p>恭喜您成为&nbsp; PRIME &nbsp;金牌会员<br>新品及全价商品享受8.5折优惠<br>300元现金券&nbsp; 已经充入您账户</p>
		    </div>
			<%-- <div class="smile-pop" style="display:block">
				<div class="smile-box">
			    	<div class="vip_icon"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/member/smile.png${ver}"></div>
			        <p>恭喜您成为&nbsp; PRIME &nbsp;金牌会员<br>新品及全价商品享受8.5折优惠<br>300元现金券&nbsp; 已经充入您账户</p>
			        <div class="vip_go"><a href="${ctx}/meet/791">立即去体验</a></div>
			    </div>
			</div> --%>
		</c:if>
		<!-- 页面内容end -->
	</div>

</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>