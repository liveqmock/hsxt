define(["text!appreciationInfoTpl/sub/hsfpcx/hsfpcx.html",
    "appreciationInfoDat/hsfpcx/hsfpcx",
    "appreciationInfoSrc/date_util",
    "appreciationInfoLan"
], function (hsfpcxTpl, hsfpcxAjax) {
    var hsfpcxTable = null;
    var hsfpcx = {
        showPage: function (tabid) {

            $('#main-content > div[data-contentid="' + tabid + '"]').empty().html(hsfpcxTpl);
            hsfpcx.initDate();
            hsfpcx.loadGrid();

            $('#hsfpcx_tb_cx').click(function () {
                var startDate = $("#hsfpcx_tb_timeRange_start").val();
                var endDate = $("#hsfpcx_tb_timeRange_end").val();
                var resNo = $('#hsfpcx_tb_resNo').val();
                if (resNo && resNo.length < 11) {
                    comm.alert({
                        imgClass: 'tips_warn',
                        content: '请正确输入企业资源号！'
                    });
                    return;
                }
                // hsfpcx.loadGrid();
                hsfpcxTable.search({
                    startDate: startDate,
                    endDate: endDate,
                    resNo: resNo
                })
            });
        },
        loadGrid: function (params) {
            hsfpcxTable = comm.getCommBsGrid('hsfpcx_ql', {domain:'gpf_bm',url:'queryPointValueListPage'}, params || {}, hsfpcx.dataFormat)
        },
        dataFormat:function (record, rowIndex, colIndex) {
            if (colIndex == 1) {
                return record.date?record.date.substring(0,8):'';
            } else if (colIndex == 2) {
                return record.mlmPoint1 + record.mlmPoint2 + record.mlmPoint3;
            }
        },
        initDate: function () {
            var date = new Date();
            $("#hsfpcx_tb_kjyf").selectList({
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
                    $("#hsfpcx_tb_timeRange_start").val(today.addMonths(parseInt(d) - 3).bestFormat('yyyy-MM-dd'));
                    $("#hsfpcx_tb_timeRange_end").val(today.addMonths(1).bestFormat('yyyy-MM-dd'));
                }
            });

            /*日期控件*/
            $("#hsfpcx_tb_timeRange_start").datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (dateTxt, inst) {
                    var d = dateTxt.replace('-', '/');
                    $("#hsfpcx_tb_timeRange_end").datepicker('option', 'minDate', new Date(d));
                }
            });
            $("#hsfpcx_tb_timeRange_end").datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (dateTxt, inst) {
                    var d = dateTxt.replace('-', '/');
                    $("#hsfpcx_tb_timeRange_start").datepicker('option', 'maxDate', new Date(d));
                }
            });
        }
    };

    return hsfpcx;
});