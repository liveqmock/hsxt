define(function () {
	return {
		//查询打印合同内容
		findContractContentByPrint : function(data,callback){
			comm.requestFun("find_contract_content_by_print",data,callback,comm.lang("contractManage"));
		},
		//查询盖章合同内容
		findContractContentBySeal : function(data,callback){
			comm.requestFun("find_contract_content_by_seal",data,callback,comm.lang("contractManage"));
		},
		//查询合同预览内容
		findContractContentByView : function(data,callback){
			comm.requestFun("find_contract_content_by_view",data,callback,comm.lang("contractManage"));
		},
		//合同发放
		optContractGiveOut : function(data,callback){
			comm.requestFun("opt_contract_give_out",data,callback,comm.lang("contractManage"));
		},
		//查询合同发放历史
		findContractGiveOutRecode : function(data,callback){
			comm.requestFun("find_contract_give_out_recode",data,callback,comm.lang("contractManage"));
		},
		//根据编号查询合同模板
		findContractTempById : function(data,callback){
			comm.requestFun("find_contract_temp_by_id",data,callback,comm.lang("contractManage"));
		},
		//创建合同模板
		createContractTemp : function(data,callback){
			comm.requestFun("create_contract_temp",data,callback,comm.lang("contractManage"));
		},
		//修改合同模板
		modifyContractTemp : function(data,callback){
			comm.requestFun("modify_contract_temp",data,callback,comm.lang("contractManage"));
		},
		//合同模板启用
		enableContractTemp : function(data,callback){
			comm.requestFun("enable_contract_temp",data,callback,comm.lang("contractManage"));
		},
		//停用合同模板
		stopContractTemp : function(data,callback){
			comm.requestFun("stop_contract_temp",data,callback,comm.lang("contractManage"));
		},
		//合同模板审批
		contractTempAppr : function(data,callback){
			comm.requestFun("contract_temp_appr",data,callback,comm.lang("contractManage"));
		},
		//合同盖章
		sealContract	:	function(data,callback){
			comm.requestFun("sealContract",data,callback,comm.lang("contractManage"));
		},
		//合同打印
		printContract	:	function(data,callback){
			comm.requestFun("printContract",data,callback,comm.lang("contractManage"));
		}
	};
});