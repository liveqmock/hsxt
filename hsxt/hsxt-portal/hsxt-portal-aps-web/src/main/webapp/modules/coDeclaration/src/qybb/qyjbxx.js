define(['text!coDeclarationTpl/qybb/qyjbxx.html',
        'text!coDeclarationTpl/qybb/confirm_dialog.html',
        'coDeclarationDat/qybb/qybb',
	   	'coDeclarationLan'], function(qyjbxxTpl, confirm_dialogTpl, dataModoule){
	var aps_qyjbxx = {
		odata: null,//修改前数据
		ndata: null,//修改后数据
		showPage: function () {
			aps_qyjbxx.initData();
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {applyId: aps_qyjbxx.getApplyId()};
			dataModoule.getEntFilingById(params, function (res) {
				aps_qyjbxx.odata = res.data;
				$('#infobox').html(_.template(qyjbxxTpl, res.data));
				aps_qyjbxx.initForm(res.data.provinceNo, res.data.cityNo);
				$('#cancel_btn').triggerWith('#' + $("#menuName").val());
			});
		},
		/**
		 * 初始化界面
		 */
		initForm: function (prov, city) {
			$('#qybb_bc').css('display', '');
			$('#qybb_xyb').css('display', '');
			$('#qybb_syb').css('display', 'none');
			$('#qybb_tj').css('display', 'none');
			comm.initProvSelect('#provinceNo', {}, 80, null);
			comm.initCitySelect('#cityNo', {}, 80, null);

			//初始化省份
			cacheUtil.findCacheSystemInfo(function (sysRes) {
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
					comm.initProvSelect('#provinceNo', provArray, 80, prov).change(function (e) {
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function (cityArray) {
							comm.initCitySelect('#cityNo', cityArray, 80).selectListIndex(0);
						});
					});
				});
				//初始化城市
				if (prov) {
					cacheUtil.findCacheSystemInfo(function (sysRes) {
						cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function (cityArray) {
							comm.initCitySelect('#cityNo', cityArray, 80, city);
						});
					});
				}
				//赋值国家代码
				$("#countryNo").val(sysRes.countryNo);
			});

			//保存
			$('#qybb_bc').click(function () {
				if (aps_qyjbxx.validateData().form()) {
					aps_qyjbxx.savaInfo(false);
				}
			});
		},
		/**
		 * 数据校验
		 */
		validateData: function () {
			var validate = $("#filing_form").validate({
				rules: {
					entCustName: {
						required: true,
						rangelength: [2, 128]
					},
					entAddress: {
						required: true,
						rangelength: [2, 128]
					},
					provinceNo: {
						required: true
					},
					cityNo: {
						required: true
					},
					legalName: {
						required: true,
						rangelength: [2, 20]
					},
					linkman: {
						required: true,
						rangelength: [2, 20]
					},
					licenseNo: {
						required: true,
						businessLicence: true
					},
					entType: {
						rangelength: [2, 20]
					},
					phone: {
						required: true,
						mobileNo: true
					},
					dealArea: {
						rangelength: [1, 300]
					},
					reqReason: {
						rangelength: [1, 300]
					}
				},
				messages: {
					entCustName: {
						required: comm.lang("coDeclaration")[36023],
						// custname:true,
						rangelength: comm.lang("coDeclaration")[36024]
					},
					entAddress: {
						required: comm.lang("coDeclaration")[36030],
						rangelength: comm.lang("coDeclaration")[36031]
					},
					provinceNo: {
						required: comm.lang("coDeclaration")[36094]
					},
					cityNo: {
						required: comm.lang("coDeclaration")[36095]
					},
					legalName: {
						required: comm.lang("coDeclaration")[36025],
						rangelength: comm.lang("coDeclaration")[36026]
					},
					linkman: {
						required: comm.lang("coDeclaration")[36088],
						rangelength: comm.lang("coDeclaration")[36092]
					},
					licenseNo: {
						required: comm.lang("coDeclaration")[36032],
						businessLicence: comm.lang("coDeclaration")[36096]
					},
					entType: {
						rangelength: comm.lang("coDeclaration")[36093]
					},
					phone: {
						required: comm.lang("coDeclaration")[36090],
						mobileNo: comm.lang("coDeclaration")[36097]
					},
					dealArea: {
						rangelength: comm.lang("coDeclaration")[36036]
					},
					reqReason: {
						rangelength: comm.lang("coDeclaration")[36091]
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
			return validate;
		},
		/**
		 * 保存企业报备基本信息
		 * @param autoNext 是否进行下一步
		 */
		savaInfo: function () {
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function () {
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name: this.name, value: this.value, desc: desc});
			});
			var ndata = comm.cloneJSON(aps_qyjbxx.odata);
			var trs = "";
			var chg = {};
			for (var key in ckArray) {
				var chgValue = ckArray[key].value;
				if (comm.removeNull(aps_qyjbxx.odata[ckArray[key].name]) != chgValue) {
					trs += "<tr><td class=\"view_item\">" + ckArray[key].desc + "</td><td class=\"view_text\">" + comm.removeNull(aps_qyjbxx.odata[ckArray[key].name]) + "</td><td class=\"view_text\">" + chgValue + "</td></tr>";
					chg[ckArray[key].name] = {"old": comm.removeNull(aps_qyjbxx.odata[ckArray[key].name]), "new": chgValue};
					ndata[ckArray[key].name] = chgValue;
				}
			}

			//所在地区
			var provNo = $("#provinceNo").attr('optionValue');
			var cityNo = $("#cityNo").attr('optionValue');
			if ((provNo + cityNo) != (comm.removeNull(aps_qyjbxx.odata.provinceNo) + comm.removeNull(aps_qyjbxx.odata.cityNo))) {
				//获取修改前所在地区
				var oResData = cacheUtil.getRegionByCode(null, aps_qyjbxx.odata.provinceNo, aps_qyjbxx.odata.cityNo, "");
				if (!oResData.flag) {
					return;
				}
				//获取修改后所在地区
				var nResData = cacheUtil.getRegionByCode(null, provNo, cityNo, "");
				if (!nResData.flag) {
					return;
				}
				var oPlaceName = oResData.data;
				var nPlaceName = nResData.data;

				trs += "<tr><td class=\"view_item\">所在地区</td><td class=\"view_text\">" + oPlaceName + "</td><td class=\"view_text\">" + nPlaceName + "</td></tr>";
				if (ndata.provinceNo != provNo) {
					chg.provinceNo = {"old": comm.removeNull(aps_qyjbxx.odata.provinceNo), "new": provNo};
				}
				if (ndata.cityNo != cityNo) {
					chg.cityNo = {"old": comm.removeNull(ndata.cityNo), "new": cityNo};
				}
				ndata.provinceNo = provNo;
				ndata.cityNo = cityNo;
			}

			if (trs == "") {
				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
				return;
			}
			$('#qyjbxx_dialog > p').html(_.template(confirm_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
			aps_qyjbxx.initVerificationMode();
			$('#qyjbxx_dialog').dialog({
				width: 800,
				buttons: {
					'确定': function () {
						if (!aps_qyjbxx.validateViewMarkData().form()) {
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function (verifyRes) {
							ndata.applyId = aps_qyjbxx.getApplyId();
							ndata.dblOptCustId = verifyRes.data;
							ndata.provinceNo = $("#provinceNo").attr('optionValue');
							ndata.cityNo = $("#cityNo").attr('optionValue');
							dataModoule.createEntFiling(ndata, function (res) {
								aps_qyjbxx.odata = ndata;
								comm.alert({
									content: comm.lang("coDeclaration")[22000], callOk: function () {
										$("#qyjbxx_dialog").dialog('destroy');
									}
								});
							});
						});
					},
					'取消': function () {
						$(this).dialog('destroy');
					}
				}
			});
		},
		/**
		 * 获取申请编号
		 */
		getApplyId: function () {
			return comm.getCache("coDeclaration", "entFilling").applyId;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData: function () {
			return $("#qybb_dialog_form").validate({
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
	return aps_qyjbxx;
});