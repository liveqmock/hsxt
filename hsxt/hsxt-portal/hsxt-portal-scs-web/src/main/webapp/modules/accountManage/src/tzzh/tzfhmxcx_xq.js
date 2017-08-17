define(['text!accountManageTpl/tzzh/tzfhmxcx_xq.html',
        "accountManageDat/tzzh/tzzh",
		"accountManageLan"],function( tzfhmxcx_xqTpl,tzzh ){
	return tzfhmxcxXq={	 	
		showPage : function(record){
			tzzh.inverstmentTotal({
				hsResNoParam : record.hsResNo,
				dividendYear : record.dividendYear
			},function(res){
				$('#busibox').html(_.template(tzfhmxcx_xqTpl,res.data));
				var params = {
						search_hsResNo : record.hsResNo,
						search_year : record.dividendYear
				};
				gridObj = comm.getCommBsGrid("tableDetail","dividendDetailPage",params,comm.lang("accountManage"),tzfhmxcxXq.detail);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.formatDate(record.investDate,"yyyy-MM-dd");
			}
			
			if(colIndex == 1){
				return comm.formatMoneyNumber(record.investAmount);
			}
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.normalDividend);
			}
			if(colIndex == 4){
				return comm.formatMoneyNumber(record.directionalDividend);
			}
			if(colIndex == 5){
				return comm.formatMoneyNumber(record.totalDividend);
			}
		}
	}
}); 
