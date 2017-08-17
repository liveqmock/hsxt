define(function(){
	return {
		/**
		 * 查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findEntAllInfo : function(params, callback){
			comm.requestFun("findEntAllInfo", params, callback, comm.lang("companyInfo"));
		},
		//查询交易密码、预留信息是否设置
		querjymmylxxsz : function (data, callback) {
			comm.requestFun("get_is_safe_set",data,callback,comm.lang("safeSet"));
		},
		
		//加载所有国家到js缓存中 
		findContryAll : function (data,callback){
			comm.requestFun("findContryAll",data,callback,comm.lang("common"));
		},
		
		//根据国家获取省份信息
		findProvinceByParent : function (data,callback){
			comm.requestFun("findProvinceByParent",data,callback,comm.lang("common"));
		},
		
		//获取所有的银行列表信息
		findBankAll : function (data,callback){
			comm.requestFun("findBankAll",data,callback,comm.lang("common"));
		},
		
		//获取本平台的基本信息
		findSystemInfo : function (data,callback){
			comm.requestFun("findSystemInfo",data,callback,comm.lang("common"));
		},
		
		//根据国家省份获取下属城市
		findCityByParent : function (data,callback){
			comm.requestFun("findCityByParent",data,callback,comm.lang("common"));
		},
		
		//根据国家代码-省份代码-城市代码获取对应名称--同步
		getRegionByCode : function (params){
			return comm.asyncRequestFun("getRegionByCode", params, comm.lang("common"));
		},
		
		//公共部分-根据客户号查询操作员名称--异步lyh
		searchOperByCustId : function (params, callBack){
			return comm.requestFun("searchOperByCustId", params, callBack, comm.lang("common"));
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
		/** 获取操作员信息 */
		operatorDetail : function (callBack,errorCallBack){
			comm.Request({url:"operatorDetail", domain:"scsWeb"},{
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
		/** 获取操作员信息 */
		findEntName : function (callBack){
			comm.requestFun("findEntAllInfo", params, callBack, comm.lang("companyInfo"));
		},
		/** 获取示例图片列表 */
		findDocList : function (callBack){
			comm.requestFun("findDocList", null, callBack, comm.lang("common"));
		},
		/** 根据客户号获取权限信息 */
		findPermissionByCustidList : function(params, callBack){
			comm.requestFun("findPermissionByCustidList", params, callBack, comm.lang("common"));
		},
		/** 获取所有的菜单信息 */
		findPerontIdByPermission : function(params, callBack){
			comm.requestFun("findPerontIdByPermission", params, callBack, comm.lang("common"));
		}
		
	};
});