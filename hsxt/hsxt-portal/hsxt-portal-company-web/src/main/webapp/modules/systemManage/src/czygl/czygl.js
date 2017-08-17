define(['text!systemManageTpl/czygl/czygl.html',
        'text!systemManageTpl/czygl/bdhsk.html',
		'text!systemManageTpl/czygl/jbhsk.html',
		'text!systemManageTpl/czygl/jssz.html',
		'text!systemManageTpl/czygl/glyyd.html',
        'systemManageDat/systemManage'],function(czyglTpl, bdhskTpl, jbhskTpl, jsszTpl, glyydTpl,systemManage){
	return czygl = {
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
				gridObj = comm.getCommBsGrid("detailTable","list_operator",params,czygl.detail,czygl.del,czygl.add,czygl.edit,null,czygl.button1);
				
			});
		 	
		 	$("#searchBtn").click();
			
		},
		edit : function(record, rowIndex, colIndex, options){
  			var link  = '';			  			
  			
  			if ( colIndex == 7 )
  			{
				if (record.bindResNoStatus == '-1'){
					var alink =  $('<a >'+comm.lang("systemManage").cancelBind+'</a>').click(function(e) {
						
						 czygl.unBind(record);
					});
					link = $('<span>'+record.operResNo+'&nbsp;&nbsp;['+comm.lang("systemManage").inBind+']&nbsp;&nbsp;</span>').append(alink);
				
				}else if(record.bindResNoStatus == "1"){
					
					var alink =  $('<a >'+comm.lang("systemManage").cancelBind+'</a>').click(function(e) {
						
						czygl.unBind(record); 
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
  			else if (colIndex==6 && !record.admin)
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
  			
  			if (colIndex == 6 && !record.admin){
	  			link =  $('<a >'+comm.lang("systemManage").del+'</a>').click(function(e) {
	  				var confirmObj = {	
							imgFlag:true,
							width:480,
							content : comm.lang("systemManage").delContent,	
							 													
							callOk :function(){
								var param = comm.getRequestParams()
								systemManage.deleteOperator({
									adminCustId : param.custId,
									operCustId : record.operCustId,
									operCustName : record.userName
								},function(res){
									comm.yes_alert("删除成功");
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
						if(roleName.substring(19)==","){
							roleName = roleName.substring(0,19);
						}
						roleName = roleName+"....";
					}else{
						roleName = tip;
					}
				}
				return "<span title='"+tip+"' >"+roleName+"</span>";
				
  			}else if(colIndex == 5){
  				return comm.lang("systemManage").accountStatus[record.accountStatus];
  			}
  			else if (colIndex==6 && !record.admin){
  				
  				var link = '';
	  			link  =  $('<a >'+comm.lang("systemManage").grantRole+'</a>').click(function(e) {
					 czygl.grantRole(record);		
				});
				return   link ;
				
			}
		},
		add : function(record, rowIndex, colIndex, options){
			if(colIndex == 6 && !record.admin){
	  			var link = '';
	  			
				if(record.accountStatus == "1"){
					
					link=  $('<a >'+comm.lang("systemManage").activity+'</a>').click(function(e) {							
						comm.confirm({
							imgFlag : true,
							imgClass : 'tips_ques',
							content : comm.lang("systemManage").isActivity,
							callOk :function(){
								
								czygl.updateOper(record, 0);
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
								czygl.updateOper(record, 1);
							}	
						});						
					});	
				}
				return   link ;
			}
  		},
  		button1 :  function(record, rowIndex, colIndex, options){
  			var link = "";
  			if(colIndex == 6 && record.userName != '0000'){  //操作员0000不能关联营业点
				//关联营业点
	  			link  =  $('<a >'+comm.lang("systemManage").linkStoreEmp+'</a>').click(function(e) {
					 czygl.glyyd(record);		
				});
			}
  			return link;
		},
		unBind : function(record){
			
			require(['systemManageSrc/czygl/hskjb'], function(tab){
					
					$('#xtyhgl_hskjb').click();
					
					tab.showPage(record);
					
				});
			
			
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
		grantRole : function(record){
			//角色管理
//			$('#jssz_dialog').dialog({width:680,height:500,autoOpen:true});
			
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
			var hasRole = record.roleList;
			rolesGrid = comm.getCommBsGrid("czygl_rolesTable","list_role",params,
					
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
				width:720,
				height : 450,
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
							operCustId : record.operCustId
						},function(res){
							$('#czygl_rolesTable tbody').empty();
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
		},
		glyyd : function(obj){
			
			/*此代码只作为演示获取营业点信息的不同状态，实际是后台返回数据做判断。*/
//			var dlgHeight = (obj.dlyhm == '0001' ? 150 : 300);
//			var buttonsObj = (obj.dlyhm == '0001' ? {'确定' : function(){$(this).dialog('destroy');}} : {'确定' : function(){$(this).dialog('destroy');},'取消' : function(){$(this).dialog('destroy');}	});
			/*end*/
			var postData = {
					employeeAccountNo : obj.userName
			};
			systemManage.getSalerStoresByEntResNoAndEmpActNo(postData,function(res){
				
				$('#glyyd_dlg').html(_.template(glyydTpl)) ;
				
				if(comm.isEmpty(res.data)){
					$('#glyyd_msg').removeClass('none');
					$('#glyyd_list').addClass('none');
				}else{
					$('#glyyd_msg').addClass('none');
					$('#glyyd_list').removeClass('none');
					var results = res.data.result;
					var trs = "<tr>";
					for(var i = 0; i < results.length;i++){
						trs = trs + '<td align="left" height="25"><label>';
						if(results[i].isRelation == 1){
							trs = trs + '<input type="radio" name="storeId" value="'+results[i].id+'" checked="checked" class="mr5 input_checkbox"/>'+ (results[i].type == 1?results[i].addAll:results[i].name);
						}else{
							trs = trs + '<input type="radio" name="storeId" value="'+results[i].id+'"  class="mr5 input_checkbox"/>'+(results[i].type == 1?results[i].addAll:results[i].name);
						}
						trs = trs + '</label></td>';
						
						if(i%3==2){
							trs = trs + "</tr><tr>";
						}
					}
					trs = trs + "</tr>";
					
					$('#czygl_storeEmployeeTable tbody').append(trs);
					
				}
				$('#glyyd_dlg').dialog({
					title : comm.lang("systemManage").linkStoreEmp,
					width : 600,
					height: 300,
					modal : true,
					closeIcon : true,
					buttons : {
						'确定' : function(){
							var storeIds = "";
							$("input[name='storeId']:checked").each(function(){
								storeIds = storeIds + $(this).val() + ",";
							});
							
							var win = $(this);
							
							if(comm.isEmpty(storeIds)){
								comm.warn_alert(comm.lang("systemManage").noSelectStore);
								return;
								//当为多选框时的操作 ，不选择营业点视为解除已有营业点的关联关系
								/*var confirmObj = {	
										imgFlag:true,
										width:580,
										content : comm.lang("systemManage").delStore,	
										callOk :function(){
											var param = comm.getRequestParams()
											systemManage.deleteStore({
												employeeAccountNo : obj.userName
											},function(res){
												comm.yes_alert("删除成功");
												if(gridObj){
													gridObj.refreshPage();
												}
												win.dialog('destroy');
											});
										}
									 }
								 comm.confirm(confirmObj);*/ 
							}else{
								var roIds = "";
								if(obj.roleList != null && obj.roleList.length > 0){
									for(var i = 0; i < obj.roleList.length ; i++){
										roIds = roIds + obj.roleList[i].roleId + ",";
									}
								}
								roIds = roIds.substring(0,roIds.length - 1);
								storeIds = storeIds.substring(0,storeIds.length - 1);
								var ajaxData = {
										storeId:storeIds,
										roleIds:roIds,
										employeeAccountNo:obj.userName,
										employeeCustId:obj.operCustId
								};
								
								systemManage.setSalerStoreEmployeeRelation(ajaxData,function(res1){
									if(res1.data.retCode == 200){
										comm.yes_alert("关联营业点成功！");
									}else{
										comm.error_alert("关联营业点失败！");
									}
									win.dialog('destroy');
								});
							}
							
						},
						'取消' : function(){
							
							$(this).dialog('destroy');
						}
					}
				});
				
			});
			
		},
		//返回列表并切换菜单
		backList: function(){
			comm.liActive($('#xtyhgl_czygl'), '#xtyhgl_xzczy, #xtyhgl_xgczy, #xtyhgl_hskjb, #xtyhgl_hskbd', '#busibox2');
		}
	}
});