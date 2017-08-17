define(['text!payTpl/pay.html',
		'paySrc/hsbPay',
		'paySrc/hbzhPay',
		'paySrc/unionPay',
		'paySrc/quickPay'
		], function(payTpl, hsbPay, hbzhPay, unionPay, quickPay){
	return {
		showPage : function(obj){
			$('#pay_div').html(_.template(payTpl, obj));
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
			};	
			
			$('#busibox').scroll(function(){
				$('input').tooltip().tooltip('destroy');	
			});
		}
	}	
});