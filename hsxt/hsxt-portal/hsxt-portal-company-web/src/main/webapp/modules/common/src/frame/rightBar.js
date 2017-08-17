define(["text!commTpl/frame/rightBar.html",
		"commSrc/frame/index",
		"commDat/frame/chat",
		"commSrc/frame/push/push",
		"commonSrc/frame/chat/hsChat"
		],function(tpl, index, dataModoule, pushService){
	//加载右边服务模块
	$('#service').html(tpl);
	
	//加载推送消息
	if(comm.domainList['xmppTs']){
		pushService.showPage();
	}
	
	//存储每个用户未读消息数量
	comm.setCache("chatCache", "messageCount", {"totalCount":0});
	
	/**分页加载最近联系的消费者*/
	function pageQuery(pageNum){
		$.hsChat.getPerRecentContactsMsgList(pageNum, function(persons, totalRows){
			var messageCount = comm.getCache("chatCache", "messageCount");
			var personList= [];
			if(persons){
				$.each(persons, function(key, msg){
					var tsid = msg.toUid.substring(msg.toUid.lastIndexOf("_")+1, msg.toUid.length);
					var usid = msg.fromUid.substring(msg.fromUid.lastIndexOf("_")+1, msg.fromUid.length);
					
					var person = {};
					person.userImg = "";
					if(msg.messageCode == "10"){
						person.msgContent = "[图片]";
					}else{
						var content = msg.message;
						content = content.replace(/↵/ig,' ');
						content = content.replace(/&lt;/g,"<");
						content = content.replace(/&gt;/g,">");
						content = content.replace(/&amp;/g, "&");
						content = content.replace(/<\/div>*|<br><\/div>*/gi," ");
						content = content.replace(/<div>*/gi,"");
						content = content.replace(/&quot;/g,"'").replace(/\n/ig, " ");
						content = content.replace(/\\r\\n/igm, ' ');
						content = $.hsChat.getRealContent(content);
						person.msgContent = $.hsChat.Replace_img(content);
						person.msgContent = person.msgContent.replace(/\\r\\n/igm, ' ');
					}
					if(comm.getRequestParams().custId == tsid){
						person.userCustId = usid;
						person.userName = $.hsChat.getPersonName(msg.senderNickName);
						if(msg.senderHeadImg){
							if(msg.senderHeadImg.indexOf("http") == -1){
								person.userImg = comm.domainList.fsServerUrl+"/"+msg.senderHeadImg;
							}else{
								person.userImg = msg.senderHeadImg;
							}
						}
					}else{
						person.userCustId = tsid;
						person.userName = $.hsChat.getPersonName(msg.receiverNickName);
						if(msg.receiverHeadImg){
							if(msg.receiverHeadImg.indexOf("http") == -1){
								person.userImg = comm.domainList.fsServerUrl+"/"+msg.receiverHeadImg;
							}else{
								person.userImg = msg.receiverHeadImg;
							}
						}
					}
					if(messageCount[person.userCustId]){
						person.msgCount = messageCount[person.userCustId];
					}else{
						messageCount[person.userCustId] = 0;
					}
					if(msg.toUid.indexOf("nc") > -1 || msg.fromUid.indexOf("nc") > -1){
						person.perType = "w_nc_";//非持卡人
						person.realName = $.hsChat.getNickerName(person.userName);
						person.userName = person.realName;
					}else{
						person.perType = "w_c_";//持卡人
						person.realName = person.userName;
					}
					var dateArray = msg.dateTimeStr.split(" ");
					if($.hsChat.getYMDTime() == dateArray[0]){
						person.lastTime = dateArray[1].split(":")[0]+":"+dateArray[1].split(":")[1];
					}else if($.hsChat.getYMDTime(-1) == dateArray[0]){
						person.lastTime = "昨天";
					}else if($.hsChat.getYMDTime(-2) == dateArray[0]){
						person.lastTime = "前天";
					}else{
						person.lastTime = dateArray[0];
					}
					personList.push(person);
				});
				if(!$("#searchTable_chat_prevPage").hasClass("disabledCls")){
					$("#searchTable_chat_prevPage").addClass("disabledCls");
				}
				if(!$("#searchTable_chat_nextPage").hasClass("disabledCls")){
					$("#searchTable_chat_nextPage").addClass("disabledCls");
				}
				$('#searchTable_chat_prevPage').unbind("click");
				$('#searchTable_chat_nextPage').unbind("click");
				//每页显示的记录数
				var pageSize = $.hsChat.perPageSize;
				//计算总页数
				var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : Math.ceil(totalRows / pageSize);
				if(totalPages == 0){
					$("#chat_page_info").html("0/0");
				}
				if($.hsChat.perCurPage > 1){
					if($("#searchTable_chat_prevPage").hasClass("disabledCls")){
						$("#searchTable_chat_prevPage").removeClass("disabledCls");
					}
					$("#chat_page_info").html($.hsChat.perCurPage+"/"+totalPages);
					$("#searchTable_chat_prevPage").click(function(){
						pageQuery($.hsChat.perCurPage-1);
					});
				}
				if($.hsChat.perCurPage < totalPages){
					if($("#searchTable_chat_nextPage").hasClass("disabledCls")){
						$("#searchTable_chat_nextPage").removeClass("disabledCls");
					}
					$("#chat_page_info").html($.hsChat.perCurPage+"/"+totalPages);
					$("#searchTable_chat_nextPage").click(function(){
						pageQuery($.hsChat.perCurPage+1);
					});
				}
			}
			comm.delCache("chatCache", "personList");
			comm.setCache("chatCache", "personList", personList);
			$.hsChat.perSearch(personList);
		});
	}
	
	pageQuery(1);

	dataModoule.findOperatorList(null, function(res){
		var userList = res.data;
		for(var key in userList){
			if(userList[key].operCustId == comm.getRequestParams().custId){
				comm.arrayRemove(userList, key);
			}
		}
		comm.setCache("chatCache", "userList", userList);
		$.hsChat.comSearch(userList);
			
		$.hsChat.getComRecentContactsMsgList(1, 20, function(users){
			if(users){
				var tempList = [];
				$.each(users, function(key, msg){
					var tsid = msg.toUid.substring(msg.toUid.lastIndexOf("_")+1, msg.toUid.length);
					var usid = msg.fromUid.substring(msg.fromUid.lastIndexOf("_")+1, msg.fromUid.length);
					var user = $.hsChat.findComUserById(userList, usid, tsid);
					if(user){
						if(msg.messageCode == "10"){
							user.msgContent = "[图片]";
						}else{
							var content = msg.message;
							content = content.replace(/↵/ig,' ');
							content = content.replace(/&lt;/g,"<");
							content = content.replace(/&gt;/g,">");
							content = content.replace(/&amp;/g, "&");
							content = content.replace(/<\/div>*|<br><\/div>*/gi," ");
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
			comm.delCache("chatCache", "userList");
			comm.setCache("chatCache", "userList", userList);
			$.hsChat.comSearch(userList);
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
				$.hsChat.comChatClick($("#rightBar_qytxl_search").val());
			}
		});
	
		/*搜索自动完成组合框*/
		$("#rightBar_qytxl_search").combobox({
			select:function(){
				$.hsChat.comChatClick($(this).val());
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

 	//申缩菜单效果
	$(".serviceItem").accordion();
	//滚动条
	$('.listScroll').jScrollPane();

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
			var reg = (index == 1 || index == 2 || index == 3) ? /message order/g : /message/g;
			return oldClass.replace(reg, '');
		});
	}
	removeDivClass();

	//绑定事件，清除jquery ui样式
	serviceItem.bind('accordionchange mouseover mouseout click',function(){removeDivClass()});
	
	$('.addressBook').find('span.ui-accordion-header-icon').remove();

	//消息、通讯录tab单击事件
	$(".serviceNav ul li.rightTab").click(function() {
		$(this).addClass("cur").siblings().removeClass("cur");
		$('.serviceItem').hide().eq($('.serviceNav ul li.rightTab').index(this)).show();
	});
	
	//首页登录信息显示
	index.loadRightData();

	return tpl;
});