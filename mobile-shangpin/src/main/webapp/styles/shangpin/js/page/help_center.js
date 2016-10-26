$(function(){
   //tab切换
	$(".tab_info").find("a").click(function(e){
		e.preventDefault();
		var $this = $(this);
		var $thisIndex = $this.index();
		$(this).addClass("curr").siblings().removeClass("curr");
		$(".content_info").find(".content_list").eq($thisIndex).show().siblings().hide();
	});
	//控制是否显示网页头


	/*function GetQueryString(name)
	{
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r!=null)return  unescape(r[2]); return null;
	}
	var isApp = GetQueryString("isApp");
	if(isApp=="1"){
		$(".topFix").hide();
		//便利li 增加参数
		var list = $(".content-menu  li");
		list.each(function(){
			var url =$(this).children("a").attr("href")+"?isApp=1";
			$(this).attr("href",url);
		});

	}*/

});
function chatXiaoNeng(){
	var loginUrl =$("#loginUrl").val();
	if(loginUrl){
		window.location.href=loginUrl;
        return;
	}
	NTKF.im_openInPageChat('kf_9986_1355827406710');
}
 
 


