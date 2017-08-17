define(function () {
	return {
		//订单分页
		businessOrderPage:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"businessOrderPage",params,comm.lang("receivablesmanage"),detail,del, add, edit);
		},
		//查找订单明细
		getOrderDetail:function(data, callback){
			comm.requestFun("getOrderDetail",data,callback,comm.lang("receivablesmanage"));
		},
		//订单关闭
		closeOrder:function(data, callback){
			comm.requestFun("closeOrder",data,callback,comm.lang("receivablesmanage"));
		},
		//分页查询年费业务单
		annualFeeOrderPage:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"annualFeeOrderPage",params,comm.lang("receivablesmanage"),detail,del, add, edit);
		},
		//查找年费订单明细
		queryAnnualFeeOrder:function(data, callback){
			comm.requestFun("queryAnnualFeeOrder",data,callback,comm.lang("receivablesmanage"));
		},
		//系统销售费分页查询
		debtOrderPage:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"debtOrderPage",params,comm.lang("receivablesmanage"),detail,del, add, edit);
		},
		//导出订单收款记录URL
		exportBusinessOrder:function(data){
			//增加token验证参数
			data.token = comm.getRequestParams()["token"];
			data.custId = comm.getRequestParams()["custId"];
			data.channelType = comm.getRequestParams()["channelType"];
			
			var param="?";
			for(var str in data){ 
				if(data[str]!=""){
					param+=str+"="+data[str]+"&";
				}
			}
			
			return comm.domainList["apsWeb"]+comm.UrlList["exportBusinessOrder"]+param;
		},
		//网银收款对账-收款管理订单列表
		findPaymentOrderList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findPaymentOrderList", params, comm.lang("receivablesmanage"), detail);
		},
		//网银收款对账-收款管理数据对帐
		dataReconciliation:function(data, callback){
			comm.requestFun("dataReconciliation", data, callback, comm.lang("receivablesmanage"));
		},
		//网银收款对账-收款管理订单详情
		findPaymentOrderInfo:function(data, callback){
			comm.requestFun("findPaymentOrderInfo", data, callback, comm.lang("receivablesmanage"));
		},
		//获取临帐支付详情
		getTempAcctPayInfo:function(data, callback){
			comm.requestFun("getTempAcctPayInfo", data, callback, comm.lang("receivablesmanage"));
		},
		//获取操作员信息
		findOrderOperator:function(params, callback){
			comm.requestFun("findOrderOperator", params, callback, comm.lang("receivablesmanage"));
		}
	};
});