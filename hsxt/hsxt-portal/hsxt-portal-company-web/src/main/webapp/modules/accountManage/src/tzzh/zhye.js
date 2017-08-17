define(['text!accountManageTpl/tzzh/zhye.html',
        'accountManageDat/accountManage'],function(tpl,accountManage){
	return tzzh_zhye = {
		showPage : function(){
			this.bindData();
		},
		bindData : function(){
			
			accountManage.query_investment_blance({'resNo':$.cookie('pointNo')},function(res){
				var response = {
					investYear: res.data.dividendYear,
					investRate: (res.data.dividendsRate*100).toFixed(2),
					investTotal: comm.formatMoneyNumber(res.data.investmentTotal),
					investBonus: comm.formatMoneyNumber(res.data.dividendsHsb),
					investCurrency: comm.formatMoneyNumber(res.data.circulationHsb),
					investFund: comm.formatMoneyNumber(res.data.directionalHsb)
				};
				$('#busibox').html(_.template(tpl, response));
				//刷新按钮单击事件
				$('#tzPointFlush').click(function(){
					tzzh_zhye.bindData();
				});
			});
		}
	}
});