define(function () {
	return {
		//查询
		findSystemLogList : function(params, bsType, bsResult) {
			return comm.getCommBsGrid("systemLogTable", "findSystemLogList", params, null, bsType, bsResult);
		}
	};
});