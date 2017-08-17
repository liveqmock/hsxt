define(['text!scoremanageTpl/lphs/tab.html',
		'scoremanageSrc/lphs/lphs'
		], function(tab, lphs){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#lphs').click(function(){
				lphs.showPage();
				comm.liActive($('#lphs'),'#ck');
				comm.liActive($('#lphs'),'#xz');
				$('#busibox').removeClass('none');
				$('#lphs_detail').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050805000000");
			
			//遍历理赔核算的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //理赔核算
				 if(match[i].permId =='050805010000')
				 {
					 $('#lphs').show();
					 $('#lphs').click();
					 
				 }
			}
			
		}	
	}	
});