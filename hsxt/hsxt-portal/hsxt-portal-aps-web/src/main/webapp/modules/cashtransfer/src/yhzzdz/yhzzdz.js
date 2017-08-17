define(['text!cashtransferTpl/yhzzdz/yhzzdz.html',
		'cashtransferDat/cashTransfer',
		'cashtransferSrc/yhzzdz/yhdz',
		'coDeclarationLan'], function(yhzzdzTpl, dataModoule, yhdz){
	var yhzzdzFun = {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(yhzzdzTpl));	
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
		    comm.initSelect('#quickDate', comm.lang("coDeclaration").quickDateEnum);	
		    
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			/*表格请求数据*/
			$("#qry_yhzzdz_btn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						search_startDate	: $("#search_startDate").val().trim(),	//开始时间
						search_endDate		: $("#search_endDate").val().trim(),	//结束时间
						search_hsResNo		: $("#hsResNo").val().trim(),	//互生号/手机号
						search_entName		: $("#entName").val().trim()	//单位名称
						
				};
				
				yhzzdzFun.searchTable = comm.getCommBsGrid("searchTable","find_checkUp_list",params,comm.lang("cashTransfer"),yhzzdzFun.detail);
				
			});
			/*end*/
			
			/*按钮事件*/
			$('#dz_btn').click(function(){
				var obj = yhzzdzFun.searchTable.getCheckedRowsRecords();
				if(obj.length<1){
					return;
				}
				yhdz.showPage(obj);
			});
			
			/*end*/
		},
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 6){
				if(record.amount){
					return comm.fmtMoney(record.amount);
				}else{
					return "";
				}
			}
			if(colIndex == 7){
				if(record.feeAmt){
					return comm.fmtMoney(record.feeAmt);
				}else{
					return "";
				}
			}
			if(colIndex == 8){
				if(record.realAmt){
					return comm.fmtMoney(record.realAmt);
				}else{
					return "";
				}
			}
			if(colIndex == 11){
				return comm.lang("cashTransfer").commitType[record.commitType];
			}
		}
	}	
	return yhzzdzFun;
});