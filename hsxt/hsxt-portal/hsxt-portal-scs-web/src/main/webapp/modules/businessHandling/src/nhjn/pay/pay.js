define(['text!businessHandlingTpl/nhjn/pay/pay.html',
		'businessHandlingSrc/nhjn/pay/hsbPay',
		'businessHandlingSrc/nhjn/pay/eBankPay',
		'businessHandlingSrc/nhjn/pay/quickPay',
		'businessHandlingSrc/nhjn/pay/bankCardPay'
		], function(payTpl, hsbPay, eBankPay, quickPay, bankCardPay){
	return {
		showPage : function(obj){
			$('#pay_div').html(_.template(payTpl, obj));
			var str = obj.paymentMethod || 'hsbPay';
			switch(str){
				case 'hsbPay' : 
					hsbPay.showPage(obj);
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