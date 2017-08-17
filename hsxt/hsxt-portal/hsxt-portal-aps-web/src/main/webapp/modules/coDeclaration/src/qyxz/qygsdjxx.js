define(['text!coDeclarationTpl/qyxz/qygsdjxx.html',
        'coDeclarationDat/qyxz/qygsdjxx',
        'coDeclarationLan'],function(qygsdjxxTpl, dataModoule){
	return qyxz_qygsdjxx_services = {
		showPage : function(){
			qyxz_qygsdjxx_services.busLicenceApitId = null;
			qyxz_qygsdjxx_services.busLicenceFileiId = null;
			if(this.getApplyId() == null){
				$('#busibox').html(_.template(qygsdjxxTpl));
				this.initForm();
				this.picPreview();
			}else{
				this.initData();
			}
		},
		/**
		 * 绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#thum-3', $('#thum-3').children().first().attr('src'));
			});
			comm.initUploadBtn(['#busLicenceFile'], ['#thum-3'],87,52);
		},
		/**
		 * 获取申请编号
		 */
		getApplyId : function(){
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		/**
		 * @param date 注册日期
		 * @param legalCreType 证件类型
		 */
		initForm : function(date, legalCreType){
			var self = this;
			//下一步 
			$('#qygsdjxx_next').click(function(){
				self.busLicenceFileUpload(true);
			});
			
			//保存
			$('#qygsdjxx_save').click(function(){
				self.busLicenceFileUpload();
			});

			//判断页面是否有更新（编辑时候用）
			$("#qygsdjxx_form :input").change(function() {
				comm.setCache("coDeclaration", "updateFlag", true);
			});
			
			//处理返回按钮
			if ($('#050100000000_subNav_050101000000').hasClass('s_hover')){
				$('#qygsdjxx_back').hide();
			} else {
				$('#qygsdjxx_back').show();
			}
			$('#qygsdjxx_back').click(function(){
				$('#050100000000_subNav_050103000000').click();
			});
			
			//成立日期
			$("#establishingDate").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			
			$("#establishingDate").val((date?date:""));
			
			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
			});
		},
		/**
		 * 禁用与启用法人证件号码输入框
		 */
		changeLegalCreNo : function(){
			var legalType = $("#legalCreType").attr('optionValue');
			if(legalType == ""){
				$("#legalCreNo").val('');
				$("#legalCreNo").attr('disabled', true);
				$("#legalCreNo").attr('title', comm.lang("coDeclaration").chooseLegalType);
			}else{
				$("#legalCreNo").attr('disabled', false);
			}
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			var params = {
				applyId: this.getApplyId()
			};
			dataModoule.findDeclareEntByApplyId(params, function(res){
				if(res.data){
					$('#busibox').html(_.template(qygsdjxxTpl, res.data));
					self.initForm(res.data.establishingDate, res.data.legalCreType);
					if(res.data.entName != ""){
						$("#isNew").val("false");
					}
					comm.delCache("coDeclaration", "updateFlag");
					if(res.data && res.data.licenseAptitude && res.data.licenseAptitude.fileId){
						comm.initPicPreview("#thum-3", res.data.licenseAptitude.fileId, "");
						$("#thum-3").html("<img width='107' height='64' src='" + comm.getFsServerUrl(res.data.licenseAptitude.fileId) + "'/>");
						qyxz_qygsdjxx_services.busLicenceApitId = comm.removeNull(res.data.licenseAptitude.aptitudeId);
						qyxz_qygsdjxx_services.busLicenceFileiId = comm.removeNull(res.data.licenseAptitude.fileId);
					}
					qyxz_qygsdjxx_services.picPreview();
				}
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
					dealArea : {
						required : false,
						rangelength:[0, 300]
					},
					limitDate : {
						required : false,
						rangelength:[0, 50]
					}
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
			if(comm.removeNull(qyxz_qygsdjxx_services.busLicenceFileiId) != ""){
				validate.settings.rules.busLicenceFile = {required : false};
				validate.settings.messages.busLicenceFile = {required : ''};
			}else{
				validate.settings.rules.busLicenceFile = {required : true};
				validate.settings.messages.busLicenceFile = {required : comm.lang("coDeclaration")[36009]};
			}
			return validate;
		},
		/**
		 * 企业申报-附件上传
		 */
		busLicenceFileUpload : function(autoNext){
			var slef = this;
			if(!this.validateData().form()){
				return;
			}
			var ids = [];
			var delFileIds = [];
			if($("#busLicenceFile").val() != ""){
				if(comm.removeNull(qyxz_qygsdjxx_services.busLicenceFileiId) != ""){
					delFileIds[delFileIds.length] = qyxz_qygsdjxx_services.busLicenceFileiId;
				}
				ids[ids.length] = "busLicenceFile";
			}
			if(ids.length == 0){
				qyxz_qygsdjxx_services.saveBizRegInfo(autoNext);
			}else{
				comm.uploadFile(null, ids, function(data){
					if(data.busLicenceFile){
						qyxz_qygsdjxx_services.busLicenceFileiId = data.busLicenceFile;
					}
					qyxz_qygsdjxx_services.saveBizRegInfo(autoNext);
					qyxz_qygsdjxx_services.picPreview();
				}, function(err){
					qyxz_qygsdjxx_services.picPreview();
				}, {delFileIds : delFileIds});
			}
		},
		/**
		 * 企业申报-保存工商登记信息
		 */
		saveBizRegInfo : function(autoNext){
			var params = $("#qygsdjxx_form").serializeJson();
			params.applyId = this.getApplyId();
			params.busLicenceApitId = comm.removeNull(qyxz_qygsdjxx_services.busLicenceApitId);
			params.busLicenceFileiId = comm.removeNull(qyxz_qygsdjxx_services.busLicenceFileiId);
			dataModoule.saveDeclareEnt(params, function(res){
				qyxz_qygsdjxx_services.saveStep(2);
				comm.delCache("coDeclaration", "updateFlag");
				$("#isNew").val("false");
				if(res && res.data && res.data.licenseAptitude && res.data.licenseAptitude.aptitudeId){
					qyxz_qygsdjxx_services.busLicenceApitId = res.data.licenseAptitude.aptitudeId;
				}
				if(autoNext){
					$('#qylxxx').click();
				}else{
					comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
						
					}});
				}
			});
		},
		/**
		 * 控制步骤
		 * @param curStep 当前步骤
		 */
		saveStep : function(curStep){
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if((entDeclare.curStep) <= curStep){
				entDeclare.curStep = curStep;
			}
		}
	}
}); 

 