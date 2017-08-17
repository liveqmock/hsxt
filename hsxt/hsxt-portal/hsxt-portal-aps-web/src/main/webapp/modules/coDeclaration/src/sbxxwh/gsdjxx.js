define(['text!coDeclarationTpl/sbxxwh/gsdjxx.html',
        'coDeclarationDat/sbxxwh/gsdjxx',
        'coDeclarationLan'], function(gsdjxxTpl, dataModoule){
	var self = {
		showPage : function(){
			self.initForm();
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#infobox').html(_.template(gsdjxxTpl, data));
			/*按钮事件*/
			$('#gsdjxx_back').triggerWith('#glqysbzzdwh');
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findDeclareEntByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data){
					$('#infobox').html(_.template(gsdjxxTpl, res.data));
					/*按钮事件*/
					$('#gsdjxx_back').triggerWith('#glqysbzzdwh');
					var data = res.data;
					if(data.licenseAptitude&&data.licenseAptitude.fileId){
						comm.initPicPreview("#licenseImg2", data.licenseAptitude.fileId, "");
						$("#licenseImg2").attr("src", comm.getFsServerUrl(data.licenseAptitude.fileId));
					}
				}
			});
		}
	};
	return self;
});