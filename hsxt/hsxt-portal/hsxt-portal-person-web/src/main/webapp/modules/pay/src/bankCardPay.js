define(['text!payTpl/bankCardPay.html'], function(bankCardPay){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(bankCardPay, obj));
			
			
			$('#aPay_btn').text(obj.paymentName_1).click(function(){
					
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
			
			//网银支付
			$('#bPay_btn').click(function(){
				obj.paymentMethod = 'wyzf';
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			
			//银行卡支付
			$('#bankCardPay').click(function(){
				var transNo = obj.orderNumber;		//订单交易流水号
				var param = {
						transNo: transNo ,	
						payChannel : 103 ,
						goodsName : "兑换互生币"
							};
				// 网银支付
				comm.requestFun("pinganPay",param,function(response){
					var jumpUrl = response.data;
					comm.alert({
						width : 800,
						height : 200,
						imgClass : 'tips_yes',
						timeClose : 3,
						content : '恭喜，您的订单在等待支付中！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'	
					});
					
					$("#ul_myhs_right_tab li:eq(3)").find("a").trigger("click");
					
					window.open(jumpUrl);
				},comm.lang("hsbAccount"));
				
			});	
		}	
	}	
});