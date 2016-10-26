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
                <li><a href="javascript:void(0)" onclick="chatXiaoNeng()"><i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/ser_help.png" width="28" height="28" ></i>在线客服</a></li>
                <li><a href="tel:4006-900-900"><i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/phone.png" width="28" height="28"></i>电话客服</a></li>
                <%--<li><a href="javascript:void(0)"><i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/advice.png" width="28" height="28"></i></a></li>--%>
            </ul>
            <p class="help_center_question">问题查询</p>
            <nav class="tab_info">
                <a href="#" class="ico_help01 curr">新手上路</a>
                <a href="#" class="ico_help02">售后服务</a>
                <a href="#" class="ico_help03">常见问题</a>
            </nav>
            <div class="content_info">


            
              <div class="content_list">
                  <ul class="content-menu">
                      <c:choose>
                          <c:when test="${checkAPP}">
                              <li><a href="../helpApp/shopping_process.html">购物流程</a></li>
                              <li><a href="../helpApp/coupon_usage.html">优惠券使用</a></li>
                              <li><a href="../helpApp/disclaimer.html">免责声明</a></li>
                              <li><a href="../helpApp/payment_method.html">付款方式</a></li>
                              <li><a href="../helpApp/deliver_illustrate.html">配送说明</a></li>
                              <li><a href="../helpApp/member_system.html">发票制度</a></li>
                              <li><a href="../helpApp/shopping_comment.html">商品评价</a></li>
                          </c:when>
                          <c:otherwise>
                              <li><a href="shopping_process.html">购物流程</a></li>
                              <li><a href="coupon_usage.html">优惠券使用</a></li>
                              <li><a href="disclaimer.html">免责声明</a></li>
                              <li><a href="payment_method.html">付款方式</a></li>
                              <li><a href="deliver_illustrate.html">配送说明</a></li>
                              <li><a href="member_system.html">发票制度</a></li>
                              <li><a href="shopping_comment.html">商品评价</a></li>
                          </c:otherwise>
                      </c:choose>
                  </ul>
              </div>
  
              <div class="content_list" style="display: none;">
                  <ul class="content-menu">
                  <c:choose>
                    <c:when test="${checkAPP}">
                     <li><a href="../helpApp/global_purchase.html">海外购</a></li>
                     <li><a href="../helpApp/return_process.html">退换货流程</a></li>
                     <li><a href="../helpApp/return_rule.html">退换货政策</a></li>
                     <li><a href="../helpApp/repair.html">售后维修</a></li>
                     <li><a href="../helpApp/refund_description.html">退款说明</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="global_purchase.html">海外购</a></li>
                        <li><a href="return_process.html">退换货流程</a></li>
                        <li><a href="return_rule.html">退换货政策</a></li>
                        <li><a href="repair.html">售后维修</a></li>
                        <li><a href="refund_description.html">退款说明</a></li>
                    </c:otherwise>
                  </c:choose>
                  </ul>
              </div>
  
              <div class="content_list" style="display: none;">
                <div class="problem-box">
                  <h3>1. 尚品保证都是正品吗？</h3>
                  <p>尚品网是国内首家全球买手制电子商务网站，网站销售商品由尚品网资深买手经由品牌商、品牌店、品牌分销商等处精选采购，所售商品均为正品。尚品网的品牌索引分别按字母顺序列出了网站销售的品牌。</p>
                  <h3>2. 物流信息怎么查看不到？</h3>
                  <p>亲，小尚的商品又不同仓库发货，部分快递公司不能及时更新快递信息，请您喝口茶耐心等候~</p>
                  <h3>3. 国内外尺码对照表怎么看？</h3>
                  <p>尚品网是经由各品牌授权售卖的电子商务网站，网站上列出的所有商品均是真品，由供应商直接向尚品网供货。尚品网的品牌索引分别按字母顺序列出了网站销售的所有品牌。</p>
                  <p>这些尺寸表旨在提供常规指南。尺寸和款式因品牌而异。如果您的尺寸在既有尺寸之间，或者您需要有人帮您选择适当尺寸，请通过电子邮件、电话或实时聊天联系客户服务部门。</p>
                  <h3>4. 付款后大概什么时候发货？多快收到货物？我可以选择收货时间吗？</h3>
                  </p>亲，付款后发货时间仓库会在物流显示，采用顺丰快递和EMS时间如下，您可以选择工作日送货、休息日送货或双选。如果您有任何特殊需求，也可以致电客服热线4006-900-900（早08:00-晚22:00）说明，我们会尽可能为您提供便捷周到的服务。</p>
                  <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/help_center/img04.jpg" />
                  <h3>5. 商品收到后，发现发少了或者发错了，怎么办？</h3>
                  <p>亲，出现这样的情况我们很抱歉，请您第一时间联系尚品网客服热线4006-900-900（早08:00-晚22:00）或进入商品详情页面咨询在线客服，协助处理；对于被发错的商品，请拍摄下已收到商品的图片，与客服沟通何时，带客服确认后会为您安排补发或退款~</p>
                  <h3>6. 退款显示到银行卡了，但是一直未到账？</h3>
                  <p>部分银行可能没有短信到账通知，建议您先去查看一下您的银行卡交易明细哦，如果还是没有，可以联系一下我们商品详情页面的在线客服~</p>
                  <h3>7. 尚品最新会有什么活动？</h3>
                  <p>亲，关于活动您可以关注下我们小尚的首页哦，最新活动都有介绍的呢~</p>
                <div>
              </div>
  
            </div>
            
            
            
        </div>
    </div>
  </div>
</div>
</div>
 <!-- 公共尾部 start -->
<!-- <div class="app_bg"> -->
<!--     <footer class="app_foot"> -->
<!--         <p class="app_user"><a href="#">yeona</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">注册</a></p> -->
<!--         <div class="appCopyright"> -->
<!--             客服电话：<a href="tel:4006-900-900">4006-900-900</a>（08:00-24:00）<br /> -->
<!--             Copyright  2010-2016 Shangpin.com 版权所有<br /> -->
<!--             北京尚品百姿电子商务有限公司 -->
<!--         </div> -->
<!--     </footer> -->
<!-- </div> -->
<!-- 公共尾部 end -->
<input name="ctx" id="ctx" value="${pageContext.request.contextPath}"
       type="hidden" />
</body>
</html>
