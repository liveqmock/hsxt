define(['text!userpasswordTpl/dlmmcz/xtzcxx.html',
        "userpasswordDat/dlmmcz/userpwd"
        ], function(xtzcxxTpl,userpwd){
	return {
		showPage : function(){
			userpwd.findAllByResNo({"resNo": $("#ent_ResNo").val()},function(res){
				var allInfor=res.data;
				var statusInfo=allInfor.statusInfo;
				var baseInfor=allInfor.baseInfo;
				var objdata=allInfor.mainInfo;
				objdata.openDate=statusInfo.openDate;
				objdata.area=cacheUtil.getRegionByCode(baseInfor.countryCode,baseInfor.provinceCode,baseInfor.cityCode,"");
			   $('#infobox').html(_.template(xtzcxxTpl, {objdata:objdata}));
			});	
			
		}	
	}	
});