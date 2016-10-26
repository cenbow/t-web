var num = 0;
var ready=1;
var $overlay = $('#overlay');
$(".alContent").attr("style", "margin-top:0");
var imgHeight = $('.tabSlider-bd .hallBox a img').eq(0).height(); // 获取焦点图img高度
$('.tabSlider-bd .hallBox a').css('min-height', imgHeight);

//判断设备
var UA = window.navigator.userAgent;
var CLICK = 'click';
if(/ipad|iphone|android/.test(UA)){
	CLICK = 'tap';
}

$(window).scroll(BottomLoading);  //下拉加载

$('body').delegate('.coupon-list li',CLICK,function(e){
	var $that = $(this);
	var code=$(this).attr("id");
	$overlay.addClass('active');
	$('.modal').animate({"display":"block"},100,function(){
		$('.modal').addClass('modal-in');
	});
});

$('body').delegate('.btn-modal',CLICK,function(e){
	modalHidden($('.modal'));
	e.stopPropagation();
});
$('body').delegate($overlay,CLICK,function(e){
	if(e.target.classList.contains('overlay')){
		modalHidden($('.modal'));
	}
});


//筛选按钮事件
$('body').delegate('.fillBtn',CLICK,function(e){
	e.preventDefault();
	fillBtn();
});

//确定按钮事件
$('body').delegate('#finishBtn',CLICK,function(e){
	e.preventDefault();
	filterFinish();
});

//关闭按钮事件
$('body').delegate('#filter_close',CLICK,function(e){
	e.preventDefault();
	filterClose();
});

/*筛选栏里选择选项*/
$('body').delegate('.category-box li,.color-box li',CLICK,function(e){
	$(this).addClass("cur").siblings().removeClass("cur");
});

$('body').delegate('.color-box a',CLICK,function(e){
	$(this).parent().addClass("cur").siblings().removeClass("cur");
});
$('body').delegate('#sizeItem li',CLICK,function(e){
	$(this).addClass("cur").siblings().removeClass("cur");
});



//点击列表按钮切换并跳转
$('#list_menu li')[CLICK](function(e){
	e.preventDefault();
	var url = $(this).children('a').attr('href');
	if(($(this).hasClass('price-btn')) && ($(this).hasClass('curr'))){
		if($('.price-btn').hasClass('price-down')){
			$(this).removeClass('price-down');
		}else{
			$(this).addClass('price-down');
		}
	}

	$(this).addClass('curr').siblings().removeClass('curr price-down');
	window.location.href=url;

})


$(window).scroll(function(){
	topFixed('.topFix');
	topFixed('.menu-nav');
});
function modalHidden($ele) {
	$ele.removeClass('modal-in');
	$ele.one('webkitTransitionEnd',function(){
		$ele.css({"display": "none"});
		$overlay.removeClass('active');
	});
}



//筛选按钮
function fillBtn(){
	$("#filter_layer").show();
	$('body').css({'overflow':'hidden'});
	setTimeout(function(){
		$('.alContent').css({'visibility':'hidden'});
		$('.app_bg').css({'visibility':'hidden'});
	},1000)
	$("#filter_box").attr("class", "slideIn");
	//filterboxSelect();
}

//关闭筛选按钮
function filterClose(){
	$("body").unbind("touchmove");
	$("#filter_box").attr("class", "slideOut");
	$('.alContent').css({'visibility':'visible'});
	$('.app_bg').css({'visibility':'visible'});
	$('body').css({'overflow':'auto'});
	setTimeout(function(){
		$("#filter_layer").fadeOut();
	},600);
	$('#categoryNo').val("");
	$('#categoryName').val("");
	$('#size').val("");
	$('#color').val("");
	$('#colorName').val("");
	$('#price').val("");

	//$('#search_form').submit();
}

//筛选确定按钮
function filterFinish(){
	$("body").unbind("touchmove");//解除禁止滑动事件
	$("#filter_box").attr("class", "slideOut");
	$('body').css({'overflow':'auto'});
	$('.alContent').css({'visibility':'visible'});
	$('.app_bg').css({'visibility':'visible'});
	setTimeout(function(){
		$("#filter_layer").fadeOut();
		$("#start").val("1");
		//$('#search_form').submit();
	},600);
}

//上次的选中状态
function filterboxSelect(){
	//默认选中选项
	var categoryName = $("#categoryName").val();
	var brandName = $("#brandName").val();
	var productSize = $("#size").val();
	var primaryColorId = $("#color").val();
	var primaryColorName = $("#colorName").val();
	var price = $("#price").val();

	$(".category-box ul li").each(function(){
		var selectFlag ="";
		var selValue = $(this).html();
		if(selValue != ""){
			//判断列表的选中状态
			switch(selValue){
				case categoryName :
					selectFlag = "1";
					$(this).addClass("cur");
					break;
				case brandName :
					selectFlag = "1";
					$(this).addClass("cur");
					break;
				case productSize :
					selectFlag = "1";
					$(this).addClass("cur");
					break;
				case price :
					selectFlag = "1";
					$(this).addClass("cur");
					break;
				default :
					selectFlag ="";
					break;
			}
		}

	});

	$(".color-box ul li").each(function(){
		var selectFlag ="";
		var selValue = $(this).find('span').html();
		if(selValue != ""){
			//判断颜色的选中状态
			switch(selValue){
				case primaryColorName :
					selectFlag = "1";
					$(this).addClass("cur");
					$(this).find('a').css('background',primaryColorId);
					break;
				default :
					selectFlag ="";
					break;
			}
		}
	});
}

/*下拉加载*/
function BottomLoading(){
	var path = getRootPath();
	var loading = {
		img : path + '/styles/shangpin/images/201502brand/loading.gif',
		msgText : '正在加载中...',
	};
	var loadingMsg = '<div id="loading" style="display:block"><img src=' + loading.img + ' /><p>' +loading.msgText + '</p><div>'
	var hasMore = $("#hasMore").val();
	if(hasMore == "1"){

		if ($(window).scrollTop() + $(window).height() >= $(document).height()-50){
			if(!$('body').find('#loading').length){
				$('.alContent').append(loadingMsg);
			}

			if(ready==1){
				$('#loading').show();
				var pageIndex = parseInt($('#pageIndex').val()) + 1;
				$('#pageIndex').val(pageIndex);
				$('#loading').show();
				ready=0;
				$.ajax({
					type : "POST",
					url : path + "/collect/product/more",
					data : {pageIndex:pageIndex,pageSize:20,shopType:1},
					dataType:"json",
					success : function(data, textStatus, XMLHttpRequest) {
						var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
						if(sessionstatus=="timeout"){
							//var locationURL = XMLHttpRequest.getResponseHeader("locationURLAll"); // 通过XMLHttpRequest取得响应头，locationURL，
							// 如果超时就处理 ，指定要跳转的页面
							window.location.href = path + "/login?back=/collect/product/list?pageIndex=1&pageSize=20&shopType=1";
						}
						$("#hasMore").val(data.hasMore);
						$('#loading').hide();
						var hasMore = data.hasMore;
						if(data.products != null && data.products.length > 0){
							$.each(data.products,function(index,item){

								var $div = $("<div class='list_box' data-id="+item.id+"></div>");

								var $div_del = $("<div class='prod_delete'></div>");
								var pic_del = getRootPath()+"/styles/shangpin/images/member/clear-button.png?"+Math.random(10);
								var $img = $("<img src=" + pic_del + " />");
								$div_del.append($img);
								$div.append($div_del);

								var pic = item.pic.substring(0,item.pic.indexOf('-')) + "-304-404.jpg";
								var $a = $("<a></a>");
								var href = "javascript:goDetail("+item.type+","+item.isShelve+","+item.productId+")";
								$a.attr("href",href);
								$a.append($("<img src="+pic+">"))
								$div.append($a);

								var $div_text = $("<div class='li_text'></div>");
								if(item.status == "2"){
									var $div_text_sym = $("<div class='symbol'></div>");
									$.each(item.flagList,function(iv,flag){
										$div_text_sym.append("<i>"+flag+"</i>");
									})
									$div_text.append($div_text_sym);
								}


								var productColor ;
								if(isVip()){
									productColor=item.vip_ProductPrice;
								}else {
									productColor=item.nomal_ProductPrice;
								}
								var css_style;
								if(productColor.compareHasLine=='1'){
									css_style = "promotion-price-hasline";
								}else{
									css_style = "promotion-price";
								}

								var $h = $("<h5>" + item.brandNameEN + "</h5>");
								var $p = $("<p>" + item.productName + "</p>");
								var htmlSpan = '<span class="item-detail" style="color:'+productColor.color+'">'+ productColor.priceDesc +
												'<em class='+css_style +' style="color:'+productColor.compareColor+'">'+productColor.compareDesc+'</em>'+
												'</span>'
								var $span = $(htmlSpan);
								$($div_text).append($h);
								$($div_text).append($p);
								$($div_text).append($span);
								$($div).append($div_text);
								$(".prod_list").append($div);
								//var status = item.status;
							})
						}
						num++;
						ready=1;
						if(hasMore == 0){
							$('#loading').html('没有更多了');
							$('#loading').show();
							$('#loading img').hide();
						}else{
							ready=1;
						}
					},
					error : function(){
						alert('数据获取失败，请刷新页面');
					}
				});
			}
		}
	}
}

function goDetail(type,isShelve,id){
	var path=getRootPath();
	if(isShelve=='1'){
		return jShare("商品已下架","","");
	}
	if(type=='1'){
		location.href =path+"/giftCard/cardDetail?productNo="+id+"&type=1"
	}else if(type=='2'){
		location.href =path+"/giftCard/cardDetail?productNo="+id+"&type=2"
	}else{
		location.href =path+"/product/detail?productNo="+id;
	}
}

//愿望清单删除商品
$(".list_box").find(".prod_delete").click(function(e){
	e.preventDefault();
	var $pro = $(this).parent(".list_box");
	var id = $pro.attr("data-id");
	$.ajax({
		type : "GET",
		url : getRootPath() + "/collect/product/cancel",
		data : {id:id},
		dataType:"json",
		success : function(data, textStatus, XMLHttpRequest) {
			if (data.code==0){
				$pro.remove();
			}
		},
		error : function(){
			alert('数据获取失败，请重试');
		}
	});

});