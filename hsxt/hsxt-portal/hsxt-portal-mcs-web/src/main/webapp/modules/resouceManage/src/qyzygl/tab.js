define(['text!resouceManageTpl/qyzygl/tab.html',
		'resouceManageSrc/qyzygl/tgqyzyyl',
		'resouceManageSrc/qyzygl/cyqyzyyl',
		'resouceManageSrc/qyzygl/fwgszyyl' ],function( tab,tgqyzyyl,cyqyzyyl,fwgszyyl ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//托管企业一lan
			$('#xtzy_tgqyzyyl').click(function(e) {				 
                tgqyzyyl.showPage();
				this.liActive($('#xtzy_tgqyzyyl')); 
            }.bind(this));
			//成员企业一lan
			$('#xtzy_cyqyzyyl').click(function(e) {				 
                cyqyzyyl.showPage();
				this.liActive($('#xtzy_cyqyzyyl')); 
            }.bind(this)) ;
			
			//服务公司一lan
			$('#xtzy_fwgszyyl').click(function(e) {				 
                fwgszyyl.showPage();
				this.liActive($('#xtzy_fwgszyyl'));  
            }.bind(this)) ;
			 
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040203000000");
			
			//遍历资源配额一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业资源一览
				 if(match[i].permId =='040203010000')
				 {
					 $('#xtzy_tgqyzyyl').show();
					 $('#xtzy_tgqyzyyl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业资源一览
				 else if(match[i].permId =='040203020000')
				 {
					 $('#xtzy_cyqyzyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xtzy_cyqyzyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //服务公司资源一览
				 else if(match[i].permId =='040203030000')
				 {
					 $('#xtzy_fwgszyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xtzy_fwgszyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		},
		 
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
		 
	}
}); 

