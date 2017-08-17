define(function () {
	return {
		//积分账户
		//查询积分账户余额数
		findzhye : function (data, callback) {
			comm.requestFun("queryzhye",data,callback,comm.lang("accountManage"));
		},
		//初始化积分转互生币
		init_integral_transfer_Hsb : function (data,callback) {
			comm.requestFun("init_integral_transfer_Hsb",data,callback,comm.lang("accountManage"));
		},
		//积分转互生币提交
		commitJfzhsb : function (data,callback) {
			comm.requestFun("commitJfzhsb",data,callback,comm.lang("accountManage"));
		},
		//初始化积分投资
		init_integral_investment : function (data,callback) {
			comm.requestFun("init_integral_investment",data,callback,comm.lang("accountManage"));
		},
		//积分投资
		commitJftz : function (data,callback){
			comm.requestFun("commitJftz",data,callback,comm.lang("accountManage"));
		},
		
		//互生币账户
		//互生币账户余额查询
		find_hsb_blance : function (data, callback) {
			comm.requestFun("find_hsb_blance",data,callback,comm.lang("accountManage"));
		},
		//初始化货币转互生币
		init_transfer_hsb : function (data,callback) {
			comm.requestFun("init_transfer_hsb",data,callback,comm.lang("accountManage"));
		},
		//货币转互生币 提交
		transfer_hsb : function (data,callback) {
			comm.requestFun("transfer_hsb",data,callback,comm.lang("accountManage"));
		},
		//初始化互生币转货币页面
		init_hsb_transfer_currency : function (data,callback) {
			comm.requestFun("init_hsb_transfer_currency",data,callback,comm.lang("accountManage"));
		},
		//互生币转货币提交
		hsb_transfer_currency : function (data,callback){
			comm.requestFun("hsb_transfer_currency",data,callback,comm.lang("accountManage"));
		},
		//获取绑定的快捷支付银行
		getBandQuickBank : function(data,callback){ 
			comm.requestFun("get_band_quick_bank",data,callback,comm.lang("accountManage"));
		},
		//获取支持快捷支付的银行列表
		getQuickPayBank : function(data,callback){ 
			comm.requestFun("get_quick_pay_bank",data,callback,comm.lang("accountManage"));
		},
		/*//开通快捷支付
		openQuickPay : function(data,callback){ 
			comm.requestFun("open_quick_pay",data,callback,comm.lang("accountManage"));
		},*/
		//发送快捷支付短信
		sendQuickPaySms : function(data,callback){ 
			comm.requestFun("send_quick_pay_sms",data,callback,comm.lang("accountManage"));
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
		//初始化支付限额
		payLimitSetting : function(data,callback){
			comm.requestFun("payLimitSetting",data,callback,comm.lang("accountManage"));
		},
		//支付限额修改
		payLimitUpdate : function(data,callback){
			comm.requestFun("payLimitUpdate",data,callback,comm.lang("accountManage"));
		},
		
		//货币账户
		//货币账户余额查询
		find_currency_blance : function (data, callback) {
			comm.requestFun("find_currency_blance",data,callback,comm.lang("accountManage"));
		},
		//初始化货币转银行界面
		init_currency_transfer_bank : function (data,callback) {
			comm.requestFun("init_currency_transfer_bank",data,callback,comm.lang("accountManage"));
		},
		//初始化货币转银行确认界面
		getBankTransFee : function (data,callback) {
			comm.requestFun("getBankTransFee",data,callback,comm.lang("accountManage"));
		},
		//货币转银行 提交
		currency_transfer_bank : function (data,callback) {
			comm.requestFun("currency_transfer_bank",data,callback,comm.lang("accountManage"));
		},
		
		//投资账户
		//投资账户余额查询
		query_investment_blance : function (data, callback) {
			comm.requestFun("query_investment_blance",data,callback,comm.lang("accountManage"));
		},
		getPointInvestList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"getPointInvestList",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		getPointDividendList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"getPointDividendList",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		getDividendDetailList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"getDividendDetailList",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		getInvestDividendInfo : function (data, callback) {
			comm.requestFun("getInvestDividendInfo",data,callback,comm.lang("accountManage"));
		},
		
		//验证码生成URL地址
		getCodeUrl:function(){
			var param=comm.getRequestParams();
			return comm.domainList['companyWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=payLimist&"+(new Date()).valueOf();
//			var param=comm.getRequestParams();
//			return comm.domainList['companyWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=tradePwd&"+(new Date()).valueOf();
		},
		//查询网上商城销售收入详情流水
		getMallDetail : function (gridId,params, callback) {
			return comm.getCommBsGrid(gridId,"getMallDetail", params, callback);
		},
		//查询线下销售收入详情流水
		getSaleDetail : function (gridId,params, callback) {
			return comm.getCommBsGrid(gridId,"getSaleDetail", params, callback);
		},
		//账户明细查询
		detailedPage:function(gridId,params,callback,callback2){
			return comm.getCommBsGrid(gridId, "detailedPage", params, callback,callback2);
		},
		//账户操作明细详单
		getAccOptDetailed : function (data, callback) {
			comm.requestFun("get_acc_opt_detailed",data,callback,comm.lang("accountManage"));
		},
		//平安银行支付
		pingAnPay : function(data,callback){
			comm.requestFun("pingAnPay",data,callback,comm.lang("accountManage"));
		}
	};
});