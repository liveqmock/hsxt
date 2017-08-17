define(['text!coDeclarationTpl/sptjcx/ckxq/qysczl.html',
        'coDeclarationDat/sptjcx/ckxq/qysczl',
		'coDeclarationLan'],function(qysczlpl, dataModoule){
	var aps_sptjcx_qysczl =  {
		showPage: function () {
			aps_sptjcx_qysczl.initForm();
			aps_sptjcx_qysczl.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm: function () {
			$('#ckxq_view').html(_.template(qysczlpl));
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {"applyId": $("#applyId").val()};
			dataModoule.findAptitudeByApplyId(params, function (res) {
				var realList = res.data.realList;//附件信息
				var creType = res.data.creType;//证件类型
				if (realList) {
					for (var k in realList) {
						//护照
						if(creType&&creType==2&&realList[k].aptitudeType==2){continue;}
						var title = comm.getNameByEnumId(realList[k].aptitudeType, comm.lang("coDeclaration").aptitudeTypeEnum);
						comm.initPicPreview("#preview-" + realList[k].aptitudeType, realList[k].fileId, title);
						$("#preview-" + realList[k].aptitudeType).html("<img width='107' height='64' src='" + comm.getFsServerUrl(realList[k].fileId) + "'/>");
					}
				}
				if (res.data.venBefFlag == "true") {//如果为托管企业并且选择的是创业资源才需要填写创业帮扶协议
					$("#li-venBefProtocolId-1").show();
				}else{
					$("#li-venBefProtocolId-1").hide();
				}
				if(creType && creType == 2) {
					$('#preview-2-div').hide();
				}else{
					$('#preview-2-div').show();
				}
				$("#view_aptRemark").html(comm.removeNull(res.data.aptRemark));//备注说明
			});
		}
	};
	return aps_sptjcx_qysczl;
}); 
