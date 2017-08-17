define(['text!coDeclarationTpl/csyw/qylxxx.html','text!coDeclarationTpl/csyw/qylxxx_xg.html'], function(qylxxxTpl, qylxxx_xgTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(qylxxxTpl,{num:num})).append(qylxxx_xgTpl);	
			
			/*按钮事件*/		
			$('#tgqy_qylxxx_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_qylxxx_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_qylxxx_cancel').triggerWith('#fwgscsyw');
			
			$('#qylxxx_modify').click(function(){
				$('#qylxxxTpl').addClass('none');	
				$('#qylxxx_xgTpl').removeClass('none');
				
				$('#back_qylxxx').triggerWith('#qylxxx');
				
				$('#qylxxx_xg_btn').click(function(){
					/*弹出框*/
					$( "#qylxxx_xg_content" ).dialog({
						title:"企业联系信息修改确认",
						modal:true,
						width:"800",
						height:"450",
						buttons:{ 
							"确定":function(){
								$( "#qylxxx_xg_content" ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#qylxxx_xg_content" ).dialog( "destroy" );
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