define(['text!systemManageTpl/yhzgl/xgyhz.html',
        'systemManageDat/systemManage'],function(xgyhzTpl,systemManage ){
	return {
		 
		showPage : function(){
			
			$('#busibox').html(_.template(xgyhzTpl)) ;			 
			
			$('#yhzgl_xgyhz_qr').click(function(){
				
				var param = comm.getRequestParams(param);
				var postData = {
						groupName : $("#groupName").val(),
						groupDesc : $("#groupDesc").val(),
						adminCustId : param.custId
				};
				var updateFlag = $("#updateFlag").val();
				//修改
				if(updateFlag == "1")
				{
					postData.entCustId = $("#entCustId").val();
					postData.groupId = $("#groupId").val();
					systemManage.updateEntGroup(postData,function(res){
						
						$('#yhzgl_xgyhz_dialog').dialog({
							width:480,
							buttons:{
								'完成':function(){
									$('#xtyhgl_yhzgl').click();
									$(this).dialog('destroy');
								},
								'添加用户组成员' : function(){
									
									var record = {
											
											groupId : $("#groupId").val(),
											entCustId : param.entCustId,
											groupName : postData.groupName
											
									};
									require(['systemManageSrc/yhzgl/tjwhyh'], function(tab){
				  						
				  						$('#zywh').click();
				  						
				  						tab.showPage(record);
				  						
				  					});
									
									$(this).dialog('destroy');  
								}						
							}
						});
					});
				}
				//新增
				else
				{
					postData.entCustId = param.entCustId;
					
					systemManage.addEntGroup(postData,function(res){
						
						$('#yhzgl_xzyhz_dialog').dialog({
							width:480,
							buttons:{
								'完成':function(){
									$('#xtyhgl_yhzgl').click();
									$(this).dialog('destroy');
								},
								'继续添加用户组':function(){
									$("#groupName").val("");
									$("#groupDesc").val("");
									$(this).dialog('destroy');
								},
								'添加用户组成员' : function(){
									
									var record = {
											
											groupId : res.data,
											entCustId : param.entCustId,
											groupName : postData.groupName
											
									};
									require(['systemManageSrc/yhzgl/tjwhyh'], function(tab){
				  						
				  						$('#tjyhzcy').click();
				  						
				  						tab.showPage(record);
				  						
				  					});
									
									$(this).dialog('destroy');  
								}						
							}
						});
						
					});
					
				}
			});
			
			
			$('#btn_back').triggerWith('#xtyhgl_yhzgl');
				
		}
	}
}); 
