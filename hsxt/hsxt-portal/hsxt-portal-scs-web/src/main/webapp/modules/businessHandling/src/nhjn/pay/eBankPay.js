define(['text!businessHandlingTpl/nhjn/pay/eBankPay.html',
        'businessHandlingDat/businessHandling'
], function(eBankPay,systemBusiness){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(eBankPay, obj));
			var self = this;
			
			$('#comfirmPay_btn').click(function(){

				
				/*******缴纳年费 start***********/
				var param = comm.getRequestParams();
				var postData = {
						"order.custId" : param.entCustId,
						"order.orderNo" : obj.orderNo,
						"order.payChannel" : 100,
						"order.orderOperator" : param.custName,
						callbackUrl : "http://www.baidu.com"
				};
				
				systemBusiness.payAnnualFee(postData,function(res){
					
					//跳转到支付页面
					window.open(res.data);
					
					var msg ='恭喜，您的订单已提交成功！金额为：' + obj.hbAmount + '您的订单号为：'+obj.orderNo+'。';
					$("#i_content").html(msg);
					$("#i_content").dialog({
		                title:"网银缴纳年费",
		                width:"700",
		                modal:true,
		                buttons:{ 
		                	"支付成功":function(){
		                        $(this).dialog("destroy");
		                        $("#Nav_5").trigger('click');
		                        $("#subNav_5_02").trigger('click');
		                    },
		                    "支付失败":function(){
		                        $(this).dialog("destroy");
		                        $("#Nav_5").trigger('click');
		                        $("#subNav_5_02").trigger('click');
		                    }
		                }
		           });
				});
				/*********** 缴纳年费end ********/
			});	
			
			$('#quickPay_btn').click(function(){
				obj.paymentMethod = 'quickPay';
				require(['businessHandlingSrc/nhjn/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			
			$('#hsbPay_btn').click(function(){
				obj.paymentMethod = 'hsbPay';
				require(['businessHandlingSrc/nhjn/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});		
			
			//银行卡支付
			$('#bcPay_btn').click(function(){
				obj.paymentMethod = 'yhkzf';
				require(['businessHandlingSrc/nhjn/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
		}	
	}	
});