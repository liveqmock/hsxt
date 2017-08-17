define(function () {
	return {
		//获得服务器地址getFsServerUrl
		getFsServerUrl : function(pictureId) {
			var param=comm.getRequestParams();
			return  comm.domainList['fsServerUrl']+pictureId+"?userId="+param.custId+"&token="+param.token;
		},
		//查询密码长度配置(交易密码,登录密码)
		querymmpz : function (data, callback) {
			comm.requestFun("get_password_config",data,callback,comm.lang("safeSet"));
		},
		//修改登录密码
		updatedlmm : function (data, callback) {
		//	comm.requestFun("update_login_password",data,callback,comm.lang("safeSet"));
			comm.requestFunForHandFail("update_login_password",data,callback,comm.lang("safeSet"));
		},
		//新增交易密码
		addjymm:function(data, callback){
			comm.requestFun("add_trading_password",data,callback,comm.lang("safeSet"));
		},
		//修改交易密码
		updatejymm:function(data, callback){
			comm.requestFun("update_trading_password",data,callback,comm.lang("safeSet"));
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
		},
		//获取企业明细
		getqymx:function(data, callback){
			comm.requestFun("get_ent_info",data,callback,comm.lang("safeSet"));
		},
		//申请交易密码重置
		applysqjymmcz:function(data, callback){
			comm.requestFun("requested_reset_trading_password",data,callback,comm.lang("safeSet"));
		},
		//交易密码重置
		resetjymmcz:function(data, callback){
			comm.requestFun("reset_trading_password",data,callback,comm.lang("safeSet"));
		},
		//获取重置密码申请业务文件下载地址
		getczmmsqywwj:function(data, callback){
			comm.requestFun("get_tradPwd_request_file",data,callback,comm.lang("safeSet"));
		},
		//验证码生成URL地址
		getCodeUrl:function(){
			var param=comm.getRequestParams();
			return comm.domainList['scsWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=tradePwd&"+(new Date()).valueOf();
		},
		//获取交易密码和密保问题已设置
		queryTradPwdLastApply:function(callback){
			comm.requestFun("query_tradPwd_lastApply",null,callback,comm.lang("safeSet"));
		}
		
	};
});