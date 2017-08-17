define([ "commSrc/comm" ], function() {
	comm.langConfig['pointWelfare'] = {
		// 共用
		22000 : "操作成功",
		22001 : "操作失败",
		22003 : "参数错误",
		22004 : "token令牌无效",
		23801 : "数据没找到",
		24003: "该审批单已经审批过了",
		0 : "处理成功",
		34024 : "订单号不能为空",
		34025 : "复核帐号不能为空",
		34026 : "复核密码不能为空",
		34027 : "复核意见不能为空",
		24017 : "核准金额大于保障单余额",
		requestError : '请求出错，请重试！',
		begin_Date : '请输入开始日期',
		end_Date : '请输入结束日期',
		digits : '请输入整数',
		maxlength : '最多输入{0}个字',
		kjrq : '请选择快捷日期',
		ywlb : '请选择业务类别',
		categoryError : '类别不能为空',
		grantSucess : '发放成功',
		itemNameError : '项目名称不能为空',
		amountInvalid : '金额不能为空',
		healthPayAmountInvalid : '医保支付金额不能为空',
		addItemIsNull :'没有账单明细数据，请增加项目数据!',
		
		addItemError : {
			
			timeRange_start_Notnull : '账单开始时间不能为空',
			timeRange_end_Notnull : '账单结束时间不能为空',
			provinceNo_Notnull : '账单所属省份不能为空',
			cityNo_Notnull : '账单所属城市不能为空',
			
			categoryNotnull : '类别不能为空',
			quantityInvalid : '数量格式错误',
			quantityNotnull : '数量不能为空',
			priceNotnull : '单价不能为空',
			priceInvalid : '单价格式错误',
			amountNotnull : '金额不能为空',
			amountInvalid : '金额格式错误',
			healthPayAmountNotnull : '医保支付金额不能为空',
			healthPayAmountInvalid : '医保支付金额格式错误',
			personalPayAmountNotnull : '个人支付金额不能为空',
			personalPayAmountInvalid : '个人支付金额格式错误',
			proportionInvalid : '医保支付比例格式错误,范围[0-100]',
			proportionNotnull : '医保支付比例不能为空',
			itemNameNotnull : '项目名称不能为空不能为空',

			aa:''
		},
		approvalStatus : {
			0 : "待审批",
			1 : "审批通过",
			2 : "审批驳回"
		},	
		PayStatus : {
			0 : "待支付",
			1 : "处理中",
			2 : "已付款"
		},
		lphsstatus:{
			0:'待核算',
			1:'核算中',
			2:'已核算'
		},
		category : {
			0 : "中成药",
			1 : "西药",
			2 : "手术费",
			3 : "检查费",
			4 : "材料费",
			5 : "其他"
		},
		unit : {
			0 : "盒",
			1 : "瓶",
			2 : "袋",
			3 : "床",
			4 : "例",
			5 : "片",
			6 : "个",
			7 : "套",
			8 : "条",
			9 : "克",
			10 : "千克"
		},
		OrderStatus : {
			1 : "待付款 ",
			2 : "待配货",
			3 : "已完成",
			4 : "已过期",
			5 : "已关闭"
		},
		welfareType:{
			0:'互生意外伤害保障',
			1:'互生医疗补贴计划',
			2:'代他人申请身故保障金'
		},
		refuseApproval:'您正在拒绝受理积分福利审批业务的工单!',
		suspendApproval:'您正在挂起积分福利审批业务的工单!',
		refuseGrant:'您正在拒绝受理积分福利发放业务的工单!',
		suspendGrant:'您正在挂起积分福利发放业务的工单!',
		suspendSucess:'已挂起此业务工单',
		refuseSucess:'已拒绝受理此业务工单！',
		
		//积分福利复核
		fhsuspendApproval:"您正在挂起积分福利复核业务的工单！",
		fhrefuseApproval:'您正在拒绝受理积分福利复核业务的工单!',
		
		//积分福利初审
		csrefuseApproval:'您正在拒绝受理积分福利初审业务的工单!',
		cssuspendApproval:'您正在挂起积分福利初审业务的工单！',
		
		//意外伤害补贴发放
		ywsuspendGrant:'您正在挂起意外伤害补贴发放业务的工单！',
		ywrefuseGrant:'您正在拒绝受理意外伤害补贴发放业务的工单！',
		
		//互生医疗补贴发放
		mfsuspendGrant:'您正在挂起互生医疗补贴发放业务的工单！',
		mfrefuseGrant:'您正在拒绝受理互生医疗补贴发放业务的工单！',
		
		//他人身故补贴发放
		trsuspendGrant:'您正在挂起他人身故补贴发放业务的工单！',
		trrefuseGrant:'您正在拒绝受理他人身故补贴发放业务的工单！',
		
		verificationMode:{
			'1':'密码验证',
			'2':'指纹验证',
			'3':'刷卡验证',
		},
		confirmDel:"确认删除该条记录?",
		36047:"复核员用户名不可以为空!",
		36048:"复核员登录密码不可以为空!",
		beizuMaxLength:"备注信息最大不超过1000个字",
		butieMaxLength:"补贴说明最大不超过1000个字",
		smMaxlength:"说明最大不超过300个字",
		minquantity:"数量最小不能低于1",
		minprice:"单价不能为0",
		timeRange:"起始时间不能大于结束时间",
		wsprice:"单价只能输入两位小数",
		remarkLength:"复核意见最大不超过300个字",
		apprPassWordIsNotNull:"复核员登陆密码不可以为空",
		apprUserNameIsNotNull:"复核员用户名不可以为空",
		hangUp:"挂起",
	}
});