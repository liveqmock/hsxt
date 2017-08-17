define(function () {
	return {
		/**
		 * 合同查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findContractList : function(gridId,params, detail,detail2){
			comm.getCommBsGrid(gridId, "findContractList", params, comm.lang("contractManage"), detail,detail2);
		},
		//查询盖章合同内容
		findContractContentBySeal : function(data,callback){
			comm.requestFun("find_contract_content_by_seal",data,callback,comm.lang("contractManage"));
		},
		//合同打印
		printContract	:	function(data,callback){
			comm.requestFun("printContract",data,callback,comm.lang("contract"));
		}
	};
});