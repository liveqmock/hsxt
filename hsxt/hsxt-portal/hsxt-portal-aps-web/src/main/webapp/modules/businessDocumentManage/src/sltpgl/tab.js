define(['text!businessDocumentManageTpl/sltpgl/tab.html',
		'businessDocumentManageSrc/sltpgl/sltpgl'
		], function(tab, sltpgl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#sltpgl').click(function(){
				sltpgl.showPage();
				comm.liActive($('#sltpgl'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052401000000");
			
			//遍历理赔核算的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //理赔核算
				 if(match[i].permId =='052401010000')
				 {
					 $('#sltpgl').show();
					 $('#sltpgl').click();
					 
				 }
			}
		}	
	}	
});
