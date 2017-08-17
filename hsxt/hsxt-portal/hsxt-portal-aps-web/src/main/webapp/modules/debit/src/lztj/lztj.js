define(['text!debitTpl/lztj/lztj.html',
		'text!debitTpl/lztj/lztj_ck.html',
        'debitDat/debit',
		'debitLan'],function(lztjTpl, lztj_ckTpl,dataModoule){
	var lztjFun = {
		lztjBsGrid : null,
		showPage : function(){
			$('#busibox').html(_.template(lztjTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			//下拉列表
			dataModoule.listAccountMemu(null, function(res_) {
				$("#accountNameId").selectList({
					width : 500,
					optionWidth : 505,
					options : res_.data
				});
			});
			
			//分页查询
			$("#lztjquery").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_startDate:$("#search_startDate").val(),
						search_endDate:$("#search_endDate").val(),
						search_accountName:$("#accountName").val(),
				};
			 lztjFun.lztjBsGrid = dataModoule.listSumDebit("tj_searchTable",params,lztjFun.detail);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.formatMoneyNumber(record.payAllAmount);
			}else if(colIndex == 2){
				return comm.formatMoneyNumber(record.linkAllAmount);
			}else if(colIndex == 3){
				return comm.formatMoneyNumber(record.refundAllAmount);
			}else if(colIndex == 4){
				return comm.formatMoneyNumber(record.unlinkAllAmount);
			}else if(colIndex == 5){
				var link1 = $('<a>查看</a>').click(function(e) {
					lztjFun.chaKan(record);	
				}) ;
				return link1;
			}
		},
		/**
		 * 查看
		 */
		chaKan : function(obj){
			var params = {};
			params.search_receiveAccountName = obj.receiveAccountName;
			params.search_startDate = $("#search_startDate").val();
			params.search_endDate = $("#search_endDate").val();
			
			//页面控制
			$('#lztj_operation_tpl').html(_.template(lztj_ckTpl));
			comm.liActive_add($('#ckxq'));		
			$('#lztjTpl').addClass("none");
			$('#lztj_operation_tpl').removeClass("none");
			
			//加载详情
			$('#ck_export').attr('href',dataModoule.exporTempDebitSum(obj.receiveAccountName, params.search_startDate, params.search_endDate));
			dataModoule.sumDetail("xq_searchTable", params, lztjFun.chaKan_detail);	
			
			//返回
			$('#ck_back').click(function(){
				$('#lztjTpl').removeClass("none");
				$('#lztj_operation_tpl').addClass("none");
				$("#lztj_operation_tpl").html("");
				
				comm.liActive_add($('#lztj'));	
				$("#ckxq").addClass('tabNone');
			});
		},
		chaKan_detail : function(record, rowIndex, colIndex, options){			
			if(colIndex == 5){
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.linkAmount);
			}else if(colIndex == 7){
				if(record.debitStatus == 5){//退款中的显示为0
					return "0.00";
				}else{
					return comm.formatMoneyNumber(record.refundAmount);
				}
			}else if(colIndex == 8){
				return comm.formatMoneyNumber(record.unlinkAmount);
			}
		}
	} 
	
	return lztjFun;
});