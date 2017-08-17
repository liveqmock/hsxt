define(['text!payTpl/quickPay.html',
		'text!payTpl/quickPaySelected.html',
		'text!payTpl/openQuickPay.html',
		'text!payTpl/moreCredit.html',
		'text!payTpl/qPayRegistered.html'
		], function(quickPayTpl,quickPaySelectedTpl, openQuickPayTpl, moreCreditTpl, qPayRegisteredTpl){
	return quickPay={
		showPage : function(obj){
			$('#payment_operate').html(_.template(quickPayTpl,obj));
			
			$('#forgetPwd_qPay').click(function(){
				comm.setCache('safeSet','czjymm','1');
				$('#Nav_7').trigger('click');
			});
				
			$('#comfirmPay_btn').click(function(){
				if(!quickPay.validatePWD()){
					return;	
				}
				
				comm.alert({
					width : 650,
					height : 200,
					imgClass : 'tips_yes',
					timeClose : 3,
					content : '恭喜，您的订单支付成功！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：011111111，系统将在3秒后，自动返回订单详情页面。</div>'	
				});
			});	
			
			
			$('#other_bank').find('input[name = "otherQuickPay"]').click(function(){
				var val = $(this).val(),
					no = $(this).parents('li').siblings('li.pay-card-no').text(),
					obj_2 = {bankName : val, cardNo : no};
					obj_2.paymentAmount = obj.paymentAmount;
					
				$('#pay_selected').html(_.template(quickPaySelectedTpl, obj_2));
				$('#pay_pwd').val('');	
						
			});
			
			$('#openQuickPay_btn').live('click', function(){
				$('#openQuickPay_step1').html(openQuickPayTpl);
				quickPay.showTpl($('#openQuickPay_step1'));	
				
				$('#pay_money').text(obj.paymentAmount);
				
				$('#back_qpay').click(function(){
					quickPay.showTpl($('#quickPayTpl'));	
				});
				
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
				});
				/*end*/
			
				$('.bank_click').live('click', function(){
					$('#openQuickPay_step2').html(qPayRegisteredTpl);
					quickPay.showTpl($('#openQuickPay_step2'));
					
					$('#pay_money_2').text(obj.paymentAmount);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						quickPay.showTpl($('#openQuickPay_step1'));	
					});
					
					$('#agreeToPay_btn').click(function(){
						if(!quickPay.validateReg()){
							return;
						}
					});
					
				});
			});
			
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
		},
		validatePWD : function(){
			return comm.valid({
				formID : '#pwdForm',
				rules : {
					pay_pwd : {
						required : true	
					},
					pay_SMScode: {
						required : true		
					}	
				},
				messages : {
					pay_pwd : {
						required : '必填'	
					},
					pay_SMScode : {
						required : '必填'
					}
				}
			});	
		},
		validateReg : function(){
			return comm.valid({
				formID : '#registeredForm',
				rules : {
					kz_radio : {
						required : true	
					},
					pay_yhkh : {
						required : true	
					},
					pay_SMScode: {
						required : true		
					}	
				},
				messages : {
					kz_radio : {
						required : '必填'	
					},
					pay_yhkh : {
						required : '必填'	
					},
					pay_SMScode : {
						required : '必填'
					}
				}
			});	
		},
		showTpl : function(tplObj){
			$('#quickPayTpl, #openQuickPay_step1, #openQuickPay_step2').addClass('none');
			tplObj.removeClass('none');
		}	
	}	
});