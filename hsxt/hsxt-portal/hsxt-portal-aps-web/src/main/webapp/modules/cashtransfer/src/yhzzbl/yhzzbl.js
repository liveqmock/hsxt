define(['text!cashtransferTpl/yhzzbl/yhzzbl.html',
        'cashtransferSrc/yhzzbl/plzz',
        'cashtransferSrc/yhzzbl/plcd',
		'cashtransferLan'], function(yhzzblTpl,plzz,plcd){
	var yhzzblFun = {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(yhzzblTpl));	
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			/*下拉列表事件*/
			comm.initSelect('#companyState', comm.lang("cashTransfer").companyStateEnum,null,null,{name:'全部', value:''});
			/*end*/
			
			/** 请求数据 **/
			$("#qry_yhzzbl_btn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						search_transReason	: 1,
						search_startDate	: $("#search_startDate").val().trim(),	//开始时间
						search_endDate		: $("#search_endDate").val().trim(),	//结束时间
						search_hsResNo		: $("#hsResNo").val().trim(),	//互生号/手机号
						search_entName		: $("#entName").val().trim(),	//单位名称
						search_transStatus	: 1,							//状态
						search_companyState	: $("#companyState").attr("optionvalue")	//状态
				};
				
				yhzzblFun.searchTable = comm.getCommBsGrid("searchTable","find_transferRecord_list",params,comm.lang("cashTransfer"),yhzzblFun.detail);
				
			});
			/** 请求数据结束 **/
			
			/*按钮事件*/
			$('#tjzz_btn').click(function(){
				var obj = yhzzblFun.searchTable.getCheckedRowsRecords();
				if(obj.length<1){
					comm.i_alert(comm.lang("cashTransfer").selectCommitMsg);
					return false;
				}
				plzz.showPage(obj, yhzzblFun.searchTable);
			});
			
			//撤单
			$('#cd_btn').click(function(){
				var obj = yhzzblFun.searchTable.getCheckedRowsRecords();
				if(obj.length<1){
					comm.i_alert(comm.lang("cashTransfer").selectCancleMsg);
					return false;
				}
				plcd.showPage(obj, yhzzblFun.searchTable);
			});
			/*end*/
		},
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 5){
				if(record.amount){
					return comm.fmtMoney(record.amount);
				}else{
					return "";
				}
			}
			
			if(colIndex == 7){
				return comm.lang("cashTransfer").transStatusEnum[record.transStatus];
			}
			
		}
	}
	return yhzzblFun;
});