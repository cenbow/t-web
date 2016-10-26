$(function(){
    //选择礼品卡支付
    $('p.giftcard #isUse').click(function(){
        var userBalance = $("p.giftcard #isUse").attr("balance");
        var $giftCard = $("#order_detail p.total em i[type='4']")
        var $totalAmount = $("#order_detail p.total em i[type='7']");
        var amount = $totalAmount.text().trim().substr(1,$totalAmount.text().trim().length);
        if(!$(this).hasClass("cur")){
            if( $giftCard.length==0 && parseInt(userBalance)>0){
               if(amount>0){
                   $totalAmount.html('&ensp;&yen;'+(parseInt(amount)-parseInt(userBalance)));
                   $totalAmount.parent("em").before("<em>礼品卡&nbsp;: <i type='4'>-&yen;"+userBalance+"</i></em><br>")
               }
            }
            $(this).addClass("cur");
            $('.used').show();
            $(".credit").hide();
        }else{
            if($giftCard.length>0){
                $giftCard.parent("em").next("br").remove();
                $giftCard.parent("em").remove();
                $totalAmount.html('&ensp;&yen;'+(parseInt(amount)+parseInt(userBalance)));
            }
            $(this).removeClass("cur");
            $('.used').hide();
            $(".credit").show();
        }
    });

    //显示支付页面
    $("#payment a i").click(function(){
        $("#order_detail_payment").show().siblings().hide();
    })
    //返回订单详情
    $("#order_detail_payment .backBtn").click(function () {
        $("#order_detail").show().siblings().hide();
    })

   //切换支付方式
    $("#order_detail_payment .payment_box").delegate("li","click",function(){

        $(this).addClass("checked").siblings().removeClass("checked");
        var clazz = $(this).attr("clazz");
        var mainid =  $(this).attr("mainid");
        var subid = $(this).attr("subid");
        var way  = $(this).attr("way");
        var title = $(this).children("a").text();
        var $payele = $("#order_detail #payment a i");
        $payele.attr("class",clazz);
        $payele.attr("class",clazz);
        $payele.attr("mainid",mainid);
        $payele.attr("subid",subid);
        $payele.attr("way",way);
        $payele.children("em").text(title);
    })

    function getPayDom() {
        return $("#order_detail_payment ul li.checked")
    }

    //支付
    $("#order_detail div.payment_submit_all a").click(function(){

        var orderId = $("#orderId").val();
        var url;
        if($(this).attr("submitType") == "pay"){
            if($("p.giftcard #isUse").hasClass("cur")){
                url=getRootPath()+"/order/pay/payments?orderId="+orderId;
            }else{
                url = getRootPath()+$(getPayDom()).attr("url") + "?orderId=" + orderId;
            }
            window.location.href = url;
        }else{
            var postArea = $("#postArea").val();
            window.location.href = getRootPath()+"/logistice/list?orderId="+orderId+"&postArea="+postArea;
        }
    })

    //倒计时
    $(function(){

        if($("#order_detail div.payment_submit_all a[submitType='pay']").length>0) {

            var currentTimes = $("#currentTimes").val();
            var orderTime = $("#setTime").val();
            var place = $("#leftTime");
            var dt = new Date(orderTime.replace(/-/g, "/"));
            var endTime = dt.getTime() + (30 * 60 * 1000);
            var leftTime = endTime - currentTimes;
            if (dt.getTime() > currentTimes) {
                leftTime = 30 * 59 * 1000;
            }
            place.attr("leftTime", leftTime);
            window.setInterval(function () {
                ShowCountDown(place)
            }, 1000);

            function ShowCountDown(place) {
                var leftTime = place.attr("leftTime");
                var leftsecond = (leftTime / 1000);
                var day1 = Math.floor(leftsecond / (60 * 60 * 24));
                var hour = Math.floor((leftsecond - day1 * 24 * 60 * 60) / 3600);
                var minute = Math.floor((leftsecond - day1 * 24 * 60 * 60 - hour * 3600) / 60);
                var second = Math.floor(leftsecond - day1 * 24 * 60 * 60 - hour * 3600 - minute * 60);
                if (second < 10) {
                    second = '0' + second;
                }
                place.text(minute + ":" + second);
                var newTime = parseInt(leftTime) - (1 * 1000);
                place.attr("leftTime", newTime);
            }
        }
    });
})