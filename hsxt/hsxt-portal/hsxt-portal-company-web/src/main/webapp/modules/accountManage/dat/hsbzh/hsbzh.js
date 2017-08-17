define(function() {
	return {
		//企业货币兑换互生币明细查看
		getBuyHsbInfo : function(data, callback){
			comm.requestFun("getBuyHsbInfo" , data, callback,comm.lang("accountManage"));
		},
		//企业积分转互生币明细查看
		getPvToHsbInfo : function(data, callback){
			comm.requestFun("getPvToHsbInfo" , data, callback,comm.lang("accountManage"));
		},
		//查询积分投资分红详情
		hsbADPointDividendInfo : function(data,callback){
			comm.requestFun("hsbADPointDividendInfo",data,callback,comm.lang("accountManage"));
		},
		//获取年费订单详情
		hsbADAnnualFeeOrder : function(data,callback){
			comm.requestFun("hsbADAnnualFeeOrder",data,callback,comm.lang("accountManage"));
		},
		//查询线下兑换互生币详情
		hsbADGetOrder : function(data,callback){
			comm.requestFun("hsbADGetOrder",data,callback,comm.lang("accountManage"));
		},
		//查询互生币转货币详情
		hsbADHsbToCashInfo : function(data,callback){
			comm.requestFun("hsbADHsbToCashInfo",data,callback,comm.lang("accountManage"));
		},
		//查询网银，货币兑换互生币详情
		hsbADBuyHsbInfo : function(data,callback){
			comm.requestFun("hsbADBuyHsbInfo",data,callback,comm.lang("accountManage"));
		},
		//消费积分扣除统计查询
		reportsPointDeduction:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "reportsPointDeduction", params, callback);
		},
		//消费积分扣除终端统计查询
		reportsPointDeductionByChannel:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "reportsPointDeductionByChannel", params, callback);
		},
		//消费积分扣除操作员统计查询
		reportsPointDeductionByOper:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "reportsPointDeductionByOper", params, callback);
		},
		//消费积分扣除统计查询汇总
		reportsPointDeductionSum:function(params,callback){
			 comm.requestFun("reportsPointDeductionSum",params,callback,comm.lang("accountManage"));
		},
		//消费积分扣除统计查询汇总
		reportsPointDeductionByChannelSum:function(params,callback){
			 comm.requestFun("reportsPointDeductionByChannelSum",params,callback,comm.lang("accountManage"));
		},
		//消费积分扣除统计查询汇总
		reportsPointDeductionByOperSum:function(params,callback){
			 comm.requestFun("reportsPointDeductionByOperSum",params,callback,comm.lang("accountManage"));
		},
		//获取终端编号列表
		getPosDeviceNoList:function(params,callback){
			 comm.requestFun("getPosDeviceNoList",params,callback,comm.lang("accountManage"));
		},
		//获取终端编号列表
		getOperaterNoList:function(params,callback){
			 comm.requestFun("getOperaterNoList",params,callback,comm.lang("accountManage"));
		}
		
	};
});