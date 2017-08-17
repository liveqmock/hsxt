define(['text!businessHandlingTpl/jnsh/jnsh.html',
		   'text!businessHandlingTpl/jnsh/hsbzf_xq.html',
		   'text!businessHandlingTpl/jnsh/wyzf_xq.html',
		   'businessHandlingDat/businessHandling'
		   ],function( jnshTpl ,hsbzf_xqTpl, wyzf_xqTpl,businessHandling ){
	return jnsh = {	 	
		showPage : function(){
			$('#contentWidth_2').html(_.template( jnshTpl )) ;
			//订单状态
			comm.initSelect("#sel_ddzt",comm.lang("businessHandling").jnsh_ddzt2,null, null, {name:'全部', value:''});
			//时间区间查询
			comm.initBeginEndTime("#search_startDate", "#search_endDate");
			comm.initSelect('#sel_kjrq', comm.lang("common").quickDateEnum);
			//快捷日期
			$("#sel_kjrq").change(function(e){
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});

			$('#btn_cx').click(function(){
				var valid = comm.queryDateVaild("search_form");
				if(!valid.form()){
					return false;
				}
				params = {
					search_beginDate : $("#search_startDate").val(),	//开始日期
					search_endDate : $("#search_endDate").val(),		//结束日期
					search_orderStatus :$("#sel_ddzt").attr("optionvalue")		//订单状态
				},
				comm.getCommBsGrid("tableDetail","find_entannualfee_list",params,comm.lang("businessHandling"),jnsh.detail);
				
			});	
		},
		detail : function(record, rowIndex, colIndex, options){
			switch(colIndex){
			
				case 0:
						return record.orderNo;
				case 1:
						return record.orderTime;
				case 2:
						return comm.lang("businessHandling").jnsh_ddlx[record.orderType];
				case 3:
						return comm.formatMoneyNumber(record.orderCashAmount);
				case 4:
						return comm.lang("businessHandling").jnsh_zffs[record.payChannel];
				case 5:
						return comm.lang("businessHandling").jnsh_ddzt[record.orderStatus];
				case 6:
						
						var link1 =  $('<a >'+comm.lang("businessHandling").chakan+'</a>').click(function(e) {
							//显示详情	
							businessHandling.queryAnnualfeeOrder({orderNo:record.orderNo},function(res){
								jnsh.showDetail(res.data);
							});
						});
						return   link1 ;
			}
		},
		showDetail : function(obj){
				obj.orderType = comm.lang("businessHandling").jnsh_ddlx[obj.order.orderType];
				obj.payChannel = comm.lang("businessHandling").jnsh_zffs[obj.order.payChannel];
				obj.orderStatus = comm.lang("businessHandling").jnsh_ddzt[obj.order.orderStatus];
				obj.orderChannel = comm.lang("common").accessChannel[obj.order.orderChannel];
				
				var title = comm.lang("businessHandling").title_annualfeeorder;
				
				if(obj.order.payChannel == 100){
					$('#jnsh_dialog').html(_.template(wyzf_xqTpl,obj));
					title = comm.lang("businessHandling").title_wy;
				}else{
					$('#jnsh_dialog').html(_.template(hsbzf_xqTpl,obj));
					title = comm.lang("businessHandling").title_hsb;
				}
				
				$('#jnsh_dialog').dialog({
					title : title,
					width:600 ,closeIcon:true
				});
		}
	};
	return jnsh;
}); 

 