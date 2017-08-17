define(["text!commTpl/frame/header.html","homeSrc/home/home","homeSrc/login/login"],function(tpl,home,login){
	//加载页头
	
	$('#header').html(_.template(tpl , comm.lang('common') ) );
	
	$(".header-logo").click(function(){
		location.href = "index.html" ;
	});
	
	$("#headerHome").click(function(){
		 home.showPage();
	});
	
	//登出
	$('#liExit').click(function(){
		login.showPage();
	});
	
		
	return null;		
});
