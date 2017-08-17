define(['text!toolmanageTpl/ckkcgl/gjdj.html',
		'text!toolmanageTpl/ckkcgl/gjdj_2.html',
		'text!toolmanageTpl/ckkcgl/gjdj_gjly.html',
		'text!toolmanageTpl/ckkcgl/gjdj_gjbs.html',
		'toolmanageDat/ckkcgl/gjdj',
        'toolmanageLan'
		], function(gjdj_1Tpl, gjdj_2Tpl, gjdj_gjlyTpl, gjdj_gjbsTpl, dataModoule){
	return {
		showPage : function(){
			$('#busibox').html(gjdj_1Tpl);
			var self = this;
			
			$('#gjdj_1_xyb_btn').click(function(){
				self.stepOne();
			});
		},
		showTpl : function(tplObj){
			$('#gjdj_1Tpl, #gjdj_2Tpl, #gjdj_gjlyTpl, #gjdj_gjbsTpl').addClass('none');	
			tplObj.removeClass('none');
		},
		/**
		 * 工具登记第一步
		 */
		stepOne : function(){
			var self = this;
			if(!self.validateStepOne().form()){
				return;
			}
			dataModoule.findDeviceDetailByNo({deviceSeqNo:$("#deviceSeqNo").val()}, function(res){
				$('#gjdj_2Tpl').html(gjdj_2Tpl);
				self.showTpl($('#gjdj_2Tpl'));
				$('#deviceSeqNo').val(res.data.deviceSeqNo);
				$('#batchNo').val(res.data.batchNo);
				$('#productName').val(res.data.productName);
				$('#whName').val(res.data.whName);
				$('#categoryCode').val(comm.getNameByEnumId(res.data.categoryCode, comm.lang("toolmanage").categoryTypeEnum));
				$('#gjdj_2_syb_btn').click(function(){
					self.showTpl($('#gjdj_1Tpl'));
				});
				$('#gjdj_2_xyb_btn').click(function(){
					var val = $('#djlb_div').find('input[name="useType"]:checked').val();
					if(val != null){
						switch(val){
							case '1': 
								$('#gjdj_gjlyTpl').html(gjdj_gjlyTpl);
								self.showTpl($('#gjdj_gjlyTpl'));
								
								$('#gjdj_gjly_qx_btn').click(function(){
									$('#lyr_txt, #lysm_txt').val('');
								});
								
								$('#gjdj_gjly_tj_btn').click(function(){
									if(!self.validateStepLY().form()){
										return;
									}
									var params = {};
									params.deviceSeqNo = res.data.deviceSeqNo;//序列号
									params.useType = 1;//领用类别
									params.batchNo = res.data.batchNo;//批次号
									params.useName = $("#useName").val();//领用人
									params.description = $("#description_ly").val();//领用说明
									self.saveData(params);
								});
								
								break;
							case '2':
								$('#gjdj_gjbsTpl').html(gjdj_gjbsTpl);
								self.showTpl($('#gjdj_gjbsTpl'));
								
								$('#gjdj_gjbs_qx_btn').click(function(){
									$('#bssm_txt').val('');
								});
								
								$('#gjdj_gjbs_tj_btn').click(function(){
									if(!self.validateStepBS().form()){
										return;
									}
									var params = {};
									params.deviceSeqNo = res.data.deviceSeqNo;//序列号
									params.useType = 2;//领用类别
									params.batchNo = res.data.batchNo;//批次号
									params.description = $("#description_bs").val();//报损说明
									self.saveData(params);
								});
								
								break;
						}
						
						$('#gjdj_gjly_syb_btn, #gjdj_gjbs_syb_btn').click(function(){
							self.showTpl($('#gjdj_2Tpl'));
						});
					}
					else{
						comm.alert({
							imgFlag : true,
							imgClass : 'tips_error',
							content : '选择登记类别'	
						});	
					}
				});
			});
		},
		/**
		 * 工具登记-保存数据
		 */
		saveData : function(data){
			dataModoule.addDeviceUseRecord(data, function(res){
				comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
					$("#gjdj").click();
				}});
			});
		},
		/**
		 * 表单校验-第一步
		 */
		validateStepOne : function(){
			 return $("#form_step_one").validate({
				rules : {
					deviceSeqNo : {
						required : true,
					}
				},
				messages : {
					deviceSeqNo : {
						required : comm.lang("toolmanage")[36224]
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}else{
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");	
					}
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/**
		 * 表单校验-工具领用
		 */
		validateStepLY : function(){
			 return $("#form_step_four").validate({
				rules : {
					useName : {
						required : true,
						rangelength : [1, 64]
					},
					description_ly:{
						rangelength : [0, 256]
					}
				},
				messages : {
					useName : {
						required : comm.lang("toolmanage")[36231],
						rangelength : comm.lang("toolmanage")[36230]
					},
					description_ly:{
						rangelength : comm.lang("toolmanage")[36228]
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}else{
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");	
					}
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/**
		 * 表单校验-工具报损
		 */
		validateStepBS : function(){
			 return $("#form_step_three").validate({
				rules : {
					description_bs:{
						rangelength : [0, 256]
					}
				},
				messages : {
					description_bs:{
						rangelength : comm.lang("toolmanage")[36229]
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}else{
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 1000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");	
					}
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		}
	}	
});
