define(['text!accountManageTpl/hsbzh/census/yfzfkctj.html',
        'text!accountManageTpl/hsbzh/census/yfzfkctj_detail.html',
        'accountManageDat/hsbzh/hsbzh'], function(tpl,detailTpl,dataMoudle){
	return yfzfkctj = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			/*日期控件*/
			comm.initBeginEndTime("#search_beginDate","#search_endDate");
			
			//查询单击事件
			$('#scpoint_dj_searchBtn').click(function(){
				if(!comm.queryDateVaild('scpoint_dj_form').form()){
					return;
				}
				var params={
						search_beginDate : $("#search_beginDate").val(),
						search_endDate : $("#search_endDate").val()
				};
				dataMoudle.reportsPointDeductionSum(params,function(res){
					var obj=res.data;
					if(obj==null){
					    obj={
					    		ndetPoint:0,	
					    		cdetPoint:0
					    }
					}
						obj.sumPoint=(parseFloat(obj.ndetPoint)-parseFloat(obj.cdetPoint)).toFixed(2);
						$('#detailTpl').html(_.template(detailTpl,obj));
						dataMoudle.reportsPointDeduction("scpoint_dj_result_table",params,yfzfkctj.detail);	
					
				});	
			});
		},
		detail: function(record, rowIndex, colIndex, options){
			if(colIndex==1){
				return comm.formatMoneyNumber(record.ndetPoint);
			}
			if(colIndex==2){
				return comm.formatMoneyNumber(record.cdetPoint);
			}
			if(colIndex==3){
				return comm.formatMoneyNumber(parseFloat(record.ndetPoint)-parseFloat(record.cdetPoint));
			}
		}
	};
});