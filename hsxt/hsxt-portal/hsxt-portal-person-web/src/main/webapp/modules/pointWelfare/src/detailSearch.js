/**
 * 积分福利明细及详单查询
 */
define(["text!pointWelfareTpl/detailSearch.html"], function (tpl) {
	var detailSearch =  {
		_dataModule : null,
		_tfsPath : "",
		show : function(dataModule){
			//明细查询表格结果
			var searchTable = null;
			detailSearch._dataModule = dataModule;
			//加载余额查询
			$("#myhs_zhgl_box").html(tpl);
			
			// 加载开始、结束日期
			$("#spoint_beginDate, #spoint_endDate").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});


			//立即搜索单击事件
			$("#welfare_search").click(function () {
//				var valid = detailSearch.validateData();
//				if (!valid.form()) {
//					return;
//				}
				var params = {};
				if(comm.isNotEmpty($("#welfareType").val())){
					params.search_welfareType=$("#welfareType").val();
				}
				if(comm.isNotEmpty($("#approvalStatus").val())){
					params.search_approvalStatus=$("#approvalStatus").val();
				}
				if(comm.isNotEmpty($('#spoint_beginDate').val())){
					params.search_startTime=$("#spoint_beginDate").val();
				}
				if(comm.isNotEmpty($('#spoint_endDate').val())){
					params.search_endTime=$("#spoint_endDate").val();
				}
				
				dataModule.listWelfareApply(params,detailSearch.detail);
				
			});
			
			//$("#welfare_search").click();
		},
		/**
		 * 查看备注
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['welfareType'], {0:'互生意外伤害保障',1:'互生医疗补贴计划',2:'代他人申请身故保障金'});
			}
			if(colIndex == 3){
				return comm.getNameByEnumId(record['approvalStatus'], {0:'受理中',1:'受理成功',2:'驳回'});
			}
			return $('<a data-sn="'+ record['applyWelfareNo'] +'" data-type="'+ record['welfareType'] +'" >查看</a>').click(function(e) {
//				comm.alert({imgClass:'tips_i',title:'查看备注',content: record['optRemark']});
				detailSearch.showDetail(record);
			});
		},
		validateData : function(){
			return $("#welfare_form").validate({
				rules : {
					welfareType : {
						required : true
					},
					statusSearch : {
						required : true
					}
				},
				messages : {
					welfareType : {
						required : comm.lang("pointWelfare").detailSearch.welfareType
					},
					statusSearch : {
						required : comm.lang("pointWelfare").detailSearch.statusType
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+20 top+25",
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
		//查看详细
		showDetail : function(target) {
			var applyWelfareNo = target.applyWelfareNo,	//获取id
			applyType = target.welfareType,	//获取类型
			oDetail = {
					"0" : {
						title : "互生意外伤害保障详单",
						id : "dlg0",
						fillData : function () {
							detailSearch._dataModule.queryWelfareApplyDetail({
								applyWelfareNo : applyWelfareNo
							}, function(response){
								var record=response.data;
								$("#dlg0 #applyWelfareNo").text(record.applyWelfareNo);		//申请单号
								$("#dlg0 #applyDate").text(record.applyDate);				//申请时间
								$("#dlg0 #welfareType").text('互生意外伤害保障');			//福利类别
								$("#dlg0 #healthCardNo").text(record.healthCardNo==null?'':record.healthCardNo);			//医保卡号
								$("#dlg0 #proposerName").text(record.proposerName);			//申请操作人
								$("#dlg0 #approvalStatus").text(comm.getNameByEnumId(record.approvalStatus, {0:'受理中',1:'受理成功',2:'驳回'}));		//审核结果
								$("#dlg0 #replyAmount").text(record.replyAmount==null?'':comm.formatMoneyNumber(record.replyAmount));			//批复金额
								$("#dlg0 #approvalReason").text(record.approvalReason==null?'':record.approvalReason);		//审核信息
								$("#dlg0 #approvalDate").text(record.approvalDate==null?'':record.approvalDate);			//审核时间
								/**资料附件**/
								//互生卡正面
								record.hscPositivePath && $("#dlg0 #hscPositivePath").removeClass('none').attr('href', comm.getFsServerUrl(record.hscPositivePath)); 
								//互生卡背面
								record.hscReversePath && $("#dlg0 #hscReversePath").removeClass('none').attr('href', comm.getFsServerUrl(record.hscReversePath));
								//身份证正面 
								record.cerPositivePath && $("#dlg0 #cerPositivePath").removeClass('none').attr('href', comm.getFsServerUrl(record.cerPositivePath));
								//身份证背面
								record.cerReversePath && $("#dlg0 #cerReversePath").removeClass('none').attr('href', comm.getFsServerUrl(record.cerReversePath));
								//医疗证明
								record.medicalProvePath && $("#dlg0 #medicalProvePath").removeClass('none').attr('href', comm.getFsServerUrl(record.medicalProvePath));
								//其他证明
								//record.otherProvePath && $("#dlg0 #otherProvePath").removeClass('none').attr('href', comm.getFsServerUrl(record.otherProvePath));
								if(record.otherProvePath){
									for(var i=0;i<record.otherProvePath.length;i++){
										record.otherProvePath[i] && $("#dlg0 #otherProvePath"+i).removeClass('none').attr('href', comm.getFsServerUrl(record.otherProvePath[i]));
									}
								}
								//医保中心的受理回执复印件
								record.medicalAcceptPath && $("#dlg0 #medicalAcceptPath").removeClass('none').attr('href', comm.getFsServerUrl(record.medicalAcceptPath));
								//医疗费用报销计算表复印件
								record.costCountPath && $("#dlg0 #costCountPath").removeClass('none').attr('href', comm.getFsServerUrl(record.costCountPath));
								//消费者本人的社会保障卡复印件 
								record.sscPath && $("#dlg0 #sscPath").removeClass('none').attr('href', comm.getFsServerUrl(record.sscPath));
								//住院病历复印件(一份)
								record.imrPath && $("#dlg0 #imrPath").removeClass('none').attr('href', comm.getFsServerUrl(record.imrPath));
								//原始收费收据复印件(一份)
								record.ofrPath && $("#dlg0 #ofrPath").removeClass('none').attr('href', comm.getFsServerUrl(record.ofrPath));
								//疾病诊断证明书复印件(一份)
								record.ddcPath && $("#dlg0 #ddcPath").removeClass('none').attr('href', comm.getFsServerUrl(record.ddcPath));
								//费用明细清单复印件(一份) 
								record.cdlPath && $("#dlg0 #cdlPath").removeClass('none').attr('href', comm.getFsServerUrl(record.cdlPath));
							
							});
						},
						clear : function () {
							$("#applyNo_welfare,#applyDate_welfare,#applyType_welfare,#heathCardNo_welfare,#updateBy_welfare,#status_welfare_appr,#money_welfare_appr,#note_welfare_appr,#date_welfare_appr").text("");
							$("#pointCardFace_welfare,#pointCardBack_welfare,#lrcFace_welfare,#lrcBack_welfare,#medicalProve_welfare,#otherProve_welfare").removeAttr('href');
							$("#otherProve_welfare").addClass('none');
						}
					},
				"1" : {
					title : "互生医疗补贴计划详单",
					id : "dlg1",
					fillData : function () {
						detailSearch._dataModule.queryWelfareApplyDetail({applyWelfareNo : applyWelfareNo}, function(response){
							var record=response.data;
							$("#dlg1 #applyWelfareNo").text(record.applyWelfareNo);		//申请单号
							$("#dlg1 #applyDate").text(record.applyDate);				//申请时间
							$("#dlg1 #welfareType").text('互生医疗补贴计划');			//福利类别
							$("#dlg1 #healthCardNo").text(record.healthCardNo==null?'':record.healthCardNo);			//医保卡号
							$("#dlg1 #startDate").text(comm.formatDate(record.startDate,'yyyy-MM-dd'));				//诊疗开始日期
							$("#dlg1 #endDate").text(comm.formatDate(record.endDate,'yyyy-MM-dd'));					//诊疗结束日期
							$("#dlg1 #city").text(record.city);							//所在城市
							$("#dlg1 #hospital").text(record.hospital);					//所在医院
							$("#dlg1 #proposerName").text(record.proposerName);			//申请操作人
							$("#dlg1 #approvalStatus").text(comm.getNameByEnumId(record.approvalStatus, {0:'受理中',1:'受理成功',2:'驳回'}));		//审核结果
							if(record.approvalStatus==2){
								$("#dlg1 #pfje").hide();
							}
							$("#dlg1 #replyAmount").text(record.replyAmount==null?'':comm.formatMoneyNumber(record.replyAmount));			//批复金额
							$("#dlg1 #approvalReason").text(record.approvalReason==null?'':record.approvalReason);		//审核信息
							$("#dlg1 #approvalDate").text(record.approvalDate==null?'':record.approvalDate);			//审核时间
							/**资料附件**/
							//互生卡正面
							record.hscPositivePath && $("#dlg1 #hscPositivePath").removeClass('none').attr('href', comm.getFsServerUrl(record.hscPositivePath)); 
							//互生卡背面
							record.hscReversePath && $("#dlg1 #hscReversePath").removeClass('none').attr('href', comm.getFsServerUrl(record.hscReversePath));
							//身份证正面 
							record.cerPositivePath && $("#dlg1 #cerPositivePath").removeClass('none').attr('href', comm.getFsServerUrl(record.cerPositivePath));
							//身份证背面
							record.cerReversePath && $("#dlg1 #cerReversePath").removeClass('none').attr('href', comm.getFsServerUrl(record.cerReversePath));
							//消费者本人的社会保障卡复印件 
							record.sscPath && $("#dlg1 #sscPath").removeClass('none').attr('href', comm.getFsServerUrl(record.sscPath));
							//原始收费收据复印件(一份)
							record.ofrPath && $("#dlg1 #ofrPath").removeClass('none').attr('href', comm.getFsServerUrl(record.ofrPath));
							//费用明细清单复印件(一份) 
							record.cdlPath && $("#dlg1 #cdlPath").removeClass('none').attr('href', comm.getFsServerUrl(record.cdlPath));
							//门诊病历复印件(一份)
							record.omrPath && $("#dlg1 #omrPath").removeClass('none').attr('href', comm.getFsServerUrl(record.omrPath));
							//住院病历复印件(一份)
							record.imrPath && $("#dlg1 #imrPath").removeClass('none').attr('href', comm.getFsServerUrl(record.imrPath));
							//疾病诊断证明书复印件(一份)
							record.ddcPath && $("#dlg1 #ddcPath").removeClass('none').attr('href', comm.getFsServerUrl(record.ddcPath));
							//医保中心的受理回执复印件
							record.medicalAcceptPath && $("#dlg1 #medicalAcceptPath").removeClass('none').attr('href', comm.getFsServerUrl(record.medicalAcceptPath));
							//医疗费用报销计算表复印件
							record.costCountPath && $("#dlg1 #costCountPath").removeClass('none').attr('href', comm.getFsServerUrl(record.costCountPath));
							//其他附件
							if(record.otherProvePath){
								for(var i=0;i<record.otherProvePath.length;i++){
									record.otherProvePath[i] && $("#dlg1 #otherProvePath"+i).removeClass('none').attr('href', comm.getFsServerUrl(record.otherProvePath[i]));
								}
							}
						});
					},
					clear : function () {
						$("#applyNo_medical,#applyDate_medical,#applyType_medical,#heathCardNo_medical,#beginDate_medical,#endDate_medical,#cityName_medical,#histoly_medical,#createBy_medical,#apprResult_medical,#money_medical_appr,#note_medical_appr,#date_medical_appr").text("");
						$("#hscFacePath_welfare,#hscBackPath_welfare,#cerFacePath_welfare,#cerBackPath_welfare,#sscPath_welfare,#ofrPath_welfare,#cdlPath_welfare,#omrPath_welfare,#imrPath_welfare,#ddcPath_welfare").removeAttr('href');
						$("#imrPath_welfare").addClass('none');
					}
				},
				"2" : {
					title : "代他人申请身故保障金详单",
					id : "dlg2",
					fillData : function () {
						detailSearch._dataModule.queryWelfareApplyDetail({
							applyWelfareNo : applyWelfareNo
						}, function(response){
							var record=response.data;
							$("#dlg2 #applyWelfareNo").text(record.applyWelfareNo);		//申请单号
							$("#dlg2 #applyDate").text(record.applyDate);				//申请时间
							$("#dlg2 #welfareType").text('代他人申请身故保障金');			//福利类别
							$("#dlg2 #deathResNo").text(record.deathResNo);			//身故人互生卡号
							$("#dlg2 #diePeopleName").text(record.diePeopleName);			//身故人姓名
							$("#dlg2 #proposerName").text(record.proposerName);			//申请操作人
							
							$("#dlg2 #approvalStatus").text(comm.getNameByEnumId(record.approvalStatus, {0:'受理中',1:'受理成功',2:'驳回'}));		//审核结果
							$("#dlg2 #replyAmount").text(record.replyAmount==null?'':comm.formatMoneyNumber(record.replyAmount));			//批复金额
							$("#dlg2 #approvalReason").text(record.approvalReason==null?'':record.approvalReason);		//审核信息
							$("#dlg2 #approvalDate").text(record.approvalDate==null?'':record.approvalDate);			//审核时间
							/**资料附件**/
							//被保障人死亡证明附件 deathProvePath
							record.deathProvePath && $("#dlg2 #deathProvePath").removeClass('none').attr('href', comm.getFsServerUrl(record.deathProvePath));
							//户籍注销证明 hrcPath
							record.hrcPath && $("#dlg2 #hrcPath").removeClass('none').attr('href', comm.getFsServerUrl(record.hrcPath));
							//被保障人法定身份证明 diePeopleCerPath
							record.diePeopleCerPath && $("#dlg2 #diePeopleCerPath").removeClass('none').attr('href', comm.getFsServerUrl(record.diePeopleCerPath));
							//与被保障人关系证明书 ifpPath
							record.ifpPath && $("#dlg2 #ifpPath").removeClass('none').attr('href', comm.getFsServerUrl(record.ifpPath));
							//代办人法定身份证明 aipPath
							record.aipPath && $("#dlg2 #aipPath").removeClass('none').attr('href', comm.getFsServerUrl(record.aipPath));
							//其他证明材料 otherProvePath
							//record.otherProvePath && $("#dlg2 #otherProvePath").removeClass('none').attr('href', comm.getFsServerUrl(record.otherProvePath));
							if(record.otherProvePath){
								for(var i=0;i<record.otherProvePath.length;i++){
									record.otherProvePath[i] && $("#dlg2 #otherProvePath"+i).removeClass('none').attr('href', comm.getFsServerUrl(record.otherProvePath[i]));
								}
							}
						});
					},
					clear : function () {
						$("#applyNo_detah,#applyDate_death,#applyType_death,#resNo_death,#custName_death,#createBy_death,#apprResult_death_appr,#money_death_appr,#note_death_appr,#apprDate_death_appr").text("");
						$("#deathCer_death,#idLogoutCer_death,#agentAccreditPic_death,#trusteeIdCardPic_death,#elseCer_death").removeAttr('href');
						$("#elseCer_death").addClass('none');
					}
				}
				
			}[applyType];

			//清除弹出框的数据
			oDetail.clear();
			//加载数据
			oDetail.fillData();

			//显示弹出框
			$("#" + oDetail.id).dialog({
				title : oDetail.title,
				modal : true,
				width : "600",
				buttons : {
					"确定" : function () {
						$(this).dialog("destroy");
					}
				}
			});
		}
	};
	return detailSearch
});
