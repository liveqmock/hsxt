define(function(){
	return {
		//消费者资源统计详情
		personResDetail : function (callback) {
			comm.requestFun("personResDetail" , null, callback,comm.lang("resouceManage"));
		},
		//企业下的消费者资源统计
		entNextPersonStatisticsPage:function(params){
			return comm.getCommBsGrid("tableDetail", "entNextPersonStatisticsPage", params, comm.lang("resouceManage"));
		}
	};	
});
