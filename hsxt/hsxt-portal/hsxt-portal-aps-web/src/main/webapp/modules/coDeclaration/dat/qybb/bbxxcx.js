define(function () {
	return {
		/**
		 * 报备信息查询
		 * @param params 参数对象
		 * @param detail 查看详情函数
		 */
		findFilingList : function(params, detail){
			comm.getCommBsGrid(null, "findFilingList", params, comm.lang("coDeclaration"), detail);
		},
		/**
		 * 查看报备详细信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findFilingById : function(params, callback){
			comm.requestFun("findFilingById", params, callback, comm.lang("coDeclaration"));
		}
	};
});