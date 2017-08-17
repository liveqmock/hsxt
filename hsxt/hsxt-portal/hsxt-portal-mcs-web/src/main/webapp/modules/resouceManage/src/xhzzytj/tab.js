define(['text!resouceManageTpl/xhzzytj/tab.html',
		'resouceManageSrc/xhzzytj/xhzzytj' ],function( tab,xhzzytj ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//资源配额查询
			$('#xtzy_xhzzytj').click(function(e) {				 
                xhzzytj.showPage();
				 
            }.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040204000000");
			
			//遍历资源配额查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //服务公司资源配额查询
				 if(match[i].permId =='040204010000')
				 {
					 $('#xtzy_xhzzytj').show();
					 $('#xtzy_xhzzytj').click();
					 
				 }
			 }
		} 
	}
}); 

