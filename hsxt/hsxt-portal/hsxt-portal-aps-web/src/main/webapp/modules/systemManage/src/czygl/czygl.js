define(['text!systemManageTpl/czygl/czygl.html',
        'systemManageDat/systemManage'],function(czyglTpl,systemManage ){
	
	var czyglPage = {
		gridObj : null,
		showPage : function(){
			
			$('#busibox').html(_.template(czyglTpl)) ;			 
		 	
		 	//新增操作员
		 	$('#btn_xzczy').triggerWith('#xtyhgl_xzczy');
		 	
		 	//获取用户登录信息
		 	var loginInfo = comm.getRequestParams(loginInfo);
		 	
		 	$("#searchBtn").click(function(){
		 		czyglPage.query(loginInfo);
			});
		 	
		 	$("#searchBtn").click();
		 	
		},
		query:function(loginInfo){
			var params = {
					search_userName : $("#userName").val(),			//用户名
					search_realName : $("#realName").val(),			//真实姓名
					search_entCustId : 	loginInfo.entCustId 		//企业客户号
			};
			gridObj = comm.getCommBsGrid("detailTable","list_operator",params,comm.lang("systemManage"),czyglPage.detail,czyglPage.del,czyglPage.edit,czyglPage.add);
		},
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				var roles = record.roleList;
				var roleName = "";
				if(roles && roles.length > 0){
					for(var i = 0; i < roles.length; i++){
						roleName = roleName + roles[i].roleName + ",";
					}
					roleName = roleName.substring(0,roleName.length - 1);
				}
				return "<span title='"+roleName+"'>" + roleName + "</span>";
			}else if(colIndex == 7 && !record.admin){
				
				var link = '';
				
				link=  $('<a >'+comm.lang("systemManage").update+'</a>').click(function(e) {							
					
					require(['systemManageSrc/czygl/xgczy'], function(tab){
  						
  						$('#xtyhgl_xgczy').click();
  						
  						tab.showPage(record);
  						
  					});
					
				});
						  				
				return   link ;
			}
  		} ,
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
  		} ,
  		del : function(record, rowIndex, colIndex, options){
  			if(colIndex == 7 && !record.admin && record.userName != comm.getRequestParams().custName){
	  			var link = '';
	  				link =  $('<a >'+comm.lang("systemManage").del+'</a>').click(function(e) {
						//$('#dialog').dialog({autoOpen: true }) ;
						var confirmObj = {	
							imgFlag:true,
							width:480,
							content : comm.lang("systemManage").delContent + "<font color='red'>"+record.userName + "</font>," + comm.lang("systemManage").delUserName +"<font color='red'>"+record.realName + "</font>" +comm.lang("systemManage").delLaie,	
							 													
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
						 comm.confirm(confirmObj);  
					});
				return   link ;
  			}
  		},			  		 
  		detail : function(record, rowIndex, colIndex, options){
  			if(colIndex == 5){
  				var groups = record.groupList;
  				var groupNames = "";
  				if(groups && groups.length > 0){
  					for(var i = 0; i < groups.length ; i++){
  						groupNames = groupNames + groups[i].groupName + ",";
  					}
  					groupNames = groupNames.substring(0,groupNames.length - 1);
  				}
  				return "<span title='"+groupNames+"' >" + groupNames + "</span>";
  			}else if(colIndex == 6){
  				return comm.lang("systemManage").accountStatus[record.accountStatus]; 
  			}else if(colIndex == 7 && !record.admin){
	  			var link = '';
	  			link  =  $('<a >'+comm.lang("systemManage").grantRole+'</a>').click(function(e) {
					 czyglPage.grantRole(record);		
				});
				return   link ;
  			}
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
		grantRole : function(obj){
			
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
			var hasRole = obj.roleList;
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
				width:800,
				height : 500,
				autoOpen:true,
				buttons:{ 
					"下次再说":function(){
						
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
			$("#czygl_rolesTable thead input[type='checkbox']").live("click",function(){
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
		//角色设置
//		grantRole : function(record){
//			//角色管理
//			$('#jssz_dialog').dialog({width:680,autoOpen:false});
//			
//			//登录信息
//			var param = comm.getRequestParams();
//			
//			//查询角色列表
//			systemManage.roleList({
//				platformCode : comm.lang("systemManage").platformCode,
//				subSystemCode : comm.lang("systemManage").subSystemCode,
////				roleType : comm.lang("systemManage").roleType,
//				entCustId : param.entCustId,
//				custType: param.entResType
//			},function(res){
//				//角色列表
//				var roles = res.data;
//				//操作员已有角色列表
//				var haveRoles = record.roleList;
//				
//				var trs = "";
//				if(roles && roles.length > 0)
//				{
//					
//					if(haveRoles && haveRoles.length > 0)
//					{
//						for(var i = 0;i < roles.length; i++)
//						{
//							var flag = false;
//							
//							for(var j = 0 ; j < haveRoles.length; j++)
//							{
//								if(roles[i].roleId == haveRoles[j].roleId)
//								{
//									flag = true;
//									break;
//								}
//							}
//							
//							if(flag)
//							{
//								if(i%2==0){
//									trs = trs + "<tr class='even_index_row'>";
//								}else{
//									trs = trs + "<tr>";
//								}
//								trs = trs + "<td ><input class='mr5' name='roles' type='checkbox' checked='checked' value='"+roles[i].roleId+"'>"+(i+1)+"</td>" +
//								"<td>" +roles[i].roleName+"</td><td>" + roles[i].roleDesc + "</td></tr>";
//							}
//							else
//							{
//								if(i%2==0){
//									trs = trs + "<tr class='even_index_row'>";
//								}else{
//									trs = trs + "<tr>";
//								}
//								trs = trs + "<td ><input class='mr5' name='roles' type='checkbox' value='"+roles[i].roleId+"'>"+(i+1)+"</td>" +
//								"<td>" +roles[i].roleName+"</td><td>" + roles[i].roleDesc + "</td></tr>";
//							}
//						}
//					}
//					else
//					{
//						for(var i = 0;i < roles.length; i++)
//						{
//							if(i%2==0){
//								trs = trs + "<tr class='even_index_row'>";
//							}else{
//								trs = trs + "<tr>";
//							}
//							trs = trs + "<td ><input class='mr5' name='roles' type='checkbox' value='"+roles[i].roleId+"'>"+(i+1)+"</td>" +
//							"<td>" +roles[i].roleName+"</td><td>" + roles[i].roleDesc + "</td></tr>";
//						}
//					}
//				}
//				
//				//将tr加到table中
//				$('#czygl_rolesTable tbody').append(trs);
//				
//				$('#jssz_dialog').dialog({
//					width:800,
//					height : 425,
//					autoOpen:true,
//					buttons:{ 
//						"下次再说":function(){
//							//关闭并销毁
//							$('#czygl_rolesTable tbody').empty();
//							$('#jssz_dialog').dialog('destroy');
//							
//						},
//						"立刻设置":function(){
//							
//							//获取选中的角色
//							var roleIds = "";
//							$("input[name='roles']:checked").each(function(){
//								roleIds = roleIds + $(this).val() + ",";
//							});
//							roleIds = roleIds.substring(0,roleIds.length - 1);
//							
//							//提交重置角色
//							systemManage.resetRole({
//								adminCustId : param.custId,
//								roleIds : roleIds,
//								operCustId : record.operCustId
//							},function(res){
//								$('#czygl_rolesTable tbody').empty();
//								$('#jssz_dialog').dialog('destroy');
//								$('#xtyhgl_czygl').click();
//							});
//						}										
//					} 
//				});
//				
//			});
//			
//			$("#selectAll").click(function(){
//				if($(this).attr("checked"))
//				{
//					 $("[name='roles']").attr("checked",'true');//全选
//				}else
//				{
//					$("[name='roles']").removeAttr("checked");	//取消全选
//				}
//			})
//		}
	};
	return czyglPage
}); 

 