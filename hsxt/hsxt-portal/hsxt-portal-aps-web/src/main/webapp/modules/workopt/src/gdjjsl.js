define(['text!workoptTpl/jjslgd_dialog.html',
		"workoptDat/workopt",
		"workoptSrc/gdczcomm",
		"workoptLan"
		], function(jjslgd_dialogTpl,workopt,gdczcomm){
	var workoptFun = {
		bsGridPage:null,	//分页查询控件
		/** 工单拒绝受理 */
		confirmDoor:function(tmTask){
			$("#gdcz_gq_jjsl_dlg").dialog({
				title:"拒绝受理工单",
				width:"650",
				height:"400",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"拒绝受理":function(){
						var dialogSelf=this;
						//参数验证
						if(!gdczcomm.fromVerification()){
							return false;
						}
						
						//工单拒绝受理参数 
						comm.verifyDoublePwd($("#txtCheckAccount").val(),$("#txtCheckPwd").val(),$("#verificationMode").attr("optionvalue"),function(){
							var param={
									taskId:tmTask.taskId,reason:$("#txtReason").val(),
									checkType:$("#verificationMode").attr("optionvalue")
								};
							
							workopt.workOrderDoor(param,function(){
								comm.alert({imgClass: 'tips_yes' ,content:comm.lang("workopt")[22000],callOk:function(){
									$(dialogSelf).dialog("destroy");
									
									//刷新数据
									if(workoptFun.bsGridPage!=null){
										workoptFun.bsGridPage.refreshPage();
									}
								}});
							});
							
						});
					},
					"取消":function(){
						 $('#gd_dialog').html("");
						 $( this ).dialog( "destroy" );
					}
				}
			});
		},
		/** 拒绝受理确认提示 */
		jujueshouli : function(applyId,bsGrid){
			workoptFun.bsGridPage=bsGrid;
			
			//获取工单明细
			workopt.getTmTaskByBizNo({"applyId":applyId},function(rsp){
				var tmTask = rsp.data;
				
				//确认操作
				comm.confirm({
					width : 500,
					imgFlag : true,
					imgClass : 'tips_ques',
					content : '确定拒绝受理工单号：<span class="red">' + tmTask.taskId + '</span>?',
					callOk : function(){
						$('#gdcz_gq_jjsl_dlg').html(_.template(jjslgd_dialogTpl,tmTask));	
						
						//拒绝受理
						workoptFun.confirmDoor(tmTask);
						
						//验证方式
						gdczcomm.loadCheckWay();
					}
				});
			});
		}
	}
	return workoptFun;
});

