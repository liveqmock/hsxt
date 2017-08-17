define(['text!coDeclarationTpl/qybb/fjxx.html',
        'text!coDeclarationTpl/qybb/confirm_dialog.html',
        'coDeclarationDat/qybb/qybb',
	   	'coDeclarationLan'], function(fjxxTpl, confirm_dialogTpl, dataModoule){
	return {
		qybb_fjxx_trsArray : null,//修改的数据
		qybb_fjxx_oldArray : null,//修改前数据
		qybb_fjxx_newArray : null,//修改后数据
		showPage : function(){
			self = this;
			$('#infobox').html(_.template(fjxxTpl));
			
			this.initForm();
			this.initUpload();
			
			//加载样例图片
			cacheUtil.findDocList(function(map){
				comm.initPicPreview("#picFileId-3", comm.getPicServerUrl(map.picList, '1010'));
				comm.initPicPreview("#picFileId-4", comm.getPicServerUrl(map.picList, '1008'));
				comm.initPicPreview("#picFileId-5", comm.getPicServerUrl(map.picList, '1009'));
			});
			
			comm.getSecuritCode("#ent_declare_bbsh_input", "#ent_declare_bbsh_iamges", ['#ent_declare_bbsh_btn_1', '#ent_declare_bbsh_btn_2'], "ent_declare_bbsh");
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#cancel_btn').triggerWith('#'+$("#menuName").val());
			//保存
			$('#qybb_bc').click(function(){
				self.aptitudeUpload();
			});
			this.initTmpPicPreview();
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#licensePicId', '#bankPicId', '#sharePicId'];
			var imgIds = ['#realPicFileId-3', '#realPicFileId-4', '#realPicFileId-5'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds);
		},
		/**
		 * 企业报备-初始化附件信息
		 */
		initUpload : function(){
			var params = {applyId: this.getApplyId()};
			dataModoule.initUpload(params, function(res){
//				var caseList = res.data.caseList;//样例数据
				var realList = res.data.realList;//附件信息
//				for(var k in caseList){
//					var title = comm.getNameByEnumId(caseList[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
//					comm.initPicPreview("#picFileId-"+caseList[k].aptType, caseList[k].fileId, title);
//				}
				for(var k in realList){
					var title = comm.getNameByEnumId(realList[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
					comm.initPicPreview("#realPicFileId-"+realList[k].aptType, realList[k].fileId, title);
					$("#realPicFileId-"+realList[k].aptType).html("<img width='107' height='64' src='"+comm.getFsServerUrl(realList[k].fileId)+"'/>");
					$("#fileId-"+realList[k].aptType).val(realList[k].fileId);
					$("#filingAptId-"+realList[k].aptType).val(realList[k].filingAptId);
				}
			});
		},
		/**
		 * 企业报备-保存附件信息
		 */
		saveAptitude : function(dblOptCustId){
			var params = {
					applyId: this.getApplyId(),
					licensePicFileId:$("#fileId-3").val(),//营业执照扫描件文件Id
					bankPicFileId:$("#fileId-4").val(),//银行资金证明文件Id
					sharePicFileId:$("#fileId-5").val(),//合作股东证明文件Id
					licensePicAptId:$("#filingAptId-3").val(),//营业执照扫描件文件Id
					bankPicAptId:$("#filingAptId-4").val(),//银行资金证明文件Id
					sharePicAptId:$("#filingAptId-5").val(),//合作股东证明文件Id
					dblOptCustId:dblOptCustId,//复核员ID
					smsCode:$("#ent_declare_bbsh_input").val(),//验证码
					codesType:"ent_declare_bbsh"//验证码类别
				};
			dataModoule.saveAptitude(params, function(res){
				if(res.data){
					var realList = res.data;
					for(var k in realList){
						$("#filingAptId-"+realList[k].aptType).val(realList[k].filingAptId);
					}
				}
				comm.alert({content:comm.lang("coDeclaration").uploadOk});
				$("#fjxx_dialog").dialog('destroy');
			});
		},
		/**
		 * 企业报备-上传附件信息
		 */
		aptitudeUpload : function(){
			var self = this;
			if(!this.validateData().form()){
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			var odata = comm.getCache('coDeclaration', 'kqxtyw-qysczl-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			
			this.qybb_fjxx_trsArray = [];
			this.qybb_fjxx_oldArray = [];
			this.qybb_fjxx_newArray = [];
			
			self.getChangePic("#licensePicId", "#fileId-3", "营业执照扫描件", 3);
			self.getChangePic("#bankPicId", "#fileId-4", "组织机构代码证扫描件", 4);
			self.getChangePic("#sharePicId", "#fileId-5", "税务登记证扫描件", 5);
	
			var trsArray = this.qybb_fjxx_trsArray;//存入改变的TR
			for(var key in trsArray){
				trs+=trsArray[key];
			}
			if(trs == ""){
//				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
//				return;
			}
			//提交
			$('#fjxx_dialog > p').html(_.template(confirm_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
				
			var oldArray = this.qybb_fjxx_oldArray;//修改前图片信息
			var newArray = this.qybb_fjxx_newArray;//修改后图片信息
			for(var key in oldArray){
				comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
			}
			for(var key in newArray){
				comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
			}
			self.initVerificationMode();
			$('#fjxx_dialog').dialog({
				width:800,
				buttons:{
					'确定':function(){
						if(!self.validateViewMarkData().form()){
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							var ids = [];
							var delFileIds = [];
							if($("#licensePicId").val() != ""){
								if($("#fileId-3").val() != ""){
									delFileIds[delFileIds.length] = $("#fileId-3").val();
								}
								ids[ids.length] = "licensePicId";
							}
							if($("#bankPicId").val() != ""){
								if($("#fileId-4").val() != ""){
									delFileIds[delFileIds.length] = $("#fileId-4").val();
								}
								ids[ids.length] = "bankPicId";
							}
							if($("#sharePicId").val() != ""){
								if($("#fileId-5").val() != ""){
									delFileIds[delFileIds.length] = $("#fileId-5").val();
								}
								ids[ids.length] = "sharePicId";
							}
							if(ids.length == 0){
//								comm.warn_alert(comm.lang("coDeclaration").nopicUpdate);
//								return;
							}
							comm.uploadFile(null, ids, function(data){
								if(data.licensePicId){
									$("#fileId-3").val(data.licensePicId);
								}
								if(data.bankPicId){
									$("#fileId-4").val(data.bankPicId);
								}
								if(data.sharePicId){
									$("#fileId-5").val(data.sharePicId);
								}
								self.initTmpPicPreview();
								self.saveAptitude(verifyRes.data);
							}, function(err){
								self.initTmpPicPreview();
							}, {delFileIds:delFileIds});
						});
					},
					'取消':function(){
						$(this).dialog('destroy');
					}
				}
			});	
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#qybb_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : true
					},
					doublePassword : {
						required : true
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[36006]
					},
					doubleUserName : {
						required : comm.lang("coDeclaration")[36047]
					},
					doublePassword : {
						required : comm.lang("coDeclaration")[36048]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#fjxx_form").validate({
				rules : {
					licensePicId : {
						required : true
					},
					bankPicId : {
						required : false
					},
					sharePicId : {
						required : false
					},
					ent_declare_bbsh_input : {
						required : true,
						validationCode : true
					}
				},
				messages : {
					licensePicId : {
						required :  comm.lang("coDeclaration")[36009]
					},
					bankPicId : {
						required : comm.lang("coDeclaration")[36083]
					},
					sharePicId : {
						required : comm.lang("coDeclaration")[36084]
					},
					ent_declare_bbsh_input : {
						required : comm.lang("common").codesIsNull,
						validationCode : comm.lang("common").codesIsInvalid
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			/**
			 * 如果是修改则无需对上传文件进行必填校验
			 */
			if($("#fileId-3").val() != ""){
				validate.settings.rules.licensePicId = {required : false};
			}
			if($("#fileId-4").val() != ""){
				validate.settings.rules.bankPicId = {required : false};
			}
			if($("#fileId-5").val() != ""){
				validate.settings.rules.sharePicId = {required : false};
			}
			return validate;
		},
		/**
		 * 
		 * @param btnId 上传按钮ID
		 * @param fileId 图片文件ID
		 * @param desc 图片抬头
		 * @param index 图片类型（传入枚举值）
		 */
		getChangePic : function(btnId, fileId, desc, index){
			if($(btnId).val() != ""){
				var fId = $(fileId).val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new-"+index+"\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old-"+index+"\" class=\"blue\">查看</a>";
					this.qybb_fjxx_oldArray.push({picId:"#prv-pic-old-"+index, fileId: fId, title:"修改前"+desc});
				}
				this.qybb_fjxx_newArray.push({picId:"#prv-pic-new-"+index, fileId: '#realPicFileId-'+index, title:"修改后"+desc});
				this.qybb_fjxx_trsArray.push("<tr><td class=\"view_item\">"+desc+"</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>");
			}
		},
		/**
		 * 获取申请编号
		 */
		getApplyId : function(){
			return comm.getCache("coDeclaration", "entFilling").applyId;
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("coDeclaration").verificationMode, null, '1').change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
				}
			});
		}	
	}	
});