/*积分福利审批记录*/
define([
"text!scoremanageTpl/jfflspjl/jfflspjl.html",
"text!scoremanageTpl/jfflspjl/jfflspjl_ck1.html",
"text!scoremanageTpl/jfflspjl/jfflspjl_ck2.html",
"text!scoremanageTpl/jfflspjl/jfflspjl_ck3.html",
'scoremanageDat/pointWelfare',
'scoremanageLan'
],function(jfflspjlTpl,jfflspjl_ck1Tpl,jfflspjl_ck2Tpl,jfflspjl_ck3Tpl,pointWelfare){

	var self = {
			showPage : function(){
				$('#busibox').html(_.template(jfflspjlTpl));
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
		/** 分页查询 */
		pageQuery:function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var welfareType=$(".selectList-active").attr("data-Value");
			//查询参数
			var queryParam={
							"search_welfareType":welfareType!=undefined?welfareType:"",	//int	否	福利类型 0 意外伤害 1 免费医疗 2 他人身故
							"search_startTime":$("#search_startDate").val(),		//String	否	开始查询时间(申请时间) 
							"search_endTime":$("#search_endDate").val(),		//String	否	结束查询时间(申请时间)
							"search_proposerResNo":$("#proposerResNo").val().trim(),	//String	否	申请人互生号 
							"search_proposerName":$("#proposerName").val()	.trim()//String	否	申请人姓名
						};
			pointWelfare.listApprovalRecord("searchTable",queryParam,self.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['welfareType'], comm.lang('pointWelfare').welfareType);
			}
			if(colIndex == 7){
				if(record.approvalStatus==0){
					return "--";
				}
				return self.foramtSubsidyBalance(record['welfareType'], record['subsidyBalance']);
			}
			if(colIndex == 8){
				var approvalStep = record['approvalStep'];
				if(approvalStep==0){
					return comm.getNameByEnumId(record['approvalStatus'], comm.lang("pointWelfare").approvalStatus);
				}
				return comm.getNameByEnumId(record['approvalStatus'], comm.lang("pointWelfare").approvalStatus);
			}
			if(colIndex == 10){
				var link1 = $('<a>查看</a>').click(function(e) {
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
		},
		foramtSubsidyBalance : function(welfareType,subsidyBalance){
			if(welfareType == 0||welfareType == 2){
				return comm.formatMoneyNumberAps(subsidyBalance);
			}
			return '-';
		},
		shenPi_1 : function(record){
			self.getApprovalDetail(record["approvalId"],function(obj){
				if(obj.welfareQualify.effectDate==null){
					obj.welfareQualify.effectDate = "";
				}
				obj.welfareQualify.subsidyBalance=self.foramtSubsidyBalance(obj.applyInfo.welfareType,record['subsidyBalance']);
				$('#jfflspjl_detail').html(_.template(jfflspjl_ck1Tpl,obj));
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
			    //lyh
				$('#jfflspjl_detail').removeClass('none');
				$('#jfflspTpl').addClass('none');
				$('#jfflspjl_ck1Tpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ck'));
				//返回按钮
				$('#back1_jfflspjl').click(function(){
					//隐藏头部菜单
					$('#jfflspjl_detail').addClass('none');
					$('#jfflspjl_ck1Tpl').addClass('none');
					$('#jfflspTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ck').addClass("tabNone").find('a').removeClass('active');
					$('#jfflspjl').find('a').addClass('active');
				});
				
			});
			
		},
		shenPi_2 : function(record){
			self.getApprovalDetail(record["approvalId"],function(obj){
				if(obj.welfareQualify.effectDate==null){
					obj.welfareQualify.effectDate = "";
				}
				obj.welfareQualify.subsidyBalance=self.foramtSubsidyBalance(obj.applyInfo.welfareType,record['subsidyBalance']);
				$('#jfflspjl_detail').html(_.template(jfflspjl_ck2Tpl,obj));
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
				if(obj.welfareQualify.allFailureDateList!=null){
					var failureDate='';
					for(var i = 0; i < obj.welfareQualify.allFailureDateList.length; i++){
						failureDate += obj.welfareQualify.allFailureDateList[i]+',';
					}
					failureDate=failureDate.substring(0,failureDate.length-1);
					$("#allFailureDateList").text(failureDate);
				}
			
				
			    //lyh
				$('#jfflspjl_detail').removeClass('none');
				$('#jfflspTpl').addClass('none');
				$('#jfflspjl_ck2Tpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ck'));
				//返回按钮
				$('#back2_jfflspjl').click(function(){
					//隐藏头部菜单
					$('#jfflspjl_detail').addClass('none');
					$('#jfflspjl_ck2Tpl').addClass('none');
					$('#jfflspTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ck').addClass("tabNone").find('a').removeClass('active');
					$('#jfflspjl').find('a').addClass('active');
				});
				
			});
		},
		shenPi_3 : function(record){
			self.getApprovalDetail(record["approvalId"],function(obj){
				if(obj.welfareQualify.effectDate==null){
					obj.welfareQualify.effectDate = "";
				}
				obj.welfareQualify.subsidyBalance=self.foramtSubsidyBalance(obj.applyInfo.welfareType,record['subsidyBalance']);
				$('#jfflspjl_detail').html(_.template(jfflspjl_ck3Tpl,obj));
				
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
				
				  //lyh
				$('#jfflspjl_detail').removeClass('none');
				$('#jfflspTpl').addClass('none');
				$('#jfflspjl_ck3Tpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ck'));
				//返回按钮
				$('#back3_jfflspjl').click(function(){
					//隐藏头部菜单
					$('#jfflspjl_detail').addClass('none');
					$('#jfflspjl_ck3Tpl').addClass('none');
					$('#jfflspTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ck').addClass("tabNone").find('a').removeClass('active');
					$('#jfflspjl').find('a').addClass('active');
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
		}
	};
	return self;
	
});