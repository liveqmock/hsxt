define([
"text!scoremanageTpl/jfflff/trsgbtff.html",
"text!scoremanageTpl/jfflff/ff.html",
"text!scoremanageTpl/jfflff/sq_dialog.html",
"text!scoremanageTpl/jfflff/jfflff_gq_dialog.html",
'scoremanageDat/pointWelfare',
'scoremanageLan'
],function(trsgbtffTpl,ffTpl,sq_dialogTpl,jfflff_gq_dialog,pointWelfare){
 
	var self = {
			showPage : function(){
				$('#busibox').html(_.template(trsgbtffTpl));	
				/** 查询事件 */
				$("#qry_jfflff").click(function(){
					self.pageQuery();
				});
			},
		/** 分页查询 */
		pageQuery:function(){
			var welfareType=$(".selectList-active").attr("data-Value");
			//查询参数
			var queryParam={
							"search_welfareType":"2",	//int	否	福利类型 0 意外伤害 1 免费医疗 2 他人身故
							"search_proposerResNo":$("#proposerResNo").val().trim(),	//申请人互生号
							"search_proposerName":$("#proposerName").val().trim(),	//申请人姓名
							"search_proposerPapersNo":$("#proposerPapersNo").val().trim(),		//申请人证件号码
							"search_givingPersonCustId":$.cookie("custId")	//发放人客户号
						};
			pointWelfare.listPendingGrant("searchTable",queryParam,self.detail,self.hangUp,self.del);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				record['welfareTypeStr'] = comm.getNameByEnumId(record['welfareType'], comm.lang('pointWelfare').welfareType);
				return comm.getNameByEnumId(record['welfareType'], comm.lang('pointWelfare').welfareType);
			}
			if(colIndex == 5){
				return comm.formatMoneyNumberAps(record['subsidyBalance']);
			}
			if(colIndex == 6){
				return comm.formatMoneyNumberAps(record['hsPayAmount']);
			}
			if(colIndex == 7){
				var link1 = $('<a>发放</a>').click(function(e) {
					self.faFang(record,'发放');
				}) ;
				return   link1 ;
			}
			/*if(colIndex==8){
				var link1 = $('<a>挂起</a>').click(function(e) {
					comm.i_confirm(comm.lang('pointWelfare').trsuspendGrant,function(){
						pointWelfare.updateWsTask({bizNo:record.taskId,taskStatus:4,remark:comm.getCookie('custId')},function(res){
							$("#qry_jfflff").click();
							comm.warn_alert(comm.lang('pointWelfare').suspendSucess);
						});	
					});
				});
				return  link1 ;
			}*/
		},
		
		//挂起
		hangUp : function(record, rowIndex, colIndex, options){
			if(colIndex==8){
				var link = $('<a>'+comm.lang("pointWelfare").hangUp+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("pointWelfare").ywsuspendGrant,function(){
						$('#dialogBox_fh_sq').html(_.template(jfflff_gq_dialog));
						$( "#dialogBox_fh_sq" ).dialog({
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
											$( '#dialogBox_fh_sq' ).dialog( "destroy" );
										 	pointWelfare.updateWsTask({bizNo:record.taskId,taskStatus:4,remark:$('#remark').val()},function(res){
										 		comm.yes_alert(comm.lang("pointWelfare").suspendSucess,null,function(){
									    			$("#qry_jfflff").click();
												});
											});	
										}
									});
								},
								"取消":function(){
									$('#gdgqUserName, #gdgqPassWord, #remark').tooltip().tooltip("destroy");
									$( '#dialogBox_fh_sq' ).dialog( "destroy" );
								}
							}
						});
						/*下拉列表*/
						comm.initSelect("#verificationMode",
							comm.lang("pointWelfare").verificationMode).change(function(e){
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
		},
		
		validateGq : function(){
			return comm.valid({
				formID : '#ptdggjfh_gq_sq_form',
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
						required : comm.lang("pointWelfare").apprUserNameIsNotNull
					},
					gdgqPassWord:{
						required : comm.lang("pointWelfare").apprPassWordIsNotNull
					},
					remark:{
						maxlength : comm.lang("pointWelfare").remarkLength
					}
				}
			});
		},
		
		
		del : function(record, rowIndex, colIndex, options){
			if(colIndex==8){
				var link1 = $('<a>拒绝受理</a>').click(function(e) {
					comm.i_confirm(comm.lang('pointWelfare').trrefuseGrant,function(){
						pointWelfare.updateWsTask({bizNo:record.taskId,taskStatus:1,remark:comm.getCookie('custId')},function(res){
							$("#qry_jfflff").click();
							comm.warn_alert(comm.lang('pointWelfare').refuseSucess);
						});
					});
				});
				return  link1 ;
			}
		},
		validateViewMarkData : function(){
			var verificationMode = $("#verificationMode_ff").attr('optionValue');
			if(verificationMode != "1"){
				comm.warn_alert("请选择验证方式!");
				return false;
			}
			return $("#valid_pwd_form").validate({
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
						required : comm.lang("pointWelfare")[36047],
					},
					doublePassword : {
						required : comm.lang("pointWelfare")[36048],
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
		faFang : function(record, str){
			//lyh 20160322
			$('#ywshbtff_detail').html(_.template(ffTpl,{record:record, str:str})).append(_.template(sq_dialogTpl,{record:record}));
	        $('#ywshbtff_detail').removeClass('none');
			$('#trsgbtffTpl').addClass('none');
			$('#ffTpl').removeClass('none');
			$('#busibox').addClass('none');
			
			comm.liActive_add($('#ff'));
			//返回按钮
			$('#trsg_ff_cancel').click(function(){
				//隐藏头部菜单
				$('#ywshbtff_detail').addClass('none');
				$('#ffTpl').addClass('none');
				$('#trsgbtffTpl').removeClass('none');
				$('#busibox').removeClass('none');
				$('#ff').addClass("tabNone").find('a').removeClass('active');
				$('#trsgbtff').find('a').addClass('active');
			});
			
			
			$('#ff_tj').click(function(){
				var grantRemark = $.trim($("#grantRemark").val());
				if(grantRemark==''){
					comm.i_alert('发放说明不能为空');
					return false;
				}else{
					if(grantRemark.length>1000){
						comm.error_alert(comm.lang("pointWelfare").butieMaxLength);
						return false;
					}
				}
				
				$( "#sq_dialogTpl" ).dialog({
					title:"双签",
					width:"380",
					height:"350",
					modal:true,
					buttons:{ 
						"确定":function(){
							
							if(!self.validateViewMarkData().form()){
								return;
							}
							//验证双签
							comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
								 var approvalParam={
									"grantId":record.givingId,	//审批编号
									"remark": $.trim($("#grantRemark").val()),	//审批备注信息
								 };
								 pointWelfare.grantWelfare(approvalParam,function(rsp){
									$("#sq_dialogTpl").dialog( "destroy" );
									comm.yes_alert(comm.lang("pointWelfare").grantSucess);
									$('#jfflffjlcx').click();
									//$(sh_back).click();
								 });
								
							});
						},
						"取消":function(){
							$( "#sq_dialogTpl" ).dialog( "destroy" );
						}
					}
				});	
				
				/*下拉列表*/
				$("#verificationMode_ff").selectList({
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
							$('#fhy_userName_ff, #fhy_password_ff').removeClass('none');
							$('#verificationMode_prompt_ff').addClass('none');
							break;	
							
						case '2':
							$('#fhy_userName_ff, #fhy_password_ff').addClass('none');
							$('#verificationMode_prompt_ff').removeClass('none');
							break;
							
						case '3':
							$('#fhy_userName_ff, #fhy_password_ff').addClass('none');
							$('#verificationMode_prompt_ff').removeClass('none');
							break;
					}
				});
				/*end*/	
				
			});
			
		}
	} ;
	return self;
});