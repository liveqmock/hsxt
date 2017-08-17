define(['text!invoiceTpl/dqptjsfp/tab.html',
		'invoiceSrc/dqptjsfp/dqptjsfp'
		], function(tab, dqptjsfp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//地区平台接收发票
			$('#dqptjsfp').click(function(){
				dqptjsfp.showPage();
				comm.liActive($('#dqptjsfp'), '#ck, #xz, #ckqyxx');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050601000000");
			
			//遍历地区平台接收发票的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //地区平台接收发票
				 if(match[i].permId =='050601010000')
				 {
					 $('#dqptjsfp').show();
					 $('#dqptjsfp').click();
					 
				 }
			}
			
		}
	}	
});