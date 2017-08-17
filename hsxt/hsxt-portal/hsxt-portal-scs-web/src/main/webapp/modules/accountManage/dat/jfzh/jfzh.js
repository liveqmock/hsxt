define(function () {
	return {
		//积分账户余额查询
		findIntegralBalance : function (data,callback) {
			comm.requestFun("findIntegralBalance" , data, callback,comm.lang("accountManage"));
		},
		
		//初始化积分转互生币账户
		initIntegralTransferHsb : function (data, callback) {
			comm.requestFun("initIntegralTransferHsb", data, callback,comm.lang("accountManage"));
			
		},
		
		//提交积分转互生币
		integralTransferHsb: function (data, callback) {
			comm.requestFun("integralTransferHsb", data, callback,comm.lang("accountManage"));
		},
		//提交积分投资信息。
		integralInvestmentInfo : function (data,callback) {
			comm.requestFun("integralInvestmentInfo" , data, callback,comm.lang("accountManage"));
		},
		//积分投资界面初始化信息
		initIntegralInvestment : function (data,callback) { 
			comm.requestFun("initIntegralInvestment" , data, callback,comm.lang("accountManage"));
		},
		
		//交易密码查询判断是否是新设置密码
		dealPswQuery : function (callback) {
			callback({"code":0});
		},
		
		//积分转现互生币确认页面
		pointToHsbConfirmInfo : function (data, callback) {
			
			comm.requestFun("pointToHsbConfirmInfo" , data, callback,comm.lang("accountManage"));
			
		},
		
		
		//积分账号-明晰查询
		findIntegralList : function () {
			//拼装查询调用的url
			return comm.domainList.personWeb + comm.UrlList.findIntegralList;
		},
		
		
		
		//查询详细
		getSearchDetail: function (data, callback) {
			callback({"code":0,"msg":"","data":{"evidenceNo":"","transDate":"2015/08/01 12:05:32","transCode":"P_HSB_TO_CASH","transAmount":"0.1","imAmount":"0.1","toRatio":"0.1","tcurrencyCode":"156","toAmount":"1","balance":"1","remark":"test-test"}});
		},
		//消费积分撤单
		getdealAndPointDetailList: function (data, callback) {
			callback({"code":0,"msg":"","data":{"evidenceNo":"","transDate":"2015/08/01 12:05:32","transCode":"P_HSB_TO_CASH","transAmount":"0.1","imAmount":"0.1","toRatio":"0.1","tcurrencyCode":"156","toAmount":"1","balance":"1","remark":"test-test"}});
		},
		//积分账户货币分页查询
		detailedPage:function(params,callback){
			return comm.getCommBsGrid("tableDetail", "pointDetailedPage", params, comm.lang("accountManage"),callback);
		},
		//账户操作明细详单
		getAccOptDetailed : function (data, callback) {
			comm.requestFun("getAccOptDetailed",data,callback,comm.lang("accountManage"));
		},
		//查询消费积分分配详情
		getPointAllotDetailedList : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"getPointAllotDetailedList" , data,comm.lang("accountManage"), callback);
		},
		//查询消费积分分配详情
		getPointAllotDetailed : function (data, callback) {
			comm.requestFun("getPointAllotDetailed" , data, callback,comm.lang("accountManage"));
		},
		//分页查询再增值积分汇总列表
		queryBmlmListPage : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"queryBmlmListPage", data, comm.lang("accountManage"),callback);
		},
		//分页查询互生积分分配列
		queryMlmListPage : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"queryMlmListPage", data, comm.lang("accountManage"),callback);
		}
	};
});