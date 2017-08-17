define(['text!systemBusinessTpl/toolManage/pay/quickPay.html',
		'text!systemBusinessTpl/toolManage/pay/openQuickPay.html',
		'text!systemBusinessTpl/toolManage/pay/moreCredit.html',
		'text!systemBusinessTpl/toolManage/pay/moreStorageCard.html',
		'text!systemBusinessTpl/toolManage/pay/qPayRegistered.html',
		'text!systemBusinessTpl/toolManage/pay/agreement_dialog.html',
		'systemBusinessDat/systemBusiness'
], function(quickPayTpl, openQuickPayTpl, moreCreditTpl,moreStorageCardTpl, qPayRegisteredTpl,agreementTpl,systemBusinessAjax){
	var self = {
		showPage : function(obj){
			$('#payment_operate').html(_.template(quickPayTpl,obj));
			//加入绑定快捷支付的银行
			self.loadBandQuickBank();
			
			//快捷支付提交
			$('#comfirmPay_btn').click(function(){
				if(!self.validatePWD()){
					return;	
				}
				var bindingNo=$('#cardList').attr("optionvalue");
				if(comm.isEmpty(bindingNo)){
					comm.error_alert(comm.lang("systemBusiness").toolManage_selectBandQuickBank);
					return;
				}
				var param = {
						orderNo:obj.orderNo,
						payChannel:102,
						bindingNo:bindingNo,
						smsCode:$('#pay_SMScode').val()
					};
				systemBusinessAjax.toolOrderPayment(param,function(resp){
					if(resp){	
						var data = eval('(' + resp.data + ')'); 
						if(data.transStatus == '100'){
							comm.yes_alert(comm.lang("systemBusiness").toolManage_quickPaySucc);
						}else if(data.transStatus == '102'){
							comm.yes_alert(comm.lang("systemBusiness").toolManage_quickPayProcessing);
						}else{
							comm.error_alert(comm.lang("systemBusiness").toolManage_quickPayFail);
						}
						$("#gjsgcx").trigger('click');
					}
				});
			});	
			
			//发送短信验证码
			$('#getCode_qPay').unbind('click').click(function(){
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
				systemBusinessAjax.sendQuickPaySms(param,function(resp){
					if(resp){
						comm.yes_alert(comm.lang("systemBusiness").toolManage_sendSmsSucc);
					}
				});
			});
			
			//开通快捷支付
			$('#openQuickPay_btn').unbind('click').click(function(){
				$('#pay_SMScode').tooltip().tooltip("destroy");
				$('#openQuickPay_step1').html(openQuickPayTpl);
				self.showTpl($('#openQuickPay_step1'));	
				$('#pay_money').text(obj.cashAmount);
				
				$('#pay_card_tab').children('h1').unbind('click').click(function(){
					
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
					self.showTpl($('#quickPayTpl'));	
				});
				
				//储蓄卡
				self.depositCard(obj);
				//信用卡
				self.creditCard(obj);
				
			});
			
			//切换网银支付
			$('#eBankPay_btn').click(function(){
				$("#pay_SMScode").tooltip().tooltip("destroy");
				obj.paymentMethod = 'eBankPay';
				obj.isFormat = '';
				require(['systemBusinessSrc/toolManage/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			//切换互生币支付
			$('#hsbPay_btn').click(function(){
				$("#pay_SMScode").tooltip().tooltip("destroy");
				obj.paymentMethod = 'hsbPay';
				obj.isFormat = '';
				require(['systemBusinessSrc/toolManage/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
		},
		//加载绑定快捷支付银行
		loadBandQuickBank : function(){
			systemBusinessAjax.getBandQuickBank(null,function(resp){
				if(resp){
					var obj = resp.data;
					if(obj.length > 0){
						var banks = new Array();
						for(var i=0;i<obj.length;i++){
							var bank = {
								bank: obj[i].bankCode,
				   				cardType: comm.lang("systemBusiness").toolManage_bankCardType[1],
				   				lastNo:obj[i].bankCardNo.slice(-4),
				   				fullNo:obj[i].signNo,
				   				selected:(i==0)?true:false
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
			systemBusinessAjax.getQuickPayBank({cardType:'deposit'},function(resp){
				if(resp){
					var banks = resp.data;
					var qPay_ul_cxk = $('#qPay_ul_cxk');
					if(comm.isNotEmpty(banks)&&banks.length > 0){
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
						self.showTpl($('#openQuickPay_step2'));
						$('#pay_money_2').text(obj.cashAmount);
						$('#cardType').text('储畜卡');
						$('#bankLogo').addClass('bank-' + bankCode);
						var param = {
							orderNo:obj.orderNo,
							orderType:obj.orderType,
							bankCardType:1,
							bankId:bankCode,
							cashAmount:obj.cashAmount,
							callType:'bs'
						};
						self.quickPayProtocol(param);
					});
				}
			});
		},
		//信用卡
		creditCard : function(obj){
			/*信用卡银行列表点击事件*/
			systemBusinessAjax.getQuickPayBank({cardType:'credit'},function(resp){
				if(resp){
					var qPay_ul_xyk = $('#qPay_ul_xyk');
					var banks = resp.data;
					if(comm.isNotEmpty(banks)&&banks.length > 0){
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
						self.showTpl($('#openQuickPay_step2'));
						$('#pay_money_2').text(obj.cashAmount);
						$('#cardType').text('信用卡');
						$('#bankLogo').addClass('bank-' + bankCode);
						var param = {
								orderNo:obj.orderNo,
								orderType:obj.orderType,
								bankCardType:2,
								bankId:bankCode,
								cashAmount:obj.cashAmount,
								callType:'bs'
						};
						self.quickPayProtocol(param);
					});
				}
			});
		},
		//快捷支付协议
		quickPayProtocol : function(param){
			$('#back_myBankCard_2, #select_other_bank').click(function(){
				$('#pay_yhkh').tooltip().tooltip("destroy");
				$('#selectQuick').tooltip().tooltip("destroy");
				self.showTpl($('#openQuickPay_step1'));	
			});
			$('#agreeToPay_btn').click(function(){
				if(!self.validateReg()){
					return;
				}
				param.bankCardNo=$('#pay_yhkh').val();
				systemBusinessAjax.openQuickPay(param,function(resp){
					if(resp){
						window.open(resp.data);
						var msg ='恭喜，您的订单已提交成功！金额为：' + param.cashAmount + '您的订单号为：'+param.orderNo+'。';
						$("#i_content").html(msg);
						$("#i_content").dialog({
			                title:comm.lang("systemBusiness").toolManage_payChannel[102]+comm.lang("systemBusiness").toolManage_orderType[param.orderType],
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
			$('#kjzffwxy_btn').click(function(){
				$('#pay_yhkh').tooltip().tooltip("destroy");
				$('#selectQuick').tooltip().tooltip("destroy");
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
						required : comm.lang("systemBusiness").toolManage_smsCode
					}
				}
			});	
		},
		validateReg : function(){
			return comm.valid({
				formID : '#registeredForm',
				rules : {
					pay_yhkh : {
						required : true	,
						bankNo : true
					},
					selectQuick : {
						required : true,
					}
				},
				messages : {
					pay_yhkh : {
						required : comm.lang("systemBusiness").toolManage_bankNo,
						bankNo : comm.lang("systemBusiness").toolManage_bankNoLength
					},
					selectQuick : {
						required : comm.lang("systemBusiness").pleaseSelectQuick,
					}
				}
			});	
		},
		//显示页面
		showTpl : function(tplObj){
			$('#quickPayTpl, #openQuickPay_step1, #openQuickPay_step2').addClass('none');
			tplObj.removeClass('none');
		}				
	};
	return self;
});