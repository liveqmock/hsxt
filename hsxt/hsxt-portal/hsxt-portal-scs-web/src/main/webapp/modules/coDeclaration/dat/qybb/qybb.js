define(function () {
	return {
		/**
		 * 获取报备查询URL
		 */
		getFindEntFilingListUrl : function(){
			return comm.domainList.scsWeb+comm.UrlList.findEntFilingList;
		},
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
		queryShareholderList : function(params, callback){
			comm.requestFun("findShareholderList", params, callback, comm.lang("coDeclaration"));
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
			//comm.requestFun("generateSecuritCode", null, callBack, comm.lang("coDeclaration"));
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
		 * 提交报备
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		submitFiling : function(params, callBack){
			comm.requestFun("submitFiling", params, callBack, comm.lang("coDeclaration"));
		},
		isExistSameOrSimilar : function(params, callBack){
			comm.requestFun("isExistSameOrSimilar", params, callBack, comm.lang("coDeclaration"));
		}
	};
});