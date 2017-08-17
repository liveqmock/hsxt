define(['text!workflowTpl/gdcx/gqgdyl.html',
		'text!workflowTpl/gdcx/gqgdyl_zrdb_dialog.html',
		'text!workflowTpl/gdcx/zzgd_dialog.html',
		"workflowDat/workflow",
		"workflowSrc/gdcx/gdcxcomm",
		"workflowLan"
		], function(gqgdylTpl, gqgdyl_zrdb_dialogTpl, zzgd_dialogTpl,workflow,gdcxcomm){
	var workFlowFun = {
		WFBsgrid:null,			//分页函数
		custIdIsChief:null, 	//当前操作员值班主任状态 true:是，false:否
		showPage : function(){
			$('#busibox').html(_.template(gqgdylTpl));	
			
			/*快捷日期*/
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
				
				//点击查询
				$('#gqgdqry_btn').unbind('click').bind('click',function(){
					if(!comm.queryDateVaild("gqgdyl_form").form()){
						return;
					}
					workFlowFun.searchOperation();
				});
				
				//值班主任操作
				if(workFlowFun.custIdIsChief){
					// 批量转入待办
					$('#plzrdb_btn').click(function(){
						//获取选中行
						var dataObj =workFlowFun.WFBsgrid.getCheckedRowsRecords();
						
						//判断有效数据
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
		/** 分页查询 */
		searchOperation : function(){
			
			// 查询参数
			var queryParam={
				"search_bizType":$("#service_type").attr("optionvalue"),"search_status":"4",
				"search_startDate":$("#search_startDate").val(),"search_endDate":$("#search_endDate").val(),
				"search_queryResNo":$("#txtQueryResNo").val()
			};
			
			workFlowFun.WFBsgrid=workflow.workOrderPage("searchTable",queryParam,
				function(record, rowIndex, colIndex, options){
				
					if(colIndex==4){ //无互生号时默认显示“-”
						$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["hsResNo"]);
						return (comm.isNotEmpty(record["hsResNo"]) ? record["hsResNo"] : "-");
					}
					
					if(colIndex==5){ //无客户名称时默认显示“-”
						$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["custName"]);
						return (comm.isNotEmpty(record["custName"]) ? record["custName"] : "-");
				}

				//转派历史过滤空值
				if(colIndex==10){
					if(!comm.isNotEmpty(record['assignHis'])){
						return comm.lang("workflow").transfer_prompt;
					}
					
					$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["assignHis"]);
					return record['assignHis'];
				}
				
				//当前操作员为值班主任时，才能操作手动派单
				if(workFlowFun.custIdIsChief){
					var link1 = $('<a>转入待办</a>').click(function(e) {
						workFlowFun.zrdb(record);
					});
					return link1;
				}
			});
		
		}
	}
	return workFlowFun;
});

