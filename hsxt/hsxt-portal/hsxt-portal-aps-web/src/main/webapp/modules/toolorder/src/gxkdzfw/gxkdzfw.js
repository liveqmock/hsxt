define(['text!toolorderTpl/gxkdzfw/gxkdzfw.html',
		'text!toolorderTpl/gxkdzfw/gxkdzfw_ck1.html',
		'text!toolorderTpl/gxkdzfw/gxkdzfw_ck2.html',
		'text!toolorderTpl/gxkdzfw/gxkdzfw_sc.html',
        'toolorderDat/gxkdzfw/gxkdzfw',
        'toolorderLan'
		], function(gxkdzfwTpl, gxkdzfw_ck1Tpl, gxkdzfw_ck2Tpl, gxkdzfw_scTpl, dataModoule){
	
	var self = {
		showPage : function(){
			self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(gxkdzfwTpl));
			
			//**下拉列表  订单类型*/
			comm.initSelect("#cardStylestatus",comm.lang("toolorder").queryStatus,null);
			
			/** 查询事件 */
			$("#queryBtn").click(function(){
				self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_hsResNo = $("#search_hsResNo").val().trim();
			params.search_hsCustName = $("#search_hsCustName").val().trim();
			params.search_status= comm.navNull($("#cardStylestatus").attr("optionvalue"));
			dataModoule.findSpecialCardStyleList(params, self.detail,self.edit);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.formatMoneyNumberAps(record.cardStyleFee);
			}else if(colIndex == 3){
				return comm.lang("toolorder").cardStyleStatus[record.isConfirm];
			}else if(colIndex == 6){
				var link1 = $('<a>'+comm.lang("toolorder").cardStyleOpt['query']+'</a>').click(function(e) {
					//已确认
					if(record.isConfirm){
						self.show_cardStyleIsConfirm(record);
					//待确认
					}else{
						self.show_cardStyleIsNotConfirm(record);
					}
				}) ;
			    return link1;
			}	
		},
		/**
		 * 上传设计稿
		 */
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 6&&!record.isConfirm){
				var link1 = $('<a>'+comm.lang("toolorder").cardStyleOpt['upload']+'</a>').click(function(e) {
					self.show_cardStyleUpLoadFile(record);
				}) ;
			    return link1;
			}
		},
		/**
		 * 弹出查询设计稿(已确认)
		 */
		show_cardStyleIsConfirm : function(obj){
			$('#ck1_dialog').html(_.template(gxkdzfw_ck1Tpl));
			if(obj.microPic){
				$("#ck1_dialog #microPicShow").show();
				$("#ck1_dialog #microPicShow").attr('src',comm.getFsServerUrl(obj.microPic));
				comm.initPicPreview("#ck1_dialog #microPicShow", obj.microPic);
			}
			if(obj.confirmFile){
				comm.initDownload("#ck1_dialog #upDownConfirmFileShow", {1000:{fileId:obj.confirmFile, docName:''}}, 1000);
			}
			if(obj.sourceFile){
				$('#ck1_dialog #upDownSourceFileShow').attr('href',comm.getFsServerUrl(obj.sourceFile));
				comm.initDownload("#ck1_dialog #upDownSourceFileShow", {1000:{fileId:obj.sourceFile, docName:''}}, 1000);
			}
			$("#ck1_dialog" ).dialog({
				title:"查看",
				width:"800",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"关闭":function(){
						$(this).dialog("destroy");
					}
				}
			});
		},
		/**
		 * 弹出查询设计稿(未确认)
		 */
		show_cardStyleIsNotConfirm : function(obj){
			$('#ck2_dialog').html(_.template(gxkdzfw_ck2Tpl,obj));
			if(obj.microPic){
				$("#ck2_dialog #microPicShow").show();
				$("#ck2_dialog #microPicShow").attr('src',comm.getFsServerUrl(obj.microPic));
				comm.initPicPreview("#ck2_dialog #microPicShow", obj.microPic);
			}
			if(obj.mSourceFile){
				comm.initDownload("#ck2_dialog #markAccessoryShow", {1000:{fileId:obj.mSourceFile, docName:''}}, 1000);
			}
			$("#ck2_dialog" ).dialog({
				title:"查看",
				width:"800",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"关闭":function(){
						$(this).dialog("destroy");
					}
				}
			});
		},
		/**
		 * 上传设计稿
		 */
		show_cardStyleUpLoadFile : function(obj){
			$('#up_dialog').html(_.template(gxkdzfw_scTpl, obj));
			self.initUpload();
			if(obj.mSourceFile){
				comm.initDownload("#up_dialog #markAccessory", {1000:{fileId:obj.mSourceFile, docName:''}}, 1000);
			}
			if(obj.microPic){
				$('#up_dialog #microPicImg').removeClass('none');
				$('#up_dialog #microPicImg').attr('src',comm.getFsServerUrl(obj.microPic));
				comm.initPicPreview("#up_dialog #microPicImg", obj.microPic);
			}
			if(obj.sourceFile){
				$('#up_dialog #sourceFileShow').removeClass('none');
				comm.initDownload("#up_dialog #sourceFileShow", {1000:{fileId:obj.sourceFile, docName:''}}, 1000);
			}
			if(obj.confirmFile){
				$('#up_dialog #confirmFileShow').removeClass('none');
				comm.initDownload("#up_dialog #confirmFileShow", {1000:{fileId:obj.confirmFile, docName:''}}, 1000);
			}
			$("#up_dialog" ).dialog({
				title:"上传设计稿",
				width:"800",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						if(!self.validateData().form()){
							return;
						}
						self.uploadFile(function(data){
							if(data.microPic){
								$("#microPic_hidden").val(data.microPic);
							}
							if(data.sourceFile){
								$("#sourceFile_hidden").val(data.sourceFile);
							}
							if(data.confirmFile){
								$("#confirmFile_hidden").val(data.confirmFile);
							}
							self.saveData(obj.orderNo, "#up_dialog");
						});
					},
					"取消":function(){
						$('#microPic').tooltip().tooltip("destroy");
						$('#sourceFile').tooltip().tooltip("destroy");
						$('#confirmFile').tooltip().tooltip("destroy");
						$(this).dialog("destroy");
					}
				}
			});
		},
		/**
		 * 保存动作
		 * @param orderNo 订单号
		 * @param dialogId 对话框Id
		 */
		saveData : function(orderNo, dialogId){
			var params = {};
			params.orderNo = orderNo;
			params.microPic = $("#microPic_hidden").val();
			params.sourceFile = $("#sourceFile_hidden").val();
			params.confirmFile = $("#confirmFile_hidden").val();
			params.reqRemark = $("#reqRemark").val();
			dataModoule.uploadCardStyleMarkFile(params, function(res){
				comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
					$(dialogId).dialog("destroy");
					$('#microPic').val('');
					$('#sourceFile').val('');
					$('#confirmFile').val('');
					self.initData();
				}});
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
				}, {annexType:"zip-pic"});
			}
		},
		
		/**
		 * 初始化上传
		 */
		initUpload : function(){
			//comm.initUploadBtn(["#microPic"], ["#microPic"],null,null,null);
			
			$("#sourceFile").unbind('change').bind('change', function(){
				var file = $("#sourceFile").val();
				if(file != ""){
					 var fileExtend = file.substring(file.lastIndexOf('.')).toLowerCase();
					 if(fileExtend != ".zip" && fileExtend != ".rar" && fileExtend != ".cdr"){
						comm.warn_alert(comm.lang("toolorder").isNotCdrFile);
						$("#sourceFile").val("");
					 }
				}
			});
			
			$("#confirmFile").unbind('change').bind('change', function(){
				var file = $("#confirmFile").val();
				if(file != ""){
					 var fileExtend = file.substring(file.lastIndexOf('.')).toLowerCase();
					 if(fileExtend != ".doc" && fileExtend != ".docx"){
						comm.warn_alert(comm.lang("toolorder").isNotDocFile);
						$("#confirmFile").val("");
					 }
				}
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $('#upLoadFild').validate({
				rules : {
					microPic : {
						required : comm.isEmpty($('#microPic_hidden').val())
					},
					sourceFile : {
						required : comm.isEmpty($('#microPic_hidden').val())	
					},
					confirmFile : {
						required : comm.isEmpty($('#microPic_hidden').val())
					}
				},
				messages : {
					microPic : {
						required:comm.lang("toolorder").microPicIsNotNull
					},
					sourceFile : {
						required:comm.lang("toolorder").sourceFileIsNotNull
					},
					confirmFile : {
						required:comm.lang("toolorder").confirmFileIsNotNull
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
			return validate;
		}	
	};
	return self;	
});