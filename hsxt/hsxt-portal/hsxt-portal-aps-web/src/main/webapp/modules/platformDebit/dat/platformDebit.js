define(function () {
	return {
		//扣款申请记录查询
		hsbDeductionPage : function(gridId,params,detail){
			return comm.getCommBsGrid(gridId, "hsbDeductionPage", params, comm.lang("platformDebit"), detail);
		},
		//平台互生币扣款申请复核分页查询
		deductReviewPage : function(gridId,params,detail, del, add, edit){
			return comm.getCommBsGrid(gridId, "deductReviewPage", params, comm.lang("platformDebit"), detail, del, add, edit);
		},
		//平台扣款申请
		applyHsbDeduction : function(data,callback){
			comm.requestFun("applyHsbDeduction",data,callback,comm.lang("platformDebit"));
		},
		//通过互生号查找详情
		getResNoInfo : function(data,callback){
			comm.requestFun("getResNoInfo",data,callback,comm.lang("platformDebit"));
		},
		//获取扣款申请明细
		queryDeductDetail : function(data,callback){
			comm.requestFun("queryDeductDetail",data,callback,comm.lang("platformDebit"));
		},
		//扣款申请复核
		apprHsbDeduction : function(data,callback){
			comm.requestFun("apprHsbDeduction",data,callback,comm.lang("platformDebit"));
		}
	};
});