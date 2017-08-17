﻿define([ "commSrc/comm" ], function() {
	comm.langConfig["consumerInfo"] = {
			select_check_type:'请选择验证方式',
			input_account:'请输入复核员用户名',
			input_pwd:'请输入复核员登陆密码',
			160102 : "复核员帐号不存在",
			160129:"手机未验证",
			160455:"用户未设置手机号码",
			reset_login_pwd_success:"重置登录密码成功，新密码短信已发送",
			reset_trade_pwd_success:"重置交易密码成功，新密码短信已发送",
			reset_pay_pwd_success:"重置交易密码审核成功，授权码短信已发送",
			reset_pay_pwd_reject:"重置交易密码审批不通过",
			nomobile_info:"消费者未绑定手机号，无法重置密码",
			no_ent_mobile_info:"企业未绑定手机号，无法重置密码",
			userName_must:"复核用户名不可以为空!",
			passWord_must:"复核密码不可以为空",
			// 卡状态 1：不活跃、2：活跃、3：休眠、4：沉淀
			baseStatusEnum : {
			    "1" : "不活跃",
			    "2" : "活跃",
			    "3" : "休眠",
			    "4" :  "沉淀"
			 },
			 custType :{
					2 : '成员企业',
					3 : '托管企业',
					4 : '服务公司'
			},
			realnameStatusEnum:{
				1 : '未注册',
				2 : '已注册',
				3 : '已认证'
			},
			credentialTypeEnum:{
				1 : '身份证',
				2 : '护照'
			}
		}
});