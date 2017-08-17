define(['text!coDeclarationTpl/qybb/qybb/gdxx.html',
        'coDeclarationDat/qybb/qybb',
        'coDeclarationLan'],function(gdxxTpl, dataModoule){
	return {	 	
		showPage : function(){
			$('#qybb_view').html(_.template(gdxxTpl));
			
			self = this;
			
			$('#qybb_bc').css('display','none');
			$('#qybb_syb').css('display','');
			$('#qybb_xyb').css('display','');
			$('#qybb_tj').css('display','none');
			
			comm.initSelect('#idType', comm.lang("coDeclaration").idTypeEnum, 80);//初始化证件类型下拉框
			comm.initSelect('#shType', comm.lang("coDeclaration").shTypeEnum);//初始化股东性质下拉框
			
			//上一步
			$('#qybb_syb').click(function(){
				$('#qybb_qyjbxx').click();
			});
			
			//下一步
			$('#qybb_xyb').click(function(){
				$('#qybb_fjxx').click();
			});
			
			//切换证件类型
			$('#idType').change(function(){
				self.validateData();
			});
			
			//添加/保存
			$('#saveBtn').click(function(){
				self.saveShareholder();
			});
			
			//自动查询
			this.query();
			this.saveStep();
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#gd_form").validate({
				rules : {
					shName : {
						required : true,
						rangelength:[2, 20]
					},
					shType : {
						required : true
					},
					idType : {
						required : true
					},
					idNo : {
						required : true
					},
					phone : {
						required : true,
						mobileNo:true
					}
				},
				messages : {
					shName : {
						required : comm.lang("coDeclaration")[32610],
						rangelength:comm.lang("coDeclaration")[32636]
					},
					shType : {
						required : comm.lang("coDeclaration")[32611]
					},
					idType : {
						required :  comm.lang("coDeclaration")[32621]
					},
					idNo : {
						required : comm.lang("coDeclaration")[32613]
					},
					phone : {
						required : comm.lang("coDeclaration")[32625],
						mobileNo:comm.lang("coDeclaration")[32506]
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
			var legalType = $("#idType").attr('optionValue');
			if(legalType == "1"){
				validate.settings.rules.idNo = {required : true, IDCard : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[32624], IDCard : comm.lang("coDeclaration")[32508]};
			}else if(legalType == "2"){
				validate.settings.rules.idNo = {required : true, passport : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[32624], passport : comm.lang("coDeclaration")[32509]};
			}else if(legalType == "3"){
				validate.settings.rules.idNo = {required : true, businessLicence : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[32624], businessLicence : comm.lang("coDeclaration")[32507]};
			}else{
				validate.settings.rules.idNo = {required : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[32624]};
			}
			return validate;
		},
		/**
		 * 查询股东信息
		 */
		query : function(){
			var params = {search_applyId : this.getApplyId()};
			dataModoule.findShareholderList(params, this.editShareholder, this.delShareholder);
		},
		/**
		 * 删除股东信息
		 */
		delShareholder : function (record, rowIndex, colIndex) {
			if(colIndex == 0){
				return rowIndex+1;
			}else if(colIndex == 2){
				return comm.getNameByEnumId(record['shType'], comm.lang("coDeclaration").shTypeEnum);
			}else if(colIndex == 3){
				return comm.getNameByEnumId(record['idType'], comm.lang("coDeclaration").idTypeEnum);
			}else{
				return $('<a data-type="">'+comm.lang("coDeclaration").delBtn+'</a>').click(function(e) {
					comm.i_confirm (comm.lang("coDeclaration").confirmDel, function(){
						var params = {id:record['filingShId']};
						dataModoule.deleteShareholder(params, function(res){
							comm.alert({content:comm.lang("coDeclaration").deleteOk, callOk:function(){
								self.clearForm();
								self.query();
							}});
						});
					}, 400);
				});
			}
		},
		/**
		 * 编辑股东信息
		 */
		editShareholder : function (record, rowIndex, colIndex) {
			if(colIndex == 6){
				return $('<a data-type="">'+comm.lang("coDeclaration").updBtn+'</a>').click(function(e) {
					$("#shName").val(record['shName']);
					$("#shType").selectListValue(record['shType']);
					$("#idType").selectListValue(record['idType']);
					$("#idNo").val(record['idNo']);
					$("#phone").val(record['phone']);
					$("#filingShId").val(record['filingShId']);
					$("#saveBtn").html(comm.lang("coDeclaration").updBtn);
				});
			}
		},
		/**
		 * 保存股东信息，保存之后改变按钮文字为'添加'，并清空填写的信息
		 */
		saveShareholder : function () {
			if(!self.validateData().form()){
				return;
			}
			var idNo = $.trim($('#idNo').val());
			idNo =idNo.toUpperCase();
			$('#idNo').val(idNo);
			var params = $("#gd_form"). serializeJson();
			params.applyId = this.getApplyId();
			params.shType = $("#shType").attr('optionValue');
			params.idType = $("#idType").attr('optionValue');
			var filingShId = $("#filingShId").val();
			if(filingShId == ""){//股东编号为空为新增，反之为修改
				dataModoule.createShareholder(params, function(res){
					comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
						$("#saveBtn").html(comm.lang("coDeclaration").addBtn);
						self.clearForm();
						self.query();
					}});
				});
			}else{
				dataModoule.updateShareholder(params, function(res){
					comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
						$("#saveBtn").html(comm.lang("coDeclaration").addBtn);
						self.clearForm();
						self.query();
					}});
				});
			}
		},
		clearForm : function () {
			$("#shName").val("");
			$("#shType").val("");
			$("#idType").val("");
			$("#idNo").val("");
			$("#phone").val("");
			$("#filingShId").val("");
		},
		/**
		 * 控制步骤（股东信息非必填）
		 */
		saveStep : function(){
			var entFilling = comm.getCache("coDeclaration", "entFilling");
			if((entFilling.curStep+1) == 2){
				entFilling.curStep = 2;
			}
		},
		/**
		 * 获取申请编号
		 */
		getApplyId : function(){
			return comm.getCache("coDeclaration", "entFilling").applyId;
		}
	}
}); 
