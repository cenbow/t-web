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
    手机登录
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    手机登录
</rapid:override>
<rapid:override name="content">
    <%--<div class="topFix">
        <section>
            <div class="topBack" >
                <a href="/html/2016Mapp/brand.html" class="backBtn">&nbsp;</a>
                手机登录
            </div>
        </section>
    </div>--%>
    <!--头部 end-->

    <form name="login" id="J_mobiForm3" method="post" action="${ctx}/login/normal">
        <fieldset>
            <p class="c-form-search">
                <label for="mobi" class="mobi"></label>
                <input readonly="readonly" onfocus="this.blur()" type="text" id="J_MobiName" name="mobi" placeholder="手机号" onkeyup='this.value=this.value.replace(/\D/gi,"")' maxlength="11" value="${mobi}" />
                <%--<button type="button"></button>--%>
            </p>
            <input type="hidden"  name="checkType" value = "1" />
            <input type="hidden"  id="isRegiste" value = "1" />
            <p class="c-form-search">
                <label for="MobiPwd" class="MobiPwd"></label>
                <input type="password" id="J_MobiPwd" name="password" placeholder="请输入登录密码" maxlength="20"  onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled" />
                <button type="button" ></button>
                <span class="pwd_eyes close"></span>
            </p>
            <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
            <input type="button" class="login_btn" value="登 录" />
            <a href="javascript:void(0)" onclick="forgetPass('phone')" class="login_link">忘记密码？</a>
        </fieldset>
    </form>
</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>