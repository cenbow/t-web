 <%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%> 

 <div class="card_balance">
   	 <h2>账户余额：&yen;<c:if test="${giftCardRecord==null}">0</c:if><c:if test="${giftCardRecord!=null}">${giftCardRecord.mainBalance}</c:if></h2>
   	 <c:if test="${!checkAPP}">
     <a href="${ctx }/giftCard/recharge" class="recharge-btn">充值</a>
     </c:if>
      <a href="${ctx }/giftCard/productList" class="buy-card-btn">购买</a>
 </div>
 <div class="menu-nav">
       <ul class="tab_info">
         <li class="curr"><a href="javascript:;" id="buyChoice">购买记录</a></li>
         <li><a href="#10;" id="rechargeChoice">充值记录</a></li>
         <li><a href="#11" id="consumeChoice">消费记录</a></li>
       </ul>
</div>

 <div class="content_info">
           <input type="hidden" id="recordType" value="${recordType }"/>
		  	<input type="hidden" id="pageNo" value="${pageNo }"/>
		  	 <input type="hidden" id="isHaveMore${pageNo }" value="${isHaveMore }"/>
            <div class="content_detail content_list">
            	<c:choose>
            	<c:when test="${giftCardRecord!=null && fn:length(giftCardRecord.list)>0 }">
	            	<c:forEach var="record" items="${giftCardRecord.list }">
		            	<ul class="purchase-records-list">
		                	<li class="clr">
		                    	<p>订单号：<strong>${record.childOrderId }</strong></p>
		                        <span>${record.fmtDate }</span>
		                    </li>
		                    <li class="clr">
		                    	<p>${record.typeDesc }</p>
		                        <span>¥${record.faceValue } | 礼品卡</span>
		                    </li>
		                    <li class="clr">
		                    	<p>状态：<strong class="red">${record.statusDesc }</strong></p>
		                    </li>
		                    <c:if test="${record.status==1 }">
			                    <div class="btn-icon clr">
			                    	<a href="${ctx }/giftCard/toRecharge?giftCardId=${record.giftCardId}&pic=${record.pic}" class="recharge-btn">充值于本账户</a>
			                        <a class="share-btn" childOrderId="${record.childOrderId }" giftCardId="${record.giftCardId}" faceValue="${record.faceValue}" >赠送给小伙伴</a>
			                    </div>
		                    </c:if>
		                     </ul>
	                     	<div class="line"></div>
	                     </c:forEach>
                   </c:when>
                   <c:otherwise>                   
                    <div class="setup">
					   <div class="setup_img"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/card/card_null.png"></div>
					   <p>暂时没有记录哦</p>
					 </div>
                   </c:otherwise>
            	</c:choose>
            </div>
           	<%@ include file="/WEB-INF/pages/giftCard/record_list_recharge.jsp" %> 
           	<%@ include file="/WEB-INF/pages/giftCard/record_list_consume.jsp" %> 
           	<a id="addMore" class="payment_btn"<c:if test="${!isHaveMore}">style="display:none"</c:if> href="javascript:void(0);">加载更多</a>
           	<div id="loadOrder" class="payment_btn"  style="display:none">正在加载中...</div>
</div>
