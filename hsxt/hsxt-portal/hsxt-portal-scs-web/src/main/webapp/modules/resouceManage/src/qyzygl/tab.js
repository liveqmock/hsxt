define(['text!resouceManageTpl/qyzygl/tab.html',
			'resouceManageSrc/qyzygl/tgqyzyyl',
			'resouceManageSrc/qyzygl/cyqyzyyl',
			'resouceManageSrc/qyzygl/tdzyyl',
			'resouceManageSrc/qyzygl/ckxq/xtdjxx',
			'resouceManageSrc/qyzygl/ckxq/gsdjxx',
			'resouceManageSrc/qyzygl/ckxq/lxxx',
			'resouceManageSrc/qyzygl/ckxq/yhzhxx',
			'resouceManageSrc/qyzygl/ckxq/qysczl'
			],function(tab,tgqyzyyl, cyqyzyyl , tdzyyl, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl){
	return {
		//企业资源管理初始化
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			
			$('#qyzygl_tgqyzyyl').click(function(e) {		
				this.showActive(tgqyzyyl, $('#qyzygl_tgqyzyyl'));
            }.bind(this));			 
			
			$('#qyzygl_cyqyzyyl').click(function(e) {			 
				this.showActive(cyqyzyyl, $('#qyzygl_cyqyzyyl'));
            }.bind(this)); 
            
			$('#qyzygl_tdzyyl').click(function(e) {		 
				this.showActive(tdzyyl, $('#qyzygl_tdzyyl'));
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
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030401000000");
			
			//遍历企业资源管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业资源一览
				 if(match[i].permId =='030401010000')
				 {
					 $('#qyzygl_tgqyzyyl').show();
					 $('#qyzygl_tgqyzyyl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业资源一览
				 else if(match[i].permId =='030401020000')
				 {
					 $('#qyzygl_cyqyzyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qyzygl_cyqyzyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//团队资源一览
				 else if(match[i].permId =='030401030000')
				 {
					 $('#qyzygl_tdzyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qyzygl_tdzyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		},
		showActive: function(page, menuId){
			$('#busibox').removeClass('none');
			page.showPage();
			comm.liActive(menuId);
			$('#cx_content_detail').addClass('none'); 
		}
	}
}); 
