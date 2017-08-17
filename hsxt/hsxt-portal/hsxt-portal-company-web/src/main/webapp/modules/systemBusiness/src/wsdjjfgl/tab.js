define(['text!systemBusinessTpl/wsdjjfgl/tab.html',
		'systemBusinessSrc/wsdjjfgl/wsdjjf',
		'systemBusinessSrc/wsdjjfgl/wsdjjfcx',
		'systemBusinessSrc/wsdjjfgl/wsdjjfblsz',
		'systemBusinessSrc/wsdjjfgl/wsdjjfblxg',
		'systemBusinessDat/systemBusiness',
		'systemBusinessLan'
		], function(tpl, wsdjjf, wsdjjfcx, wsdjjfblsz, wsdjjfblxg,systemBusiness){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//网上登记积分
			$('#wsdjjfgl_wsdjjf').click(function(e){
				comm.liActive($(this));
				wsdjjf.showPage();
			});
			
			//网上登记积分查询
			$('#wsdjjfgl_wsdjjfcx').click(function(e){
				comm.liActive($(this));
				wsdjjfcx.showPage();
			});
			
			//网上登记积分比例设置
			$('#wsdjjfgl_wsdjjfblsz').click(function(e){
				comm.liActive($(this));
				wsdjjfblsz.showPage();
			});
			
			//网上登记积分比例修改
			$('#wsdjjfgl_wsdjjfblxg').click(function(e){
				comm.liActive($(this));
				wsdjjfblxg.showPage();
			});
			
			//获取缓存中网上登记积分管理三级菜单
			var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020203000000"); 
			systemBusiness.queryEntPointRate({},function(res){
				var jfblsz="1";
				if(res.data == ""){
					jfblsz = '0';
				}
			
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //网上登记积分
				 if(match[i].permId =='020203010000')
				 {
					 $('#wsdjjfgl_wsdjjf').show();
					 $('#wsdjjfgl_wsdjjf').click();
					//已经设置默认值
					 isModulesDefault = true;
				 }
				 //网上登记积分查询
				 else if(match[i].permId =='020203020000')
				 {
					 $('#wsdjjfgl_wsdjjfcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#wsdjjfgl_wsdjjfcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //网上登记积分比例设置
				 else if(match[i].permId =='020203030000' && jfblsz=='0')
				 {
					 $('#wsdjjfgl_wsdjjfblsz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#wsdjjfgl_wsdjjfblsz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //网上登记积分比例修改
				 else if(match[i].permId =='020203040000' && jfblsz=='1')
				 {
					 $('#wsdjjfgl_wsdjjfblxg').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#wsdjjfgl_wsdjjfblxg').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			});
		}
	}
});