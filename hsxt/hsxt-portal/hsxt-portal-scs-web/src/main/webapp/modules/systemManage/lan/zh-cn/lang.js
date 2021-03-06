define(["commSrc/comm"],function(){
	comm.langConfig['systemManage'] ={
		
		subSystemCode : "SCS",
		platformCode : "HSXT",
		
		//角色类型1：全局:2：平台:3：私有
		roleType : "3",
		passworddlsuccess:"重置新密码成功!",
		username : "请输入正确的用户名,例如：0000",
		passwordLogin : "请输入6位数字登录密码",
		passwordLogin2 : "输入6位数字确认登录密码",
		22001 :"请求失败，请稍后重试",
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
	    160437:"角色已使用，不能删除",
	    
	    33317:"请输入正确的11位互生号",
	    
	    160315:"两次互生号不一致",
	    160316:"确认互生卡号不能为空",
	    
	    160382 : "此用户组已存在",
	    160103 : "用户已存在",
	    160108 : "管理员密码验证错误",
	    160102 : "没有改操作员信息",
	    160426 : "角色使用中，不允许删除",
	    160427 : "角色已存在",
	    160355 : "企业客户类型不能为空",
	    160125 : "该互生号已被绑定",
	    160121 : "互生卡号不存在",
	    
	    99904 : "确认登录密码不能为空",
	    99905 : "两次密码不一致",
	    99918:"请输入正确的手机号",
	    33317:"手机号不能为空",
	    33318:"用户姓名不能为空",
	    33319:"用户状态不能为空",
	    33320:"请输入正确的互生卡号",
	    33321:"描述不超过100个字",
	    33322:"职务在20个字之内",
	    33323:"用户姓名在20个字之内",
	    
	    160453:"角色使用中,已分配权限不能删除",
	    accountStatus : {
	    	0 : "启用",
	    	1 : "停用"
	    },
	    bindResNoStatus : {
	    	2 : "绑定中",
	    	0 : "未绑定",
	    	1 : "绑定"
	    },
	    
	    notBind : "未绑定",
	    inBind : "绑定中",
	    bind : "已绑定",
	    
		update : "修改",
		del : "删除",
		activity : "启用",
		unactivity : "停用",
		isActivity : "确认启用此操作员？",
		isUnActivity : "确认停用此操作员？",
		delContent : "确认删除选中的记录？",
		groupUser : "组员维护",
		nullUserCustId : "未选者任何用户！",
		remove : "移除",
		confirRemoveUser: "您确认移除选择的用户?",
		confirAddUser : "您确认将选择的用名添加到用户组？",
		grantRole : "角色设置",
		menuPower : "菜单授权",
		cancelBind : "取消绑定",
		relieveBind: "解除绑定",
		confirCancelBind : "确认取消绑定？",
		bindCard : "绑定互生卡",
		pwd_number_repeated_error:"密码不规范:数字不能全部重复 。如111111、222222",                
		pwd_increase_error:"密码不规范:数字不能顺增或顺降。例123456、234567",
		duties_maxLength:"职务名称字数不能超过{0}个",
		userConfirError:"管理员验证错误！",
		managerPwd:"管理员登录密码错误"
	}
});
