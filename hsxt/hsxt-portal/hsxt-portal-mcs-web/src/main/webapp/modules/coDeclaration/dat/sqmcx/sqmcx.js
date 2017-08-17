define(function () {
	return {
		/**
		 * 授权码查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findAuthCodeList : function(params, detail){
			comm.getCommBsGrid(null, "findAuthCodeList", params, comm.lang("coDeclaration"));
		}
	};
});