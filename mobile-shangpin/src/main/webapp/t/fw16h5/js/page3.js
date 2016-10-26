var page_3_hook, page_3_in = function (e) {
    e.find(".point").fadeIn("slow", function () {
        var deg = 0, rotate = function () {
            deg += 360 * 3;
            e.find(".icon").animate({rotate:deg}, {
                duration:500,
                step: function (now, fx) {
                    //console.log(now % 360)
                    $(this).css("transform","rotate("+(now % 360)+"deg)");
                },
            });

            page_3_hook = window.setTimeout(rotate, 3000);
        };
        e.find(".icon").show();
        page_3_hook = window.setTimeout(rotate, 2000);
    });
},page_3_out = function () {
    window.clearTimeout(page_3_hook);
}
$(function(){
    var $page = $("#page_3"), page_hook,
        rB_rotating = $page.find(".rB_rotating"),rotating = $page.find(".rotating"), deg = -20,
        points = $page.find(".point01, .point02, .point03"), frame = new Frame(true, 300),
        bag = $page.find(".bag");

    frame.onPlaying = function(i){
        bag.attr("src","/image/page2/bag_"+i+".png");
        frame.speed -= 1;
        if(frame.speed < 40){
            frame.speed = 40;
        }
    }
    frame.onPlayed = function(i){
        bag.attr("src","/image/page2/bag_1.png");
    }

    $page.find(".rotatBox").on("touchstart", function(){
        page_hook = window.setInterval(function(){
            if(rB_rotating.css("display") !== "none"){
                rB_rotating.hide();
                points.hide();
                rotating.show();
                frame.play(1,16);
                return;
            }
            deg -= 2;
            if(deg === -360){
                window.clearInterval(page_hook);
            }
            rotating.css("transform","rotate("+ deg +"deg)");
        },40);
    }).on("touchend", function(){
        window.clearInterval(page_hook);
        rB_rotating.show();
        points.show();
        rotating.hide();
        frame.stop();
        frame.speed = 300;
    });

    points.on(TOUCH_EVENT, function(){
        var index = $(this).prevAll().length - 1;
        $page.find(".bag0" + index).show();
    });

    $page.find(".close").on(TOUCH_EVENT, function(){
        $(this).parent().hide();
    })
});