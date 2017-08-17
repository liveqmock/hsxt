define(['text!certificateManageTpl/dsfywzsgl/tab.html',
		'certificateManageSrc/dsfywzsgl/zsffgl',
		'certificateManageSrc/dsfywzsgl/zslsjlcx'
		], function(tab, zsffgl, zslsjlcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//证书发放管理
			$('#zsffgl').click(function(){
				zsffgl.showPage();
				comm.liActive($('#zsffgl'), '#ffzs, #ck');
				$('#busibox').removeClass('none');
				$('#zslsjlcx_ckTpl').addClass('none');
				$('#zsffgl_ckTpl').addClass('none');
				$('#zsffgl_ffzsTpl').addClass('none');
				$('#busibox_ck').addClass("none");
			}.bind(this));
			//证书历史记录查询
			$('#zslsjlcx').click(function(){
				zslsjlcx.showPage();
				comm.liActive($('#zslsjlcx'), '#ffzs, #ck');
				$('#busibox').removeClass('none');
				$('#zslsjlcx_ckTpl').addClass('none');
				$('#zsffgl_ckTpl').addClass('none');
				$('#zsffgl_ffzsTpl').addClass('none');
				$('#busibox_ck').addClass("none");
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050901000000");
			
			//遍历第三方业务证书管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //证书发放管理
				 if(match[i].permId =='050901010000')
				 {
					 $('#zsffgl').show();
					 $('#zsffgl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //证书历史记录查询
				 else if(match[i].permId =='050901020000')
				 {
					 $('#zslsjlcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zslsjlcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});