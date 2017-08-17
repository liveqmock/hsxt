

define(['text!infoManageTpl/glqyxxgl/qylxxx.html',
		'text!infoManageTpl/glqyxxgl/qylxxx_xg.html',
		'text!infoManageTpl/glqyxxgl/qylxxx_xg_dialog.html',
		'text!infoManageTpl/glqyxxgl/qylxxx_xg_sjyz_dialog.html',
		'text!infoManageTpl/glqyxxgl/qylxxx_xg_yxyz_dialog.html',
		'infoManageDat/glqyxxgl/qylxxx',
		'infoManageDat/infoManage',
		'infoManageLan'
		], function(qylxxxTpl, qylxxx_xgTpl, qylxxx_xg_dialogTpl, qylxxx_xg_sjyz_dialogTpl, qylxxx_xg_yxyz_dialogTpl,dataModoule,infoManage){
	var custType = '';
	var startResType = '';
	var self =  {
		showPage : function(obj){
			this.initData(obj);
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
			$('#qylxxx_cancel').click(function(){
				$("#glqyxxwhTpl").removeClass("none");
				$('#glqyxxwh_operation').addClass("none").html("");
				comm.liActive($('#glqyxxwh'),'#xgqyxx');
			});
			//点击修改页面提交按钮
			$('#qylxxx_xg_btn').click(function(){
				self.initChangeData();
			});
			//点击修改页面取消按钮
			$('#back_qylxxx').click(function(){
				$("#qylxxxTpl1").show();
				$("#qylxxxTpl2").hide();
			});
			
			//邮箱验证
			$('#btn_lxxx_yz').click(function(){
				var params = {};
				var email = $("#lxxx_contactEmail").text();
				if(email == null || email == ""){
					//comm.alert('邮箱地址为空，请修改邮箱地址。');
					return;
				}
				params.email = email;
				var hideEmail = email.substr(0, 2) + "****" + email.substr(email.indexOf("@"), email.length);
				params.userName = comm.getSysCookie('custName');
				params.entResNo = comm.getSysCookie('pointNo');
				infoManage.validEmail(params,function(){
					comm.alert('系统已发送验证邮件到'+hideEmail+'，请及时登录邮箱完成绑定。');
				});
			});
		},
		picPreview : function(){
			//绑定图片预览
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate','#certificate2'], ['#bimg','#bimg2'], 81, 52);
			//加载下载模板
			cacheUtil.findDocList(function(map){
				comm.initDownload("#certificateFileId", map.comList, '1007');
				//目前还没有【创业帮扶协议】文档模板
//				comm.initDownload("#certificateFileId2", map.comList, '1012');
			});
		},
		/**
		 * 绑定图片预览
		 */
		initPicPreview : function(){
			var btnIds = ['#certificate','#certificate2'];
			var imgIds = ['#bimg','#bimg2','#aimg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		/**
		 * 初始化数据
		 */
		initData : function(obj){
			dataModoule.searchEntExtInfo({"belongEntCustId": obj.entCustId}, function(res){
				self.initShowForm(res.data);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 */
		initShowForm : function(data){
			comm.delCache('infoManage', 'glqyxxgl-qylxxx-info');
			comm.setCache('infoManage', 'glqyxxgl-qylxxx-info', data);
			var data = comm.getCache('infoManage', 'glqyxxgl-qylxxx-info');
			if(data){
				$('#infobox').html(_.template(qylxxxTpl, data)).append(_.template(qylxxx_xgTpl, data));
				if(data.contactProxy){
					comm.initPicPreview("#img-1", data.contactProxy, "");
					comm.initPicPreview("#img-2", data.contactProxy, "");
					$("#aimg").children().first().attr("src", comm.getFsServerUrl(data.contactProxy));
					$("#bimg").children().first().attr("src", comm.getFsServerUrl(data.contactProxy));
				}
								
				if(data){
					var temp_custType = data.custType;
					var temp_startResType = data.startResType;
					custType = temp_custType;
					startResType = temp_startResType;
					if(3 == temp_custType && 2 == temp_startResType){
						var helpAgreement = data.helpAgreement;
						if(null != helpAgreement && '' != helpAgreement){
							comm.initPicPreview("#other_protocol", helpAgreement, "");
							comm.initPicPreview("#bimg2", helpAgreement, "");
							$("#other_protocol").attr("src", comm.getFsServerUrl(helpAgreement));
							$("#other_protocol2").attr("src", comm.getFsServerUrl(helpAgreement));
						}
					}
				}
			}
			self.initForm();
			self.picPreview();
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qylxxx_form").validate({
				 rules : {
					 contactPerson : {
						required : true,
						rangelength:[2, 20]
					},
					contactAddr : {
						rangelength : [2, 128]
					},
					contactPhone : {
						required : true,
						mobileNo : true
					},
					postCode : {
						zipCode : true
					},
					contactEmail : {
						email : true
					},
					certificate : {
						required : true
					},
					certificate2 : {
						required : true
					}
				},
				messages : {
					contactPerson : {
						required : comm.lang("infoManage")[36014],
						rangelength : comm.lang("infoManage")[36015]
					},
					contactAddr : {
						rangelength : comm.lang("infoManage")[36016]
					},
					contactPhone : {
						required : comm.lang("infoManage")[36017],
						mobileNo : comm.lang("infoManage")[36018]
					},
					postCode : {
						zipCode : comm.lang("infoManage")[36019]
					},
					contactEmail : {
						email : comm.lang("infoManage")[36020]
					},
					certificate : {
						required : comm.lang("infoManage")[31058]
					},
					certificate2 : {
						required : comm.lang("infoManage")[36020]
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
			var cerFile = $("#contactProxy").val();
			if(comm.isNotEmpty(cerFile)){
				validate.settings.rules.certificate = {required : false};
				validate.settings.messages.certificate = {required : ''};
			}
			var cerFile2 = $("#helpAgreement").val();
			if(comm.isNotEmpty(cerFile2)){
				validate.settings.rules.certificate2 = {required : false};
				validate.settings.messages.certificate2 = {required : ''};
			}
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			var validate = $("#confirm_dialog_form").validate({
				rules : {
					verificationMode : {
						required : true
					},
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : true
					},
					doublePassword : {
						required : true
					},
				},
				messages : {
					verificationMode : {
						required : comm.lang("infoManage")[36050]
					},
					viewMark : {
						rangelength : comm.lang("infoManage")[36006]
					},
					doubleUserName : {
						required : comm.lang("infoManage")[36047]
					},
					doublePassword : {
						required : comm.lang("infoManage")[36048]
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
			
			var type = $("#verificationMode").attr("optionvalue");
			if(type == '1'){
				validate.settings.rules.doubleUserName = {required : true};
				validate.settings.rules.doublePassword = {required : true};
			}else{
				validate.settings.rules.doubleUserName = {required : false};
				validate.settings.rules.doublePassword = {required : false};
			}
			
			return validate;
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
			var odata = comm.getCache('infoManage', 'glqyxxgl-qylxxx-info');
			var ndatas = comm.cloneJSON(odata);
			var ndata = {};
			var trs = "";
			var chg = [];
			for(var key = 0;key < ckArray.length; key++){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
//					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					chg.push({"logType":4,"updateField":ckArray[key].name,"updateFieldName":ckArray[key].desc,"updateValueOld":comm.removeNull(odata[ckArray[key].name]), "updateValueNew":ckArray[key].value});
					ndata[ckArray[key].name] = ckArray[key].value;
					ndatas[ckArray[key].name] = ckArray[key].value;
				}
			}
			
			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			//处理联系人授权委托书
			if($("#certificate").val() != ""){
				var fId = $("#contactProxy").val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old", fileId: fId, title:"修改前联系人授权委托书"});
				}
				newArray.push({picId:"#prv-pic-new", fileId: '#bimg', title:"修改后联系人授权委托书"});
				trs+="<tr><td class=\"view_item\">联系人授权委托书</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
			
			if(3 == custType && 2 == startResType){
				//处理创业帮扶协议
				if($("#certificate2").val() != ""){
					var fId = $("#helpAgreement").val();
					var newPic1 = "<a href=\"#\" id=\"prv-pic-new1\" class=\"blue\">查看</a>";
					var oldPic1 = "无";
					if(fId != ""){
						oldPic1 = "<a href=\"#\" id=\"prv-pic-old1\" class=\"blue\">查看</a>";
						oldArray.push({picId:"#prv-pic-old1", fileId: fId, title:"修改前创业帮扶协议"});
					}
					newArray.push({picId:"#prv-pic-new1", fileId: '#bimg2', title:"修改后创业帮扶协议"});
					trs+="<tr><td class=\"view_item\">创业帮扶协议</td><td class=\"view_text\">"+oldPic1+"</td><td class=\"view_text\">"+newPic1+"</td></tr>";
				}
			}
			
			
			
			
			
			if(trs == ""){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return;
			}
			//提交
			$('#qylxxx_dialog > p').html(_.template(qylxxx_xg_dialogTpl));
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
				height:500,
				buttons:{
					'确定':function(){
						if(!self.validateViewMarkData().form()){
							return;
						}
						var verificationMode = $("#verificationMode").attr('optionValue');
						if(verificationMode != "1"){
							comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
							return ;
						}
						//验证双签
//						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							var ids = [];
							var delFileIds = [];
							if(comm.isNotEmpty($("#certificate").val())){
								if(comm.isNotEmpty($("#contactProxy").val())){
									delFileIds[delFileIds.length] = $("#contactProxy").val();
								}
								ids[ids.length] = "certificate";
							}
							if(comm.isNotEmpty($("#certificate2").val())){
								if(comm.isNotEmpty($("#helpAgreement").val())){
									delFileIds[delFileIds.length] = $("#helpAgreement").val();
								}
								ids[ids.length] = "certificate2";
							}
							
							
							if(ids.length == 0){
								self.saveData(ndata,ndatas, chg);
							}else{
								comm.uploadFile(null, ids, function(data){
									if(data.certificate){
										chg.push({"logType":4,"updateField":'contactProxy',"updateFieldName":'联系人授权委托书',"updateValueOld":odata.contactProxy, "updateValueNew":data.certificate});
										$("#contactProxy").val(data.certificate);
										ndata.contactProxy = data.certificate;
									}
									if(data.certificate2){
										chg.push({"logType":4,"updateField":'helpAgreement',"updateFieldName":'创业帮扶协议',"updateValueOld":odata.helpAgreement, "updateValueNew":data.certificate2});
										$("#helpAgreement").val(data.certificate2);
										ndata.helpAgreement = data.certificate2;
									}
									if(data.certificate || data.certificate2){
										self.saveData(ndata,ndatas, chg);
									}
								}, function(err){
									self.picPreview();
								}, {delFileIds : delFileIds});
							}
//						});
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
		saveData : function(ndata,ndatas, chg){
			var self = this;
			comm.getToken(null,function(resp){
				if(resp){
					ndata.dbUserName = $("#doubleUserName").val();
					ndata.loginPwd = comm.encrypt($('#doublePassword').val(),resp.data);
					ndata.secretKey = resp.data;
					ndata.optRemark = $("#viewMark").val();
					ndata.changeContent = JSON.stringify(chg);
					ndata.belongEntCustId = ndatas.entCustId;
					ndata.typeEum = "4";
					dataModoule.updateLinkInfo(ndata, function(res){
						comm.alert({content:comm.lang("infoManage")[22000], callOk:function(){
							$('#qylxxx_dialog').dialog('destroy');
//							self.initShowForm(ndatas);
//							self.picPreview();
							$("#qylxxx").click();
						}});
					});
				}
			});
			
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("infoManage").verificationMode, null, '1').change(function(e){
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
			
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
		}
	};
	return self
});