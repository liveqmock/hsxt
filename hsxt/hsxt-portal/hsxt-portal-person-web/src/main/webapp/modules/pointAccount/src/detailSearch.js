define(["text!pointAccountTpl/detailSearch.html",
        "pointAccountDat/pointAccount"], function (tpl,dataModule) {
	var detailSearch =  {
		show : function(dataModule){
			//加载余额查询
			$("#myhs_zhgl_box").html(tpl);
			// 加载开始、结束日期
			$("#search_startDate, #search_endDate").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});

			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).val()];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			//加载业务类别
			comm.initSelectOption($("#spoint_subject"),comm.lang("pointAccount").businessTypeEnum);
			//立即搜索单击事件
			$("#spoint_searchBtn").click(function () {
				var valid = comm.queryDateVaild("search_inverst_form");
				if (!valid.form()) {
					return;
				}
				detailSearch.pageQuery();
			});
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var jsonData = {
					"search_businessType":comm.navNull($("#spoint_subject").val()),
					"search_accType":"10110",
					"search_beginDate":comm.navNull($("#search_startDate").val()),
					"search_endDate":comm.navNull($("#search_endDate").val()),
					};
			dataModule.detailedPage(jsonData,function(record, rowIndex, colIndex, options){
				//渲染列:交易时间
				if(colIndex == 0){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				//渲染列:交易类型
				if(colIndex == 1){
					return detailSearch.getTransType(record.transSys,record.transType);
				}
				//渲染列:业务类型
				if(colIndex == 2){
					return comm.lang("pointAccount").businessTypeEnum[record["businessType"]];
				} 
				//渲染列：发生金额
				if(colIndex==3){
					return comm.formatMoneyNumber(record["amount"]);	
				}
				//渲染列：交易后金额  
				if(colIndex==4){
					if(record.accBalanceNew==null){
						return "--";
					}else{
						return comm.formatMoneyNumber(record.accBalanceNew);	
					}
				}
			});
		},
		getTransType : function(transSys,transType){
			var transTypeName="";
			if(transSys=='PS'){
				var tr=transType.charAt(3);
				if(tr=='0'||tr=='4'||tr=='3' ||tr=='8'||tr=='5'){
					transTypeName="消费积分";
				}else if(tr=='1' ||tr=='2'||tr=='6' ){
					transTypeName="消费积分撤单";
				}
			}else{
				transTypeName= comm.lang("pointAccount").transTypeEnum[transType];
			}
			return transTypeName;
	   },
  };
  return detailSearch
});
