define(["text!myHsCardTpl/importantInfoChange.html",
        "text!myHsCardTpl/importantInfoChange2.html",
        "text!myHsCardTpl/important_form_idCard.html",
        "text!myHsCardTpl/important_form_passport.html",
        "text!myHsCardTpl/important_form_business.html"], function (tpl, tpl2, idCardTpl, passportTpl, businessTpl) {
	var self = {
		realNameAuth : null,
		dataModule_ : null,
		show : function(dataModule){
			dataModule_ = dataModule;
			//加载页面
			$("#myhs_zhgl_box").html(tpl+tpl2);
			
			//获取绑定的手机号码
			dataModule.findMobileByCustId(null,function(response){
				if (response && response.data != '') 
				{
					//数据非空验证
					if (response.data ) 
					{
						var mobile = response.data.mobile ;
						$('#mobile').val(mobile);
					}
				}
			});
			
			dataModule.initPerChange(null, function(res){
				if(res.data.authStatus != "3"){
					$("#impInfoChange_view_div").hide();
					$("#noRealNameAuth").show();
					return;
				}
				if(res.data.imptStatus){
					$("#impInfoChange_view_div").hide();
					$("#impInfoChange_tip_Info").show();
					return;
				}else if(res.data.imptStatus==false && res.data.perChangeInfo!=null){
					var status=res.data.perChangeInfo.status;
					if(status == 2 ){//已通过
						$("#successDiv").show();
						$("#impInfoChange_view_div").hide();
					}else if(status == 3 || status == 4){//平台审批驳回  || 平台复核驳回
						$("#failDiv").show();
						$("#apprRemark").text(comm.removeNull(res.data.perChangeInfo.apprRemark));
						$("#updateDate").text(res.data.perChangeInfo.updateDate);
						$("#impInfoChange_view_div").hide();
					}
					$("#reApply-1, #reApply-2").click(function(){
						$("#impInfoChange_view_div").show();
						$("#failDiv").hide();
						$("#successDiv").hide();
						var obj = res.data.realNameAuth;
						obj.maskName = res.data.maskName;
						self.showDiv();
						self.realNameAuth = obj;
						self.setRelValue(obj, obj.cerType);
					});
				}else if(res.data.imptStatus==false && res.data.perChangeInfo==null){
					var obj = res.data.realNameAuth;
					obj.maskName = res.data.maskName;
					$("#certype").val(obj.cerType);
					self.showDiv();
					self.realNameAuth = obj;
					self.setRelValue(obj, obj.cerType);
				}
				
				//绑定上一步按钮
				$("#impInfoChange_lastBtn").click(function(){
					self.showDiv();
				});
				//绑定提交按钮
				$("#impInfoChange_submitBtn").click(function(){
					self.savaPic();
				});
				self.initTmpPicPreview();
			});
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#imp_lrcfmyBlogImage', '#imp_lrcbmyBlogImage', '#imp_lrccmyBlogImage', '#imp_lrcdmyBlogImage'];
			var imgIds = ['#imp_lrcfuploadImage_i', '#imp_lrcbuploadImage_i', '#imp_lrccuploadImage_i', '#imp_lrcduploadImage_i'];
//			$("body").on("change", "", function(){
//				for(var k = 0; k < imgIds.length; k++){
//					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
//				}
//			});
			comm.initUploadBtn(btnIds, imgIds);
		},
		/**
		 * 显示编辑界面
		 * @param obj 重要信息
		 * @param idType 证件类型
		 */
		showDiv : function(){
			$("#baseInfo").show();
			$("#impInfoChange_mod_div").hide();
		},
		/**
		 * 设置value
		 * @param obj 认证信息
		 * @param idType 证件类型
		 */
		setRelValue : function(obj, idType){
			//证件类型
			var idText = comm.getNameByEnumId(idType, comm.lang("myHsCard").credenceEnum);
			if(idType == 1){//身份证
				
				//加载样例图片
				cacheUtil.findDocList(function(map){
					comm.initPicPreview("#impInfo_demo_href_1", comm.getPicServerUrl(map.picList, '1001'), null);
					comm.initPicPreview("#impInfo_demo_href_2", comm.getPicServerUrl(map.picList, '1002'), null);
					comm.initPicPreview("#impInfo_demo_href_3", comm.getPicServerUrl(map.picList, '1005'), null);
					comm.initPicPreview("#impInfo_demo_href_4", comm.getPicServerUrl(map.picList, '1030'), null);
				});
				
				$('#baseInfo').html(_.template(idCardTpl, obj));
				$("#impInfoChange_oldCerType2_1").html(idText);
				$("#impInfoChange_userName2_1").html(obj.maskName);
				$("#impInfoChange_newCerType2_1").val(idText);
				$("#impInfoChange_oldSex2_1").html(comm.getNameByEnumId(obj.sex, comm.lang("errorCodeMgs").sexEnum));
				//加载国家信息从缓存中读取
				cacheUtil.findCacheContryAll(function(contryList){
					$("#impInfoChange_oldCountry2_1").html(comm.getCountryName(contryList, obj.countryCode));
					comm.initOption('#nationality', contryList , 'countryName','countryNo', true);
				});
				//长期按钮事件
				$("#impInfoChange_limitDateChk_1").click(function () {
					if ($(this).is(":checked")) {
						$("#validDate").val(comm.lang("myHsCard").longTerm).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');
					} else {
						$("#validDate").val('').removeAttr("disabled").parent().removeClass('bg_EFEFEF');
					}
				});
				/*日期控件*/
				$("#validDate").datepicker({
					dateFormat : 'yy-mm-dd',
					minDate: 0
				});
				//身份证将x转成X
				$("#cerNo").change(function(){
					var cerNo = $(this).val();
					cerNo = cerNo.replace(/x/g,'X');
					$("#cerNo").val(cerNo);
				});
				//绑定下一步按钮
				$("#impInfoChange_nextBtn_1").click(function(){
					if(!self.ID_validateData().form()){
						return;
					}
					//判断是否有变更的信息
					var change = self.getChanges(false);
					if(comm.isEmpty(change)){
						comm.error_alert(comm.lang("myHsCard").importNoChange);
						return;
					}
					$("#baseInfo").hide();
					$("#hjbgzm_li").show();
					//加载验证码
					$("#imp_validateCode").attr("src", dataModule_.getImportantValidateCode());
					
					// 验证码刷新
					$("#imp_validateCode, #imp_img-code").click(function () {
						$("#imp_validateCode").attr("src", dataModule_.getImportantValidateCode());
					});
					$("#impInfoChange_mod_div").show();
				});
			}else if(idType == 2){//护照
				
				//加载样例图片
				cacheUtil.findDocList(function(map){
					comm.initPicPreview("#impInfo_demo_href_1", comm.getPicServerUrl(map.picList, '1027'), null);
					comm.initPicPreview("#impInfo_demo_href_3", comm.getPicServerUrl(map.picList, '1022'), null);
				});
				
				$('#baseInfo').html(_.template(passportTpl, obj));
				$("#impInfoChange_oldCerType2_2").html(idText);
				$("#impInfoChange_userName2_2").html(obj.maskName);
				$("#impInfoChange_newCerType2_2").val(idText);
				$("#impInfoChange_oldSex2_2").html(comm.getNameByEnumId(obj.sex, comm.lang("errorCodeMgs").sexEnum));
				//加载国家信息从缓存中读取
				cacheUtil.findCacheContryAll(function(contryList){
					$("#impInfoChange_oldCountry2_2").html(comm.getCountryName(contryList, obj.countryCode));
					comm.initOption('#nationality', contryList , 'countryName','countryNo', true);
				});
				
				/*日期控件*/
				$("#validDate").datepicker({
					dateFormat : 'yy-mm-dd',
					minDate: 0
				});
				//绑定下一步按钮
				$("#impInfoChange_nextBtn_2").click(function(){
					if(!self.passport_validateData().form()){
						return;
					}
					//判断是否有变更的信息
					var change = self.getChanges(false);
					if(comm.isEmpty(change)){
						comm.error_alert(comm.lang("myHsCard").importNoChange);
						return;
					}
					
					$("#baseInfo").hide();
					$("#impInfoChange_mod_div").show();
					
					//护照和营业执照没有证件反面和户籍变更正面
					$("impInfoChange2_oppositeImg").hide();
					
					//加载验证码
					$("#imp_validateCode").attr("src", dataModule_.getImportantValidateCode());
					
					// 验证码刷新
					$("#imp_validateCode, #imp_img-code").click(function () {
						$("#imp_validateCode").attr("src", dataModule_.getImportantValidateCode());
					});
					
					$("#impInfoChange2_oppositeImg").hide();
				});
			}else{//营业执照
				
				//加载样例图片
				cacheUtil.findDocList(function(map){
					comm.initPicPreview("#impInfo_demo_href_1", comm.getPicServerUrl(map.picList, '1010'), null);
					comm.initPicPreview("#impInfo_demo_href_3", comm.getPicServerUrl(map.picList, '1023'), null);
				});
				
				$('#baseInfo').html(_.template(businessTpl, obj));
				$("#impInfoChange_oldCerType2_3").html(idText);
				$("#impInfoChange_entName2_3").html(obj.entName);
				$("#impInfoChange_newCerType2_3").val(idText);
				/*日期控件*/
				$("#entBuildDate").datepicker({
					dateFormat : 'yy-mm-dd',
					maxDate: '+0D'
				});
				//绑定下一步按钮
				$("#impInfoChange_nextBtn_3").click(function(){
					if(!self.businessLicense_validateData().form()){
						return;
					}
					//判断是否有变更的信息
					var change = self.getChanges(false);
					if(comm.isEmpty(change)){
						comm.error_alert(comm.lang("myHsCard").importNoChange);
						return;
					}
					
					$("#baseInfo").hide();
					$("#impInfoChange_mod_div").show();
					
					//护照和营业执照没有证件反面和户籍变更正面
					$("impInfoChange2_oppositeImg").hide();
					
					//加载验证码
					$("#imp_validateCode").attr("src", dataModule_.getImportantValidateCode());
					
					// 验证码刷新
					$("#imp_validateCode, #imp_img-code").click(function () {
						$("#imp_validateCode").attr("src", dataModule_.getImportantValidateCode());
					});
					
					$("#impInfoChange2_oppositeImg").hide();
				});
			}
		},
		/**
		 * 保存图片
		 */
		savaPic : function(){
			if(!self.validatePicturesData().form()){
				return;
			}
			var params = {
					vaildCode:$("#imp_checkcode").val(),
					codeType:"importantInfoChange"
			};
			//验证验证码是否正确
			comm.verificationCode(params,comm.lang("myHsCard"),function(resv){
				
				comm.i_confirm(comm.lang("myHsCard").importSubmit, function () {
					var ids = [];
					if($("#imp_lrcfmyBlogImage").val() != ""){
						ids[ids.length] = "imp_lrcfmyBlogImage";
					}
					if($("#imp_lrcbmyBlogImage").val() != ""){
						ids[ids.length] = "imp_lrcbmyBlogImage";
					}
					if($("#imp_lrccmyBlogImage").val() != ""){
						ids[ids.length] = "imp_lrccmyBlogImage";
					}
					if($("#imp_lrcdmyBlogImage").val() != ""){
						ids[ids.length] = "imp_lrcdmyBlogImage";
					}
					comm.uploadFile(null, ids, function (response) {
						if(response.imp_lrcfmyBlogImage){
							$('#imp_new_lrcFacePath').val(response.imp_lrcfmyBlogImage);
						}
						if(response.imp_lrcbmyBlogImage){
							$('#imp_new_lrcBackPath').val(response.imp_lrcbmyBlogImage);
						}
						if(response.imp_lrccmyBlogImage){
							$('#imp_new_lrcCackPath').val(response.imp_lrccmyBlogImage);
						}
						if(response.imp_lrcdmyBlogImage){
							$('#imp_new_lrcBlogPath').val(response.imp_lrcdmyBlogImage);
						}
						self.saveData();
						self.initTmpPicPreview();
					}, function(){
						self.initTmpPicPreview();
					});
				},650);
			});
		},
		/**
		 * 获取修改项
		 */
		getChanges : function(flag){
			var certype = self.realNameAuth.cerType;//$("#certype").val();
			var chg = [];
			$(".isChange").each(function(){
				if($(this).val() != "" && comm.removeNull($(this).val()) != comm.removeNull(self.realNameAuth[$(this).attr("name")])){
					chg.push($(this).parent().parent()[0].children[0].innerText);
				}
			});
			if(certype == "1" || certype == "2"){
				if($("#nationality").val() != "" && $("#nationality").val() != self.realNameAuth.countryCode){
					chg.push(comm.lang("myHsCard").country);	//国籍
				}
				if($("#validDate").val() != "" && $("#validDate").val() != self.realNameAuth.validDate){
					chg.push(comm.lang("myHsCard").cerValidate); //证件有效期
				}
			}
			if(flag){
				
				if(certype == "1"){
					chg.push(comm.lang("myHsCard").faceImg);			//证件正面
					chg.push(comm.lang("myHsCard").backImg);			//证件反面
					chg.push(comm.lang("myHsCard").addrPicImg);			//手持证件大头照
					chg.push(comm.lang("myHsCard").holdImg);			//户籍变更证明
				}else{
					chg.push(comm.lang("myHsCard").faceImg);			//证件正面
					chg.push(comm.lang("myHsCard").holdImg);			//手持证件大头照
				}
			}
			var str = "";
			for(var key in chg){
				str+=chg[key]+",";
			}
			return str.substring(0,str.length - 1);
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var params = {};
			params.changeItem = self.getChanges(true);
			params.mobile = $('#mobile').val();
			params.applyReason = $("#impInfoChange_appReason").val();
			params.nationalityOld = self.realNameAuth.countryCode;
			params.creTypeOld = self.realNameAuth.cerType;
			params.creNoOld = self.realNameAuth.cerNo;
			params.creExpireDateOld = self.realNameAuth.validDate;
			params.creIssueOrgOld = self.realNameAuth.issuingOrg;
			params.registorAddressOld = self.realNameAuth.birthAddress;
			params.professionOld = self.realNameAuth.job;
			params.creFacePicOld = self.realNameAuth.cerPica;
			params.creBackPicOld = self.realNameAuth.cerPicb;
			params.creHoldPicOld = self.realNameAuth.cerPich;
			params.issuePlaceOld = self.realNameAuth.issuePlace;
			params.entNameOld = self.realNameAuth.entName;
			params.entRegAddrOld = self.realNameAuth.entRegAddr;
			params.entTypeOld = self.realNameAuth.entType;
			params.entBuildDateOld = self.realNameAuth.entBuildDate;
			params.creTypeNew = self.realNameAuth.cerType;
			params.creNoNew = $("#cerNo").val();
			params.checkcode = $("#imp_checkcode").val(); //验证码
			params.creFacePicNew = $("#imp_new_lrcFacePath").val();
			params.creBackPicNew = $("#imp_new_lrcBackPath").val();
			params.creHoldPicNew = $("#imp_new_lrcCackPath").val();
			params.residenceAddrPic = $("#imp_new_lrcBlogPath").val();//户籍变更证明
			params.custName = $("#userName").val();
			if(self.realNameAuth.cerType == 1 || self.realNameAuth.cerType == 2){
				params.nameOld = self.realNameAuth.userName;
				params.sexOld = self.realNameAuth.sex;
				
				params.nameNew = $("#userName").val();
				params.sexNew = $("#sex").val();
				params.nationalityNew = $("#nationality").val();
				params.creExpireDateNew = $("#validDate").val();
				params.creIssueOrgNew = $("#issuingOrg").val();
				if(self.realNameAuth.cerType == 1){
					params.professionNew = self.realNameAuth.job;//$("#job").val();
					params.registorAddressNew = $("#birthAddress").val();
				}else{
					params.registorAddressNew = $("#birthPlace").val();
					params.issuePlaceNew = $("#issuePlace").val();
				}
			}else if(self.realNameAuth.cerType == 3){
				params.entNameNew = $("#entName").val();
				params.entRegAddrNew = $("#entRegAddr").val();
				params.entTypeNew = $("#entType").val();
				params.entBuildDateNew = $("#entBuildDate").val();
			}
			dataModule_.createPerChange(params, function(res){
				comm.alert({width : 800,content:comm.lang("myHsCard").importSubmitsuccessfully, callOk:function(){
					$("#impInfoChange_mod_div").hide();
					$("#impInfoChange_view_div").hide();
					$("#impInfoChange_tip_Info").show();
				}});
			});
		},
		
		/**
		 * 校验身份证类型
		 */
		ID_validateData : function(){
			return $("#impInfoChange_ID_form").validate({
				rules : {
					userName : {
						rangelength:[2, 20]
					},
					cerNo : {
						IDCard:true
					},
					issuingOrg : {
						rangelength:[2, 128]
					},
					birthAddress : {
						rangelength:[2, 128]
					}
				},
				messages : {
					userName : {
						rangelength : comm.lang("myHsCard").realNameReg.nameLength
					},
					cerNo : {
						IDCard:comm.lang("myHsCard").realNameReg.cernum
					},
					issuingOrg : {
						rangelength : comm.lang("myHsCard")[30191]
					},
					birthAddress : {
						rangelength : comm.lang("myHsCard")[30189]
					}
				},
				errorPlacement : function (error, element) {
					if (!$(element).is(":text")) {
						element = element.parent();
					}
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+150 bottom+35",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					if ($(element).is(":text")) {
						$(element).tooltip();
						$(element).tooltip("destroy");
					} else {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					}
				}
			});
		},
		/**
		 * 校验护照类型
		 */
		passport_validateData : function(){
			return $("#impInfoChange_passport_form").validate({
				rules : {
					userName : {
						rangelength:[2, 20]
					},
					cerNo : {
						passport:true
					},
					birthPlace : {
						rangelength:[2, 128]
					},
					issuePlace : {
						rangelength:[2, 128]
					},
					issuingOrg : {
						rangelength:[2, 128]
					}
				},
				messages : {
					userName : {
						rangelength : comm.lang("myHsCard").realNameReg.nameLength
					},
					cerNo : {
						passport:comm.lang("myHsCard").realNameReg.cernum
					},
					birthPlace : {
						rangelength:comm.lang("myHsCard")[30193]
					},
					issuePlace : {
						rangelength:comm.lang("myHsCard")[30194]
					},
					issuingOrg : {
						rangelength:comm.lang("myHsCard")[30195]
					}
				},
				errorPlacement : function (error, element) {
					if (!$(element).is(":text")) {
						element = element.parent();
					}
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+150 bottom+35",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					if ($(element).is(":text")) {
						$(element).tooltip();
						$(element).tooltip("destroy");
					} else {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					}
				}
			});
		},
		/**
		 * 校验营业执照类型
		 */
		businessLicense_validateData : function(){
			return $("#impInfoChange_businessLicense_form").validate({
				rules : {
					entName : {
						rangelength:[2, 64],
					},
					entRegAddr : {
						rangelength:[2, 128],
					},
					entType : {
						rangelength:[2, 20],
					},
					cerNo : {
					     businessLicence : true
				    }
				},
				messages : {
					entName : {
						rangelength:comm.lang("myHsCard").imporantReg.entNameLength
					},
					entRegAddr : {
						rangelength:comm.lang("myHsCard").imporantReg.entRegAddrLength
					},
					entType : {
						rangelength:comm.lang("myHsCard")[30196]
					},
					cerNo : {
						     businessLicence : comm.lang("myHsCard")[30135]
					}
					
				},
				errorPlacement : function (error, element) {
					if (!$(element).is(":text")) {
						element = element.parent();
					}
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+150 bottom+35",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					if ($(element).is(":text")) {
						$(element).tooltip();
						$(element).tooltip("destroy");
					} else {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					}
				}
			});
		},
		/**
		 * 校验图片上传文件
		 */
		validatePicturesData : function(){
			var validate = $("#impInfoChange_form").validate({
				rules : {
					imp_lrcfmyBlogImage : {
						required : true
					},
					imp_lrcbmyBlogImage : {
						required : function () {
							return $("#impInfoChange2_oppositeImg").is(":visible");
						}
					},
					imp_lrccmyBlogImage : {
						required : true
					},
					imp_lrcdmyBlogImage : {
						required : function () {
							return $("#hjbgzm_li").is(":visible");
						}
					},
					impInfoChange_appReason : {
						required : true,
						rangelength:[1, 200],
					},
					imp_checkcode:{
						required : true
					}
				},
				messages : {
					imp_lrcfmyBlogImage : {
						required : comm.lang("myHsCard").realNameAuth.requiredTop
					},
					imp_lrcbmyBlogImage : {
						required : comm.lang("myHsCard").realNameAuth.requiredBack
					},
					imp_lrccmyBlogImage : {
						required : comm.lang("myHsCard").realNameAuth.requiredCack
					},
					imp_lrcdmyBlogImage : {
						required : comm.lang("myHsCard").realNameAuth.requiredBlogImage
					},
					impInfoChange_appReason : {
						required : comm.lang("myHsCard")[30197],
						rangelength:comm.lang("myHsCard")[30198],
					},
					imp_checkcode:{
						required : comm.lang("myHsCard").realNameAuth.requiredCode
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left top+35",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}
					else{
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left top+35",
								at : "left top"
							}
						}).tooltip("open");	
					}
				},
				success : function (element) {
					if ($(element).is(":text")) {
						$(element).tooltip();
						$(element).tooltip("destroy");
					} else {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					}
				}
			});
			if($('#imp_new_lrcFacePath').val() != ""){
				validate.settings.rules.imp_lrcfmyBlogImage = {required:false};
			}
			if($('#imp_new_lrcBackPath').val() != ""){
				validate.settings.rules.imp_lrcbmyBlogImage = {required:false};
			}
			if($('#imp_new_lrcCackPath').val() != ""){
				validate.settings.rules.imp_lrccmyBlogImage = {required:false};
			}
			if($('#imp_new_lrcBlogPath').val() != ""){
				validate.settings.rules.imp_lrcdmyBlogImage = {required:false};
			}
			return validate;
		}
	};
	return self;
});
