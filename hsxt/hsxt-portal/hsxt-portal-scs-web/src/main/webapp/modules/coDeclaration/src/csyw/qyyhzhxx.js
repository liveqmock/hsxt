define(['text!coDeclarationTpl/csyw/qyyhzhxx.html','text!coDeclarationTpl/csyw/qyyhzhxx_xg.html'], function(qyyhzhxxTpl, qyyhzhxx_xgTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(qyyhzhxxTpl,{num:num})).append(qyyhzhxx_xgTpl);	
			
			/*按钮事件*/		
			$('#tgqy_qyyhzhxx_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_qyyhzhxx_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_qyyhzhxx_cancel').triggerWith('#fwgscsyw');
			
			$('#qyyhzhxx_modify').click(function(){
				$('#qyyhzhxxTpl').addClass('none');	
				$('#qyyhzhxx_xgTpl').removeClass('none');
				
				$('#back_qyyhzhxx').triggerWith('#qyyhzhxx');
				
				/*自动完成组合框*/
				$("#brandSelect").combobox();
				$(".ui-autocomplete").css({
					"max-height":"250px",
					"overflow-y":"auto",
					"overflow-x":"hidden",
					"height":"250px",
					"border":"1px solid #CCC"
				});
				$(".combobox_style").find("a").attr("title","显示所有选项");
				/*end*/
				
				/*下拉列表*/
				$("#province_slt").selectList({
					width:220,
					options:[
						{name:'广东省',value:'100'},
						{name:'江西省',value:'101'},
						{name:'湖南省',value:'102'}
					]
				}).change(function(e){
				});
				
				$("#city_slt").selectList({
					width:220,
					options:[
						{name:'深圳市',value:'100'},
						{name:'南昌市',value:'101'},
						{name:'长沙市',value:'102'}
					]
				}).change(function(e){
				});
				/*end*/
				
				$('#qyyhzhxx_xg_btn').click(function(){
					/*弹出框*/
					$( "#qyyhzhxx_xg_content" ).dialog({
						title:"企业银行帐户信息修改确认",
						modal:true,
						width:"800",
						height:"450",
						buttons:{ 
							"确定":function(){
								$( "#qyyhzhxx_xg_content" ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#qyyhzhxx_xg_content" ).dialog( "destroy" );
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