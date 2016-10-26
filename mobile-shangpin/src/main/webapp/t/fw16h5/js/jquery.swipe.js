(function($){
    $.fn.swipe = function(callback, cancelable){
        var positionX = 0, positionY = 0, direction = {left:"left",right:"right",up:"up",down:"down"};
        $(this).on("touchstart", function (e) {
            positionX = e.originalEvent.changedTouches[0].clientX;
            positionY = e.originalEvent.changedTouches[0].clientY;
            return cancelable;
        }).on("touchmove", function(e){
            return cancelable;
        }).on("touchend", function(e){
            var endX = e.originalEvent.changedTouches[0].clientX, endY = e.originalEvent.changedTouches[0].clientY;
            var returnVal = false;
            var spanX = Math.abs(endX - positionX), spanY = Math.abs(endY - positionY);
            //水平滑动
            if(spanX > 100 && spanX > spanY){
                returnVal = callback({
                    direction:endX < positionX? direction.left : direction.right,
                });
            }
            //垂直滑动
            if(spanY > 100 && spanY > spanX){
                returnVal = callback({
                    direction:endY < positionY? direction.down : direction.up
                });
            }

            return e.cancelable === false || returnVal;
        });
    };
})($);

