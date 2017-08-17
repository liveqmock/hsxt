define(['text!userpasswordTpl/dlmmcz/gsdjxx.html',
        "userpasswordDat/dlmmcz/userpwd",
        "userpasswordLan"
        ], function(gsdjxxTpl,userpwd){
	return {
		showPage : function(){
	          //
			userpwd.findAllByResNo({"resNo": $("#ent_ResNo").val()},function(res){
				var allInfor=res.data;
				var baseInfor=allInfor.baseInfo;
				var obj=allInfor.mainInfo;
				var nature = allInfor.extendInfo.nature;
				if(null == nature){
					nature = '';
				}
				obj['nature'] = nature;
				obj.buildDate=baseInfor.buildDate;
				obj.endDate=baseInfor.endDate;
				obj.businessScope=baseInfor.businessScope;
				obj.entCustTypeName=comm.lang("consumerInfo").custType[allInfor.statusInfo.custType];
				obj.legalPersonId=allInfor.extendInfo.legalPersonId;
				obj.credentialType=comm.lang("consumerInfo").credentialTypeEnum[allInfor.extendInfo.credentialType];
			    $('#infobox').html(_.template(gsdjxxTpl, {obj:obj}));
				var busiLicenseImg = obj.busiLicenseImg;
				if(comm.isNotEmpty(busiLicenseImg)){
					$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
				}
				
				$("#busi_license_img").click(function(){//企业营业执照扫描件图片预览
			        comm.initTmpPicPreview("#busi_license_span",comm.getFsServerUrl(busiLicenseImg),"营业执照扫描件预览");
			    });
			    
				
			});
		}	
	}	
});