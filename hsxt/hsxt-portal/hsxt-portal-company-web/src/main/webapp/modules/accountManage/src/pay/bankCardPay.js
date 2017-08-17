define(['text!accountManageTpl/pay/bankCardPay.html', 'accountManageDat/accountManage'], function(bankCardPayTpl,accountManage){
	var pingAnPayFun = {
		showPage : function(obj){
			$('#payment_operate').html(_.template(bankCardPayTpl, obj));
			
			//确定支付
			$('#bankCardPay').click(function(){
				var param = {
						transNo : obj.orderNumber, 	
						payChannel : 103,
						goodsName : "兑换互生币"
							};
				//获取支付跳转url地址
				accountManage.pingAnPay(param ,function(resp){
						window.open(resp.data);
						var msg ='恭喜，您的订单已提交成功！金额为：' + obj.paymentAmount + '您的订单号为：'+obj.orderNumber+'。';
						$("#i_content").html(msg);
						$("#i_content").dialog({
			                title:"银行卡支付兑换互生币",
			                width:"700",
			                modal:true,
			                buttons:{ 
			                	"支付成功":function(){
			                        $(this).dialog("destroy");
			                        $("#hsbzh_dhhsb").trigger('click');
			                    },
			                    "支付失败":function(){
			                        $(this).dialog("destroy");
			                        $("#hsbzh_dhhsb").trigger('click');
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