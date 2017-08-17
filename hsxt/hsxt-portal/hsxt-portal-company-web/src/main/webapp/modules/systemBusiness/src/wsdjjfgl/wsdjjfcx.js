define(['text!systemBusinessTpl/wsdjjfgl/wsdjjfcx.html',
        'systemBusinessDat/systemBusiness'], function(tpl,systemBusiness){
	var wsdjjfcx = {
		showPage : function(){
			$('#busibox').html(tpl);
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
			
			//时间控件
			comm.initBeginEndTime("#search_beginDate",'#search_endDate');
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if(!comm.queryDateVaild('wsdjjfcx_form').form()){
					return;
				}
				wsdjjfcx.loadGrid();
			});
		},	
		//加载表格
		loadGrid:function(){
			var params={
					search_startDate : $("#search_beginDate").val(),
					search_endDate : $("#search_endDate").val(),
					search_perResNo : $("#perResNo").val()
			};
			 comm.getCommBsGrid("wsdjjfcx_table","pointRegisterList",params,wsdjjfcx.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				if(record.orderAmount==null ||record.orderAmount==0){
					return comm.formatMoneyNumber(record.transAmount);
				}
				return comm.formatMoneyNumber(record.orderAmount);
			}else if(colIndex == 3){
					return comm.formatMoneyNumber(record.transAmount);
			}else if(colIndex == 4){
				return comm.formatTransRate(record.pointRate);
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.entPoint);
			}else if(colIndex == 6){
				return comm.formatDate(record.sourceTransDate,'yyyy-MM-dd');
		    }
		}
	};
	
	return wsdjjfcx;
});