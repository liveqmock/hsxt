define(['text!userpasswordTpl/dlmmcz/yhzhxx.html',
        "userpasswordDat/dlmmcz/userpwd"
        ], function(yhzhxxTpl,userpwd){
	var self= {
		showPage : function(){
			$('#infobox').html(_.template(yhzhxxTpl));
			//var self = this;
			userpwd.findDefaultBankByEntResNo({"resNo": $("#ent_ResNo").val()},function(res){
				var list = res.data;
				if(list!=null){
					$("#hava_cards").show();
					 $("#no_cards").hide();
					//$("#add_bank").show();
					cacheUtil.findCacheLocalInfo(function(sysRes){
						//币种
						var currencyNameCn = sysRes.currencyNameCn;
					        //for(var key in list){
					        for(var key =0; key<list.length ;key++){
								self.addBank(currencyNameCn, list[key]);
							}
					});
			 }else{
				 $("#hava_cards").hide();
				 $("#no_cards").show();
			 }
				
			});		
        },
        addBank : function(currencyNameCn, bank){
        	var li = (bank.isDefaultAccount == 1)?$("#default_bank_ul").html():$("#bank_ul").html();
			li = li.replace("bankAccNo", comm.showLastCard(bank.bankAccNo));
			li = li.replace("bankCode", bank.bankCode);
			li = li.replace("currencyName", comm.removeNull(currencyNameCn));
			$("#banks").append(li);
        }
	};
	return self;
});