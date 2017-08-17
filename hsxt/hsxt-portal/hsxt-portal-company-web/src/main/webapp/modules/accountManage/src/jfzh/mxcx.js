define(['text!accountManageTpl/jfzh/mxcx/mxcx.html',
		'accountManageDat/accountManage',
		], function(tpl,accountManage){
	return  jfzhMxcx = {
		showPage : function(){
			$('#busibox').html(tpl);
			comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null);
		 	comm.initSelect($("#scpoint_subject"),comm.lang("accountManage").businessEnum,null,0,{name:'全部',value:'0'});
			
			/*日期控件*/
			comm.initBeginEndTime("#search_beginDate","#search_endDate");
			
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_beginDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if(!comm.queryDateVaild('point_form').form()){
					return;
				}
				jfzhMxcx.loadGrid();
			});
		},
		loadGrid:function(){
			var params = {
				search_accType:'10110',
				search_beginDate : comm.navNull($("#search_beginDate").val()),
				search_endDate : comm.navNull($("#search_endDate").val()),
				search_businessType : comm.navNull($("#scpoint_subject").attr('optionValue'),'0'),
			};
			accountManage.detailedPage("scpoint_result_table",params,jfzhMxcx.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			var transTypeName=jfzhMxcx.getTransType(record.transSys,record.transType);
			//渲染列:交易时间
			if(colIndex == 0){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}
			if(colIndex == 1){
				return transTypeName;
			}else if(colIndex == 2){
				return comm.lang("accountManage").businessTypeEnum[record.businessType];
			}else if(colIndex == 3){
				return comm.formatMoneyNumber(record.amount);
			}else if(colIndex == 4){
				if(record.accBalanceNew==null){
					return "--";
				}else{
					return comm.formatMoneyNumber(record.accBalanceNew);	
				}
			}
		},
		getTransType : function(transSys,transType){
				var transTypeName="";
				if(transSys=='PS'){
					var tr=transType.charAt(3);
					 if(tr=='1' ||tr=='2'){
						transTypeName="消费积分撤单";
					}else{
						transTypeName= comm.lang("accountManage").transTypeEnum[transType];
					}
				}else{
					transTypeName= comm.lang("accountManage").transTypeEnum[transType];
				}
				return transTypeName;
		}
	};
});