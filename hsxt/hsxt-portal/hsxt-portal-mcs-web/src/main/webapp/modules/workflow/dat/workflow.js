define(function() {
	return {
		// 工单分页
		workOrderPage : function(gridId, params, detail, del, add, edit) {
			return comm.getCommBsGrid(gridId, "workOrderPage", params, comm.lang("workflow"), detail, del, add, edit);
		},
		// 工单挂起
		workOrderSuspend : function(data, callback) {
			comm.requestFun("workOrderSuspend", data, callback, comm.lang("workflow"));
		},
		// 工单终止
		workOrderTermination : function(data, callback) {
			comm.requestFun("workOrderTermination", data, callback, comm.lang("workflow"));
		},
		// 工单转入待办
		workOrderToDo : function(data, callback) {
			comm.requestFun("workOrderToDo", data, callback, comm.lang("workflow"));
		},
		// 工单催办
		workOrderReminders : function(data, callback) {
			comm.requestFun("workOrderReminders", data, callback, comm.lang("workflow"));
		},
		// 工单拒绝受理
		workOrderDoor : function(data, callback) {
			comm.requestFun("workOrderDoor", data, callback, comm.lang("workflow"));
		},
		// 获取值班员列表
		getAttendantList : function(data, callback) {
			comm.requestFun("getAttendantList", data, callback, comm.lang("workflow"));
		},
		
		// 获取值班组列表
		getGroupList : function(data, callback) {
			comm.requestFun("getGroupList", data, callback, comm.lang("workflow"));
		},
		// 获取工作组下组员日程安排
		getMembersSchedule : function(data, callback) {
			comm.requestFun("getMembersSchedule", data, callback, comm.lang("workflow"));
		},
		//新增值班组
		groupAdd: function(data, callback) {
			comm.requestFun("groupAdd", data, callback, comm.lang("workflow"));
		},
		//修改值班组
		groupUpdate: function(data, callback) {
			comm.requestFun("groupUpdate", data, callback, comm.lang("workflow"));
		},
		//获取值班组明细
		getGroupInfo: function(data, callback) {
			comm.requestFun("getGroupInfo", data, callback, comm.lang("workflow"));
		},
		//获取值班员明细
		getAttendantInfo: function(data, callback) {
			comm.requestFun("getAttendantInfo", data, callback, comm.lang("workflow"));
		},
		//新增值班员业务节点
		addBizType: function(data, callback) {
			comm.requestFun("addBizType", data, callback, comm.lang("workflow"));
		},
		//删除值班员业务节点
		deleteBizType: function(data, callback) {
			comm.requestFun("deleteBizType", data, callback, comm.lang("workflow"));
		},
		// 移除值班员
		removeOperator: function(data, callback) {
			comm.requestFun("removeOperator", data, callback, comm.lang("workflow"));
		},
		//值班组开启或关闭
		udpateGroupStatus: function(data, callback) {
			comm.requestFun("udpateGroupStatus", data, callback, comm.lang("workflow"));
		},
		//执行值班组计划
		executeSchedule: function(data, callback) {
			comm.requestFun("executeSchedule", data, callback, comm.lang("workflow"));
		},
		//暂停值班组计划
		pauseSchedule: function(data, callback) {
			comm.requestFun("pauseSchedule", data, callback, comm.lang("workflow"));
		},
		//保存值班计划
		saveSchedule: function(data, callback) {
			comm.requestFun("saveSchedule", data, callback, comm.lang("workflow"));
		},
		//获取企业员工
		getEntOperList: function(data, callback) {
			comm.requestFun("getEntOperList", data, callback, comm.lang("workflow"));
		},
		//导出值班计划URL
		exportSchedule:function(data){
			return comm.domainList["mcsWeb"]+comm.UrlList["exportSchedule"]+
			"?year="+data.year+"&month="+data.month+"&groupName="+data.groupName+"&groupId="+data.groupId;
		},
		//获取操作员对应的业务类型
		getBizTypeList:function(data, callback){
			comm.requestFun("getBizTypeList", data, callback, comm.lang("workflow"));
		},
		//操作员是否为值班主任
		isChief : function(callback){
			comm.requestFun("isChief", null, callback, comm.lang("workflow"));
		}
	};
});