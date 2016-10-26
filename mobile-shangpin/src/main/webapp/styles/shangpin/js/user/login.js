(function(e) {
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
				$(".login_btn[type=submit]").removeClass("click");
				$(".bind_btn").removeClass("click");
				$(".login_btn").attr("type","button");
				if(vLen > 0){
					v[0].style.display = "none"
				}
                e.preventDefault();
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
	isNeedMsgCode = true;
	//手机验证码倒计时
	var isclick = true;
	$("#passwordGetCode").on("click",function(){
		if(!isclick) return false;
		//手机号码验证
		var re = /^1\d{10}$/;
		if ($.trim($("#J_MobiName").val()) == "" || !re.test($("#J_MobiName").val())){
			$(".mobiMsg").html("请输入正确的手机号码！")
			return;
		}
		$(".mobiMsg").html("");
		sendphonecode($("#J_MobiName").val());
		var that = $(this),timeId;
		var num = 60;
		var thiscon = $(this).attr("data-waiting");
		that.text(num+thiscon)
		isclick = false;
		timeId = setInterval(function(){
			num--;
			that.text(num+thiscon)
			if(num == 0){
				clearInterval(timeId);
				that.text("重新获取");
				isclick = true;
			}
		},1000);

	});
	$("#onlyGetCode").on("click",function(){
		if(!isclick) return false;
		var that = $(this),timeId;
		var num = 60;
		var thiscon = $(this).attr("data-waiting");
		that.text(num+thiscon)
		isclick = false;
		timeId = setInterval(function(){
			num--;
			that.text(num+thiscon)
			if(num == 0){
				clearInterval(timeId);
				that.text("重新获取");
				isclick = true;
			}
		},1000);

	});
	$("#emailGetCode").on("click",function(){
		if(!isclick) return false;
		//邮箱重置密码
		var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/
		if ($.trim($("#J_mailName").val()) == "" || !re.test($("#J_mailName").val())){
			$(".mobiMsg").html("请输入正确的邮箱地址！")
			return;
		}
		var path = getRootPath()+"/user/sendEmailCode";
		$.post(path,{mail:$("#J_mailName").val()},function(data){
			if(data.code!="0"){
				$(".mobiMsg").html(data.msg);
			}
		},"json");
		var that = $(this),timeId;
		var num = 60;
		var thiscon = $(this).attr("data-waiting");
		that.text(num+thiscon)
		isclick = false;
		timeId = setInterval(function(){
			num--;
			that.text(num+thiscon)
			if(num == 0){
				clearInterval(timeId);
				that.text("重新获取");
				isclick = true;
			}
		},1000);

	});

    window.Login = function() {},
    $.extend(window.Login.prototype, {
        init: function() {},
		//手机验证
		mobiForm1: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiName").val()) == "" && !$.trim($("#J_verCode").val()) == ""){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
				}else{
					$(".login_btn").removeClass("click");
				}
			})
            $("#J_mobiForm1").on("submit",
            function(e) {
				//验证手机
                var re = /^1\d{10}$/;
				if ($.trim($("#J_MobiName").val()) == "" ){
					return $(".mobiMsg").html("手机号不能为空！"),
					$("#J_MobiName").addClass("error"),
                	!1;
				}else if($.trim($("#J_MobiName").val().length)<11||$.trim($("#J_MobiName").val().length)>11){
					return $(".mobiMsg").html("请输入11位手机号码！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if(!re.test($("#J_MobiName").val())){
					return $(".mobiMsg").html("手机号码格式不正确！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiName").removeClass("error");
				}
				//图形码
				if ($.trim($("#J_verCode").val()) == ""){
					return $(".mobiMsg").html("请输入验证码！"),
					$("#J_verCode").addClass("error"),
					!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_verCode").removeClass("error");
				}
				if ($.trim($("#J_verCode").val()) == "" || $("#J_verCode").val().length != 4){
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
		//手机注册
		mobiForm2: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiName").val()) == "" && !$.trim($("#J_MobiPwd").val()) == "" && !$.trim($("#J_MobiCode").val()) == ""){
					$(".bind_btn").addClass("click");
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
					//弹层
					$(".bind_btn").click(function(){
						$(".pop_overlay").addClass("pop");
					})
					$(".close_pop").click(function(){
						$(".pop_overlay").removeClass("pop")
					});
				}else{
					$(".bind_btn").removeClass("click");
					$(".login_btn").removeClass("click");
					$(".login_btn").attr("type","button");
				}
			})
            $("#J_mobiForm2").on("submit",
            function(e) {
				$(".pop_overlay").removeClass("pop")
				//验证是否勾选了同意用户协议
				var agree = $(".agreement span:first").attr("class");
				if(agree=="checked"){
					return $(".mobiMsg").html("请先同意尚品用户协议！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}
				//验证手机
				var re = /^1\d{10}$/;
				if ($.trim($("#J_MobiName").val()) == "" ){
					return $(".mobiMsg").html("手机号不能为空！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if($.trim($("#J_MobiName").val().length)<11||$.trim($("#J_MobiName").val().length)>11){
					return $(".mobiMsg").html("请输入11位手机号码！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if(!re.test($("#J_MobiName").val())){
					return $(".mobiMsg").html("手机号码格式不正确！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiName").removeClass("error");
				}
				//验证码
				if ($.trim($("#J_MobiCode").val()) == ""){
					return $(".mobiMsg").html("短信校验码不能为空！"),
					$("#J_MobiCode").addClass("error"),
					!1;
				}else if($("#J_MobiCode").val().length != 6){
					return $(".mobiMsg").html("请输入6位短信校验码！"),
						$("#J_MobiCode").addClass("error"),
						!1;
				}
				else{
					$(".mobiMsg").html("");
					$("#J_MobiCode").removeClass("error");
				}
				//设置密码
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
		//邮箱手机合并
		mailCombineForm2: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiName").val()) == ""  && !$.trim($("#J_MobiCode").val()) == ""){
					$(".bind_btn").addClass("click");
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
					//弹层
					$(".bind_btn").click(function(){
						$(".pop_overlay").addClass("pop");
					})
					$(".close_pop").click(function(){
						$(".pop_overlay").removeClass("pop")
					});
				}else{
					$(".bind_btn").removeClass("click");
					$(".login_btn").removeClass("click");
					$(".login_btn").attr("type","button");
				}
			})
            $("#J_mail_combine_Form2").on("submit",
            function(e) {
				$(".pop_overlay").removeClass("pop")

				//验证手机
				var re = /^1\d{10}$/;
				if ($.trim($("#J_MobiName").val()) == "" ){
					return $(".mobiMsg").html("手机号不能为空！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if($.trim($("#J_MobiName").val().length)<11||$.trim($("#J_MobiName").val().length)>11){
					return $(".mobiMsg").html("请输入11位手机号码！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if(!re.test($("#J_MobiName").val())){
					return $(".mobiMsg").html("手机号码格式不正确！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiName").removeClass("error");
				}
				//验证码
				if ($.trim($("#J_MobiCode").val()) == ""){
					return $(".mobiMsg").html("短信校验码不能为空！"),
					$("#J_MobiCode").addClass("error"),
					!1;
				}else if($("#J_MobiCode").val().length != 6){
					return $(".mobiMsg").html("请输入6位短信校验码！"),
						$("#J_MobiCode").addClass("error"),
						!1;
				}
				else{
					$(".mobiMsg").html("");
					$("#J_MobiCode").removeClass("error");
				}
            }),
            new $.search(".c-form-search");
        },

		//设定密码
        mobiForm3: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiName").val()) == "" && !$.trim($("#J_MobiPwd").val()) == ""){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
				}else{
					$(".login_btn").removeClass("click");
				}
			})
            $("#J_mobiForm3").on("submit",
            function(e) {
				//验证手机
				var re = /^1\d{10}$/;
				if ($.trim($("#J_MobiName").val()) == "" ){
					return $(".mobiMsg").html("手机号不能为空！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if($.trim($("#J_MobiName").val().length)<11||$.trim($("#J_MobiName").val().length)>11){
					return $(".mobiMsg").html("请输入11位手机号码！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else if(!re.test($("#J_MobiName").val())){
					return $(".mobiMsg").html("手机号码格式不正确！"),
						$("#J_MobiName").addClass("error"),
						!1;
				}else{
					$(".mobiMsg").html("");
					$("#J_MobiName").removeClass("error");
				}

				//登录密码
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
		//验证邮箱
		mailForm: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_mailName").val()) == "" && !$.trim($("#J_verCode").val()) == ""){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
				}else{
					$(".login_btn").removeClass("click");
				}
			})
            $("#J_mailForm").on("submit",
            function(e) {
                var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/
				if ($.trim($("#J_mailName").val()) == ""){
					return $(".login_errorMsg").html("邮箱地址不能为空！"),
					$("#J_mailName").addClass("error"),
                	!1;
				}else if(!re.test($("#J_mailName").val())){
					return $(".login_errorMsg").html("邮箱地址格式不正确！"),
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
				if (pwdLen < 4){
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
		//邮箱登录
		mailLoginForm: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_mailName").val()) == "" && !$.trim($("#J_MobiPwd").val()) == ""){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
				}else{
					$(".login_btn").removeClass("click");
				}
			})
			$("#J_mailLoginForm").on("submit",
				function(e) {
					var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/
					if ($.trim($("#J_mailName").val()) == ""){
						return $(".login_errorMsg").html("邮箱地址不能为空！"),
							$("#J_mailName").addClass("error"),
							!1;
					}else if(!re.test($("#J_mailName").val())){
						return $(".login_errorMsg").html("邮箱地址格式不正确！"),
							$("#J_mailName").addClass("error"),
							!1;
					}else{
						$(".login_errorMsg").html("");
						$("#J_mailName").removeClass("error");
					}
					//登录密码
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
		//绑定手机
		bindForm: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiName").val()) == "" && !$.trim($("#J_MobiCode").val()) == ""){
					//高亮显示
					$(".bind_btn").addClass("click");
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
					//弹层
					$(".bind_btn").click(function(){
						$("#J_bindForm").submit();
					})
					/*$(".bind_btn").click(function(){
						$(".pop_overlay").addClass("pop");
					})
					$(".close_pop").click(function(){
						$(".pop_overlay").removeClass("pop");
					});*/
				}else{
					$(".bind_btn").removeClass("click");
					$(".login_btn").removeClass("click");
				}
			})
			$("#J_bindForm").on("submit",
				function(e) {
					$(".pop_overlay").removeClass("pop");
					//验证手机
					var re = /^1\d{10}$/;
					if ($.trim($("#J_MobiName").val()) == "" ){
						return $(".mobiMsg").html("手机号不能为空！"),
							$("#J_MobiName").addClass("error"),
							!1;
					}else if($.trim($("#J_MobiName").val().length)<11||$.trim($("#J_MobiName").val().length)>11){
						return $(".mobiMsg").html("请输入11位手机号码！"),
							$("#J_MobiName").addClass("error"),
							!1;
					}else if(!re.test($("#J_MobiName").val())){
						return $(".mobiMsg").html("手机号码格式不正确！"),
							$("#J_MobiName").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_MobiName").removeClass("error");
					}
					if(isNeedMsgCode){
						//验证码
						if ($.trim($("#J_MobiCode").val()) == ""){
							return $(".mobiMsg").html("短信校验码不能为空！"),
								$("#J_MobiCode").addClass("error"),
								!1;
						}else if($("#J_MobiCode").val().length != 6){
							return $(".mobiMsg").html("请输入6位短信校验码！"),
								$("#J_MobiCode").addClass("error"),
								!1;
						}
						else{
							$(".mobiMsg").html("");
							$("#J_MobiCode").removeClass("error");
						}
					}
				}),
				new $.search(".c-form-search");


		},
		//邮箱重置密码
		mailResetForm: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_mailName").val()) == "" && !$.trim($("#J_MobiPwd").val()) == "" && !$.trim($("#J_MobiCode").val()) == ""){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
				}else{
					$(".login_btn").removeClass("click");
				}
			})
			$("#J_mailResetForm").on("submit",
				function(e) {
					var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/
					if ($.trim($("#J_mailName").val()) == ""){
						return $(".login_errorMsg").html("邮箱地址不能为空！"),
							$("#J_mailName").addClass("error"),
							!1;
					}else if(!re.test($("#J_mailName").val())){
						return $(".login_errorMsg").html("邮箱地址格式不正确！"),
							$("#J_mailName").addClass("error"),
							!1;
					}else{
						$(".login_errorMsg").html("");
						$("#J_mailName").removeClass("error");
					}
					//验证码
					if ($.trim($("#J_MobiCode").val()) == ""){
						return $(".mobiMsg").html("邮箱校验码不能为空！"),
							$("#J_MobiCode").addClass("error"),
							!1;
					}else if($("#J_MobiCode").val().length != 6){
						return $(".mobiMsg").html("请输入6位邮箱校验码！"),
							$("#J_MobiCode").addClass("error"),
							!1;
					}
					else{
						$(".mobiMsg").html("");
						$("#J_MobiCode").removeClass("error");
					}
					//设置密码
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
		//短信验证
		checkmobiForm: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_verCode").val()) == ""){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");
				}else{
					$(".login_btn").removeClass("click");
				}
			})
			$("#J_checkmobiForm").on("submit",
				function(e) {
					//图形码
					if ($.trim($("#J_verCode").val()) == ""){
						return $(".mobiMsg").html("请输入验证码！"),
							$("#J_verCode").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_verCode").removeClass("error");
					}
					if ($.trim($("#J_verCode").val()) == "" || $("#J_verCode").val().length != 6){
						return $(".mobiMsg").html("请输入正确短信校验码！"),
							$("#J_verCode").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_verCode").removeClass("error");
					}

				}),
				new $.search(".c-form-search");
		},
		//密码登录
		loginForm: function() {
			//高亮显示
			$("input").bind('input propertychange',function(){
				if(!$.trim($("#J_MobiName").val()) == "" && !$.trim($("#J_MobiPwd").val()) == "" && !$.trim($("#J_verCode").val()) == ""  ){
					$(".login_btn").addClass("click");
					$(".login_btn").attr("type","submit");

				}else{
					$(".login_btn").removeClass("click");
				}
			})
			$(".login_btn").click(function(){

				$(".login_mail").addClass("reLogin");
			})

			$("#J_loginForm").on("submit",
				function(e) {
					//验证手机
					var re = /^1\d{10}$/;
					if ($.trim($("#J_MobiName").val()) == "" ){
						return $(".mobiMsg").html("手机号不能为空！"),
							$("#J_MobiName").addClass("error"),
							!1;
					}else if($.trim($("#J_MobiName").val().length)<11||$.trim($("#J_MobiName").val().length)>11){
						return $(".mobiMsg").html("请输入11位手机号码！"),
							$("#J_MobiName").addClass("error"),
							!1;
					}else if(!re.test($("#J_MobiName").val())){
						return $(".mobiMsg").html("手机号码格式不正确！"),
							$("#J_MobiName").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_MobiName").removeClass("error");
					}
					//设置密码
					var pwdLen = $("#J_MobiPwd").val().length;
					if ($.trim($("#J_MobiPwd").val()) == "" || pwdLen < 6){
						return $(".mobiMsg").html("请输入6-20位密码！"),
							$("#J_MobiPwd").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_MobiPwd").removeClass("error");
					}
					//图形码
					if ($.trim($("#J_verCode").val()) == ""){
						return $(".mobiMsg").html("请输入验证码！"),
							$("#J_verCode").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_verCode").removeClass("error");
					}
					if ($.trim($("#J_verCode").val()) == "" || $("#J_verCode").val().length != 6){
						return $(".mobiMsg").html("请输入正确短信校验码！"),
							$("#J_verCode").addClass("error"),
							!1;
					}else{
						$(".mobiMsg").html("");
						$("#J_verCode").removeClass("error");
					}
				}),
			new $.search(".c-form-search");
		}
        //forgetForm1: function() {
        //    $("#J_Forget1").on("submit",
        //    function(e) {
        //        var re = /^([\w]+[_|\_|\-|\.]?)*[\w]+@([\w]+[_|\_|\-|\.]?)*[\w]+\.[a-zA-Z]{2,3}$/,
			//		mre = /^1\d{10}$/;
			//	if ($.trim($("#J_UserNameTxt").val()) == "" || !re.test($("#J_UserNameTxt").val()) && !mre.test($("#J_UserNameTxt").val())){
			//		return $(".login_errorMsg").html("请输入正确邮箱地址或手机号码！"),
			//		$("#J_UserNameTxt").addClass("error"),
        //        	!1;
			//	}else{
			//		$(".login_errorMsg").html("");
			//		$("#J_UserNameTxt").removeClass("error");
			//	}
        //
			//	if ($.trim($("#J_verCode").val()) == ""){
			//		return $(".mobiMsg").html("请输入验证码！"),
			//		$("#J_verCode").addClass("error"),
			//		!1;
			//	}else{
			//		$(".mobiMsg").html("");
			//		$("#J_verCode").removeClass("error");
			//	}
        //
        //    }),
        //    new $.search(".c-form-search");
        //},
        //forgetForm2: function() {
        //    $("#J_Forget2").on("submit",
        //    function(e) {
        //
			//	if ($.trim($("#J_MobiCode").val()) == "" || $("#J_MobiCode").val().length != 6){
			//		return $(".mobiMsg").html("请输入正确验证码！"),
			//		$("#J_MobiCode").addClass("error"),
			//		!1;
			//	}else{
			//		$(".mobiMsg").html("");
			//		$("#J_MobiCode").removeClass("error");
			//	}
        //
			//	if ($.trim($("#J_MobiPwd").val()) == "" || $("#J_MobiPwd").val().length < 6){
			//		return $(".mobiMsg").html("请输入6-20位密码！"),
			//		$("#J_MobiPwd").addClass("error"),
			//		!1;
			//	}else{
			//		$(".mobiMsg").html("");
			//		$("#J_MobiPwd").removeClass("error");
			//	}
        //
			//	if ($.trim($("#J_resetPwd").val()) == ""){
			//		return $(".mobiMsg").html("请输入确认密码！"),
			//		$("#J_resetPwd").addClass("error"),
			//		!1;
			//	}else if($.trim($("#J_MobiPwd").val()) != $.trim($("#J_resetPwd").val())){
			//		return $(".mobiMsg").html("两次密码输入不一致！"),
			//		$("#J_resetPwd").addClass("error"),
			//		!1;
			//	}else{
			//		$(".mobiMsg").html("");
			//		$("#J_resetPwd").removeClass("error");
			//	}
        //
        //    }),
        //    new $.search(".c-form-search");
        //}
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


	//性别切换事件
	//手机
	$("#mobiGender").delegate("a","click",function(){
		$(this).addClass("cur").siblings("a").removeClass("cur");
		$("#sexVal").val($(this).attr("name"));
		return false;
	});

});

//登录表单验证
if($("#J_loginForm").length > 0){
	var Login = new Login();
	Login.loginForm();

	//设置nickValue
	if(window.localStorage && document.getElementById('J_MobiName').value =='')
	{
	 document.getElementById('J_MobiName').value = window.localStorage.getItem('lastNick');
	}
}
//手机验证
if($("#J_mobiForm1").length > 0){
	var Login = new Login();
	Login.mobiForm1();
}
//手机注册
if($("#J_mobiForm2").length > 0){
	var Login = new Login();
	Login.mobiForm2();
}
//邮箱绑定
if($("#J_mail_combine_Form2").length > 0){
	var Login = new Login();
	Login.mailCombineForm2();
}
//设定密码
if($("#J_mobiForm3").length > 0){
	var Login = new Login();
	Login.mobiForm3();
}
//邮箱验证
if($("#J_mailForm").length > 0){
	var Login = new Login();
	Login.mailForm();
}
//邮箱登录
if($("#J_mailLoginForm").length > 0){
	var Login = new Login();
	Login.mailLoginForm();
}
//绑定手机
if($("#J_bindForm").length > 0){
	var Login = new Login();
	Login.bindForm();
}
//邮箱重置密码
if($("#J_mailResetForm").length > 0){
	var Login = new Login();
	Login.mailResetForm();
}
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
		$(this).siblings("p").find("input").attr("type","text");
	}else{
		$(this).addClass("close");
		$(this).siblings("p").find("input").attr("type","password");
	}
});

//勾选同意协议
$(".agreement span").click(function(){
	if($(this).hasClass("checked")){
		$(this).removeClass("checked");
	}else{
		$(this).addClass("checked");
	}
});
$(".sp_info").click(function(){
	$(".pop_overlay").addClass("pop");
});
$(".close_pop").click(function(){
	$(".pop_overlay").removeClass("pop")
});

//ios7兼容
/*$("input").focus(function(){
	$(".topFix").css("position","absolute");
});
$("input").blur(function(){
	$(".topFix").css("position","fixed");
});*/

function thirdLogin(mode){
	var path = getRootPath();
	if(mode=="qq"){
		window.location.href = path +"/thirdLogin/qqlogin";
	}else if(mode=="wx"){
		window.location.href = path +"/thirdLogin/wxlogin";
	}else if(mode =='wb'){
		window.location.href = path +"/thirdLogin/wblogin";
	}
}

function sendphonecode(phone){
	if(phone == "" || phone == null){
		return;
	}
	var thrid_type = $("#third_bind_tel_page").val();
	var path;
	if(thrid_type) {
        //判断是否去账户合并
        path = window.location.pathname;
        if (path.indexOf("/login/normal") != -1) {
            path = getRootPath() + "/user/checkPhoneUser";
            $.ajax({
                type: "post",
                url: path,
                dataType: 'json',
                data: {"mobile": phone},
                async: false,
                success: function (data) {
                    if (thrid_type == "2" && data.isExist == "1") {//需要进行账户合并
                        $(".pop_overlay").addClass("pop");
                        $(".close_pop").click(function () {
                            $(".pop_overlay").removeClass("pop")
                        });
                        isNeedMsgCode = false;
                        return;
                    } else {
                        isNeedMsgCode = true;
                    }
                }
            });
        } else if (path.indexOf("/thirdLogin/") != -1 && (thrid_type == "2" ||thrid_type == "3" || thrid_type == "4" || thrid_type == "5")) {
            path = getRootPath() + "/user/checkPhoneUser";
            $.ajax({
                type: "post",
                url: path,
                dataType: 'json',
                data: {"mobile": phone},
                async: false,
                success: function (data) {
                    if (data.isExist == "1") {//老用户
                        $(".pop_overlay").addClass("pop");
                        $(".close_pop").click(function () {
                            $(".pop_overlay").removeClass("pop")
                        });
                        isNeedMsgCode = false;
                        return;
                    } else {
                        isNeedMsgCode = false;
                        $("#J_bindForm").submit();
                        return;
                    }
                }
            });
        }
    }
    if(isNeedMsgCode){
		var isRegiste = $("#isRegiste").val();
		if(isRegiste=="1"){//注册短信
			path = getRootPath()+"/repeatSendRegistPhoneCode";
		}else{//验证码短信
			path = getRootPath()+"/repeatSendFindpwdCode";
		}
		$.post(path,{mobi:phone},function(data){
			if(data.code!="0"){
				alert(data.msg);
				return;
			}
		},"json");
	}
}

function forgetPass(name) {
	if(name=="mail"){
		var path = getRootPath()+"/user/toFindPwd/email";
		var param="";
		var email = $("#J_mailName").val();
		if(email!=null && email != undefined) {
			param = "?mobi=" + email;
		}
		window.location.href=path+param;
	}else if(name=="phone"){
		var path = getRootPath()+"/user/toFindPwd/phone";

		var phone = $("#J_MobiName").val();
		if(phone!=null && phone != undefined) {
			param = "?mobi=" + phone;
		}
		window.location.href=path+param;
	}
}

function changeImage(){
	var path=getRootPath();
	var img = path+ "/captcha?t="+ new Date().getTime() ;
	$('span img').attr("src",img);
}




