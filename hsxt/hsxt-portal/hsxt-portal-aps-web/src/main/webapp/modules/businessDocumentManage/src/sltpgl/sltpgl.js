define(['text!businessDocumentManageTpl/sltpgl/sltpgl.html',
		'text!businessDocumentManageTpl/sltpgl/edit.html',
		'text!businessDocumentManageTpl/sltpgl/del.html',
		'text!businessDocumentManageTpl/sltpgl/sub.html',
		'text!businessDocumentManageTpl/sltpgl/version.html',
        'businessDocumentManageDat/businessDoc',
		'businessDocumentManageLan'
	], function(sltpglTpl, editTpl, delTpl, subTpl, versionTpl, dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(records){
			var self = this;
			$('#busibox').html(_.template(sltpglTpl, {record:records}));
			//弹框
			$("input[value='编辑']").click(function(){
				var index = parseInt($(this).attr("id").replace("edt_", ""));
				$('#sltpgl-edit-dialog > p').html(_.template(editTpl, records[index]));
				self.getSltpList(records[index].docCode);
				self.initPicPreview(records[index].fileId);
				$('#sltpgl-edit-dialog').dialog({
					title : "修改示例图片",
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					width : 500,
					buttons: {
						"确定":function(){
							self.saveDoc("#sltpgl-edit-dialog");
						},
						"取消":function(){
						 	$(this).dialog("destroy");
						}
					}
				});	
			});
			$("input[value='删除']").click(function(){
				var index = parseInt($(this).attr("id").replace("del_", ""));
				$('#sltpgl-edit-dialog > p').html(_.template(delTpl));
				$('#sltpgl-edit-dialog').dialog({
					title : "提示",
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					width : 400,
					buttons: {
						"确定":function(){
							var params = {};
							params.docCode = records[index].docCode;
							params.fileType = 1;
							dataModoule.deleteBusinessDoc(params, function(res){
								$("#sltpgl-edit-dialog").dialog("destroy");
								comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
									self.initData();
								}});
							});
						},
						"取消":function(){
						 	$(this).dialog("destroy");
						}
					}
				});	
			});
			
			$("input[value='版本']").click(function(){
				var index = parseInt($(this).attr("id").replace("ver_", ""));
				var params = {};
				params.docCode = records[index].docCode;
				dataModoule.findBusinessImageDocHis(params, function(res){
					var records_ = res.data;
					for(var key in records_){
						records_[key].image = "<img width='166' height='97' src='"+comm.getFsServerUrl(records_[key].fileId)+"'/>";
						records_[key].statusText = comm.getNameByEnumId(records_[key].status, comm.lang("businessDocumentManage").statusEnum);
					}
					$('#sltpgl-edit-dialog > p').html(_.template(versionTpl, {record:records_}));
					for(var key in records_){
						comm.initPicPreview("#preview_"+records_[key].docId, records_[key].fileId, null);
					};
					$(".version_btn").click(function(){
						var docId = $(this).attr("id");
						comm.confirm({
							imgFlag:true,
							title: "提示信息",
							content : "确认恢复此版本？",
							callOk:function(){
								var params = {};
								params.docId = docId;
								dataModoule.recoveryBusinessImageDoc(params, function(res){
									$('#sltpgl-edit-dialog').dialog("destroy");
									comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
										self.initData();
									}});
								});
							},
							callCancel:function(){
								
							}
						});
					});
					$('#sltpgl-edit-dialog').dialog({
						title : "版本信息",
						open: function (event, ui) {
							$(".ui-dialog-titlebar-close", $(this).parent()).show();
						},
						width : 1000,
						height: 600,
						buttons: {
							"确定":function(){
								$(this).dialog("destroy");
							},
							"取消":function(){
								$(this).dialog("destroy");
							}
						}
					});	
				});
			});
			
			$("input[value='发布']").click(function(){
				var index = parseInt($(this).attr("id").replace("sub_", ""));
				var picSrc = comm.getFsServerUrl(records[index].fileId);
				$('#sltpgl-edit-dialog > p').html(_.template(subTpl, {src:picSrc}));
				$('#sltpgl-edit-dialog').dialog({
					title : "提示",
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					width : 500,
					height:"340",
					buttons: {
						"确定":function(){
						 	if(!self.validateData_2().form()){
								return;
							}
							//验证双签
							comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
								var params = {};
								params.docId = records[index].docId;
								params.fileType = 1;
								dataModoule.releaseBusinessDoc(params, function(res){
									$("#sltpgl-edit-dialog").dialog("destroy");
									comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
										self.initData();
									}});
								});
							});
						},
						"取消":function(){
						 	$(this).dialog("destroy");
						}
					}
				});	
				
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
				
			});
			$("input[value='停用']").click(function(){
				var index = parseInt($(this).attr("id").replace("sto_", ""));
				var picSrc = comm.getFsServerUrl(records[index].fileId);
				$('#sltpgl-edit-dialog > p').html(_.template(subTpl, {src:picSrc}));
				$('#sltpgl-edit-dialog').dialog({
					title : "提示",
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					width : 500,
					height:"340",
					buttons: {
						"确定":function(){
							if(!self.validateData_2().form()){
								return;
							}
							//验证双签
							comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
								var params = {};
								params.docCode = records[index].docCode;
								params.fileType = 1;
								dataModoule.stopUsedBusinessDoc(params, function(res){
									$("#sltpgl-edit-dialog").dialog("destroy");
									comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
										self.initData();
									}});
								});
							});
						},
						"取消":function(){
						 	$(this).dialog("destroy");
						}
					}
				});	
				
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
				
			});
			$(".img-exp-add").click(function(){
				$('#sltpgl-edit-dialog > p').html(_.template(editTpl));
				self.getSltpList();				
				self.initPicPreview();
				$('#sltpgl-edit-dialog').dialog({
					title : "新增示例图片",
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					width : 500,
					buttons: {
						"确定":function(){
						 	self.saveDoc("#sltpgl-edit-dialog");
						},
						"取消":function(){
						 	$(this).dialog("destroy");
						}
					}
				});
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findBusinessImageDocList(null, function(res){
				var records = res.data;
				for(var key in records){
					records[key].src = comm.getFsServerUrl(records[key].fileId);
					records[key].statusText = comm.getNameByEnumId(records[key].status, comm.lang("businessDocumentManage").statusEnum);
				}
				self.initForm(records);
			});
		},
		/**
		 * @param defaultVal 默认值（可选参数）
		 */
		getSltpList:function(defaultVal){
			comm.initSelect('#docCode', comm.lang("common").samplePicEnum, 180, comm.removeNull(defaultVal), null, 180);
		},
		/**
		 * 保存示例图片
		 */
		saveDoc : function(dialogId){
			var self = this;
			if(!this.validateData().form()){
				return;
			}
			if($("#fileId").val() != ""){
				comm.uploadFile(null, ['fileId'], function(data){
					if(data.fileId){
						$("#hidden-fileId").val(data.fileId);
					}
					self.saveBusinessDoc(dialogId);
				}, function(err){
					self.initPicPreview();
				}, {delFileIds:null});
			}else{
				self.saveBusinessDoc(dialogId);
			}
		},
		/**
		 * 绑定预览
		 */
		initPicPreview : function(fileId){
			var btnIds = ['#fileId'];
			var imgIds = ['#imgage'];
			if(fileId){
				$("#imgage").html("<img width='136' height='102' src='"+comm.getFsServerUrl(fileId)+"'/>");
			}
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 136, 102);
		},
		/**
		 * 保存示例图片
		 */
		saveBusinessDoc : function(dialogId){
			var self = this;
			var params = {};
			params.docId = $("#docId").val();//文件编号
			params.docName = $("#docName").val();//图片名称
			params.docCode = $("#docCode").attr('optionValue');//唯一标识
			params.remark = $("#remark").val();//图片说明
			params.fileId = $("#hidden-fileId").val();//文件ID
			params.docType = 1;//文件类型
			dataModoule.savePicDoc(params, function(res){
				$(dialogId).dialog("destroy");
				comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
					self.initData();
				}});
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#edit_form").validate({
				 rules : {
					docName : {
						required : true,
						rangelength : [1, 20],
					},
					docCode : {
						required : true
					},
					remark : {
						rangelength:[0, 300]
					}
				},
				messages : {
					docName : {
						required : comm.lang("businessDocumentManage")[36361],
						rangelength : comm.lang("businessDocumentManage")[36365]
					},
					docCode : {
						required : comm.lang("businessDocumentManage")[36363],
					},
					remark : {
						rangelength:comm.lang("businessDocumentManage")[36366],
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
			var fileId = $("#hidden-fileId").val();
			if(comm.removeNull(fileId) == ""){
				validate.settings.rules.fileId = {required : true};
				validate.settings.messages.fileId = {required : comm.lang("businessDocumentManage")[36364]};
			}else{
				validate.settings.rules.fileId = {required : false};
			}
			return validate;
		},
		/**
		 * 表单校验-双签
		 */
		validateData_2 : function(){
			return $("#form_two").validate({
				rules : {
					pk_double_username : {
						required : true
					},
					pk_double_password : {
						required : true
					}
				},
				messages : {
					pk_double_username : {
						required : comm.lang("businessDocumentManage")[36200]
					},
					pk_double_password : {
						required : comm.lang("businessDocumentManage")[36201]
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
	}	
});
