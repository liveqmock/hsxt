define(['text!platformProxyTpl/dhhsb/dhhsb.html',
        'platformProxyDat/platformProxy',
        'platformProxyLan'], function(dhhsbTpl,dataMoudle){
	var dhhsbFun = {
		limit: null,
		exchangeRate: null,		//转换税率
		showPage: function () {
			$('#busibox').html(_.template(dhhsbTpl));
			var json = [];
			$.fn.bsgrid.init('searchTable', {
				autoLoad: false,
				pageSizeSelect: true,
				pageSize: 10,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false,   //显示空白行
				localData: json
			});

			//查询互生币
			$('#query_btn').click(function () {
				var resNo = $("#companyResNo").val();
				if (comm.isEmpty(resNo)) {
					comm.error_alert(comm.lang("platformProxy").pleaseInputResNo);
					return;
				}
				if (!comm.isServiceResNo(resNo) && !comm.isTrustResNo(resNo) && !comm.isMemberResNo(resNo)) {
					comm.error_alert(comm.lang("platformProxy").isEntHsResNo);
					return;
				}
				dataMoudle.companyInfor({resNo: resNo}, function (res) {
					var data = res.data;
					var json = [];
					if (data != null) {
						json = [{
							"th_1": res.data.entResNo,
							"th_2": res.data.entName,
							"th_3": res.data.contactPerson,
							"th_4": res.data.contactPhone
						}];
						$("#companyCustId").val(data.entCustId);
						$("#companyCustName").val(data.entName);
						$("#formsubmit_div").removeClass("none");
					} else {
						$("#formsubmit_div").addClass("none");
					}

					$.fn.bsgrid.init('searchTable', {
						pageSizeSelect: true,
						pageSize: 10,
						stripeRows: true,  //行色彩分隔 
						displayBlankRows: false,   //显示空白行
						localData: json
					});
					dataMoudle.getEntBuyHsbLimit({hsCustId: data.entCustId}, function (resp) {
						dhhsbFun.limit = resp.data;
					});
					cacheUtil.findCacheSystemInfo(function (resp) {
						$('#currencyCode').val(resp.currencyCode);
						$('#currencyCodeCn').val(resp.currencyNameCn);
						$('#exchangeRate').val(resp.exchangeRate);
						dhhsbFun.exchangeRate = resp.exchangeRate;
					});
				});
			});

			//计算兑换互生币
			$('#orderHsbAmount').keyup(function () {
				dhhsbFun.calculateHSB();
			});

			//提交订单
			$('#buyhsbform_submit').click(function () {
				if (!dhhsbFun.validateData()) {
					return;
				}

				//重新计算互生币
				dhhsbFun.calculateHSB();

				comm.i_confirm(comm.lang("platformProxy").confirmSubmitBuyHsb, function () {
					var params = $("#buyhsbform").serializeJson();
					dataMoudle.saveBuyHsb(params, function (res) {
						comm.yes_alert(comm.lang("platformProxy").buyhsb_success, 400);
						dhhsbFun.showPage();
					});
				});
			});
		},
		/**
		 * 计算互生币转换
		 */
		calculateHSB: function () {
			var hsbAmount = $('#orderHsbAmount').val();
			$('#orderCashAmount').val(hsbAmount * dhhsbFun.exchangeRate);
			$('#orderOriginalAmount').val(hsbAmount);
			$('#orderCashAmountValue').val(comm.formatMoneyNumberAps(hsbAmount * dhhsbFun.exchangeRate));
			$('#orderOriginalAmountValue').val(comm.formatMoneyNumberAps(hsbAmount));
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			return comm.valid({
				formID: '#buyhsbform',
				rules: {
					orderHsbAmount: {
						required: true,
						number: true,
						huobi: true,
						min: Number(dhhsbFun.limit.S_SINGLE_BUY_HSB_MIN),
						max: Number(dhhsbFun.limit.S_SINGLE_BUY_HSB_MAX)
					}
				},
				messages: {
					orderHsbAmount: {
						required: comm.lang("platformProxy").hsbAmountRequired,
						number: comm.lang("platformProxy").hsbAmountNumber,
						huobi: comm.lang("platformProxy").hsbAmountdigits,
						min: comm.lang("platformProxy").buyHsbMin,
						max: comm.lang("platformProxy").buyHsbMax
					}
				}
			});
		}
	};
	return dhhsbFun;
});