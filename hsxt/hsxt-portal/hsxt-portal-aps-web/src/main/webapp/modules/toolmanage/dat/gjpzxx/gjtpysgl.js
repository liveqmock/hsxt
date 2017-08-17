define(function () {
	return {
		/**
		 * 互生卡样-分页查询互生卡样
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 */
		findCardStyleList:function(params, detail, del){
			return comm.getCommBsGrid("searchTable", "findCardStyleList", params, comm.lang("toolmanage"), detail, del);
		},
		/**
		 * 互生卡样-添加互生卡样
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addCardStyle : function(params, callback){
			comm.requestFun("addCardStyle", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 互生卡样-互生卡样的启用/停用
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		cardStyleEnableOrStop : function(params, callback){
			comm.requestFun("cardStyleEnableOrStop", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 互生卡样-查询互生卡样
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findCardStyleById : function(params, callback){
			comm.requestFun("findCardStyleById", params, callback, comm.lang("toolmanage"));
		}
	};
});