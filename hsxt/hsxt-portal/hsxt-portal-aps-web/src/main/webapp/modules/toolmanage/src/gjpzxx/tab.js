define(['text!toolmanageTpl/gjpzxx/tab.html',
		'toolmanageSrc/gjpzxx/gjlebxx',
		'toolmanageSrc/gjpzxx/gjliebxx',
		'toolmanageSrc/gjpzxx/gjtpysgl',
		'toolmanageSrc/gjpzxx/skqksnmgl',
		'toolmanageSrc/gjpzxx/gysxx',
		'toolmanageSrc/gjpzxx/gjjgsp',
		'toolmanageSrc/gjpzxx/gjxjsp'
		], function(tab, gjlebxx, gjliebxx, gjtpysgl, skqksnmgl, gysxx, gjjgsp, gjxjsp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#gjlebxx').click(function(){
				gjlebxx.showPage();
				comm.liActive($('#gjlebxx'));
			}.bind(this));
			
			$('#gjliebxx').click(function(){
				gjliebxx.showPage();
				comm.liActive($('#gjliebxx'));
			}.bind(this)) ;
			
			$('#gjtpysgl').click(function(){
				gjtpysgl.showPage();
				comm.liActive($('#gjtpysgl'));
			}.bind(this));
			
			$('#skqksnmgl').click(function(){
				skqksnmgl.showPage();
				comm.liActive($('#skqksnmgl'));
			}.bind(this));
			
			$('#gysxx').click(function(){
				gysxx.showPage();
				comm.liActive($('#gysxx'));
			}.bind(this));
			
			$('#gjjgsp').click(function(){
				gjjgsp.showPage();
				comm.liActive($('#gjjgsp'));
			}.bind(this));
			
			$('#gjxjsp').click(function(){
				gjxjsp.showPage();
				comm.liActive($('#gjxjsp'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051801000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //工具类别信息
				 if(match[i].permId =='051801010000')
				 {
					 $('#gjlebxx').show();
					 $('#gjlebxx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //工具列表信息
				 else if(match[i].permId =='051801020000')
				 {
					 $('#gjliebxx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjliebxx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//刷卡器KSN码管理
				 else if(match[i].permId =='051801040000')
				 {
					 $('#skqksnmgl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#skqksnmgl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//供应商信息
				 else if(match[i].permId =='051801050000')
				 {
					 $('#gysxx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gysxx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//工具价格审批
				 else if(match[i].permId =='051801060000')
				 {
					 $('#gjjgsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjjgsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//工具下架审批
				 else if(match[i].permId =='051801070000')
				 {
					 $('#gjxjsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjxjsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
			
		}	
	}	
});