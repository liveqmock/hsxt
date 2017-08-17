define(['text!companyInfoTpl/lxxx/lxxx_xg.html',
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
			$('#backBt_qiyelianxi').click(function(){
			 	$('#lxxx').click();
			});
			$('#submitBt_qiyelianxi').click(function(){
			 	self.saveLink();
			});
			
			/*//联系人授权委托书
			if(data && data.contactProxy){
				$("#certificateFile").val(data.contactProxy);
				comm.initPicPreview("#bimg", data.contactProxy, "");
				$("#bimg").children().first().attr("src", comm.getFsServerUrl(data.contactProxy));
			}
			//其他协议
			if(data && data.helpAgreement){
				$("#certificateFile2").val(data.helpAgreement);
				comm.initPicPreview("#bimg2", data.helpAgreement, "");
				$("#bimg2").children().first().attr("src", comm.getFsServerUrl(data.helpAgreement));
			}
			
			//绑定下载按钮
			cacheUtil.findDocList(function(map){
				comm.initDownload("#certificateFileId", map.comList, '1007');
				comm.initDownload("#certificateFileId2", map.comList, '1007');
			});
			
			this.picPreview();*/
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
						rangelength : comm.lang("companyInfo")[31055],
					},
					postCode : {
						zipCode : comm.lang("companyInfo")[31056],
					},
					contactEmail : {
						email2 : comm.lang("companyInfo")[31057],
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
			 if( $("#legalPerson").val() == $("#name").val() ){
				 return validate;
			 }
			/*//检查附件信息，如果存在附件信息则不检查文件必填
			var cerFile = $("#certificateFile").val();
			if(cerFile != ""){
				validate.settings.rules.certificate = {required : false};
				validate.settings.messages.certificate = {required : comm.lang("companyInfo")[31058]};
			}else{
				validate.settings.rules.certificate = {required : true};
				validate.settings.messages.certificate = {required : comm.lang("companyInfo")[31058]};
			}*/
			return validate;
		},
		/**
		 * /绑定图片预览
		 *//*
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
				comm.initTmpPicPreview('#bimg2', $('#bimg2').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate'], ['#bimg'], 81, 52);
			comm.initUploadBtn(['#certificate2'], ['#bimg2'], 81, 52);
		},*/
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
			if(comm.isNotEmpty($("#certificate").val())){
				if(comm.isNotEmpty($("#certificateFile").val())){
					delFileIds[delFileIds.length] = $("#certificateFile").val();
				}
				ids[ids.length] = "certificate";
			}
			if(comm.isNotEmpty($("#certificate2").val())){
				if(comm.isNotEmpty($("#certificateFile2").val())){
					delFileIds[delFileIds.length] = $("#certificateFile2").val();
				}
				ids[ids.length] = "certificate2";
			}
			if(ids.length == 0){
				self.saveData();
			}else{
				comm.uploadFile(null, ids, function(data){
					if(data.certificate){
						$("#certificateFile").val(data.certificate);
					}
					if(data.certificate2){
						$("#certificateFile2").val(data.certificate);
					}
					self.saveData();
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
			//params.helpAgreement = $("#certificateFile2").val();//其他协议书
			dataModoule.updateLinkInfo(params, function(res){
				//更新缓存数据
				/*var datas = comm.getCache("companyInfo", "entAllInfo");
				datas.contactAddr = params.contactAddr;
				datas.postCode = params.postCode;
				datas.contactEmail = params.contactEmail;
				datas.contactProxy = params.contactProxy;
				datas.asEntStatusInfo.isAuthEmail=0;*/
				//删除缓存
				comm.delCache("companyInfo", "entAllInfo");
				comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
					$('#lxxx').click();
				}});
			});
		}
	};
	 
	return self;
	
}); 

 