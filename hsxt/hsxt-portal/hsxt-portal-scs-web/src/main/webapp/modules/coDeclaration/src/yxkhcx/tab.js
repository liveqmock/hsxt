define(['text!coDeclarationTpl/yxkhcx/tab.html',
			'coDeclarationSrc/yxkhcx/yxkhcx' 
			],function(tab,yxkhcx  ){
	return {
		//意向客户查询初始化
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			 
			// 
			$('#yxkhcx_yxkhcx').click(function(e) {				 		 
                yxkhcx.showPage();
				comm.liActive($('#yxkhcx_yxkhcx'));
            }.bind(this));			 
		    
		    
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030207000000");
			
			//遍历意向客户查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //意向客户查询
				 if(match[i].permId =='030207010000')
				 {
					 $('#yxkhcx_yxkhcx').show();
					 $('#yxkhcx_yxkhcx').click();
					 
				 }
			}
			
		} 
	}
}); 

