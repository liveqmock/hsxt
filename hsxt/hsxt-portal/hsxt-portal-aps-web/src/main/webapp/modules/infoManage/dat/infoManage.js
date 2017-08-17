define(function () {
	return {
		//查询消费者资源信息
		listConsumerInfo:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"resourcedirect_person_page",params,comm.lang("infoManage"),detail,del, add, edit);
		},
		//查询消费者所有信息接口
		queryConsumerAllInfo:function(data,callback){
			comm.requestFun("resourcedirect_queryConsumerAllInfo",data,callback,comm.lang("infoManage"));
		},
		//查询消费者所有信息接口
		queryConsumerAllInfo:function(data,callback){
			comm.requestFun("resourcedirect_queryConsumerAllInfo",data,callback,comm.lang("infoManage"));
		},
		//查询消费者绑定银行卡
		queryConsumerBanks:function(data,callback){
			comm.requestFun("resourcedirect_queryConsumerBanks",data,callback,comm.lang("infoManage"));
		},
		
		listConsumerBanks:function(data,callback){
			comm.requestFunForHandFail("resourcedirect_queryConsumerBanks",data,callback,comm.lang("infoManage"));
		},
		//查询消费者绑定快捷银行卡
		queryConsumerQkBanks:function(data,callback){
			comm.requestFun("resourcedirect_queryConsumerQkBanks",data,callback,comm.lang("infoManage"));
		},
		//查询资源名录详细信息
		seachToolProduct : function(data,callback){
			comm.requestFun("resourcedirect_detail",data,callback,comm.lang("infoManage"));
		},
		//文件上传路径
		getUploadFilePath : function(){
			return  comm.domainList['apsWeb']+comm.UrlList["upload"];
		},
		//成员企业资格注销审批 详情
		seachMemberQuitDetail : function(data,callback){
			comm.requestFun("membercompquit_detail",data,callback,comm.lang("infoManage"));
		},
		//资格注销审批提交
		commitMemberQuitAppr : function(data,callback){
			comm.requestFun("membercompquit_approval",data,callback,comm.lang("infoManage"));
		},
		//资格注销复核提交
		commitMemberQuitReview : function(data,callback){
			comm.requestFun("membercompquit_review",data,callback,comm.lang("infoManage"));
		},
		//查询积分活动详细信息
		serchPointActivityDetail : function(data,callback){
			comm.requestFun("activityapply_query_detail",data,callback,comm.lang("infoManage"));
		},
		//提交积分活动审批
		commitPointActivityAppr : function(data,callback){
			comm.requestFun("activityapply_apprval",data,callback,comm.lang("infoManage"));
		},
		//提交积分活动复核
		commitPointActivityReview : function(data,callback){
			comm.requestFun("activityapply_review",data,callback,comm.lang("infoManage"));
		},
		//个人（消费者）实名认证审批
		apprPerRealNameIdentific : function(data,callback){
			comm.requestFun("appr_perrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//个人（消费者）实名认证复核
		reviewPerRealNameIdentific : function(data,callback){
			comm.requestFun("review_perrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//修改个人（消费者）实名认证信息
		modifyPerRealNameIdentific : function(data,callback){
			comm.requestFun("modify_perrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//查看个人（消费者）实名认证状详细信息
		queryDetailPerRealNameIdentific : function(data,callback){
			comm.requestFun("query_perrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//企业实名认证审批
		apprEntRealNameIdentific : function(data,callback){
			comm.requestFun("appr_entrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//企业实名认证复核
		reviewEntRealNameIdentific : function(data,callback){
			comm.requestFun("review_entrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//修改企业实名认证信息
		modifyEntRealNameIdentific : function(data,callback){
			comm.requestFun("modify_entrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//企业实名认证状详细信息
		queryDetailEntRealNameIdentific : function(data,callback){
			comm.requestFun("query_entrealnameidentific",data,callback,comm.lang("infoManage"));
		},
		//审批消费者重要信息
		apprPerImportantInfo : function(data,callback){
			comm.requestFun("appr_perimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//复核消费者重要信息
		reviewPerImportantInfo : function(data,callback){
			comm.requestFun("review_perimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//查询消费者重要信息
		queryPerImportantInfo : function(data,callback){
			comm.requestFun("query_perimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//修改消费者重要信息
		modifyPerImportantInfo : function(data,callback){
			comm.requestFun("modify_perimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//审批企业重要信息
		apprEntimportantinfochange : function(data,callback){
			comm.requestFun("appr_entimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//复核企业重要信息
		reviewEntimportantinfochange : function(data,callback){
			comm.requestFun("review_entimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//修改企业重要信息
		modifyEntimportantinfochange : function(data,callback){
			comm.requestFun("modify_entimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//查询企业重要信息
		queryEntimportantinfochange : function(data,callback){
			comm.requestFun("query_entimportantinfochange",data,callback,comm.lang("infoManage"));
		},
		//托管企业 成员企业、服务公司详情
		resourceFindEntAllInfo : function(data, callback){
			comm.requestFun("resourceFindEntAllInfo", data, callback, comm.lang("infoManage"));
		},
		//账户资源管理-企业资源
		findResEntInfoList:function(params, detail){
			return comm.getCommBsGrid(null, "findResEntInfoList", params, comm.lang("infoManage"), detail);
		},
		//账户资源管理-消费者资源
		findResConsumerInfoList:function(params, detail){
			return comm.getCommBsGrid(null, "findResConsumerInfoList", params, comm.lang("infoManage"), detail);
		},
		//业务许可信息查询
		findBusinessPmInfo : function(data, callback){
			comm.requestFun("findBusinessPmInfo", data, callback, comm.lang("infoManage"));
		},
		//业务许可信息设置
		setBusinessPmInfo : function(data, callback){
			comm.requestFun("setBusinessPmInfo", data, callback, comm.lang("infoManage"));
		},
		//消费者重要信息变更审批查询
		queryPerImportSpInfo : function(data, detail){
			return comm.getCommBsGrid("searchTable", "queryPerImportSpInfo", data, comm.lang("infoManage"), detail);
		},
		//企业重要信息变更审批查询
		queryEntImportSpInfo : function(data, detail){
			return comm.getCommBsGrid("searchTable", "queryEntImportSpInfo", data, comm.lang("infoManage"), detail);
		},
		//修改企业的一般信息,并保持记录
		updateEntInfo : function(data, callback){
			comm.requestFun("updateRelEntBaseInfo", data, callback, comm.lang("infoManage"));
		},
		//获得文件在文件系统服务器中的url
		getFsServerUrl : function(fileId) {
			var custId = comm.getCookie('custId'); 	// 读取 cookie 客户号
			var token = comm.getCookie('token');
			
			return  comm.domainList['fsServerUrl']+fileId+"?userId="+custId+"&token="+token;
		},
		//查看企业的修改记录信息
		queryEntInfoBak : function(data, detail){
			return comm.getCommBsGrid("xgjl_table", "queryEntInfoBak", data, comm.lang("infoManage"), detail);
		},
		/**
		 * 发送验证邮箱的邮件
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		validEmail : function(params, callback){
			comm.requestFun("validEmail", params, callback, comm.lang("infoManage"));
		}
	};
});