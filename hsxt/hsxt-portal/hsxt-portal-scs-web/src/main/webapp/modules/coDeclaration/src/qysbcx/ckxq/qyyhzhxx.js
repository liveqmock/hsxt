define(['text!coDeclarationTpl/qysbcx/ckxq/qyyhzhxx.html',
			'coDeclarationDat/qysbfk/ckxq/qyyhzhxx',
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
			$('#ckxq_xg').css('display','none');
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		if ( $('#qysbcx_tgqysbtj > a').hasClass('active') ){
		 			$('#tgqysbtj_cx').removeClass('none');
		 			$('#cx_content_detail').addClass('none');	
		 			return ;
		 		}
		 		if ( $('#qysbcx_cyqysbtj > a').hasClass('active') ){
		 			$('#cyqysbtj_cx').removeClass('none');
		 			$('#cx_content_detail').addClass('none');	
		 			return ;
		 		}
		 		if ( $('#qysbcx_fwgssbtj > a').hasClass('active') ){
		 			$('#fwgssbtj_cx').removeClass('none');
		 			$('#cx_content_detail').addClass('none');	
		 			return ;
		 		}
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {"applyId": $("#applyId").val()};
			dataModoule.findBankByApplyId(params, function(res){
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
					//获取银行名称
					cacheUtil.findCacheBankAll(function(bankRes){
						$("#bankCodeText").html(comm.getBankNameByCode(res.data.bank.bankCode, bankRes));
					});
				}
			});
		},
	}
}); 
