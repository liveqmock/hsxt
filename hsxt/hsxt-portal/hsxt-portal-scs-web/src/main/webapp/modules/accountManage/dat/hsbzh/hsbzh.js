define(function () {
	return {
		//互生币账户余额查询
		findHsbBlance : function (data,callback) { 
			comm.requestFun("findHsbBlance" , data, callback,comm.lang("accountManage"));
		},
		//互生币转货币-界面初始化
		initHsbTransferCurrency : function (data,callback) { 
			comm.requestFun("initHsbTransferCurrency" , data, callback,comm.lang("accountManage"));
		},
		
		//互生币转货币-提交数据
		hsbTransferCurrency : function (data,callback) { 
			comm.requestFun("hsbTransferCurrency" , data, callback,comm.lang("accountManage"));
		},
		
		//兑换互生币-界面初始化
		initTransferHsb : function(data,callback){
			comm.requestFun("initTransferHsb" , data, callback,comm.lang("accountManage"));
		},
		
		//兑换互生币-数据提交
		transferHsb : function(data,callback){
			comm.requestFun("transferHsb" , data, callback,comm.lang("accountManage"));
		},
		
		//查询客户绑定的快捷支付
		bankDetailSearch : function(data,callback){
			comm.requestFun("bankDetailSearch" , data, callback,comm.lang("accountManage"));
		},
		//初始化货币转互生币
		init_transfer_hsb : function (data,callback) {
			comm.requestFun("init_transfer_hsb",data,callback,comm.lang("accountManage"));
		},
		//货币转互生币 提交
		transfer_hsb : function (data,callback) {
			comm.requestFun("transfer_hsb",data,callback,comm.lang("accountManage"));
		},
		
		//获取绑定的快捷支付银行
		getBandQuickBank : function(data,callback){ 
			comm.requestFun("get_band_quick_bank",data,callback,comm.lang("accountManage"));
		},
		//获取支持快捷支付的银行列表
		getQuickPayBank : function(data,callback){ 
			comm.requestFun("get_quick_pay_bank",data,callback,comm.lang("accountManage"));
		},
		//兑换互生币货币支付
		validateHbPay : function(data,callback){
			comm.requestFun("validate_HbPay",data,callback,comm.lang("accountManage"));
		},
		//兑换互生币开通快捷并支付
		openQuickPay : function(data,callback){
			comm.requestFun("open_QuickPay",data,callback,comm.lang("accountManage"));
		},
		//兑换互生币快捷支付
		validatePayPwd : function(data,callback){
			comm.requestFun("validate_PayPwd",data,callback,comm.lang("accountManage"));
		},
		//兑换互生币网银支付
		netPay : function(data,callback){
			comm.requestFun("net_Pay",data,callback,comm.lang("accountManage"));
		},
		//获取快捷支付验证码
		quickPayVerifyCode : function(data,callback){
			comm.requestFun("quickPayVerifyCode",data,callback,comm.lang("accountManage"));
		},
		
		//互生币账户明细查询
		detailedPage:function(params,callback){
			return comm.getCommBsGrid("tableDetail", "hsbDetailedPage", params, comm.lang("accountManage"),callback);
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
		//查询积分转互生币详情
		hsbADPvToHsbInfo : function(data,callback){
			comm.requestFun("hsbADPvToHsbInfo",data,callback,comm.lang("accountManage"));
		},
		//互生币兑换支付
		convertPay : function(data,callback){
			comm.requestFun("convertPay",data,callback,comm.lang("accountManage"));
		}
	};
});