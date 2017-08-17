define(['text!resouceManageTpl/qyzygl/ckxq/qysczl.html',
        'resouceManageDat/resouceManage',
		'resouceManageLan'],function(qysczlTpl, dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#ckxq_view').html(_.template(qysczlTpl, data));
			if(data.legalPersonPicFront){
				comm.initPicPreview("#preview-1", data.legalPersonPicFront, "");
				$("#preview-1").html("<img width='107' height='64' src='"+comm.getFsServerUrl(data.legalPersonPicFront)+"'/>");
			}
			if(data.legalPersonPicBack){
				comm.initPicPreview("#preview-2", data.legalPersonPicBack, "");
				$("#preview-2").html("<img width='107' height='64' src='"+comm.getFsServerUrl(data.legalPersonPicBack)+"'/>");
			}
			if(data.busiLicenseImg){
				comm.initPicPreview("#preview-3", data.busiLicenseImg, "");
				$("#preview-3").html("<img width='107' height='64' src='"+comm.getFsServerUrl(data.busiLicenseImg)+"'/>");
			}
			if(data.orgCodeImg){
				comm.initPicPreview("#preview-6", data.orgCodeImg, "");
				$("#preview-6").html("<img width='107' height='64' src='"+comm.getFsServerUrl(data.orgCodeImg)+"'/>");
			}
			if(data.taxRegImg){
				comm.initPicPreview("#preview-7", data.taxRegImg, "");
				$("#preview-7").html("<img width='107' height='64' src='"+comm.getFsServerUrl(data.taxRegImg)+"'/>");
			}
			//特殊处理托管企业创业资源类型
			if(data.custType == 3 && data.startResType == 2){
				$("#li_venBefProtocolId").show();
				if(data.helpAgreement){
					comm.initPicPreview("#preview-8", data.helpAgreement, "");
					$("#preview-8").html("<img width='107' height='64' src='"+comm.getFsServerUrl(data.helpAgreement)+"'/>");
				}
			}
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			var entAllInfo = comm.getCache("resouceManage", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findCompanyAllInfo({companyCustId:$("#resEntCustId").val()}, function(res){
					comm.setCache("resouceManage", "entAllInfo", res.data);
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		}
	}
}); 

