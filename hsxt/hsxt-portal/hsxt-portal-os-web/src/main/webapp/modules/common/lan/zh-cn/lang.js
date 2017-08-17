define(["commSrc/comm"],function(){
	comm.langConfig["common"] ={		
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
		errorCodes : {
			'19200'	: '操作成功',
			'19201'	: '操作失败',
			'19202'	: '输入参数错误',
			'19203'	: '没有权限',
			'19204'	: '安全令牌无效',
			'19205'	: '文件不存在',
			'19206'	: '文件大小超过限制',
			'19207'	: '文件类型非法 ',
			'19400'	: '文件服务器不可用',
			'19401'	: '文件服务器繁忙',
			"22029" : "上传失败，文件类型不支持",
			"22030" : "上传失败，文件大小超过最大限制",
			"24001"	: "必传参数为空",
			"24008" : "身故人没有达到享受意外身故保障的福利的资格"
		}
		
		
	}
});
