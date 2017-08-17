define(function () {
	return {
		//查询订单详情
		seachToolOrderDetail : function(data,callback){
			comm.requestFun("tool_order_detail",data,callback,comm.lang("toolorder"));
		},
		//获得文件在文件系统服务器中的url
		getFsServerUrl : function(fileId) {
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			
			return  comm.domainList['fsServerUrl']+fileId+"?userId="+custId+"&token="+token;
		},
		//文件上传路径
		getUploadFilePath : function(){
			return  comm.domainList['apsWeb']+comm.UrlList["fileupload"];
		},
		wrapParam : function (paraData){
			//测试cookie读取操作
			$.cookie('cookie_custId', 'cookie_custId');
			$.cookie('cookie_pointNo', 'cookie_pointNo');
			$.cookie('cookie_token', 'cookie_token');
			//读取
			//var custId = $.cookie('cookie_custId'); // 读取 cookie 客户号
			//var hsNo = $.cookie('cookie_pointNo'); // 读取 cookie pointNo
			//var token = $.cookie('cookie_token'); // 读取 cookie pointNo
			
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			var entCustId ='token_entcustId_p25_2015_10_16';
			var entResNo ='token_entResNo_p25_2015_10_16';
			var param={
					custId  : custId,
					hsNo    : hsNo,
					token   : token,
					entCustId : entCustId,
					entResNo : entResNo
			};
			
			if(typeof(paraData) != undefined &&paraData!=""&&paraData!=null){
				 return $.extend(param,paraData);
			}else {
				return param;
			}
		},
		//添加关联之前的校验
		addRelation : function(data,callback){
			comm.requestFun("add_relation",data,callback,comm.lang("toolorder"));
		},
		//批量添加关联
		addBatchRelation : function(data,callback){
			comm.requestFun("add_batch_relation",data,callback,comm.lang("toolorder"));
		},
		//查询设备关联详情
		queryDeviceRelevanceDetail : function(data,callback){
			comm.requestFun("queryDeviceRelevanceDetail",data,callback,comm.lang("toolorder"));
		},
		//查询企业重要信息（联系人、联系电话）
		searchEntMainInfo : function(data,callback){
			comm.requestFun("query_entmaininfo",data,callback,comm.lang("toolorder"));
		},
		//查询互生卡及对应密码列表
		findHscPass : function(data,callback){
			comm.requestFun("hscardmade_password_list",data,callback,comm.lang("toolorder"));
		},
		//开卡
		openCard : function(data,callback){
			comm.requestFun("hscardmade_opencard",data,callback,comm.lang("toolorder"));
		},
		//关闭订单
		closeOrder : function(data,callback){
			comm.requestFun("hscardmade_closeorder",data,callback,comm.lang("toolorder"));
		},
		//确认订单
		confirmOrder : function(data,callback){
			comm.requestFun("tool_confirm",data,callback,comm.lang("toolorder"));
		},
		//制作单作成查询
		productionList : function(data,callback){
			comm.requestFun("hscardmade_zzdzc",data,callback,comm.lang("toolorder"));
		},
		//互生卡制作单作成
		cardMark : function(data,callback){
			comm.requestFun("hscardmade_cardmark",data,callback,comm.lang("toolorder"));
		},
		//互生卡制作单作成
		queryCardMark : function(data,callback){
			comm.requestFun("hscardmade_querycardmark",data,callback,comm.lang("toolorder"));
		},
		//查询互生卡配置卡样
		queryCardStyle : function(data,callback){
			comm.requestFun("hscardmade_querycardstyle",data,callback,comm.lang("toolorder"));
		},
		//卡样上传
		queryUploadcardstyle : function(data,callback){
			comm.requestFun("hscardmade_uploadcardstyle",data,callback,comm.lang("toolorder"));
		},
		//互生卡入库查询
		queryCardIn : function(data,callback){
			comm.requestFun("hscardmade_querycardin",data,callback,comm.lang("toolorder"));
		},
		//互生卡入库
		cardIn : function(data,callback){
			comm.requestFun("hscardmade_cardin",data,callback,comm.lang("toolorder"));
		},
		//导出密码
		exportMM : function(confNo,companyResNo){
			return comm.domainList['apsWeb']+comm.UrlList["hscardmade_exportpassword"] + "?confNo=" + confNo+"&companyResNo="+companyResNo+"&loginToken="+comm.getSysCookie('token')+"&custId="+comm.getSysCookie('custId')+"&channelType=1";
		},
		//导出暗码
		exportDark : function(confNo,companyResNo){
			return comm.domainList['apsWeb']+comm.UrlList["hscardmade_exportdark"] + "?confNo=" + confNo+"&companyResNo="+companyResNo+"&loginToken="+comm.getSysCookie('token')+"&custId="+comm.getSysCookie('custId')+"&channelType=1";
		},
		//修改卡样锁定状态
		modifyCardIsLock : function(data,callback){
			comm.requestFun("hscardmade_modifycarylock",data,callback,comm.lang("toolorder"));
		},
		//根据互生号查询企业重要信息
		queryEntMainInfoByResNo : function(data,callback){
			comm.requestFun("query_entmaininfo_byresno",data,callback,comm.lang("toolorder"));
		},
		//查询可以购买的工具类型
		queryToolType : function(data,callback){
			comm.requestFun("query_tool_type",data,callback,comm.lang("toolorder"));
		},
		//查询可以购买的工具数量
		queryToolNum : function(data,callback){
			comm.requestFun("query_tool_num",data,callback,comm.lang("toolorder"));
		},
		//查询收货地址、企业地址
		queryAddress : function(data,callback){
			comm.requestFun("query_address",data,callback,comm.lang("toolorder"));
		},
		//查询卡样
		queryCarTypes : function(data,callback){
			comm.requestFun("query_car_style",data,callback,comm.lang("toolorder"));
		},
		//提交代购订单
		commitProxyOrder : function(data,callback){
			comm.requestFun("add_plat_proxyorder",data,callback,comm.lang("toolorder"));
		},
		//查询代购订单详情
		queryProxyOrder : function(data,callback){
			comm.requestFun("query_proxyorder_auditing_detail",data,callback,comm.lang("toolorder"));
		},
		//审核代购订单
		audingProxyOrder : function(data,callback){
			comm.requestFun("auditing_proxyorder",data,callback,comm.lang("toolorder"));
		},
		//查看配送信息
		queryShippinData : function(data,callback){
			comm.requestFun("query_tooldispatching_detail",data,callback,comm.lang("toolorder"));
		},
		//查看配送单
		queryShippin : function(data,callback){
			comm.requestFun("query_queryshipping_detail",data,callback,comm.lang("toolorder"));
		},
		//查询工具数量
		queryTollNum : function(data,callback){
			comm.requestFun("tooldispatching_query_tool_num",data,callback,comm.lang("toolorder"));
		},
		//添加配送单
		commitShippinData : function(data,callback){
			comm.requestFun("add_tooldispatching",data,callback,comm.lang("toolorder"));
		},
		//查询企业设备信息
		queryEntDeviceList	:	function(data,callback){
			comm.requestFun("queryEntDeviceList",data,callback,comm.lang("toolorder"));
		},
		//批量导入售后
		batchUpload			:	function(data,callback){
			comm.requestFun("batchUpload",data,callback,comm.lang("toolorder"));
		},
		//查询工具售后单
		queryAfterServiceByNo	:	function(data,callback){
			comm.requestFun("queryAfterServiceByNo",data,callback,comm.lang("toolorder"));
		},
		//添加售后单
		addAfterPaidOrder	:	function(data,callback){
			comm.requestFun("addAfterPaidOrder",data,callback,comm.lang("toolorder"));
		},
		//工具售后单审批
		apprAfterService	:	function(data,callback){
			comm.requestFun("apprAfterService",data,callback,comm.lang("toolorder"));
		},
		//拒绝受理
		workOrderDoor		:	function(data,callback){
			comm.requestFun("rejectOrder",data,callback,comm.lang("toolorder"));
		},
		//挂起
		workOrderSuspend	:	function(data,callback){
			comm.requestFun("suspendOrder",data,callback,comm.lang("toolorder"));
		},
		//查询售后配置单详情
		queryAfterConfigDetail	:	function(data,callback){
			comm.requestFun("queryAfterConfigDetail",data,callback,comm.lang("toolorder"));
		},
		reassociation		:	function(data,callback){
			comm.requestFun("reassociation",data,callback,comm.lang("toolorder"));
		},
		keepAssociation		:	function(data,callback){
			comm.requestFun("keepAssociation",data,callback,comm.lang("toolorder"));
		},
		findPrintDetailById	:	function(data,callback){
			comm.requestFun("findPrintDetailById",data,callback,comm.lang("toolorder"));
		}
	};
});