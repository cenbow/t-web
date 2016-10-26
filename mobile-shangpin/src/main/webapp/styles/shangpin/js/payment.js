$(function(){

	//礼品支付页密码输入框修改浏览器兼容问题
	if($(".paymet_success").length>0 && $("#giftPsd").length>0){
		$("#giftPsd").focus(function(){
			$(window).scrollTop($(".giftCard").offset().top);
		});
	}

	$(".paymet_block fieldset p span a").click(function(){
		$(".paymet_block fieldset p span a").removeClass("cur");
		$(this).toggleClass("cur");
	})

	//选择礼品卡支付
	$("#giftCardpay").click(function(){

		$(this).toggleClass("cur");
		var onlineAmount = $("#onlineAmount").val();//订单需要支付的金额，已经优惠后需要支付的金额
		var canUseGiftAmount = $("#canUseGiftAmount").val();//礼品卡余额
		var realPayAmount = $("#realPayAmount").val();

		//如果选择礼品卡支付
		if($(this).hasClass("cur")){
			$(".giftCard a i").remove();
			var $html1 = "<i>使用礼品卡为本次支付 &yen;<b id='paycard'>" + canUseGiftAmount + "</b></i>"
			$("#giftcardbalance").closest("a").append($html1);
			$("#realpay").text(realPayAmount);
			//如果礼品卡的余额大于需要支付的金额,则礼品卡全额支付
			if(parseInt(realPayAmount)<=0){
				$(".payment-method").hide();
			}else{
				$(".payment-method").show();
			}
		}else{
			$(".giftCard a i").remove();
			$("#realpay").text(onlineAmount);
			$(".payment-method").show();
		}
		return false;
	});
	$("#giftCardpay").trigger("click");
})

//礼品卡支付
function normal_pay(){

	var path = getRootPath();
	var orderId = $("#orderId").val();
	var giftPsd = $("#giftPsd").val();
	var date = $("#date").val();
	var url =getPay().url;
	var gateWay =getPay().gateWay;
	if($("#giftCardpay").hasClass("cur")){

		if(giftPsd == ""){
			alert("请输入礼品卡密码!");
			return;
		}
		$(".payment_btn").text("正在支付，请稍等.......");
		$.post(path + "/order/pay/giftCard/pay",{
			"orderId" : orderId,
			"password" : giftPsd,
			"gateWay":gateWay
		},function(data){
			if(data.code == "0"){
				//判断礼品卡是不是全额支付
				if(data.returnInfo.payresult == "2"){
					window.location.href = path  + "/order/pay/giftCard/success?orderId=" + orderId;
				}else{
					alert("礼品卡支付成功！");
					window.location.href = getRootPath() +url+"?orderId="+orderId;
				}
			}else{
				alert(data.msg);
				$(".payment_btn").text("立即支付");
				return;
			}
		},"json")
	}else{
		window.location.href = getRootPath() +url+"?orderId="+orderId;
	}

	function getPay() {
		var pay = {};
		$(".alContent fieldset p.payment-method").each(function(index,item){
			var $span_a = $("span a",item);
			if($span_a.hasClass("cur")){
				pay.url = $span_a.parent("span").attr("url");
				pay.gateWay = $span_a.parent("span").attr("gateWay");
			}
		})
		return pay;
	}
}
