define(function () {
	return {
		/**
		 * 企业报备审核查询
		 * @param params 参数对象
		 * @param det 自定义函数1
		 * @param del 自定义函数2
		 * @param add 自定义函数3
		 */
		findApprFilingList : function(params, det, del, add){
			return comm.getCommBsGrid(null, "findApprFilingList", params, comm.lang("coDeclaration"), det, del, add);
		},
		/**
		 * 查看报备详细信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findFilingById : function(params, callback){
			comm.requestFun("findFilingById", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 地区平台审批企业报备
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		apprCommFiling : function(params, callback){
			comm.requestFun("apprCommFiling", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 依据企业互生号查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findMainInfoByResNo : function(params, callback){
			comm.requestFun("findMainInfoByResNo", params, callback, comm.lang("coDeclaration"));
		}
	};
});