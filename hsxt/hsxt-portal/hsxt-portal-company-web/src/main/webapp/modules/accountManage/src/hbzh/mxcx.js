define(['text!accountManageTpl/hbzh/mxcx/mxcx.html',
		'accountManageDat/hbzh/hbzh',
		'accountManageDat/accountManage',
		'accountManageLan'
		], function(tpl,hbzh,accountManage){
	return mxcx = {
		showPage : function(){
			$('#busibox').html(tpl);
			 
			/*下拉列表*/
			 comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null);
			$("#quickDate").change(function(e){
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_beginDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			}); 
				
			comm.initSelect($("#scpoint_subject"),comm.lang("accountManage").businessEnum,null,0,{name:'全部',value:'0'});

			//时间控件
			comm.initBeginEndTime("#search_beginDate",'#search_endDate');
			
			//查询
			$("#scpoint_searchBtn").click(function(){
				if(!comm.queryDateVaild('cash_form').form()){
					return;
				}
				mxcx.pageQuery();
			});	
		},
		pageQuery:function(){
					var params = {
							"search_businessType":comm.navNull($("#scpoint_subject").attr('optionValue'),'0'),
							"search_accType":"30110",
							"search_beginDate":comm.navNull($("#search_beginDate").val()),
							"search_endDate":comm.navNull($("#search_endDate").val()),
							};
					accountManage.detailedPage("scpoint_result_table",params,function(record, rowIndex, colIndex, options){
						//渲染列:交易时间
						if(colIndex == 0){
							return comm.formatDate(record.createdDate,'yyyy-MM-dd');
						}
						//渲染列:交易类型
						if(colIndex == 1){
							return comm.lang("accountManage").transTypeEnum[record["transType"]];
						}
						//渲染列:业务类型
						if(colIndex == 2){
							return comm.lang("accountManage").businessTypeEnum[record["businessType"]];
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
		}
	}
});
