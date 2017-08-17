define(['text!coDeclarationTpl/sptjcx/ckxq/qygsdjxx.html',
        'coDeclarationDat/sptjcx/ckxq/qygsdjxx',
		'coDeclarationLan'],function(qygsdjxxTpl, dataModoule){
	return {	 	
		showPage : function(){
		 	this.initData();
		 	this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').empty().html(_.template( qygsdjxxTpl));
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findDeclareEntByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data){
					$('#ckxq_view').html(_.template(qygsdjxxTpl, res.data));
					$('#legalCreTypeText').html(comm.getNameByEnumId(res.data.legalCreType, comm.lang("coDeclaration").idCardTypeEnum));
				}
				if(res.data && res.data.licenseAptitude && res.data.licenseAptitude.fileId){
					comm.initPicPreview("#thum-3", res.data.licenseAptitude.fileId, "");
					$("#thum-3").attr('src', comm.getFsServerUrl(res.data.licenseAptitude.fileId));
				}
			});
		}
	}
}); 
