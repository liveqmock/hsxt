define(['text!systemManageTpl/yhzgl/xgyhz.html',
        'systemManageDat/systemManage'],function(xgyhzTpl,systemManage ){
	var xgyhzPage = {
		 
		showPage : function(){
			
			
			$('#busibox').html(_.template(xgyhzTpl)) ;			 
			
			$('#yhzgl_xgyhz_qr').click(function(){
				
				if(!xgyhzPage.validateData()){
					return;
				}
				
				var param = comm.getRequestParams(param);
				var postData = {
						groupName : $("#groupName").val().replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, ''),
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
						$('#xtyhgl_yhzgl').click();
					});
				}
				//新增
				else
				{
					postData.entCustId = param.entCustId;
					
					systemManage.addEntGroup(postData,function(res){
						
						$('#yhzgl_xgyhz_dialog').dialog({
							width:680,
							buttons:{
								'完成':function(){
									$('#xtyhgl_yhzgl').click();
									$(this).dialog('destroy');
								},
								'添加用户组成员' : function(){
									
									var record = {
											
											groupId : res.data,
											entCustId : param.entCustId,
											groupName : postData.groupName
											
									};
									require(['systemManageSrc/yhzgl/tjwhyh'], function(tab){
				  						
				  						$('#xtyhgl_tjwhzy').click();
				  						
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
				
		},
  		validateData : function(){
			return comm.valid({
				formID : '#groupForm',
				rules : {
					groupName:{
						required : true,
						maxlength : 15
					},
					groupDesc:{
						maxlength : 300
					}
				},
				messages : {
					groupName:{
						required : comm.lang("systemManage")[33308],
						maxlength : comm.lang("systemManage")[33316]
					},
					groupDesc:{
						maxlength : comm.lang("systemManage")[33317]
					}
				}
			});
		}
	};
	return xgyhzPage
}); 
