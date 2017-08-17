define(function () {
	return {
		//
		getInvestDividend:function(gridId,detail){
			return comm.getCommBsGrid(gridId,"getInvestDividend",null,comm.lang("InvestmentBonus"),detail);
		},
		//
		addDividendRate:function(param, callback){
			comm.requestFun("addDividendRate",param,callback,comm.lang("InvestmentBonus"));
		}
	};
});