define(['text!companyInfoTpl/smrz/smrz_zt.html',
		'text!companyInfoTpl/smrz/smrz.html',
		"companyInfoDat/companyInfo"], function(shxxTpl, smrzTpl,companyInfo){
	return {
		// 审核信息
		shxxTpl : shxxTpl,
		// 实名认证-企业信息
		smrzTpl : smrzTpl,
		// comm : comm,
		showPage : function(){
			var self = this;
			
			var sysInfo = companyInfo.getSystemInfo();
			companyInfo.findRealNameAuthApply({"entCustId":sysInfo.entCustId},function(resData){
				// $('#busibox').html(_.template(gsdjxxTpl,
				// response.data)).append(_.template(gsdjxx_xgTpl,
				// response.data));
				self.gotoPage(resData.data);
				
			});
			
		},
		// 根据状态跳转
		gotoPage : function (data){
			var self = this;
			//第一次实名认证
			if(data==null||data==undefined){
				self.displayImportantInfo();
				return;
			}
			
			var zt = data.status;
			if(zt == "2") {
				// 实名认证已经通过
				var rp=comm.lang("companyInfo").universal.kindly_reminder,
					rp1=comm.lang("companyInfo").smrz.pass_prompt;
				
				$("#contentWidth_3").html('<div class="bc mt40"><div class="page_tips bc"><h1 class="f16 fb tc">'+rp+'</h1><p class="f14 fb tc">'+rp1+'</p></div></div>');
				return;
			}
			if(zt == "1"||zt == "0") {
				
				// 初审通过，等待复审
				$("#contentWidth_3").html(_.template(shxxTpl,data));
				var ids=['#lrcFacePic','#lrcBackPic','#licensePic','#orgPic','#taxPic'];
				self.viewUploadedPic(data,ids);
				if(zt=="0"){
					$("#approve_status").html(comm.lang("companyInfo")['applyStatus'][zt]);
				}
				if(zt=="1"){
					$("#approve_status").html(comm.lang("companyInfo")['applyStatus'][zt]);
				}
				
				$("#btn_cxsmrz").css("display","none");
				return;
			}
			
			if(zt == "3") {
				// 实名认证审核信息-驳回
				$("#contentWidth_3").html(_.template(shxxTpl,data));
				
				$("#smrz_zt_tpl").removeClass('none');
				var ids=['#lrcFacePic','#lrcBackPic','#licensePic','#orgPic','#taxPic'];
				self.viewUploadedPic(data,ids);
				$("#approve_status").html(comm.lang("companyInfo")["applyStatus"][data.status]);
				
				// 重新实名认证按钮单击事件
				$("#btn_cxsmrz").removeClass('none').click(function(){
					// 从第二步开始
					self.initEntInfoPage(data);
				});
				
				return;
			}
			
		},
		
		//企业信息页面
		initEntInfoPage : function(resData){
			var self = this;
			
			$('#contentWidth_3').html(_.template(smrzTpl,resData));
			
			$("#lab_credence_type").text(comm.lang("companyInfo")["credenceTypes"][resData.entCustType]);
			
			self.initUpload_btn();
			// 下一步按钮
			$('#btn_smrz_xyb').click(function(){
				$("#smrz_qyxx").addClass('none');
				$("#smrz_qyxx2").removeClass('none');
				
				var ids=['#legalRepresentative_front_img','#legalRepresentative_back_img','#busLicenceNo_img','#organizationNo_img','#taxplayerNo_img'];
				
				self.viewUploadedPic(resData,ids);
			
				self.loadSamplePic();
				
				// 加载验证码
				self.loadVerifiedCode();
				
			});
			// 上一步按钮
			$('#btn_smrz_syb').click(function(){
				$("#smrz_qyxx").removeClass('none');
				$("#smrz_qyxx2").addClass('none');
			});
			// 提交按钮
			$('#btn_smrz_tj').click(function(){
				
				if (!self.validateData()) {
					return;
				}
				
				self.submit(resData);
			});
			// 换一张
			$("#img_smrz_yzm, #img_smrz_hyz").click(function(){
				self.loadVerifiedCode();
			});
		},
		//显示重要信息
		displayImportantInfo : function(){
			
			var self = this;
			var sysInfo = companyInfo.getSystemInfo();
			companyInfo.findRealNameAuthInfo({"entCustId":sysInfo.entCustId},function(response){
				var resData=response.data;
				self.initEntInfoPage(resData);
			});
		},
		
		//加载验证码
		loadVerifiedCode : function(){
			var v=companyInfo.loadVerifiedCode();
			$("#validateCode").html(v);
			$("#validateCode_hide").val(v);
			
		},
		
		//加载样例图片
		loadSamplePic :  function (){
			var self=this;
			companyInfo.findSamplePictureId({"entCustId":sysInfo.entCustId},function(response){
				//加载示例图
				var resData=response.data;
				
				$('#legalRepresentative_front_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.lrCredentialFrontFileId));
				$('#legalRepresentative_back_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.lrCredentialBackFileId));
				$('#busLicenceNo_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.busLicenceFileId));
				$('#organizationNo_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.organizationFileId));
				$('#taxplayerNo_img_case').attr("data-imgSrc", companyInfo.getFsServerUrl(resData.taxplayerFileId));
				
				self.viewCasePic();
			});
			
		},
		
		//初始化上传按钮
		initUpload_btn : function (){
	
			var btnId=["#lrCredentialFrontFileId","#lrCredentialBackFileId","#busLicenceFileId","#organizationFileId","#taxplayerFileId"];	
			var divId=["#legalRepresentative_front_img","#legalRepresentative_back_img", "#busLicenceNo_img","#organizationNo_img","#taxplayerNo_img"];	
			
			$("body").on("change","",function(){
				for(var k in divId){
					comm.initTmpPicPreview(divId[k], $(divId[k]).children().first().attr('src'));
				}
			});
			
			comm.initUploadBtn(btnId,divId);
		},
		
		
		//查看已上传的图片大图
		viewUploadedPic : function (resData,ids){
			var fileIds=[
							resData.lrcFacePic!=null?resData.lrcFacePic:resData.creFaceImg,
							resData.lrcBackPic!=null?resData.lrcBackPic:resData.creBackImg,
							resData.licensePic!=null?resData.licensePic:resData.busiLicenseImg,
							resData.orgPic!=null?resData.orgPic:resData.orgCodeImg,
							resData.taxPic!=null?resData.taxPic:resData.taxRegImg
			             ];
			
			$.each(ids,function(i,v){
				url=comm.getFsServerUrl(fileIds[i]);
				var html="<img src="+url+"/>";
				$(v).html(html);
				
				comm.bindPicViewer($(v+">img"),url);
			});
		},
		
		
		//点击查看样例图片大图
		viewCasePic : function (){
			//显示大图
			var ids=['#legalRepresentative_front_img_case','#legalRepresentative_back_img_case','#busLicenceNo_img_case','#organizationNo_img_case','#taxplayerNo_img_case'];
			var obj;
			$.each(ids,function(i,v){
				obj=$(v);
				comm.bindPicViewer(obj,obj.attr("data-imgsrc"));
			});
		},
		submit : function (resData){
			var self = this;
			if(!self.validateData()){
				return;
			}
			
			//上传成功后，需要提交文件返回的文件ID
			var success = function(data){
				//提交数据 
				$("#lrCredentialFrontFileId").value=data.lrCredentialFrontFileId;
				$("#lrCredentialBackFileId").value=data.lrCredentialBackFileId;
				$("#busLicenceFileId").value=data.busLicenceFileId;
				$("#organizationFileId").value=data.organizationFileId;
				$("#taxplayerFileId").value=data.taxplayerFileId;
				
				var add_data={
						"lrcFacePic" : data.lrCredentialFrontFileId,
						"lrcBackPic" : data.lrCredentialBackFileId,
						"licensePic" : data.busLicenceFileId,
						"orgPic" : data.organizationFileId,
						"taxPic" : data.taxplayerFileId,
						"certificatePic" : "00"
				};
				var loginInfo = companyInfo.getSystemInfo();
				var info={
					"userType" : loginInfo.custType,
					"entCustId" : loginInfo.entCustId,
					"entCustName"	: resData.entCustName!=null?resData.entCustName:resData.entName,
					"entCustNameEn"	: resData.entCustNameEn!=null?resData.entCustNameEn:resData.entNameEn,
					"legalName"	: resData.legalName!=null?resData.legalName:resData.creName,
					"legalCreNo"	: resData.legalCreNo!=null?resData.legalCreNo:resData.creNo,
					"legalCreType" : resData.creType,
					"licenseNo"	: resData.licenseNo!=null?resData.licenseNo:resData.busiLicenseNo,
					"orgNo"	: resData.orgNo!=null?resData.orgNo:resData.orgCodeNo,
					"taxNo"	: resData.taxNo,
				    "entResNo" : resData.entResNo,
				    "custType" : loginInfo.entCustType,
				    "countryNo": "",
				    "provinceNo":"",
				    "cityNo":"",
				    "entAddr":resData.orgNo!=null?resData.entAddr:resData.entRegAddr,
				    "linkman":resData.orgNo!=null?resData.linkman:resData.contactPerson,
				    "mobile":resData.orgNo!=null?resData.mobile:resData.contactPhone,
				    "optCustId" : loginInfo.custId,
				    "optName" : loginInfo.custName,
				    "optEntName" : loginInfo.entCustName
		    	
				}
				
				add_data=$.extend(info,add_data)
				
				companyInfo.submitRealNameAuthData(add_data,function(){
					comm.alert(comm.lang("companyInfo").smrz.auth_complete);
					$('#smrzxx').trigger('click');
				},function(){
					self.initUpload_btn();
				});
			};
			
			//失败后要重新初始化上传按键 ，否则不能重要选择文件
			var error = function(resData){
				self.initUpload_btn();
			};
			
			//上传
			var ids =['lrCredentialFrontFileId','lrCredentialBackFileId','busLicenceFileId',
				      'organizationFileId','taxplayerFileId'];
			comm.uploadFile(comm.getUploadFilePath(),ids,success,error);
			
			//重要加载验证码
			self.loadVerifiedCode();
		},
		
		validateData : function(){
			
			if(""==$('#validateCode_hide').val()||$('#validateCode_hide').val()!=$("#input_verificationCode").val()) {
				comm.alert(comm.lang("companyInfo").universal.input_verified_code);
				
				return false;
			}

			
			return comm.valid({
				formID : '#smrz_qyxx_form_file',
				top : 25,
				rules : {
					lrCredentialFrontFileId : {
						required : true
					},
					lrCredentialBackFileId : {
						required : true
					},
					busLicenceFileId : {
						required : true
					},
					organizationFileId : {
						required : true
					},
					taxplayerFileId : {
						required : true
					}
				},
				
				messages : {
					lrCredentialFrontFileId : {
						
						required : comm.lang("companyInfo").smrz.lrCredence_front_required
					},
					lrCredentialBackFileId : {
						required : comm.lang("companyInfo").smrz.lrCredence_back_required
					},
					busLicenceFileId : {
						required : comm.lang("companyInfo").smrz.busLicence_required
					},
					organizationFileId : {
						required : comm.lang("companyInfo").smrz.organization_required
					},
					taxplayerFileId : {
						required : comm.lang("companyInfo").smrz.taxplayer_required
					}
				}
			});
		}
	};
});