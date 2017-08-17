define(['text!resouceManageTpl/qyzygl/ckxq/gsdjxx.html',
        'resouceManageDat/resouceManage',
		'resouceManageLan'],function(gsdjxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#ckxq_view').html(_.template(gsdjxxTpl, data));
			var busiLicenseImg = '';
			if(data){
				busiLicenseImg = data.busiLicenseImg;
				$("#legalCreTypeText").html(comm.getNameByEnumId(data.credentialType, comm.lang("common").creTypes));
				$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
			}
			$('#ModifyBt_gongshang').click(function(){
				$('#qyxx_gsdjxxxg').click();
			});
			
			$("#busi_license_img").click(function(){//营业执照扫描件图片预览
		        comm.initTmpPicPreview("#busi_license_span",comm.getFsServerUrl(busiLicenseImg),"营业执照扫描件预览");
		    });
			
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

 