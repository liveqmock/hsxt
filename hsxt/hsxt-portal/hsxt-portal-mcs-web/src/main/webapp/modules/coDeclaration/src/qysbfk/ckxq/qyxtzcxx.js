define(['text!coDeclarationTpl/qysbfk/ckxq/qyxtzcxx.html',
		'text!coDeclarationTpl/qysbfk/ckxq/qyxtzcxx_dialog.html',
        'coDeclarationDat/qysbfk/ckxq/qyxtzcxx',
		'coDeclarationLan'
		],function(qyxtzcxxTpl, qyxtzcxx_dialogTpl, dataModoule){
	var mcs_qysbfk_qyxizcxx = {
		showPage: function () {
			$('#ckxq_view').html(_.template(qyxtzcxxTpl));
			mcs_qysbfk_qyxizcxx.initData();
			$('#ckxq_xg').css('display', '');
			$('#ckxq_qx').click(function () {
				if ($('#ckxq_xg').text() == '保　存') {
					$('#ckxq_xg').text('修　改');
					$("#ckxq_qx").text('返　回');
					$("#skqr_tj").show();
					$('#qyxtzcxx_1').removeClass('none');
					$('#qyxtzcxx_2').addClass('none');
				} else {
					mcs_qysbfk_qyxizcxx.gotoList();
				}
			});
			//修改
			$('#ckxq_xg').click(function () {
				if ($(this).text() != '保　存') {
					$('#qyxtzcxx_1').addClass('none');
					$('#qyxtzcxx_2').removeClass('none');
					$(this).text('保　存');
					$("#ckxq_qx").text('取　消');
					$("#skqr_tj").hide();
				} else {
					mcs_qysbfk_qyxizcxx.initChangeData();
				}
			});
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			dataModoule.findDeclareByApplyId({"applyId": $("#applyId").val()}, function (res) {
				mcs_qysbfk_qyxizcxx.initShowForm(res.data, false);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 * @param isCallBack 是否是回调成功
		 */
		initShowForm: function (data, isCallBack) {
			comm.delCache('coDeclaration', 'qysbfk-qyxtzcxx-info');//保存前数据
			comm.setCache('coDeclaration', 'qysbfk-qyxtzcxx-info', data);
			if (data) {
				$('#ckxq_view').html(_.template(qyxtzcxxTpl, data));
				//获取币种
				cacheUtil.findCacheSystemInfo(function (sysRes) {
					$('#currencyText-1').html(sysRes.currencyNameCn);
					$('#currencyText-2').val(sysRes.currencyNameCn);
				});
				//获取地区信息
				cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function (resdata) {
					$("#placeName-1").html(resdata);
					$("#placeName-2").val(resdata);
				});
			}
			if (isCallBack) {
				$('#ckxq_xg').text('修改');
				$('#ckxq_xg').click();
				$("#skqr_tj").show();
			}
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#qyxtzcxx_form").validate({
				rules: {
					toEntCustName: {
						required: true,
						rangelength: [2, 128]
					},
					toEntEnName: {
						rangelength: [2, 128]
					}
				},
				messages: {
					toEntCustName: {
						required: comm.lang("coDeclaration")[33201],
						rangelength: comm.lang("coDeclaration")[33202]
					},
					toEntEnName: {
						rangelength: comm.lang("coDeclaration")[33203]
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
		 * 表单校验
		 */
		validateViewMarkData: function () {
			return $("#qyxtzcxx_dialog_form").validate({
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
						rangelength: comm.lang("coDeclaration")[33204]
					},
					doubleUserName: {
						required: comm.lang("coDeclaration")[33255]
					},
					doublePassword: {
						required: comm.lang("coDeclaration")[33256]
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
		 * 初始化保存页面
		 */
		initChangeData: function () {
			if (!mcs_qysbfk_qyxizcxx.validateData().form()) {
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function () {
				var desc = $(this).parents()[0].previousElementSibling.innerText;
				ckArray.push({name: this.name, value: this.value, desc: desc});
			});
			var odata = comm.getCache('coDeclaration', 'qysbfk-qyxtzcxx-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			for (var key in ckArray) {
				if (comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value) {
					trs += "<tr><td class=\"view_item\">" + ckArray[key].desc + "</td><td style='word-break:break-all'  class=\"view_text\">" + comm.removeNull(odata[ckArray[key].name]) + "</td><td style='word-break:break-all'  class=\"view_text\">" + ckArray[key].value + "</td></tr>";
					chg[ckArray[key].name] = {"old": comm.removeNull(odata[ckArray[key].name]), "new": ckArray[key].value};
					ndata[ckArray[key].name] = ckArray[key].value;
				}
			}
			if (trs == "") {
				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
				return;
			}
			//保存
			$('#qyxtzcxx_dialog > p').html(_.template(qyxtzcxx_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
			$('#qyxtzcxx_dialog').dialog({
				width: 800,
				buttons: {
					'确定': function () {
						if (!mcs_qysbfk_qyxizcxx.validateViewMarkData().form()) {
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function (verifyRes) {
							ndata.dblOptCustId = verifyRes.data;
							ndata.optRemark = $("#viewMark").val();
							ndata.changeContent = JSON.stringify(chg);
							dataModoule.saveDeclare(ndata, function (res) {
								comm.alert({
									content: comm.lang("coDeclaration")[22000], callOk: function () {
										$('#qyxtzcxx_dialog').dialog('destroy');
										//mcs_qysbfk_qyxizcxx.initShowForm(ndata, true);
										$('#ckxq_qyxtzcxx').click();
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
		 * 返回至列表
		 */
		gotoList: function () {
			var custType = $("#custType").val();
			if (custType == "4") {
				$('#qysbfk_fwgssbfk').click();
			} else if (custType == "2") {
				$('#qysbfk_cyqysbfk').click();
			} else {
				$('#qysbfk_tgqysbfk').click();
			}
		}
	};
	return mcs_qysbfk_qyxizcxx;
}); 
