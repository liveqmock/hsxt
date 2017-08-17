define(['text!accountManageTpl/pay/quickPay.html',
		'text!accountManageTpl/pay/openQuickPay.html',
		'text!accountManageTpl/pay/moreCredit.html',
		'text!accountManageTpl/pay/moreStorageCard.html',
		'text!accountManageTpl/pay/qPayRegistered.html',
		'text!accountManageTpl/pay/agreement_dialog.html',
		'accountManageDat/hsbzh/hsbzh'
], function(quickPayTpl, openQuickPayTpl, moreCreditTpl,moreStorageCardTpl, qPayRegisteredTpl,agreementTpl,accountManageAjax){
	var quickPay = {
		defalutCountdown : 60,		//默认倒计时时间数（秒）
		countdown : 60,				//快捷验证码重新发送时间（秒）
		showPage : function(obj){
			
			//初始化读秒信息
			quickPay.countdown = 0;
			$("#getCode_countdown").hide();
			$("#getCode_qPay").show();
			$("#getCode_qPay").text("获取验证码");
			
			$('#payment_operate').html(_.template(quickPayTpl,obj));
			var self = this;
			var creditCardList;
			var debitCardList;
			
			var jsonParam =  {};
			//加入获取绑定的快捷支付银行卡集合
			comm.requestFun("quickBankDetailSearch",jsonParam,function(response){
				var queryOptions = [];
				if (response) {
					var datas = response.data;
					$.each(datas,function(n,value){
						var bankCode = value.bankCode;
						var signNo = value.signNo;
						var bankName = value.bankName;
						var cardType = comm.getNameByEnumId(value.bankCardType, comm.lang("accountManage").bankCardTypeEnum);
						
						var bankCardNo = value.bankCardNo;
						var lastNo = bankCardNo.substring(bankCardNo.length-4,bankCardNo.length);
						var selected = false;
						if(n == 0){
							selected = true;
						}
						queryOptions.push({bank     : bankCode,
										   cardType : cardType,
										   lastNo   : lastNo,
										   fullNo   : signNo,
										   selected : selected
										  });
					});
					$('#cardList').selectBank({
						optionHeight:150,
						optionWidth:200,
						cardListInfo:queryOptions
					});
				}
			},comm.lang("hsbAccount"));
			
//			$('#forgetPwd_qPay').click(function(){
//				
//				comm.setCache('safeSet','czjymm','1');
//				$('#Nav_6').trigger('click');
//			});
			
			//快捷支付提交
			$('#comfirmPay_btn').click(function(){
				if(!self.validatePWD()){
					return;	
				}
				
				var bindingNo =$("#cardList").attr('optionvalue');
				var smsCode = $.trim($("#pay_SMScode").val());		//校验码
				if(comm.isEmpty(bindingNo)){
					comm.error_alert(comm.lang("systemBusiness").accountManage_selectBandQuickBank);
					return;
				}
				
				//获取token
				comm.getToken(function(response){
					if(response){
						//确认取消按钮
						comm.i_confirm(comm.lang("accountManage").affireKjZf,function() {
								//重置短信获取按钮
								quickPay.countdown = 0;
								$("#pay_SMScode").val("");
								$("#pay_SMScode").addClass("bg_ef");
								$("#pay_SMScode").attr("readonly", true);
								
								var param = {
										transNo:obj.orderNumber,
										bindingNo:bindingNo,
										randomToken:response.data,
										smsCode:smsCode
									};
								accountManageAjax.validatePayPwd(param,function(resp){
									function jumpUrls(){
										$("#hsbzh_zhye").find("a").trigger("click");
									}
									if(resp){	
										var data = eval('(' + resp.data + ')'); 
										if(data.transStatus == '100'){
											comm.yes_alert(comm.lang("accountManage").accountManage_quickPaySucc);
											setTimeout(jumpUrls,3000);
										}else if(data.transStatus == '102'){
											comm.yes_alert(comm.lang("accountManage").accountManage_quickPayProcessing);
											setTimeout(jumpUrls,3000);
										}else if(comm.lang("accountManage").dhhsb[data.bankRespCode]){
											comm.error_alert(comm.lang("accountManage").dhhsb[data.bankRespCode]);
										}else{
											comm.error_alert(comm.lang("accountManage").accountManage_quickPayFail);
										}
									}
								});
							});
						}
					});
			});	
			
			//发送短信验证码
			$('#getCode_qPay').click(function(){
				var bindingNo = $('#cardList').attr("optionvalue");
				if(comm.isEmpty(bindingNo)){
					comm.error_alert(comm.lang("accountManage").accountManage_selectBandQuickBank);
					return;
				}
				var param = {
					transNo:obj.orderNumber,
					bindingNo:bindingNo,
					privObligate : ""
				};
				accountManageAjax.quickPayVerifyCode(param,function(resp){
					if(resp){
						comm.yes_alert(comm.lang("accountManage").accountManage_sendSmsSucc);
						
						quickPay.countdown=quickPay.defalutCountdown;
						$("#pay_SMScode").attr("readonly", false);
						$("#pay_SMScode").removeClass("bg_ef");
						
						//按钮倒计时
						quickPay.countdownSend();
					}
				});
			});
			
			//开通快捷支付
			$('#openQuickPay_btn').unbind('click').click(function(){

				//$("#quick_pay_pwd").tooltip().tooltip("destroy");
				$("#pay_SMScode").tooltip().tooltip("destroy");
				$('#openQuickPay_step1').html(openQuickPayTpl);
				self.showTpl($('#openQuickPay_step1'));	
				
				$('#pay_money').text(obj.paymentAmount);
				
				
				//获取支持快捷支付的储蓄银行卡列表
				comm.requestFun("queryPayBankAll",jsonParam,function(response){
					
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
					/*end*/
					
				});
				/*end*/
				
				qPay_ul_cxk.on('click','.bank_click', function(){
					var bankName = $(this).children('i').attr('data-name');
											
					$('#openQuickPay_step2').html(qPayRegisteredTpl);
					self.showTpl($('#openQuickPay_step2'));
					
					$('#pay_money_2').text(obj.paymentAmount);
					$('#cardType').text('储畜卡');
					$('#bankLogo').addClass('bank-' + bankName);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						$("#pay_yhkh").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
						
						self.showTpl($('#openQuickPay_step1'));	
						
					});
					
					$('#agreeToPay_btn').click(function(){
						
						if(!self.validateReg()){
							return;	
						}
						comm.getToken(function(resp){
							if(resp){
								comm.i_confirm(comm.lang("accountManage").affireKjZf,function() {
									var bankCardNo = $.trim($("#pay_yhkh").val());	//开通的快捷支付银行卡号
									var bankCardType = 1;//储蓄卡
									var bankId = bankName;
									var url = "http://localhost:8081/hsxt-portal-company-web/index.html";
									//传递参数
									var jsonData = {	
										transNo			:   obj.orderNumber,
										bankCardNo      :   bankCardNo,
										bankCardType    :   bankCardType,
										bankId			:   bankId,
										randomToken		:	resp.data,
										jumpUrl			:   url
									};
									// 同意开通并支付
									accountManageAjax.openQuickPay(jsonData,function(response){
										
										var jumpUrl = response.data;
										comm.alert({
											width : 800,
											height : 200,
											imgClass : 'tips_yes',
											timeClose : 3,
											content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'	
										});
										setTimeout(jumpUrls,3000);
										function jumpUrls(){
											window.open(jumpUrl);
											$("#hsbzh_zhye").find("a").trigger("click");
										}
									});
								});
							}
						});
					});
					
					$('#kjzffwxy_btn').click(function(){
						$("#pay_yhkh").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
						$('#kjzffwxy_dlg').html(agreementTpl).dialog({
							title : '快捷支付服务协议',
							width : '800',
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
				
				/*end*/
				
				
				
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
					/*end*/
					
				});
				/*end*/
				
				qPay_ul_xyk.on('click','.bank_click', function(){
					var bankName = $(this).children('i').attr('data-name');
											
					$('#openQuickPay_step2').html(qPayRegisteredTpl);
					self.showTpl($('#openQuickPay_step2'));
					
					$('#pay_money_2').text(obj.paymentAmount);
					$('#cardType').text('信用卡');
					$('#bankLogo').addClass('bank-' + bankName);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						self.showTpl($('#openQuickPay_step1'));	
						
					});
					
					$('#agreeToPay_btn').click(function(){
						
						if(!self.validateReg()){
							return;	
						}
						var bankCardNo = $.trim($("#pay_yhkh").val());	//开通的快捷支付银行卡号
						var bankCardType = 2;//信用卡
						var bankId = bankName;
						var url = "http://localhost:8081/hsxt-portal-company-web/index.html";
						//传递参数
						var jsonData = {	
							transNo			:   obj.orderNumber,
							bankCardNo      :   bankCardNo,
							bankCardType    :   bankCardType,
							bankId			:   bankId,
							jumpUrl			:   url
						};
						// 同意开通并支付
						accountManageAjax.openQuickPay(jsonData,function(response){
							
							var jumpUrl = response.data;
							comm.alert({
								width : 800,
								height : 200,
								imgClass : 'tips_yes',
								timeClose : 3,
								content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'	
							});
							setTimeout(jumpUrls,3000);
							function jumpUrls(){
								window.open(jumpUrl);
								$("#hsbzh_zhye").find("a").trigger("click");
							}
						});
						
						
					});
					
					$('#kjzffwxy_btn').click(function(){
						$("#pay_yhkh").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
						$('#kjzffwxy_dlg').html(agreementTpl).dialog({
							title : '快捷支付服务协议',
							width : '800',
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
			
			//切换网银支付
			$('#eBankPay_btn').click(function(){
				//$("#quick_pay_pwd").tooltip().tooltip("destroy");
				$("#pay_SMScode").tooltip().tooltip("destroy");
				obj.paymentMethod = 'eBankPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			//切换互生币支付
			$('#hbPay_btn').click(function(){
				//$("#quick_pay_pwd").tooltip().tooltip("destroy");
				$("#pay_SMScode").tooltip().tooltip("destroy");
				obj.paymentMethod = 'hbPay';
				require(['accountManageSrc/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			/*end*/
		},
		validatePWD : function(){
			return comm.valid({
				formID : '#pwdForm',
				rules : {
					pay_SMScode: {
						digits : true,
						rangelength : [6,6],
						required : true		
					}
				},
				messages : {
					pay_SMScode : {
						digits : comm.lang('accountManage').hsbBuy.digits2,
						rangelength : comm.lang('accountManage').hsbBuy.numLen,
						required : comm.lang("accountManage").smsCode
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
		validateReg : function(){
			return comm.valid({
				formID : '#registeredForm',
				rules : {
					pay_yhkh : {
						required : true	,
						rangelength   : [16,19]
					},
					selectQuick : {
						required : true,
					}
				},
				messages : {
					pay_yhkh : {
						required : comm.lang("accountManage").bankNo	,
						rangelength   : comm.lang('accountManage').bankNoLength
					},
					selectQuick : {
						required : comm.lang("accountManage").pleaseSelectQuick
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
		//显示页面
		showTpl : function(tplObj){
			$('#quickPayTpl, #openQuickPay_step1, #openQuickPay_step2').addClass('none');
			tplObj.removeClass('none');
		},
		/**
		 * 倒计时发送
		 */
		countdownSend : function () { 
			if (quickPay.countdown < 1) {
				$("#getCode_countdown").hide();
				
				$("#getCode_qPay").show();
				$("#getCode_qPay").text("获取验证码");
				quickPay.countdown=quickPay.defalutCountdown;
			} else { 
				$("#getCode_qPay").hide();
				
				$("#getCode_countdown").show();
				$("#getCode_countdown").html("剩余时间&nbsp;(&nbsp" + quickPay.countdown + "&nbsp)秒");
				quickPay.countdown--;
				window.setTimeout(function() { quickPay.countdownSend(); },1000);
			}
		}				
	};
	
	return quickPay;
});