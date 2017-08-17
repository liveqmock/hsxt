define(["text!myHsCardTpl/realNameAuth.html",
        "text!myHsCardTpl/realNameAuth2.html",
		'myHsCardLan'], function (tpl, tpl2) {
	var realNameAuth = {
		_dataModule : null,
		show : function(dataModule){
			var self = this;
			//加载页面
			$("#myhs_zhgl_box").html(tpl + tpl2);
			this._dataModule = dataModule;
			
			//绑定图片显示
			realNameAuth.initTmpPicPreview();
			
			//tfs服务器路径
			var tfsPath = '';
			//加载国家信息从缓存中读取
			cacheUtil.findCacheContryAll(function(contryList){
				comm.initOption('#trueNameAut_country_1', contryList , 'countryName','countryNo');
				comm.initOption('#trueNameAut_country_2', contryList , 'countryName','countryNo');
			});
			
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
			//初始化界面数据
			dataModule.initAuthentication(null,function(response){
				if (response) {
					//检查认证状态
					if(!self.checkAuthStatus(response.data.authStatus)){
						return;
					}
					//判断认证信息
					if(!response.data.realNameReg){
						return;
					}
					//注册信息
					var dataParam = {};
					dataParam.custName = response.data.realNameReg.realName;//用户姓名
					dataParam.country = response.data.realNameReg.countryCode;//国家编码
					dataParam.certype = response.data.realNameReg.certype;//证件类型
					dataParam.creNo = response.data.realNameReg.cerNo;//证件编号
					dataParam.entName = response.data.realNameReg.entName;//企业名称
					dataParam.entRegAddr = response.data.realNameReg.entRegAddr;//注册地址
					//认证信息
					var realNameObj = (response.data.realNameAuth)?response.data.realNameAuth:{};
					var realNameReg = response.data.realNameReg;
					if(realNameObj.status == 0){//待审批
						self.showReuslt(0, realNameObj);
						return;
					}else if(realNameObj.status == 1){//审批中
						self.showReuslt(1, realNameObj);
						return;
					}
					if(realNameObj.status == 3 || realNameObj.status == 4){//审批驳回
						self.showReuslt(3, realNameObj);
					}
					
					//保存设置的证件类型以及申请编号
					$("#certype").val(dataParam.certype);
					$("#applyId").val(comm.removeNull(realNameObj.applyId));
					
					//证件类型
					switch(dataParam.certype){
						case '1' : 
							//设置div显示
							if(realNameObj.status != 3 && realNameObj.status != 4){//为编辑状态
								$('#ID_div').show();
								$('#passport_div').hide();
								$('#BusinessLicense_div').hide();
							}
							
							// 初始化正价类型下拉框
							comm.initSelectOption('#trueNameAut_certype_1', comm.lang("myHsCard").credenceEnum);
							
							//证件有效期
							$("#trueNameAut_creExpiryDate_1").datepicker({
								"changeMonth" : true,
								"changeYear" : true,
								"dateFormat" : "yy-mm-dd",
								"minDate" : new Date()
							});
							
							//回填值
							$('#trueNameAut_name_1').val(dataParam.custName);
							$("#trueNameAut_country_1").val(dataParam.country).attr("disabled", "disabled")
								.parent().addClass('bg_EFEFEF').append('<span class="mask-gray"></span>');
							$("#trueNameAut_certype_1").val(dataParam.certype).attr("disabled", "disabled")
								.parent().addClass('bg_EFEFEF').append('<span class="mask-gray"></span>');
							$("#trueNameAut_creNo_1").val(dataParam.creNo).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');
							
							//长期按钮事件
							$("#trueNameAut_limitDateChk_1").click(function () {
								if ($(this).is(":checked")) {
									$("#trueNameAut_creExpiryDate_1").val('长期').attr("disabled", "disabled").parent().addClass('bg_EFEFEF');
								} else {
									$("#trueNameAut_creExpiryDate_1").val('').removeAttr("disabled").parent().removeClass('bg_EFEFEF');
								}
							});
							//下一步单击事件
							$("#trueNameAut_submitBtn_1").click(function () {
								var valid = realNameAuth.ID_validateData();
								if (!valid.form()) {
									return;
								}
								$('#ID_div').hide();
								$('#again_shiming').hide();
								$('#verification_status0_li').hide();
								$('#verification_status1_li').hide();
								$('#verification_bohui1_li').hide();
								$('#verification_bohui2_li').hide();
								$("#register_step_two").show();
								$("#trueNameAut_mod_div").show();
								$("#trueNameAut2_lastBtn").show();
								$("#trueNameAut2_submitBtn").show();
								$("#verification_checkcode_li").show();
								self.showBtn("");
							});
							break;
						case '2' : 
							//设置div显示
							if(realNameObj.status != 3 && realNameObj.status != 4){//为编辑状态
								$('#ID_div').hide();
								$('#passport_div').show();
								$('#BusinessLicense_div').hide();
							}
							
							// 初始化正价类型下拉框
							comm.initSelectOption('#trueNameAut_certype_2', comm.lang("myHsCard").credenceEnum);
							
							//证件有效期
							$("#trueNameAut_creExpiryDate_2").datepicker({
								"changeMonth" : true,
								"changeYear" : true,
								"dateFormat" : "yy-mm-dd",
								"minDate" : new Date()
							});
							
							//回填值
							$('#trueNameAut_name_2').val(dataParam.custName);
							$("#trueNameAut_country_2").val(dataParam.country).attr("disabled", "disabled")
								.parent().addClass('bg_EFEFEF').append('<span class="mask-gray"></span>');
							$("#trueNameAut_certype_2").val(dataParam.certype).attr("disabled", "disabled")
								.parent().addClass('bg_EFEFEF').append('<span class="mask-gray"></span>');
							$("#trueNameAut_creNo_2").val(dataParam.creNo).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');
							
							//下一步单击事件
							$("#trueNameAut_submitBtn_2").click(function () {
								if(!realNameAuth.passport_validateData().form()) {
									return;
								}
								$('#passport_div').hide();
								$('#again_shiming').hide();
								$('#verification_status0_li').hide();
								$('#verification_status1_li').hide();
								$('#verification_bohui1_li').hide();
								$('#verification_bohui2_li').hide();
								$("#register_step_two").show();
								$("#trueNameAut_mod_div").show();
								$("#trueNameAut2_lastBtn").show();
								$("#trueNameAut2_submitBtn").show();
								$("#verification_checkcode_li").show();
								self.showBtn("");
							});
							
							break;
						case '3' : 
							//设置div显示
							if(realNameObj.status != 3 && realNameObj.status != 4){//为编辑状态
								$('#ID_div').hide();
								$('#passport_div').hide();
								$('#businessLicense_div').show();
							}
							// 初始化正价类型下拉框
							comm.initSelectOption('#trueNameAut_certype_3', comm.lang("myHsCard").credenceEnum);
							$("#trueNameAut_certype_3").val(dataParam.certype).attr("disabled", "disabled")
								.parent().addClass('bg_EFEFEF').append('<span class="mask-gray"></span>');
							$("#trueNameAut_creNo_3").val(dataParam.creNo).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');
							$("#enterpriseName_3").val(comm.removeNull(dataParam.entName));//企业名称
							$("#registeredAddress_3").val(comm.removeNull(dataParam.entRegAddr));//注册地址
							
							//下一步单击事件
							$("#trueNameAut_submitBtn_3").click(function () {
								if (!realNameAuth.businessLicense_validateData().form()) {
									return;
								}
								$('#businessLicense_div').hide();
								$('#again_shiming').hide();
								$('#verification_status0_li').hide();
								$('#verification_status1_li').hide();
								$('#verification_bohui1_li').hide();
								$('#verification_bohui2_li').hide();
								$("#register_step_two").show();
								$("#trueNameAut_mod_div").show();
								$("#trueNameAut2_lastBtn").show();
								$("#trueNameAut2_submitBtn").show();
								$("#verification_checkcode_li").show();
								self.showBtn("");
							});
							
							/*日期控件*/
							$("#datOfEstablishment_3").datepicker({
								dateFormat : 'yy-mm-dd',
								maxDate : -1
							});
							break;	
					}
					
					//真实姓名
					if (dataParam.certype) {
						
							//设置图片上的lable内容
							if(dataParam.certype == 1){//只有身份证才显示反面图片
								$("#trueNameAut2_oppositeImg").show();
							}else if(dataParam.certype == 2){  //护照
								$("#trueNameAut2_oppositeImg").hide();
							}else if(dataParam.certype == 3){  //营业执照
								$("#trueNameAut2_oppositeImg").hide();
							}
							
						//加载样例图片
						cacheUtil.findDocList(function(map){
							if ('2' == dataParam.certype) {//护照
								comm.initPicPreview("#verification_demo_href_1", comm.getPicServerUrl(map.picList, '1027'), null);
								comm.initPicPreview("#verification_demo_href_3", comm.getPicServerUrl(map.picList, '1022'), null);
							} else if ('3' == dataParam.certype) {//营业执照
								comm.initPicPreview("#verification_demo_href_1", comm.getPicServerUrl(map.picList, '1010'), null);
								comm.initPicPreview("#verification_demo_href_3", comm.getPicServerUrl(map.picList, '1023'), null);
							} else if ('1' == dataParam.certype) {//身份证
								comm.initPicPreview("#verification_demo_href_1", comm.getPicServerUrl(map.picList, '1001'), null);
								comm.initPicPreview("#verification_demo_href_2", comm.getPicServerUrl(map.picList, '1002'), null);
								comm.initPicPreview("#verification_demo_href_3", comm.getPicServerUrl(map.picList, '1005'), null);
							}
						});
					}
					
				}
			});
			
			//上一步返回按钮
			$("#trueNameAut2_lastBtn").click(function () {
				var certype = comm.removeNull($("#certype").val());
				if(certype == 1){
					$("#ID_div").show();
					$("#trueNameAut_submitBtn_1").show();
				}else if(certype == 2){
					$("#passport_div").show();
					$("#trueNameAut_submitBtn_2").show();
				}else{
					$("#businessLicense_div").show();
					$("#trueNameAut_submitBtn_3").show();
				}
				$("#register_step_two").hide();
				$("#trueNameAut_mod_div").hide();
			});
			
			//提交按钮
			$("#trueNameAut2_submitBtn").click(function () {
				var valid = realNameAuth.validatePicturesData();
				if (!valid.form()) {
					return;
				}
				
				var params = {
						vaildCode:$("#checkcode").val(),
						codeType:"realNameAuth"
				};
				//验证验证码
				comm.verificationCode(params,comm.lang("myHsCard"),function(resv){
					
					var ids = [];
					var delFileIds = [];
					if($("#lrcfmyBlogImage").val() != ""){
						if($("#lrcFacePath").val() != ""){
							delFileIds[delFileIds.length] = $("#lrcFacePath").val();
						}
						ids[ids.length] = "lrcfmyBlogImage";
					}
					if($("#lrcbmyBlogImage").val() != ""){
						if($("#lrcBackPath").val() != ""){
							delFileIds[delFileIds.length] = $("#lrcBackPath").val();
						}
						ids[ids.length] = "lrcbmyBlogImage";
					}
					if($("#lrccmyBlogImage").val() != ""){
						if($("#lrcCackPath").val() != ""){
							delFileIds[delFileIds.length] = $("#lrcCackPath").val();
						}
						ids[ids.length] = "lrccmyBlogImage";
					}
					if(ids.length == 0){//图片无改变直接保存数据
						self.saveData(dataModule);
					}else{//图片上传
						comm.uploadFile(null, ids, function (response) {
							if(response.lrcfmyBlogImage){
								$('#lrcFacePath').val(response.lrcfmyBlogImage);
							}
							if(response.lrcbmyBlogImage){
								$('#lrcBackPath').val(response.lrcbmyBlogImage);
							}
							if(response.lrccmyBlogImage){
								$('#lrcCackPath').val(response.lrccmyBlogImage);
							}
							self.saveData(dataModule);
						}, function(){
							self.initTmpPicPreview();
						});
					}
					self.initTmpPicPreview();
				});
			});
			
			self.validateCode(dataModule);
			// 验证码刷新
			$("#validateCode, #img-code").click(function () {
				self.validateCode(dataModule);
			});
		},
		//生存验证码图片
		validateCode:function(dataModule){
			$("#validateCode").attr("src", dataModule.getValidateCode());
		},
		/**
		 * 保存数据
		 * @param dataModule
		 */
		saveData : function(dataModule){
			var self = this;
			//组织公共参数
			var jsonParams = {
				certype	:	$('#certype').val(),		//证件类别
				cerpica	:	$('#lrcFacePath').val(),	//证件正面
				cerpicb	:	$('#lrcBackPath').val(),	//证件反面
				cerpich	:	$('#lrcCackPath').val(),    //手持证件大头照
				applyId	:	comm.removeNull($('#applyId').val()),//申请编号
				checkcode:  $("#checkcode").val(), //验证码
				mobile :	$("#mobile").val()
			};
			//根据证件类型获取参数
			switch(jsonParams.certype){
				case '1' : //身份证
					jsonParams.name = $('#trueNameAut_name_1').val();//姓名
					jsonParams.sex = $("#trueNameAut_sex_1  option:selected").val();//性别
					jsonParams.countryNo =  $('#trueNameAut_country_1').val();	//国家
					jsonParams.credentialsNo = $("#trueNameAut_creNo_1").val();//证件号码
					jsonParams.validDate = $("#trueNameAut_creExpiryDate_1").val();//证件有效期
					jsonParams.birthAddr = $("#trueNameAut_birthAddress_1").val();//户籍所在地
					jsonParams.profession = $("#trueNameAut_profession_1").val();//职业
					jsonParams.licenceIssuing = $("#trueNameAut_licenceIssuing_1").val();//发证机关
					jsonParams.postScript = $("#trueNameAut_postscript_1").val();//认证附言
					break;
				case '2' ://护照
					jsonParams.name = $('#trueNameAut_name_2').val();//姓名
					jsonParams.sex = $("#trueNameAut_sex_2  option:selected").val();//性别
					jsonParams.countryNo =  $('#trueNameAut_country_2').val();	//国家
					jsonParams.credentialsNo = $("#trueNameAut_creNo_2").val();//证件号码
					jsonParams.validDate = $("#trueNameAut_creExpiryDate_2").val();//证件有效期
					jsonParams.birthAddr = $("#PlaceOfBirth_2").val();//出生地点
					jsonParams.issuePlace = $("#PlaceOfIssue_2").val();//签发地点
					jsonParams.licenceIssuing = $("#issuingAuthority_2").val();//签发机关
					jsonParams.postScript = $("#trueNameAut_postscript_2").val();//认证附言
					break;
				case '3' ://营业执照
					jsonParams.entName = $('#enterpriseName_3').val();//企业名称
					jsonParams.entRegAddr = $("#registeredAddress_3").val();//注册地址
					jsonParams.entBuildDate =  $('#datOfEstablishment_3').val();//成立日期
					jsonParams.credentialsNo = $("#trueNameAut_creNo_3").val();//证件号码
					jsonParams.entType = $("#companyType_3").val();//公司类型
					jsonParams.postScript = $("#trueNameAut_postscript_3").val();//认证附言
					break;
			}
			//实名注册
			dataModule.authentication(jsonParams, function (response) {
				comm.alert({content:comm.lang("myHsCard").relNameAuthuccessfully, callOk:function(){
					self.showReuslt(0, jsonParams);//页面跳转
				}});
			});
		},
		/**
		 * 检查认证状态，并做出具体显示与隐藏
		 * @param authStatus 认证状态
		 */
		checkAuthStatus : function(authStatus){
			if(authStatus == "2"){//已实名注册
				return true;
			}
			if(authStatus == '1'){//未实名注册
				
				//改成温馨提示
				$('#trueNameAut_view_div').addClass('none');
				$('#noRealNameReg').removeClass('none');								
				
			}else{//已实名认证
				$("#ID_div").hide();
				$('#verification_success_div').show();
			}
			return false;
		},
		/**
		 * 显示结果
		 * @param status 审批状态
		 * @param realNameAuth 认证信息
		 */
		showReuslt : function(status, realNameAuth){
			if(status == 0){
				$("#verification_status0_li").show();
			}else if(status == 1){
				$("#verification_status1_li").show();
			}else{
				$('#ID_div').hide();
				$('#passport_div').hide();
				$('#BusinessLicense_div').hide();
				$("#again_shiming").show();
				$("#verification_bohui1_li").show();
				$("#verification_bohui2_li").show();
				$("#verification_shenpi_reason").html(comm.removeNull(realNameAuth.apprContent));
				//点击进入重新实名认证
				$("#again_shiming").click(function () {
					var certype = comm.removeNull(realNameAuth.certype);
					if(certype == 1){
						$("#ID_div").show();
					}else if(certype == 2){
						$("#passport_div").show();
					}else{
						$("#businessLicense_div").show();
					}
					$("#trueNameAut_submitBtn_"+certype).show();
					$("#register_step_two").hide();
					$("#trueNameAut_mod_div").hide();
				});
			}
			$("#trueNameAut_mod_div").show();
			$("#verification_checkcode_li").hide();
			$("#trueNameAut2_lastBtn").hide();
			$("#trueNameAut2_submitBtn").hide();
			this.showBtn("none");
			if(realNameAuth.certype == 1){//只有身份证才显示反面图片
				$("#trueNameAut2_oppositeImg").show();
				$("#lrcfmyBlogImage_span").html("身份证正面<span class='red ml5'>*</span>");
				$("#lrcbmyBlogImage_span").html("身份证反面<span class='red ml5'>*</span>");
				$("#lrccmyBlogImage_span").html("手持身份证大头照<span class='red ml5'>*</span>");
			}else if(realNameAuth.certype == 2){  //护照
				$("#trueNameAut2_oppositeImg").hide();
				$("#lrcfmyBlogImage_span").html("护照正面<span class='red ml5'>*</span>");
				$("#lrccmyBlogImage_span").html("手持护照大头照<span class='red ml5'>*</span>");
			}else if(realNameAuth.certype == 3){  //营业执照
				$("#trueNameAut2_oppositeImg").hide();
				$("#lrcfmyBlogImage_span").html("营业执照正面<span class='red ml5'>*</span>");
				$("#lrccmyBlogImage_span").html("手持营业执照大头照<span class='red ml5'>*</span>");
			}
			if(realNameAuth.cerpica){//证件正面照
				$("#lrcFacePath").val(realNameAuth.cerpica);
				comm.initTmpPicPreview("#lrcfuploadImage", comm.getFsServerUrl(realNameAuth.cerpica));
			}
			if(realNameAuth.cerpicb){//证件背面照
				$("#lrcBackPath").val(realNameAuth.cerpicb);
				comm.initTmpPicPreview("#lrcbuploadImage", comm.getFsServerUrl(realNameAuth.cerpicb));
			}
			if(realNameAuth.cerpich){//手持证件照
				$("#lrcCackPath").val(realNameAuth.cerpich);
				comm.initTmpPicPreview("#lrccuploadImage", comm.getFsServerUrl(realNameAuth.cerpich));
			}
		},
		/**
		 * 显示与隐藏按钮
		 * @param display 审批状态
		 */
		showBtn : function(display){
			$("#verification_demo_href_1").css("display", display);
			$("#verification_demo_href_2").css("display", display);
			$("#verification_demo_href_3").css("display", display);
			$("#verification_sendfile_href_1").css("display", display);
			$("#verification_sendfile_href_2").css("display", display);
			$("#verification_sendfile_href_3").css("display", display);
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#lrcfmyBlogImage', '#lrcbmyBlogImage','#lrccmyBlogImage'];
			var imgIds = ['#lrcfuploadImage_i', '#lrcbuploadImage_i','#lrccuploadImage_i'];
//			$("body").on("change", "", function(){
//				for(var k in imgIds){
//					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
//				}
//			});
			comm.initUploadBtn(btnIds, imgIds, 107, 64, "");
		},
		/**
		 * 校验身份证类型
		 */
		ID_validateData : function(){
			return $("#trueNameAut_ID_form").validate({
				rules : {
					trueNameAut_sex_1 : {
						required : true
					},
					trueNameAut_birthAddress_1 : {
						required : true,
						rangelength:[2, 128]
					},
					trueNameAut_licenceIssuing_1 : {
						rangelength:[2, 20],
						required : true
					},
					trueNameAut_creExpiryDate_1 : {
						required : true
					},
					trueNameAut_profession_1 : {
						required : true,
						rangelength:[2, 20],
						job : true
					},
					trueNameAut_postscript_1 : {
						rangelength:[0, 300],
					}
				},
				messages : {
					trueNameAut_sex_1 : {
						required : comm.lang("myHsCard").realNameAuth.requiredSex
					},
					trueNameAut_birthAddress_1 : {
						required : comm.lang("myHsCard").realNameAuth.requiredAddress,
						rangelength:comm.lang("myHsCard")[30189]
					},
					trueNameAut_licenceIssuing_1 : {
						rangelength:comm.lang("myHsCard").realNameAuth.licenceIssuing,
						required : comm.lang("myHsCard").realNameAuth.requiredLicence
					},
					trueNameAut_creExpiryDate_1 : {
						required : comm.lang("myHsCard").realNameAuth.requiredExpiryDate
					},
					trueNameAut_profession_1 : {
						required : comm.lang("myHsCard").realNameAuth.requiredJob,
						rangelength:comm.lang("myHsCard").realNameAuth.jobRangLength,
						job : comm.lang("myHsCard").realNameAuth.job
					},
					trueNameAut_postscript_1 : {
						rangelength:comm.lang("myHsCard")[30179]
					}
				},
				errorPlacement : function (error, element) {
					//性别除外，目的（是性别的错误提示信息与户籍地址、发证机关的错误提示信息对齐
					if (!$(element).is(":text") && $(element).attr("id") != "trueNameAut_sex_1") {
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
			return $("#trueNameAut_passport_form").validate({
				rules : {
					trueNameAut_sex_2 : {
						required : true
					},
					PlaceOfBirth_2 : {
						required : true,
						rangelength:[2, 128]
					},
					trueNameAut_creExpiryDate_2 : {
						required : true,
						rangelength:[2, 20]
					},
					issuingAuthority_2 : {
						required : true,
						rangelength:[2, 20]
					},
					PlaceOfIssue_2 : {
						required : true,
						rangelength:[2, 40]
					},
					trueNameAut_postscript_2 : {
						rangelength:[0, 300],
					}
				},
				messages : {
					trueNameAut_sex_2 : {
						required : comm.lang("myHsCard").realNameAuth.requiredSex
					},
					PlaceOfBirth_2 : {
						required : comm.lang("myHsCard").realNameAuth.requiredPlaceOfBirth,
						rangelength:comm.lang("myHsCard")[30193]
					},
					trueNameAut_creExpiryDate_2 : {
						required : comm.lang("myHsCard").realNameAuth.requiredExpiryDate,
						rangelength:comm.lang("myHsCard")[30192]
					},
					issuingAuthority_2 : {
						required : comm.lang("myHsCard").realNameAuth.requiredIssuingAuthority,
						rangelength:comm.lang("myHsCard")[30195]
					},
					PlaceOfIssue_2 : {
						required : comm.lang("myHsCard").realNameAuth.requiredPlaceOfIssue,
						rangelength:comm.lang("myHsCard")[30194]
					},
					trueNameAut_postscript_2 : {
						rangelength:comm.lang("myHsCard")[30179]
					}
				},
				errorPlacement : function (error, element) {
					//性别除外，目的（是性别的错误提示信息与户籍地址、发证机关的错误提示信息对齐
					if (!$(element).is(":text")  && $(element).attr("id") != "trueNameAut_sex_2") {
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
			return $("#trueNameAut_businessLicense_form").validate({
				rules : {
					companyType_3 : {
						required : true,
						rangelength:[2, 20],
					},
					datOfEstablishment_3 : {
						required : true,
						date : true
					},
					trueNameAut_postscript_3 : {
						rangelength:[0, 300],
					}
				},
				messages : {
					companyType_3 : {
						required : comm.lang("myHsCard")[30170],
						rangelength:comm.lang("myHsCard")[30196]
					},
					datOfEstablishment_3 : {
						required : comm.lang("myHsCard")[30182],
						date : comm.lang("myHsCard").realNameAuth.dateFormError
					},
					trueNameAut_postscript_3 : {
						rangelength:comm.lang("myHsCard")[30179]
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
			var validate = $("#trueNameAut_form").validate({
				rules : {
					lrcfuploadImage : {
						required : true
					},
					lrcbuploadImage : {
						required : function () {
							return $("#trueNameAut2_oppositeImg").is(":visible");
						}
					},
					lrccuploadImage : {
						required : true
					},
					checkcode : {
						required : true,
						rangelength : [4, 4]
					}
				},
				messages : {
					lrcFacePath : {
						required : comm.lang("myHsCard").realNameAuth.requiredTop
					},
					lrcBackPath : {
						required : comm.lang("myHsCard").realNameAuth.requiredBack
					},
					lrcCackPath : {
						required : comm.lang("myHsCard").realNameAuth.requiredCack
					},
					checkcode : {
						required : comm.lang("myHsCard").realNameAuth.requiredCode,
						rangelength : comm.lang("myHsCard").realNameAuth.rangelength
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
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
			/**
			 * 如果是修改则无需对上传文件进行必填校验
			 */
			if($("#lrcFacePath").val() != ""){
				validate.settings.rules.lrcfuploadImage = {required : false};
			}
			if($("#lrcBackPath").val() != ""){
				validate.settings.rules.lrcbuploadImage = {required : false};
			}
			if($("#lrcCackPath").val() != ""){
				validate.settings.rules.lrccuploadImage = {required : false};
			}
			return validate;
		}
	};
	return realNameAuth
});