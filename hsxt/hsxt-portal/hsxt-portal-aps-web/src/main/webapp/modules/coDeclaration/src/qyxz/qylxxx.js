define(['text!coDeclarationTpl/qyxz/qylxxx.html',
        'coDeclarationDat/qyxz/qylxxx',
        'coDeclarationLan'],function(qylxxxTpl, dataModoule){
	var qyxz_qylxxx_services = {
		showPage: function () {
			qyxz_qylxxx_services.venBefProtocolAptId = null;
			qyxz_qylxxx_services.venBefProtocolFileId = null;
			if (qyxz_qylxxx_services.getApplyId() == null) {
				$('#busibox').html(_.template(qylxxxTpl));
				qyxz_qylxxx_services.initForm();
				qyxz_qylxxx_services.picPreview();
			} else {
				qyxz_qylxxx_services.initData();
			}
		},
		/**
		 * 绑定图片预览
		 */
		picPreview: function () {
			$("body").on("change", "", function () {
				comm.initTmpPicPreview('#apsXzBImg', $('#apsXzBImg').children().first().attr('src'));
				comm.initTmpPicPreview('#apsXzCImg', $('#apsXzCImg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate', '#protocolApt'], ['#apsXzBImg', '#apsXzCImg'], 87, 52);
		},
		/**
		 * 获取申请编号
		 */
		getApplyId: function () {
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		/**
		 * 初始化页面
		 */
		initForm: function () {
			//处理返回按钮
			if ($('#050100000000_subNav_050101000000').hasClass('s_hover')) {
				$('#qylxxx_back').hide();
			} else {
				$('#qylxxx_back').show();
			}
			$('#qylxxx_back').click(function () {
				$('#050100000000_subNav_050103000000').click();
			});

			//判断页面是否有更新（编辑时候用）
			$("#qylxxx_form :input").change(function () {
				comm.setCache("coDeclaration", "updateFlag", true);
			});

			//保存
			$('#qylxxx_save').click(function () {
				qyxz_qylxxx_services.certificateUpload(false);
			});
			
			//申报提交
			$('#qylxxx_sbtj').click(function () {
				qyxz_qylxxx_services.certificateUpload(true);
			});

			//绑定下载按钮
			cacheUtil.findDocList(function (map) {
				comm.initDownload("#certificateFileId", map.comList, '1007');
				comm.initDownload("#helpFileId", map.comList, '1006');
			});
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {
				applyId: qyxz_qylxxx_services.getApplyId()
			};
			dataModoule.findLinkmanByApplyId(params, function (res) {
				if (res.data.link) {
					$('#busibox').html(_.template(qylxxxTpl, res.data.link));
					$("#isNew").val("false");
				} else {
					$('#busibox').html(_.template(qylxxxTpl));
				}
				if (res.data.venBefFlag == "true") {
					qyxz_qylxxx_services.venBefFlag = "true";
					$('#venBef_ul').show();
				} else {
					qyxz_qylxxx_services.venBefFlag = "false";
					$('#venBef_ul').hide();
				}
				qyxz_qylxxx_services.initForm();
				if (res.data.link && res.data.link.certificateFile) {
					comm.initPicPreview("#apsXzAuthImg", res.data.link.certificateFile, "");
					$("#apsXzAuthImg").attr("src", comm.getFsServerUrl(res.data.link.certificateFile));
				}
				if (res.data.link && res.data.link.protocolAptitude && res.data.link.protocolAptitude.fileId) {
					comm.initPicPreview("#apsXzHelpImg", res.data.link.protocolAptitude.fileId, "");
					$("#apsXzHelpImg").attr("src", comm.getFsServerUrl(res.data.link.protocolAptitude.fileId));
					qyxz_qylxxx_services.venBefProtocolAptId = comm.removeNull(res.data.link.protocolAptitude.aptitudeId);
					qyxz_qylxxx_services.venBefProtocolFileId = comm.removeNull(res.data.link.protocolAptitude.fileId);
				}
				qyxz_qylxxx_services.picPreview();
				comm.delCache("coDeclaration", "updateFlag");
			});
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#qylxxx_form").validate({
				rules: {
					linkman: {
						required: true,
						rangelength: [2, 20]
					},
					address: {
						rangelength: [2, 128]
					},
					mobile: {
						required: true,
						mobileNo: true
					},
					zipCode: {
						zipCode: true
					},
					email: {
						email2: true
					}
				},
				messages: {
					linkman: {
						required: comm.lang("coDeclaration")[36014],
						rangelength: comm.lang("coDeclaration")[36015]
					},
					address: {
						rangelength: comm.lang("coDeclaration")[36016]
					},
					mobile: {
						required: comm.lang("coDeclaration")[36017],
						mobileNo: comm.lang("coDeclaration")[36018]
					},
					zipCode: {
						zipCode: comm.lang("coDeclaration")[36019]
					},
					email: {
						email2: comm.lang("coDeclaration")[36020]
					}
				},
				errorPlacement: function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag: true,
						destroyTime: 3000,
						position: {
							my: "left+2 top+30",
							at: "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success: function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			//检查附件信息，如果存在附件信息则不检查文件必填
			var cerFile = $("#certificateFile").val();
			if (cerFile != "") {
				validate.settings.rules.certificate = {required: false};
				validate.settings.messages.certificate = {required: ''};
			} else {
				validate.settings.rules.certificate = {required: true};
				validate.settings.messages.certificate = {required: comm.lang("coDeclaration")[36021]};
			}
			if (comm.removeNull(qyxz_qylxxx_services.venBefFlag) == "true") {
				if (comm.removeNull(qyxz_qylxxx_services.venBefProtocolFileId) != "") {
					validate.settings.rules.protocolApt = {required: false};
					validate.settings.messages.protocolApt = {required: ''};
				} else {
					validate.settings.rules.protocolApt = {required: true};
					validate.settings.messages.protocolApt = {required: comm.lang("coDeclaration")[36012]};
				}
			}
			return validate;
		},
		/**
		 * 企业申报-上传附件信息
		 */
		certificateUpload: function (autoSubmit) {
			if (!this.validateData().form()) {
				return;
			}
			var ids = [];
			var delFileIds = [];
			if ($("#certificate").val() != "") {
				if ($("#certificateFile").val() != "") {
					delFileIds[delFileIds.length] = $("#certificateFile").val();
				}
				ids[ids.length] = "certificate";
			}
			if ($("#protocolApt").val() != "") {
				if (comm.removeNull(qyxz_qylxxx_services.venBefProtocolFileId) != "") {
					delFileIds[delFileIds.length] = qyxz_qylxxx_services.venBefProtocolFileId;
				}
				ids[ids.length] = "protocolApt";
			}
			if (ids.length == 0) {
				qyxz_qylxxx_services.saveLinkInfo(autoSubmit);
			} else {
				comm.uploadFile(null, ids, function (data) {
					if (data.certificate) {
						$("#certificateFile").val(data.certificate);
					}
					if (data.protocolApt) {
						qyxz_qylxxx_services.venBefProtocolFileId = data.protocolApt;
					}
					qyxz_qylxxx_services.saveLinkInfo(autoSubmit);
					qyxz_qylxxx_services.picPreview();
				}, function (err) {
					qyxz_qylxxx_services.picPreview();
				}, {delFileIds: delFileIds});
			}
		},
		/**
		 * 企业申报-保存信息
		 */
		saveLinkInfo: function (autoSubmit) {
			var params = $("#qylxxx_form").serializeJson();
			params.applyId = this.getApplyId();
			params.venBefFlag = comm.removeNull(qyxz_qylxxx_services.venBefFlag);
			params.venBefProtocolAptId = comm.removeNull(qyxz_qylxxx_services.venBefProtocolAptId);
			params.venBefProtocolFileId = comm.removeNull(qyxz_qylxxx_services.venBefProtocolFileId);
			var isNew = $("#isNew").val();
			dataModoule.saveLinkInfo(params, function (res) {
				qyxz_qylxxx_services.saveStep(3);
				comm.delCache("coDeclaration", "updateFlag");
				$("#isNew").val("false");
				if (res && res.data && res.data.protocolAptitude && res.data.protocolAptitude.aptitudeId) {
					qyxz_qylxxx_services.venBefProtocolAptId = res.data.protocolAptitude.aptitudeId;
				}
				if (autoSubmit) {
					dataModoule.submitDeclare({applyId:params.applyId}, function (submitRes) {
						var entDeclare = comm.getCache("coDeclaration", "entDeclare");
						entDeclare.isSubmit = true;
						comm.delCache("coDeclaration", "updateFlag");
						comm.alert({
							content: comm.lang("coDeclaration")[22000], callOk: function () {
								if ($('#050100000000_subNav_050101000000').hasClass('s_hover')) {
									$('#050100000000_subNav_050101000000').click();
								} else {
									$('#050100000000_subNav_050103000000').click();
								}
							}
						});
					});
				} else {
					comm.alert({
						content: comm.lang("coDeclaration")[22000], callOk: function () {}
					});
				}
			});
		},
		/**
		 * 控制步骤
		 * @param curStep 当前步骤
		 */
		saveStep: function (curStep) {
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if ((entDeclare.curStep) <= curStep) {
				entDeclare.curStep = curStep;
			}
		}
	};
	return qyxz_qylxxx_services;
}); 

 