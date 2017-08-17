define(['text!coDeclarationTpl/qybb/tab.html',
		'coDeclarationSrc/qybb/qybbsh',
		'coDeclarationSrc/qybb/yybbsh',
		'coDeclarationSrc/qybb/bbxxcx'
		], function(tab, qybbsh, yybbsh, bbxxcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#qybbsh').click(function(){
				qybbsh.showPage();
				comm.liActive($('#qybbsh'),'#qybbxg, #yybbxg');
			}.bind(this));
			
			$('#yybbsh').click(function(){
				yybbsh.showPage();
				comm.liActive($('#yybbsh'),'#qybbxg, #yybbxg');
			}.bind(this));
			
			$('#bbxxcx').click(function(){
				bbxxcx.showPage();
				comm.liActive($('#bbxxcx'),'#qybbxg, #yybbxg');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050105000000");
			
			//遍历审批统计查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //企业报备审核
				 if(match[i].permId =='050105010000')
				 {
					 $('#qybbsh').show();
					 $('#qybbsh').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //异议报备审核
				 else if(match[i].permId =='050105020000')
				 {
					 $('#yybbsh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#yybbsh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //报备信息查询
				 else if(match[i].permId =='050105030000')
				 {
					 $('#bbxxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#bbxxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			
		}	
	}	
});