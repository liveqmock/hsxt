define(['text!accountManageTpl/tzzh/jftzmxcx.html',
        "accountManageDat/tzzh/tzzh",
		"accountManageLan"
        ],function( jftzmxcxTpl,tzzh ){
	var jftzMxcx= {	 	
		showPage : function(){
			$('#busibox').html(_.template(jftzmxcxTpl)) ;
			
			//载入下拉参数
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null,"");
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
			   jftzMxcx.pageQuery();
		   });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {search_beginDate:$("#search_startDate").val(),search_endDate:$("#search_endDate").val()};
			tzzh.pointInvestPage(param,function(record, rowIndex, colIndex, options){
				//渲染列：交易时间
				if(colIndex == 1){
					return comm.formatDate(record.investDate,"yyyy-MM-dd");
				}
				//渲染列：投资积分数
				if(colIndex==2){
					return comm.formatMoneyNumber(record["investAmount"]);	
				}
				//渲染列：交易后投资积分数 
				if(colIndex==3){
					return comm.formatMoneyNumber(record["accumulativeInvestCount"]);	
				}
			});
		}
	}
	return jftzMxcx;
}); 
