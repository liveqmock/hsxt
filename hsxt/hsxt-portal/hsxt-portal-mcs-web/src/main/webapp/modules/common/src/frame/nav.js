define(["text!commTpl/frame/nav.html", "commDat/common","commSrc/frame/index","commSrc/cacheUtil","text!commTpl/frame/subNav.html"],function(tpl,common,index,cacheUtil,subNavTpl){
	//加载中间菜单）
 
	$('#nav').html(_.template(tpl  ,  comm.lang('common')   ) );
	
	//菜单按钮事件开始
	var currentClick;
	
	//执行调用缓存方法查询用户权限
	cacheUtil.findPermissionByCustidList(function(response){
		
		//获取缓存中一级菜单
		var match = comm.findPermissionJsonByParentId("0");
		
		//加载中间菜单） 
		$('#nav').html(_.template(tpl,match));
		
		//菜单按钮事件开始 
		$("#navUl li").click(function(e){
			var parentId= $(this).children().children("span").attr("id")
			$(this).siblings().children().removeClass("navMenu_hover");
			$("#sideBarMenu li").addClass('sideBar_hover').siblings().removeClass('sideBar_hover');
			$(this).children().addClass("navMenu_hover");
			
		

			//设置隐藏
			$(".subNav1 ul").hide();
			
			//查询二级菜单
			var levelMenu = comm.findPermissionJsonByParentId(parentId);
			
			//加载中间菜单
			$('#subNav').html(_.template(subNavTpl, levelMenu));
			
			$("#subNav li").release().click(function(key){
				$(this).parent().children().children().removeClass("s_hover");
				$(this).children().addClass("s_hover");
				var childrenId = $(this).children().attr("id");
				var strMenuUrl =$(this).children().attr("menuUrl");
				var lstMenuUrl;
					var arr = new Array();
					if(""!=strMenuUrl&&null!=strMenuUrl){
						lstMenuUrl=strMenuUrl.split(",")
						for(var i=0;i<lstMenuUrl.length;i++){
							arr[i]=lstMenuUrl[i];
						}
					}
					require(arr,function(src){	
						src.showPage();
					})
			});
		
		});
		
		
	});
	
	
	//子菜单按钮链接开始
	
	function liActive( liObj ){	
		 $('.subNav1 ul a').removeClass('s_hover'); 	
	 	liObj.addClass('s_hover');
	 	liObj.parent().siblings('li').find('a').removeClass('s_hover');
	};
	

	
	//子菜单按钮链接结束
	 
	//首页登录信息显示
	index.loadIndxData();
	
	return tpl;	
});