define(function() {
	return {
		/* 消费者信息分页查询* */
		listconsumer_pwd : function(autoLoad,params, callback) {
			return comm.controlLoadBsGrid(autoLoad,"consumerTable", "listconsumer_pwd",
					params, comm.lang("consumerInfo"), callback);
		},
		/* 登陆密码企业信息分页查询* */
		listbelongent_pwd : function(autoLoad,params, callback) {
			return comm.controlLoadBsGrid(autoLoad,"entinfoTable", "listbelongent_pwd",
					params, comm.lang("consumerInfo"), callback);
		},
		/* 交易密码企业信息待审核分页查询* */
		listentjy_pwd : function(autoLoad,params, callback) {
			return comm.controlLoadBsGrid(autoLoad,"entinfoTable", "listentjy_pwd", params,
					comm.lang("consumerInfo"), callback);
		},
		/* 交易密码企业信息分页查询* */
		listentjylist_pwd : function(autoLoad,params, callback) {
			return comm.controlLoadBsGrid(autoLoad,"entinfoTable", "listentjylist_pwd",
					params, comm.lang("consumerInfo"), callback);
		},
		/* 根据互生号查询企业信息* */
		findAllByResNo : function(params, callback) {
			comm.requestFun("findAllByResNo", params, callback, comm
					.lang("consumerInfo"));
		},
		/* 根据互生号查询企业信息一般默认银行账户信息* */
		findDefaultBankByEntResNo : function(params, callback) {
			comm.requestFun("findDefaultBankByEntResNo", params, callback, comm
					.lang("consumerInfo"));
		},
		/* 企业交易密码重置* */ 
		Entjycz : function(params, callback) {
			comm.requestFun("listentjycz_pwd", params, callback, comm.lang("consumerInfo"));
		},
		/* 消费者登陆密码重置* */ 
		consumerdl_pwd : function(params, callback) {
			comm.requestFun("consumerdl_pwd", params, callback, comm
					.lang("consumerInfo"));
		},
		/* 消费者交易密码重置* */ 
		consumerjy_pwd : function(params, callback) {
			comm.requestFun("consumerjy_pwd", params, callback, comm
					.lang("consumerInfo"));
		},
		/* 企业登陆密码重置* */ 
		entdlcz_pwd : function(params, callback) {
			comm.requestFun("entdlcz_pwd", params, callback, comm
					.lang("consumerInfo"));
		}
	};
});