define(['text!companyInfoTpl/gsdjxx/gsdjxx.html',
        'companyInfoDat/gsdjxx/gsdjxx',
		'companyInfoLan'],function(gsdjxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			var busiLicenseImg = '';
			$('#busibox').html(_.template(gsdjxxTpl, data));
			if(data){
				busiLicenseImg = data.busiLicenseImg;
				if(null != busiLicenseImg && '' != busiLicenseImg){
					$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
				}
			}

			if(data){
				$("#legalCreTypeText").html(comm.getNameByEnumId(data.credentialType, comm.lang("common").creTypes));
			}
			$('#ModifyBt_gongshang').click(function(){
				$('#qyxx_gsdjxxxg').click();
			});
			$("#busi_license_img").click(function(){//托管、成员企业营业执照扫描件图片预览
		        comm.initTmpPicPreview("#busi_license_span",comm.getFsServerUrl(busiLicenseImg),"营业执照扫描件预览");
		    });
		},
		
		
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findEntAllInfo(null, function(res){
					comm.setCache("companyInfo", "entAllInfo", res.data);
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		}
	}
}); 

 