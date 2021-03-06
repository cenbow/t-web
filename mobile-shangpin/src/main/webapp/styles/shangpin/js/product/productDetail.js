var menuTopHeight=0;
var secondPropName=$("#secondPropName").val();
var firstPropName=$("#firstPropName").val();
var addCartFlag = true;
var cartCount = 0;
$(function(){
	//是否是app
	var isapp=$("#_isapp").val();
	if(isapp){
		$('.footerBtm').css("bottom","0px");
	}	
	//设置全局所有的ajax请求为同步请求
	$.ajaxSetup({
	  async: false
	});
	var lowestPrice=$("#lowestInfo").attr("lowestPrice");
	var marketPrice=$("#lowestInfo").attr("marketPrice");
	var isPromotion=$("#lowestInfo").attr("isPromotion");
	showPrice(lowestPrice,marketPrice,isPromotion,"0");
	
	var path = getRootPath();
	var proShelves=$("#proShelves").val();
	if(proShelves == 1){
		$(".goods-sold-out").hide();
		$(".detailed_information").show();
	}else{
		$(".detailed_information").hide();
		$(".footerBtm").hide();
		$(".goods-sold-out").show();
	}

        //点击收藏
	$(".alContent").on("click",".collection_btn",function(){
		if(!$(this).hasClass("cur")){
			
			collect();
		}else{
			$("").removeClass("cur");
			cancleCollect();
		}
				
	})

	var isShoppingCart =false; //公共变量
	var commodityNumber = 1;   //公共变量
	//tab切换
	var tabFlag=1;
	var sizeFlag=1
	$(".tab_info").find("li").click(function(){
		var $this = $(this);
		var $thisIndex = $this.index();
		var id=$this.attr("id");
		$(this).addClass("curr").siblings().removeClass("curr");
		if(id=="product_tab_1"){
			$(".product_introduction").show();
			$(".other_service_ep").show();
			$(".list-box").show();
			$(".service_pic").show();
			$(".content_info").find(".content_list").eq(0).show().siblings().hide();
		}
		if(id=="product_tab_2"){
			$(".list-box").hide();
			$(".brand-shop-enter").hide();
			$(".service_pic").hide();
			$(".content_info").find(".content_list").eq(1).show().siblings().hide();
		} 
		if(id=="product_tab_3"){
			$(".list-box").hide();
			$(".brand-shop-enter").hide();
			$(".service_pic").hide();
			getSize();
			$(".content_info").find(".content_list").eq(2).show().siblings().hide();
		}
		if(id=="product_tab_4"){
//			getTemplate();
			$(".content_info").find(".content_list").eq(3).show().siblings().hide();
		}
		if(id=="product_tab_5"){
			$(".list-box").hide();
			$(".brand-shop-enter").hide();
			$(".service_pic").hide();
			//&(".detailed_information ")
			getIntroduction();			 
			$(".content_info").find(".content_list").eq(4).show().siblings().hide();
		}
	});
	$(window).scroll(function(){
		topFixed('.topFix');
	});
	var flag=1;
	//评论--查看更多评论
	$(".moreComment").click(function(){
		var $this = $(this);
		$this.parents(".content_list").find(".content_comment").show();
		
		$this.addClass("hidden");
		$(".putAwayComment").removeClass("hidden");
		if(flag==1){
			getComment(1);
			flag=0;
		}
	
		
	});

	//评论--收起评论
	$(".putAwayComment").click(function(){
		var $this = $(this);
		$this.parents(".content_list").find(".comment_height").find(".content_comment").each(function(){
			var thisIndex = $(this).index();
			if(thisIndex<3){
				$(this).show();
			}else{
				$(this).removeAttr("style"); 
				$(this).hide();
			}
		});
		$this.addClass("hidden");
	 
		$(".moreComment").removeClass("hidden");
		
	})

	//点击大图片显示图片的列表
	$(".photo_details").click(function(){
		$(".simple_information").show();
		$(".detailed_information").hide();
	});

	//点击图片列表中的图片返回大图片显示
	$(".simple_information").find("img").click(function(){
		$(".detailed_information").show();
		$(".simple_information").hide();
		$("html, body").animate({ scrollTop: 0 }, 120);
	})

	var touch = 1; //公共变量控制是否滑动屏幕
	var submitHtml; //公共变量控制是否滑动屏幕
	//点击“加入购物车”,“立即按钮购买”
	$(".add_btn, .buy_btn, .select_bar").click(function(e){
			e.preventDefault();
		if($(".footerBtmFixed div a.add_btn").attr("cartSoldOut")!='1'){
			touch = 0;
			$(".alProd").height(document.documentElement.clientHeight).show();
			$("body").attr("style","overflow:hidden");
			$("body").bind("touchmove",function(e){
				if(touch==0){
					e.preventDefault();
				}
			})
	
			$(".alProdInfo").show();
		}
	});
	
	//点击“加入购物车”按钮
	$(".add_btn").click(function(){
		if($(".footerBtmFixed div a.add_btn").attr("cartSoldOut")!='1'){
			isShoppingCart = true;
			submitHtml = '<a href="javascript:;" class="submit_btn">确定</a>';
			$(".submit").html(submitHtml);
		}else{
			return jShare("商品已售罄","","");
		}
	});

	//点击“立即购买”按钮
	$(".buy_btn").click(function(e){
		e.preventDefault();
		if($(".footerBtmFixed div a.buy_btn").attr("buySoldOut")!='1'){
			isShoppingCart = false;
			submitHtml = '<a href="javascript:;" class="submit_btn">确定</a>';
			$(".submit").html(submitHtml);
		}else{
			return jShare("商品已售罄","","");
		}
	});
	
	
	//点击“尺寸”按钮
	$(".select_bar").click(function(){
		if($(".product_promotional a.select_bar").attr("selectSoldOut")!='1'){
			isShoppingCart = false;
			var submitHtml;
			if($(".footerBtm .collection_btn").hasClass("cur")){
				submitHtml = '<p><a href="javascript:;" class="collection_btn cur">已加入愿望清单</a><a href="javascript:;" class="addShopCart">加入购物袋</a></p>';
			}else{
				submitHtml = '<p><a href="javascript:;" class="collection_btn">加入愿望清单</a><a href="javascript:;" class="addShopCart">加入购物袋</a></p>';
			}
			$(".submit").html(submitHtml);
		}else{
			return jShare("商品已售罄","","");
		}
	});
	
	//选择商品后点击“确定”购买
	$(".submit").on("click",".submit_btn",function(){
		gosunmit();
		return false;
	});
	$(".submit").on("click",".addShopCart",function(){
		isShoppingCart = true;
		gosunmit();
		return false;
	});
	$(".submit").on("click",".goBuy",function(){
		isShoppingCart = false;
		gosunmit();
		return false;
	});
	 //
	function gosunmit(){
		var color_info = $(".color_info").find("li.curr").length;
		var size_info = $(".size_info").find("li.curr").length;
		var maxNum = $(".amount_val").attr("maxValue");
		var choiceNum = parseInt($(".amount_val").val());
		
		var id=$(".color_info").find("li.buySold.curr").attr("id");
		var issecondprop=$(".color_info").find("li.curr").attr("issecondprop");
		if($(".color_info").length > 0 && color_info==0){			
			$(".color_info").find("h3").eq(0).html(""+firstPropName+"："+"<span class='red'>请选择"+firstPropName+"</span>")
		}else if(color_info==0 && $(".size_info").length > 0 && size_info==0){			
			$(".size_info").find("h3").eq(0).html(""+secondPropName+"："+"<span class='red'>请选择"+secondPropName+"</span>")
		}else if(choiceNum>maxNum){ //选择的件数大于库存的件数时，提示"库存不足"
			return jShare('库存不足',"","");
		}else{
			var id=$(".color_info").find("li.buySold.curr").attr("id");
			var issecondprop=$(".color_info").find("li.curr").attr("issecondprop");
			if(issecondprop=='1'){
				var size_info = $("#second_"+id).find("li.curr").length;
				if(size_info==0){
					$(".size_info").find("h3").html(secondPropName+"："+"<span class='red'>请选择"+secondPropName+"</span>")
					return;
				}
				
			}			
			$(".alProd, .alProdInfo").hide();
			$("body").removeAttr("style");
			touch = 1;
			if(isShoppingCart==true){
			    addCart();
			    if(addCartFlag==false){
					return;
				}
			}else{
				var skuId=$("#buySku").val();
				var productId=$("#productNo").val();
				var amount=$("#buyCount").val();
				var activityId=$("#topicId").val();
				if(skuId==null||skuId==''||amount==null||amount*1<0){
					return jShare('商品信息有误',"","");
				}
				var isWX = $("#_iswx").val();
				window.location.href=path+"/settlement/now?productId="+productId+"&skuId="+skuId+"&amount="+amount+"&activityId="+activityId;

			}
		}	
	}


	//选择颜色点击
	$(".color_info ul li").click(function(){
		var $this = $(this);
		if(!$(this).hasClass("soldOut")){
			$this.addClass("curr").siblings().removeClass("curr");
			$("#buyCount").val("1");
			$(".amount_val").val("1");
			$this.parents("div").children("h3").html("颜色："+$this.html());
			//$(".choice_product").find("span").eq(0).removeClass("red").html("已选择"+firstPropName+$this.find("p").html())
			var id="second_"+$this.attr("id");
			if($this.attr("issecondprop")=='1'){
				$(".size_info").each(function(){
					var second_id=$(this).attr("id");
					if(id==second_id){
						$("#"+second_id).removeAttr("style"); 
						var select=0;
						$("#"+second_id+" li").each(function(){
							if($(this).hasClass('curr')){
								$("#buySku").val($(this).children("#sku").val());
								select=1;
								$(".choice_product").find("span").eq(1).removeClass("red").html("已选择"+secondPropName+$(this).html())
								var salePrice=$(this).children("#salePrice").val();
								var marketPrice=$(this).children("#marketPrice").val();
								var isPromotion=$(this).children("#isPromotion");
							
								showPrice(salePrice,marketPrice,isPromotion,"1");
							}
						});
						if(select==0){
							$("#buySku").val("");
							$(".choice_product").find("span").eq(1).removeClass("red").html("");
						}
					}else{
						$(this).attr("style","display:none");
					}
				});
			}else{
				$(".size_info").hide();
				var salePrice=$("#"+id).children("#salePrice").val();
				var marketPrice=$("#"+id).children("#marketPrice").val();
				var sku=$("#"+id).children("#sku").val();
				var isPromotion=$("#"+id).children("#isPromotion");
				showPrice(salePrice,marketPrice,isPromotion,"1");
				$(".choice_product").find("span").eq(1).removeClass("red").html("");
				$("#buySku").val($("#"+id).children("#sku").val());
				
			}
		}
	});

	//选择尺码点击
	$(".size_info ul li").click(function(e){
		$("#buyCount").val("1");
		$(".amount_val").val("1");
		if($(this).hasClass('size-sold-out')){
			$('.size-detail-info').hide();
			return false;
		}
		var $this = $(this);
		if($this.hasClass("soldOut")){
			e.preventDefault();
			return;
		}
		e.stopPropagation();
		$('.table-box table').children().remove();
		var count = parseInt($this.attr("count"));
		if(count > 0){
			var trContent="<tr>";
			for(var i = 1; i <= count; i++){
				trContent += "<td>" + $this.attr('data-key' + i) + "</td>";
			}
			trContent += "</tr><tr>";
			for(var i = 1; i <= count; i++){
				trContent += "<td>" + $this.attr('data-val' + i) + "</td>";
			}
			trContent += "</tr>";
		
		
			$('.table-box table').append(trContent);
				
			var offLeft = $this.offset().left + $this.width()/2;
			var sizeOffLeft = offLeft- $('.size-detail-info').width()/2;
			var sizeOffRight = $('.size_info').width() - $('.size-detail-info').width();
			var offTop = $this.position().top;
			
			
			if( sizeOffLeft>0&&sizeOffLeft<sizeOffRight){
				$('.size-detail-info').css({left:sizeOffLeft});
				$('.size-detail-info .size-detail-arrow').css({left:'50%',marginLeft:'-10px'});
			}else if( sizeOffLeft>=sizeOffRight){
				
				$('.size-detail-info').css({left:sizeOffRight});
				$('.size-detail-info .size-detail-arrow').css({left:offLeft-sizeOffRight,marginLeft:0});
				
			}else{
				$('.size-detail-info').css({left:0});
				$('.size-detail-info .size-detail-arrow').css({left:offLeft,marginLeft:0});
			}
			$('.size-detail-info').css({top:offTop-65});
			if(sizeOffRight<=0){
				touch = 1;
			}
			if($this.hasClass('curr')){
				if($('.size-detail-info').is(":hidden")){
				  	$('.size-detail-info').show();
				}else{
					$('.size-detail-info').hide();
				  	touch = 0;
				}
				return;
			}
			$this.addClass("curr").siblings().removeClass("curr");
			$('.size-detail-info').show();
			$this.parents("div").children("h3").html($this.html());
		}
		if(!$(this).hasClass("soldOut")){
			$this.addClass("curr").siblings().removeClass("curr");
			$this.parents("div").children("h3").html(secondPropName+"："+$this.html());
			//$(".choice_product").find("span").eq(1).removeClass("red").html("已选择"+secondPropName+$this.html())
			var salePrice=$this.children("#salePrice").val();
			var marketPrice=$this.children("#marketPrice").val();
			var sku=$this.children("#sku").val();
			var isPromotion=$this.children("#isPromotion").val();
			showPrice(salePrice,marketPrice,isPromotion,"1");
			$("#buySku").val(sku);
		}
	
	});
	
	$('.size-close').click(function(){
		$('.size-close').parent().hide();
		touch = 0;
	});
	$(document).click(function(){
		$('.size-detail-info').hide();
	});
	//添加商品数量点击
	$(".amount_add").click(function(){
		var colorName = $("#color_name").val();
		var sizeName = $("#size_name").val();
		var color_info = $(".color_info").find("li.curr").length;
		var size_info = $(".size_info").find("li.curr").length;
		if(color_info==0  && colorName!=""){
			$(".color_info").find("h3").eq(0).html(""+firstPropName+"："+"<span class='red'>请选择"+firstPropName+"</span>")
		}else if(colorName=="" && $(".size_info").length > 0 && size_info==0){
			$(".size_info").find("h3").eq(0).html(""+sizeName+"："+"<span class='red'>请选择"+sizeName+"</span>")
		}else{
			var id=$(".color_info").find("li.buySold.curr").attr("id");
			var issecondprop=$(".color_info").find("li.curr").attr("issecondprop");
			var skuCount=0;
			if(issecondprop=='1'){
				var size_info = $("#second_"+id).find("li.curr").length;
				skuCount=$("#second_"+id).find("li.curr").children("#count").val();
				if(size_info==0){
					$(".size_info").find("h3").html(secondPropName+"："+"<span class='red'>请选择"+secondPropName+"</span>");
					return;
				}
				
			}else if($(".size_info").length > 0 && colorName==""){
			        skuCount=$(".size_info").find("li.curr").children("#count").val();
		    }else if(color_info== 0 && size_info==0){
			        skuCount=$("#second_first_0").children("#count").val();
		    }else{
				    skuCount=$("#second_"+id).children("#count").val();
			}
			var amount_val = $(".amount_val").val();
			if(skuCount>amount_val*1){
				if(amount_val*1<10){
					$(".amount_val").val(++amount_val);
				}else{
					
					$(".amount_val").val(10);
					return jShare("抱歉！您最多只能购买10件!","","");
				}
				
				
				commodityNumber=$(".amount_val").val();
				$("#buyCount").val(commodityNumber);
			}else{
				return jShare("抱歉！您最多只能购买"+skuCount+"件!","","");
			}
			
		}
		
	});

	//删除商品数量点击
	$(".amount_cut").click(function(){
		var color_info = $(".color_info").find("li.curr").length;
		var size_info = $(".size_info").find("li.curr").length;
		var colorName = $("#color_name").val();
		var sizeName = $("#size_name").val();
		if(color_info==0 && colorName!=""){
			$(".color_info").find("h3").eq(0).html(""+firstPropName+"："+"<span class='red'>请选择"+firstPropName+"</span>")
		}else if(colorName=="" && $(".size_info").length > 0 && size_info==0){
			$(".size_info").find("h3").eq(0).html(""+sizeName+"："+"<span class='red'>请选择"+sizeName+"</span>")
		}else{
			var id=$(".color_info").find("li.buySold.curr").attr("id");
			var issecondprop=$(".color_info").find("li.curr").attr("issecondprop");
			if(issecondprop=='1'){
				var size_info = $("#second_"+id).find("li.curr").length;
				if(size_info==0){
					$(".size_info").find("h3").html(secondPropName+"："+"<span class='red'>请选择"+secondPropName+"</span>");
					return;
				}
			}else if( $(".size_info").length > 0 && colorName==""){
				skuCount=$(".size_info").find("li.curr").children("#count").val();
			}else if(color_info== 0 && size_info==0){
				skuCount=$("#second_first_0").children("#count").val();
			}else{
				skuCount=$("#second_"+id).children("#count").val();
			}
			var amount_val = $(".amount_val").val();
			if(amount_val>1){
				$(".amount_val").val(--amount_val);
				commodityNumber=$(".amount_val").val();
				$("#buyCount").val(commodityNumber);
			}
		}
		
		
	});
	//输入框中直接修改商品数量
	$(".amount_val").keyup(function(){
		var color_info = $(".color_info").find("li.curr").length;
		var size_info = $(".size_info").find("li.curr").length;
		var colorName = $("#color_name").val();
		var sizeName = $("#size_name").val();
		if(color_info==0 && colorName!=""){
			$(".amount_val").val(1);
			$(".color_info").find("h3").eq(0).html(""+firstPropName+"："+"<span class='red'>请选择"+firstPropName+"</span>")
		}else if(colorName=="" && $(".size_info").length > 0 && size_info==0){
			$(".size_info").find("h3").eq(0).html(""+sizeName+"："+"<span class='red'>请选择"+sizeName+"</span>")
		}else{
			var id=$(".color_info").find("li.buySold.curr").attr("id");
			var issecondprop=$(".color_info").find("li.curr").attr("issecondprop");
			var skuCount=0;
			if(issecondprop=='1'){
				var size_info = $("#second_"+id).find("li.curr").length;
				skuCount=$("#second_"+id).find("li.curr").children("#count").val();
				if(size_info==0){
					$(".amount_val").val(1);
					$(".size_info").find("h3").html(secondPropName+"："+"<span class='red'>请选择"+secondPropName+"</span>");
					return;
				}
				
			}else if( $(".size_info").length > 0  && colorName==""){
				skuCount=$(".size_info").find("li.curr").children("#count").val();
			}else if(color_info== 0 && size_info==0){
				skuCount=$("#second_first_0").children("#count").val();
			}else{
				skuCount=$("#second_"+id).children("#count").val();
			}
			var amount_val = $(".amount_val").val();
			if(amount_val<1){
				$(".amount_val").val(1);
				$("#buyCount").val($(".amount_val").val(1));
			}else if(skuCount>10){
				 if(amount_val*1>skuCount){//库存小于选择
					$(".amount_val").val(10);
					return jShare("抱歉！您最多只能购买10件!","","");
				}else if(amount_val>10){
					$(".amount_val").val(10);
					return jShare("抱歉！您最多只能购买10件!","","");
				}else{
					
					$(".amount_val").val(amount_val);
				
				}
			}else{
				if(amount_val*1>skuCount){//库存小于选择
					$(".amount_val").val(skuCount);
					return jShare("抱歉！您最多只能购买"+skuCount+"件!","","");
				}else if(amount_val>10){
					$(".amount_val").val(skuCount);
					return jShare("抱歉！您最多只能购买"+skuCount+"件!","","");
				}else{
					$(".amount_val").val(amount_val);
					
				}
			}
			
			commodityNumber=$(".amount_val").val();
			$("#buyCount").val(commodityNumber);
		}

	})

	//关闭商品弹出层
	$(".close_btn").click(function(){
		$(".alProd, .alProdInfo").hide();
		$("body").removeAttr("style");
		touch = 1;
	})
	
	//购物车商品数量
	$.get(path + "/cart/count",function(data){
		if(data==0 || data==null){
			$("#forCartCount").html(null);
		}else if(data == -1){
			$("#forCartCount").html(null);
		}else{
			$("#forCartCount").attr("style","display:block");
			$("#forCartCount").html(data);
		}
	});
	
});


function showPrice(salePrice,marketPrice,isPromotion,isLoad){
	/*if(salePrice*1<marketPrice){
		$(".product_show section article .not_promotion").append("<span class='market'>原尚品价  &yen;"+marketPrice+"</span>");
		$("#forSaleDesc").addClass("red");
		var saleDesc=div(salePrice,marketPrice);
		$("#forSaleDesc").html(saleDesc+"折");
	}else{
		$(".product_show section article .not_promotion").append("");
		$(".product_show section article ul li.red").removeClass("red");
	}*/
	var color_info = $(".color_info").find("li.buySold").length;//
	var size_info = $(".size_info").find("li.buySold").length;
	$(".alProdInfo p b").html("&yen;"+salePrice);
	
	if(isLoad=='0'){
		if(color_info<=1&&size_info<=1){//只有一个sku
			var count=0;
			var color_firsr_count=0;
			var buySoldNun=0;
			if(size_info==0){
				count=$("#second_first_0").children("#count").val();
				buySoldNun=$(".color_info").find("li.buySold").attr("flag");
				if(count==0 && color_info>0){
				  color_firsr_count=$("#second_first_"+buySoldNun+"").children("#count").val();//sku的库存从这个标签上取值	
				}
			}else{
				count=$(".size_info").find("li.buySold").children("#count").val();
			}
			if(count*1>0 || buySoldNun*1>0){
				$(".color_info").find("li.buySold").addClass("curr")
				$(".size_info").find("li.buySold").addClass("curr")
				$(".choice_product").find("span").eq(0).removeClass("red").html("已选择"+firstPropName+$(".color_info").find("li.buySold").find("p").html())
				if(size_info>0){
					$(".choice_product").find("span").eq(1).removeClass("red").html("已选择"+secondPropName+$(".size_info").find("li.buySold").html())
				}
				if(size_info==0){
					if(count==0 && color_firsr_count>0){
					   $(".color_info").find("li").removeClass("curr")//默认选中当前颜色
					   $(".size_info").find("li").removeClass("curr")//默认选中当前尺码					   
					   $(".color_info").find("li.buySold").addClass("curr")//默认选中当前颜色
					   $(".size_info").find("li.buySold").addClass("curr")//默认选中当前尺码
					   $(".color_info").find("h3").eq(0).html(""+firstPropName+"："+ $(".color_info").find("li.buySold").html());
					  $("#buySku").val($("#second_first_"+buySoldNun+"").children("#sku").val());
					}else{
					  $("#buySku").val($("#second_first_0").children("#sku").val());
					}
				}else{
					$("#buySku").val($(".size_info").find("li.buySold").children("#sku").val());
				}
				
			}else{
				$(".footerBtmFixed div a.add_btn").attr("cartSoldOut","1")
				$(".footerBtmFixed div a.buy_btn").attr("buySoldOut","1")
				$("#buyCount").val("0");
			}
		}else if(color_info<=1){
			$(".color_info").find("li").addClass("curr");
			$(".choice_product").find("span").eq(0).removeClass("red").html("已选择"+firstPropName+$(".color_info").find("li").find("p").html())
		}
	}
	
}
//加入购物车
function addCart(){
	var path = getRootPath();
	var productNo = $("#productNo").val();
	var topicNo = $("#topicId").val();
	var sku = $("#buySku").val();
	var count = parseInt($('#buyCount').val());
	if($("#sku").val()==""){
		return jShare('请选择尺码',"","");
	}
	if(count <= 0){
		return jShare('库存不足',"","");
	}
	$.post(path + "/cart/add",{
		"sku" : sku,
		"productNo" : productNo,
		"topicNo" : topicNo,
		"quantity":count
		},
		function(data,textStatus, XMLHttpRequest){
			var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
			if(sessionstatus=="timeout"){
				var locationURL = XMLHttpRequest.getResponseHeader("locationURLAll"); // 通过XMLHttpRequest取得响应头，locationURL，
				// 如果超时就处理 ，指定要跳转的页面
				window.location.href = locationURL;
			}
			if(data.code == 2){
				window.location = path + "/login?back=cart/list";
			}else if(data.code == 0){
				console.dir(data);
				$.get(path + "/cart/count",function(data1){
					if(data1==0 || data1==null){
						//$("#forCartCount").html(null);
					}else if(data1 == -1){
						//$("#forCartCount").html(null);
					}else{
						cartCount = data1;
						$("#forCartCount").html(data1);
						jShare('加入购物袋成功',"","");
					}
				});
				var brandName=$("#brandName").val();
				_smq.push(['custom','加入购物车',brandName,productNo,count]);
			}else if(data.code == 1){
				addCartFlag = false;
				return jShare(data.msg,"","");
			}
	});
}
function div(exp1, exp2)
{
    var n1 = Math.round(exp1); //四舍五入
    var n2 = Math.round(exp2); //四舍五入
    var rslt = n1 / n2; //除
    var value = Math.round(parseFloat(rslt) * 100) / 100;
    return (value*10).toFixed(1);
}

function chatXiaoNeng(){
	var back = window.location.pathname + window.location.search;
	back = back.replace(getRootPath(),"");
	if(back){
		var reg=new RegExp("&","g");
		back =encodeURI(back).replace(reg,"9uuuuu9");
	}
	$.ajax({
		type: "GET",
		url: getRootPath()+"/isLogin?"+new Date(),
		success: function(data){
			//未登录
			if(data==undefined || data=="" || data == "0" ){
				window.location= getRootPath() + "/login?back=" + back;
			}
			//已登录
			if(data == "1"){
				NTKF.im_openInPageChat('kf_9986_1355827406710');
			}
		}
	});
}