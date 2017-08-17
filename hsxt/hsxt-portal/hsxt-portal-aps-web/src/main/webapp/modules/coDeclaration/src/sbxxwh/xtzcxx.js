define(['text!coDeclarationTpl/sbxxwh/xtzcxx.html',
        'coDeclarationDat/sbxxwh/xtzcxx',
        'coDeclarationLan'], function(xtzcxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
		 	this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#infobox').html(_.template(xtzcxxTpl));
			/*按钮事件*/
			$('#xtzcxx_back').triggerWith('#glqysbzzdwh');
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findDeclareByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data){
					$('#infobox').html(_.template(xtzcxxTpl, res.data));
					/*按钮事件*/
					$('#xtzcxx_back').triggerWith('#glqysbzzdwh');
					
					//平台信息
					cacheUtil.findCacheSystemInfo(function(sysRes){
						$('#currencyText').html(sysRes.currencyNameCn);
					});
					
					//获取地区信息
					cacheUtil.syncGetRegionByCode(null, res.data.provinceNo, res.data.cityNo, "", function(resdata){
						$("#placeName").html(resdata);
					});
					var custType = $("#custType").val();
					if(custType == 2 || custType == 3){
						$("#viewText").html("启用资源类型");
						$("#viewName").html(comm.getNameByEnumId(res.data.toBuyResRange, comm.lang("common").allToBuyResRangeEnum));
					}else if(custType == 4){
						$("#viewText").html("经营类型");
						$("#viewName").html(comm.getNameByEnumId(res.data.toBusinessType, comm.lang("common").toBusinessTypes));
					}
				}
			});
		},
	}	
});