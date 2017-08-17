define(['text!systemManageTpl/jsgl/xzxgjs.html',
		   'text!systemManageTpl/jsgl/xzxgjs_dialog.html',
		   'systemManageDat/systemManage'
 
 ],function(xzxgjsTpl ,xzxgjs_dialogTpl ,systemManage){
	var xzxgjsPage = {
		
		showPage : function(){
			
			$('#busibox').html(_.template(xzxgjsTpl)) ;			 
		 	
			$('#xzxgjs_tj').click(function(){
				
				//获取用户登录信息
			 	var loginInfo = comm.getRequestParams();
			 	
		 		var updataFlag = $("#updateFlag").val();
		 		
		 		var postData = {
		 				adminCustId : loginInfo.custId,		//操作人客户号
		 				roleName : $.trim($("#roleName").val()),	//角色名称
		 				roleDesc : $.trim($("#roleDesc").val())		//角色描述
		 		}
		 		//修改
		 		if(updataFlag == "1"){
		 			
		 			postData.roleId = $("#roleId").val();		//角色id
		 			
		 			postData.roleType = $("#roleType").val();
		 			
		 			systemManage.updateRole(postData,function(res){
		 				
		 				xzxgjsPage.menuAuth($("#roleId").val());
		 				
		 			});
		 		}
		 		//添加
		 		else{
		 			
		 			postData.entCustId = loginInfo.entCustId; //企业客户号
		 			
		 			postData.roleType = comm.lang("systemManage").roleType;
		 			
	 				systemManage.addRole(postData,function(res){
	 					
	 					var roleId = res.data;
	 					
	 					xzxgjsPage.menuAuth(roleId);
	 					
	 				});
		 		}
		 		
				
			}.bind(this)); 
		  
			//取消
			$('#xzxgjs_qx').triggerWith('#xtyhgl_jsgl');	
		},
		//菜单授权
		menuAuth : function(roleId){
			
				//树型 角色 展示
				var setting = {
					check: {
						enable: true,
						chkDisabledInherit: true
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};
				
				//查询角色已有的权限
				systemManage.rolePerm({
					
					platformCode : comm.lang("systemManage").platformCode,	//平台编码
					
					subSystemCode : comm.lang("systemManage").subSystemCode,	//子系统编码
					
					roleId : roleId
					
				},function(res1){
					
					//查询系统权限列表
					systemManage.listPerm({
						
						platformCode : comm.lang("systemManage").platformCode,	//平台编码
						subSystemCode : comm.lang("systemManage").subSystemCode	//子系统编码
						
					},function(res2){
						
						var data = [];
						var result1 = res1.data;	//角色已有权限
						var result2 = res2.data;  //系统权限列表
						
						if(result2 && result2.length > 0)
						{
							if(result1 && result1.length > 0)
							{
								for(var i = 0; i < result2.length; i++)
								{
									//【设置密保问题】 菜单是0000操作员所特有的菜单  不可在此设置菜单  
									//问题为后台人员不愿修改查询系统权限列表的接口，把这一菜单在后台查询时直接过滤掉，只有通过前台js中手动过滤。
									if(result2[i].permName == "设置密保问题"){
										continue;
									}
									
									var temp;
									
									var flag = false;
									
									for(var j = 0; j < result1.length; j++)
									{
										if(result2[i].permId == result1[j].permId)
										{
											flag = true;
											break;
										}
										
										
									}
									if(flag)
									{
										temp = {id:result2[i].permId,pId:result2[i].parentId,name:result2[i].permName,checked:true};
									}
									else
									{
										temp = {id:result2[i].permId,pId:result2[i].parentId,name:result2[i].permName};
									}
									
									data.push(temp);
								}
							}
							else
							{
								if(result2 && result2.length > 0)
								{
									for(var i = 0; i < result2.length; i++)
									{
										//【设置密保问题】 菜单是0000操作员所特有的菜单  不可在此设置菜单  
										//问题为后台人员不愿修改查询系统权限列表的接口，把这一菜单在后台查询时直接过滤掉，只有通过前台js中手动过滤。
										if(result2[i].permName == "设置密保问题"){
											continue;
										}
										
										var temp = {id:result2[i].permId,pId:result2[i].parentId,name:result2[i].permName};
										data.push(temp);
									}
								}
							}
							
						}
						
						$.fn.zTree.init($("#xzczy_jsfp"), setting, data);
						
						$("#jsgl_cdsq_dialog").dialog({
							width: 680,				 
							buttons:{
								"下次再说":function(){
									$(this).dialog('destroy');
									$("#xtyhgl_jsgl").click();
								},
								"立即设置":function(){
									
									var treeObj=$.fn.zTree.getZTreeObj("xzczy_jsfp");
									
									var nodes=treeObj.getCheckedNodes(true);
									
									var powerIds="";
									for(var i=0;i<nodes.length;i++)
									{
										powerIds+=nodes[i].id + ",";
									}
									powerIds = powerIds.substring(0,powerIds.length - 1);
									
									//获取用户登录信息
									loginInfo = comm.getRequestParams();
									
									var win = $(this);
									//提交赋与角色权限
									systemManage.grantPerm({
										adminCustId : loginInfo.custId,
										roleId : roleId,
										powerIds : powerIds
									},function(res3){
										
										win.dialog('destroy');
										$("#xtyhgl_jsgl").click();
									});
								}
							}
						});
						
					});
					
				});
			 
		}
	};
	return xzxgjsPage
}); 

 