define(['text!scoremanageTpl/jfflfh/tab.html',
		'scoremanageSrc/jfflfh/jfflfh'
		], function(tab, jfflsp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//积分福利复核
			$('#jfflsp').click(function(){
				jfflsp.showPage();
				comm.liActive($('#jfflsp'),'#ck');
				$('#busibox').removeClass('none');
				$('#jfflsp_detail').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050802000000");
			
			//遍历积分福利复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //积分福利复核
				 if(match[i].permId =='050802010000')
				 {
					 $('#jfflsp').show();
					 $('#jfflsp').click();
					 
				 }
			}
		}
	}	
});