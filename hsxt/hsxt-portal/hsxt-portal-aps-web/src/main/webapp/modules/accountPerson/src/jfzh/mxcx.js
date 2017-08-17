define(['text!accountPersonTpl/jfzh/mxcx/mxcx.html',
		'accountPersonDat/accountPerson'
		], function(tpl,accountPerson){
	return cardPersonJfzhMxcx={
		showPage : function(){
			$('#busibox').html(tpl);
			comm.initSelect($("#quickDate"),comm.lang("accountPerson").quickDateEnum,null,null);
		 	comm.initSelect($("#scpoint_subject"),comm.lang("accountPerson").businessEnum,null,null);
			
		 	//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if(!comm.queryDateVaild("scpoint_form").form()){
					return;
				}
				cardPersonJfzhMxcx.loadGrid();
			});
		},
		loadGrid:function(){
			var params = {
				search_accType:'10110',
				search_beginDate : comm.removeNull($("#search_startDate").val()),
				search_endDate : comm.removeNull($("#search_endDate").val()),
				search_businessType : comm.removeNull($("#scpoint_subject").attr('optionValue')),
				search_hsResNo : comm.removeNull($("#hsResNo").val()),
				search_enterpriseName : comm.removeNull($("#enterpriseName").val()),
			};
			 accountPerson.getAccoutGrid("scpoint_result_table",params,cardPersonJfzhMxcx.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			var transTypeName=cardPersonJfzhMxcx.getTransType(record.transSys,record.transType);
			//渲染列:交易时间
			if(colIndex == 2){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}
			if(colIndex == 3){
				return transTypeName;
			}else if(colIndex == 4){
				return comm.lang("accountPerson").businessEnum[record.businessType];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.sourceTransAmount);
			}else if(colIndex == 7){
				return comm.formatMoneyNumber(record.amount);
			}else if(colIndex == 8){
				return comm.formatMoneyNumber(record.accBalanceNew);	
			}
		},
		getTransType : function(transSys,transType){
			var transTypeName="";
			if(transSys=='PS'){
				var tr=transType.charAt(3);
				if(tr=='0'||tr=='4'||tr=='8'){
					transTypeName="消费积分";
				}else if(tr=='1' ||tr=='2'){
					transTypeName="消费积分撤单";
				}
			}else{
				transTypeName= comm.lang("accountPerson").transTypeEnum[transType];
			}
			return transTypeName;
	   }
	};
});