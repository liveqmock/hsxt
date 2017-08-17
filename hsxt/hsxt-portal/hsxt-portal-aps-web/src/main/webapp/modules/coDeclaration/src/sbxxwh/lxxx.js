define(['text!coDeclarationTpl/sbxxwh/lxxx.html',
        'coDeclarationDat/sbxxwh/lxxx',
        'coDeclarationLan'], function(lxxxTpl, dataModoule){
	var  self =  {
		showPage : function(){
			self.initData();
			self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#infobox').html(_.template(lxxxTpl));
			/*按钮事件*/
			$('#lxxx_back').triggerWith('#glqysbzzdwh');
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findLinkmanByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data.link){
					res.data.link.venBefFlag = res.data.venBefFlag;
					$('#infobox').html(_.template(lxxxTpl, res.data.link));
					/*按钮事件*/
					$('#lxxx_back').triggerWith('#glqysbzzdwh');
				}
				var data = res.data.link;
				if(data && data.certificateFile){
					comm.initPicPreview("#img", data.certificateFile, "");
					$("#img").attr("src", comm.getFsServerUrl(data.certificateFile));
				}
				if(data.protocolAptitude&&data.protocolAptitude.fileId){
					comm.initPicPreview("#helpxys2", data.protocolAptitude.fileId, "");
					$("#helpxys2").attr("src", comm.getFsServerUrl(data.protocolAptitude.fileId));
				}
			});
		},
	};
	return self;
});