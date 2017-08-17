define(['text!payTpl/hbzhPay.html'/*,
		'text!payTpl/selectPayMethod.html'*/
		], function(hbzhPayTpl/*, selectPayMethodTpl*/){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(hbzhPayTpl, obj));
			
			var self = this;
				/*selectedValue = $('#selectedForm').serializeJson();
				selectedValue.paymentName_1 = obj.paymentName_1;*/
				
			$('#forgetPwd_hbzh').click(function(){
				comm.setCache('safeSet','czjymm','1');
				$('#Nav_7').trigger('click');
			});
			
			$('#comfirmPay_btn').click(function(){
				if(!self.validatePWD()){
					return;	
				}
				
				/*$('#success_dialog').html(success_dialogTpl);
				$( "#success_dialog" ).dialog({
					title:"温馨提示",
					width:"500",	
					modal:true,
					timeClose : 1
				});*/
				comm.alert({
					width : 650,
					height : 200,
					imgFlag : true,
					imgClass : 'tips_yes',
					timeClose : 3,
					content : '恭喜，您的订单支付成功！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：011111111，系统将在3秒后，自动返回订单详情页面。</div>'	
				});
			});	
			
			/*$('#otherPayment_btn').click(function(){
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
								case 'wyzf' :
									obj.paymentMethod = 'wyzf';
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
			
			$('#qPay_btn').click(function(){
				obj.paymentMethod = 'kjzf';
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
			
		},
		validatePWD : function(){
			return comm.valid({
				formID : '#pwdForm',
				rules : {
					pay_pwd : {
						required : true	
					}	
				},
				messages : {
					pay_pwd : {
						required : '必填'	
					}
				}
			});	
		}	
	}	
});