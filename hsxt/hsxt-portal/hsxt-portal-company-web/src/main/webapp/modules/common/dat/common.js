define(function(){
	return {
		//安全退出
		loginOff : function(callback){
			callback();
		},
		//查询密码长度配置(交易密码,登录密码)
		querjymmylxxsz : function (data, callback) {
			comm.requestFun("get_is_safe_set",data,callback,comm.lang("safeSet"));
		},
		//获取地区平台信息
		getLocalInfo : function(callback){
			comm.requestFun("get_localInfo",null,callback,comm.lang("common"));
		},
		//获取国家列表
		getCountryAll : function(callback){
			comm.requestFun("get_country_all",null,callback,comm.lang("common"));
		},
		//获取国家下省列表
		getProvinceList : function(param,callback){
			comm.requestFun("get_province_list",param,callback,comm.lang("common"));
		},
		//获取省下城市列表
		getCityList : function(param,callback){
			comm.requestFun("get_city_list",param,callback,comm.lang("common"));
		},
		//根据城市编号查询城市信息
		getCityById : function(param,callback){
			comm.requestFun("get_city_by_id",param,callback,comm.lang("common"));
		},
		//获取银行列表
		getBankList : function(callback){
			comm.requestFun("get_bank_list",null,callback,comm.lang("common"));
		},
		
		//公共部分-根据客户号查询操作员名称--异步lyh
		searchOperByCustId : function (params, callBack){
			return comm.requestFun("searchOperByCustId", params, callBack, comm.lang("common"));
		},
		
		//根据国家代码-省份代码-城市代码获取对应名称--同步
		getRegionByCode : function (params){
			return comm.asyncRequestFun("getRegionByCode", params, comm.lang("common"));
		},
		
		//根据国家代码-根据国家代码获取省份城市信息--同步
		findProvCity : function (params){
			return comm.asyncRequestFun("findProvCity", params, comm.lang("common"));
		},
		
		//查询快捷支付银行列表
		findPayBankAll : function (params, callBack){
			comm.requestFun("findPayBankAll", params, callBack, comm.lang("common"));
		},
		
		//根据国家代码-省份代码-城市代码获取对应名称--异步
		syncGetRegionByCode : function (params, callBack){
			comm.requestFun("getRegionByCode", params, callBack, comm.lang("common"));
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
		//查询企业状态
		searchEntStatusInfo : function(data,callback){
			comm.requestFun("query_ent_status",data,callback,comm.lang("systemBusiness"));
		},
		/** 获取示例图片列表 */
		findDocList : function (callBack){
			comm.requestFun("findDocList", null, callBack, comm.lang("common"));
		},
		//根据地区信息获取对应名称--同步
		getAreaSplitJoint : function (params){
			return comm.asyncRequestFun("getAreaSplitJoint", params, comm.lang("common"));
		},
		/**根据客户号获取当前用户下的所有菜单 */
		findPermissionByCustidList : function (callBack){
			comm.requestFun("findPermissionByCustidList", null, callBack, comm.lang("common"));
		},
		/**
		 * 查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findEntAllInfo : function(params, callback){
			comm.requestFun("findEntAllInfo", params, callback, comm.lang("companyInfo"));
		}
		 
	};
});