define(['text!businessServiceTpl/xtywsp/tzjfhdsp.html',
		   'text!businessServiceTpl/xtywsp/jfhdsq/spwtg.html',
		   'text!businessServiceTpl/xtywsp/jfhdsq/dfwgssp.html',
		   'text!businessServiceTpl/xtywsp/jfhdsq/ddqptsp.html',
		   'text!businessServiceTpl/xtywsp/jfhdsq/fwgsspbh.html',
		   'text!businessServiceTpl/xtywsp/jfhdsq/sp.html',
		   'businessServiceDat/businessService'		
],function( tzjfhdspTpl , spwtgTpl, dfwgsspTpl, ddqptspTpl, fwgsspbhTpl,spTpl,businessService ){
	var tzjfhdspPage = {	 	
		spwtgTpl: spwtgTpl,
		dfwgsspTpl:dfwgsspTpl,
		ddqptspTpl:ddqptspTpl,
		fwgsspbhTpl:fwgsspbhTpl,
		spTpl:spTpl,	
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(tzjfhdspTpl));
			
			//初始化服务公司审批状态下拉框
		 	comm.initSelect("#sel_spzt",comm.lang("businessService").appStatusEnum,140);
		 	
			$('#btn_cx').click(function(){
				tzjfhdspPage.pageQuery(true);
			});

			tzjfhdspPage.pageQuery();
		},
		/** 分页查询 */
		pageQuery:function(autoLoad){
			var gridObj ;
			
			//获取cookie中的客户号等信息
			var param = comm.getRequestParams();
			
			params = {
					search_entResNo : $("#entResNo").val(),		//成员企业互生号
					search_entName : $("#entName").val(),		//成员企业名称
					search_linkman :$("#linkman").val(),		//联系人
					search_serResNo:param.pointNo,					//服务公司互生号
					search_status : $("#sel_spzt").attr('optionvalue'),		//申请状态
					search_applyType : 0						//申请类别  :0  为停止积分活动申请,1为参与积分活动申请
			}; 
				
			searchTable = gridObj = comm.controlLoadBsGrid(autoLoad,"tableDetail","find_activity_apply_list",params,comm.lang("businessService"),tzjfhdspPage.detail,tzjfhdspPage.add);
		},
		detail : function(record, rowIndex, colIndex, options)
		{
			if(colIndex == 7)
			{
				
				var zt =  record.status;
				var link1 =  $('<a  >'+comm.lang("businessService").query+'</a>').click(function(e) {
					
					//显示详情	
					tzjfhdspPage.showDetail(record);
				});
				return   link1 ;
			}
		},
		add : function(record, rowIndex, colIndex, options)
		{
			var zt =  record.status;
			if(colIndex == 5)
			{
				return comm.lang("businessService").appStatusShowEnum[zt];
			}
			else if(colIndex == 7)
			{
				if (zt!='0')  //待服务公司审批		
				{ 
					return '' 
				} ;				 
				var link1 =  $('<a >'+comm.lang("businessService").approval+'</a>').click(function(e) {				 
					tzjfhdspPage.approvalStop(record);
				});
				return   link1 ;
			}
		},
		//审批
		approvalStop : function(obj){
			businessService.seachPointActivityDetail(
					{
						applyId:obj.applyId
					},
					function(res)
					{
				
						$('#tzjfhdsp_dialog>p').html(_.template(spTpl,res.data));
						//申请书附件文件id
						var fileId = res.data.POINT_ACTIVITY.bizApplyFile;
						//申请书在文件系统中的url访问地址
						var href = businessService.getFsServerUrl(fileId);
						$("#ywblsqs").attr("href",href);
						
						$('#tzjfhdsp_dialog').dialog(
						{
							title:comm.lang("businessService").wait_approval,
							width:600 ,
							closeIcon:true,
							buttons:{
								'审批通过' : function(){
									tzjfhdspPage.approveQuitCommit(obj, true,this);
								},
								'审批驳回' : function(){
									tzjfhdspPage.approveQuitCommit(obj, false,this);
								}									
							}
						}); 
					});
		},
		//审批提交
		approveQuitCommit : function(obj,isPass,el){
			
			//获取cookie中的客户号等信息
			var param = comm.getRequestParams();
			
			if(comm.isEmpty($("#apprRemark").val())){
				comm.warn_alert(comm.lang("businessService").apprRemarkNotNull);
				return;
			}
			
			var postData = {
					applyId:obj.applyId,  		//申请单id
					optCustId : param.custId,			//经办企业客户号
					optEntName : param.custEntName,	//企业名称
					optName : param.cookieOperNoName, 			//经办人
					isPass : isPass,			//审批结果
					apprRemark : $("#apprRemark").val() 	//审批意见
			};
			//提交审批信息
			businessService.commitPointActivityAppr(postData,function(res){
				
				//刷新表单
				//if(tzjfhdspPage.searchTable)tzjfhdspPage.searchTable.refreshPage();
				$('#xtywsp_tzjfhdsp').click();
				
				//关闭弹出窗口
				$(el).dialog('destroy');
				
			});
		},
		showDetail : function(obj){
			
			var oData = {
				"0" : {
					name : comm.lang("businessService").appStatusShowEnum[0],// "待服务公司审批",
					tpl : dfwgsspTpl,
					datMethod : ""
				},
				"1" : {
					name : comm.lang("businessService").appStatusShowEnum[1],//"服务公司审批通过",
					tpl : ddqptspTpl,
					datMethod : "",
					width: 600
				},
				"2" : {
					name : comm.lang("businessService").appStatusShowEnum[2],//"地区平台初审通过",
					tpl : ddqptspTpl,
					datMethod : ""
				},
				"3" : {
					name : comm.lang("businessService").appStatusShowEnum[3],//"成员企业资格已注销",
					tpl : ddqptspTpl,
					datMethod : "",
					width: 600
				},
				"4" : {
					name : comm.lang("businessService").appStatusShowEnum[4],//"服务公司审批驳回",
					tpl : spwtgTpl,
					datMethod : "",
					width: 600
				},
				"5" : {
					name : comm.lang("businessService").appStatusShowEnum[5],//"地区平台审批驳回",
					tpl : spwtgTpl,
					datMethod : "",
					width: 600
				},
				"6" : {
					name : comm.lang("businessService").appStatusShowEnum[6],//"地区平台复核驳回",
					tpl : spwtgTpl,
					datMethod : "",
					width: 600
				}
			}[obj.status];
			if(oData && !$.isEmptyObject(oData)){
				//查询详细信息
				businessService.seachPointActivityDetail(
						{
							applyId:obj.applyId
						},function(res){
					
					$('#tzjfhdsp_dialog>p').html(_.template(oData.tpl,res.data));//(oData.tpl,res.data);
					
					//申请书附件文件id
					var fileId = res.data.POINT_ACTIVITY.bizApplyFile;
					
					//申请书在文件系统中的url访问地址
					var href = businessService.getFsServerUrl(fileId);
					
					$("#ywblsqs").attr("href",href);
					
					$('#tzjfhdsp_dialog').dialog({
						title:oData.name,
						width:600 ,
						closeIcon:true,
						buttons:{
							'确定' : function(){
								
								
								$(this).dialog('destroy');
							} 							
						}
					}); 
				});
			}
		}
	};
	return tzjfhdspPage
}); 
