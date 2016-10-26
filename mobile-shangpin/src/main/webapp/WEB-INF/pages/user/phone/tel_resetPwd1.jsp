<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>



<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/login.css${ver}" rel="stylesheet" />
    <script type="text/javascript" charset="utf-8">
        loader = SP.core
                .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
                .excute()
                <%--.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")--%>
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/user/login.js${ver}")
                .excute()
    </script>
</rapid:override >


<%-- 浏览器标题 --%>
<rapid:override name="title">
    验证手机
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    验证手机
</rapid:override>
<rapid:override name="content">
    <form name="login" id="J_mobiForm1" method="post" action="${ctx}/user/toFindPwd/checkCode">
        <fieldset>
            <p class="c-form-search">
                <label for="mobi" class="mobi"></label>
                <input type="text" id="J_MobiName" name="mobi" placeholder="手机号" maxlength="11" onkeyup='this.value=this.value.replace(/\D/gi,"")' value = "${mobi}" />
                <button type="button"></button>
                <span class="datalist"></span>
            </p>
            <div class="verification_box">
                <p class="c-form-search verification">
                    <label for="verCode"  class="verCode"></label>
                    <input type="text" id="J_verCode" name="verCode" placeholder="请输入图形码" class="verCode"  maxlength="4" />
                    <button type="button"></button>
                </p>
                <span>
                <img src="${ctx}/captcha" width="100" height="34" onclick="changeImage(this);">
            </span>
            </div>

            <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
            <input type="button" class="login_btn" value="下一步" readonly="readonly"/>
           <%-- <p  class="login_mail"><a href="${ctx}/user/toFindPwd/email">邮箱找回密码</a></p>--%>
        </fieldset>
    </form>

        <!--<div class="btm_img"><a href="#"></a></div>-->
        <%--<div id="others" class="clr">
            <p>
                <span>第三方登录</span>
                <em></em>
            </p>
            <div class="other-login">
                <c:if test="${checkWX}">
                    <a href="javascript:" onclick="thirdLogin('wx')"><i><img alt="" src="${cdn:pic(pageContext.request)}/styles/shangpin/images/user/login/wechat.png"></i></a>
                </c:if>
                <a href="javascript:" onclick="thirdLogin('qq')"><i><img alt="" src="${cdn:pic(pageContext.request)}/styles/shangpin/images/user/login/qq.png"></i></a>
                <a href="javascript:" onclick="thirdLogin('wb')"><i><img alt="" src="${cdn:pic(pageContext.request)}/styles/shangpin/images/user/login/weibo.png"></i></a>
            </div>
        </div>--%>

</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>

