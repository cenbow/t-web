
$(function(){
	$(".payment_box li").bind("click",function(){
		$(this).addClass("checked").siblings().removeClass("checked");
	})
	
	/*确认支付弹层*/
	$(".sure_pay").click(function(){
		//$(".purchase_bg").show();	
	});
	$(".purchase_bg li").click(function(){
		queryPayResult();	
	});
});