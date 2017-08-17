define(function(){
	var ajaxRequest = {
		/*获取企业下角色和默认角色列表接口*/
			getEnterperiseRoleList : function(param,callback){
			comm.Request({url:'/role/getEnterperiseRoleList',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/*用户角色关系集合*/
		getRoleSysAuth : function(param,callback){
			comm.Request({url:'/role/getRoleSysAuth',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/*添加用户角色*/
		addRole : function(param,callback){
			comm.Request({url:'/role/addRole',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/*添加角色权限*/
		addRoleAuth : function(param,callback){
			comm.Request({url:'/role/addRoleAuth',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		getAllSysAuth : function(param,callback){
			comm.Request({url:'/role/getAllSysAuth',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		getAuthListByRoleIds : function(param,callback){
			comm.Request({url:'/role/getAuthListByRoleIds',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		//修改角色
		updateRole : function(param,callback){
			comm.Request({url:'/role/updateRole',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		//根据id ,读取对象
		getSysRoleByroleId : function(param,callback){
			comm.Request({url:'/role/getSysRoleByroleId',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		//修改角色
		batchDeleteRole : function(param,callback){
			comm.Request({url:'/role/batchDeleteRole',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		}
	};	
	return ajaxRequest;
});