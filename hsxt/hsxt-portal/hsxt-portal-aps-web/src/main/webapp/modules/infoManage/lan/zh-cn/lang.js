define(['commSrc/comm'], function(){
	comm.langConfig['infoManage'] = {
		22000:'操作成功', 
		22035:'查询开始时间不能为空',
		22011:'查询结束时间不能为空',
		22012:'查询开始时间格式错误',
		22013:'查询结束时间格式错误',
		22014:'查询开始时间不能大于查询结束时间',
		requestError : '请求错误',
		mustbj:'必填项不可以为空!',
		160355 : '必传参数为空',
		160354 : 'EMAIL发送失败',
		
		31053:'身份证格式不正确',
		31054:'护照格式不正确',
		31058:'联系人授权委托书不能为空',
		31059:'创业帮扶协议不能为空',
		31072:'法人代表证件正面文件不能为空',
		31073:'法人代表证件反面文件不能为空',
		31074:'营业执照扫描件文件不能为空',
		31075:'组织机构代码证扫描件不能为空',
		31076:'税务登记证扫描件不能为空',
		31077:'企业中文名称应为2~64个字',
		31078:'企业英文名称应为2~128个字',
		31079:'企业地址应为2~128个字',
		31080:'企业法人代表应为2~20个字',
		31081:'营业执照注册号不正确',
		31082:'联系人应为2~20个字',
		31083:'联系人手机格式不正确',
		31093:'组织机构代码证号为7~20个字',
		31094:"纳税人识别号为7~20个字符",
		31097:"营业执照注册号为7~20个字符",
		
		34505 : '复核员名称不能为空',
		34506 : '复核员密码不能为空',
		34531 : '企业名称不能为空',
		34532 : '企业英文名称不能为空',
		34533 : '企业地址不能为空',
		34534 : '法人代表不能为空',
		34535 : '法人代表证件类型不能为空',
		34536 : '法人代表证件号码不能为空',
		34537 : '营业执照注册号不能为空',
		34538 : '组织机构代码证号不能为空',
		34539 : '纳税人识别号不能为空',
		34540 : '法人代表证件正面图片不能为空',
		34541 : '法人代表证件反面图片不能为空',
		34542 : '营业执照扫描件不能为空',
		34543 : '组织机构代码证扫描件不能为空',
		34544 : '税务登记证扫描件不能为空',
		34545 : '授权委托书不能为空',
		34546 : '申请编号不能为空',
		34547 : '企业互生号不能为空',
		34548 : '企业客户号不能为空',
		34549 : '客户类型不能为空',
		34550 : '联系人不能为空',
		34551 : '联系人手机号不能为空',
		34552 : '请选择审核结果',
		
		34553:'姓名不能为空',
		34554:'性别不能为空',
		34555:'国籍不能为空',
		34556:'民族不能为空',
		34557:'户籍地址不能为空',
		34558:'发证机关不能为空',
		34559:'证件类型不能为空',
		34560:'证件号码不能为空',
		34561:'证件正面照不能为空',
		34562:'证件背面照不能为空',
		34563:'手持证件照不能为空',
		34564:'证件有效期不能为空',
		34565:'操作员客户号不能为空',
		34566:'操作员名字不能为空',
		34567:'操作员所属公司名称/个人消费者名称不能为空',
		34568:'双签操作员客户号不能为空',
		34569:'变更项不能为空',
		34570:"审批结果不能为空",
		34571:"请输入正确的姓名",
		36006:'备注说明应为0~300个字',
		36047:'复核员用户名不能为空',
		36048:'复核员登陆密码不能为空',
		36023:'企业名称不能为空',
		36024:'企业名称应为2~64个字',
		36025:'企业法人代表不能为空',
		36026:'企业法人代表应为2~20个字',
		36027:'企业类型应为2-20个字',
		36028:'组织机构代码证号不能为空',
		36029:'成立日期格式不正确',
		36030:'企业地址不能为空',
		36031:'企业地址应为2~128个字',
		36032:'营业执照注册号不能为空',
		36033:'营业执照注册号不正确',
		36034:'联系电话格式不正确',
		36035:'纳税人识别号不能为空',
		36036:'企业经营范围应为1~300个字',
		36037:'营业期限应为0~50个字',
		36038:'身份证格式不正确',
		36039:'护照格式不正确',
		36040:'证件号码不能为空',
		36041:'工商登记信息不能为空',
		36042:'账户名称不能为空',
		36043:'结算币种不能为空',
		36044:'银行卡号格式不正确',
		36045:'两次账号输入不一致',
		36046:'银行账户信息不能为空',
		36050:'请选择验证方式',
		30024:'验证码错误',
		160102:'复核员不存在',
		160108:'密码错误',
		36352:'开户行不能为空',
		36094:'所属省份不能为空',
		36095:'所属城市不能为空',
		22043:"银行卡号不能为空",
		22053: "验证码不能为空",
		30128: "验证码已过期",
		
		//企业联系信息
		36014 : "联系人姓名不能为空",
		36015 : "联系人姓名在2-20个字之内",
		36016 : "联系地址在2-128个字之内",
		36017 : "联系人手机不能为空",
		36018 : "请输入正确的手机号码",
		36019 : "请输入正确的邮政编码",
		36020 : "请输入正确的企业邮箱",
		36021 : "审核意见应为2-300个字符",
		
		notSupportValidateMode:'当前版本暂时不支持这种验证方式',
		confirmDelBank:'确定删除此银行帐户？',
		accountNoComIsNull:'确认账户不能为空',
		ent_update_prompt:"企业系统注册信息修改",
		describNotNull:"说明信息不能为空",
		orgNoError : "组织机构代码证号不正确",
		taxNoError : "纳税人识别号输入错误",
		changeValSame:'您的变更申请内容相同',
		
			
		//企业积分状态
		pointStatus : {
			1:'正常',
			2:'预警',
			3:'休眠',
			4:'长眠',
			5:'已注销',
			6:'申请停止积分活动中',
			7:'停止积分',
			8:'注销申请中'
		},
		idCardTypeEnum : {//证件类型
			1:'身份证',
			2:'护照',
		},
		idCardType : {//证件类型
			1:'身份证',
			2:'护照',
			3:'营业执照'
		},
		//企业认证状态
		realNameAuthSatus : {
			1 : '已认证',
			0 : '未认证'
		},
		whetherEnum : {//是否
			'':'',
			0:'否',
			1:'是',
		},
		//成员企业注销、 积分活动审批状态
		appStatusEnum : {
			0:'待服务公司审批',
			4:'服务公司审批驳回',	//
			1:'待地区平台审批',		//（或）待地区平台审批
			5:'地区平台审批驳回',	//
			2:'待地区平台复核',		//（或）待地区平台复审
			3:'地区平台复核通过',	//（或）地区平台复审通过
			6:'地区平台复核驳回'
		},
		applyBusinessStatueEnum:{ 
		    0 : '待服务公司审批',	//待服务公司审批
		    1 : '待地区平台审批',	//待地区平台审批
		    2 : '待地区平台复核',	//待地区平台复核
		    3 : '地区平台复核通过', //地区平台复核通过
		    4 : '服务公司审批驳回',	//服务公司审批驳回
		    5 : '地区平台审批驳回',  //地区平台审批驳回
		    6 : '地区平台复核驳回' //地区平台复核驳回
		},
		realNameAuthBizAction:{
			1:'实名认证申请提交',
			2:'实名认证申请初审修改',
			3:'实名认证申请初审通过',
			4:'实名认证申请初审驳回',
			5:'实名认证申请复核修改',
			6:'实名认证申请复核通过',
			7:'实名认证申请复核驳回'
		},
		 entCustType :{
				2 : '成员企业',
				3 : '托管企业',
				4 : '服务公司'
		},
		changeInfoBizAction:{
			1:'重要信息变更申请提交',
			2:'重要信息变更申请初审修改',
			3:'重要信息变更申请初审通过',
			4:'重要信息变更申请初审驳回',
			5:'重要信息变更申请复核修改',
			6:'重要信息变更申请复核通过',
			7:'重要信息变更申请复核驳回'
		},
		//实名认证状态
		realNameStatus : {
			0:'未审批',
			1:'地区平台初审通过',
			2:'地区平台复审通过',
			3:'审核未通过'
		},
		//实名认证审批查询状态
		realNameSerchStatus : {
			0:'待审批',
			3:'审批驳回',
			1:'待复核',
			2:'复核通过',
			4:'复核驳回'
		},
		//实名认证状态
		realNameStatuEnum : {
			0:'待审批',
			1:'待复核'
		},
		//资格注销状态
		memberStatuEnum : {
			1:'待地区平台审批',
			2:'待地区平台复核'
		},
		//重要信息变更状态（bug：15208，15209）
		importantStatus : {
			0:'待审批',
			1:'待复核'
		},
		//重要信息变更审批查询状态
		importantSerchStatus : {
			0:'待审批',
			3:'审批驳回',
			1:'待复核',
			2:'复核通过',
			4:'复核驳回'
		},
		//实名认证业务结果
		realNameAuthBizResult : {
			1 : '实名认证申请提交',
			2 : '修改实名认证申请',
			3 : '实名认证初审通过',
			4 : '实名认证复核通过',
			5 : '实名认证审批驳回'
		},
		//消费者实名认证可修改的项
		smrzPersonItem : {
			entName : '企业名称',
			entRegAddr : '注册地址',
			entBuildDate : '成立日期',
			entType : '公司类型',
			
			name : '姓名',
			sex : '性别',
			countryName : '国籍',
			certype : '证件类型',
			credentialsNo : '证件号码',
			validDate : '证件有效期',
			birthAddr : '户籍地址',
			birthAddr2 : '出生地点',
			postScript : '认证附言',
			licenceIssuing:'发证机关',
			licenceIssuing2:'签发机关',
			profession:'职业',
			cerpica : '证件正面',
			cerpicb : '证件反面',
			issuePlace:'签发地点',
			cerpich : '手持证件照'
		},
		//企业实名认证可修改的项
		smrzEntItem : {
			entCustName : '企业中文名称',
			entCustNameEn : '企业英文名称',
			entAddr : '企业地址',
			legalName : '法人代表',
			certype : '法人代表证件类型',
			legalCreNo : '法人代表证件号码',
			licenseNo : '营业执照注册号',
			orgNo : '组织机构代码证号',
			taxNo : '纳税人识别号',
			lrcFacePic : '法人代表证件正面',
			lrcBackPic : '法人代表证件反面',
			licensePic : '营业执照扫描件',
			orgPic : '组织机构代码证扫描件',
			taxPic : '税务登记证扫描件',
			certificatePic : '联系人授权委托书扫描件'
		},
		//个人重要信息变更审批可变项
		zyxxPersonItem : {
			entBuildDateNew : '成立日期',
			entRegAddrNew : '注册地址',
			entTypeNew : '企业类型',
			entNameNew : '企业名称',
			
			nameNew : '姓名',
			sexNew : '性别',
			nationalityNew : '国籍',
			certype : '证件类型',
			creTypeNew : '证件类型',
			creNoNew : '证件号码',
			creExpireDateNew : '证件有效期',
			creIssueOrgNew:'发证机关',
			creIssueOrgNew2:'签发机关',
			registorAddressNew : '户籍地址',
			registorAddressNew2: '出生地点',
			professionNew:'职业',
			issuePlaceNew:'签发地点',
			applyReason : '申请变更原因',
			creFacePicNew : '证件正面',
			creBackPicNew : '证件反面',
			creHoldPicNew : '手持证件照',
			residenceAddrPic:'户籍变更证明'
		},
		zyxxEntItem : {
			custNameNew : '企业中文名称',
			custNameEnNew : '企业英文名称',
			custAddressNew : '企业地址',
			legalRepNew : '法人代表',
			legalRepCreTypeNew : '法人代表证件类型',
			certype : '法人代表证件类型',
			legalRepCreNoNew : '法人代表证件号码',
			bizLicenseNoNew : '营业执照注册号',
			organizerNoNew : '组织机构代码证号',
			taxpayerNoNew : '纳税人识别号',
			linkmanNew : '联系人姓名',
			linkmanMobileNew : '联系人手机',
			legalRepCreFacePicNew : '法人代表证件正面',
			legalRepCreBackPicNew : '法人代表证件反面',
			bizLicenseCrePicNew : '营业执照扫描件',
			organizerCrePicNew : '组织机构代码证扫描件',
			taxpayerCrePicNew : '税务登记证扫描件',
			linkAuthorizePicNew : '联系人授权委托书扫描件',
			imptappPic : '重要信息变更业务办理申请书'
		},
		//企业实名认证证件类型
		legalCreType : {
			1 : '身份证',
			2 : '护照'
		},
		//消费者实名认证证件类型
		realNameCreType : {
			1 : '身份证',
			2 : '护照',
			3 : '营业执照'
		},
		personSex : {
			1 : '男',
			0 : '女'
		},
		sexType : {
			1 : '男',
			0 : '女'
		},
		verificationMode : {
			1 : '密码验证',
			2 : '指纹验证',
			3 : '刷卡验证'
		},
		//消费者实名状态
		consumerRealNameAuthSatus : {
			1 : '未实名注册',
			2 : '实名注册',
			3 : '实名认证'
		},
		//消费者互生卡状态
		consumerCardStatus : {
			1 : '启用',
			2 : '停用',
			3 : '挂失'
		},
		//业务许可
		businessPermison : {
			0 : '启用',
			1 : '限制'
		},
		aptTypeEnum : {//附件类型
			1:'法人代表证件正面',
			2:'法人代表证件反面',
			3:'营业执照扫描件',
			4:'银行资金证明',
			5:'合作股东证明代码证明',
		},
		aptitudeTypeEnum : {//附件类型
			1:'法人代表证件正面',
			2:'法人代表证件反面',
			3:'营业执照扫描件',
			6:'组织机构代码证扫描件',
			7:'税务登记证扫描件',
			8:'创业帮扶协议',
		},
		bizStatusEnum : {//办理状态信息-业务状态
			1:'内容待确定',
			2:'内容待确定',
			3:'内容待确定',
		},
		bizResultEnum : {//办理状态信息-业务结果
			1:'内容待确定',
			2:'内容待确定',
			3:'内容待确定',
		},
		//业务许可
		businessPermisonSetResult : "业务许可设置成功",
		//修改企业信息
		updateEntBaseResult : "修改企业信息成功",
		//消费者资源一览消费者实名状态
		custRealNameAuthSatus : {
			1 : '未实名注册',
			2 : '实名注册',
			3 : '实名认证'
		},
		workTaskRefuseAccept : '拒绝受理',
		
		wtCustInfoChangeRefuseConfirm: '您确认拒绝受理消费者重要信息变更审批业务的工单?',
		wtCustInfoChangePauseConfirm: '消费者重要信息变更审批业务',
		
		wtTrustteeInfoChangeRefuseConfirm: '您确认拒绝受理托管企业重要信息变更审批业务的工单?',
		wtTrustteeInfoChangePauseConfirm: '托管企业重要信息变更审批业务',
		
		wtMemberInfoChangeRefuseConfirm: '您确认拒绝受理成员企业重要信息变更审批业务的工单?',
		wtMemberInfoChangePauseConfirm: '成员企业重要信息变更审批业务',
		
		wtServiceInfoChangeRefuseConfirm: '您确认拒绝受理服务公司重要信息变更审批业务的工单?',
		wtServiceInfoChangePauseConfirm: '服务公司重要信息变更审批业务',
		
		wtMemberCompCancelRefuseComfirm:'您确认拒绝受理该成员企业资格注销审批业务的工单?',
		wtMemberCompCancelPauseComfirm:'成员企业资格注销审批业务',
		
		wtStopPointRefuseComfirm:'您确认拒绝受理停止积分活动审批业务的工单?',
		wtStopPointPauseComfirm:'停止积分活动审批业务',
		
		wtJoinPointRefuseComfirm:'您确认拒绝参与积分活动审批业务的工单?',
		wtJoinPointPauseComfirm:'参与积分活动审批业务',
		
		wtCusteRealNameRefuseComfirm:'您确认拒绝受理消费者实名认证审批业务的工单?',
		wtCusteRealNamePauseComfirm:'消费者实名认证审批业务',
		
		wtTrustteeRealNameRefuseComfirm:'您确认拒绝受理托管企业实名认证审批业务的工单?',
		wtTrustteeRealNamePauseComfirm:'托管企业实名认证审批业务',
		
		wtMemberRealNameRefuseComfirm:'您确认拒绝受理成员企业实名认证审批业务的工单?',
		wtMemberRealNamePauseComfirm:'成员企业实名认证审批业务',
		
		wtServiceRealNameRefuseComfirm:'您确认拒绝受理服务公司实名认证审批业务的工单?',
		wtServiceRealNamePauseComfirm:'服务公司实名认证审批业务',
		
		workTaskHangUp : '挂起',
		workTaskHangUpConfir : '您确认挂起扣款复核的工单?',
		workTaskInfoMgnPauseConfirm:'重要信息变更审批业务',
		apprval : '审批',
		fuhe : '复核',
		sptitle:'成员企业资格注销审批',
		fhtitle:'成员企业资格注销复核',
		query : '查看',
		queryTitile:'成员企业资格注销审批查询详情',
		hdsptitle : '参与积分活动申请审批',
		hdfhtitle : '参与积分活动申请复核',
		tzjfhdcxxq : '停止积分活动审批查询详情',
		cyjfhdcxxq : '参与积分活动审批查询详情',
		hdspSuccess : '活动审批成功!',
		hdfhSuccess : '活动复核成功!',
		
		apprveResult : '请选择审核结果',
		smrzconfirUpdata : '实名认证修改确认',
		zyxxbgconfirUpdata : '重要信息变更修改确认',
		xfzsmrzUpdataSuccess : '消费者实名认证修改成功!',
		xfzsmrzspSuccess : '消费者实名认证审批成功!',
		xfzsmrzfhSuccess : '消费者实名认证复核成功!',
		cyqysmrzUpdataSuccess : '成员企业实名认证修改成功!',
		cyqysmrzspSuccess : '成员企业实名认证审批成功!',
		cyqysmrzfhSuccess : '成员企业实名认证复核成功!',
		fwgssmrzUpdataSuccess : '服务公司实名认证修改成功!',
		fwgssmrzspSuccess : '服务公司实名认证审批成功!',
		fwgssmrzfhSuccess : '服务公司实名认证复核成功!',
		tgqysmrzUpdataSuccess : '托管企业实名认证修改成功!',
		tgqysmrzspSuccess : '托管企业实名认证审批成功!',
		tgqysmrzfhSuccess : '托管企业实名认证复核成功!',
		
		xfzzyxxUpdataSuccess : '消费者重要信息修改成功!',
		xfzzyxxspSuccess : '消费者重要信息审批成功!',
		xfzzyxxfhSuccess: '消费者重要信息复核成功!',
		cyqyzyxxUpdataSuccess : '成员企业重要信息修改成功!',
		cyqyzyxxspSuccess : '成员企业重要信息审批成功!',
		cyqyzyxxfhSuccess : '成员企业重要信息复核成功!',
		fwgszyxxUpdataSuccess : '服务公司重要信息修改成功!',
		fwgszyxxspSuccess : '服务公司重要信息审批成功!',
		fwgszyxxfhSuccess : '服务公司重要信息复核成功!',
		tgqyzyxxUpdataSuccess : '托管企业重要信息修改成功!',
		tgqyzyxxspSuccess : '托管企业重要信息审批成功!',
		tgqyzyxxfhSuccess : '托管企业重要信息复核成功!',
		
		noUpdate : '没有修改任何内容',
		statusInfo : '办理状态信息详情',
		updateTitle:'修改',
		updateRecordTitle:'修改记录',
		notAloneChangeLicenseNo:"不单独提供申请营业执照注册号的重要信息的变更业务。",
		pleaseInputCreNo:'法人证件类型变更,请填入证件号码',
		hasAppr : '已审批',
		hasReview : '已复核',
		
		longTerm :'长期',
		blztxxNoUpdate:"无修改信息",
		resFeeChargeModeEnum:{//系统销售费收费方式
			1 : '正常开启系统', 
			2 : '免费开启系统', 
			3 : '欠费开启系统'
		},
		artResTypeEnum:{ //企业资源类型
			1 : '首段资源', 
			2 : '创业资源 ', 
			3 : '全部资源', 
			4 : '正常成员企业', 
			5 : '免费成员企业'
		},
		businessTypeEnum:{//经营类型
			0 : '普通企业',
			1 : '连锁企业'
		},
		particularPointStatusEnum:{ //参与积分状态
			 2 : '参与积分',
			 1 : '停止积分'
		},
		//企业状态
		entStatusEnum : {
			1:'正常',
			2:'预警',
			3:'休眠',
			4:'长眠',
			5:'已注销',
			6:'申请停止积分活动中',
			7:'停止积分',
			8:'注销申请中'
		},
		//互生卡状态
		HSCardStatusEnum:{
			1:'不活跃',
			2:'活跃',
			3:'休眠',
			4:'沉淀'
		}
	}
});