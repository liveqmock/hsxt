define(['text!coDeclarationTpl/qysbfk/ckxq/sbxx.html',
        'coDeclarationSrc/qysbfk/point_choose',
        'coDeclarationDat/qysbfk/ckxq/sbxx',
		'coDeclarationLan'],function(sbxxTpl, pointChoose, dataModoule){
	var mcs_qysbfk_sbxx = {
		showPage: function () {
			comm.delCache("coDeclaration", "pickResNo");
			comm.delCache("coDeclaration", "oldPickResNo");
			mcs_qysbfk_sbxx.initForm();
			mcs_qysbfk_sbxx.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm: function (data) {
			$('#ckxq_view').html(_.template(sbxxTpl, data));
			var custType = $("#custType").val();//申请类别
			if (custType == "4") {
				$('#ckxq_xg').show();
				$('#cust_type_04').show();
				$('#cust_type_0203').hide();
				$('#cust_type_04_01').show();
			} else {
				$('#ckxq_xg').hide();
				$('#cust_type_04').hide();
				$('#cust_type_0203').show();
				$('#cust_type_04_01').hide();
			}

			$("#cust_type_04").find("input:radio").attr("disabled", true);

			//取消
			$('#ckxq_qx').click(function () {
				$("#select_btn").hide();
				if ($('#ckxq_xg').text() == '保　存') {
					$('#ckxq_xg').text('修　改');
					$("#cust_type_04").find("input:radio").attr("disabled", true);
				} else {
					mcs_qysbfk_sbxx.gotoList();
				}
			});

			//修改
			$('#ckxq_xg').click(function () {
				$("#entResNo").show();
				$("input[name='chkType']").attr("disabled", false);
				$("#select_btn").hide();
				if ($(this).text() != '保　存') {
					$(this).text('保　存');
				} else {
					mcs_qysbfk_sbxx.managePickResNo();
				}
			});

			//顺序选配
			$('#sxxp1').click(function () {
				mcs_qysbfk_sbxx.findSerResNo(data);
				$("#entResNo").val("");
				$("#select_btn").hide();
			});

			//人工选配
			$('#rgxp1').click(function () {
				$("#entResNo").val("");
				$("#select_btn").show();
			});

			//选择互生号
			$('#select_btn').click(function () {
				pointChoose.findServicesPointList(data.countryNo, data.provinceNo, data.cityNo, "#entResNo");
			});

			if (custType == "3") {//托管企业-显示启用资源类型
				if (data) {
					$("#toBuyResRange").html(comm.getNameByEnumId(data.toBuyResRange, comm.lang("coDeclaration").toBuyResRangeEnum));
				}
			} else if (custType == "2") {//成员企业-显示启用资源类型
				if (data) {
					$("#toBuyResRange").html(comm.getNameByEnumId(data.toBuyResRange, comm.lang("coDeclaration").cyToBuyResRangeEnum));
				}
			} else {//服务公司-隐藏启用资源类型
				if (data) {
					if (data.toEntResNo) {
						if (data.toSelectMode == 0) {//顺序
							$("#sxxp1").attr("checked", true);
						} else {//人工
							$("#rgxp1").attr("checked", true);
						}
						if (comm.removeNull(data.toEntResNo) != "") {
							$("#entResNo").show();
						} else {
							$("#entResNo").hide();
						}
						$("#entResNo").val(data.toEntResNo);
						$("#old-entResNo").val(data.toEntResNo);
					} else {
						$("input[name='chkType']").attr("checked", false);
					}
					$("#fwBuyResRange").html(comm.getNameByEnumId(data.toBusinessType, comm.lang("common").toBusinessTypes));
				}
			}
			if (data) {
				if (data.toEntResNo) {
					comm.setCache("coDeclaration", "pickResNo", data.toEntResNo);
					comm.setCache("coDeclaration", "oldPickResNo", data.toEntResNo);
				}
				//获取操作员
				cacheUtil.searchOperByCustId(data.applyOperator, function (res) {
					$("#applyOperator").html(comm.getOperNoName(res));
				});
			}
		},
		/**
		 * 查询服务公司可用互生号--顺序选号
		 */
		findSerResNo: function (data) {
			var params = {};
			params.countryNo = data.countryNo;
			params.provinceNo = data.provinceNo;
			params.cityNo = data.cityNo;
			dataModoule.findSerResNo(params, function (res) {
				$("#entResNo").val(res.data);
				comm.setCache("coDeclaration", "pickResNo", res.data);
			});
		},
		/**
		 * 管理公司为服务公司选号
		 */
		managePickResNo: function () {
			var pickResNo = $("#entResNo").val();
			if ($("#rgxp1").attr("checked") && pickResNo == "") {
				comm.error_alert(comm.lang("coDeclaration")[33266]);
				return;
			}
			var params = {};
			params.applyId = $("#applyId").val();
			params.pickResNo = pickResNo;
			params.toSelectMode = comm.removeNull($('input[name="chkType"]:checked').val());
			dataModoule.managePickResNo(params, function (res) {
				comm.alert({
					content: comm.lang("coDeclaration")[22000], callOk: function () {
						$("#old-entResNo").val(pickResNo);
						comm.setCache("coDeclaration", "oldPickResNo", pickResNo);
					}
				});
			});
		},
		/**
		 * 是否保存过数据
		 */
		isSaveData: function () {
			var pickResNo = comm.getCache("coDeclaration", "pickResNo");
			var oldentResNo_ = comm.getCache("coDeclaration", "oldPickResNo");
			if (!pickResNo) {
				return false;
			}
			if (pickResNo == oldentResNo_) {
				return true;
			}
			return false;
		},
		/**
		 * 检查数据
		 */
		checkData: function () {
			var pickResNo = comm.getCache("coDeclaration", "pickResNo");
			var oldentResNo_ = comm.getCache("coDeclaration", "oldPickResNo");
			if (!pickResNo) {
				return {isPass: false, message: comm.lang("coDeclaration").chooseResNo};
			}
			if (pickResNo == oldentResNo_) {
				return {isPass: true};
			}
			return {isPass: false, message: comm.lang("coDeclaration").sbxxNotSave};
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {"applyId": $("#applyId").val()};
			dataModoule.findDeclareAppInfoByApplyId(params, function (res) {
				if (res.data) {
					mcs_qysbfk_sbxx.initForm(res.data);
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
	return mcs_qysbfk_sbxx;
}); 
