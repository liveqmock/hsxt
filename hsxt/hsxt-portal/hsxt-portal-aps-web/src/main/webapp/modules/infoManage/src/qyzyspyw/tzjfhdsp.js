define(['text!infoManageTpl/qyzyspyw/tzjfhdsp.html',
		'text!infoManageTpl/qyzyspyw/tzjfhdsp_sp_dialog.html',
		'text!infoManageTpl/qyzyspyw/tzjfhdsp_fh_dialog.html',
		'infoManageDat/infoManage',
		'commDat/common'], 
		function(tzjfhdspTpl, tzjfhdsp_sp_dialogTpl, tzjfhdsp_fh_dialogTpl,infoManage,common){
	var tzjfhdspPage = {
			
		gridObj : null,
		showPage : function(){
			
			
			$('#busibox').html(_.template(tzjfhdspTpl));
			
			/*下拉列表*/
			comm.initSelect("#status",comm.lang("infoManage").memberStatuEnum);
	
			$("#searchBtn").click(function(){
				
				var params = {
						search_applyType : '0',			//申请类别 0为停止积分活动申请， 1为参与积分活动申请
						search_entResNo : $("#entResNo").val(),			//企业互生号
						search_entName : $("#entName").val(),			//企业名称
						search_linkman : $("#linkman").val(), 			//联系人
						search_status : $("#status").attr("optionvalue")//状态
				};
				tzjfhdspPage.gridObj = comm.getCommBsGrid("searchTable","activityapply_apprlist",params,comm.lang("infoManage"),tzjfhdspPage.detail,tzjfhdspPage.query);
				
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
				if(record.status == '1'){
					link1 = $('<a>'+comm.lang("infoManage").apprval+'</a>').click(function(e) {
						tzjfhdspPage.shenPi(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '2'){
					link1 = $('<a>'+comm.lang("infoManage").fuhe+'</a>').click(function(e) {
						tzjfhdspPage.fuHe(record);
						
					}.bind(this) ) ;
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
                            content : comm.lang("infoManage").wtStopPointRefuseComfirm,
                            callOk : function(){
                                    var param={"bizNo":record.applyId};
                                    common.workTaskRefuseAccept(param,function(rsp){
                                            //成功
                                            comm.alert({imgClass: 'tips_yes' ,content:comm.lang("infoManage")[22000],callOk:function(){
                                            	tzjfhdspPage.gridObj.refreshPage();
                                            }});
                                    });
                            }        
                    });
	            }.bind(this));
				
	            return link;
			}
		},
		query : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
			
				return comm.workflow_operate(comm.lang("infoManage").workTaskHangUp, comm.lang("infoManage").wtStopPointPauseComfirm, function(){
                    require(["workoptSrc/gdgq"],function(gdgq){
                            gdgq.guaQi(record.applyId,tzjfhdspPage.gridObj);
                    });
                });
			}
		},
		
		shenPi : function(obj){
			
			infoManage.serchPointActivityDetail({applyId:obj.applyId},function(res){
				
				$('#dialogBox > div').html(_.template(tzjfhdsp_sp_dialogTpl, res.data));
				
				//申请书附件文件id
				var fileId = res.data.POINT_ACTIVITY.bizApplyFile;
				
				//申请书在文件系统中的url访问地址
				var href = comm.getFsServerUrl(fileId);
				
				$("#ywblsqs").attr("href",href);
				
				
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"停止积分活动申请审批",
					width:"600",
					modal:true,
					closeIcon:true,
					buttons:{ 
						"审批通过":function(){
							tzjfhdspPage.approveShenpiCommit(obj, true);
							$( this ).dialog( "destroy" );
						},
						"审批驳回":function(){
							tzjfhdspPage.approveShenpiCommit(obj, false);
							$( this ).dialog( "destroy" );
						}
					}
				});
				
			});
			
		},
		//审批提交
		approveShenpiCommit : function(obj,isPass){
			
			var param = comm.getRequestParams(param);
			
			var postData = {
					applyId:obj.applyId,
					optCustId : param.custId,
					optEntName : param.custEntName,
					optName : param.cookieOperNoName,
					isPass : isPass,
					apprRemark : $("#apprRemark").val()
			};
			//提交审批信息
			infoManage.commitPointActivityAppr(postData,function(res){
				
				//刷新表单
				if(tzjfhdspPage.gridObj){
					tzjfhdspPage.gridObj.refreshPage();
				}
				
			});
		},
		fuHe : function(obj){
			
			infoManage.serchPointActivityDetail({applyId:obj.applyId},function(res){
				
				$('#dialogBox > div').html(_.template(tzjfhdsp_fh_dialogTpl, res.data));
				
				//申请书附件文件id
				var fileId = res.data.POINT_ACTIVITY.bizApplyFile;
				
				//申请书在文件系统中的url访问地址
				var href = comm.getFsServerUrl(fileId);
				
				$("#ywblsqs").attr("href",href);
					
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"停止积分活动申请复核",
					width:"600",
					modal:true,
					closeIcon:true,
					buttons:{ 
						"复核通过":function(){
							tzjfhdspPage.approveFuheCommit(obj, true);
							$( this ).dialog( "destroy" );
						},
						"复核驳回":function(){
							tzjfhdspPage.approveFuheCommit(obj, false);
							$( this ).dialog( "destroy" );
						}
					}
				});
				
			});
			
		},
		//提交复核
		approveFuheCommit:function(obj,isPass){
			
			var param = comm.getRequestParams(param);
			
			var postData = {
					applyId:obj.applyId,
					optCustId : param.custId,
					optEntName : param.custEntName,
					optName : param.cookieOperNoName,
					isPass : isPass,
					apprRemark : $("#apprRemark").val()
			};
			//提交复核信息
			infoManage.commitPointActivityReview(postData,function(res){
				
				//刷新表单
				if(tzjfhdspPage.gridObj){
					tzjfhdspPage.gridObj.refreshPage();
				}
				
			});
		}
	};
	return tzjfhdspPage
});