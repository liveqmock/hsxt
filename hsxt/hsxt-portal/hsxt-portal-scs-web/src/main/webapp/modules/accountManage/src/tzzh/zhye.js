define(['text!accountManageTpl/tzzh/zhye.html',
        "accountManageDat/tzzh/tzzh",
		"accountManageLan"],function( zhyeTpl,tzzh ){
	return {	 	
		showPage : function(){
			
			//获取登录信息
			var param = comm.getRequestParams();
			
			tzzh.inverstmentTotal({
				hsResNoParam : param.pointNo,
				dividendYear:new Date().getFullYear()-1
			},function(res){
				res.data.investmentTotal = comm.formatMoneyNumber(res.data.investmentTotal);
				$('#busibox').html(_.template(zhyeTpl,res.data)) ;
				
				$("#refreshBtn").click(function(){
					$('#tzzh_zhye').click();
				});
			});
			
			
		}
	}
}); 
