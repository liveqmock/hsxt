define(['text!payTpl/openQuickPay.html',
		'text!payTpl/moreCredit.html'/*,
		'text!payTpl/moreStorageCard.html'*/
		], function(openQuickPayTpl, moreCreditTpl/*, moreStorageCardTpl*/){
	return {
		showPage : function(obj){
			$('#payment_operate').html(openQuickPayTpl);
			
			var paymentName_1 = obj.paymentName_1;
			
			$('#pay_money').text(obj.paymentAmount);
			
			$('#back_myBankCard').click(function(){
					
			});
			
			
			/*信用卡支付*/
			
			var qPay_ul_yhk = $('#qPay_ul_yhk');
			
			/*选择更多银行操作*/			
			qPay_ul_yhk.children('li:last').click(function(){
				
				qPay_ul_yhk.append(moreCreditTpl);
				$(this).hide();
				
				/*收起操作*/	
				qPay_ul_yhk.children('li:last').click(function(){
					qPay_ul_yhk.children('li.add').remove().end()
						.children('li.more-select').show();
					
				});	
				/*end*/
				
			});
			/*end*/
			
			/*已经签约的快捷支付*/
			var qPay_already_ul_xyk = $('#qPay_already_ul_xyk');
			qPay_already_ul_xyk.find('input:radio').click(function(){
				
				var obj = {paymentMethod : 'kjzf'};
					obj.paymentName_1 = paymentName_1;
				
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});	
				
				
			});
			/*end*/
			
			/*选择新快捷支付*/
			var qPay_ul_yhk = $('#qPay_ul_yhk');
			qPay_ul_yhk.find('input:radio').live('click', function(){
				var obj_2 = {bankName : $(this).val(), type : '信用卡'};
				obj_2.paymentAmount = obj.paymentAmount;
				obj_2.paymentMethod = obj.paymentMethod;
				obj_2.paymentName_1 = obj.paymentName_1;
				require(['paySrc/qPayRegistered'], function(qPayRegistered){
					qPayRegistered.showPage(obj_2);
				});
				
			});
			/*end*/
			
			/*end*/
			
			$('#aPay_btn').text(obj.paymentName_1)
				.click(function(){
					
					if(obj.paymentName_1 == '货币账户支付'){
						obj.paymentMethod = 'hbzhzf';
					}
					else if(obj.paymentName_1 == '互生币账户支付'){
						obj.paymentMethod = 'hsbzf';	
					}

					require(['paySrc/pay'],function(pay){
						pay.showPage(obj);
					});	
						
				});
			
			
			$('#bPay_btn').click(function(){
				obj.paymentMethod = 'wyzf';
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
			
		}	
	}	
});