<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/newCategory.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
			loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/zepto.1.1.3.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/iscroll.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/categorylist150422.js${ver}")
	</script>
</rapid:override >
<rapid:override name="page_title">
	全部品牌
</rapid:override>
<rapid:override name="content">
  <div class="cate-box">
		<div class="pageContent" id="pageContent">
			<div class="pageBox">
				<a id="pageTop" name="pageTop"></a>
				<div class="tabs_box">
					<div class="tabs_Cell">
						<c:forEach var="brand" items="${brands.brandList}">
							<section class="brand_txtList">
							<c:if test="${fn:length(brand.brands)> 0}">
							<c:choose>
				        		<c:when test="${brand.capital=='#'}">
				        			<h3>${brand.capital}<a id="all" name="${brand.capital}"></a></h3>
				        		</c:when>
				        		<c:otherwise>
				        			<h3>${brand.capital}<a id="${brand.capital}" name="${brand.capital}"></a></h3>
				        		</c:otherwise>
	        				</c:choose>
							</c:if>
							<ul>
							<c:forEach var="b" items="${brand.brands}">
								<li ctag='{"id":"B1","bid":"${b.id}","bname":"${b.nameEN}"}'><a href="${ctx }/brand/product/list?brandNo=${b.id}&brandName=${b.nameEN}&postArea=0" ctag='{"id":"B1","bid":"${b.id}","bname":"${b.nameEN}"}'>${b.nameEN}</a></li>
							</c:forEach>
							</ul>
							</section>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
        <div class="letter_box1" id="categoryLetter">
            <div class="letter_list">
             	<c:forEach var="brand" items="${brands.brandList}">
	        		<c:if test="${fn:length(brand.brands)> 0}">
	        			<c:choose>
			        		<c:when test="${brand.capital=='#'}">
			        			<a href="#all">${brand.capital }</a>
			        		</c:when>
			        		<c:otherwise>
			        			<a href="#${brand.capital}">${brand.capital }</a>
			        		</c:otherwise>
		        		</c:choose>
	        		</c:if>
	        	</c:forEach>
             </div>
         </div>
     </div>  
</rapid:override>
<rapid:override name="down_page">
 </rapid:override>
 <rapid:override name="footer">
 </rapid:override>

<%@ include file="/WEB-INF/pages/common/common.jsp" %> 