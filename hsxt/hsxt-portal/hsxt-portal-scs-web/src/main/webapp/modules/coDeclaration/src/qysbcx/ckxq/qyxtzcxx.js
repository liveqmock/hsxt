define(['text!coDeclarationTpl/qysbcx/ckxq/qyxtzcxx.html',
	        'coDeclarationDat/qysbcx/ckxq/qyxtzcxx',
			'coDeclarationLan'
		],function(qyxtzcxxTpl ,dataModoule){
	return {	 	
		showPage : function(){
			this.initData();
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
		initData: function(){
			var params = {"applyId": $("#applyId").val()};
			dataModoule.findDeclareByApplyId(params, function(res){
				if(res.data){
					$('#ckxq_view').html(_.template(qyxtzcxxTpl, res.data));
					//平台信息
					cacheUtil.findCacheSystemInfo(function(sysRes){
						$('#currencyText').html(sysRes.currencyNameCn);
					});
					//获取地区信息
					cacheUtil.syncGetRegionByCode(null, res.data.provinceNo, res.data.cityNo, "", function(resdata){
						$("#placeName").html(resdata);
					});
				}
			});
		}
	}
}); 
