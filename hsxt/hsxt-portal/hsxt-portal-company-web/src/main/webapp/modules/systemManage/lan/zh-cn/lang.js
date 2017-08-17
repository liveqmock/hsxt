define(["commSrc/comm"], function () {
	comm.langConfig["systemManage"] = {
		
		platformCode:"HSXT",
		subSystemCode : "COMPANY",
		//universal 通用的提示信息
		universal :{
			unchanged : "没有任何信息被修改",
			confirm : "确定",
			close : "关闭",
			credencePicture : "证件图片",
			samplePicture : "证件图片",
			kindly_reminder : "温馨提示",
			file_upload_wait : "文件上传中...",
			oprate_success : "操作成功！",
			input_verified_code : "验证码输入错误",
			input_unequable : '两次结果不一样',
			input_bank_num :'输入16-19位数字',
			not_empty_province : '省份不能为空',
			not_empty_city : '城市不能为空',
			not_empty_company : "企业名称不能为空",
			approve_status_wait : "待审批",
			approve_status_pass : "审批通过",
			approve_status_reject : "审批驳回",
			today : "今天",
			current_week : "本周",
			current_moth : "本月",
			view : "查看",
			view_detail : "查看详细",
			id_card : "身份证",
			pass_port : "护照"
		},
		
		gxdjxx : {
			not_empty_company_type : "企业类型不能为空",
			not_empty_create_date : "创建时间不能为空",
			not_empty_contact_tel : "联系人手机号码不能这空",
			input_tel_illage : "手机号码输入不正确"
		},
		lxxx : {
			mail_valid_alert_title : "邮箱验证提示信息",
			illage_input_url : "输入的网址不正确",
			illage_input_email : "输入的邮箱不正确",
			illage_input_qq : "输入的qq不正确",
			illage_input_fax : "输入的传真号码不正确",
			illage_input_telphone : "输入的办法室电话号码不正确"
		},
		
		smrz :{
			auth_complete : "实名认证申请已经提交,请等待审批",
			pass_prompt: "贵企业已通过实名认证！",
			lrCredence_front_required : "必须上传法人代表证件的正面扫描件",
			lrCredence_back_required : "必须上传法人代表证件的背面扫描件",
			organization_required : "必须上传组织机构扫描件",
			busLicence_required : "必须上传营业执照扫描件",
			taxplayer_required : "必须上传税务登记证扫描件"
			
		},
		xtdjxx : {
			
		},
		
		yhzhxx :{
		  del_bank_acc_confirm	: "确认删除银行账户？"
		},
		zyxxbg:{
			approve_wait_promt : "实名认证申请已经提交,请等待审批",
			not_empty_company_name : "企业名称不能为空",
			not_empty_company_addr : "企业地址不能为空",
			not_empty_legal_Person : "法人代表不能为空",
			not_empty_legal_credence_type : "法人代表证件类型不能为空",
			not_empty_legal_credence_no : "法人代表证件号码不能为空",
			not_empty_busLisence_no : "营业执照注册号不能为空",
			not_empty_organization_no : "组织机构代码证号不能为空",
			not_empty_taxpalyer_no : "纳税人识别号不能为空",
			not_empty_contact_p_name : "联系人姓名不能为空",
			not_empty_contact_p_tel : "联系人手机不能为空",
			change_apply_file_required : "必须上传重要信息变更的申请书",
			powerOfAttorney_file_required : "必须上传授权委托书的扫描件"
			
		},
		
		/****************************************系统用户管理 ***********************************/
		
		//角色类型1：全局:2：平台:3：私有
		roleType : "3",
		
		username : "请输入正确的用户名,例如：0000",
		passwordLogin : "登录密码需6位且只能是数字",
		
		22001:"发送请求失败，请稍后在试",
		33301:"企业客户号不能为空",
	    33302:"互生号不能为空",
	    33303:"用户名（员工账号）不能为空",
	    33304:"登录密码不能为空",
	    33305:"管理员客户号不能为空",
	    33306:"操作员客户号不能为空",
	    33307:"用户组描述不能为空",
	    33308:"用户组名称不能为空",
	    33309:"用户组编号不能为空",
	    33310:"角色描述不能为空",
	    33311:"角色名称不能为空",
	    33312:"请选择角色",
	    33313:"权限不能为空",
	    33316:"要绑定的互生号不能为空",
	    33317:"手机号不能为空",
	    33318:"用户姓名不能为空",
	    33319:"用户状态不能为空",
	    33320:"请输入正确的互生卡号",
	    33321:"描述字符在100个字之内",
	    33322:"职务在20个字之内",
	    33323:"用户姓名在20个字之内",
	    
	    160315:"两次互生号不一致",
	    160316:"确认互生卡号不能为空",
	    
	    160382 : "此用户组已存在",
	    160103 : "用户已存在",
	    160108 : "管理员密码验证错误",
	    160102 : "用户不存在或已注销或已禁用",
	    160125 : "该互生号已被绑定",
	    160426 : "角色已绑定数据（操作员/菜单），不能删除！",
	    160427 : "角色已存在",
	    160437 : "角色已绑定数据（操作员/菜单），不能删除！",
	    160146 : "数据解密错误",
	    160427 : "角色已经存在!",
	    160453 : "角色已分配菜单，不允许删除！",
	    160452 : "角色已被操作员引用，不允许删除！",
	    
	    99917:"请输入正确的11位互生号",
	    99918:"请输入正确的手机号",
	    99904 : "确认登录密码不能为空",
	    99905 : "两次密码不一致",
	    
	    36530:"企业营业点不能为空",
		36531:"企业互生号不能为空",
		36532:"企业客户号不能为空",
		36533:"企业员工账号不能为空",
		36534:"创建人不能为空",
		
		accountStatus : {
			0 : "启用",
	    	1 : "禁用"
		},
		bindResNoStatus : {
	    	2 : "绑定中",
	    	0 : "未绑定",
	    	1 : "绑定"
	    },
	    notBind : "未绑定",
	    inBind : "绑定中",
	    bind : "已绑定",
	    resetPwdTitle:"重置登录密码",
	    resetPwdSuccess:"重置密码成功",
		update : "修改",
		del : "删除",
		activity : "启用",
		unactivity : "禁用",
		isActivity : "确认启用此操作员？",
		isUnActivity : "确认停用此操作员？",
		delContent : "确认删除选中的记录？",
		delStore : "当前没有选择营业点，视为解除已有营业点的关联关系操作，确认解除吗？",
		noSelectStore:"未选择任何营业点！",
		groupUser : "组员维护",
		nullUserCustId : "未选者任何用户！",
		remove : "移除",
		confirRemoveUser: "您确认移除选择的用户?",
		confirAddUser : "您确认将选择的用名添加到用户组？",
		grantRole : "角色设置",
		linkStoreEmp:"关联营业点",
		menuPower : "菜单授权",
		cancelBind : "取消绑定",
		confirCancelBind : "确认取消绑定？",
		bindCard : "绑定互生卡",
		pwd_number_repeated_error:"密码不规范:数字不能全部重复 。如111111、222222",                
		pwd_increase_error:"密码不规范:数字不能顺增或顺降。例123456、234567"
	/*******************************************end ***********************************************/
	}
});