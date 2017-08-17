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
		], function(tab, tgqysptj, cyqysptj, fwgssptj, 	sbxx, qyxtzcxx , qygsdjxx,qylxxx, qyyhzhxx, qysczl,blztxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#tgqysptj').click(function(){
				tgqysptj.showPage();
				comm.liActive($('#tgqysptj'));
			}.bind(this));
			
			$('#cyqysptj').click(function(){
				cyqysptj.showPage();
				comm.liActive($('#cyqysptj'));
			}.bind(this));
			
			$('#fwgssptj').click(function(){
				fwgssptj.showPage();
				comm.liActive($('#fwgssptj'));
			}.bind(this));
			
			
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
			
            //权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040304000000");
			
			//遍历资源配额一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业审批统计
				 if(match[i].permId =='040304010000')
				 {
					 $('#tgqysptj').show();
					 $('#tgqysptj').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业审批统计
				 else if(match[i].permId =='040304020000')
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
				 else if(match[i].permId =='040304030000')
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
			
			
		}
	}	
});