define(['text!coDeclarationTpl/qybb/gdxx.html',
        'text!coDeclarationTpl/qybb/confirm_dialog.html',
        'coDeclarationDat/qybb/qybb',
        'coDeclarationLan'], function(gdxxTpl, confirm_dialogTpl, dataModoule){
	return {
		self : null,
		showPage : function(){
			self = this;
			$('#infobox').html(_.template(gdxxTpl));
			
			$('#cancel_btn').triggerWith('#'+$("#menuName").val());
			
			comm.initSelect('#idType', comm.lang("coDeclaration").idTypeEnum, 80);//初始化证件类型下拉框
			comm.initSelect('#shType', comm.lang("coDeclaration").shTypeEnum);//初始化股东性质下拉框
			
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
						required : comm.lang("coDeclaration")[36077],
						rangelength:comm.lang("coDeclaration")[36097]
					},
					shType : {
						required : comm.lang("coDeclaration")[36078]
					},
					idType : {
						required :  comm.lang("coDeclaration")[36079]
					},
					idNo : {
						required : comm.lang("coDeclaration")[36080]
					},
					phone : {
						required : comm.lang("coDeclaration")[36081],
						mobileNo:comm.lang("coDeclaration")[36018]
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
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[36080], IDCard : comm.lang("coDeclaration")[36038]};
			}else if(legalType == "2"){
				validate.settings.rules.idNo = {required : true, passport : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[36080], passport : comm.lang("coDeclaration")[36039]};
			}else if(legalType == "3"){
				validate.settings.rules.idNo = {required : true, businessLicence : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[36080], businessLicence : comm.lang("coDeclaration")[36096]};
			}else{
				validate.settings.rules.idNo = {required : true};
				validate.settings.messages.idNo = {required : comm.lang("coDeclaration")[36080]};
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
			var params = $("#gd_form"). serializeJson();
			params.idNo = params.idNo ? params.idNo.toUpperCase() : "";
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
				params.dblOptCustId = "1005";
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
		 * 获取申请编号
		 */
		getApplyId : function(){
			return comm.getCache("coDeclaration", "entFilling").applyId;
		}
	}	
});