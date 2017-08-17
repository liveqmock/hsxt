define(['text!accountManageTpl/tzzh/zhye.html'],function(tpl){
	return {
		showPage : function(){
			this.bindData();
		},
		bindData : function(){
			var self = this;
			var response = {
				investYear: 2014,
				investRate: '100%',
				investTotal: comm.formatMoneyNumber(8200.00),
				investBonus: comm.formatMoneyNumber(8000.00),
				investCurrency: comm.formatMoneyNumber(5000.00),
				investFund: comm.formatMoneyNumber(3000.00)
			};
			$('#busibox').html(_.template(tpl, response));
			
			//刷新按钮单击事件
			$('#tzPointFlush').click(function(){
				self.bindData();
			});
		}
	}
});