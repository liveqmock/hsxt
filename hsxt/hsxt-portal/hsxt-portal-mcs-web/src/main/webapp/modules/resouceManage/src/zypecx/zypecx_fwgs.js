define(['text!resouceManageTpl/zypecx/zypecx_fwgs.html',
		'text!resouceManageTpl/zypecx/fwgs_dialog.html',
        'resouceManageDat/zypecx/zypecx',
        'resouceManageLan' 
		],function(zypecxTpl, fwgs_dialogTpl, dataModoule){
	var mcs_zypecx_fwgs = {
		showPage: function () {
			mcs_zypecx_fwgs.initForm();
		},
		/**
		 * 初始化表单
		 */
		initForm: function () {
			$('#busibox').html(_.template(zypecxTpl));
			//初始化省份城市
			comm.initProvSelect('#provinceNo', {}, 100, null);
			comm.initProvSelect('#cityNo', {}, 100, null);

			//地区缓存
			cacheUtil.findProvCity();

			//获取存在配额的省/州/直辖市
			dataModoule.statResDetailOfManager(null, function (rsp) {
				if (rsp.data != null && rsp.data.provinceList != null && rsp.data.provinceList.length > 0) {
					var province = rsp.data;
					var provinceArray = [];
					if (province != null && province.provinceList != null) {
						$.each(province.provinceList, function (k, v) {
							provinceArray.push({provinceNameCn: comm.getProvNameByCode(v.provinceNo), provinceNo: v.provinceNo});
						});
					}

					//初始化省/州/直辖市
					comm.initProvSelect('#provinceNo', provinceArray, 100).change(function (e) {
						var cityArray = [{cityNameCn: comm.lang("resouceManage").select_pompt, cityNo: ''}];
						var cityList = comm.getCityByproCityCode($(this).attr('optionValue'));
						if (cityList != null && cityList != undefined) {
							$.each(cityList, function (k, v) {
								cityArray.push({cityNameCn: v, cityNo: k});
							});
						}
						comm.initCitySelect('#cityNo', cityArray, 100).selectListIndex(0);
					});
				}
			});

			$('#queryBtn').click(function () {
				mcs_zypecx_fwgs.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {};
			params.sprovinceCode = $("#provinceNo").attr('optionValue');
			params.scityCode = $("#cityNo").attr('optionValue');
			dataModoule.statResDetailOfProvince(params, function (res) {
				//res.data.cityNum = (res.data.cityList)?res.data.cityList.length:0;
				$("#provinceText").html(comm.getProvNameByCode($("#provinceNo").attr('optionValue')));
				$("#cityNum").html(comm.removeNull(res.data.cityNum));
				$("#sResNum").html(comm.removeNull(res.data.sResNum));
				$("#useSResNum").html(comm.removeNull(res.data.useSResNum));
				comm.getEasyBsGrid("detailTable", res.data.cityList, mcs_zypecx_fwgs.detail);
			});
		},
		/**
		 * 查看动作
		 */
		detail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 0) {
				return comm.getCityNameByCode($("#provinceNo").attr('optionValue'), record['cityNo']);
			} else if (colIndex == 1) {
				return $("#currencyText").val();
			} else if (colIndex == 2) {
				return $('<a>' + record['totalNum'] + '</a>').click(function (e) {
					if (record['totalNum'] > 0) {
						mcs_zypecx_fwgs.chaKan(record['cityNo'], null);
					}
				});
			} else if (colIndex == 3) {
				return $('<a>' + record['usedNum'] + '</a>').click(function (e) {
					if (record['usedNum'] > 0) {
						mcs_zypecx_fwgs.chaKan(record['cityNo'], [1]);
					}
				});
			} else if (colIndex == 4) {
				return $('<a>' + record['usableNum'] + '</a>').click(function (e) {
					if (record['usableNum'] > 0) {
						mcs_zypecx_fwgs.chaKan(record['cityNo'], [0, 2]);
					}
				});
			} else if (colIndex == 5) {
				return $('<a>' + record['unUseNum'] + '</a>').click(function (e) {
					if (record['unUseNum'] > 0) {
						mcs_zypecx_fwgs.chaKan(record['cityNo'], [0]);
					}
				});
			} else if (colIndex == 6) {
				return $('<a>' + record['usingNum'] + '</a>').click(function (e) {
					if (record['usingNum'] > 0) {
						mcs_zypecx_fwgs.chaKan(record['cityNo'], [2]);
					}
				});
			}
		},
		chaKan: function (cityCode, status) {
			$('#fwgs_dialog').html(fwgs_dialogTpl);
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
	return mcs_zypecx_fwgs;
}); 

 