define(['text!coDeclarationTpl/htgl/tab.html',
			'coDeclarationSrc/htgl/htgl' 
			],function(tab,htgl  ){
	return {
		//合同管理初始化
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			 
			// 
			$('#htgl_htgl').click(function(e) {				 		 
                htgl.showPage();
				comm.liActive($('#htgl_htgl'));
            }.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030205000000");
			
			//遍历企业申报复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //授权码查询
				 if(match[i].permId =='030205010000')
				 {
					 $('#htgl_htgl').show();
					 $('#htgl_htgl').click();
				 }
			 }
		 
			
		} 
	}
}); 

