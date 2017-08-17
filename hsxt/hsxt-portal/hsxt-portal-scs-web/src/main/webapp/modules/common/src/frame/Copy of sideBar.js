define(["text!commTpl/frame/sideBar.html"],function(tpl){
	//加载左边导航栏
 
 	$('#sideBar').html(tpl);
	
	//菜单上下移动效果开始
	var menuLength = $("#sideBarMenu li").length;
	var currentTop = 0;
	var liHeight = 84;
	
	$("#sideBarBtn_up").click(function(){
		if(currentTop < 0){
			$("#sideBarMenu").css("top",(parseInt(currentTop) + liHeight) + "px");
			}
		currentTop = $("#sideBarMenu").css("top").replace("px","");
		//console.log("currentTop: " + currentTop);
		});
		
	$("#sideBarBtn_down").click(function(){		
		if(currentTop > -(menuLength - 7)*liHeight){
			$("#sideBarMenu").css("top",(parseInt(currentTop) - liHeight) + "px");
			}
		currentTop = $("#sideBarMenu").css("top").replace("px","");
		//console.log("currentTop:" + currentTop);
		});
	//菜单上下移动效果结束
	
	function sideBar_nav(e){
		var currentId = $(e.currentTarget).attr("id");
		var currentIdArr = currentId.split("_");
		var currentNumber = currentIdArr[1];
		
		/* 20150211 清除主菜单选中状态 */
		$("#navUl").children("li").children("div").removeClass("navMenu_hover");
		$("#navUl").children("li").children("div").children("span").css("color","white");
		/* end */
		
		/* 20150211 添加侧边栏菜单选中状态 */
		$(e.currentTarget).addClass("menu_hover");
		$(e.currentTarget).siblings().removeClass("menu_hover");
		/* end */
		
		$(".subNav1 ul").hide();
		$("#subNav_" + currentNumber).show();
	}
	
	//调用企业系统信息二级
	$("#Nav_4").click(function(e){
		sideBar_nav(e);
	});
	
	//调用系统年费缴纳二级
	$("#Nav_5").click(function(e){
		sideBar_nav(e);
	});
	
	//调用系统安全设置二级
	$("#Nav_6").click(function(e){
		sideBar_nav(e);
	});
	
	//调用系统用户管理二级
	$("#Nav_7").click(function(e){
		sideBar_nav(e);
	});
	
	//调用使用文档下载二级
	$("#Nav_8").click(function(e){
		sideBar_nav(e);
	});
	
	//调用消息中心二级
	$("#Nav_9").click(function(e){
		sideBar_nav(e);
	});
	
	return tpl;	
});