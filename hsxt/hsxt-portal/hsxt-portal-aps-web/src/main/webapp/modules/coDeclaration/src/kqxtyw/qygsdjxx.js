define(['text!coDeclarationTpl/kqxtyw/qygsdjxx.html',
        'text!coDeclarationTpl/kqxtyw/confirm_dialog.html',
        'coDeclarationDat/kqxtyw/qygsdjxx',
		'coDeclarationLan'], function(qygsdjxxTpl, confirm_dialogTpl, dataModoule){
	var self={
		showPage : function(){
			self.initData();
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
			$('#qygsdjxx_cancel').triggerWith('#'+$("#menuName").val());
			//点击修改页面提交按钮
			$('#qygsdjxx_xg_btn').click(function(){
				self.initChangeData();
			});
			//点击修改页面取消按钮
			$('#back_qygsdjxx').click(function(){
				$("#qygsdjxxTpl1").show();
				$("#qygsdjxxTpl2").hide();
			});
			
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.findDeclareEntByApplyId({"applyId": $("#applyId").val()}, function(res){
				self.initShowForm(res.data);
			});
		},
		/**
		 * /绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#licenseImg', $('#licenseImg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#licenseFileBtn'], ['#licenseImg'], 81, 52);
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 */
		initShowForm : function(data){
			comm.delCache('coDeclaration', 'kqxtyw-qygsdjxx-info');
			comm.setCache('coDeclaration', 'kqxtyw-qygsdjxx-info', data);
			if(data){
				$('#infobox').html(_.template(qygsdjxxTpl, data));
				//成立日期
				$("#establishingDate").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate()
				});
				
				$("#establishingDate").val((data.establishingDate?data.establishingDate:""));
				if(data.licenseAptitude&&data.licenseAptitude.fileId){
					comm.initPicPreview("#licenseImg", data.licenseAptitude.fileId, "");
					comm.initPicPreview("#licenseImg2", data.licenseAptitude.fileId, "");
					$("#licenseImg2").attr("src", comm.getFsServerUrl(data.licenseAptitude.fileId));
					$("#licenseImg").children().first().attr("src", comm.getFsServerUrl(data.licenseAptitude.fileId));
					$("#licenseImg").css({'background':'none'});
				}
			}
			self.initForm();
			self.picPreview();
			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#licenseDemoBtn", comm.getPicServerUrl(map.picList, '1010'), null);
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qygsdjxx_form").validate({
				 rules : {
					entName : {
						required : true,
						rangelength:[2, 64]
					},
					legalName : {
						required : true,
						rangelength:[2, 20]
					},
					entType : {
						required : false,
						rangelength:[2, 20]
					},
					/*orgNo : {
						required : true,
						orgcode : true
					},*/
					establishingDate : {
						required : false,
						date : true
					},
					entAddress : {
						required : true,
						rangelength:[2, 128]
					},
					licenseNo : {
						required : true,
						businessLicence:true
					},
					/*linkmanMobile : {
						required : false,
						phoneCN : true
					},
					taxNo : {
						required : true,
						taxcode : true
					},*/
					dealArea : {
						required : false,
						rangelength:[0, 300]
					},
					limitDate : {
						required : false,
						rangelength:[0, 50]
					},
				},
				messages : {
					entName : {
						required : comm.lang("coDeclaration")[36023],
						rangelength:comm.lang("coDeclaration")[36024]
					},
					legalName : {
						required :  comm.lang("coDeclaration")[36025],
						rangelength:comm.lang("coDeclaration")[36026]
					},
					entType : {
						rangelength:comm.lang("coDeclaration")[36027]
					},
					/*orgNo : {
						required : comm.lang("coDeclaration")[36028],
						orgcode : comm.lang("common")[22059]
					},*/
					establishingDate : {
						date : comm.lang("coDeclaration")[36029]
					},
					entAddress : {
						required : comm.lang("coDeclaration")[36030],
						rangelength:comm.lang("coDeclaration")[36031]
					},
					licenseNo : {
						required : comm.lang("coDeclaration")[36032],
						businessLicence:comm.lang("coDeclaration")[36033]
					},
					/*linkmanMobile : {
						phoneCN : comm.lang("coDeclaration")[36034]
					},
					taxNo : {
						required : comm.lang("coDeclaration")[36035],
						taxcode : comm.lang("common")[22060]
					},*/
					dealArea : {
						rangelength: comm.lang("coDeclaration")[36036]
					},
					limitDate : {
						rangelength:comm.lang("coDeclaration")[36037]
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
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			var verificationMode = $("#verificationMode").attr('optionValue');
			if(verificationMode != "1"){
				comm.warn_alert(comm.lang("coDeclaration").notSupportValidateMode);
				return false;
			}
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
			var odata = comm.getCache('coDeclaration', 'kqxtyw-qygsdjxx-info');
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
			
			//处理营业执照扫描件
			if(comm.isNotEmpty($("#licenseFileBtn").val())){
				var fId = $("#licenseFile").val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old", fileId: fId, title:"修改前营业执照扫描件"});
				}
				newArray.push({picId:"#prv-pic-new", fileId: '#licenseImg', title:"修改后营业执照扫描件"});
				trs+="<tr><td class=\"view_item\">营业执照扫描件</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
			if(trs == ""){
				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
				return;
			}
			//提交
			$('#qygsdjxx_dialog > p').html(_.template(confirm_dialogTpl));
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
				buttons:{
					'确定':function(){
						if(!self.validateViewMarkData().form()){
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							ndata.verifyDouble = "true";
							ndata.dblOptCustId = verifyRes.data;
							ndata.optRemark = $("#viewMark").val();
							ndata.changeContent = JSON.stringify(chg);
							ndata.isNew = 'false';
							if(comm.isNotEmpty(ndata.licenseAptitude.fileId)){
								ndata.busLicenceFileiId = ndata.licenseAptitude.fileId;
							}
							var ids = [];
	 						var delFileIds = [];
	 						if(comm.isNotEmpty($("#licenseFileBtn").val())){
	 							ids[0] = "licenseFileBtn";
	 							if(comm.isNotEmpty($("#licenseFile").val())){
	 								delFileIds[0] = $("#licenseFile").val();
	 							}
	 						}
	 						if(ids.length==0){
	 							self.saveData(ndata);
	 							return;
	 						}
	 						comm.uploadFile(null, ids, function(data){
								if(data.licenseFileBtn){
									$("#licenseFile").val(data.licenseFileBtn);
									ndata.busLicenceFileiId = data.licenseFileBtn;
								}
								self.saveData(ndata);
							}, function(err){
								self.picPreview();
							}, {delFileIds : delFileIds});
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
			delete ndata.licenseAptitude;
			dataModoule.saveDeclareEnt(ndata, function(res){
				comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
					$('#qygsdjxx_dialog').dialog('destroy');
					$('#ckxq_qygsdjxx').click();
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