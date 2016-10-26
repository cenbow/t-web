/****************************************************************************************/
$(function(){
    //运费弹层
    $("#settle_product .freight_info").click(function(){
        var text = $(this).attr("desc");
        $("#settle_product .freight_bg p").text(text);
        $("#settle_product .freight_bg").show();
        return false;
    });
    $("#settle_product .freight_box a").click(function(){
        $("#settle_product .freight_bg").hide();
        return false;
    });
})
