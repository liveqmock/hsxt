define(['text!debitTpl/lztkfh/tab.html',
		'debitSrc/lztkfh/lztkfh'
		], function(tab, lztkfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//临账退款复核
			$('#lztkfh').click(function(){
				lztkfh.showPage();
				comm.liActive($('#lztkfh'), '#tksqfh');
				$('#tkfhDiv').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050404000000");
			
			//遍历临账退款复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账退款复核
				 if(match[i].permId =='050404010000')
				 {
					 $('#lztkfh').show();
					 $('#lztkfh').click();
					 
				 }
			}
		}
	}	
});