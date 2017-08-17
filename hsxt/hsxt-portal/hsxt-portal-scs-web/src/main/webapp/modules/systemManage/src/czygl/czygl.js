define(['text!systemManageTpl/czygl/czygl.html',
        'systemManageDat/systemManage'],function(czyglTpl,systemManage){
	var czyglPage = {
		gridObj : null,
		showPage : function(){
			
			$('#busibox').html(_.template(czyglTpl)) ;			 
			//Todo...
		 	
		 	//新增操作员
		 	$('#btn_xzczy').triggerWith('#xtyhgl_xzczy');
		 	
		 	//获取用户登录信息
		 	var loginInfo = comm.getRequestParams(loginInfo);
		 	
		 	$("#searchBtn").click(function(){
				
				var params = {
						search_userName : $("#userName").val(),			//用户名
						search_realName : $("#realName").val(),			//真实姓名
						search_entCustId : 	loginInfo.entCustId 		//企业客户号
				};
				gridObj = comm.getCommBsGrid("detailTable","list_operator",params,comm.lang("systemManage"),czyglPage.detail,czyglPage.del,czyglPage.add,czyglPage.edit);
				
			});
		 	
		 	$("#searchBtn").click();
			
		},
		edit : function(record, rowIndex, colIndex, options){
  			var link  = '';			  			
  			
  			if ( colIndex == 6 )
  			{
				if (record.bindResNoStatus == '-1'){
					var alink =  $('<a >'+comm.lang("systemManage").cancelBind+'</a>').click(function(e) {
						
						require(['systemManageSrc/czygl/hskjb'], function(tab){
							
							$('#xtyhgl_hskjb').click();
							
							tab.showPage(record);
							
						});
//						 czyglPage.unBind(record);
					});
					link = $('<span>'+record.operResNo+'&nbsp;&nbsp;['+comm.lang("systemManage").inBind+']&nbsp;&nbsp;</span>').append(alink);
				
				}else if(record.bindResNoStatus == "1"){
					
					var alink =  $('<a >'+comm.lang("systemManage").relieveBind+'</a>').click(function(e) {
						
						require(['systemManageSrc/czygl/hskjb'], function(tab){
							
							$('#xtyhgl_hskjb').click();
							
							tab.showPage(record);
							
						});
//						czyglPage.unBind(record); 
					});
					link = $('<span>'+record.operResNo+'&nbsp;&nbsp;['+comm.lang("systemManage").bind+']&nbsp;&nbsp;</span>').append(alink);
				
				}else {
					link = 	$('<a >'+comm.lang("systemManage").bindCard+'</a>').click(function(e) {
						
						require(['systemManageSrc/czygl/hskbd'], function(tab){
	  						
	  						$('#xtyhgl_hskbd').click();
	  						
	  						tab.showPage(record);
	  						
	  					});
					});
				}				  			
			} 
  			else if (colIndex==7 && !record.admin)
  			{
  				link =  $('<a >'+comm.lang("systemManage").update+'</a>').click(function(e) {							
					
					require(['systemManageSrc/czygl/xgczy'], function(tab){
  						
  						$('#xtyhgl_xgczy').click();
  						
  						tab.showPage(record);
  						
  					});
				});		
  			}
							
			return  link;
  		} ,
  		del : function(record, rowIndex, colIndex, options){
  			
  			var link  = '';			  			
  			
  			if (colIndex == 7 && !record.admin){
	  			link =  $('<a >'+comm.lang("systemManage").del+'</a>').click(function(e) {
	  				var confirmObj = {	
							imgFlag:true,
							width:480,
							content : comm.lang("systemManage").delContent,	
							 													
							callOk :function(){
								var param = comm.getRequestParams()
								systemManage.deleteOperator({
									adminCustId : param.custId,
									operCustId : record.operCustId
								},function(res){
									if(gridObj){
										gridObj.refreshPage();
									}
								});
							}
						 }
					 comm.confirm(confirmObj) ; 
				});
			}

			return   link ;
  		},			  		 
  		detail : function(record, rowIndex, colIndex, options){
  			var link  = '';			  			
  			if(colIndex == 4){
  				var roles = record.roleList;
				var roleName = "";
				var tip = "";
				if(roles && roles.length > 0){
					for(var i = 0; i < roles.length; i++){
						tip = tip + roles[i].roleName + ",";
					}
					tip = tip.substring(0,tip.length - 1);
					
					if(tip.length>20){
						roleName = tip.substring(0,20);
						if(roleName.substring(19) == ","){
							roleName=roleName.substring(0,19);
						}
						roleName=roleName+"....";
					}else{
						roleName = tip;
					}
				}
				return "<span title='"+tip+"'>"+roleName+"</span>";
				
  			}else if(colIndex == 5){
  				return comm.lang("systemManage").accountStatus[record.accountStatus];
  			}
  			else if (colIndex==7 && !record.admin){
  				
  				var link = '';
	  			link  =  $('<a >'+comm.lang("systemManage").grantRole+'</a>').click(function(e) {
					 czyglPage.grantRole(record);		
				});
				return   link ;
				
			}
		},
		add : function(record, rowIndex, colIndex, options){
			if(colIndex == 7 && !record.admin){
	  			var link = '';
	  			
				if(record.accountStatus == "1"){
					
					link=  $('<a >'+comm.lang("systemManage").activity+'</a>').click(function(e) {							
						comm.confirm({
							imgFlag : true,
							imgClass : 'tips_ques',
							content : comm.lang("systemManage").isActivity,
							callOk :function(){
								
								czyglPage.updateOper(record, 0);
							}	
						});						
					});	
				}else{
					link=  $('<a >'+comm.lang("systemManage").unactivity+'</a>').click(function(e) {							
						comm.confirm({
							imgFlag : true,
							imgClass : 'tips_ques',
							content : comm.lang("systemManage").isUnActivity,
							callOk :function(){
								czyglPage.updateOper(record, 1);
							}	
						});						
					});	
				}
				return   link ;
			}
  		},
		unBind : function(record){
			
			var confirmObj = {	
					imgFlag:true,
					width:480,
					content : comm.lang("systemManage").confirCancelBind,	
					 													
					callOk :function(){
						systemManage.unBindCard({
							operCustId : record.operCustId
						},function(res){
							if(gridObj){
								gridObj.refreshPage();
							}
						});
					}
				 }
			 comm.confirm(confirmObj) ; 
			
		},
		//修改状态
		updateOper : function(record,status){
			//获取用户登录信息
		 	var loginInfo = comm.getRequestParams();
		 	
		 	var postData = {
					adminCustId : loginInfo.custId,
					operCustId : record.operCustId,
					accountStatus : status,
					entCustId : record.entCustId,
					entResNo : record.entResNo,
					loginPwd : record.loginPwd,
					userName : record.userName
			};
		 	systemManage.updateOperator(postData,function(res){
		 		if(gridObj){
		 			gridObj.refreshPage();
		 		}
		 	});
		},
		//角色设置
		grantRole : function(obj){
			//角色管理
			$('#jssz_dialog').dialog({width:680,autoOpen:false});
			
			//登录信息
			var loginInfo = comm.getRequestParams();
			
			var params = {
					search_entCustId : loginInfo.entCustId,
					search_custType : loginInfo.entResType,
					search_platformCode : comm.lang("systemManage").platformCode,	//平台
					search_subSystemCode : comm.lang("systemManage").subSystemCode,	//子系统
					search_roleName:""
//					search_roleType : ''		//角色类型
			};
			//已有角色列表
			var hasRole = obj.roleList;
			
			//查询角色列表
			rolesGrid = comm.getCommBsGrid("czygl_rolesTable","list_role",
				params,comm.lang("systemManage"),
					
				function(record, rowIndex, colIndex, options)
				{
					if(colIndex==1)
					{
						if(rowIndex == 0)
						{
							$("#czygl_rolesTable thead input[type='checkbox']").attr("checked",true);
						}
						//选中已选择的用户组
						if(hasRole && hasRole.length > 0)
						{
							for(var i = 0; i < hasRole.length; i++)
							{
								if(hasRole[i].roleId == record.roleId)
								{
									$('#czygl_rolesTable tbody tr:eq('+rowIndex+') td:eq(0)').find("input[type='checkbox']").attr("checked",true);
								}
							}
						}
						
						if(!$('#czygl_rolesTable tbody tr:eq('+rowIndex+') td:eq(0)').find("input[type='checkbox']").is(":checked"))
						{
							$("#czygl_rolesTable thead input[type='checkbox']").attr("checked",false);
						}
						
						return record.roleName;
					}
					
				});
			
			$('#jssz_dialog').dialog({
					width:700,
					height : 500,
					autoOpen:true,
					buttons:{ 
						
						"下次再说":function(){
							//关闭并销毁
							$('#czygl_rolesTable tbody').empty();
							$('#jssz_dialog').dialog('destroy');
							
						},
						"立刻设置":function(){
							
							//获取选中的角色
							var roleIds = "";
							if(hasRole && hasRole.length > 0){
	 							for(var i = 0; i < hasRole.length; i++){
	 								roleIds = roleIds + hasRole[i].roleId + ",";
	 							}
	 						}
	 						roleIds = roleIds.substring(0,roleIds.length - 1);
							
							//提交重置角色
							systemManage.resetRole({
								adminCustId : loginInfo.custId,
								roleIds : roleIds,
								operCustId : obj.operCustId
							},function(res){
								$('#jssz_dialog').dialog('destroy');
								$('#xtyhgl_czygl').click();
							});
						}										
					} 
				});
				
				//捕捉选择角色页面的表头checkbox选中事件，可分页保存选择的角色
				$("#czygl_rolesTable thead input[type='checkbox']").click(function(){
					var flag = $(this).is(':checked');
					
					$("#czygl_rolesTable tbody input[type='checkbox']").each(function(){
						
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
				$("#czygl_rolesTable tbody input[type='checkbox']").live("click",function(){
					
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
			
		}
	};
	return czyglPage
}); 

 