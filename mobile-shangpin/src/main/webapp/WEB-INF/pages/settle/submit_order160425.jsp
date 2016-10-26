<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<div id="settle">
<c:choose>
    <c:when test="${!checkAPP}">
        <div class="topFix" id="order_head">
            <section>
                <div class="topBack" >
                    <a href="javascript:;" class="backBtn">&nbsp;</a>
                    <span class="top-title">提交订单</span>
                    <ul class="alUser_icon clr">
                        <li><a href="<c:url value='/'/>"><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/home_icon.png${ver}" width="25" height="25" alt="首页"></a></li>
                    </ul>
                </div>
            </section>
        </div>
        <div class="alContent" id="order_content" >
    </c:when>
    <c:otherwise>
        <div class="alContent" id="order_content" style="margin-top: 0;">
    </c:otherwise>
</c:choose>
<!--头部 end-->
	<div class="paymet_block">
        <form name="login" id="J_Login">

            <fieldset style="padding-bottom:0px" >
            	<legend class="first_leg"></legend>
                <c:set var="lastReceived" value="${settle.received.currentAddress}" />
                <c:choose>
                    <c:when test="${not empty lastReceived and not empty lastReceived.id}">
                        <p class="selected">
                            <a href="javascript:;">
                                <em>${lastReceived.name}</em>
                                <i class="phone">${lastReceived.tel}</i><br>
                                <c:if test="${not empty cardId}">
                                    <span>${cardId}</span></br>
                                </c:if>
                                <span>${lastReceived.provName}${lastReceived.cityName}
                                        ${lastReceived.areaName}${lastReceived.townName}${lastReceived.addr}</span>
                            </a>
                            <input type="hidden" name="addressId" value="${lastReceived.id}"/><%--收货地址--%>
                            <input type="hidden" id="forth_id" value="${lastReceived.town}"/>
                            <input type="hidden" id="third_id" value="${lastReceived.area}"/><%--兼容万一之前用户没有四级地址--%>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p class="select">
                            <a href="javascript:;">
                                <em>请填写配送地址</em>
                                <i class="phone"></i><br>
                                <span></span>
                            </a>
                            <input type="hidden" name="addressId" value=""/><%--收货地址id--%>
                            <input type="hidden" id="fourth_id" value="${lastReceived.townName}"/>
                        </p>
                    </c:otherwise>
                </c:choose>
                <p id= "product" class="select coupons" style="border: none"><a class="discount_btn">${settle.product.title} <i>${settle.product.desc}</i></a></p>
                <p id="coupon" class="select coupons cash" style="margin-top: 10px"><a href="javascript:;" count="${settle.coupon.count}">${settle.coupon.title}<i><em>${settle.coupon.desc}</em></i></a></p>

                <%--礼品卡区域--%>
                <c:if test="${not empty settle.giftCard and settle.giftCard.isShow== '1'}">
                <p id="giftCard" class="select-invoice coupons cash">
                    <a href="#">${settle.giftCard.title}
                        <i canUseAmount="${settle.giftCard.canUseAmount}" prompt="${settle.giftCard.prompt}">
                            <em>${settle.giftCard.desc}</em>
                        </i>
                    </a>
                    <span class="select-option ${settle.giftCard.buttonStatus == '1'? "cur":""}" valid="${settle.giftCard.valid}"></span>
                </p>
                </c:if>

                <c:forEach items="${payments}" var="pay">
                    <c:if test="${pay.isSelected==1}">
                        <p id="payment" style="border: none" class="select payment-method cash">
                            <a href="javascript:;" >支付方式
                                <i class=${pay.clazz} url="${pay.url}" mainId="${pay.id}" subId="${pay.subpayCode}" way="${pay.way}"><em>${pay.name}</em>
                                </i>
                            </a>
                        </p>
                    </c:if>
                </c:forEach>

                <p id="invoice" class="select-invoice" style="margin-top: 10px" valid="${settle.invoice.valid}">
                    <a href="#">是否开发票 
                        <c:if test="${settle.invoice.valid eq 0  }">
                            <em class="attention">${ settle.invoice.prompt}</em>
                        </c:if>
                    </a>
                    <span class="select-option" id="yes"></span>
                </p>
                <p class="select invoice">
                    <a href="#">
                        发票类型：<em>电子发票</em><br>
                        发票抬头：<em>个人</em><br>
                        发票内容：<em>商品全称</em>
                    </a>
                </p>

                <p class="total">
                    <c:forEach var="price" items="${settle.priceShow.price}">
                        <c:if test="${price.amount!='0' or price.type ==1 or price.type==7}">
                            ${price.title}:&nbsp;&nbsp;${price.type==2 or price.type==3 or price.type==4 or price.type==6 ? "&minus;":"&ensp;"}<i type="${price.type}">&yen;${price.amount}</i><br/>
                            <%--<c:choose>
                                <c:when test="${price.type==2 or price.type==3 or price.type==4 or price.type==6}">
                                    -
                                </c:when>
                                <c:otherwise>
                                    &nbsp;
                                </c:otherwise>
                            </c:choose>--%>
                            <%-- &yen;${price.amount}</i><br/>--%>
                        </c:if>
                    </c:forEach>
                </p>
            </fieldset>
        </form>
        <p class="attention">${settle.prompt}</p>
        <div class="payment_submit">
            <p>应付金额：<i> &yen;${settle.priceShow.onlineAmount}</i></p>
            <a href="javascript:;" class="payment_btn" ctag='{"id":"O1","pid":"${settle.productIds}"}' >提交订单 <em>(${settle.priceShow.settlementCount})</em> </a>
            <input type="submit" class="payment_btn" value="提交订单">
        </div>
    </div>
</div>

<div id="overlay"></div>
<div id="popup_box_id" >
    <a href="javascript:;" class="title_closeBtn">×</a>
    <h1 id="popup_title_id" style="display: block;">填写收货人身份证号码</h1>
    <div id="popup_content_id" class="share">
        <div id="popup_message_id">
        	<p>根据海关的要求，海外商品需要您填写收货人的身份证号码进行个人物品入境申报。</p>
            <div class="identification-box">
                <label for="identificationNum">身份证号码：</label>
                <input type="text" id="J_identificationNum" name="identificationNum" placeholder="请输入身份证号码" required maxlength="18">
            </div>
        </div>
        <div id="popup_panel_id">
          <button type="button" id="popup_cancel_id">&nbsp;取消&nbsp;</button>
          <button type="button" id="popup_ok_id">&nbsp;保存&nbsp;</button>
        </div>
    </div>
</div>

<div class="discount">
	<div class="dis_box">
        <input placeholder="请输入折扣码" value=""/>
        <p>温馨提示：折扣码与优惠券只能使用一种</p>
        <a class="dis_false">取消</a>
        <a class="dis_true">确定</a>
    </div>
</div>

<!--地址不全时候显示-->
<div id="select-area-overlay" style="height: 667px; display: none;"></div>
<div class="select-layer" id="select_street_layer" style="display: none;">
    <a href="javascript:;" class="close_btn">关闭</a>
    <h3 style="text-align:center">请选择街道</h3>
    <dl class="select-opt" id="orgin_area_street" title="街道"></dl>
</div>
</div>
