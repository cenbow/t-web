<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}"
		rel="stylesheet" />
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/swiper.min.css"
		rel="stylesheet" />
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
	<style type="text/css">
		body{
			background: #ffffff;
		}
		.z-vippower{
			margin-top: 0px;
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
	<c:set var="_isVip" value="${sessionScope.mshangpin_user.isVip() }"></c:set>
	<c:set var="_isEip" value="${sessionScope.mshangpin_user.isEip() }"></c:set>
	<c:set var="_isTry" value="${sessionScope.mshangpin_user_level.isTryVip() }"></c:set>
	<c:set var="_canTry" value="${sessionScope.mshangpin_user.canTryVip() }"></c:set>
	<c:set var="_canTry30" value="${sessionScope.mshangpin_user.isShangUser() }"></c:set>
	<c:set var="_showCouponTip" value="${ true }"></c:set>
	<div class="z-vipmember_h" style="margin-top: 0px">
		<div class="z-vipmember_title">${sessionScope.mshangpin_user.name ne ''? sessionScope.mshangpin_user.name : sessionScope.mshangpin_user.nickName}</div>
		<div class="z-vipmenber_desc">
			<c:if test="${! _isVip }">
				<a class="vipmenber_icon" href="javascript:;"></a>
			</c:if>
			<c:if test="${_isVip && !_isEip}">
				<a class="vipmenber_icon vipmenber_gold" href="javascript:;"></a>
				<span class="vipmenber_time">有效期至${sessionScope.mshangpin_user_level.memberEndTime }</span>
			</c:if>
			<c:if test="${_isEip}">
				<a class="vipmenber_icon vipmenber_super" href="javascript:;"></a>
				<span class="vipmenber_time">有效期至${sessionScope.mshangpin_user_level.memberEndTime }</span>
			</c:if>
		</div>
		<div class="">
			<span class="vipmenber_money">近12个月内累计消费金额
				¥${sessionScope.mshangpin_user_level.consumedAmount }</span> <a
				href="${ctx}/help/member/reading-scores.html" class="vipmenber_ques"></a>
		</div>
	</div>
	<div style="background:#F6F6F6;height: 5px;"></div>
	<c:if test="${! _isVip}">
		<div class="total">近12个月内累计消费金额满 ¥10,000，即可成为 PRIME 金牌会员</div>
		<div class="z-vippower">
			<div class="vippower_title">PRIME 金牌会员专享以下特权：</div>
			<p>1.全价商品享受VIP专属优惠</p>
			<p>2.每月会员日全站免国际、国内运费</p>
			<p>3.15天无理由退换货</p>
		</div>
		<div class="z-vipmember_btn" <%--style="height: 500px"--%>>
			<c:if test="${ !_canTry }">
				<a href="${ctx}/t/vip/buy">¥299/年购买 PRIME 金牌会员</a>
				<p class="vip_time">
		           <span style="position: relative">购买金牌会员，立返300元现金券礼包，点击查看<i></i></span>
		       	</p>
			</c:if>
			<c:if test="${ _canTry }">
				<a href="${ctx}/user/right/experience" class="vip_try">试用体验15天 <br /> <span
					style="">开启 PRIME 金牌会员权益</span></a>
				<c:set var="_showCouponTip" value="${false }"></c:set>
				<p class="vip_time">
					<span style="position: relative" onclick="window.location.href='${ctx}/t/vip/buy'">购买 PRIME 金牌会员，立返300元现金券礼包<i></i>
					</span>
				</p>
			</c:if>
		</div>
	</c:if>
	<c:if test="${_isEip }">
		<div class="z-vippower" <%--style="height: 500px"--%>>
			<div class="vippower_title">SUPERME 至尊会员专享以下权益：</div>
			<p>1.全价商品享受8.5折优惠</p>
	        <p>2.客服绿色通道,专属时尚顾问</p>
	        <p>3.全年全站免国际、国内运费</p>
	        <p>4.365天无理由退换货</p>
		</div>
	</c:if>
	<c:if test="${_isVip && ! _isEip}">
		<div class="z-vippower" <c:if test="${ !_isTry }"> <%--style="height:500px"--%> </c:if> >
			<div class="vippower_title">PRIME 金牌会员专享以下特权：</div>
			<p>1.全价商品享受8.5折优惠</p>
			<p>2.每月会员日全站免国际、国内运费</p>
			<p>3.15天无理由退换货</p>
		</div>
		<c:if test="${ _isTry }">
			<div class="z-vipmember_btn" <%--style="height: 500px"--%>>
				<a href="${ctx}/t/vip/buy">¥299/年购买正式 PRIME 金牌会员</a>
				<p class="vip_time">
		           <span style="position: relative">购买金牌会员，立返300元现金券礼包，点击查看<i></i></span>
		       </p>
			</div>
		</c:if>
	</c:if>
	<c:if test="${ _canTry30}">
		<div class="z-vipmember_btn" <%--style="height: 500px"--%>>
			<a href="${ctx}/share/me">员工专属福利</a>
		</div>
	</c:if>
	<%-- 非可试用情况可出现下面弹框 --%>
	<c:if test="${_showCouponTip}">
		<div class="vip_info_bg">
		    <div class="vip_info">
		        <p class="info_title">优惠券说明</p>
		        <p>1．购买299元/年会员卡,立返300现金券礼包</p>
		        <p>2．300现金券包含3张100元现金券，全网可用</p>
		        <a href="javascript:;" class="vip_info_button">知道了</a>
		    </div>
		</div> 
	</c:if>
</rapid:override>
<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>