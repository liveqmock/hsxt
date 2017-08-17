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
		
		getLoginInfo : function(callback) {
			callback({"loginIp":"192.168.1.189","lastLoginDate":1438916887000,"nickName":"","loginCount":"0","loginResNo":"01003010728"});
		},
		
		//加载所有国家到js缓存中 
		findContryAll : function (data,callback){
			comm.requestFun("findContryAll",data,callback,comm.lang("common"));
		},
		//获取地区平台信息
		getLocalInfo : function(callback){
			comm.requestFun("get_localInfo",null,callback,comm.lang("common"));
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
			//return comm.requestFun("searchOperByCustId", params, callBack, comm.lang("common"));
			
			comm.Request({url:"searchOperByCustId", domain:comm.getProjectName()},{
				data:comm.getRequestParams(params),
				type:'POST',
				dataType:"json",
				success:function(rsp){
					//非空验证
					if (rsp.retCode && rsp.retCode ==22000){
						callBack(rsp);
					}else{
						callBack(null);
					}
				}
			});
			
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
		//根据国家代码-根据国家代码获取省份城市信息--同步
		findProvCity : function (params){
			return comm.asyncRequestFun("findProvCity", params, comm.lang("common"));
		},
		//根据国家代码-省份代码-城市代码获取对应名称--异步
		syncGetRegionByCode : function (params, callBack){
			comm.requestFun("getRegionByCode", params, callBack, comm.lang("common"));
		},
        //获取平台工单业务类型
        getBizAuthList : function (data,callback){
                comm.requestFun("getBizAuthList",data,callback,comm.lang("common"));
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
		/** 获取示例图片列表 */
		findDocList : function (callBack){
			comm.requestFun("findDocList", null, callBack, comm.lang("common"));
		},
		/** 根据货币代码查询货币信息 */
		findCurrencyByCode : function (currencyCode, callBack){
			comm.requestFun("findCurrencyByCode", {currencyCode:currencyCode}, callBack, comm.lang("common"));
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
		},
		/**查询企业扩展信息(客户号) */
		findCompanyExtInfo : function (params,callBack){
			comm.requestFun("find_company_ext_info", params, callBack, comm.lang("common"));
		},
		/**查询企业一般信息(客户号)  */
		findCompanyBaseInfo : function (params,callBack){
			comm.requestFun("find_company_base_info", params, callBack, comm.lang("common"));
		},
		/**查询企业重要信息(客户号)  */
		findCompanyMainInfo : function (params,callBack){
			comm.requestFun("find_company_main_info", params, callBack, comm.lang("common"));
		},
		/**查询企业所有信息(客户号)  */
		findCompanyAllInfo : function (params,callBack){
			comm.requestFun("find_company_all_info", params, callBack, comm.lang("common"));
		},
		/**查询企业状态信息(客户号)  */
		findCompanyStatusInfo : function (params,callBack){
			comm.requestFun("find_company_status_info", params, callBack, comm.lang("common"));
		},
		/**根据企业资源号获取企业重要信息  */
		findCompanyMainInfoByResNo : function (params,callBack){
			comm.requestFun("find_company_main_info_by_res_no", params, callBack, comm.lang("common"));
		},
		/**查询企业的联系信息(客户号)  */
		findCompanyContactInfo : function (params,callBack){
			comm.requestFun("find_company_contact_info", params, callBack, comm.lang("common"));
		},
		/**查询企业客户号 ，通过企业互生号  */
		findCompanyCustIdByResNo : function (params,callBack){
			comm.requestFun("find_company_cust_id_by_res_no", params, callBack, comm.lang("common"));
		}
	};
});