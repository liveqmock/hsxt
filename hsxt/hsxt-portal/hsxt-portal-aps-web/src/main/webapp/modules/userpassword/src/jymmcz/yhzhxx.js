define(['text!userpasswordTpl/jymmcz/yhzhxx.html',
        "userpasswordDat/dlmmcz/userpwd"
        ], function(yhzhxxTpl,userpwd){
	return {
		showPage : function(){
			$('#infobox').html(_.template(yhzhxxTpl));
			userpwd.findDefaultBankByEntResNo({"resNo": $("#ent_ResNo").val()},function(res){
				if(res.data!=null){
					$("#bankNumberText").html(comm.hideCard(res.data.bankAccNo));
					$("#bankNameText").html(res.data.bankAccName);
				}
			});
		}	
	}	
});