(function(e) {
	//读取错误msg
	var msg=$("#msg").val();
	if(msg!=""){ 
		$(".mobiMsg").html(msg);
	}
	
    e.search = function(t) {
        return this.el = e(t),
        this.el.each(this.init);
    },
    e.extend(e.search.prototype, {
        init: function(t) {
            var n = e(this).find("input"),
			str,
			v = e(this).find("span.datalist"),
			vLen = v.length,
			filter = ["qq.com","sina.com","sohu.com","126.com","163.com","139.com"],
            r = e(this).find("button");	! n.val() && (r[0].style.display = "none");
			if(vLen > 0){! n.val() && (v[0].style.display = "none");}
			
            var fn = function() {
                r[0].style.display = "block",
                !n.val() && (r[0].style.display = "none");
				
				if(n.val().indexOf("@") > -1 && vLen > 0){
					
					v[0].style.display = "block",
					! n.val() && (v[0].style.display = "none");
					
					var word = n.val().substr(n.val().indexOf("@")+1), w = "", ret = [];
					if (word === "" && v.find("a").length == 0) {
						
						for (var i = 0, len = filter.length; i < len; i++) {
							v.append("<a href='#'>" + filter[i] + "</a>");
						}
						
						return filter;
					}
					for (var i = 0, len = filter.length; i < len; i++) {
						w = filter[i];
						if (w.indexOf(word) === 0) {
							ret.push(w);
						}
					}
					if(ret.length > 0){
						//console.log(ret);
						v.empty();
						for(var i = 0, len = ret.length; i < len; i++){
							v.append("<a href='#'>" + ret[i] + "</a>");
						}
						
						if(n.val().indexOf(".") > -1){
							v[0].style.display = "none";
						}else{
							v[0].style.display = "block",
							! n.val() && (v[0].style.display = "none");
						}
						
					}else{
						v.hide();
					}
					return ret;
					
				}else{
					v.hide();
				}
				
				str = $.trim(n.val());
				
            };
            n.keyup(fn),
            r.click(function(e) {
                n.val("").focus(),
                r[0].style.display = "none";
				if(vLen > 0){
					v[0].style.display = "none"
				}
                e.preventDefault();
				$(".login_btn").removeClass("click");
            }),
			v.delegate("a","click",function(e){
				//console.log(str);
				v[0].style.display = "none",
				n.val(str + "@" + $(this).text()),
				e.preventDefault();
			}),
			n.blur(function(){
				if(vLen > 0){
					setTimeout(function(){v[0].style.display = "none"},100);
				}
				setTimeout(function(){r[0].style.display = "none"},100);
			});
        }
    });
})(jQuery),
function() {
    window.Login = function() {},
    $.extend(window.Login.prototype, {
        init: function() {},
        loginForm: function() {
            $("#J_Login").on("submit",
            function(e) {
                var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/,
					mre = /^1\d{10}$/;
				if ($.trim($("#J_UserNameTxt").val()) == "" || !re.test($("#J_UserNameTxt").val()) && !mre.test($("#J_UserNameTxt").val())){
					return $(".login_errorMsg").html("请输入正确邮箱地址或手机号码！"),
					$("#J_UserNameTxt").addClass("error"),
                	!1;
				}else{
					$(".login_errorMsg").html("");
					$("#J_UserNameTxt").removeClass("error");
				}
				window.localStorage.setItem("lastNick",$("#J_UserNameTxt").val());
				
                if ($.trim($("#J_PassWordTxt").val()) == ""){
					return $(".login_errorMsg").html("请输入密码！"),
					$("#J_PassWordTxt").addClass("error"),
					!1;
				}else{
					$(".login_errorMsg").html("");
					$("#J_PassWordTxt").removeClass("error");
				}
            }),
            new $.search(".c-form-search");
        },
		mobiForm1: function() {
            $("#J_mobiForm1").on("submit",
            function(e) {
                var re = /^1\d{10}$/;
				if ($.trim($("#J_MobiName").val()) == "" || !re.test($("#J_MobiName").val())){
					return $(".mobiMsg").html("请输入正确手机号码！"),
					$("#J_MobiName").addClass("error"),
                	!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiName").removeClass("error");
				}
								
				if ($.trim($("#J_MobiCode").val()) == ""){
					return $(".mobiMsg").html("请输入验证码！"),
					$("#J_MobiCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiCode").removeClass("error");
				}
				if ($.trim($("#J_MobiCode").val()) == "" || $("#J_MobiCode").val().length != 6){
					return $(".mobiMsg").html("请输入正确短信校验码！"),
					$("#J_MobiCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiCode").removeClass("error");
				}
				var pwdLen = $("#J_MobiPwd").val().length;
				if ($.trim($("#J_MobiPwd").val()) == "" || pwdLen < 6){
					return $(".mobiMsg").html("请输入6-20位密码！"),
					$("#J_MobiPwd").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiPwd").removeClass("error");
				}
            }),
            new $.search(".c-form-search");
        },
		mobiForm2: function() {
            $("#J_mobiForm2").on("submit",
            function(e) {
				if ($.trim($("#J_MobiCode").val()) == "" || $("#J_MobiCode").val().length != 6){
					return $(".mobiMsg").html("请输入正确短信校验码！"),
					$("#J_MobiCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiCode").removeClass("error");
				}
				
            }),
            new $.search(".c-form-search");
        },
		checkmobiForm: function() {
            $("#J_checkmobiForm").on("submit",
            function(e) {
				var re = /^1\d{10}$/;
				if ($.trim($("#J_MobiName").val()) == "" || !re.test($("#J_MobiName").val())){
					return $(".mobiMsg").html("请输入正确手机号码！"),
					$("#J_MobiName").addClass("error"),
                	!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiName").removeClass("error");
				}
				
				if ($.trim($("#J_verCode").val()) == "" || $("#J_verCode").val().length != 4){
					return $(".mobiMsg").html("请输入正确图形码！"),
					$("#J_verCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_verCode").removeClass("error");
				}
				
            }),
            new $.search(".c-form-search");
        },
        mobiForm3: function() {
            $("#J_mobiForm3").on("submit",
            function(e) {
				
				var pwdLen = $("#J_MobiPwd").val().length;
				if ($.trim($("#J_MobiPwd").val()) == "" || pwdLen < 6){
					return $(".mobiMsg").html("请输入6-20位密码！"),
					$("#J_MobiPwd").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiPwd").removeClass("error");
				}
							
            }),
            new $.search(".c-form-search");
        },
		mailForm: function() {
            $("#J_mailForm").on("submit",
            function(e) {
                var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/,
					mre = /^1\d{10}$/;
				if ($.trim($("#J_mailName").val()) == "" || !re.test($("#J_mailName").val()) && !mre.test($("#J_mailName").val())){
					return $(".login_errorMsg").html("请输入正确邮箱地址！"),
					$("#J_mailName").addClass("error"),
                	!1;
				}else{
					$(".login_errorMsg").html("");
					$("#J_mailName").removeClass("error");
				}
				var pwdLen = $("#J_verCode").val().length;
				if ($.trim($("#J_verCode").val()) == ""){
					return $(".mobiMsg").html("请输入验证码！"),
					$("#J_verCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_verCode").removeClass("error");
				}
				if (pwdLen < 6){
					return $(".mobiMsg").html("请输入正确验证码！"),
					$("#J_verCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_verCode").removeClass("error");
				}
				
            }),
            new $.search(".c-form-search");
        },
        forgetForm1: function() {
            $("#J_Forget1").on("submit",
            function(e) {
                var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/,
					mre = /^1\d{10}$/;
				if ($.trim($("#J_UserNameTxt").val()) == "" || !re.test($("#J_UserNameTxt").val()) && !mre.test($("#J_UserNameTxt").val())){
					return $(".login_errorMsg").html("请输入正确邮箱地址或手机号码！"),
					$("#J_UserNameTxt").addClass("error"),
                	!1;
				}else{
					$(".login_errorMsg").html("");
					$("#J_UserNameTxt").removeClass("error");
				}
				
				if ($.trim($("#J_verCode").val()) == ""){
					return $(".mobiMsg").html("请输入验证码！"),
					$("#J_verCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_verCode").removeClass("error");
				}
				
            }),
            new $.search(".c-form-search");
        },
        forgetForm2: function() {
            $("#J_Forget2").on("submit",
            function(e) {
				
				if ($.trim($("#J_MobiCode").val()) == "" || $("#J_MobiCode").val().length < 6){
					return $(".mobiMsg").html("请输入正确验证码！"),
					$("#J_MobiCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiCode").removeClass("error");
				}
				
				if ($.trim($("#J_MobiPwd").val()) == "" || $("#J_MobiPwd").val().length < 6){
					return $(".mobiMsg").html("请输入6-20位密码！"),
					$("#J_MobiPwd").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiPwd").removeClass("error");
				}
				
				if ($.trim($("#J_resetPwd").val()) == ""){
					return $(".mobiMsg").html("请输入确认密码！"),
					$("#J_resetPwd").addClass("error"),
					!1;
				}else if($.trim($("#J_MobiPwd").val()) != $.trim($("#J_resetPwd").val())){
					return $(".mobiMsg").html("两次密码输入不一致！"),
					$("#J_resetPwd").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_resetPwd").removeClass("error");
				}
				
            }),
            new $.search(".c-form-search");
        }
    });
}(jQuery);

$(function(){
	
	//tab切换事件
	var tabs = function(nav,content){
		$(nav).find("li").bind("click",function(){
			var index = $(this).index();
			$(this).addClass("cur").siblings().removeClass("cur");
			$(content).eq(index).show().siblings(content).hide();
		});
	}
	
	tabs(".tabs_menu", ".tabs_Cell");
	
	//监听input值，高亮按钮
	$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiCode").val()) == "" && !$.trim($("#J_MobiPwd").val()) == "" &&!$.trim($("#J_resetPwd").val()) == ""){
					$(".login_btn").addClass("click");
				}else{
					$(".login_btn").removeClass("click");
				}
			})

	//性别切换事件
	//手机
	$("#mobiGender").delegate("a","click",function(){
		$(this).addClass("cur").siblings("a").removeClass("cur");
		$("#sexVal").val($(this).attr("name"));
		return false;
	});
	
});

//登录表单验证
if($("#J_Login").length > 0){
	var Login = new Login();
	Login.loginForm();
	
	//设置nickValue
	if(window.localStorage && document.getElementById('J_UserNameTxt').value =='')
	{
	 document.getElementById('J_UserNameTxt').value = window.localStorage.getItem('lastNick');
	}
}

//注册表单验证
if($("#J_mobiForm1").length > 0){
	var Login = new Login();
	Login.mobiForm1();
}

if($("#J_mobiForm2").length > 0){
	var Login = new Login();
	Login.mobiForm2();
}

if($("#J_mobiForm3").length > 0){
	var Login = new Login();
	Login.mobiForm3();
}

//邮箱验证
if($("#J_mailForm").length > 0){
	var Login = new Login();
	Login.mailForm();
}

//手机验证
if($("#J_checkmobiForm").length > 0){
	var Login = new Login();
	Login.checkmobiForm();
}

//忘记密码表单验证
if($("#J_Forget1").length > 0){
	var Login = new Login();
	Login.forgetForm1();
}

//忘记支付密码表单验证
if($("#J_Forget2").length > 0){
	var Login = new Login();
	Login.forgetForm2();
}

//密码眼开关

$(".pwd_eyes").click(function(){
	if($(this).hasClass("close")){
		$(this).removeClass("close");
		$(this).siblings("input").attr("type","text");
	}else{
		$(this).addClass("close");
		$(this).siblings("input").attr("type","password");
	}
});

$(".agreement span").click(function(){
	if($(this).hasClass("checked")){
		$(this).removeClass("checked");
	}else{
		$(this).addClass("checked");
	}
});



$(function() {
	//验证码倒计时
	var isclick = true;
	$(".get_code").on("click",function(){
		
		var phone = $("#phone").val();
		var mre = /^1[358]\d{9}$/;
		if(phone==""){
			alert("请输入正确的手机号码！");
			return ;
		}
		if (phone != "" && !mre.test(phone)) {
			alert("请输入正确的手机号码！");
			return;
		}
		
		//防止重复点击按钮执行下面事件
		if($(this).attr("disabled")){
			return;
		}
		
		if(!isclick) return false;
		var that = $(this),timeId;
		var num = 90;
		var thiscon = $(this).attr("data-waiting");
		that.text(num+thiscon)
		var path = getRootPath();
		$.ajax({
			type:"get",
			url: path+"/giftCard/sendVerifyCode?timstamp="+ new Date().getTime() + "&phone=" + phone,
			dataType:"json",
			success:function(data){
				var code = data.code;
				if(code == "1"){
					isclick = false;
					alert(data.msg);
					return;
				}
			}
		});	
		
		isclick = false;
		timeId = setInterval(function(){
			num--;
			that.text(num+thiscon)
			if(num == 0){
				clearInterval(timeId);
				that.text("获取验证码");
				isclick = true;
			}
		},1000);
	});
});

