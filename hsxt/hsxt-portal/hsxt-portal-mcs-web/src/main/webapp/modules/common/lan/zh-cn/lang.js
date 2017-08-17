define(["commSrc/comm"],function(){
	comm.langConfig['common'] ={		
		ui : {
			header:{
				shouye:'首页',
				anquantuichu:'安全退出'
			} ,
			nav:{
				zhanghuguanli:'账户管理',
				xitongziyuanguanli:'系统资源管理',
				jifenzhanghu:'积分账户',
				huobizhanghu:'货币账户',
				chengshishuishouduijiezhanghu:'城市税收对接账户',
				ziyuanpeieryulanbiao:'资源配额一览表',
				ziyuanpeierchaxun:'资源配额查询',
				qiyeziyuanguanli:'企业资源管理',
				xiaofeizheziyuantongji:'消费者资源统计',
				xitongdengjixinxi:'系统登记信息',
				gongshangdengjixinxi:'工商登记信息',
				lixixinxi:'联系信息',
				yinhangzhanghuxinxi:'银行账户信息',
				xiugaidenglumima:'修改登录密码',
				shezhijiaoyimima:'设置交易密码',
				xiugaijiaoyimima:'修改交易密码',
				chongzhijiyimima:'重置交易密码',
				shezhimibaowenti:'设置密保问题',
				shezhiyuliuxinxi:'设置预留信息',
				xiugaiyuliuxinxi:'修改预留信息',
				chaozuoyuanguanli:'操作员管理',
				yonghuzuguanli:'用户组管理',
				jiaosheguanli:'角色管理',
				fasongxiaoxi:'发送消息',
				yifaxiaoxi:'已发消息'
				
			} ,
			sideBar:{
				
			},
			rightBar:{
				
			},
			footer:{
				gongsimingcheng:'© 2013-2014 Intergrowth Corporation 深圳市互生科技有限公司',
				beianxinxi:'粤ICP备13058455号'
			}
		},
		quickDateEnum : {
			1 : "近一日",
			2 : "近一周",
			3 : "近一月"
			// 4 : "近三月"
		},
		160446:"当前的复核员隶属企业不是管理平台",
		160102:"复核员不存在",
		160101:"客户号不存在",
		160104:"复核员已锁定",
		160108:"复核员登录密码错误",
		160422:"操作员与复核员不能是同一人",
		160450:"管理平台复核员不具备双签复核员角色权限",
		160447:"管理平台复核员与当前操作员不属于同一企业",
		160452 :"该角色已被操作员使用，不能删除！",
	    160453 : "该角色已授权菜单，不能删除！",
	    
		160398:"操作失败，银行账号已经存在",
		22015:'双签验证失败:用户名称不能为空',
		22016:'双签验证失败:登陆密码不能为空',
		22017:'双签验证失败:验证类型不能为空',
		22018:'双签验证失败:生成随机Token失败，请重试.',
		22019:'双签验证失败:账号或密码错误.',
		22020:'双签验证失败:随机Token不能为空.',
		22022:"获取地区信息出现异常",
		22023:"获取国家信息出现异常，请重试",
		22024:"获取省份列表出现异常，请重试",
		22025:"获取银行列表出现异常，请重试",
		22026:"获取平台信息出现异常，请重试",
		22027:"获取城市列表出现异常，请重试",
		22063:"备注应为0~300个字符",
		//共用 
		begin_Date: '请输入开始日期',
		end_Date : '请输入结束日期',
		kjrq: '请选择快捷日期',
		ywlb:'请选择业务类别',
		//积分转货币
		jfzhb_zcjfs:'请输入转出积分数',
		jfzhb_jyzzh:'转入货币账户交易子账户',
		jfzhb_jymm:'请输入8位交易密码',
		
		//积分转货币-明细查询
		message:'1341234',
		comm : {
			requestError: '请求出错，请重试！',
			uploading : '文件上传中...',
			upload_erro : '文件上传错误',
			token_error : '获取token错误！'
		},
		// 接入渠道
		accessChannel :{
			1:'网页终端',
			2:'POS机终端',
			3:'刷卡器终端',
			4:'移动App终端',
			5:'互生平板',
			6:'系统终端',
			7:'语音终端',
			8:'第三方接入',
			9:'预留'
		},
		//服务公司-企业经营类型
		toBusinessTypes : {
			0 : "普通企业",
			1 : "连锁企业"
		},
		errorCodes : {
			'19200'	: '操作成功',
			'19201'	: '操作失败',
			'19202'	: '输入参数错误',
			'19203'	: '没有权限',
			'19204'	: '您的账号在异地登录，如需继续此操作，请重新登录！',
			'19205'	: '文件不存在',
			'19206'	: '文件大小超过限制',
			'19207'	: '文件类型非法 ',
			'19400'	: '文件服务器不可用',
			'19401'	: '文件服务器繁忙',
			"22029" : "上传失败，文件类型不支持",
			"22030" : "上传失败，文件大小超过最大限制",
		},
		creTypes : {
			"1" : "身份证",
			"2" : "护照",
			"3" : "营业执照"
		},
		//服务公司-企业经营类型
		toBusinessTypes : {
			0 : "普通企业",
			1 : "连锁企业"
		},
		//托管成员企业类型
		startResType:{ //企业资源类型
			1 : '首段资源',
			2 : '创业资源 ',
			3 : '全部资源',
			4 : '正常成员企业',
			5 : '免费成员企业'
		},
		sexTypes : {
			1 : "男",
			2 : "女"
		},
		custType:{
			1:'持卡人',
			2:'成员企业',
			3:'托管企业',
			4:'服务公司',
			5:'管理公司',
			6:'地区平台',
			7:'总平台',
			8:'其他平台'
		},
		//重要信息变更状态，
		importantChanteStatus:{0: "待审批", 1: "通过地区平台初审", 2: "通过地区平台复核", 3: "申请驳回"},
		entStatus:{
			1: "正常",
			2: "预警",
			3: "休眠",
			4: "长眠",
			5:"已注销",
			6:"申请停止积分活动中",
			7:"停止积分活动",
			8:"注销申请中"
		},
		businessType:{
			0:"普通企业", 1:"连锁企业"
		}
		,
		/**
		 * 互生卡补卡状态
		 */
		hsCardOrderStatus : {
			1 : "待付款",
			2 : "待配货",
			3 : "已完成 ",
			4 : "已过期",
			5 : "已关闭",
			6 : "待确认 ",
			7 : "已撤单"
		},
		cardStatus:{1: "启用", 2: "挂失", 3: "停用"},
		/**
		 * 积分福利类型
		 */
		welfareType:{
			0:"互生意外伤害保障",
			1:"互生医疗补贴计划",
			2:"代他人申请身故保障金"
		},
		/**
		 * 积分福利审批结果
		 */
		welfareApprovalStatus:{
			0:"受理中",
			1:"受理成功",
			2:"驳回"
		},
		//电话工单处理类型
		DealStatus:{
			0:"未知",
			1:"已完成",
			2:"处理中",
			3:"无效单"
		},
		//工单优先级
		Priority:{
			1:"低",
			2:"高"
		},
		//客服来电类型
		CallerType:{
			0:"未知",
			1:"运维类",
			2:"商务类",
			3:"诉建类"
		},
		callCenterReport:{1:"当日",2:"最近一周",3:"最近一月"},
		//OCX返回结果
		callCenterErrorMenu:{
			0:"无错误",
			100:"无效的参数",
			200:"无效的坐席参数",
			300:"座席已分配",//--2016.2.29 新增
			1000:"坐席不可用",
			1010:"没有可用的座席",
			1100:"坐席忙",
			1110:"座席置忙",// ---2016.2.19新增",
			1150:"坐席未签入",
			1200:"坐席已挂机：监听时",
			1300:"坐席未摘机：外呼时",
			1400:"无效的电话号码",
			1410:"交换机转内失败",//---2016.2.19新增
			2000:"操作超时",
			2010:"长度错误",
			3000:"没有可用的外线通道",
			3050:"无效的中继参数",
			3060:"中继不可用",
			3100:"坐席没有呼入",
			3100:"呼叫外线失败:通常是外线占线",
			3500:"转外呼叫失败",
			3550:"无人值守时转手机",
			4000:"没有可用的语音设备",
			5000:"数据库错误",
			5100:"无效的数据",
			5200:"系统错误",
			//语音文件下载错误描述
			6200:"文件不存在",
			6300:"文件不可访问"
		},
		requestFailed:'请求失败...',
		requestFailedWithCode:'请求失败，错误代码：',
		
		/** 日期错误提示 **/
		10001:'开始时间不能为空',
		10002:'结束时间不能为空',
		10003:'查询开始时间不能大于查询结束时间',
		10004:'查询日期间隔不能大于一年',
		
		/**BS错误码定义*/
		12000:"参数不能为nulll",
		12001:"dubbo调用UC失败",
		12002:"未查询到企业信息",
		12003:"调用LCS失败",
		12004:"没有查询到数据",
		12005:"记录已经完成",
		12006:"查询异常",
		12007:"任务状态不是办理中",
		12008:"查询地区平台客户号错误",
		12009:"超出互生币支付限额",
		12010:"参数不能为空",
		12011:"参数类型错误",
		12030:"保存增值节点失败",
		12101:"新增工具产品失败",
		12102:"工具上架或下架失败",
		12103:"工具有价格变更的待审批",
		12104:"工具上架或下架审批失败",
		12105:"新增工具价格变更失败",
		12106:"工具价格变更审批失败",
		12107:"新增互生卡样失败",
		12108:"互生卡样启用或停用失败",
		12109:"互生卡样设置成默认卡样失败",
		12110:"互生卡样启用或停用审批失败",
		12111:"工具申购下单购买数量超标",
		12112:"工具订单提交失败",
		12113:"订制卡样订单提交失败",
		12114:"新增平台代购订单失败",
		12115:"平台代购订单审批失败",
		12116:"查询工具互生卡失败",
		12117:"个人补卡订单提交失败",
		12118:"企业重做卡订单提交失败",
		12119:"工具订单支付处理中,稍等支付结果",
		12120:"工具订单已支付",
		12121:"工具订单已关闭或已失效",
		12122:"更新工具订单支付方式失败",
		12123:"更新卡样锁定状态失败",
		12124:"上传卡制作确认文件失败",
		12125:"上传卡样素材失败",
		12126:"企业确认卡样失败",
		12127:"上传互生卡制作确认文件失败",
		12128:"工具订单不是待确认状态",
		12129:"工具订单未查询到配置单",
		12130:"工具订单确认制作失败",
		12131:"工具订单撤单失败",
		12132:"生成积分刷卡器KSN失败",
		12133:"导入消费刷卡器KSN失败",
		12134:"工具设备不存在",
		12135:"工具设备不是未使用状态",
		12136:"根据配置单和客户号未查询到POS机数据",
		12137:"配置单不是待配置状态",
		12138:"设备已关联其他配置单",
		12139:"工具设备已经配置",
		12140:"POS机配置关联失败",
		12141:"根据配置单和客户号未查询到POS机或平板数据",
		12142:"未查询到设备关联",
		12143:"工具设备配置关联已使用 ",
		12144:"配置设备关系使用失败",
		12145:"根据配置单和客户号未查询到刷卡器数据",
		12146:"刷卡器配置关联失败",
		12147:"根据配置单和客户号未查询到互生平板数据",
		12148:"平板配置关联失败",
		12149:"配置单不是待开卡的状态",
		12150:"批量开卡失败",
		12151:"未查询到重做互生卡信息",
		12152:"重做互生卡开卡失败",
		12153:"互生卡入库失败",
		12154:"配置单不是待入库状态",
		12155:"卡制作单制成失败",
		12156:"新增配置方式失败",
		12157:"修改配置方式失败",
		12158:" 删除配置方式失败",
		12159:"新增快递公司失败",
		12160:"修改快递公司失败",
		12161:"删除快递公司失败 ",
		12162:"工具配置单类型不相同",
		12163:"不是同一企业",
		12164:"不是同一地址",
		12165:"不是同一服务公司下的企业",
		12166:"配置单不是待发货状态",
		12167:"发货单类型错误",
		12168:"新增发货单失败",
		12169:"工具签收失败",
		12170:"新增仓库失败",
		12171:"修改仓库失败",
		12172:"删除仓库失败",
		12173:"新增供应商失败",
		12174:"修改供应商失败",
		12175:"删除供应商失败",
		12176:"入库序列号为NULL",
		12177:"设备序列号有已经入库",
		12178:"刷卡器入库数据为NULL",
		12179:"该批次号已经入库",
		12180:"工具入库失败",
		12181:"工具库存盘库失败",
		12182:"工具入库抽检失败",
		12183:"当前批次的设备不存在",
		12184:"新增工具使用记录失败",
		12185:"修改配置单的仓库id失败",
		12186:"工具设备不是出库和使用状态",
		12187:"新增售后服务失败 ",
		12188:"未查询到售后服务数据",
		12189:"售后服务审批失败",
		12191:"未查询客户配置单的售后清单数据",
		12192:"配置刷卡器售后关联失败",
		12193:"售后设备不是未使用或已返修状态",
		12194:"售后配置秘钥设备失败",
		12195:"新增售后发货单失败",
		12196:"新增申报配置单失败",
		12197:"工具订单关闭失败",
		12198:"工具订单修改制作状态失败",
		12199:"订单状态错误，不是待付款或待确认状态",
		12200:"导出积分刷卡器ksn失败",
		12201:"导出消费刷卡器ksn失败",
		12202:"你已经申请了补办互生卡业务，无需再次申请",
		12203:"不是同一消费者",
		12204:"平台代购订单类型错误",
		12205:"仓库已经选择省份",
		12206:"配置单不是待制作状态",
		12207:"批次中有设备序列号已经导入",
		12208:"批量导入售后服务有处理中的序列号",
		12209:"设备序列号列表中有重复的数据",
		12210:"设备状态错误,是未使用或已报废状态",
		12211:"配置刷卡器售后保持关联失败",
		12212:"互生卡已确认制作数量超出最大数量",
		12300:"年费待审方案已存在",
		12301:"年费方案不存在",
		12302:"年费方案状态错误",
		12303:"年费方案DB错误",
		12304:"年费信息已存在",
		12305:" 费信息不存在",
		12306:"年费信息DB错误",
		12307:"年费计费单已存在",
		12308:"年费计费单不存在",
		12310:"此企业还未到缴年费时间",
		12311:"年费业务单不存在",
		12312:"年费欠款数据生成记账TXT文件错误",
		12313:"年费记账数据解析错误",
		12314:"年费计费单DB错误",
		12315:"年费业务单DB错误",
		12321:"重置交易密码申请存在",
		12322:"重置交易密码DB错误",
		12331:"开发票的金额不足",
		12332:"发票统计数据为null",
		12333:"发票金额不相等",
		12334:"平台发票DB错误",
		12335:"客户发票DB错误",
		12336:"客户清单DB错误",
		12337:"发票统计DB错误",
		12338:"平台发票不存在",
		12339:"该类发票金额已开完",
		12340:"消费积分扣除累计金额至少1000元才可开发票",
		12341:"此类型资源费待审方案已存在，请不要重复提交",
		12342:"资源费方案DB错误",
		12351:"税率调整DB错误",
		12352:"企业税率调整申请存在",
		12353:"生成上一个月税率调整记录TXT文件失败",
		12356:"收款账户信息已存在",
		12357:"该业务订单待复核的临账关联已存在",
		12361:"收款账户名称DB错误",
		12362:"收款账户信息DB错误",
		12363:"临账DB错误",
		12364:"临账DB错误",
		12365:"临账关联的业务订单不存在",
		12366:"关联总金额与折合货币金额不相等",
		12367:"临账关联DB错误",
		12368:"退款金额跟临账未关联金额不相等",
		12369:"临账退款DB错误",
		12370:"收款账户名称已存在",
		12371:"消息与公告DB错误",
		12372:"消息与公告发送异常",
		12381:"日终批记账形成TXT文件错误",
		12382:"日终账务对账形成TXT文件错误",
		12383:"增值详情DB错误",
		12384:"增值汇总DB错误",
		12385:"再增值积分DB错误",
		12406:"生成投资分红记帐文件失败",
		12407:"多线程生成投资分红记帐文件失败",
		12408:"目录路径错路",
		12409:"写入文件失败",
		12410:"校验文件数据失败",
		12463:"更新投资分红记帐状态失败",
		12476:"客户此业务已经被禁止",
		12477:"dubbo调用失败",
		12478:"帐户余额不足",
		12479:"获取快捷支付短信验证码异常",
		12480:"查询积分投资分红详情异常",
		12481:"查询积分投资分红计算详情异常",
		12482:"生成投资分红记录异常",
		12483:"投资分红生成记帐分解异常 ",
		12484:"投资分红生成记帐文件异常",
		12411:"读取投资分红对帐文件失败",
		12412:"保存工单异常",
		12413:"更新任务状态异常",
		12416:"查询订单详情异常",
		12422:"分页参数为空",
		12423:"查询订单列表异常",
		12435:"保存年度分红比率异常",
		12438:"保存积分投资异常",
		12440:"查询积分投资列表异常",
		12441:"查询积分投资分红列表异常",
		12442:"查询积分投资分红计算明细列表异常",
		12460:"更新订单状态异常",
		12461:"保存通用订单异常",
		12462:"更新订单客户号异常",
		12464:"更新资源费订单异常",
		12466:"记帐分解异常",
		12467:"查询未完成订单异常",
		12469:"获取积分投资通用信息异常",
		12471:"关闭订单异常",
		12472:"调用账务实时记账异常",
		12473:"获取支付URL异常",
		12474:"订单已超时",
		12475:"获取快捷支付短信码异常",
		12401:"获取年度分红比率列表异常",
		12402:"同步更新工单系统工单状态异常",
		12403:"定时生成年度投资分红记录",
		12404:"积分投资金额超过积分保底金额",
		12405:"订单已支付",
		12415:"业务订单不是待支付状态",
		12417:"业务订单不存在",
		12418:"重要信息变更期",
		12419:"查询客户状态异常",
		12485:"保存示例图片文档异常",
		12486:"已存在使用中的文档",
		12487:"更新文档状态异常",
		12488:"修改业务文件异常",
		12489:"文档标识已存在",
		12490:"查询办理业务列表异常",
		12491:"查询常用业务列表异常",
		12492:"订单正在审批中",
		12493:"保存消息模版异常",
		12494:"消息模版已存在",
		12495:"已存在使用中的消息模版",
		12496:"复核消息模版异常",
		12497:"模版名称已存在",
		12498:"启用消息模版异常",
		12499:"停用消息模版异常",
		12501:"可分配的资源配额数量不足 ",
		12502:"可释放的空闲资源配额数量不足",
		12503:"不是首次配置",
		12504:"审批数量大于申请数量",
		12505:"有申请中的数据",
		12506:"地区平台配额审批失败",
		12507:"地区平台配额申请失败",
		12508:"地区平台配额申请调用总平台失败",
		12509:"省配额申请失败",
		125010:"省配额审批失败",
		12511:"城市配额申请失败",
		12512:"城市配额审批失败",
		12513:"省配额审批状态错误",
		12514:"城市配额审批状态错误",
		12515:"有申请中的数据",
		12520:"提交意向客户失败",
		12521:"处理意向客户失败",
		12526:"保存报备企业基本信息失败",
		12527:"保存报备企业股东信息失败",
		12528:"保存报备企业附件失败",
		12529:"存在完全相同的报备企业名称",
		12530:"提交报备失败",
		12531:"审批报备失败",
		12532:"提出异议失败",
		12533:"审批异议报备失败",
		12534:"删除报备企业股东信息失败",
		12535:"删除报备失败",
		12540:"申报系统中已存在相同的企业名",
		12541:"该服务公司未报备该被申报企业",
		12542:"拟用互生号不能为空",
		12543:"启用资源类型为空或者与客户类型不匹配",
		12544:"企业互生号不可用（已使用或已占用）",
		12545:"客户类型错误",
		12546:"保存申报企业系统注册信息失败",
		12547:"保存申报企业工商登记信息失败",
		12548:"保存申报企业联系信息失败",
		12549:" 保存申报企业银行信息失败",
		12550:"保存申报企业附件信息失败",
		12551:"提交申报失败",
		12552:"服务公司审批申报失败",
		12553:"管理公司审批申报失败",
		12554:"管理公司复核申报失败",
		12555:"开系统欠款审核失败",
		12556:"用户中心开户失败",
		12557:"开启增值服务失败",
		12558:"开启系统处理本地事务失败",
		12559:"开启系统失败",
		12560:"该城市的配额已用完，没有可用的互生号",
		12561:"该申报企业已开启系统",
		12562:"选互生号失败",
		12563:"付款成功后更新申报相关信息失败",
		12564:"状态不是待提交，不能删除申报",
		12565:" 删除申报失败",
		12566:"保存积分增值点失败",
		12567:"分解资源费失败",
		12568:"拒绝开启系统失败",
		12569:"不能重新提交正在申报的申请",
		12570:"重新提交申报申请失败",
		12591:"存在正在审批的个人实名认证申请",
		12592:"申请个人实名认证失败",
		12593:"修改个人实名认证失败",
		12594:"审批个人实名认证失败",
		12595:"复核个人实名认证失败",
		12596:"存在正在审批的企业实名认证申请",
		12597:"申请企业实名认证失败",
		12598:"修改企业实名认证失败",
		12599:"审批企业实名认证失败",
		12600:"复核企业实名认证失败",
		12611:"存在正在审批的个人重要信息变更申请",
		12612:"申请个人重要信息变更失败",
		12613:"修改个人重要信息变更失败 ",
		12614:"审批个人重要信息变更失败",
		12615:"复核个人重要信息变更失败",
		12616:"存在正在审批的企业重要信息变更申请",
		12617:"申请企业重要信息变更失败",
		12618:"修改企业重要信息变更失败",
		12619:"审批企业重要信息变更失败 ",
		12620:"复核企业重要信息变更失败",
		12631:"申请成员企业注销失败",
		12632:"重复提交成员企业注销申请",
		12633:"服务公司审批成员企业注销失败",
		12634:"平台审批成员企业注销失败",
		12635:"平台复核成员企业注销失败",
		12636:"存在正在审批的重要信息变更",
		12637:"该企业欠年费",
		12638:"该企业存在未完成的订单",
		12639:"注销增值系统账户失败 ",
		12640:"互生币转货币失败",
		12641:"提交银行转账失败",
		12642:"注销用户中心账户失败",
		12643:"释放互生号失败",
		12651:"存在正在审批的积分活动申请",
		12652:"申请积分活动失败",
		12653:"服务公司审批积分活动失败",
		12654:"平台审批积分活动失败",
		12655:"平台复核积分活动失败",
		12656:"存在未完成的工具订单",
		12657:"欠款开启系统资源费未归还",
		12661:"保存合同模板失败",
		12662:"审批合同模板失败",
		12663:"启用合同模板失败",
		12664:"停用合同模板失败",
		12665:"合同盖章失败",
		12666:"合同发放失败",
		12667:"生成合同失败",
		12668:"未找到可用的合同模板",
		12685:"保存证书模板失败",
		12686:"审批证书模板失败",
		12687:"启用证书模板失败",
		12688:"停用证书模板失败",
		12689:"证书盖章失败",
		12690:"证书发放失败",
		12691:"生成证书失败",
		12692:"未找到可用的证书模板",
		12696:"申请关闭企业系统失败",
		12697:"申请开启企业系统失败",
		12698:"审批开关系统失败",
		12699:"存在正在审批的开关系统申请",
		12700:"股东已经存在",
		12701:"增值父节点未设置",
		12702:"申报企业未上传文件资料",
		12703:"申报企业缺少创业帮扶文件",
		12704:"申报企业缺少文件资料",
		12705:"证件已被使用",
		12706:"证书模板数据库操作异常",
		12707:"发送申报办理授权码出现异常",
		12708:"开启系统同步UC失败",
		12709:"开启系统同步BM失败",
		12710:"资源费分配记账失败",
		12711:"合同DB错误",
		12712:"在UC中存在重复数据",
		12713:"存在同类型处于已启用状态或停用待复核状态的模版",
		12901:"互生币扣除DB错误",
		12902:"重置交易密码发送短信失败",
		/**TM错误码定义代码范围：42000~42999*/
		42100:"参数为空",
		42101:"查询工单列表异常",
		42105:"保存任务异常",
		42107:"更新任务状态异常",
		42108:"分页参数为空",
		42110:"更新任务紧急状态异常",
		42111:"保存值班组异常",
		42112:"更新值班组异常",
		42113:"查询值班组异常",
		42114:"查询值班员列表异常",
		42115:"查询值班员信息异常",
		42116:"删除值班员异常",
		42117:"保存值班员异常",
		42118:"更改值班主任异常",
		42123:"值班组名称已存在",
		42124:"未设置值班主任",
		42125:"值班组名称不存在",
		42126:"值班计划已存在",
		42127:"月份校验失败",
		42128:"无值班计划或无排除",
		42129:"排班数有误",
		42131:"派单异常",
		42132:"未查询到数据",
		42133:"保存值班计划异常",
		42134:"保存值班员排班异常",
		42135:"修改值班员值班状态异常",
		42136:"暂停值班计划异常",
		42137:"执行值班计划异常",
		42138:"调班异常",
		42139:"必须有两个排班才可调班",
		42140:"换班异常",
		42141:"更新催办状态异常",
		42142:"查询转派工单列表异常",
		42143:"查询催办紧急工单列表异常",
		42144:"保存批量工单任务异常",
		42145:"添加值班员授权异常",
		42146:"删除值班员授权时异常",
		42147:"更新值班组开关状态异常",
		42148:"获取值班计划异常",
		42149:"业务类型已存在",
		42150:"保存业务类型时异常",
		42151:"保存授权记录异常",
		42152:"修改业务类型名称异常",
		42153:"删除业务类型异常",
		42154:"删除值班员授权异常",
		42155:"查询业务类型列表异常",
		42156:"获取值班员授权列表异常",
		42157:"查询企业业务类型列表异常",
		42158:"查询授权值班员时异常",
		42159:"查询当班的值班员时异常",
		42160:"获取随机值班员异常",
		42161:"自动催办紧急工单异常",
		42162:"没有操作员可办理此业务",
		42163:"调用用户中心接口异常",
		42164:"非值班主任无权限操作",
		/**AO异常码定义，异常代码范围：43000~43999*/
		43201:"参数为空",
		43202:"参数错误",
		43203:"超出最大限制",
		43204:"调用AC未查询到数据",
		43205:"校验失败",
		43206:"保存银行转账记录异常",
		43207:"保存银行转账失败记录异常",
		43208:"查询银行转账详情异常",
		43209:"查询银行转账列表异常",
		43210:"银行转账失败处理异常",
		43211:"未查询到银行转账记录异常",
		43212:"调用UC查询持卡人状态异常",
		43213:"调用UC查询企业状态异常",
		43214:"保存积分转互生币异常",
		43215:"保存互生币转货币异常",
		43216:"查询积分转互生币详情异常",
		43217:"查询互生币转货币详情异常",
		43218:"调用支付系统获取支付URL异常",
		43219:"调用支付系统获取手机支付TN码异常",
		43220:"调用支付系统获取快捷支付短信码异常",
		43221:"调用帐务系统查询帐户余额异常",
		43222:"调用帐务系统单笔冲正异常",
		43223:"调用支付系统单笔转账异常",
		43224:"保存兑换互生币异常 ",
		43225:"保存企业代兑互生币异常 ",
		43226:"保存POS兑换互生币异常",
		43227:"保存POS代兑互生币异常",
		43228:"冲正POS兑换互生币异常",
		43229:"冲正兑换互生币异常",
		43230:"冲正企业代兑互生币异常",
		43231:"冲正POS代兑互生币异常",
		43232:"代兑互生币冲正通知异地异常",
		43233:"代兑互生币冲正通知异地返回失败",
		43234:"查询兑换互生币详情异常",
		43235:"查询企业代兑互生币详情异常",
		43236:"获取网银支付链接异常",
		43237:"开通快捷支付并获取支付URL链接异常 ",
		43238:"获取快捷支付URL链接异常",
		43239:"获取手机支付TN码异常",
		43240:"获取快捷支付短信码异常",
		43241:"兑换互生币货币支付异常",
		43242:"兑换互生币更新支付状态异常",
		43243:"兑换互生币订单已超时",
		43244:"查询未支付的兑换互生币订单异常",
		43245:"兑换互生币更新支付方式异常",
		43246:"查询POS兑换互生币详情异常",
		43247:"查询POS代兑互生币详情异常",
		43248:"终端设备业务数据批结算保存异常",
		43249:"查询兑互生币总金额总笔数异常",
		43250:"询代兑互生币总金额总笔数异常",
		43251:"查询终端批结算上传标识异常",
		43252:"终端设备业务数据已经上传",
		43253:"查询终端批结算批总账编号异常",
		43254:"保存终端批上传异常",
		43255:"终端批结算更新上传时间和上传标识异常",
		43256:"删除错误(异常)",
		43257:"调用账务实时记账异常",
		43258:"保存记帐分解异常",
		43259:"银行转帐客户名与帐户名不一致",
		43260:"撤单异常 ",
		43261:"批量转帐异常",
		43262:"未查询到数据",
		43263:"帐户余额不足",
		43264:"数据迁移失败",
		43265:"生成文件失败",
		43266:"多线程生成日终生成记账对账文件失败",
		43267:"多线程生成日终生成网银支付对账文件失败",
		43268:"客户此业务已经被禁止",
		43269:"业务金额小于单笔最小允许金额",
		43270:"业务金额大于单笔最大允许金额",
		43271:"当日累计请求次数大于单日允许次数",
		43272:"当日累计申请金额大于单日允许金额",
		43273:"积分转互生币金额大于积分保底金额",
		43274:"批量自动提交转账异常",
		43275:"调用UC未查询到数据",
		43276:"银行转账对帐异常",
		43277:"调用UC查询银行帐户信息异常 ",
		43278:"银行转账对账异常",
		43279:"更新过期且未支付的兑换互生币订单异常",
		43280:"未批结算不可做批上传",
		43281:"写入文件错误",
		43282:"读取文件错误",
		43283:"不是文件夹",
		43284:"重复提交冲正",
		43285:"非法的冲正请求",
		43286:"更新银行帐户验证状态时异常",
		43287:"欠年费不能进行银行转账",
		43288:"存在相同的记录",
		43289:"欠年费不能进行代兑互生币",
		43290:"互生币支付金额大于单笔最大金额",
		43291:"互生币支付金额超出单日最大限额",
		43292:"销户积分转互生币异常",
		43293:"销户互生币转货币异常",
		43294:"互生币转货币金额超出保底互生币限制",
		43295:"重要信息变更期",
		43296:"调用支付系统计算手续费异常",
		43297:"获取客户私有参数项异常",
		43298:"未实名注册",
		/**LCS*/
		23801:"数据没找到 ",
		23804:"初始化地区平台信息未找到 ",
		/**用户中心*/
		160000:"未知错误，请查看日志",
		160001:"查询数据异常",
		160002:"处理超时",
		160003:"未成功加载配置文件(IO异常)",
		160004:"连接数据库超时",
		160005:"更新数据异常",
		160006:"保存数据异常",
		160007:"删除数据异常",
		160102:"用户不存在",
		160104:"用户已锁定",
		160103:"用户已存在",
		160110:"渠道类型错误",
		160108:"用户登录密码错误",
		160144:"用户已登录",
		160148:"用户名为空",
		160146:"数据解密错误",
		160222:"查询互生卡信息异常",
		160223:"互生卡信息不存在",
		160224:"持卡人信息不存在",
		160225:"查询客户号异常",
		160226:"非沉淀状态不能注销",
		160301:"当前操作员不是企业管理员",
		160227:"更新实名状态异常",
		160228:"检查证件是否注册异常",
		160229:"保存实名注册信息异常",
		160231:"更新互生卡信息异常",
		160235:"互生卡的卡状态为空",
		160236:"互生卡已启用",
		160237:"保存非持卡人信息异常",
		160147:"企业不存在",
		160121:"互生号不存在",
		160125:"互生卡已绑定",
		160126:"互生号不能为空",
		160127:"互生号已存在被使用",
		160122:"互生卡已停用",
		160131:"互生卡已挂失",
		160206:"实名认证信息不存在",
		160101:"客户号不存在",
		160350:"企业非激活状态",
		160106:"会话非法",
		160107:"会话过期",
		160351:"短信发送失败",
		160352:"邮箱未设置",
		160141:"邮箱未验证",
		160353:"用户邮箱错误",
		160129:"手机未验证",
		160133:"短信验证码不正确",
		160136:"邮箱验证码不正确",
		160134:"短信验证码已过期",
		160137:"邮箱验证码已过期",
		160354:"EMAIL发送失败",
		160355:"必传参数为空",
		160202:"系统用户信息不存在",
		160203:"更新系统用户信息失败",
		160204:"保存系统用户信息失败",
		160205:"秘钥长度不是16位",
		160221:"实名状态为[已实名注册]方能保存实名认证信息",
		160219:"证件已实名注册或实名认证",
		160220:"证件已实名注册",
		160214:"查询持卡人信息异常",
		160200:"保存持卡人信息异常",
		160211:"保存网络信息异常",
		160251:"更新非持卡人信息异常",
		160256:"通过客户号查询非持卡人信息异常",
		160254:"通过手机号码查询客户号异常",
		160140:"手机号码不正确",
		160253:"非持卡人信息不存在",
		160201:"更新持卡人信息异常",
		160145:"姓名不正确",
		160130:"保存互生卡信息异常",
		160109:"证件号码不正确",
		160212:"查询实名认证信息异常",
		160150:"用户类型非法",
		160149:"非法参数值",
		160120:"密保问题未设置",
		160210:"保存密保失败",
		160119:"密保信息不正确",
		160363:"设备未找到",
		160356:"问题修改失败",
		160357:"操作员未绑定互生卡",
		160358:"短信验证码验证失败",
		160359:"登录密码错误",
		160360:"交易密码错误",
		160361:"密码修改失败",
		160362:"收货地址修改失败",
		160364:"银行帐户未找到",
		160365:"POS机积分比例未设置",
		160366:"互生卡暗码不正确",
		160367:"设备未签入",
		160368:"POS机比对MAC失败次数超过最大限制，需重新登录",
		160369:"POS机比对MAC失败",
		160370:"随机token为空",
		160371:"随机token不正确",
		160372:"设备已存在",
		160373:"未登录或已被踢出",
		160374:"solr索引添加失败",
		160375:"solr索引更新失败",
		160376:"solr索引删除失败",
		160377:"solr索引查询失败",
		160378:"企业机积分比例未设置",
		160379:"密保问题不属于该用户",
		160380:"用户没有配置该收货地址",
		160381:"用户收货地址ID为空",
		160382:"此用户组已存在",
		160383:"用户没有权限访问该操作",
		160384:"企业已存在",
		160385:"授权码不正确",
		160386:"更新密保失败",
		160387:"查询密保失败",
		160207:"密保问题不正确或未设置",
		160388:"注销失败",
		160389:"互生code非法",
		160390:"查询域名信息失败",
		160391:"保存域名信息失败",
		160392:"更新域名信息失败",
		160393:"POS生成pikmac失败，详见日志",
		160394:"授权码已过期",
		160208:"非持卡人没有互生号",
		160395:"操作员信息更新失败",
		160215:"企业扩展信息更新失败",
		160396:"更新收货地址异常",
		160397:"保存收货地址异常",
		160398:" 是否默认银行账户字段值非法",
		160216:"企业状态信息更新失败",
		160217:"密保问题转换中文失败",
		160218:"批量查询持卡人的实名状态失败",
		160105:"通过企业的客户类型查询企业的信息失败",
		160111:"邮件标题转换中文失败",
		160399:"更新企业状态信息出错",
		160400:"删除企业快捷银行账户出错",
		160401:"删除消费者快捷银行账户出错",
		160402:"未通过消费者交易密码身份验证",
		160403:"消费者交易密码身份验证token已过期",
		160404:"重置登录密码的短信验证token已过期",
		160405:"重置登录密码的短信验证token不正确",
		160406:"重置登录密码的邮件验证token已过期",
		160407:"重置登录密码的邮件验证token不正确",
		160408:"重置登录密码的密保验证token已过期",
		160409:"重置登录密码的密保验证token不正确",
		160410:"搜索引擎搜索失败",
		160411:"交易密码连续出错导致用户锁定",
		160412:"登录验证码不存在",
		160413:"登录验证码不正确",
		160414:"删除收货地址异常",
		160415:"交易密码未设置",
		160416:"证件类型非法",
		160417:"管理员不能重置自己的登录密码",
		160418:"保存用户操作员记录日志表失败",
		160419:"互生卡未绑定",
		160420:"当前的复核员隶属企业不是地区平台",
		160421:"批量查询企业的客户号失败",
		160422:"操作员与复核员不能是同一人",
		160423:"当前复核员不具备双签复核员角色权限",
		160424:"批量查询企业税率失败",
		160425:"该企业未设置客户类型",
		160426:"企业客户类型不正确",
		160436:"传入的客户号与当前银行卡隶属的客户号不一致",
		160427:"角色已存在",
		160428:"权限已存在",
		160429:"不能删除有组员的用户组",
		160430:"保存银行账户信息异常",
		160431:"设置默认账户为非默认异常",
		160432:"银行账户已存在",
		160433:"查询企业银行账户数量异常",
		160434:"查询消费者银行账户数量异常",
		160435:"对象类型非法",
		160437:"角色使用中",
		160438:"查询未生效的企业税率信息异常",
		160439:"更新未生效的企业税率信息异常",
		160440:"删除未生效的企业税率信息异常",
		160441:"保存未生效的企业税率信息异常",
		160442:"查询企业税率记录数异常",
		160443:"未通过实名认证",
		160444:"第二个密码错误",
		160445:"第二个密码已经设置",
		160446:"当前的复核员隶属企业不是管理平台",
		160447:"管理平台复核员与当前操作员不属于同一企业",
		160448:"地区平台复核员与当前操作员不属于同一企业",
		160449:"地区平台复核员不具备双签复核员角色权限",
		160450:"管理平台复核员不具备双签复核员角色权限",
		160451:"企业操作员数量不能超过10000",
		160452:"角色使用中,已分配用户",
		160453:"角色使用中,已分配权限",
		160454:"不支持管理员修改登录密码",
		160455:"用户未设置手机号码",
		160456:"邮箱验证失败，验证邮箱链接已失效！",
		160457:"企业系统已关闭",
		160458:"修改企业名称或者联系方式（电话）,调用电商服务异常：",
		160459:"企业已注销",
		160460:"您的邮箱已经绑定且验证成功，请不要重复绑定。",
		160461:"您的手机号已经绑定且验证成功，请不要重复绑定。",
		160462:"操作员绑定互生号，发送互动消息失败。",
		/**PS*/
		11001:"参数错误",
		11002:"找不到原订单",
		11003:"批结算账不平",
		11004:"交易类型错误",
		11005:"积分小于最小值",
		11006:"找不到文件",
		11007:"编码格式",
		11008:"文件读写错误",
		11009:"数据库异常",
		11010:"调用账务错误",
		11011:"数据不存在",
		11012:"数据己存在",
		11013:"原订单已撤单，无法撤单",
		11014:"已经被冲正，无法重复冲正",
		11015:"线程异常",
		11016:"写文件异常",
		11017:"日终积分分配批处理任务异常",
		11018:"日终互生币结算批处理任务异常",
		11019:"日终积分结算批处理任务异常",
		11020:"日终终批处理任务-日终批量退税 任务异常",
		11021:"PS跨平台交易异常：秘钥长度错误",
		11022:"请求报文格式有误",
		11023:"调用账务非互生异常错误",
		11024:"调用用户中心系统读取客户号失败",
		11025:"原订单已退货，无法退货",
		11026:"退货金额不能大于原订单金额,无法退货",
		11027:"互生币单笔支付超限",
		11028:"互生币支付每日超限",
		11029:"缓存中找不到值",
		11030:"已冲正",
		11031:"交易类型错误",
		11032:"定金结算失败",
		11033:"请不要重复请求！谢谢合作",
		11034:"调用支付系统获取手机支付TN码异常",
		11035:"获取快捷支付短信验证码异常",
		11036:"调用支付系统获取快捷支付URL异常",
		/**AC*/
		13000:"参数为空",
		13001:"重复数据",
		13002:"参数格式错误",
		13003:"客户无此账户",
		13004:"账户余额可变更状态异常",
		13005:"正常记账金额为负",
		13006:"冲正红冲金额为正",
		13007:"消费者账户余额不足",
		13008:"数据库异常",
		13009:"金额超出",
		13010:"已冲正红冲",
		13011:"IO流异常",
		13012:"线程中断异常",
		13013:"企业账户余额不足",
		13014:"文件读写异常",
		13015:"已经完全退款",
		13016:"企业积分余额不足",
		13017:"企业互生币余额不足",
		13018:"企业货币余额不足",
		13019:"消费者积分余额不足",
		13020:"消费者互生币余额不足",
		13021:"消费者货币余额不足",
		13022:"当前退款金额大于原单可退款金额",
		/**其他*/
		15103:"该银行卡已经签约，不能重复签约!"
	}
});