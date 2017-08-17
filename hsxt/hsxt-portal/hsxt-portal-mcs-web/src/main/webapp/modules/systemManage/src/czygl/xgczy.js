define(['text!systemManageTpl/czygl/xgczy.html',
        'text!systemManageTpl/czygl/czdlmm_dlg.html',
        'systemManageDat/systemManage'],function(xgczyTpl,czdlmm_dlgTpl,systemManage ){
	var xgczyPage = {
		groupBsGrid : null,
		rolesGrid : null,
		groups : null,
		hasRole : null,
		showPage : function(record){
			
			hasRole = record.roleList;
			
			//初始化用户组
			groups = record.groupList;
			var groupIds = "";
			var groupNames = "";
			if(groups && groups.length > 0)
			{
				for(var i = 0; i < groups.length; i++)
				{
					groupIds = groupIds + groups[i].groupId + ",";
					groupNames = groupNames + groups[i].groupName + ",";
				}
			}
			groupIds = groupIds.substring(0,groupIds.length - 1);
			groupNames = groupNames.substring(0,groupNames.length - 1);
			
			record.groupIds = groupIds;
			record.groupNames = groupNames;
			
			$('#busibox').html(_.template(xgczyTpl,record)) ;			 
		 	
			//获取用户登录信息
		 	var loginInfo = comm.getRequestParams(loginInfo);
		 	
			//初始化用户组
			groupBsGrid = comm.getCommBsGrid("groupTable","list_entgroup",{search_entCustId:loginInfo.entCustId},comm.lang("systemManage"),xgczyPage.detail);
			
			var params = {
					search_entCustId:loginInfo.entCustId,	//企业客户号
					 
					 search_custType : loginInfo.entResType,	//客户类型
					 
					 search_platformCode : comm.lang("systemManage").platformCode,	//平台
					 
					 search_subSystemCode : comm.lang("systemManage").subSystemCode	//子系统
			};
			
			//初始化角色列表
			rolesGrid = comm.getCommBsGrid("roleTable","list_role",params,comm.lang("systemManage"),xgczyPage.roleDetail);
			
			$('#xgczy_qx').triggerWith('#xtyhgl_czygl');
			
			//给操作员选择用户组
			$('#btn_xgyhz').click(function(){
				
				$('#xgczy_dialog').dialog({
					width:680,
					height:400,
					buttons:{
						'确定':function(){
						
							var groupIds = "";
							var groupNames = "";
							//给操作员选择用户组
							if(groups && groups.length > 0){
								for(var i = 0; i < groups.length; i++){
									groupIds = groupIds + groups[i].groupId + ",";
									groupNames = groupNames + groups[i].groupName + ",";
								}
								groupIds = groupIds.substring(0,groupIds.length -1);
								groupNames = groupNames.substring(0,groupNames.length -1);
							}
							$("#groupId").val(groupIds);
							$("#groupName").val(groupNames);
							
							$(this).dialog('destroy');
						},
						'取消':function(){
							
							$(this).dialog('destroy');
						}
					}
				});
				
				//捕捉选择用户组页面的checkbox选中事件，可分页保存选择的用户组
				$("#groupTable tbody input[type='checkbox']").click(function(){
					
					var id = $(this).val();
					var name = $(this).parent().next().text();
					//如果是选中，则加入到已选用户组中
					if($(this).is(':checked'))
					{
						//判断数组中是否已存在改用户组
						var tempFlag = true;
						for(var i = 0; i < groups.length; i++){
							if(groups[i].groupId == id){
								tempFlag = false;
								break;
							}
						}
						if(tempFlag){
							var temp = {
									groupId : id,
									groupName : name.replace(/(^\s*)|(\s*$)/g, "")
							};
							groups[groups.length] = temp;
						}
					}
					else //否则 ，从已选用户组中移除
					{
						var point = -1;
						for(var i = 0; i < groups.length; i++){
							if(groups[i].groupId == id){
								point = i;
								break;
							}
						}
						groups.splice(point,1);
					}
				});
				
			});
			
			//捕捉选择用户组页面的表头checkbox选中事件，可分页保存选择的用户组
			$("#groupTable thead input[type='checkbox']").click(function(){
				var flag = $(this).is(':checked');
				
				$("#groupTable tbody input[type='checkbox']").each(function(){
					
					var id = $(this).val();
					var name = $(this).parent().next().text();
					//如果是选中，则加入到已选用户组中
					if(flag)
					{
						//判断数组中是否已存在改用户组
						var tempFlag = true;
						for(var i = 0; i < groups.length; i++){
							if(groups[i].groupId == id){
								tempFlag = false;
								break;
							}
						}
						if(tempFlag){
							var temp = {
									groupId : id,
									groupName : name.replace(/(^\s*)|(\s*$)/g, "")
							};
							groups[groups.length] = temp;
						}
						
					}
					else //否则 ，从已选用户组中移除
					{
						var point = -1;
						for(var i = 0; i < groups.length; i++){
							if(groups[i].groupId == id){
								point = i;
								break;
							}
						}
						groups.splice(point,1);
					}
				});
				
			});
			
			//修改密码
			$('#czxmm_btn').click(function(){
				/*弹出框*/
				$( "#czdlmm_dlg" ).html(czdlmm_dlgTpl).dialog({
					title : comm.lang("systemManage").resetPwdTitle,
					width:"380",
					height:"200",
					modal:true,
					buttons:{ 
						"确定":function(){
							if(!xgczyPage.validateDataPwd()){
								return;
							}
							if(!xgczyPage.pwdFormatChk()){
								return;
							}
							var loginPwd = $("#loginPwd").val();	//密码
							//获取动态token加密用户密码
							comm.requestFun("getToken", null, function(res){
					 			
					 			loginPwd = comm.encrypt(loginPwd, res.data);
					 			
					 			//获取用户登录信息
							 	var loginInfo = comm.getRequestParams();
							 	var postData = {
					 					operCustId : record.operCustId,
					 					adminCustId : loginInfo.custId,
						 				userName : record.userName,	//用户名
						 				realName : record.realName,	//真实名
						 				operDuty : record.operDuty,	//职务
						 				loginPwd : loginPwd,				//密码
						 				randomToken : res.data,			//随机token
						 				remark : record.remark,		//描述
						 				mobile : record.mobile,		//手机
						 				accountStatus:record.accountStatus//状态
						 				
					 			};
							 	//提交修改
					 			systemManage.updateOperator(postData,function(res1){
					 				comm.i_alert(comm.lang("systemManage").resetPwdSuccess);
					 				$('#czdlmm_dlg').dialog('destroy');	
					 			});
					 			
					 		});
							
						},
						"取消":function(){
							$('#czdlmm_dlg').dialog('destroy');	
						}
					}
				});
			});
			
			//确认
			$('#czygl_xgczy_qr').click(function(){
				
				if(!xgczyPage.validateData()){
					return;
				}
				//获取用户登录信息
			 	var loginInfo = comm.getRequestParams();
			 	
			 	var loginPwd = $("#loginPwd").val();	//密码
			 	
		 		var postData = {
	 					adminCustId : loginInfo.custId,
	 					operCustId : record.operCustId,		//客户号
	 					userName : $("#userName").val(),	//用户名
	 					realName : $("#realName").val(),	//真实名
	 					operDuty : $("#operDuty").val(),	//职务
	 					remark : $("#remark").val(),		//描述
	 					mobile : $("#mobile").val(),		//手机
	 					groupIds : $("#groupId").val(),		//用户组
	 					accountStatus : $("input[name='activity']:checked").val()
	 					
	 			};
		 			
		 		//提交修改
	 			systemManage.updateOperator(postData,function(res1){
	 				
	 				$('#xzczy_cg_dialog').dialog({
	 					width:680,
	 					buttons:{
	 						'下次再说':function(){
	 							
	 							$(this).dialog('destroy');
	 							$('#xtyhgl_czygl').click();
	 						},
	 						'立即设置' : function(){
	 							//给操作员设置角色
	 							var roleIds = "";
		 						if(hasRole && hasRole.length > 0){
		 							for(var i = 0; i < hasRole.length; i++){
		 								roleIds = roleIds + hasRole[i].roleId + ",";
		 							}
		 						}
		 						roleIds = roleIds.substring(0,roleIds.length - 1);
	 							
	 							var win = $(this);
	 							systemManage.resetRole({
	 								
	 								adminCustId : loginInfo.custId,
	 								
	 								roleIds : roleIds,
	 								
	 								operCustId : record.operCustId
	 								
	 							},function(res2){
	 								
	 								win.dialog('destroy');
	 								
	 								$('#xtyhgl_czygl').click();
	 							});
	 						}
	 					}
	 				});
	 				
	 			});
			});
			
			//捕捉选择角色页面的表头checkbox选中事件，可分页保存选择的角色
			$("#roleTable thead input[type='checkbox']").click(function(){
				var flag = $(this).is(':checked');
				
				$("#roleTable tbody input[type='checkbox']").each(function(){
					
					var id = $(this).val();
					var name = $(this).parent().next().text();
					//如果是选中，则加入到已选角色中
					if(flag)
					{
						//判断数组中是否已存在改角色
						var tempFlag = true;
						for(var i = 0; i < hasRole.length; i++){
							if(hasRole[i].roleId == id){
								tempFlag = false;
								break;
							}
						}
						if(tempFlag){
							var temp = {
									roleId : id,
									roleName : name.replace(/(^\s*)|(\s*$)/g, "")
							};
							hasRole[hasRole.length] = temp;
						}
						
					}
					else //否则 ，从已选角色中移除
					{
						var point = -1;
						for(var i = 0; i < hasRole.length; i++){
							if(hasRole[i].roleId == id){
								point = i;
								break;
							}
						}
						hasRole.splice(point,1);
					}
				});
				
			});
			//捕捉选择角色页面的checkbox选中事件，可分页保存选择的角色
			$("#roleTable tbody input[type='checkbox']").live("click",function(){
				
				var id = $(this).val();
				var name = $(this).parent().next().text();
				//如果是选中，则加入到已选角色中
				if($(this).is(':checked'))
				{
					//判断数组中是否已存在改角色
					var tempFlag = true;
					for(var i = 0; i < hasRole.length; i++){
						if(hasRole[i].roleId == id){
							tempFlag = false;
							break;
						}
					}
					if(tempFlag){
						var temp = {
								roleId : id,
								roleName : name.replace(/(^\s*)|(\s*$)/g, "")
						};
						hasRole[hasRole.length] = temp;
					}
				}
				else //否则 ，从已选角色中移除
				{
					var point = -1;
					for(var i = 0; i < hasRole.length; i++){
						if(hasRole[i].roleId == id){
							point = i;
							break;
						}
					}
					hasRole.splice(point,1);
				}
			});
		},
		
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex==1){
				if(rowIndex == 0){
					$("#groupTable thead input[type='checkbox']").attr("checked",true);
				}
				//选中已选择的用户组
				if(groups && groups.length > 0)
				{
					for(var i = 0; i < groups.length; i++)
					{
						if(groups[i].groupId == record.groupId){
							$('#groupTable tbody tr:eq('+rowIndex+') td:eq(0)').find("input[type='checkbox']").attr("checked",true);
						}
					}
				}
				if(!$('#groupTable tbody tr:eq('+rowIndex+') td:eq(0)').find("input[type='checkbox']").is(":checked")){
					$("#groupTable thead input[type='checkbox']").attr("checked",false);
				}
				return record.groupName;
			}
			
		},
		roleDetail : function(record, rowIndex, colIndex, options){
			if(colIndex==1){
				if(rowIndex == 0){
					$("#roleTable thead input[type='checkbox']").attr("checked",true);
				}
				//选中已选择的用户组
				if(hasRole && hasRole.length > 0)
				{
					for(var i = 0; i < hasRole.length; i++)
					{
						if(hasRole[i].roleId == record.roleId){
							$('#roleTable tbody tr:eq('+rowIndex+') td:eq(0)').find("input[type='checkbox']").attr("checked",true);
						}
					}
				}
				
				if(!$('#roleTable tbody tr:eq('+rowIndex+') td:eq(0)').find("input[type='checkbox']").is(":checked")){
					$("#roleTable thead input[type='checkbox']").attr("checked",false);
				}
				
				return record.roleName;
			}
			
		},
		validateDataPwd:function(){
			return comm.valid({
				formID : '#xgczypwdForm',
				rules : {
					loginPwd:{
						required : true,
						passwordLogin : true
					},
					loginPwd2:{
						required : true,
						passwordLogin : true,
						equalTo : "#loginPwd"
					}
				},
				messages : {
					loginPwd:{
						required : comm.lang("systemManage")[33304],
						passwordLogin : comm.lang("systemManage").passwordLogin
					},
					loginPwd2:{
						required : comm.lang("systemManage")[33324],
						passwordLogin : comm.lang("systemManage").passwordLogin,
						equalTo : comm.lang("systemManage")[33325]
					}
				}
			});
		},
		
		//密码格式验证
        pwdFormatChk:function(){
        	
            /** 密码数据格式验证 */
            var $inputNewTradePwd=$("#loginPwd");
            var $tradePwdLen=$inputNewTradePwd.attr("maxlength")-1;//密码长度
            var $newTradePwd=$inputNewTradePwd.val();        //密码内容
            
            //匹配6位顺增或顺降  
            eval("var increaseReg =/^(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"+$tradePwdLen+"}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"+$tradePwdLen+"})\\d$/;"); 
            if(increaseReg.test($newTradePwd)){
                comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("systemManage").pwd_increase_error });
                return false;
            }
          
	        //匹配6位重复数字  
	        eval("var repeatedReg =/^([\\d])\\1{"+$tradePwdLen+"}$/;"); 
	        if(repeatedReg.test($newTradePwd)){
	                comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("systemManage").pwd_number_repeated_error });
	                return false;
	        }
	        return true;
        },
        
		validateData : function(){
			return comm.valid({
				formID : '#xgczyForm',
				rules : {
					userName:{
						required : true,
						username : true
					},
					realName:{
						required : true,
						maxlength : 20
					},
					operDuty:{
						maxlength : 10
					},
					mobile:{
						mobileNo : true
					},
					remark:{
						maxlength : 100
					}
				},
				messages : {
					userName:{
						required : comm.lang("systemManage")[33303],
						username : comm.lang("systemManage").username
					},
					realName:{
						required : comm.lang("systemManage").usernamebt,
						maxlength : comm.lang("systemManage")[33318]
					},
					operDuty:{
						maxlength : comm.lang("systemManage")[33319]
					},
					mobile:{
						mobileNo : comm.lang("systemManage")[33321]
					},
					remark:{
						maxlength : comm.lang("systemManage")[33320]
					}
				}
			});
		}
	};
	return xgczyPage
}); 

 