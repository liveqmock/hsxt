define(['text!systemBusinessTpl/jnxtsynf/pay/quickPay.html',
		'text!systemBusinessTpl/jnxtsynf/pay/openQuickPay.html',
		'text!systemBusinessTpl/jnxtsynf/pay/moreCredit.html',
		'text!systemBusinessTpl/jnxtsynf/pay/moreStorageCard.html',
		'text!systemBusinessTpl/jnxtsynf/pay/qPayRegistered.html',
		'text!systemBusinessTpl/jnxtsynf/pay/agreement_dialog.html',
		'systemBusinessDat/systemBusiness'
], function(quickPayTpl, openQuickPayTpl, moreCreditTpl,moreStorageCardTpl, qPayRegisteredTpl,agreementTpl,systemBusiness){
return quickPayFun={
		defalutCountdown : 60,		//默认倒计时时间数（秒）
		countdown : 60,				//快捷验证码重新发送时间（秒）
		showPage : function(obj){
			$('#payment_operate').html(_.template(quickPayTpl,obj));
			//加入绑定快捷支付的银行
			quickPayFun.loadBandQuickBank();	
			
			//初始化读秒信息
			quickPayFun.countdown = 0;
			$("#getAnnual_countdown").hide();
			$("#getAnnual_qPay").show();
			$("#getAnnual_qPay").text("获取验证码");
			//确认支付
			$('#comfirmPay_btn').click(function(){
				if(!quickPayFun.validatePWD()){
					return;	
				}
				
				var bindingNo=$('#cardList').attr("optionvalue");
				if(comm.isEmpty(bindingNo)){
					comm.error_alert(comm.lang("systemBusiness").toolManage_selectBandQuickBank);
					return;
				}
				
				//获取token  加密交易密码
				comm.getToken(function(resp){
					
					var tradePwd = comm.tradePwdEncrypt($('#pay_pwd').val(),resp.data);
					
					/*******缴纳年费 start***********/
					var param = comm.getRequestParams();
					var postData = {
							"order.custId" : param.entCustId,
							"order.orderNo" : obj.orderNo,
							"order.payChannel" : 102,
							"order.orderOperator" : param.custName,
							tradePwd : tradePwd,
							randomToken:resp.data,
							bindingNo : bindingNo,
							smsCode:$('#pay_SMScode').val()
					};
					
					systemBusiness.payAnnualFee(postData,function(resp){
						function jumpUrls(){
							$("#xtywbl_jnxtsynf").find("a").trigger("click");
						}
						if(resp){	
							var data = eval('(' + resp.data + ')'); 
							if(data.transStatus == '100'){
								comm.yes_alert(comm.lang("systemBusiness").accountManage_quickPaySucc);
								quickPayFun.countdown=0;
								setTimeout(jumpUrls,2000);
							}else if(data.transStatus == '102'){
								comm.yes_alert(comm.lang("systemBusiness").accountManage_quickPayProcessing);
								setTimeout(jumpUrls,2000);
							}else if(comm.lang("systemBusiness").dhhsb[data.bankRespCode]){
								comm.error_alert(comm.lang("systemBusiness").dhhsb[data.bankRespCode]);
							}else{
								comm.error_alert(comm.lang("systemBusiness").accountManage_quickPayFail);
							}
						}
						
						comm.alert({
							width : 650,
							height : 200,
							imgFlag : true,
							imgClass : 'tips_yes',
							timeClose : 3,
							content : '恭喜，您的订单支付成功！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：'+obj.orderNo+'，系统将在3秒后，自动返回订单详情页面。</div>'	
						});
					});
					/*********** 缴纳年费end ********/
					
				});
			});	
			
			
			//发送短信验证码
			$('#getAnnual_qPay').unbind('click').click(function(){
				var bindingNo = $('#cardList').attr("optionvalue");
				if(comm.isEmpty(bindingNo)){
					comm.error_alert(comm.lang("systemBusiness").toolManage_selectBandQuickBank);
					return;
				}
				var param = {
					callType:'bs',	
					orderNo:obj.orderNo,
					bindingNo:bindingNo
				};
				systemBusiness.sendQuickPaySms(param,function(resp){
					if(resp){
						comm.yes_alert(comm.lang("systemBusiness").toolManage_sendSmsSucc);
						
						//按钮倒计时
						quickPayFun.countdown=quickPayFun.defalutCountdown;	
						quickPayFun.countdownSend();
					}
				});
			});
			
			//添加新的银行卡
			$('#openQuickPay_btn').unbind('click').click(function(){
				$('#openQuickPay_step1').html(openQuickPayTpl);
				quickPayFun.showTpl($('#openQuickPay_step1'));	
				$('#pay_money').text(obj.hbAmount);
				
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
				
				//返回
				$('#back_qpay').click(function(){
					quickPayFun.showTpl($('#quickPayTpl'));	
				});
				
				//储蓄卡
				quickPayFun.depositCard(obj);
				//信用卡
				quickPayFun.creditCard(obj);
				
			});
			
			$('#aPay_btn').click(function(){
					$("#pay_SMScode").tooltip().tooltip("destroy");
					obj.paymentMethod = 'hsbPay';	
					require(['systemBusinessSrc/jnxtsynf/pay/pay'],function(pay){
						pay.showPage(obj);
					});	
						
				});
			
			
			$('#bPay_btn').click(function(){
				$("#pay_SMScode").tooltip().tooltip("destroy");
				obj.paymentMethod = 'eBankPay';
				require(['systemBusinessSrc/jnxtsynf/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
			
		},
		//加载绑定快捷支付银行
		loadBandQuickBank : function(){
			systemBusiness.getBandQuickBank(null,function(resp){
				if(resp){
					var obj = resp.data;
					if(obj.length > 0){
						var banks = new Array();
						for(var i=0;i<obj.length;i++){
							var selected = false;
							if(i == 0){
								selected = true;
							}
							var bank = {
								bank: obj[i].bankCode,
				   				cardType: comm.lang("systemBusiness").toolManage_bankCardType[1],
				   				lastNo:obj[i].bankCardNo.slice(-4),
				   				fullNo:obj[i].signNo,
				   				selected:selected
							};
							banks[i] = bank;
						}
						$('#cardList').selectBank({
							optionHeight:150,
							cardListInfo:banks
						});	
					}	
				}
			});		
		},
		//储蓄卡
		depositCard : function(obj){
			/*储畜卡银行列表点击事件*/
			systemBusiness.getQuickPayBank({cardType:'deposit'},function(resp){
				if(resp){
					var banks = resp.data;
					$('#qPay_ul_cxk').empty();
					var qPay_ul_cxk = $('#qPay_ul_cxk');
					if(banks.length > 0){
						for(var i= 0;i<banks.length;i++){
							var li = $('<li class="bank_click"><i class="bank-logo bank-'+banks[i].bankCode+'" data-name="'+banks[i].bankCode+'"></i></li>');
							if(Number(banks[i].bankCode) <= 5){
								qPay_ul_cxk.append(li);
							}
						}
					}
					var li = $('<li class="more-select pay-text-blue"><div class="bc clearfix w70"><span class="fl">更多银行</span><i class="fr ml5 pay-ui-gray pay-ui-arrow-down"></i></div></li>');
					qPay_ul_cxk.append(li);
					/*选择更多银行操作*/			
					qPay_ul_cxk.children('li:last').click(function(){
						qPay_ul_cxk.append(_.template(moreStorageCardTpl,{banks:banks}));
						$(this).hide();
						/*收起操作*/	
						qPay_ul_cxk.children('li:last').click(function(){
							qPay_ul_cxk.children('li.add').remove().end()
								.children('li.more-select').show();
						});	
					});
					qPay_ul_cxk.on('click','.bank_click', function(){
						var bankCode = $(this).children('i').attr('data-name');
						$('#openQuickPay_step2').html(qPayRegisteredTpl);
						quickPayFun.showTpl($('#openQuickPay_step2'));
						$('#pay_money_2').text(obj.hbAmount);
						$('#cardType').text('储畜卡');
						$('#bankLogo').addClass('bank-' + bankCode);
						var param = {
							orderNo:obj.orderNo,
							bankCardType:1,
							bankId:bankCode,
							callType:'bs'
						};
						quickPayFun.quickPayProtocol(param);
					});
				}
			});
		},
		//信用卡
		creditCard : function(obj){
			/*信用卡银行列表点击事件*/
			systemBusiness.getQuickPayBank({cardType:'credit'},function(resp){
				if(resp){
					$('#qPay_ul_xyk').empty();
					var qPay_ul_xyk = $('#qPay_ul_xyk');
					var banks = resp.data;
					if(banks.length > 0){
						for(var i= 0;i<banks.length;i++){
							var li = $('<li class="bank_click"><i class="bank-logo bank-'+banks[i].bankCode+'" data-name="'+banks[i].bankCode+'"></i></li>');
							if(Number(banks[i].bankCode) <= 5){
								qPay_ul_xyk.append(li);
							}
						}
					}
					var li = $('<li class="more-select pay-text-blue"><div class="bc clearfix w70"><span class="fl">更多银行</span><i class="fr ml5 pay-ui-gray pay-ui-arrow-down"></i></div></li>');
					qPay_ul_xyk.append(li);
					/*选择更多银行操作*/			
					qPay_ul_xyk.children('li:last').click(function(){
						qPay_ul_xyk.append(_.template(moreCreditTpl,{banks:banks}));
						$(this).hide();
						/*收起操作*/	
						qPay_ul_xyk.children('li:last').click(function(){
							qPay_ul_xyk.children('li.add').remove().end()
								.children('li.more-select').show();
						});	
					});
					qPay_ul_xyk.on('click','.bank_click', function(){
						var bankCode = $(this).children('i').attr('data-name');
						$('#openQuickPay_step2').html(qPayRegisteredTpl,{banks:banks});
						quickPayFun.showTpl($('#openQuickPay_step2'));
						$('#pay_money_2').text(obj.hbAmount);
						$('#cardType').text('信用卡');
						$('#bankLogo').addClass('bank-' + bankCode);
						var param = {
								orderNo:obj.orderNo,
								bankCardType:2,
								bankId:bankCode,
								cashAmount:obj.hbAmount,
								callType:'bs'
						};
						quickPayFun.quickPayProtocol(param);
					});
				}
			});
		},
		//快捷支付协议
		quickPayProtocol : function(param){
			$('#back_myBankCard_2, #select_other_bank').click(function(){
				$("#selectQuick").tooltip().tooltip("destroy");
				$("#pay_yhkh").tooltip().tooltip("destroy");
				quickPayFun.showTpl($('#openQuickPay_step1'));	
			});
			$('#agreeToPay_btn').click(function(){
				if(!quickPayFun.validateReg()){
					return;
				}
				param.bankCardNo=$('#pay_yhkh').val();
				systemBusiness.openQuickPay(param,function(resp){
					if(resp){
						window.open(resp.data);
						var msg ='恭喜，您的订单已提交成功！金额为：' + param.hbAmount + '您的订单号为：'+param.orderNo+'。';
						$("#i_content").html(msg);
						$("#i_content").dialog({
			                title:"快捷支付申购工具",
			                width:"700",
			                modal:true,
			                buttons:{ 
			                	"支付成功":function(){
			                        $(this).dialog("destroy");
			                        $("#subNav_3_07").trigger('click');
			                    },
			                    "支付失败":function(){
			                        $(this).dialog("destroy");
			                        $("#subNav_3_07").trigger('click');
			                    }
			                }
			           });
					}
				});
			});
			$('#kjzffwxy_btn').click(function(){
				$("#selectQuick").tooltip().tooltip("destroy");
				$("#pay_yhkh").tooltip().tooltip("destroy");
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
		},
		validatePWD : function(){
			return comm.valid({
				formID : '#pwdForm',
				rules : {
					pay_SMScode: {
						required : true		
					}	
				},
				messages : {
					pay_SMScode : {
						required : comm.lang("accountManage").smsCode
					}
				}
			});	
		},
		validateReg : function(){
			return comm.valid({
				formID : '#registeredForm',
				rules : {
					selectQuick : {
						required : true
					},
					pay_yhkh : {
						required : true	,
						bankNo	 : true
					}	
				},
				messages : {
					selectQuick : {
						required : comm.lang("accountManage").pleaseSelectQuick	
					},
					pay_yhkh : {
						required : comm.lang("accountManage").bankNo	,
						bankNo   : comm.lang("accountManage").bankNoLength
					}
				}
			});	
		},
		showTpl : function(tplObj){
			$('#quickPayTpl, #openQuickPay_step1, #openQuickPay_step2').addClass('none');
			tplObj.removeClass('none');
		},
		/**
		 * 倒计时发送
		 */
		countdownSend : function () { 
			if (quickPayFun.countdown < 1) {
				$("#getAnnual_countdown").hide();
				
				$("#getAnnual_qPay").show();
				$("#getAnnual_qPay").text("获取验证码");
				quickPayFun.countdown=quickPayFun.defalutCountdown;
			} else { 
				$("#getAnnual_qPay").hide();
				
				$("#getAnnual_countdown").show();
				$("#getAnnual_countdown").html("剩余时间&nbsp;(&nbsp" + quickPayFun.countdown + "&nbsp)");
				quickPayFun.countdown--;
				window.setTimeout(function() { quickPayFun.countdownSend(); },1000);
			}
		}
	}	
});