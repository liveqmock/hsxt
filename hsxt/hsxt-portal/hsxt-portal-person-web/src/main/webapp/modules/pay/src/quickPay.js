define(['text!payTpl/quickPay.html',
		/*'text!payTpl/selectPayMethod.html',*/
		'text!payTpl/quickPaySelected.html',
		'text!payTpl/openQuickPay.html',
		'text!payTpl/moreCredit.html',
		'text!payTpl/moreStorageCard.html',
		'text!payTpl/qPayRegistered.html',
		'text!payTpl/kjzffwxy.html'
		], function(quickPayTpl, /*selectPayMethodTpl,*/ quickPaySelectedTpl, openQuickPayTpl, moreCreditTpl, moreStorageCardTpl, qPayRegisteredTpl, kjzffwxyTpl){
	var quickPayFun = {
		defalutCountdown : 60,		//默认倒计时时间数（秒）
		countdown : 60,				//快捷验证码重新发送时间（秒）
		showPage : function(obj){
			$('#payment_operate').html(_.template(quickPayTpl));
			$("#pay_SMScode").val("");
			$("#pay_SMScode").attr("readonly", true);
			$("#pay_SMScode").css({"background":"#efefef"});
			
			quickPayFun.countdown=0;
			var transNo = obj.orderNumber;//订单交易流水号
			var creditCardList;
			var debitCardList;
			
			$("#quickPayAmount").text(obj.paymentAmount);
			$("#buyHsbAmount").val(obj.buyHsbNum);
			
			
			quickPayFun.getRandomToken();
			
			var jsonParam =  {custType:1};
			                
			
			//获取绑定的快捷支付银行卡集合
			comm.requestFun("quickBankDetailSearch",jsonParam,function(response){
				var queryOptions = [];
				if (response) {
					var datas = response.data;
					$.each(datas,function(n,value){
						var bankCode = value.bankCode;
						var signNo = value.signNo;
						var bankName = value.bankName;
						var cardType = comm.getNameByEnumId(value.bankCardType, comm.lang("myHsCard").bankCardTypeEnum);
						var bankCardNo = value.bankCardNo;
						var lastNo = bankCardNo.substring(bankCardNo.length-4,bankCardNo.length);
						var selected = false;
						if(n == 0){
							selected = true;
						}
						queryOptions.push({bank     : bankCode,
										   signNo   : signNo,
										   cardType : cardType,
										   lastNo   : lastNo,
										   fullNo   : bankCardNo,
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
			
			//去掉快捷支付验证交易密码 by likui 
//			$('#forgetPwd_qPay').click(function(){
//				comm.setCache('safeSet','resetDelPassword','1');
//				$('#side_safetySet').click();	
//			});
				
			// 获取快捷支付验证码
			$('#getCode_qPay').click(function(){
				
				var signNo = $('#cardList').attr("signNo");
				var data = {
						transNo			:   transNo,
						bindingNo       :   signNo,
						privObligate	:	""
				}
				comm.requestFun("quickPayVerifyCode",data,function(response){
					if(response){
						//comm.yes_alert(comm.lang("hsbAccount").accountManage_sendSmsSucc);
						
						quickPayFun.countdown=quickPayFun.defalutCountdown;
						$("#pay_SMScode").attr("readonly", false);
						$("#pay_SMScode").css({"background":""});
						//按钮倒计时
						quickPayFun.countdownSend();
					}
				},comm.lang("hsbAccount"));
			});
			
			
			$('#comfirmPay_btn').click(function(){
				var valid = quickPayFun.validatePWD();
				if(!valid.form()){
					return;	
				}
				
				var smsCode = $.trim($("#pay_SMScode").val());		//校验码
				var signNo = $('#cardList').attr("signNo");
				
				//传递参数
				var jsonData = {	
					transNo			:   transNo,
					bindingNo       :   signNo,
					smsCode			:   smsCode
				};
				
				//重置短信获取按钮
				quickPayFun.countdown = 0;
				$("#pay_SMScode").val("");
				$("#pay_SMScode").attr("readonly", true);
				$("#pay_SMScode").css({"background":"#efefef"});
				
				// 去付款
				comm.requestFun("validatePayPwd",jsonData,function(response){
					function jumpUrls(){
						$("#ul_myhs_right_tab li:eq(3)").find("a").trigger("click");
					}
					
					if(response){	
						var data = eval('(' + response.data + ')'); 
						if(data.transStatus == '100'){
							comm.yes_alert(comm.lang("hsbAccount").quickPaySucc);
							setTimeout(jumpUrls,2000);
							quickPayFun.countdown=0;
						}else if(data.transStatus == '102'){
							comm.yes_alert(comm.lang("hsbAccount").quickPayProcessing);
							setTimeout(jumpUrls,2000);
						}else if(comm.lang("hsbAccount").dhhsb[data.bankRespCode]){
							comm.error_alert(comm.lang("hsbAccount").dhhsb[data.bankRespCode]);
						}else{
							comm.error_alert(comm.lang("hsbAccount").quickPayFail);
						}
					}
					
				},comm.lang("hsbAccount"));
				
			});	
			
			
			$('#other_bank').find('input[name = "otherQuickPay"]').click(function(){
				var val = $(this).val(),
					no = $(this).parents('li').siblings('li.pay-card-no').text(),
					obj_2 = {bankName : val, cardNo : no};
					obj_2.paymentAmount = obj.paymentAmount;
					
				$('#pay_selected').html(_.template(quickPaySelectedTpl, obj_2));
						
			});
			
			$('#openQuickPay_btn').click(function(){
				
				$("#pay_SMScode").tooltip().tooltip("destroy");
				
				$('#openQuickPay_step1').html(openQuickPayTpl);
				quickPayFun.showTpl($('#openQuickPay_step1'));	
				
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
					quickPayFun.showTpl($('#quickPayTpl'));	
				});
				
				
				/*储蓄卡银行列表点击事件*/
				
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
					quickPayFun.showTpl($('#openQuickPay_step2'));
					
					$('#pay_money_2').text(obj.paymentAmount);
					$('#cardType').text('储蓄卡');
					$('#bankLogo').addClass('bank-' + bankName);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						$("#pay_yhkh").tooltip().tooltip("destroy");
						quickPayFun.showTpl($('#openQuickPay_step1'));	
						
					});
					
					$('#agreeToPay_btn').click(function(){
						
						var valid = quickPayFun.validateReg();
						if(!valid.form()){
							return;	
						}
					
						var bankCardNo = $.trim($("#pay_yhkh").val());	//开通的快捷支付银行卡号
						var bankCardType = 1;//储蓄卡
						var bankId = bankName;
						var url = comm.domainList['personWeb']+"index.html";
						//传递参数
						var jsonData = {	
							transNo			:   transNo,
							bankCardNo      :   bankCardNo,
							bankCardType    :   bankCardType,
							bankId			:   bankId,
							jumpUrl			:   url
						};
						// 同意开通并支付
						comm.requestFun("dhhsbOpenQuickPay",jsonData,function(response){
							
							var jumpUrl = response.data;
							comm.alert({
								width : 800,
								height : 240,
								imgClass : 'tips_yes',
								timeClose : 3,
								content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><br /><div class="">您的订单号为：' + obj.orderNumber + '<br/><br />系统将在3秒后，自动返回订单详情页面。支付成功的订单可在该页面查询。</div>'	
							});
							setTimeout(jumpUrls,3000);
							function jumpUrls(){
								window.open(jumpUrl);
								$("#ul_myhs_right_tab li:eq(3)").find("a").trigger("click");
							}
						},comm.lang("hsbAccount"));
						
					});
					
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
					quickPayFun.showTpl($('#openQuickPay_step2'));
					
					$('#pay_money_2').text(obj.paymentAmount);
					$('#cardType').text('信用卡');
					$('#bankLogo').addClass('bank-' + bankName);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						quickPayFun.showTpl($('#openQuickPay_step1'));	
						
					});
					
					$('#agreeToPay_btn').click(function(){
						
						var valid = quickPayFun.validateReg();
						if(!valid.form()){
							return;	
						}
						var bankCardNo = $.trim($("#pay_yhkh").val());	//开通的快捷支付银行卡号
						var bankCardType = 2;//信用卡
						var bankId = bankName;
						var url = "http://localhost:8081/hsxt-portal-person-web/index.html";
						//传递参数
						var jsonData = {	
							transNo			:   transNo,
							bankCardNo      :   bankCardNo,
							bankCardType    :   bankCardType,
							bankId			:   bankId,
							jumpUrl			:   url
						};
						// 同意开通并支付
						comm.requestFun("dhhsbOpenQuickPay",jsonData,function(response){
							
							var jumpUrl = response.data;
							comm.alert({
								width : 800,
								height : 200,
								imgClass : 'tips_yes',
								timeClose : 3,
								content : '恭喜，您的订单提交成功！请在支付页面尽快完成支付！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><br /><div class="">您的订单号为：' + obj.orderNumber + '<br/><br />系统将在3秒后，自动返回订单详情页面。支付成功的订单可在该页面查询。</div>'	
							});
							setTimeout(jumpUrls,3000);
							function jumpUrls(){
								window.open(jumpUrl);
								//location.href = jumpUrl;
							}
						},comm.lang("hsbAccount"));
						
						
					});
					
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
				/*end*/
			});
			
			$('#aPay_btn').text(obj.paymentName_1).click(function(){
				
				$("#pay_SMScode").tooltip().tooltip("destroy");
				
					if(obj.paymentName_1 == '货币账户支付'){
						obj.paymentMethod = 'hbzhzf';
					}
					else if(obj.paymentName_1 == '互生币账户支付'){
						obj.paymentMethod = 'hsbzf';	
					}

					require(['paySrc/pay'],function(pay){
						pay.showPage(obj);
					});	
						
				});
			
			
			$('#bPay_btn').click(function(){
				
				$("#pay_SMScode").tooltip().tooltip("destroy");
				
				obj.paymentMethod = 'wyzf';
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
		},
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
					},
					selectQuick : {
						required : true,
					}
				},
				messages : {
					pay_yhkh : {
						required : comm.lang('hsbAccount').bankNo,	
						rangelength   : comm.lang('hsbAccount').bankNoLength
					},
					selectQuick : {
						required : comm.lang("hsbAccount").pleaseSelectQuick,
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
		},
		/**
		 * 倒计时
		 */
		showCountdown : function () { 
			window.setTimeout(quickPayFun.countdownSend,1000)
		},
		/**
		 * 倒计时发送
		 */
		countdownSend : function () { 
			quickPayFun.countdown--;
				if (quickPayFun.countdown < 1) {
					$("#getCode_countdown").hide();
					
					$("#getCode_qPay").show();
					$("#getCode_qPay").html("获取验证码");
				} else { 
					$("#getCode_qPay").hide();
					$("#getCode_countdown").show();
					
					$("#getCode_countdown").html("剩余时间&nbsp;(&nbsp" + quickPayFun.countdown + "&nbsp)秒");
					quickPayFun.showCountdown();
			    }
		}
	}	
	return quickPayFun;
});