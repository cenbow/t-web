<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override  name="custum">
    <link rel="stylesheet" href="${cdn:js(pageContext.request)}/styles/shangpin/css/font-css/font-awesome.min.css?${ver}">
    <link href="${cdn:js(pageContext.request)}/styles/shangpin/css/campaign/shareUrl/vip2016.css?${ver}" rel="stylesheet" />
    <script type="text/javascript" charset="utf-8">
        loader = SP.core.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js?${ver}")  //jquery库文件
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js?${ver}")    //图片懒加载
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js?${ver}")
                .excute()
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js?${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/campaign/shareUrl/member_invitation.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/weixin/weixin_ready.js${ver}")
                .excute();

    </script>
</rapid:override>

<rapid:override name="header">

</rapid:override>
<rapid:override name="title">
    好友邀请函
</rapid:override>
<rapid:override name="page_title">
    好友邀请函
</rapid:override>

<rapid:override name="content">

<div class="invitation">
    <input type="hidden" name="_mainTitle"  id="_mainTitle"  value="尚品网《员工福利》"/>
    <input type="hidden" name="_mainDesc"  id="_mainDesc"  value="我在尚品，免费送您一年 PRIME 金牌会员！"/>
    <input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="http://pic5.shangpin.com/e/u/16/10/10/20161010163543957823-10-10.jpg"/>
    <p style="text-align:center; margin-top:20px;">尚品网 PRIME 金牌会员</p>
    <p style="font-size:20px; text-align:center; letter-spacing:5px; font-weight:bold;margin-top:5px">邀请函</p>
    <p style="margin-top:15px;">时髦精，你的机会来啦！</p>
    <p style="margin-top:7px;">　　高颜值、懂时尚、好品位的你被我万里挑一。成为尚品网 PRIME 金牌会员，特权如下：</p>
    <p>1.全价商品享受8.5折优惠<br>2.每月会员日全站免国际、国内运费<br>3.15天无理由退换货</p>
    <p style="margin-top:10px; text-align:right;">——来自好友<span><strong>${name}</strong></span>的邀请</p>

    <div class="invitation_form clr">
        <dl class="invitation_form_title">立即成为 PRIME 金牌会员</dl>
        <dd>
            <input id="phone" type="tel" class="phonenum" placeholder="请输入您的手机号" value>
        </dd>
        <dd>
            <input type="hidden" id="inviteCode" value="${inviteCode}"/>
            <input type="tel" class="messagecode" placeholder="输入短信验证码" value>
            <div class="getcode" data-waiting="秒后重新获取">获取短信验证码</div>
            <span id="check_pop"></span>
        </dd>
        <dd>
            <a class="sure_form">确认</a>
        </dd>
    </div>
    <!--<p style="padding-bottom:10px;">温馨提示：<br>您的手机号可以直接登录尚品网，登录密码查看手机短信</p>-->
</div>

<!-- 已经体验过 -->
<div class="public_bg_fail_2" style="display:none">
    <div class="public_info">
        <%--<p class="public_info_title">抱 歉</p>--%>
        <p class="public_info_content public_center">您已经是尚品网 PRIME 金牌会员啦</p>
        <a href="http://m.shangpin.com/meet/791" class="public_info_button">立即体验</a>
    </div>
</div>

<div class="public_bg_fail_3" style="display:none">
    <div class="public_info">
        <%--<p class="public_info_title">抱 歉</p>--%>
        <p class="public_info_content public_center">您已经体验过尚品网 PRIME 金牌会员，如想继续享有 PRIME 金牌会员特权，您可以¥299/年 购买正式 PRIME 金牌会员，购买成功立返300元现金券礼包，使用无限制</p>
        <a href="${ctx}/t/vip/buy" class="public_info_button" >去购买</a>
    </div>
</div>

<div class="public_bg_fail_4" style="display:none">
    <div class="public_info">
       <%-- <p class="public_info_title">抱 歉</p>--%>
        <p class="public_info_content public_center">你来晚了<br>PRIME 金牌会员邀请名额已经送完</p>
        <a href="http://m.shangpin.com/download" class="public_info_button" >下载APP 去逛逛</a>
    </div>
</div>

<div class="public_bg_suc" style="display:none">
    <div class="public_info">
        <p class="public_info_content public_center">恭喜你获得了尚品网 PRIME 金牌会员特权,30天内成功购物1单，即可激活12个月会员权益</p>
        <a href="http://m.shangpin.com/download" class="public_info_button">下载APP，立即体验</a>
    </div>
</div>

</rapid:override>
<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/another_common.jsp" %>
