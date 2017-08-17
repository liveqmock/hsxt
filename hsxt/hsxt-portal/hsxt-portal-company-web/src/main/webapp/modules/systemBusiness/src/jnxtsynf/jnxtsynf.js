define(['text!systemBusinessTpl/jnxtsynf/jnxtsynf.html',
        'text!systemBusinessTpl/jnxtsynf/payment.html',
		'text!systemBusinessTpl/jnxtsynf/dialog_info.html',
		'systemBusinessDat/systemBusiness'
		], function(tpl, paymentTpl,dialogTpl,systemBusiness){
	return jnxtsynf={
		showPage : function(){
			var resData;
			
			systemBusiness.serchAnnualfeeInfo({},function(res){
				
				res.data.availablePointNum = comm.formatMoneyNumber(res.data.availablePointNum);
				res.data.price = comm.formatMoneyNumber(res.data.price);
				res.data.payFee = comm.formatMoneyNumber(res.data.payFee);
				
				$('#busibox').html(_.template(tpl,res.data)).append(paymentTpl);
				resData = res.data;
						
						//提交按钮事件
						$('#btn_jnxtsynf_tj').click(function(){
							var param = comm.getRequestParams();
							var postData = {
									"order.custId" : param.entCustId,
									"order.orderHsbAmount" : res.data.payFee,
									"order.orderChannel" : 1,
									"order.orderOperator" : param.custName+"("+param.operName+")",
									"areaStartDate" : resData.payTime.split("~")[0].trim(),
									"areaEndDate" : resData.payTime.split("~")[1].trim()
							};
							
							systemBusiness.createAnnualFeeOrder(postData,function(res1){
								
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
										hbAmount : hbAmount
								}
								
								require(['systemBusinessSrc/jnxtsynf/pay/pay'], function(pay){
									pay.showPage(obj);
								});
							});
						});
			});
		}
	};
});