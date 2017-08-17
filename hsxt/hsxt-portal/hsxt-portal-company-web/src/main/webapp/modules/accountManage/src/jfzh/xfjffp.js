define(['text!accountManageTpl/jfzh/census/xfjffp.html',
        'accountManageSrc/jfzh/mxcx_xq'], function(tpl,mxcx_xq){
	return xfjffp = {
		showPage : function(){
			$('#busibox').html(tpl);
			//日期控件
			comm.initBeginEndTime("#search_beginDate","#search_endDate");
			//查询单击事件
			$('#scpoint_dj_searchBtn').click(function(){
				if(!comm.queryDateVaild('xfjffp_form').form()){return;}
				xfjffp.queryPage();
			});
		},
		// 分页查询消费积分分配列表
		queryPage : function(){
			var params = {};
			params.search_beginBatchNo = comm.removeNull($("#search_beginDate").val());
			params.search_endBatchNo = comm.removeNull($("#search_endDate").val());
			comm.getCommBsGrid("tableDetail","queryPointDetailSumPage", params,xfjffp.detail);
		},
		// 列表列转换
		detail: function(record, rowIndex, colIndex, options){
			if(colIndex == 1){ 
				return xfjffp.hzDateFormat(record.batchNo);   // 汇总时间 
			}else if(colIndex == 2){
				return comm.formatMoneyNumber(record.sum);    // 消费积分数
			}else if(colIndex == 4){
				return comm.formatMoneyNumber(record.backSum);// 撤单积分数
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.collect);// 获积分分配数
			}else if(colIndex == 7){
				var link1 =  null;
				link1 =  $('<a>查看</a>').click(function(e) {
					xfjffp.chakan(record);// 消费积分分配详情
				});
				return link1;
			}
		},
		// 汇总时间拼装
		hzDateFormat : function(date){
			return date+" 00:00:00" + "-" + date + " 23:59:59";
		},
		// 查看积分分配详情
		chakan : function(record){
            mxcx_xq.showPage('jfzh_xq_xhjffp',record.batchNo);
			$('#jfzh_xq_xhjffp').css('display','');
			$('#jfzh_xq_jfhsfp').css('display','none');
			$('#jfzh_xq_xhjffp').parent('ul').find('a').removeClass('active');
			$('#jfzh_xq_xhjffp').find('a').addClass('active');	
		}
	};
});