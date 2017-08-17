define(["text!pointAccountTpl/balanceInquiry.html"], function (tpl) {
	return {
		show : function(dataModule){
			//加载余额查询
			$("#myhs_zhgl_box").html(tpl)
			//刷新点击事件
			$("#pointFlush").click(function () {
				$("#pointFlush").prop("disabled","disabled");
				//获取数据
				dataModule.getAssetValuePoint(null,function (response){
					//操作成功
					$("#pointAcct").text(comm.formatMoneyNumber(response.data.pointBlance || '0'));						//积分数账户余额
					$("#usablePointNum").text(comm.formatMoneyNumber(response.data.usablePointNum || '0'));				//可用积分数
					$("#todaysNewPoint").text(comm.formatMoneyNumber(response.data.todaysNewPoint || '0'));				//今日积分数
					$("#securityPointNumber").text(response.data.securityPointNumber || '0');							//保底积分数
					$("#pointFlush").prop("disabled","");
				});
				
			});
			
			//触发刷新事件，查询积分账户余额
			$("#pointFlush").trigger('click');
		}
	};
});
