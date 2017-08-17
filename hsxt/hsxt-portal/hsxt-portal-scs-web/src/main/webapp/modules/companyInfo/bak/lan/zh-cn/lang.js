define(["commSrc/comm"], function () {
	comm.langConfig["companyInfo"] = {
		//后台返回的状态码	
		 errorCodes : {
			22000 : "操作成功",
			22001 : "操作失败",
			33001 : "所在城市不能不能空",
		    33002 : "所在省份不能为空",
		    33003 : "开户银行不能为空",
		    33004 : "银行卡号不正确",
			    
			    // 联系人信息
		    33005 : "办公室电话不正确",
		    33006 : "邮箱地址不正确",
		    33007 : "邮政编码不正确",
		    33008 : "传真号码不正确",
		    33009 : "QQ号码不正确",
			  
				// 重要信息变更
		    33010 : "企业名称不能为空",
		    33011 : "企业地址不能为空",
		    33012 : "法人代表不能为空",
		    33013 : "法人代表证件号码不能为空",
		    33014 : "法人代表证件不能为空",
		    33015 : "营业执照注册号不能为空",
		    33016 : "组织机构号码不能为空",
		    33017 : "纳税人识别号不能为空",
		    33018 : "申请书文件不能为空",
		    33019 : "授权委托书图片不能为空",
		    33020 : "重要信息变更业务办理申请书不能为空",
			    
		    33021 : "手机号码不正确",
		    33022 : "网站地址不正确",
		    33023 : "邮政编码不正确",
		    160101: "客户号不存在",
		    160355: "必传参数为空",
		    12000 : "参数不能为空",
		    12545 : "重复申请企业实名认证",
		    12002 : "申请个人重要信息变更失败",
		    12574 : "重复申请企业重要信息变更",
		    160114:	"银行账户已存在",
		    160115:	"银行账户已锁定",
		    160101: "客户号不存在，企业客户号：1005",
		    
		    19200	: '操作成功',
		    19201	: '操作失败',
		    19202	: '输入参数错误',
		    19203	: '没有权限',
		    19204	: '您的账号在异地登录，如需继续此操作，请重新登录！'

		},
		
		custEntTypes : [
				
			{
				"value" : 2,
				"name"  : "成员企业"
			},
			{
				"value" : 3,
				"name"  : "托管企业"
			},
			{
				"value" : 4,
				"name"  : "服务公司"
			},
			{
				"value" : 5,
				"name"  : "管理公司"
			},
			{
				"value" : 6,
				"name"  : "地区平台"
			},
			{
				"value" : 7,
				"name"  : "总平台"
			},
			{
				"value" : 8,
				"name"  : "其他地区平台"
			}
		],
		
		//证件类型
		credenceTypes : {
			1 : "身份证",
			2 : "护照",
			3 : "营业执照"
		},
		
		quickDate : {
			'today' : 'getTodaySE',
			'week' : 'getWeekSE',
			'month' : 'getMonthSE'
		},
		applyStatus : {
			0 : "待审批",
			1 : "地区平台初审通过",
			2 : "地区平台复审通过",
			3 : "审批未通过"
		},
		
		bankList : {
			1 : "工商银行",
			2 : "农业银行",
			3 : "中国银行",
			4 : "建设银行"
		},
		//universal 通用的提示信息
		universal :{
			unchanged : "没有任何信息被修改",
			confirm : "确定",
			confirm_submit : "确定是否提交",
			close : "关闭",
			clear : "取消",
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
			today : "今天",
			current_week : "本周",
			current_moth : "本月",
			view : "查看",
			view_detail : "查看详细",
			id_card : "身份证",
			pass_port : "护照",
			bus_licence : "营业执照"
		},
		
		gxdjxx : {
			not_empty_company_type : "企业类型不能为空",
			not_empty_create_date : "创建时间不能为空",
			not_empty_contact_tel : "联系人手机号码不能这空",
			input_tel_illage : "手机号码输入不正确",
			input_busScope_out_length : "输入的经营范围不能超过300个字符"
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
		  del_bank_acc_confirm	: "确认删除银行账户",
		  bank_no_empty	: "银行卡号不能为空",
		  bank_acc_not_empty : "开户银行不能为空",
		},
		zyxxbg:{
			approve_wait_promt : "实名认证申请已经提交,请等待审批",
			not_empty_company_name : "企业名称不能为空",
			not_empty_company_addr : "企业地址不能为空",
			not_empty_legal_Person : "输入非法的法人代表",
			not_empty_legal_credence_type : "输入非法的法人代表证件类型",
			not_empty_legal_credence_no : "输入非法的法人代表证件号码",
			not_empty_busLisence_no : "输入非法的营业执照注册号",
			not_empty_organization_no : "输入非法的组织机构代码证号",
			not_empty_taxpalyer_no : "输入非法的纳税人识别号",
			not_empty_contact_p_name : "输入非法的联系人姓名",
			not_empty_contact_p_tel : "输入非法的联系人手机",
			change_apply_file_required : "必须上传重要信息变更的申请书",
			powerOfAttorney_file_required : "必须上传授权委托书的扫描件",
			promt_approving : "目前您处于重要信息变更申请处理中，在此期间，本业务暂无法受理!",
			promt_confirm_submit : "重要信息变更申请提交处理期间，货币转银行账户业务、代兑互生币业务、积分投资业务暂无法受理，确认要提交申请？",
			promt_change_condition : "还未实名认证，不能进行重要信息变更。"
		},
		
		provinces :[
			{
				"name" : "广东",
				"value" : "1",
				"countryCode" : "1"
			},
			{
				"name" : "湖南",
				"value" : "2",
				"countryCode" : "1"
			}
		],
		
		citys :[
			{
				"name" : "深圳",
				"value" : "11",
				"provinceCode" : "1"
			},
			{
				"name" : "长沙",
				"value" : "22",
				"provinceCode" : "2"
			}
		],
		

	}
});