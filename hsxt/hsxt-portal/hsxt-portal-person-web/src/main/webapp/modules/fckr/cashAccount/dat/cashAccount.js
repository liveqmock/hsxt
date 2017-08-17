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
		
		//货币账户余额查询
		findCurrencyBlance : function (data,callback) { 
			var jsonParam = this.getParameters(data);
			comm.requestFun("findCurrencyBlance" , jsonParam, callback,comm.lang("cashAccount"));
		},
		
		//货币账户-货币转银行，初始化界面
		initCurrencyTransferBank : function(data, callback){
			var jsonParam = this.getParameters(data);
			comm.requestFun("initCurrencyTransferBank" , jsonParam, callback,comm.lang("cashAccount"));
		},
		//货币账户-货币转银行，初始化界面
		getBankTransFee : function(data, callback){
			var jsonParam = this.getParameters(data);
			comm.requestFun("getBankTransFee" , jsonParam, callback,comm.lang("cashAccount"));
		},
		//确认转出返回信息  互生币转现提交
		currencyTransferBank: function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("currencyTransferBank" , jsonParam, callback,comm.lang("cashAccount"));
		},
		
		//货币账户-货币转银行，查询绑定银行信息
		queryBankAccount : function (data, callback) {
			comm.Request({url : 'findBankBindList', domain : 'personWeb'},{
				data : data,
				success :function(response){
					callback(response);
				}
			});
			//callback({"banklist":[{"bank":"中国建设银行","accountNo":"1234199011220011","currency":"人民币","bankAddr":"深圳市南山区", "isDefault":"Y","usedFlag":"N","id":"1"}]});
		},
		//获取现金转账初始化数据
		accountCash : function (callback) {
			//comm.requestPost('', callback);
			callback({"bank":[{"bankCode":"111","defaultFlag":"N","usedFlag":"N","bankAccount":"1234567890123456"}],"available":"100","code":0});
		},
		//获取银行名称
		getBankName : function (bankNo) {
			var bankName = "";
			$.each(comm.getCache("personWeb", "cache").bankList, function (i, o) {
				if (o.bankCode == bankNo) {
					bankName = o.bankName;
					return false;
				}
			});
			return bankName;
		},
		//货币转银行确认信息
		getCashCashConfimInfo : function (data, callback) {
			comm.Request({url : 'getCashCashConfimInfo', domain : 'personWeb'},{
				data : data,
				success :function(response){
					callback(response);
				}
			});
			//callback({"code":0,"msg":"","verifyStatus":"Y","regStatus":"Y","importantInfoStatus":"Y","quantity":"100","bankAcctId":"","bankAccount":"1234567890123456","bankAcctName":"","bankCode":"111","cityName":"深圳","currencyName":"人民币","currFee":"1","realMoneyNum":99});
		},
		//交易密码查询判断是否是新设置密码
		dealPswQuery : function (callback) {
			//comm.requestPost('dealPswQuery', callback)
			callback({"code":0});
		},
		
		//获取明细数据
		queryDetailsByTransNo: function (data, callback) {
			comm.requestFun("queryDetailsByTransNo",data,callback,comm.lang("cashAccount"));
		},
		getcashToBankDetailList: function (data, callback) {
			//comm.requestPost('dealPswQuery', data, callback)
			callback({});
		},
		getcashToBankFailDetailList: function (data, callback) {
			//comm.requestPost('dealPswQuery', data, callback)
			callback({});
		},
		
		pointDetailQuery : function (data, callback) {
			comm.Request({url : 'pointDetailQuery', domain : 'personWeb'},{
				data : data,
				success :function(response){
					callback(response);
				}
			});
		},
		//货币账户分页查询
		detailedPage:function(params,callback){
			return comm.getCommBsGrid("scash_result_table", "cashDetailedPage", params, comm.lang("hsbAccount"),callback);
		}
	};
});