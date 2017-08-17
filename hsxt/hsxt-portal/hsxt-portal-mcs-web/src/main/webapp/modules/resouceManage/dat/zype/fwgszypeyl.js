define(function () {
	return {
		/**
		 * 统计管理公司下的资源数据
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		statResDetailOfManager : function(params, callback){
			comm.requestFun("statResDetailOfManager", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 统计二级区域下的资源数据
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		statResDetailOfProvince : function(params, callback){
			comm.requestFun("statResDetailOfProvince", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 三级区域(城市)下的资源列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		listResInfoOfCity : function(params, callback){
			comm.requestFun("listResInfoOfCity", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 统计管理公司下的企业资源
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		statResCompanyResM : function(params, callback){
			comm.requestFun("statResCompanyResM", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 统计服务公司的企业资源
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findCompanyResMList : function(params, detail){
			comm.getCommBsGrid(null, "findCompanyResMList", params, comm.lang("resouceManage"), detail);
		}
	};
});