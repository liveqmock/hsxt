define(["text!commTpl/frame/chat/contact.html",
		"commDat/frame/chat/chat",
		"commonSrc/frame/chat/hsChat"
		], function(contactTpl, dataModoule){
	return {
		showPage : function(){
			var self = this;
			//查询分组及用户
			dataModoule.findTaskGroupInfo(null, function(res){
				$("#serviceContent_2_4").html(_.template(contactTpl, {list:res.data}));
				$.hsChat.groupIdc["total"] = 0;
				$.each(res.data, function(key, group){
					var groupId = group.groupId;
					var operators = group.operators;
					$.hsChat.groupIds[groupId] = [];
					$.hsChat.groupIdc[groupId] = 0;
					if(operators){
						$.each(operators, function(key, operator){
							if(comm.getRequestParams().custId != operator.optCustId){//排除当前登陆用户
								if(!$.hsChat.userList[operator.optCustId]){
									operator.msgCount = 0;
									operator.groupIds = [groupId];
									$.hsChat.userList[operator.optCustId] = operator;
								}else{
									$.hsChat.userList[operator.optCustId].groupIds.push(groupId);
								}
								$.hsChat.groupIds[groupId].push(operator);
							}
						});
					}
					$.hsChat.search($.hsChat.groupIds[groupId], groupId);
				});
				self.initForm();
				$(".serviceItem").accordion();
			});
		},
		initForm : function(){
			//加载下拉框
			var options = '<option value="">输入名称</option>';
			$.each($.hsChat.userList, function(key, user){
				options+='<option value='+user.optCustId+'>'+user.operatorName+'</option>';
			});
			$("#rightBar_qytxl_search").html(options);
			
			//绑定回车事件
			$('#serviceContent_2_4').find(".search_combobox_style").keydown(function(e){
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
			
			$('#qytxl_serviceItem').find('h3').click(function(){
				var itemId_tmp = $(this).attr('id'),
					itemId = itemId_tmp.replace('ui-accordion-qytxl_serviceItem-header-', '') - 0;	
				$('#qytxl_serviceItem').scrollTop(itemId * 37);
			});
		}
	}
});

