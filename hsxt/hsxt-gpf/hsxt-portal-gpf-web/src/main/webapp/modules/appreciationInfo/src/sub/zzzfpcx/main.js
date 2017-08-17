define(["text!appreciationInfoTpl/sub/zzzfpcx/zzzfpcx.html",
		"text!appreciationInfoTpl/sub/zzzfpcx/jfzzzfpsm.html",
	"appreciationInfoDat/zzzfpcx/zzzfpcx",
	"appreciationInfoSrc/date_util",
	"appreciationInfoLan"
		],function(zzzfpcxTpl, jfzzzfpsmTpl,zzzfpcxAjax){
	var zzzfpcx = {

		showPage: function (tabid) {

			$('#main-content > div[data-contentid="' + tabid + '"]').empty().html(zzzfpcxTpl);

			zzzfpcx.initDate();
			zzzfpcx.loadGrid();

			//说明
			$('#zzzfpcx_tb_jfzzzfpsm').click(function () {
				$('#zzzfpcxDlg').html(jfzzzfpsmTpl).dialog({
					title: '积分再增值分配说明',
					width: 800,
					modal: true,
					closeIcon: true,
					buttons: {
						"确定": function () {
							$(this).dialog("destroy");
						}
					}
				});
			});
			//查询操作
			$('#zzzfpcx_tb_cx').click(function () {
				var startDate = $("#zzzfpcx_tb_timeRange_start").val();
				var endDate = $("#zzzfpcx_tb_timeRange_end").val();
				var resNo = $('#zzzfpcx_tb_resNo').val();
				if (resNo && resNo.length < 11) {
					comm.alert({
						imgClass: 'tips_warn',
						content: '请正确输入企业互生号！'
					});
					return;
				}
				zzzfpcx.loadGrid({
					startDate: startDate,
					endDate: endDate,
					resNo: resNo
				});
			});

		},
		initDate: function () {
			var date = new Date();
			$("#zzzfpcx_tb_kjyf").selectList({
				borderWidth: 1,
				borderColor: '#CCC',
				options: [
					{name: date.bestFormat('yyyy-MM'), value: '3'},
					{name: date.addMonths(-1).bestFormat('yyyy-MM'), value: '2'},
					{name: date.addMonths(-1).bestFormat('yyyy-MM'), value: '1'}
				]
			}).change(function () {
				var d = $(this).attr('optionvalue');
				if (d) {
					var today = new Date();
					today.setDate(1);
					$("#zzzfpcx_tb_timeRange_start").val(today.addMonths(parseInt(d)-3).bestFormat('yyyy-MM-dd'));
					$("#zzzfpcx_tb_timeRange_end").val(today.addMonths(1).bestFormat('yyyy-MM-dd'));
				}
			});

			/*日期控件*/
			$("#zzzfpcx_tb_timeRange_start").datepicker({
				dateFormat: 'yy-mm-dd',
				onSelect: function (dateTxt, inst) {
					var d = dateTxt.replace('-', '/');
					$("#hsfpcx_tb_timeRange_end").datepicker('option', 'minDate', new Date(d));
				}
			});
			$("#zzzfpcx_tb_timeRange_end").datepicker({
				dateFormat: 'yy-mm-dd',
				onSelect: function (dateTxt, inst) {
					var d = dateTxt.replace('-', '/');
					$("#hsfpcx_tb_timeRange_start").datepicker('option', 'maxDate', new Date(d));
				}
			});
		},
		loadGrid: function (params) {
			comm.getCommBsGrid('zzzfpcx_ql', {domain:'gpf_bm',url:'queryBmlmListPage'}, params || {}, zzzfpcx.dataFormat)
		},
		dataFormat:function (record,rowIndex,colIndex) {
			return Math.formatFloat(record.percent*100,1)+'%';
		}
	};

	return zzzfpcx;
});