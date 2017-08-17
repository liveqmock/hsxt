define(["text!zypeManageTpl/zysjylb/zysjylb_01.html",
		"text!zypeManageTpl/zysjylb/zysjylb_xq.html",
		"text!zypeManageTpl/zysjylb/zysjylb_xq_dialog.html",
		"zypeManageDat/zypeManage",
		"zypeManageSrc/zype_comm",
		"zypeManageLan"
		],function(zysjylb_01Tpl, zysjylb_xqTpl, zysjylb_xq_dialogTpl,zypeManage,zypeComm){
	var aps_zysjylb_01 = {
		currencyName: null,//货币名称
		gridObj:null,
		showPage: function () {
			var isAutoLoad = true; //是否自动加载数据
			//加载地区缓存
			cacheUtil.findProvCity();
			var $mEntResNo = $("#uEnt li a[class='active']").text();
			//货币名称
			zypeComm.getCurrencyName(function (data) {
				aps_zysjylb_01.currencyName = data;
			});
			//数据资源一览表
			zypeManage.listTable({"mEntResNo": $mEntResNo}, function (rsp) {
				//加载管理公司html以及数据
				$('#infobox').html(_.template(zysjylb_01Tpl, rsp.data));
				//列表数据
				var qsoplJson = rsp.data.provinceList;
				if (qsoplJson == null || qsoplJson.length == 0) {
					isAutoLoad = false;
				}
				//分页组件赋值
				aps_zysjylb_01.gridObj = $.fn.bsgrid.init('searchTable', {
					autoLoad: isAutoLoad,
					pageSizeSelect: true,
					pageSize: 10,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					localData: qsoplJson,
					operate: {
						detail: function (record, rowIndex, colIndex, options) {
							//获取省份
							if (colIndex == 0) {
								return comm.getProvNameByCode(record['provinceNo']);
							}
							if (colIndex == 1) {
								return aps_zysjylb_01.currencyName;
							}
							return $("<a>" + comm.lang("zypeManage").quota_query_title + "</a>").click(function (e) {
								aps_zysjylb_01.chaKan(record);
							});
						}
					}
				});
			});
			/*end*/
		},

		chaKan: function (obj) {
			var isAutoLoad = true; //是否自动加载数据
			var $liActiveId = $("#uEnt li a[class='active']").parent().attr("id");

			//获取二级区域城市配额资源
			zypeManage.resQuotaQuery({"provinceNo": obj.provinceNo}, function (rsp) {

				//增加一级区域代码
				rsp.data.provinceNo = obj.provinceNo;
				rsp.data.provinceName = comm.getProvNameByCode(obj.provinceNo);
				rsp.data.cityCount = rsp.data.cityList.length;

				//html、数据输出
				$('#infobox').html(_.template(zysjylb_xqTpl, rsp.data));
				comm.liActive_add($('#xq'));

				//列表数据
				var qsoplJson = rsp.data.cityList;
				if (qsoplJson == null || qsoplJson.length == 0) {
					isAutoLoad = false;
				}

				//分页控件赋值				
				var gridObj_01 = $.fn.bsgrid.init('searchTable_xq', {
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
							if (colIndex == 1) {
								return comm.getCityNameByCode(rsp.data.provinceNo, record['cityNo']);
							}

							return $('<a>' + gridObj_01.getCellRecordValue(rowIndex, colIndex) + '</a>').click(function (e) {
								if (gridObj_01.getCellRecordValue(rowIndex, colIndex) != 0) {
									aps_zysjylb_01.ck_dialog(record, colIndex);
								}
							});
						}
					}

				});
				$('#back_zysjylb').triggerWith('#' + $liActiveId);
			});

		},
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
				var isAutoLoad = true; //是否自动加载数据
				if (rsp.data == null || rsp.data.length == 0) {
					isAutoLoad = false;
				}

				//分页控件赋值
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
		}
	};
	return aps_zysjylb_01;

});