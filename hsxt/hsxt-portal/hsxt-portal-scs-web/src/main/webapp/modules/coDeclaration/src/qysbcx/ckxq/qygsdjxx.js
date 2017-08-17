define(['text!coDeclarationTpl/qysbcx/ckxq/qygsdjxx.html',
		'coDeclarationDat/qysbcx/ckxq/qygsdjxx',
		'coDeclarationLan'
        ],function(qygsdjxxTpl, dataModoule){
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
			dataModoule.findDeclareEntByApplyId(params, function(res){
				if(res.data){
					$('#ckxq_view').html(_.template(qygsdjxxTpl, res.data));
					$('#legalCreTypeText').html(comm.getNameByEnumId(res.data.legalCreType, comm.lang("coDeclaration").idCardTypeEnum));
				}
				if(res.data && res.data.licenseAptitude && res.data.licenseAptitude.fileId){
					comm.initPicPreview("#thum-3", res.data.licenseAptitude.fileId, "");
					// .html("<img width='107' height='64' src='" + comm.getFsServerUrl(res.data.licenseAptitude.fileId) + "'/>");
					$("#thum-3").attr('src', comm.getFsServerUrl(res.data.licenseAptitude.fileId));
				}
			});
		}
	}
}); 
