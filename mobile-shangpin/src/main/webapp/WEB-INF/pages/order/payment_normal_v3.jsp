<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request) }/styles/shangpin/css/page/payment.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
		loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.using("${cdn:css(pageContext.request)}/styles/shangpin/js/payment.js${ver}")
				.excute();
	</script>
</rapid:override>

<rapid:override name="page_title">
	提交成功
</rapid:override>

<rapid:override name="content">
	<div class="alContent">
		<div class="paymet_block">
			<div class="paymet_success">
				<h3>订单已提交</h3>
				<p class="tips">30分钟后订单将被取消，请您尽快支付</p>
			</div>
			<fieldset>
				<c:if test="${giftcardbalance!='0.00' && canUseGiftCard == '1' }">
					<p class="giftCard">
						<a id="giftCardpay" href="#"> 礼品卡余额 &yen;<b id="giftcardbalance">${giftCardBalance}</b><br>
								<%--  <i>使用礼品卡为本次支付 &yen;<b id="paycard">${canUseGiftCard}</b></i>--%>
						</a>
						<input type="password" id="giftPsd" name="password" placeholder="请输入密码（6-20位数字+字母结合）" required class="giftCard" /><br />
						<c:if test="${phoneNum != ''&& phoneNum != null}">
							<a href="${ctx}/order/card/password?mobile=${phoneNum}" class="r_link">忘记密码</a>
						</c:if>
						<c:if test="${phoneNum == '' || phoneNum == null }">
							<a href="#" class="r_link">你的礼品卡未绑定手机号，请联系客服4006-900-900找回密码</a>
						</c:if>
					</p>
					<p class="price">您需要支付 &yen;<b id="realpay"></b></p>
				</c:if>

				<c:forEach items="${payments}" var="pay">
					<p class="payment-method">
						<span class=${pay.clazz2} gateWay="${pay.gateway}" url=${pay.url} >
							<a href="javascript:;" ${pay.isSelected == '1' ? "class='cur'" : ""}>
								<em>${pay.name}</em>
								<br>
							</a>
						 </span>
					</p>
				</c:forEach>

				<div class="payment_submit">
					<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
					<input type="hidden" id="date" name="date" value="${date}"/>
					<input type="hidden" id="phoneNum" name="phoneNum" value="${phoneNum}"/>
					<input type="hidden" id="onlineAmount" name="onlineAmount" value="${onlineAmount}"/>
					<input type="hidden" id="canUseGiftAmount" name="canUseGiftAmount" value="${canUseGiftAmount}"/>
					<input type="hidden" id="realPayAmount" name="realPayAmount" value="${realPayAmount}"/>
					<a id="pay" href="javascript:normal_pay();" class="payment_btn">立即支付</a>
				</div>
			</fieldset>
		</div>
	</div>
</rapid:override>
<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %> 