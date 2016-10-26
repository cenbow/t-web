var page_0_in = function () {

}
$(function(){
    var $page = $("#page_0");
    $page.find("#point").on("touchend", function(e){
        window.pager(0,1,true);
    });
    
    var moving = function (position) {
        //$("#pannel").html(Math.round(position * 100) / 100);
        var deg = position * 20 / 30;
        $(".page_0").css({"transform":"perspective(120px) rotateY("+deg+"deg)"});
        (function(s){
            $("#point").css("left", s - position * s / 30);
        })(186);

        (function(s){
            $page.find(".title").css("left", s - position * s / 30);
        })(24);
    };

    window.addEventListener('deviceorientation', function(e) {
        if($page.css("display") !== "block"){
            return;
        }

        var gamma = e.gamma;

        if(gamma < -20 || gamma > 20){
            return false;
        }

        moving(gamma / 5);
    });

    var positionX = 0, position = 0;
    $("#pannel").on("touchstart", function (e) {
        positionX = e.originalEvent.changedTouches[0].clientX;
        //position = 0;
        return false;
    }).on("touchmove", function(e){
        var endX = e.originalEvent.changedTouches[0].clientX;
        var spanX = endX - positionX;
        
        (function (position) {
            //pannel
            var _continue = (function(){
                if(position < -180 || position > 180) return false;
                //console.log(position)
                var start = 186, left = start + position;
                if(left < 0) left = 0;
                if(left > 370) left = 370;
                $("#pannel").css("left", left);
                return true;
            })();

            position = position / 60;

            if(_continue === false) return;

            moving(position);
            
        })(position + spanX)

        return false;
    }).on("touchend", function (e) {
        return false;
    });

    $page.find(".skip").on("touchend", function () {
        window.pager(0,2)
    })
});