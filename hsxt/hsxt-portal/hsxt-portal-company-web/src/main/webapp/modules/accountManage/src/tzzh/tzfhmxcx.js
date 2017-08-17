define(['text!accountManageTpl/tzzh/tzfhmxcx.html',
		'text!accountManageTpl/tzzh/tzfhxq.html',
		'accountManageDat/accountManage'
		], function(tpl, tzfhxqTpl,accountManage){
	var tzfhxqFun = {
		tzfhxqTpl : tzfhxqTpl,
		showPage : function(){
			$('#busibox').html(tpl);
			var self = this;
			
			comm.initSelect("#scpoint_beginDate",comm.lang("accountManage").years);
			comm.initSelect("#scpoint_endDate",comm.lang("accountManage").years);
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				var queryParam={
						'custID' :  $.cookie('custId'),
						"startYear":$("#scpoint_beginDate").val(),	
						"endYear":$("#scpoint_endDate").val(),	
						};

				accountManage.getPointDividendList("scpoint_result_table",queryParam,function(record, rowIndex, colIndex, options){
					if(colIndex == 2){
						return comm.formatMoneyNumber(record.totalDividend);
					}
					if(colIndex == 3){
						var link1 = $('<a title="查看详情">查看</a>').click(function(e) {
							//查看详情点击事件
							self.showDetail(record);
						}) ;
						return   link1 ;
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#scpoint_searchBtn').click();
			
		},
		validateData : function(){
			return comm.valid({
				formID : '#scpoint_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		showDetail : function(obj){
			//显示投资分红详情页签
			comm.liActive_add($('#tzzh_tzfhxq'));
			
			//隐藏列表页面
			$("#busibox").addClass('none');
			//加载投资分红详情页面
			$('#busibox_xq').removeClass('none').animate({scrollTop:"0px"}).html(this.tzfhxqTpl);
			var param1={'resNo' : obj.hsResNo , "dividendYear":obj.dividendYear};
			
			
			var searchTable2 = accountManage.getDividendDetailList("invest_details_table",param1,function(record, rowIndex, colIndex, options){
				if(colIndex == 0){
					return comm.formatDate(record.investDate,"yyyy-MM-dd");
				}
				if(colIndex == 1){
					return comm.formatMoneyNumber(record.investAmount);
				}
				if(colIndex == 3){
					return comm.formatMoneyNumber(record.normalDividend);
				}
				if(colIndex == 4){
					return comm.formatMoneyNumber(record.directionalDividend);
				}
				if(colIndex == 5){
					return comm.formatMoneyNumber(record.totalDividend);
				}
			});
			
			var aa = accountManage.getInvestDividendInfo(param1,function(res){
				$('#dividendInvestTotal').text(comm.formatMoneyNumber(res.data.dividendInvestTotal));
				$('#yearNormalDividend').text(comm.formatMoneyNumber(res.data.totalDividend));
				$('#yearDividendRate').text(res.data.yearDividendRate*100+'%');
				$('#investZq').text(obj.dividendYear+'-01-01 ~ '+obj.dividendYear+'-12-31');
			});

			
			
			//返回按钮
			$("#btn_fh").click(function(){
				//隐藏头部菜单
				comm.liActive($('#tzzh_tzfhmxcx'), '#tzzh_tzfhxq', '#busibox_xq');
			});
		}
	}
	return tzfhxqFun;
});