﻿define(["text!commTpl/frame/rightBar.html",
		"commSrc/frame/index",
		"commDat/frame/chat",
		"commSrc/frame/push/push",
		"commonSrc/frame/chat/hsChat"
		],function(tpl, index, dataModoule, pushService){
	//加载右边服务模块
	var rightBar = this;
	
	$('#service').html(tpl);
	
	//加载推送消息
	if(comm.domainList['xmppTs']){
		pushService.showPage();
	}
	
	dataModoule.findOperatorList(null, function(res){
		var userList = res.data;
		for(var key in userList){
			if(userList[key].operCustId == comm.getRequestParams().custId){
				comm.arrayRemove(userList, key);
			}
		}
		$.hsChat.getRecentContactsMsgList("Y", 1, 20, function(users){
			if(users){
				var tempList = [];
				$.each(users, function(key, msg){
					var tsid = msg.toUid.substring(msg.toUid.lastIndexOf("_")+1, msg.toUid.length);
					var usid = msg.fromUid.substring(msg.fromUid.lastIndexOf("_")+1, msg.fromUid.length);
					var user = $.hsChat.findUserById(userList, usid, tsid);
					if(user){
						if(msg.messageCode == "10"){
							user.msgContent = "[图片]";
						}else{
							var content = msg.message;
							content = content.replace(/&lt;/g,"<");
							content = content.replace(/&gt;/g,">");
							content = content.replace(/&amp;/g, "&");
							content = content.replace(/<\/div>*|<br><\/div>*/gi,"<br>");
							content = content.replace(/<div>*/gi,"");
							content = content.replace(/&quot;/g,"'").replace(/\n/ig, " ");
							content = $.hsChat.getRealContent(content);
							user.msgContent = $.hsChat.Replace_img(content);
							user.msgContent = user.msgContent.replace(/\\r\\n/igm, ' ');
						}
						var dateArray = msg.dateTimeStr.split(" ");
						if($.hsChat.getYMDTime() == dateArray[0]){
							user.lastTime = dateArray[1].split(":")[0]+":"+dateArray[1].split(":")[1];
						}else if($.hsChat.getYMDTime(-1) == dateArray[0]){
							user.lastTime = "昨天";
						}else if($.hsChat.getYMDTime(-2) == dateArray[0]){
							user.lastTime = "前天";
						}else{
							user.lastTime = dateArray[0];
						}
						tempList.push(user);
					}
				});
				userList = tempList.concat(userList);
			}else{
				$.each(userList, function(key, user){
					user.msgCount = 0;
				});
			}
			comm.setCache("chatCache", "userList", userList);
			$.hsChat.search(userList);
			bindSearch();
		});
	});
	
	function bindSearch(){
		var userList = comm.getCache("chatCache", "userList");
		//加载下拉框
		var options = '<option value="">输入名称</option>';
		$.each(userList, function(key, user){
			options+='<option value='+user.operCustId+'>'+user.realName+'</option>';
		});
		$("#rightBar_qytxl_search").html(options);
		
		//绑定回车事件
		$('#service').find(".search_combobox_style").keydown(function(e){
			if(e.keyCode == 13){
				$.hsChat.chatClick($("#rightBar_qytxl_search").val());
			}
		});
	
		/*搜索自动完成组合框*/
		$("#rightBar_qytxl_search").combobox({
			select:function(){
				$.hsChat.chatClick($(this).val());
			}	
		});
		
		$(".ui-autocomplete").css({
			"max-height":"250px",
			"overflow-y":"auto",
			"overflow-x":"hidden",
			"height":"250px",
			"border":"1px solid #CCC"
		});
		
		$(".search_combobox_style").find("a").css('display', 'none');
	}
	
	//消息、通讯录tab单击事件
	$(".serviceNav ul li.rightTab").click(function() {
		$(this).addClass("cur").siblings().removeClass("cur");
		$('.serviceItem').hide().eq($('.serviceNav ul li.rightTab').index(this)).show();
	});
	
	$( ".serviceItem" ).accordion();//申缩菜单效果
	
	$('.message').find('span.ui-accordion-header-icon').remove();	
	/*$('.listScroll').jScrollPane();*///滚动条
	
	//清除jquery ui样式
	var serviceItem = $(".serviceItem");
	serviceItem.removeClass(function(index, oldClass){
		var reg = index == 0 ? /serviceItem/g : /serviceItem addressBook/g;
		return oldClass.replace(reg, '');
	});
	
	function removeDivClass() {
		//清除h3标题的jquery ui样式
		serviceItem.children("h3").removeClass().children("span").remove();
		//清除div content的jquery ui样式
		serviceItem.children("div").removeClass(function(index, oldClass){
			var reg = ( index == 0 || index == 1) ? /message order/g : /message/g;
			return oldClass.replace(reg, '');
		});
	}
	removeDivClass();
	 
	//选项菜单事件结束
	
	//拨号记录选中高亮背景
	
	/*$("#recordList").children().click(function(){
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
	}*/

	//移除rightBar组件自带的class

	/*var serviceItem = $(".serviceItem");
	var serviceItemClass = serviceItem.attr("class");
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
	
	removeDivClass();*/

	//end
	
	/*20150325 add*/
	
	/*$("#fwgsDetail_btn").click(function(){
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
		$( ".jqp-tabs_3" ).tabs();
	});
		*/
		

	//end
	index.loadRightData();
	
	return tpl;
});