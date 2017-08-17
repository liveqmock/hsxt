define(function () {
	return {
		findEntDeclareReviewList : function(params, detail){
			comm.getCommBsGrid(null, "findEntDeclareReviewList", params, comm.lang("coDeclaration"), detail);
		}
	};
});