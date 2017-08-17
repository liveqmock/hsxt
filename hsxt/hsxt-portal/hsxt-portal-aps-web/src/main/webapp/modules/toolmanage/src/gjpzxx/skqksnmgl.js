define(['text!toolmanageTpl/gjpzxx/skqksnmgl.html',
		'text!toolmanageTpl/gjpzxx/skqksnmgl_sc_dialog.html',
		'text!toolmanageTpl/gjpzxx/skqksnmgl_ck_dialog.html',
		'text!toolmanageTpl/gjpzxx/skqksnmgl_dc_dialog.html',
		'text!toolmanageTpl/gjpzxx/skqksnmgl_fh_dialog.html',
		'text!toolmanageTpl/gjpzxx/skqksnmgl_dcjl_dialog.html',
        'toolmanageDat/gjpzxx/skqksnmgl',
		'toolmanageLan'
		], function(skqksnmglTpl, skqksnmgl_sc_dialogTpl, skqksnmgl_ck_dialogTpl, skqksnmgl_dc_dialogTpl, skqksnmgl_fh_dialogTpl,skqksnmgl_dcjl_dialogTpl,dataModoule){
	return {
		skqksnmgl_self : null,
		showPage : function(){
			skqksnmgl_self = this;
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(skqksnmglTpl));
			comm.initSelect('#search_mcrType', comm.lang("toolmanage").mcrTypeEnum, null, null, {name:'', value:''});
			comm.initSelect('#search_storeStatus', comm.lang("toolmanage").storeStatusEnum, null, null, {name:'', value:''});
			/*绑定查询按钮*/
			$('#queryBtn').click(function(){
				skqksnmgl_self.initData();
			});
			/*绑定添加按钮*/
			$('#scksnxlh_btn, #scksnxlh_btn2').click(function(){
				$('#dialogBox_sc').html(_.template(skqksnmgl_sc_dialogTpl));
				var targetId= $(this).attr('id') ,title =null;
				switch(targetId){
					case 'scksnxlh_btn':
						$("#skqfl_2").val('积分刷卡器');
						title ="生成积分刷卡器KSN码";
						$('#table_jfskfl').removeClass('tabNone');
						$('#table_xfskfl').addClass('tabNone');
						break;
					case 'scksnxlh_btn2':
						$("#skqfl_2").val('消费刷卡器');
						title ="上传消费刷卡器KSN码";
						$('#table_jfskfl').addClass('tabNone');
						$('#table_xfskfl').removeClass('tabNone');
						break;	
				}
				/*弹出框*/
				$( "#dialogBox_sc" ).dialog({
					title:title,
					width:"500",
					modal:true,
					buttons:{ 
						"确定":function(){
							var skqfl_2 = $('#skqfl_2').val();
							var obj = {category: skqfl_2, num : 0};
							if (targetId == 'scksnxlh_btn'){
								if(!skqksnmgl_self.validateData_1().form()){
									return;
								}
								obj.num = parseInt($('#quantity').val());
								$(this).dialog( "destroy" );
								var confirmObj = {	
									width : 450,
									imgFlag : 'true',
									imgClass: 'tips_ques' ,
									content : '你确认要生成' + skqfl_2 + '数量：<span class="red">' + obj.num + '</span> ？',	
									callOk :function(){									
										$('#dialogBox_fh').html(_.template(skqksnmgl_fh_dialogTpl, obj));									
										/*弹出框*/
										$( "#dialogBox_fh" ).dialog({
											title:"生成"+skqfl_2+"KSN码复核",
											width:"600",
											height:"300",
											modal:true,
											buttons:{ 
												"确定":function(){
													if(!skqksnmgl_self.validateData_2().form()){
														return;
													}
													//验证双签
													comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
														dataModoule.createPointKSNInfo({number:obj.num}, function(res){
															$("#dialogBox_fh").dialog("destroy");
															comm.alert({
																width:600,
																imgClass : 'tips_yes',	
																content : '已生成' + obj.category + '，数量：' + obj.num + '，批次号：'+res.data+'！',
																callOk:function(){
																	skqksnmgl_self.initData();
																}
															});
														});
													});
												},
												"取消":function(){
													 $( this ).dialog( "destroy" );
												}
											}
										});
										/*end*/
										
										comm.initSelect("#verificationMode_fh", comm.lang("common").verificationMode, 185, '1').change(function(e){
											var val = $(this).attr('optionValue');
											switch(val){
												case '1':
													$('#fhy_userName_fh, #fhy_password_fh').removeClass('none');
													$('#verificationMode_prompt_fh').addClass('none');
													break;	
													
												case '2':
													$('#fhy_userName_fh, #fhy_password_fh').addClass('none');
													$('#verificationMode_prompt_fh').removeClass('none');
													break;
													
												case '3':
													$('#fhy_userName_fh, #fhy_password_fh').addClass('none');
													$('#verificationMode_prompt_fh').removeClass('none');
													break;
											}
										});
									}
								}
								comm.confirm(confirmObj);
							} else {
								if(!skqksnmgl_self.validateData_file().form()){
									return;
								}
								if(!skqksnmgl_self.isTXT($("input[name='ksnY']").val())){
									comm.error_alert(comm.lang("toolmanage")[36260]);
									return false;
								}
								if(!skqksnmgl_self.isTXT($("input[name='ksnE']").val())){
									comm.error_alert(comm.lang("toolmanage")[36263]);
									return false;
								}
								if(!skqksnmgl_self.isTXT($("input[name='ksnS']").val())){
									comm.error_alert(comm.lang("toolmanage")[36266]);
									return false;
								}
								if(!skqksnmgl_self.isTXT($("input[name='ksnQ']").val())){
									comm.error_alert(comm.lang("toolmanage")[36269]);
									return false;
								}
								var files = ['ksnY', 'ksnE', 'ksnS', 'ksnQ'];
								var url = comm.domainList[comm.getProjectName()]+comm.UrlList["parseKSNSeq"];
								comm.uploadFile(url, files, function(data){
									obj.num = data.length;
									$('#dialogBox_fh').html(_.template(skqksnmgl_fh_dialogTpl, obj));
									$("#dialogBox_fh").dialog({
										title:"生成"+skqfl_2+"KSN码复核",
										width:"600",
										height:"300",
										modal:true,
										buttons:{ 
											"确定":function(){
												if(!skqksnmgl_self.validateData_2().form()){
													return;
												}
												//验证双签
												comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
													dataModoule.importConsumeKSNInfo({beanJson:JSON.stringify(data)}, function(res){
														$("#dialogBox_fh").dialog("destroy");
														$("#dialogBox_sc").dialog("destroy");
														comm.alert({
															width:600,
															imgClass : 'tips_yes',	
															content : '已导入' + obj.category + '，数量：' + obj.num + '.',
															callOk:function(){
																skqksnmgl_self.initData();
															}
														});  
													});
												});
											},
											"取消":function(){
												 $( this ).dialog( "destroy" );
											}
										}
									});
									
									comm.initSelect("#verificationMode_fh", comm.lang("common").verificationMode, 185, '1').change(function(e){
										var val = $(this).attr('optionValue');
										switch(val){
											case '1':
												$('#fhy_userName_fh, #fhy_password_fh').removeClass('none');
												$('#verificationMode_prompt_fh').addClass('none');
												break;	
											case '2':
												$('#fhy_userName_fh, #fhy_password_fh').addClass('none');
												$('#verificationMode_prompt_fh').removeClass('none');
												break;
											case '3':
												$('#fhy_userName_fh, #fhy_password_fh').addClass('none');
												$('#verificationMode_prompt_fh').removeClass('none');
												break;
										}
									});
								}, function(res){
									
								}, null);
							} 
						},
						"取消":function(){
							$('#form_upload').find('input,textarea,select').tooltip().tooltip( "destroy" );
							 $( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_mcrType = comm.removeNull($("#search_mcrType").attr('optionValue'));
			params.search_storeStatus = comm.removeNull($("#search_storeStatus").attr('optionValue'));
			dataModoule.findMcrKsnRecordList(params, this.detail);
		},
		/**
		 *导出动作
		 */
		exportContent : function(record, rowIndex, colIndex, options){
	/*		if(colIndex == 0){
				return comm.getNameByEnumId(record['mcrType'], comm.lang("toolmanage").mcrTypeEnum);
			}else if(colIndex == 3){
				return comm.getNameByEnumId(record['storeStatus'], comm.lang("toolmanage").storeStatusEnum);
			}*/
			if(colIndex == 6){
				return $('<a>导出</a>').click(function(e) {
					skqksnmgl_self.daoChu(record);
				}.bind(this));
			}
		},
		
		/**
		 *导出记录
		 */
		exportContentHistory : function(record, rowIndex, colIndex, options){
			if(colIndex == 0 || colIndex == 3){
				return null;
			}
			return $('<a>导出记录</a>').click(function(e) {
				skqksnmgl_self.daoChujl(record);
			}.bind(this));
		},
		
		/**
		 *查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.getNameByEnumId(record['mcrType'], comm.lang("toolmanage").mcrTypeEnum);
			}else if(colIndex == 3){
				return comm.getNameByEnumId(record['storeStatus'], comm.lang("toolmanage").storeStatusEnum);
			}
			return $('<a>查看</a>').click(function(e) {
				skqksnmgl_self.chaKan(record);
			}.bind(this));
		},
		
		//
		/**
		 *查看动作
		 */
		details : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return $('<a>查看</a>').click(function(e) {
					 comm.alert({
						 width : "500",
						 height: "250",
						  title:"导出说明",
						  content:record['remark']
						 });
				}.bind(this));
			}
		},
	   
		
		//
		/**
		 * 查看
		 */
		chaKan : function(obj){
			obj.mcrTypeText = comm.getNameByEnumId(obj['mcrType'], comm.lang("toolmanage").mcrTypeEnum);
			obj.storeStatusText = comm.getNameByEnumId(obj['storeStatus'], comm.lang("toolmanage").storeStatusEnum);
			$('#dialogBox_ck').html(_.template(skqksnmgl_ck_dialogTpl, obj));
			if(obj['mcrType'] == 1){
				dataModoule.findPointKSNInfo({batchNo:obj.batchNo}, function(res){
					comm.getEasyBsGrid("searchTable_ck1", res.data);
				});
			}else{
				dataModoule.findConsumeKSNInfo({batchNo:obj.batchNo}, function(res){
					comm.getEasyBsGrid("searchTable_ck2", res.data);
				});
			}
			$("#dialogBox_ck").dialog({
				title:"",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			$('#searchTable_ck1_pt, #searchTable_ck2_pt').addClass('td_nobody_r_b');
		},
		/**
		 * 导出
		 */
		daoChu : function(obj){
			$('#dialogBox_dc').html(_.template(skqksnmgl_dc_dialogTpl, obj));
			comm.initSelect("#verificationMode_dc", comm.lang("common").verificationMode, 185, '1').change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName_dc, #fhy_password_dc').removeClass('none');
						$('#verificationMode_prompt_dc').addClass('none');
						break;	
					case '2':
						$('#fhy_userName_dc, #fhy_password_dc').addClass('none');
						$('#verificationMode_prompt_dc').removeClass('none');
						break;
					case '3':
						$('#fhy_userName_dc, #fhy_password_dc').addClass('none');
						$('#verificationMode_prompt_dc').removeClass('none');
						break;
				}
			});
			$( "#dialogBox_dc" ).dialog({
				title:"刷卡器KSN码导出",
				width:"600",
				height:"420",
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!skqksnmgl_self.validateData_3().form()){
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#exp_pk_double_username").val(), $("#exp_pk_double_password").val(), 1, function(verifyRes){
							var params = {};
							if(obj.mcrType == 1){
								window.open(dataModoule.exportPointKSNInfo(obj.batchNo, "积分KSN信息-"+obj.batchNo, $("#exp_remark").val()), '_blank');
							}else{
								window.open(dataModoule.exportConsumeKSNInfo(obj.batchNo, "消费KSN信息-"+obj.batchNo, $("#exp_remark").val()), '_blank');
							}
							$("#dialogBox_dc").dialog("destroy");
						});
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			});
		},
		//

		 daoChujl : function(obj){
				$('#dialogBox_dcjl').html(skqksnmgl_dcjl_dialogTpl);
				
				
				
				
				
				dataModoule.queryKsnExportRecordList({batchNo:obj.batchNo}, function(res){
			comm.getEasyBsGrid("searchTable_dcjl", res.data,skqksnmgl_self.details);
			
				if(res.data.length==0){
					
					comm.alert({
						imgFlag : true ,
						imgClass : 'tips_yes' ,
						content : comm.lang("toolmanage").noDaoChuJl
					});
					return false;
				}
				
				/*弹出框*/
				$( "#dialogBox_dcjl" ).dialog({
					title:"历史导出记录",
					width:"850",
					height:"420",
					modal:true,
					buttons:{ 
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				});
				
				
								
				
		     
			   },
		
		
			   
			
		
		//
		/**
		 * 表单校验-数量
		 */
		validateData_1 : function(){
			return $("#form_one").validate({
				rules : {
					quantity : {
						required : true,
						digits : true,
						min:1
					}
				},
				messages : {
					quantity : {
						required : comm.lang("toolmanage")[36255],
						digits : comm.lang("toolmanage").validDigitsNumber,
						min: comm.lang("toolmanage").scQuantityMin
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
		 * 表单校验-双签
		 */
		validateData_2 : function(){
			return $("#form_two").validate({
				rules : {
					pk_double_username : {
						required : true
					},
					pk_double_password : {
						required : true
					},
					exp_remark:{
						maxlength : 300
					}
				},
				messages : {
					pk_double_username : {
						required : comm.lang("toolmanage")[36200]
					},
					pk_double_password : {
						required : comm.lang("toolmanage")[36201]
					},
					exp_remark:{
						maxlength : comm.lang("toolmanage").maxlength
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
		 * 表单校验-双签
		 */
		validateData_3 : function(){
			return $("#form_three").validate({
				rules : {
					exp_pk_double_username : {
						required : true
					},
					exp_pk_double_password : {
						required : true
					},
					exp_remark : {
						maxlength : 300
					}
				},
				messages : {
					exp_pk_double_username : {
						required : comm.lang("toolmanage")[36200]
					},
					exp_pk_double_password : {
						required : comm.lang("toolmanage")[36201]
					},
					exp_remark : {
						maxlength : comm.lang("toolmanage")[36277]
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
		 * 表单校验-文件
		 */
		validateData_file : function(){
			return $("#form_upload").validate({
				rules : {
					ksnY : {
						required : true
					},
					ksnE : {
						required : true
					},
					ksnS : {
						required : true
					},
					ksnQ : {
						required : true
					}
				},
				messages : {
					ksnY : {
						required : comm.lang("toolmanage")[36256]
					},
					ksnE : {
						required : comm.lang("toolmanage")[36257]
					},
					ksnS : {
						required : comm.lang("toolmanage")[36258]
					},
					ksnQ : {
						required : comm.lang("toolmanage")[36259]
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
		/***
		 * 判断文件是否为txt
		 */
		isTXT : function(filepath){
            var ext = filepath.substring(filepath.lastIndexOf("."), filepath.length).toUpperCase();
            if (ext != ".TXT") {
            	return false;
            }
            return true;
		},
	}	
});