define(['text!cardReapplyPayTpl/pay.html',
		'cardReapplyPaySrc/hsbPay',
		'cardReapplyPaySrc/hbzhPay',
		'cardReapplyPaySrc/unionPay',
		'cardReapplyPaySrc/quickPay'
		], function(payTpl, hsbPay, hbzhPay, unionPay, quickPay){
	return {
		showPage : function(obj){
			$('#pay_div').html(_.template(payTpl, obj));
			
			$("#orderNumber").text(obj.orderNumber);
			$("#buyHsbNum").text(obj.buyHsbNum);
			$("#paymentAmount").text(comm.formatMoneyNumber(obj.paymentAmount));
			$("#paymentHbAmount").text(comm.formatMoneyNumber(obj.paymentAmount));
			$("#currency").text(obj.currency);
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
			case 'kjzf' :
				
				break;
			};
			
			$('#busibox').scroll(function(){
				$('input').tooltip().tooltip('destroy');	
			});
		}
	}	
});