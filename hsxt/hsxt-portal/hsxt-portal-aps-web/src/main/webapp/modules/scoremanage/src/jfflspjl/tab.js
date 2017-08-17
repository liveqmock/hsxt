define(['text!scoremanageTpl/jfflspjl/tab.html',
		'scoremanageSrc/jfflspjl/jfflspjl'
		], function(tab, jfflspjl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//积分福利审批记录
			$('#jfflspjl').click(function(){
				jfflspjl.showPage();
				comm.liActive($('#jfflspjl'),'#ck');
				$('#busibox').removeClass('none');
				$('#jfflspjl_detail').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050803000000");
			
			//遍历积分福利审批记录的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //积分福利审批记录
				 if(match[i].permId =='050803010000')
				 {
					 $('#jfflspjl').show();
					 $('#jfflspjl').click();
					 
				 }
			}
			
		}	
	}	
});