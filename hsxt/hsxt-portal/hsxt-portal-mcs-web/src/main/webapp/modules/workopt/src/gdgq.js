define(['text!workoptTpl/gqgd_dialog.html',
		"workoptDat/workopt",
		"workoptSrc/gdczcomm",
		"commDat/common",
		"workoptLan"
		], function(gqgd_dialogTpl,workopt,gdczcomm,common){
	var workoptFun = {
		bsGridPage:null,	//分页查询控件
		applyId : null, 	//业务单号
		/**
		 * 工单挂起
		 */
		confirmSuspend:function(){
			/*弹出框*/
			$("#gdcz_gq_jjsl_dlg").dialog({
				title:"挂起工单",
				width:"660",
				height:"300",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"挂起":function(){
						var dialogSelf=this;
						
						//参数验证
						if(!gdczcomm.fromVerification()){
							return false;
						}
						
						// 双签验证
						comm.verifyDoublePwd($("#txtCheckAccount").val(),$("#txtCheckPwd").val(),$("#verificationMode").attr("optionvalue"),
						function(){
							//挂起业务单对应的工单
							var param={bizNo:workoptFun.applyId,remark:$("#txtReason").val()};
							
							common.workTaskHangUp(param,function(rsp){
								//成功提示
								comm.alert({imgClass: 'tips_yes' ,content:comm.lang("workopt")[22000],callOk:function(){
									
									//清除数据
									$(dialogSelf).dialog("destroy");
									 $('#gdcz_gq_jjsl_dlg').html("");
									 
									//刷新数据
									if(workoptFun.bsGridPage!=null){
										workoptFun.bsGridPage.refreshPage();
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
		/** 挂起确认提示 */
		guaQi : function(applyId,bsGrid){
			workoptFun.bsGridPage=bsGrid;
			workoptFun.applyId=applyId;
			
			//获取工单明细
		/*	workopt.getTmTaskByBizNo({"applyId":workoptFun.applyId},function(rsp){
				var tmTask = rsp.data;*/
				
				/*comm.confirm({
					width : 500,
					imgFlag : true,
					imgClass : 'tips_ques',
					content : '确定挂起业务号：<span class="red">' + workoptFun.applyId + '</span>的工单？',
					callOk : function(){*/
						$('#gdcz_gq_jjsl_dlg').html(_.template(gqgd_dialogTpl));	
						
						//工单挂起
						workoptFun.confirmSuspend();
						
						//验证方式
						gdczcomm.loadCheckWay();
			/*		} 	
				});
			});*/
		}
	}	
	return workoptFun;
});

