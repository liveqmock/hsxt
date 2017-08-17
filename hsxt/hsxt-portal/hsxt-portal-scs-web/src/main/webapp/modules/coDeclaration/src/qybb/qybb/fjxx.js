define(['text!coDeclarationTpl/qybb/qybb/fjxx.html',
        'coDeclarationDat/qybb/qybb',
		'coDeclarationSrc/qybb/qybb/gdxx',
	   	'coDeclarationLan'],function(fjxxTpl,dataModoule, gdxx){
	var isSave;
	var scs_qybb_fjxx = {
		showPage: function () {
			$('#qybb_view').html(_.template(fjxxTpl));
			$('#qybb_bc').css('display', '');
			$('#qybb_syb').css('display', '');
			$('#qybb_xyb').css('display', 'none');
			$('#qybb_tj').css('display', '');

			scs_qybb_fjxx.initForm();
			scs_qybb_fjxx.generateSecuritCode();
			scs_qybb_fjxx.initUpload();
			scs_qybb_fjxx.queryShareholderList();
		},
		/**
		 * 初始化界面
		 */
		initForm: function () {
			isSave = false;
			//上一步
			$('#qybb_syb').click(function () {
				if (comm.isNotEmpty($('#isSaveFj').val())) {
					$('#qybb_gdxx').click();
					return false;
				}
				if ($('i[id^=realPicFileId-] img').length > 0) {
					comm.i_confirm(comm.lang("coDeclaration").noSubmitAttachement, function () {
						//lyh修改
						gdxx.showPage();
						comm.liActive($('#qybb_gdxx'));
					}, 380);

				} else {
					$('#qybb_gdxx').click();
				}
			});

			//保存
			$('#qybb_bc').click(function () {
				scs_qybb_fjxx.aptitudeUpload(null);
				isSave = true;
			});
			scs_qybb_fjxx.initTmpPicPreview();
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview: function () {
			var btnIds = ['#licensePicId', '#bankPicId', '#sharePicId'];
			var imgIds = ['#realPicFileId-3', '#realPicFileId-4', '#realPicFileId-5'];
			$("body").on("change", "", function () {
				for (var k = 0; k < imgIds.length; k++) {
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds);

			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#picFileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
				comm.initPicPreview("#picFileId-4", comm.getPicServerUrl(map.picList, '1008'), null);
				comm.initPicPreview("#picFileId-5", comm.getPicServerUrl(map.picList, '1009'), null);
			});
		},
		/**
		 * 获取验证码
		 */
		generateSecuritCode: function () {
			$('#changeVaildCode').unbind('click').bind('click', function () {
				$('#vaildCodeImg').attr('src', comm.generateSecuritCode('entFiling'));
			});
			$('#changeVaildCode').trigger('click');
		},
		/**
		 * 企业报备-初始化附件信息
		 */
		initUpload: function () {
			var params = {applyId: this.getApplyId()};
			dataModoule.initUpload(params, function (res) {
				var realList = res.data.realList;//附件信息
				for (var k = 0; k < realList.length; k++) {
					var title = comm.getNameByEnumId(realList[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
					comm.initPicPreview("#realPicFileId-" + realList[k].aptType, realList[k].fileId, title);
					$("#realPicFileId-" + realList[k].aptType).html("<img width='107' height='64' src='" + comm.getFsServerUrl(realList[k].fileId) + "'/>");
					$("#fileId-" + realList[k].aptType).val(realList[k].fileId);
					$("#filingAptId-" + realList[k].aptType).val(realList[k].filingAptId);
				}
			});
		},
		/**
		 * 企业报备-保存附件信息
		 * @param direct 是否直接提交
		 */
		saveAptitude: function (direct) {
			var params = {
				applyId: this.getApplyId(),
				licensePicFileId: $("#fileId-3").val(),//营业执照扫描件文件Id
				bankPicFileId: $("#fileId-4").val(),//银行资金证明文件Id
				sharePicFileId: $("#fileId-5").val(),//合作股东证明文件Id
				licensePicAptId: $("#filingAptId-3").val(),//营业执照扫描件文件Id
				bankPicAptId: $("#filingAptId-4").val(),//银行资金证明文件Id
				sharePicAptId: $("#filingAptId-5").val(),//合作股东证明文件Id
				vaildCode: $('#vaildCode').val()//验证码
			};
			dataModoule.saveAptitude(params, function (res) {
				if (res.data) {
					var realList = res.data;
					for (var k = 0; k < realList.length; k++) {
						$("#filingAptId-" + realList[k].aptType).val(realList[k].filingAptId);
					}
				}
				if (direct&&typeof direct == 'function') {
					direct();
				}else{
					comm.alert({content: comm.lang("coDeclaration").uploadOk});
					scs_qybb_fjxx.saveStep(3);
					$('#isSaveFj').val('save');
				}
			});
		},
		/**
		 * 企业报备-上传附件信息
		 * @param direct 是否直接提交
		 */
		aptitudeUpload: function (direct) {
			if (!scs_qybb_fjxx.validateData().form()) {
				return;
			}
			var ids = [];
			var delFileIds = [];
			if ($("#licensePicId").val() != "") {
				if ($("#fileId-3").val() != "") {
					delFileIds[delFileIds.length] = $("#fileId-3").val();
				}
				ids[ids.length] = "licensePicId";
			}
			if ($("#bankPicId").val() != "") {
				if ($("#fileId-4").val() != "") {
					delFileIds[delFileIds.length] = $("#fileId-4").val();
				}
				ids[ids.length] = "bankPicId";
			}
			if ($("#sharePicId").val() != "") {
				if ($("#fileId-5").val() != "") {
					delFileIds[delFileIds.length] = $("#fileId-5").val();
				}
				ids[ids.length] = "sharePicId";
			}
			if (ids.length == 0) {
				
				if(direct&&typeof direct == 'function') {
					direct();
				}
				return;
			}
			comm.verificationCode({
				vaildCode :$('#vaildCode').val(),
				codeType : 'entFiling'
			},
			comm.lang("coDeclaration"),function(){
				comm.uploadFile(null, ids, function (data) {
					if (data.licensePicId) {
						$("#fileId-3").val(data.licensePicId);
					}
					if (data.bankPicId) {
						$("#fileId-4").val(data.bankPicId);
					}
					if (data.sharePicId) {
						$("#fileId-5").val(data.sharePicId);
					}
					scs_qybb_fjxx.initTmpPicPreview();
					scs_qybb_fjxx.saveAptitude(direct);
				}, function (err) {
					scs_qybb_fjxx.initTmpPicPreview();
				}, {delFileIds: delFileIds});
			});
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#fjxx_form").validate({
				rules: {
					licensePicId: {
						required: true
					},
					bankPicId: {
						required: true
					},
					sharePicId: {
						required: false
					},
					vaildCode: {
						required: true
					}
				},
				messages: {
					licensePicId: {
						required: comm.lang("coDeclaration")[32639]
					},
					bankPicId: {
						required: comm.lang("coDeclaration")[32640]
					},
					sharePicId: {
						required: comm.lang("coDeclaration")[32641]
					},
					vaildCode: {
						required: comm.lang("coDeclaration").inputVaildCode
					}
				},
				errorPlacement: function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag: true,
						destroyTime: 3000,
						position: {
							my: "left+200 top+30",
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
			if ($("#fileId-3").val() != "") {
				validate.settings.rules.licensePicId = {required: false};
			}
			if ($("#fileId-4").val() != "") {
				validate.settings.rules.bankPicId = {required: false};
			}
			if ($("#fileId-5").val() != "") {
				validate.settings.rules.sharePicId = {required: false};
			}
			if ($("#isHaveShareholder").val() != "" && $("#fileId-5").val() == "") {
				validate.settings.rules.sharePicId = {required: true};
			}
			return validate;
		},
		/**
		 * 控制步骤
		 * @param curStep 当前步骤
		 */
		saveStep: function (curStep) {
			var entFilling = comm.getCache("coDeclaration", "entFilling");
			if ((entFilling.curStep + 1) >= curStep) {
				entFilling.curStep = curStep;
			}
		},
		/**
		 * 获取申请编号
		 */
		getApplyId: function () {
			return comm.getCache("coDeclaration", "entFilling").applyId;
		},
		queryShareholderList: function () {
			var params = {search_applyId: this.getApplyId()};
			dataModoule.queryShareholderList(params, function (res) {
				if (res.data.length > 0) {
					$('#hzgdzmTip').show();
					$('#isHaveShareholder').val('1');
				}
			});
		}
	};
	return scs_qybb_fjxx;
}); 
