define(['text!systemBusinessTpl/toolManage/pay/hsbPay.html',
        'systemBusinessDat/systemBusiness',
        'accountManageDat/accountManage'
], function(hsbPayTpl,systemBusinessAjax,accountManageAjax){
	var mayUseHsb = "0";
	var self = {
			showPage : function(obj){
				$('#payment_operate').html(_.template(hsbPayTpl, obj));
				mayUseHsb = "0";
				//查询互生币账户
				accountManageAjax.init_hsb_transfer_currency(null,function(resp){
					mayUseHsb = comm.isNotEmpty(resp.data.circulationHsb)?(resp.data.circulationHsb-resp.data.hsbMinimum):"0";
					mayUseHsb = Number(mayUseHsb) > 0 ? mayUseHsb: "0";
					$('#hsbAcct').html(comm.formatMoneyNumber(mayUseHsb));
					if(comm.isNotEmpty(obj.isFrist)){
						obj.isFrist = null;
						if(Number(obj.payHsbAmount) > Number(mayUseHsb)){
							//$('#quickPay_btn').trigger('click');
						}
					}
				});
				$('#forgetPwd_hsb').click(function(){
					$('#020500000000').trigger('click');
					$('#020500000000_subNav_020504000000').trigger('click');
				});
				
				$('#comfirmPay_btn').click(function(){
					if(Number(mayUseHsb) < Number(obj.payHsbAmount)){
						comm.error_alert(comm.lang("systemBusiness").hsbAccountBalanceLess);
						return;
					}
					if(!self.validatePWD()){
						return;	
					}
					comm.getToken(function(resp){
						if(resp){
							var tradePwd = comm.tradePwdEncrypt($('#pay_pwd').val(),resp.data);
							var param = {
									orderNo:obj.orderNo,
									tradePwd:comm.tradePwdEncrypt($('#pay_pwd').val(),resp.data),
									payChannel:200,
									randomToken:resp.data,
									orderAmount:obj.payHsbAmount
								};
							systemBusinessAjax.toolOrderPayment(param,function(resp){
								var msg ='恭喜，您的订单支付成功！金额为：' +  obj.hsbAmount + '您的订单号为：'+obj.orderNo+'。'
								comm.yes_alert(msg,'800');
								$("#gjsgcx").trigger('click');
							});
						}
					});
				});
				
				/* 屏蔽快捷支付
				$('#quickPay_btn').click(function(){
					$('#pay_pwd').tooltip().tooltip("destroy");
					obj.paymentMethod = 'quickPay';
					obj.isFormat = '';
					require(['systemBusinessSrc/toolManage/pay/pay'],function(pay){
						pay.showPage(obj);
					});
				});*/

				$('#eBankPay_btn').click(function(){
					$('#pay_pwd').tooltip().tooltip("destroy");
					obj.paymentMethod = 'eBankPay';
					obj.isFormat = '';
					require(['systemBusinessSrc/toolManage/pay/pay'],function(pay){
						pay.showPage(obj);
					});
				});
			},
			validatePWD : function(){
				return comm.valid({
					formID : '#pwdForm',
					rules : {
						pay_pwd : {
							required : true	,
							minlength:8
						}	
					},
					messages : {
						pay_pwd : {
							required :comm.lang("systemBusiness").transPassNotNull,
							minlength:comm.lang("systemBusiness").trans_pass8
						}
					}
				});	
			}	
			
	};
	return self;	
});