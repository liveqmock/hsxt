define(['text!companyInfoTpl/yhzhxx/yhzhxx_sc.html' ,
        'companyInfoDat/yhzhxx/yhzhxx'
        ],function(yhzhxxscTpl,dataModoule){
	return {
		 
		showPage : function(){
			
			
			var bank = comm.getCache("companyInfo", "detailBank");
			$('#contentWidth_4').html(_.template(yhzhxxscTpl, bank));
			//$("#accountName").html(comm.getRequestParams().custEntName);//账户名称
			if(bank){
				//开户地区
				cacheUtil.syncGetRegionByCode(null, bank.provinceNo, bank.cityNo, "", function(resdata){
					$("#placeName").html(resdata);
				});
			}
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
			
			$('#yhzh_confirm_delete').click(function(){
				comm.i_confirm(comm.lang("companyInfo").confirmDelBank, function(){
					dataModoule.delBank({accId:bank.accId}, function(res){
						comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
							$('#qyxx_yhzhxx').click();
						}});
					});
				}, 380);
			})
			
			
			
		}
	}
}); 

 