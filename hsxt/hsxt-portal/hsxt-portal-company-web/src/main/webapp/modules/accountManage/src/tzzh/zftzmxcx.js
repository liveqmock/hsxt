define(['text!accountManageTpl/tzzh/zftzmxcx.html',
        'accountManageDat/accountManage'], function(tpl,accountManage){
	return zftzmxcx = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			
			searchTable = null;
			
			/*下拉列表*/
			$("#quickDate").selectList({
				options:[
					{name:'今天',value:'today'},
					{name:'最近一周',value:'week'},
					{name:'最近一月',value:'month'}
				]
			}).change(function(e){
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_beginDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			/*日期控件*/
			comm.initBeginEndTime("#search_beginDate","#search_endDate");
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if(!comm.queryDateVaild('point_tz_form').form()){
					return;
				}
				var queryParam={
						'custID' : $.cookie('custId') , //$.cookie('custId');
						"startDate":$("#search_beginDate").val(),	
						"endDate":$("#search_endDate").val(),	
						};
		
				searchTable = accountManage.getPointInvestList("scpoint_result_table",queryParam,zftzmxcx.detail);
				
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.formatDate(record.investDate,"yyyy-MM-dd");
			}
			if(colIndex == 2){
				return comm.formatMoneyNumber(record.investAmount);
			}
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.accumulativeInvestCount);
			}
		}
	};
});