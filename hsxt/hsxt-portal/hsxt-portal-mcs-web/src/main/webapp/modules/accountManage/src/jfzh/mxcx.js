define(['text!accountManageTpl/jfzh/mxcx.html',
        "accountManageDat/jfzh/jfzh",
        "accountManageLan"
		],function(mxcxTpl,jfzh){
	return  jfzhMxcx={
		showPage : function(name){			
			$('#busibox').html(_.template(mxcxTpl)) ;

		 	//载入下拉参数
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum);
		 	comm.initSelect($("#optTerm"),comm.lang("accountManage").businessEnum,null,0);
			
		 	//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			   
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
		   $("#qry_jfzhb").click(function(){
			   var valid = comm.queryDateVaild("fm_jfzhb_mxcx");
				if (!valid.form()) {
					return;
				}
				jfzhMxcx.pageQuery();
		   });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var jsonData = {
					"search_businessType":$("#optTerm").attr("optionValue"),
					"search_accType":"10110",
					"search_beginDate":$("#search_startDate").val(),
					"search_endDate":$("#search_endDate").val()
					};
			jfzh.integralDetailedPage(jsonData,function(record, rowIndex, colIndex, options){
				//渲染列:交易时间
				if(colIndex == 0){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				//渲染列:交易类型
				if(colIndex == 1){
					return jfzhMxcx.getTransType(record.transSys,record.transType);
				}
				//渲染列:业务类型
				if(colIndex == 2){
					return comm.lang("accountManage").businessEnum[record["businessType"]];
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
		},
	     getTransType : function(transSys,transType){
				var transTypeName="";
				if(transSys=='PS'){
					var tr=transType.charAt(3);
					 if(tr=='1' ||tr=='2'){
						transTypeName="消费积分撤单";
					}else{
						transTypeName= comm.lang("accountManage").transTypeEnum[transType];
					}
				}else{
					transTypeName= comm.lang("accountManage").transTypeEnum[transType];
				}
				return transTypeName;
		}
	}
}); 

