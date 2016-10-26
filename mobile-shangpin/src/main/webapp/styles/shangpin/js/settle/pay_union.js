$(function () {

    /*$(window).bind('beforeunload', function () {
        return "确定要离开订单提交页吗";
    });*/
    //***************************************************结算页面刷新begin********************************************/
    function freshSettle(whoReload) {

        var data = getFreshSettleData(whoReload);
        return postFreshSettle(data)

    }

    function addressRefresh(isCod) {

        var path = getRootPath();
        $.ajax({
            type: "POST",
            url: path + "/settlement/addressFreshV3",
            data: {isCod:isCod},
            dataType: "json",
            async:false,//同步 !重要,防止修改结算参数页面没有刷新完提交
            success: function (data) {
                if(data.payments){
                    pay_view_fresh(data.payments);
                }
            },
            error: function (error) {
                alert("数据错误,请重试!");
            }
        })
    }

    //**检测微信 且没有支付方式 **/
    function payOnly(){
        var wx = $("input[name=\"wx\"]").val();
        if(wx && $("#payment").length<=0){
            alert("您要支付的商品只支持使用支付宝付款，请使用浏览器打开页面或者下载尚品APP下单!");
            $(window).unbind('beforeunload');
            window.location.href = getRootPath() + "/cart/list";
        }
    }
    payOnly();

    function getFreshSettleData(whoReload) {


        var receivedId = $("input[name='addressId']").val();
        var provinceId = $("input[name='provinceId']").val();
        var received = {isLoad:"0",receivedId:receivedId,provinceId:provinceId};

        var couponNo = $("input[name='couponNo']").val();
        var canUserAmount = $("input[name='coupon_canUseAmount']").val() ? $("input[name='coupon_canUseAmount']").val() : "0";

        var isUseGiftCard = $("#giftCard span").hasClass("cur") ? "1":"0";
        var balance = $("input[name='giftCard_balance']").val() ? $("input[name='giftCard_balance']").val(): "0";
        var giftCanUserAmount= $("input[name='giftCard_canUseAmount']").val() ? $("input[name='giftCard_canUseAmount']").val() : "0";
        var couponCount = $("input[name='coupon_count']").val();
        var couponType = $("input[name='coupon_type']").val();
        var coupon = {isLoad:"0",couponNo:couponNo,canUseAmount:canUserAmount,count:couponCount,type:couponType}
        var giftCard = {isLoad:"0",isUsed:isUseGiftCard,balance:balance,canUseAmount:giftCanUserAmount}
        var buyIds =$("input[name='buyId']").val().substr(0,$("input[name='buyId']").val().length-1).split("|");

        switch (whoReload){
            case "coupon": coupon.isLoad="1";break;
            case "giftCard":giftCard.isLoad="1";break;
            default:console.info("reload error");
        }
        var fresh_param = {
            received: received,coupon: coupon,giftCard:giftCard,buyIds:buyIds
        };
        return fresh_param;
    }

    function postFreshSettle(data) {

        var path = getRootPath();
        var result={suc:true,msg:""};
        $.ajax({
            type: "POST",
            url: path + "/settlement/freshV3",
            data: {fresh:JSON.stringify(data)},
            dataType: "json",
            async:false,//同步 !重要,防止修改结算参数页面没有刷新完提交
            success: function (data) {

                if(data.code=="0"){
                    freshFormFill(data);
                    result.suc =  true;
                }else{
                    result.suc = false;
                    result.msg = data.msg;
                    alert(data.msg);
                }
            },
            error: function (error) {
                alert("数据错误,请重试!");
            }
        })
        return result;
    }

    function freshFormFill(data) {

        var settle = data.settle;
        var payments = data.payments;
        var giftCard = settle.giftCard;
        var priceShow = settle.priceShow;

        //是否包含国外商品刷新
        if(settle.isNeedCardId){
            $("input[name='isContainOutside']").val(settle.isNeedCardId);
        }
       // var invoice = settle.invoice;
       // var product = settle.product;
        var coupon = settle.coupon;
        if(coupon && coupon.isLoad=="1"){
            conpon_view_fresh(coupon);
        }

        if(giftCard && giftCard.isLoad=="1"){
            giftcard_view_fresh(giftCard);
        }
        if(priceShow.isLoad=="1"){
            priceShow_view_fresh(priceShow);//费用刷新
        }
        pay_view_fresh(payments);//支付方式刷新
        payOnly();
    }

    function conpon_view_fresh(coupon){
        var count = coupon.count;
        var title = coupon.title;
        var desc = coupon.desc;
        var html = "<a href='javascript:;' count="+coupon.count+">"+title+"<i><em>"+desc+"</em></i></a>";
        $("#coupon").html(html);
    }

    /**礼品卡刷新**/
    function giftcard_view_fresh(giftCard) {

        //选中
        if(giftCard.buttonStatus=="1"){
            $("#giftCard a span").addClass("cur");
        }
        $("#giftCard span").attr("valid",giftCard.valid);
        $("#giftCard a i").attr("canuseamount",giftCard.canUseAmount);
        $("#giftCard a i").attr("prompt",giftCard.prompt);
        $("input[name='giftCard_canUseAmount']").val(giftCard.canUseAmount);
        $("#giftCard a i em").text(giftCard.prompt);
    }

    /* 支付方式刷新**/
    function pay_view_fresh(payments) {

        var paytemp;//主页选中的支付方式
        //打开页刷新
        var allhtml = "";
        $.each(payments,function (index,pay) {

            var checkClass = "";
            if(pay.isSelected=="1"){
                paytemp = '<a href="javascript:;">支付方式 <i class="'+pay.clazz+'" url="'+pay.url+'" mainid="'+pay.id+'" subid="'+pay.subpayCode+'" way="'+pay.way+'"><em>'+pay.name+'</em> </i> </a>';
                checkClass="checked";
            }
            var html = ' <li class="'+checkClass+'" clazz="'+pay.clazz+'" url="'+pay.url+'" mainId="'+pay.id+'" subId="'+pay.subpayCode+'" way="'+pay.way+'">'
                    + '<em class="payment_icon '+pay.clazz+'"></em>'
                    + '<a href="javascript:;" >'+pay.name+'</a>'
                    + '</li>';
            allhtml+= html;
        });
        $("#payment").empty().html(paytemp);
        $("#settle_payment ul.payment_box ").empty().html(allhtml);
    }

    /* 金额刷新 **/
    function priceShow_view_fresh(priceShow) {

        var totalAmount = priceShow.onlineAmount;
        var feeHtml = "";
        $.each(priceShow.price,function(i,item){
            var amountShow = "";
            if(item.type=="2" ||item.type=="3" ||item.type=="4" || item.type=="6"){
                amountShow = "&thinsp;&minus;&yen;"+item.amount;
            }else{
                amountShow = "&nbsp;&nbsp;&nbsp;&yen;"+item.amount;
            }
            if(item.amount!='0' || item.type=="1" || item.type=="7"){
                feeHtml += item.title +":<i type='"+item.type+"'>&nbsp;&nbsp;"+amountShow+"</i></br>";
            }
        });
        $("#settle p.total").empty().html(feeHtml);
        //$("#settle p.attention").empty();
        $("#settle div.payment_submit p i").html("&yen;"+totalAmount);
    }

    $("#settle .backBtn").click(function (){
        if(window.confirm("确定要离开订单提交页吗?")){
            window.history.back(-1);
        }
    })

    //返回到主页面
    $("#settle_payment .backBtn").click(function () {
        backMain();
    })
    $("#settle_address_add .backBtn").click(function () {
        backMain();
    })
    $("#settle_address_list .backBtn").click(function () {
        backMain();
    })
    $("#settle_coupon .backBtn").click(function () {
        backMain();
    })
    $("#settle_invoice .backBtn").click(function () {
        backMain();
    })
    
    function backMain() {
        scroll(0, 0);
        $("#settle").show().siblings().hide();
    }

    function hasAboradProduct(){
        var has= $("input[name='isContainOutside']").val();
        if(has =="1"){
            return true;
        }else{
            return false;
        }
    }
    
    /******************************************地址相关********************************************/
    /** 地址选择回填**/
    $("#settle .paymet_block p:first").click(function () {
        $("#settle_address_list").show().siblings().hide();
        //绑定地址点击回调
        var pDom$ = $(this);
        $("#settle_address_list").delegate("#address_list_content p", "click", function () {
            //地址不可用默认灰色
            if($(this).hasClass("gray_disable")){
                alert("当前地址不可用！")
                return;
            }else{
                var addressId = $(this).attr("id");//地址id
                var receive_name = $(this).find("i").attr("name");//姓名
                var receive_phone = $(this).find("i").attr("tel");//手机号
                var receive_cardID = $(this).find("i").attr("cardid");//身份证号
                var isCod =  $(this).find("i").attr("isCod");
                cardIdInput(receive_cardID,pDom$);
                var receive_addr = $(this).find("em").text();//地址
                var forth_id = $(this).attr("town");
                var third_id = $(this).attr("area");
                var province = $(this).attr("province");
                $("a em", pDom$).text(receive_name);
                $("a i", pDom$).text(receive_phone);
                //是否有身份证的id
                if($("a span:eq(1)",pDom$).length>0){
                    $("a span:eq(1)", pDom$).text(receive_addr);
                }else{
                    $("a span:eq(0)", pDom$).text(receive_addr);
                }
                $("#forth_id").val(forth_id);
                $("#third_id").val(third_id);
                $("input[name='addressId']").val(addressId);
                $("input[name='provinceId']").val(province);
                //刷新结算页
                backMain();
                addressRefresh(isCod)
            }
        });
    });

    //身份证id 处理
    function cardIdInput(cardId,pDom$){

        if(!!cardId){
            if($("a span:eq(1)",pDom$).length>0){
                $("a span:eq(0)",pDom$).text(cardId);
            }else{
                $("a i",pDom$).after("<br><span>"+cardId+"</span>");
            }
        }else{
            if($("a span:eq(1)",pDom$).length>0){
                $("a span:eq(0)",pDom$).next("br").remove();
                $("a span:eq(0)",pDom$).remove();
            }
        }
    }

    //地址列表添加
    $("#settle_address_list").delegate("a.payment_btn","click",function () {
        //scroll(0, 0);
        $("#settle_address_add").show().siblings().hide();
        address_add_clear();
    })

    /**
     * 地址添加初始化页面数据
     */
    function address_add_clear(){
        $("#J_userName").val("");
        $("#J_mobileNum").val("");
        $("#J_addr").val("");
        $("#J_code").val("");
        $("#select_area").text("省市区");
        $("#address_add_cardID").val("");
        $("#province").val("");
        $("#provincename").val("");
        $("#city").val("");
        $("#cityname").val("");
        $("#area").val("");
        $("#areaname").val("");
        $("#town").val("");
        $("#townname").val("");
    }
    
    /** 添加地址提交**/
    $("#settle_address_add .payment_btn").click(function () {
        if(addAddressListValidate()){
            addAddressList();
        }
    })

    function addAddressListValidate(){

        var re = /^[\u4e00-\u9fa5]$/;
        var mre = /^1[34578]\d{9}$/;
        var post = /^[1-9][0-9]{5}$/;
        //请选择四级地址
        if($.trim($("#province").val()) == "" || $.trim($("#city").val()) == "" || $.trim($("#area").val()) == "" || $.trim($("#town").val()) == ""){
            return jShare('请选择省市地址!', "", ""), null, !1;
        }

        //收货人地址
        if ($.trim($("#J_addr").val()) == "") {
            return jShare('请输入详细地址!', "", ""), null, !1;
        } else {
            $("#J_addr").removeClass("error");
        }

        //收货人邮编
        if ($.trim($("#J_code").val()) == "" || !post.test($("#J_code").val())) {
            return jShare('请输入正确邮编!', "", ""),
                // $("#J_code").addClass("error"),
                null,
                !1;
        } else {
            $("#J_code").removeClass("error");
        }

        //收货人姓名
        if ($.trim($("#J_userName").val()) == "") {
            return jShare('请输入中文名称!', "", ""),
                null,
                !1;
        } else {
            $("#J_userName").removeClass("error");
        }

        //收货人电话
        if ($.trim($("#J_mobileNum").val()) == "" || !mre.test($("#J_mobileNum").val())) {
            return jShare('请输入11位手机号码!', "", ""),
                // $("#J_mobileNum").addClass("error"),
                null,
                !1;
        } else {
            $("#J_mobileNum").removeClass("error");
        }

        if($("#address_add_cardID").val()!=""){
            //身份证号码
            if (!checkCard($("#address_add_cardID").val())) {
                jShare('请输入正确的身份证号码！', "", ""),null,!1;
                return false;
            }
        }
        return true;
    }

    //结算添加地址提交页提交
    function addAddressList() {

        var path = getRootPath();
        //ajax 提交保存收货地址
        var address = $("#order_address_form").serialize();
        var buyId = $("input[name='buyId']").val();
        var orderSource = $("input[name='orderSource']").val();
        address +="&buyId="+buyId +"&orderSource="+orderSource;
        $.post(path + "/settlement/address/add", address, function (data) {
            if (data.code == 0) {
                //更改地址类表数据
                $(".add_block_list").remove("");
                $.each(data.addresses, function (index, item) {
                    var clazz = "addr_block add_block_list";
                    if(item.enabled=="0"){
                        clazz +=" gray_disable";
                    }
                    var $html = "<p class='"+clazz+"' id='" + item.id + "' province='"+item.province+"' area='" + item.area + "' town='" + item.town + "'>" +
                        "<span>" +
                        "<i isCod="+item.cod+" name="+item.name+" tel="+item.tel+" town="+item.town+" cardId="+item.cardID+" >" + item.name + "&nbsp;&nbsp;" + item.tel + "</i>" +
                        "<em>" + item.provName + item.cityName + item.areaName + item.townName + item.addr + "</em>" +
                        "</span></p>"
                    $("#append_address div.payment_submit").before($html);
                });
                var obj = data.addresses[0];
                var receive_name = obj.name;
                var receive_tel = obj.tel;
                var receive_addr = obj.provName + obj.cityName + obj.areaName + obj.townName + obj.addr;
                $("input[name='addressId']").val(obj.id);
                $("#forth_id").val(obj.town);//四级地址
                $("#third_id").val(obj.area);
                var adom$ = $("#order_content p:first a");
                adom$.children("em").text(receive_name);
                adom$.children("i").text(receive_tel);
                adom$.parent("p").attr("class","selected");
                var cardId = obj.cardID;
                cardIdInput(cardId,$("#order_content p:first"));
                //是否有身份证的id
                if($("span:eq(1)",adom$).length>0){
                    $("span:eq(1)", adom$).text(receive_addr);
                }else{
                    $("span:eq(0)", adom$).text(receive_addr);
                }
                $("#order_content").show().siblings().hide();
                $("#order_head").show();
                $("input[name='provinceId']").val(obj.province);
                address_add_clear();
                addressRefresh(obj.cod);
                backMain();
            } else {
                alert("收货地址添加失败！");
                return;
            }
        }, "json");
    }

    /*******************************发票相关begin******************************************************/
    $("#settle #invoice").next("p").children("a").click(function () {
        scroll(0, 0);
        $("#settle_invoice").show().siblings().hide();
        showPageInvoice();
    })

    //显示发票页面初始化发票页数据
    function showPageInvoice(){

        var invoiceflag = $("input[name='invoiceflag']").val();//是否开发票
        var invoicetype = $("input[name='invoicetype']").val();//发票类型
        var invoicetitle = $("input[name='invoicetitle']").val();//发票名称
        var invoicecontent =$("input[name='invoicecontent']").val();//发票内容 $("#J_invoiceContent option:selected").val()?$("#J_invoiceContent option:selected").val():"";
        var invoiceemail = $("input[name='invoiceemail']").val();//发票邮箱
        var invoicetel = $("input[name='invoicetel']").val();//发票手机号

        //名称
        $("#J_invoiceName").val(invoicetitle);
        //类型
        var person$ = $(".invoice_info p em a.personal");
        var company$ = $(".invoice_info p em a.company");
        if(invoicetype=="1"){
            if(!person$.hasClass("active_a")){
                company$.removeClass("active_a");
                person$.addClass("active_a");
                $(".org_name").hide();
            }
        }else{
            if(!company$.hasClass("active_a")){
                company$.addClass("active_a");
                person$.removeClass("active_a");
                $(".org_name").show();
            }
            if(invoicetitle==""){
                $("#J_invoiceName").val("单位");
            }else{
                $("#J_invoiceName").val(invoicetitle);
            }
        }
        //内容
        var select_value = $("#J_invoiceContent").children('option');
        $.each(select_value,function(index,item){
            $(this).attr("selected",false);
            if($(this).text()==invoicecontent){
                $("#J_invoiceContent").val($(this).val());
            }
        })
        $("#J_invoiceMail").val(invoiceemail);
        $("#J_invoicePhone").val(invoicetel);
    }


    $('#settle #invoice').delegate('#yes','click',function () {
        
        if($("input[name='invoiceflag']").val()=="1"){
            $("input[name='invoiceflag']").val("0")
            $('#settle #invoice').next("p.invoice").hide();
            $(this).removeClass("cur");
        }else{
            var flag = $("#settle #invoice").attr("valid");
            if (flag=="0"){
            	var invoice_tip=$("#settle #invoice em").text();
                alert(invoice_tip);
            }else{
                
                $("input[name='invoiceflag']").val("1");
                $('#settle #invoice').next("p.invoice").show();
                $(this).addClass("cur");
            }
        }
    });
    /**********************************end**********************************************************/
   /***********************************优惠券页面****************************************************/ 
    //点击显示优惠券页面
    $("#settle #coupon").delegate("a", "click",function () {
        scroll(0, 0);
        $("#settle_coupon").show().siblings().hide();
        showCouponPageData();
    })

    //优惠券点击选中
    $("#coupons_content ul").delegate("li", "click", function () {
        
        var type = $("#coupons_content").attr("type");
        //取消选中
        if($(this).children("b").hasClass("coupons_selected")){
            $(this).children("b").attr("class","coupons_select");
            $("input[name='couponNo']").val("0");
            $("input[name='coupon_canUseAmount']").val("0");
            $("#coupon a i em").text("您有"+$("#coupon a").attr("count")+"张优惠券可用");
            freshSettle("coupon");//刷新结算页
        }else{

             $(this).siblings("li").children("b[class=\"coupons_selected\"]").attr("class","coupons_select");
             var id = $(this).children("input[name=\"couponId\"]").val();
             var amount = $(this).children("div").children("i").attr("amount");
             var type = $("#coupons_content").attr("type");
             $("input[name='couponNo']").val(id);
             $("input[name='coupon_type']").val(type);
             var canUseAmount =  $(this).children("div").children("i").attr("canUseAmount");
             $("input[name='coupon_canUseAmount']").val(canUseAmount);

             $("#coupon a i em").val("已优惠"+canUseAmount+"元");
             backMain();
             freshSettle("coupon");//刷新结算页
        }
    })

    //显示优惠券页面 type 国内 国外标识
    function showCouponPageData() {

        var buyId =$("input[name='buyId']").val();
        var couponNo = $("input[name='couponNo']").val();
        var pageIndex = "0";
        var pageSize = "200";
        var tempData = {
            buyId: buyId,
            pageIndex: pageIndex,
            pageSize: pageSize,
            couponNo: couponNo,
        };
        $.ajax({
            url: getRootPath() + "/settlement/couponsV3",
            type: "post",
            dataType: "json",
            data: tempData,
            success: function (backdata) {
                $("#settle_coupon .alContent.order_coupons ul").empty()
                fillCouponPage(backdata);
            }, error: function (error) {
                console.info("coupon error");
            }
        });
    }

    //优惠券页面加载
    function fillCouponPage(data) {
        data = data.coupons;
        var amount = data.count;
        $("input[name='coupon_count']").val(amount);
        var list = data.list;

        var allhtml = "";
        $.each(list, function (i, item) {
            var type = ""
            if (item.type == "0") {
                type = "sale";
            } else {
                type = "cash"
            }
            var bHtml = "";
            if(item.isSelected=="1"){
                bHtml = '<b id="coupon_selected" class="coupons_selected"></b>';
            }else{
                bHtml = '<b id="coupon_selected" class="coupons_select"></b>';
            }
            if(type=="cash"){
                var htmlTem = '<li class="cash">' +
                    /*+'<h4><img src= ' + getRootPath() + '/styles/shangpin/images/order/cash_coupon_angle.png width="69" height="145" /></h4>'*/
                     '<input type="hidden" name="couponId" value="'+ item.couponNo +'"/>'
                    + '<div class="cash">'
                    + '<i canUseAmount='+item.canUseAmount+' amount="' + item.amount + '">&yen;' + item.amount + '</i>'
                    + '<em>' + item.name + '</em>'
                    + '<span><strong>' + item.expiryDate + '</strong></span>'
                    + '<span>' + item.rule + '</span>'
                    + '</div>'
                    + '<p><img src=' + getRootPath() + '/styles/shangpin/images/order/cash.png width="69" height="145" /></p>'
                    + bHtml
                    + '</li>';
                allhtml += htmlTem;
            }else{
                var htmlTem = '<li class="sale">' +
                   /* '<h4><img src= ' + getRootPath() + '/styles/shangpin/images/order/coupon_angle.png width="69" height="145" /></h4>'*/
                     '<input type="hidden" name="couponId" value="' + item.couponNo + '"/>'
                    + '<div class="sale">'
                    + '<i canUseAmount='+item.canUseAmount+' amount="' + item.amount + '">&yen;' + item.amount + '</i>'
                    + '<em>' + item.name + '</em>'
                    + '<span><strong>' + item.expiryDate + '</strong></span>'
                    + '<span>' + item.rule + '</span>'
                    + '</div>'
                    + '<p><img src=' + getRootPath() + '/styles/shangpin/images/order/coupon.png width="69" height="145" /></p>'
                    + bHtml
                    + '</li>';
                allhtml += htmlTem;
            }
        })
        $("#settle_coupon #coupons_content ul").empty().append(allhtml);
    }

    $("#coupons_code + a").click(function () {
        var code = $("#coupons_code").val();
        activeCoupon(code);
    });

    //发送优惠券激活码
    function activeCoupon(code) {

        if ($.trim($("#coupons_code").val()) == "") {
            return jShare('请输入优惠券激活码!', "", ""),
                $("#coupons_code").addClass("error"), !1;
        } else {
            $("#coupons_code").removeClass("error");
        }
        var path = getRootPath();
        $.post(path + "/coupon/order/active", {"code": "coupon:" + code}, function (data) {

            if (data.code == "0") {
                jShare("亲，优惠券充值成功", "", "");
                $("#coupons_code").val("");
                var type = $("#coupons_content").attr("type");
                if (type == "inner") {
                    showCouponPageData("inner");
                } else {
                    showCouponPageData("outer");
                }
            } else {
                jShare(data.msg, "", "");
                $("#coupons_code").val("");
            }
        }, "json");
    }
    /*****************************优惠券end*****************************************/

    /*****************************礼品卡*********************************************/
    $("#settle #giftCard span").click(function () {
        
        if (!$(this).hasClass("cur")) {
            if($(this).attr("valid")=="0"){
                alert($("#giftCard a i").attr("prompt"));
                return false;
            }
            $(this).addClass("cur");
            freshSettle("giftCard");
        } else {
            $(this).removeClass("cur");
            var test = $(this).siblings("a").children("i").attr("prompt");
            $(this).siblings("a").children("i").children("em").html(test);
            freshSettle("giftCard");
        }
    })
    /*****************************礼品卡end******************************************/
    
    //订单提交
    $("#order_content .payment_btn").click(function () {
        submitOrder();
    });

    function submitOrder(){
        if(area_not_complate()){
            var param = orderSubmitParam();
            console.dir(param);
            var validate = varlidateSubmitParam(param);
            if (validate) {
                //有国外的商品
                if(hasAboradProduct()){
                    //身份证校验
                    if(!valiCardId()){
                        return false
                    }
                }
                var url = getRootPath() + "/settlement/submitV3";
                $("#order_content .payment_btn").text("正在提交订单.......");
                $("#order_content .payment_btn").unbind("click");//防止重复提交
                $.post(url, {param:JSON.stringify(param)}, function (data) {
                    submitResult(data);
                }, "json");

            }
        }
    }

    /**
     * 提交表单校验
     * @returns {{flag: boolean, msg: string}}
     */
    function varlidateSubmitParam(param) {

        if (!param.consigneeId) {
            alert("请选择用户地址");
            return false;
        }
        // 开发票验证
        if (param.invoiceFlag == "1") {
            if (!param.invoiceInfo.invoiceTitle ) {
                alert("请输入发票抬头！");
                return false;
            }
            if (!param.invoiceInfo.invoiceContent ) {
                alert("请输入发票内容！");
                return false;
            }
            if(!param.invoiceInfo.invoiceType){
                alert("请输入发票类型!")
                return false;
            }
            if(!param.invoiceInfo.invoiceTel){
                alert("请输入发票手机号码！");
                return false;
            }
        }
        return true;
    }

    function getPayDom(){
        return $("#settle_payment ul li.checked");
    }


    /**
     * 提交订单参数获取
     * @returns {{consigneeId: (*|jQuery), ticketNo: (*|jQuery), orderFrom: number, orderType: number, cartDetailIds: (*|jQuery), payTypeId: *, payTypeChildId: *, isUseGiftcardPay: number, invoiceFlag: Number, invoiceInfo: {invoiceType: (*|jQuery), invoiceTitle: (*|jQuery), invoiceContent: (*|jQuery), invoiceEmail: (*|jQuery), invoiceTel: (*|jQuery)}}}
     */
    function orderSubmitParam() {

        var receivedId = $("input[name='addressId']").val();
        var couponNo = $("input[name='couponNo']").val();
        if(couponNo == "0"){
            couponNo = "";
        }
        var paytypeid = getPayDom().attr("mainId");	//主支付方式编号	String	必须
        var paytypechildid = getPayDom().attr("subId");//子支付方式编号	String	必须

        var hasGift = $("#giftCard span").hasClass("cur");
        var isUseGiftCardPay = hasGift ? 1 : 0;//	是否使用礼品卡 String	必须	0：为不使用；1：使用

        var invoiceFlag = parseInt($("input[name='invoiceflag']").val());//是否开发票
        var invoiceType = $("input[name='invoicetype']").val();//发票类型
        var invoiceTitle = $("input[name='invoicetitle']").val();//发票名称
        var invoiceContent =$("input[name='invoicecontent']").val();//发票名称 $("#J_invoiceContent option:selected").val()?$("#J_invoiceContent option:selected").val():"";
        var invoiceEmail = $("input[name='invoiceemail']").val();//发票邮箱
        var invoiceTel = $("input[name='invoicetel']").val();//发票手机号
        var invoiceInfo = {
            invoiceType:invoiceType,
            invoiceTitle:invoiceTitle,
            invoiceContent:invoiceContent,
            invoiceEmail:invoiceEmail,
            invoiceTel:invoiceTel
        }

        return {
            consigneeId: receivedId, ticketNo: couponNo, payTypeId:paytypeid,
            payTypeChildId:paytypechildid, isUseGiftcardPay:isUseGiftCardPay,
            invoiceFlag:invoiceFlag,invoiceInfo:invoiceInfo,
        };
    }

    /**
     * 身份证校验
     */
    function valiCardId(){

        // 如果收货地址中的身份证号码不存在则弹层提示
        var addressId = $("input[name='addressId']").val();
        var result;
        $.ajax({
            type : "get",
            url : getRootPath() + "/address/ajax/hasCardID?"+new Date(),
            data : {
                "addressId" : addressId
            },
            async : false,
            dataType : "json",
            success : function(data) {
                if (!data) {
                    // 收货地址不存在身份证号
                    $('#card_forget_input').show();
                    $('#overlay').show();
                    $('#popup_box_id').show();
                    result = false;
                } else {
                    result = true;
                }
            }
        });
        return result;
    }

    // 身份证保存
    $("#popup_ok_id").click(function () {
        saveCardID();
    })

    // 身份证关闭按钮
    $("#popup_cancel_id,.title_closeBtn").click(function(e) {
        e.preventDefault();
        $('#overlay').hide();
        $('#popup_box_id').hide();
    });

    // 保存身份证号码
    function saveCardID() {
        var cardID = $("#J_identificationNum").val().toUpperCase();
        var addressId = $("input[name='addressId']").val();
        // 验证身份证号码
        if (cardID == "") {
            alert("请输入身份证号码！");
            return false;
        }
        if (!checkCard(cardID)) {
            alert("您输入的身份证号码无效！");
            return false;
        } else {
            // ajax请求修改省份证号码
            $.ajax({
                url : getRootPath() + '/address/ajax/addCardID',
                data : {
                    "addressId" : addressId,
                    "cardID" : cardID
                },
                type : "get",
                dataType : 'json',
                success : function(data) {
                    if (data != null) {
                        var code = data.code;
                        var msg = data.msg;
                        if (code == "1") {
                            alert(msg);
                        } else {
                            // 关闭弹层
                            $('#overlay').hide();
                            $('#popup_box_id').hide();
                            alert("添加身份证信息成功!");
                        }
                    }
                }
            });
        }
    }

    //调用结算返回逻辑处理
    function submitResult(data) {
        var code = data.code;
        if (data.code == "0") {
            
            var orderId = data.content.mainOrderNo;
            var url = $(getPayDom()).attr("url") + "?orderId=" + orderId;
            var title = $(getPayDom()).children("a").children("em").text();
            var way = $(getPayDom()).attr("way");//0线上 1线下
            //有礼品卡支付
            if(data.content.isUseGiftcardPay=="1"){
                url = "/order/pay/payments?orderId=" + orderId;
                title = "礼品卡"
            }
            //优惠券,折扣码全额支付
            $("#settle #order_content .payment_btn").val("正在跳转到" + title + "支付页面......");
            window.location.href = getRootPath() + url;

        }else if(data.code == "2001"){//换仓
            alert("产品过于火爆，订单内商品的发货仓库发生了变化，我们为您选择了新的仓库，可能涉及到商品运费变化，请您核对后再提交订单！");
            location.reload(true);//强制刷新页面
            return;
        }else if (data.code == "3005") {//库存不足
            alert("您的订单中有库存不足的商品");
            $("#order_content .payment_btn").text("提交订单");
            $("#order_content .payment_btn").bind("click",submitOrder);
            return;
        } else if (data.code == "3003") {//地址不全
            area_not_complate();
            $("#order_content .payment_btn").bind("click",submitOrder);
            return;
        } else {
            if (data.msg == "库存不足") {
                alert("您的订单中有库存不足的商品");
            } else {
                alert(data.msg);
            }
            $("#order_content .payment_btn").bind("click",submitOrder);
            $("#order_content .payment_btn").text("提交订单");
            return;
        }
    }

    /*******************************支付_begin*******************************************/
    $("#settle_payment .payment_box").delegate("li","click",function(){

        $(this).addClass("checked").siblings().removeClass("checked");
        var clazz = $(this).attr("clazz");
        var mainid =  $(this).attr("mainid");
        var subid = $(this).attr("subid");
        var way  = $(this).attr("way");
        var url = $(this).attr("url");
        var $payele = $("#settle #payment a i");
        $payele.attr("class",clazz);
        $payele.attr("url",url);
        $payele.attr("mainid",mainid);
        $payele.attr("subid",subid);
        $payele.attr("way",way);
        $payele.children("em").text($(this).children("a").text());
    })

    //支付方式显示
    $("#settle").delegate("#payment" ,"click",function(){
        $("#settle_payment").show().siblings().hide();
    })
    /*******************************支付_end**********************************************/

    //商品信息显示
    $("#settle").delegate("#product" ,"click",function(){
        window.location.href="./products";
    })

    //地址提交订单返回数据地址不全
    function area_not_complate(){
        var forthId = $("#forth_id").val();
        if(forthId == '' || forthId == '0') {
            $("#select-area-overlay, #select_street_layer").show();
            var town = $("#third_id").val();
            $.post(getRootPath() + "/address/town", {areaId: town}, function (data) {
                $("#orgin_area_street").empty();
                $.each(data, function (index, item) {
                    $("#orgin_area_street").append("<dd><a href='javascript:;' id=" + item.id + ">" + item.name + "</a></dd>");
                });
            }, "json");
            return false;
        }else{
            return true;
        }
    }

    $("#select-area-overlay, .close_btn").click(function () {
        $("#select-area-overlay, .select-layer").hide();
    });

    //选择四级地址
    $("#select_street_layer").delegate("a", "click", function () {
        $(this).addClass("cur");
        var obj = $("#select_street_layer dd  a");
        obj.removeClass("cur");
        var townId = $(this).attr("id");
        var addrId = $("input[name='addressId']").val();
        $("#forth_id").val(townId);
        setTimeout(function () {
            $("#select-area-overlay, .select-layer").hide();
            //更新收货地址
            var path = getRootPath();
            $.get(path + "/address/ajax/update?addressId=" + addrId, function (data) {
                console.log("update address:" + data);
                $.post(path + "/address/ajax/edit", {
                    id: data.id,
                    province: data.province,
                    city: data.city,
                    area: data.area,
                    town: townId,
                    name: data.name,
                    addr: data.addr,
                    postcode: data.postcode,
                    tel: data.tel
                }, function (data) {
                    if (data.code == "0") {
                        $.each(data.content, function (index, item) {
                            if (addrId == item.id) {
                                var address = item.provname + item.cityname + item.areaname + item.townname + item.addr;
                                if($(".first_leg").next("p").children("a").children("span").length>1){
                                    $(".first_leg").next("p").children("a").children("span").eq(1).text(address);
                                }else{
                                    $(".first_leg").next("p").children("a").children("span").eq(0).text(address);
                                }
                                return;
                            }
                        });
                    } else {
                        alert("四级地址更新失败");
                        return;
                    }
                }, "json");
            }, "json");
        }, 300);
        return false;
    });
});
