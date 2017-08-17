define(['text!coDeclarationTpl/sbxxwh/yhzhxx.html',
        'coDeclarationDat/sbxxwh/yhzhxx',
		'coDeclarationLan'], function(yhzhxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#infobox').html(_.template(yhzhxxTpl));
			$("#bank-li").hide();
			/*按钮事件*/
			$('#yhzhxx_back').triggerWith('#glqysbzzdwh');
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findBankByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data.bank){
					$("#bank-li").show();
					if(res.data.bank.isDefault){
						$('#isDefault').addClass('default_bank');
						$('#isDefault').removeClass('bank_bg');
					}else{
						$('#isDefault').addClass('bank_bg');
						$('#isDefault').removeClass('default_bank');
					}
					//隐藏卡号
					$("#accountNoText").html(comm.hideCard(res.data.bank.accountNo));
					//平台信息
					cacheUtil.findCacheSystemInfo(function(sysRes){
						$('#currencyName').html(sysRes.currencyNameCn);
					});
					//查找银行名称
					cacheUtil.findCacheBankAll(function(bankRes){
						$("#bankCodeText").html(comm.getBankNameByCode(res.data.bank.bankCode, bankRes));
					});
				}
			});
		},
	}	
});