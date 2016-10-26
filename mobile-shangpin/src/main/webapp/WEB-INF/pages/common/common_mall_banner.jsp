<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<%-- 非首页页面全部依赖这个页面 --%>

<rapid:override name="header">
	<c:if test="${!checkAPP}">
	    <!--头部 start-->
		<div class="topFix">
		    <section id="topMenu">
		        <div class="topBack">
		        
		        <a href="javascript:history.go(-1);" class="backBtn" id="_backBtn">&nbsp;</a>
		        <script type="text/javascript">
		        	function shoudBackIndex(){
		        		var referer = document.referrer;
		        		if(referer!=''){
		        			var paramIdx=referer.indexOf("?");
		        			if(paramIdx>0)
		        				referer=referer.substring(0,paramIdx);
		        		}
		        		console.log(referer);
		        		if(referer.substring('t/act/red/1')>0){
		        			try{
			        			document.getElementById("_backBtn").href="${ctx}/subject/product/list?topicId=60908396";
		        			}catch( e){}
		        		}
		        	}
		        </script>
		        		<span class="top-title">
			        		<rapid:block name="page_title">
			        		尚品网_全球时尚轻奢购物网站
			        		</rapid:block>
			        	</span>
		        <ul class="alUser_icon clr">
		     <%--    <li><a href="/mshangpin/index"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/home_icon.png" width="25" height="25" alt="首页"></a></li> --%>
<%-- 		            <li><a href="<c:url value='/index'/>"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/home_icon.png" width="25" height="25" alt="首页"></a></li> --%>
               			<li><a href="javascript:;" class="fillBtn"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/filter_icon.png" width="25" height="25" alt=""></a></li>
		        </ul>
		        </div>
		    </section>
		</div>
		<!--头部 end-->
	</c:if>
</rapid:override>

<%@ include file="/WEB-INF/pages/common/base_mall_banner.jsp" %> 