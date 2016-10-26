$(function () {
    //移除默认提示
    $(".phonenum").click(function () {
        $(".phonenum").removeAttr("placeholder");
    });
    $(".messagecode").click(function () {
        $(".messagecode").removeAttr("placeholder");
    });

    var path = getRootPath();
    //判断格式
    $(".sure_form").click(function () {
        var phonenum = $(".phonenum").val();
        var messagecode = $(".messagecode").val();
        var inviteCode = $("#inviteCode").val();
        if (phonenum != "" && messagecode != "") {
            if (!(/^1\d{10}$/.test(phonenum))) {
                $('#check_pop').show();
                $("#check_pop").html("请输入正确的手机号！").addClass("cur")
            } else if (messagecode.length != 6) {
                $('#check_pop').show();
                $("#check_pop").html("验证码不正确！").addClass("cur")
            } else {
                $('#check_pop').hide();
                $.ajax({
                    type: "POST",
                    url: path + "/share/d/submit",
                    data: {phone:phonenum,inviteCode:inviteCode,verifyCode:messagecode},
                    dataType: "json",
                    async:false,//同步
                    success: function (data) {
                        if(data.code=="0"){
                            $(".public_bg_suc").show();
                        }else{
                            getErrorMsg(data);
                        }
                    },error: function (error) {
                        alert("数据错误,请重试!");
                    }
                });
            }
        }else {
            $('#check_pop').show();
            $("#check_pop").html("请输入正确手机号码和验证码！").addClass("cur")
        };
    });

    //手机验证码倒计时
    var isclick = true;
    $(".getcode").on("click", function () {
        if (!isclick) return false;
        //手机号码验证
        var re = /^1\d{10}$/;
        var phonenum = $(".phonenum").val();
        if ($.trim(phonenum) == "" || !re.test(phonenum)) {
            $('#check_pop').show();
            setTimeout('$("#check_pop").fadeOut()', 2000)
            $("#check_pop").html("请输入正确的手机号！").addClass("cur")
            return;
        }

        $.ajax({
            type: "POST",
            url: path + "/share/d/sendCode",
            data: {phone:phonenum},
            dataType: "json",
            async:false,//同步
            success: function (data) {
                if(data.code=="0"){
                    var that = $(".getcode"), timeId;
                    var num = 60;
                    var thiscon = $(".getcode").attr("data-waiting");
                    that.text(num + thiscon)
                    isclick = false;
                    timeId = setInterval(function () {
                        num--;
                        that.text(num + thiscon)
                        if (num == 0) {
                            clearInterval(timeId);
                            that.text("重新获取");
                            isclick = true;
                        }
                    }, 1000);
                }else {
                    getErrorMsg(data);
                }
            },
            error: function (error) {
                alert("数据错误,请重试!");
            }
        })
    });

    function getErrorMsg(data){

        if(data.code!="0"){
            if(data.code=="1"){
                alert(data.msg);
            }else if(data.code=="2"){
                $(".public_bg_fail_2").show();
            }else if(data.code=="3"){
                $(".public_bg_fail_3").show();
            }else if (data.code =="4"){
                $(".public_bg_fail_4").show();
            }
        }
        return;
    }

    $(".public_info_button").click(function () {
        $(".public_bg").hide();
    })
});