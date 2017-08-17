define(function () {
	return {
		/**
		 * 消息模版列表
		 * @param params 参数对象
		 * @param detail1 自定义函数
		 * @param detail2 自定义函数
		 * @param detail3 自定义函数
		 */
		findMessageTplList:function(params, detail1, detail2, detail3){
			return comm.getCommBsGrid("searchTable", "findMessageTplList", params, comm.lang("serviceMsgManage"), detail1, detail2, detail3);
		},
		/**
		 * 消息模版审批列表
		 * @param params 参数对象
		 * @param detail1 自定义函数
		 * @param detail2 自定义函数
		 */
		findMessageTplApprList:function(params, detail1, detail2,detail3, detail4){
			return comm.getCommBsGrid("searchTable", "findMessageTplApprList", params, comm.lang("serviceMsgManage"), detail1, detail2,detail3, detail4);
		},
		/**
		 * 模版审批历史列表
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findMsgTplApprHisList:function(params, detail){
			return comm.getCommBsGrid("searchTable_list", "findMsgTplApprHisList", params, comm.lang("serviceMsgManage"), detail);
		},
		/**
		 * 保存消息模版
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveMessageTpl : function(params, callback){
			comm.requestFun("saveMessageTpl", params, callback, comm.lang("serviceMsgManage"));
		},
		/**
		 * 查询模版详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findMessageTplInfo : function(params, callback){
			comm.requestFun("findMessageTplInfo", params, callback, comm.lang("serviceMsgManage"));
		},
		/**
		 * 复核模版
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		reviewTemplate : function(params, callback){
			comm.requestFun("reviewTemplate", params, callback, comm.lang("serviceMsgManage"));
		},
		/**
		 * 启用/停用模版
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		startOrStopTpl : function(params, callback){
			comm.requestFun("startOrStopTpl", params, callback, comm.lang("serviceMsgManage"));
		},
		/**
		 * 分页查询短信消息发送
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findNoteByPage:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findNoteByPage", params, comm.lang("serviceMsgManage"), detail);
		},
		/**
		 * 分页查询邮件消息发送
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findEmailByPage:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findEmailByPage", params, comm.lang("serviceMsgManage"), detail);
		},
		/**
		 * 分页查询业务互动消息发送
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findDynamicBizByPage:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findDynamicBizByPage", params, comm.lang("serviceMsgManage"), detail);
		}
	}
});