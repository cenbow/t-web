;$(function(){
	//移除默认提示
	$("#email").click(function(){
		$("#email").removeAttr("placeholder");
	});
	$("#utel").click(function(){
		$("#utel").removeAttr("placeholder");
	});
	$("#ucheckword").click(function(){
		$("#ucheckword").removeAttr("placeholder");
	});
	$(".date").click(function(){
		$(this).removeAttr("placeholder");
	});
	//判断格式
	$(".z-vipmember_btn").click(function() {
		var email_dom = $("#email");
        var utel_dom = $("#utel");
        var ucheckword_dom = $("#ucheckword");
        
		var emailval = email_dom.val();
		var utel = utel_dom.val();
		var ucheckword = ucheckword_dom.val();
		var birth = $("#birth").val();
		var sex = $("#sex").val()
		if(utel_dom && utel_dom.length>0){
			utel=utel_dom.val();
			ucheckword=ucheckword_dom.val();
            if(!(/^1\d{10}$/.test(utel))) {
                $('#check_pop').show();
                $("#check_pop div").html("请输入正确的手机号！").addClass("cur");
                return
            }else if (ucheckword.length != 6) {
                $('#check_pop').show();
                $("#check_pop div").html("验证码不正确！").addClass("cur");
                return
            }
		}
		if(email_dom && email_dom.length>0){
            emailval=email_dom.val();
            if (!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/).test(emailval)) {
                $('#check_pop').show();
                $("#check_pop div").html("请输入正确的邮箱地址！");
                return ;
            }
        }
		postTry();
	});
	
	//手机验证码倒计时
	var isclick = true;
	$("#passwordGetCode").on("click", function () {
		if (!isclick) return false;
		//手机号码验证
		var re = /^1\d{10}$/;
		var utel=$("#utel").val()
		if ($.trim(utel) == "" || !re.test(utel)) {
			$('#check_pop').fadeIn();
			setTimeout('$("#check_pop").fadeOut()', 2000)
			$("#check_pop div").html("请输入正确的手机号！").addClass("cur")
			return;
		}
		var that = $(this), timeId;
		var num = 60;
		var thiscon = $(this).attr("data-waiting");
		that.text(num + thiscon)
		isclick = false;
		$.post(getRootPath()+"/user/right/sendCode","phone="+utel);
		timeId = setInterval(function () {
			num--;
			that.text(num + thiscon)
			if (num == 0) {
				clearInterval(timeId);
				that.text("重新获取");
				isclick = true;
			}
		}, 1000);
	});
	//性别生日弹层
	var instance = mobiscroll.select('#sex', {
		theme: 'ios',
		lang: 'zh',
		display: 'bottom',
	});

	var instance = mobiscroll.date('#birth', {
		theme: 'ios',
		display: 'bottom',
		lang: 'zh',
		dateFormat: 'yyyymmdd',
		endYear:new Date().getFullYear() //结束年份
	});
});