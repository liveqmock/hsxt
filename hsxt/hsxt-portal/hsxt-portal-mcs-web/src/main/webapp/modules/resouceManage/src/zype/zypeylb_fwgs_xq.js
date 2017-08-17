define(['text!resouceManageTpl/zype/zypeylb_fwgs_xq.html',
		'text!resouceManageTpl/zype/fwgs_zy_dialog.html',
        'resouceManageDat/zype/fwgszypeyl',
        'resouceManageLan' 
		],function(zypeylb_xqTpl, fwgs_zy_dialogTpl, dataModoule){
	var mcs_zypeylb_fwgs_xq =  {
		showPage: function () {
			$('#busibox').html(_.template(zypeylb_xqTpl));
			//平台信息
			dataModoule.statResDetailOfProvince({sprovinceCode: $("#sprovinceCode").val()}, function (res) {
				res.data.cityNum = (res.data.cityList) ? res.data.cityList.length : 0;
				$('#busibox').html(_.template(zypeylb_xqTpl, res.data));
				$("#provinceText").html(comm.getRegionByCode(null, $("#sprovinceCode").val(), null));
				comm.getEasyBsGrid("detailTable-2", res.data.cityList, mcs_zypeylb_fwgs_xq.detail);
			});
		},
		/**
		 * 查看动作
		 */
		detail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 0) {
				return comm.getCityNameByCode($("#sprovinceCode").val(), record['cityNo']);
			} else if (colIndex == 1) {
				return $("#currencyText").val();
			} else if (colIndex == 2) {
				return $('<a>' + record['totalNum'] + '</a>').click(function (e) {
					if (record['totalNum'] > 0) {
						mcs_zypeylb_fwgs_xq.chaKan(record['cityNo'], null);
					}
				});
			} else if (colIndex == 3) {
				return $('<a>' + record['usedNum'] + '</a>').click(function (e) {
					if (record['usedNum'] > 0) {
						mcs_zypeylb_fwgs_xq.chaKan(record['cityNo'], [1]);
					}
				});
			} else if (colIndex == 4) {
				return $('<a>' + record['usableNum'] + '</a>').click(function (e) {
					if (record['usableNum'] > 0) {
						mcs_zypeylb_fwgs_xq.chaKan(record['cityNo'], [0, 2]);
					}
				});
			} else if (colIndex == 5) {
				return $('<a>' + record['unUseNum'] + '</a>').click(function (e) {
					if (record['unUseNum'] > 0) {
						mcs_zypeylb_fwgs_xq.chaKan(record['cityNo'], [0]);
					}
				});
			} else if (colIndex == 6) {
				return $('<a>' + record['usingNum'] + '</a>').click(function (e) {
					if (record['usingNum'] > 0) {
						mcs_zypeylb_fwgs_xq.chaKan(record['cityNo'], [2]);
					}
				});
			}
		},
		chaKan: function (cityCode, status) {
			$('#fwgs_dialog').html(fwgs_zy_dialogTpl);
			/*弹出框*/
			$("#fwgs_dialog").dialog({
				title: "城市资源状态详情",
				width: "820",
				modal: true,
				buttons: {
					"确定": function () {
						$(this).dialog("destroy");
					}
				}
			});
			var params = {};
			params.scityCode = cityCode;
			params.status = status;
			dataModoule.listResInfoOfCity(params, function (res) {
				comm.getEasyBsGrid("searchTable_fwgs_dialog", res.data);
			});
		}
	};
	return mcs_zypeylb_fwgs_xq;
}); 

 