define(['text!certificateManageTpl/zsff/zsff.html',
		'certificateManageDat/certificate',
		'certificateManageLan'
		], function(zsffTpl, dataModule){
	return {
		zsffTable : null,
		chaKanObj : null,
		showPage : function(obj,btnId){
			/*表格数据模拟*/
			var reqParam = {
					certificateNo : obj.certificateNo
			};
			dataModule.findSellCertificateById(reqParam, function (res) {
				if (res.data) {
					var reqData = res.data;
					res.data['isPrint'] = obj.isPrint;
					reqData.statusName = comm.lang("certificateManage").sealStatusEnum[reqData.status];
					$('#busibox_ck').html(_.template(zsffTpl, res.data));
					comm.liActive_add($('#ffzs'));
					$("#busibox").addClass("none");
					$("#zsffgl_ffzsTpl").removeClass("none");
					$('#busibox_ck').removeClass("none");
					//$('#ffzs_cancel').triggerWith('#'+btnId);
					//返回按钮
					$('#ffzs_cancel').click(function () {
						//隐藏头部菜单
						$('#zsffgl_ffzsTpl').addClass('none');
						$('#busibox').removeClass('none');
						$('#ffzs').addClass("tabNone").find('a').removeClass('active');
						$('#zsffgl').find('a').addClass('active');
						$('#busibox_ck').addClass("none");
					});


					$('#submit_btn').click(function () {
						var submitParam = {
							certificateNo: reqData.certificateNo,
							sendOperator: $.cookie('custName')
						};
						var sendRemark = $.trim($("#sendRemark").val());
						if (comm.isNotEmpty(sendRemark)) {
							submitParam.sendRemark = sendRemark
						} else {
							comm.error_alert(comm.lang("certificateManage").zsSendRemarkError);
							return false;
						}

						if (obj.isSend) {

							//原件是否已收回
							var originalIsRec = $('input:radio[name=originalIsRec]:checked').val();
							var recRemark = $.trim($('#recRemark').val());

							if (originalIsRec == 1 && comm.isEmpty(recRemark)) {
								comm.error_alert(comm.lang("certificateManage").zsRecvRemarkError);
								return false;
							} else {
								submitParam.recRemark = recRemark;
							}
							if (originalIsRec) {
								submitParam.originalIsRec = originalIsRec;
							}
						}

						dataModule.giveOutThirdCertificate(submitParam, function (res) {
							if (res.retCode == '22000') {
								//$("#"+btnId).click();
								$('#zsffgl_ffzsTpl').addClass('none');
								$('#busibox').removeClass('none');
								$('#ffzs').addClass("tabNone").find('a').removeClass('active');
								$('#zsffgl').find('a').addClass('active');
								$('#qry_zffsgl_btn').click();
							}
						});
					});
				} else {
					comm.error_alert("error");//comm.lang("messageCenter")[32702]);
				}
			});
			
			/*end*/	
		}
		
	}	
});