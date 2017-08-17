define(["commSrc/comm"], function () {
	comm.langConfig["pointAccount"] = {
			//错误毛描述
			22000:'操作成功', 
			22004 : "token令牌无效",
			22006 : "客户号不能为空",
			22007 : "登录token不能为空",
			30001 : "转出积分数不能为空",
			30002 : "交易密码不能为空",
			30003 : "转入互生币不能为空",
			30004 : "客户名不能为空",
			30005 : "转出积分数大于可用积分数，请重新输入!",
			30006 : "转出积分数小于100的整数，请重新输入!",
			30007 : "积分投资金额应为100的整数倍",
			30008 : "投资积分大于账户余额，请修改积分投资数",
			30009 : "输入的积分数小于最小限制",
			22003 : "参数错误",
			30154 : "转出积分数量必须是整数",
			160411: "交易密码连续出错导致用户锁定",
			160360: "交易密码错误", 
			160415: "交易密码未设置",
			12478 :	"帐户余额不足",
			43263 : "积分帐户余额不足",
			12418 : "重要信息变更期不能积分投资",
			11011:"数据不存在",
			//前台错误信息信息验证
			digits : "请输入整数",
			maxlength : "最多输入10位数",
			dealPwdLength : "请输入8位交易密码",
			randomTokenInvalid:"获取随机报文失败，请重试",
			confirmIntegralHSCurrency:"确认要申请积分转互生币吗",
			pointToCashMaxlength:"最多输入10位数",
			confirmIntegralInvestment :"是否确认提交申请积分投资操作",
			integralInvestmentSuccessfully : "投资积分成功",
			integralHSBSuccessfully : "积分转互生币成功",
			businessEnum : {//业务类别
				0:'全部',
				1:'收入',
				2:'支出',
			},
			
			quickDateEnum : {//快捷日期类别
				'today':'今天',
				'week':'本周',
				'month':'本月',
			},
			
			
			detailSearch : {
				//积分投资明细查询
				quickDateRequired : "请至少选择一个科目",
				quickDateMinlength : "请至少选择一个科目",
				beginDateRequired : "请输入开始时间",
				beginDateError : "开始时间输入有误",
				beginDateEnd : "开始日期不能大于结束日期",
				endDateRequired : "请输入结束时间",
				endDateError : "结束时间输入有误",
				endDateBegin : "结束日期不能小于开始日期",
				noOperation : "该类型暂未作处理!"
			},
			// 业务类别
			businessTypeEnum : {
				"0" : "全部",
				"1" : "收入",
				"2" : "支出"
			},
			// 交易类型
			transTypeEnum : {
				"T11000" : "积分投资",
				"M61000" : "积分转货币",
			    "M11000" : "积分转互生币",
			    "X10630" : "系统调账转出",
			    "X10530" : "系统调账转入",
			    "S72000" : "平台业务扣款",
			    "X11000" : "系统平账",
			    "X14000" : "系统平账"
			}
	}
});