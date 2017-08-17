define(['text!companyInfoTpl/smrz/smrz.html',
        'companyInfoDat/smrz/smrz',
		'companyInfoLan'],function(smrzTpl, dataModoule){
	var smrzobj = {
		entInfo: null,
		showPage: function () {
			smrzobj.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm: function (data) {
			smrzobj.entInfo = data.entExtendInfo ? data.entExtendInfo : {};

			$('#contentWidth_3').html(_.template(smrzTpl, smrzobj.entInfo));

			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
			});

			if (data.isRealnameAuth) {//认证通过
				$("#smrz_ztts_tpl").show();
			} else {
				var realNameObj = data.entRealnameAuth ? data.entRealnameAuth : {};
				if (realNameObj.status == 0 || realNameObj.status == 1 || realNameObj.status == 2) {//待审批

					$("#createdDate").text(realNameObj.createdDate);
					$("#smrz_spz_tpl").show();
					return;
				} else if (realNameObj.status == 3 || realNameObj.status == 4) {//审批驳回（地区平台审批驳回，复核驳回）
					$("#status_3").show();
					$("#smrz_csrz").show();
					$("#smrz_zt_tpl").show();
					$("#apprDate").html(comm.removeNull(realNameObj.apprDate));
					$("#backMessage").html(comm.removeNull(realNameObj.apprContent));
					this.picPreview(realNameObj);
				} else {
					$("#smrz_tpl").show();
				}
			}

			//重新实名认证
			$('#smrz_csrz').click(function () {
				$("#smrz_tpl").show();
				$("#smrz_zt_tpl").hide();
			});

			$('#changeVaildCode').unbind('click').bind('click', function () {
				$('#vaildCodeImg').attr('src', comm.generateSecuritCode('realNameAuth'));
			});
			$('#changeVaildCode').trigger('click');
			//提交
			$('#smrz_tj').click(function () {
				smrzobj.saveFile();
			});

			this.initTmpPicPreview();
		},
		/**
		 * 显示图片
		 */
		picPreview: function (realNameObj) {
			comm.initPicPreview("#preview_3", comm.removeNull(realNameObj.licensePic));
			// $("#preview_3").html("<img width='107' height='64' src='" + comm.getFsServerUrl(comm.removeNull(realNameObj.licensePic)) + "'/>");
			$("#preview_3").attr('src', comm.getFsServerUrl(comm.removeNull(realNameObj.licensePic)));
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview: function () {
			var btnIds = ['#busLicenceFileId'];
			var imgIds = ['#preview-3'];
			comm.initUploadBtn(btnIds, imgIds, 87, 52);
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var smrzobj = this;
			dataModoule.initEntRealName(null, function (res) {
				smrzobj.initForm(res.data);
			});
		},
		/**
		 * 保存图片
		 */
		saveFile: function () {
			if (!smrzobj.validateData().form()) {
				return;
			}
			var params = {
				vaildCode: $('#vaildCode').val(),
				codeType: "realNameAuth"
			};
			//验证验证码
			comm.verificationCode(params, comm.lang("companyInfo"), function (res) {

				var ids = ['busLicenceFileId'];

				//上传文件
				comm.uploadFile(null, ids, function (data) {
					if (data.busLicenceFileId) {
						$("#fileId-3").val(data.busLicenceFileId);
					}
					smrzobj.saveData();
					smrzobj.initTmpPicPreview();
				}, function (err) {
					smrzobj.initTmpPicPreview();
				}, {delFileIds: null});
			});
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			return $("#qysczl_form").validate({
				rules: {
					busLicenceFileId: {
						required: true
					},
					postScript: {
						rangelength: [1, 300]
					},
					vaildCode: {
						required: true
					}
				},
				messages: {
					busLicenceFileId: {
						required: comm.lang("companyInfo")[32639]
					},
					postScript: {
						rangelength: comm.lang("companyInfo")[32536]
					},
					vaildCode: {
						required: comm.lang("companyInfo")[32692]
					}
				},
				errorPlacement: function (error, element) {
					if ($(element).attr('id') == 'vaildCode' || $(element).attr('id') == 'postScript') {
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
					} else {
						$(element).parent().parent().attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag: true,
							destroyTime: 3000,
							position: {
								my: "left+2 top+30",
								at: "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}
				},
				success: function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/**
		 * 保存数据
		 */
		saveData: function () {
			var params = {};
			params.entCustName = smrzobj.entInfo.entCustName;		//企业名称
			params.countryNo = smrzobj.entInfo.countryNo;			//国家代码
			params.provinceNo = smrzobj.entInfo.provinceNo;			//省份代码
			params.cityNo = smrzobj.entInfo.cityNo;					//城市代码
			params.entAddr = smrzobj.entInfo.entRegAddr;			//注册地址
			params.linkman = smrzobj.entInfo.contactPerson;			//联系人
			params.mobile = smrzobj.entInfo.contactPhone;			//联系人电话
			params.legalName = smrzobj.entInfo.legalPerson;			//企业法人
			params.licenseNo = smrzobj.entInfo.busiLicenseNo;		//营业执照号码
			params.licensePic = $("#fileId-3").val();		//营业执照扫描件
			params.postScript = $("#postScript").val();		//认证附言

			dataModoule.createEntRealNameAuth(params, function (res) {
				comm.alert({
					content: comm.lang("companyInfo").realNameSubmitSuccess, callOk: function () {
						//重新加载
						smrzobj.initData();
					}
				});
			});
		}
	};
	return smrzobj;
}); 

 