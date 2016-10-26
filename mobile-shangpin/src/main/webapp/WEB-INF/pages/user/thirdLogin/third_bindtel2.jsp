<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/login.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/others_bindtel2.css${ver}" rel="stylesheet" />
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
    绑定手机
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    绑定手机
</rapid:override>
<rapid:override name="content">
    <div class="tabs_box">
        <div class="tabs_Cell">

            <form name="login" id="J_mobiForm2" method="post" action="${ctx}/thirdLogin/bindPhone">
                <input type="hidden" name="bindteltype" value="2">
                <fieldset>
                    <p class="c-form-search">
                        <label for="mobi" class="mobi"></label>
                        <input readonly="readonly" onfocus="this.blur()" type="text" id="J_MobiName" name="mobi" placeholder="手机号" onkeyup='this.value=this.value.replace(/\D/gi,"")' maxlength="11" value="${mobi}"/>
                    </p>
                    <div class="verification_box">
                        <p class="c-form-search verification">
                            <label for="mobiCode" class="mobiCode"></label>
                            <input type="text" id="J_MobiCode" name="mobiCode" placeholder="输入验证码" maxlength="6" />
                            <button type="button"></button>
                        </p>
                        <a href="javascript:;" class="getCode" id="passwordGetCode" data-waiting="秒">获取验证码</a>
                    </div>
                    <div class="verification_box">
                        <p class="c-form-search verification" style="width: 90%">
                            <label for="MobiPwd" class="MobiPwd"></label>
                            <input style="width: 60%" type="text" id="J_MobiPwd" name="MobiPwd" placeholder="请设置6-20位字母或数字" maxlength="20" />
                            <button type="button" ></button>
                        </p>
                        <span class="pwd_eyes"></span>
                    </div>
                    <!--<a class="pwd_tips" href="javascript:;" style="margin-top: 5px">密码由6-20位数字组成，区分大小写</a>-->
                    <a class="pwd_tips" href="javascript:;" style="margin-top: 5px">绑定后，您的${third_type}账号和手机号都可以登录</a>
                    <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
                    <input type="button" class="login_btn" value="绑 定" />
                </fieldset>
                <%--<div class="pop_overlay">
                    <div class="pop_container">
                        <h2>该手机号已注册</h2>
                        <p>
                            <span>1.请确认手机号码是否输入有误;</span>
                            <span>2.绑定该手机,则当前登录账号会和手机账号进行合并;</span>
                            <span>3.合并后,订单/礼品卡等记录会加到一起,不会丢失;购物车/待支付商品以手机账号为准;</span>
                            <span>4.合并后,会员权益会根据两个账号贡献之和重新计算，会有惊喜哦!</span>
                            <span>5.合并后,登录密码/支付密码/头像/昵称/生日/性别/尺码等个人资料以手机账号为基准，建议登录后修改完善;</span>
                            <span>6.合并后,两个账号原有的登录方式均有效，均登录到合并后的账号，建议登录后到个人中心删除不常用的登录方式;</span>
                            <span>7.如有任何问题,请致电尚品客服4006-900-900</span>
                        </p>
                        <input type="submit" class="login_btn" value="进行绑定">
                    </div>
                    <em class="close_pop"></em>
                </div>--%>
            </form>
        </div>
    </div>

</rapid:override>

<rapid:override name="footer">
</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>
