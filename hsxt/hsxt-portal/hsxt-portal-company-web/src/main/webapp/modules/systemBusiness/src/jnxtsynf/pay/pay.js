define(['text!systemBusinessTpl/jnxtsynf/pay/pay.html',
		'systemBusinessSrc/jnxtsynf/pay/hsbPay',
		'systemBusinessSrc/jnxtsynf/pay/eBankPay',
		'systemBusinessSrc/jnxtsynf/pay/quickPay'
		], function(payTpl, hsbPay, eBankPay, quickPay){
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
			};	
			
			$('#busibox').scroll(function(){
				$('input').tooltip().tooltip('destroy');	
			});
		}
	}	
});