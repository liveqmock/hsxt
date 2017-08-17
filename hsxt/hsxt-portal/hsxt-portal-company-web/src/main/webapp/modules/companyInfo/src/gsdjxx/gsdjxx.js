define(['text!companyInfoTpl/gsdjxx/gsdjxx.html',
		'text!companyInfoTpl/gsdjxx/gsdjxx_xg.html',
        'companyInfoDat/gsdjxx/gsdjxx',
		'companyInfoLan'
	], function (gsdjxxTpl, gsdjxx_xgTpl, dataModoule) {
	var self = {
		showPage : function(){
			self.initForm();
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(gsdjxxTpl, data));
			if(data){
				var busiLicenseImg = data.busiLicenseImg;
				if(comm.isNotEmpty(busiLicenseImg)){
					comm.initPicPreview("#busi_license_img", busiLicenseImg, "");
					comm.initPicPreview("#bimg", busiLicenseImg, "");
					$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
					$("#busi_license_img2").attr("src", comm.getFsServerUrl(busiLicenseImg));
				}
			}
			
			$('#ModifyBt_gongshang').click(function(){
				$('#gsdjxx_xg').click();
			});
		},
		/**
		 * 绑定图片预览
		 */
		initPicPreview : function(){
			var btnIds = ['#certificate'];
			var imgIds = ['#bimg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findEntAllInfo(null, function(res){
				comm.setCache("companyInfo", "entAllInfo", res.data);
				self.initForm(res.data);
			});
		}
	};
	return self;
});