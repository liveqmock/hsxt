define(['text!certificateManageTpl/dsfywzsgl/zslsjlcx.html',
		'text!certificateManageTpl/dsfywzsgl/zslsjlcx_ck.html',
		'certificateManageLan'
		], function(zslsjlcxTpl, zslsjlcx_ckTpl){
	var aps_dsfywzsgl_zslsjlcx = {
		showPage: function () {
			$('#busibox').empty().html(_.template(zslsjlcxTpl));

			/*下拉列表*/
			comm.initSelect('#printStatus', comm.lang("certificateManage").printStatusEnum, null, null, {name: '全部', value: ''});
			comm.initSelect('#sendStatus', comm.lang("certificateManage").sendStatusEnum, null, null, {name: '全部', value: ''});
			comm.initSelect('#sealStatus', comm.lang("certificateManage").sealStatusSelector, null, null, {name: '全部', value: ''});
			/*end*/

			/*表格数据模拟*/
			$("#qry_third_certificate_his_btn").click(function () {
				var params = {
					search_entResNo: $("#entResNo").val().trim(),			//企业互生号
					search_certificateNo: $("#certificateNo").val().trim(),		//证书唯一识别码
					search_entCustName: $("#entCustName").val().trim()		//企业名称
				};
				/*var sealStatus = $("#sealStatus").attr("optionvalue");
				if (sealStatus) {
					params.search_sealStatus = sealStatus;
				}*/
				var printStatus = $("#printStatus").attr("optionvalue");
				if (printStatus) {
					params.search_printStatus = printStatus;
				}
				var sendStatus = $("#sendStatus").attr("optionvalue");
				if (sendStatus) {
					params.search_sendStatus = sendStatus;
				}

				comm.getCommBsGrid("searchTable", "find_third_certificate_recode_by_page", params, comm.lang("certificateManage"), aps_dsfywzsgl_zslsjlcx.detail);
			});

			/*end*/
		},
		detail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 1) {
	            return record.certificateNo ? record.certificateNo.substring(11, record.certificateNo.length) : '';
	        }
			/*if (colIndex == 5) {
				return comm.lang("certificateManage").sealStatusEnum[record.sealStatus];
			}*/
			if (colIndex == 5) {
				return comm.lang("certificateManage").printStatusEnum[record.isPrint];
			}
			if (colIndex == 7) {
				return comm.lang("certificateManage").sendStatusEnum[record.isSend];
			}
			if (colIndex == 8) {
				return $('<a>查看</a>').click(function (e) {
					aps_dsfywzsgl_zslsjlcx.chaKan(record);
				});
			}
		},
		chaKan: function (obj) {
			$('#busibox_ck').html(_.template(zslsjlcx_ckTpl, obj));
			$('#busibox_ck').removeClass("none");
			comm.liActive_add($('#ck'));
			$("#busibox").addClass("none");
			$("#zslsjlcx_ckTpl").removeClass("none");
			/*表格数据模拟*/
			var chaKanParam = {
				search_certificateNo: obj.certificateNo
			};

			comm.getCommBsGrid("chakanSearchTable", "find_third_certificate_give_out_recode", chaKanParam, comm.lang("certificateManage"), aps_dsfywzsgl_zslsjlcx.chaKanDetail);

			/*end*/

			//	$('#back_zslsjlcx').triggerWith('#zslsjlcx');
			//返回按钮
			$('#back_zslsjlcx').click(function () {
				//隐藏头部菜单
				$('#zslsjlcx_ckTpl').addClass('none');
				$('#busibox').removeClass('none');
				$('#ck').addClass("tabNone").find('a').removeClass('active');
				$('#zslsjlcx').find('a').addClass('active');
				$('#busibox_ck').addClass("none");
			});
			//

		},
		chaKanDetail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 1) {
                return record.certificateNo ? record.certificateNo.substring(11, record.certificateNo.length) : '';
            }
			if (colIndex == 2) {
				return comm.lang("certificateManage").printStatusEnum[record.isPrint];
			}
			if (colIndex == 5) {
				if (record.sendRemark) {
					return $('<a>查看</a>').click(function (e) {
						comm.alert(record.sendRemark);
					});
				}
			}
			if (colIndex == 6) {
				if (record.recRemark) {
					return $('<a>查看</a>').click(function (e) {
						comm.alert(record.recRemark);
					});
				}
			}
		}
	};
	return aps_dsfywzsgl_zslsjlcx;
});