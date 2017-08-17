define(['text!coDeclarationTpl/csyw/qygsdjxx.html','text!coDeclarationTpl/csyw/qygsdjxx_xg.html'], function(qygsdjxxTpl, qygsdjxx_xgTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(qygsdjxxTpl,{num:num})).append(qygsdjxx_xgTpl);	
			
			/*按钮事件*/
			$('#tgqy_qygsdjxx_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_qygsdjxx_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_qygsdjxx_cancel').triggerWith('#fwgscsyw');
			
			$('#qygsdjxx_modify').click(function(){
				$('#qygsdjxxTpl').addClass('none');	
				$('#qygsdjxx_xgTpl').removeClass('none');
				
				/*日期控件*/
				$('#establishDate').datepicker({dateFormat:'yy-mm-dd'});	
				$('#yyDate').datepicker({dateFormat:'yy-mm-dd'});
				/*end*/
				
				$('#back_qygsdjxx').triggerWith('#qygsdjxx');
				
				$('#qygsdjxx_xg_btn').click(function(){
					/*弹出框*/
					$( "#qygsdjxx_xg_content" ).dialog({
						title:"企业工商登记信息修改确认",
						modal:true,
						width:"800",
						height:"450",
						buttons:{ 
							"确定":function(){
								$( "#qygsdjxx_xg_content" ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#qygsdjxx_xg_content" ).dialog( "destroy" );
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