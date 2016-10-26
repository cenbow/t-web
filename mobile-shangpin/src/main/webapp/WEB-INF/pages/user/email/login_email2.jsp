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
    邮箱登录
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    邮箱登录
</rapid:override>
<rapid:override name="content">
    <form name="login" id="J_mailLoginForm" method="post" action="${ctx}/login/normal">
        <fieldset>
            <p class="c-form-search">
                <label for="email" class="mail"></label>
                <input readonly="readonly" onfocus="this.blur()" type="text" id="J_mailName" name="mobi" placeholder="请输入邮箱地址" required value="${mobi}" />
                <%--<button type="button"></button>--%>
            </p>
            <input type="hidden"  name="checkType" value = "2" />
            <p class="c-form-search">
                <label for="MobiPwd" class="MobiPwd"></label>
                <input type="password" id="J_MobiPwd" name="password" placeholder="请输入登录密码" maxlength="20"  onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled" />
                <button type="button" ></button>
                <span class="pwd_eyes close"></span>
            </p>
            <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
            <input type="button" class="login_btn" value="登 录" />
            <a href="javascript:void(0);" onclick="forgetPass('mail')" class="login_link">忘记密码？</a>
        </fieldset>
    </form>
</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>
