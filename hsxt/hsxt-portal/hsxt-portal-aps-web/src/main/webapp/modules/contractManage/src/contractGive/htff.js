define(['text!contractManageTpl/contractGive/htff.html',
		'text!contractManageTpl/contractGive/htff_ffht.html',
		'text!contractManageTpl/contractGive/htff_htffls.html',	
		'contractManageSrc/contractGive/htffls',
		'contractManageDat/contract',
		'contractManageLan'
		], function(htffTpl, htff_ffhtTpl, htff_htfflsTpl, htffls, dataModule){
	var aps_htff = {
		showPage: function () {
			$('#busibox').html(_.template(htffTpl));

			/*下拉列表*/
			comm.initSelect('#sendStatus', comm.lang("contractManage").isGiveOut, null, null, {name: '全部', value: ''});
			/*end*/

			/*表格数据模拟*/

			$("#qry_htff_btn").click(function () {
				var htff_params = {
					search_entResNo: $("#entResNo").val().trim(),			//企业互生号
					search_entCustName: $("#entCustName").val().trim()		//企业名称
				};

				var sendStatus = $("#sendStatus").attr("optionvalue");
				if (sendStatus) {
					htff_params.search_sendStatus = sendStatus;
				}
				comm.delCache("contractManage", "htff_searchTable_params");
				if(htff_params.search_entResNo||htff_params.search_entCustName||sendStatus) {
					comm.setCache("contractManage", "htff_searchTable_params", htff_params);
				}
				comm.getCommBsGrid("searchTable", "find_contract_give_out_by_page", htff_params, comm.lang("contractManage"), aps_htff.detail, aps_htff.edit);
			});

			/*end*/
		},
		searchTable:function () {
			var org_params = comm.getCache("contractManage", "htff_searchTable_params");
			if(org_params) {
				if(org_params.search_entResNo)$("#entResNo").val(org_params.search_entResNo);
				if(org_params.search_entCustName)$("#entCustName").val(org_params.search_entCustName);
				if(org_params.search_sendStatus){
					$("#sendStatus").attr("optionvalue",org_params.search_sendStatus);
					$("#sendStatus").val(comm.lang("contractManage").isGiveOut[org_params.search_sendStatus]);
				}
				$("#qry_htff_btn").click();
			}
		},
		detail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 4) {
				return comm.lang("contractManage").custType[record.custType];
			}
			if (colIndex == 6) {
				return comm.lang("contractManage").isGiveOut[record.send];
			}
			if (colIndex == 8) {
				record.custTypeName = comm.lang("contractManage").custType[record.custType];
				return $('<a>合同发放历史</a>').click(function (e) {
					if (record.sendDate) {
						record.sendDateView = record.sendDate.substr(0, 10);
					}
					htffls.showPage(record);
				});
			}
		},

		edit: function (record, rowIndex, colIndex, options) {
			if (colIndex == 8) {
				if (record.send) {
					return $('<a>重新发放</a>').click(function (e) {
						comm.liActive_add($('#ffht'));
						record.custTypeName = comm.lang("contractManage").custType[record.custType];
						$('#busibox').html(_.template(htff_ffhtTpl, record));
						$('#ffht_save_btn').click(function () {
							if ($("#recRemark").val().trim() == "") {
								comm.error_alert(comm.lang("contractManage").recRemarkInvalid);
								return;
							}
							if ($("#sendRemark").val().trim() == "") {
								comm.error_alert(comm.lang("contractManage").sendRemarkInvalid);
								return;
							}
							var reqParam = {
								uniqueNo: $.trim($("#uniqueNo").val()),
								originalIsRec: $('input:radio[name="originalIsRec"]:checked').val(),
								recRemark: $.trim($("#recRemark").val()),
								sendRemark: $.trim($("#sendRemark").val())
							};
							dataModule.optContractGiveOut(reqParam, function (res) {
								if (res.retCode = 22000) {
									$('#051100000000_subNav_051103000000').click();
								}
							});
						});
						$('#ffht_cancel').triggerWith('#htff');
					});
				}else{
					return $('<a>发放合同</a>').click(function (e) {
						comm.liActive_add($('#ffht'));
						record.custTypeName = comm.lang("contractManage").custType[record.custType];
						$('#busibox').html(_.template(htff_ffhtTpl, record));
						$('#ffht_save_btn').click(function () {
							if ($("#sendRemark").val().trim() == "") {
								comm.error_alert(comm.lang("contractManage").sendRemarkInvalid);
								return;
							}
							var reqParam = {
								uniqueNo: $.trim($("#uniqueNo").val()),
								sendRemark: $.trim($("#sendRemark").val())
							};
							dataModule.optContractGiveOut(reqParam, function (res) {
								if (res.retCode = 22000) {
									$('#051100000000_subNav_051103000000').click();
								}
							});
						});
						$('#ffht_cancel').triggerWith('#htff');
					});
				}
			}
		}
	};
	return aps_htff;
});