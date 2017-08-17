define(['text!workflowTpl/gdcx/tab.html',
		'workflowSrc/gdcx/bjgdyl',
		'workflowSrc/gdcx/zzgdyl',
		'workflowSrc/gdcx/gqgdyl',
		'workflowSrc/gdcx/jjslgdyl',
		'workflowSrc/gdcx/blzgdyl',
		'workflowSrc/gdcx/wfpgdyl'
		], function(tab, bjgdyl, zzgdyl, gqgdyl, jjslgdyl, blzgdyl,wfpgdyl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#bjgdyl').click(function(){
				bjgdyl.showPage();
				comm.liActive($('#bjgdyl'));
			}.bind(this));
			
			$('#zzgdyl').click(function(){
				zzgdyl.showPage();
				comm.liActive($('#zzgdyl'));
			}.bind(this));
			
			$('#gqgdyl').click(function(){
				gqgdyl.showPage();
				comm.liActive($('#gqgdyl'));
			}.bind(this));
			
			$('#jjslgdyl').click(function(){
				jjslgdyl.showPage();
				comm.liActive($('#jjslgdyl'));
			}.bind(this));
			
			$('#blzgdyl').click(function(){
				blzgdyl.showPage();
				comm.liActive($('#blzgdyl'));
			}.bind(this));
			
			$('#wfpgdyl').click(function(){
				wfpgdyl.showPage();
				comm.liActive($('#wfpgdyl'));
			}.bind(this));

			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040501000000");
			
			//遍历工单查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //办结工单一览
				 if(match[i].permId =='040501010000')
				 {
					 $('#bjgdyl').show();
					 $('#bjgdyl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //挂起工单一览
				 else if(match[i].permId =='040501020000')
				 {
					 $('#gqgdyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gqgdyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //拒绝受理工单一览
				 else if(match[i].permId =='040501030000')
				 {
					 $('#jjslgdyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jjslgdyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //办理中工单一览
				 else if(match[i].permId =='040501040000')
				 {
					 $('#blzgdyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#blzgdyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //未分配工单一览
				 else if(match[i].permId =='040501050000')
				 {
					 $('#wfpgdyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#wfpgdyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});