define(['text!coDeclarationTpl/qysbfk/ckxq/qylxxx.html',
		'text!coDeclarationTpl/qysbfk/ckxq/qylxxx_dialog.html',
        'coDeclarationDat/qysbfk/ckxq/qylxxx',
		'coDeclarationLan'],function(qylxxxTpl ,qylxxx_dialogTpl, dataModoule){
	var cacheData = null;
	return qysbfk_qylxxx_services = {	 	
		showPage : function(){
		 	 this.initData();
		 	 this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#ckxq_xg').css('display','');		 
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		if($('#ckxq_xg').text() == '保　存' ){		 			 			
		 			$('#ckxq_xg').text('修　改');
		 			$("#skqr_tj").show();
		 			//$('#qylxxx_1').removeClass('none');
		 			//$('#qylxxx_2').addClass('none')  ;
		 			self.initShowForm(cacheData, false);
		 		} else {
		 			$('#cx_content_list').removeClass('none');
		 			$('#cx_content_detail').addClass('none');
		 		}		 		
		 	});
		 	//修改
		 	$('#ckxq_xg').click(function(){	
		 		if ($(this).text() !='保　存' )	{
		 			$('#qylxxx_1').addClass('none');
		 			$('#qylxxx_2').removeClass('none');
		 			$(this).text('保　存');
		 			$("#skqr_tj").hide();
		 		} else {
		 			self.initChangeData();
		 		}		 		
		 	}); 
		},
		/**
		 * /绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
				comm.initTmpPicPreview('#cimg', $('#cimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate', '#protocolApt'], ['#bimg', '#cimg'], 81, 52);
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findLinkmanByApplyId({"applyId": $("#editApplyId").val()}, function(res){
				cacheData = res.data.link;
				self.initShowForm(res.data.link, false);
				if(res.data.venBefFlag == "true"){
					qysbfk_qylxxx_services.venBefFlag = "true";
					$('#venBef_tr').show();
					$('#venBef_ul').show();
				}else{
					qysbfk_qylxxx_services.venBefFlag = "false";
					$('#venBef_tr').hide();
					$('#venBef_ul').hide();
				}
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 * @param isCallBack 是否是回调成功
		 */
		initShowForm : function(data, isCallBack){
			comm.delCache('coDeclaration', 'qysbfk-qylxxx-info');
			comm.setCache('coDeclaration', 'qysbfk-qylxxx-info', data);
			var data = comm.getCache('coDeclaration', 'qysbfk-qylxxx-info');
			if(data){
				$('#ckxq_view').html('').append(_.template(qylxxxTpl, data));
				if(data.certificateFile){
					comm.initPicPreview("#img", data.certificateFile, "");
					comm.initPicPreview("#bimg", data.certificateFile, "");
					$("#img").attr("src", comm.getFsServerUrl(data.certificateFile));
					$("#bimg").children().first().attr("src", comm.getFsServerUrl(data.certificateFile));
				}
				if(data.protocolAptitude && data.protocolAptitude.fileId){
					comm.initPicPreview("#img_1", data.protocolAptitude.fileId, "");
					comm.initPicPreview("#cimg", data.protocolAptitude.fileId, "");
					$("#img_1").attr("src", comm.getFsServerUrl(data.protocolAptitude.fileId));
					$("#cimg").children().first().attr("src", comm.getFsServerUrl(data.protocolAptitude.fileId));
					qysbfk_qylxxx_services.venBefProtocolAptId = comm.removeNull(data.protocolAptitude.aptitudeId);
					qysbfk_qylxxx_services.venBefProtocolFileId = comm.removeNull(data.protocolAptitude.fileId);
				}
			}
			if(qysbfk_qylxxx_services.venBefFlag == "true"){
				$('#venBef_tr').show();
				$('#venBef_ul').show();
			}
			if(isCallBack){
				$('#ckxq_xg').text('修改');
				$("#skqr_tj").show();
				//$('#ckxq_xg').click();
			}
			//绑定下载按钮
		 	cacheUtil.findDocList(function(map){
	 			comm.initDownload("#certificateFileId", map.comList, '1007');
		 	});
			this.picPreview();
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
						required : false,
						rangelength : [2, 128]
					},
					webSite : {
						required : false,
						isUrl : true
					},
					phone : {
						required : false,
						telphone : true
					},
					job : {
						required : false,
						job : true
					},
					mobile : {
						required : true,
						mobileNo : true
					},
					zipCode : {
						required : false,
						zipCode : true
					},
					email : {
						required : false,
						email2 : true
					},
					fax : {
						required : false,
						fax : true
					},
					qq : {
						required : false,
						qq : true
					}
				},
				messages : {
					linkman : {
						required : comm.lang("coDeclaration")[32645],
						rangelength : comm.lang("coDeclaration")[32646]
					},
					address : {
						required : comm.lang("coDeclaration")[32647],
						rangelength : comm.lang("coDeclaration")[32648]
					},
					webSite : {
						required : comm.lang("coDeclaration")[32649],
						isUrl : comm.lang("coDeclaration")[32650]
					},
					phone : {
						required : comm.lang("coDeclaration")[32510],
						telphone : comm.lang("coDeclaration")[32511]
					},
					job : {
						required : comm.lang("coDeclaration")[32651],
						job : comm.lang("coDeclaration")[32652]
					},
					mobile : {
						required : comm.lang("coDeclaration")[32653],
						mobileNo : comm.lang("coDeclaration")[32654]
					},
					zipCode : {
						required : comm.lang("coDeclaration")[32512],
						zipCode : comm.lang("coDeclaration")[32513]
					},
					email : {
						required : comm.lang("coDeclaration")[32655],
						email2 : comm.lang("coDeclaration")[32656]
					},
					fax : {
						required : comm.lang("coDeclaration")[32514],
						fax : comm.lang("coDeclaration")[32515]
					},
					qq : {
						required : comm.lang("coDeclaration")[32657],
						qq : comm.lang("coDeclaration")[32658]
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
			 
			 /** by wangjg 2016-03-08 注释联系委托书验证 */
			//检查附件信息，如果存在附件信息则不检查文件必填
			/*var cerFile = $("#certificateFile").val();
			if(cerFile != ""){
				validate.settings.rules.certificate = {required : false};
				validate.settings.messages.certificate = {required : comm.lang("coDeclaration")[32660]};
			}else{
				validate.settings.rules.certificate = {required : true};
				validate.settings.messages.certificate = {required : comm.lang("coDeclaration")[32660]};
			}*/
			/** by wangjg 2016-03-08 注释联系委托书验证 */
			
			if(comm.removeNull(qysbfk_qylxxx_services.venBefFlag) == "true"){
				if(comm.removeNull(qysbfk_qylxxx_services.venBefProtocolFileId) != ""){
					validate.settings.rules.protocolApt = {required : false};
					validate.settings.messages.protocolApt = {required : ''};
				}else{
					validate.settings.rules.protocolApt = {required : true};
					validate.settings.messages.protocolApt = {required : comm.lang("coDeclaration")[32682]};
				}
			}
			 
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#qylxxx_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300],
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[32599],
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
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('coDeclaration', 'qysbfk-qylxxx-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			for(var key in ckArray){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					ndata[ckArray[key].name] = ckArray[key].value;
				}
			}
			
			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			//处理联系人授权委托书
			if($("#certificate").val() != ""){
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
			
			//处理创业帮扶协议
			if($("#protocolApt").val() != ""){
				var fId = qysbfk_qylxxx_services.venBefProtocolFileId;
				var newPic = "<a href=\"#\" id=\"prv-pic-new_02\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old_02\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old_02", fileId: fId, title:"修改前创业帮扶协议"});
				}
				newArray.push({picId:"#prv-pic-new_02", fileId: '#cimg', title:"修改后创业帮扶协议"});
				trs+="<tr><td class=\"view_item\">创业帮扶协议</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
 			if(trs == ""){
 				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
 				return;
 			}
 			//提交
 			$('#qylxxx_dialog > p').html(_.template(qylxxx_dialogTpl));
 			$('#copTable tr:eq(1)').before(trs);
 			
 			for(var key in oldArray){
				comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
			}
			for(var key in newArray){
				comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
			}
 			$('#qylxxx_dialog').dialog({
 				width:800,
 				buttons:{
 					'确定':function(){
 						if(!self.validateViewMarkData().form()){
 							return;
 						}
 						var ids = [];
 						var delFileIds = [];
 						if($("#certificate").val() != ""){
 							if($("#certificateFile").val() != ""){
 								delFileIds[delFileIds.length] = $("#certificateFile").val();
 							}
 							ids[ids.length] = "certificate";
 						}
						if($("#protocolApt").val() != ""){
							if(comm.removeNull(qysbfk_qylxxx_services.venBefProtocolFileId) != ""){
								delFileIds[delFileIds.length] = qysbfk_qylxxx_services.venBefProtocolFileId;
							}
							ids[ids.length] = "protocolApt";
						}
 						if(ids.length == 0){
 							self.saveData(ndata, chg);
 						}else{
 							comm.uploadFile(null, ids, function(data){
 								if(data.certificate){
 									chg.certificateFile = {"old":$("#certificateFile").val(), "new":data.certificate};
 									$("#certificateFile").val(data.certificate);
 									ndata.certificateFile = data.certificate;
 								}
								if(data.protocolApt){
									chg.protocolApt = {"old":qysbfk_qylxxx_services.venBefProtocolFileId, "new":data.protocolApt};
									qysbfk_qylxxx_services.venBefProtocolFileId = data.protocolApt;
								}
								self.saveData(ndata, chg);
 							}, function(err){
 								self.picPreview();
 							}, {delFileIds : delFileIds});
 						}
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
		saveData : function(ndata, chg){
			var self = this;
			delete ndata.protocolAptitude;
			ndata.optRemark = $("#viewMark").val();
			ndata.venBefFlag = comm.removeNull(qysbfk_qylxxx_services.venBefFlag);
			ndata.venBefProtocolAptId = comm.removeNull(qysbfk_qylxxx_services.venBefProtocolAptId);
			ndata.venBefProtocolFileId = comm.removeNull(qysbfk_qylxxx_services.venBefProtocolFileId);
			ndata.changeContent = JSON.stringify(chg);
			dataModoule.saveLinkInfo(ndata, function(res){
				if(res && res.data && res.data.protocolAptitude && res.data.protocolAptitude.aptitudeId){
					qysbfk_qylxxx_services.venBefProtocolAptId = res.data.protocolAptitude.aptitudeId;
					ndata.protocolAptitude = {};
					ndata.protocolAptitude.aptitudeId = res.data.protocolAptitude.aptitudeId;
					ndata.protocolAptitude.fileId = res.data.protocolAptitude.fileId;
				}
				if(res && res.data){
					cacheData = res.data;
				}
				comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
					$('#qylxxx_dialog').dialog('destroy');
					self.initShowForm(ndata, true);
				}});
			});
		}
	}
}); 
