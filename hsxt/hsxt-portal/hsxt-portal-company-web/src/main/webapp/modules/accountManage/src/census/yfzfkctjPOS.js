define(['text!accountManageTpl/census/yfzfkctjPOS.html',
        'text!accountManageTpl/census/yfzfkctjPOS_detail.html',
        'accountManageDat/hsbzh/hsbzh'], function(tpl,detailTpl,dataMoudle){
    	return yfzfkctjPOS = {
	showPage : function(){
		$('#busibox').html(tpl);
		
		
		/*日期控件*/
		comm.initBeginEndTime("#search_beginDate","#search_endDate");
		
		comm.initSelect('#scpoint_dsPOS_subject', comm.lang("accountManage").channel);
		//查询单击事件
		$('#scpoint_dsPOS_searchBtn').click(function(){
			if(!comm.queryDateVaild('scpoint_dsPOS_form').form()){
				return;
			}
			var params={
					search_beginDate : $("#search_beginDate").val(),
					search_endDate : $("#search_endDate").val(),
					search_equipmentNo: $("#scpoint_dsPOS_equipmentNo").val(),
					search_channelType: $("#scpoint_dsPOS_subject").attr("optionvalue")==0 ?'':$("#scpoint_dsPOS_subject").attr("optionvalue")
			};
			dataMoudle.reportsPointDeductionByChannelSum(params,function(res){
				var obj=res.data;
				if(obj==null){
				    obj={
				    		ndetPoint:0,	
				    		cdetPoint:0
				    }
				}
				obj.sumPoint=(parseFloat(obj.ndetPoint)-parseFloat(obj.cdetPoint)).toFixed(2);
				$('#detailTpl').html(_.template(detailTpl,obj));
				dataMoudle.reportsPointDeductionByChannel("scpoint_dsPOS_result_table",params,yfzfkctjPOS.detail);	
			});	
		});
	},
	detail: function(record, rowIndex, colIndex, options){
		if(colIndex == 1){
			return comm.lang("common").accessChannel[record["channelType"]];
		} 
		if(colIndex==3){
			return comm.formatMoneyNumber(record.ndetPoint);
		}
		if(colIndex==4){
			return comm.formatMoneyNumber(record.cdetPoint);
		}
		if(colIndex==5){
			return comm.formatMoneyNumber(parseFloat(record.ndetPoint)-parseFloat(record.cdetPoint));
		}
	}
};
});