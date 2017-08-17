define(['text!accountManageTpl/jfzh/census/jfzzzfp.html', 
	     'accountManageDat/jfzh/jfzh'], function(tpl,jfzh){
		return jfzzzfp={
			showPage : function(){
				$('#busibox').html(tpl);
				//日期控件
				comm.initBeginEndTime('#search_beginDate','#search_endDate');
				//查询单击事件
				$('#scpoint_dj_searchBtn').click(function(){
					var params = {
							search_beginDate : comm.navNull($("#search_beginDate").val()),
							search_endDate : comm.navNull($("#search_endDate").val())
						};
					if(!comm.queryDateVaild('jfzzzfp_form').form()){return;}
					jfzh.queryBmlmListPage("tableDetail",params,jfzzzfp.detail);
				});
			
			},
			detail : function(record, rowIndex, colIndex, options){
				if(colIndex == 0){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				else if(colIndex == 1){
					return record.calcStartTime+' - '+record.calcEndTime;
				}
				else if(colIndex == 2){
					return comm.formatMoneyNumber(record.baseVal);
				}
				else if(colIndex == 3){
					return comm.formatMoneyNumber(record.pointREF);
				}
				else if(colIndex == 4){
					return comm.toPercent(record.percent);
				}
				else if(colIndex == 5){
					return comm.formatMoneyNumber(record.bmlmPoint);
				}
				else if(colIndex == 6){
					return comm.formatMoneyNumber(record.totalRow);
				}
			}
		};
	});