define(['text!coDeclarationTpl/sptjcx/ckxq/qyxtzcxx.html',
        'coDeclarationDat/sptjcx/ckxq/qyxtzcxx',
		'coDeclarationLan'
		],function(qyxtzcxxTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(qyxtzcxxTpl));
		 	$('#ckxq_xg').css('display','');
		 	$('#ckxq_qx').click(function(){	 		
		 		$('#cx_content_list').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData: function(){
			dataModoule.findDeclareByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data){
					$('#ckxq_view').html(_.template(qyxtzcxxTpl, res.data));
					//获取币种
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
