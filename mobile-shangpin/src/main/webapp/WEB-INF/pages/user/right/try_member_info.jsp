<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/base.css${ver}"
		rel="stylesheet" />
	<%-- <link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/swiper.min.css"
		rel="stylesheet" /> --%>
	<link rel="stylesheet"
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/font-css/font-awesome.min.css" />
	<link rel="stylesheet"
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/mobiscroll.custom-3.0.0-beta4.min.css" />
	<link
		href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/vip/vip2016.css${ver}"
		rel="stylesheet" />
	<!--页面专用CSS-->

	<script
		src="${cdn:css(pageContext.request)}/styles/shangpin/js/core.js"
		type="text/javascript" charset="utf-8"></script>
	<script
		src="${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}"
		type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript" charset="utf-8">
		var ver = Math.random();
		loader = SP.core
				//jquery库文件
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
				//图片懒加载
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/swiper.min.js${ver}")
				.excute()
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				//图片懒加载
				.excute()
				.using(
						"${cdn:js(pageContext.request)}/styles/shangpin/js/fClub/fClub.js${ver}") //页面专用JS
				.excute()
		        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/mobiscroll.min.js${ver}")
		        .using("${cdn:js(pageContext.request)}/styles/shangpin/js/user/member_info.js${ver}")
	</script>
</rapid:override>
<%-- 浏览器标题 --%>
<rapid:override name="title">
	尚品会员
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
	尚品会员
</rapid:override>
<rapid:override name="content">
	<div class="z-vippower" style="margin-top: 0px ;">
    	<div class="vippower_title" style="font-size: 15px">您未完善个人资料</div>
        <p style="font-size: 12px;color: #000">方便尚品网提供更好的服务和保护您的账户</p>
    </div>
    <div class="member_info" style="height: 630px">
	    <form action="${ctx}/user/right/infopro" method="post" id="try_form">
	        <input type="hidden" id="topicId" name="topicId"  value="${topicId}" >
	        <div class="info_item sex">
	            <div>
	                <em class="info_title">性别：</em>
	                <span>
	                <c:set var="female" value="${sessionScope.mshangpin_user.gender==1 }"></c:set>
	                <select id="sex" name="gender">
	                	<option value="1" selected="${!female }">男</option>
	                	<option value="0" selected="${female }">女</option>
	                </select>
	                </span>
	            </div>
	        </div>
	        <div class="info_item birth clr">
	            <div>
	                <em class="info_title">生日：</em>
	                <span>
	                <input id="birth" name="birthday"/>
	                </span>
	            </div>
	        </div>
	        <c:if test="${sessionScope.mshangpin_user.canModifyEmail()}">
		        <div class="info_item">
		            <!--<div class="info_title">2. 您的邮箱</div>-->
		            <div class="verification_box">
		                <p class="c-form-search verification">
		                    <label for="email" class="info_title">邮箱：</label>
		                    <input type="text" id="email" name="email" placeholder="请输入邮箱">
		                    <!--<span class="datalist" style="display: none;"></span>-->
		                </p>
		            </div>
		        </div>
	        </c:if>
	        <c:if test="${ empty sessionScope.mshangpin_user.mobile }">
		        <div class="info_item">
		            <!--<div class="info_title">3. 您的手机</div>-->
		            <div class="verification_box" style="border-bottom: 1px solid #e5e5e5;margin: 0;padding-left: 3%">
		                <p class="c-form-search verification">
		                    <label for="mobile" class="info_title">手机：</label>
		                    <input type="text" id="utel" name="phone" placeholder="请输入手机号" maxlength="11">
		                    <!--<span class="datalist" style="display: none;"></span>-->
		                </p>
		            </div>
		            <div class="verification_box">
		
		                <p class="c-form-search verification">
		                    <label for="mobiCode" class="info_title" style="opacity: 0">验证：</label>
		                    <input type="text" id="ucheckword" name="code" placeholder="请输入验证码" maxlength="6">
		                    <!--<button type="button" style="display: none;"></button>-->
		                </p>
		                <a href="javascript:;" class="getCode" id="passwordGetCode" name="code" data-waiting="秒">获取验证码</a>
		            </div>
		        </div>
			</c:if>
	        <div class="z-vipmember_btn">
	            <a href="javascript:;">确定</a>
	        </div>
		</form>
    </div>
<!--网络故障-->
  <div class="pop error-pop" id="error-pop">
      <div style="position:absolute;top:40%;left:10%; background:#fff; width:80%; padding:20px 0; border-radius:10px;">
          <div style=" width:80px; height:80px; margin:0 auto;"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/member/error.png" style="width:100%;"></div>
          <div style=" font-size:17px; line-height:58px; text-align:center;" id="errorMsg">可能网络有故障，请重试!</div>
          <div style="width:140px; height:45px; margin:0 auto; background:#0700c5; color:#fff; line-height:45px; text-align:center; font-size:17px;" onclick="knowError();">我知道了</div>
      </div>
  </div>

  <!--申请通过-->
  <div class="pop smile-pop" id="smile-pop">
      <div style="position:absolute;top:40%;left:10%;  background:#fff; width:80%; padding:20px 0; border-radius:10px;">
          <div style=" width:80px; height:80px; margin:0 auto;"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/member/smile.png" style="width:100%;"></div>
          <div style=" width:80%; margin:0 auto; font-size:17px; line-height:20px; margin-top:22px; margin-bottom:22px;text-align:center;">恭喜您已成功开启 PRIME 金牌会员试用资格!</div>
          <div style="width:140px; height:45px; margin:0 auto; background:#0700c5; color:#fff; line-height:45px; text-align:center; font-size:17px;" onclick="javascript:window.location.href='http://m.shangpin.com/meet/791'">立即去体验</div>
      </div>
  </div>

 <!--申请通过 从活动页面过来。需要返回到活动页面-->
  <div class="pop smile-pop" id="smile-pop-topic">
      <div style="position:absolute;top:40%;left:10%;  background:#fff; width:80%; padding:20px 0; border-radius:10px;">
          <div style=" width:80px; height:80px; margin:0 auto;"><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/member/smile.png" style="width:100%;"></div>
          <div style=" width:80%; margin:0 auto; font-size:17px; line-height:20px; margin-top:22px; margin-bottom:22px;text-align:center;">恭喜您已成功开启 PRIME 金牌会员试用资格!</div>
      </div>
  </div>

  <!--格式验证-->
  <div class="pop check_pop" id="check_pop"  onclick="document.getElementById('check_pop').style.display='none';">
      <div style="position:absolute;top:40%;left:10%; background:#fff; width:80%; padding:20px 0; border-radius:10px;text-align: center"></div>
  </div>
<script type="text/javascript">
var _trySubmit=true;
function postTry(){
	if(!_trySubmit) return;
	var topicId=$("#topicId").val();
	var form=$("#try_form");
	var data=form.serialize();
	var url=form.attr("action");
	var btn_a=$(".z-vipmember_btn a").first();
	btn_a.text("提交中");
	$.ajax({
		type:"POST",
		url:url,
		data:data,
		success:function(result){
			if(result.code=='0'){
				_trySubmit=false;
				if(topicId!=""){
					$('#smile-pop-topic').show();
					setTimeout(function(){
						$('#smile-pop-topic').hide();
							window.location=getRootPath()+"/subject/product/list?topicId="+topicId;
					},2000);
				}else{
					$('#smile-pop').show();				
				}
			}else{
			    $("#errorMsg").text(result.msg);
				$('#error-pop').show();
				btn_a.text("重试");
				_trySubmit=true;
			}
		},error:function(result){
			_trySubmit=true;
            $("#errorMsg").text("可能网络有故障，请重试!");
			btn_a.text("重试");
			$('#error-pop').show();			
		}
	});
}

function knowError() {
    $("#errorMsg").text("可能网络有故障，请重试!");
    $('#error-pop').hide();
}
</script>
</rapid:override>
<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>