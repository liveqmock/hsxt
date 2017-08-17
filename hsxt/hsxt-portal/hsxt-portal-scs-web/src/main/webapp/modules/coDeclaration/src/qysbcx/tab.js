define(['text!coDeclarationTpl/qysbcx/tab.html',
			'coDeclarationSrc/qysbcx/tgqysbtj',
			'coDeclarationSrc/qysbcx/cyqysbtj',
			'coDeclarationSrc/qysbcx/fwgssbtj',
			//子标签切换
			'coDeclarationSrc/qysbcx/ckxq/sbxx',			
			'coDeclarationSrc/qysbcx/ckxq/qyxtzcxx',
			'coDeclarationSrc/qysbcx/ckxq/qygsdjxx',
			'coDeclarationSrc/qysbcx/ckxq/qylxxx',
			'coDeclarationSrc/qysbcx/ckxq/qyyhzhxx',
			'coDeclarationSrc/qysbcx/ckxq/qysczl',
			'coDeclarationSrc/qysbcx/ckxq/blztxx'  
			],function(tab,tgqysbtj, cyqysbtj , fwgssbtj ,sbxx, qyxtzcxx , qygsdjxx,qylxxx, qyyhzhxx, qysczl,blztxx ){
	return {	 
		showPage : function(){
			$('.operationsInner').html(tab);
			/*
			 *   主标签切换
			 */ 
			$('#qysbcx_tgqysbtj').click(function(e) {				 		 
                tgqysbtj.showPage();
                $('#cx_content_detail').addClass('none');
				comm.liActive($('#qysbcx_tgqysbtj'));
				//添加返回标志
				comm.delCache('coDeclaration','cxType');
				comm.setCache('coDeclaration','cxType', 3);
            }.bind(this));			 
			// 
			$('#qysbcx_cyqysbtj').click(function(e) {
                cyqysbtj.showPage();
                $('#cx_content_detail').addClass('none');
				comm.liActive($('#qysbcx_cyqysbtj'));
				//添加返回标志
				comm.delCache('coDeclaration','cxType');
				comm.setCache('coDeclaration','cxType', 2);
            }.bind(this)); 
			// 
			$('#qysbcx_fwgssbtj').click(function(e) {
                fwgssbtj.showPage();
                $('#cx_content_detail').addClass('none');
				comm.liActive($('#qysbcx_fwgssbtj'));
				//添加返回标志
				comm.delCache('coDeclaration','cxType');
				comm.setCache('coDeclaration','cxType', 4);
            }.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			
			
			//处理编辑时返回到列表事件
			var cxType = comm.getCache('coDeclaration','cxType');
			if(cxType == 2){
				isModulesDefault = true ;
				$('#qysbcx_cyqysbtj').click();
			}else if(cxType == 4){
				isModulesDefault = true ;
				$('#qysbcx_fwgssbtj').click();
			}else if(cxType == 3){
				isModulesDefault = true ;
				$('#qysbcx_tgqysbtj').click();
			}
			
			var match = comm.findPermissionJsonByParentId("030202000000");
			
			//遍历积分账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业申报统计
				 if(match[i].permId =='030202010000')
				 {
					 $('#qysbcx_tgqysbtj').show();
					 if(isModulesDefault == false )
					 {
						 $('#qysbcx_tgqysbtj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
					 }
					 
				 //成员企业申报统计
				 else if(match[i].permId =='030202020000')
				 {
					 $('#qysbcx_cyqysbtj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qysbcx_cyqysbtj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //服务公司申报统计
				 else if(match[i].permId =='030202030000')
				 {
					 $('#qysbcx_fwgssbtj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qysbcx_fwgssbtj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			/*
			 *  子标签切换
			 */
			$('#ckxq_sbxx').click(function(e) {		 
                sbxx.showPage();
				comm.liActive($('#ckxq_sbxx'));
            }.bind(this));  
            
            $('#ckxq_qyxtzcxx').click(function(e) {		 
                qyxtzcxx.showPage();
				comm.liActive($('#ckxq_qyxtzcxx'));
            }.bind(this));  
			
			
			$('#ckxq_qygsdjxx').click(function(e) {		 
                qygsdjxx.showPage();
				comm.liActive($('#ckxq_qygsdjxx'));
            }.bind(this));  
            
            
            $('#ckxq_qylxxx').click(function(e) {		 
                qylxxx.showPage();
				comm.liActive($('#ckxq_qylxxx'));
            }.bind(this));  
            
            $('#ckxq_qyyhzhxx').click(function(e) {		 
                qyyhzhxx.showPage();
				comm.liActive($('#ckxq_qyyhzhxx'));
            }.bind(this));  
            
			$('#ckxq_qysczl').click(function(e) {		 
                qysczl.showPage();
				comm.liActive($('#ckxq_qysczl'));
            }.bind(this));  
            
            $('#ckxq_blztxx').click(function(e) {		
                blztxx.showPage();
				comm.liActive($('#ckxq_blztxx'));
            }.bind(this));  
            
		   //审核确认
		   $('#skqr_tj').click(function(){
		   		$('#skqr_dialog>p').html( skqr_dialogTpl );
		   		$('#skqr_dialog').dialog({
		   			width:480,
		   			buttons:{
		   				'确认' : function(){
		   					$(this).dialog('destroy');
		   				} ,
		   				'取消' : function(){
		   					$(this).dialog('destroy');
		   				}
		   			}
		   		});
		   });	
            
			
		 	//返回列表
		 	$('#ckxq_back').click(function(){
		 		 $('#cx_content_list').removeClass('none');
		 		 $('#cx_content_detail').addClass('none')  ;
		 	});
			
		} 
	}
}); 

