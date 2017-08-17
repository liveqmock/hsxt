define(['text!invoiceTpl/dqptkfpyw/tab.html',
		'invoiceSrc/dqptkfpyw/dqptkfpyw'
		], function(tab, dqptkfpyw){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//地区平台开发票业务
			$('#dqptkfpyw').click(function(){
				dqptkfpyw.showPage();
				comm.liActive($('#dqptkfpyw'), '#lrfp, #ckqyxx, #ckfpxx');
			}.bind(this));
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050602000000");
			
			//遍历地区平台开发票业务的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //地区平台开发票业务
				 if(match[i].permId =='050602010000')
				 {
					 $('#dqptkfpyw').show();
					 $('#dqptkfpyw').click();
					 
				 }
			}
		}	
	}	
});