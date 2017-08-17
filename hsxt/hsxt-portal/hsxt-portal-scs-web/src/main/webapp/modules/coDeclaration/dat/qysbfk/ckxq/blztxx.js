define(function () {
	return {
		findOperationHisList : function(params, detail){
			comm.getCommBsGrid("blztxxfk_tableDetail", "findOperationHisList", params, comm.lang("coDeclaration"), detail);
		}
	};
});