define(['text!cardReapplyPayTpl/unionPay.html'/*,
		'text!payTpl/selectPayMethod.html'*/
		], function(unionPayTpl/*, selectPayMethodTpl*/){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(unionPayTpl, obj));
			
			
			$('#aPay_btn').text(obj.paymentName_1)
				.click(function(){
					
					if(obj.paymentName_1 == '货币账户支付'){
						obj.paymentMethod = 'hbzhzf';
					}
					else if(obj.paymentName_1 == '互生币账户支付'){
						obj.paymentMethod = 'hsbzf';	
					}

					require(['cardReapplyPaySrc/pay'],function(pay){
						pay.showPage(obj);
					});	
						
				});
			
			/* 
			$('#qPay_btn').click(function(){
				obj.paymentMethod = 'kjzf';
				require(['cardReapplyPaySrc/pay'],function(pay){
					pay.showPage(obj);
				});	
			});*/
			
			var transNo = obj.orderNumber;//订单交易流水号
			
			$('#netPay').click(function(){
				
				
				var url = "http://localhost:8081/hsxt-portal-person-web/index.html";
				//传递参数
				var jsonData = {	
					orderNo			:   transNo,
					jumpUrl			:   url,
					payChannel      :   "103"
				};
				// 网银支付
				comm.requestFun("toolOrderOayment",jsonData,function(response){
					
					var jumpUrl = response.data;
					comm.alert({
						width : 800,
						height : 200,
						imgClass : 'tips_yes',
						timeClose : 3,
						content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + comm.formatMoneyNumber(obj.paymentAmount) + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'
					});
					
					$(".ul-myhs-left-list li:eq(3)").trigger("click");
					
					setTimeout(jumpUrls,3000);
					function jumpUrls(){
						window.open(jumpUrl);
					}
				},comm.lang("hsbAccount"));
				
				});	
		}	
	}	
});