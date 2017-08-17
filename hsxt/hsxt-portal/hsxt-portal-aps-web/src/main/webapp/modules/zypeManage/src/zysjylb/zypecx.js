define(['text!zypeManageTpl/zysjylb/zypecx.html',
		'text!zypeManageTpl/zysjylb/zysjylb_xq_dialog.html',
		"zypeManageDat/zypeManage",
		"zypeManageSrc/zype_comm",
		"zypeManageLan"
		], function(zypecxTpl, zysjylb_xq_dialogTpl,zypeManage,zypecomm){
	var aps_zypecx = {
		showPage: function () {
			$('#busibox').html(_.template(zypecxTpl));

			//加载地区缓存
			cacheUtil.findProvCity();

			//初始化地区控件
			comm.initProvSelect('#area', {}, 70, null);
			comm.initCitySelect('#city', {}, 70, null);

			//地区级联
			zypecomm.initArea(function () {
				//分页查询
				aps_zypecx.pageQuery();
				zypecomm.initCity($("#area").attr("optionvalue"));
			});

			/*下拉列表*/
			$("#area").change(function (e) {
				zypecomm.initCity($("#area").attr("optionvalue"));
			});

			//查询事件
			$("#btnQuery").click(function () {
				aps_zypecx.pageQuery();
			});

		},
		//分页查询
		pageQuery: function () {
			var isAutoLoad = true; //是否自动加载数据

			//查询参数
			var param = {"provinceNo": $("#area").attr("optionvalue"), "cityNo": $("#city").attr("optionvalue")};
			zypeManage.resQuotaQuery(param, function (rsp) {
				$("#tdProvinceNo").text($("#area").val());
				$("#sCityCount").text(rsp.data.cityNum);
				$("#sSResNum").text(rsp.data.sResNum);
				$("#sUseSResNum").text(rsp.data.useSResNum);

				//列表数据
				var qsoplJson = rsp.data.cityList;
				if (qsoplJson == null || qsoplJson.length == 0) {
					isAutoLoad = false;
				}

				//分页控件赋值
				var gridObj = $.fn.bsgrid.init('searchTable_xq', {
					autoLoad: isAutoLoad,
					pageSizeSelect: true,
					pageSize: 10,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					localData: qsoplJson,
					operate: {
						detail: function (record, rowIndex, colIndex, options) {
							//获取城市名
							if (colIndex == 0) {
								return comm.getCityNameByCode(rsp.data.provinceNo, record['cityNo']);
							}
							return $('<a>' + gridObj.getCellRecordValue(rowIndex, colIndex) + '</a>').click(function (e) {
								if (gridObj.getCellRecordValue(rowIndex, colIndex) != 0) {
									aps_zypecx.ck_dialog(record, colIndex);
								}
							});
						}
					}
				});
			});
		}
		,
		ck_dialog: function (obj, colIndex) {
			$('#dialogBox').html(_.template(zysjylb_xq_dialogTpl));

			var status = [];
			var title = null, table = null;
			switch (colIndex) {
				case 2:
					status = null;
					title = '城市资源配额';
					table = 'searchTable_dialog';
					$('#searchTable_dialog').removeClass('tabNone');
					$('#searchTable_dialog2').addClass('tabNone');
					break;
				case 3:
					status = [1];
					title = '已使用资源配额';
					table = 'searchTable_dialog';
					$('#searchTable_dialog').removeClass('tabNone');
					$('#searchTable_dialog2').addClass('tabNone');
					break;
				case 4:
					status = [0];
					title = '未分配资源';
					table = 'searchTable_dialog2';
					$('#searchTable_dialog').addClass('tabNone');
					$('#searchTable_dialog2').removeClass('tabNone');
					break;
				case 5:
					status = [2];
					title = '拟分配资源';
					table = 'searchTable_dialog';
					$('#searchTable_dialog').removeClass('tabNone');
					$('#searchTable_dialog2').addClass('tabNone');
					break;
			}

			zypeManage.cityResStatusDetail({"cityNo": obj.cityNo, "status": status}, function (rsp) {
				var isAutoLoad = true; //自动加载

				//判断数据存在
				if (rsp == null || rsp.data == null || rsp.data.length == 0) {
					isAutoLoad = false;
				}

				$.fn.bsgrid.init(table, {
					autoLoad: isAutoLoad,
					pageSizeSelect: true,
					pageSize: 10,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					localData: rsp.data

				});
			});

			/*弹出框*/
			$("#dialogBox").dialog({
				title: title,
				width: "820",
				modal: true,
				buttons: {
					"确定": function () {
						$(this).dialog("destroy");
					}
				}
			});
			/*end*/
		}
	};
	return aps_zypecx;
});