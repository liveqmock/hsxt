define(['text!coDeclarationTpl/csyw/qysczl.html','text!coDeclarationTpl/csyw/qysczl_xg.html'], function(qysczlTpl, qysczl_xgTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(qysczlTpl,{num:num})).append(qysczl_xgTpl);	
			
			/*按钮事件*/
			$('#tgqy_qysczl_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_qysczl_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_qysczl_cancel').triggerWith('#fwgscsyw');
			
			$('#qysczl_modify').click(function(){
				$('#qysczlTpl').addClass('none');	
				$('#qysczl_xgTpl').removeClass('none');
				
				$('#back_qysczl').triggerWith('#qysczl');

				
				$('#qysczl_xg_btn').click(function(){
					/*弹出框*/
					$( "#qysczl_xg_content" ).dialog({
						title:"企业上传资料修改确认",
						modal:true,
						width:"800",
						height:"450",
						buttons:{ 
							"确定":function(){
								$( "#qysczl_xg_content" ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#qysczl_xg_content" ).dialog( "destroy" );
							}
						}
				
					  });
					/*end*/		
				});
			});
			/*end*/
		}	
	}
});