define(['text!systemBusinessTpl/toolManage/pay/pay.html',
		'systemBusinessSrc/toolManage/pay/hsbPay',
		'systemBusinessSrc/toolManage/pay/eBankPay',
		'systemBusinessSrc/toolManage/pay/quickPay'
		], function(payTpl, hsbPay, eBankPay, quickPay){
	return {
		showPage : function(obj){
			if(comm.isEmpty(obj.payHsbAmount)){
				obj.payHsbAmount = obj.hsbAmount;
			}
			if(comm.isNotEmpty(obj.isFormat)&&obj.isFormat=='format'){
				obj.cashAmount = comm.formatMoneyNumber(obj.cashAmount);
				obj.hsbAmount = comm.formatMoneyNumber(obj.hsbAmount);
			}
			$('#pay_div').html(_.template(payTpl, obj));
			var str = obj.paymentMethod || 'hsbPay';
			
			switch(str){
			case 'hsbPay' :
				hsbPay.showPage(obj);
				break;
			case 'eBankPay' :
				eBankPay.showPage(obj);
				break;
			case 'quickPay' :
				quickPay.showPage(obj);
				break;
			};
			$('#busibox').scroll(function(){
				$('input').tooltip().tooltip('destroy');	
			});
		}
	};	
});