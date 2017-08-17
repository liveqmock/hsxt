define(['text!businessServiceTpl/xtywsp/cyqyzgzxsp.html',
		   'text!businessServiceTpl/xtywsp/dialog/cyqyzgyzx.html',	
		   'text!businessServiceTpl/xtywsp/dialog/spwtg.html',
		   'text!businessServiceTpl/xtywsp/dialog/dfwgssp.html',
		   'text!businessServiceTpl/xtywsp/dialog/ddqptsp.html',
		   'text!businessServiceTpl/xtywsp/dialog/fwgsspbh.html',
		   'text!businessServiceTpl/xtywsp/dialog/sp.html',
		   'businessServiceDat/businessService',
		   'businessServiceLan'
],function( cyqyzgzxspTpl ,cyqyzgyzxTpl, spwtgTpl, dfwgsspTpl, ddqptspTpl, fwgsspbhTpl,spTpl,businessService ){
	return cyqyzgzxsp = {	
		cyqyzgyzxTpl:cyqyzgyzxTpl,
		spwtgTpl: spwtgTpl,
		dfwgsspTpl:dfwgsspTpl,
		ddqptspTpl:ddqptspTpl,
		fwgsspbhTpl:fwgsspbhTpl,
		spTpl:spTpl,
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(cyqyzgzxspTpl)) ;
			
		 	//初始化服务公司审批状态下拉框
		 	comm.initSelect("#sel_spzt",comm.lang("businessService").cancelApplyStatueEnum,140);
		 	 
		 	//点击查询
			$('#btn_cx').click(function(){
				cyqyzgzxsp.pageQuery(true);
			});
			
			//初始化
			this.pageQuery();
		},
		/** 分页查询 */
		pageQuery:function(autoLoad){
			var gridObj;
			
			//获取cookie中的客户号等信息
			var param = comm.getRequestParams();
			
			params = {
					search_entResNo : $("#entResNo").val(),		//成员企业互生号
					search_entName : $("#entName").val(),		//成员企业名称
					search_linkman :$("#linkman").val(),		//联系人
					search_serResNo:param.pointNo,					//服务公司互生号
					search_status : $("#sel_spzt").attr('optionvalue')		//申请状态
				};
			searchTable = gridObj = comm.controlLoadBsGrid(autoLoad,"tableDetail","membercomp_quit_list",params,comm.lang("businessService"),cyqyzgzxsp.detail,cyqyzgzxsp.add);
		},
		detail : function(record, rowIndex, colIndex, options){
		/*	if(colIndex == 5)
			{
				return comm.lang("businessService").cancelApplyStatueEnum[record['status']];
			}*/
			if(colIndex == 7)
			{
				
				var zt =  record.status;
				var link1 =  $('<a  >'+comm.lang("businessService").query+'</a>').click(function(e) {
					
					//显示详情	
					cyqyzgzxsp.showDetail(record);
					
				});
				return   link1 ;
			}
		},
		add : function(record, rowIndex, colIndex, options){
			var zt =  record.status;
			if(colIndex == 5)
			{
				return comm.lang("businessService").cancelApplyShowStatueEnum[zt];
			}
			else if(colIndex == 7)
			{
				if (zt!='0') 	//待服务公司审批	
				{ 
					return '' 
				} ;					 
				var link1 =  $('<a >'+comm.lang("businessService").approval+'</a>').click(function(e) {
					
					cyqyzgzxsp.approvalQuit(record);
					
				});
				
				return   link1 ;
			}
		},
		//审批
		approvalQuit : function(obj){
			businessService.seachMemberQuitDetail({applyId:obj.applyId},function(res){
				$('#cyqyzgzxsp_dialog>p').html(_.template(spTpl,res.data));
				var quitData = res.data.MEMBER_QUIT;
				//业务办理书
				if(quitData.bizApplyFile!=null){
					quitData['bizApplyFilePath']=businessService.getFsServerUrl(quitData.bizApplyFile);
					comm.initPicPreview("#ywblsqs",res.data.MEMBER_QUIT.bizApplyFile);
				}
				//企业实地考察报告
				if(quitData.reportFile!=null){
					quitData['reportFilePath']=businessService.getFsServerUrl(quitData.reportFile);
					comm.initPicPreview("#qysdkcbg",res.data.MEMBER_QUIT.reportFile);
				}
				//企业双方声明文件
				if(quitData.statementFile!=null){
					quitData['statementFilePath']=businessService.getFsServerUrl(quitData.statementFile);
					comm.initPicPreview("#qysfsmwj",res.data.MEMBER_QUIT.statementFile);
				}
				//其他文件
				if(quitData.otherFile!=null){
					quitData['otherFilePath']=businessService.getFsServerUrl(quitData.otherFile);
					comm.initPicPreview("#qtwjck",res.data.MEMBER_QUIT.otherFile);
				}
				
				
				//如果是企业自己注销，需上传企业实地考察报告、企业双方声明文件
				if(1==quitData.applyType){
					var btnIds = ['#reportFile', '#statementFile'];
					var imgIds = ['#thum-1', '#thum-2'];
					$("body").on("change", "", function(){
						for(var k in imgIds){
							comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
						}
					});
					
					comm.initUploadBtn(btnIds, imgIds);
					
					//加载样例图片
					cacheUtil.findDocList(function(map){
						comm.initDownload("#case-fileId-1", map.comList, '1008');
						comm.initDownload("#case-fileId-2", map.comList, '1009');
//						comm.initPicPreview("#case-fileId-1", comm.getPicServerUrl(map.picList, '1013'), null);
//						comm.initPicPreview("#case-fileId-2", comm.getPicServerUrl(map.picList, '1014'), null);
					});
				}
				
				$('#cyqyzgzxsp_dialog').dialog({
					title:comm.lang("businessService").wait_approval,
					width:780 ,
					height : 800,
					closeIcon:true,
					buttons:{
						'审批通过' : function(){
							if(2==quitData.applyType){
								cyqyzgzxsp.approveQuitCommit2(obj, true,this);
							}else{
								if(!cyqyzgzxsp.applyCheck().form()){
									return false;
								}
								cyqyzgzxsp.approveQuitCommit(obj, true,this);
							}
						},
						'审批驳回' : function(){
							if(2==quitData.applyType){
								cyqyzgzxsp.approveQuitCommit2(obj, false,this);
							}else{
								cyqyzgzxsp.approveQuitCommit(obj, false,this);
							}
						}									
					}
				}); 
			});
		},
		//审批提交
		approveQuitCommit : function(obj,isPass,el){
			
			//上传有修改的文件
			comm.uploadFile(null, ["reportFile","statementFile"], function(data){
				//保存上传成功的文件
				if(data.reportFile){$("#hidReportFile").val(data.reportFile);}
				if(data.statementFile){$("#hidStatementFile").val(data.statementFile);}
				
				//获取cookie中的客户号等信息
				var param = comm.getRequestParams();
				var postData = {
						applyId:obj.applyId,				//申请单编号（id）
						isPass : isPass,					//审批结果
						optEntName : param.custEntName,	//操作员所属公司名称
						optName : param.cookieOperNoName,						//操作员名字
						optCustId : param.custId,//操作员客户号
						apprRemark : $("#apprRemark").val(), //审批意见
						reportFile : $("#hidReportFile").val(),
						statementFile : $("#hidStatementFile").val()
				};
				//提交审批信息
				businessService.commitMemberQuitAppr(postData,function(res){
					
					//刷新表单
					if(searchTable){
						searchTable.refreshPage();
					}
					
					//关闭弹出窗口
					$(el).dialog('destroy');
					
				});
				
			},function(err){
				
			},{});
			
		},
		//服务公司申请注销审批提交
		approveQuitCommit2 : function(obj,isPass,el){
				//获取cookie中的客户号等信息
				var param = comm.getRequestParams();
				var postData = {
						applyId:obj.applyId,				//申请单编号（id）
						isPass : isPass,					//审批结果
						optEntName : param.custEntName,	//操作员所属公司名称
						optName : param.cookieOperNoName,						//操作员名字
						optCustId : param.custId,//操作员客户号
						apprRemark : $("#apprRemark").val() //审批意见
				};
				//提交审批信息
				businessService.commitMemberQuitAppr(postData,function(res){
					//刷新表单
					if(searchTable){
						searchTable.refreshPage();
					}
					//关闭弹出窗口
					$(el).dialog('destroy');
				});	
		},
		showDetail : function(obj){
			
			var oData = {
				"0" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[0],// "待服务公司审批",
					tpl : dfwgsspTpl,
					datMethod : ""
				},
				"1" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[1],//"服务公司审批通过",
					tpl : ddqptspTpl,
					datMethod : "",
					width: 600
				},
				"2" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[2],//"地区平台初审通过",
					tpl : ddqptspTpl,
					datMethod : ""
				},
				"3" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[3],//"成员企业资格已注销",
					tpl : ddqptspTpl,
					datMethod : "",
					width: 600
				},
				"4" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[4],//"服务公司审批未通过",
					tpl : spwtgTpl,
					datMethod : "",
					width: 600
				},
				"5" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[5],//"地区平台审批未通过",
					tpl : spwtgTpl,
					datMethod : "",
					width: 600
				},
				"6" : {
					name : comm.lang("businessService").cancelApplyShowStatueEnum[6],//"地区平台复核未通过",
					tpl : spwtgTpl,
					datMethod : "",
					width: 600
				}
			}[obj.status];
			if(oData && !$.isEmptyObject(oData)){
				//查询详细信息
				businessService.seachMemberQuitDetail({applyId:obj.applyId},function(res){
					
					$('#cyqyzgzxsp_dialog>p').html(_.template(oData.tpl,res.data));
					
					var quitData = res.data.MEMBER_QUIT;
					//业务办理申请书
					if(quitData.bizApplyFile!=null){
						quitData['bizApplyFilePath']=businessService.getFsServerUrl(quitData.bizApplyFile);
						comm.initPicPreview("#ywblsqs",res.data.MEMBER_QUIT.bizApplyFile);
					}
					//企业实地考察报告
					if(quitData.reportFile!=null){
						quitData['reportFilePath']=businessService.getFsServerUrl(quitData.reportFile);
						comm.initPicPreview("#qysdkcbg",res.data.MEMBER_QUIT.reportFile);
					}
					//企业双方声明文件
					if(quitData.statementFile!=null){
						quitData['statementFilePath']=businessService.getFsServerUrl(quitData.statementFile);
						comm.initPicPreview("#qysfsmwj",res.data.MEMBER_QUIT.statementFile);
					}
					//其他文件
					if(quitData.otherFile!=null){
						quitData['otherFilePath']=businessService.getFsServerUrl(quitData.otherFile);
						comm.initPicPreview("#qtwjck",res.data.MEMBER_QUIT.otherFile);
					}
					
					$('#cyqyzgzxsp_dialog').dialog({
						title:oData.name,
						width:700 ,
						closeIcon:true,
						buttons:{
							'确定' : function(){
								$(this).dialog('destroy');
							} 							
						}
					}); 
				});
			}
		},
		/** 审批数据验证 */   	
		applyCheck:function(){
			return $("#applyForm").validate({
				rules : {
					reportFile : {
						required : function(){
								return (comm.isEmpty($("#hidReportFile").val()) && comm.isEmpty($("#reportFile").val()));
							},
					},
					statementFile : {
						required : function(){
								return (comm.isEmpty($("#hidStatementFile").val()) && comm.isEmpty($("#statementFile").val()));
							},
					}
				},
				messages : {
					reportFile:{
						required : comm.lang("businessService").select_reportFile_error
					},
					statementFile : {
						required : comm.lang("businessService").select_statementFile_error
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+210 top+30",
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
		
		}
	}
	return cyqyzgzxsp;
}); 
