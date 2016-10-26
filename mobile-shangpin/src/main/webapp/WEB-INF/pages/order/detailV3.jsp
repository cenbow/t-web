<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/order/order_detail.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/2016Mapp/payment.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
			loader = SP.core.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/order/order_detail.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/topFix.js${ver}")
				.excute();


	</script>
</rapid:override >
<%-- 浏览器标题 --%>
<rapid:override name="title">
	订单详情
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	订单详情
</rapid:override>

<rapid:override name="downloadAppShowHead">

</rapid:override>

<rapid:override name="header">

</rapid:override>

<rapid:override name="content">

<input type="hidden" id="currentTimes" name="currentTimes" value="${currentTimes}">

<div id="order_detail">

	<c:if test="${!checkAPP}">
		<div class="topFix" id="order_head">
			<section>
				<div class="topBack" >
					<a href="javascript:history.go(-1);" class="backBtn">&nbsp;</a>
					<span class="top-title">订单详情</span>
					<ul class="alUser_icon clr">
						<li><a href="<c:url value='/'/>"><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/home_icon.png${ver}" width="25" height="25" alt="首页"></a></li>
					</ul>
				</div>
			</section>
		</div>
		<%--<div class="alContent" id="order_content" >--%>
	</c:if>
		<%--<c:otherwise>
			<div class="alContent" id="order_content" style="margin-top: 0;">
		</c:otherwise>--%>
	<div class="paymet_block">
		<form name="login" id="J_Login">
			<fieldset>
				<legend class="first_leg"></legend>
				<p class="selected order-type">下单时间：${orderDetail.orderTime}</p>
				<p class="selected order-type">订单编号：${orderDetail.mainOrderId } <em>${orderDetail.statusName}</em></p>
				<c:if test="${orderDetail.orderList[0].detail[0].giftType eq '1'}">
					<span class="e-gift-card">您选购的是电子礼品卡，订单支付成功后，您可在支付成功的页面或"我的尚品-礼品卡"查看购买记录</span>
				</c:if>

				<!--收货人信息地址/配送信息-->
				<p class="selected">
					<a href="#" style="background: none">
						<em>${orderDetail.receive.name }</em>
						<i class="phone">${orderDetail.receive.tel}</i>
						<span style="display: block;margin: 5px auto">${orderDetail.receive.cardID}</span>
						<span>${orderDetail.receive.provName }${orderDetail.receive.cityName}${orderDetail.receive.areaName }${orderDetail.receive.townName }${orderDetail.receive.addr}</span>
					</a>
				</p>

				<!--商品信息-->
				<c:forEach var="orderList" items="${orderDetail.orderList}">
					<p class="selected order-type">${orderList.title}</p>
					<c:forEach  var="detail" items="${orderList.detail}">
						<p class="pord_info">
							<c:choose>
								<c:when test="${detail.giftType eq '2'}">
									<a href="${ctx}/giftCard/cardDetail?productNo=${detail.id}&type=2" class="clr">
								</c:when>
								<c:when test="${detail.giftType eq '1'}">
								<a href="${ctx}/giftCard/cardDetail?productNo=${detail.id}&type=1" class="clr">
									</c:when>
								<c:otherwise>
									<a href="${ctx}/product/detail?productNo=${detail.id}" class="clr">
								</c:otherwise>
							</c:choose>
									<img src="${fn:substring(detail.pic,0,fn:indexOf(detail.pic,'-'))}-112-134.jpg" width="56" height="67"/>
									<ins>
										<i>${detail.brandNameEN}<br>${detail.name}</i>
										<c:forEach var="attr" items="${detail.attribute}">
										<em>${attr.name}: ${attr.value}</em>
										</c:forEach>
										<br>
										<em class="price">&yen;${detail.price} <b>x${detail.quantity}</b> </em>
									</ins>
								</a>
						</p>
					</c:forEach>
				</c:forEach>

				<%--<p class="loadMore"><a href="#">更多商品</a></p>--%>
				<!--礼品卡-->
				<c:if test="${orderDetail.isusablegiftcardpay == '1'}">
				<p class="select-invoice giftcard">
					<a href="#">礼品卡</a><!--class="discount_btn" 礼品卡弹层-->
					<em class="credit" ${orderDetail.isUseGiftCard=='1' ? 'style=\"display: none;\"' : ''}>&yen;${orderDetail.giftCardBalance}</em>
					<em class="used" ${orderDetail.isUseGiftCard=='1' ? 'style=\"display: inline;\"' : ''}>已使用礼品卡<i>&yen;${orderDetail.canUseGiftAmount}</i></em>
					<span class="select-option ${orderDetail.isUseGiftCard=='1' ? "cur": ""}" id="isUse" balance="${orderDetail.canUseGiftAmount}"></span>
				</p>
				</c:if>
				<!--支付方式-->
				<c:if test="${orderDetail.canPay == '1'}">
					<p id="payment" style="border: none" class="select payment-method cash">
						<a href="javascript:;">支付方式
							<i  class="${pay.clazz}" clazz="${pay.clazz}" url="${pay.url}" mainId="${pay.id}" subId="${pay.subpayCode}" way="${pay.way}">
								<em>${pay.name}</em>
							</i>
						</a>
					</p>
				</c:if>
				<c:if test="${not empty orderDetail.invoice.title}">
					<!--发票信息-->
					<p class="select-invoice" style="margin-top: 10px">发票信息</p>
					<p class="select invoice">
						<em>发票类型：</em>电子发票<br>
						<em>发票抬头：</em>${orderDetail.invoice.title}<br />
						<em>发票内容：</em>${orderDetail.invoice.context}
					</p>
				</c:if>

				<c:if test="${orderDetail.status == '1'}"><%-- @todo 需要支付类型--%>
					<!--支付信息-->
					<p class="select-invoice" style="margin-top: 10px">支付信息</p>
					<p class="select invoice">
						<em>支付类型：</em>${orderDetail.payInfo}<br>
						<em>支付时间：</em>${orderDetail.payTime}<br>
					</p>
				</c:if>

				<p class="total">
					<c:forEach var="priceShow" items="${orderDetail.priceShow}">
						<c:choose>
							<c:when test="${priceShow.type=='7'}">
								<em>${priceShow.title}：<i type="${priceShow.type}">&ensp;&yen;${priceShow.amount}</i><br></em>
							</c:when>
							<c:otherwise>
								<c:if test="${priceShow.amount*1>0 }">
									<em>${priceShow.title}：<i type="${priceShow.type}">${priceShow.type eq '2' || priceShow.type eq '6' || priceShow.type eq '3' || priceShow.type eq '4'  ? "-" : "&ensp;"}&yen;${priceShow.amount}</i></em><br>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</p>
			</fieldset>
		</form>
		<c:if test="${orderDetail.orderList[0].canLogistics == '1'}">
            <div class="payment_submit_all">
                <a href="javascript:;" class="payment_btn_pay" submitType="logistics" >订单追踪</a>
            </div>
			<input type="hidden" name="orderId" id="orderId" value="${orderDetail.mainOrderId }"/>
			<input type="hidden" name="postArea" id="postArea" value="${orderDetail.orderList[0].detail[0].postArea}"/>
		</c:if>
		<c:if test="${orderDetail.canPay == '1' }">
			<div class="payment_submit_all">
				<a href="javascript:;" class="payment_btn_pay" submitType="pay" value="" >立即支付&nbsp;<em id="leftTime" leftTime=""></em></a>
			</div>
			<input type="hidden" name="orderId" id="orderId" value="${orderDetail.mainOrderId }"/>
			<input type="hidden" id="currentTimes" name="currentTimes" value="${currentTimes}">
			<input type="hidden" name="setTime" id="setTime" value="${orderDetail.orderTime}"/>
		</c:if>
	</div>
</div>

<%@include file="/WEB-INF/pages/order/payments.jsp" %>

<div id="overlay"></div>
<div id="popup_box" >
    <a href="#" class="title_closeBtn">×</a>
    <h1 id="popup_title" style="display: block;">填写收货人身份证号码</h1>
    <div id="popup_content" class="share">
        <div id="popup_message">
        	<p>根据海关的要求，海外商品需要您填写收货人的身份证号码进行个人物品入境申报。</p>
            <div class="identification-box">
                <label for="identificationNum">身份证号码：</label>
                <input type="text" id="J_identificationNum" name="identificationNum" placeholder="请输入身份证号码" required maxlength="18">
            </div>
        </div>
        <div id="popup_panel">
          <button type="button" id="popup_cancel">&nbsp;取消&nbsp;</button> 
          <button type="button" id="popup_ok">&nbsp;保存&nbsp;</button>
        </div>
    </div>
</div>
</rapid:override> 
<%-- 页面的尾部 --%>
<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/another_common_mall_banner.jsp" %> 