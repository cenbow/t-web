<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<%-- 非首页页面全部依赖这个页面 --%>
<rapid:override name="header">

<c:if test="${!checkAPP}">
    <!--头部 start-->
	<div class="topFix">
	    <section id="topMenu">
	        <div class="topBack" >
	        <a href="javascript:history.go(-1);" class="backBtn">&nbsp;</a>
	        	<span>
	        		<rapid:block name="page_title">
	        		尚品网_全球时尚轻奢购物网站
	        		</rapid:block>
	        	</span>
	        	
	        <ul class="alUser_icon clr">
	            <li><a href="<c:url value='/index'/>"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/home_icon.png" width="25" height="25" alt="首页"></a></li>
				<li>
					<a href="<c:url value='/cart/list'/>" ctag='{"id":"H3"}'><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/pic_newshopping.png" width="26" height="26" alt="购物袋" >
						<em id="forCartCount" style="display:block">${mshangpin_user.cartCount}</em>
					</a>
				</li>
	            <%--
	            <li><a href="#"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/order_icon.png" width="25" height="25" alt="购物车"></a></li>
	            <li><a href="${ctx}/user/info"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/user_icon.png" width="25" height="25" alt="账户"></a></li>
	            --%>
	        </ul>
	        </div>
	    </section>
	</div>
	<!--头部 end-->
</c:if>
</rapid:override>

<%@ include file="/WEB-INF/pages/common/base_banner.jsp" %> 