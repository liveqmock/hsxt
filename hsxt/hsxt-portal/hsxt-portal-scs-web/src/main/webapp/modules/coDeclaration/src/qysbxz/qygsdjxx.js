define(['text!coDeclarationTpl/qysbxz/qygsdjxx.html',
        'coDeclarationDat/qysbxz/qygsdjxx',
        'coDeclarationLan'],function(qygsdjxxTpl, dataModoule){
	var scs_qygsdjxx =  {
		showPage: function () {
			scs_qygsdjxx.busLicenceApitId = null;
			scs_qygsdjxx.busLicenceFileiId = null;
			if (scs_qygsdjxx.getApplyId() == null) {
				$('#contentWidth_2').html(_.template(qygsdjxxTpl));
				this.initForm();
				this.picPreview();
			} else {
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
		getApplyId: function () {
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		/**
		 * @param date 注册日期
		 * @param legalCreType 证件类型
		 */
		initForm: function (date, legalCreType) {
			//处理返回按钮
			if ($('#030200000000_subNav_030201000000').hasClass('s_hover')) {
				$('#qygsdjxx_fh').hide();
			} else {
				$('#qygsdjxx_fh').show();
			}
			$('#qygsdjxx_fh').click(function () {
				$('#030200000000_subNav_030202000000').click();
			});

			//判断页面是否有更新（编辑时候用）
			$("#qygsdjxx_form :input").change(function () {
				comm.setCache("coDeclaration", "updateFlag", true);
			});

			//下一步 
			$('#qygsdjxx_next').click(function () {
				scs_qygsdjxx.busLicenceFileUpload(true);
			});

			//保存
			$('#qygsdjxx_save').click(function () {
				scs_qygsdjxx.busLicenceFileUpload();
			});

			//成立日期
			$("#establishingDate").datepicker({
				changeMonth: true,
				changeYear: true,
				dateFormat: "yy-mm-dd",
				maxDate: new Date()
			});

			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
			});

			$("#establishingDate").val((date ? date : ""));
		},
		/**
		 * 禁用与启用法人证件号码输入框
		 */
		changeLegalCreNo: function () {
			var legalType = $("#legalCreType").attr('optionValue');
			if (legalType == "") {
				$("#legalCreNo").val('');
				$("#legalCreNo").attr('disabled', true);
				$("#legalCreNo").attr('title', "请先选择法人代表证件类型.");
			} else {
				$("#legalCreNo").attr('disabled', false);
			}
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var params = {
				applyId: this.getApplyId()
			};
			dataModoule.findDeclareEntByApplyId(params, function (res) {
				if (res.data) {
					$('#contentWidth_2').html(_.template(qygsdjxxTpl, res.data));
					scs_qygsdjxx.initForm(res.data.establishingDate, res.data.legalCreType);
					if (res.data.entName != "") {
						$("#isNew").val("false");
					}
				}
				if(res.data && res.data.licenseAptitude && res.data.licenseAptitude.fileId){
					comm.initPicPreview("#thum-3", res.data.licenseAptitude.fileId, "");
					$("#thum-3").html("<img width='107' height='64' src='" + comm.getFsServerUrl(res.data.licenseAptitude.fileId) + "'/>");
					scs_qygsdjxx.busLicenceApitId = comm.removeNull(res.data.licenseAptitude.aptitudeId);
					scs_qygsdjxx.busLicenceFileiId = comm.removeNull(res.data.licenseAptitude.fileId);
				}
				scs_qygsdjxx.picPreview();
				//加载样例图片
				cacheUtil.findDocList(function (map) {
					comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
				});
			});
		},
		/**
		 * 表单校验
		 */
		validateData: function () {

//			jQuery.validator.addMethod('xx', function(value, element) {
//				var legalCreType =$('#legalCreType').attr('optionValue');
//				if(legalCreType!=null&&legalCreType!=''){
//					return value!=''&&value!=null;
//				}
//				
//				return this.optional(element) ;
//				
//			}, '');

			var validate = $("#qygsdjxx_form").validate({
				rules: {
					entName: {
						required: true,
						rangelength: [2, 64]
					},
					legalName: {
						required: true,
						rangelength: [2, 20]
					},
					entType: {
						required: false,
						rangelength: [2, 20]
					},
					establishingDate: {
						required: false,
						date: true
					},
					entAddress: {
						required: true,
						rangelength: [2, 128]
					},
					licenseNo: {
						required: true,
						businessLicence: true
					},
					dealArea: {
						required: false,
						rangelength: [0, 300]
					},
					limitDate: {
						required: false,
						rangelength: [0, 50]
					}
				},
				messages: {
					entName: {
						required: comm.lang("coDeclaration")[32617],
						rangelength: comm.lang("coDeclaration")[32630]
					},
					legalName: {
						required: comm.lang("coDeclaration")[32620],
						rangelength: comm.lang("coDeclaration")[32632]
					},
					entType: {
						required: comm.lang("coDeclaration")[32673],
						rangelength: comm.lang("coDeclaration")[32674]
					},
					establishingDate: {
						required: comm.lang("coDeclaration")[32675],
						date: comm.lang("coDeclaration")[32676]
					},
					entAddress: {
						required: comm.lang("coDeclaration")[32618],
						rangelength: comm.lang("coDeclaration")[32631]
					},
					licenseNo: {
						required: comm.lang("coDeclaration")[32619],
						businessLicence: comm.lang("coDeclaration")[32507]
					},
					dealArea: {
						required: comm.lang("coDeclaration")[32626],
						rangelength: comm.lang("coDeclaration")[32628]
					},
					limitDate: {
						required: comm.lang("coDeclaration")[32677],
						rangelength: comm.lang("coDeclaration")[32678]
					}
				},
				errorPlacement: function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag: true,
						destroyTime: 3000,
						position: {
							my: "left+2 top+30",
							at: "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success: function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			if(comm.removeNull(scs_qygsdjxx.busLicenceFileiId) != ""){
				validate.settings.rules.busLicenceFile = {required : false};
				validate.settings.messages.busLicenceFile = {required : ''};
			}else{
				validate.settings.rules.busLicenceFile = {required : true};
				validate.settings.messages.busLicenceFile = {required : comm.lang("coDeclaration")[32639]};
			}
			return validate;
		},
		/**
		 * 企业申报-附件上传
		 */
		busLicenceFileUpload : function(autoNext){
			if(!scs_qygsdjxx.validateData().form()){
				return;
			}
			var ids = [];
			var delFileIds = [];
			if($("#busLicenceFile").val() != ""){
				if(comm.removeNull(scs_qygsdjxx.busLicenceFileiId) != ""){
					delFileIds[delFileIds.length] = scs_qygsdjxx.busLicenceFileiId;
				}
				ids[ids.length] = "busLicenceFile";
			}
			if(ids.length == 0){
				scs_qygsdjxx.saveBizRegInfo(autoNext);
			}else{
				comm.uploadFile(null, ids, function(data){
					if(data.busLicenceFile){
						scs_qygsdjxx.busLicenceFileiId = data.busLicenceFile;
					}
					scs_qygsdjxx.saveBizRegInfo(autoNext);
					scs_qygsdjxx.picPreview();
				}, function(err){
					scs_qygsdjxx.picPreview();
				}, {delFileIds : delFileIds});
			}
		},
		/**
		 * 企业申报-保存工商登记信息
		 */
		saveBizRegInfo: function (autoNext) {
			var params = $("#qygsdjxx_form").serializeJson();
			params.applyId = this.getApplyId();
			params.busLicenceApitId = comm.removeNull(scs_qygsdjxx.busLicenceApitId);
			params.busLicenceFileiId = comm.removeNull(scs_qygsdjxx.busLicenceFileiId);
			dataModoule.saveDeclareEnt(params, function (res) {

				scs_qygsdjxx.saveStep(2);

				comm.delCache("coDeclaration", "updateFlag");

				$("#isNew").val("false");
				if(res && res.data && res.data.licenseAptitude && res.data.licenseAptitude.aptitudeId){
					scs_qygsdjxx.busLicenceApitId = res.data.licenseAptitude.aptitudeId;
				}
				if (autoNext) {
					$('#qysbxz_qylxxx').click();
				} else {
					comm.alert({
						content: comm.lang("coDeclaration")[21000], callOk: function () {

						}
					});
				}
			});
		},
		/**
		 * 控制步骤
		 * @param curStep 当前步骤
		 */
		saveStep: function (curStep) {
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if ((entDeclare.curStep) <= curStep) {
				entDeclare.curStep = curStep;
			}
		}
	};

	return scs_qygsdjxx;
}); 
