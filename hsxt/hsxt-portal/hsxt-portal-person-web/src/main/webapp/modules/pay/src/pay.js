define(['text!payTpl/pay.html',
		'paySrc/hsbPay',
		'paySrc/hbzhPay',
		'paySrc/unionPay',
		'paySrc/quickPay',
		'paySrc/bankCardPay'
		], function(payTpl, hsbPay, hbzhPay, unionPay, quickPay, bankCardPay){
	return {
		showPage : function(obj){
			$('#pay_div').html(_.template(payTpl, obj));
			
			$("#orderNumber").text(obj.orderNumber);
			$("#buyHsbNum").text(comm.formatMoneyNumber(obj.buyHsbNum));
			$("#paymentAmount").text(obj.paymentAmount);
			$("#paymentHbAmount").text(obj.paymentAmount);
			$("#currency").text(obj.currency);
			$("#currencyName").text(obj.currencyName);
			$("#rate").text(obj.hbRate);
			var str = obj.paymentMethod || 'hsbzf';
			switch(str){
				case 'hsbzf' : 
					hsbPay.showPage(obj);
					break;	
				case 'hbzhzf' : 
					hbzhPay.showPage(obj);
					break;	
				case 'wyzf' :
					unionPay.showPage(obj);
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