<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

	<ul class="coupon_list select_coupon used_list">
	<c:choose>
	<c:when test="${coupons!=null && fn:length(coupons)>0 }">
		<c:forEach var="coupon" items="${coupons}">
			<c:if test="${coupon.type == '1' }">
           			<li class="cash">
			          <c:choose>
			              <c:when test="${coupon.statuscode=='0' }">
			                 <input type="hidden" name="couponId" id="couponId" value="${coupon.couponno}"/>
		                     <div class="cash">
		                	 <i id="coupon_amount">&yen;${coupon.amount }</i>
		                     <em>${coupon.name}</em>
		                     <span>
		                      <strong>${coupon.expirydate}</strong>
		                   	   ${coupon.rule}
		                     </span>
		                     </div>
		                     <p><img src="${ctx}/styles/shangpin/images/order/cash.png" width="69" height="121" /></p>
			              </c:when>
			              <c:when test="${coupon.statuscode=='1' }">
			                 <h4><img src="${ctx}/styles/shangpin/images/card/used.png" /></h4>
			                 <input type="hidden" name="couponId" id="couponId" value="${coupon.couponno}"/>
			                 <div class="cash">
			                	<i id="coupon_amount">&yen;${coupon.amount }</i>
			                    <em>${coupon.name}</em>
			                    <span>
			                      <strong >${coupon.expirydate}</strong>
			                   	 ${coupon.rule}
			                    </span>
			                 </div>
			                 <p><img src="${ctx}/styles/shangpin/images/card/cash_invalid.png" width="69" height="121" /></p>
			              </c:when>
			              <c:otherwise>
			                   <h4><img src="${ctx}/styles/shangpin/images/card/expired.png" /></h4>
			                  <input type="hidden" name="couponId" id="couponId" value="${coupon.couponno}"/>
			                  <div class="cash">
			                	<i id="coupon_amount">&yen;${coupon.amount }</i>
			                    <em>${coupon.name}</em>
			                    <span>
			                      <strong >${coupon.expirydate}</strong>
			                   	 ${coupon.rule}
			                    </span>
			                 </div>
			                  <p><img src="${ctx}/styles/shangpin/images/card/cash_invalid.png" width="69" height="121" /></p>
			              </c:otherwise>
			          </c:choose>
		            </li>
           		</c:if>
           		<c:if test="${coupon.type == '0' }">
           			<li class="sale">
	           			<c:choose>
	           			   <c:when test="${coupon.statuscode=='0' }">
			           		   <input type="hidden" name="couponId" id="couponId" value="${coupon.couponno}"/>
				               <div class="cash">
				                	<i id="coupon_amount">&yen;${coupon.amount }</i>
				                    <em>${coupon.name}</em>
				                    <span>
				                      <strong >${coupon.expirydate}</strong>
				                       ${coupon.rule}
				                    </span>
				               </div>
				               <p><img src="${ctx}/styles/shangpin/images/order/coupon.png" width="69" height="121" /></p>
	           			   </c:when>
	           			    <c:when test="${coupon.statuscode=='1' }">
	           			       <h4><img src="${ctx}/styles/shangpin/images/card/used.png" /></h4>
				               <input type="hidden" name="couponId" id="couponId" value="${coupon.couponno}"/>
				               <div class="cash">
				                	<i id="coupon_amount">&yen;${coupon.amount }</i>
				                    <em>${coupon.name}</em>
				                    <span>
				                      <strong>${coupon.expirydate}</strong>
				                       ${coupon.rule}
				                    </span>
				                </div>
				                <p><img src="${ctx}/styles/shangpin/images/card/coupon_invalid.png" width="69" height="121" /></p>
	           			    </c:when>
	           			   <c:otherwise>
	           			       <h4><img src="${ctx}/styles/shangpin/images/card/expired.png" /></h4>
				               <input type="hidden" name="couponId" id="couponId" value="${coupon.couponno}"/>
				               <div class="cash">
				                	<i id="coupon_amount">&yen;${coupon.amount }</i>
				                    <em>${coupon.name}</em>
				                    <span>
				                      <strong>${coupon.expirydate}</strong>
				                       ${coupon.rule}
				                    </span>
				                </div>
				                <p><img src="${ctx}/styles/shangpin/images/card/coupon_invalid.png" width="69" height="121" /></p>
	           			   </c:otherwise>
	           			</c:choose>
		            </li>
           		</c:if>
		</c:forEach>
		</c:when>
		<c:otherwise>
		 <div class="setup">
		     <div class="setup_img"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/card/coupon_null.png"></div>
			 <p>暂无优惠券</p>
		</div>
		</c:otherwise>
	 </c:choose>
	</ul>
	<c:if test="${hasMore}">
		<a id="haveMore" class="payment_btn moreButton" href="javascript:getMoreList('1','used_list');">点击查看更多</a>
	</c:if>
	<input type="hidden" id="used_list" name="used" value="${start}"/>
<!-- </div> -->