<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
	<c:choose>
		<c:when test="{${!checkAPP}}">
			<div class="topFix" id="coupons_head" style="display: none">
			   <section>
			       <div class="topBack" >
			       <a href="javascript:;" class="backBtn">&nbsp;</a>
			       选择优惠
			       <ul class="alUser_icon clr">
					   <li><a href="<c:url value='/'/>"><img src="${cdn:css(pageContext.request)}/styles/shangpin/images/home_icon.png${ver}" width="25" height="25" alt="首页"></a></li>
			       </ul>
			       </div>
			   </section>
			</div>
			<div class="alContent order_coupons" id="coupons_content" type="" style="display: none;min-height: 550px;">
		</c:when>
		<c:otherwise>
			<div class="alContent order_coupons" id="coupons_content" type=""  style="display: none;margin-top: 0;min-height: 100%;">
		</c:otherwise>
	</c:choose>
	<%--<ul class="tabs_menu coupon_menu_order">
       <li class="cur">使用优惠券</li>
       <li>使用折扣码</li>
   </ul>--%>
   
   <div class="coupon_block_order">
   	<div class="tabs_Cell">	
           <form class="coupons_active_order">
               <!--  <h3>优惠券激活</h3>-->
           	<fieldset>
                   <p>
                       <input type="text" id="coupons_code" name="coupons_code" placeholder="输入优惠券编码" required />
                       <a href="javascript:;" class="coupons_submit">确认</a>
                   </p>
               </fieldset>
           </form>
           
           <ul class="coupon_list_order select_coupon">
<%--                <li class="cash">
                    <h4><img src="${ctx}/styles/shangpin/images/order/cash_coupon_angle.png" width="69" height="145" /></h4>
                    <input type="hidden" name="couponId" id="couponId" value=""/>
                    <div class="cash">
                        <i id="coupon_amount">&yen;</i>
                        <em></em>
                        <span></span>
                        <span></span>
                    </div>
                    <p><img src="${ctx}/styles/shangpin/images/order/cash_coupon.png" width="69" height="145" /></p>
                    <b id="coupon_selected" class="coupons_select"></b>
                </li>
                <li class="sale">
                       <h4><img src="${ctx}/styles/shangpin/images/order/coupon_angle.png" width="69" height="145" /></h4>
                       <input type="hidden" name="couponId" id="couponId" value=""/>
                    <div class="cash">
                        <i id="coupon_amount">&yen;</i>
                        <em></em>
                        <span>
                          <strong></strong>
                        </span>
                    </div>
                    <p><img src="${ctx}/styles/shangpin/images/order/coupon.png" width="69" height="145" /></p>
                    <b id="coupon_selected" class="coupons_select"></b>
                </li>--%>
        </ul>
        <input type="hidden" id="online_pay" name="online_pay" value=""/>
    </div>
	<%--<div class="tabs_Cell">

        <form class="coupons_active_order">
        	<fieldset>
                <p class="code_active">
                    <input type="text" id="sale_code" name="sale_code" placeholder="输入折扣码" required />
                    <a href="javascript:promoCodeInfo();" id="saleCode_btn" class="coupons_submit">确认</a>
                </p>

                <p class="code_result" style="display:none;">
                    <span><i id="prom_code"></i> &nbsp;&nbsp; 已优惠 ¥<i id="pro_amount"></i></span>
                    <input type="hidden" id="coupon_carriage" name="coupon_carriage" value=""/>
                    <input type="hidden" id="coupon_payamount" name="coupon_payamount" value=""/>
                    <a href="javascript:cannelPromoCode();" id="saleCacel_btn" class="coupons_submit">更改</a>
                </p>
            </fieldset>
        </form>

        <p class="coupons_tips_order">温馨提示：当你使用现金券、优惠券、折扣码购买的商品发生退货时，现金券/优惠券抵扣的金额将不会退还。</p>

    </div>--%>
</div>
</div>
