 define(['text!messageCenterTpl/fsxx/xxcg.html',
 		'text!messageCenterTpl/fsxx/xgxx.html'
		,"text!messageCenterTpl/fsxx/dialog.html",
		"text!messageCenterTpl/fsxx/fsxx_yl.html",
		'messageCenterSrc/fsxx/xgxx',
		'messageCenterDat/messageCenter',
		'xheditor_cn'
		],function(xxcgTpl, xgxxTpl, dialogTpl, fsxx_ylTpl, xgxx, messageCenter){
	var xxcgPage =  {
		//searchTable : null,
		gridObj:null,
		showPage : function(){
			$('#busibox').html(_.template(xxcgTpl)).append(dialogTpl);			 

		 	$('#new_btn').triggerWith('#fsxx');
			
			$("#qry_msg_btn").click(function(){
				var params = {
						search_msgTitle : $("#msgTitle").val(),	//消息标题
						search_status : 1
				};
				xxcgPage.gridObj =comm.getCommBsGrid("detailTable","find_message_list",params,comm.lang("messageCenter"),xxcgPage.detail,xxcgPage.del);
			});
			
			$("#del_btn").click(function(){
				var msgIds =xxcgPage.gridObj.getCheckedValues("msgId");
				if(msgIds.length<1){
					comm.i_alert(comm.lang("messageCenter").selectDelMsg);
					return;
				}
				var params = {
						msgId : escape(JSON.stringify(msgIds)),	//消息ID
						status : 1
				};
				comm.i_confirm(comm.lang("messageCenter").confirmMessageCenterDelete, function(){
					//删除	
					messageCenter.delMessages(params,function(res){
						/*
						//刷新列表
						if(searchTable){
							searchTable.refreshPage();
						}
						*/
						$("#qry_msg_btn").click();
					});
				}, 320);
			});
			
			$("#qry_msg_btn").click();
		},
		detail : function(record, rowIndex, colIndex, options){
			//console.log("===============");
			if(colIndex == 0){
				return rowIndex + 1;
			}else if(colIndex == 1){
				var receiptors = record.receiptor.split(",");
				var result ="";
				for(var i=0;i<receiptors.length;i++){
					if(receiptors[i]){
						var _rec = comm.lang("messageCenter").receiptor[receiptors[i]];
						if(!_rec){
							_rec = receiptors[i];
						}
						result += _rec+",";
					}
				}
				if(result != '' && result != undefined){
					result = result.substring(0,result.length-1);
					return "<span title='"+result+"'>" + result + "</span>";
				}else{
					return "<span title='"+record.receiptor+"'>" + record.receiptor + "</span>";
				}
			}else if(colIndex == 2){
				return comm.lang("messageCenter").msglevel[record.level];
			}else if(colIndex == 4){
				if(record.updatedDate!=null&&record.updatedDate!=''){
					return record.updatedDate;
				}
				return record.createdDate;
			}
			if(colIndex == 5){
				
				var link1 =  $('<a >'+comm.lang("messageCenter").xiugai+'</a>').click(function(e) {
					var postData = record;
					//获取接收信息的单位的中文名称
					var receiptors = record.receiptor.split(",");
					var result ="";
					for(var i=0;i<receiptors.length;i++){
						if(receiptors[i]){
							var receiptor = comm.lang("messageCenter").receiptor[receiptors[i]];
							if(undefined==receiptor){
								receiptor = receiptors[i];
							}
							result += receiptor+",";
						}
					}
					result=result.substring(0,result.length-1) ;
					postData.receiptorName = result;
					//重要级别
					postData.levelName = comm.lang("messageCenter").msglevel[record.level];
					//消息类型
					postData.typeName = comm.lang("messageCenter").msgtype[record.type];
					postData.summary = record.summary;
					//显示详细信息页面
					xgxx.showPage(postData);
				});
				return   link1 ;
			}
		},
		del : function(record,rowIndex,colIndex,options){
			if(colIndex == 5)
			{
				var link1 =  $('<a >'+comm.lang("messageCenter").del+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("messageCenter").confirmMessageCenterDelete, function(){
						var postData = {
								msgId : record.msgId
							};
						//删除	
						messageCenter.delMessageById(postData,function(res){
							/*
							//刷新列表
							if(searchTable){
								searchTable.refreshPage();
							}
							*/
							$("#qry_msg_btn").click();
						});
					}, 320);
				});
				return   link1 ;
			}
		},
		xiuGai : function(obj){
			var that = this;
			that.showTpl($('#xgxxTpl'));
			comm.liActive_add($('#xg'));
			$('#xgxxTpl').html(_.template(xgxxTpl, obj));
			
			/*富文本框*/
			$('#Message_content_xg').xheditor({
				tools:"Cut,Copy,Paste,Pastetext,|,Blocktag,FontfaceFontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Hr,Table,|,Source,Preview,Fullscreen",
				upLinkExt:"zip,rar,txt",
				upImgExt:"jpg,jpeg,gif,png",
				upFlashExt:"swf",
				upMediaExt:"wmv,avi,wma,mp3,mid",
				width:678,
				height:150
			});	
			/*end*/
			
			$('#addmsg_xg').click(function(){
				
				/*弹出框*/
				$( "#dialogTpl" ).dialog({
					title:"选择接收单位",
					width:"300",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( "#dialogTpl" ).dialog( "destroy" );
							
							var checkValue = '';
							
							if(!($('#receivingUnit_1').hasClass('none'))){
								$('#receivingUnit_1').find('input:checked').each(function(){
									checkValue += $(this).val() + ', ';
								});					
							}
							else if(!($('#receivingUnit_2').hasClass('none'))){
								$('#receivingUnit_2').find('input:checked').each(function(){
									checkValue += $(this).val() + ', ';
								});		
							}
							$('#receiveCompany_xg').val(checkValue);
							
						},
						"取消":function(){
							$( "#dialogTpl" ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
				
				/*消息类型单选框事件*/
				$("input[name = msgType_xg]").click(function(e){
					var currentValue = $(e.currentTarget).val();
					var receivingUnit_1 = $("#receivingUnit_1");
					var receivingUnit_2 = $("#receivingUnit_2");
					if(currentValue == 1){
						receivingUnit_1.removeClass("none");	
						receivingUnit_2.addClass("none");
					}
					else if(currentValue == 2){
						receivingUnit_2.removeClass("none");
						receivingUnit_1.addClass("none");	
					}
				});
				/*end*/
			
			});
			
			$('#back_xxcg_btn').click(function(){
				that.showTpl($('#xxcgTpl'));
				comm.liActive($('#xxcg'), '#xg, #yl');
					
			});
			
			$('#xxcg_yl_btn').click(function(){
				$('#xxcg_ylTpl').html(fsxx_ylTpl);
				that.showTpl($('#xxcg_ylTpl'));
				comm.liActive_add($('#yl'));
				
				$('#yl_back_btn').click(function(){
					that.showTpl($('#xgxxTpl'));
					comm.liActive_add($('#xg'));
					$('#yl').addClass('tabNone');	
				});
				
			});
			
		},
		showTpl : function(tplObj){
			$('#xxcgTpl, #xgxxTpl, #xxcg_ylTpl').addClass('none');
			tplObj.removeClass('none');
		}
	};
	
	return xxcgPage;
}); 

 