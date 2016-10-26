<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<c:if test="${cart != null}">
	<input type="hidden" id="checkAPP" value="${checkAPP}" />
	<input type="hidden" name="wx" value="${checkWX}">
	<input name="ctx" id="ctx" value="${pageContext.request.contextPath}"
		type="hidden" />
    <input name="isVip" id="isVip" value="${_isVip}" type="hidden"/>
	<form id="cartForm" action="${ctx}/cart/topay" method="get">
		<input type="hidden" id="shopids" name="shopCartDetailIds" />
	</form>

	<c:if test="${cart != null && cart.cartList != null && fn:length(cart.cartList) > 0 }">
		<!--内容区域 start-->
		<input type="hidden" id="ctx" value="${ctx}" />
        <input name="isVip" id="isVip" value="${_isVip}" type="hidden"/>
		<section class="order_block">
			<c:forEach var="spList" items="${cart.cartList}">
				<dl>
					<c:if test="${spList.title != ''}">
						<dt>
							<c:choose>
								<c:when test="${spList.click.type =='1'}">
									<c:choose>
										<c:when test="${checkAPP }">
											<a
												href="shangpinapp://phone.shangpin/actiongoactivitylist?title=${spList.click.name}&activityid=${spList.click.refContent}">
										</c:when>
										<c:otherwise>
											<a
												href="${ctx }/subject/product/list?topicId=${spList.click.refContent}&postArea=1"
												>
										</c:otherwise>
									</c:choose>
								</c:when>

								<c:when test="${spList.click.type == '2'}">
									<c:choose>
										<c:when test="${checkAPP }">
											<a
												href="shangpinapp://phone.shangpin/actiongocatelist?title=${spList.click.name}&filters=category_${spList.click.refContent}&categoryid=${spList.click.refContent}">
										</c:when>
										<c:otherwise>
											<a
												href="${ctx }/category/product/list?categoryNo=${spList.click.refContent}&postArea=1"
												>
										</c:otherwise>
									</c:choose>
								</c:when>

								<c:when test="${spList.click.type == '3'}">
									<c:choose>
										<c:when test="${checkAPP }">
											<a
												href="shangpinapp://phone.shangpin/actiongobrandlist?title=${spList.click.name}&filters=brand_${spList.click.refContent}&brandid=${spList.click.refContent}">
										</c:when>
										<c:otherwise>
											<a
												href="${ctx}/brand/product/list?brandNo=${spList.click.refContent}"
												>
										</c:otherwise>
									</c:choose>
								</c:when>

								<c:when test="${spList.click.type == '4'}">
									<c:choose>
										<c:when test="${checkAPP }">
											<a
												href="shangpinapp://phone.shangpin/actiongodetail?title=${spList.click.name}&productid=${spList.click.refContent}">
										</c:when>
										<c:otherwise>
											<a
												href="${ctx}/product/detail?productNo=${spList.click.refContent}"
												>
										</c:otherwise>
									</c:choose>
								</c:when>

								<c:when test="${spList.click.type == '5'}">
									<c:choose>
										<c:when test="${checkAPP }">
											<a
												href="${spList.click.refContent}">
										</c:when>
										<c:otherwise>
											<a
												href="${spList.click.refContent}"
												>
										</c:otherwise>
									</c:choose>
								</c:when>
								
								<c:when test="${spList.click.type =='0'}">
									<c:choose>
										<c:when test="${checkAPP }">
											<a
												href="shangpinapp://phone.shangpin/actiongoactivitylist?title=${spList.click.name}&activityid=${spList.click.refContent}">
										</c:when>
										<c:otherwise>
											<a
												href="${ctx }/subject/product/list?topicId=${spList.click.refContent}&postArea=1"
												>
										</c:otherwise>
									</c:choose>
								</c:when>
							</c:choose>

							<span>满减</span> <i class="saveAmount">${spList.title}</i><em>&gt;</em>
							</a>
						</dt>
					</c:if>

					<c:forEach var="cartItem" items="${spList.productList}">
						<c:choose>
							<c:when test="${cartItem.msgType == '1'}">
								<dd class="clr dd_fail" id="${cartItem.cartDetailId}"
									ver="${cartItem.spu}">
							</c:when>

							<c:when test="${cartItem.msgType == '4'}">
								<dd class="clr dd_offshelf" id="${cartItem.cartDetailId}"
									ver="${cartItem.spu}">
							</c:when>

							<c:when
								test="${cartItem.msgType == '2' || cartItem.postArea =='1'}">
								<dd class="clr domestic-goods" id="${cartItem.cartDetailId}"
									ver="${cartItem.spu}">
							</c:when>
							<c:when
								test="${cartItem.msgType == '2' || cartItem.postArea =='2'}">
								<dd class="clr overseas-goods" id="${cartItem.cartDetailId}"
									ver="${cartItem.spu}">
							</c:when>

						</c:choose>

						<a href="javascript:;" id="list_2" class="close">关闭</a>

						<c:choose>
							<c:when test="${cartItem.isChecked == '1' }">
								<!--当商品被勾选中  -->
								<a class="input input_curr" href="javascript:;"> <input
									type="checkbox" class="choice_commodity" checked="checked"
									value="${cartItem.cartDetailId}"
									postArea="${cartItem.postArea}" />
								</a>
							</c:when>
							<c:otherwise>
								<a class="input" href="javascript:;"> <input type="checkbox"
									class="choice_commodity" value="${cartItem.cartDetailId}"
									postArea="${cartItem.postArea}" />
								</a>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${checkAPP}">
								<a
									href="shangPinApp://phone.shangpin/actiongodetail?productid=${cartItem.spu}"
									class="img"> <span class="fail">售罄</span> <img
									src="${cartItem.pic}" width="70" height="94" />
								</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/product/detail?productNo=${cartItem.spu}"
									class="img"> <span class="fail">售罄</span> <img
									src="${cartItem.pic}" width="70" height="94" />
								</a>
							</c:otherwise>
						</c:choose>

						<h2>
							<a href="#"> <span>${cartItem.brand}&nbsp;${cartItem.name}</span>
							</a>
						</h2>

						<c:if test="${cartItem.countryDesc!= ''||cartItem.postArea =='2'}">
							<%--<p class="oversea_tag">${cartItem.countryDesc}</p>--%>
						</c:if>

						<p class="clr">
							<c:forEach var="attr" items="${cartItem.attribute}">
								<c:choose>
									<c:when test="${attr.name == '颜色'}">
										<span class="color">${attr.name}：${attr.value}</span>
									</c:when>
									<c:otherwise>
										<span class="size">${attr.name}：${attr.value}</span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</p>

						<div class="clr">
							<span class="price">
								<span class="p_third" style="color: ${cartItem.priceColor}">&yen;${cartItem.price}</span>
								<c:if test="${ cartItem.shangPinPrice != ''}">
                                    <span class="p_second" style="${cartItem.isShowDeleteLine =="1" ? 'text-decoration: line-through;': ''} color: ${cartItem.shangPinPriceColor}">&yen;${cartItem.shangPinPrice}</span>
                                </c:if>
						    </span>
                           <%-- <span class="price">
                                <b>&yen;<em class="commodity_price" comprice="${cartItem.price}">${cartItem.price}</em>
                                </b><br/>
                            </span>--%>
							<div class="fillNumber">
								<c:choose>
									<c:when test="${cartItem.quantity=='1'}">
										<a href="javascript:;" class="cut disabled">-</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:;" class="cut">-</a>
									</c:otherwise>
								</c:choose>
								<span class="prodNum">${cartItem.quantity}</span>
								<c:choose>
									<c:when test="${cartItem.quantity=='10'}">
										<a href="javascript:;" class="add disabled">+</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:;" class="add">+</a>
									</c:otherwise>
								</c:choose>
								<input type="hidden" class="numberVal" value="${cartItem.count}">
							</div>
						</div>

						</dd>
					</c:forEach>
				</dl>
			</c:forEach>
			<!--footer start-->
			<footer>
				<div>
					<c:choose>
						<c:when test="${cart.isCheckedAll == '1'}">
							<a class="input_all inputall_curr" href="javascript:;"
								id="chooseAll"> <input type="checkbox"
								class="choice_commodity" />
							</a>
						</c:when>
						<c:otherwise>
							<a class="input_all" href="javascript:;" id="chooseNotAll"> <input
								type="checkbox" class="choice_commodity" />
							</a>
						</c:otherwise>
					</c:choose>
					<span style="margin-left: 5px">全选</span>
					<span class="total_amount">总金额：<b><em>&yen;<i
								id="total_amount">${cart.totalAmount}</i></em></b></span>
					<c:if test="${cart.totalAmountDesc != ''}">
						<span class="save_amount">${cart.totalAmountDesc}</span>
					</c:if>

				</div>
				<c:choose>
					<c:when test="${cart.totalAmount != '0' }">
                            <a id="immediately" class="btn immediately" ctag='{"id":"STM1","cid":"${cart.productIds}"}' href="javascript:;">结算(<em id="total_number">${cart.totalSettlement}</em>)
						</a>
					</c:when>
					<c:otherwise>
						<a class="btn immediately no_submit" href="javascript:;">结算(<em id="total_number">0</em>)
						</a>
					</c:otherwise>
				</c:choose>

			</footer>
			<!--footer end-->

		</section>
		<!-- 提示层 -->
		<div class="tip-overlay"></div>
	</c:if>
	<!--海外境内一起结算提示 start-->
	<div class="coupon_tip">
		<c:if test="${cart.prompt != ''}">
      	${cart.prompt}
      </c:if>
	</div>
	<!--海外境内一起结算提示 end-->

</c:if>

