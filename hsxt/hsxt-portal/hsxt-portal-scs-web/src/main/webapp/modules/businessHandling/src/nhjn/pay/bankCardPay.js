define(['text!accountManageTpl/pay/bankCardPay.html', 'businessHandlingDat/businessHandling'], 
		function(bankCardPayTpl,systemBusiness){
	var pingAnPayFun = {
		showPage : function(obj){
			$('#payment_operate').html(_.template(bankCardPayTpl, obj));
			
			//确定支付
			$('#bankCardPay').click(function(){

				var param = comm.getRequestParams();
				var postData = {
						"order.custId" : param.entCustId,
						"order.orderNo" : obj.orderNo,
						"order.payChannel" : 103,
						"order.orderOperator" : param.custName
				};
				
				systemBusiness.payAnnualFee(postData,function(res){
					
					//跳转到支付页面
					window.open(res.data);
					
					var msg ='恭喜，您的订单已提交成功！金额为：' + obj.hbAmount + '您的订单号为：'+obj.orderNo+'。';
					$("#i_content").html(msg);
					$("#i_content").dialog({
		                title:"银行卡缴纳年费",
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
			});	
			
			//货币支付
			$('#hbPay_btn').click(function(){
				obj.paymentMethod = 'hbPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});	
			
			//网银支付
			$('#bPay_btn').click(function(){
				obj.paymentMethod = 'eBankPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});	
			
		}	
	}
	return pingAnPayFun;
});