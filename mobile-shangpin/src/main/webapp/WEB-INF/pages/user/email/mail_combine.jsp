<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/login.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/mail4_accout_combine.css${ver}" rel="stylesheet" />
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
    账号合并
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    账号合并
</rapid:override>
<rapid:override name="content">
    <div class="tabs_box">
        <div class="tabs_Cell">
            <form name="login" id="J_mail_combine_Form2" method="post" action="${ctx}/thirdLogin/bindPhone">
                <input type="hidden" name="bindteltype" value="4">
                <fieldset>
                    <p class="c-form-search">
                        <label for="mobi" class="mobi"></label>
                        <input readonly="readonly" onfocus="this.blur()" type="text" id="J_MobiName" name="mobi" placeholder="手机号" onkeyup='this.value=this.value.replace(/\D/gi,"")' maxlength="11" value = "${mobi}"/>
                    </p>
                    <input type="hidden" name="needCombine" value="1">
                    <div class="verification_box">
                        <p class="c-form-search verification">
                            <label for="mobiCode" class="mobiCode"></label>
                            <input type="text" id="J_MobiCode" name="mobiCode" placeholder="输入验证码" maxlength="6" />
                            <button type="button"></button>
                        </p>
                        <a href="javascript:;" class="getCode" id="passwordGetCode" data-waiting="秒">获取验证码</a>
                    </div>
                    <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
                    <input type="button" class="login_btn" value="绑定合并"/>
                </fieldset>
            </form>
        </div>
    </div>
</rapid:override>

<rapid:override name="footer">
</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>