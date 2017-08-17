define(['text!accountManageTpl/hsbzh/census/yfzfkctjCZY.html',
        'text!accountManageTpl/hsbzh/census/yfzfkctjCZY_detail.html',
        'accountManageDat/hsbzh/hsbzh'], function(tpl,detailTpl,dataMoudle){
	return yfzfkctjCZY = {
	
		showPage : function(){
			var self = this;
			$('#busibox').html(tpl);
			comm.initBeginEndTime("#search_beginDate","#search_endDate");//日期控件
			self.operateNo();//操作员编号列表
			//查询单击事
			$('#scpoint_dsCZY_searchBtn').click(function(){
				if(!comm.queryDateVaild('scpoint_dsCZY_form').form()){ return;}
				self.queryPage();//统计和列表查询
			});
		},
		//统计和列表查询
		queryPage : function(){
			var params={};
			params.search_beginDate = $("#search_beginDate").val();
			params.search_endDate = $("#search_endDate").val();
			params.search_operCustName = $("#scpoint_dsCZY_code").val();
			dataMoudle.reportsPointDeductionByOperSum(params,function(res){
				var obj=res.data;
				if(obj){
					var ndetPoint = obj.ndetPoint || 0;
					var cdetPoint = obj.cdetPoint || 0;
					obj.sumPoint = (parseFloat(ndetPoint)-parseFloat(cdetPoint)).toFixed(2);
				}else{
					var obj = {};
					obj.ndetPoint = 0;
					obj.cdetPoint = 0;
					obj.sumPoint = 0.00;
				}
				$('#detailTpl').html(_.template(detailTpl,obj));
				dataMoudle.reportsPointDeductionByOper("scpoint_dsCZY_result_table",params,yfzfkctjCZY.detail);	
			});	
		},
		
		detail: function(record, rowIndex, colIndex, options){
			if(colIndex==2){
				return comm.formatMoneyNumber(record.ndetPoint);
			}
			if(colIndex==3){
				return comm.formatMoneyNumber(record.cdetPoint);
			}
			if(colIndex==4){
				return comm.formatMoneyNumber(parseFloat(record.ndetPoint)-parseFloat(record.cdetPoint));
			}
		},
		// 获取操作员编号列表查询
		operateNo : function(){
			dataMoudle.getOperaterNoList(null,function(res){
				var list = res.data;
				var queryOptions = [{"name":"","value":""}];
				$.each(list,function(n,v){
					queryOptions.push({"name":v.userName,"value":v.userName});
				});
				$("#scpoint_dsCZY_code").selectList({
					width:135,
					optionWidth:140,
					overflowY:'auto',
					options: queryOptions
				})
			});
		}
	};
});