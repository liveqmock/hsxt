define(['text!userpasswordTpl/jymmcz/lxxx.html',
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
				$('#infobox').html(_.template(lxxxTpl, obj));
				if(obj.authProxyFile!=null){
					comm.initPicPreview("#img", obj.authProxyFile, "");
					$("#img").attr("src", comm.getFsServerUrl(obj.authProxyFile));
				}
			});	
		}	
	}	
});