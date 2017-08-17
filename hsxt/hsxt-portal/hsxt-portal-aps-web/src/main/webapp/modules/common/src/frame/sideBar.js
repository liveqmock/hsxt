define(["text!commTpl/frame/sideBar.html","commSrc/cacheUtil","text!commTpl/frame/subNav.html"],function(tpl,cacheUtil,subNavTpl){
	
	cacheUtil.findPermissionByCustidList(function(response){
		
		//获取缓存中一级菜单
		var match = comm.findPermissionJsonByParentId("050000000000");
		
		//加载左边导航栏
		$('#sideBar').html(_.template(tpl, match));
		
		//菜单上下移动效果开始
		var menuLength_1 = $("#sideBarMenu_1 li").length,
			currentTop = 0,
			liHeight = 84;
		
		$("#sideBarBtn_up_1").click(function(){
			if(currentTop < 0){
				$("#sideBarMenu_1").css("top",(parseInt(currentTop) + liHeight) + "px");
				}
			currentTop = $("#sideBarMenu_1").css("top").replace("px","");
			////console.log("currentTop: " + currentTop);
			});
			
		$("#sideBarBtn_down_1").click(function(){
			/*2015-10-16 框架高度调整*/
			var menuHeight = $(this).siblings('.sideBar_menu_h2').height()
			var menuLast = 	parseInt(menuHeight / liHeight);
			/*end*/		
			if(currentTop > -(menuLength_1 - menuLast)*liHeight){
				$("#sideBarMenu_1").css("top",(parseInt(currentTop) - liHeight) + "px");
				}
			currentTop = $("#sideBarMenu_1").css("top").replace("px","");
			////console.log("currentTop:" + currentTop);
			});
		// end
		
		//按钮事件 20150115 add
		
		function sideBar_nav(id){
			var currentId = (id || ''),
				currentIdArr = currentId.split("_"),
				currentNumber = currentIdArr[1];
			
			/* 20150211 清除主菜单选中状态 */
			$("#navUl > li > div").removeClass("navMenu_hover")
				.children("span").css("color","white");
			/* end */
			
			/* 20150211 添加侧边栏菜单选中状态 */
			$("#" + currentId).addClass("menu_hover")
				.siblings().removeClass("menu_hover");
			/* end */
			
			$(".subNav1 ul").hide();
			$("#subNav_" + currentNumber).show()
				.children("li:first").children("a")
				.click();//点击一级菜单默认显示第一个子菜单页面
			
			//20150311 add
			$("#subNav_" + currentNumber).css("left",0 + "px");
			$(".arrow_right").hide();
			$(".arrow_left").hide();
		}
		
		$('#sideBarMenu_1, #sideBarMenu_2').children('li').click(function(){
//			sideBar_nav($(this).children("span").attr("id"));
			var parentId= $(this).children("span").attr("id")
			$(this).addClass('menu_hover').siblings().removeClass('menu_hover');
			$("#navUl > li > div").removeClass("navMenu_hover").children("span").css("color","white");
			$(".subNav1 ul").hide();
			//获取缓存中一级菜单
			var match = comm.findPermissionJsonByParentId(parentId);
				//加载中间菜单
				
			$('#subNav').html(_.template(subNavTpl, match));
			
			$("#subNav li").release().click(function(key){
				
				var childrenId = $(this).children().attr("id");
				
				$(this).parent().children().children().removeClass("s_hover");
				$(this).children().addClass("s_hover");
				var childrenId = $(this).children().attr("id");
				var strMenuUrl =$(this).children().attr("menuUrl");
				var lstAuthUrl;
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
	

	
	//end
	return tpl;	
});