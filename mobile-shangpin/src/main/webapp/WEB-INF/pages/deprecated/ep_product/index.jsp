<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>

<rapid:override name="custum">
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/public.css${ver}" rel="stylesheet" />
	<link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/product/detail.css${ver}" rel="stylesheet" />
	<script type="text/javascript">window.bd && bd._qdc && bd._qdc.init({app_id: '75feae525068fb2bec34e48e'});</script>
	<script type="text/javascript" charset="utf-8">
			loader = SP.core
				.install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/weixin/jweixin-1.0.0.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/weixin/WXShareM.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js//j.dialogs.js${ver}")
				.excute()
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/comm.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/downloadAppShow.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/product/epProductDetail.js${ver}")
				.using("${cdn:js(pageContext.request)}/styles/shangpin/js/product/commonProductDetail.js${ver}")
	</script>

	<!--集成脚本加载 -->
	<script language="javascript" type="text/javascript">

		NTKF_PARAM = {
			siteid:"kf_9986",                    	//企业ID，为固定值，必填
			settingid:"kf_9986_1355827406710",   	//接待组ID，为固定值，必填
			uid:"${mshangpin_user.userid}",         //用户ID，未登录可以为空，但不能给null，uid赋予的值在显示到小能客户端上
			uname:"${mshangpin_user.name}",         //用户名，未登录可以为空，但不能给null，uname赋予的值显示到小能客户端上
//			isvip:"1",                          //是否为vip用户，0代表非会员，1代表会员，取值显示到小能客户端上
			userlevel:"${mshangpin_user.lv}",       //网站自定义会员级别，0-N，可根据选择判断，取值显示到小能客户端上
//			erpparam:"abc",                     //erpparam为erp功能的扩展字段，可选，购买erp功能后用于erp功能集成
			itemid: "${productDetail.basic.firstProps[0].secondProps[0].sku}",				       	//(必填)商品ID
			itemparam:"m"				   			//(选填)itemparam为商品接口扩展字段，用于商品接口特殊要求集成
		}

	</script>
	<!--基础脚本加载 -->
	<script type="text/javascript" src="http://download.ntalker.com/t2ds/ntkfstat.js" charset="utf-8"></script>

</rapid:override ><rapid:override name="title">
    	商品详情
</rapid:override>
<rapid:override name="downloadAppShowBottom">
</rapid:override>
<rapid:override name="page_title">
	商品详情
</rapid:override>
<rapid:override name="appLayer">
</rapid:override>
<rapid:override name="content">
<input id="_iswx" name="_iswx" type="hidden" value="${checkWX }"/>
<input type="hidden" name="_shareUrl"  id="_shareUrl"  value="${productDetail.share.url}"/>
<input type="hidden" name="_mainTitle"  id="_mainTitle"  value="${productDetail.share.title}${productDetail.share.desc}"/>
<input type="hidden" name="_mainDesc"  id="_mainDesc"  value="${productDetail.share.title}"/>
<input type="hidden" name="_mainImgUrl" id="_mainImgUrl"  value="${fn:replace(productDetail.share.pic,'-10-10','-160-218') }"/>
<c:import url="basic.jsp"></c:import>  
<c:import url="baInfo.jsp"></c:import> 
<c:import url="info.jsp"></c:import>
<c:import url="brandStory.jsp"></c:import>
<c:import url="other_info.jsp"></c:import>
<c:import url="buy.jsp"></c:import>
</rapid:override>


<%@ include file="/WEB-INF/pages/common/another_common_mall_banner.jsp" %> 