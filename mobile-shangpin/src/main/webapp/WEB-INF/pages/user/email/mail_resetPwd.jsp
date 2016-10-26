<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>



<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/login.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/mail_resetpwd1.css${ver}" rel="stylesheet" />
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
    验证邮箱
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    重置密码
</rapid:override>
<rapid:override name="content">
    <form name="login" id="J_mailResetForm" method="post" action="${ctx}/user/resetPwd/email">
        <fieldset>
            <p class="c-form-search">
                <label for="mobi" class="mail"></label>
                <input type="text" readonly ="readonly" onfocus="this.blur()"  id="J_mailName" name="mobi" placeholder="请输入邮箱" value="${mobi}">
                <span class="datalist"></span>
            </p>
            <div class="verification_box">
                <p class="c-form-search verification">
                    <label for="mobiCode" class="mobiCode"></label>
                    <input type="text" id="J_MobiCode" name="mobiCode" placeholder="输入验证码" maxlength="6" />
                    <button type="button"></button>
                </p>
                <a href="javascript:;" class="getCode" id="emailGetCode" data-waiting="秒">发送邮件</a>
            </div>
            <div class="verification_box">
                <p class="c-form-search verification" style="width: 90%">
                    <label for="MobiPwd" class="MobiPwd"></label>
                    <input style="width: 60%" type="text" id="J_MobiPwd" name="password" placeholder="请设置6-20位字母或数字" maxlength="20"   />
                    <button type="button" ></button>
                </p>
                <span class="pwd_eyes"></span>
            </div>
            <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
            <input type="submit" class="login_btn" value="确认" />
        </fieldset>
    </form>
</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>

