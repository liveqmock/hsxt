define(function () {
	return {
		//获得参数列表
		getParameters : function (paraData){
			
			//json用户身份参数构造
			var jsonParam={
					custName:"张三",						//用户名
					custId 	:"0618601000620151130",		//客户号
					hsResNo :"06186010006", 			//互生号
					token 	:"bdc6f862cf491d02"			//随机token
			};
			
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
		getAssetValuePoint : function (data,callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("findIntegralBalance" , jsonParam, callback,comm.lang("pointAccount"));
		},
		
		//初始化积分转互生币账户
		initIntegralTransferHsb : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("initIntegralTransferHsb", jsonParam, callback,comm.lang("pointAccount"));
		},
		
		//提交积分转互生币
		integralTransferHsb: function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("integralTransferHsb", jsonParam, callback,comm.lang("pointAccount"));
		},
		//提交积分投资信息。
		integralInvestmentInfo : function (data,callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("integralInvestmentInfo" , jsonParam, callback,comm.lang("pointAccount"));
		},
		//积分投资界面初始化信息
		initIntegralInvestment : function (data,callback) { 
			var jsonParam = this.getParameters(data);
			comm.requestFun("initIntegralInvestment" , jsonParam, callback,comm.lang("pointAccount"));
		},
		
		//交易密码查询判断是否是新设置密码
		dealPswQuery : function (callback) {
			//comm.requestPost('dealPswQuery', callback)
			callback({"code":0});
		},
		
		//积分转现互生币确认页面
		pointToHsbConfirmInfo : function (data, callback) {
			comm.requestFun("pointToHsbConfirmInfo" , data, callback,comm.lang("pointAccount"));
		},
		
		
		//积分账号-明晰查询
		findIntegralList : function () {
			//拼装查询调用的url
			return comm.domainList.personWeb + comm.UrlList.findIntegralList;
		},
		//积分账户货币分页查询
		detailedPage:function(params,callback){
			return comm.getCommBsGrid("spoint_result_table", "pointDetailedPage", params, comm.lang("pointAccount"),callback);
		},
		queryDetailsByTransNo : function (data, callback) {
			comm.requestFun("queryDetailsByTransNo" , data, callback,comm.lang("pointAccount"));
		}
	};
});