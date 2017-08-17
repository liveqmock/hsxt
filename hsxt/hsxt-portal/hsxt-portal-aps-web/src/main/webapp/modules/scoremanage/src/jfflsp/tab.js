define(['text!scoremanageTpl/jfflsp/tab.html',
		'scoremanageSrc/jfflsp/jfflsp'
		], function(tab, jfflsp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#jfflsp').click(function(){
				jfflsp.showPage();
				comm.liActive($('#jfflsp'),'#ck');
				$('#busibox').removeClass('none');
				$('#jfflsp_detail').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050801000000");
			
			//遍历积分福利初审的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //积分福利初审
				 if(match[i].permId =='050801010000')
				 {
					 $('#jfflsp').show();
					 $('#jfflsp').click();
					 
				 }
			}
		}
	}	
});