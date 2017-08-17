define(['text!invoiceTpl/qysltzsqsh/tab.html',
		'invoiceSrc/qysltzsqsh/qysltzsqsh',
		'invoiceSrc/qysltzsqsh/qysltzspfh',
		'invoiceSrc/qysltzsqsh/qysltzsqcx'
		], function(tab, qysltzsqsh, qysltzspfh, qysltzsqcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#qysltzsqsh').click(function(){
				qysltzsqsh.showPage();
				$('#spxxDiv, #fhxxDiv').addClass('none');
				comm.liActive($('#qysltzsqsh'), '#ck, #sp, #fh');
			}.bind(this));
			
			$('#qysltzspfh').click(function(){
				qysltzspfh.showPage();
				$('#spxxDiv, #fhxxDiv').addClass('none');
				comm.liActive($('#qysltzspfh'), '#ck, #sp, #fh');
			}.bind(this));
			
			$('#qysltzsqcx').click(function(){
				qysltzsqcx.showPage();
				$('#spxxDiv, #fhxxDiv').addClass('none');
				comm.liActive($('#qysltzsqcx'), '#ck');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050606000000");
			
			//遍历节余款管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //企业税率调整申请审核
				 if(match[i].permId =='050606010000')
				 {
					 $('#qysltzsqsh').show();
					 $('#qysltzsqsh').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业税率调整审批复核
				 else if(match[i].permId =='050606020000')
				 {
					 $('#qysltzspfh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qysltzspfh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //企业税率调整申请审批查询
				 else if(match[i].permId =='050606030000')
				 {
					 $('#qysltzsqcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qysltzsqcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});