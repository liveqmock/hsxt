define([ "commSrc/comm" ], function() {
	comm.langConfig["platformProxy"] = {
		// 后台返回的状态码
			22000 : "操作成功",
			22001 : "操作失败",
			12305 : "企业客户号对应的年费信息不存在",
			12310 : "未到缴费时间",
			160101 : "该企业客户号找不到企业信息",
			160147 : "企业不存在",
			31037:   "手机号码不能为空",
			9996:"参数错误",
			12115 : "输入审核意见大于系统定义的300字符长度",
			22069: "业务操作失败,请稍后重试",
		isHsResNo:"该输入正确的11位互生号",
		annualFee_success:'缴纳年费的订单号为：',
		buyhsb_success:'兑换互生币订单提交成功',
		proxyOrder_appr_success:'复核成功',
		hsbAmountRequired:'互生币数量必输入',
		hsbAmountNumber:'互生币数量需为数字',
		hsbAmountdigits:'互生币数量最多两位小数',
		hsResNoRequired :'互生号必输入',
		isEntHsResNo :'请输入企业或服务互生号',
		isTorSHsResNo :'请输入托管或服务互生号',
		isTorBHsResNo :'请输入企业互生号',
		isTEntHsResNo :"请输入托管互生号",
		pleaseInputResNo:"请输入互生号",
		buyHsbMin : "兑换互生币的最小值为{0}",
		buyHsbMax : "兑换互生币的最大值为{0}",
		confirmSubmitBuyHsb:"你是否确认提交兑换互生币?",
		toolNumNotNull : '请输入要购买工具的申购数量',
		toolManage_selectAddr: '请选择收货地址',
		toolManage_addDeliverAddressSucc : '新增自定义地址成功',
		toolManage_provinceIsNotNull:'所在地区省必选',
		toolManage_cityIsNotNull:'所在地区城市必选',
		toolManage_fullAddressIsNotNull:'详细地址必填',
		toolManage_receiverIsNotNull:'收货人姓名必填',
		toolManage_mobileIsNotNull:'手机号码必填',
		toolManage_toolNameSelect : '请选择要购买的工具',
		toolManage_digits:'请输入整数',
		toolManage_maxLenght:'输入最大长度是{0}',
		toolManage_appremarkRequire:'审核意见不能为空',
		toolManage_statuRequire:'审核结果不能为空',
		
		//当申购互生卡时 申购数量限制
		toolManage_cardBuyNum : '互生卡购买时必须是1000的整数倍，或一次性申购完剩余的{0}张互生卡！',
		//申购数量不能大于
		toolManage_toolMaxNum : '企业还可以申购{0}的数量为{1}',
		toolManage_consumePoint : '积分刷卡器和消费刷卡器一共',
		toolManage_toolNumNotNull : '请输入要购买工具的申购数量',
		//地址类型
		toolManage_addrType : {
			contact:'联系地址',
			ent:'企业地址', 
			auto:'自定义地址'
		},
		//工具类别
		toolManage_categoryCode:{
			P_POS:'POS机',
			TABLET:'互生平板', 
			POINT_MCR:'积分刷卡器',
			CONSUME_MCR:'消费刷卡器',
			P_CARD:'互生卡', 
			NORMAL:'普通工具',
			GIFT:'赠品', 
			SUPPORT:'配套产品'
		},
		status : {
			0:'待审批',
			1:'审批通过',
			2:'审批驳回'
		},
		determine : '确定',
		cancel : '取消',
		query : '查看',
		approval : '审批',
		edit : '修改',
		doubleAppr:"复核",
		refuseAccept:"拒绝受理",
		optRefuseAccept:"您正在拒绝受理平台代购工具复核业务的工单！",
		hangUp:"挂起",
		optHangUp:"您正在挂起平台代购工具复核业务的工单！",
		workTaskRefuseAcceptSucc:"工单拒绝受理成功",
		workTaskHangUp:"工单挂起成功",
		proxyOrderType :{
			'103':'新增申购工具 ',
			'107':'个性卡定制服务',
			'110':'系统资源申购'
		},
		pleaseSelectAgreement:"请先勾选托管企业系统消费者资源协议",
		pleaseSelectSegment:"请先勾选托管企业系统消费者资源段",
		31137: "工具参数不能为空",
		31138: "申购消费资源段不能为空",
		31139: "申购消费资源段价格错误",
		31140: "申购消费资源段资源数量错误",
		31141:"企业开启资源类型是全部资源",
		contactAddrIsNull:'联系地址为空,请选择其他的地址',
		apprUserNameIsNotNull : '复核用户名不能为空',
		apprPassWordIsNotNull : '复核密码不能为空',
		remarkLength300 : '挂起原因长度不能超过300',
		verificationMode:{
			'1':'密码验证',
			'2':'指纹验证',
			'3':'刷卡验证'
		},
		22006: "客户号不能为空",
		12220: "选择资源段含有已下单或已申购",
		12224: "选择资源段含有代购中的,请先处理完代购中订单"
	}
});