$(function(){
    var $page = $("#page_8"), url = "post.php",
        $texts = $page.find("input:text"), $labels = $page.find("label"), $radios = $page.find("input:radio");
    $texts.on(TOUCH_EVENT, function () {
        $(this).focus();
        return false;
    }).on("touchend", function () {
        return false;
    });

    $(".sex label").on("touchend",function(){
        $(".sex label").removeClass("active");
        $(this).addClass("active");
    });

    $labels.on(TOUCH_EVENT, function(){
        $(this).find("input").prop("checked", "checked");
    })

    $page.find(":submit").on(TOUCH_EVENT, function () {
        //console.log("submit click")
        var isPass = true;
        $texts.each(function(i,v){
            if($(v).val() === ""){
                alert($(v).attr("placeholder") + "不能为空");
                isPass = false;
                return false;
            };
        });

        if(! isPass) return true;

        var sex_checked = $labels.find(":checked");
        if(sex_checked.length === 0){
            alert("请选择性别");
            isPass = false;
        }

        if(! isPass) return true;

        $("form").ajaxSubmit(function(){
            window.pager(8,9, true);
        });
        return true;
        //console.log("submit")
    });

    window.addEventListener('deviceorientation', function(e) {
        if($page.css("display") !== "block"){
            return;
        }

        var gamma = e.gamma, beta = e.beta, alpha = e.alpha;

        if(gamma < -20 || gamma > 30){
            return false;
        }

        var _left = gamma * 2;

        //$page.find(".name").val(_left)


        $page.find(".bg").css("background-position-x", _left)
    });

})