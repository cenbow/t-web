<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<!-- 开启对web app程序的支持 -->
<meta name="apple-mobile-web-app-capable" content="yes">
<!-- 全屏模式浏览 -->
<meta name="apple-touch-fullscreen" content="yes">
<!-- 改变Safari状态栏的外观 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<!-- 禁止自动识别5位以上数字为电话 -->
<meta name="format-detection" content="telephone=no">

<title>客服帮助</title>
<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}" rel="stylesheet" />
<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/public.css${ver}" rel="stylesheet" />
<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/help_center.css${ver}" rel="stylesheet" /><!--页面专用CSS-->

<script src="${cdn:css(pageContext.request)}/styles/shangpin/js/core.js${ver}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
  var ver = Math.random();
    loader = SP.core
      .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")  //jquery库文件
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/core.js${ver}")    //图片懒加载
        .excute()
		.using("${cdn:js(pageContext.request)}/styles/shangpin/js/page/help_center.js${ver}")
        .excute();
      
</script>
    <!--集成脚本加载 -->
    <script language="javascript" type="text/javascript">

        NTKF_PARAM = {
            siteid:"kf_9986",                    	//企业ID，为固定值，必填
            settingid:"kf_9986_1355827406710",   	//接待组ID，为固定值，必填
            uid:"${user.userId}",         //用户ID，未登录可以为空，但不能给null，uid赋予的值在显示到小能客户端上
            uname:"${user.name}",         //用户名，未登录可以为空，但不能给null，uname赋予的值显示到小能客户端上
            isvip:"${user.lv=='0006' ? '1' : '0'}",                          //是否为vip用户，0代表非会员，1代表会员，取值显示到小能客户端上
            userlevel:"${user.lv}",       //网站自定义会员级别，0-N，可根据选择判断，取值显示到小能客户端上
//			erpparam:"abc",                     //erpparam为erp功能的扩展字段，可选，购买erp功能后用于erp功能集成
            itemid: "",				       	//(必填)商品ID
            itemparam:"m"				   			//(选填)itemparam为商品接口扩展字段，用于商品接口特殊要求集成
        }

    </script>
    <!--基础脚本加载 -->
    <script type="text/javascript" src="http://download.ntalker.com/t2ds/ntkfstat.js" charset="utf-8"></script>

</head>

<body>
<div class="container">
    <c:if test="${!checkAPP}">
        <!--头部 start-->
        <div class="topFix">
            <section>
                <div class="topBack">
                    <a href="javascript:history.go(-1);" class="backBtn">&nbsp;</a>
                    <span class="top-title">客服帮助</span>
                    <ul class="alUser_icon clr">
                        <li><a href="${ctx}/index"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/home_icon.png" width="25" height="25" alt="首页"></a></li>
                    </ul>
                </div>
            </section>
        </div>
        <!--头部 end-->
    </c:if>
    <input type="hidden" id="isApp" value="${isApp}">
    <input type="hidden" id="loginUrl" value="${loginUrl}">
    <!-- 页面内容start -->
    <div class="alContent">
    	<div class="help_box">
            <ul class="help_box_list">
                <li><a href="javascript:void(0)" onclick="chatXiaoNeng()"><i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/ser_help.png" width="28" height="28" ></i>在线客服<p style="font-size:6px">(早08:00-晚22:00)</p></a></li>
                <li><a href="tel:4006-900-900"><i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/phone.png" width="28" height="28"></i>电话客服<p style="font-size:6px">(早08:00-晚22:00)</p></a></li>
                <%--<li><a href="javascript:void(0)"><i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/advice.png" width="28" height="28"></i></a></li>--%>
            </ul>
            <p class="help_center_question" style="height:2px;"></p>
            <nav class="tab_info">
                <a href="#" class="ico_help01 curr">售后服务</a>
                <a href="#" class="ico_help02">支付方式</a>
                <a href="#" class="ico_help03">配送说明</a>
                <a href="#" class="ico_help03">用户安全</a>
            </nav>

            <div class="content_info">
                <div class="content_list">
                    <ul class="content-menu">
                        <c:choose>
                            <c:when test="${checkAPP}">
                                <li><a href="../helpApp/member_system.html">发票说明</a></li>
                                <li><a href="../helpApp/return_process.html">退换货流程</a></li>
                                <li><a href="../helpApp/return_rule.html">退换货说明</a></li>
                                <li><a href="../helpApp/position_ship.html">退换货地址及运费</a></li>
                                <li><a href="../helpApp/refund_description.html">退款说明</a></li>
                                <li><a href="../helpApp/repair.html">售后维修</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="../help/member_system.html">发票说明</a></li>
                                <li><a href="../help/return_process.html">退换货流程</a></li>
                                <li><a href="../help/return_rule.html">退换货说明</a></li>
                                <li><a href="../help/position_ship.html">退换货地址及运费</a></li>
                                <li><a href="../help/refund_description.html">退款说明</a></li>
                                <li><a href="../help/repair.html">售后维修</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="content_list" style="display: none;">
                    <ul class="content-menu">
                        <c:choose>
                            <c:when test="${checkAPP}">
                                <li><a href="../helpApp/payment_method_online.html">在线支付</a></li>
                                <li><a href="../helpApp/payment_method_cod.html">货到付款</a></li>
                                <li><a href="../helpApp/gift-pay.html">礼品卡支付</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="../help/payment_method_online.html">在线支付</a></li>
                                <li><a href="../help/payment_method_cod.html">货到付款</a></li>
                                <li><a href="../help/gift-pay.html">礼品卡支付</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="content_list" style="display: none;">
                    <ul class="content-menu">
                        <c:choose>
                            <c:when test="${checkAPP}">
                                <li><a href="../helpApp/deliver_range.html">配送范围</a></li>
                                <li><a href="../helpApp/deliver_pay.html">配送费用及时效</a></li>
                                <li><a href="../helpApp/deliver_illustrate.html">无货/配货异常处理办法</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="../help/deliver_range.html">配送范围</a></li>
                                <li><a href="../help/deliver_pay.html">配送费用及时效</a></li>
                                <li><a href="../help/deliver_illustrate.html">无货/配货异常处理办法</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="content_list" style="display: none;">
                    <ul class="content-menu">
                        <c:choose>
                            <c:when test="${checkAPP}">
                                <li><a href="../helpApp/protect.html">保护客户隐私</a></li>
                                <li><a href="../helpApp/usage_behavior.html">客户信息使用行为</a></li>
                                <li><a href="../helpApp/information_safe.html">信息安全</a></li>
                                <li><a href="../helpApp/paysafe.html">支付安全</a></li>
                                <li><a href="../helpApp/free-explan.html">免责说明</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="../help/protect.html">保护客户隐私</a></li>
                                <li><a href="../help/usage_behavior.html">客户信息使用行为</a></li>
                                <li><a href="../help/information_safe.html">信息安全</a></li>
                                <li><a href="../help/paysafe.html">支付安全</a></li>
                                <li><a href="../help/free-explan.html">免责说明</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

        <!-- 公共尾部 end -->
        <input name="ctx" id="ctx" value="${pageContext.request.contextPath}" type="hidden" />
        <input name="isVip" id="isVip" value="${_isVip}" type="hidden"/>
    </body>
</html>
