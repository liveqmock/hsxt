define(["text!cashAccountTpl/headTab.html",
		"cashAccountDat/cashAccount",
		"cashAccountSrc/balanceInquiry",
		"cashAccountSrc/cashTransfer",
		"cashAccountSrc/detailSearch"
	], function (tpl, cashAccount, balanceInquiry, cashTransfer, detailSearch) {
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
				//加载 余额查询、货币转银行、明细查询
				try{
					args[dataId + 1].show(cashAccount);
				}catch(e){}
			});
			
			//加载第一个菜单
			$('#ul_myhs_right_tab li a:eq(0)').trigger('click');
		}
	};
});