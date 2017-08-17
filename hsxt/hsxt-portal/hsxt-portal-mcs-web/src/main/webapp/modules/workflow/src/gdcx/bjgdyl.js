define(['text!workflowTpl/gdcx/bjgdyl.html',
        "workflowDat/workflow",
        "workflowSrc/gdcx/gdcxcomm",
        "workflowLan"], function(bjgdylTpl,workflow,gdcxcomm){
	return {
		showPage : function(){
			$('#busibox').html(_.template(bjgdylTpl));
			var self = this;
			
			/*快捷日期*/
			comm.initSelect("#quickDate",comm.lang("workflow").quickDateEnum);
			$("#quickDate").change(function(){
				gdcxcomm.quickDateChange($(this).attr("optionvalue"));
			});
			
			/*加载业务类型*/
			gdcxcomm.initBizAuthList();
			
			/*日期控件*/
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			
			/*查询数据*/	
			$('#bjglqry_btn').unbind('click').bind('click', function(){
				
				var valid = comm.queryDateVaild("bjgdyl_from");
				if (!valid.form()) {
					return;
				}
				self.searchOperation();	
			});
			
		},
		/** 分页查询 */
		searchOperation : function(){
			// 查询参数
			var queryParam={
				"search_bizType":$("#service_type").attr("optionvalue"),"search_status":"3",
				"search_startDate":$("#search_startDate").val(),"search_endDate":$("#search_endDate").val(),
				"search_queryResNo":$("#txtQueryResNo").val()
					};
			
			workflow.workOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				if(colIndex==3){ //无互生号时默认显示“-”
					$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["hsResNo"]);
					return (comm.isNotEmpty(record["hsResNo"]) ? record["hsResNo"] : "-");
					
				}
				
				if(colIndex==4){ //无客户名称时默认显示“-”
					$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["custName"]);
					return (comm.isNotEmpty(record["custName"]) ? record["custName"] : "-");
				}
				
				//转派历史过滤空值
				if(colIndex==8){
					if(!comm.isNotEmpty(record['assignHis'])){
						return comm.lang("workflow").transfer_prompt;
					}
					
					$("#searchTable tbody tr:eq("+rowIndex+") td:eq("+colIndex+")").attr("title",record["assignHis"]);
					return record['assignHis'];
				}
			});
		}	
	}	
});

