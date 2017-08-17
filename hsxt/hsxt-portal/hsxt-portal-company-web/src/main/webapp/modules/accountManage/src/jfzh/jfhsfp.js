define(['text!accountManageTpl/jfzh/census/jfhsfp.html',
        'accountManageDat/jfzh/jfzh',
        'accountManageSrc/jfzh/mxcx_xq'], function(tpl,jfzh,mxcx_xq){
	return jfhsfp={
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
				if(!comm.queryDateVaild('jfhsfp_form').form()){return;}
				jfzh.queryMlmListPage("tableDetail",params,jfhsfp.detail);
			});
		
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}else if(colIndex == 1){
				return record.calcStartTime+' - '+record.calcEndTime;
			}
			else if(colIndex == 2){
				return comm.formatMoneyNumber(record.points);
			}
			else if(colIndex == 3){
				return comm.formatMoneyNumber(record.totalRow);
			}
			var link1 =  $('<a>查看</a>').click(function(e) {
				var param = {
						transNo:record.totalId,
						transType:'U16000'
				};
				mxcx_xq.showPage('jfzh_xq_jfhsfp',param);
			});
			return link1;
		}
	};
});