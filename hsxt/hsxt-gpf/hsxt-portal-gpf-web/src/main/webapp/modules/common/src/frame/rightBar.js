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
		
		/*for(var i = 0; i <= 6; i+=2){*/
		for(var i = 0; i <= 4; i+=2){/*20150325 add*/
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
	
	
	   $( "#serviceContent_2_4>.serviceItem>h3" ).click(function(){
			$(this).siblings('h3').next('div').hide();
			$(this).next('div').show('slow');
		})
		
		$( "#serviceContent_2_2>.serviceItem>h3" ).click(function(){
			$(this).siblings('h3').next('div').hide();
			$(this).next('div').show('slow');
		})	
	
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

	
	
	//拨号键盘选中状态 20150209 修改
	$("#dialPanel").children().bind({
		mousedown:function(e){
			var currentBt = $(e.currentTarget);
			var currentClass = currentBt.attr("class");
			currentBt.addClass(currentClass + "_hover");
			},
		mouseup:function(e){
			mouseUpOut(e);
			},
		mouseout:function(e){
			mouseUpOut(e);
			}
		});
		
		function mouseUpOut(e){
			var currentBt = $(e.currentTarget);
			var currentClass = currentBt.attr("class");	
			var arr = [];
			arr = currentClass.split(' ');
			for(var i = 1, l = arr.length; i <= l; i++){//20150209 add
				currentBt.removeClass(arr[i]);	
			}
		}

	//移除rightBar组件自带的class
	var sv2_2 = $("#serviceContent_2_2"),
		sv2_4 = $("#serviceContent_2_4");
		
	sv2_2.children("div").attr("class", "serviceItem")
		.children("h3").removeAttr("class").unbind("mouseenter").click(function(){$(this).removeAttr("class");})
		.children("span").remove();
	sv2_4.children("div").attr("class", "serviceItem")
		.children("h3").removeAttr("class").unbind("mouseenter").click(function(){$(this).removeAttr("class");})
		.children("span").remove();
	
	sv2_2.children("div").children("div").attr("class", "tabDiv_h");
	sv2_4.children("div").children("div").attr("class", "tabDiv_h");
	
	/*var serviceItemClass = serviceItem.attr("class");
	var itemClassArr = [];
	itemClassArr = serviceItemClass.split(" ");
	for(var i = 0, l = itemClassArr.length; i <= l; i++){
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
		var itemDivClassArr = [];
		itemDivClassArr = serviceItemDivClass.split(" ");
		for(var i = 0, l = itemDivClassArr.length; i <= l; i++){
			if(itemDivClassArr[i] != "tabDiv_h"){
			serviceItem.children("div").removeClass(itemDivClassArr[i]);
			}
		}
	}
	
	removeDivClass();
	*/
	
	//end
	
	/*20150325 add*/
	
	$("#fwgsDetail_btn").click(function(){
		$("#fwgsDetail_content").dialog({
			title:"服务公司详细信息",
			width:"800",
			height:"450",
			modal:true,
			buttons:{ 
				"确定":function(){
					$(this).dialog("destroy");
				}
			}
		});
		$( ".jqp-tabs_1" ).tabs();
	});
	
	$("#qyDetail_btn").click(function(){
		$("#qyDetail_content").dialog({
			title:"企业详细信息",
			width:"800",
			height:"450",
			modal:true,
			buttons:{ 
				"确定":function(){
					$(this).dialog("destroy");
				}
			}
		});
		$( ".jqp-tabs_2" ).tabs();
	});
	
	$("#xfzDetail_btn").click(function(){
		$("#xfzDetail_content").dialog({
			title:"消费者详细信息",
			width:"1000",
			height:"410",
			modal:true,
			buttons:{ 
				"确定":function(){
					$(this).dialog("destroy");
				}
			}
		});
		
		$("#fltype").selectbox({width:200}).change(function(){
			console.log("change...");
		});
		
		$( ".jqp-tabs_3" ).tabs();
		
	});
		
	//end
	
	return tpl;
});