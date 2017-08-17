define(['text!coDeclarationTpl/qysbcx/ckxq/qylxxx.html',
        'coDeclarationDat/qysbcx/ckxq/qylxxx',
		'coDeclarationLan'],function(qylxxxTpl ,dataModoule){
	return {	 	
		showPage : function(){
		 	 this.initData();
		 	 this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_xg').css('display','none');
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		if ( $('#qysbcx_tgqysbtj > a').hasClass('active') ){
		 			$('#tgqysbtj_cx').removeClass('none');
		 			$('#cx_content_detail').addClass('none');	
		 			return ;
		 		}
		 		if ( $('#qysbcx_cyqysbtj > a').hasClass('active') ){
		 			$('#cyqysbtj_cx').removeClass('none');
		 			$('#cx_content_detail').addClass('none');	
		 			return ;
		 		}
		 		if ( $('#qysbcx_fwgssbtj > a').hasClass('active') ){
		 			$('#fwgssbtj_cx').removeClass('none');
		 			$('#cx_content_detail').addClass('none');	
		 			return ;
		 		}
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {"applyId": $("#applyId").val()};
			dataModoule.findLinkmanByApplyId(params, function(res){
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
		}
	}
}); 
