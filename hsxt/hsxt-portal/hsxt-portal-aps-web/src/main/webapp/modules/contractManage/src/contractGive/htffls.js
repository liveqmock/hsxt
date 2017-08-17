define(['text!contractManageTpl/contractGive/htff_htffls.html',
		'contractManageLan'
		], function(fflsTpl){
	var aps_htffls = {
		chaKanObj: null,
		showPage: function (obj, btnId) {

			comm.liActive_add($('#htffls'));
			$('#busibox').html(_.template(fflsTpl, obj));
			/*表格数据模拟*/
			var chaKanParam = {
				search_contractNo: obj.contractNo
			};
			aps_htffls.chaKanObj = comm.getCommBsGrid("searchTable", "find_contract_give_out_recode", chaKanParam, comm.lang("certificateManage"), aps_htffls.detail);
			/*end*/
			$('#back_ffht').triggerWith('#htff');

		},
		detail: function (record, rowIndex, colIndex, options) {
			if (null != aps_htffls.chaKanObj.getAllRecords() && aps_htffls.chaKanObj.getAllRecords().length > 0) {
				$('#htffls_sendDate').text(aps_htffls.chaKanObj.getAllRecords()[0].operatedDate);
				$('#htffls_sendCount').text(aps_htffls.chaKanObj.getAllRecords().length);
			}
			if (colIndex == 3) {
				if (record.sendRemark && record.sendRemark != '') {
					return $('<a>查看</a>').click(function (e) {
						comm.alert(record.sendRemark);
					});
				}
				return "";
			}
			else if (colIndex == 4) {
				if (record.recRemark && record.recRemark != '') {
					return $('<a>查看</a>').click(function (e) {
						comm.alert(record.recRemark);
					});
				}
				return "";
			}
		}
	};
	return aps_htffls;
});