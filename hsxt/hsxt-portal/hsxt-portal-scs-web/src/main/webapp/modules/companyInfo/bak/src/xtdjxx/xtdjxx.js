define(["text!companyInfoTpl/xtdjxx/xtdjxx.html",
        "companyInfoDat/companyInfo"
        ], function(xtdjxxTpl, companyInfo){
	return{
		showPage : function(){
			var sysInfo = companyInfo.getSystemInfo();
			companyInfo.findSysRegisterInfo({"entCustId":sysInfo.entCustId},function (responseData) {
				$('#busibox').html(_.template(xtdjxxTpl, responseData.data));
				$("#currency").html(companyInfo.getSystemInfo().platform_currency);
			});
		}
	}
});