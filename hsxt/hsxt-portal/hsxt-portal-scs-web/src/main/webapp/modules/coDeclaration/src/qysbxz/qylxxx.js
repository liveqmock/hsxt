define(['text!coDeclarationTpl/qysbxz/qylxxx.html',
        'coDeclarationDat/qysbxz/qylxxx',
        'coDeclarationLan'],function(qylxxxTpl, dataModoule){
	var qysbxz_qylxxx_services = {
		showPage: function () {
			qysbxz_qylxxx_services.venBefProtocolAptId = null;
			qysbxz_qylxxx_services.venBefProtocolFileId = null;
			if (qysbxz_qylxxx_services.getApplyId() == null) {
				$('#contentWidth_2').html(_.template(qylxxxTpl));
				qysbxz_qylxxx_services.initForm();
				qysbxz_qylxxx_services.picPreview();
			} else {
				qysbxz_qylxxx_services.initData();
			}
		},
		picPreview: function () {
			$("body").on("change", "", function () {
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
				comm.initTmpPicPreview('#cimg', $('#cimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate', '#protocolApt'], ['#bimg', '#cimg'], 87, 52);
		},
		getApplyId: function () {
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		initForm: function () {
			//处理返回按钮
			if ($('#030200000000_subNav_030201000000').hasClass('s_hover')) {
				$('#qylxxx_fh').hide();
			} else {
				$('#qylxxx_fh').show();
			}
			$('#qylxxx_fh').click(function () {
				$('#030200000000_subNav_030202000000').click();
			});

			//判断页面是否有更新（编辑时候用）
			$("#qylxxx_form :input").change(function () {
				comm.setCache("coDeclaration", "updateFlag", true);
			});

			//申报提交 
			$('#qylxxx_sbtj').click(function(){
				qysbxz_qylxxx_services.certificateUpload(true);
			});

			//保存
			$('#qylxxx_save').click(function () {
				qysbxz_qylxxx_services.certificateUpload(false);
			});

			//绑定下载按钮
			cacheUtil.findDocList(function (map) {
				comm.initDownload("#certificateFileId", map.comList, '1007');
				comm.initDownload("#case-fileId-8", map.comList, '1006');
			});
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {
				applyId: qysbxz_qylxxx_services.getApplyId()
			};
			dataModoule.findLinkmanByApplyId(params, function (res) {
				if (res.data.link) {
					$('#contentWidth_2').html(_.template(qylxxxTpl, res.data.link));
					$("#isNew").val("false");
				} else {
					$('#contentWidth_2').html(_.template(qylxxxTpl));
				}
				if (res.data.fileId) {
					$('#certificateFileId').attr("href", comm.getFsServerUrl(res.data.fileId));
				}
				if (res.data.venBefFlag == "true") {
					qysbxz_qylxxx_services.venBefFlag = "true";
					$('#venBef_ul').show();
				}else{
					qysbxz_qylxxx_services.venBefFlag = "false";
					$('#venBef_ul').hide();
				}
				qysbxz_qylxxx_services.initForm();
				if (res.data.link && res.data.link.certificateFile) {
					comm.initPicPreview("#img", res.data.link.certificateFile, "");
					$("#img").attr("src", comm.getFsServerUrl(res.data.link.certificateFile));
				}
				if (res.data.link && res.data.link.protocolAptitude && res.data.link.protocolAptitude.fileId) {
					comm.initPicPreview("#img1", res.data.link.protocolAptitude.fileId, "");
					$("#img1").attr("src", comm.getFsServerUrl(res.data.link.protocolAptitude.fileId));
					qysbxz_qylxxx_services.venBefProtocolAptId = comm.removeNull(res.data.link.protocolAptitude.aptitudeId);
					qysbxz_qylxxx_services.venBefProtocolFileId = comm.removeNull(res.data.link.protocolAptitude.fileId);
				}
				qysbxz_qylxxx_services.picPreview();
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
						required: false,
						rangelength: [2, 128]
					},
					webSite: {
						required: false,
						isUrl: true
					},
					phone: {
						required: false,
						telphone: true
					},
					job: {
						required: false,
						job: true
					},
					mobile: {
						required: true,
						mobileNo: true
					},
					zipCode: {
						required: false,
						zipCode: true
					},
					email: {
						required: false,
						email2: true
					},
					fax: {
						required: false,
						fax: true
					},
					qq: {
						required: false,
						qq: true
					}
				},
				messages: {
					linkman: {
						required: comm.lang("coDeclaration")[32645],
						rangelength: comm.lang("coDeclaration")[32646]
					},
					address: {
						required: comm.lang("coDeclaration")[32647],
						rangelength: comm.lang("coDeclaration")[32648]
					},
					webSite: {
						required: comm.lang("coDeclaration")[32649],
						isUrl: comm.lang("coDeclaration")[32650]
					},
					phone: {
						required: comm.lang("coDeclaration")[32510],
						telphone: comm.lang("coDeclaration")[32511]
					},
					job: {
						required: comm.lang("coDeclaration")[32651],
						job: comm.lang("coDeclaration")[32652]
					},
					mobile: {
						required: comm.lang("coDeclaration")[32653],
						mobileNo: comm.lang("coDeclaration")[32654]
					},
					zipCode: {
						required: comm.lang("coDeclaration")[32512],
						zipCode: comm.lang("coDeclaration")[32513]
					},
					email: {
						required: comm.lang("coDeclaration")[32655],
						email2: comm.lang("coDeclaration")[32656]
					},
					fax: {
						required: comm.lang("coDeclaration")[32514],
						fax: comm.lang("coDeclaration")[32515]
					},
					qq: {
						required: comm.lang("coDeclaration")[32657],
						qq: comm.lang("coDeclaration")[32658]
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
				validate.settings.messages.certificate = {required: comm.lang("coDeclaration")[32660]};
			} else {
				validate.settings.rules.certificate = {required: true};
				validate.settings.messages.certificate = {required: comm.lang("coDeclaration")[32660]};
			}
			if (comm.removeNull(qysbxz_qylxxx_services.venBefFlag) == "true") {
				if (comm.removeNull(qysbxz_qylxxx_services.venBefProtocolFileId) != "") {
					validate.settings.rules.protocolApt = {required: false};
					validate.settings.messages.protocolApt = {required: ''};
				} else {
					validate.settings.rules.protocolApt = {required: true};
					validate.settings.messages.protocolApt = {required: comm.lang("coDeclaration")[32682]};
				}
			}
			return validate;
		},
		/**
		 * 企业申报-上传附件信息
		 */
		certificateUpload: function (autoSubmit) {
			var qysbxz_qylxxx_services = this;
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
				if (comm.removeNull(qysbxz_qylxxx_services.venBefProtocolFileId) != "") {
					delFileIds[delFileIds.length] = qysbxz_qylxxx_services.venBefProtocolFileId;
				}
				ids[ids.length] = "protocolApt";
			}
			if (ids.length == 0) {
				this.saveLinkInfo(autoSubmit);
			} else {
				comm.uploadFile(null, ids, function (data) {
					if (data.certificate) {
						$("#certificateFile").val(data.certificate);
					}
					if (data.protocolApt) {
						qysbxz_qylxxx_services.venBefProtocolFileId = data.protocolApt;
					}
					qysbxz_qylxxx_services.saveLinkInfo(autoSubmit);
					qysbxz_qylxxx_services.picPreview();
				}, function (err) {
					qysbxz_qylxxx_services.picPreview();
				}, {delFileIds: delFileIds});
			}
		},
		/**
		 * 企业申报-保存信息
		 */
		saveLinkInfo: function (autoSubmit) {
			var slef = this;
			var params = $("#qylxxx_form").serializeJson();
			params.applyId = this.getApplyId();
			params.venBefFlag = comm.removeNull(qysbxz_qylxxx_services.venBefFlag);
			params.venBefProtocolAptId = comm.removeNull(qysbxz_qylxxx_services.venBefProtocolAptId);
			params.venBefProtocolFileId = comm.removeNull(qysbxz_qylxxx_services.venBefProtocolFileId);
			var isNew = $("#isNew").val();
			dataModoule.saveLinkInfo(params, function (res) {

				slef.saveStep(3);

				comm.delCache("coDeclaration", "updateFlag");

				$("#isNew").val("false");
				if (res && res.data && res.data.protocolAptitude && res.data.protocolAptitude.aptitudeId) {
					qysbxz_qylxxx_services.venBefProtocolAptId = res.data.protocolAptitude.aptitudeId;
				}
				if (autoSubmit) {
					dataModoule.submitDeclare({applyId:params.applyId}, function (submitRes) {
                        var entDeclare = comm.getCache("coDeclaration", "entDeclare");
                        entDeclare.isSubmit = true;
                        comm.delCache("coDeclaration", "updateFlag");
                        comm.alert({
                            content: comm.lang("coDeclaration")[22000], callOk: function () {
                                //跳转到企业申报查询页面
                                $('a[menuurl="coDeclarationSrc/qysbcx/tab"]').trigger('click');
                            }
                        });
                    });
				} else {
					comm.alert({
						content: comm.lang("coDeclaration")[21000], callOk: function () {

						}
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
	return qysbxz_qylxxx_services;
}); 
