define(['text!accountManageTpl/hbzh/hbzhye.html',
'accountManageDat/hbzh/hbzh','accountManageLan' ],function(jfzhyeTpl ,dataModule){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(jfzhyeTpl)) ;
			 
			//Todo...
		 
			// 刷新点击事件
			$("#cashFlush").click(function () {
				//查询货币余额
				dataModule.findCurrencyBlance(null, function (response) {
					$("#cashAcctTotal").text(comm.formatMoneyNumber(response.data.currencyBlance));
				});
			});
			// 触发刷新事件，查询币账户余额
			$("#cashFlush").trigger('click');
				
		}
	}
}); 

 