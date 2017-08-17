define(['text!resouceManageTpl/xhzzytj/tab.html',
			'resouceManageSrc/xhzzytj/xhzzytj' 
			],function(tab,xhzzytj  ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			
			
			// 
			$('#xhzzytj_xhzzytj').click(function(e) {				 		 
                xhzzytj.showPage();
				comm.liActive($('#xhzzytj_xhzzytj'));
            }.bind(this));			 
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030402000000");
			
			//遍历意向客户查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //意向客户查询
				 if(match[i].permId =='030402010000')
				 {
					 $('#xhzzytj_xhzzytj').show();
					 $('#xhzzytj_xhzzytj').click();
					 
				 }
			}
		 
			
		} 
	}
}); 
