<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%> 

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/new_member.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/public.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
			loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/homeScroll.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.excute()
	</script>
	<!--集成脚本加载 -->
	<%--<script language="javascript" type="text/javascript">

		NTKF_PARAM = {
			siteid:"kf_9986",                    	//企业ID，为固定值，必填
			settingid:"kf_9986_1355827406710",   	//接待组ID，为固定值，必填
			uid:"${mshangpin_user.userId}",         //用户ID，未登录可以为空，但不能给null，uid赋予的值在显示到小能客户端上
			uname:"${mshangpin_user.name}",         //用户名，未登录可以为空，但不能给null，uname赋予的值显示到小能客户端上
//			isvip:"1",                          //是否为vip用户，0代表非会员，1代表会员，取值显示到小能客户端上
			userlevel:"${mshangpin_user.lv}",       //网站自定义会员级别，0-N，可根据选择判断，取值显示到小能客户端上
//			erpparam:"abc",                     //erpparam为erp功能的扩展字段，可选，购买erp功能后用于erp功能集成
			itemid: "",				       	//(必填)商品ID
			itemparam:"m"				   			//(选填)itemparam为商品接口扩展字段，用于商品接口特殊要求集成
		}
	</script>
	<!--基础脚本加载 -->
	<script type="text/javascript" src="http://download.ntalker.com/t2ds/ntkfstat.js" charset="utf-8"></script>--%>
</rapid:override >

<%-- 页标题 --%>
<rapid:override name="page_title">
	我的尚品
</rapid:override>

<rapid:override name="content">
	<div class="member_box">
	    <c:set var="_isVip" value="${sessionScope.mshangpin_user.isVip() }"></c:set>
	    <c:set var="_isEip" value="${sessionScope.mshangpin_user.isEip() }"></c:set>
	    <c:set var="_isTry" value="${sessionScope.mshangpin_user_level.isTryVip() }"></c:set>
	    <c:set var="canTryVip" value="${sessionScope.mshangpin_user.canTryVip() }"></c:set>
	    <header>
			<img src="${sessionScope.mshangpin_user.icon}" title="用户头像" />
	        <h5>${sessionScope.mshangpin_user.name ne ''? sessionScope.mshangpin_user.name : sessionScope.mshangpin_user.nickName}</h5>
	        <a href="${ctx}/user/right/about">
	        <c:if test="${ _isVip && !_isEip }">
	        		<p style="width:135px">
	        		PRIME 金牌会员
		        	<c:if test="${_isTry }">
		        	(试用)
		        	</c:if>
	        	</p>
	        </c:if>
	        <c:if test="${!_isVip }">
	        	<c:if test="${canTryVip }">
		        	<p style="width:130px">试用 PRIME 金牌会员</p>
	        	</c:if>
	        	<c:if test="${!canTryVip }">
		        	<p style="width:130px">立享 PRIME 金牌会员</p>
	        	</c:if>
	        </c:if>
	        <c:if test="${_isEip }">
	        	<p style="width:130px">SUPREME 至尊会员</p>
	        </c:if>
	        </a>
	    </header>

	     <ul class="menu">
            	<li><a href="${ctx}/collect/product/list?pageIndex=1&pageSize=20&shopType=1" class="favorPorduct">愿望清单</a></li>
            	<li class="line"><a href="${ctx}/order/list?statusType=1" class="order">订单</a></li>
                <li><a href="${ctx}/help/index.html" class="service">客服</a></li>
                <li class="line"><a href="${ctx}/coupon/list" class="coupon">卡券</a></li>                
                <li class="line"><a href="${ctx}/address/list" class="addr">收货地址管理</a></li>
	            <li class="line"><a href="${ctx}/user/toFindPwd" class="change">修改密码</a></li>
	           <%-- <li><a href="${ctx}/help/index.html" class="help">帮助</a></li>--%>
          </ul>
	</div>
	
	<rapid:override name="downloadAppShowBottom">
	</rapid:override>
</rapid:override > 

<%@ include file="/WEB-INF/pages/common/another_common_mall_banner.jsp" %>
