define(['text!accountManageTpl/jfzh/zhye.html',
        'accountManageDat/jfzh/jfzh'],function(zhyeTpl,dataModule){
	return {
		showPage : function(){
			$('#busibox').html(_.template(zhyeTpl)) ;
			//刷新点击事件
			$("#pointFlush").click(function () {
			
				//获取数据
				dataModule.findIntegralBalance(null,function (response){
					//操作成功
					$("#pointAcct").text(comm.formatMoneyNumber(response.data.pointBlance || '0'));						//积分数账户余额
					$("#usablePointNum").text(comm.formatMoneyNumber(response.data.usablePointNum || '0'));				//可用积分数
					$("#yesterdayPoint").text(comm.formatMoneyNumber(response.data.yesterdayPoint || '0'));				//今日积分数
					$("#securityPointNumber").text(response.data.securityPointNumber || '0');							//保底积分数
				});
				
			});
			
			//触发刷新事件，查询积分账户余额
			$("#pointFlush").trigger('click');
		}
	}
}); 
