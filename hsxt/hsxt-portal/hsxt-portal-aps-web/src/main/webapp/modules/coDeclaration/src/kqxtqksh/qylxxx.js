define(['text!coDeclarationTpl/kqxtqksh/qylxxx.html',
        'text!coDeclarationTpl/kqxtqksh/confirm_dialog.html',
        'coDeclarationDat/kqxtqksh/qylxxx',
		'coDeclarationLan'], function(qylxxxTpl, confirm_dialogTpl, dataModoule){
	var self = {
		showPage : function(){
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			
			$("#qylxxxTpl1").show();
			$("#qylxxxTpl2").hide();
			
			//点击查看页面修改按钮
			$('#qylxxx_modify').click(function(){
				$("#qylxxxTpl1").hide();
				$("#qylxxxTpl2").show();
			});
			//点击查看页面取消按钮
			$('#qylxxx_cancel').triggerWith('#'+$("#menuName").val());
			//点击修改页面提交按钮
			$('#qylxxx_xg_btn').click(function(){
				self.initChangeData();
			});
			//点击修改页面取消按钮
			$('#back_qylxxx').click(function(){
				$("#qylxxxTpl1").show();
				$("#qylxxxTpl2").hide();
			});
		},
		picPreview : function(){
			//绑定图片预览
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#helpxys', $('#helpxys').children().first().attr('src'));
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate'], ['#bimg'], 81, 52);
			comm.initUploadBtn(['#hlepFileBtn'], ['#helpxys'], 81, 52);
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findLinkmanByApplyId({"applyId": $("#applyId").val()}, function(res){
				res.data.link.venBefFlag = res.data.venBefFlag;
				self.initShowForm(res.data.link);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 */
		initShowForm : function(data){
			comm.delCache('coDeclaration', 'kqxtyw-qylxxx-info');
			comm.setCache('coDeclaration', 'kqxtyw-qylxxx-info', data);
			var data = comm.getCache('coDeclaration', 'kqxtyw-qylxxx-info');
			if(data){
				$('#infobox').html(_.template(qylxxxTpl, data));
				if(data.certificateFile){
					comm.initPicPreview("#img", data.certificateFile, "");
					comm.initPicPreview("#bimg", data.certificateFile, "");
					$("#img").attr("src", comm.getFsServerUrl(data.certificateFile));
					$("#bimg").children().first().attr("src", comm.getFsServerUrl(data.certificateFile));
				}
				if(data.protocolAptitude&&data.protocolAptitude.fileId){
					comm.initPicPreview("#helpxys2", data.protocolAptitude.fileId, "");
					comm.initPicPreview("#helpxys", data.protocolAptitude.fileId, "");
					$("#helpxys2").attr("src", comm.getFsServerUrl(data.protocolAptitude.fileId));
					$("#helpxys").children().first().attr("src", comm.getFsServerUrl(data.protocolAptitude.fileId));
				}
			}
			self.initForm();
			self.picPreview();
			//绑定下载按钮
			cacheUtil.findDocList(function(map){
				comm.initDownload("#certificateFileId", map.comList, '1007');
				comm.initDownload("#hlepFileId", map.comList, '1006');
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qylxxx_form").validate({
				 rules : {
					linkman : {
						required : true,
						rangelength:[2, 20]
					},
					address : {
						rangelength : [2, 128]
					},
					mobile : {
						required : true,
						mobileNo : true
					},
					zipCode : {
						zipCode : true
					},
					email : {
						email2 : true
					}
				},
				messages : {
					linkman : {
						required : comm.lang("coDeclaration")[36014],
						rangelength : comm.lang("coDeclaration")[36015]
					},
					address : {
						rangelength : comm.lang("coDeclaration")[36016]
					},
					mobile : {
						required : comm.lang("coDeclaration")[36017],
						mobileNo : comm.lang("coDeclaration")[36018]
					},
					zipCode : {
						zipCode : comm.lang("coDeclaration")[36019]
					},
					email : {
						email2 : comm.lang("coDeclaration")[36020]
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
			//检查附件信息，如果存在附件信息则不检查文件必填
			var cerFile = $("#certificateFile").val();
			if(cerFile != ""){
				validate.settings.rules.certificate = {required : false};
				validate.settings.messages.certificate = {required : ''};
			}else{
				validate.settings.rules.certificate = {required : true};
				validate.settings.messages.certificate = {required : comm.lang("coDeclaration")[36021]};
			}
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#confirm_dialog_form").validate({
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
		 * 初始化提交页面
		 */
		initChangeData : function(){
			if(!self.validateData().form()){
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('coDeclaration', 'kqxtyw-qylxxx-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			for(var key = 0;key < ckArray.length; key++){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					ndata[ckArray[key].name] = ckArray[key].value;
				}
			}
			
			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			//处理联系人授权委托书
			if(comm.isNotEmpty($("#certificate").val())){
				var fId = $("#certificateFile").val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old", fileId: fId, title:"修改前联系人授权委托书"});
				}
				newArray.push({picId:"#prv-pic-new", fileId: '#bimg', title:"修改后联系人授权委托书"});
				trs+="<tr><td class=\"view_item\">联系人授权委托书</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			//处理创业帮扶协议书
			if(comm.isNotEmpty($("#hlepFileBtn").val())){
				var fId = $("#hlepFile").val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new1\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old1\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old1", fileId: fId, title:"修改前创业帮扶协议书"});
				}
				newArray.push({picId:"#prv-pic-new1", fileId: '#helpxys', title:"修改后创业帮扶协议书"});
				trs+="<tr><td class=\"view_item\">创业帮扶协议书</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
			if(trs == ""){
				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
				return;
			}
			//提交
			$('#qylxxx_dialog > p').html(_.template(confirm_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
			
			for(var key in oldArray){
				comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
			}
			for(var key in newArray){
				comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
			}
			self.initVerificationMode();
			$('#qylxxx_dialog').dialog({
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
	 						ndata.verifyDouble = "true";
	 						ndata.dblOptCustId = verifyRes.data;
	 						ndata.optRemark = $("#viewMark").val();
	 						ndata.changeContent = JSON.stringify(chg);
	 						if(comm.isNotEmpty($("#certificate").val())){
	 							if(comm.isNotEmpty($("#certificateFile").val())){
	 								delFileIds[0] = $("#certificateFile").val();
	 							}
	 							ids[0] = "certificate";
	 						}
	 						if(comm.isNotEmpty($("#hlepFileBtn").val())){
	 							if(comm.isNotEmpty($("#hlepFile").val())){
	 								delFileIds[1] = $("#hlepFile").val();
	 							}
	 							ids[1] = "hlepFileBtn";
	 						}
							if(ids.length == 0){
								self.saveData(ndata);
							}else{
								comm.uploadFile(null, ids, function(data){
									if(data.certificate){
										chg.certificateFile = {"old":$("#certificateFile").val(), "new":data.certificate};
										$("#certificateFile").val(data.certificate);
										ndata.certificateFile = data.certificate;
									}
									if(data.hlepFileBtn){
	 									chg.hlepFileId = {"old":$("#hlepFile").val(), "new":data.hlepFileBtn};
	 									$("#hlepFile").val(data.hlepFileBtn);
	 									ndata.venBefProtocolFileId = data.hlepFileBtn;
	 									ndata.venBefFlag="true";
	 								}
									self.saveData(ndata);
								}, function(err){
									self.picPreview();
								}, {delFileIds : delFileIds});
							}
						});
					},
					'取消':function(){
						$(this).dialog('destroy');
					}
				}
			});	
		},
		
		/**
		 * 保存数据
		 * @param ndata 注册信息
		 */
		saveData : function(ndata){
			delete ndata.protocolAptitude;
			dataModoule.saveLinkInfo(ndata, function(res){
				comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
					$('#qylxxx_dialog').dialog('destroy');
					//self.initShowForm(ndata, true);
					$("#ckxq_qylxxx").click();
				}});
			});
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
	};
	return self;
});