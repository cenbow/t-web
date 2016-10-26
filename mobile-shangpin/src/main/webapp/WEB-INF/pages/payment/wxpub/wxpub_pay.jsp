<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>


<rapid:override name="custum">
	<script src="${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}" type="text/javascript" charset="utf-8"></script>
</rapid:override >


<%-- 浏览器标题 --%>
<rapid:override name="title">
	我的订单
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	订单管理
</rapid:override>

<rapid:override name="content">

<body>
<script>
	$(document).ready(function() {
		var appId = $("#appId").val();
		var timestamp = $("#timeStamp").val();
		var packageStr = $("#package").val();
		packageStr = packageStr.replace(/\+/g, "%20");
		packageStr = packageStr.replace("&amp;", "&");
		var paySign = $("#paySign").val();
		var signType = $("#signType").val();
		var noncestr = $("#nonceStr").val();
		weixinSendPay(appId, timestamp, packageStr, paySign, signType, noncestr);
	});


	function weixinSendPay(appId, timestamp, packageStr, paySign, signType,  noncestr) {
		// 公众号支付
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
			WeixinJSBridge.invoke('getBrandWCPayRequest', {
				"appId": appId, // 公众号名称，由商户传入
				"timeStamp": timestamp, // 时间戳
				"nonceStr": noncestr, // 随机串
				"package": packageStr,// 扩展包
				"signType": signType, // 微信签名方式:1
				"paySign": paySign
				// 微信签名
			}, function (res) {
				var back = $("#backurl").val();
				if (res.err_msg == "get_brand_wcpay_request:ok") {
					location.href =  back;
				} else {
					location.href =  back;
				}
			});

		});
	}

</script>

		<input type="hidden" id="backurl" value="${backUrl}">
		<c:forEach var="pa" items="${payParam}">
			<input type="hidden" id="${pa.key }" name="${pa.key }" value="${pa.value }"/>
		</c:forEach>
	

</body>
  
</rapid:override> 

 <%-- 页面的尾部 --%>      
 <rapid:override name="footer">
 
 </rapid:override> 

<%@ include file="/WEB-INF/pages/common/common.jsp" %> 
