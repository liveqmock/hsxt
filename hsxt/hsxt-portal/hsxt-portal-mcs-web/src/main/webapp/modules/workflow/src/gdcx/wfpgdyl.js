define(['text!workflowTpl/gdcx/wfpgdyl.html',
		'text!workflowTpl/gdcx/gqgdyl_zrdb_dialog.html',
		'text!workflowTpl/gdcx/zzgd_dialog.html',
		'text!workflowTpl/gdcx/gqgd_dialog.html',
		"workflowDat/workflow",
		"workflowSrc/gdcx/gdcxcomm",
		"workflowLan"
		], function(wfpgdylTpl, gqgdyl_zrdb_dialogTpl,zzgd_dialogTpl, gqgd_dialogTpl,workflow,gdcxcomm){
	var workFlowFun = {
		WFBsgrid:null,			//分页函数
		custIdIsChief:null, 	//当前操作员值班主任状态 true:是，false:否
		showPage : function(){
			$('#busibox').html(_.template(wfpgdylTpl));	
			
			//快捷日期
			comm.initSelect("#quickDate",comm.lang("workflow").quickDateEnum);
			$("#quickDate").change(function(){
				gdcxcomm.quickDateChange($(this).attr("optionvalue"));
			});
			
			//加载业务类型
			gdcxcomm.initBizAuthList();	
			
			//日期控件
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			
			//获取操作员是否为值班主任
			workflow.isChief(function(rsp){
				workFlowFun.custIdIsChief = rsp.data;
				
				//查询
				$('#wfpgdqry_btn').unbind('click').bind('click',function(){
					if(!comm.queryDateVaild("blzgdyl_form").form()){
						return;
					}
					workFlowFun.searchOperation();
				});
				
				
				//值班主任操作
				if(workFlowFun.custIdIsChief){
					
					//批量转入待办
					$('#plzrdb_btn').click(function(){
						//获取选中行
						var dataObj =workFlowFun.WFBsgrid.getCheckedRowsRecords();
						
						if(dataObj.length == 0){
							comm.alert({
								imgClass : 'tips_error',
								content : '至少选择一行数据执行批量转入待办业务！'
								
							});
						}
						else{
							workFlowFun.showDialog(dataObj, 460);
							$('#zrdbTable').parent('div').addClass('zrdbTable_scroll');
							comm.scrollTable('zrdbTable');
						}
						
						//隐藏标题栏
						$("#zrdbTable>thead>tr:eq(0)").hide();
					});
				}else{
					$('#plzrdb_btn').hide();
				}
			});
		},
		/** 转入待办 */
		zrdb : function(obj){
			var dataObj = [];
			dataObj.push(obj);
			this.showDialog(dataObj, 330);
			$('#zrdbTable_pt_outTab').remove();	
		},
		/** 批量转入待办 */
		showDialog : function(dataObj, h){
			$('#gd_dialog').html(gqgdyl_zrdb_dialogTpl);	
			//加载公共方法
			gdcxcomm.workFlowToDo(dataObj,workFlowFun.WFBsgrid);
		},
		/** 工单挂起 */
		guaQi : function(obj){
			comm.confirm({
				width : 500,
				imgFlag : true,
				imgClass : 'tips_ques',
				content : '确定挂起工单号：<span class="red">' + obj.taskId + '</span>的工单？',
				callOk : function(){
					$('#gd_dialog').html(_.template(gqgd_dialogTpl,obj));	
					
					/*弹出框*/
					$( "#gd_dialog" ).dialog({
						title:"挂起工单",
						width:"660",
						height:"350",
						modal:true,
						closeIcon : true,
						buttons:{ 
							"挂起":function(){
								var dialogSelf=this;
								
								//参数验证
								if(!gdcxcomm.fromVerification()){
									return false;
								}
								
								/** 双签验证 */
								comm.verifyDoublePwd($("#txtCheckAccount").val(),$("#txtCheckPwd").val(),$("#verificationMode").attr("optionvalue"),
								function(){
									//工单挂起
									var param={
											taskId:obj.taskId,reason:$("#txtReason").val(),
											checkType:$("#verificationMode").attr("optionvalue")
										};
									workflow.workOrderSuspend(param,function(){
										comm.alert({imgClass: 'tips_yes' ,content:comm.lang("workflow")[22000],callOk:function(){
											$(dialogSelf).dialog("destroy");
											workFlowFun.WFBsgrid.refreshPage();
										} });
									});
								});
								
							},
							"取消":function(){
								 $('#gd_dialog').html("");
								 $(this).dialog("destroy");
							}
						}
					});
					
					//验证方式
					gdcxcomm.loadCheckWay();
					
					$("#td_gqgd_jbr").text("发起时间");
					$("#gqgd_jbr").text(obj.createdDate);
					$("#gqgd_sj_name").text("");
					$("#gqgd_sj_txt").text("");
				} 	
			});
		},
		/** 分页查询*/
		searchOperation : function(){

			// 查询参数
			var queryParam={
				/*"search_executor":$("#txtExecutor").val(),*/
				"search_bizType":$("#service_type").attr("optionvalue"),
				"search_status":"0",
				"search_startDate":$("#search_startDate").val(),
				"search_endDate":$("#search_endDate").val(),
				"search_queryResNo":$("#txtQueryResNo").val()
			};
			
			//分页查询
			workFlowFun.WFBsgrid = workflow.workOrderPage("searchTable",queryParam,
			function(record, rowIndex, colIndex, options){
				
				if(colIndex==3){ //无互生号时默认显示“-”
					$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["hsResNo"]);
					return (comm.isNotEmpty(record["hsResNo"]) ? record["hsResNo"] : "-");
				}
				if(colIndex==4){ //无客户名称时默认显示“-”
					$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["custName"]);
					return (comm.isNotEmpty(record["custName"]) ? record["custName"] : "-");
				}
				
				//当前操作员为值班主任时，才能操作手动派单
				if(workFlowFun.custIdIsChief){
					var link1 = $('<a>转入待办</a>').click(function(e) {
						workFlowFun.zrdb(record);
					}) ;
					return link1;
				}
			},
			function(record, rowIndex, colIndex, options){
				if(colIndex==6 && workFlowFun.custIdIsChief){
					var link4 = $('<a>挂起</a>').click(function(e) {
						workFlowFun.guaQi(record);
					});
					return link4;
				}
			});
				
		}
	}	
	return workFlowFun;
});

