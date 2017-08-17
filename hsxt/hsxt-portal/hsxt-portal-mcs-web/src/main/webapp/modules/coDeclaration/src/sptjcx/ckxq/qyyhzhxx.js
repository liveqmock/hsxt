define(['text!coDeclarationTpl/sptjcx/ckxq/qyyhzhxx.html',
        'coDeclarationDat/sptjcx/ckxq/qyyhzhxx',
		'coDeclarationLan'],function(qyyhzhxxTpl, dataModoule){
	return {	 	
		showPage : function(){
		 	this.initData();
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(qyyhzhxxTpl));
		 	$('#ckxq_xg').css('display','');
		 	$('#ckxq_qx').click(function(){
		 		$('#cx_content_list').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findBankByApplyId({"applyId": $("#applyId").val()}, function(res){
				$('#ckxq_view').html(_.template(qyyhzhxxTpl, res.data));
				if(res.data.bank){
					if(res.data.bank.isDefault == null){
						$("#isdefaultText").html("");
					}else if(!res.data.bank.isDefault){
						$("#isdefaultText").html("否");
					}else{
						$("#isdefaultText").html("是");
					}
					$("#accountNoText").html(comm.hideCard(res.data.bank.accountNo));
					//获取地区信息
					cacheUtil.syncGetRegionByCode(null, res.data.bank.provinceNo, res.data.bank.cityNo, "", function(resdata){
						$("#placeName").html(resdata);
					});
					//获取开户行
					cacheUtil.findCacheBankAll(function(bankRes){
						$("#bankCodeText").html(comm.getBankNameByCode(res.data.bank.bankCode, bankRes));
					});
				}
			});
		},
	}
}); 
