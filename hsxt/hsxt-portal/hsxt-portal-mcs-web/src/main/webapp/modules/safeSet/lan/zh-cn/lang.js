define([ "commSrc/comm" ], function() {
	comm.langConfig['safeSet'] = {
		// 共用
		begin_Date : '请输入开始日期',
		end_Date : '请输入结束日期',
		kjrq : '请选择快捷日期',
		ywlb : '请选择业务类别',
		// 积分转货币
		jfzhb_zcjfs : '请输入转出积分数',
		jfzhb_jyzzh : '转入货币账户交易子账户',
		jfzhb_jymm : '请输入8位交易密码',

		// 共用
		22000 : "操作成功",
		22001 : "操作失败",
		22003 : "参数错误",
		23901 : "参数错误",
		22004 : "token令牌无效",
		0 : "处理成功",
		160355 : "参数错误，请稍后再试！",
		requestError : '请求出错，请重试！',
		begin_Date : '请输入开始日期',
		end_Date : '请输入结束日期',
		digits : '请输入整数',
		maxlength : '最多输入{0}个字',
		kjrq : '请选择快捷日期',
		ywlb : '请选择业务类别',
	    pwd_number_repeated_error:"密码不规范:数字不能全部重复 。如111111、222222",
		pwd_increase_error:"密码不规范:数字不能顺增或顺降。例123456、654321",
		trad_pwd_number_repeated_error:"密码不规范:数字不能全部重复 。如11111111、22222222",
		trad_pwd_increase_error:"密码不规范:数字不能顺增或顺降。例12345678、87654321",
		// 修改登录密码
		input_old_password : '请输入6位数旧登录密码',
		input_new_password : '请输入6位数字新密码',
		input_confirm_password : '请再次输入6位数字新密码',
		password_inconsistent : '两次输入密码不一致',
		update_password_success : '修改登录密码成功',
		password_maxlength : '请输入{0}位数字的密码',
		password_new_old_same:'新密码和旧密码相同，请重新输入新密码！',
		23000 : "旧密码不能为空",
		23001 : "新秘密不能为空",
		23002 : "确认密不能为空",
		23003 : "两次密码不一致",
		23004 : "密码规则不满足,请根据规则输入密码",
		160102 : "用户不存在",
		160109 : "旧登录密码不正确",
		160359 : "旧登录密码错误，请重新输入！",
		160101 : "该企业客户号找不到企业状态信息",
		160394 : "授权码已过期",
		160108 : "旧登录密码错误",

		// 设置密保问题
		question_answer_null : "请输入密保答案",
		set_question_answer_success : "设置密保答案成功",
		select_pwd_question : "请选择密保问题",
		23013 : "密保答案不能为空",
		23014 : "密保参数错误",
		160210:"保存密保失败",

		// 设置预留信息
		input_reserve_info : "请输入预留信息",
		set_reserve_info_success : '设置预留信息成功',
		update_reserve_info_success : "修改预留信息成功",
		23015 : "预留信息存在,无需再次新增 ",
		30026 : "未设置预留信息",

	}
});
