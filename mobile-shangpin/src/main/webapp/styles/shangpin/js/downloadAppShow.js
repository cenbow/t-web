$(function() {
	var ran=Math.floor(Math.random() * 2);
	var imageSrc=getRootPath()+"/styles/shangpin/images/new-mall/";
	if(ran==1){
		$("#_favorImg").attr("src",(imageSrc+"download_bottom01.png"));
	}
	$("#_favor").val(ran);
	//判断移动设备版本
	var browser={
		versions:function(){ 
			   var u = navigator.userAgent, app = navigator.appVersion; 
			   return {//移动终端浏览器版本信息 
					trident: u.indexOf('Trident') > -1, //IE内核
					presto: u.indexOf('Presto') > -1, //opera内核
					webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
					gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
					mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
					ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
					android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
					iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
					iPad: u.indexOf('iPad') > -1, //是否iPad
					webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
					isIOS7: u.indexOf('iPhone OS 7') > -1  //最新的IOS7版本
				};
			 }(),
			 language:(navigator.browserLanguage || navigator.language).toLowerCase()
	};
	var apphref=getRootPath()+"/download";
	if(browser.versions.android==true){
		if (navigator.userAgent.toLowerCase().indexOf("micromessenger") > -1) {
		} else {
			apphref=getRootPath()+"/download.action?p=102&ch=4&fileName=shangpin_4.apk";		
		}
	}else if(browser.versions.iPhone==true||browser.versions.iPad==true||browser.versions.ios==true){
		if (navigator.userAgent.toLowerCase().indexOf("micromessenger") > -1) {
		}else{
			apphref="https://itunes.apple.com/cn/app/id598127498?mt=8";
		}
	}
	
	$(".headApp").click(function(){
		window.location.href=getRootPath()+"/countDownload";
		return false;
	});
	
	$(".track .slider").click(function(){
		var favor=$("#_favor").val();
		window.location.href=getRootPath()+"/countDownload?favor="+favor;
		return false;
	});
	$(".download_bottom_app").click(function(){
		var favor=$("#_favor").val();
		window.location.href=getRootPath()+"/countDownload?favor="+favor;
		return false;
	});
	//关闭
	$('.close-app').click(function(){
		$('.daoliuBanner').hide();
		$('.footerBtm').css("bottom","0px");
	});
	
	
});


