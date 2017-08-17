define(["text!fckr_hsbAccountTpl/balanceInquiry.html"], function (tpl) {
	return {
		show : function(dataModule){
			// 加载余额查询
			$("#myhs_zhgl_box").html(tpl);

			$("#hsbFlush").click(function() {
				
				//查询互生币余额
				dataModule.findHsbBlance(null,function(response) {
					//获取数据值
					$("#htcashAcct").text(comm.formatMoneyNumber(response.data.circulationHsb) || '0');		//流通币
				});
			});
			//触发刷新事件，查询积分账户余额
			$("#hsbFlush").trigger('click');
		}
	};
});
