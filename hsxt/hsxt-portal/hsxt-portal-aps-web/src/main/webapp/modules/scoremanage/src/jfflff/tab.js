define(['text!scoremanageTpl/jfflff/tab.html',
		'scoremanageSrc/jfflff/ywshbtff',
		'scoremanageSrc/jfflff/mfylbtff',
		'scoremanageSrc/jfflff/trsgbtff',
		'scoremanageSrc/jfflff/jfflffjlcx'
		], function(tab,ywshbtff,mfylbtff,trsgbtff,jfflffjlcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));

			$('#ywshbtff').click(function(){
				ywshbtff.showPage();
				comm.liActive($('#ywshbtff'),'#ff,#ck');
				$('#busibox').removeClass('none');
				$('#ywshbtff_detail').addClass('none');
			}.bind(this));
			
			$('#mfylbtff').click(function(){
				mfylbtff.showPage();
				comm.liActive($('#mfylbtff'),'#ff,#ck');
				$('#busibox').removeClass('none');
				$('#ywshbtff_detail').addClass('none');
			}.bind(this));
			
			$('#trsgbtff').click(function(){
				trsgbtff.showPage();
				comm.liActive($('#trsgbtff'),'#ff,#ck');
				$('#busibox').removeClass('none');
				$('#ywshbtff_detail').addClass('none');
			}.bind(this));
			
			$('#jfflffjlcx').click(function(){
				jfflffjlcx.showPage();
				comm.liActive($('#jfflffjlcx'),'#ff,#ck');
				$('#busibox').removeClass('none');
				$('#ywshbtff_detail').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050804000000");
			
			//遍历积分福利发放的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //意外伤害补贴发放
				 if(match[i].permId =='050804010000')
				 {
					 $('#ywshbtff').show();
					 $('#ywshbtff').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //互生医疗补贴发放
				 else if(match[i].permId =='050804020000')
				 {
					 $('#mfylbtff').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#mfylbtff').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//他人身故补贴发放
				 else if(match[i].permId =='050804030000')
				 {
					 $('#trsgbtff').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#trsgbtff').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
					//积分福利发放记录查询
				 else if(match[i].permId =='050804040000')
				 {
					 $('#jfflffjlcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jfflffjlcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});