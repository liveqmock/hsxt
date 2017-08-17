/*积分福利初审*/
define([
"text!scoremanageTpl/jfflfh/jfflfh.html",
"text!scoremanageTpl/jfflfh/jfflfh_sp1.html",
"text!scoremanageTpl/jfflfh/jfflfh_sp2.html",
"text!scoremanageTpl/jfflfh/jfflfh_sp3.html",
"text!scoremanageTpl/jfflfh/sh_dialog.html",
"text!scoremanageTpl/jfflfh/jffjfh_gq_dialog.html",
'scoremanageDat/pointWelfare',
"commDat/common",
'scoremanageLan'
],function(jfflspTpl,jfflsp_sp1Tpl,jfflsp_sp2Tpl,jfflsp_sp3Tpl,sh_dialogTpl,jfflfh_gq_dialog,pointWelfare,commonAjax){
	
	var self = {
			showPage : function(){
				$('#busibox').html(_.template(jfflspTpl)).append(sh_dialogTpl);	
				self = this;
				//时间控件		    
			    comm.initBeginEndTime('#search_startDate','#search_endDate');
				/*end*/	
				
				/*下拉列表*/
				$("#welfareType").selectList({
					width : 150,
					optionWidth : 155,
					options:[
						{name:'互生意外伤害保障',value:'0'},
						{name:'互生医疗补贴计划',value:'1'},
						{name:'代他人申请身故保障金',value:'2'}
					]
				});
				/*end*/
				
				/** 查询事件 */
				$("#qry_jfflsp_btn").click(function(){
					self.pageQuery();
				});
				/*end*/	
			},
		foramtSubsidyBalance : function(welfareType,subsidyBalance){
			if(welfareType == 0||welfareType == 2){
				return comm.formatMoneyNumberAps(subsidyBalance);
			}
			return '-';
		},
		/** 分页查询 */
		pageQuery:function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var welfareType=$(".selectList-active").attr("data-Value");
			//查询参数
			var queryParam={
							"search_welfareType":welfareType!=undefined?welfareType:"",	//int	否	福利类型 0 意外伤害 1 免费医疗 2 他人身故
							"search_appovalStep":1,	//int	是	审批步骤： 0初审 1复审
							"search_startTime":$("#search_startDate").val(),		//String	否	开始查询时间(申请时间) 
							"search_endTime":$("#search_endDate").val(),		//String	否	结束查询时间(申请时间)
							"search_proposerResNo":$("#proposerResNo").val().trim(),	//String	否	申请人互生号 
							"search_proposerName":$("#proposerName").val().trim(),	//String	否	申请人姓名
							"search_approvalCustId":$.cookie("custId")	//String	是	审批人客户号
						};
			pointWelfare.listPendingApproval("searchTable",queryParam,self.detail,self.hangUp,self.del);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['welfareType'], comm.lang('pointWelfare').welfareType);
			}
			if(colIndex == 7){
				return self.foramtSubsidyBalance(record['welfareType'], record['subsidyBalance']);
			}
			if(colIndex == 8){
				return comm.getNameByEnumId(record['approvalStatus'], comm.lang('pointWelfare').approvalStatus);
			}
			if(colIndex == 10){
				var link1 = $('<a>审批</a>').click(function(e) {
					if(record['welfareType'] == 0){
						self.shenPi_1(record);
					}
					else if(record['welfareType'] == 2){
						self.shenPi_2(record);
					}
					else if(record['welfareType'] == 1){
						self.shenPi_3(record);
					}
				}) ;
				return   link1 ;
			}
		/*	if(colIndex==11){
				var link1 = $('<a>挂起</a>').click(function(e) {
					comm.i_confirm(comm.lang('pointWelfare').fhsuspendApproval,function(){
						pointWelfare.updateWsTask({bizNo:record.taskId,taskStatus:4,remark:comm.getCookie('custId')},function(res){
							$("#qry_jfflsp_btn").click();
							comm.warn_alert(comm.lang('pointWelfare').suspendSucess);
						});	
					});
				});
				return  link1 ;
			}*/
		},
		
		//挂起
		hangUp : function(record, rowIndex, colIndex, options){
			if(colIndex==11){
				var link = $('<a>'+comm.lang("pointWelfare").hangUp+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("pointWelfare").fhsuspendApproval,function(){
						$('#dialogBox_fh_sq').html(_.template(jfflfh_gq_dialog));
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
													$("#qry_jfflsp_btn").click();
												});
											});	
											/*commonAjax.workTaskHangUp({bizNo:record.approvalId,taskStatus:4,remark:$('#remark').val()},function(resp){
												comm.yes_alert(comm.lang("toolmanage").workTaskHangUp,null,function(){
													 self.initData();
												});
											});*/
										}
									});
								},
								"取消":function(){
									$('#gdgqUserName, #gdgqPassWord, #remark').tooltip().tooltip("destroy");
									$( '#dialogBox_fh_sq' ).dialog( "destroy" );
								}
							}
						});
						//下拉列表
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
			if(colIndex==11){
				var link1 = $('<a>拒绝受理</a>').click(function(e) {
					comm.i_confirm(comm.lang('pointWelfare').fhrefuseApproval,function(){
						pointWelfare.updateWsTask({bizNo:record.taskId,taskStatus:1,remark:comm.getCookie('custId')},function(res){
							$("#qry_jfflsp_btn").click();
							comm.warn_alert(comm.lang('pointWelfare').refuseSucess);
						});
					});
				});
				return  link1 ;
			}
		},
		shenPi_1 : function(record){
			var self=this;
			self.getApprovalDetail(record["approvalId"],function(obj){
				if(obj.welfareQualify.effectDate==null){
					obj.welfareQualify.effectDate = "";
				}
				obj.firstApprovalInfo.subsidyBalance=self.foramtSubsidyBalance(obj.applyInfo.welfareType,obj.firstApprovalInfo.subsidyBalance);
				$('#jfflsp_detail').html(_.template(jfflsp_sp1Tpl,obj))+sh_dialogTpl;
				//互生卡正面
				self.showImg('hscPositivePath',obj.applyInfo.hscPositivePath);
				//互生卡背面
				self.showImg('hscReversePath',obj.applyInfo.hscReversePath);
				//身份证正面 
				self.showImg('cerPositivePath',obj.applyInfo.cerPositivePath);
				//身份证背面
				self.showImg('cerReversePath',obj.applyInfo.cerReversePath);
				//医疗证明
				self.showImg('medicalProvePath',obj.applyInfo.medicalProvePath);
				//其他证明
				//self.showImg('otherProvePath',obj.applyInfo.otherProvePath);
				if(obj.applyInfo.otherProvePath!=null){
					for(var i=0;i<obj.applyInfo.otherProvePath.length;i++){
						var aid="otherProvePath"+i;
						var spanid= "otherProveSpan"+i;
						self.showImg(aid,obj.applyInfo.otherProvePath[i]);
						$("#"+spanid).show();
					}
				}
				
				//消费者本人的社会保障卡复印件 
				self.showImg('sscPath',obj.applyInfo.sscPath);
				//原始收费收据复印件(一份
				self.showImg('ofrPath',obj.applyInfo.ofrPath);
				//费用明细清单复印件(一份 
				self.showImg('cdlPath',obj.applyInfo.cdlPath);
				//住院病历复印件(一份
				self.showImg('imrPath',obj.applyInfo.imrPath);
				//疾病诊断证明书复印件(一份
				self.showImg('ddcPath',obj.applyInfo.ddcPath);
				//医保中心的受理回执复印件
				self.showImg('medicalAcceptPath',obj.applyInfo.medicalAcceptPath);
				//医疗费用报销计算表复印件
				self.showImg('costCountPath',obj.applyInfo.costCountPath);
				
				
				if(obj.welfareQualify.allFailureDateList!=null){
					var failureDate='';
					for(var i = 0; i < obj.welfareQualify.allFailureDateList.length; i++){
						failureDate += obj.welfareQualify.allFailureDateList[i]+',';
					}
					failureDate=failureDate.substring(0,failureDate.length-1);
					$("#allFailureDateList").text(failureDate);
				}
				self.showApprovalDialog($('#sh1_btn'),$('#back1_jfflsp'),record["approvalId"]);	
				   //lyh
				$('#jfflsp_detail').removeClass('none');
				$('#jfflspTpl').addClass('none');
				$('#jfflsp_sp1Tpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ck'));
				//返回按钮
				$('#back1_jfflsp').click(function(){
					//隐藏头部菜单
					$('#jfflsp_detail').addClass('none');
					$('#jfflsp_sp1Tpl').addClass('none');
					$('#jfflspTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ck').addClass("tabNone").find('a').removeClass('active');
					$('#jfflsp').find('a').addClass('active');
				});
				
				//审批状态按钮  通过、驳回
				$('input[name="approvalResult"]').change(function(){
					self.approvalResultChange();
				});
			
			});
		},
		shenPi_2 : function(record){
			var self=this;
			self.getApprovalDetail(record["approvalId"],function(obj){
				if(obj.welfareQualify.effectDate==null){
					obj.welfareQualify.effectDate = "";
				}
				obj.firstApprovalInfo.subsidyBalance=self.foramtSubsidyBalance(obj.applyInfo.welfareType,obj.firstApprovalInfo.subsidyBalance);
				$('#jfflsp_detail').html(_.template(jfflsp_sp2Tpl,obj))+sh_dialogTpl;
				//互生卡正面
				self.showImg('hscPositivePath',obj.applyInfo.hscPositivePath);
				//互生卡背面
				self.showImg('hscReversePath',obj.applyInfo.hscReversePath);
				//身份证正面 
				self.showImg('cerPositivePath',obj.applyInfo.cerPositivePath);
				//身份证背面
				self.showImg('cerReversePath',obj.applyInfo.cerReversePath);
				
				//被保障人死亡证明附件 deathProvePath
				self.showImg('deathProvePath',obj.applyInfo.deathProvePath);
				//户籍注销证明 hrcPath
				self.showImg('hrcPath',obj.applyInfo.hrcPath);
				//被保障人法定身份证明 diePeopleCerPath
				self.showImg('diePeopleCerPath',obj.applyInfo.diePeopleCerPath);
				//与被保障人关系证明书 ifpPath
				self.showImg('ifpPath',obj.applyInfo.ifpPath);
				//代办人法定身份证明 aipPath
				self.showImg('aipPath',obj.applyInfo.aipPath);
				//其他证明材料 otherProvePath
				//self.showImg('otherProvePath',obj.applyInfo.otherProvePath);
				if(obj.applyInfo.otherProvePath!=null){
					for(var i=0;i<obj.applyInfo.otherProvePath.length;i++){
						var aid="otherProvePath"+i;
						var spanid= "otherProveSpan"+i;
						self.showImg(aid,obj.applyInfo.otherProvePath[i]);
						$("#"+spanid).show();
					}
				}
			
				self.showApprovalDialog($('#sh2_btn'),$('#back2_jfflsp'),record["approvalId"]);
				//$('#back2_jfflsp').triggerWith('#jfflsp');	
				
				if(obj.welfareQualify.allFailureDateList!=null){
					var failureDate='';
					for(var i = 0; i < obj.welfareQualify.allFailureDateList.length; i++){
						failureDate += obj.welfareQualify.allFailureDateList[i]+',';
					}
					failureDate=failureDate.substring(0,failureDate.length-1);
					$("#allFailureDateList").text(failureDate);
				}
				
				 //lyh
				$('#jfflsp_detail').removeClass('none');
				$('#jfflspTpl').addClass('none');
				$('#jfflsp_sp2Tpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ck'));
				//返回按钮
				$('#back2_jfflsp').click(function(){
					//隐藏头部菜单
					$('#jfflsp_detail').addClass('none');
					$('#jfflsp_sp2Tpl').addClass('none');
					$('#jfflspTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ck').addClass("tabNone").find('a').removeClass('active');
					$('#jfflsp').find('a').addClass('active');
				});
				
				//审批状态按钮  通过、驳回
				$('input[name="approvalResult"]').change(function(){
					self.approvalResultChange();
				});
			
			});
		},
		shenPi_3 : function(record){
			var self=this;
			self.getApprovalDetail(record["approvalId"],function(obj){
				if(obj.welfareQualify.effectDate==null){
					obj.welfareQualify.effectDate = "";
				}
				obj.firstApprovalInfo.subsidyBalance=self.foramtSubsidyBalance(obj.applyInfo.welfareType,obj.firstApprovalInfo.subsidyBalance);
				$('#jfflsp_detail').html(_.template(jfflsp_sp3Tpl,obj))+sh_dialogTpl;
				
				//互生卡正面
				self.showImg('hscPositivePath',obj.applyInfo.hscPositivePath);
				//互生卡背面
				self.showImg('hscReversePath',obj.applyInfo.hscReversePath);
				//身份证正面 
				self.showImg('cerPositivePath',obj.applyInfo.cerPositivePath);
				//身份证背面
				self.showImg('cerReversePath',obj.applyInfo.cerReversePath);
				//消费者本人的社会保障卡复印件 
				self.showImg('sscPath',obj.applyInfo.sscPath);
				//原始收费收据复印件(一份
				self.showImg('ofrPath',obj.applyInfo.ofrPath);
				//费用明细清单复印件(一份 
				self.showImg('cdlPath',obj.applyInfo.cdlPath);
				//门诊病历复印件(一份
				self.showImg('omrPath',obj.applyInfo.omrPath);
				//住院病历复印件(一份
				self.showImg('imrPath',obj.applyInfo.imrPath);
				//疾病诊断证明书复印件(一份
				self.showImg('ddcPath',obj.applyInfo.ddcPath);
				//医保中心的受理回执复印件
				self.showImg('medicalAcceptPath',obj.applyInfo.medicalAcceptPath);
				//医疗费用报销计算表复印件
				self.showImg('costCountPath',obj.applyInfo.costCountPath);
				//其他附件
				if(obj.applyInfo.otherProvePath!=null){
					for(var i=0;i<obj.applyInfo.otherProvePath.length;i++){
						var aid="otherProvePath"+i;
						var spanid= "otherProveSpan"+i;
						self.showImg(aid,obj.applyInfo.otherProvePath[i]);
						$("#"+spanid).show();
					}
				}
				self.showApprovalDialog($('#sh3_btn'),$('#back3_jfflsp'),record["approvalId"]);	
			
				 //lyh
				$('#jfflsp_detail').removeClass('none');
				$('#jfflspTpl').addClass('none');
				$('#jfflsp_sp3Tpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ck'));
				//返回按钮
				$('#back3_jfflsp').click(function(){
					//隐藏头部菜单
					$('#jfflsp_detail').addClass('none');
					$('#jfflsp_sp3Tpl').addClass('none');
					$('#jfflspTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ck').addClass("tabNone").find('a').removeClass('active');
					$('#jfflsp').find('a').addClass('active');
				});
				
				//审批状态按钮  通过、驳回
				$('input[name="approvalResult"]').change(function(){
					self.approvalResultChange();
				});
				
				
			});

		},
		getApprovalDetail:function(approvalId,callback){
			var param={'approvalId':approvalId};
			pointWelfare.queryApprovalDetail(param,function(rsp){
				var obj=rsp.data;
				callback(obj);
			});
		},
		showImg:function(imgObj,path){
			if(path!=null&&path!=''){
				$("#"+imgObj).removeClass('none').attr('href', comm.getFsServerUrl(path));
			}
		},
		approvalResultChange :function(){
			//审批状态按钮  通过、驳回
			var approvalResult =  $("input[name='approvalResult']:checked").val();
			if(approvalResult=='2'){
				$('#li_replyAmount').addClass('none');
			}else{
				$('#li_replyAmount').removeClass('none');
			}
		},
		showApprovalDialog:function(sh_btn,sh_back,approvalId){
			var self=this;
			sh_btn.click(function(){
				$( "#sh_dialog" ).dialog({
					title:"复核信息",
					width:"740",
					height:"600",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"确定":function(){
							if(!self.validte()){
								return false;
							}
						    var approvalParam={
								"approvalId":approvalId,	//审批编号
								"approvalResult":$("input[name='approvalResult']:checked").val(),	//int	审批结果  1 通过  2 不通过驳回
								"replyAmount":$.trim($("#replyAmount").val()),	//审批金额
								"remark":$.trim($("#approvalRemark").val()),	//审批备注信息
							};
							pointWelfare.approvalWelfare(approvalParam,function(rsp){
								$( "#sh_dialog" ).dialog( "destroy" );
								$("#jfflsp").click();
							});
						},
						"取消":function(){
							$( "#sh_dialog" ).dialog( "destroy" );
						}
					}
				});		
			});
		},
		validte:function(){
			var approvalResult=$("input[name='approvalResult']:checked").val();
			var remark=$("#approvalRemark").val();
			var replyAmount=$.trim($("#replyAmount").val());
			if(approvalResult==1 && replyAmount==''){
				comm.i_alert('审批金额不能为空');
				return false;
			}
			if(approvalResult==1 &&!(/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(replyAmount)){
				comm.i_alert('审批金额格式不正确');
				return false;
			}
			if(remark==''){
				comm.i_alert('请填写审批备注信息');
				return false;
			}else{
				if(remark.length>1000){
					comm.error_alert(comm.lang("pointWelfare").beizuMaxLength);
					return false;
				}
			}
			return true;
		}
	};
	return self;
	
});