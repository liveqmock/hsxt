define(['text!infoManageTpl/glqyxxgl/qygsdjxx.html',
		'text!infoManageTpl/glqyxxgl/qygsdjxx_xg.html',
		'text!infoManageTpl/glqyxxgl/qygsdjxx_xg_dialog.html',
		'infoManageDat/glqyxxgl/qygsdjxx',
		'infoManageLan'
		], function(qygsdjxxTpl, qygsdjxx_xgTpl, qygsdjxx_xg_dialogTpl,infoManage){
	var self = {
		showPage : function(obj){
			
			this.initData(obj);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			
			$("#qygsdjxxTpl1").show();
			$("#qygsdjxxTpl2").hide();
			
			//点击查看页面修改按钮
			$('#qygsdjxx_modify').click(function(){
				$("#qygsdjxxTpl1").hide();
				$("#qygsdjxxTpl2").show();
			});
			//点击查看页面取消按钮
			$('#qygsdjxx_cancel').click(function(){
				$("#glqyxxwhTpl").removeClass("none");
				$('#glqyxxwh_operation').addClass("none").html("");
				comm.liActive($('#glqyxxwh'),'#xgqyxx');
			});
			//点击修改页面提交按钮
			$('#qygsdjxx_xg_btn').click(function(){
				self.initChangeData();
			});
			//点击修改页面取消按钮
			$('#back_qygsdjxx').click(function(){
				$("#qygsdjxxTpl1").show();
				$("#qygsdjxxTpl2").hide();
			});
			
			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(obj){
			var self = this;
			infoManage.searchEntExtInfo({"belongEntCustId": obj.entCustId}, function(res){
				self.initShowForm(res.data);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 */
		initShowForm : function(data){
			var self = this;
			comm.delCache('infoManage', 'glqyxxgl-qygsdjxx-info');
			comm.setCache('infoManage', 'glqyxxgl-qygsdjxx-info', data);
			if(data){
//				$('#infobox').html(_.template(qygsdjxxTpl, data));
				$('#infobox').html(_.template(qygsdjxxTpl, data)).append(_.template(qygsdjxx_xgTpl, data));
					var busiLicenseImg = data.busiLicenseImg;
					if(null != busiLicenseImg && '' != busiLicenseImg){
						comm.initPicPreview("#busi_license_img", busiLicenseImg, "");
						comm.initPicPreview("#bimg", busiLicenseImg, "");
						$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
						$("#busi_license_img2").attr("src", comm.getFsServerUrl(busiLicenseImg));
					}
				
				//成立日期
				$("#buildDate").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate()
				});
				
				$("#buildDate").val((data.buildDate?data.buildDate:""));
				$('#legalCreTypeText').html(comm.getNameByEnumId(data.credentialType, comm.lang("infoManage").idCardTypeEnum));
				comm.initSelect('#credentialType', comm.lang("infoManage").idCardTypeEnum, 189, data.credentialType).change(function(){
					self.validateData();
					self.changeLegalCreNo();
				});
			}else{
				//初始化证件类型
				comm.initSelect('#credentialType', comm.lang("infoManage").idCardTypeEnum, 189).change(function(){
					self.validateData();
					self.changeLegalCreNo();
				});
			}
			self.changeLegalCreNo();
			self.initForm();
			self.picPreview();
		},
		
		picPreview : function(){
			//绑定图片预览
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate'], ['#bimg'], 81, 52);
			//加载下载模板
//			cacheUtil.findDocList(function(map){
//				comm.initDownload("#certificateFileId", map.comList, '1007');
//			});
		},
		
		/**
		 * 绑定图片预览
		 */
		initPicPreview : function(){
			var btnIds = ['#certificate'];
			var imgIds = ['#bimg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		
		/**
		 * 禁用与启用法人证件号码输入框
		 */
		changeLegalCreNo : function(){
			var legalType = $("#credentialType").attr('optionValue');
			if(legalType == ""){
				$("#legalPersonId").attr('disabled', true);
				$("#legalPersonId").attr('title', "请先选择法人代表证件类型.");
			}else{
				$("#legalPersonId").attr('disabled', false);
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qygsdjxx_form").validate({
				 rules : {
					 entCustName : {
						required : true,
						rangelength:[2, 64]
					},
					legalPerson : {
						required : true,
						rangelength:[2, 20]
					},
					custType : {
						rangelength:[2, 20]
					},
					
					buildDate : {
						date : true
					},
					entRegAddr : {
						required : true,
						rangelength:[2, 128]
					},
					busiLicenseNo : {
						required : true,
						businessLicence:true
					},
					businessScope : {
						rangelength:[0, 300]
					},
					endDate : {
						rangelength:[0, 50]
					},
				},
				messages : {
					entCustName : {
						required : comm.lang("infoManage")[36023],
						rangelength:comm.lang("infoManage")[36024]
					},
					legalPerson : {
						required :  comm.lang("infoManage")[36025],
						rangelength:comm.lang("infoManage")[36026]
					},
					custType : {
						rangelength:comm.lang("infoManage")[36027]
					},
					
					buildDate : {
						date : comm.lang("infoManage")[36029]
					},
					entRegAddr : {
						required : comm.lang("infoManage")[36030],
						rangelength:comm.lang("infoManage")[36031]
					},
					busiLicenseNo : {
						required : comm.lang("infoManage")[36032],
						businessLicence:comm.lang("infoManage")[36033]
					},
					
					businessScope : {
						rangelength: comm.lang("infoManage")[36036]
					},
					endDate : {
						rangelength:comm.lang("infoManage")[36037]
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
			var legalType = $("#credentialType").attr('optionValue');
			if(legalType == "1"){
				validate.settings.rules.legalPersonId = {required : false, IDCard : true};
				validate.settings.messages.legalPersonId = {required : comm.lang("infoManage")[36040],	IDCard : comm.lang("infoManage")[36038]};
			}else if(legalType == "2"){
				validate.settings.rules.legalPersonId = {required : false, passport : true};
				validate.settings.messages.legalPersonId = {required : comm.lang("infoManage")[36040],	passport : comm.lang("infoManage")[36039]};
			}else{
				validate.settings.rules.legalPersonId = {required : false};
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
						required : false
					},
					doublePassword : {
						required : false
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
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('infoManage', 'glqyxxgl-qygsdjxx-info');
			var ndata = {};
			var ndatas = comm.cloneJSON(odata);
			var trs = "";
			var chg = [];
			for(var key = 0; key < ckArray.length; key++){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
//					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					chg.push({"logType":3,"updateField":ckArray[key].name,"updateFieldName":ckArray[key].desc,"updateValueOld":comm.removeNull(odata[ckArray[key].name]), "updateValueNew":ckArray[key].value});
					ndata[ckArray[key].name] = ckArray[key].value;
					ndatas[ckArray[key].name] = ckArray[key].value;
				}
			}
			
			//比较证件类型
			var oType = comm.removeNull(odata.credentialType);
			var nType = $("#credentialType").attr('optionValue');
			
			
//			if(oType != nType){
//				var oDesc = comm.getNameByEnumId(oType, comm.lang("infoManage").idCardTypeEnum);
//				var nDesc = comm.getNameByEnumId(nType, comm.lang("infoManage").idCardTypeEnum);
//				trs+="<tr><td class=\"view_item\">法人代表证件类型</td><td class=\"view_text\">"+oDesc+"</td><td class=\"view_text\">"+nDesc+"</td></tr>";
//				chg.credentialType = {"old":oType, "new":nType};
//				chg.push({"logType":3,"updateField":'credentialType',"updateFieldName":'证件类型',"updateValueOld":oType, "updateValueNew":nType});
//				ndata.credentialType = nType;
//				ndatas.credentialType = nType;
//			}
			
			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			//处理营业执照扫描件
			if($("#certificate").val() != ""){
				var fId = $("#busiLicenseImg").val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old", fileId: fId, title:"修改前营业执照扫描件"});
				}
				newArray.push({picId:"#prv-pic-new", fileId: '#bimg', title:"修改后营业执照扫描件"});
				trs+="<tr><td class=\"view_item\">营业执照扫描件</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
			if(trs == ""){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return;
			}
			
			//提交
			$('#qygsdjxx_dialog > p').html(_.template(qygsdjxx_xg_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
			for(var key in oldArray){
				comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
			}
			for(var key in newArray){
				comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
			}
			self.initVerificationMode();
			$('#qygsdjxx_dialog').dialog({
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
						
						
						var ids = [];
						var delFileIds = [];
						if($("#certificate").val() != ""){
							if($("#busiLicenseImg").val() != ""){
								delFileIds[delFileIds.length] = $("#busiLicenseImg").val();
							}
							ids[ids.length] = "certificate";
						}
						
						if(ids.length == 0){
							self.saveData(ndata,ndatas, chg);
						}else{
							comm.uploadFile(null, ids, function(data){
								if(data.certificate){
									chg.push({"logType":4,"updateField":'busiLicenseImg',"updateFieldName":'营业执照扫描件',"updateValueOld":odata.busiLicenseImg, "updateValueNew":data.certificate});
									$("#busiLicenseImg").val(data.certificate);
									ndata.busiLicenseImg = data.certificate;
									self.saveData(ndata,ndatas, chg);
								}
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
		
		saveData : function(ndata,ndatas, chg){
			comm.getToken(null,function(resp){
				if(resp){
					ndata.dbUserName = $("#doubleUserName").val();
					ndata.loginPwd = comm.encrypt($('#doublePassword').val(),resp.data);
					ndata.secretKey = resp.data;
					ndata.optRemark = $("#viewMark").val();
					ndata.changeContent = JSON.stringify(chg);
					ndata.belongEntCustId = ndatas.entCustId;
					ndata.typeEum = "3";
					infoManage.updateDeclareEnt(ndata, function(res){
						comm.alert({content:comm.lang("infoManage")[22000], callOk:function(){
							$('#qygsdjxx_dialog').dialog('destroy');
							$("#qygsdjxx").click();
						//	self.initShowForm(ndatas);
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
						$('#doubleUserNameTr, #doublePasswordTr').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
					case '2':
						$('#doubleUserNameTr, #doublePasswordTr').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					case '3':
						$('#doubleUserNameTr, #doublePasswordTr').addClass('none');
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