define(function () {
	return {
		//操作员列表查询（企业客户号查询所有）
		findOperatorList : function(params, callback){
			comm.requestFun("list_operator_buentcustId", params, callback, comm.lang("systemManage"));
		},
		//添加操作员
		addOperator : function(params,callback){
			comm.requestFun("add_operator", params, callback, comm.lang("systemManage"));
		},
		//修改操作员
		updateOperator : function(params,callback){
			comm.requestFun("update_operator", params, callback, comm.lang("systemManage"));
		},
		//删除操作员
		deleteOperator : function(params,callback){
			comm.requestFun("delete_operator", params, callback, comm.lang("systemManage"));
		},
		//查询操作员
		queryOperator : function(params,callback){
			comm.requestFun("query_operator", params, callback, comm.lang("systemManage"));
		},
		//查询归属地区平台
		alongPlatform : function(params,callback){
			comm.requestFun("entinfo_platform", params, callback, comm.lang("systemManage"));
		},
		
		//添加用户组
		addEntGroup : function(params,callback){
			comm.requestFun("add_entgroup", params, callback, comm.lang("systemManage"));
		},
		//删除用户组
		deleteEntGroup : function(params,callback){
			comm.requestFun("delete_entgroup", params, callback, comm.lang("systemManage"));
		},
		//修改用户组
		updateEntGroup : function(params,callback){
			comm.requestFun("update_entgroup", params, callback, comm.lang("systemManage"));
		},
		//用户组添加成员
		addGroupUser : function(params,callback){
			comm.requestFun("addgroupuser_entgroup", params, callback, comm.lang("systemManage"));
		},
		//用户组删除成员
		delGroupUser : function(params,callback){
			comm.requestFun("delgroupuser_entgroup", params, callback, comm.lang("systemManage"));
		},
		//查询用户组信息（包括用户组成员列表）
		queryEntGroup : function(params,callback){
			comm.requestFun("userlist_entgroup", params, callback, comm.lang("systemManage"));
		},
		
		//添加角色
		addRole : function(params,callback){
			comm.requestFun("add_role",params,callback,comm.lang("systemManage"));
		},
		//删除角色
		delRole : function(params,callback){
			comm.requestFun("delete_role",params,callback,comm.lang("systemManage"));
		},
		//修改角色
		updateRole : function(params,callback){
			comm.requestFun("update_role",params,callback,comm.lang("systemManage"));
		},
		//给操作员分配角色
		grantRole : function(params,callback){
			comm.requestFun("grantrole_role",params,callback,comm.lang("systemManage"));
		},
		//给操作员重置角色
		resetRole : function(params,callback){
			comm.requestFun("resetrole_role",params,callback,comm.lang("systemManage"));
		},
		//给操作员重置角色
		roleList : function(params,callback){
			comm.requestFun("rolelist_role",params,callback,comm.lang("systemManage"));
		},
		
		
		
		//权限列表
		listPerm : function(params,callback){
			comm.requestFun("listperm_perm",params,callback,comm.lang("systemManage"));
		},
		//给角色赋权限
		grantPerm : function(params,callback){
			comm.requestFun("grantperm_perm",params,callback,comm.lang("systemManage"));
		},
		//查看角色以后权限
		rolePerm : function(params,callback){
			comm.requestFun("roleperm_perm",params,callback,comm.lang("systemManage"));
		}
	};
});