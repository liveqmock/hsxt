define(function () {
	return {
		/**
		 * 授权码查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findAuthCodeList : function(params, detail){
			comm.getCommBsGrid(null, "findAuthCodeList", params, comm.lang("coDeclaration"), detail);
		},
		/**
		 * 发送短信
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		sendAuthCode : function(params, callback){
			comm.requestFun("sendAuthCode", params, callback, comm.lang("coDeclaration"));
		}
	};
});