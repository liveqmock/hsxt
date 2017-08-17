define(['text!businessHandlingTpl/nhjn/nhjn.html',
        'text!businessHandlingTpl/nhjn/payment.html',
        'text!businessHandlingTpl/nhjn/dialog_info.html',
        'text!businessHandlingTpl/nhjn/wyzf.html',
        'text!businessHandlingTpl/nhjn/hsbzf.html',
        'businessHandlingDat/businessHandling'
        ],function( nhjnTpl,paymentTpl,dilTpl,wyzfTpl,hsbzfTpl,businessHandling){
	return {	 	
		showPage : function(){
			businessHandling.searchEntAnnualfeeInfo({},function(res){
				
				res.data.availablePointNum2 = comm.formatMoneyNumber(res.data.availablePointNum);
				res.data.price2 = comm.formatMoneyNumber(res.data.price);
				res.data.payFee2 = comm.formatMoneyNumber(res.data.payFee);
				
					$('#contentWidth_2').html(_.template(nhjnTpl,res.data)).append(paymentTpl) ;
					
					resData = res.data;
					
					//提交按钮事件
					$('#nhjn_post').click(function(){
						
						var param = comm.getRequestParams();
						var postData = {
								"order.custId" : param.entCustId,
								"order.orderHsbAmount" : res.data.payFee,
								"order.orderChannel" : 1,
								"order.orderOperator" : param.custName+"("+param.operName+")",
								"areaStartDate" : resData.payTime.split("~")[0].trim(),
								"areaEndDate" : resData.payTime.split("~")[1].trim()
						};
						
						businessHandling.createAnnualFeeOrder(postData,function(res1){
							
							//隐藏申请页面
							$('#jnxtsynf').addClass('none');
							$('#pay_div').removeClass('none');
							
							
							var hbAmount = parseFloat(resData.transRate) * parseFloat(resData.payFee);
							hbAmount = hbAmount.toFixed(2);
							
							var obj = {
									paymentMethod : 'hsbPay',
									paymentName_1 : '互生币账户支付',
									availableAmount : resData.availablePointNum,
									availableHbNum : resData.availableHbNum,
									paymentAmount : resData.payFee,
									orderNo : res1.data.order.orderNo,
									transRate : resData.transRate,
									hbAmount : hbAmount,
									payHsbAmount : resData.payFee,
									isFrist:"true"
							}
							
							require(['businessHandlingSrc/nhjn/pay/pay'], function(pay){
								pay.showPage(obj);
							});
							
						});
						
					});
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#hsb_form',
				rules : {
					transPass : {
						required : true,
						rangelength : [8,8]
					}
				},
				messages : {
					transPass : {
						required : comm.lang("businessHandling")[32705],
						rangelength : comm.lang("businessHandling").trans_pass8
					}
				}
			});
		}
	}
}); 

 