define(['text!accountManageTpl/pay/eBankPay.html',
        'accountManageDat/accountManage'
], function(eBankPayTpl,accountManageAjax){
	return eBankPay = {
		showPage : function(obj){
			$('#payment_operate').html(_.template(eBankPayTpl, obj));
			
			$('#comfirmPay_btn').click(function(){
				var param = {
						transNo : obj.orderNumber,
						jumpUrl : "",
						privObligate : ""
				}
				accountManageAjax.netPay(param,function(resp){
					if(resp){
						window.open(resp.data);
						var msg ='恭喜，您的订单已提交成功！金额为：' + obj.paymentAmount + '您的订单号为：'+obj.orderNumber+'。';
						$("#i_content").html(msg);
						$("#i_content").dialog({
			                title:"网银兑换互生币",
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
					}
				});
			});	
			
			
			/*$('#quickPay_btn').click(function(){
				obj.paymentMethod = 'quickPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});*/
			
			//银行卡支付
			$('#bcPay_btn').click(function(){
				$("#hb_pay_pwd").tooltip().tooltip("destroy");
				obj.paymentMethod = 'yhkzf';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
			//货币支付
			$('#hbPay_btn').click(function(){
				obj.paymentMethod = 'hbPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});		
		}	
	}	
});