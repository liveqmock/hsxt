define(function () {
	return {
		/**
		 * 成员企业-审批统计查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findDeclareStatisticsList : function(params, detail){
			comm.getCommBsGrid(null, "findDeclareStatisticsList", params, comm.lang("coDeclaration"), detail);
		}
	};
});