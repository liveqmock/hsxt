define(['text!coDeclarationTpl/sptjcx/ckxq/qysczl.html',
        'coDeclarationDat/sptjcx/ckxq/qysczl',
		'coDeclarationLan'],function(qysczlpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(qysczlpl)) ;
		 	$('#ckxq_xg').css('display','');
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		$('#cx_content_list').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {"applyId": $("#applyId").val()};
			dataModoule.findAptitudeByApplyId(params, function(res){
				var realList = res.data.realList;//附件信息
				var creType = res.data.creType;//证件类型
				if(realList){
					for(var k in realList){
						//护照
						if(creType&&creType==2&&realList[k].aptitudeType==2){continue;}
						var title = comm.getNameByEnumId(realList[k].aptitudeType, comm.lang("coDeclaration").aptitudeTypeEnum);
						if(comm.isNotEmpty(realList[k].fileId)){
							$('#li-'+realList[k].aptitudeType).css('display','block');
						}
						comm.initPicPreview("#preview-"+realList[k].aptitudeType, realList[k].fileId, title);
						$("#preview-"+realList[k].aptitudeType).html("<img width='107' height='64' src='"+comm.getFsServerUrl(realList[k].fileId)+"'/>");
					}
				}
				$("#view_aptRemark").html(comm.removeNull(res.data.aptRemark));//备注说明
			});
		}
	}
}); 
