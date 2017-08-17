define(['text!resouceManageTpl/zype/tab.html', 
			'resouceManageSrc/zype/zypeylb_fwgs',
			'resouceManageSrc/zype/zypeylb_fwgs_xq', 
			/*'resouceManageSrc/zype/zypeylb_tgqy',*/  
			'resouceManageSrc/zype/zypeylb_tgqy_xq', 
			/*'resouceManageSrc/zype/zypeylb_cyqy' ,*/
			'resouceManageSrc/zype/zypeylb_cyqy_xq',
			'resouceManageSrc/zype/zypeylb_qyzype'
		],function(tab,zypeylb_fwgs, zypeylb_fwgs_xq, /*zypeylb_tgqy,*/ zypeylb_tgqy_xq,/*zypeylb_cyqy,*/ zypeylb_cyqy_xq, zypeylb_qyzype){
	return {	 
		showPage : function(){	
		
			$('.operationsInner').html(_.template(tab)) ;
			//服务公司资源配额一览表
			$('#xtzy_zype_fwgs').click(function(e) {	
                zypeylb_fwgs.showPage();	
                 $('#xtzy_zype_fwgs_xq, #xtzy_zype_tgqy_xq, #xtzy_zype_cyqy_xq').css({'display':'none'});	
                this.liActive($('#xtzy_zype_fwgs'));		 
            }.bind(this));
			
			$('#xtzy_zype_fwgs_xq').click(function(e) {				 
                zypeylb_fwgs_xq.showPage();	
                $('#xtzy_zype_fwgs_xq').css({'display':'block'});	
                this.liActive($('#xtzy_zype_fwgs_xq'));		 
            }.bind(this));
			
			/*$('#xtzy_zype_tgqy').click(function(e) {	
                zypeylb_tgqy.showPage();	
                 $('#xtzy_zype_fwgs_xq, #xtzy_zype_tgqy_xq, #xtzy_zype_cyqy_xq').css({'display':'none'});	
                this.liActive($('#xtzy_zype_tgqy'));		 
            }.bind(this));*/
			
			$('#xtzy_zype_tgqy_xq').click(function(e) {				 
                zypeylb_tgqy_xq.showPage();	
                $('#xtzy_zype_tgqy_xq').css({'display':'block'});	
                this.liActive($('#xtzy_zype_tgqy_xq'));		 
            }.bind(this));
			
			/*$('#xtzy_zype_cyqy').click(function(e) {	
                zypeylb_cyqy.showPage();	
                 $('#xtzy_zype_fwgs_xq, #xtzy_zype_tgqy_xq, #xtzy_zype_cyqy_xq').css({'display':'none'});	
                this.liActive($('#xtzy_zype_cyqy'));		 
            }.bind(this));*/
			
			$('#xtzy_zype_cyqy_xq').click(function(e) {				 
                zypeylb_cyqy_xq.showPage();	
                $('#xtzy_zype_cyqy_xq').css({'display':'block'});	
                this.liActive($('#xtzy_zype_cyqy_xq'));		 
            }.bind(this));
			//企业系统资源一览表
			$('#xtzy_zype_qyzype').click(function(e) {	
                zypeylb_qyzype.showPage();	
                 $('#xtzy_zype_fwgs_xq, #xtzy_zype_tgqy_xq, #xtzy_zype_cyqy_xq').css({'display':'none'});	
                this.liActive($('#xtzy_zype_qyzype'));		 
            }.bind(this));
			
			/*$('#xtzy_zype_qyzype_xq').click(function(e) {				 
                xtzy_zype_qyzype_xq.showPage();	
                $('#xtzy_zype_qyzype_xq').css({'display':'block'});	
                this.liActive($('#xtzy_zype_qyzype_xq'));		 
            }.bind(this));*/
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040201000000");
			
			//遍历资源配额一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //服务公司资源配额一览表
				 if(match[i].permId =='040201010000')
				 {
					 $('#xtzy_zype_fwgs').show();
					 $('#xtzy_zype_fwgs').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业系统资源一览表
				 else if(match[i].permId =='040201020000')
				 {
					 $('#xtzy_zype_qyzype').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xtzy_zype_qyzype').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			 
		} ,
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
		
		
	}
}); 

