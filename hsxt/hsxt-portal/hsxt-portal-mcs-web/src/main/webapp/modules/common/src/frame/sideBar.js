define(["text!commTpl/frame/sideBar.html","commSrc/cacheUtil","text!commTpl/frame/subNav.html"],function(tpl,cacheUtil,subNavTpl){
	//获取缓存中所有菜单列表
	cacheUtil.findPermissionByCustidList(function(response){
		//获取缓存中一级菜单
		var match = comm.findPermissionJsonByParentId("0");
		$('#sideBar').html(_.template(tpl, match));
		
		//菜单上下移动效果开始
		var menuLength_1 = $("#sideBarMenu_1 li").length;
		var currentTop = 0;
		var liHeight = 84;
		
		$("#sideBarBtn_up_1").click(function(){
			if(currentTop < 0){
				$("#sideBarMenu_1").css("top",(parseInt(currentTop) + liHeight) + "px");
				}
			currentTop = $("#sideBarMenu_1").css("top").replace("px","");
			//console.log("currentTop: " + currentTop);
			});
			
		$("#sideBarBtn_down_1").click(function(){		
			/*2015-10-16 框架高度调整*/
			var menuHeight = $(this).siblings('.sideBar_menu_h2').height()
			var menuLast = 	parseInt(menuHeight / liHeight);
			/*end*/
			if(currentTop > -(menuLength_1 - 6)*liHeight){
				$("#sideBarMenu_1").css("top",(parseInt(currentTop) - liHeight) + "px");
				}
			currentTop = $("#sideBarMenu_1").css("top").replace("px","");
			//console.log("currentTop:" + currentTop);
			});
			
		var menuLength_2 = $("#sideBarMenu_2 li").length;
		
		$("#sideBarBtn_up_2").click(function(){
			if(currentTop < 0){
				$("#sideBarMenu_2").css("top",(parseInt(currentTop) + liHeight) + "px");
				}
			currentTop = $("#sideBarMenu_2").css("top").replace("px","");
			//console.log("currentTop: " + currentTop);
			});
			
		$("#sideBarBtn_down_2").click(function(){		
			/*2015-10-16 框架高度调整*/
			var menuHeight = $(this).siblings('.sideBar_menu_h2').height()
			var menuLast = 	parseInt(menuHeight / liHeight);
			/*end*/	
			if(currentTop > -(menuLength_2 - 6)*liHeight){
				$("#sideBarMenu_2").css("top",(parseInt(currentTop) - liHeight) + "px");
				}
			currentTop = $("#sideBarMenu_2").css("top").replace("px","");
			//console.log("currentTop:" + currentTop);
			});
		//菜单上下移动效果结束

		
		$( ".sideBarBox" ).accordion();//申缩菜单效果
		

		//移除sideBar组件自带的class

		var sideBarBox = $("#sideBarBox");
		var boxClass = sideBarBox.attr("class");
		var classArr = new Array();
		classArr = boxClass.split(" ");
		for(var i = 0; i <= classArr.length; i++){
			if(classArr[i] != "sideBarBox"){
				sideBarBox.removeClass(classArr[i]);
			}
		}

		sideBarBox.children("div").removeClass();
		sideBarBox.children("div").css("height","588px");

		sideBarBox.children("h3").each(function(){
			$(this).children("span").remove();

			var h3Class = $(this).attr("class");
			var h3ClassArr = new Array();
			h3ClassArr = h3Class.split(" ");
			for(var i = 0; i <= h3ClassArr.length; i++){
				if((h3ClassArr[i] != "sideBarTab") && (h3ClassArr[i] != "bg_blue") && (h3ClassArr[i] != "bg_red")){
					$(this).removeClass(h3ClassArr[i]);
				}
			}

			$(this).mouseover(function(){
				var h3HoverClass = $(this).attr("class");
				var h3HoverClassArr = new Array();
				h3HoverClassArr = h3HoverClass.split(" ");
				for(var i = 0; i <= h3HoverClassArr.length; i++){
					if((h3HoverClassArr[i] != "sideBarTab") && (h3HoverClassArr[i] != "bg_blue") && (h3HoverClassArr[i] != "bg_red")){
						$(this).removeClass(h3HoverClassArr[i]);
					}
				}
				
			});

			$(this).click(function(){
				var h3ClickClass = $(this).attr("class");
				var h3ClickClassArr = new Array();
				h3ClickClassArr = h3ClickClass.split(" ");
				for(var i = 0; i <= h3ClickClassArr.length; i++){
					if((h3ClickClassArr[i] != "sideBarTab") && (h3ClickClassArr[i] != "bg_blue") && (h3ClickClassArr[i] != "bg_red")){
						$(this).removeClass(h3ClickClassArr[i]);
					}
				}
				
			});

			$(this).focus(function(){
				var h3FocusClass = $(this).attr("class");
				var h3FocusClassArr = new Array();
				h3FocusClassArr = h3FocusClass.split(" ");
				for(var i = 0; i <= h3FocusClassArr.length; i++){
					if((h3FocusClassArr[i] != "sideBarTab") && (h3FocusClassArr[i] != "bg_blue") && (h3FocusClassArr[i] != "bg_red")){
						$(this).removeClass(h3FocusClassArr[i]);
					}

				}

			});


		});
		
		
		//左边菜单事件
		$("#sideBarMenu_1 li").click(function(e){
			$(this).addClass('menu_hover').siblings().removeClass('menu_hover');
			var parentId= $(this).children("span").attr("id")
			var objParam={"parentId":parentId}
			
			
			$(".subNav1 ul").hide();
			currentLiId = parentId;
			if (currentLiId){
				var idArry = [];
				//idArry = currentLiId.split("_");
				var idNumber = currentLiId;
				$("#subNav_" + idNumber).show();

				$("#subNav_" + idNumber).children("li:first").children("a")
				.click();//点击一级菜单默认显示第一个子菜单页面
				//子菜单滚动 20150310 add
			}
			
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

	
	return tpl;	

	
});