define(["text!commTpl/frame/rightBar.html"],function(tpl){
//加载右边服务模块
	
	$('#service').html(tpl);
	$( ".serviceItem" ).accordion();//申缩菜单效果
	
	$('.listScroll').jScrollPane();//滚动条
	
	//选项菜单事件开始
	var currentTab;
	var arrow1 = "<i class='nav1_arrow'></i>";
	var arrow2 = "<i class='nav2_arrow'></i>";
	
	$("#serviceNav_1").children(":first").append(arrow1);
	$("#serviceNav_2").children(":first").append(arrow2);
	
	$("#serviceNav_1").children().click(function(){
		currentTab = $(this).index();
		$(this).siblings().removeClass("nav1_bt_hover").addClass("nav1_bt").remove(".nav1_arrow");
		$(this).addClass("nav1_bt_hover").append(arrow1);
		
		for(var i = 0; i <= 6; i+=2){
			$("#serviceContent_1_" + i).hide();
		}
		$("#serviceContent_1_" + currentTab).show();
	});
	
	$("#serviceNav_2").children().click(function(){
		currentTab = $(this).index();
		$(this).siblings().removeClass("nav2_bt_hover").addClass("nav2_bt").remove(".nav2_arrow");
		$(this).addClass("nav2_bt_hover").append(arrow2);
		
		for(var i = 0; i <= 4; i+=2){
			$("#serviceContent_2_" + i).hide();
		}
		$("#serviceContent_2_" + currentTab).show();
	});
	//选项菜单事件结束
	
	//拨号记录选中高亮背景
	
	$("#recordList").children().click(function(){
		$(this).siblings().removeClass("record_list_bg");
		$(this).addClass("record_list_bg");
	});
	
	//拨号子菜单选中状态
	$("#serviceSubNavBt").children().click(function(){
		$(this).siblings().children().removeClass("serviceSubNav_bt_hover");
		$(this).children().addClass("serviceSubNav_bt_hover");
	});
	
	//其它按钮点击选中状态

	
	
	//拨号键盘选中状态
	$("#dialPanel").children().bind({
		mousedown:function(){
			var currentClass = $(this).attr("class");
			$(this).addClass(currentClass + "_hover");
			},
		mouseup:function(){
			var currentClass = $(this).attr("class");
			var arr = new Array();
			arr = currentClass.split(' ');
			$(this).removeClass(arr[1]);
			}
		});

	//移除rightBar组件自带的class

	var serviceItem = $(".serviceItem");
	var serviceItemClass = serviceItem.attr("class");
	var itemClassArr = new Array();
	itemClassArr = serviceItemClass.split(" ");
	for(var i = 0; i <= itemClassArr.length; i++){
		if(itemClassArr[i] != "serviceItem"){
			serviceItem.removeClass(itemClassArr[i]);
		}
	}
	serviceItem.children("h3").removeClass();
	serviceItem.children("h3").children("span").remove();

	serviceItem.children("h3").bind({
		mouseover:function(){$(this).removeClass();},
		click:function(){
			$(this).removeClass();
			removeDivClass();
		},
		focus:function(){
			$(this).removeClass();
			removeDivClass();
		}
	});

	function removeDivClass(){
		var serviceItemDivClass = serviceItem.children("div").attr("class");
		var itemDivClassArr = new Array();
		itemDivClassArr = serviceItemDivClass.split(" ");
		for(var i = 0; i <= itemDivClassArr.length; i++){
			if(itemDivClassArr[i] != "tabDiv_h"){
			serviceItem.children("div").removeClass(itemDivClassArr[i]);
			}
		}
	}
	
	removeDivClass();

	//end
	
	return tpl;
});