define(["text!cashAccountTpl/balanceInquiry.html"], function (tpl) {
	return {
		show : function(dataModule){
			// 加载余额查询
			$("#myhs_zhgl_box").html(tpl);
			// 刷新点击事件
			$("#cashFlush").click(function () {
				//查询货币余额
				dataModule.findCurrencyBlance(null, function (response) {
					$("#cashAcctTotal").text(comm.formatMoneyNumber(response.data.currencyBlance));
				});
			});
			// 触发刷新事件，查询积分账户余额
			$("#cashFlush").trigger('click');
		}
	};
});