define(['text!accountPersonTpl/tzzh/zftzmxcx.html',
        'accountPersonDat/accountPerson',
		'accountPersonLan'], function(tpl,accountPerson){
	return cardPersonJftzmxcx={
		showPage : function(){
			$('#busibox').html(tpl);
			
			comm.initSelect($("#quickDate"),comm.lang("accountPerson").quickDateEnum,null,null);
			
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
				cardPersonJftzmxcx.loadGrid();
			});
		},
		loadGrid:function(){
			var params = {
				search_accType:'10410',
				search_beginDate : comm.removeNull($("#search_startDate").val()),
				search_endDate : comm.removeNull($("#search_endDate").val()),
				search_businessType : comm.removeNull($("#scpoint_subject").attr('optionValue')),
				search_hsResNo : comm.removeNull($("#hsResNo").val()),
				search_enterpriseName : comm.removeNull($("#enterpriseName").val())
			};
			 accountPerson.getAccoutGrid("scpoint_result_table",params,cardPersonJftzmxcx.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			//渲染列:交易时间
			if(colIndex == 2){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.amount);
			}else if(colIndex == 4){
				return comm.formatMoneyNumber(record.accBalanceNew);
			}
		}
		
	};
});