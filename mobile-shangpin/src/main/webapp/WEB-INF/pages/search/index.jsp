<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<rapid:override name="custum">
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/search/search.css${ver}"
		rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
	var ver = Math.random();
		loader = SP.core
		    .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
		    .using("${cdn:js(pageContext.request)}/styles/shangpin/js/201502brand/iscroll.js${ver}")
			.excute()
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/tabslider.js${ver}")
			.excute()
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
			.using("${cdn:js(pageContext.request)}/styles/shangpin/js/search/search.js${ver}");
	</script>
</rapid:override>
<%-- 浏览器标题 --%>
<rapid:override name="title">
	搜索列表
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	搜索列表
</rapid:override>
<rapid:override name="content">
	<!--search-->
	<div class="search-page js-search-page">
    
    	<div class="search-header">
          <form class="search-form" method="get" action="${ctx}/search/list">              
            <input class="search-input" type="search" placeholder="搜索商品、品牌、货号" name="keyword" maxlength="25">
            <span class="search-btn"></span> 
            <span class="search-cancel-btn"></span>
          </form>
          <a href="javascript:history.go(-1);" class="search-close-btn">取消</a>
        </div>
        <div class="search-fast-box">
         <c:if test="${searchKeyword !=null}">
            <h3 class="search-title">${searchKeyword.title}</h3>
            <div class="search-word-list">
              <c:forEach items="${searchKeyword.list}" var="model">
                  <%@ include file="/WEB-INF/pages/search/model_rule.jsp"%>
                     ${model.name }
	               </a>
             </c:forEach>
            </div>
         </c:if> 
         <c:if test="${fn:length(searkeys) >0}">
            <div class="line"></div>
            <h3 class="search-title">历史搜索</h3>
            <div class="search-word-list js-history-list">
              <c:forEach items="${searkeys}" var="va" begin="0" end="9">
                 <a href="${ctx}/search/list?keyword=${va}">${va}</a>
              </c:forEach>
            </div>
            <a href="javascript:;" class="search-clear-btn">清空历史搜索</a>
         </c:if>
        </div>
        
        <!--联想数据-->
        <div class="search-dynamicd-data">	
        </div>

    </div>    
</rapid:override>
<rapid:override name="statistics">
</rapid:override>
<rapid:override name="footer">
</rapid:override>
<%@ include file="/WEB-INF/pages/common/base_meet.jsp"%>

