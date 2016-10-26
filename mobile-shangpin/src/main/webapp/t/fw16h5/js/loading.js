var TOUCH_EVENT = "touchstart";
$(function () {
	function mainsize() {
		var maxWidth = document.body.clientWidth;
		var maxHeight = document.body.clientHeight;
		window.scale = 1;
		if (maxWidth / maxHeight > 750 / 1204) {
			window.scale = maxHeight / 1204
		} else {
			window.scale = maxWidth / 750;
		}
		$("#main").css("left", (maxWidth - 750) / 2 + "px");
		$("#main").css("top", (maxHeight - 1204) / 2 + "px");
		$("#main").css("-webkit-transform", "scale(" + window.scale + ")")
		$("#main").css("transform", "scale(" + window.scale + ")")
	}
	mainsize();
});
//loading
$(function () {
	var count = 0;
	var images = ["arrow.png","bg1.png","load/ele.png","page0/bg.png","page0/ele.png","page0/skip.png","page1/10_b.png","page1/11_a.png","page1/11_b.png","page1/11_c.png","page1/11_d_1.png","page1/11_d_10.png","page1/11_d_11.png","page1/11_d_12.png","page1/11_d_13.png","page1/11_d_14.png","page1/11_d_15.png","page1/11_d_2.png","page1/11_d_3.png","page1/11_d_4.png","page1/11_d_5.png","page1/11_d_6.png","page1/11_d_7.png","page1/11_d_8.png","page1/11_d_9.png","page1/12_a.png","page1/12_b.png","page1/13_a.png","page1/13_b.png","page1/14_a.png","page1/14_b.png","page1/14_h.png","page1/14_i.png","page1/15_b.png","page1/15_b2.png","page1/15_c.png","page1/15_d.png","page1/15_f.png","page1/15_g.png","page1/15_g2.png","page1/16_a.png","page1/16_b.png","page1/16_d.png","page1/16_e.png","page1/16_g.png","page1/17_a.png","page1/17_b.png","page1/17_c.png","page1/17_d.png","page1/17_e.png","page1/18_a.png","page1/18_b.png","page1/1_a.png","page1/1_b.png","page1/2_e.png","page1/2_f.png","page1/4_c.png","page1/4_d.png","page1/4_e.png","page1/4_h.png","page1/6_b.png","page1/6_c.png","page1/7_a.png","page1/7_b.png","page4/box.png","page4/image_1.png","page5/box.png","page5/ele.png"];
	$.ajaxSetup({
		cache: true
	});
	var _length = images.length;
	images.forEach(function (file) {
		var _ = new Image();
		_.src = "image/" + file;
		$(_).bind("load error", function () {
			count++;
			var percent = parseInt(100 * count / _length);
			$("#number").text( percent + '%');
			$(".loading span").css("width", percent + '%');
			if (count === _length) {
				$("#load").hide();
				$("#main").html($("textarea").val());
				return;
			}
		});
	});
});