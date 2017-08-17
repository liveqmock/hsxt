define(['text!zypeManageTpl/zypzspcx/ejzypzspcx.html',
		'text!zypeManageTpl/zypzspcx/ejzypzspcx_ck_dialog.html',
		"zypeManageDat/zypeManage",
		"zypeManageSrc/zype_comm",
		"zypeManageLan"
		], function(ejzypzspcxTpl, ejzypzspcx_ck_dialogTpl,zypeManage,zypecomm){
	var aps_ejzypzspcx = {
		showPage: function () {
			$('#busibox').html(_.template(ejzypzspcxTpl));


			//加载地区缓存
			cacheUtil.findProvCity();

			//初始化地区控件
			comm.initProvSelect('#area', {}, 70, null);

			//地区级联
			zypecomm.initArea(function () {
			});

			//加载配置类型
			comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

			//查询事件
			$("#btnQuery").click(function () {
				aps_ejzypzspcx.queryPage();
			});
			/*end*/
		},
		//分页查询
		queryPage: function () {

			//查询参数
			var queryParam = {
				"search_proviceNo": $("#area").attr("optionvalue"),
				"search_applyType": $("#type").attr("optionvalue")
			};
			zypeManage.provinceQuotaPage("searchTable", queryParam, function (record, rowIndex, colIndex, options) {
				if (colIndex == 0) {
					return comm.getProvNameByCode(record["provinceNo"]);
				}

				if (colIndex == 1) {
					return comm.lang("zypeManage").quotaTypeEnume[record.applyType];
				}

				return $("<a>" + comm.lang("zypeManage").quota_query_title + "</a>").click(function (e) {
					aps_ejzypzspcx.chaKan(record);
				});
			});

		},
		chaKan: function (record) {
			zypeManage.provinceQuotaDetail({"applyId": record.applyId}, function (rsp) {
				var data = rsp.data;
				$('#dialogBox').html(_.template(ejzypzspcx_ck_dialogTpl, data));

				comm.groupOperInfo(data.reqOperator, function (info) {
					$("#tdReqOperator").text(info);
				});//申请操作员信息
				comm.groupOperInfo(data.apprOperator, function (info) {
					$("#tdApprOperator").text(info);
				});//复核操作员信息

			});

			/*弹出框*/
			$("#dialogBox").dialog({
				title: comm.lang("zypeManage").province_res_quota_detail,
				width: "800",
				modal: true,
				buttons: {
					"确定": function () {
						$(this).dialog("destroy");
					}
				}
			});
		}
	};
	return aps_ejzypzspcx;
});