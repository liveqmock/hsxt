define(['text!coDeclarationTpl/qysbcx/ckxq/qysczl.html',
			'coDeclarationDat/qysbcx/ckxq/qysczl',
			'coDeclarationLan'],function( qysczlpl ,dataModoule){
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
			dataModoule.findAptitudeByApplyId(params, function(res){
				$('#ckxq_view').html(_.template(qysczlpl));
				$("#aptRemark").html(comm.removeNull(res.data.aptRemark));//备注信息
				var realList = res.data.realList;//附件信息
				var venBefFlag = res.data.venBefFlag;//是否需要填写创业帮扶协议
				if(realList){
					for(var k in realList){
						var title = comm.getNameByEnumId(realList[k].aptitudeType, comm.lang("coDeclaration").aptitudeTypeEnum);
						comm.initPicPreview("#preview-"+realList[k].aptitudeType, realList[k].fileId, title);
						$("#preview-"+realList[k].aptitudeType).html("<img width='107' height='64' src='"+comm.getFsServerUrl(realList[k].fileId)+"'/>");
					}
				}
				if(venBefFlag == "true"){
					$("#li_venBefProtocolId").show();
					$("#li-venBefProtocolId").show();
				}else{
					$("#li_venBefProtocolId").hide();
					$("#li-venBefProtocolId").hide();
				}
				$("#venBefFlag").val(venBefFlag);
				if(res.data.link){
					$('#ckxq_view').html(_.template(qysczlpl, res.data.link));
				}
			});
		},
	}
}); 
