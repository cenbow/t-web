<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request) }/styles/shangpin/css/page/member.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request) }/styles/shangpin/css/page/addr.css${ver}" rel="stylesheet" />
	<script type="text/javascript" charset="utf-8">
		loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/j.dialogs.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/overseas/validIDCard.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/address.js${ver}")
				.excute()
	</script>
</rapid:override>



<rapid:override name="page_title">
	编辑地址
</rapid:override>

<rapid:override name="content">
		<div class="paymet_block">
			<input type="hidden" id="msg"  value="${msg}"/>
			<form name="login" id="J_Login" method="post" action="${ctx}/address/update">
	        	<input type="hidden" id="addressId" name="id" value="${address.id}"/>
	        	<input type="hidden" id="province" name="province" value="${address.province}"/>
	        	<input type="hidden" id="provincename" name="provname" value="${address.provname }"/>
	        	<input type="hidden" id="city" name="city" value="${address.city}"/>
	        	<input type="hidden" id="cityname" name="cityname" value="${address.cityname}"/>
	        	<input type="hidden" id="area" name="area" value="${address.area}"/>
	        	<input type="hidden" id="areaname" name="areaname" value="${address.areaname}"/>
	        	<input type="hidden" id="town" name="town" value="${address.town}"/>
	        	<input type="hidden" id="townname" name="townname" value="${address.townname}"/>
	            <fieldset class="notBtm">
	                <legend>收货人信息</legend>
	                <p>
	                    <label for="userName">姓名：</label>
	                    <input type="text" id="J_userName" name="name" value="${address.name}" placeholder="请填写个中文名称" required />
	                </p>
	                <p>
	                    <label for="mobileNum">联系电话：</label>
	                    <input type="text" id="J_mobileNum" name="tel" value="${address.tel}" placeholder="请输入11位手机号码" required maxlength="11" />
	                </p>
	                <legend>收货地址</legend>
	                <p class="select"><a href="#" id="select_area">${address.provname}${address.cityname}${address.areaname}${address.townname}</a></p>
	                <p>
	                    <label for="addr">详细地址：</label>
	                    <input type="text" id="J_addr" name="addr" value="${address.addr}" placeholder="请输入详细街道地址" required maxlength="50" />
	                </p>
	                <p>
	                    <label for="code">邮编信息：</label>
	                    <input type="text" id="J_code" name="postcode" value="${address.postcode}" placeholder="请输入您所在地区的邮编" required />
	                </p>
	                <p>
	                    <label for="code"  style="width:75px;">身份证号码：</label>
	                    <input type="text" id="J_cardID" name="cardID" placeholder="请填写收货人的身份证号码"  value="${address.cardID}" maxlength="18" />
	                </p>
	                <ul>
	                    <li>身份证号码仅在您购买海外商品时使用，如您购买国内商品可不填</li>
	                </ul>
	                <div class="payment_submit">
	                    <a href="javascript:;" class="payment_btn">保存</a>
	                    <input type="submit" <%--onclick="toUpdateAddress()"--%> class="payment_btn"  value="保存" />
	                </div>
	            </fieldset>
	            <div class="explain">
                <h3>关于身份证填写的声明</h3>
                <p>1. 身份证号码仅在您购买海外商品时使用，根据海关的要求，购买海外商品时需要您填写收货人的身份证号码进行个人物品入境申报。</p>
                <p>2. 因海关会对您填写的身份证是否会收货人的身份证信息进行验证，请确保填写正确，否则商品可能无法正常通关。</p>
                <p>3. 尚品网承诺，您填写的身份证信息仅用于清关，绝不做他用，请放心填写。</p>
            </div>
	        </form>
	    </div>

	<div id="area_overlay"></div>
	<div id="area_layer">
		<a href="javascript:;" class="prev_btn">返回</a>
        <a href="javascript:;" class="close_btn">关闭</a>
		<h3>省份</h3>
	    <dl id="area_province" title="省份">
	    	<c:forEach var="provice" items="${provinces}">
	    		<dd><a href="#" id="${provice.id}">${provice.name}</a></dd>
	    	</c:forEach>
	    </dl>
	    <dl id="area_city" title="城市">
	       
	    </dl>
	    <dl id="area_county" title="地区">
	        
	    </dl>
	    <dl id="area_street" title="街道">
	       
	    </dl>
	</div>
</rapid:override>
<rapid:override name="down_page">
	
</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %> 