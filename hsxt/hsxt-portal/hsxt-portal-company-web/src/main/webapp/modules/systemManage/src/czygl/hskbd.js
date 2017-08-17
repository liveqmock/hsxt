define(['text!systemManageTpl/czygl/bdhsk.html',
        'systemManageDat/systemManage'],function(hskbdTpl,systemManage ){
	return bdhsk =  {
		
		showPage : function(record){
			
			
			
			$('#busibox').html(_.template(hskbdTpl,record)) ;			 
		 	
		 	
		  	$('#btn_bdhsk_cx').triggerWith('#xtyhgl_czygl');
			
			$('#btn_bdhsk_tj').click(function(){
				
				if(!bdhsk.validateData()){
					return;
				}
				var loginPwd = $("#loginPwd").val();	//密码
			 	
				//获取用户登录信息
			 	var loginInfo = comm.getRequestParams();
			 	
			 	//获取动态token加密用户密码
			 	comm.requestFun("getToken", null, function(res){
			 		
			 		//密码加密
			 		loginPwd = comm.encrypt(loginPwd, res.data);
			 		
			 		var postData = {
			 				hsNo : $("#operResNo").val(),	//要绑定的互生卡号
			 				operCustId : record.operCustId,	//操作员客户号
			 				adminCustId : loginInfo.custId,	//操作者（管理员）客户号
			 				resNo : loginInfo.pointNo,		//操作者（管理员）企业互生号
			 				loginPwd : loginPwd,			//操作者（管理员）登录密码
			 				userName : loginInfo.custName,	//操作者（管理员）用户名
			 				randomToken : res.data,			//随机token
			 				custType : loginInfo.entResType //客户类型
			 				
			 		};
			 		
			 		systemManage.bindCard(postData,function(res1){
			 			
			 			$("#userNameTd").text(record.userName);
			 			$("#realNameTd").text(record.realName);
			 			$("#hskhTd").text(postData.hsNo);
			 			$('#hskbd_dialog').dialog({
			 				width:480,
			 				buttons:{
			 					'确定':function(){
			 						
			 						$(this).dialog('destroy');
			 						$('#xtyhgl_czygl').click();
			 					}
			 				}
			 			});
			 			
			 		});
			 	});
				
			});
			
		},
		validateData : function(){
			return comm.valid({
				formID : '#hskbdForm',
				rules : {
					operResNo:{
						required : true,
						hsCardNo : true
					},
					operResNo2:{
						required : true,
						equalTo : '#operResNo'
					},
					loginPwd:{
						required : true,
						passwordLogin : true
					}
				},
				messages : {
					operResNo:{
						required : comm.lang("systemManage")[33316],
						hsCardNo : comm.lang("systemManage")[33320]
					},
					operResNo2:{
						required : comm.lang("systemManage")[160316],
						equalTo : comm.lang("systemManage")[160315]
					},
					loginPwd:{
						required : comm.lang("systemManage")[33304],
						passwordLogin : comm.lang("systemManage").passwordLogin
					}
				}
			});
		}
	}
}); 

 