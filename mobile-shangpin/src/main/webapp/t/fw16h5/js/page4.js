var page_4_index = 0;
var page_4_in = function (e) {
    page_4_index = 0;
    e.find(".item span").removeClass("active").eq(0).addClass("active");
};
$(function(){
    var $page = $("#page_4"), $imgs = $page.find(".img img"), $item= $page.find(".item span");
    $page.find(".img").swipe(function (e) {
        if(e.direction === "left"){
            return page_4_slider_pager(page_4_index, page_4_index+1);
        }
        

        return true;
    }, false);
    $page.find(".next").on("touchend", function (e) {
        return page_4_slider_pager(page_4_index, page_4_index+1);
    });
    $page.find(".pre").on("touchend", function (e) {
        return page_4_slider_pager(page_4_index, page_4_index-1);
    });
    window.page_4_slider_pager = function (from, to) {
        if(from > to) {
            if(to < 0){
                to = $imgs.length - 1;
            }
            $imgs.eq(from).animate({left: 600}, 200);

            $imgs.eq(to).css("left", -600).animate({left: 10}, 200);
        }else{
            if(to === $imgs.length){
                to = 0;
            }
            $imgs.eq(from).animate({left: -600}, 200);
            $imgs.eq(to).css("left", 600).animate({left: 10}, 200);
        }

        $item.eq(from).removeClass("active");
        $item.eq(to).addClass("active");
        page_4_index = to;
        return false;
    }
});

