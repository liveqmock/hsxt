define(function () {
	return {
		/**
		 * 待开启系统信息查询
		 * @param params 参数对象
		 * @param detail 查看详情函数
		 */
		findOpenSysApprList : function(params, detail,orderOpt){
			return comm.getCommBsGrid(null, "findOpenSysApprList", params, comm.lang("coDeclaration"), detail,orderOpt);
		}
	};
});