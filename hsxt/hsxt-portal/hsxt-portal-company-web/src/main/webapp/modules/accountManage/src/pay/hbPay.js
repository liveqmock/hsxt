define(['text!accountManageTpl/pay/hbPay.html',
        'accountManageDat/accountManage'
], function(hbPayTpl,accountManageAjax){
	return hbPay = {
		showPage : function(obj){
			$('#payment_operate').html(_.template(hbPayTpl, obj));			
						
			$('#forgetPwd_hsb').click(function(){
				$('#020500000000').trigger('click');
				$('#020500000000_subNav_020504000000').trigger('click');
			});
			
			var jsonParam = {};
			
			// 查询货币余额
			comm.requestFun("find_currency_blance",jsonParam,function(response){
				
					var currencyBlance = "0"
					if(response.data && response.data.currencyBlance){
						currencyBlance = response.data.currencyBlance;
					}
					$("#hbAmount").text(currencyBlance);
					
			},comm.lang("hsbAccount"));
			
			
			
			$('#comfirmPay_btn').click(function(){
				
				var hbAmount = $("#hbAmount").text();
				var payAmount = obj.paymentAmount;
				var hbAmount_f = parseFloat(hbAmount);
				var payAmount_f = parseFloat(payAmount);
				if(payAmount_f > hbAmount_f){
					comm.warn_alert("货币余额不足");
					return;
				}
				
				if(!hbPay.validatePWD()){
					return;	
				}
				comm.getToken(function(resp){
					if(resp){
						//var tradePwd = comm.encrypt($('#hb_pay_pwd').val(),resp.data);
						
						//传递参数
						var param = {
								transNo:obj.orderNumber,
								tradePwd:comm.tradePwdEncrypt($('#hb_pay_pwd').val(),resp.data),
								randomToken:resp.data
							};
						accountManageAjax.validateHbPay(param,function(resp){
							var msg ='恭喜，您的订单支付成功！金额为：' + obj.paymentAmount + '您的订单号为：'+obj.orderNumber+'。'
							comm.yes_alert(msg,'700');
							setTimeout(jumpUrls,2000);
							function jumpUrls(){
								
								$("#hsbzh_zhye").find("a").trigger("click");
							}
						});
					}
				});
			});				
			
			//快捷支付
			$('#quickPay_btn').click(function(){
				$("#hb_pay_pwd").tooltip().tooltip("destroy");
				obj.paymentMethod = 'quickPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			
			//网页支付
			$('#eBankPay_btn').click(function(){
				$("#hb_pay_pwd").tooltip().tooltip("destroy");
				obj.paymentMethod = 'eBankPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
			//银行卡支付
			$('#bcPay_btn').click(function(){
				$("#hb_pay_pwd").tooltip().tooltip("destroy");
				obj.paymentMethod = 'yhkzf';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
		},
		validatePWD : function(){
			return comm.valid({
				formID : '#pwdForm',
				rules : {
					hb_pay_pwd : {
						required : true	,
						minlength:8
					}	
				},
				messages : {
					hb_pay_pwd : {
						required : comm.lang("accountManage").transPassNotNull,
						minlength:comm.lang("accountManage").trans_pass8
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+180 top+5",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});	
		}	
	}	
});