define(['text!certificateManageTpl/xszgzsgl/tab.html',
		'certificateManageSrc/xszgzsgl/zsgz',
		'certificateManageSrc/xszgzsgl/zsffgl',
		'certificateManageSrc/xszgzsgl/zslsjlcx'
		], function(tab, zsgz, zsffgl, zslsjlcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#zsgz').click(function(){
				zsgz.showPage();
				comm.liActive($('#zsgz'), '#ck, #ffzs');
				$('#busibox').removeClass('none');
				$('#zsgz_ckTpl').addClass('none');
				$('#zsffgl_ffzsTpl').addClass('none');
				$('#zsffrzTpl').addClass('none');
				$('#busibox_ck').addClass("none");
			}.bind(this));
			
			$('#zsffgl').click(function(){
				zsffgl.showPage();
				comm.liActive($('#zsffgl'), '#ck, #ffzs');
				$('#busibox').removeClass('none');
				$('#zsgz_ckTpl').addClass('none');
				$('#zsffgl_ffzsTpl').addClass('none');
				$('#zsffrzTpl').addClass('none');
				$('#busibox_ck').addClass("none");
			}.bind(this));
			
			$('#zslsjlcx').click(function(){
				zslsjlcx.showPage();
				comm.liActive($('#zslsjlcx'), '#ck, #ffzs');
				$('#busibox').removeClass('none');
				$('#zsgz_ckTpl').addClass('none');
				$('#zsffgl_ffzsTpl').addClass('none');
				$('#zsffrzTpl').addClass('none');
				$('#busibox_ck').addClass("none");
			}.bind(this));
			
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050902000000");
			
			//遍历第三方业务证书管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //证书盖章
				 if(match[i].permId =='050902010000')
				 {
					 $('#zsgz').show();
					 $('#zsgz').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //证书发放管理
				 else if(match[i].permId =='050902020000')
				 {
					 $('#zsffgl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zsffgl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//证书历史记录查询
				 else if(match[i].permId =='050902030000')
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