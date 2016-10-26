<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="title">
	  910会员日
</rapid:override>
<rapid:override name="custum">
<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/star-packet/red_32/base.css${ver}" rel="stylesheet" />
<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/star-packet/red_698/packet.css${ver}" rel="stylesheet" />
</rapid:override>
<rapid:override name="content">
    <input type="hidden" name="_shareUrl"  id="_shareUrl"  value="http://m.shangpin.com/t/act/red/1"/>
	<input type="hidden" name="_currentUrl" id="_currentUrl"  value=""/>
    <input type="hidden" name="type"  id="type"  value="${type}"/>
    <input type="hidden" name="_mainTitle"  id="_mainTitle"  value="这么尖儿的货还有福利！"/>
    <input type="hidden" name="_mainDesc"  id="_mainDesc"  value="910尚品会员日几万款尖儿货首发，竟然还有50元红包太！幸！福！"/>
    <input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="${cdn:pic(pageContext.request)}/styles/shangpin/images/star-packet/red_698/300-300.jpg"/>
    <%@ include file="/WEB-INF/pages/star/1698/info.jsp"%>
	
</rapid:override>
<rapid:override name="static_file">
    <script src="${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}" type="text/javascript" charset="utf-8"></script>
	<script src="${cdn:js(pageContext.request)}/styles/shangpin/js/star-packet/20151020/jquery.min.js${ver}" type="text/javascript" charset="utf-8"></script>
	<script src="${cdn:js(pageContext.request)}/styles/shangpin/js/star-packet/20151020/weixin_ready.js${ver}" type="text/javascript" charset="utf-8"></script>
	<script src="${cdn:js(pageContext.request)}/styles/shangpin/js/star-packet/red/packet.js${ver}" type="text/javascript" charset="utf-8"></script>
</rapid:override>
<%@ include file="/WEB-INF/pages/star/1698/red_packet_base.jsp" %>