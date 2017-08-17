define(['text!accountManageTpl/hsbzh/mxcx.html',
			"accountManageDat/hsbzh/hsbzh",
			"accountManageDat/accountManage",
			"accountManageLan"
			],function( mxcxTpl,hsbzh,accountManage){
	var hsbzhMxcx={
		showPage : function(){
			$('#busibox').html(_.template(mxcxTpl)) ;
 			
 			//载入下拉参数
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null,"");
		 	comm.initSelect($("#optBusiType"),comm.lang("accountManage").businessEnum,null,0,{name:'全部',value:0});
		 	comm.initSelect($("#optHsbType"),comm.lang("accountManage").hsbAccTypeEnum,null,"20110");
		 	
		 	//时间控件
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
		   //查询
		   $("#btnQuery").click(function(){
			   var valid = comm.queryDateVaild("search_form");
			   if(!valid.form()){
				   return false;
			   }
			   hsbzhMxcx.pageQuery();
		   });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var jsonData = {
					"search_businessType":$("#optBusiType").attr("optionValue"),
					"search_accType":$("#optHsbType").attr("optionValue"),
					"search_beginDate":$("#search_startDate").val(),
					"search_endDate":$("#search_endDate").val()
					};
			hsbzh.detailedPage(jsonData,function(record, rowIndex, colIndex, options){
				//渲染列:交易时间
				if(colIndex == 0){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				//渲染列:交易类型
				if(colIndex == 1){
					return comm.lang("accountManage").transTypeEnum[record["transType"]];
				}
				//渲染列:业务类型
				if(colIndex == 2){
					return comm.lang("accountManage").businessTypeEnum[record["businessType"]];
				} 
				//渲染列：发生金额
				if(colIndex==3){
					return comm.formatMoneyNumber(record["amount"]);	
				}
				//渲染列：交易后金额  
				if(colIndex==4){
					if(record.accBalanceNew==null){
						return "--";
					}else{
						return comm.formatMoneyNumber(record.accBalanceNew);	
					}
				}
			});
		}
	}
	return hsbzhMxcx;
})
