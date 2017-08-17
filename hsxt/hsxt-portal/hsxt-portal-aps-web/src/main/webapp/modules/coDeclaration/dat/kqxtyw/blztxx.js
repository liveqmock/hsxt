define(function () {
	return {
		findOperationHisList : function(params, detail){
			comm.getCommBsGrid("blztxx_tableDetail", "findOperationHisList", params, comm.lang("coDeclaration"), detail);
		}
	};
});