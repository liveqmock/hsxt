define(['text!messageCenterTpl/wfxx/wfxx.html',
        'text!messageCenterTpl/wfxx/ckxq.html',
        'messageCenterSrc/wfxx/ckxq',
        'messageCenterDat/messageCenter'],function(wfxxTpl,ckxqTpl,ckxq,messageCenter ){
	return {
		
		searchTable : null,
		self : null,
		showPage : function(){
			$('#busibox').html(_.template(wfxxTpl)) ;			 
			//Todo...
		 
			var gridObj ;
			self = this;
			$("#qry_btn").click(function(){
				
				var params = {
						search_msgTitle : $("#msgTitle").val(),	//消息标题
						search_msgType : 1
				};
				
				searchTable = gridObj = comm.getCommBsGrid("detailTable","find_message_list",params,comm.lang("messageCenter"),self.detail,self.del);
				
			});
			
			$("#qry_btn").click();
				
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return rowIndex + 1;
			}else if(colIndex == 1){
				var result = comm.lang("messageCenter").receiptor[record.receiptor];
				if(result != '' && result != undefined){
					return result;
				}else{
					return record.receiptor;
				}
			}else if(colIndex == 2){
				return comm.lang("messageCenter").msglevel[record.level];
			}
			if(colIndex == 5){
				
				var link1 =  $('<a >'+comm.lang("messageCenter").chakan+'</a>').click(function(e) {
//					self.initChaKan(record);
					var postData = record;
					//获取接收信息的单位的中文名称
					var _rec = comm.lang("messageCenter").receiptor[record.receiptor];
					if('' != _rec && _rec != undefined)
					{
						postData.receiptorName = _rec;
					}
					else
					{
						postData.receiptorName = record.receiptor;
					}
					//重要级别
					postData.levelName = comm.lang("messageCenter").msglevel[record.level];
					//消息类型
					postData.typeName = comm.lang("messageCenter").msgtype[record.type];
					//显示详细信息页面
					ckxq.showPage(postData);
				});
				return   link1 ;
			}
		},
		del : function(record,rowIndex,colIndex,options){
			if(colIndex == 5)
			{
				var link1 =  $('<a >'+comm.lang("messageCenter").del+'</a>').click(function(e) {
					
					//删除	
					messageCenter.delMessageById({msgId:record.msgId},function(res){
						//刷新列表
						if(searchTable){
							searchTable.refreshPage();
						}
					});
					
				});
				return   link1 ;
			}
		}
//		initChaKan : function(record){
//			//隐藏列表页面
//			$('#busibox').addClass('none');
//			//组装数据
//			var postData = record;
//			//获取接收信息的单位的中文名称
//			var _rec = comm.lang("messageCenter").receiptor[record.receiptor];
//			if('' != _rec && _rec != undefined)
//			{
//				postData.receiptorName = _rec;
//			}
//			else
//			{
//				postData.receiptorName = record.receiptor;
//			}
//			//加载编辑页面
//			$('#busibox_2').removeClass('none').html(_.template(ckxqTpl,postData));
//			$('#message_content').xheditor({height:175});
//			$('#btn_xzjsdw').click(function(){
//				$('#jsdw_dialog').dialog({
//					 buttons:{
//					 	'确定':function(){
//					 		var value = $("input[name='selectReceiveEnt']:checked").val();
//					 		var name = $("input[name='selectReceiveEnt']:checked").next().text();
//					 		$("#receiveEntValue").attr("value",value);
//					 		$("#receiveEnt").attr("value",name);
//					 		$(this).dialog('destroy');
//					 	},
//					 	'取消':function(){
//					 		
//					 		$(this).dialog('destroy');
//					 	}
//					 }
//				});
//			});
//			
//			$("#btn_send").click(function(){
//				if (!self.validateData()) {
//					return;
//				}
//				var content = $('#message_content').val();
//				if(content == null || "" == content){
//					comm.error_alert(comm.lang("messageCenter")[32702]);
//					return;
//				}
//				var postData = {
//						msgId : $("#msgId").val(),
//						title : $("#ckxx_msgTitle").val(),						//消息标题
//						msg : content,					//消息内容
//						type : $("input[name='msgType']:checked").val(),	//消息类型
//						level : $("input[name='msgLevel']:checked").val(),	//消息级别
//						sender : '111',										//发送人
//						receiptor : $("#receiveEntValue").val(), 			//接收者
//						entResNo : '12393847362',							//发生企业互生号
//						entCustId : '111',									//方式企业客户号
//						entCustName : 'xxx服务公司' 							//发送企业名称
//				};
//				messageCenter.sendMessage(postData,function(res){
//					$("#subNav_9_02").click();
//				});
//			});
//			$("#btn_save").click(function(){
//				if (!self.validateData()) {
//					return;
//				}
//				var content = $('#message_content').val();
//				if(content == null || "" == content){
//					comm.error_alert(comm.lang("messageCenter")[32702]);
//					return;
//				}
//				var postData = {
//						msgId : $("#msgId").val(),
//						title : $("#ckxx_msgTitle").val(),						//消息标题
//						msg : content,					//消息内容
//						type : $("input[name='msgType']:checked").val(),	//消息类型
//						level : $("input[name='msgLevel']:checked").val(),	//消息级别
//						sender : '111',										//发送人
//						receiptor : $("#receiveEntValue").val(), 			//接收者
//						entResNo : '12393847362',							//发生企业互生号
//						entCustId : '111',									//方式企业客户号
//						entCustName : 'xxx服务公司' 							//发送企业名称
//				};
//				messageCenter.sendMessage(postData,function(res){
//					$("#subNav_9_03").click();
//				});
//			});
//			$("#receiveEnt").keyup(function(){
//				$("#receiveEntValue").attr("value",$("#receiveEnt").val());
//			});
//		},
//		validateData : function(){
//			return comm.valid({
//				formID : '#ckxx_form',
//				rules : {
//					ckxx_msgTitle : {
//						required : true
//					},
//					receiveEnt : {
//						required : true
//					}
//				},
//				messages : {
//					ckxx_msgTitle : {
//						required : comm.lang("messageCenter")[32701]
//					},
//					receiveEnt : {
//						required : comm.lang("messageCenter")[32703]
//					}
//				}
//			});
//		}
	}
}); 
