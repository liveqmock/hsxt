define(['text!resouceManageTpl/cyqyztcx/cyqyzg_zx.html',
        "resouceManageDat/resouceManage",
        "resouceManageLan"],function(cyqyzg_zxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').html(_.template(cyqyzg_zxTpl));
			  
			$("#cyqyzg_bank").combobox({'width':400});
			
			//下一步
		 	$('#btn_cyqyzgzx_xyb').click(function(){
		 		if(self.validateStepOne().form()){
		 			$('#cyqyzgzx_1').addClass('none');
			 		$('#cyqyzgzx_2').removeClass('none');
		 		}
		 	});
		 	
		 	//上一步		 
		 	$('#btn_cyqyzgzx_syb').click(function(){
		 		$('#cyqyzgzx_2').addClass('none');
		 		$('#cyqyzgzx_1').removeClass('none');
		 	});
		 
			 //提交
			 $('#btn_cyqyzgzx_tj').click(function(){
				 self.saveFile();
			 });
			 this.initTmpPicPreview();
		},
		/**
		 * 保存文件
		 */
		saveFile : function(){
			var self = this;
			if(self.validateStepTwo()){
				var ids = ['otherFile', 'reportFile', 'statementFile'];
				comm.uploadFile(null, ids, function(data){
					if(data.otherFile){
						$("#file_id_1").val(data.otherFile);
					}
					if(data.reportFile){
						$("#file_id_2").val(data.reportFile);
					}
					if(data.statementFile){
						$("#file_id_3").val(data.statementFile);
					}
					self.saveData();
					self.initTmpPicPreview();
				}, function(err){
					self.initTmpPicPreview();
				}, {delFileIds:null});
			}
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var data = comm.getCache("resouceManage", "memberQuit");
			var params = {};
			params.quit_entCustId = data['entCustId'];
			params.quit_entResNo = data['entResNo'];
			params.quit_entCustName = data['entName'];
			params.entAddress = data['entAddr'];
			params.linkman = data['contactPerson'];
			params.linkmanMobile = data['contactPhone'];
			params.applyReason = $("#applyReason").val();
			params.oldStatus = data['pointStatus'];
			params.bankAcctId = $("#acctId").val();
		    params.otherFile = $("#file_id_1").val();
			params.reportFile = $("#file_id_2").val();
			params.statementFile = $("#file_id_3").val();
			params.createdBy = comm.getCookie('userName')+'('+comm.getCookie('operName')+')';
			dataModoule.createMemberQuit(params, function(res){
				 comm.alert({content:comm.lang("resouceManage")[22000],callOk:function(){
				 	$('#cyqyztcx_cyqyztcx').click();
				 }});
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var data = comm.getCache("resouceManage", "memberQuit");
			//平台信息
			cacheUtil.findCacheSystemInfo(function(sysRes){
				$('#currencyText').html(sysRes.currencyNameCn);
			});
			
			if(data){
				$("#entName-1, #entName-2").html(data['entName']);
			}
			dataModoule.findCompanyBanksByCustId({companyCustId:data['entCustId']}, function(response){
				var bankList = response.data;
				$.each(response.data, function (i, o) {
					var moren = ""; // 是否默认
					var yz = "";
					if (o.isDefaultAccount == '1'){
						moren = "[默认]";
					}
					if (o.isDefaultAccount == '0') {
						yz = "[未验证]";
					} else {
						yz = "[已验证]";
					}
					$("#cyqyzg_bank").append('<option value="' + i + '">' + yz + "&nbsp;&nbsp;" + o.bankName + o.bankAccNo + "&nbsp;&nbsp;" + moren + '</option>');
				});
				$("#cyqyzg_bank").combobox({select:function(){
					var bank = bankList[$("#cyqyzg_bank").val()];
					$("#bankName").html(bank['bankName']);
					$("#acctId").val(bank['accId']);
					//获取地区信息
					cacheUtil.syncGetRegionByCode(null, bank.provinceNo, bank.cityNo, "", function(resdata){
						$("#placeName").html(resdata);
					});
					$("#bankAcct").html(bank['bankAccNo']);
				}})
			});
		},
		/**
		 * 表单校验
		 */
		validateStepOne : function(){
			return $("#upload_form").validate({
				rules : {
					reportFile : {
						required : $("#preview-2").is(":visible")
					},
					statementFile : {
						required : $("#preview-3").is(":visible")
					},
					applyReason : {
						required : true,
						rangelength:[1, 300]
					}
				},
				messages : {
					reportFile : {
						required : comm.lang("resouceManage").investigationReportIsNull
					},
					statementFile : {
						required : comm.lang("resouceManage").declarePicIsNull
					},
					applyReason : {
						required : comm.lang("resouceManage").applyReasonIsNull,
						rangelength:comm.lang("resouceManage")[32530]
					}
				},
				errorPlacement : function (error, element) {
					if($(element).attr("name") == "applyReason"){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+50 top+30",
								at : "left top"
							}
						}).tooltip("open");
					}else if($(element).attr("name") == "reportFile"){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+100 top-10",
								at : "left top"
							}
						}).tooltip("open");
					}else if($(element).attr("name") == "statementFile"){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+100 top+10",
								at : "left top"
							}
						}).tooltip("open");
					}
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			/**
			 * 如果是修改则无需对上传文件进行必填校验
			 */
			if($("#file_id_2").val() != ""){
				validate.settings.rules.reportFile = {required : false};
			}
			if($("#file_id_3").val() != ""){
				validate.settings.rules.statementFile = {required : false};
			}
		},
		/**
		 * 表单校验
		 */
		validateStepTwo : function(){
			if($("#acctId").val() == ""){
				comm.warn_alert(comm.lang("resouceManage").bankIsNull);
				return false;
			}
			return true;
			var v = $("#bank_form12").validate({
				rules : {
					cyqyzg_bank : {
						required : true
					}
				},
				messages : {
					cyqyzg_bank : {
						required : comm.lang("resouceManage").bankIsNull
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
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#otherFile', '#reportFile', '#statementFile'];
			var imgIds = ['#preview-1', '#preview-2', '#preview-3'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		}
	}
}); 
