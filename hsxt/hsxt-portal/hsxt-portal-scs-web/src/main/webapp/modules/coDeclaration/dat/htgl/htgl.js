define(function () {
	return {
		//查询打印合同内容
		findContractContentByPrint : function(data,callback){
			comm.requestFun("find_contract_content_by_print",data,callback,comm.lang("coDeclaration"));
		},
		//合同打印
		printContract	:	function(data,callback){
			comm.requestFun("printContract",data,callback,comm.lang("coDeclaration"));
		},
		/**
		 * 合同查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findContractList : function(params, detail,detail2){
			comm.getCommBsGrid(null, "findContractList", params, comm.lang("coDeclaration"), detail,detail2);
		}
	};
});