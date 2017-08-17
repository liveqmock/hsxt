define(function () {
	return {
		//查询密码长度配置(交易密码,登录密码)
		querymmpz : function (data, callback) {
			comm.requestFun("get_password_config",data,callback,comm.lang("safeSet"));
		},
		//修改登录密码
		updatedlmm : function (data, callback) {
	//		comm.requestFun("update_login_password",data,callback,comm.lang("safeSet"));
			comm.requestFunForHandFail("update_login_password", data, callback, comm.lang("safeSet"));
		},
		//获取密保问题列表
		getmblb:function(data, callback){
			comm.requestFun("get_question_list",data,callback,comm.lang("safeSet"));
		},
		//设置密保问题答案
		setmbwt:function(data, callback){
			comm.requestFun("set_pwd_question",data,callback,comm.lang("safeSet"));
		},
		//获取预留信息
		getylxx:function(data, callback){
			comm.requestFun("get_reserve_info",data,callback,comm.lang("safeSet"));
		},
		//设置预留信息
		setylxx:function(data, callback){
			comm.requestFun("set_reserve_info",data,callback,comm.lang("safeSet"));
		},
		//修改预留信息
		updatelxx:function(data, callback){
			comm.requestFun("update_reserve_info",data,callback,comm.lang("safeSet"));
		}
	};
});