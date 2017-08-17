define(['text!systemBusinessTpl/toolManage/pay/eBankPay.html',
        'systemBusinessDat/systemBusiness'
], function(eBankPay,systemBusinessAjax){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(eBankPay, obj));
			
			$('#comfirmPay_btn').click(function(){
				var param = {
					orderNo:obj.orderNo,
					payChannel:103
				}
				systemBusinessAjax.toolOrderPayment(param,function(resp){
					if(resp){
						window.open(resp.data);
						var msg ='恭喜，您的订单已提交成功！金额为：' + obj.cashAmount + '您的订单号为：'+obj.orderNo+'。';
						$("#i_content").html(msg);
						$("#i_content").dialog({
			                title:comm.lang("systemBusiness").toolManage_payChannel[103]+comm.lang("systemBusiness").toolManage_orderType[obj.orderType],
			                width:"740",
			                modal:true,
			                buttons:{ 
			                	"支付成功":function(){
			                        $(this).dialog("destroy");
			                        $("#gjsgcx").trigger('click');
			                    },
			                    "支付失败":function(){
			                        $(this).dialog("destroy");
			                        $("#gjsgcx").trigger('click');
			                    }
			                }
			           });
					}
				});
			});	
			
			/* 屏蔽快捷支付
			$('#quickPay_btn').click(function(){
				obj.paymentMethod = 'quickPay';
				obj.isFormat = '';
				require(['systemBusinessSrc/toolManage/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});*/
			
			$('#hsbPay_btn').click(function(){
				obj.paymentMethod = 'hsbPay';
				obj.isFormat = '';
				require(['systemBusinessSrc/toolManage/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});		
		}	
	}	
});