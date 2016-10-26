$(function(){
    var $page = $("#page_5"), positionX = 0, positionY = 0, current_index = 0,i
    imgs= $page.find(".img"), boxes = $page.find(".box");
    $page.find(".box").swipe(function (e) {
        if(e.direction === "left"){
            return slider_pager(current_index, current_index+1);
        }
        if(e.direction === "right"){
            return slider_pager(current_index, current_index-1);
        }
        return true;
    }, false);

    var slider_pager = function(from, to){
        if(from < to){
            if(to === imgs.length){
                to = 0;
            }
            imgs.eq(from).animate({left:-800}, 200);
            boxes.eq(from).animate({left:-800}, 200);
            imgs.eq(to).css({left:750}).animate({left:0}, 200);
            boxes.eq(to).css({left:750}).animate({left:0}, 200);
        }else{
            if(to < 0){
                to = imgs.length - 1;
            }
            imgs.eq(from).animate({left:750}, 200);
            boxes.eq(from).animate({left:750}, 200);
            imgs.eq(to).css({left:-800}).animate({left:0}, 200);
            boxes.eq(to).css({left:-800}).animate({left:0}, 200);
        }
        current_index = to;
        return false;
    }
});