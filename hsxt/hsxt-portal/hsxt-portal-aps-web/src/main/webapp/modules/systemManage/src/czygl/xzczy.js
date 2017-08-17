define(['text!systemManageTpl/czygl/xzczy.html' ,
        'systemManageDat/systemManage'],function(xzczyTpl,systemManage){
	var xzczyPage = {
		 
		groups : null,
		groupsGrid:null,
		roles : null,
		rolesGrid:null,
		showPage : function(){
			
			//保存已选用户组
			groups = new Array();
			//保存已选角色
			roles = new Array();
			
			$('#busibox').html(_.template(xzczyTpl)) ;			 
		 	
			//获取用户登录信息
		 	var loginInfo = comm.getRequestParams(loginInfo);
		 	
		 	//初始化用户组列表
			groupsGrid = comm.getCommBsGrid("groupTable","list_entgroup",{search_entCustId:loginInfo.entCustId},comm.lang("systemManage"),xzczyPage.detail);
			
			//初始化角色列表
			rolesGrid = comm.getCommBsGrid("roleTable","list_role",{
				search_entCustId : loginInfo.entCustId,
				search_custType : loginInfo.entResType,
				search_platformCode : comm.lang("systemManage").platformCode,	//平台
				search_subSystemCode : comm.lang("systemManage").subSystemCode	//子系统
				},comm.lang("systemManage"),xzczyPage.roleDetail);
			
			$('#btn_xzyhz').click(function(){
				$('#xzczy_dialog').dialog({
					width:700,
					height:450,
					buttons:{
						'确定':function(){
							//给操作员选择用户组
							var groupIds = "";
							var groupNames = "";
							if(groups && groups.length > 0){
								for(var i = 0; i < groups.length; i++){
									groupIds = groupIds + groups[i].groupId + ",";
									groupNames = groupNames + groups[i].groupName + ",";
								}
								groupIds = groupIds.substring(0,groupIds.length -1);
								groupNames = groupNames.substring(0,groupNames.length -1);
							}
							$("#groupId").val(groupIds);
							$("#groupName").text(groupNames);
							$(this).dialog('destroy');
						},
						'取消':function(){
							
							$(this).dialog('destroy');
						}
					}
				});
			});
			
			//捕捉选择用户组页面的表头checkbox选中事件，可分页保存选择的用户组
			$("#groupTable thead input[type='checkbox']").live("click",function(){
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
			//捕捉选择用户组页面的checkbox选中事件，可分页保存选择的用户组
			$("#groupTable tbody input[type='checkbox']").live("click",function(){
				
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
			
			//取消
			$("#xz_cancel").click(function(){
				$('#xtyhgl_czygl').click();
			});
			//确认
			$('#xzczy_qr').click(function(){
				
				if(!xzczyPage.validateData()){
					return;
				}
				if(!xzczyPage.pwdFormatChk()){
					return;
				}
				//获取用户登录信息
			 	var loginInfo = comm.getRequestParams(loginInfo);
			 	
			 	var loginPwd = $("#loginPwd").val();	//密码
			 	
			 	//获取动态token加密用户密码
			 	comm.requestFun("findCardHolderToken", null, function(res){
			 		
			 		loginPwd = comm.encrypt(loginPwd, res.data);
			 		
			 		var postData = {
			 				adminCustId : loginInfo.custId,
			 				entCustId : loginInfo.entCustId,  	//企业客户号
			 				entResNo : loginInfo.pointNo,		//企业互生号
			 				userName : $("#userName").val(),	//用户名
			 				realName : $("#realName").val(),	//真实名
			 				operDuty : $("#operDuty").val(),	//职务
			 				loginPwd : loginPwd,				//密码
			 				randomToken : res.data,			//随机token
			 				remark : $("#remark").val(),		//描述
			 				mobile : $("#mobile").val(),		//手机
			 				groupIds : $("#groupId").val(),		//用户组
			 				accountStatus : $("input[name='activity']:checked").val()
			 				
			 		};
			 		//添加操作员
			 		systemManage.addOperator(postData,function(res1){
			 			
			 			$('#xzczy_cg_dialog').dialog({
			 				width:680,
			 				buttons:{
			 					"继续新增操作员":function(){
									$("#userName").val("");
									$("#loginPwd").val("");
									$("#loginPwd2").val("");
									$("#mobile").val("");
									$("#realName").val("");
									$("#operDuty").val("");
									$("#groupId").val("");
									$("#groupName").val("");
									$("#remark").val("");
									$( this ).dialog( "destroy" );
								},
			 				   '下次再说':function(){
			 						
			 						$(this).dialog('destroy');
			 						$('#xtyhgl_czygl').click();
			 					},
			 					'立即设置' : function(){
			 						//给操作员设置角色
			 						var roleIds = "";
			 						if(roles && roles.length > 0){
			 							for(var i = 0; i < roles.length; i++){
			 								roleIds = roleIds + roles[i].roleId + ",";
			 							}
			 						}
			 						roleIds = roleIds.substring(0,roleIds.length - 1);
			 						
			 						var win = $(this);
			 						systemManage.grantRole({
			 							
			 							adminCustId : loginInfo.custId,
			 							
			 							roleIds : roleIds,
			 							
			 							operCustId : res1.data
			 							
			 						},function(res2){
			 							
			 							win.dialog('destroy');
			 							
			 							$('#xtyhgl_czygl').click();
			 						});
			 						
			 					}
			 					
			 				}
			 			});
			 			
			 		});
			 	},comm.lang("systemManage"));
			});
			
			//捕捉选择角色页面的表头checkbox选中事件，可分页保存选择的角色
			$("#roleTable thead input[type='checkbox']").live("click",function(){
				var flag = $(this).is(':checked');
				
				$("#roleTable tbody input[type='checkbox']").each(function(){
					
					var id = $(this).val();
					var name = $(this).parent().next().text();
					//如果是选中，则加入到已选角色中
					if(flag)
					{
						//判断数组中是否已存在改角色
						var tempFlag = true;
						for(var i = 0; i < roles.length; i++){
							if(roles[i].roleId == id){
								tempFlag = false;
								break;
							}
						}
						if(tempFlag){
							var temp = {
									roleId : id,
									roleName : name.replace(/(^\s*)|(\s*$)/g, "")
							};
							roles[roles.length] = temp;
						}
						
					}
					else //否则 ，从已选角色中移除
					{
						var point = -1;
						for(var i = 0; i < roles.length; i++){
							if(roles[i].roleId == id){
								point = i;
								break;
							}
						}
						roles.splice(point,1);
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
					for(var i = 0; i < roles.length; i++){
						if(roles[i].roleId == id){
							tempFlag = false;
							break;
						}
					}
					if(tempFlag){
						var temp = {
								roleId : id,
								roleName : name.replace(/(^\s*)|(\s*$)/g, "")
						};
						roles[roles.length] = temp;
					}
				}
				else //否则 ，从已选角色中移除
				{
					var point = -1;
					for(var i = 0; i < roles.length; i++){
						if(roles[i].roleId == id){
							point = i;
							break;
						}
					}
					roles.splice(point,1);
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
				if(roles && roles.length > 0)
				{
					for(var i = 0; i < roles.length; i++)
					{
						if(roles[i].roleId == record.roleId){
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
		validateData : function(){
			return comm.valid({
				formID : '#xzczyForm',
				rules : {
					userName:{
						required : true,
						username : true
					},
					mobile:{
						telphone:true
					},
					realName:{
						required : true,
						maxlength:20
					},
					operDuty:{
						maxlength:20
					},
					loginPwd:{
						required : true,
						passwordLogin : true
					},
					loginPwd2:{
						required : true,
						equalTo : "#loginPwd"
					}
				},
				messages : {
					userName:{
						required : comm.lang("systemManage")[33303],
						username : comm.lang("systemManage").username
					},
					mobile:{
						telphone:comm.lang("systemManage")[33314]
					},
					realName:{
						required:comm.lang("systemManage")[33317],
						maxlength:comm.lang("systemManage")[33315]
					},
					operDuty:{
						maxlength:comm.lang("systemManage")[33316]
					},
					loginPwd:{
						required : comm.lang("systemManage")[33304],
						passwordLogin : comm.lang("systemManage").passwordLogin
					},
					loginPwd2:{
						required : comm.lang("systemManage")[33324],
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
        }
	};
	return xzczyPage
}); 

 