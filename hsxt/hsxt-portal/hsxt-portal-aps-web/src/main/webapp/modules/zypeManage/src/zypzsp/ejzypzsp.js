define(['text!zypeManageTpl/zypzsp/ejzypzsp.html',
		'text!zypeManageTpl/zypzsp/ejzypzsp_sp_dialog.html',
		"zypeManageDat/zypeManage",
		"zypeManageSrc/zype_comm",
		"zypeManageLan"
		], function(ejzypzspTpl, ejzypzsp_sp_dialogTpl,zypeManage,zypecomm){
	var aps_ejzypzsp = {
		showPage: function () {
			$('#busibox').html(_.template(ejzypzspTpl));

			//初始化地区控件
			comm.initProvSelect('#area', {}, 70, null);

			//地区级联
			zypecomm.initArea(function () {
			});

			//加载配置类型
			comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

			//查询事件
			$("#btnQuery").click(function () {
				aps_ejzypzsp.queryPage();
			});

		},
		//分页查询
		queryPage: function () {
			//经办人操作号
			var exeCustId = comm.getCookie("custId");

			//判断操作员是否为管理员
			if (comm.getCookie("isAdmin") == "true") {
				exeCustId = "";
			}

			//查询参数
			var queryParam = {
				"search_proviceNo": $("#area").attr("optionvalue"),
				"search_applyType": $("#type").attr("optionvalue"),
				"search_status": 0, "search_exeCustId": exeCustId
			};
			zypeManage.provinceQuotaPage("searchTable", queryParam, function (record, rowIndex, colIndex, options) {
				if (colIndex == 0) {
					return comm.getProvNameByCode(record["provinceNo"]);
				}

				if (colIndex == 1) {
					return comm.lang("zypeManage").quotaTypeEnume[record.applyType];
				}

				return $("<a>" + comm.lang("zypeManage").approve_title + "</a>").click(function (e) {
					aps_ejzypzsp.shenPi(record);
				});
			});
		},
		shenPi: function (record) {
			//加载审批信息
			zypeManage.getEntResDetail({"mEntResNo": record.entResNo}, function (rsp1) {
				zypeManage.resQuotaQuery({"provinceNo": record.provinceNo}, function (rsp2) {

					var entDetail = rsp1.data;
					var scDetail = rsp2.data;

					detail = {"entDetail": entDetail, "scDetail": scDetail, "applyDetail": record};
					$('#dialogBox').html(_.template(ejzypzsp_sp_dialogTpl, detail));

					//操作员
					comm.groupOperInfo(record.reqOperator, function (info) {
						$("#tdReqOperator").text(info);
					});

				});
			});

			/*弹出框*/
			$("#dialogBox").dialog({
				title: comm.lang("zypeManage").province_res_quota_approve,
				width: "800",
				modal: true,
				buttons: {
					"确定": function () {
						var dl = this;

						if (!aps_ejzypzsp.approveCheck(record)) {
							return false;
						}

						//提交申请
						var approveParam = {
							"applyId": record.applyId,
							"apprNum": $("#txtApprNum").val(),
							"status": $("input[name='radStatus']:checked").val(),
							"apprRemark": $("#txtApprRemark").val()
						};

						zypeManage.provinceQuotaApprove(approveParam, function (rsp) {
							comm.alert({
								content: comm.lang("zypeManage").approve_success_point,
								callOk: function () {
									$(dl).dialog("destroy");
									aps_ejzypzsp.queryPage();//刷新数据
								}
							});
						});
					},
					"取消": function () {
						$(this).dialog("destroy");
					}
				}
			});
			/*end*/
		},
		//审批验证
		approveCheck: function (obj) {
			var reg = /^[1-9]+[0-9]*$/;  //数字格式规范：首个数字必须大于0
			var $apprNum = $("#txtApprNum").val(); //申请数量

			if (!reg.test($apprNum)) {
				comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").input_appr_num});
				return false;
			}

			//批复数量,不可大于申请数
			if (obj.applyNum < parseInt($apprNum)) {
				comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").appr_num_error_point});
				return false;
			}

			return true;
		}
	};
	return aps_ejzypzsp;
});