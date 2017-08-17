define(['text!payTpl/unionPay.html'/*,
		'text!payTpl/selectPayMethod.html'*/
		], function(unionPayTpl/*, selectPayMethodTpl*/){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(unionPayTpl, obj));
			
			/*var self = this,
				selectedValue = $('#selectedForm').serializeJson();
				selectedValue.paymentName_1 = obj.paymentName_1;
			
			$('#otherPayment_btn').click(function(){
				$('#select_dialog').html(_.template(selectPayMethodTpl, selectedValue));
				$( "#select_dialog" ).dialog({
					title:"选择其它支付方式",
					width:"600",
					closeIcon : true,
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
							var val = $(this).find('input[name = "paymentMethod"]:checked').val();
							switch(val){
								case 'hsbzf' :
									obj.paymentMethod = 'hsbzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
								case 'hbzhzf' :
									obj.paymentMethod = 'hbzhzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
								case 'kjzf' :
									obj.paymentMethod = 'kjzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
							}
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
			});*/
			
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
			
			
			$('#qPay_btn').click(function(){
				obj.paymentMethod = 'kjzf';
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			
		}	
	}	
});