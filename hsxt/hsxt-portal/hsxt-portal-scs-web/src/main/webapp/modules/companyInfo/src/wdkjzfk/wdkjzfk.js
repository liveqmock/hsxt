define(['text!companyInfoTpl/wdkjzfk/wdkjzfk.html',
		'text!companyInfoTpl/wdkjzfk/pay/openQuickPay.html',
		'text!companyInfoTpl/wdkjzfk/pay/qPayRegistered.html',
		'text!companyInfoTpl/wdkjzfk/pay/moreCredit.html',
		'text!companyInfoTpl/wdkjzfk/pay/moreStorageCard.html',
		'text!companyInfoTpl/wdkjzfk/pay/kjzffwxy.html',
        'companyInfoDat/wdkjzfk/wdkjzfk',
        'companyInfoLan'
		], function(wdkjzfkTpl, openQuickPayTpl, qPayRegisteredTpl, moreCreditTpl, moreStorageCardTpl, kjzffwxyTpl, dataModoule){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(wdkjzfkTpl);
			//去掉添加快捷卡操作 只能在支付的界面开通并支付添加，业务调整20160308 peter.li
			$("#add_bank").hide();
			//查询绑定的快捷支付银行
			dataModoule.findPayBanksByCustId(null, function(res){
				var list = res.data.banks;//绑定的快捷支付银行
				var maxNum = res.data.maxNum;//最大绑定数量
				if(list && list.length > 0){
					$('#wdkjzfkList').removeClass('none');
					if(list.length > (maxNum-1)){
						$("#add_bank").hide();
						$("#left-card-count").html(0);
					}else{
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
					$('#tips').removeClass('none');
					$("#left-card-count").html(maxNum);
				}
			});
			
			$('#btn_addCard').click(function(){
				$('#wdkjzfk_tj').html(openQuickPayTpl);
				self.findPayBank();
				self.showTpl($('#wdkjzfk_tj'));
				comm.liActive_add($('#tjkjzfk'));
				
				$('#zfje_div').addClass('none');
				
				$('#back_qpay').html('&lt; 返回我的快捷支付卡')
					.click(function(){
						self.showTpl($('#wdkjzfkList'));
						comm.liActive($('#wdkjzfk'), '#tjkjzfk');		
					});
				
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
						qPay_ul_cxk.children('li.add').remove().end().children('li.more-select').show();
						$(".none_card_1").hide();
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
			});
		},
		validateReg : function(){
			return comm.valid({
				formID : '#registeredForm',
				rules : {
					accountNo : {
						required : true,
						bankNo : true,
					},
					selectQuick : {
						required : true,
					}
				},
				messages : {
					accountNo : {
						required : comm.lang("companyInfo")[32516],
						bankNo : comm.lang("companyInfo")[32517],
					},
					selectQuick : {
						required : comm.lang("companyInfo").pleaseSelectQuick,
					}
				},
			});	
		},
		showTpl : function(tplObj){
			$('#wdkjzfkList, #wdkjzfk_tj, #wdkjzfk_tj_2').addClass('none');
			tplObj.removeClass('none');
		},
		showInputTpl : function(obj){
			var that = this;
			$('#wdkjzfk_tj_2').html(_.template(wdkjzfk_tj_2Tpl, obj));
			that.showTpl($('#wdkjzfk_tj_2'));
			$('#return_card_add').click(function(){
				that.showTpl($('#wdkjzfk_tj'));		
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
					$('#wdkjzfk_tj_2').html(qPayRegisteredTpl);
					self.showTpl($('#wdkjzfk_tj_2'));
					
					$('#zfje_2_div').addClass('none');
					$('#cardType').text('储畜卡');
					$('#bankLogo').addClass('bank-' + bank.bankCode);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						$("#accountNo").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
						self.showTpl($('#wdkjzfk_tj'));
					});
					
					$('#agreeToPay_btn').text('同意开通').click(function(){
						self.saveBank(bank, cardType);
					});
					
					$('#kjzffwxy_btn').click(function(){
						$("#accountNo").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
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
					$('#wdkjzfk_tj_2').html(qPayRegisteredTpl);
					self.showTpl($('#wdkjzfk_tj_2'));
					
					$('#zfje_2_div').addClass('none');
					$('#cardType').text('信用卡');
					$('#bankLogo').addClass('bank-' + bank.bankCode);
				
					$('#back_myBankCard_2, #select_other_bank').click(function(){
						$("#accountNo").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
						self.showTpl($('#wdkjzfk_tj'));	
					});
					
					$('#agreeToPay_btn').text('同意开通').click(function(){
						self.saveBank(bank, cardType);
					});
					
					$('#kjzffwxy_btn').click(function(){
						$("#accountNo").tooltip().tooltip("destroy");
						$("#selectQuick").tooltip().tooltip("destroy");
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
			if(!this.validateReg()){
				return;
			}
			var params = {};
			params.bankCode = bank.bankCode;
			params.bankName = bank.bankName;
			params.bankCardNo = $("#accountNo").val();
			params.bankCardType = cardType;
			dataModoule.addPayBank(params, function(res){
				comm.alert({content:comm.lang("companyInfo").bankSubmitSuccess, callOk:function(){
					$("#wdkjzfk").click();
				}});
			});
		},
		/**
		 * 添加银行卡li并绑定事件
		 * @param bank 银行账户
		 */
		addBank : function(bank){
			var deleteId = "delete_"+bank.accId;
			var li = $("#li-exampleCard").html();
			li = li.replace("bankName", comm.removeNull(bank.bankName));
			li = li.replace("bankName", comm.removeNull(bank.bankName));
			li = li.replace("cardType", comm.removeNull(comm.getNameByEnumId(bank.bankCardType, comm.lang("companyInfo").bankCardTypeEnum)));
			li = li.replace("cardNo", comm.showLastCard(bank.bankCardNo));
			li = li.replace("option", '<a id="'+deleteId+'" class="card_delete">删除</a>');
			li = li.replace("bankCode", comm.removeNull(bank.bankCode));
			
			$("#add_bank").before(li);
			$("#"+deleteId).click(function(){
				comm.i_confirm(comm.lang("companyInfo").confirmDelPayBank, function(){
					dataModoule.delPayBank({accId:bank.accId}, function(res){
						comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
							$('#wdkjzfk').click();
						}});
					});
				}, 320);
			});
		}
	}
});