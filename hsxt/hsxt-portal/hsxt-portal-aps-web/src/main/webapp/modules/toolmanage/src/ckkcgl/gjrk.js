define(['text!toolmanageTpl/ckkcgl/gjrk.html',
		'text!toolmanageTpl/ckkcgl/gjrk_rktj_dialog.html',
		'toolmanageDat/ckkcgl/gjrk',
        'toolmanageLan'
		], function(gjrkTpl, gjrk_rktj_dialogTpl, dataModoule){
	
	var self = {
			  showPage : function(){
				this.initForm();
			},

		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(gjrkTpl));
			
			//初始化工具类别
			comm.initSelect('#categoryCode', comm.lang("toolmanage").categoryTypeRkEnum, 185, null).change(function(){
				var code = $(this).attr('optionValue');
				if(code == "POINT_MCR" || code == "CONSUME_MCR"){
					$("#enterBatchNo").attr('disabled', false);
					$("#enterBatchNoSpan").show();
					$("#enterBatchNo").css('background-color', '#FFFFFF');
				}else{
					$("#enterBatchNo").val("");
					$("#enterBatchNoSpan").hide();
					$("#enterBatchNo").attr('disabled', true);
					$("#enterBatchNo").css('background-color', '#DDDDDD');
				}
				if(code == "P_POS"||code == "TABLET"||code == "POINT_MCR" || code == "CONSUME_MCR"){
					$("#quantity").attr('disabled', true);
					$("#quantity").css('background-color', '#DDDDDD');
				}else{
					$("#quantity").attr('disabled', false);
					$("#quantity").css('background-color', '#FFFFFF');
				}
				
				
				//初始化工具名称
				if(code != ""){
					dataModoule.findToolProductList({categoryCode:code}, function(res){
						comm.initSelect('#productId', res.data, 185, "").selectListIndex(0);
					});
				}else{
					comm.initSelect('#productId', {}, 185, "");
				}
				self.showDeviceSeqNo(code);
			});
			
			//初始化工具名称
			comm.initSelect('#productId', {}, 185, null);
			
			//初始化供应商
			dataModoule.findAllupplierList(null, function(res){
				comm.initSelect('#supplierId', res.data, 185, null);
			});
			
			//初始化仓库
			dataModoule.findAllWarehouseList(null, function(res){
				comm.initSelect('#whId', res.data, 185, null);
			});
			
			//绑定提交事件
			$('#rktj_btn').click(function(){
				self.getDeviceSeq();
			});
		},
		/**
		 * 显示或隐藏选择机器码文件
		 * @param categoryCode 工具类别
		 */
		showDeviceSeqNo : function(categoryCode){
			if(categoryCode == "P_POS" || categoryCode == "TABLET"){
				$("#deviceSeqNoSpan").show();
			}else{
				$("#deviceSeqNoSpan").hide();
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#gjrk_form").validate({
				rules : {
					categoryCode : {
						required : true,
					},
					productId : {
						required : true,
					},
					supplierId : {
						required : true,
					},
					whId : {
						required : true,
					},
					quantity : {
						number:true,
						less:0,
					},
					purchasePrice : {
						number:true,
						less:0,
					},
					marketPrice : {
						number:true,
						less:0,
					},
				},
				messages : {
					categoryCode : {
						required : comm.lang("toolmanage")[36202],
					},
					productId : {
						required : comm.lang("toolmanage")[36203],
					},
					supplierId : {
						required : comm.lang("toolmanage")[36204],
					},
					whId : {
						required : comm.lang("toolmanage")[36205],
					},
					quantity : {
						less : comm.lang("toolmanage")[36208],
						number:comm.lang("toolmanage")[36208],
					},
					purchasePrice : {
						less : comm.lang("toolmanage")[36209],
						number:comm.lang("toolmanage")[36209],
					},
					marketPrice : {
						less : comm.lang("toolmanage")[36210],
						number:comm.lang("toolmanage")[36210],
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
			//检查特殊必填
			var code = $("#categoryCode").attr('optionValue');
			if(code != ""){
				if(code == "P_POS" || code == "TABLET"){
					validate.settings.rules.deviceSeqNo = {required : true};
					validate.settings.messages.deviceSeqNo = {required : comm.lang("toolmanage")[36207]};
					validate.settings.rules.enterBatchNo = {required : false};
				}else if(code == "POINT_MCR" || code == "CONSUME_MCR"){
					validate.settings.rules.deviceSeqNo = {required : false};
					validate.settings.rules.enterBatchNo = {required : true};
					validate.settings.messages.enterBatchNo = {required : comm.lang("toolmanage")[36206]};
				}else{
					validate.settings.rules.deviceSeqNo = {required : false};
					validate.settings.rules.enterBatchNo = {required : false};
				}
			}
			return validate;
		},
		/**
		 * 处理机器码文件
		 */
		getDeviceSeq : function(){
			if(!self.validateData().form()){
				return;
			}
			var code = $("#categoryCode").attr('optionValue');
			if(code == "P_POS" || code == "TABLET"){
				var url = comm.domainList[comm.getProjectName()]+comm.UrlList["getDeviceSeq"];
				comm.uploadFile(url, ['"deviceSeqNo"'], function(res){
					self.saveData(res);
				}, function(res){
					
				}, null);
			}else{
				this.saveData("");
			}
		},
		/**
		 * 入库提交
		 */
		saveData : function(deviceSeqNos){
			var params = {};
			params.categoryCode = $("#categoryCode").attr('optionValue');
			params.productId = $("#productId").attr('optionValue');
			params.supplierId = $("#supplierId").attr('optionValue');
			params.whId = $("#whId").attr('optionValue');
			params.enterBatchNo = $("#enterBatchNo").val();
			params.quantity = $("#quantity").val();
			params.purchasePrice_hidden = comm.formatMoneyNumber($("#purchasePrice").val());
			params.marketPrice_hidden =comm.formatMoneyNumber($("#marketPrice").val());
			params.purchasePrice = $("#purchasePrice").val();
			params.marketPrice =$("#marketPrice").val();
			params.operNo = comm.getCookieOperNoName();
			params.categoryName = $("#categoryCode").val();
			params.productName = $("#productId").val();
			params.supplierName = $("#supplierId").val();
			params.whName = $("#whId").val();
			params.deviceSeqNos = deviceSeqNos;
			$('#dialogBox').html(_.template(gjrk_rktj_dialogTpl, params));
			this.initVerificationMode();
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"工具入库提交",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!self.validateViewMarkData().form()){
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							dataModoule.productEnter(params, function(res){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									$('#dialogBox').dialog('destroy');
									$('#gjrk').click();
								}});
							});
						});
					},
					"取消":function(){
						 $( this ).dialog("destroy");
					}
				}
			});
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("toolmanage").verificationMode, 185, '1').change(function(e){
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
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#confirm_dialog_form").validate({
				rules : {
					doubleUserName : {
						required : true,
					},
					doublePassword : {
						required : true,
					},
				},
				messages : {
					doubleUserName : {
						required : comm.lang("toolmanage")[36200],
					},
					doublePassword : {
						required : comm.lang("toolmanage")[36201],
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
		},
	};
	return self;
});

