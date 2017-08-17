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
		//互生币账户余额查询
		findHsbBlance : function (data,callback) { 
			var jsonParam = this.getParameters(data);
			comm.requestFun("findHsbBlance" , jsonParam, callback,comm.lang("hsbAccount"));
		},
		
		//互生币转货币-界面初始化
		initHsbTransferCurrency : function (data,callback) { 
			var jsonParam = this.getParameters(data);
			comm.requestFun("initHsbTransferCurrency" , jsonParam, callback,comm.lang("hsbAccount"));
		},
		
		//互生币转货币-提交数据
		hsbTransferCurrency : function (data,callback) { 
			var jsonParam = this.getParameters(data);
			comm.requestFun("hsbTransferCurrency" , jsonParam, callback,comm.lang("hsbAccount"));
		},
		
		//兑换互生币-界面初始化
		initTransferHsb : function(data,callback){
			comm.requestFun("initTransferHsb" , data, callback,comm.lang("hsbAccount"));
		},
		
		//兑换互生币-数据提交
		transferHsb : function(data,callback){
			comm.requestFun("transferHsb" , data, callback,comm.lang("hsbAccount"));
		},
		
		//查询客户绑定的快捷支付
		bankDetailSearch : function(data,callback){
			comm.requestFun("bankDetailSearch" , data, callback,comm.lang("hsbAccount"));
		},
		
		//互生币账户货币分页查询
		detailedPage:function(params,callback){
			return comm.getCommBsGrid("shtc_result_table", "hsbDetailedPage", params, comm.lang("hsbAccount"),callback);
		},
		//查询详情
		queryDetailsByTransNo : function(data,callback){
			comm.requestFun("queryDetailsByTransNo",data,callback,comm.lang("hsbAccount"));
		},
		 //查找PS的交易类型
		getHsbTransType : function(data,callback){
			comm.requestFun("getHsbTransType",data,callback,comm.lang("hsbAccount"));
		},
		//初始化支付限额
		payLimitSetting : function(data,callback){
			comm.requestFun("payLimitSetting",data,callback,comm.lang("hsbAccount"));
		},
		//支付限额修改
		payLimitUpdate : function(data,callback){
			comm.requestFun("payLimitUpdate",data,callback,comm.lang("hsbAccount"));
		},
		//验证码生成URL地址
		getCodeUrl:function(){
			var param=comm.getRequestParams();
			return comm.domainList['personWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=payLimist&"+(new Date()).valueOf();
		},
		//支付限额验证码生成URL地址
		getCodeUrlPayLimist:function(){
			var param=comm.getRequestParams();
			return comm.domainList['personWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=payLimist&"+(new Date()).valueOf();
		}
	};
});