define(['text!businessDocumentManageTpl/ywwdxzgl/cyywwd.html',
		'text!businessDocumentManageTpl/ywwdxzgl/edit1.html',
		'text!businessDocumentManageTpl/ywwdxzgl/sub1.html',
        'businessDocumentManageDat/businessDoc',
		'businessDocumentManageLan'
	], function(cyywwdTpl, edit1Tpl, sub1Tpl, dataModoule){
	return {
		cyywwd_self : null,
		showPage : function(){
			cyywwd_self = this;
			this.initForm();
		},
		/**
		 *初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(cyywwdTpl));
			comm.initSelect('#search_docStatus', comm.lang("businessDocumentManage").statusEnum, null, null, {name:'全部', value:''});
			$("#queryBtn").click(function(){
				cyywwd_self.initData();
			});
			$("#xzmb_btn").click(function(){
				cyywwd_self.editData(null, null, "新增常用业务文档");
			});
			cyywwd_self.initData();
		},
		editData : function(record, docCode, title){
			$('#sltpgl-edit-dialog > p').html(_.template(edit1Tpl, record));
			cyywwd_self.getDocList(comm.removeNull(docCode));
			$('#sltpgl-edit-dialog').dialog({
				title : title,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				width : 500,
				buttons: {
					"确定":function(){
						cyywwd_self.saveDoc("#sltpgl-edit-dialog");
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			});	
			
			$("#fileId").change(function(){
				$('#showFileName').text($("#fileId").val());
			});
			
		},
		/**
		 * @param defaultVal 默认值（可选参数）
		 */
		getDocList:function(defaultVal){
			comm.initSelect('#docCode', comm.lang("common").sampleComEnum, 180, comm.removeNull(defaultVal), null, 100);
		},
		/**
		 *初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_docName = $("#search_docName").val();
			params.search_docStatus = $("#search_docStatus").attr('optionValue');
			dataModoule.findBusinessNormalDocList(params, this.detail_1, this.detail_2, this.detail_3);
		},
		/**
		 *自定义函数1
		 */
		detail_1 : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['status'], comm.lang("businessDocumentManage").statusEnum);
			}
			if(record.status == 2){
				return $('<a>停用</a>').click(function(e) {
					$('#sltpgl-edit-dialog > p').html(_.template(sub1Tpl, record));
					$('#sltpgl-edit-dialog').dialog({
						title : "提示",
						open: function (event, ui) {
							$(".ui-dialog-titlebar-close", $(this).parent()).show();
						},
						width : 500,
						height:"260",
						buttons: {
							"确定":function(){
								if(!cyywwd_self.validateData_2().form()){
									return;
								}
								//验证双签
								comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
									var params = {};
									params.docCode = record.docCode;
									params.fileType = 3;
									dataModoule.stopUsedBusinessDoc(params, function(res){
										$("#sltpgl-edit-dialog").dialog("destroy");
										comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
											cyywwd_self.initData();
										}});
									});
								});
							},
							"取消":function(){
								$(this).dialog("destroy");
							}
						}
					});	
					
					/*下拉列表*/
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
				}.bind(this));
			}else{
				return $('<a>发布</a>').click(function(e) {
					$('#sltpgl-edit-dialog > p').html(_.template(sub1Tpl, record));
					$('#sltpgl-edit-dialog').dialog({
						title : "提示",
						open: function (event, ui) {
							$(".ui-dialog-titlebar-close", $(this).parent()).show();
						},
						width : 500,
						height:"260",
						buttons: {
							"确定":function(){
								if(!cyywwd_self.validateData_2().form()){
									return;
								}
								//验证双签
								comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
									var params = {};
									params.docId = record.docId;
									params.fileType = 3;
									dataModoule.releaseBusinessDoc(params, function(res){
										$("#sltpgl-edit-dialog").dialog("destroy");
										comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
											cyywwd_self.initData();
										}});
									});
								});
							},
							"取消":function(){
								$(this).dialog("destroy");
							}
						}
					});
					
					/*下拉列表*/
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
				}.bind(this));
			}
		},
		/**
		 *自定义函数2
		 */
		detail_2 : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return null;
			}
			if(record.status != 2){
				return $('<a>编辑</a>').click(function(e) {
					cyywwd_self.editData(record, record.docCode, "修改常用业务文档");
				}.bind(this));
			}
		},
		/**
		 *自定义函数3
		 */
		detail_3 : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return null;
			}
			if(record.status != 2){
				return $('<a>删除</a>').click(function(e) {
					var confirmObj = {
						imgFlag:true , //显示图片
						content : '您确认删除此常用业务文档？',			 
						callOk :function(){
							var params = {};
							params.docCode = record.docId;
							params.fileType = 3;
							dataModoule.deleteBusinessDoc(params, function(res){
								comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
									cyywwd_self.initData();
								}});
							});			
						}
					}
					comm.confirm(confirmObj);
				}.bind(this));
			}
		},
		/**
		 * 保存示例图片
		 */
		saveDoc : function(dialogId){
			if(!this.validateData().form()){
				return;
			}
			var delFileIds = [];
			if($("#fileId").val() != ""){
				if($("#hidden-fileId").val() != ""){
					delFileIds[0] = $("#hidden-fileId").val();
				}
				comm.uploadFile(null, ['fileId'], function(data){
					if(data.fileId){
						$("#hidden-fileId").val(data.fileId);
					}
					cyywwd_self.saveBusinessDoc(dialogId);
				}, function(err){

				}, {delFileIds:delFileIds, annexType:"word"});
			}else{
				cyywwd_self.saveBusinessDoc(dialogId);
			}
		},
		/**
		 * 保存示例图片
		 */
		saveBusinessDoc : function(dialogId){
			var params = {};
			params.docId = $("#docId").val();//文件编号
			params.docName = $("#docName").val();//图片名称
			params.docCode = $("#docCode").attr('optionValue');//唯一标识
			params.remark = $("#remark").val();//图片说明
			params.fileId = $("#hidden-fileId").val();//文件ID
			params.docType = 3;//文件类型
			dataModoule.saveComDoc(params, function(res){
				$(dialogId).dialog("destroy");
				comm.alert({content:comm.lang("businessDocumentManage")[22000], callOk:function(){
					cyywwd_self.initData();
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
