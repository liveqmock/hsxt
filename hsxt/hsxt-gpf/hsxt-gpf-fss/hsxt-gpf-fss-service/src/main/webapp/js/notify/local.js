$(function () {
    //dataTable初始化
    var localTable = $('#local-data-table').DataTable({
        "jQueryUI": true,
        "pagingType": "full_numbers",
        "dom": '<""l>t<"F"fp>',
        "processing": true,
        "serverSide": true,
        "ajax": Root + '/local/queryLocalNotifyForPage',
        //"fnServerParams": function (aoData) {
        // aoData.push({"name": "notifyStartDate", "value": $("#select #startDate").val()});
        // aoData.push({"name": "notifyEndDate", "value": $("#select #endDate").val()});
        // aoData.push({"name": "fromPlat", "value": $("#select #loginId").val()});
        // aoData.push({"name": "toPlat", "value": encodeURI($("#select #userName").val())});
        // aoData.push({"name": "fromSys", "value": $("#select #userCode").val()});
        // aoData.push({"name": "toSys", "value": $("#select #resourceNo").val()});
        // aoData.push({"name": "purpose", "value": $("#select #phone").val()});
        // },
        "columns": [
            {
                "data": "no", "render": function (data, type, full, meta) {
                    return meta.row;
                }
            },
            {"data": "notifyNo"},
            {"data": "fromSys"},
            {"data": "toSys"},
            {"data": "notifyTime"},
            {"data": "purpose"},
            {"data": "fileCount"},
            {"data": "received"},
            {"data": "allCompleted"},
            {"data": "completedTime"},
            {"data": "allPass"},
            {"data": "remark"},
            {"data": "createTime"},
            {
                "data": "received", "render": function (data, type, full, meta) {
                return data == "0" ? '<a class="resend" style="cursor: pointer"><i class="icon-off"></i></a>' : "";
            }
            }
        ]

    });

    //初始化下拉框
    $('select').select2();

    //初始化任务触发事件
    $('#local-data-table tbody').on("click", ".resend", function () {
        var rowData = localTable.row(this.parentNode.parentNode).data();
        $.ajax({
            url: Root + "/local/touchLocalNotifyJobByNotifyNo",
            type: 'POST',
            data: {notifyNo: rowData.notifyNo},
            dataType: 'json',
            success: function (result) {
                alert(result);
            }
        });
    });

});
