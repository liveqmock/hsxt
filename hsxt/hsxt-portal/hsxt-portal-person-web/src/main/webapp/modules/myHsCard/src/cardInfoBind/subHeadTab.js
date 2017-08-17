define(["text!myHsCardTpl/cardInfoBind/subHeadTab.html",
		//"myHsCardSrc/cardInfoBind/realNameBind",
		"myHsCardSrc/cardInfoBind/bankCardBind",
		"myHsCardSrc/cardInfoBind/mobileBind",
		"myHsCardSrc/cardInfoBind/mailBind",
		"myHsCardSrc/cardInfoBind/MyFastPaymentCard"
	], function (tplSubHeadTab) {
	//把参数转换成数组
	var args = [].slice.apply(arguments);
	return {
		show : function(dataModule){
			//加载子菜单
			$("#myhs_zhgl_box").html(tplSubHeadTab);
			//子菜单点击事件
			$('#myhs_kxxbd a').click(function (e) {
				var o = $(this),
				dataId = parseInt(o.attr('data-id'), 10);
				//清除选中状态
				$('#myhs_kxxbd a').removeClass('a_select');
				//切换激活状态
				o.addClass('a_select');
				//加载 实名绑定、银行卡绑定、手机绑定、邮箱绑定
				try{
					args[dataId].show(dataModule);
				}catch(e){}
			});
			
			//加载第一子个菜单
			$('#myhs_kxxbd a:eq(0)').trigger('click');
		}
	};
});
