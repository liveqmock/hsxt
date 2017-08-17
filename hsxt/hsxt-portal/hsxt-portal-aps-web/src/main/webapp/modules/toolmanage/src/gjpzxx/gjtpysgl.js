define(['text!toolmanageTpl/gjpzxx/gjtpysgl.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_tj_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_ck_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_jrqr_dialog.html',
		'toolmanageDat/gjpzxx/gjtpysgl',
		'toolmanageLan'
		], function(gjtpysglTpl, gjtpysgl_tj_dialogTpl, gjtpysgl_ck_dialogTpl,gjtpysgl_jrqr_dialogTpl, dataModoule){
	return gjtpysgl_self =  {
		showPage : function(){
			this.initForm();
		},
		initForm : function(){
			$('#busibox').html(_.template(gjtpysglTpl));
			$('#queryBtn').click(function(){
				gjtpysgl_self.initData();
			});
			$('#tj_btn').click(function(){
				gjtpysgl_self.addCardStyle();
			});	
		},
		initData : function(){
			var params = {search_cardStyleName:$("#search_cardStyleName").val().trim()};
			dataModoule.findCardStyleList(params, this.del, this.detail);
		},
		/**
		 * 初始化上传
		 */
		initUpload : function(){
			comm.initUploadBtn(["#microPic"], ["#microPic"],null,null,null);
			
			$("#sourceFile").unbind('change').bind('change', function(){
				var file = $("#sourceFile").val();
				if(file != ""){
					 var fileExtend = file.substring(file.lastIndexOf('.')).toLowerCase();
					 if(fileExtend != ".zip" && fileExtend != ".rar" && fileExtend != ".cdr"){
						comm.warn_alert(comm.lang("toolmanage").isNotZipFile);
						$("#sourceFile").val("");
					 }
				}
			});
			
			$("#confirmFile").unbind('change').bind('change', function(){
				var file = $("#confirmFile").val();
				if(file != ""){
					 var fileExtend = file.substring(file.lastIndexOf('.')).toLowerCase();
					 if(fileExtend != ".doc" && fileExtend != ".docx"){
						comm.warn_alert(comm.lang("toolmanage").isNotDocFile);
						$("#confirmFile").val("");
					 }
				}
			});
		},
		/**
		 * 添加卡样
		 */
		addCardStyle : function(){
			$('#dialogBox_tj').html(_.template(gjtpysgl_tj_dialogTpl));
			gjtpysgl_self.initUpload();
			/*弹出框*/
			$( "#dialogBox_tj" ).dialog({
				title:"添加互生卡样式",
				width:"600",
				closeIcon:true,
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!gjtpysgl_self.validateData().form()){
							return;
						}
						comm.tooluploadFile(null, ['microPic', 'sourceFile', 'confirmFile'], function(data){
							if(data.microPic){
								$("#microPicId").val(data.microPic);
							}
							if(data.sourceFile){
								$("#sourceFileId").val(data.sourceFile);
							}
							if(data.confirmFile){
								$("#confirmFileId").val(data.confirmFile);
							}
							var params = {};
							params.cardStyleName = $("#cardStyleName").val();
							params.microPic = $("#microPicId").val();
							params.confirmFile = $("#confirmFileId").val();
							params.sourceFile = $("#sourceFileId").val();
							params.reqOperator = comm.getCookieOperNoName();
							dataModoule.addCardStyle(params, function(data){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									$('#dialogBox_tj').dialog('destroy');
									gjtpysgl_self.initData();
								}});
							});
						}, function(res){
							gjtpysgl_self.initUpload();
						}, {annexType:"zip-pic",permission:"0"});
					},
					"取消":function(){
						$('#tj_form').find('input,textarea,select').tooltip().tooltip( "destroy" );
						$( this ).dialog("destroy");
					}
				}
			});
		},
		
		//上传文件
		uploadFile : function(callBack){
			var microPic_hidden = $('#microPic_hidden').val();
			var sourceFile_hidden = $('#sourceFile_hidden').val();
			var confirmFile_hidden = $('#confirmFile_hidden').val();
			if(comm.isNotEmpty(microPic_hidden)&&comm.isNotEmpty(sourceFile_hidden)&&comm.isNotEmpty(confirmFile_hidden)){
				var resp = {
					microPic:microPic_hidden,	
					sourceFile:sourceFile_hidden,	
					confirmFile:confirmFile_hidden
				};
				callBack(resp);
			}else{
				var ids = ['microPic','sourceFile','confirmFile'];
				comm.tooluploadFile(null, ids, function(resp){
					if(resp){
						callBack(resp);
					}
				},function(err){
					self.initUpload();
				}, {annexType:"zip-pic",permission:"0"});
			}
		},
		
		
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return comm.getNameByEnumId(record['enableStatus'], comm.lang("toolmanage").enableStatus);
			}
			if(record.enableStatus == 1){
				return $('<a>禁用</a>').click(function(e) {
					gjtpysgl_self.jrqr(record);
				}.bind(this));
			}
			return $('<a>启用</a>').click(function(e) {
				gjtpysgl_self.jrqr(record);
			}.bind(this));
		},
		/**
		 * 查看动作
		 */
		del : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return null;
			} 
			return $('<a>查看</a>').click(function(e) {
				gjtpysgl_self.chaKan(record);
			}.bind(this));
		},
		/**
		 * 禁用/启用
		 */
		jrqr : function(record){
			if(record.enableStatus == 1){
				record.enableStatus1 = "启用";
				record.enableStatus2 = "禁用";
			}else{
				record.enableStatus1 = "禁用";
				record.enableStatus2 = "启用";
			}
			$('#dialogBox_jrqr').html(_.template(gjtpysgl_jrqr_dialogTpl, record));
			
			gjtpysgl_self.initVerificationMode();
			
			/*弹出框*/
			$( "#dialogBox_jrqr" ).dialog({
				title:"状态修改",
				width:"400",
				height:"350",
				closeIcon:true,
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!gjtpysgl_self.validateViewMarkData().form()){
							return;
						}
						
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							var params = {};
							params.cardStyleId = record.cardStyleId;
							params.reqOperator = comm.getCookieOperNoName();
							params.enableStatus = (record.enableStatus == 1)?0:1;
							dataModoule.cardStyleEnableOrStop(params, function(data){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									$('#dialogBox_jrqr').dialog('destroy');
									gjtpysgl_self.initData();
								}});
							});
						});
					},
					"取消":function(){
						$('#confirm_dialog_form').find('input,textarea,select').tooltip().tooltip( "destroy" );
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("common").verificationMode, 185, '1').change(function(e){
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
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			var validate = $("#tj_form").validate({
				rules : {
					cardStyleName : {
						required : true,
						rangelength:[1, 20]
					},
					microPic : {
						required : comm.isEmpty($('#microPicId').val())
					},
					sourceFile : {
						required : comm.isEmpty($('#sourceFileId').val())	
					},
					confirmFile : {
						required : comm.isEmpty($('#confirmFileId').val())
					}
				},
				messages : {
					cardStyleName : {
						required : comm.lang("toolmanage")[36278],
						rangelength: comm.lang("toolmanage")[36282],
					},
					microPic : {
						required : comm.lang("toolmanage")[36279],
					},
					sourceFile : {
						required : comm.lang("toolmanage")[36280],
					},
					confirmFile : {
						required : comm.lang("toolmanage")[36285],
					}
				},
				errorPlacement : function (error, element) {
					if($(element).attr('name') == 'microPic'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+180",
								at : "top+15"
							}
						}).tooltip("open");
					}else 	if($(element).attr('name') == 'cardStyleName'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+38",
								at : "top+15"
							}
						}).tooltip("open");
					}else 	if($(element).attr('name') == 'sourceFile'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+265",
								at : "top+15"
							}
						}).tooltip("open");
					}
					else{
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+265",
								at : "top+15"
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
			//检查附件信息，如果存在附件信息则不检查文件必填
			var microPicId = $("#microPicId").val();
			var sourceFileId = $("#sourceFileId").val();
			var confirmFileId = $("#confirmFileId").val();
			if(microPicId != ""){
				validate.settings.rules.microPic = {required : false};
				validate.settings.messages.microPic = {required : ''};
			}
			if(sourceFileId != ""){
				validate.settings.rules.sourceFile = {required : false};
				validate.settings.messages.sourceFile = {required : ''};
			}
			if(confirmFileId != ""){
				validate.settings.rules.confirmFile = {required : false};
				validate.settings.messages.confirmFile = {required : ''};
			}
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#confirm_dialog_form").validate({
				rules : {
					doubleUserName : {
						required : true,
					},
					doublePassword : {
						required : true,
					},
				},
				messages : {
					doubleUserName : {
						required : comm.lang("toolmanage")[36200],
					},
					doublePassword : {
						required : comm.lang("toolmanage")[36201],
					},
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
		chaKan : function(record){
			dataModoule.findCardStyleById({cardStyleId:record.cardStyleId}, function(res){
				var obj = res.data;
				$('#dialogBox_ck').html(_.template(gjtpysgl_ck_dialogTpl, obj));
				comm.initPicPreview("#view-microPic", obj.microPic);
				comm.initDownload("#view-sourceFile", {1000:{fileId:obj.sourceFile, docName:''}}, 1000);
				if(obj.confirmFile){
					$('#view-confirmFile').removeClass('none');
					comm.initDownload("#view-confirmFile", {1001:{fileId:obj.confirmFile, docName:''}}, 1001);
				}
				$("#dialogBox_ck").dialog({
					title:"互生卡样式详情",
					width:"600",
					closeIcon:true,
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
		}
	}	
});