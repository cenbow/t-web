<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/login.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/mail3_bindtel.css${ver}" rel="stylesheet" />
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
            <form name="login" id="J_bindForm" method="post" action="${ctx}/thirdLogin/bindPhone">
                <input type="hidden" name="bindteltype" value="3">
                <fieldset>
                    <p class="bind_tips">绑定常用的手机号：</p>
                    <p class="c-form-search">
                        <label for="mobi" class="mobi"></label>
                        <input type="text" id="J_MobiName" name="mobi" placeholder="手机号" onkeyup='this.value=this.value.replace(/\D/gi,"")' maxlength="11" value="${mobi}"/>
                        <button type="button"></button>
                        <span class="datalist"></span>
                    </p>
                    <input type="hidden" id="third_bind_tel_page" value="${type_num}">
                    <div class="verification_box">
                        <p class="c-form-search verification">
                            <label for="mobiCode" class="mobiCode"></label>
                            <input type="text" id="J_MobiCode" name="mobiCode" placeholder="输入验证码"  maxlength="6" />

                            <button type="button"></button>
                        </p>
                        <a href="javascript:;" class="getCode" id="passwordGetCode" data-waiting="秒">获取验证码</a>
                    </div>
                    <a class="pwd_tips" href="javascript:;">绑定后，使用邮箱或手机均可登录</a>
                    <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
                    <input type="button" class="bind_btn" value="绑 定" />
                </fieldset>
                <div class="pop_overlay">
                    <div class="pop_container">
                        <h2>账号合并声明</h2>
                        <p>
                            <span>您输入的手机号码已在尚品网注册过，是否要将当前登录账号和手机账号合并？</span>
                            <span>1.如果不想合并，请关闭当前页面，输入其他手机号码或直接用手机账号登录；</span>
                            <span>2.如果选择合并，则当前登录账号会和手机账号合并成一个账号：</span>
                            <span>a)合并后,订单/礼品卡等记录会合到一起,不会丢失; </span>
                            <span>b)合并后,会员等级及权益等会根据两个账号消费金额综合计算，等级有可能会提升;  </span>
                            <span>c)合并后,个人资料如头像/性别/生日/地址等均以手机账号的信息为准，当前登录账号的信息暂不考虑；</span>
                            <span>d)合并后，购物车/优惠券/愿望清单等均以手机账号的信息为准，当前登录账号的信息暂不考虑；</span>
                            <span>e)合并后,无论用哪个账号登录，均登录到合并后的账号，登录密码以手机账号的密码为准，如果密码不对，请通过手机/邮箱找回密码；</span>
                            <span>如有任何问题,请致电尚品客服4006-900-900</span>
                        </p>
                        <input type="submit" class="login_btn" value="同意合并">
                    </div>
                    <em class="close_pop"></em>
                </div>
            </form>
        </div>
    </div>
</rapid:override>

<rapid:override name="footer">
</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>