function cancelOrder(o,v){
	  if(confirm("确认取消该订单吗？")){
		  var path = getRootPath();
		  $.ajax({
			    url:path+'/order/cancel',
			    data:{
			    	"mainOrderNo":o,
			    	"postArea":v,
			    	"deltime":Math.random()
		  		},
			    dataType:'json',
			    timeout: 30000,
			    async:false,
				error: function (xmlHttpRequest, error) {
					if(error == "error"){
		          		alert("非常抱歉，有点小问题，请联系客服人员！");
		          	}else{
				        alert("您的网络异常");
					}
				    return
				},success:function(data){
					if("0"==data.code){
					 window.location.href=path+"/order/list?statusType="+$("#statusType").val()
					}else{
						if(data.msg=='undefined'){
							alert("非常抱歉，有点小问题，请联系客服人员！");
						}else{
							alert(data.msg);
						}
						
					}
			    }
			  });
	  }
}