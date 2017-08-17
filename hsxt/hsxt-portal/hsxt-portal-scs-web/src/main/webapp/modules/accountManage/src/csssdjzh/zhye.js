define(['text!accountManageTpl/csssdjzh/zhye.html',
        "accountManageDat/csssdjzh/csssdjzh",
        "accountManageLan"
        ],function(zhyeTpl,csssdjzh){
	return {		
		showPage : function(){
			var self=this;
			$('#busibox').html(_.template(zhyeTpl)) ;			 
			//加载税金
			self.getTaxBalance();
			//刷新账户税金
			$("#aRefresh").click(function(){
				self.getTaxBalance();
			});	
		},
		/** 账户税金 */
		getTaxBalance:function(){
			//获取服务公司税金
			csssdjzh.cityTaxBalance({},function(rsp){
				var balance=rsp.data;
				
				$("#tHSBTaxBlance").html(comm.formatMoneyNumber(balance.hsbTaxBlance));
				$("#tIntegralTaxBlance").html(comm.formatMoneyNumber(balance.integralTaxBlance));
				$("#tCityTaxBlance").html(comm.formatMoneyNumber(balance.cityTaxBlance));
			});
		}
	}
}); 

 