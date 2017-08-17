define(function () {
	return {
		//发送短信验证码
		mobileSendCode: function (jsonParam, callback) {
			comm.requestFun("no_mobileSendCode" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//获取绑定的手机号码
		findMobileByCustId : function (jsonParam, callback) {
			comm.requestFun("no_findMobileByCustId" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//修改绑定手机号码
		editBindMobile : function (jsonParam, callback) {
			comm.requestFun("no_editBindMobile" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//根据用户号获取绑定的Email
		findEamilByCustId : function (jsonParam, callback) {
			comm.requestFun("no_findEamilByCustId" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//添加绑定Eamil
		addBindEmail : function (jsonParam, callback) {
			comm.requestFun("no_addBindEmail" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//修改邮箱
		mailModify : function (data, callback) {
		//	comm.requestFun("no_mailModify",data,callback,comm.lang("myHsCard"));
			comm.requestFunForHandFail("no_mailModify",data,callback,comm.lang("myHsCard"));
		},
		
		//添加绑定手机号
		checkSmsCode : function (jsonParam, callback) {
			comm.requestFun("checkSmsCode" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//添加绑定手机号
		editBindMobile : function (jsonParam, callback) {
			comm.requestFun("no_editBindMobile" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//获取银行列表数据
		findBankBindList : function (jsonParam,callback) { 
			comm.requestFun("no_findBankBindList" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//增加银行卡信息
		addBankCard : function (jsonParam, callback) {
			comm.requestFun("no_addBankBind" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//删除银行卡信息
		delBankCard : function (jsonParam, callback) {
			comm.requestFun("no_delBankCard" , jsonParam, callback,comm.lang("myHsCard"));
		},
		/**
		 * 查询绑定的快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findPayBanksByCustId : function(params, callBack){
			comm.requestFun("no_findPayBanksByCustId" , params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 添加快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		addPayBank : function(params, callBack){
			comm.requestFun("no_addPayBank", params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 删除快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		delPayBank : function(params, callBack){
			comm.requestFun("no_delPayBank" , params, callBack, comm.lang("myHsCard"));
		},
		
		/**
		 * 根据客户号查询网络信息
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findNetworkInfoByCustId : function(params, callBack){
			comm.requestFun("findNetworkInfoByCustId" , params, callBack, comm.lang("myHsCard"));
		},
		
		/**
		 * 修改网络信息
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		updateNetworkInfo : function(params, callBack){
			comm.requestFun("updateNetworkInfo" , params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 删除收货地址信息
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		removeAddr : function(params, callBack){
			comm.requestFun("removeAddr" , params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 设置默认收货地址
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		setDefaultAddr : function(params, callBack){
			comm.requestFun("setDefaultAddr" , params, callBack, comm.lang("myHsCard"));
		},
		
		/**
		 * 修改收货地址
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		updateReceiveAddr : function(params, callBack){
			comm.requestFun("updateReceiveAddr" , params, callBack, comm.lang("myHsCard"));
		},
		
		/**
		 * 查询收货地址详情
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findReceiveAddrInfo : function(params, callBack){
			comm.requestFun("findReceiveAddrInfo" , params, callBack, comm.lang("myHsCard"));
		},
		
		/**
		 * 查询收货地址
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findReceiveAddrByCustId : function(params, callBack){
			comm.requestFun("findReceiveAddrByCustId" , params, callBack, comm.lang("myHsCard"));
		},
		
		
	};
});