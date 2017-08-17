define(function () {
	return {
		/**
		 * 账户名称信息
		 */
		saveAccountInfo : function(params, callback){
			comm.requestFun("createAccount", params, callback, comm.lang("debit"));
		},
		queryAccountInfo : function(params,callback){
			comm.requestFun("getAccountById", params, callback, comm.lang("debit"));
		},
		updateAccountInfo : function(params,callback){
			comm.requestFun("updateAccout", params, callback, comm.lang("debit"));
		},
		listAccountMemu : function(params,callback){
			comm.requestFun("listAccountMemu", params, callback, comm.lang("debit"));
		},
		listAccount : function(gridId,params,callback){
			 comm.getCommBsGrid("searchTable","listAccount", params,comm.lang("debit"), callback);
		},
		/**
		 * 账户信息
		 */
		saveAccountingInfo : function(params, callback){
			comm.requestFun("createAccounting", params, callback, comm.lang("debit"));
		},
		queryAccountingInfo : function(params,callback){
			comm.requestFun("getAccountingById", params, callback, comm.lang("debit"));
		},
		updateAccountingInfo : function(params,callback){
			comm.requestFun("updateAccouting", params, callback, comm.lang("debit"));
		},
		listAccountingMemu : function(params,callback){
			comm.requestFun("listAccountingMemu", params, callback, comm.lang("debit"));
		},
		listAccounting : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"listAccounting", params,comm.lang("debit"), callback);
		},
		forbidAccountInfo : function(params,callback){
			comm.requestFun("forbidAccountInfo", params, callback, comm.lang("debit"));
		},
		/**
		 * 临账信息 临账录入，查看，修改，复核。列表
		 */
        createDebit : function(params, callback){
			comm.requestFun("createDebit", params, callback, comm.lang("debit"));
		},
		getDebitById : function(params,callback){
			comm.requestFun("getDebitById", params, callback, comm.lang("debit"));
		},
		updateDebit : function(params,callback){
			comm.requestFun("updateDebit", params, callback, comm.lang("debit"));
		},
		approveDebit : function(params,callback){
			comm.requestFun("approveDebit", params, callback, comm.lang("debit"));
		},
		listDebit : function(gridId,params,callback,callback1,callback2){
			return comm.getCommBsGrid(gridId,"listDebit", params,comm.lang("debit"), callback,callback1,callback2);
		},
		queryDebitTaskListPage : function(gridId,params,callback,callback1,callback2){
			return comm.getCommBsGrid(gridId,"queryDebitTaskListPage", params,comm.lang("debit"), callback,callback1,callback2);
		},
		queryDebitListByQuery : function(gridId,params,callback){
			return comm.getCommBsGrid(gridId,"queryDebitListByQuery", params,comm.lang("debit"),callback);
		},
		/**
		 * 临账退款：申请 临账退款查看，复核。列表
		 */
        createRefundDebit : function(params, callback){
			comm.requestFun("createRefundDebit", params, callback, comm.lang("debit"));
		},
		getrefundDebitById : function(params,callback){
			comm.requestFun("getrefundDebitById", params, callback, comm.lang("debit"));
		},
		approveRefundDebit : function(params,callback){
			comm.requestFun("approveRefundDebit", params, callback, comm.lang("debit"));
		},
		listRefundDebit : function(gridId,params,callback,callback1){
			 comm.getCommBsGrid(gridId,"listRefundDebit", params,comm.lang("debit"), callback,callback1);
		},
		
		/**
		 * 临账关联:查询未付款订单，申请关联,复核临账关联，根据订单编号查询对应的临账关联申请，关联未复核订单列表
		 */
		listNopayOrder : function(gridId,params,callback,callback1){
			 comm.getCommBsGrid(gridId,"listNopayOrder", params,comm.lang("debit"), callback,callback1);
		},
		createDebitLink : function(params,callback){
			comm.requestFun("createDebitLink", params, callback, comm.lang("debit"));
		},
		approveDebitLink : function(params,callback){
			comm.requestFun("approveDebitLink", params, callback, comm.lang("debit"));
		},
		findDebitLinkByOrderNo : function(gridId,params,callback){
			return comm.getCommBsGrid(gridId,"findDebitLinkByOrderNo", params,comm.lang("debit"), callback);
		},
		noApproveList : function(gridId,params,callback,callback1,callback2){
			return comm.getCommBsGrid(gridId,"noApproveList", params,comm.lang("debit"), callback,callback1,callback2);
		},
		queryTempOrderByDebitId : function(gridId,params,callback){
			comm.getCommBsGrid(gridId,"queryTempOrderByDebitId", params,comm.lang("debit"), callback);
		},
		invoiceGetOrderByOrderId : function(params,callback){
			comm.requestFun("invoiceGetOrderByOrderId", params, callback, comm.lang("debit"));
		},
		queryTempAcctLinkListByDebitId:function(params,callback){
			comm.requestFun("queryTempAcctLinkListByDebitId", params, callback, comm.lang("debit"));
		},
		/*
		 * 不动款
		 */
		turnNotMove : function(params,callback){
			comm.requestFun("turnNotMove", params, callback, comm.lang("debit"));
		},
		notMoveSum : function(params,callback){
			comm.requestFun("notMoveSum", params, callback, comm.lang("debit"));
		},
		/**
		 * 临账统计 查看
		 */ 		
		listSumDebit : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"listSumDebit", params,comm.lang("debit"),callback);
		},		
		sumDetail : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"getSumDetail", params,comm.lang("debit"),callback);
		},
		exporTempDebitSum : function(receiveAccountId, startDate, endDate){
			var userInfo = comm.getRequestParams();	//用户信息
			return comm.domainList['apsWeb']+comm.UrlList["exporTempDebitSum"] + 
					"?receiveAccountName=" + receiveAccountId+
					"&startDate="+startDate+
					"&endDate="+endDate+
					"&token="+userInfo.token+
					"&custId="+userInfo.custId+
					"&channelType="+userInfo.channelType;
		},
		//查询单个临账关联信息(待关联、已关联、审批信息)
		queryDebitLinkInfo:function(params,callback){
			comm.requestFun("queryDebitLinkInfo", params, callback, comm.lang("debit"));
		}
	};
});