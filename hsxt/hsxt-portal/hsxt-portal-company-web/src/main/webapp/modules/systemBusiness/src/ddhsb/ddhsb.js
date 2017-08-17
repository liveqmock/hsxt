define(['text!systemBusinessTpl/ddhsb/ddhsb.html',
		'text!systemBusinessTpl/ddhsb/ddhsb_qr.html',
		'systemBusinessDat/systemBusiness'
		], function(ddhsbTpl, ddhsb_qrTpl, dataMoudle){
	var ddhsb={
		showPage : function(){
			$('#busibox').html(ddhsbTpl).append(ddhsb_qrTpl);	
			dataMoudle.findExchangeHsbInfo(null, function(res){
				var data=res.data;
				if(data){
					// 初始化页面值
					$('#acctBal').val(comm.formatMoneyNumber(data.bal)); 
					$('#changeRate').val(data.rate);//货币转换率
					$('#rateTxt').text(data.rate);//货币转换率
					
					$("#regMinAount_buyHsb").text(data.regBuyHsbMin); 			// 实名认证兑换互生最小值
					$("#regMaxAount_buyHsb").text(data.regBuyHsbMax); 			// 实名认证兑换互生最大值
					$("#unRegMinAount_buyHsb").text(data.noRegBuyHsbMin); 	// 未实名认证兑换互生最小值
					$("#unRegMaxAount_buyHsb").text(data.noRegBuyHsbMax); 		// 未实名认证兑换互生最大值
					
					$("#qr_regMinAount_buyHsb").text(data.regBuyHsbMin); 			// 实名认证兑换互生最小值
					$("#qr_regMaxAount_buyHsb").text(data.regBuyHsbMax); 			// 实名认证兑换互生最大值
					$("#qr_unRegMinAount_buyHsb").text(data.noRegBuyHsbMin); 	// 未实名认证兑换互生最小值
					$("#qr_unRegMaxAount_buyHsb").text(data.noRegBuyHsbMax);// 未实名认证兑换互生最大值
				}
			});
			$('#cardNo').unbind('blur').bind('blur', function(){
				var cardNo=$("#cardNo").val();
				if(cardNo && comm.ispersonResNo(cardNo)){
					dataMoudle.getCardHolderDayLimit({"cardNo":cardNo}, function(res){
						$("#qr_maxAountDaily_buyHsb").text(res.data.amountMax); 	// 每日最多兑换互生币数量
						$("#qr_leftAountDaily_buyHsb").text(comm.formatMoneyNumber2(res.data.amountAvailable)); // 今天还可兑换的互生币数量
					});
					$('#proxyAmount').focus();
				}else{
					ddhsb.validateExp("#cardNo",comm.lang("systemBusiness").resNoP); // 持卡人卡号不正确
				}	
		  });
			
	      $('#confirmBtn').click(function(){
	    	    var cardNo = $('#cardNo').val(); // 互生卡号
	    	    var amount = $('#proxyAmount').val(); // 兑换的互生币金额
	    	    var changeRate = $('#changeRate').val(); // 汇率
				ddhsb.destroyTooltip();
				
				var valid = ddhsb.validateData();
				if (!valid.form()) {
					return false;	
				}
				var param = {};
				param.cardNo = cardNo;
				param.exchangeAmount = amount;
					// 从后台校验当前是否可以进行代兑互生币start
				comm.requestFun("getExchangeHsbValidate",param,function(res){
						var map = res.data;
						if(map.exption){
							switch(map.exption){
								case "1" : ddhsb.validateExp("#acctBal",comm.lang('systemBusiness').hsbBuy.alarm5); // 企业代兑互生币余额不足
								   break;
								case "Y2" : ddhsb.validateExp("#proxyAmount",comm.strFormat(comm.lang('systemBusiness').hsbBuy.alarm2,[$('#regMinAount_buyHsb').text(),$('#regMaxAount_buyHsb').text()])); // 实名认证兑换互生最小值;
						           break;
								case "N2" : ddhsb.validateExp("#proxyAmount",comm.strFormat(comm.lang('systemBusiness').hsbBuy.alarm1,[$('#unRegMinAount_buyHsb').text(),$('#unRegMaxAount_buyHsb').text()])); // 未实名认证兑换互生最小值;
						           break;
								case "Y3" : ddhsb.validateExp("#proxyAmount",comm.strFormat(comm.lang('systemBusiness').hsbBuy.alarm2,[$('#regMinAount_buyHsb').text(),$('#regMaxAount_buyHsb').text()])); // 实名认证兑换互生最大值;
						           break;
								case "N3" : ddhsb.validateExp("#proxyAmount",comm.strFormat(comm.lang('systemBusiness').hsbBuy.alarm1,[$('#unRegMinAount_buyHsb').text(),$('#unRegMaxAount_buyHsb').text()])); // 未实名认证兑换互生最小值;
						           break;
								case "4" :  ddhsb.validateExp("#proxyAmount",comm.lang('systemBusiness').hsbBuy.alarm3); // 持卡人当日可兑换数量为0;
						           break;s
								case "5" :  ddhsb.validateExp("#proxyAmount",comm.strFormat(comm.lang('systemBusiness').hsbBuy.alarm4,[comm.formatMoneyNumber2(map.amt)])); // 持卡人当日可兑换余额不足;
						           break;
							}
						}else{
							ddhsb.showTpl($('#ddhsb_qrTpl'));
							
							$('#numberTxt').text(cardNo);
							$('#ddjeTxt').text(comm.formatMoneyNumber(amount));
							var hsbAmount = amount * changeRate;
	
							$('#payHsbTxt').text(comm.formatMoneyNumber(hsbAmount));
							$('#getHsbTxt').text(comm.formatMoneyNumber(hsbAmount));
							
							$('#cancel_btn').click(function(){
								ddhsb.showTpl($('#ddhsbTpl'));	
							});
							
							$('#dh_btn').unbind('click').click(function(){
									var exchangeParam = {
											entCustType		:	$.cookie('entCustType'),
											perResNo		:	cardNo,
											perCustType		:	"1",
											cashAmt			:	amount,
											channel			:	"1"
									};
									dataMoudle.exchange(exchangeParam,function(res){
										if(res.retCode == 22000){
											comm.yes_alert('代兑互生币成功',null,function(){
												$('#ddhsb').trigger('click');
											});
										}
									});
								});
						}
				},comm.lang("systemBusiness"));// 从后台校验当前是否可以进行代兑互生币end
		});
   },
   validateExp : function (element,error) {
		$(element).attr("title", error).tooltip({
			tooltipClass: "ui-tooltip-error",
			position : {
				my : "left+160 top+5",
				at : "left top"
			}
		}).tooltip("open");
		$(".ui-tooltip").css("max-width", "230px");
   },
   destroyTooltip : function () {
	   $("#acctBal").attr("title","").tooltip().tooltip("destroy");
	   $("#proxyAmount").attr("title","").tooltip().tooltip("destroy");
   },   
   showTpl : function(tplObj){
		$('#ddhsbTpl, #ddhsb_qrTpl').addClass('none');
		tplObj.removeClass('none');	
   },
   validateData : function(){
		return $("#proxyHsbForm").validate({
			rules : {
				cardNo : {
					required : true	,
					personResNo:true
				},
				proxyAmount:{
						     required : true,
						     huobi : true,
				}
			},
			messages : {
				cardNo : {
					required : comm.lang("systemBusiness").wsjfdj_resNoNotNull,
					personResNo: comm.lang("systemBusiness").resNoP	
				},
				proxyAmount:{
							required : comm.lang('systemBusiness').hsbBuy.required,
							huobi : comm.lang("systemBusiness").hsbBuy.huobi,
				}
			},
			errorPlacement : function (error, element) {
				$(element).attr("title", $(error).text()).tooltip({
					tooltipClass: "ui-tooltip-error",
					position : {
						my : "left+160 top+5",
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
	return ddhsb;
});