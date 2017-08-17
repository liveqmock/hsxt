define(['text!accountManageTpl/hsbzh/dhhsb/dhhsb.html',
		'text!accountManageTpl/hsbzh/dhhsb/payment.html',
		'accountManageDat/accountManage',
		'systemBusinessDat/systemBusiness',
		"accountManageLan"
		], function(tpl, paymentTpl,accountManageAjax,systemBusiness){
	return dhhsb = {
		restrictData : null, 	//业务限制数据
		showPage : function(){
				
				$('#busibox').html(_.template(tpl)).append(paymentTpl);
				
				//加载数据
				var jsonParam ={}
				accountManageAjax.init_transfer_hsb(jsonParam,function (response) {
					if (response) {
						dhhsb.restrictData = response.data.restrict;						//业务限制数据	
						$("#acpoint_currency").val(response.data.currencyCode);				//当前平台币种
						$("#currencyNameCn").text(response.data.currencyNameCn);			//当前平台币种名称
						$("#acpoint_rate").text(response.data.exchangeRate);				//当前兑换率
						$("#amountMinByTime").text(response.data.aounmtMinByTime); 			// 单笔兑换互生最小值
						$("#amountMaxByTime").text(response.data.amountMaxByTime); 			// 单笔兑换互生最大值
					}
				})
				
				//转现积分数文本框-按下键盘、失去焦点事件
				$("#acpoint_appBuyCpointNum").bind("keyup blur change", function () {
					var v = $(this).val(),
					r = $("#acpoint_rate").text();
					if (!isNaN(v) && !isNaN(r)) {
						var cpoint = parseFloat(v),
						num = (cpoint * r).toFixed(2);
						$("#acpoint_convertAppNum").text(num);
						$("#acpoint_realMoney").text(num);
						if (!v) {
							$("#acpoint_convertAppNum, #acpoint_realMoney").text("");
						}
					}
				});
				
				//申请提交按钮事件
				$('#acpointQrBtn').click(function(){
					try{
						var restrictValue = dhhsb.restrictData.restrictValue;
						if("1" == restrictValue){
							var restrictRemark = dhhsb.restrictData.restrictRemark;
							var msg = "兑换互生币业务暂时不能受理！原因：" + restrictRemark;
							comm.warn_alert(msg);
							return;
						}
					}catch(ex){}
					
					//lyh修改
					var hb_appBuyNum = $("#acpoint_appBuyCpointNum").val();
					if(hb_appBuyNum!=""){
						if(hb_appBuyNum.indexOf(".")!=-1){
							var sxindex=hb_appBuyNum.indexOf(".");
							var laststr=hb_appBuyNum.split(".");
							if(laststr[1]=="00"){
								hb_appBuyNum=hb_appBuyNum.substring(0,sxindex);
							}
							$("#acpoint_appBuyCpointNum").val(hb_appBuyNum);
						}
					}
					
					if (!dhhsb.validateData()) {
						return;
					}
					var hb_rate = $("#acpoint_rate").text();
					var currency = $("#acpoint_currency").val();
					var hb_realMoney = $("#acpoint_realMoney").text();
					var currencyNameCn = $("#currencyNameCn").text();
					
					jsonParam.hb_rate = hb_rate;
					jsonParam.currency = currency;
					jsonParam.hb_realMoney = hb_realMoney;
					jsonParam.orderHsbAmount = hb_appBuyNum;
					hb_appBuyNum=comm.formatMoneyNumber(hb_appBuyNum)
					//兑换互生币提交订单
					accountManageAjax.transfer_hsb(jsonParam, function (response) { 
						var orderNumber = response.data;
						var obj = {
								paymentMethod : "eBankPay",
								currency      : currency,
								currencyName  : currencyNameCn,
								buyHsbNum     : hb_appBuyNum,
								paymentAmount : hb_realMoney,
								hbRate        : hb_rate,
								paymentName_1 : '货币账户支付',
								orderNumber   : orderNumber
						};
						//隐藏申请页面
						$('#cpointPrepay').addClass('none');
						$('#pay_div').removeClass('none');
						require(['accountManageSrc/pay/pay'], function(pay){
							pay.showPage(obj);	
						});
					});
				});
				
				//互生币支付页面返回修改
				$('#acpoint_cash_return').click(function(){
					//隐藏互生币支付页面
					$('#cpointPrepay_cashpay').addClass('none');
					//显示申请页面
					$('#cpointPrepay').removeClass('none');
				});
				//互生币支付页确认
				$('#acpoint_cash_paymentSubmit').click(function(){
					if (!dhhsb.validatePwdData()) {
						return;
					}
					
					comm.confirm({
						content: "确认要申请购买吗？",
						imgFlag: 1,
						callOk: function () {
							
							comm.alert({
								content: '兑换互生币成功！',
								callOk: function(){
									//跳转至明细查询页面
									$("#hsbzh_mxcx").trigger('click');
								}
							});
							//清空数据
							$("#qr_acpoint_cash_appBuyCpointNum").text("");
							$("#acpoint_dealPwd").val("");
						}
					});
				});
				//网银支付页面返回修改
				$('#acpoint_ebank_return').click(function(){
					//隐藏互生币支付页面
					$('#cpointPrepay_ebankpay').addClass('none');
					//显示申请页面
					$('#cpointPrepay').removeClass('none');
				});
				//网银支付页确认
				$('#acpoint_ebank_paymentSubmit').click(function(){
					comm.confirm({
						content: "确认要申请购买吗？",
						imgFlag: 1,
						callOk: function () {
							var radioVal = $("#acpoint_ebank_radioVal").val(),
							appBuyCpointNum = $("#qr_acpoint_ebank_appBuyCpointNum").text(),
							dealPwd = null;
							//window.open(comm.domainList['local']+comm.UrlList["buyCpointOperate"]+"?appBuyCpointNum="+appBuyCpointNum+"&dealPwd="+dealPwd+"&radioVal="+radioVal);
							$('#dhhsb-dialog > p').html('<div class="tc fb f14 pt9">兑换互生币订单已提交银行，请确认支付结果。</div>');
							$('#dhhsb-dialog').dialog({
								title:"兑换互生币",
								width: 500,
								buttons: {
									"支付成功": function(){
										//跳转至明细查询页面
										$("#hsbzh_mxcx").trigger('click');
										$(this).dialog("destroy");
									},
									"支付失败": function(){
										//跳转至明细查询页面
										$("#hsbzh_mxcx").trigger('click');
										$(this).dialog("destroy");
									}
								}
							});
							//清空数据
							$("#qr_acpoint_ebank_appBuyCpointNum").text("");
						}
					});
				});
				
		},

		validateData : function(){
			
			var minAmount = $("#amountMinByTime").text();
			var maxAmount = $("#amountMaxByTime").text();
			
			return comm.valid({
				formID : '#acpoint_appForm',
				rules : {
					acpoint_appBuyCpointNum : {
						required : true	,
						huobi : true,
						max : maxAmount,
						min : minAmount
					}
				},
				messages : {
					acpoint_appBuyCpointNum : {
						required : comm.lang('accountManage').hsbBuy.required,
						huobi : comm.lang("accountManage").hsbBuy.huobi,
						max : comm.lang('accountManage').hsbBuy.max,
						min : comm.lang('accountManage').hsbBuy.min
					}
				}
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#acpoint_appForm_qr',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});