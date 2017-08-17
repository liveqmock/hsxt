define(['text!invoiceTpl/dqptspfp/tab.html',
		'invoiceSrc/dqptspfp/dqptspfp'
		], function(tab, dqptspfp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//地区平台审批发票
			$('#dqptspfp').click(function(){
				dqptspfp.showPage();
				comm.liActive($('#dqptspfp'), '#ck, #lrfp, #ckqyxx');
			}.bind(this));
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050604000000");
			
			//遍历地区平台审批发票的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //地区平台审批发票
				 if(match[i].permId =='050604010000')
				 {
					 $('#dqptspfp').show();
					 $('#dqptspfp').click();
					 
				 }
			}
		}	
	}	
});