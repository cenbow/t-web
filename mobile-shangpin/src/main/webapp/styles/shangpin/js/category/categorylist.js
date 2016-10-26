var path = getRootPath();
$(function(){
	//判断当是微信的时候不显示头
	var iswx =$("#_iswx").attr("value");
	var isapp =$("#_isapp").attr("value");
	if(!isapp){
		$('.cate-box').css({top:45});
	}else{
		$('.cate-box').css({top:0});
	}
	var scrollLeft,
		scrollMid,
		scrollRight;
	if($("#categoryLeft").length > 0){
		scrollLeft();
	}
	if($("#categoryMid").length > 0){
		scrollMid();
	}
	if($("#categoryRight").length > 0){
		scrollRight();
	}
	//tab切换事件
	var tabs = function(nav,content){
		$(nav).find("li").bind("click",function(e){
			e.preventDefault();
			var nav_id=$(this).find('input')[0].value;


			$(this).addClass("cur").siblings().removeClass("cur");
			if(nav_id==10000){
				window.location=getRootPath()+ "/brand/list";
				return;
			}
			$("#categoryMid div .tabs_cell").text("");
			//$(content).eq(index).show().siblings(content).hide();
			scrollMid.refresh();
			var li_a = $(this).children('a').children("strong").attr("ctag");//埋点参数
			getCategory(nav_id,li_a);
			scrollMid.refresh();
			scrollMid.scrollTo(0, 0, 200);
		});
		$(nav).find("li:first strong").trigger("click");
	}
	tabs(".cate-list", ".tabs_cell");
	
	
	var scrollLeft,
		scrollMid,
		scrollRight;
	function scrollLeft(){
	  scrollLeft = new iScroll('categoryLeft',{
				  hScrollbar:false,
				  vScrollbar:false,
				  hideScrollbar:true
			  });
	}
	function scrollMid(){
	  scrollMid = new iScroll('categoryMid',{
				  hScrollbar:false,
				  vScrollbar:false,
				  hideScrollbar:true
			  });
	}
	function scrollRight(){
	  scrollRight = new iScroll('categoryRight',{
				  hScrollbar:false,
				  vScrollbar:false,
				  hideScrollbar:true
			  });
	}
	//监听横竖屏切换事件
	window.addEventListener("orientationchange", function() {

		scrollMid.refresh();
		
	}, false);
	


//请求分类信息
function getCategory(id,ctagStr) {
	 if(id ==null){
		 $("#categoryMid div .tabs_cell").append("暂无商品");	
	 }
		$.ajax({
			url : path + "/category/operations?"+Math.random(),
			data : {
				"id" : id
				},
			dataType : "json",
			success : function(data) {
				var html = "";
				var len = 0;
				if (data.categoryItem.operation != null) {
					len = data.categoryItem.operation.length;
				}
				if (len > 0) {
					$.each(data.categoryItem.operation, function(index, item) {
					    if(item.name !=""){
					    	html +="<div class='cate-brand-title'><a href='javascript:;'>";
					    	html +=item.name;
					    	html +="</a><em></em></div>";
					    }
						if(item.type!=null && item.type>0){
							switch(parseInt(item.type)) 
							{ 
							case 1: 
								html +="<div class='cate-banner'>";
							    html = htmlInfo(item,html,ctagStr);
								html +="</div>";
								break;
							case 4:
							case 5:
								html +="<div class='brandBox clr'>";
								html = htmlInfo(item,html,ctagStr);
								html +="</div>";
								break;
							default:; 
							}
						}
					});
					$("#categoryMid div .tabs_cell").append(html);
					setTimeout(function(){
						scrollMid.refresh();
					},500)
				}else{
					$("#categoryMid div .tabs_cell").append("暂无商品");	
				}
			}
		});
		//scrollMid.refresh();
	 //
}
//拼接分类信息
function htmlInfo(items,htmls,ctagStr) {

	/**var ctag ={
		id:"",
		menuId:ctag.menuId,
		menuName:ctag.menuName,
		menuIndex:ctag.menuId,
	}**/
	$.each(items.list, function(index,item) {
		var ctag = JSON.parse(ctagStr);
		ctag.id="R1";
		ctag.submname=item.name;
		var url;
		switch(parseInt(item.type))
		{ 
		 case 1:
			 ctag.sid=item.refContent;
			 url = path+"/subject/product/list?topicId="+item.refContent;
			break; 
		 case 2:
			 var url =path+"/category/product/list?filters="+item.filters;
		 	 ctag.cid=item.refContent;
		   break; 
		 case 3:
		 	 url= path+"/brand/product/list?filters="+item.filters;
			 ctag.bid=item.refContent;
			break;
		 case 4:
		 	 url = path+"/product/detail?productNo="+item.refContent;
			 ctag.spu=item.refContent;
			break;
		 case 5: //任意链接
			 url = item.refContent;
			 ctag.url=item.refContent;
			break;
		 case 6: //特殊链接refContent 为枚举
			 url =htmlLink(html,parseInt(item.refContent));
			 ctag.url=url;
			break;
		 case 9:
		 	 url = path+"/lable/product/list?tagId="+item.refContent;
			 ctag.lid=item.refContent;
			break;
		 default:
			 url  = "#";
		   break;
		}
		htmls += "<a href='"+url+"' ctag='"+JSON.stringify(ctag)+"' >";
		var pic = item.pic.substring(0,item.pic.indexOf('-')) + "-"+items.width+"-"+items.height+".jpg";
		htmls +="<img alt='' src='"+pic+"' ctag='"+JSON.stringify(ctag)+"'>";
		if(item.type=='5'){
            htmls +="<span>"+item.name+"</span>";
		}
		htmls +="</a>";
	});
	return htmls;
}

	/**
	 *
	 * @param type
	 * @returns {*}
	 */
	function htmlLink(type){
	var url ;
	switch(type)
	{
	 case 1:
		 url =path+"/coupon/list";
		break;
	 case 2:
		 url =path+"/order/list-1";
	   break;
	 case 3:
		 url =path+"/collect/product/list?pageIndex=1&pageSize=20&shopType=1";
		break;
	 case 4:
		 url =path+"/giftCard/productList";
		break;
	 case 7:
		 url ="http://m.aolai.com";
		break;
	 default:
		 url ="#";
	   break;
	}
	return url;
}


});