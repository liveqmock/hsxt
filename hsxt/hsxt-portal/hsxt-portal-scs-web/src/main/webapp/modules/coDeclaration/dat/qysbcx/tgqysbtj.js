define(function () {
	return {
		/**
		 * 企业申报查询-托管企业
		 * @param params 参数对象
		 * @param funOne 自定义函数1
		 * @param funTwo 自定义函数2
		 * @param funTre 自定义函数3
		 */
		findEntDeclareQueryList : function(params, funOne, funTwo, funTre){
			return comm.getCommBsGrid("tableDetail", "findEntDeclareQueryList", params, comm.lang("coDeclaration"), funOne, funTwo, funTre);
		},
		/**
		 * 查询报备进行步骤
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		findDeclareStep : function(params, callBack){
			comm.requestFun("findDeclareStep", params, callBack, comm.lang("coDeclaration"));
		},
		/**
		 * 删除报备信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		delUnSubmitDeclare : function(params, callBack){
			comm.requestFun("delUnSubmitDeclare", params, callBack, comm.lang("coDeclaration"));
		}
	};
});