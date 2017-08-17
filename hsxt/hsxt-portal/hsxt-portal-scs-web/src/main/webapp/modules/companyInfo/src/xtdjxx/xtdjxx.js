define(['text!companyInfoTpl/xtdjxx/xtdjxx.html',
        'companyInfoDat/gsdjxx/gsdjxx',
		'companyInfoLan'],function(xtdjxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(xtdjxxTpl, data));
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
			dataModoule.findEntAllInfo(null, function(res){
				comm.setCache("companyInfo", "entAllInfo", res.data);
				self.initForm(res.data);
			});
		}
	}
}); 

 