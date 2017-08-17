define(['text!companyInfoTpl/lxxx/lxxxxg.html',
        'companyInfoDat/lxxx/lxxx',
		'companyInfoLan'],function(lxxxxgTpl, dataModoule){
	var self = {
		showPage : function(){
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(lxxxxgTpl, data));
			
			//取消
			$('#backBt_qiyelianxi').click(function(){
				comm.confirm({
					imgFlag : true,
					imgClass : 'tips_ques',
					content : comm.lang("companyInfo").cancel_tips,
					callOk : function(){
						$('#qyxx_lxxx').click();
					} 	
				});
			});
			$('#submitBt_qiyelianxi').click(function(){
			 	self.saveLink();
			});
			
			/*//联系人授权委托书,如果不为空则隐藏上传按钮、下载模板按钮
			if(data && data.contactProxy){
				$("#certificateFile").val(data.contactProxy);
				comm.initPicPreview("#img", data.contactProxy, "");
				comm.initPicPreview("#bimg", data.contactProxy, "");
				$("#img").attr("src", comm.getFsServerUrl(data.contactProxy));
				$("#bimg").children().first().attr("src", comm.getFsServerUrl(data.contactProxy));
				$("#upload_btn").hide(); 
				$("#certificate").hide(); 
				$("#certificateFileId").hide();
			}
			self.picPreview();
			
			//绑定下载按钮
			cacheUtil.findDocList(function(map){
				comm.initDownload("#certificateFileId", map.comList, '1007');
			});*/
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findEntAllInfo(null, function(res){
					comm.setCache("companyInfo", "entAllInfo", res.data);
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qylxxx_form").validate({
				 rules : {
					 contactAddr : {
						required : false,
						rangelength : [2, 128],
					},
					postCode : {
						required : false,
						zipCode : true,
					},
					contactEmail : {
						required : false,
						email2 : true,
					},
				},
				messages : {
					contactAddr : {
						rangelength : comm.lang("companyInfo")[32648],
					},
					postCode : {
						zipCode : comm.lang("companyInfo")[32513],
					},
					contactEmail : {
						email2 : comm.lang("companyInfo")[32656],
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
			//检查附件信息，如果存在附件信息则不检查文件必填
			/*var cerFile = $("#certificateFile").val();
			if(cerFile != ""){
				validate.settings.rules.certificate = {required : false};
				validate.settings.messages.certificate = {required : comm.lang("companyInfo")[33239]};
			}else{
				validate.settings.rules.certificate = {required : true};
				validate.settings.messages.certificate = {required : comm.lang("companyInfo")[33239]};
			}*/
			return validate;
		},
		/**
		 * /绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate'], ['#bimg'], 81, 52);
		},
		/**
		 * 保存联系信息
		 */
		saveLink : function(){
			if(!self.validateData().form()){
				return;
			}
			self.saveData();
			
			/*var ids = [];
			var delFileIds = [];
			if($("#certificate").val() != ""){
				if($("#certificateFile").val() != ""){
					delFileIds[delFileIds.length] = $("#certificateFile").val();
				}
				ids[ids.length] = "certificate";
			}
			if(ids.length == 0){
				self.saveData();
			}else{
				comm.uploadFile(null, ids, function(data){
					if(data.certificate){
						$("#certificateFile").val(data.certificate);
						self.saveData();
					}
					self.picPreview();
				}, function(err){
					self.picPreview();
				}, {delFileIds : delFileIds});
			}*/
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var params = {};
			params.contactAddr = $("#contactAddr").val();//联系地址
			params.postCode = $("#postCode").val();//邮政编码
			params.contactEmail = $("#contactEmail").val();//安全邮箱
			//params.contactProxy = $("#certificateFile").val();//联系人授权委托书
			dataModoule.updateLinkInfo(params, function(res){
				//更新缓存数据
				comm.delCache("companyInfo", "entAllInfo");
				comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
					$('#qyxx_lxxx').click();
				}});
			});
		}
	};
	return self;
}); 

 