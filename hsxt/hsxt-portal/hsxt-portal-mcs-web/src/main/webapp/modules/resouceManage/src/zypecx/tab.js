define(['text!resouceManageTpl/zypecx/tab.html',
		'resouceManageSrc/zypecx/zypecx_fwgs',
		'resouceManageSrc/zypecx/zypecx_tgqy',
		'resouceManageSrc/zypecx/zypecx_cyqy'
		],function( tab,zypecx_fwgs,zypecx_tgqy,zypecx_cyqy ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			
			//资源配额查询
			$('#xtzy_zypecx_fwgs').click(function(e) {				 
                zypecx_fwgs.showPage();
				this.liActive($('#xtzy_zypecx_fwgs'));
            }.bind(this));
			
			//托管企业配合查询业务需求隐藏菜单
			$('#xtzy_zypecx_tgqy').click(function(e) {				 
                zypecx_tgqy.showPage();
				this.liActive($('#xtzy_zypecx_tgqy'));
            }.bind(this));
			
			//成员企业配合查询业务需求隐藏菜单
			$('#xtzy_zypecx_cyqy').click(function(e) {				 
                zypecx_cyqy.showPage();
				this.liActive($('#xtzy_zypecx_cyqy'));
            }.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040202000000");
			
			//遍历资源配额查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //服务公司资源配额查询
				 if(match[i].permId =='040202010000')
				 {
					 $('#xtzy_zypecx_fwgs').show();
					 $('#xtzy_zypecx_fwgs').click();
					 
				 }
			 }
		} ,
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 

