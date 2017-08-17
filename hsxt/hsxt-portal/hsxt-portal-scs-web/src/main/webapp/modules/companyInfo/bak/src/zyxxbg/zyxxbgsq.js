define(['text!companyInfoTpl/zyxxbg/zyxxbgsq.html',
        'companyInfoDat/companyInfo'
        ], function(tpl,companyInfo){
	return {
		showPage : function(){
			var self = this;
			
			var sysInfo=companyInfo.getSystemInfo();
			
			//判断是否实名认证 
			if("3"!=sysInfo.isRealnameAuth){
				
				var rp=comm.lang("companyInfo").universal.kindly_reminder,
				rp1=comm.lang("companyInfo").zyxxbg.promt_change_condition;
				$("#busibox").html('<div class="bc mt40"><div class="page_tips bc"><h1 class="f16 fb tc">'+rp+'</h1><p class="f14 fb tc">'+rp1+'</p></div></div>');
				
				$("#zyxxbgjlcx").attr("class","tabNone");
				return;
			}
			
			companyInfo.findChangeApplyOrder({"entCustId" : sysInfo.entCustId},function(response){
				
				var resData=response.data;
				if(resData==null){
					self.gotoPage();
					return;
				}
				var status=resData.status;
				if(status=='1'||status=='0'){
					var rp=comm.lang("companyInfo").universal.kindly_reminder;
					var context=comm.lang("companyInfo").zyxxbg.promt_approving;
					
					$("#contentWidth_3").html('<div class="bc mt40"><div class="page_tips bc"><h1 class="f16 fb tc">'+rp+'</h1><p class="f14 fb tc">'+context+'</p></div></div>');
				}else if(status=='3'){
					self.intEntInfoPage(resData,2);
				}
			});
		
		},
		gotoPage : function(){
			var self = this;
			var sysInfo = companyInfo.getSystemInfo();
			companyInfo.findImportantInfo({"entCustId":sysInfo.entCustId},function(response){
				var resData=response.data;
				
				self.intEntInfoPage(resData,1);
				
			});
		},
		//flag 1、重要信息 2、变更信息
		intEntInfoPage : function(resData,flag){
			var self = this;
			$('#contentWidth_3').html(_.template(tpl,resData));
			
			self.loadCredenceTypes(resData);
			
			//获得在的身份证是code，需要转成名称显示 
			self.nextStep(resData,flag);
		},
		
		//下一步按钮
		nextStep : function(resData,flag){
			var self=this;
			$('#btn_zyxxbgsq_xyb').click(function(){
				
				if(!self.validChangeData()){
					return;
				}
				
				
				self.loadVerifiedCode();
				
				self.changeVerifiedCode();
				
				self.prveStep();
				
				self.triggerSubmit(resData);
				
				self.loadSamplePic();
				
				self.viewCasePic();
				
				self.initUpload_btn();
				
				self.loadPicture(resData,flag);
				//显示头部菜单
				comm.liActive_add($('#zyxxbg_tpwjsc'));
				$("#zyxx_tpl").addClass('none');
				$("#tpsc_tpl").removeClass('none');
			});
			
		},
		
	
		
		//上一步按钮
		prveStep : function (){
			$('#btn_zyxxbgsq_syb').click(function(){
				//显示头部菜单
				comm.liActive_add($('#zyxxbg_bgsq'));
				$("#tpsc_tpl").addClass('none');
				$("#zyxx_tpl").removeClass('none');
			});
		},
		
		
		loadVerifiedCode : function(){
				var validateCode=companyInfo.loadVerifiedCode();
			// 加载验证码
				$("#validateCode").html(validateCode);
				$("#validateCode_hide").val(validateCode);
		},
		
		initUpload_btn : function(){
		
			var objIds=["#legalRepCreFacePicNew","#legalRepCreBackPicNew","#bizLicenseCrePicNew","#organizerCrePicNew","#taxpayerCrePicNew","#linkAuthorizePicNew","#applyFileId"];
			var divIds=["#lrCredentialFrontFile_img","#lrCredentialBackFile_img","#busLicenceFile_img","#organizationFile_img","#taxplayerFile_img","#powerOfAttorneyFile_img","#applyFile_img"];
			
			
			$("body").on("change","",function(){
				for(var k in divIds){
					comm.initTmpPicPreview(divIds[k], $(divIds[k]).children().first().attr('src'));
				}
			});
			
			comm.initUploadBtn(objIds,divIds);
			//清空原来的图片
			$.each(divIds,function(i,v){
				$(v).empty();
			});
		
		},
		
		loadPicture : function (resData , flag){
			//加载图片 flag:1、UC获得的重要信息 2、BS获得的变更信息
			if(flag==1){
				$("#lrCredentialFrontFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.creFaceImg)+"' />");
				$("#lrCredentialBackFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.creBackImg)+"' />");
				$("#busLicenceFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.busiLicenseImg)+"' />");
				$("#organizationFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.orgCodeImg)+"' />");
				$("#taxplayerFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.taxRegImg)+"' />");
				$("#powerOfAttorneyFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.authProxyFile)+"' />");
			}else {
				$("#lrCredentialFrontFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.legalRepCreFacePicNew!=null?resData.legalRepCreFacePicNew:resData.legalRepCreFacePicOld)+"' />");
				$("#lrCredentialBackFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.legalRepCreBackPicNew!=null?resData.legalRepCreBackPicNew:resData.legalRepCreBackPicOld)+"' />");
				$("#busLicenceFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.bizLicenseCrePicNew!=null?resData.bizLicenseCrePicNew:resData.bizLicenseCrePicOld)+"' />");
				$("#organizationFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.organizerCrePicNew!=null?resData.organizerCrePicNew:resData.organizerCrePicOld)+"' />");
				$("#taxplayerFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.taxpayerCrePicNew!=null?resData.taxpayerCrePicNew:resData.taxpayerCrePicOld)+"' />");
				$("#powerOfAttorneyFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.linkAuthorizePicNew!=null?resData.linkAuthorizePicNew:resData.linkAuthorizePicOld)+"' />");
				$("#applyFile_img").html("<img src='"+companyInfo.getFsServerUrl(resData.setImptappPic)+"' />");
			}
		},
		
		loadSamplePic :  function (){
			var sysInfo = companyInfo.getSystemInfo();
			companyInfo.findSamplePictureId({"entCustId":sysInfo.entCustId},function(response){
				var resData=response;
				//加载示例图
				$('#legalRepresentative_front_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.legalRepCreFacePicNew));
				$('#legalRepresentative_back_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.legalRepCreBackPicNew));
				$('#busLicenceNo_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.bizLicenseCrePicNew));
				$('#organizationNo_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.organizerCrePicNew));
				$('#taxplayerNo_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.taxpayerCrePicNew));
				$('#powerOfAttorneyFile_img_case').attr("href", companyInfo.getFsServerUrl(resData.linkAuthorizePicNew));
				$('#applyFile_img_case').attr("href", companyInfo.getFsServerUrl(resData.applyFileId));
				
			});
		},
		
		triggerSubmit : function (resData){
			var self = this;
			//提交按钮
			$('#btn_zyxxbgsq_tj').click(function(){
			var buttons1={},buttons2={};
			    buttons1[comm.lang("companyInfo").universal.confirm] =function(){
			    	self.submit(resData);
				};
				
				buttons2[comm.lang("companyInfo").universal.clear] =function(){
					$(this).dialog("destroy");
				};
			  var buttons={buttons1,buttons2};
				
				var cp =comm.lang("companyInfo").universal.confirm_submit ;
			  $( "#confirm_dialog" ).dialog({ //对话框方法调用
					title:cp,
					modal:true,
					width:"500",
					height:'200',
					closeIcon:true,//显示或隐蔽右上角关闭按钮(建议),
					buttons:buttons,
			        //显示或隐藏右上角的关闭按钮(同上，不建议)
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).hide();
					}
			
			  });
				
			
				
			});
		},
		
		submit : function (resData){
			var self = this;
			
			if (!self.validateData()) {
				return;
			}
			
			var ids =['legalRepCreFacePicNew','legalRepCreBackPicNew','bizLicenseCrePicNew',
				      'organizerCrePicNew','taxpayerCrePicNew','linkAuthorizePicNew','applyFileId'];
			
			var success = function(data){
				//提交数据 
				$("#legalRepCreFacePicNew").value=data.legalRepCreFacePicNew;
				$("#legalRepCreBackPicNew").value=data.legalRepCreBackPicNew;
				$("#bizLicenseCrePicNew").value=data.bizLicenseCrePicNew;
				$("#organizerCrePicNew").value=data.organizerCrePicNew;
				$("#taxpayerCrePicNew").value=data.taxpayerCrePicNew;
				$("#linkAuthorizePicNew").value=data.linkAuthorizePicNew;
				$("#applyFileId").value=data.applyFileId;
				
				var picData={
						"legalRepCreFacePicNew" : data.legalRepCreFacePicNew,
						"legalRepCreBackPicNew" : data.legalRepCreBackPicNew,
						"bizLicenseCrePicNew" : data.bizLicenseCrePicNew,
						"organizerCrePicNew" : data.organizerCrePicNew,
						"taxpayerCrePicNew" : data.taxpayerCrePicNew,
						"linkAuthorizePicNew" : data.linkAuthorizePicNew,
						"imptappPic" : data.applyFileId
				}
				
				var add_data=$('#zyxxbgsq_xx2_form_c').serializeJson();
				
				var data=self.getOtherData(resData);
				add_data=$.extend(add_data,data);
				add_data=$.extend(add_data,picData);
				
				companyInfo.submitChangeApply1(add_data,function(response){
						comm.alert(comm.lang("companyInfo").universal.approve_wait_promt);
						$('#zyxxbg_bgsq').trigger('click');
				},function(response){
					comm.errorInfoPop(response.retCode);
					self.initUpload_btn();
				});
			};
			
			//失败后要重新初始化上传按键 ，否则不能重要选择文件
			var error = function(resData){
				self.initUpload_btn();
			};
			
			//上传
			comm.uploadFile(comm.getUploadFilePath(),ids,success,error);
			
			//重要加载验证码
			self.loadVerifiedCode();
			
		},
		
		getOtherData : function (resData) {
			var loginInfo=companyInfo.getSystemInfo();
			var add_data;
			var entBaseInfo = companyInfo.findSysRegisterInfo1(null,function(response){
				var entBaseInfo= response.data;
				 add_data={
					entResNo : resData.entResNo,
					entCustId : loginInfo.entCustId,
					optCustId : loginInfo.custId,
					optName : loginInfo.custName,
					optEntName : loginInfo.entCustName,
					entCustName : resData.entCustName!=null?resData.entCustName:resData.entName,
					custType : resData.entCustType,
					linkman : resData.linkman!=null? resData.linkman: resData.contactPerson,
					mobile : resData.mobile!=null? resData.mobile: resData.contactPhone,
					countryNo : resData.countryNo!=null? resData.countryNo: entBaseInfo.countryCode,
					provinceNo :  resData.provinceNo!=null? resData.provinceNo: entBaseInfo.provinceCode,
					cityNo : resData.cityNo!=null? resData.cityNo: entBaseInfo.cityCode,
					custNameOld :  resData.custNameOld!=null? resData.custNameOld: resData.entName,
					custNameEnOld : resData.custNameEnOld!=null? resData.custNameEnOld: resData.entNameEn,
					custAddressOld :  resData.custAddressOld!=null? resData.custAddressOld: resData.entRegAddr,
					linkmanOld : resData.linkmanOld!=null? resData.linkmanOld: resData.contactPerson,
					linkmanMobileOld : resData.linkmanMobileOld!=null? resData.linkmanMobileOld: resData.contactPhone,
					legalRepOld :  resData.legalRepOld!=null? resData.legalRepOld: resData.creName,
					legalRepCreTypeOld : resData.legalRepCreTypeOld!=null? resData.legalRepCreTypeOld: resData.creType,
					legalRepCreNoOld : resData.legalRepCreNoOld!=null? resData.legalRepCreNoOld: resData.creNo,
					bizLicenseNoOld : resData.bizLicenseNoOld!=null? resData.bizLicenseNoOld: resData.busiLicenseNo,
					organizerNoOld : resData.organizerNoOld!=null? resData.organizerNoOld: resData.orgCodeNo,
					taxpayerNoOld : resData.taxpayerNoOld!=null? resData.taxpayerNoOld: resData.taxNo,
					legalRepCreFacePicOld : resData.legalRepCreFacePicOld!=null? resData.legalRepCreFacePicOld: resData.creFaceImg,
					legalRepCreBackPicOld : resData.legalRepCreBackPicOld!=null? resData.legalRepCreBackPicOld: resData.creBackImg,
					bizLicenseCrePicOld : resData.bizLicenseCrePicOld!=null? resData.bizLicenseCrePicOld: resData.busiLicenseImg,
					organizerCrePicOld : resData.organizerCrePicOld!=null? resData.organizerCrePicOld: resData.orgCodeImg,
					taxpayerCrePicOld :  resData.taxpayerCrePicOld!=null? resData.taxpayerCrePicOld: resData.taxRegImg,
					linkAuthorizePicOld :  resData.linkAuthorizePicOld!=null? resData.linkAuthorizePicOld: resData.authProxyFile,
					legalRepCreTypeNew : $("#credentialType option:selected").val()
				}
			
			});
			return add_data;
		},
		
		
		viewCasePic : function (){
			var ids = ['#legalRepresentative_front_img_case','#legalRepresentative_back_img_case','#busLicenceNo_img_case','#organizationNo_img_case','#taxplayerNo_img_case','#powerOfAttorneyFile_img_case','#applyFile_img_case'] ; 
			var obj;
			$.each(ids,function(i,v){
				obj=$(v);
				comm.bindPicViewer(obj,obj.text());
			});
		},
				
		
		changeVerifiedCode : function (){
			var self=this;
			//换一张
			$("#img_zyxxbgsq_yzm, #img_zyxxbgsq_hyz").click(function(){
				self.loadVerifiedCode();
			});
		},
		
		loadCredenceTypes :  function(data){
			
			comm.initSelect1('#credentialType',comm.lang("companyInfo")['credenceTypes'],data.creType);
			
			$("#credentialType_old").html(comm.lang("companyInfo")['credenceTypes'][data.creType]);
		
		},
		disabledUpload_btn : function (){
			var companyNameCn_old=$("#companyNameCn_old").html();
			var companyNameEn_old=$("#companyNameEn_old").html();
			var address_old=$("#address_old").html();
			var legalRepresentative_old=$("#legalRepresentative_old").html();
			var credentialType_old=$("#credentialType_old").html();
			var credentialNo_old=$("#credentialNo_old").html();
			var busLicenceNo_old=$("#busLicenceNo_old").html();
			var organizationNo_old=$("#organizationNo_old").html();
			var taxplayerNo_old=$("#taxplayerNo_old").html();
			var constactName_old=$("#constactName_old").html();
			var constactPhoneNo_old=$("#constactPhoneNo_old").html();
			
			var companyNameCn=$("#companyNameCn").val().trim();
			var companyNameEn=$("#companyNameEn").val().trim();
			var address=$("#address").val().trim();
			var legalRepresentative=$("#legalRepresentative").val().trim();
			var credentialType=$("#credentialType").find("option:selected").text().trim();
			var credentialNo=$("#credentialNo").val().trim();
			var busLicenceNo=$("#bizLicenseNoNew").val().trim();
			var organizationNo=$("#organizationNo").val().trim();
			var taxplayerNo=$("#taxplayerNo").val().trim();
			var constactName=$("#linkmanNew").val().trim();
			var constactPhoneNo=$("#linkmanMobileNew").val().trim();

			
			$("#legalRepCreFacePicNew,#legalRepCreBackPicNew,#bizLicenseCrePicNew,#organizerCrePicNew,#taxpayerCrePicNew,#linkAuthorizePicNew,#applyFileId").click(function(e){
				//如果没有变更的信息，不要上传文件，需要 打误选的文件清除
				if ( constactName_old == constactName
						&& constactPhoneNo_old == constactPhoneNo) {
					  $("#linkAuthorizePicNew").val("");
				}
				
				//如果没有变更的信息，不要上传文件，需要 打误选的文件清除
				if (companyNameCn_old == companyNameCn && address_old == address
						&& legalRepresentative_old == legalRepresentative
						&& credentialType_old == credentialType
						&& credentialNo_old == credentialNo
						&& busLicenceNo_old == busLicenceNo
						&& organizationNo_old == organizationNo) {
					
					$("#legalRepCreFacePicNew").val("");
					$("#legalRepCreBackPicNew").val("");
					$("#bizLicenseCrePicNew").val("");
					$("#organizerCrePicNew").val("");
					$("#taxpayerCrePicNew").val("");
				}
				
			});
			
		},
		
		validChangeData : function(){
			
			return comm.valid({
				formID : '#zyxxbgsq_xx2_form_c',
				top : 5,
				left:200,
				rules : {
					bizLicenseNoNew : {
						required : true,
						rangelength : [11,19]
					},
					organizerNoNew : {
						required : true,
						rangelength : [11,19]
					},
					taxpayerNoNew : {
						required : true,
						rangelength : [11,19]
					},
					linkmanMobileNew : {
						required : true,
						mobileNo : true
					},
					linkmanNew: {
						required : true,
						custname : true
					}
				
				},
				
				messages : {
					
					bizLicenseNoNew : {
						required : comm.lang("companyInfo")["zyxxbg"]["not_empty_busLisence_no"],
						rangelength : comm.lang("companyInfo")["zyxxbg"]["not_empty_busLisence_no"]
					},
					organizerNoNew : {
						required : comm.lang("companyInfo")["zyxxbg"]["not_empty_organization_no"],
						rangelength : comm.lang("companyInfo")["zyxxbg"]["not_empty_organization_no"]
					},
					taxpayerNoNew : {
						required : comm.lang("companyInfo")["zyxxbg"]["not_empty_taxpalyer_no"],
						rangelength : comm.lang("companyInfo")["zyxxbg"]["not_empty_taxpalyer_no"]
					},
					linkmanMobileNew : {
						required : comm.lang("companyInfo")["zyxxbg"]["not_empty_contact_p_tel"],
						mobileNo : comm.lang("companyInfo")["zyxxbg"]["not_empty_contact_p_tel"]
					},
					linkmanNew: {
						required : comm.lang("companyInfo")["zyxxbg"]["not_empty_contact_p_name"],
						custname : comm.lang("companyInfo")["zyxxbg"]["not_empty_contact_p_name"]
					}
				}
				
			});
		},
		
		validateData : function(){
			var companyNameCn_old=$("#companyNameCn_old").html();
			var companyNameEn_old=$("#companyNameEn_old").html();
			var address_old=$("#address_old").html();
			var legalRepresentative_old=$("#legalRepresentative_old").html();
			var credentialType_old=$("#credentialType_old").html();
			var credentialNo_old=$("#credentialNo_old").html();
			var busLicenceNo_old=$("#busLicenceNo_old").html();
			var organizationNo_old=$("#organizationNo_old").html();
			var taxplayerNo_old=$("#taxplayerNo_old").html();
			var constactName_old=$("#constactName_old").html();
			var constactPhoneNo_old=$("#constactPhoneNo_old").html();
			
			var companyNameCn=$("#companyNameCn").val().trim();
			var companyNameEn=$("#companyNameEn").val().trim();
			var address=$("#address").val().trim();
			var legalRepresentative=$("#legalRepresentative").val().trim();
			var credentialType=$("#credentialType").find("option:selected").text().trim();
			var credentialNo=$("#credentialNo").val().trim();
			var busLicenceNo=$("#bizLicenseNoNew").val().trim();
			var organizationNo=$("#organizationNo").val().trim();
			var taxplayerNo=$("#taxplayerNo").val().trim();
			var constactName=$("#linkmanNew").val().trim();
			var constactPhoneNo=$("#linkmanMobileNew").val().trim();
			
			var legalRepCreFacePicNew=$("#legalRepCreFacePicNew").val();
			var legalRepCreBackPicNew=$("#legalRepCreBackPicNew").val();
			var bizLicenseCrePicNew=$("#bizLicenseCrePicNew").val();
			var organizerCrePicNew=$("#organizerCrePicNew").val();
			var taxpayerCrePicNew=$("#taxpayerCrePicNew").val();
			var linkAuthorizePicNew=$("#linkAuthorizePicNew").val();
			var applyFile=$("#applyFileId").val();
			
			if(companyNameCn.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_company_name);
				return false;
			}
			if(address.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_company_addr);
				return false;
			}
			if(legalRepresentative.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_legal_Person);
				return false;
			}
			if(credentialType.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_legal_credence_type);
				return false;
			}
			if(credentialNo.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_legal_credence_no);
				return false;
			}
			if(busLicenceNo.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_busLisence_no);
				return false;
			}
			if(organizationNo.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_organization_no);
				return false;
			}
			if(taxplayerNo.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_taxpalyer_no);
				return false;
			}
			if(constactName.trim()==""){comm.lang("companyInfo").zyxxbg.not_empty_contact_p_name
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_contact_p_name);
				return false;
			}
			if(constactPhoneNo.trim()==""){
				comm.alert(comm.lang("companyInfo").zyxxbg.not_empty_contact_p_tel);
				return false;
			}

			if (companyNameCn_old == companyNameCn && address_old == address
					&& legalRepresentative_old == legalRepresentative
					&& credentialType_old == credentialType
					&& credentialNo_old == credentialNo
					&& busLicenceNo_old == busLicenceNo
					&& organizationNo_old == organizationNo
					&& taxplayerNo_old == taxplayerNo
					&& constactName_old == constactName
					&& constactPhoneNo_old == constactPhoneNo) {
				comm.alert(comm.lang("companyInfo").universal.unchanged);
				return false;
			}
			
			
			/***判断是否有变更 ***/
			if(companyNameCn_old!=companyNameCn||companyNameEn_old!=companyNameEn
					||address_old!=address
					||credentialType_old!=credentialType
					||taxplayerNo_old!=taxplayerNo){
				return comm.valid({
					formID : '#zyxxbgsq_xx2_form_c',
					top : 25,
					rules : {
						legalRepCreFacePicNew : {
							required : true
						},
						legalRepCreBackPicNew : {
							required : true
						},
						bizLicenseCrePicNew : {
							required : true
						},
						organizerCrePicNew : {
							required : true
						},
						taxpayerCrePicNew : {
							required : true
						},
						applyFileId: {
							required : true
						}
					
					},
						
					messages : {
						legalRepCreFacePicNew : {
							required : comm.lang("companyInfo").smrz.lrCredence_front_required
						},
						legalRepCreBackPicNew : {
							required : comm.lang("companyInfo").smrz.lrCredence_back_required
						},
						bizLicenseCrePicNew : {
							required : comm.lang("companyInfo").smrz.busLicence_required
						},
						organizerCrePicNew : {
							required : comm.lang("companyInfo").smrz.organization_required
						},
						taxpayerCrePicNew : {
							required : comm.lang("companyInfo").smrz.taxplayer_required
						},
						applyFileId: {
							required : comm.lang("companyInfo").zyxxbg.change_apply_file_required
						}
					}
				});
			}
			
			if(constactName!=constactName_old||constactPhoneNo!=constactPhoneNo_old){
				return comm.valid({
					formID : '#zyxxbgsq_xx2_form_c',
					top : 25,
					rules : {
						linkAuthorizePicNew : {
							required : true
						},
						applyFileId: {
							required : true
						}
					},
					messages : {
						linkAuthorizePicNew : {
							required : comm.lang("companyInfo").zyxxbg.powerOfAttorney_file_required
						},
						applyFileId: {
							required : comm.lang("companyInfo").zyxxbg.change_apply_file_required
						}
					}
				});
			}
			
			return true;
		}
	};
});