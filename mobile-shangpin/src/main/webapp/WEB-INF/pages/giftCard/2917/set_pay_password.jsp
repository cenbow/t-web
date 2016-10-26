<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<rapid:override name="custum">
<style>
.main_box img{ width:100%}
</style>
<script type="text/javascript" charset="utf-8">
  var ver = Math.random();
    loader = SP.core
      .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js?" + ver)  //jquery库文件
        .excute()
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/giftcard/set.js?" + ver)    //页面专用JS
        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js?" + ver)   //页面专用JS
        .excute()
      
</script>
<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/giftcard/set_pay_password.css?${ver}" rel="stylesheet" /><!--页面专用CSS-->
</rapid:override>
<%-- 页标题 --%>
<rapid:override name="page_title">
	设置支付密码
</rapid:override>


<rapid:override name="downloadAppShowHead">

</rapid:override>

<rapid:override name="content">
	 <div class="tabs_box">
    	<div class="tabs_Cell">
        	<div class="phone_num">请输入<em>${phone }</em>收到的短信验证码</div>
            <form name="login" id="J_Forget2" action="cardPayPasswd" method="post">
                <fieldset>
                    <input  id="phone" name="phone" type="hidden"  value="${sessionScope.mshangpin_user.mobile }"/>
                     <input type="hidden" name="msg" id="msg" value="${msg }" />
                    <p class="c-form-search verification">
                        <label for="mobiCode" class="mobiCode"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/giftcard/check_code.png"></label>
                        <input type="text" id="J_MobiCode" name="mobiCode" placeholder="请输入验证码" required maxlength="6" />
                        <button type="button"></button>
                        <a href="javascript:;" class="login_btn get_code" data-waiting="秒后再次获取">获取验证码</a>
                    </p>
                    <p class="c-form-search">
                        <label for="MobiPwd" class="MobiPwd"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/giftcard/password.png"></label>
                        <input type="password" id="J_MobiPwd" name="passwd" placeholder="请设置6-20位字母、数子特殊符号的密码" required maxlength="20" />
                        <button type="button" ></button>
                        <span class="pwd_eyes close"></span>
                    </p>
                    <p class="c-form-search">
                        <label for="resetPwd" class="resetPwd"><img src="${cdn:js(pageContext.request)}/styles/shangpin/images/giftcard/password.png"></label>
                        <input type="password" id="J_resetPwd" name="nextpasswd" placeholder="请再次输入密码" required maxlength="20" />
                        <button type="button" ></button>
                        <span class="pwd_eyes close"></span>
                    </p>
                    <p class="login_errorMsg mobiMsg">&nbsp;</p>
                    <input type="submit" class="login_btn" value="确 定" />
                </fieldset>
            </form>
            
        </div>
      </div>
</rapid:override>
<rapid:override name="down_page">
	
</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %> 
