define(['text!coDeclarationTpl/csyw/tab.html',
			'text!coDeclarationTpl/csyw/ckxq/skqr_dialog.html',
			'coDeclarationSrc/csyw/tgqycsyw',
			'coDeclarationSrc/csyw/cyqycsyw',
			'coDeclarationSrc/csyw/fwgscsyw',
			
			//子标签切换
			'coDeclarationSrc/csyw/ckxq/sbxx',
			'coDeclarationSrc/csyw/ckxq/qyxtzcxx',
			'coDeclarationSrc/csyw/ckxq/qygsdjxx',
			'coDeclarationSrc/csyw/ckxq/qylxxx',
			'coDeclarationSrc/csyw/ckxq/qyyhzhxx',
			'coDeclarationSrc/csyw/ckxq/qysczl',
			'coDeclarationSrc/csyw/ckxq/blztxx',
			'coDeclarationDat/csyw/tab',
	        'coDeclarationLan'
			],function(tab, skqr_dialogTpl, tgqycsyw, cyqycsyw, fwgscsyw, sbxx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl, blztxx, dataModoule){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab));
			
			// 
			$('#csyw_tgqycsyw').click(function(e) {				 		 
                tgqycsyw.showPage();
				comm.liActive($('#csyw_tgqycsyw'));
				$('#ckxq_qx').triggerWith('#csyw_tgqycsyw');
            });			 
			
			// 
			$('#csyw_cyqycsyw').click(function(e) {
                cyqycsyw.showPage();
				comm.liActive($('#csyw_cyqycsyw'));
				$('#ckxq_qx').triggerWith('#csyw_cyqycsyw');
            }); 
			// 
			$('#csyw_fwgscsyw').click(function(e) {
                fwgscsyw.showPage();
				comm.liActive($('#csyw_fwgscsyw'));
				$('#ckxq_qx').triggerWith('#csyw_fwgscsyw');
            });  
		 	
		 	/*
			 *  子标签切换
			 */
			$('#ckxq_sbxx').click(function(e) { 
                sbxx.showPage();
				comm.liActive($('#ckxq_sbxx'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            });  
            
            $('#ckxq_qyxtzcxx').click(function(e) {		 
                qyxtzcxx.showPage();
				comm.liActive($('#ckxq_qyxtzcxx'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            });  
			
			
			$('#ckxq_qygsdjxx').click(function(e) {		 
                qygsdjxx.showPage();
				comm.liActive($('#ckxq_qygsdjxx'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            });  
            
            
            $('#ckxq_qylxxx').click(function(e) {		 
                qylxxx.showPage();
				comm.liActive($('#ckxq_qylxxx'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            });  
            
            $('#ckxq_qyyhzhxx').click(function(e) {		 
                qyyhzhxx.showPage();
				comm.liActive($('#ckxq_qyyhzhxx'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            });  
            
			$('#ckxq_qysczl').click(function(e) {		 
                qysczl.showPage();
				comm.liActive($('#ckxq_qysczl'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            });  
            
            $('#ckxq_blztxx').click(function(e) {	
                blztxx.showPage();
				comm.liActive($('#ckxq_blztxx'));
				$('#ckxq_xg').text('修　改');
				$('#ckxq_qx').text('返　回');
				$("#skqr_tj").show();
            }); 
            
            //权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040301000000");
			
			//遍历资源配额一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业初审业务
				 if(match[i].permId =='040301010000')
				 {
					 $('#csyw_tgqycsyw').show();
					 $('#csyw_tgqycsyw').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业初审业务
				 else if(match[i].permId =='040301020000')
				 {
					 $('#csyw_cyqycsyw').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#csyw_cyqycsyw').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //服务公司初审业务
				 else if(match[i].permId =='040301030000')
				 {
					 $('#csyw_fwgscsyw').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#csyw_fwgscsyw').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		 	
		   //审核确认
		   $('#skqr_tj').click(function(){
		   		$('#csyw_skqr_dialog>p').empty().html(skqr_dialogTpl);
			   $("input[name='isPass']").attr('checked', false);
		   		$('#csyw_skqr_dialog').dialog({
		   			width:480,
		   			buttons:{
		   				'确认' : function(){
		   					var params = {};
		   					params.applyId = $("#applyId").val();
							var pass = $("input[name='isPass']:checked").val();
							if(!pass){
								comm.warn_alert('请选择审核结果!');
								return;
							}
		   					params.isPass = (pass == '0');
		   					params.apprRemark = $(this).find('#apprRemark').val();
							var win = $(this);
		   					dataModoule.managerApprDeclare(params, function(res){
		   						comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
		   							win.dialog('destroy');
		   							var custType = $("#custType").val();
		   							if(custType == "4"){
		   								$('#csyw_fwgscsyw').click();
		   							}else if(custType == "2"){
		   								$('#csyw_cyqycsyw').click();
		   							}else{
		   								$('#csyw_tgqycsyw').click();
		   							}
								}});
		   					});
		   				} ,
		   				'取消' : function(){
		   					$(this).dialog('destroy');
		   				}
		   			}
		   		});
		   });	
		} 
	}
}); 

