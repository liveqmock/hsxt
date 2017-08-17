define(function () {
	return {
		/**
		 * 查询常用业务文档列表
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 * @param add 自定义函数3
		 */
		findBusinessNormalDocList:function(params, detail, del, add){
			return comm.getCommBsGrid("searchTable", "findBusinessNormalDocList", params, comm.lang("businessDocumentManage"), detail, del, add);
		},
		/**
		 * 查询办理业务文档列表
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 * @param add 自定义函数3
		 */
		findBusinessBizDocList:function(params, detail, del, add){
			return comm.getCommBsGrid("searchTable", "findBusinessBizDocList", params, comm.lang("businessDocumentManage"), detail, del, add);
		},
		/**
		 * 保存业务文件-示例图片
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		savePicDoc : function(params, callback){
			comm.requestFun("savePicDoc", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 保存业务文件-常用业务文档
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveComDoc : function(params, callback){
			comm.requestFun("saveComDoc", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 保存业务文件-办理业务申请书
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveBusDoc : function(params, callback){
			comm.requestFun("saveBusDoc", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 发布文档
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		releaseBusinessDoc : function(params, callback){
			comm.requestFun("releaseBusinessDoc", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 删除文档
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		deleteBusinessDoc : function(params, callback){
			comm.requestFun("deleteBusinessDoc", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 停用文档
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		stopUsedBusinessDoc : function(params, callback){
			comm.requestFun("stopUsedBusinessDoc", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 查询文档详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findBusinessDocInfo : function(params, callback){
			comm.requestFun("findBusinessDocInfo", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 获取示例图片版本
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findBusinessImageDocHis : function(params, callback){
			comm.requestFun("findBusinessImageDocHis", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 获取示例图片管理列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findBusinessImageDocList : function(params, callback){
			comm.requestFun("findBusinessImageDocList", params, callback, comm.lang("businessDocumentManage"));
		},
		/**
		 * 恢复示例图片版本
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		recoveryBusinessImageDoc : function(params, callback){
			comm.requestFun("recoveryBusinessImageDoc", params, callback, comm.lang("businessDocumentManage"));
		}
	};
});