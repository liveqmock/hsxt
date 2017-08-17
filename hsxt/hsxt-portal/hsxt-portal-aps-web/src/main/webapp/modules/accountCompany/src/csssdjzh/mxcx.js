define(['text!accountCompanyTpl/csssdjzh/mxcx/mxcx.html',
		'text!accountCompanyTpl/csssdjzh/mxcx/xq.html',
		"accountCompanyDat/accountCompany",
		"accountCompanyLan"
		], function(tpl, xqTpl,accountCompany){
	return csssdjzhMxcx={
		showPage : function(){
			$('#busibox').html(tpl);
			//载入下拉参数  
		 	comm.initSelect($("#quickDate"),comm.lang("accountCompany").quickDateEnum,null,null);
		 	comm.initSelect($("#scpoint_subject"),comm.lang("accountCompany").taxRateTypeEnum,null,null);
		 	
		 	//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			   
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
		   //查询
		   $("#scpoint_searchBtn").click(function(){
			   if(!comm.queryDateVaild("scpoint_form").form()){
					return;
				}
			   csssdjzhMxcx.pageQuery();
		   });
		},
		showDetail : function(obj){
			$('#csssdjzh-dialog > p').html(_.template(xqTpl, obj));
			$('#csssdjzh-dialog').dialog({
				title: obj.type + '详情',
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				width: 'auto'
			});
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {
					"search_accType":comm.removeNull($("#scpoint_subject").attr("optionvalue")),
					"search_isTaxAccount":true,
					"search_beginDate":comm.removeNull($("#search_startDate").val()),
					"search_endDate":comm.removeNull($("#search_endDate").val())
					};
			accountCompany.getAccoutGrid("scpoint_result_table",param,function(record, rowIndex, colIndex, options){
				//渲染列:交易时间
				if(colIndex == 2){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				//渲染列:交易类型
				if(colIndex == 3){
					return comm.lang("accountCompany").transTypeEnum[record["transType"]]+'纳税';
				}
				//渲染列:业务类型
				if(colIndex == 4){
					return comm.lang("accountCompany").taxRateTypeEnum[record["accType"]];
				} 
				//渲染列：发生金额
				if(colIndex==5){
					return comm.formatMoneyNumber(record["amount"]);	
				}
			});
		}
	};
});