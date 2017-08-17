define(function () {
	return {
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
			
			comm.requestFun("queryDealPwdAndReserveInfo",data,callback,comm.lang("safetySet"));
			//callback({"code":0,"reserveInfo":"我的互生"});
		},
		getLoginInfo : function(callback) {
			callback({"loginIp":"192.168.1.189","lastLoginDate":1438916887000,"nickName":"","loginCount":"0","loginResNo":"01003010728"});
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
		
		//公共部分-根据客户号查询操作员名称--异步lyh
		searchOperByCustId : function (params, callBack){
			return comm.requestFun("searchOperByCustId", params, callBack, comm.lang("common"));
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
		
		//根据国家代码-省份代码-城市代码获取对应名称--异步
		syncGetRegionByCode : function (params, callBack){
			comm.requestFun("getRegionByCode", params, callBack, comm.lang("common"));
		},
		//预留信息是否设置
		querjymmylxxsz : function (callback) {
			comm.requestFun("get_is_safe_set",null,callback,comm.lang("safeSet"));
		},
        //获取平台工单业务类型
        getBizAuthList : function (data,callback){
             comm.requestFun("getBizAuthList",data,callback,comm.lang("common"));
        },
		/** 获取操作员信息 */
		operatorDetail : function (callBack,errorCallBack){
			comm.Request({url:"operatorDetail", domain:"mcsWeb"},{
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
		//根据国家代码-根据国家代码获取省份城市信息--同步
		findProvCity : function (params){
			return comm.asyncRequestFun("findProvCity", params, comm.lang("common"));
		},
		/** 获取示例图片列表 */
		findDocList : function (callBack){
			comm.requestFun("findDocList", null, callBack, comm.lang("common"));
		},
		/**根据客户号获取当前用户下的所有菜单 */
		findPermissionByCustidList : function (callBack){
			comm.requestFun("findPermissionByCustidList", null, callBack, comm.lang("common"));
		},
		/**工单拒绝受理 */
		workTaskRefuseAccept : function (params,callBack){
			comm.requestFun("work_task_refuse_accept", params, callBack, comm.lang("common"));
		},
		/**工单挂起 */
		workTaskHangUp : function (params,callBack){
			comm.requestFun("work_task_hang_up", params, callBack, comm.lang("common"));
		}
	};
});