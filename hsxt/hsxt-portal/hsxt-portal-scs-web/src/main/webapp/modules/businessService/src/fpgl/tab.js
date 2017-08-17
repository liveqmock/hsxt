define(['text!businessServiceTpl/fpgl/tab.html',
		'businessServiceSrc/fpgl/fpsq/fpsq',
		'businessServiceSrc/fpgl/fpsqcx/fpsqcx' ,
		'businessServiceSrc/fpgl/qysyfp/qysyfp' 
	 
		], function(tpl, fpsq, fpsqcx,qysyfp ){
	return {
		//发票管理初始化
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
		
			$('#fpgl_cxfpsqd').click(function(e){
				comm.liActive($(this), '#fpgl_fpsq');
				$('#fpgl_fpsq').addClass('tabNone');
				fpsqcx.showPage();
			});	
			 
			$('#fpgl_fpsq').click(function(e){
				comm.liActive_add($(this));
				
				fpsq.showPage();
			});
			
			$('#fpgl_qysyfp').click(function(e){
				comm.liActive($(this), '#fpgl_fpsq');
				$('#fpgl_fpsq').addClass('tabNone');
				qysyfp.showPage();
			});
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030302000000");
			
			//遍历发票管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //企业开发票查询
				 if(match[i].permId =='030302010000')
				 {
					 $('#fpgl_cxfpsqd').show();
					 $('#fpgl_cxfpsqd').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业索取发票
				 else if(match[i].permId =='030302020000')
				 {
					 $('#fpgl_qysyfp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fpgl_qysyfp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			 
	 
		}
		
		
		
		
	}
});