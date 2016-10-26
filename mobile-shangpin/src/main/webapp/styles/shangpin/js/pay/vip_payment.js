
$(function(){
	$(".payment_box li").bind("click",function(){
		$(this).addClass("checked").siblings().removeClass("checked");
	})
});