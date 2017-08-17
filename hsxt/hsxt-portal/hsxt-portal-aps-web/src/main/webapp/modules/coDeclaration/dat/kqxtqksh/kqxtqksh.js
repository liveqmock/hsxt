define(function () {
	return {
		/**
		 * 开系统欠费审核列表
		 * @param params 参数对象
		 * @param detail 查看详情函数
		 */
		findOpenSysList : function(params, detail){
			comm.getCommBsGrid(null, "findOpenSysList", params, comm.lang("coDeclaration"), detail);
		}
	};
});