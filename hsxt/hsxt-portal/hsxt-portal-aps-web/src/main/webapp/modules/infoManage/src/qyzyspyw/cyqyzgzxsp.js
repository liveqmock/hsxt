define(['text!infoManageTpl/qyzyspyw/cyqyzgzxsp.html',
		'text!infoManageTpl/qyzyspyw/cyqyzgzxsp_sp_dialog.html',
		'text!infoManageTpl/qyzyspyw/cyqyzgzxsp_fh_dialog.html',
		'text!infoManageTpl/qyzyspyw/jjgd_dialog.html',
		'infoManageDat/infoManage',
		'commDat/common'
		], function(cyqyzgzxspTpl, cyqyzgzxsp_sp_dialogTpl, cyqyzgzxsp_fh_dialogTpl,jjgd_dialogTpl,infoManage,common){
	var cyqyzgzxspPage = {
		gridObj : null,
		showPage : function(){
			
			$('#busibox').html(_.template(cyqyzgzxspTpl));	
			
			
			//初始化服务公司审批状态下拉框
		 	comm.initSelect("#status",comm.lang("infoManage").memberStatuEnum);
	
			
			$("#searchBtn").click(function(){
				
				var params = {
						search_entResNo : $("#entResNo").val(),			//企业互生号
						search_entName : $("#entName").val(),			//企业名称
						search_linkman : $("#linkman").val(), 			//联系人
						search_status : $("#status").attr("optionvalue")//状态
				};
				cyqyzgzxspPage.gridObj = comm.getCommBsGrid("searchTable","membercompquit_apprlist",params,comm.lang("infoManage"),cyqyzgzxspPage.detail,cyqyzgzxspPage.query);
				
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5)
			{
				return comm.lang("infoManage").applyBusinessStatueEnum[record.status];
			}
			if(colIndex == 7)
			{
				
				var link1 = null;
				if(record.status == '1')
				{
					link1 = $('<a>'+comm.lang("infoManage").apprval+'</a>').click(function(e) {
						
						cyqyzgzxspPage.shenPi(record);
						
					}) ;
				}
				else if(record.status == '2')
				{
					link1 = $('<a>'+comm.lang("infoManage").fuhe+'</a>').click(function(e) {
						
						cyqyzgzxspPage.fuHe(record);
						
					}) ;
				}
				
				return link1;
			}
			else if(colIndex == 8)
			{
				var link =  $('<a>'+comm.lang("infoManage").workTaskRefuseAccept+'</a>').click(function(e) {
					
					
                    comm.confirm({
                            imgFlag : true,
                            width:500,
                            imgClass : 'tips_i',
                            content : comm.lang("infoManage").wtMemberCompCancelRefuseComfirm,
                            callOk : function(){
                                    var param={"bizNo":record.applyId};
                                    common.workTaskRefuseAccept(param,function(rsp){
                                            //成功
                                            comm.alert({imgClass: 'tips_yes' ,content:comm.lang("infoManage")[22000],callOk:function(){
                                            	cyqyzgzxspPage.gridObj.refreshPage();
                                            }});
                                    });
                            }        
                    });
	            });
				
	            return link;
				
			}
		},
		query : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
				 return comm.workflow_operate(comm.lang("infoManage").workTaskHangUp, comm.lang("infoManage").wtCusteRealNamePauseComfirm, function(){
                     require(["workoptSrc/gdgq"],function(gdgq){
                             gdgq.guaQi(record.applyId,cyqyzgzxspPage.gridObj);
                     });
                 });
			}
		},
		shenPi : function(obj){
			
			infoManage.seachMemberQuitDetail({applyId:obj.applyId},function(res){
				var quitData = res.data.MEMBER_QUIT;

				$('#dialogBox > div').html(_.template(cyqyzgzxsp_sp_dialogTpl,res.data));

				comm.initPicPreview("#qtwjck", quitData.otherFile);
				comm.initPicPreview("#qysdkcbg", quitData.reportFile);
				comm.initPicPreview("#qysfsmwj", quitData.statementFile);
				comm.initPicPreview("#ywblsqs", quitData.bizApplyFile);
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:comm.lang("infoManage").sptitle,
					width:"600",
					modal:true,
					closeIcon:true,
					buttons:{ 
						"审批通过":function(){
							cyqyzgzxspPage.approveQuitCommit(obj, true);
							$( this ).dialog( "destroy" );
						},
						"审批驳回":function(){
							cyqyzgzxspPage.approveQuitCommit(obj, false);
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
			
		},
		
		//审批提交
		approveQuitCommit : function(obj,isPass){
			
			var param = comm.getRequestParams(param);
			
			var postData = {
					applyId:obj.applyId,				//申请单编号（id）
					isPass : isPass,					//审批结果
					optEntName : param.custEntName,		//操作员所属公司名称
					optName : param.cookieOperNoName,			//操作员名字
					optCustId : param.custId,			//操作员客户号
					apprRemark : $("#apprRemark").val() //审批意见
			};
			//提交审批信息
			infoManage.commitMemberQuitAppr(postData,function(res){
				
				//刷新表单
				if(cyqyzgzxspPage.gridObj){
					cyqyzgzxspPage.gridObj.refreshPage();
				}
				
			});
		},
		
		fuHe : function(obj){

			infoManage.seachMemberQuitDetail({applyId:obj.applyId},function(res){
				
				var quitData = res.data.MEMBER_QUIT;
				
				$('#dialogBox > div').html(_.template(cyqyzgzxsp_fh_dialogTpl,res.data));
				
				comm.initPicPreview("#qtwjck", quitData.otherFile);
				comm.initPicPreview("#qysdkcbg", quitData.reportFile);
				comm.initPicPreview("#qysfsmwj", quitData.statementFile);
				comm.initPicPreview("#ywblsqs", quitData.bizApplyFile);
				
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:comm.lang("infoManage").fhtitle,
					width:"600",
					modal:true,
					closeIcon:true,
					buttons:{ 
						"复核通过":function(){
							$( this ).dialog( "destroy" );
							cyqyzgzxspPage.reviewQuitCommmit(obj,true);
						},
						"复核驳回":function(){
							$( this ).dialog( "destroy" );
							cyqyzgzxspPage.reviewQuitCommmit(obj,false);
						}
					}
				});
			});
		},
		//提交复核
		reviewQuitCommmit : function(obj,isPass){
			
			var param = comm.getRequestParams(param);
			
			var postData = {
					applyId:obj.applyId,				//申请单编号（id）
					isPass : isPass,					//审批结果
					optEntName : param.custEntName,		//操作员所属公司名称
					optName : param.cookieOperNoName,			//操作员名字
					optCustId : param.custId,			//操作员客户号
					apprRemark : $("#apprRemark").val() //审批意见
			};
			//提交审批信息
			infoManage.commitMemberQuitReview(postData,function(res){
				
				//刷新表单
				if(cyqyzgzxspPage.gridObj){
					cyqyzgzxspPage.gridObj.refreshPage();
				}
				
			});
		},
		//工单拒绝受理
		workflowRefuseAccept : function(applyId,bsGrid){
			
				$('#gdcz_gq_jjsl_dlg').html(_.template(jjgd_dialogTpl));	
				
				/*弹出框*/
				$("#gdcz_gq_jjsl_dlg").dialog({
					title:"拒绝受理工单",
					width:"660",
					height:"300",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"确定":function(){
							var dialogSelf=this;
							
							//参数验证
							if(!cyqyzgzxspPage.fromVerification()){
								return false;
							}
							
							// 双签验证
							comm.verifyDoublePwd($("#txtCheckAccount").val(),$("#txtCheckPwd").val(),$("#verificationMode").attr("optionvalue"),
							function(){
								//挂起业务单对应的工单
								var param={bizNo:applyId,remark:$("#txtReason").val()};
								
								common.workTaskRefuseAccept(param,function(rsp){
									//成功提示
									comm.alert({imgClass: 'tips_yes' ,content:comm.lang("infoManage")[22000],callOk:function(){
										
										//清除数据
										$(dialogSelf).dialog("destroy");
										 $('#gdcz_gq_jjsl_dlg').html("");
										 
										//刷新数据
										if(bsGrid!=null){
											bsGrid.refreshPage();
										}
									}});
								});
								
							});
						},
						"取消":function(){
							 $('#gdcz_gq_jjsl_dlg').html("");
							 $(this).dialog("destroy");
						}
					}
				});
		},
		/** 表单提交验证 */
		fromVerification:function(){
			return comm.valid({
				formID : '#fTermination',
				rules : {
					txtReason : {
						required : true,
						maxlength :  300
					},
					verificationMode : {
						required : true
					},
					txtCheckAccount : {
						required : function(){
							if($("#verificationMode").attr("optionvalue")=="1"){
								return true;
							}
							return false;
						}
					},
					txtCheckPwd : {
						required : function(){
							if($("#verificationMode").attr("optionvalue")=="1"){
								return true;
							}
							return false;
						}
					}
				},
				messages : {
					txtReason : {
						required :  comm.lang("workopt")[36048],
						maxlength : comm.lang("workopt").inp_len_err
					},
					verificationMode : {
						required : comm.lang("workopt").sel_chk_tp_err
					},
					txtCheckAccount : {
						required :  comm.lang("workopt")[34025]
					},
					txtCheckPwd : {
						required : comm.lang("workopt")[34026]
					}
				}
			});
		}
	};
	return cyqyzgzxspPage
});