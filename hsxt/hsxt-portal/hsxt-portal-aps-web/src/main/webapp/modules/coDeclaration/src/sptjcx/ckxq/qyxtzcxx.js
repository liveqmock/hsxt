define(['text!coDeclarationTpl/sptjcx/ckxq/qyxtzcxx.html',
        'coDeclarationDat/sptjcx/ckxq/qyxtzcxx',
		'coDeclarationLan'],function(qyxtzcxxTpl, dataModoule){
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
		},
		/**
		 * 初始化数据
		 */
		initData: function(){
			dataModoule.findDeclareByApplyId({"applyId": $("#applyId").val()}, function(res){
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
					//处理经营类型
					if($("#custType").val() == "4"){
						$("#business_type_td").show();
						$("#business_type_value").html(comm.getNameByEnumId(res.data.toBusinessType, comm.lang("common").toBusinessTypes));
					}
				}
			});
		}
	}
}); 
