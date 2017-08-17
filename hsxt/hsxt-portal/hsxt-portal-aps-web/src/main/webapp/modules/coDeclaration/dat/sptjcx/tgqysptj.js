define(function () {
	return {
		/**
		 * 托管企业-审批统计查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 * @param del 自定义函数
		 */
		findDeclareStatisticsList : function(params, detail, del){
			comm.getCommBsGrid(null, "findDeclareStatisticsList", params, comm.lang("coDeclaration"), detail, del);
		},
		/**
		 * 查询申报进行步骤
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeclareStep : function(params, callback){
			comm.requestFun("findDeclareStep", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 删除申报信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		delUnSubmitDeclare : function(params, callBack){
			comm.requestFun("delUnSubmitDeclare", params, callBack, comm.lang("coDeclaration"));
		}
	};
});