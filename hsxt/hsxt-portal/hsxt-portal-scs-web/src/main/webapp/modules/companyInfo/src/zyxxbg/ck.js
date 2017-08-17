define(['text!companyInfoTpl/zyxxbg/ck.html',
        'companyInfoDat/zyxxbg/zyxxbg',
		'companyInfoLan'],function(ckTpl,dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#contentWidth_3').html(_.template(ckTpl, data));
			$("#legalRepCreTypeNew").html(comm.getNameByEnumId(data.legalRepCreTypeNew, comm.lang("companyInfo").idCardTypeEnum));
			$("#legalRepCreTypeOld").html(comm.getNameByEnumId(data.legalRepCreTypeOld, comm.lang("companyInfo").idCardTypeEnum));
			$("#status").html(comm.getNameByEnumId(data.status, comm.lang("companyInfo").spStatusEnum2));
			
			if(comm.isNotEmpty(data.bizLicenseCrePicNew)){
				var picFileId1= '<a href="#" id="picFileId-1" class="mr10 blue">营业执照</a><br>';
				$("#picFiles").append(picFileId1);
				comm.initPicPreview("#picFileId-1", data.bizLicenseCrePicNew);
			}
			
			if(comm.isNotEmpty(data.linkAuthorizePicNew)){
				var picFiledId7 = '<a href="#" id="picFileId-7" class="mr10 blue">联系人授权委托书</a><br>';
				$("#picFiles").append(picFiledId7);
				comm.initPicPreview("#picFileId-7", data.linkAuthorizePicNew);
			}
			
			if(comm.isNotEmpty(data.imptappPic)){
				var imptappPic = '<a href="#" id="picFileId-6" class="mr10 blue">重要信息变更业务办理申请书</a>';
				$("#picFiles").append(imptappPic);
				comm.initPicPreview("#picFileId-6", data.imptappPic);
			}
			
			$('#btn_fh').click(function(){
				comm.liActive($('#zyxxbg_jlcx'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#zyxxbg_tpwjsc').css('display','none');	
                var obj = {
    					startDate:$("#searchstartDate").val(),
    					endDate:$("#searchendDate").val(),
    					status:$("#searchstatus").val()
    				};
                require(["companyInfoSrc/zyxxbg/zyxxbgcx"],function(zyxxbgcx){	
                	zyxxbgcx.showPage(obj);
                	zyxxbgcx.initData();
                });
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findEntChangeByApplyId({applyId:$("#applyId").val()}, function(res){
				self.initForm(res.data);
			});
		}
	}
}); 

 