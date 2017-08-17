define(['text!zypeManageTpl/ejqypepz/ejqypepzcx.html',
		'text!zypeManageTpl/ejqypepz/ejqypepzcx_ck_dialog.html',
		"zypeManageDat/zypeManage","zypeManageLan"
		], function(ejqypepzcxTpl, ejqypepzcx_ck_dialogTpl,zypeManage){
	var aps_ejqypepzcx = {
		showPage: function () {
			$('#busibox').html(_.template(ejqypepzcxTpl));

			//获取身份
			cacheUtil.findCacheSystemInfo(function (sysInfo) {
				cacheUtil.findCacheProvinceByParent(sysInfo.countryNo, function (provinceList) {
					var options = [{name: '', value: ''}];
					$(provinceList).each(function (i, v) {
						options.push({name: v.provinceName, value: v.provinceNo});
					});
					$("#area").selectList({
						options: options,
						optionWidth: 120, // 列表宽度
						optionHeight: 200//列表高度
					});
				});
			});

			//加载配置类型
			comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

			//加载审核状态
			comm.initSelect("#state", comm.lang("zypeManage").quotaStatusEnume);


			//查询事件
			$("#btnQuery").click(function () {
				aps_ejqypepzcx.queryPage();
			});
		},
		//分页查询
		queryPage: function () {
			//查询参数
			var queryParam = {
				"search_proviceNo": $("#area").attr("optionvalue"),
				"search_applyType": $("#type").attr("optionvalue"),
				"search_status": $("#state").attr("optionvalue")
			};
			zypeManage.provinceQuotaPage("searchTable", queryParam, function (record, rowIndex, colIndex, options) {
				if (colIndex == 0) {
					return comm.getProvNameByCode(record['provinceNo']);
				}
				if (colIndex == 1) {
					return comm.lang("zypeManage").quotaTypeEnume[record["applyType"]];
				}
				if (colIndex == 4) {
					return comm.lang("zypeManage").quotaStatusEnume[record["status"]];
				}
				return $("<a>" + comm.lang("zypeManage").quota_query_title + "</a>").click(function (e) {
					aps_ejqypepzcx.chaKan(record);
				});
			});

		},
		chaKan: function (obj) {
			zypeManage.provinceQuotaDetail({"applyId": obj.applyId}, function (rsp) {
				var data = rsp.data;
				$('#dialogBox').html(_.template(ejqypepzcx_ck_dialogTpl, data));

				if (comm.isNotEmpty(data.reqOperator)) {//申请操作员信息
					comm.groupOperInfo(data.reqOperator, function (info) {
						$("#tdReqOperator").text(info);
					});//申请操作员信息
				}
				if (comm.isNotEmpty(data.apprOperator)) {//审批操作员信息
					comm.groupOperInfo(data.apprOperator, function (info) {
						$("#tdApprOperator").text(info);
					});
				}
			});

			/*弹出框*/
			$("#dialogBox").dialog({
				title: comm.lang("zypeManage").province_res_quota_config_detail,
				width: "800",
				modal: true,
				buttons: {
					"确定": function () {
						$(this).dialog("destroy");
					}
				}
			});
			/*end*/
		}
	};
	return aps_ejqypepzcx;
});