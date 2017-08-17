define(['text!infoManageTpl/zyzhgl/tab.html',
		'infoManageSrc/zyzhgl/tgqyzy',
		'infoManageSrc/zyzhgl/cyqyzy',
		'infoManageSrc/zyzhgl/fwgszy',
		'infoManageSrc/zyzhgl/xfzzy'
		], function(tab, tgqyzy, cyqyzy, fwgszy, xfzzy){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#tgqyzy').click(function(){
				tgqyzy.showPage();
				comm.liActive($('#tgqyzy'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");			
				$('#ent_list').hide();
				$('#ent_detail').hide();
			}.bind(this));
			
			$('#cyqyzy').click(function(){
				cyqyzy.showPage();
				comm.liActive($('#cyqyzy'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");			
				$('#ent_list').hide();
				$('#ent_detail').hide();
			}.bind(this));
			
			$('#fwgszy').click(function(){
				fwgszy.showPage();
				comm.liActive($('#fwgszy'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");			
				$('#ent_list').hide();
				$('#ent_detail').hide();
			}.bind(this));
			
			$('#xfzzy').click(function(){
				xfzzy.showPage();
				comm.liActive($('#xfzzy'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");			
				$('#ent_list').hide();
				$('#ent_detail').hide();
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051607000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业资源
				 if(match[i].permId =='051607010000')
				 {
					 $('#tgqyzy').show();
					 $('#tgqyzy').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业资源
				 else if(match[i].permId =='051607020000')
				 {
					 $('#cyqyzy').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqyzy').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//服务公司资源
				 else if(match[i].permId =='051607030000')
				 {
					 $('#fwgszy').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fwgszy').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费者资源
				 else if(match[i].permId =='051607040000')
				 {
					 $('#xfzzy').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xfzzy').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}
	}	
});