define(function () {
	return {
		//获得文件在文件系统服务器中的url
		getFsServerUrl : function(fileId) {
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			
			return  comm.domainList['fsServerUrl']+fileId+"?userId="+custId+"&token="+token;
		},
		//查询企业年费缴纳信息
		searchEntAnnualfeeInfo : function(data,callback){
			comm.requestFun("find_entannualfee_info",data,callback,comm.lang("businessHandling"));
		},
		//查询订单详情
		queryAnnualfeeOrder : function(data,callback){
			comm.requestFun("query_annualfee_order",data,callback,comm.lang("businessHandling"));
		},
		//缴纳年费
		payAnnualFee : function(data,callback){
			comm.requestFun("pay_annualfee_info",data,callback,comm.lang("businessHandling"));
		},
		//缴纳系统使用年费
		createAnnualFeeOrder : function(data,callback){
			comm.requestFun("create_annualfee_order",data,callback,comm.lang("businessHandling"));
		},
		//获取绑定的快捷支付银行
		getBandQuickBank : function(data,callback){ 
			comm.requestFun("get_band_quick_bank",data,callback,comm.lang("systemBusiness"));
		},
		//获取支持快捷支付的银行列表
		getQuickPayBank : function(data,callback){ 
			comm.requestFun("get_quick_pay_bank",data,callback,comm.lang("systemBusiness"));
		},
		//开通快捷支付
		openQuickPay : function(data,callback){ 
			comm.requestFun("open_quick_pay",data,callback,comm.lang("systemBusiness"));
		},
		//发送快捷支付短信
		sendQuickPaySms : function(data,callback){ 
			comm.requestFun("send_quick_pay_sms",data,callback,comm.lang("systemBusiness"));
		},
		//查询年费区间
		searchAnnualInterval : function(data,callback){
			comm.requestFun("find_entannual_interval",data,callback,comm.lang("businessHandling"));
		},
		//缴纳年费
		initLocalInfo : function(data,callback){
			comm.requestFun("init_LocalInfo",data,callback,comm.lang("businessHandling"));
		},
	};
});