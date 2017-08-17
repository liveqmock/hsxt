define(["text!investAccountTpl/balanceInquiry.html"], function (tpl) {
	return {
		show : function(dataModule){
			//加载余额查询
			$("#myhs_zhgl_box").html(tpl);
			//刷新点击事件
			$("#investFlush").click(function () {
				
				//查询投资账户余额
				dataModule.findInvestmentBlance(null,function (response) {
					
					//加载数据
					var rate =( Math.round( response.data.dividendsRate * Math.pow( 10, 4 + 2 ) ) / Math.pow( 10, 4 ) ).toFixed(2) + '%';
					
					$("#dividendsRate").text(rate); 								//投资分红回报率
					$("#dividendYear,#dividendYear2").text(response.data.dividendYear); //年份
					$("#investIntegralTotal").text(comm.formatMoneyNumber(response.data.investmentTotal)); 	//累计积分投资总数
					$("#dividentsTotal").text(comm.formatMoneyNumber(response.data.dividendsHsb)); 			//年度投资分红互生币
					$("#ccyHsbTotal").text(comm.formatMoneyNumber(response.data.circulationHsb)); 			//流通币
					$("#dcHsbTotal").text(comm.formatMoneyNumber(response.data.directionalHsb)); 			//定向消费币
							
				});
			});
			//触发刷新事件，查询积分账户余额
			$("#investFlush").trigger('click');
		},
		//
		toPercent : function (num,n){
			n = n || 2;
			
			return ( Math.round( num * Math.pow( 10, n + 2 ) ) / Math.pow( 10, n ) ).toFixed(n) + '%';
			
		
		}
	
	};
});
