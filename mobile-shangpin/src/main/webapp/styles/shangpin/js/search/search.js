var path = getRootPath();
$(function(){
	
	var $searchInput = $(".search-input");
	//var path = getRootPath();
	//否显示搜索联想内容
	function searchFn(){
		var len = $searchInput.val().length;
		if(len){
			var key=$searchInput.val();
			$.ajax({
	            type: "POST",
	            url: path + "/search/suggestion",  
	            data : {
	            	"keyword":key
	    		},
	            dataType: "json",
	            async:true,//同步 !重要,防止修改结算参数页面没有刷新完提交
	            success: function (data) {
	            	var addrTipHtml="";
	            	if(data.code == 0){
	            		console.log(data.searchKeyword.suggestions);
	            		$.each(data.searchKeyword.suggestions, function(n, v) {
						addrTipHtml += '<a href="'+path+'/search/list?keyword='+v.name+'">'+v.name;
						if(v.labels.length>0){
							addrTipHtml += '<span>';
							$.each(v.labels, function(o, i) {
								addrTipHtml += '<b onclick="labeLink('+"'"+v.name+" "+i.value+"'"+');return false;">';
								addrTipHtml +=i.value;
								addrTipHtml += '</b>';
							});
							addrTipHtml += '</span>';
						}
						addrTipHtml += '</a>';
	            		});
	            		$(".search-dynamicd-data").empty();
	            		$(".search-dynamicd-data").append(addrTipHtml);
						$(".search-dynamicd-data").show();	
						$(".search-cancel-btn").show();	
					}
	            },
	            error: function (error) {
	                addrTipHtml +="响应失败!";
	            }
	        })
		}else{
			$(".search-dynamicd-data").hide();	
			$(".search-cancel-btn").hide();		
		}	
	}
	
	//清除历史搜索
	$('.search-clear-btn').click(function(e) {
		e.preventDefault();
		var path = getRootPath();
		$.ajax({
            type: "POST",
            url: path + "/search/clearCookie",
            data : {},
            async:false,//同步 !重要,防止修改结算参数页面没有刷新完提交
            dataType: "json",
            success: function (data) {
            	$('.js-history-list').children().remove();
        		$(this).addClass('no-border').html('暂无搜索历史');
            },
            error: function (error) {
              //  addrTipHtml +="响应失败!";
            }
        })
		$('.js-history-list').children().remove();
		$(this).addClass('no-border').html('暂无搜索历史');
	});
	
	$searchInput.keyup(function(){
		searchFn();
	});
	
	//取消当前搜索内容
	$(".search-cancel-btn").click(function(e){
		e.stopPropagation();
		$searchInput.val("");
		searchFn();	
	});
	
})

function labeLink(s){
	window.location.href = path + '/search/list?keyword='+s;
}