var TOUCH_EVENT = "touchend";
//swipe
$(function () {
	var current_page = 0;
	$(".con_wrap").swipe(function (e) {
		//console.log(e.direction)
		if(e.direction === "up"){
			current_page = pager(current_page, current_page - 1);
			return true;
		}else if(e.direction === "down"){
			current_page = pager(current_page, current_page + 1);
			return true;
		}
		return false;
	}, false);

	window.pager = function (from, to, force) {
	    /*console.log(from,to);*/
		if(from===2&&to===3){
			from=2;
			to=6;
		}
		if(from===6&&to===5){
			from=6;
			to=2;
		}
	/*	if(from===4&&to===5){
			from=4;
			to=6;
		}*/
		/*if(from===6&&to===5){
			from=6;
			to=4;
		}*/
		/*if(from===6&&to===7){
			from=6;
			to=8;
		}
		if(from===8&&to===7){
			from=8;
			to=6;
		}*/

		if(from === 0 && to === 1 && !force){
			return from;
		}
		if(from === 8 && to === 9 && !force){
			return from;
		}

		if(from === 9 && to === 8 && !force){
			return from;
		}

		if(from === 2 && to === 1 && !force){
			to  = 0;
		}

		if(from === 1){
			dirctor.stop();
		}

		var page_length = $(".page").length;
		var isdown = from < to;
		if (from < 0 || from > page_length - 1) {
			return from;
		}
		if (isdown && from === page_length - 1) {
			to = 0;
		}
		if (isdown === false && from === 0) {
			return from;
		}
		var speed = 200, clientHeight = document.body.clientHeight;
		if (window["page_" + from + "_outing"]) {
			var _run = window["page_" + from + "_outing"]($("#page_" + from));
			//console.log(_run);
			if (!_run) {
				return from;
			}
		}
		$("#page_" + from + " .con_wrap").animate({"top": isdown ? -1 * clientHeight : clientHeight}, speed, function () {
			$("#page_" + from + " div").removeAttr("style"); //删除所有自定义的样式
			$("#page_" + from + " img").removeAttr("style"); //删除所有自定义的样式
			var cover = $("#page_" + from).hide().find(".cover")
			cover.css({"bottom": "-" + cover.css("height")}).removeAttr("dir");
		});

		if (window["page_" + from + "_out"]) {
			window["page_" + from + "_out"]($("#page_" + from));
		}
		$("#page_" + to + " .con_wrap").css({"top": isdown ? clientHeight : -1 * clientHeight}).animate({"top": 0}, speed);
		$("#page_" + to).show();
		if (window["page_" + to + "_in"]) {
			window["page_" + to + "_in"]($("#page_" + to));
		}
		current_page = to;

		return to;
	}
	var init = function (num) {
		current_page = num;
		$("#load").hide();
		$(".page").hide();
		if (window["page_" + num + "_in"]) {
			window["page_" + num + "_in"]($("#page_" + num));
		}
		$("#page_" + num).show();
	};
	init(0);

	//音乐
	var audio = $(".sound").show();
	var music = audio.find("#page1_music").get(0);
	audio.bind("touchend", function () {
	  var classname = $(this).attr("class");
	  if (classname.indexOf("off") === -1) {
	    music.pause();
	    $(this).removeClass("sound_on").addClass("sound_off");
	  } else {
	    music.play();
	    $(this).removeClass("sound_off").addClass("sound_on");
	  }
	});
});
