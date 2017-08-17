/*define(['text!workflowTpl/gdcx/zzgdyl.html',
        "workflowDat/workflow",
        "workflowSrc/gdcx/gdcxcomm",
        "workflowLan"], function(zzgdylTpl,workflow,gdcxcomm){
	return {
		showPage : function(){
			$('#busibox').html(_.template(zzgdylTpl));	
			var self = this;
			
			快捷日期
			comm.initSelect("#quickDate",comm.lang("workflow").quickDateEnum);
			$("#quickDate").change(function(){
				gdcxcomm.quickDateChange($(this).attr("optionvalue"));
			});
			
			gdcxcomm.initBizAuthList()	加载业务类型
			
			日期控件
			$("#timeRange_start").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_end").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#timeRange_end").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_start").datepicker('option', 'maxDate', new Date(d));	
				}
			});
		
			页面首次加载
			this.searchOperation();
			
			查询按钮	
			$('#zzgdqry_btn').unbind('click').bind('click', function(){
				self.searchOperation();
			});
			
		},
		searchOperation : function(){
			// 查询参数
			var queryParam={
				"search_bizType":$("#service_type").attr("optionvalue"),"search_status":"5",
				"search_startDate":$("#timeRange_start").val(),"search_endDate":$("#timeRange_end").val()
					};
			var gridObj=workflow.workOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				if(colIndex==2){
					return gdcxcomm.getBizAuthName(gridObj.getColumnValue(rowIndex,"bizType"));
				}
			});
		}
	}	
});

*/