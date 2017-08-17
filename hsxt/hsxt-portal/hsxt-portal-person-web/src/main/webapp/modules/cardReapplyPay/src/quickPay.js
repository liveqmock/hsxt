define(['text!cardReapplyPayTpl/quickPay.html',
		/*'text!payTpl/selectPayMethod.html',*/
		'text!cardReapplyPayTpl/quickPaySelected.html',
		'text!cardReapplyPayTpl/openQuickPay.html',
		'text!cardReapplyPayTpl/moreCredit.html',
		'text!cardReapplyPayTpl/moreStorageCard.html',
		'text!cardReapplyPayTpl/qPayRegistered.html',
		'text!cardReapplyPayTpl/kjzffwxy.html',
		'cardReapplyPayLan'
		], function(quickPayTpl, quickPaySelectedTpl, openQuickPayTpl, moreCreditTpl, moreStorageCardTpl, qPayRegisteredTpl, kjzffwxyTpl){
	var creditCardList;// 信用卡集合
	var debitCardList; // 储蓄卡集合
	var isSendCode = false;// 是否发送验证码标识
	var self = {
			
		showPage : function(obj){
			$('#payment_operate').html(_.template(quickPayTpl,obj));//初始化快捷支付页面
			self.getRandomToken();   // 获取随机Token
			self.getBankList();      // 获取绑定的快捷支付银行卡集合
			self.getCode_Qpay(obj);  // 获取快捷支付验证码
			self.comfirmPay(obj);    // 确定快捷支付
			self.openQuickPay(obj);  // 添加快捷支付的银行卡（储蓄、信用卡）
			self.changeLabel(obj);   // 切换支付方式页签
			self.changeLabel1(obj);  // 切换网银支付方式页签
		},
		//获取绑定的快捷支付银行卡集合
		getBankList : function(){
			var jsonParam =  {custType:1};
			comm.requestFun("quickBankDetailSearch",jsonParam,function(response){
				var queryOptions = [];
				if (response) {
					var datas = response.data;
					if(datas && datas.length > 0){
						for (var i = 0 ;i < datas.length;i++){
							var value = datas[i];
							var bankCode = value.bankCode;
							var signNo = value.signNo;
							var bankName = value.bankName;
							var bankCardType = value.bankCardType;
							var cardType = comm.getNameByEnumId(value.bankCardType, comm.lang("myHsCard").bankCardTypeEnum);
							var bankCardNo = value.bankCardNo;
							var lastNo = bankCardNo.substring(bankCardNo.length-4,bankCardNo.length);
							var selected = false;
							if(i == 0){
								selected = true;
							}
							queryOptions.push({
								bank     : bankCode,
								signNo   : signNo,
								cardType : cardType,
								lastNo   : lastNo,
								fullNo   : signNo,
								selected : selected
							});
						}
					}
					$('#cardList').selectBank({
						optionHeight:150,
						optionWidth:200,
						cardListInfo:queryOptions
					});
				}
			},comm.lang("hsbAccount"));

		},
		// 获取快捷支付验证码
		getCode_Qpay : function(obj){
			$('#getCode_qPay').click(function(){
				var signNo = $('#cardList').attr("optionValue");
				if(comm.isEmpty(signNo)){
					comm.error_alert(comm.lang("hsbAccount").selectBandQuickBank);
					return;
				}
				var data = {};
				data.orderNo = obj.orderNumber;
				data.bindingNo = signNo;
				data.privObligate = "";
				comm.requestFun("sendQuickPaySms",data,function(response){
					if(response){
						comm.yes_alert(comm.lang("hsbAccount").accountManage_sendSmsSucc);
						isSendCode = true;
					}
				},comm.lang("hsbAccount"));
			});
		},
		// 确定快捷支付
		comfirmPay : function(obj){
			$('#comfirmPay_btn').click(function(){
				if(!isSendCode){comm.yes_alert(comm.lang("hsbAccount").sendSmsCode);return;} // 请先获取手机验证码
				var valid = self.validatePWD(); //校验 
				if(!valid.form()){return;}
				var jsonData = {};
				jsonData.orderNo = obj.orderNumber;
				jsonData.bindingNo = comm.removeNull($('#cardList').attr("signNo"));  //签约号
				jsonData.smsCode = comm.removeNull($.trim($("#pay_SMScode").val())); //校验码
				jsonData.payChannel = "102";
				// 校验支付密码后去付款
				comm.requestFun("toolOrderOayment",jsonData,function(response){
					function jumpUrls(){
						$(".ul-myhs-left-list li:eq(3)").trigger("click");
					}
					if(response){
						var data = eval('(' + response.data + ')');
						if(data.transStatus == '100'){
							comm.yes_alert(comm.lang("hsbAccount").quickPaySucc);
							setTimeout(jumpUrls,3000);
						}else if(data.transStatus == '102'){
							comm.yes_alert(comm.lang("hsbAccount").quickPayProcessing);
							setTimeout(jumpUrls,3000);
						}else if(comm.lang("hsbAccount").dhhsb[data.bankRespCode]){
							comm.error_alert(comm.lang("hsbAccount").dhhsb[data.bankRespCode]);
						}else{
							comm.error_alert(comm.lang("hsbAccount").quickPayFail);
						}
					}
				},comm.lang("hsbAccount"));
			});
		},
		
		// 同意开通储蓄卡快捷方式并支付
		agreeCXKToPay : function(obj,bankName){
			$('#agreeToPay_btn').click(function(){
				var valid = self.validateReg();
				if(!valid.form()){return;}
				//传递参数
				var jsonData = {};
				jsonData.orderNo = comm.removeNull(obj.orderNumber); // 订单号
				jsonData.bankCardNo = comm.removeNull($.trim($("#pay_yhkh").val())); //开通的快捷支付银行卡号
				jsonData.bankCardType = 1; //储蓄卡
				jsonData.bankId = comm.removeNull(bankName); // 银行ID
				// 同意开通并支付
				comm.requestFun("openQuickPay",jsonData,function(response){
					var jumpUrl = response.data;
					comm.alert({
						width : 800,
						height : 200,
						imgClass : 'tips_yes',
						timeClose : 3,
						content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + comm.formatMoneyNumber(obj.paymentAmount) + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'
					});
					setTimeout(jumpUrls,3000);
					function jumpUrls(){
						window.open(jumpUrl);
						$("#ul_myhs_right_tab li:eq(4)").find("a").trigger("click");
					}
				},comm.lang("cardReapplyPay"));

			});
		},
		// 同意开通信用卡快捷方式并支付
		agreeXYKToPay : function(obj,bankName){
			$('#agreeToPay_btn').click(function(){
				var valid = self.validateReg();
				if(!valid.form()){return;}
				//传递参数
				var jsonData = {};
				jsonData.orderNo = comm.removeNull(obj.orderNumber); // 订单号
				jsonData.bankCardNo = comm.removeNull($.trim($("#pay_yhkh").val())); //开通的快捷支付银行卡号
				jsonData.bankCardType = 2; //信用卡
				jsonData.bankId = comm.removeNull(bankName); // 银行ID
				// 同意开通并支付
				comm.requestFun("openQuickPay",jsonData,function(response){
					var jumpUrl = response.data;
					comm.alert({
						width : 800,
						height : 200,
						imgClass : 'tips_yes',
						timeClose : 3,
						content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + comm.formatMoneyNumber(obj.paymentAmount) + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'
					});
					setTimeout(jumpUrls,3000);
					function jumpUrls(){
						window.open(jumpUrl);
						//location.href = jumpUrl;
					}
				},comm.lang("cardReapplyPay"));
			});
		},
		// 添加新的银行卡
		openQuickPay : function(obj){
			
			// 其他银行事件
			$('#other_bank').find('input[name = "otherQuickPay"]').click(function(){
				var val = $(this).val(),
					no = $(this).parents('li').siblings('li.pay-card-no').text(),
					obj_2 = {bankName : val, cardNo : no};
				    obj_2.paymentAmount = obj.paymentAmount;
				$('#pay_selected').html(_.template(quickPaySelectedTpl, obj_2));
			});
			// 添加新的银行卡
			$('#openQuickPay_btn').unbind("click").bind("click",function(){

				$("#pay_SMScode").tooltip().tooltip("destroy");
				$('#openQuickPay_step1').html(openQuickPayTpl);
				self.showTpl($('#openQuickPay_step1'));
				$('#car_pay_money').html(comm.formatMoneyNumber(obj.paymentAmount));

				self.getPayBankAll(obj);//获取支持快捷支付的储蓄和信用银行卡列表
				
				// 储蓄卡和信用卡的切换事件
				$('#pay_card_tab').children('h1').click(function(){
					$('#pay_card_tab').children('h1').removeClass('hover');
					$(this).addClass('hover');
					if($(this).attr('data-tab') == 'cxk'){
						$('#qPay_ul_cxk').removeClass('none');
						$('#qPay_ul_xyk').addClass('none');
					}
					else if($(this).attr('data-tab') == 'xyk'){
						$('#qPay_ul_cxk').addClass('none');
						$('#qPay_ul_xyk').removeClass('none');
					}
				});

				// 返回快捷支付页面
				$('#back_qpay').click(function(){
					$("#pay_yhkh").tooltip().tooltip("destroy");
					self.showTpl($('#quickPayTpl'));
				});

				/*储畜卡银行列表点击事件*/
				var qPay_ul_cxk = $('#qPay_ul_cxk');
				/*选择更多银行操作*/
				qPay_ul_cxk.children('li:last').click(function(){
					qPay_ul_cxk.append(_.template(moreStorageCardTpl,debitCardList));
					$(this).hide();
					/*收起操作*/
					qPay_ul_cxk.children('li:last').click(function(){
						qPay_ul_cxk.children('li.add').remove().end()
							.children('li.more-select').show();
					});
				});

				// 储蓄卡列表添加对应的银行logo样式
				qPay_ul_cxk.on('click','.bank_click', function(){
					var bankName = $(this).children('i').attr('data-name');
					$('#openQuickPay_step2').html(qPayRegisteredTpl);
					self.showTpl($('#openQuickPay_step2'));
					$('#pay_money_2').html(comm.formatMoneyNumber(obj.paymentAmount));
					$('#cardType').text('储畜卡');
					$('#bankLogo').addClass('bank-' + bankName);
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						$("#pay_yhkh").tooltip().tooltip("destroy");
						self.showTpl($('#openQuickPay_step1'));
					});
					self.agreeCXKToPay(obj,bankName);// 同意开通快捷方式并支付
					// 快捷支付服务协议
					$('#kjzffwxy_btn').click(function(){
						$('#kjzffwxy_dlg').html(kjzffwxyTpl).dialog({
							title : '快捷支付服务协议',
							width : '500',
							modal : true,
							closeIcon : true,
							buttons : {
								'关闭' : function(){
									$(this).dialog('destroy');
								}
							}
						});
					});

				});

				/*信用卡银行列表点击事件*/
				var qPay_ul_xyk = $('#qPay_ul_xyk');
				/*选择更多银行操作*/
				qPay_ul_xyk.children('li:last').click(function(){
					qPay_ul_xyk.append(_.template(moreCreditTpl,creditCardList));
					$(this).hide();
					/*收起操作*/
					qPay_ul_xyk.children('li:last').click(function(){
						qPay_ul_xyk.children('li.add').remove().end()
							.children('li.more-select').show();
					});
				});
				// 信用卡列表添加对应的银行logo样式
				qPay_ul_xyk.on('click','.bank_click', function(){
					var bankName = $(this).children('i').attr('data-name');
					$('#openQuickPay_step2').html(qPayRegisteredTpl);
					self.showTpl($('#openQuickPay_step2'));
					$('#pay_money_2').html(comm.formatMoneyNumber(obj.paymentAmount));
					$('#cardType').text('信用卡');
					$('#bankLogo').addClass('bank-' + bankName);
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						self.showTpl($('#openQuickPay_step1'));

					});
					self.agreeXYKToPay(obj,bankName);// 同意开通信用卡快捷方式并支付
					// 快捷支付服务协议
					$('#kjzffwxy_btn').click(function(){
						$('#kjzffwxy_dlg').html(kjzffwxyTpl).dialog({
							title : '快捷支付服务协议',
							width : '500',
							modal : true,
							closeIcon : true,
							buttons : {
								'关闭' : function(){
									$(this).dialog('destroy');
								}
							}
						});
					});
				});
			});
		},
		
		//获取支持快捷支付的储蓄和信用银行卡列表
		getPayBankAll : function(obj){
			var jsonParam =  {custType:1};
			comm.requestFun("queryPayBankAll",jsonParam,function(response){
				$('#car_pay_money').html(comm.formatMoneyNumber(obj.paymentAmount));
				if (response.data) {
					creditCardList = response.data.CreditCard;
					debitCardList = response.data.DebitCard;
					$.each(debitCardList,function(n,value){
						if($("#qPay_ul_cxk li:eq(5)").length == 0){
							$("#qPay_ul_cxk").prepend("<li class='bank_click'><i class='bank-logo bank-"+value.bankCode+"' " + "data-name="+value.bankCode+"></i></li>");
						}
						if(n == 4) return false;
					});
					$.each(creditCardList,function(n,value){
						if($("#qPay_ul_xyk li:eq(5)").length == 0){
							$("#qPay_ul_xyk").prepend("<li class='bank_click'><i class='bank-logo bank-"+value.bankCode+"' " + "data-name="+value.bankCode+"></i></li>");
						}
						if(n == 4) return false;
					});
				}
			},comm.lang("hsbAccount"));
		},
		// 校验
		validatePWD : function(){
			return $("#pwdForm").validate({
				left : 300,
				top : -100,
				rules : {
					pay_SMScode: {
						digits : true,
						rangelength : [6,6],
						required : true
					}
				},
				messages : {
					pay_SMScode : {
						digits : comm.lang('hsbAccount').hsbBuy.digits2,
						rangelength : comm.lang('hsbAccount').hsbBuy.numLen,
						required : comm.lang('hsbAccount').smsCode
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+180 top+5",
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
		},
		// 切换互生币/货币支付方式页签
		changeLabel : function(obj){
			$('#aPay_btn').html(obj.paymentName_1).click(function(){
				$("#pay_SMScode").tooltip().tooltip("destroy");
				if(obj.paymentName_1 == '货币账户支付'){
					obj.paymentMethod = 'hbzhzf';
				}
				else if(obj.paymentName_1 == '互生币账户支付'){
					obj.paymentMethod = 'hsbzf';
				}
				require(['cardReapplyPaySrc/pay'],function(pay){
					pay.showPage(obj);
				});
			});
		},
		// 切换网银支付方式页签
		changeLabel1 : function(obj){
			$('#bPay_btn').click(function(){
				$("#pay_SMScode").tooltip().tooltip("destroy");
				obj.paymentMethod = 'wyzf';
				require(['cardReapplyPaySrc/pay'],function(pay){
					pay.showPage(obj);
				});
			});
		},
		
		/**
		 * 获取随机token数据
		 */
		getRandomToken : function (){
			//获取随机token
			comm.getToken(null,function(response){
				//非空数据验证
				if(response.data)
				{
					$("#pi_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("pointAccount").randomTokenInvalid);
				}

			});
		},
		validateReg : function(){
			return $("#registeredForm").validate({
				rules : {
					pay_yhkh : {
						required : true	,
						rangelength	 : [16,19]
					}
				},
				messages : {
					pay_yhkh : {
						required : comm.lang('hsbAccount').bankNo,
						rangelength   : comm.lang('hsbAccount').bankNoLength
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+180 top+5",
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
		},
		showTpl : function(tplObj){
			$('#quickPayTpl, #openQuickPay_step1, #openQuickPay_step2').addClass('none');
			tplObj.removeClass('none');
		}
	};
	return self;
});