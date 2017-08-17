define(["text!fckr_accountManagementTpl/MyFastPaymentCard.html"
	], function (MyFastPaymentCardTpl) {
	var mailBind = {
			_dataModule : null,
			show : function(dataModule){
				var self = this;
				_dataModule = dataModule;
				//加载页面
				$("#myhs_zhgl_box").html(MyFastPaymentCardTpl);
				//加载数据
				self.findPayBanks();
				
				$('#btn_addCard').click(function(){
					$('#MyFastPaymentCard_add').html(openQuickPayTpl);
					self.findPayBank();
					self.showTpl($('#MyFastPaymentCard_add'));
					/*$('#fashPaymentCardList').addClass('none');	
					$('#MyFastPaymentCard_add').removeClass('none');*/
					
					$('#zfje_div').addClass('none');
					$('#back_qpay').html('&lt; 返回我的快捷支付卡')
						.click(function(){
							self.showTpl($('#fashPaymentCardList'));	
						});
					$('#MyFastPaymentCard_add').addClass('pay-container-inner bc');
					
					
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
					
					
					/*储畜卡银行列表点击事件*/
					
					var qPay_ul_cxk = $('#qPay_ul_cxk');
							
					/*选择更多银行操作*/			
					qPay_ul_cxk.children('li:last').click(function(){
						
						qPay_ul_cxk.append(moreStorageCardTpl);
						$(".none_card_1").show();
						$(this).hide();
						
						/*收起操作*/	
						qPay_ul_cxk.children('li:last').click(function(){
							$(".none_card_1").hide();
							qPay_ul_cxk.children('li.add').remove().end().children('li.more-select').show();
						});	
						/*end*/
						
					});
					/*end*/
					
					/*信用卡银行列表点击事件*/
					
					var qPay_ul_xyk = $('#qPay_ul_xyk');
							
					/*选择更多银行操作*/			
					qPay_ul_xyk.children('li:last').click(function(){
						
						qPay_ul_xyk.append(moreCreditTpl);
						$(".none_card_2").show();
						$(this).hide();
						
						/*收起操作*/	
						qPay_ul_xyk.children('li:last').click(function(){
							$(".none_card_2").hide();
							qPay_ul_xyk.children('li.add').remove().end().children('li.more-select').show();
						});	
						/*end*/
						
					});
					/*end*/
				});
				
			},
			validateReg : function(){
				var valid=$("#registeredForm").validate({
					rules : {
						accountNo : {
							required : true,
							bankNo : true
						},
						selectQuick : {
							required : true
						}
					},
					messages : {
						accountNo : {
							required : comm.lang("myHsCard")[22043],
							bankNo : comm.lang("myHsCard")[22046]
						},
						selectQuick : {
							required : comm.lang("myHsCard").pleaseSelectQuick
						}
					},
					errorPlacement : function (error, element) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 2000,
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
				return valid.form();
			},
			showTpl : function(tplObj){
				$('#fashPaymentCardList, #MyFastPaymentCard_add, #MyFastPaymentCard_add_2').addClass('none');
				tplObj.removeClass('none');	
			},
			showInputTpl : function(obj){
				var that = this;
				$('#MyFastPaymentCard_add_2').html(_.template(MyFastPaymentCard_add_2Tpl, obj));
				that.showTpl($('#MyFastPaymentCard_add_2'));
				$('#return_card_add').click(function(){
					that.showTpl($('#MyFastPaymentCard_add'));		
				});	
			},
			/**
			 * 加载银行列表
			 */
			findPayBank : function(){
				var self = this;
				cacheUtil.findPayBankAll(null, function(map){
					var creditList = map.CreditCard;//信用卡
					var debitList = map.DebitCard;//储蓄卡
					if(creditList){
						for(var key in creditList){
							self.addBankLi(creditList[key], 2, parseInt(key) > 4);
						}
					}
					if(debitList){
						for(var key in debitList){
							self.addBankLi(debitList[key], 1, parseInt(key) > 4);
						}
					}
				});
			},
			/**
			 * 添加银行卡li并绑定事件
			 * @param bank 银行信息
			 * @param cardType 银行信息1-储蓄卡 2-信用卡
			 * @param isHidden 是否隐藏
			 */
			addBankLi : function(bank, cardType, isHidden){
				var self = this;
				var detailId = "detail_"+cardType+"_"+bank.bankCode;
				var li = $("#exampleCard").html();
				li = li.replace("bankCode", bank.bankCode);
				li = li.replace("bankCode", bank.bankCode);
				li = li.replace("li-id", detailId);
				li = li.replace("title_name", bank.bankName);
				if(isHidden){
					li = li.replace("bank_click", "bank_click none none_card_"+cardType);
				}
				if(cardType == 1){
					$("#qPay_ul_cxk_more").before(li);
					$("#"+detailId).click(function(){
						$('#MyFastPaymentCard_add_2').html(qPayRegisteredTpl);
						self.showTpl($('#MyFastPaymentCard_add_2'));
						
						$('#zfje_2_div').addClass('none');
						$('#cardType').text('储畜卡');
						$('#bankLogo').addClass('bank-' + bank.bankCode);
					
						$('#back_myBankCard_2, #select_other_bank').click(function(){
							self.showTpl($('#MyFastPaymentCard_add'));	
						});
						
						$('#agreeToPay_btn').text('同意开通').click(function(){
							self.saveBank(bank, cardType);
						});
						
						$('#kjzffwxy_btn').click(function(){
							$('#kjzffwxy_dlg').html(kjzffwxyTpl).dialog({
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
				}else{
					$("#qPay_ul_xyk_more").before(li);
					$("#"+detailId).click(function(){
						$('#MyFastPaymentCard_add_2').html(qPayRegisteredTpl);
						self.showTpl($('#MyFastPaymentCard_add_2'));
						
						$('#zfje_2_div').addClass('none');
						$('#cardType').text('信用卡');
						$('#bankLogo').addClass('bank-' + bank.bankCode);
					
						$('#back_myBankCard_2, #select_other_bank').click(function(){
							self.showTpl($('#MyFastPaymentCard_add'));	
						});
						
						$('#agreeToPay_btn').text('同意开通').click(function(){
							self.saveBank(bank, cardType);
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
				}
			},
			/**
			 * 保存银行
			 */
			saveBank : function(bank, cardType){
				var self = this;
				if(!this.validateReg()){
					return;
				}
				var params = {};
				params.bankCode = bank.bankCode;
				params.bankName = bank.bankName;
				params.bankCardNo = $("#accountNo").val();
				params.bankCardType = cardType;
				_dataModule.addPayBank(params, function(res){
					comm.alert({content:comm.lang("myHsCard").bankSubmitSuccess, callOk:function(){
						self.findPayBanks();
						self.showTpl($('#fashPaymentCardList'));
					}});
				});
			},
			/**
			 * 添加银行卡li并绑定事件
			 * @param bank 银行账户
			 */
			addBank : function(bank){
				var self = this;
				var deleteId = "delete_"+bank.accId;
				
				var last4Number = bank.bankCardNo.substr(bank.bankCardNo.length - 4,4);
				
				var bankCardType = bank.bankCardType;
				
				if(bankCardType == '1'){
					bankCardType = '储蓄卡';
				}else if(bankCardType == '2'){
					bankCardType = '信用卡';
				}else if(bankCardType == '3'){
					bankCardType = '存折';
				}
				
				tplBankLi = '<li class="bank_bg pr">'+
//					'<i class="default_bank"></i>'+
					'<div class="bank_row bank_row_margin_1">'+
					'	<span class="f13">尾号：'+last4Number+'</span>'+
					'	<span class="ml5 f13 red">'+bankCardType+'</span>'+
					'</div>'+
					'<div class="bank_row tc"><i class="large-bank-logo large-bank-'+bank.bankCode+'"></i></div>'+
					'<div class="bank_row bank_row_margin_2 tr">'+
					'	<a id="'+deleteId+'" class="card_delete">删除</a>'+
					'</div>'+
				'</li>';
				
				$("#add_bank").before(tplBankLi);
				$("#"+deleteId).click(function(){
					comm.i_confirm(comm.lang("myHsCard").confirmDelPayBank, function(){
						_dataModule.delPayBank({accId:bank.accId}, function(res){
							comm.alert({content:comm.lang("myHsCard")[22000], callOk:function(){
								self.findPayBanks();
								
							}});
						});
					}, 320);
				});
			},
			findPayBanks : function(){
				var self = this;
				$(".bank_bg").remove();//移除所有子元素
				//查询绑定的快捷支付银行
				_dataModule.findPayBanksByCustId(null, function(res){
					var list = res.data.banks;//绑定的快捷支付银行
					var maxNum = res.data.maxNum;//最大绑定数量
					if(list && list.length > 0){
						$('#fashPaymentCardList').removeClass('none');
						if(list.length > (maxNum-1)){
							$("#add_bank").hide();
							$("#left-card-count").html(0);
						}else{
							$("#add_bank").hide();
							$("#left-card-count").html(maxNum-list.length);
						}
						
						//遍历所有的快捷支付卡
						for(var i=0 ; i < list.length ; i++)
						{
							var obj = list[i];
							if(obj)
							{
								self.addBank(obj);
							}
						}
					}else{
						$('#noQuickCard').removeClass('none');
						$("#add_bank").hide();
						$("#left-card-count").html(maxNum);
					}
				});
			}
		};
	return mailBind
});
