define(["text!commTpl/frame/nav.html","commSrc/frame/index","commSrc/cacheUtil","text!commTpl/frame/subNav.html"],function(tpl,index,cacheUtil,subNavTpl){
	//加载中间菜单） 
	$('#nav').html(tpl);
	$(".arrow_left").hide();
	$(".arrow_right").hide();	
	
	//执行调用缓存方法查询用户权限
	cacheUtil.findPermissionByCustidList(function(response){
		
		//获取缓存中一级菜单
		var match = comm.findPermissionJsonByParentId("050000000000");
		
		//加载中间菜单 
		$('#nav').html(_.template(tpl,match));
		
		//菜单按钮事件开始 
		
		$("#navUl li").click(function(e){
			/*var currentClick = $(e.currentTarget).index();
			$(this).siblings().children().removeClass("navMenu_hover");
			$(this).siblings().children().children("span").css("color","white");
			$(this).children().addClass("navMenu_hover");
			$(this).children().children("span").css("color","#3d8028");
			$(".subNav1 ul").children("li").children("a").removeClass("s_hover");//20150107 kuangrb add
			$(".subNav1 ul").hide();
			$("#subNav_" + currentClick).show();*/
			
			var currentLi = $(e.currentTarget);
			currentLi.siblings().children().removeClass("navMenu_hover")
				.children("span").css("color","white").end().end().end()
				.children().addClass("navMenu_hover").children("span").css("color","#3d8028");
			
			/* 20150211 清除侧边栏菜单选中状态 */
			$(".sideBar_menu_inner > li").removeClass("menu_hover");
			/* end */

			$(".subNav1 > ul").find("a").removeClass("s_hover").end().hide();
			
			var currentLiId = currentLi.attr("id");
			var parentId= $(this).children().children("span").attr("id")
			//查询三级菜单
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
	
	
	//子菜单事件集合
	var navObj = {};
	//子菜单事件触发方法
	function triggerClick(moduleSrc,fileName){
		require([moduleSrc],function(src){
			navObj["fileName"] = src;
			navObj["fileName"].show();	
		})
		if (navObj["fileName"]){
			navObj["fileName"].show();			
		}	
	}
	
	function subNav_hover(e){
		$(e.currentTarget).addClass("s_hover")
			.parent().siblings().children("a").removeClass("s_hover");
	}
	
	 
	//子菜单按钮链接结束
	
	//加载首页操作员信息
	index.loadIndxData();
	
	
	
	return tpl;	
});