define(['text!coDeclarationTpl/sptjcx/ckxq/qylxxx.html',
        'coDeclarationDat/sptjcx/ckxq/qylxxx',
		'coDeclarationLan'],function(qylxxxTpl, dataModoule){
	return {	 	
		showPage : function(){
		 	this.initData();
		 	this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(qylxxxTpl));
		 	$('#ckxq_xg').css('display','');
		 	$('#ckxq_qx').click(function(){
		 		$('#cx_content_list').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findLinkmanByApplyId({"applyId": $("#applyId").val()}, function(res){
				if(res.data.link){
					$('#ckxq_view').html(_.template(qylxxxTpl, res.data.link));
				}
				if(res.data.link && res.data.link.certificateFile){
					comm.initPicPreview("#img", res.data.link.certificateFile, "");
					$("#img").attr("src", comm.getFsServerUrl(res.data.link.certificateFile));
				}
				if(res.data.venBefFlag == "true"){
					$("#venBef_tr").show();
					if(res.data.link && res.data.link.protocolAptitude && res.data.link.protocolAptitude.fileId){
						comm.initPicPreview("#img_1", res.data.link.protocolAptitude.fileId, "");
						$("#img_1").attr("src", comm.getFsServerUrl(res.data.link.protocolAptitude.fileId));
					}
				}
			});
		},
	}
}); 
