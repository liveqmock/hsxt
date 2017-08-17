define(['text!systemBusinessTpl/fpgl/tab.html',
		'systemBusinessSrc/fpgl/fpsq/fpsq',
		'systemBusinessSrc/fpgl/fpsqcx/fpsqcx' ,
		'systemBusinessSrc/fpgl/qysyfp/qysyfp' 
	 
		], function(tpl, fpsq, fpsqcx,qysyfp ){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
		
			$('#fpgl_cxfpsqd').click(function(e){
				comm.liActive($(this), '#fpgl_cxfpsqd');
				$('#fpgl_fpsq').addClass('tabNone');
				fpsqcx.showPage();
			});	
			 
			$('#fpgl_fpsq').click(function(e){
				comm.liActive($(this), '#fpgl_fpsq');
				var allAmount=0;
				fpsq.showPage(allAmount);
			});
			
			$('#fpgl_qysyfp').click(function(e){
				comm.liActive($(this), '#fpgl_qysyfp');
				$('#fpgl_fpsq').addClass('tabNone');
				qysyfp.showPage();
			});
			 
			//获取缓存发票管理三级菜单
			var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020209000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //企业开发票查询
				 if(match[i].permId =='020209010000')
				 {
					 $('#fpgl_cxfpsqd').show();
					 $('#fpgl_cxfpsqd').click();
					//已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业开发票查询
				 else if(match[i].permId =='020209020000')
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