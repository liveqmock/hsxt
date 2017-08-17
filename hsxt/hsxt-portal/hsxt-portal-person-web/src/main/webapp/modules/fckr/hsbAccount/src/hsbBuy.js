define(["text!fckr_hsbAccountTpl/hsbBuy.html", 
		"text!fckr_hsbAccountTpl/payment.html"
		], function (tpl, tplPayment) {
	var hsbBuy = {
		show : function(dataModule){
			//加载页面模板
			$("#myhs_zhgl_box").html(tpl + tplPayment);
			
			var data = comm.getRequestParams();
			//参数构造
			var jsonParam ={"orderOperator":data.custId}
			                
			
			//获取初始化数据
			dataModule.initTransferHsb(jsonParam,function (response) {
				if (response) {
					//加载数据
					$("#hb_currency").text(response.data.currencyCode);			//当前平台币种
					$("#currencyNameCn").text(response.data.currencyNameCn);			//当前平台币种名称
					$("#hb_rate").text(response.data.exchangeRate);			//当前兑换率
					
					$("#regMinAount_buyHsb").text(comm.formatMoneyNumber(response.data.regBuyHsbMin)); 			// 已填写真实姓名兑换互生最小值
					$('#regMinAount_buyHsbVal').val(response.data.regBuyHsbMin);
					
					$("#regMaxAount_buyHsb").text(comm.formatMoneyNumber(response.data.regBuyHsbMax)); 			// 已填写真实姓名兑换互生最大值
					$('#regMaxAount_buyHsbVal').val(response.data.regBuyHsbMax);
					
					$("#unRegMinAount_buyHsb").text(comm.formatMoneyNumber(response.data.noRegBuyHsbMin)); 	// 未填写真实姓名兑换互生最小值
					$('#unRegMinAount_buyHsbVal').val(response.data.noRegBuyHsbMin);
					
					$("#unRegMaxAount_buyHsb").text(comm.formatMoneyNumber(response.data.noRegBuyHsbMax)); 		// 未填写真实姓名兑换互生最大值
					$('#unRegMaxAount_buyHsbVal').val(response.data.noRegBuyHsbMax);
					
					$("#maxAountDaily_buyHsb").text(comm.formatMoneyNumber(response.data.amountMax)); 	// 每日最多兑换互生币数量
					$('#maxAountDaily_buyHsbVal').val(response.data.amountMax);
					
					$("#leftAountDaily_buyHsb").text(comm.formatMoneyNumber(response.data.amountAvailable)); // 今天还可兑换的互生币数量
					$('#leftAountDaily_buyHsbVal').val(response.data.amountAvailable);
					
					$("#hb_hid_isRegistration").text(response.data.authStatus); // 实名或者未实名标识
				}
			});
			
			//兑换互生币数量文本框-按下键盘事
			$("#hb_appBuyNum").keyup(function () {
				
				//转出积分数数据验证
				var valid = hsbBuy.validateData();
				if (!valid.form()) {
					$("#hb_convertAppMoney").text("");
					$("#hb_realMoney").text("");
					return;
				}
				var hsbNumber =  $(this).val();	//获取互生币数量
				var exchangeRate = $("#hb_rate").text();	//获取货币兑换比率
				
				//数字验证
				if (!isNaN(hsbNumber) && !isNaN(exchangeRate)) {

					var ratefee = $("#htc_hid_currencyFee").val();	//货币转换费

					var num = parseFloat(hsbNumber) * parseFloat(exchangeRate);	//计算需要花多少钱
					
					$("#hb_convertAppMoney").text(comm.formatMoneyNumber(num));
					$("#hb_realMoney").text(comm.formatMoneyNumber(num));
					$("#realMoney").val(num);
					 
				}else {
					$("#hb_convertAppMoney").text("");
					$("#hb_realMoney").text("");
				}
			});
			
			//提交申请点击事件
			$('#hb_QrBtn').click(function(){
				
				//转出积分数数据验证
				var valid = hsbBuy.validateData();
				if (!valid.form()) {
					return;
				}
				
				var hb_rate = $("#hb_rate").text();
				var currency = $("#hb_currency").val();
				var hb_realMoney = $("#hb_realMoney").text();
				var hb_appBuyNum = $("#hb_appBuyNum").val();
				var currencyNameCn = $("#currencyNameCn").text();
				var realMoney = $("#realMoney").val();
				
				jsonParam.hb_rate = hb_rate;
				jsonParam.currency = currency;
				jsonParam.hb_realMoney = hb_realMoney;
				jsonParam.orderHsbAmount = hb_appBuyNum;
				jsonParam.custType = 1;
				
				//兑换互生币提交订单
				dataModule.transferHsb(jsonParam, function (response) { 
					var orderNumber = response.data;
					var obj = {
							paymentMethod : "wyzf",
							currency      : currency,
							currencyName : currencyNameCn,
							buyHsbNum     : hb_appBuyNum,
							paymentAmount : hb_realMoney,
							realMoney     : realMoney,
							hbRate        : hb_rate,
							paymentName_1 : '货币账户支付',
							orderNumber   : orderNumber
						};
						//隐藏申请页面
						$('#hsbBuy').addClass('none');
						$('#pay_div').removeClass('none');
						require(['paySrc/pay'], function(pay){
							pay.showPage(obj);	
						});
				});
			});
		},
		getMinAmount : function(){
			var arry = {};
			var isRegFlag = $("#hb_hid_isRegistration").text();
			if(isRegFlag == '1'){
				var unRegMinAmount = $("#unRegMinAount_buyHsbVal").val();
				var unRegMaxAount = $("#unRegMaxAount_buyHsbVal").val();
				var amountArry = [unRegMinAmount,unRegMaxAount];
				arry.minAmount = unRegMinAmount;
				arry.alarmType = comm.strFormat(comm.lang('hsbAccount').hsbBuy.alarm1,amountArry);
			}else{
				var regMaxAount = $("#regMaxAount_buyHsbVal").val();
				var regMinAmount = $("#regMinAount_buyHsbVal").val();
				arry.minAmount = regMinAmount;
				var amountArry = [regMinAmount,regMaxAount];
				arry.alarmType = comm.strFormat(comm.lang('hsbAccount').hsbBuy.alarm2,amountArry);
			}
			return arry;
		},
		getMaxAmount : function(){
			
			var arry = {};
			var leftAmount = $("#leftAountDaily_buyHsbVal").val();//当日还可兑换金额
			var isRegFlag = $("#hb_hid_isRegistration").text();
			// 未实名注册
			if(isRegFlag == '1'){
				var unRegMaxAount = $("#unRegMaxAount_buyHsbVal").val();
				var unRegMinAmount = $("#unRegMinAount_buyHsbVal").val();
				var leftAmount_f = parseFloat(leftAmount);
				var unRegMaxAount_f = parseFloat(unRegMaxAount);
				if(leftAmount_f > unRegMaxAount_f){
					arry.maxAmount = unRegMaxAount_f;
					var amountArry = [unRegMinAmount,unRegMaxAount];
					arry.alarmType = comm.strFormat(comm.lang('hsbAccount').hsbBuy.alarm1,amountArry);
				}else{
					arry.maxAmount = leftAmount_f;
					if(leftAmount_f <= 0){
						arry.alarmType = comm.lang('hsbAccount').hsbBuy.alarm3;
					}else{
						var leftAmt = [leftAmount_f];
						arry.alarmType = comm.strFormat(comm.lang('hsbAccount').hsbBuy.alarm4,leftAmt);
					}
				}
			//已实名注册
			}else{
				var regMaxAount = $("#regMaxAount_buyHsbVal").val();
				var regMinAmount = $("#regMinAount_buyHsbVal").val();
				var leftAmount_f = parseFloat(leftAmount);
				var regMaxAount_f = parseFloat(regMaxAount);
				if(leftAmount_f > regMaxAount_f){
					arry.maxAmount = regMaxAount_f;
					var amountArry = [regMinAmount,regMaxAount];
					arry.alarmType = comm.strFormat(comm.lang('hsbAccount').hsbBuy.alarm2,amountArry);
				}else{
					arry.maxAmount = leftAmount_f;
					if(leftAmount_f <= 0){
						arry.alarmType = comm.lang('hsbAccount').hsbBuy.alarm3;
					}else{
						var leftAmt = [leftAmount_f];
						arry.alarmType = comm.strFormat(comm.lang('hsbAccount').hsbBuy.alarm4,leftAmt);
					}
				}
			}
			return arry;
		},
		validateData : function () {
			
			var minAmount = hsbBuy.getMinAmount().minAmount;
			var minAlarType = hsbBuy.getMinAmount().alarmType;
			var maxAmount = hsbBuy.getMaxAmount().maxAmount;
			var MaxAlarmType = hsbBuy.getMaxAmount().alarmType;
			
			//转出积分数数据验证
			return $("#hb_appForm").validate({
				rules : {
					hb_appBuyNum : {
						required : true,
						huobi : true,
						max : maxAmount,
						min : minAmount
					}
				},
				messages : {
					hb_appBuyNum : {
						required : comm.lang('hsbAccount').hsbBuy.required,
						huobi : comm.lang("hsbAccount").hsbBuy.digits,
						max : MaxAlarmType,//comm.lang('hsbAccount').hsbBuy.max,
						min : minAlarType//comm.lang('hsbAccount').hsbBuy.min
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
	};
	return hsbBuy;
});
