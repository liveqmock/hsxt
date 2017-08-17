define(["commSrc/comm"], function () {
	comm.langConfig["systemService"] = {
		systemLogSearch : {
			//积分投资明细查询
			quickDateRequired : "请至少选择一个科目",
			quickDateMinlength : "请至少选择一个科目",
			beginDateRequired : "请输入开始时间",
			beginDateError : "开始时间输入有误",
			beginDateEnd : "开始日期不能大于结束日期",
			endDateRequired : "请输入结束时间",
			endDateError : "结束时间输入有误",
			endDateBegin : "结束日期不能小于开始日期",
			sysLogTypeRequired : "请至少选择一个业务类型",
			realNameReg : "实名注册"
		},
		/**
		 * 业务类型
		 */
		bsType : {
			'findTruenameregList' : "实名注册",
			'findTruenameaugList' : "实名认证",
			'findImptinfochangeList' : "重要信息变更",
			'findCardapplyList' : "互生卡补办",
			'findCardlossList' : "互生卡挂失",
			'findCarddislossList' : "互生卡解挂"
		},
		/**
		 * 注册状态
		 */
		regStatus : {
			1 : "未实名注册",
			2 : "已实名注册",
			3 : "已实名认证"
		},
		/**
		 * 实名认证状态
		 */
		tureNameStatus : {
			0 : "待审批",
			1 : "待审批",
			2 : "审批通过",
			3 : "申请驳回",
			4 : "申请驳回"
		},
		/**
		 * 重要信息变更状态
		 */
		chgStatus : {
			0 : "待审批",
			1 : "待审批",
			2 : "审批通过",
			3 : "申请驳回",
			4 : "申请驳回"
		},
		/**
		 * 互生卡补卡状态
		 */
		orderStatus : {
			1 : "待付款",
			2 : "待配货",
			3 : "已完成 ",
			4 : "已过期",
			5 : "已关闭",
			6 : "待确认 ",
			7 : "已撤单",
			9 : '已发货'
		},
		bsResult : {
			0 : "成功",
			1 : "失败"
		}
	}
});