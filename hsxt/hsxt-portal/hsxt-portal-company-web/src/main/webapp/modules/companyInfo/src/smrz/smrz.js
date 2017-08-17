define(['text!companyInfoTpl/smrz/smrz.html',
        'companyInfoDat/smrz/smrz',
		'companyInfoLan'],function(smrzTpl, dataModoule){
	return {
		entInfo : null,
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			 
			var self = this;
			if(comm.isNotEmpty(data.entExtendInfo)){
				entInfo = data.entExtendInfo?data.entExtendInfo:{};
			}else if(data.isRealnameAuth){
				entInfo = {};
			}
			$('#busibox').html(_.template(smrzTpl, entInfo));
			if(data.isRealnameAuth){//认证通过
				$("#smrz_ztts_tpl").show();
				return;
			}else{
				var realNameObj = data.entRealnameAuth?data.entRealnameAuth:{};				
				if(realNameObj.status == 0){//待审批
					if(comm.isNotEmpty(realNameObj.createdDate)&&realNameObj.createdDate.length > 10){
						realNameObj.createdDate = realNameObj.createdDate.substr(0,10);
					}
					$('#apprShowMsg').text(comm.strFormat(comm.lang("companyInfo").realNameAuthAppr,[realNameObj.createdDate]));
					$("#smrz_spz_tpl").show();
					return;
				}else if(realNameObj.status == 1){//审批中
					$('#apprShowMsg').text(comm.strFormat(comm.lang("companyInfo").realNameAuthAppr,[realNameObj.createdDate]));
					$("#smrz_spz_tpl").show();
					return;
				}
				if(realNameObj.status == 3 || realNameObj.status == 4){//审批驳回
					$("#status_3").show();
					$("#smrz_csrz").show();
					$("#smrz_zt_tpl").show();
					$("#apprDate").html(comm.removeNull(realNameObj.apprDate));
					$("#backMessage").html(comm.removeNull(realNameObj.apprContent));
					this.picPreview(realNameObj);
				}else{
					$("#smrz_tpl").show();
				}
			}
			
			//重新实名认证
			$('#smrz_csrz').click(function(){
				$("#smrz_tpl").show();
				$("#smrz_zt_tpl").hide();
			});
			
			//加载样例图片
			cacheUtil.findDocList(function(map){
				comm.initPicPreview("#busLicenceNo_img_case", comm.getPicServerUrl(map.picList, '1010'), null);
			});
			
			//提交
			$('#smrz_tj').click(function(){
				self.saveFile();
			});
			
			//加载验证码
			$("#validateCode").attr("src",dataModoule.getCodeUrl());
			
			//换一张验证码
			$('#btn_sqcz_yzm,#validateCode').click(function(){
				$("#validateCode").attr("src",dataModoule.getCodeUrl());
			});
			
			this.initTmpPicPreview();
		},
		/**
		 * 显示图片
		 */
		picPreview : function(realNameObj){
			comm.initPicPreview("#preview_3", comm.removeNull(realNameObj.licensePic));
			$("#preview_3").html("<img width='107' height='64' src='"+comm.getFsServerUrl(comm.removeNull(realNameObj.licensePic))+"'/>");
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#busLicenceFileId'];
			var imgIds = ['#preview-3'];
			comm.initUploadBtn(btnIds, imgIds);
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.initEntRealName(null, function(res){
				self.initForm(res.data);
			});
		},
		/**
		 * 保存图片
		 */
		saveFile : function(){
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var params = {
					vaildCode:$('#input_verificationCode').val(),
					codeType:"realNameAuth"
				};
			comm.verificationCode(params,comm.lang("companyInfo"),function(res){
				
				var ids = ['busLicenceFileId'];
				comm.uploadFile(null, ids, function(data){
					if(data.busLicenceFileId){
						$("#fileId-3").val(data.busLicenceFileId);
					}
					self.saveData();
					self.initTmpPicPreview();
				}, function(err){
					self.initTmpPicPreview();
				}, {delFileIds:null});
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			return $("#qysczl_form").validate({
				rules : {
					busLicenceFileId : {
						required : $("#biz_li").is(":visible")
					},
					postScript : {
						rangelength : [2, 300]
					},
					input_verificationCode : {
						required : true
					}
				},
				messages : {
					busLicenceFileId : {
						required :  comm.lang("companyInfo")[31074]
					},
					postScript : {
						rangelength : comm.lang("companyInfo")[31087]
					},
					input_verificationCode : {
						required : comm.lang("companyInfo")[31095]
					}
				},
				errorPlacement : function (error, element) {
					
					if($(element).attr('id')=='input_verificationCode' || $(element).attr('id')=='postScript'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+2 top+30",
								at : "left top"
							}
						}).tooltip("open");
						
					}else{
						$(element).parent().parent().attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+2 top+30",
								at : "left top"
							}
						}).tooltip("open");
					}
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var params = {};
			params.entCustName = entInfo.entCustName;		//企业名称
			params.countryNo = entInfo.countryNo;			//国家代码	
			params.provinceNo = entInfo.provinceNo;			//省份代码
			params.cityNo = entInfo.cityNo;					//城市代码
			params.entAddr = entInfo.entRegAddr;			//注册地址
			params.linkman = entInfo.contactPerson;			//联系人
			params.mobile = entInfo.contactPhone;			//联系人电话
			params.legalName = entInfo.legalPerson;			//企业法人
			params.licenseNo = entInfo.busiLicenseNo;		//营业执照号码
			
			params.licensePic = $("#fileId-3").val();		//营业执照扫描件
			params.postScript = $("#postScript").val();		//认证附言
			params.custType = comm.getRequestParams().entResType;//企业类型
			params.verificationCode=$("#input_verificationCode").val(); //验证码
			dataModoule.createEntRealNameAuth(params, function(res){
				comm.alert({content:comm.lang("companyInfo").realNameSubmitSuccess, callOk:function(){
					$('#smrzxx').click();
				}});
			},function(){
				$("#validateCode").attr("src",dataModoule.getCodeUrl());
			});
		}
	}
}); 

 