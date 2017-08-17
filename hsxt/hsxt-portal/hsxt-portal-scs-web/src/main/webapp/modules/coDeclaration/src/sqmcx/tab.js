define(['text!coDeclarationTpl/sqmcx/tab.html',
			'coDeclarationSrc/sqmcx/sqmcx' 
			],function(tab,sqmcx  ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			 
			// 
			$('#sqmcx_sqmcx').click(function(e) {				 		 
                sqmcx.showPage();
				comm.liActive($('#sqmcx_sqmcx'));
            }.bind(this));			 
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030204000000");
			
			//遍历企业申报复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //授权码查询
				 if(match[i].permId =='030204010000')
				 {
					 $('#sqmcx_sqmcx').show();
					 $('#sqmcx_sqmcx').click();
					 
				 }
			 }
			
		} 
	}
}); 

