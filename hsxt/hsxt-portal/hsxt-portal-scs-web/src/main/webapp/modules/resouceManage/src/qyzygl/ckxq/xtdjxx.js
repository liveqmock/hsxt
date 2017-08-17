define(['text!resouceManageTpl/qyzygl/ckxq/xtdjxx.html',
        'resouceManageDat/resouceManage',
		'resouceManageLan'],function(xtdjxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#ckxq_view').html(_.template(xtdjxxTpl, data));
			if(data){
				//获取地区信息
				cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function(resdata){
					$("#placeName").html(resdata);
				});
			}
			//平台信息
			cacheUtil.findCacheSystemInfo(function(sysRes){
				$('#currencyText').html(sysRes.currencyNameCn);
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			var entAllInfo = comm.getCache("resouceManage", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findCompanyAllInfo({companyCustId:$("#resEntCustId").val()}, function(res){
					comm.setCache("resouceManage", "entAllInfo", res.data);
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		}
	}
}); 

 