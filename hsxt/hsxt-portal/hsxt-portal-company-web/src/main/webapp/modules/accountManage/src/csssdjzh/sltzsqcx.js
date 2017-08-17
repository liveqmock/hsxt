define(['text!accountManageTpl/csssdjzh/sltzsqcx/sltzsqcx.html',
		'text!accountManageTpl/csssdjzh/sltzsqcx/xq.html',
        "accountManageDat/csssdjzh/csssdjzh",
        "accountManageLan"
		], function(tpl, xqTpl,csssdjzh){
	return sltzsqcx = {
		showPage : function(){
			
			$('#busibox').html(tpl);
			
			//载入下拉参数  
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null,"");
		 	//comm.initSelect($("#optTerm"),comm.lang("accountManage").businessEnum,null,0);
			
			//时间控件
			comm.initBeginEndTime('#search_beginDate','#search_endDate');
		 	
			   
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_beginDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
		   //查看申请材料图片
			$("#applyImg a").live("click",function(){
				var imgRel=$(this).attr("rel");
				if(imgRel){
					var urlDownload=csssdjzh.getFsServerUrl(imgRel);
					window.open(urlDownload,'_blank') 
				}else{
					comm.alert({content:'图片不存在!'});
				}
			});
		   //查询
		   $("#btnQuery").click(function(){
			   if(!comm.queryDateVaild('tax_apply_form').form()){
					return;
				}
			   sltzsqcx.pageQuery();
		   });
		},
		showDetail : function(applyId){
			csssdjzh.taxApplyDetail({"applyId":applyId},function(rsp){
				$('#sltzsqcx-dialog > p').html(_.template(xqTpl, rsp.data));
				$('#sltzsqcx-dialog').dialog({
					title:'税率调整申请详情',
					closeIcon : true,
					/*open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},*/
					width:680
				});
			});
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var sltzsqcx=this;
			var param = {"search_beginDate":$("#search_beginDate").val(),"search_endDate":$("#search_endDate").val()};
			csssdjzh.taxAdjustmentApplyPage(param,function(record, rowIndex, colIndex, options){	
				if(colIndex==2){
					return ((parseFloat(record["currTaxrate"])*100).toFixed(2))+"%";
				}
				//列
				if(colIndex==3){
					return ((parseFloat(record["applyTaxrate"])*100).toFixed(2))+"%";
				}
				//状态列
				if(colIndex==4){
					return comm.lang("accountManage").taxStatusEnum[record["status"]];
				}
				if(colIndex==5){
				return $('<a title="查看详情">查看</a>').click(function(e){
					//查看详情点击事件
					sltzsqcx.showDetail(record["applyId"]);
				});
				}
			});
		}
	}
}); 

 