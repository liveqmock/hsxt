define(['text!userpasswordTpl/dlmmcz/lxxx.html',
        "userpasswordDat/dlmmcz/userpwd"
        ], function(lxxxTpl,userpwd){
	return {
		showPage : function(){
			userpwd.findAllByResNo({"resNo": $("#ent_ResNo").val()},function(res){
				var allInfor=res.data;
				var baseInfor=allInfor.baseInfo;
				var obj=allInfor.mainInfo;
				obj.contactAddr=baseInfor.contactAddr;
				obj.postCode=baseInfor.postCode;
				obj.contactEmail=baseInfor.contactEmail;
				obj.custType = baseInfor.entCustType;
				obj.startResType = baseInfor.startResType;
				
				$('#infobox').html(_.template(lxxxTpl, obj));
				if(obj.authProxyFile!=null){
					comm.initPicPreview("#img", obj.authProxyFile, "");
					$("#img").attr("src", comm.getFsServerUrl(obj.authProxyFile));
				}
 				if(3 == obj.custType && 2 == obj.startResType){
 					var helpAgreement = allInfor.extendInfo.helpAgreement;
 					if(comm.isNotEmpty(helpAgreement)){
 						comm.initPicPreview("#other_protocol", helpAgreement, "");
 						$("#other_protocol").attr("src", comm.getFsServerUrl(helpAgreement));
     				}
 				}
			});	
		}	
	}	
});