define(['text!accountManageTpl/csssdjzh/zhye.html',
        "accountManageDat/csssdjzh/csssdjzh",
        "accountManageLan"], function(tpl,csssdjzh){
	var zhye = {
		showPage : function(){
			$('#busibox').html(_.template(tpl));
			
			//加载税金
			zhye.getTaxBalance();
			//刷新账户税金
			$("#aRefresh").click(function(){
				zhye.getTaxBalance();
			});	
		},
		/** 账户税金 */
		getTaxBalance:function(){
			//获取服务公司税金
			csssdjzh.cityTaxBalance(function(rsp){
				var balance=rsp.data;
				$("#tHSBTaxBlance").html(comm.formatMoneyNumber(balance.hsbTaxBlance));
				$("#tIntegralTaxBlance").html(comm.formatMoneyNumber(balance.integralTaxBlance));
				$("#tCityTaxBlance").html(comm.formatMoneyNumber(balance.cityTaxBlance));
			});
		}
	}
	return zhye;
});