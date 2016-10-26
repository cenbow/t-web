<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request) }/styles/shangpin/css/page/card/cards_coupons.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/giftcard/font-awesome.min.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
		var ver = Math.random();
			loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js?" + ver)
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js?" + ver)
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/giftcard/record_list.js?" + ver)
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/user_new_coupons.js?" + ver)
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/j.dialogs.js?" + ver)
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/coupon/coupon.js?" + ver)
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/head_scroll.js?" + ver)
				.excute();
	</script>
</rapid:override>



<rapid:override name="page_title">
	卡券
</rapid:override>

<rapid:override name="content">
     <ul class="tabs_menu coupon_menu">
        <li class="cur">优惠券</li>
		 <li>礼品卡</li>
	 </ul>
     <div class="coupon_block">
    	<div class="tabs_Cell">
    	   <form class="coupons_active">
            	<fieldset>
                    <p>
                        <input type="text" id="coupons_code" name="coupons_code" placeholder="输入优惠券兑换码" required />
                        <a href="javascript:activeCoupon();" class="coupons_submit">兑换</a>
                    </p>
                </fieldset>
            </form>
          <!-- 未使用   -->
		 <c:import url="/coupon/newList"/>
    	</div>
    	<div class="tabs_Cell">
			<c:import url="/giftCard/list"/>
	    </div>
    	<!-- // -->
    </div>
</rapid:override>
<rapid:override name="down_page">
	
</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %> 