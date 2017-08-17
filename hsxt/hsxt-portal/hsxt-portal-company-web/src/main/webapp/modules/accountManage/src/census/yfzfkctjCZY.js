define(['text!accountManageTpl/census/yfzfkctjCZY.html',
        'text!accountManageTpl/census/yfzfkctjCZY_detail.html',
        'accountManageDat/hsbzh/hsbzh'], function(tpl,detailTpl,dataMoudle){
	return yfzfkctjCZY = {
	
		showPage : function(){
			$('#busibox').html(tpl);
			
			/*日期控件*/
			comm.initBeginEndTime("#search_beginDate","#search_endDate");

			//查询单击事件
			$('#scpoint_dsCZY_searchBtn').click(function(){
				if(!comm.queryDateVaild('scpoint_dsCZY_form').form()){
					return;
				}
				var params={
						search_beginDate : $("#search_beginDate").val(),
						search_endDate : $("#search_endDate").val(),
						search_operCustName:$("#scpoint_dsCZY_code").val()
				};
				dataMoudle.reportsPointDeductionByOperSum(params,function(res){
					var obj=res.data;
					if(obj==null){
					    obj={
					    		ndetPoint:0,	
					    		cdetPoint:0
					    }
					}
					obj.sumPoint=(parseFloat(obj.ndetPoint)-parseFloat(obj.cdetPoint)).toFixed(2);
					$('#detailTpl').html(_.template(detailTpl,obj));
					dataMoudle.reportsPointDeductionByOper("scpoint_dsCZY_result_table",params,yfzfkctjCZY.detail);	
				});	
			});
		},
		detail: function(record, rowIndex, colIndex, options){
			if(colIndex==3){
				return comm.formatMoneyNumber(record.ndetPoint);
			}
			if(colIndex==4){
				return comm.formatMoneyNumber(record.cdetPoint);
			}
			if(colIndex==5){
				return comm.formatMoneyNumber(parseFloat(record.ndetPoint)-parseFloat(record.cdetPoint));
			}
		}
	};
});