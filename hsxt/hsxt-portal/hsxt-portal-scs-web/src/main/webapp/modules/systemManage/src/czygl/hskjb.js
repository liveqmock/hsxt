define(['text!systemManageTpl/czygl/hskjb.html',
        'systemManageDat/systemManage'],function(hskjbTpl,systemManage){
	var hskjbPage = {
		 
		showPage : function(record){
			$('#busibox').html(_.template(hskjbTpl,record)) ;			 
			//Todo...
		 	
		 	$('#hskjb_qx').triggerWith('#xtyhgl_czygl');
		 
			
			$('#hskjb_tj').click(function(){
				if (!hskjbPage.validateUnbindHSKData()) {
					return;
				}
				
				//获取用户登录信息
			 	var loginInfo = comm.getRequestParams();
			 	
				//获取token
				comm.getToken(function(tokenRes){
					
					//密码加密
			 		loginPwd = comm.encrypt($("#hskjbPwd").val(), tokenRes.data);
					var postData = {
			 				operCustId : record.operCustId,	//操作员客户号
			 				resNo : loginInfo.pointNo,		//操作者（管理员）企业互生号
			 				loginPwd : loginPwd,			//操作者（管理员）登录密码
			 				userName : loginInfo.custName,	//操作者（管理员）用户名
			 				randomToken : tokenRes.data,			//随机token
			 				custType : loginInfo.entResType //客户类型
			 				
			 		};
						//解绑互生卡
					systemManage.unBindCard(postData,function(res){
							$('#hskjb_dialog').dialog({
								width:480,
								buttons:{
									'确定':function(){
										
										//if(self._searchTable) self._searchTable.refreshPage();
										$(this).dialog("destroy");
										$('#xtyhgl_czygl').click()
										
									}
								}
							});
						});
				});
				
				
				
			});
			
			
		},
		validateUnbindHSKData : function(){
			return comm.valid({
				formID : '#jbhsk_form',
				rules : {
					hskjbPwd:{
						required : true
					}
				},
				messages : {
					hskjbPwd:{
						required : comm.lang("systemManage").managerPwd
					}
				}
			});
		}
	};
	return hskjbPage
}); 

 