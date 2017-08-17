define(['text!coDeclarationTpl/kqxtyw/qysczl.html',
        'text!coDeclarationTpl/kqxtyw/confirm_dialog.html',
        'coDeclarationDat/kqxtyw/qysczl',
		'coDeclarationLan'], function(qysczlTpl, confirm_dialogTpl, dataModoule){
	var aps_kqxtyw_qysczl = {
		creType:null,
		showPage: function () {
			aps_kqxtyw_qysczl.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm: function () {
			$("#qysczlTpl1").show();
			$("#qysczlTpl2").hide();

			//点击查看页面修改按钮
			$('#qysczl_modify').click(function () {
				$("#qysczlTpl1").hide();
				$("#qysczlTpl2").show();
			});
			//点击查看页面取消按钮
			$('#qysczl_cancel').triggerWith('#' + $("#menuName").val());
			//点击修改页面提交按钮
			$('#qysczl_xg_btn').click(function () {
				aps_kqxtyw_qysczl.initChangeData();
			});
			//点击修改页面取消按钮
			$('#back_qysczl').click(function () {
				$("#qysczlTpl1").show();
				$("#qysczlTpl2").hide();
			});

			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#case-fileId-1", comm.getPicServerUrl(map.picList, '1003'), null);
				comm.initPicPreview("#case-fileId-2", comm.getPicServerUrl(map.picList, '1004'), null);
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
				comm.initPicPreview("#case-fileId-6", comm.getPicServerUrl(map.picList, '1006'), null);
				comm.initPicPreview("#case-fileId-7", comm.getPicServerUrl(map.picList, '1007'), null);
				comm.initDownload("#case-fileId-8", map.comList, '1006');
			});
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			dataModoule.findAptitudeByApplyId({"applyId": $("#applyId").val()}, function (res) {
				$('#infobox').html(_.template(qysczlTpl));
				aps_kqxtyw_qysczl.initShowForm(res.data, false);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 附件信息
		 */
		initShowForm: function (data) {
			comm.delCache('coDeclaration', 'kqxtyw-qysczl-info');
			comm.setCache('coDeclaration', 'kqxtyw-qysczl-info', data);
			var realList = data.realList;//附件信息
			var venBefFlag = data.venBefFlag;//是否需要填写创业帮扶协议
			aps_kqxtyw_qysczl.creType = data.creType;
			$("#aptRemark").val(comm.removeNull(data.aptRemark));//备注说明
			$("#view_aptRemark").html(comm.removeNull(data.aptRemark));//备注说明
			if (realList) {
				for (var k in realList) {
					if(realList[k].fileId) {
						var title = comm.getNameByEnumId(realList[k].aptitudeType, comm.lang("coDeclaration").aptitudeTypeEnum);
						comm.initPicPreview("#thum-" + realList[k].aptitudeType, realList[k].fileId, title);
						$("#thum-" + realList[k].aptitudeType).html("<img width='107' height='64' src='" + comm.getFsServerUrl(realList[k].fileId) + "'/>");
						comm.initPicPreview("#preview-" + realList[k].aptitudeType, realList[k].fileId, title);
						$("#preview-" + realList[k].aptitudeType).html("<img width='107' height='64' src='" + comm.getFsServerUrl(realList[k].fileId) + "'/>");
						$("#aptitude-" + realList[k].aptitudeType).val(realList[k].aptitudeId);
						$("#real-fileId-" + realList[k].aptitudeType).val(realList[k].fileId);
					}
				}
			}
			//处理创业帮扶协议
			if (venBefFlag == "true") {
				$("#li-venBefProtocolId-1").show();
				$("#li-venBefProtocolId-2").show();
			} else {
				$("#li-venBefProtocolId-1").hide();
				$("#li-venBefProtocolId-2").hide();
			}
			if(aps_kqxtyw_qysczl.creType&&aps_kqxtyw_qysczl.creType==2) {
				$('#case-fileId-1').hide();
				$('#preview-2-div').hide();
				$('#lrCredentialBackFileDiv').hide();
			}else{
				$('#case-fileId-1').show();
				$('#preview-2-div').show();
				$('#lrCredentialBackFileDiv').show();
			}
			$("#venBefFlag").val(venBefFlag);
			this.initForm();
			this.initTmpPicPreview();
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview: function () {
			var btnIds = ['#lrCredentialFrontFileId', '#lrCredentialBackFileId', '#busLicenceFileId', '#organizationFileId', '#taxplayerFileId', '#venBefProtocolId'];
			var imgIds = ['#thum-1', '#thum-2', '#thum-3', '#thum-6', '#thum-7', '#thum-8'];
			$("body").on("change", "", function () {
				for (var k in imgIds) {
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds);
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#qysczl_form").validate({
				rules: {
					lrCredentialFrontFileId: {
						required: true
					},
					lrCredentialBackFileId: {
						required: true
					},
					busLicenceFileId: {
						required: true
					},
					organizationFileId: {
						required: true
					},
					taxplayerFileId: {
						required: true
					},
					venBefProtocolId: {
						required: false
					},
					aptRemark: {
						rangelength: [0, 300]
					}
				},
				messages: {
					lrCredentialFrontFileId: {
						required: comm.lang("coDeclaration")[36007]
					},
					lrCredentialBackFileId: {
						required: comm.lang("coDeclaration")[36008]
					},
					busLicenceFileId: {
						required: comm.lang("coDeclaration")[36009]
					},
					organizationFileId: {
						required: comm.lang("coDeclaration")[36010]
					},
					taxplayerFileId: {
						required: comm.lang("coDeclaration")[36011]
					},
					venBefProtocolId: {
						required: comm.lang("coDeclaration")[36012]
					},
					aptRemark: {
						rangelength: comm.lang("common")[22063]
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
			/**
			 * 如果是修改则无需对上传文件进行必填校验
			 */
			if ($("#real-fileId-1").val() != "") {
				validate.settings.rules.lrCredentialFrontFileId = {required: false};
			}
			if (aps_kqxtyw_qysczl.creType==2||$("#real-fileId-2").val() != "") {
				validate.settings.rules.lrCredentialBackFileId = {required: false};
			}
			if ($("#real-fileId-3").val() != "") {
				validate.settings.rules.busLicenceFileId = {required: false};
			}
			if ($("#real-fileId-6").val() != "") {
				validate.settings.rules.organizationFileId = {required: false};
			}
			if ($("#real-fileId-7").val() != "") {
				validate.settings.rules.taxplayerFileId = {required: false};
			}
			//为托管企业需要校验创业帮扶协议件
			if ($("#venBefFlag").val() == "true" && $("#real-fileId-8").val() == "") {
				validate.settings.rules.venBefProtocolId = {required: true};
			} else {
				validate.settings.rules.venBefProtocolId = {required: false};
			}
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData: function () {
			return $("#confirm_dialog_form").validate({
				rules: {
					viewMark: {
						rangelength: [0, 300]
					},
					doubleUserName: {
						required: true
					},
					doublePassword: {
						required: true
					}
				},
				messages: {
					viewMark: {
						rangelength: comm.lang("coDeclaration")[36006]
					},
					doubleUserName: {
						required: comm.lang("coDeclaration")[36047]
					},
					doublePassword: {
						required: comm.lang("coDeclaration")[36048]
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
		},
		/**
		 * 初始化提交页面
		 */
		initChangeData: function () {
			if (!aps_kqxtyw_qysczl.validateData().form()) {
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			var odata = comm.getCache('coDeclaration', 'kqxtyw-qysczl-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};

			comm.delCache('coDeclaration', 'kqxtyw-qysczl-trsArray');
			comm.delCache('coDeclaration', 'kqxtyw-qysczl-oldArray');
			comm.delCache('coDeclaration', 'kqxtyw-qysczl-newArray');
			comm.setCache('coDeclaration', 'kqxtyw-qysczl-trsArray', []);
			comm.setCache('coDeclaration', 'kqxtyw-qysczl-oldArray', []);
			comm.setCache('coDeclaration', 'kqxtyw-qysczl-newArray', []);

			aps_kqxtyw_qysczl.getChangePic("#lrCredentialFrontFileId", "#real-fileId-1", "法人代表证件正面", 1);
			if(aps_kqxtyw_qysczl.creType==null || aps_kqxtyw_qysczl.creType != 2) {
				aps_kqxtyw_qysczl.getChangePic("#lrCredentialBackFileId", "#real-fileId-2", "法人代表证件反面", 2);
			}
			aps_kqxtyw_qysczl.getChangePic("#busLicenceFileId", "#real-fileId-3", "营业执照扫描件", 3);
			aps_kqxtyw_qysczl.getChangePic("#organizationFileId", "#real-fileId-6", "组织机构代码证扫描件", 6);
			aps_kqxtyw_qysczl.getChangePic("#taxplayerFileId", "#real-fileId-7", "税务登记证扫描件", 7);
			aps_kqxtyw_qysczl.getChangePic("#venBefProtocolId", "#real-fileId-8", "创业帮扶协议", 8);

			var trsArray = comm.getCache('coDeclaration', 'kqxtyw-qysczl-trsArray');//存入改变的TR
			$.each(trsArray,function(index,content){
				trs += content;
			});
			var newAptRemark = comm.removeNull($('#aptRemark').val());
			var oldAptRemark = comm.removeNull(ndata.aptRemark);
			if (newAptRemark != oldAptRemark) {
				trs = trs + '<tr><td class="view_item">备注说明</td><td class="view_text">' + oldAptRemark + '</td><td class="view_text">' + newAptRemark + "</td></tr>";
			}
			if (!trs) {
				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
				return;
			}
			//提交
			$('#qysczl_dialog > p').html(_.template(confirm_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);

			var oldArray = comm.getCache('coDeclaration', 'kqxtyw-qysczl-oldArray');//修改前图片信息
			var newArray = comm.getCache('coDeclaration', 'kqxtyw-qysczl-newArray');//修改后图片信息
			for (var key in oldArray) {
				if(oldArray[key].picId) {
					comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
				}
			}
			for (var key in newArray) {
				if(newArray[key].picId) {
					comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
				}
			}
			aps_kqxtyw_qysczl.initVerificationMode();
			$('#qysczl_dialog').dialog({
				width: 800,
				buttons: {
					'确定': function () {
						if (!aps_kqxtyw_qysczl.validateViewMarkData().form()) {
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function (verifyRes) {
							var ids = [];
							var delFileIds = [];
							if ($("#lrCredentialFrontFileId").val() != "") {
								if ($("#real-fileId-1").val() != "") {
									delFileIds[delFileIds.length] = $("#real-fileId-1").val();
								}
								ids[ids.length] = "lrCredentialFrontFileId";
							}
							if ($("#lrCredentialBackFileId").val() != "") {
								if ($("#real-fileId-2").val() != "") {
									delFileIds[delFileIds.length] = $("#real-fileId-2").val();
								}
								ids[ids.length] = "lrCredentialBackFileId";
							}
							if ($("#busLicenceFileId").val() != "") {
								if ($("#real-fileId-3").val() != "") {
									delFileIds[delFileIds.length] = $("#real-fileId-3").val();
								}
								ids[ids.length] = "busLicenceFileId";
							}
							if ($("#organizationFileId").val() != "") {
								if ($("#real-fileId-6").val() != "") {
									delFileIds[delFileIds.length] = $("#real-fileId-6").val();
								}
								ids[ids.length] = "organizationFileId";
							}
							if ($("#taxplayerFileId").val() != "") {
								if ($("#real-fileId-7").val() != "") {
									delFileIds[delFileIds.length] = $("#real-fileId-7").val();
								}
								ids[ids.length] = "taxplayerFileId";
							}
							if ($("#venBefProtocolId").val() != "") {
								if ($("#real-fileId-8").val() != "") {
									delFileIds[delFileIds.length] = $("#real-fileId-8").val();
								}
								ids[ids.length] = "venBefProtocolId";
							}
							comm.uploadFile(null, ids, function (data) {
								if (data.lrCredentialFrontFileId) {
									chg[1] = {"old": $("#real-fileId-1").val(), "new": data.lrCredentialFrontFileId};
									$("#real-fileId-1").val(data.lrCredentialFrontFileId);
								}
								if (data.lrCredentialBackFileId) {
									chg[2] = {"old": $("#real-fileId-2").val(), "new": data.lrCredentialBackFileId};
									$("#real-fileId-2").val(data.lrCredentialBackFileId);
								}
								if (data.busLicenceFileId) {
									chg[3] = {"old": $("#real-fileId-3").val(), "new": data.busLicenceFileId};
									$("#real-fileId-3").val(data.busLicenceFileId);
								}
								if (data.organizationFileId) {
									chg[6] = {"old": $("#real-fileId-6").val(), "new": data.organizationFileId};
									$("#real-fileId-6").val(data.organizationFileId);
								}
								if (data.taxplayerFileId) {
									chg[7] = {"old": $("#real-fileId-7").val(), "new": data.taxplayerFileId};
									$("#real-fileId-7").val(data.taxplayerFileId);
								}
								if (data.venBefProtocolId) {
									chg[8] = {"old": $("#real-fileId-8").val(), "new": data.venBefProtocolId};
									$("#real-fileId-8").val(data.venBefProtocolId);
								}
								aps_kqxtyw_qysczl.saveData(ndata, chg, verifyRes);
							}, function (err) {
								aps_kqxtyw_qysczl.initTmpPicPreview();
							}, {delFileIds: delFileIds});
						});
					},
					'取消': function () {
						$(this).dialog('destroy');
					}
				}
			});
		},
		/**
		 *
		 * @param btnId 上传按钮ID
		 * @param fileId 图片文件ID
		 * @param desc 图片抬头
		 * @param index 图片类型（传入枚举值）
		 */
		getChangePic: function (btnId, fileId, desc, index) {
			var trsArray = comm.getCache('coDeclaration', 'kqxtyw-qysczl-trsArray');
			var oldArray = comm.getCache('coDeclaration', 'kqxtyw-qysczl-oldArray');
			var newArray = comm.getCache('coDeclaration', 'kqxtyw-qysczl-newArray');
			if ($(btnId).val() != "") {
				var fId = $(fileId).val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new-" + index + "\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if (fId != "") {
					oldPic = "<a href=\"#\" id=\"prv-pic-old-" + index + "\" class=\"blue\">查看</a>";
					oldArray.push({picId: "#prv-pic-old-" + index, fileId: fId, title: "修改前" + desc});
				}
				newArray.push({picId: "#prv-pic-new-" + index, fileId: '#thum-' + index, title: "修改后" + desc});
				trsArray.push("<tr><td class=\"view_item\">" + desc + "</td><td class=\"view_text\">" + oldPic + "</td><td class=\"view_text\">" + newPic + "</td></tr>");
			}
		},
		/**
		 * 保存数据
		 * @param ndata 附件信息
		 */
		saveData: function (ndata, chg, verifyRes) {
			var params = $("#qysczl_form").serializeJson();
			params.applyId = $("#applyId").val();
			params.aptRemark = $("#aptRemark").val();
			params.lrCredentialFrontFileId = $("#real-fileId-1").val();
			params.lrCredentialBackFileId = $("#real-fileId-2").val();
			params.busLicenceFileId = $("#real-fileId-3").val();
			params.organizationFileId = $("#real-fileId-6").val();
			params.taxplayerFileId = $("#real-fileId-7").val();
			params.venBefProtocolFileId = $("#real-fileId-8").val();
			params.lrCredentialFrontAptitudeId = $("#aptitude-1").val();
			params.lrCredentialBackAptitudeId = $("#aptitude-2").val();
			params.busLicenceAptitudeId = $("#aptitude-3").val();
			params.organizPicAptitudeId = $("#aptitude-6").val();
			params.taxRegPicAptitudeId = $("#aptitude-7").val();
			params.venBefProtocolAptitudeId = $("#aptitude-8").val();
			params.venBefFlag = $("#venBefFlag").val();
			params.verifyDouble = "true";
			params.dblOptCustId = verifyRes.data;
			params.optRemark = $("#viewMark").val();
			params.creType = aps_kqxtyw_qysczl.creType;
			params.changeContent = JSON.stringify(chg);
			dataModoule.saveDeclareAptitude(params, function (res) {
				var realList = res.data;
				if (realList) {
					for (var k in realList) {
						if(realList[k].aptitudeId) {
							$("#aptitude-" + realList[k].aptitudeType).val(realList[k].aptitudeId);
						}
					}
				}
				ndata.realList = res.data;
				ndata.aptRemark = $("#aptRemark").val();
				comm.alert({
					content: comm.lang("coDeclaration")[22000], callOk: function () {
						$('#qysczl_dialog').dialog('destroy');
						aps_kqxtyw_qysczl.initShowForm(ndata, true);
					}
				});
			});
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode: function () {
			comm.initSelect("#verificationMode", comm.lang("coDeclaration").verificationMode, null, '1').change(function (e) {
				var val = $(this).attr('optionValue');
				switch (val) {
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
				}
			});
		}
	};
	return aps_kqxtyw_qysczl;
});