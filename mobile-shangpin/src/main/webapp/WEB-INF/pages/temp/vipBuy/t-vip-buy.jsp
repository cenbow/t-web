 <%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>



<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/2016Mapp/payment.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/vip/vip2016.css${ver}" rel="stylesheet" /><!--页面专用CSS-->
    <style>
        body .alContent{width:100%; min-width:320px;max-width:640px; overflow:hidden; margin-top:0px;}
    </style>
    <style>
        <c:if test="${checkAPP}">
        /*支付确认页面*/
            .z-paylist{
                background:#fff;
                width:100%;
                font-size:14px;
                margin-top:0px;
            }
        </c:if>
    </style>

    <script type="text/javascript" charset="utf-8">
        loader = SP.core
                .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js${ver}")
                .excute()
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/2016Mapp/payment.js${ver}" )
                .excute()
    </script>
</rapid:override >


<%-- 浏览器标题 --%>
<rapid:override name="title">
	购买 PRIME 金牌会员
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	购买 PRIME 金牌会员
</rapid:override>

<rapid:override name="header">
    <c:if test="${!checkAPP}">
         <div class="topFix" >
             <section>
                 <div class="topBack">
                     <a href="javascript:history.go(-1);" class="backBtn">&nbsp;</a>
                     <span class="top-title">购买 PRIME 金牌会员</span>
                     <ul class="alUser_icon clr">
                         <li>
                             <a href="${ctx}/index" ><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/home_icon.png" width="25" height="25" alt="首页"></a>
                         </li>
                         <li><a href="${ctx}/user/home"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/user_icon.png" width="25" height="25" alt="账户"></a></li>
                     </ul>
                 </div>
             </section>
         </div>
    </c:if>
</rapid:override>



<rapid:override name="content">
    <script>
    	function queryPayResult(){
    		window.location.href="${ctx}/right-vip/order/query";
    	}
        function buyVip() {
            var back = window.location.pathname;
            back = back.replace(getRootPath(),"");
            if(back){
                var reg=new RegExp("&","g");
                back =encodeURI(back).replace(reg,"9uuuuu9");
            }
            $.ajax({
                type: "GET",
                url: getRootPath()+"/isLogin?"+new Date(),
                success: function(data){
                    //未登录
                    var app=${checkAPP?"1":false};
                    if(data==undefined || data=="" || data == "0" ){
                        if(app){
                            window.location=$("#loginUrl").val();
                        }else{
                            window.location= getRootPath() + "/login?back=" + back;
                        }
                    }
                    //已登录
                    if(data == "1"){
                        var paytype = $("#_payBox .checked").attr("data-gw");
                        if(!paytype){
                            alert("请选择支付方式");
                            return ;

                        }
                        $("#btn_pay_confirm a").text("支付中...");
                        $("#btn_pay_confirm").removeAttr("onclick");
                        $("#gateway").val(paytype);
                        var delay_time=3000;
                        if(paytype.indexOf('WEIXIN')>-1){
                        	delay_time=6000;
                        }
                        $("#vipForm").submit();
                        window.setTimeout(function(){
                        	$(".purchase_bg").show();},delay_time);
	                    
                    }
                }
            });
        }
    </script>
    <input type="hidden" id="loginUrl" value="${loginUrl}">
    <form id="vipForm" method="post" action="${ctx}/right-vip/order">
        <input name="gateway" id="gateway" type="hidden"/>
        <ul class="z-paylist" id="_content">
            <li><span class="z-ccc fl">开通服务</span> <span class="z-black fr"> PRIME 金牌会员</span>
            </li>
            <li><span class="z-ccc fl">付款方式</span> <span class="z-black fr">按年付费</span>
            </li>
            <li><span class="z-ccc fl">应付金额</span> <span class="z-black fr">￥299/年</span>
            </li>
        </ul>
        <!--支付方式-->
        <div class="z-paymethod">支付方式</div>
            <ul class="payment_box clr cur" id="_payBox">
            <c:choose>
                <c:when test="${checkWX}">
                    <li class="checked" data-gw="WEIXINPUB">
                        <em class="payment_icon wechat"></em>
                        <a href="javascript:;">微信</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="checked" data-gw="WEIXINWAP">
                        <em class="payment_icon wechat"></em>
                        <a href="javascript:;">微信</a>
                    </li>
                    <li data-gw="ALIWAP">
                        <em class="payment_icon zhifubao"></em>
                        <a href="javascript:;">支付宝</a>
                    </li>
                </c:otherwise>
            </c:choose>
            </ul>
            <p class="invoice" style="background-color: #f6f6f6;">
		            支付完成后，不支持退款<br/>
		            若需要开具发票，请联系客服400-900-900
            </p>
        <!--确认支付-->
        <div class="z-config_pay" style="margin:0px 10px" onclick="buyVip()" id="btn_pay_confirm">
            <a href="javascript:;" class="sure_pay">确认支付</a>
        </div>
    </form>
    <div class="purchase_bg">
		<div class="weixin">
	    	<p>已完成支付？</p>
	        <ul>
	        	<li>否</li>
	            <li>是</li>
	        </ul>
	    </div>
	</div>  
</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>