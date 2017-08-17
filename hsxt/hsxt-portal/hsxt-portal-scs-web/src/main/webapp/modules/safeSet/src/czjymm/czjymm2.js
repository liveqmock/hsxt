define(['text!safeSetTpl/czjymm/czjymm2.html',"safeSetDat/safeSet","safeSetLan" ],function(czjymm2Tpl,safeSet){
	return {
		 
		showPage : function(){
			var self = this;
			$('#contentWidth_4').html(_.template(czjymm2Tpl)) ;		
			
			//绑定下载按钮
			cacheUtil.findDocList(function(map){
				comm.initDownload("#aTmpDownload", map.busList, '1002');
			});
			
			//获取企业信息
			safeSet.getqymx({},function(res){
				$("#txtEntCustId").html(res.data.entResNo);
				$("#txtEntName").html(res.data.entName);
				$("#txtContactPerson").html(res.data.contactPerson);
				$("#txtContactPhone").html(res.data.contactPhone);
			});
			
		 	$('#czyjmm_fh').triggerWith('#xtaq_czjymm');

			//取消
			$('#btn_back').triggerWith('#xtaq_czjymm');
			
			self.initUpload_btn();
			
			//初始化上传控件
			//comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],85,45);
			
			//生成验证
			self.getVCode();
			
			//换一张验证码
			$('#btn_sqcz_yzm,#validateCode').click(function(){
				self.getVCode();
			});
			
			//提交重置申请
			$('#czjymm_tjzcsq').click(function(){
				//提交数据
				self.submit();
			});
		},
		initUpload_btn : function (){
			
			var btnIds = ['#fileApply'];
			var imgIds = ['#imgApplyImg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			
			comm.initUploadBtn(btnIds, imgIds);
		},
		/** 交易密码重置申请验证 */
		validateData : function(){

			var validate= $("#sqcz_form").validate({
				rules : {
					fileApply:{
						required:function(){
							//申请业务书判断存在
							if($.trim($("#hidFileApply").val())!=""){return false;}
							else{return true;}
						}
					},
					txtCode:{
						required : true,
						maxlength:4
					},
					txtExplain:{
						required : true,
						maxlength:300
					}
				},
				messages : {
					fileApply:{
						required:comm.lang("safeSet").select_requested_reset_tradpwd_file
					},
					txtCode:{
						required :comm.lang("safeSet").input_reset_trading_password_code,
						maxlength:comm.lang("safeSet").maxlength
					},
					txtExplain:{
						required :comm.lang("safeSet").input_reset_trading_password_explain,
						maxlength:comm.lang("safeSet").maxlength
					}
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}else{
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
					}
				}
			});
			
			return validate.form();
		},
		//提交修改数据 
		submit : function (){
			var self = this;
			if (!self.validateData()) {
				return;
			}
			
			//上传文件
			self.uploadApplyFile(function(fileId){
				var applyParam={"applyID" :fileId,"explain" :$("#txtExplain").val(),"validateCode":$("#txtCode").val()};
				
				//提交后台
				safeSet.applysqjymmcz(applyParam,function(res){
					//提交申请后，显示提示信息
					$('#czjymm_sq').addClass('none');
			    	$('#czjymm_cg').removeClass('none');	
				});
				
				//初始化上传控件
				comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],85,64);
			});
		},
		/** 上传业务文件申请书 */
		uploadApplyFile:function(callBack){
			//文件上传地址
			var uploadUrl=comm.domainList['scsWeb']+comm.UrlList["fileupload"];
			
			//获取已上传的文件编号
			var $fileApplyId=$.trim($("#hidFileApply").val()); 
			
			//判断文件存在;存在时直接返回文件编号，否则上传文件
			if($fileApplyId==""){
				comm.uploadFile(uploadUrl,["fileApply"],function(data){
					$("#hidFileApply").val(data.fileApply);
					
					//回调函数，返回文件编号
					callBack(data.fileApply);
				});
			}else{
				//回调函数，返回文件编号
				callBack($fileApplyId);
			}
		},
		//验证码获取
		getVCode:function(){
			$("#validateCode").attr("src",safeSet.getCodeUrl());
		}
	}
}); 

 