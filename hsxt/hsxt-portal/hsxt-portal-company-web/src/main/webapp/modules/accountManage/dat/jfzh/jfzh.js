define(function () {
	return {
		//获得参数列表
		getParameters : function (paraData){
			
			//json用户身份参数构造
			var jsonParam={};
			
			//传递过来的参数继承JsonParam
			if(typeof(paraData) != "undefined" && paraData != null )
			{
				 return $.extend(jsonParam,paraData);
			}
			else 
			{
				return jsonParam;
			}
			
		},	
		//积分账户余额查询
		findIntegralBalance : function (data,callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("findIntegralBalance" , jsonParam, callback,comm.lang("accountManage"));
		},
		
		//初始化积分转互生币账户
		initIntegralTransferHsb : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("initIntegralTransferHsb", jsonParam, callback,comm.lang("accountManage"));
			
		},
		
		//提交积分转互生币
		integralTransferHsb: function (data, callback) {
			var jsonParam = this.getParameters(data);
			
			comm.requestFun("integralTransferHsb", jsonParam, callback,comm.lang("accountManage"));
		},
		//提交积分投资信息。
		integralInvestmentInfo : function (data,callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("integralInvestmentInfo" , jsonParam, callback,comm.lang("accountManage"));
		},
		//积分投资界面初始化信息
		initIntegralInvestment : function (data,callback) { 
			var jsonParam = this.getParameters(data);
			comm.requestFun("initIntegralInvestment" , jsonParam, callback,comm.lang("accountManage"));
		},
		
		//交易密码查询判断是否是新设置密码
		dealPswQuery : function (callback) {
			//comm.requestPost('dealPswQuery', callback)
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
			//comm.requestPost('', data, callback)
			callback({"code":0,"msg":"","data":{"evidenceNo":"","transDate":"2015/08/01 12:05:32","transCode":"P_HSB_TO_CASH","transAmount":"0.1","imAmount":"0.1","toRatio":"0.1","tcurrencyCode":"156","toAmount":"1","balance":"1","remark":"test-test"}});
		},
		//消费积分撤单
		getdealAndPointDetailList: function (data, callback) {
			//comm.requestPost('', data, callback)
			callback({"code":0,"msg":"","data":{"evidenceNo":"","transDate":"2015/08/01 12:05:32","transCode":"P_HSB_TO_CASH","transAmount":"0.1","imAmount":"0.1","toRatio":"0.1","tcurrencyCode":"156","toAmount":"1","balance":"1","remark":"test-test"}});
		},
		//积分账户货币分页查询
		detailedPage:function(params,callback){
			return comm.getCommBsGrid("tableDetail", "pointDetailedPage", params, comm.lang("accountManage"),callback);
		},
		//积分账户操作详情
		getAccOptDetailed : function (data, callback) {
			comm.requestFun("getPointAccOptDetailed" , data, callback,comm.lang("accountManage"));
		},
		//查询消费积分分配详情
		getPointAllotDetailedList : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"getPointAllotDetailedList", data, callback);
		},
		getPointAllotDetailed:function (data, callback) {
			comm.requestFun("getPointAllotDetailed" , data, callback,comm.lang("accountManage"));
		},
		
		//分页查询再增值积分汇总列表
		queryBmlmListPage : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"queryBmlmListPage", data, callback);
		},
		//分页查询互生积分分配列
		queryMlmListPage : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"queryMlmListPage", data, callback);
		}
		
	};
});