define(['text!systemManageTpl/czygl/xzczy.html',
        'text!systemManageTpl/czygl/xzczy_dialog.html',
        'systemManageDat/systemManage'],function(xzczyTpl,xzczy_dialogTpl,systemManage ){
	return xzczyPage ={
			roles : null,
			rolesGrid:null,
		showPage : function(){
			
			//保存已选角色
			roles = new Array();
			
			$('#busibox').html(_.template(xzczyTpl)) ;			 
		 	
			comm.initSelect("#accountStatus",comm.lang("systemManage").accountStatus);
			
			//获取用户登录信息
		 	var loginInfo = comm.getRequestParams(loginInfo);
		 	
			//初始化角色列表
		 	rolesGrid = comm.getCommBsGrid("roleTable","list_role",{
				
				 search_entCustId:loginInfo.entCustId,		//企业客户号
				 
				 search_custType : loginInfo.entResType,   //客户类型
				 
				 search_platformCode : comm.lang("systemManage").platformCode,	//平台
				 
				 search_subSystemCode : comm.lang("systemManage").subSystemCode	//子系统
				 
				},xzczyPage.roleDetail);
		 	
		 	
			$('#xzczy_tj').click(function(){
				if(!xzczyPage.validateData()){
					return;
				}
				
				if(!xzczyPage.pwdFormatChk()){
					return;
				}
				
				//获取用户登录信息
			 	var loginInfo = comm.getRequestParams();
			 	
			 	var loginPwd = $("#loginPwd").val();	//密码
			 	
			 	//获取动态token加密用户密码
			 	comm.requestFun("getToken", null, function(res){
			 		
			 		//密码加密
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
			 				accountStatus : $("#accountStatus").attr("optionvalue"),
			 				mobile : $("#mobile").val(),
			 				
			 		};
			 		
			 		//添加操作员
			 		systemManage.addOperator(postData,function(res1){
			 			
//			 			$('#xzczy_dialog').html(_.template(xzczy_dialogTpl));
//			 			
//			 			//初始化角色列表
//			 			roles = comm.getCommBsGrid("roleTable","list_role",{
//			 				
//			 				search_entCustId:loginInfo.entCustId,
//			 				
//			 				search_platformCode : comm.lang("systemManage").platformCode,	//平台编码
//							
//			 				search_subSystemCode : comm.lang("systemManage").subSystemCode,	//子系统编码
//							
////							roleType : comm.lang("systemManage").roleType,
//							
//			 				search_custType : loginInfo.entResType
//			 				},comm.lang("systemManage"));
			 			
			 			$('#xzczy_dialog').dialog({
			 				width:680,
			 				buttons:{
			 					'下次再说':function(){
			 						
			 						$(this).dialog('destroy');
			 						$('#xtyhgl_czygl').click();
			 					},
			 					'立即设置':function(){

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
			
			
			$("#btn_xzxgczy_cx").click(function(){
				$('#xtyhgl_czygl').click();
			});
			
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
				formID : '#xzczyForm',
				rules : {
					userName:{
						required : true,
						username : true
					},
					loginPwd:{
						required : true,
						passwordLogin : true
					},
					loginPwd2:{
						required : true,
						passwordLogin : true,
						equalTo : "#loginPwd"
					},
					mobile : {
						required : true,
						telphone : true
					},
					realName : {
						required : true,
						maxlength:20
					},
					accountStatus : {
						required : true
					},
					remark:{
						maxlength:100
					},
					operDuty:{
						maxlength:20
					}
				},
				messages : {
					userName:{
						required : comm.lang("systemManage")[33303],
						username : comm.lang("systemManage").username
					},
					loginPwd:{
						required : comm.lang("systemManage")[33304],
						passwordLogin : comm.lang("systemManage").passwordLogin
					},
					loginPwd2:{
						required : comm.lang("systemManage")[99904],
						passwordLogin : comm.lang("systemManage").passwordLogin,
						equalTo : comm.lang("systemManage")[99905]
					},
					mobile : {
						required : comm.lang("systemManage")[33317],
						telphone : comm.lang("systemManage")[99918]
					},
					realName : {
						required : comm.lang("systemManage")[33318],
						maxlength:comm.lang("systemManage")[33323]
					},
					accountStatus : {
						required : comm.lang("systemManage")[33319]
					},
					remark:{
						maxlength:comm.lang("systemManage")[33321]
					},
					operDuty:{
						maxlength:comm.lang("systemManage")[33322]
					}
				}
			});
		}
	};
	return xzczyPage
}); 

 