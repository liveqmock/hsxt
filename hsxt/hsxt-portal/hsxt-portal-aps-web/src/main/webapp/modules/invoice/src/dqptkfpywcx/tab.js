define(['text!invoiceTpl/dqptkfpywcx/tab.html',
		'invoiceSrc/dqptkfpywcx/dqptkfpywcx'
		], function(tab, dqptkfpywcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			//地区平台开发票业务查询
			$('#dqptkfpywcx_dqptkfpywcx').click(function(){
				dqptkfpywcx.showPage();
				comm.liActive($('#dqptkfpywcx_dqptkfpywcx'), '#ckqyxx, #ckfpxx, #xgps');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050603000000");
			
			//遍历地区平台开发票业务查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //地区平台开发票业务查询
				 if(match[i].permId =='050603010000')
				 {
					 $('#dqptkfpywcx_dqptkfpywcx').show();
					 $('#dqptkfpywcx_dqptkfpywcx').click();
					 
				 }
			}
		}	
	}	
});