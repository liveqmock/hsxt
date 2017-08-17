define(['text!coDeclarationTpl/sptjcx/tab.html',
		'coDeclarationSrc/sptjcx/tgqysptj',
		'coDeclarationSrc/sptjcx/cyqysptj',
		'coDeclarationSrc/sptjcx/fwgssptj',
		
		//子标签切换
			'coDeclarationSrc/sptjcx/ckxq/sbxx',			
			'coDeclarationSrc/sptjcx/ckxq/qyxtzcxx',
			'coDeclarationSrc/sptjcx/ckxq/qygsdjxx',
			'coDeclarationSrc/sptjcx/ckxq/qylxxx',
			'coDeclarationSrc/sptjcx/ckxq/qyyhzhxx',
			'coDeclarationSrc/sptjcx/ckxq/qysczl',
			'coDeclarationSrc/sptjcx/ckxq/blztxx'  
		], function(tab, tgqysptj, cyqysptj, fwgssptj , 	sbxx, qyxtzcxx , qygsdjxx,qylxxx, qyyhzhxx, qysczl,blztxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#tgqysptj').click(function(){
				tgqysptj.showPage();
				$('#busibox').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
				comm.liActive($('#tgqysptj'));
				//添加返回标志
				comm.delCache('coDeclaration','cxType');
				comm.setCache('coDeclaration','cxType', 3);
			}.bind(this));
			
			$('#cyqysptj').click(function(){
				cyqysptj.showPage();
				$('#busibox').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
				comm.liActive($('#cyqysptj'));
				//添加返回标志
				comm.delCache('coDeclaration','cxType');
				comm.setCache('coDeclaration','cxType', 2);
			}.bind(this));
			
			$('#fwgssptj').click(function(){
				fwgssptj.showPage();
				$('#busibox').removeClass('none');
		 		$('#cx_content_detail').addClass('none');
				comm.liActive($('#fwgssptj'));
				//添加返回标志
				comm.delCache('coDeclaration','cxType');
				comm.setCache('coDeclaration','cxType', 4);
			}.bind(this));
			
			//处理编辑时返回到列表事件
			var cxType = comm.getCache('coDeclaration','cxType');
			if(cxType == 2){
				$('#cyqysptj').click();
			}else if(cxType == 4){
				$('#fwgssptj').click();
			}else{
				$('#tgqysptj').click();
			}
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050103000000");
			
			//遍历审批统计查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业审批统计
				 if(match[i].permId =='050103010000')
				 {
					 $('#tgqysptj').show();
					 $('#tgqysptj').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业审批统计
				 else if(match[i].permId =='050103020000')
				 {
					 $('#cyqysptj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqysptj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //服务公司审批统计
				 else if(match[i].permId =='050103030000')
				 {
					 $('#fwgssptj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fwgssptj').click();
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
				$('#ckxq_xg').text('修改');
            }.bind(this)).click();  
            
            $('#ckxq_qyxtzcxx').click(function(e) {		 
                qyxtzcxx.showPage();
				comm.liActive($('#ckxq_qyxtzcxx'));
				$('#ckxq_xg').text('修改');
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
            
            //返回列表
		 	$('#ckxq_back').click(function(){
		 		 $('#busibox').removeClass('none');
		 		 $('#cx_content_detail').addClass('none')  ;
		 	});
			
		}
	}	
});