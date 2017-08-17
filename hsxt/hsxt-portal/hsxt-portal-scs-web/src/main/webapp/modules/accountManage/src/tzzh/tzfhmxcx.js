define(['text!accountManageTpl/tzzh/tzfhmxcx.html',
        "accountManageDat/tzzh/tzzh",
        "accountManageSrc/tzzh/tzfhmxcx_xq",
		"accountManageLan"
        ],function( tzfhmxcxTpl,tzzh,tzfhmxcx_xq){
	 var tzfhMxcx={	 	
		showPage : function(){
			$('#busibox').html(_.template(tzfhmxcxTpl)) ;
			
			//加载年份
			tzfhMxcx.loadSelectYear($("#beginYear"),false,"2010","2014");
			tzfhMxcx.loadSelectYear($("#endYear"),true);
			
			/** 年份下拉改变结束年份 */
			$("#beginYear").change(function(){
				 var getBeginYear=$(this).attr("optionValue"); //开始年份
			 	 
			 	 if(getBeginYear!=""){
			 		 //开始年份大于结束年份时，重新加载结束下拉年份
			 		tzfhMxcx.loadSelectYear($("#endYear"), false, getBeginYear);
			 		// }
			 	 }else {
			 		//填充年份
			 		selectId.attr({"optionValue":"","value":""});
			 	 }
			});
			
			//分页显示
			tzfhMxcx.pageQuery();
			
			//立即搜索单击事件
			$("#btnQuery").click(function () {
				var valid = tzfhMxcx.validateData();
				if (!valid.form()) {
					return;
				}
				//分页显示
				tzfhMxcx.pageQuery();
			});
			
			
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {search_beginYear:$("#beginYear").attr("optionValue"),search_endYear:$("#endYear").attr("optionValue")};
			tzzh.pointDividendPage(param,function(record, rowIndex, colIndex, options){
				//渲染列：发生金额
				if(colIndex==2){
					return comm.formatMoneyNumber(record["totalDividend"]);	
				}
				
				var link1 =  $('<a >查看</a>').click(function(e) {
		 			
					$('#tzzh_tzfhxq').click();
					
					tzfhmxcx_xq.showPage(record);
				 
				});
				return   link1 ;
			});
		},
		//验证年份选择
		validateData : function(){
			return $("#tzfhmxcx").validate({
				rules : {
					sshare_beginDate : {
						required : true
					},
					sshare_endDate : {
						required : true
					}
				},
				messages : {
					sshare_beginDate : {
						required : comm.lang("accountManage").beginYear,
					},
					sshare_endDate : {
						required : comm.lang("accountManage").endYear,
					}
				}
			});
		},
		/** 加载下拉年份 */
		loadSelectYear:function(selectId,isStart,startYear,selectedValue){
			var count; //开始循环年份
			var currentYear=new Date().getFullYear(); //获取当前年份
			var options=new Array(); 
			
			//循环开始年份
			if(isStart){
				count=2010;
			}else {
				count=startYear;
			}
			//清空文本数据
			selectId.attr({"optionValue":"","value":""});
			//填充年份
			for(var i=currentYear;i>=count;i--){
				options.push({name:i, value:i});
			}
			//绑定填充数据
			selectId.selectList({options:options});
			//绑定默认值
			if(selectedValue){
				selectId.selectListValue(selectedValue);
			}else{
				selectId.selectListValue(currentYear);
			}
		}
	}
	 return tzfhMxcx;
}); 
