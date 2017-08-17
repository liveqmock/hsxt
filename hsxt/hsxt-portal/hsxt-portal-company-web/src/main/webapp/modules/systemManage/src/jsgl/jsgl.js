define(['text!systemManageTpl/jsgl/jsgl.html',
		'text!systemManageTpl/jsgl/cdsq.html',
		'text!systemManageTpl/jsgl/xzxgjs.html',
		'systemManageDat/systemManage',
		'jqueryztree'
		], function(tpl, cdsqTpl, xzxgTpl,systemManage){
	return jsgl = {
		
		cdsqTpl: cdsqTpl,
		loginInfo : null,
		gridObj : null,
		showPage : function(){
			
			
			
			$('#busibox').html(_.template(tpl));			 
			
			//新增操作员单击事件
			$('#btn_xzjs').triggerWith('#xtyhgl_xzxgjs');
			
			
			//获取用户登录信息
		 	loginInfo = comm.getRequestParams();
		 	
			
			var params = {
					search_entCustId : loginInfo.entCustId,
					search_custType : loginInfo.entResType,
					search_platformCode : comm.lang("systemManage").platformCode,	//平台
					search_subSystemCode : comm.lang("systemManage").subSystemCode	//子系统
//					search_roleType : ''		//角色类型
			};
			
			gridObj = comm.getCommBsGrid("detailTable","list_role",params,jsgl.detail,jsgl.del,jsgl.edit,jsgl.add);
			
		},
		
		edit : function(record, rowIndex, colIndex, options){
			/**
			 * 角色类型1：全局:2：平台:3：私有
			 *全局角色不可修改，系统初始化时带入。
			 *平台角色由平台操作员维护
			 *企业在角色查看时可以看到全局角色和平台角色，企业操作员、企业设备可以成为这种角色
			 *私有角色隶属于企业，企业可以增删改自己的角色
			 */
			if(record.roleType != '1'){
				
				var link1 = '';
				link1 =  $('<a >'+comm.lang("systemManage").update+'</a>').click(function(e) {	
					
					$('#xtyhgl_xzxgjs').click();
					
					$("#roleId").val(record.roleId);
					$("#roleName").val(record.roleName);
					$("#roleDesc").val(record.roleDesc);
					$("#updateFlag").val("1");
					$("#roleType").val(record.roleType);
				});
				return   link1 ;
			}
  		} ,
  		del : function(record, rowIndex, colIndex, options){
  			if(record.roleType != '1'){
  				
  				var link1 = '';
  				
  				link1 =  $('<a >'+comm.lang("systemManage").del+'</a>').click(function(e) {
  					
  					var confirmObj = {	
  							imgFlag:true,
  							width:480,
  							content : comm.lang("systemManage").delContent,	
  							
  							callOk :function(){
  								systemManage.delRole({
  									roleId : record.roleId,		 	//角色id
  									adminCustId : loginInfo.custId	//操作人客户号
  								},function(res){
  									comm.yes_alert("删除成功");
  									if(gridObj){
  										gridObj.refreshPage();
  									}
  								});
  							}
  					}
  					comm.confirm(confirmObj);								 
  				});
  				return   link1 ;
  			}
  		} ,
  		detail :  function(record, rowIndex, colIndex, options){
  			if(record.roleType != '1'){
  				
  				var link1 = '';
  				link1 =  $('<a >'+comm.lang("systemManage").menuPower+'</a>').click(function(e) {							
  					jsgl.menuAuth(record);
  				});
  				return   link1 ;
  			}
  		},
  		
		//菜单授权
		menuAuth : function(obj){
				
				//查询角色已有的权限
				systemManage.rolePerm({
					
					platformCode : comm.lang("systemManage").platformCode,	//平台编码
					
					subSystemCode : comm.lang("systemManage").subSystemCode,	//子系统编码
					
					roleId : obj.roleId
					
				},function(res1){
					
					//查询系统权限列表
					systemManage.listPerm({
						
						custType : loginInfo.entResType,		//企业类型
						
						platformCode : comm.lang("systemManage").platformCode,	//平台编码
						
						subSystemCode : comm.lang("systemManage").subSystemCode	//子系统编码
						
					},function(res2){
						
						var data = [];
						
						var result1 = res1.data;	//角色已有权限
						
						var result2 = res2.data;  //系统权限列表
						
						//以下if操作 将角色已有权限标为选中状态
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

						$.fn.zTree.init($("#xzczy_jsfp"), setting, data);
						
						$("#jsgl_cdsq_dialog").dialog({
							width: 680,				 
							buttons:{
								"下次再说":function(){
									$(this).dialog('destroy');
									
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
						            
						            var win = $(this);
						            //提交赋与角色权限
						            systemManage.grantPerm({
						            	adminCustId : loginInfo.custId,
						            	roleId : obj.roleId,
						            	powerIds : powerIds
						            },function(res3){
						            	
						            	win.dialog('destroy');
						            });
									
								}
							}
						});
						
					});
					
				});
				
		}
	};
});