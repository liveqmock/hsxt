define(function () {
	return {
		/**
		 * 企业报备-新增股东信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		createShareholder : function(params, callback){
			comm.requestFun("createShareholder", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业报备-删除股东信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		deleteShareholder : function(params, callback){
			comm.requestFun("deleteShareholder", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业报备-修改股东信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateShareholder : function(params, callback){
			comm.requestFun("updateShareholder", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业报备-查询股东信息
		 * @param params 参数对象
		 * @param deitFun 自定义函数
		 * @param delFun 自定义函数
		 */
		findShareholderList : function(params, deitFun, delFun){
			return comm.getCommBsGrid("tableDetail2", "findShareholderList", params, comm.lang("coDeclaration"), deitFun, delFun);
		},
		/**
		 * 企业报备-初始化附件信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		initUpload : function(params, callBack){
			comm.requestFun("initUpload", params, callBack, comm.lang("coDeclaration"));
		},
		/**
		 * 企业报备-保存附件信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		saveAptitude : function(params, callBack){
			comm.requestFun("saveAptitude", params, callBack, comm.lang("coDeclaration"));
		},
		/**
		 * 获取验证码
		 * @param callBack 自定义函数
		 */
		generateSecuritCode : function(callBack){
			var param=comm.getRequestParams();
			return comm.domainList['apsWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=entDeclare&"+(new Date()).valueOf();
			
		},
		/**
		 * 依据申请编号查询报备企业基本信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		getEntFilingById : function(params, callBack){
			comm.requestFun("getEntFilingById", params, callBack, comm.lang("coDeclaration"));
		},
		/**
		 * 新增报备企业基本信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		createEntFiling : function(params, callBack){
			comm.requestFun("createEntFiling", params, callBack, comm.lang("coDeclaration"));
		},
		/**
		 * 依据企业互生号查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findSameItem : function(params, callback){
			comm.requestFun("findSameItem", params, callback, comm.lang("coDeclaration"));
		}
	};
});