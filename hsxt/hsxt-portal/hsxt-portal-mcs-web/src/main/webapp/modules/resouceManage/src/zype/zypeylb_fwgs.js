define(['text!resouceManageTpl/zype/zypeylb_fwgs.html',
        'resouceManageDat/zype/fwgszypeyl',
        'resouceManageLan'],function(zypeylbTpl, dataModoule){
	var mcs_zypeylb_fwgs = {
		showPage: function () {
			$('#busibox').html(_.template(zypeylbTpl));
			//地区缓存
			cacheUtil.findProvCity();
			//平台信息
			cacheUtil.findCacheSystemInfo(function (sysRes) {
				dataModoule.statResDetailOfManager(null, function (res) {
					$('#busibox').html(_.template(zypeylbTpl, res.data));
					$('#currencyText').val(sysRes.currencyNameCn);
					$.fn.bsgrid.lockScreen;
					comm.getEasyBsGrid("tableDetailfwgs", res.data.provinceList, mcs_zypeylb_fwgs.detail);
					$.fn.bsgrid.unlockScreen;
				});
			})
		},
		/**
		 * 查看动作
		 */
		detail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 0) {
				return comm.getRegionByCode(null, record['provinceNo'], null);
			} else if (colIndex == 1) {
				return $("#currencyText").val();
			} else if (colIndex == 4) {
				return $('<a>查看</a>').click(function (e) {
					$("#sprovinceCode").val(record['provinceNo']);
					$('#xtzy_zype_fwgs_xq').click();
				});
			}
		}
	};
	return mcs_zypeylb_fwgs;
}); 

 