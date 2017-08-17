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
		//退出
		loginOff : function (callback) {
			//comm.requestPost('loginOff', callback)
			callback({"code": 0});
		},
		//检测互生卡
		checkHSCard : function (callback) {
			/*comm.requestPost({
				url : '/home/checkhsCard.do?' + new Date().getTime(),
				domain : 'hsportal'
			}, callback)*/
			callback({"data":true,"msg":null,"retCode":200});
		},
		//获取预留信息
		getReserveInfo : function (data,callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("queryDealPwdAndReserveInfo",jsonParam,callback,comm.lang("safetySet"));
		},
		getLoginInfo : function(callback) {
			callback({"loginIp":$.cookie('lastLoginIp'),"lastLoginDate":$.cookie('lastLoginDate'),"nickName":$.cookie('custName'),"loginResNo":$.cookie('resNo')});
		},
		
		//加载所有国家到js缓存中 
		findContryAll : function (data,callback){
			comm.requestFun("findContryAll",data,callback,comm.lang("errorCodeMgs"));
		},
		
		//根据国家获取省份信息
		findProvinceByParent : function (data,callback){
			comm.requestFun("findProvinceByParent",data,callback,comm.lang("errorCodeMgs"));
		},
		
		//获取所有的银行列表信息
		findBankAll : function (data,callback){
			comm.requestFun("findBankAll",data,callback,comm.lang("errorCodeMgs"));
		},
		
		//获取本平台的基本信息
		findSystemInfo : function (data,callback){
			comm.requestFun("findSystemInfo",data,callback,comm.lang("errorCodeMgs"));
		},
		
		//根据国家省份获取下属城市
		findCityByParent : function (data,callback){
			comm.requestFun("findCityByParent",data,callback,comm.lang("errorCodeMgs"));
		},
		/** 获取操作员信息 */
		operatorDetail : function (callBack,errorCallBack){
			comm.Request({url:"operatorDetail", domain:comm.getProjectName()},{
				data:comm.getRequestParams(),
				type:'POST',
				async:false,
				dataType:"json",
				success:function(response){
					//非空验证
					if (response.retCode ==22000){
						callBack(response);	
					}else{
						errorCallBack(response);
					}
				},
				error: function(){
					errorCallBack(null);
				}
			});
			
		},
		
		//根据国家代码-省份代码-城市代码获取对应名称--同步
		getRegionByCode : function (params){
			return comm.asyncRequestFun("getRegionByCode", params, comm.lang("common"));
		},
		
		//根据国家代码-根据国家代码获取省份城市信息--同步
		findProvCity : function (params){
			return comm.asyncRequestFun("findProvCity", params, comm.lang("common"));
		},
		
		//根据国家代码-省份代码-城市代码获取对应名称--异步
		syncGetRegionByCode : function (params, callBack){
			comm.requestFun("getRegionByCode", params, callBack, comm.lang("common"));
		},
		
		//查询快捷支付银行列表
		findPayBankAll : function (params, callBack){
			comm.requestFun("findPayBankAll", params, callBack, comm.lang("common"));
		},
		/** 获取示例图片列表 */
		findDocList : function (callBack){
			comm.requestFun("findDocList", null, callBack, comm.lang("common"));
		},
		//获取 用户注册信息
		findConsumerInfo : function (jsonParam, callback) {
			comm.requestFun("queryConsumerInfo" , jsonParam, callback,comm.lang("common"));
		}
	};
});