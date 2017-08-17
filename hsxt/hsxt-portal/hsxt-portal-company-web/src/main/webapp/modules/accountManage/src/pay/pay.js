define(['text!accountManageTpl/pay/pay.html',
		'accountManageSrc/pay/hbPay',
		'accountManageSrc/pay/eBankPay',
		'accountManageSrc/pay/quickPay',
		'accountManageSrc/pay/bankCardPay'
		], function(payTpl, hbPay, eBankPay, quickPay, bankCardPay){
	return {
		showPage : function(obj){
			$('#pay_div').html(_.template(payTpl, obj));
			var str = obj.paymentMethod || 'eBankPay';
			switch(str){
				case 'hbPay' : 
					hbPay.showPage(obj);
					break;	
				case 'eBankPay' :
					eBankPay.showPage(obj);
					break;
				case 'yhkzf' :
					bankCardPay.showPage(obj);
					break;
			};
			
			$('#busibox').scroll(function(){
				$('input').tooltip().tooltip('destroy');	
			});
		}
	}	
});