var Dirctor = function(){
    this.data = [];
    this.page = $("#page_1");
    this.scenes = this.page.find(".scene");
}

Dirctor.prototype.push = function (func, timeout) {
    this.data.push({func:func, timeout:timeout});
};

Dirctor.prototype.transfer = function (index){
    var _ = this;
    this.push(function(){
        if(index > 0){
            _.scenes.eq(index-1).hide();
            //_.page.find(".scene_" + _fix(index)).hide();
        }
        index++;
        _.scenes.eq(index-1).show();
        //_.page.find(".scene_"+ _fix(index)).show();
    }, 0)

}

Dirctor.prototype.play = function (callback) {
    this.data.reverse();
    var _ = this;
    var _func = function () {
        if(_.data.length === 0){
            $(".sound").show();
            _.stop();
            if(callback) callback();
            return;
        }
        var item  = _.data.pop();
        item["func"]();

        window.setTimeout(function(){
            _func();
        }, item["timeout"]);
    }
    _func();
};

Dirctor.prototype.stop = function () {
    this.data = [];
}
var dirctor = new Dirctor();

var page_1_out = function (e) {
    dirctor.stop();
}
var page_1_in = function (e) {
    $(".sound").hide();
    var films = [
        //region # scene_01 #
        function() {
            for (var i = 0; i < 25; i++) {
                var item = e.find(".scene_01 .b");
                dirctor.push(function () {
                    item.fadeToggle(70)
                }, 70);
            }
        },
        //endregion

        //region # scene_02 #
         function () {
            var E = e.find(".scene_02 .e");
             var a = e.find(".scene_02 .a"),b = e.find(".scene_02 .b"),c = e.find(".scene_02 .c");
            dirctor.push(function () {
                //var a = e.find(".scene_02 .a");
                var frame = new Frame(false, 40);
                //frame.onPlaying = function (e) {
                    //a.css("background-position", e +"px -563px")
                //}
                frame.play(-148,-178);
                var girl = e.find(".scene_02 .girl");
                girl.animate({left:0}, 1000);
                var d = e.find(".scene_02 .d");
                d.animate({left:-483}, 500, function(){
                    d.animate({"left":42}, 500);
                })
                var f = e.find(".scene_02 .f");
                f.animate({"left":750}, 500, function () {
                    f.animate({"left":282}, 500);
                });
            }, 500);
             dirctor.push(function () {

                 a.animate({top:277}, 200);
                 b.animate({top:847}, 200);
                 c.show().animate({top:1043}, 200);
             },300);
             dirctor.push(function () {
                 a.animate({"background-position-x":88},1000);
                 c.animate({"background-position-x":100},1000);
             },1500);

             dirctor.push(function () {
                 a.animate({"top":-650}, 200);
                 b.animate({"top":-650}, 200);
                 c.animate({"top":-1100}, 200);
             },200);


        },
        //endregion
        //region # scene_03xin #
        function() {
            var a = e.find(".scene_03 .a"),b = e.find(".scene_03 .b"),c = e.find(".scene_03 .c");
            dirctor.push(function () {
                //h.animate({"top":-450}, 200);
                //i.animate({"top":-450}, 200);
                //j.animate({"top":-450}, 200);
            },100);
        },
//endregion

        //endregion

        //region # scene_07 #
        function () {
            var E = e.find(".scene_07 .e"), f = e.find(".scene_07 .f"),
                g = e.find(".scene_07 .g"),j = e.find(".scene_07 .j"),
                i = e.find(".scene_07 .i"),h = e.find(".scene_07 .h"),
                d = e.find(".scene_07 .d"),
                a = e.find(".scene_07 .a"),b = e.find(".scene_07 .b"),c = e.find(".scene_07 .c");
            dirctor.push(function () {
              /*  a.animate({"top":-1300},100);
                b.animate({"top":-650}, 100);
                c.animate({"top":-1100}, 200);*/
            },200);
           /* dirctor.push(function () {
                h.animate({"top":-450}, 200);
                i.animate({"top":-450}, 200);
                j.animate({"top":-450}, 200);
            },200);*/
        },
        //endregion

        //endregion

        //region # scene_10 #
        function(){
            var a  = e.find(".scene_10 .a"),b  = e.find(".scene_10 .b"),boy  = e.find(".scene_10 .boy"),s = e.find(".scene_10"),c  = e.find(".scene_10 .c");
            dirctor.push(function () {
				boy.animate({"top":0}, 500);
            },1000);
            dirctor.push(function () {
                a.animate({"top":393}, 200);
                c.animate({"top":980}, 200);
            },200);

            dirctor.push(function () {
                a.animate({"left":60}, 1000);
                c.animate({"left":-90}, 1000);
            },500);

            dirctor.push(function () {
                a.animate({"left":-790}, 200);
                c.animate({"left":-790}, 200);

            },200);

			/*dirctor.push(function () {
				boy.animate({"top":'1000px'}, 500);
            },600);*/
        },
        //endregion

        //endregion


        //region # scene_11 #
        function(){
         var a  = e.find(".scene_11 .a"),b  = e.find(".scene_11 .b"),c = e.find(".scene_08 .c"), img = e.find(".scene_11 img");
         },
        //endregion

        //region # scene_12 #
        function(){
            var a = e.find(".scene_12 .a"),b = e.find(".scene_12 .b");
            dirctor.push(function () {
                a.animate({"top":37}, 200);
                b.animate({"top":302}, 200);
            },500);
            dirctor.push(function () {
                a.animate({"top":57}, 200);
                b.animate({"top":287}, 200);
            },800);
            dirctor.push(function () {
                a.animate({"top":291}, 100);
                b.animate({"top":73}, 100);
                var frame = new Frame(false, 40);
                frame.onPlaying = function (x) {
                    var rotate = x
                    a.css("transform","rotate("+rotate+"deg)");
                    b.css("transform","rotate("+rotate+"deg)");
                };
                frame.play(-5,-45, 10);
            },800);
            dirctor.push(function () {
                a.animate({"top":291-576-95,"left":-576}, 100);
                b.animate({"top":73+885-401,"left":885}, 100);
            },100);
        },
        //endregion

        //region # scene_13 #
        function(){
            var a = e.find(".scene_13 .a"),b = e.find(".scene_13 .b"),c = e.find(".scene_13 .c");
        /*    dirctor.push(function () {
                a.animate({"top":543}, 1000);
                b.animate({"top":27}, 1000);
            },200);

            dirctor.push(function () {
                a.animate({"top":503},300);
                b.animate({"top":67},300);
            },2000);
            dirctor.push(function () {
                a.animate({"top":-730}, 300);
                b.animate({"top":1300}, 300);
            },900);
			dirctor.push(function () {
				c.show();
                c.animate({"top":0}, 1000);                   
            },2000);
			dirctor.push(function () {
                c.animate({"top":543}, 1000);                
            },1000);
*/
        },
        //endregion

        //region # scene_14 #
        function(){
            var a = e.find(".scene_14 .a"),b = e.find(".scene_14 .b"),c = e.find(".scene_14 .c"),
                d = e.find(".scene_14 .d"), E = e.find(".scene_14 .e"), f = e.find(".scene_14 .f"),
                g = e.find(".scene_14 .g"),h = e.find(".scene_14 .h"),i = e.find(".scene_14 .i");

            var speed = 100;
            dirctor.push(function () {
            },500);

            dirctor.push(function () {
                a.animate({"left":-80}, 200);
                b.animate({"left":-80}, 200);
                c.animate({"left":251},200);
                f.animate({"left":0}, 200);
            },200);

            dirctor.push(function () {
                a.animate({"left":60}, 1000);
                b.animate({"left":-90}, 1000);
                c.animate({"left":151},1000);
                f.animate({"left":-10}, 1000);
            },1000);

            dirctor.push(function () {
                a.animate({"left":-790}, 200);
                b.animate({"left":-790}, 200);
                f.animate({"left":-850}, 200);

                c.animate({"left":71},200);
                d.animate({"left":298}, 200);
                E.animate({"left":0},200);
            },200);

        },
        //endregion

        //region # scene_15 #
        function () {
            var a = e.find(".scene_15 .a"),b = e.find(".scene_15 .b"),c = e.find(".scene_15 .c"),
                d = e.find(".scene_15 .d"), E = e.find(".scene_15 .e"), f = e.find(".scene_15 .f"),
                g = e.find(".scene_15 .g"),h = e.find(".scene_15 .h"),i = e.find(".scene_15 .i"),
                j = e.find(".scene_15 .j"), j2= e.find(".scene_15 .j2"),b2= e.find(".scene_15 .b2"),
                s = e.find(".scene_15");
            dirctor.push(function () {
                var frame = new Frame(false, 40);
                frame.onPlaying = function(x){
                    var scaleX = 1.0 * x / 100;
                    s.css("transform","scaleX("+scaleX+")")
                };
                frame.play(0, 100, 20)
            },1000);
            dirctor.push(function () {
                E.show();
                E.animate({"top":'600px'},300);
            },1000);
            dirctor.push(function () {
                E.animate({"left":'100px'},300);
            },1000);
            dirctor.push(function () {
                var s = e.find(".scene_15");
                var frame = new Frame(false, 40);
                frame.onPlaying = function(x){
                    var scaleX = 1.0 * x / 100;
                    s.css("transform","scaleX("+scaleX+")")
                };
                frame.onPlayed = function(){
                    d.hide();
                    E.hide();
                  /*  f.hide();*/
                    a.show();
                    b.show();
                    c.show();
                }
                frame.play(100, 0, 20)
            },1000);

            dirctor.push(function () {
                var s = e.find(".scene_15");
                var frame = new Frame(false, 40);
                frame.onPlaying = function(x){
                    var scaleX = 1.0 * x / 100;
                    s.css("transform","scaleX("+scaleX+")")
                };
                frame.onPlayed = function(){
                    g.show();
                    h.show();
                    i.show();
                    j.show();
                }
                frame.play(0, 100, 20)
            },100);

            dirctor.push(function () {
                g.animate({"top":23},500);
                j.animate({"top":902},500);
                i.animate({"top":603},500);
                h.animate({"top":292},500);
            },100);
            dirctor.push(function () {
                g.animate({"top":23},500);
                j.animate({"top":902},500);
                i.animate({"top":603},500);
                h.animate({"top":292},500);
            },100);


            dirctor.push(function () {
                b.animate({"left":200},500);
            },500);
            dirctor.push(function () {
                b.animate({"left":50},1500);
            },1500);
        },


        //endregion

        //region # scene_16 #
        function(){
            var a = e.find(".scene_16 .a"),z = e.find(".scene_16 .z"),
                s = e.find(".scene_16"),    b = e.find(".scene_16 .b")
            var speed = 1000;
            dirctor.push(function () {
                a.animate({"background-position-x":-22, "left":0}, 800);
            },1500);

            dirctor.push(function () {
                var frame = new Frame(false, 40);
                frame.onPlaying = function (x) {
                    var scale = 1.0 * x / 100;
                    if(x == 0){
                        scale = 0.01;
                        s.css("background-color","white");
                    }
                    s.css("transform","scaleY("+scale+")")
                };
                frame.play(100,0, 20)
            },200) ;

            dirctor.push(function () {
                s.animate({scale:100},{
                    duration:200,
                    step: function (now, fx) {
                        //console.log(now)
                        var scale = 1 - 1.0 * now / 100;
                        $(this).css("transform","scaleX("+scale+") scaleY(0.01)")
                    }
                })
            },10);



            
        },
        //endregion

        //region # scene_17 #
        function(){
            var divs = e.find(".scene_17").find("div");

            for(var _ = 1; _ < divs.length; _++){
                (function(item){
                    dirctor.push(function () {
                        divs.eq(item - 1).hide();
                        divs.eq(item).show();
                    }, 1000);
                })(_);
            }
            dirctor.push(function () {},1000);
        },
        //endregion
        function(){
            dirctor.push(function () {
                window.pager(1,2);
            },0);
        }];

    /*
     dirctor.push(function () {
     },500);
     */
    var from = 0; to = films.length;
    //from = 6, to = from+2;
    for (i = from; i < to; i++){
        dirctor.transfer(i);
        films[i]();
    }
    dirctor.play();
}

var page_1_out = function(){
    $(".sound").show();
}


$(function(){
    var $page = $("#page_1");
    var moving = function (position) {
        //$("#pannel").html(Math.round(position * 100) / 100);
        var deg = position * 20 / 30;
        $(".page_1").css({"transform":"perspective(120px) rotateY("+deg+"deg)"});
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

});

