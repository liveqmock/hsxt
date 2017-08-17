define(function () {
	return {
		//销售资格证书盖章
		sellCertificateSeal : function(data,callback){
			comm.requestFun("sell_certificate_seal",data,callback,comm.lang("certificate"));
		},
		//根据ID查询销售资格证书
		findSellCertificateById : function(data,callback){
			comm.requestFun("find_sell_certificate_by_id",data,callback,comm.lang("certificate"));
		},
		//查询销售资格证书内容
		findSellCertificateContent : function(data,callback){
			comm.requestFun("find_sell_certificate_content",data,callback,comm.lang("certificate"));
		},
		//打印销售资格证书
		printSellCertificate : function(data,callback){
			comm.requestFun("print_sell_certificate",data,callback,comm.lang("certificate"));
		},
		//销售资格证书发放
		giveOutSellCertificate : function(data,callback){
			comm.requestFun("give_out_sell_certificate",data,callback,comm.lang("certificate"));
		},
		//查询销售资格证书发放历史
		findSellCertificateGiveOutRecode : function(data,callback){
			comm.requestFun("find_sell_certificate_give_out_recode",data,callback,comm.lang("certificate"));
		},
		//打印第三方证书
		printThirdCertificate : function(data,callback){
			comm.requestFun("print_third_certificate",data,callback,comm.lang("certificate"));
		},
		//发放第三方证书
		giveOutThirdCertificate : function(data,callback){
			comm.requestFun("give_out_third_certificate",data,callback,comm.lang("certificate"));
		},
		//查询第三方证书发放历史
		queryThirdCertificateGiveOutRecode : function(data,callback){
			comm.requestFun("find_third_certificate_give_out_recode",data,callback,comm.lang("certificate"));
		},
		//新增证书模板
		createCertificateTemp : function(data,callback){
			comm.requestFun("create_certificate_temp",data,callback,comm.lang("certificate"));
		},
		//修改证书模板
		modifyCertificateTemp : function(data,callback){
			comm.requestFun("modify_certificate_temp",data,callback,comm.lang("certificate"));
		},
		//根据ID查询证书模板
		findCertificateTempById : function(data,callback){
			comm.requestFun("find_certificate_temp_by_id",data,callback,comm.lang("certificate"));
		},
		//启用证书模板
		enableCertificateTemp : function(data,callback){
			comm.requestFun("enable_certificate_temp",data,callback,comm.lang("certificate"));
		},
		//停用证书模板
		stopCertificateTemp : function(data,callback){
			comm.requestFun("stop_certificate_temp",data,callback,comm.lang("certificate"));
		},
		//证书模板审批
		certificateTempAppr : function(data,callback){
			comm.requestFun("certificate_temp_appr",data,callback,comm.lang("certificate"));
		},
		//根据ID查询证书
		findCertificateContentById : function(data,callback){
			comm.requestFun("find_certificate_content_by_id",data,callback,comm.lang("certificate"));
		},
		//查询证书模板最新审核记录详情
		queryCertiLastTemplateAppr : function(data,callback){
			comm.requestFun("queryCertiLastTemplateAppr",data,callback,comm.lang("certificate"));
		}
	};
});