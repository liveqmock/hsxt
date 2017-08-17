define(['text!companyInfoTpl/zyxxbg/zyxxbgsq.html',
        'companyInfoDat/zyxxbg/zyxxbg',
		'companyInfoLan'],function(zyxxbgsqTpl,dataModoule){
	var zyxxbgspPage = {
		entInfo : null,
		showPage : function(){
			zyxxbgspPage.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			entInfo = data.entExtendInfo?data.entExtendInfo:{};//企业信息
			$('#contentWidth_3').show();
			$('#contentWidth_3').html(_.template(zyxxbgsqTpl, entInfo));
	
			var changeInfo = data.changeInfo?data.changeInfo:{};//重要信息变更信息
			if(changeInfo.status == 0 || changeInfo.status == 1){//审批中
				$("#tips_0").show();
				return;
			}
			if(changeInfo.status == 2){ 		//审批通过
				$("#tips_3").show();
			}else if(changeInfo.status == 3 || changeInfo.status == 4){ //地区平台初审、复核驳回
				$("#tips_2").show();
				$("#reApplyDiv").show();
				$("#apprDate").html(changeInfo.updateDate);
				$("#apprRemarkMessage").html(changeInfo.apprRemark);
			}else{
				$("#base_info_div").show();
			}
			
			//加载样例图片
			cacheUtil.findDocList(function(map){
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
				comm.initDownload("#bussChangeAppId", map.busList, '1001');
				comm.initDownload("#certificateFileId", map.comList, '1007');
			});
			
			$('#changeVaildCode').unbind('click').bind('click',function(){
				$('#vaildCodeImg').attr('src',comm.generateSecuritCode('importInfoAuth'));
			});
			$('#changeVaildCode').trigger('click');
			//提交
			$('#btn_tj').click(function(){
				if(zyxxbgspPage.rualVailData()){
					return;
				}
				if(zyxxbgspPage.validateData().form()){
					zyxxbgspPage.saveFile();
				}
			});
			
			//重新申请
			$('#reApplyBtn').click(function(){
				$("#tips_2").hide();
				$("#reApplyDiv").hide();
				$("#base_info_div").show();
			});
			
			//重新申请
			$('#back_btn').click(function(){
				$("#tips_3").hide();
				$("#base_info_div").show();
			});
			
			//显示声明
			$('#zyxxbgsq_sm').mouseover(function(){
				$(this).next('div').removeClass('none');
			});
			
			//隐藏声明
			$('#zyxxbgsq_sm').mouseout(function(){
				$(this).next('div').addClass('none');
			});
			
			this.initTmpPicPreview();
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.initEntChange(null, function(res){
				zyxxbgspPage.initForm(res.data);
			});
		},
		/**
		 * 保存图片
		 */
		saveFile : function(){
			var params = {
				vaildCode:$('#vaildCode').val(),
				codeType:"importInfoAuth"
			};
			//验证验证码
			comm.verificationCode(params,comm.lang("companyInfo"),function(res){
					//验证码正确  弹出确认修改对话框
					comm.confirm({
						imgFlag:true,
						width:700,
						content:comm.lang("companyInfo").entChangeConfirm,
						ok:'确定',
						cancel:'取消',
						callOk : function(){
							
							var ids = ['busLicenceFileId','imptappPic','linkAuthorizePic'];
							//确认修改 则开始上传相关文件
							comm.uploadFile(null, ids, function(data){
								if(data.busLicenceFileId){
									$("#hide_busLicenceFileId").val(data.busLicenceFileId);
								}
								if(data.imptappPic){
									$("#hide_imptappPic").val(data.imptappPic);
								}
								if(data.linkAuthorizePic){
									$("#hide_linkAuthorizePic").val(data.linkAuthorizePic);
								}
								zyxxbgspPage.saveData();
								zyxxbgspPage.initTmpPicPreview();
							}, function(err){
								zyxxbgspPage.initTmpPicPreview();
							}, {delFileIds:null});
						},
						callCancel:function(){
							
						} 	 
					});
			});
		},
		//业务规则验证数据 by likui
		rualVailData :function (){
			var custNameNew = $.trim($("#entCustName").val());//变更后的企业名称
			var custAddressNew = $.trim($("#entRegAddr").val());//变更后的企业地址
			var linkmanNew = $.trim($("#contactPerson").val());//变更后的联系人姓名
			var linkmanMobileNew = $.trim($("#contactPhone").val());//变更后的联系人手机
			var legalRepNew = $.trim($("#legalPerson").val());//变更后的法人代表
			var bizLicenseNoNew = $.trim($("#busiLicenseNo").val());//变更后的营业执照号
			
			//判断有无信息修改
			if(comm.isEmpty(custNameNew)&&comm.isEmpty(custAddressNew)&&comm.isEmpty(linkmanNew)
					&&comm.isEmpty(linkmanMobileNew)&&comm.isEmpty(legalRepNew)&&comm.isEmpty(bizLicenseNoNew)){
				comm.error_alert(comm.lang("companyInfo").pleaseInputImportInfo,null,"top");
				return true;
			}
			//不单独提供申请营业执照号、组织机构代码证号、纳税人识别号的重要信息的变更业务，修改其中任一项时，提示用户必须修改其余两项；
			//	修改企业名称同时可选择修改这三个证件号码。
			if(comm.isEmpty(custNameNew)&&comm.isEmpty(custAddressNew)&&comm.isEmpty(legalRepNew)){
				if(comm.isNotEmpty(bizLicenseNoNew)){
					comm.warn_alert(comm.lang("companyInfo").notAloneChangeLicenseNo,"520");
					return true;
				}
			}
			return false;
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#busLicenceFileId','#imptappPic', '#linkAuthorizePic'];
			var imgIds = ['#thum-3', '#thum-7','#thum-6'];
			comm.initUploadBtn(btnIds, imgIds);
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			
			var params = {};
			params.entCustId = entInfo.entCustId;//企业客户号
			params.entResNo = entInfo.entResNo;//企业互生号
			params.entCustName = entInfo.entCustName;//企业名称
			params.linkman = entInfo.contactPerson;//企业联系人姓名
			params.mobile = entInfo.contactPhone;//企业联系人手机
			params.countryNo = entInfo.countryNo;//企业所在国家代码
			params.provinceNo = entInfo.provinceNo;//企业所在省代码
			params.cityNo = entInfo.cityNo;//企业所在城市代码
			params.imptappPic = $("#hide_imptappPic").val();//重要信息变更业务办理申请书扫描件
			params.applyReason = $("#applyReason").val();//申请变更原因
			params.custNameOld = entInfo.entCustName;//变更前的企业名称
			params.custAddressOld = entInfo.entRegAddr;//变更前的企业地址
			params.linkmanOld = entInfo.contactPerson;//变更前的联系人姓名
			params.linkmanMobileOld = entInfo.contactPhone;//变更前的联系人手机
			params.legalRepOld = entInfo.legalPerson;//变更前的法人代表
			params.bizLicenseNoOld = entInfo.busiLicenseNo;//变更前的营业执照号
			params.bizLicenseCrePicOld = entInfo.busiLicenseImg;//变更前的营业执照正本扫描件
			params.linkAuthorizePicOld = entInfo.contactProxy;//变更前的联系人授权委托书
			params.custNameNew = $("#entCustName").val();//变更后的企业名称
			params.custAddressNew = $("#entRegAddr").val();//变更后的企业地址
			params.linkmanNew = $("#contactPerson").val();//变更后的联系人姓名
			params.linkmanMobileNew = $("#contactPhone").val();//变更后的联系人手机
			params.legalRepNew = $("#legalPerson").val();//变更后的法人代表
			params.bizLicenseNoNew = $("#busiLicenseNo").val();//变更后的营业执照号
			params.bizLicenseCrePicNew = $("#hide_busLicenceFileId").val();//变更后的营业执照正本扫描件
			params.linkAuthorizePicNew = $("#hide_linkAuthorizePic").val();//变更后的联系人授权委托书
			
			dataModoule.createEntChange(params, function(res){
				comm.alert({content:comm.lang("companyInfo").zyxxSubmitSuccess, callOk:function(){
					$("#tips_0").show();
					$("#base_info_div").hide();
					$("#contentWidth_3").show();
				}});
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#zyxxbgsq_form").validate({
				rules : {
					entCustName : {
						rangelength : [2, 64],
						equalContent : "#oldEntCustName"
					},
					entRegAddr : {
						rangelength : [2, 128],
						equalContent : "#oldEntRegAddr"
					},
					legalPerson : {
						rangelength : [2, 20],
						equalContent : "#oldLegalPerson"
					},
					busiLicenseNo : {
						rangelength : [7, 20],
						businessLicence:true,
						equalContent : "#oldBusiLicenseNo"
					},
					contactPerson : {
						rangelength : [2, 20],
						equalContent : "#oldContactPerson"
					},
					contactPhone : {
						mobileNo : true,
						equalContent : "#oldContactPhone"
					},
					
					
					busLicenceFileId : {
						required : function(){
							return comm.isNotEmpty($("#entCustName").val())||comm.isNotEmpty($("#entRegAddr").val())||comm.isNotEmpty($("#legalPerson").val())||comm.isNotEmpty($("#busiLicenseNo").val());
						},
					},
					linkAuthorizePic : {
						required : function(){
							return comm.isNotEmpty($("#contactPerson").val())||comm.isNotEmpty($("#contactPhone").val());
						}
					},
					imptappPic : {
						required : true
					},
					applyReason : {
						required : true,
						rangelength : [1, 300]
					},
					vaildCode : {
						required : true
					}
				},
				messages : {
					entCustName : {
						rangelength : comm.lang("companyInfo")[32686],
						equalContent : comm.lang("companyInfo").changeValSame
					},
					entRegAddr : {
						rangelength : comm.lang("companyInfo")[32631],
						equalContent : comm.lang("companyInfo").changeValSame
					},
					legalPerson : {
						rangelength : comm.lang("companyInfo")[32632],
						equalContent : comm.lang("companyInfo").changeValSame
					},
					busiLicenseNo : {
						businessLicence:comm.lang("companyInfo")[32507],
						rangelength :comm.lang("companyInfo")[32693],
						equalContent : comm.lang("companyInfo").changeValSame
					},
					contactPerson : {
						rangelength : comm.lang("companyInfo")[32646],
						equalContent : comm.lang("companyInfo").changeValSame
					},
					contactPhone : {
						mobileNo : comm.lang("companyInfo")[32654],
						equalContent : comm.lang("companyInfo").changeValSame
					},
					
					
					busLicenceFileId : {
						required :  comm.lang("companyInfo")[32639]
					},
					linkAuthorizePic : {
						required : comm.lang("companyInfo")[32660]
					},
					imptappPic : {
						required : comm.lang("companyInfo")[32537]
					},
					applyReason : {
						required : comm.lang("companyInfo")[32539],
						rangelength : comm.lang("companyInfo")[32538]
					},
					vaildCode : {
						required : comm.lang("companyInfo")[32692]
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")||$(element).attr("id")=='applyReason') {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
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
						$(".ui-tooltip").css("max-width", "230px");
						
					}
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			 
			if(comm.isNotEmpty($("#hide_busLicenceFileId").val())){
				validate.settings.rules.busLicenceFileId = {required : false};
			}
			if(comm.isNotEmpty($("#hide_imptappPic").val())){
				validate.settings.rules.imptappPic = {required : false};
			}
			if(comm.isNotEmpty($("#hide_linkAuthorizePic").val())){
				validate.settings.rules.linkAuthorizePic = {required : false};
			}
			return validate;
		}
	};
	return zyxxbgspPage
}); 

 