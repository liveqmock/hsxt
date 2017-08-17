define(["text!systemServiceTpl/headTab.html",
		"systemServiceDat/systemService",
		"systemServiceSrc/systemLogSearch"
	], function (tpl, systemService, systemLogSearch) {
	//把参数转换成数组
	var args = [].slice.apply(arguments);
	return {
		show : function () {
			//加载顶部导航菜单
			$('#operationsArea').html(tpl);

			//*******************切换顶部导航菜单单击事件*******************
			$('#ul_myhs_right_tab li a').click(function (e) {
				var o = $(this),
				dataId = parseInt(o.attr('data-id'), 10);
				//清除选中状态
				$('#ul_myhs_right_tab li a').removeClass('a_active');
				//切换激活状态
				o.addClass('a_active');
				//加载 系统日志查询
				try{
					args[dataId + 1].show(systemService);
				}catch(e){}
			});
			
			//加载第一个菜单
			$('#ul_myhs_right_tab li a:eq(0)').trigger('click');
		}
	};
});