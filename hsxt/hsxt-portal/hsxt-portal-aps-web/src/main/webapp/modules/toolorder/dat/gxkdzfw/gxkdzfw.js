define(function () {
	return {
		/**
		 * 工具制作管理-个性卡定制查询列表
		 */
		findSpecialCardStyleList:function(params, detail,edit){
			return comm.getCommBsGrid("searchTable", "findSpecialCardStyleList", params, comm.lang("toolorder"), detail,edit);
		},
		/**
		 * 工具制作管理-根据订单号查询互生卡样
		 */
		findCardStyleByOrderNo : function(params, callback){
			comm.requestFun("findCardStyleByOrderNo", params, callback, comm.lang("toolorder"));
		},
		/**
		 * 工具制作管理-上传卡样制作文件
		 */
		uploadCardStyleMarkFile : function(params, callback){
			comm.requestFun("uploadCardStyleMarkFile", params, callback, comm.lang("toolorder"));
		}
	}
});