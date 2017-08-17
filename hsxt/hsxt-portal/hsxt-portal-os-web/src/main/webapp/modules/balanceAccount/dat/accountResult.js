define(function () {
	return {
		/*积分福利资格分页查询**/
		payAndBankList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"payAndBankList",params,comm.lang("accountResult"),detail,del, add, edit);
		},
		/*支付与业务分页查询**/
		payAndBusinessList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"payAndBusinessList",params,comm.lang("accountResult"),detail,del, add, edit);
		},
		/*业务与账务分页查询**/
		businessAndAcList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"businessAndAcList",params,comm.lang("accountResult"),detail,del, add, edit);
		},
		/*积分与账务分页查询**/
		pvAndAcList:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"pvAndAcList",params,comm.lang("accountResult"),detail,del, add, edit);
		},
		
	
	};
});