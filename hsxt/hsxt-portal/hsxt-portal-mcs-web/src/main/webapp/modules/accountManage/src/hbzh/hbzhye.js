define(['text!accountManageTpl/hbzh/hbzhye.html',
        "accountManageDat/hbzh/hbzh",
        "accountManageLan" ],function(jfzhyeTpl,hbzh){
	return {
		showPage : function(){
			var self=this;
			$('#busibox').html(_.template(jfzhyeTpl)) ;
			 
			//加载余额
			self.getCurrencyBlance();
			//刷新余额
			$("#aRefresh").click(function(){
				self.getCurrencyBlance();
			});
			
			//刷新按钮单击事件
			$('#hbPointFlush').click(function(){
				self.getCurrencyBlance();
			});
		   	
		},
		/** 获取积分余额 */
		getCurrencyBlance:function(){
			//获取积分余额
			hbzh.findCurrencyBlance(function(rsp){
				var balance=rsp.data;
				var title; //积分标题
				//平台所在区域
				switch (balance.countryNo) {
				case "156":
					title="人民币"; 
					break;
				case "344":
					title="港币";   
					break;
				case "446":
					title="澳门元";
					break;
				}
				$("#hbPointCNY").html(comm.formatMoneyNumber(balance.currencyBlance)); //账户余额

			});
		}
	}
}); 

 