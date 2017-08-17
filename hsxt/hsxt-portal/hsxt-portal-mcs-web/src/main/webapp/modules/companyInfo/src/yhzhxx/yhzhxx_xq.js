define(['text!companyInfoTpl/yhzhxx/yhzhxx_xq.html' ],function(yhzhxxxqTpl ){
	return {
		 
		showPage : function(){
			var bank = comm.getCache("companyInfo", "detailBank");
			$('#contentWidth_4').html(_.template(yhzhxxxqTpl, bank));
			//$("#accountName").html(comm.getRequestParams().custEntName);//账户名称
			if(bank){
				//开户地区
				cacheUtil.syncGetRegionByCode(null, bank.provinceNo, bank.cityNo, "", function(resdata){
					$("#placeName").html(resdata);
				});
			}
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
		}
	}
}); 

 