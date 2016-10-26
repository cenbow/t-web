$(function(){
    var $page = $("#page_9");
    $page.find(".point01").on(TOUCH_EVENT, function(){
        window.pager(9, 0);
    });
    $page.find(".point02").on(TOUCH_EVENT, function(){
        $page.find(".shareLayer").show();
    })
    $page.find(".shareLayer").on(TOUCH_EVENT, function(){
        $(this).hide();
    });
});