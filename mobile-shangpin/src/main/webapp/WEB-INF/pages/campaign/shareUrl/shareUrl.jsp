<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="title">
    邀请好友
</rapid:override>
<rapid:override name="page_title">
    邀请好友
</rapid:override>

<rapid:override  name="custum">

    <link href="${cdn:js(pageContext.request)}/styles/shangpin/css/campaign/shareUrl/vip2016.css?${ver}" rel="stylesheet" /><!--页面专用CSS-->
    <%--<script src="${cdn:js(pageContext.request)}/styles/shangpin/js/core.js${ver}" type="text/javascript" charset="utf-8"></script>--%>
    <script type="text/javascript" charset="utf-8">
        loader = SP.core
        .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")    //图片懒加载
        .using("${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}")
        .excute()
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js${ver}")
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/campaign/shareUrl/member_invitation.js${ver}")
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/weixin/weixin_ready.js${ver}")
        .excute();
    </script>
    <script type="text/javascript">

        function share(){
            var name = $("#shareName").val();
            if(!name){
                alert("请输入您的姓名");
                return;
            }
            var url = $("#_shareUrl").val()+encodeURIComponent(name);
            console.info(url);
            var checkApp = $("#checkAPP").val();
            if (checkApp) {
                var url = $("#_shareUrl").val()+encodeURIComponent(name);
                url = encodeURI(url);
                var shareTitle = $("#_mainTitle").val();
                var shareDesc = $("#_mainDesc").val();
                var sharePic = $("#_mainImgUrl").val();
                if (url) {
                    window.location.href = "shangpinapp://phone.shangpin/actiongoshare?title=" + shareTitle + "&desc=" + shareDesc + "&url=" + url + "&imgurl=" + sharePic;
                }
            }
        }
    </script>
</rapid:override>

<rapid:override name="content">

        <!--主体-->
        <div id="invite_img">
           <%-- <img src="${cdn:js(pageContext.request)}/styles/shangpin/images/campaign/shareUrl/invite_bg.png">--%>
            <div class="invite_info_container">
                <h3>你已获得${canInviteCount}个 <br> PRIME 金牌会员邀请名额</h3>
                <input id="shareName" type="text" name="" value="" placeholder="输入你的名字，让小伙伴认识你">
                <button type="" onclick="share()">开始邀请</button>
            </div>
            <div class="invite_number">已邀请了&nbsp;<em>${invitedCount}</em>&nbsp;人</div>
        </div>
        <input type="hidden" name="checkAPP" id="checkAPP" value="${checkAPP}">
        <input type="hidden" name="_shareUrl"  id="_shareUrl"  value="${url}"/>
        <input type="hidden" name="_currentUrl" id="_currentUrl"  value=""/>
        <input type="hidden" name="_mainTitle"  id="_mainTitle"  value="尚品网《员工福利》"/>
        <input type="hidden" name="_mainDesc"  id="_mainDesc"  value="我在尚品，免费送您一年 PRIME 金牌会员！"/>
        <input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="http://pic5.shangpin.com/e/u/16/10/10/20161010163543957823-10-10.jpg"/>
       <%-- <input type="hidden" name="_mainTitle"  id="_mainTitle"  value="时髦精，你的机会来啦！"/>
        <input type="hidden" name="_mainDesc"  id="_mainDesc"  value="高颜值、懂时尚、好品位的你被我万里挑一。成为尚品网 PRIME 金牌会员，特权如下：1.全价商品享受8.5折优惠 2.每月会员日全站免国际、国内运费 3.15天无理由退换货"/>
        <input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="${cdn:pic(pageContext.request)}/styles/shangpin/images/campaign/shareUrl/share.jpg"/>--%>

</rapid:override>

<rapid:override name="footer">

</rapid:override>

<%--<%@ include file="/WEB-INF/pages/common/base.jsp" %>--%>
<%@ include file="/WEB-INF/pages/common/another_common.jsp"%>