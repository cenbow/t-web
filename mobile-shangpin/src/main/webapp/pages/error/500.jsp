<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<style>
.no_result{
	padding-top:55px;
	text-align:center;
}
.no_result img{
	border:0;
}
.no_result span{
	padding:20px 0;
	font-size:16px;
	line-height:150%;
	color:#555;
}
.no_result img, .no_result span, .no_result a{
	display:block;
	margin:0 auto;
}
.no_result a{
	width:130px;
	height:38px;
	line-height:38px;
	background-color:#0700c6;
	border:1px solid #0700c6;
	color:#fff;
	font-size:14px;
	text-align:center;

}
.no_result a:hover{
	background-color:#0700c6;
	border:1px solid #0700c6;
	color:#fff;
}
</style>
</rapid:override>

<rapid:override name="content">
<div class="alContent">
  <!--内容区域 start-->
  <section class="detail_block">
    <div class="no_result">
      <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/404.png" width="95" height="95">
      <span>咦，出错了。</span>
     <a href="<c:url value='/'/>">去首页</a>
    </div>
  </section>
  <!--内容区域 end-->
</div>
</rapid:override>
<rapid:override name="footer">
</rapid:override> 
<%@ include file="/WEB-INF/pages/common/base.jsp" %> 