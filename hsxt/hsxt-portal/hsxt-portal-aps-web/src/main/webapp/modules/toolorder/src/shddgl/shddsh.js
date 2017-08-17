define(['text!toolorderTpl/shddgl/shddsh.html',
		'text!toolorderTpl/shddgl/shddsh_sh_dialog.html',
		'text!toolorderTpl/shddgl/shddsh_ck_dialog.html',
		'text!toolorderTpl/shddgl/shddsh_gq_sq_dialog.html',
		'toolorderDat/toolorder',
		"commDat/common",
		'toolorderLan'
		], function(shddshTpl, shddsh_sh_dialogTpl, shddsh_ck_dialogTpl,shddsh_gq_sq_dialogTpi, dataModule, commonAjax){
	var self = {
		showPage : function(){
			$('#busibox').html(_.template(shddshTpl));

			//时间控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');

			/*下拉列表*/
			comm.initSelect('#apprStatus', comm.lang("toolorder").apprStatusSelector,null,null,{name:'全部', value:''});

			/*end*/

			/*表格数据模拟*/
			$("#qry_shddsh_btn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_startDate		: $("#search_startDate").val().trim(),			//
					search_endDate			: $("#search_endDate").val().trim(),			//
					search_hsCustName		: $("#hsCustName").val().trim(),		//
					search_hsResNo			: $("#hsResNo").val().trim()
				};
				var apprStatus = $("#apprStatus").attr("optionvalue");
				if(apprStatus){
					params.search_apprStatus = apprStatus;
				}

				searchTable = comm.getCommBsGrid("searchTable","queryAfterOrderApprPage",params,comm.lang("toolorder"),self.detail,self.del);
			});

			/*end*/

		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==4){
				return comm.lang("toolorder").apprStatusSelector[record.status];
			}
			if(colIndex==5){
				return comm.lang("toolorder").payStatusSelector[record.payStatus];
			}
			if(colIndex==7){
				var obj = searchTable.getRecord(rowIndex);
				var	link1 = null;

				if(searchTable.getColumnValue(rowIndex, 'status') == '1')	{

					link1 = $('<a>查看</a>').click(function(e) {
						var dataObj=null;
						$('#dialogBox').html(_.template(shddsh_ck_dialogTpl, obj));
						/*表格数据模拟*/
						dataModule.queryAfterServiceByNo({orderNo:obj.afterOrderNo},function(res){
							if(res.retCode==22000){
								dataObj = res.data;
								var listObj = res.data.detail;
								uploadGridObj = comm.getEasyBsGrid("searchTable_1",listObj,function(record, rowIndex, colIndex, options){
									if(colIndex == '2'){
										return comm.lang("toolorder").categoryCode[record.categoryCode];
									}
									if(colIndex == '3'){
										return comm.lang("toolorder").disposeTypeEnum[record.disposeType];
									}
								});
								$("#reqOperator").html(dataObj.reqOperator);
								$("#reqRemark").html(dataObj.reqRemark);
						
								$("#apprRemark").html(dataObj.apprRemark);
								$("#payStatus").html(comm.lang("toolorder").payStatusSelector[obj.payStatus]);
							}
						});

						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"售后订单查看",
							width:"900",
							closeIcon : true,
							modal:true,
							buttons:{
								"关闭":function(){
									$( this ).dialog( "destroy" );
								}
							}
						});
						/*end*/


						$('#searchTable_1_pt').addClass('td_nobody_r_b');


					}) ;
				}else if(searchTable.getColumnValue(rowIndex, 'status') == '0'){
					link1 = $('<a>审批</a>').click(function(e) {
						var dataObj=null;
						$('#dialogBox').html(_.template(shddsh_sh_dialogTpl, obj));
						/*表格数据模拟*/
						dataModule.queryAfterServiceByNo({orderNo:obj.afterOrderNo},function(res){
							if(res.retCode==22000){
								dataObj = res.data;
								var listObj = res.data.detail;
								uploadGridObj = comm.getEasyBsGrid("searchTable_1",listObj,function(record, rowIndex, colIndex, options){
									if(colIndex == '2'){
										return comm.lang("toolorder").categoryCode[record.categoryCode];
									}
									if(colIndex == '3'){
										var type = record.disposeType;
										var str = '<select id="disposeType'+rowIndex+'">';
										str +='<option value="">-- 选择 --</option>';
										str +='<option value="1"';
										if(type=='1'){
											str += 'selected=true ';
										}
										str +='>无故障</option>';
										str +='<option value="2"';
										if(type=='2'){
											str += 'selected=true ';
										}
										str += '>重新配置</option>';
										str +='<option value="3"';
										if(type=='3'){
											str += 'selected=true ';
										}
										str += '>换货</option>';
										str +='<option value="4"';
										if(type=='4'){
											str += 'selected=true ';
										}
										str += '>维修</option></select>';
										return str;
									}
									if(colIndex == '4'){
										var amt = record.disposeAmount?record.disposeAmount:0;
										return '<input type="text" id="disposeAmount'+rowIndex+'" value="'+ amt +'" class="infobox pl5 pr5 w100" placeholder="输入维修补款金额" onkeyup="if(isNaN(value))execCommand(\'undo\')" onafterpaste="if(isNaN(value))execCommand(\'undo\')" />';
									}

								});
								$("#reqOperator").html(dataObj.reqOperator);
								$("#reqRemark").html(dataObj.reqRemark);

								$("#apprRemark").html(dataObj.apprRemark);
								$("#payStatus").html(comm.lang("toolorder").payStatusSelector[obj.payStatus]);
							}
						});
						$("#verificationMode_qy").selectList({
							width : 180,
							optionWidth : 185,
							options:[
								{name:'密码验证',value:'1'},
								{name:'指纹验证',value:'2'},
								{name:'刷卡验证',value:'3'}
							]
						}).change(function(e){
							var val = $(this).attr('optionValue');
							switch(val){
								case '1':
									$('#fhy_userName_qy, #fhy_password_qy').removeClass('none');
									$('#verificationMode_prompt_qy').addClass('none');
									break;

								case '2':
									$('#fhy_userName_qy, #fhy_password_qy').addClass('none');
									$('#verificationMode_prompt_qy').removeClass('none');
									break;

								case '3':
									$('#fhy_userName_qy, #fhy_password_qy').addClass('none');
									$('#verificationMode_prompt_qy').removeClass('none');
									break;
							}
						});
						/*end*/


						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"售后订单审核",
							width:"900",
							height:'620',
							closeIcon : true,
							modal:true,
							buttons:{
								"提交":function(){
									if(uploadGridObj == null ){
										return;
									}

									var validTypeObj = $("#verificationMode_qy").attr("optionvalue");
									if(!validTypeObj){
										comm.error_alert(comm.lang("toolorder").confirmType);
										return;
									}
									var batchParam = {
										afterOrderNo:	dataObj.afterOrderNo,
										exeCustId	:	$.cookie('custId'),
										status		:	'1',
										apprRemark	:	$("#apprRemark").val().trim(),
										apprOperator:	$.cookie('custName')
									};

									var objAry = uploadGridObj.getAllRecords();
									var uploadAry = [];
									for(var i=0;i<objAry.length;i++){
										var obj = objAry[i];

										var str="";
										if(obj.deviceSeqNo){
											str += obj.deviceSeqNo+";";
										}else{
											str += ";";
										}

										if(obj.categoryCode){
											str += obj.categoryCode+";";
										}else{
											str += ";";
										}

										if($("#disposeType"+i).val()!=""){
											str += $("#disposeType"+i).val()+";";
										}else{
											comm.error_alert(comm.lang("toolorder").pleaseSelectDisposeType);
											return;
										}

										if($("#disposeAmount"+i).val()!=""){
											str += $("#disposeAmount"+i).val()+";";
										}else{
											comm.error_alert(comm.lang("toolorder").pleaseInputDisposeAmount);
											return;
										}

										if(obj.disposeStatus){
											str += obj.disposeStatus+";";
										}else{
											str += ";";
										}

										if(obj.productId){
											str += obj.productId;
										}

										uploadAry.push(str);//obj.entResNo+","+obj.terminalNo+","+obj.deviceSeqNo+","+obj.deviceFalg);
									}
									batchParam.listDetail = encodeURIComponent(JSON.stringify(uploadAry));


									comm.verifyDoublePwd($("#optUserId").val(),$("#pwd").val(),validTypeObj,function(res){
										dataModule.apprAfterService(batchParam,function(res){
											comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
												$("#dialogBox").dialog( "destroy" );
												$('#qry_shddsh_btn').click();
											}});
										});
									});
								},
								"取消":function(){
									$( this ).dialog( "destroy" );
								}
							}
						});
						/*end*/
						/*下拉列表*/
						comm.initSelect('#validType', comm.lang("toolorder").verificationMode);
						$("#validType").change(function(e){
							var val = $(this).attr('optionValue');
							switch(val){
								case '1':
									$('#fhyPwd_plcxgl').removeClass('none');
									$('#verificationMode_prompt_plcxgl').addClass('none');
									break;

								case '2':
									$('#fhyPwd_plcxgl').addClass('none');
									$('#verificationMode_prompt_plcxgl').removeClass('none');
									break;

								case '3':
									$('#fhyPwd_plcxgl').addClass('none');
									$('#verificationMode_prompt_plcxgl').removeClass('none');
									break;
							}
						});
						/*end*/
						$('#searchTable_1_pt').addClass('td_nobody_r_b');
					});
				}
				return link1;
			}

			if(colIndex==8){
				if(searchTable.getColumnValue(rowIndex, 'status') == '0'){
					var link = $('<a>'+comm.lang("toolorder").hangUp+'</a>').click(function(e) {
						comm.i_confirm(comm.lang("toolorder").optHangUp,function(){
							$('#dialogBox_gdgq').html(_.template(shddsh_gq_sq_dialogTpi));
							$( "#dialogBox_gdgq" ).dialog({
								title:"工单挂起",
								width:"800",
								height:"340",
								modal:true,
								buttons:{
									"挂起":function(){
										if($('#verificationMode').attr('optionValue')!='1'){
											return;
										}
										if(!self.validateGq()){
											return;
										}
										comm.verifyDoublePwd($("#gdgqUserName").val(), $("#gdgqPassWord").val(), 1, function(resp){
											if(resp){
												$( '#dialogBox_gdgq' ).dialog( "destroy" );
												commonAjax.workTaskHangUp({bizNo:record.afterOrderNo},function(resp){
													comm.yes_alert(comm.lang("toolorder").workTaskHangUp,null,function(){
														$("#qry_shddsh_btn").click();
													});
												});
											}
										});
									},
									"取消":function(){
										$('#gdgqUserName, #gdgqPassWord, #remark').tooltip().tooltip("destroy");
										$( '#dialogBox_gdgq' ).dialog( "destroy" );
									}
								}
							});
							/*下拉列表*/
							comm.initSelect("#verificationMode",
								comm.lang("toolorder").verificationMode).change(function(e){
								var val = $(this).attr('optionValue');
								switch(val){
									case '1':
										$('#gdgqUserName_li, #gdgqPassWord_li').removeClass('none');
										$('#verificationMode_prompt').addClass('none');
										break;

									case '2':
										$('#gdgqUserName_li, #gdgqPassWord_li').addClass('none');
										$('#verificationMode_prompt').removeClass('none');
										break;

									case '3':
										$('#gdgqUserName_li, #gdgqPassWord_li').addClass('none');
										$('#verificationMode_prompt').removeClass('none');
										break;
								}
							});
							$("#verificationMode").selectListValue("1");
						});
					});
					return link;
				}
			}
		},
		del	:	function(record, rowIndex, colIndex, options){
			if(colIndex==8){
				if(searchTable.getColumnValue(rowIndex, 'status') == '0'){
					var link = $('<a>'+comm.lang("toolorder").refuseAccept+'</a>').click(function(e) {
						comm.i_confirm(comm.lang("toolorder").optRefuseAccept,function(){
							commonAjax.workTaskRefuseAccept({bizNo:record.afterOrderNo},function(resp){
								comm.yes_alert(comm.lang("toolorder").workTaskRefuseAcceptSucc,null,function(){
									$("#qry_shddsh_btn").click();
								});
							});
						});
					});
					return link;
				}
			}
		},
		validateGq : function(){
			return comm.valid({
				formID : '#shddsh_gq_sq_form',
				rules : {
					gdgqUserName:{
						required : true
					},
					gdgqPassWord:{
						required : true
					},
					remark:{
						maxlength : 300
					}
				},
				messages : {
					gdgqUserName:{
						required : comm.lang("toolorder").apprUserNameIsNotNull
					},
					gdgqPassWord:{
						required : comm.lang("toolorder").apprPassWordIsNotNull
					},
					remark:{
						maxlength : comm.lang("toolorder").remarkLength300
					}
				}
			});
		}
	};
	return self;
});