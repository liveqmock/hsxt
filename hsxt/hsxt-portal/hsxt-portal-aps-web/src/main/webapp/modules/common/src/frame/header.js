define(["text!commTpl/frame/header.html"],function(tpl){
	//加载页头
	$('#header').html(tpl);
	
	$(".logo").click(function(){
		location.href = "index.html" ;
	});
	
	/*$(".icon_user").click(function(){
		alert("user按钮点击事件");
	});
	
	$(".icon_arrow_down").click(function(){
		alert("arrow_down按钮点击事件");
	});*/
	
	$(".icon_home").click(function(){
		location.href = "index.html" ;
	});
	
	/*$(".icon_skin").click(function(){
		alert("skin按钮点击事件");
	});
	
	$(".icon_setUp").click(function(){
		alert("setUp按钮点击事件");
	});
	
	$(".icon_list").click(function(){
		alert("list按钮点击事件");
	});*/
	
	$(".icon_close").click(function(){
		
	});
	
	//跳转至电商运营系统
	$("#lShopOperatManage").live("click",function(){
		var parma={};
		//获取所有cookie
		var cookies=document.cookie;  
		var cookieArray=cookies.split(";");
		for(var i =0;i<cookieArray.length;i++){
			var key=cookieArray[i].split("="); //获取cookie键
			parma[comm.trim(key[0])]=comm.navNull(key[1]);
		}
		
		window.open(comm.domainList["shopUrl"]+"?ecosEnt="+comm.base64Encrypt(JSON.stringify(parma)));
	});
	
	//跳转至互生运营管理
	$("#lHSOperatManage").live("click",function(){
		window.open(comm.domainList["hsOperateUrl"]);
	});
	
	//安全退出单击事件
	$("#loginOff").click(function () {
		comm.i_confirm("是否要退出？",function () {
			//清除cookies
			var cookies=document.cookie;  //获取所有cookie
			var cookieArray=cookies.split(";");
			
			for(var i =0;i<cookieArray.length;i++){
				var key=cookieArray[i].split("="); //获取cookie键
				//清除cookis，设置过期时间
				$.cookie(key[0].replace(" ",""),null,{expires:-1,path:"/"});
			}
			window.location.replace(comm.domainList["quitUrl"]);
		});
	});
	
	
	
		
	return tpl;	
	
});
