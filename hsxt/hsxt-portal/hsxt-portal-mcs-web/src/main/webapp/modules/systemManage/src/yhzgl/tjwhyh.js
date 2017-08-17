define(['text!systemManageTpl/yhzgl/tjwhyh.html' ,
	    'text!systemManageTpl/yhzgl/tjyh.html',
	    'systemManageDat/systemManage'],function(tjwhyhTpl,tjyhTpl,systemManage){
	var tywhyhPage = {
		 
		gridObj : null,
		globObj : null,
		havopers : null,
		showPage : function(obj){
			
			
			globObj = obj;
			
			//用户组已有的用户
			havopers = obj.opers;
			
			$('#busibox').html(_.template(tjwhyhTpl)) ;	
			
			//获取用户登录信息
		 	var loginInfo = comm.getRequestParams();
		 	 
		 	 //添加用户
		 	$('#btn_tjyh').click(function(){
	 
		 		//查询所有用户
		 		systemManage.findOperatorList({entCustId : loginInfo.entCustId},function(res){
		 			
		 			var data = res;
		 			data.hav = havopers;
		 			
		 			$('#tjyh_dialog >p').html( _.template( tjyhTpl ,data));
		 			$('#tjyh_dialog').dialog({
		 				title:'提示',
		 				width:680,
		 				buttons:{
		 					'确定':function(){
		 						comm.confirm({
		 							imgFlag:true,
		 							width:480,
		 							content: comm.lang("systemManage").confirAddUser,
		 							callOk:function(){
		 								
		 								//获取登录信息
		 								var param = comm.getRequestParams();
		 								//拼接操作员客户id
		 								var userid = "";
		 								$("input[name='userid']:checked").each(function(e){
		 									userid = userid + $(this).val() + ",";
		 								});
		 								userid = userid.substring(0,userid.length - 1);
		 								
		 								var win = $(this);
		 								
		 								if(userid == ""){
		 									comm.error_alert(comm.lang("systemManage").nullUserCustId);
		 								}else{
		 									systemManage.addGroupUser({
		 										adminCustId : param.custId,	//操作人客户号
		 										operCustId : userid,		//操作员卡户好
		 										groupId : obj.groupId		//用户组编号
		 									},function(res){
		 										
		 										//将js中用户组已有的用户改为刚选择保存的用户
		 										havopers = [];
		 										var userids = userid.split(",");
		 										for(var i = 0; i < userids.length; i++){
		 											var hh = {};
		 											hh.operCustId = userids[i];
		 											havopers[havopers.length] = hh;
		 										}
		 										
		 										if(gridObj){
		 											gridObj.refreshPage();
		 										}
		 										win.dialog('destroy');
		 									})
		 								}
		 							}.bind(this) ,
		 							callCancel:function(){							 		
		 								
		 							}.bind(this)
		 							
		 						});								 
		 						
		 					},
		 					'取消':function(){
		 						
		 						$(this).dialog('destroy');
		 					} 
		 				}
		 			});	
		 			
		 			$("#userids").click(function(){
		 				if($(this).prop("checked")){
		 					
		 					 $("[name = 'userid']:checkbox").attr("checked", true);
		 					 
		 				}else{
		 					
		 					$("[name = 'userid']:checkbox").attr("checked", false);
		 					
		 				}
		 			})
		 		});
		 	});
		 	 
		 	 
		 	 
		 	 
		 	 
			
			var params = {
					entCustId : 	obj.entCustId, 		//企业客户号
					groupName : obj.groupName
			};
			gridObj = comm.getCommBsGrid("detailTable","userlist_entgroup",params,comm.lang("systemManage"),tywhyhPage.del);
			
			
				
		},
		del : function(record, rowIndex, colIndex, options){
  			var link1 =  $('<a >'+comm.lang("systemManage").remove+'</a>').click(function(e) {							
				//alert(gridObj.getRecordIndexValue(record, 'xh'));
				var confirmObj = {	
					imgFlag:true,
					width:400,
					
					content : comm.lang("systemManage").confirRemoveUser,	
					 													
					callOk :function(){
						//获取登录信息
						var param = comm.getRequestParams();
							//删除记录
							//gridObj.deleteRow( gridObj.getSelectedRowIndex() );
						systemManage.delGroupUser({
							adminCustId : param.custId,  //操作人客户号
							operCustId : record.operCustId,		//操作员客户号
							groupId : globObj.groupId		//用户组编号
						},function(res){
							if(gridObj)
							{
								gridObj.refreshPage();
							}
						});
					}
				 }
				 comm.confirm(confirmObj);
				
			});
			return   link1 ;
  		}
	};
	return tywhyhPage
}); 
