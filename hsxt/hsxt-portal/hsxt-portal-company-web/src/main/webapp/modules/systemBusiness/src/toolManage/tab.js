define(['text!systemBusinessTpl/toolManage/tab.html',
		'systemBusinessSrc/toolManage/serviceToolBuy/serviceToolBuy',
		'systemBusinessSrc/toolManage/cardBuy/resourcebBuy',
		'systemBusinessSrc/toolManage/swingTerminal',
		'systemBusinessSrc/toolManage/queryToolOrder',
		'systemBusinessSrc/toolManage/cardStyleBuy',
		'systemBusinessLan'
		], function(tpl, serviceToolBuy, resourcebBuy, swingTerminal, queryToolOrder, cardStyleBuy){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//工具申购
			$('#gjgl_gjsg').click(function(e){
				comm.liActive($(this), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
				serviceToolBuy.showPage();
			});
			
			
			//互生卡申购
			$('#gjgl_hsksg').click(function(e){
				comm.liActive($(this), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
				resourcebBuy.showPage();
			});
			
			
			//刷卡终端管理
			$('#skzdgl').click(function(e){
				comm.liActive($(this), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
				swingTerminal.showPage();
			});
			
			//工具申购查询
			$('#gjsgcx').click(function(e){
				comm.liActive($(this), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
				queryToolOrder.showPage();
			});
			
			//个性卡定制服务
			$('#gxkdzfw').click(function(e){
				comm.liActive($(this), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
				cardStyleBuy.showPage();
			});
			
			//获取缓存中工具管理三级菜单
			var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020201000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //工具申购
				 if(match[i].permId =='020201010000')
				 {
					 $('#gjgl_gjsg').show();
					//默认选中
					 $('#gjgl_gjsg').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //互生卡申购
				 else if(match[i].permId =='020201020000' && $.cookie('entResType')==3)
				 {
					 $('#gjgl_hsksg').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjgl_hsksg').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //刷卡终端管理
				 else if(match[i].permId =='020201030000')
				 {
					 $('#skzdgl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#skzdgl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //工具申购查询
				 else if(match[i].permId =='020201040000')
				 {
					 $('#gjsgcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjsgcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//个性卡定制服务
				 else if(match[i].permId =='020201050000' && $.cookie('entResType')==3)
				 {
					 $('#gxkdzfw').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gxkdzfw').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}
});