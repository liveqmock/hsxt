define(['text!coDeclarationTpl/csyw/qyxtzcxx.html','text!coDeclarationTpl/csyw/qyxtzcxx_xg.html'], function(qyxtzcxxTpl, qyxtzcxx_xgTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(qyxtzcxxTpl,{num:num})).append(qyxtzcxx_xgTpl);	
			
			/*按钮事件*/
			$('#tgqy_qyxtzcxx_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_qyxtzcxx_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_qyxtzcxx_cancel').triggerWith('#fwgscsyw');
			
			$('#qyxtzcxx_modify').click(function(){
				$('#qyxtzcxxTpl').addClass('none');	
				$('#qyxtzcxx_xgTpl').removeClass('none');
				
				$('#back_qyxtzcxx').triggerWith('#qyxtzcxx');
				
				$('#qyxtzcxx_xg_btn').click(function(){
					/*弹出框*/
					$( "#qyxtzcxx_xg_content" ).dialog({
						title:"系统注册信息修改确认",
						modal:true,
						width:"800",
						height:"450",
						buttons:{ 
							"确定":function(){
								$( "#qyxtzcxx_xg_content" ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#qyxtzcxx_xg_content" ).dialog( "destroy" );
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