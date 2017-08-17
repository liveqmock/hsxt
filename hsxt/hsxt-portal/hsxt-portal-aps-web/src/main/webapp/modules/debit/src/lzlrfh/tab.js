define(['text!debitTpl/lzlrfh/tab.html',
		'debitSrc/lzlrfh/lzlrfh'
		], function(tab, lzlrfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//临账录入复核
			$('#lzlrfh').click(function(){
				lzlrfh.showPage();
				comm.liActive($('#lzlrfh'), '#ckxq');
				$('#fhDiv').addClass('none');	
			}.bind(this));
			
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050403000000");
			
			//遍历临账录入复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账录入复核
				 if(match[i].permId =='050403010000')
				 {
					 $('#lzlrfh').show();
					 $('#lzlrfh').click();
					 
				 }
			}
		}	
	}	
});