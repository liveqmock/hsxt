define(["commSrc/comm"], function () {
	comm.langConfig["accountManagementD"] = {
		cardInfoBind : {
			realNameBind : {
				required : "请输入真实姓名",
				maxlength : "姓名最大长度{0}位!",
				minlength : "姓名最小长度{0}位",
				required2 : "确认姓名不能为空",
				equalTo : "两次输入的姓名不一样"
			},
			bindBankCard : {
				required : "请输入银行卡号",
				bankNo : "请输入正确的银行卡号",
				maxlength : "银行卡号不超过{0}位",
				required1 : "请选择开户银行",
				required2 : "请输入确认卡号",
				equalTo : "确认卡号与银行卡号不一致"
			},
			mobileBind : {
				mobile : "请输入手机号码",
				mobileErr : "请输入正确的手机号码",
				checkCode : "请输入短信验证码",
				digits : "请输入正确的短信验证码",
				maxlength : "验证码不能超过{0}位",
				pwd : "请输入登录密码"
			},
			mailBind : {
				required : "请输入邮箱",
				required1 : "请输入确认邮箱",
				required2 : "请输入登录密码",
				email : "请输入正确的邮箱格式",
				equalTo : "两次邮箱输入不一致"
			},
			enterpriseName : {
				required : "请输入企业名称"
			},
			address : {
				required : "请输入注册地址"
			}
		},
		realNameReg : {
			required : "请输入证件号码",
			cernum : "证件号码格式错误"
		},
		realNameAuth : {
			requiredSex : "请选择性别",
			requiredAddress : "请输入户籍地址",
			requiredLicence : "请输入发证机关",
			requiredExpiryDate : "请输入证件有效期",
			requiredJob : "请输入职业",
			job : "请输入正确的格式",
			requiredText : "请输入认证附言",
			requiredTop : "请上传证件正面图片",
			requiredBack : "请上传证件反面图片",
			requiredCack : "请上传手持证件大头照",
			
			requiredCode : "请输入验证码!",
			rangelength : "请输入正确的验证码",
			remote : "验证码输入错误",
			requiredPlaceOfBirth : "请输入出生地点",
			requiredIssuingAuthority : "请输入签发机关",
			requiredPlaceOfIssue : "请输入签发地点",
			requiredEnterpriseName : "请输入企业名称",
			requiredRegisteredAddress : "请输入注册地址",
			requiredCompanyType : "请输入公司类型",
			requiredDatOfEstablishment : "请输入成立日期"/*,
			requiredOperatingPeriod : "请输入营业期限",
			requiredOperatingRange : "请输入经营范围"*/
		},
		hsCardLossReg : {
			required : "请输入挂失原因",
			requiredPwd : "请输入登录密码"
		},
		hsCardDisLossReg : {
			
		},
		hsCardReapply : {
			reason : "请输入补办原因",
			address : "请选择补办地址",
			dealPwd : "请输入交易密码",
			digits : "请输入正确的交易密码",
			maxlength : "交易密码的长度为{0}位",
			minlength : "交易密码的长度为{0}位"
		},
		userAddress : {
			receiverNameRequired : "请输入收货人姓名",
			phoneRequired : "请输入收货人手机",
			fixedTelephoneRequired : "请输入收货人电话",
			areaRequired : "请选择收货人地区",
			userAddressRequired : "请输入收货人详细地址",
			postcodeRequired : "请输入收货人邮编",
			mobile : "手机号码格式错误",
			phoneCN : "电话号码格式错误",
			zipCode : "邮编格式错误",
		},
		importantInfoChange : {
			required : "请输入真实姓名",
			equalName : "姓名没有变更",
			equalId : "证件号码没有变更",
			equalJob : "职业没有变更",
			equalAddress : "户籍地址没有变更",
			equalLicence : "发证机关没有变更",
			equalExpiryDate : "证件有效期没有变更",
			equalPlaceOfBirth : "出生地点没有变更",
			equalPlaceOfIssue : "签发地点没有变更",
			equalIssuingAuthority : "签发机关没有变更",
			equalEnterpriseName : "企业名称没有变更",
			equalRegisteredAddress : "注册地址没有变更",
			equalCompanyType : "公司类型没有变更",
			
			
			
			cernum : "证件号码格式错误",
			job : "请输入正确的格式",
			requiredReason : "请输入申请变更的原因",
			requiredTop : "请上传证件正面图片",
			requiredBack : "请上传证件反面图片",
			requiredCack : "请上传手持证件大头照",
			requiredProve : "请上传户籍变更证明",
			requiredCode : "请输入验证码!",
			rangelength : "请输入正确的验证码",
			remote : "验证码输入错误",
			
		}
	}
});