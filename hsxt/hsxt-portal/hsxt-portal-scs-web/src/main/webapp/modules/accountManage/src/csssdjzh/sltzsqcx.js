define(['text!accountManageTpl/csssdjzh/sltzsqcx.html',
        'text!accountManageTpl/csssdjzh/sltzsqcx_dsh.html',
		'text!accountManageTpl/csssdjzh/sltzsqcx_tg_bh.html',
        "accountManageDat/csssdjzh/csssdjzh",
        "accountManageLan"
        ],function(sltzsqcxTpl,sltzsqcx_dshTpl,sltzsqcx_tg_bhTpl,csssdjzh){
	var sltzsqcxFun = {		
		showPage : function(){
			$('#busibox').html(_.template(sltzsqcxTpl)) ;	

		 	//载入下拉参数  
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null,"");
		 	//comm.initSelect($("#optTerm"),comm.lang("accountManage").businessEnum,null,0);
		 	
			//默认当天时间
			//var arr = comm.getTodaySE();
			//$("#search_startDate").val(arr[0]);
			//$("#search_endDate").val(arr[1]);
		 	
		 	//时间控件
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			   
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
		   $("#btnQuery").click(function(){
			   var valid = comm.queryDateVaild("search_form");
			   if(!valid.form()){
				   return false;
			   }
			   sltzsqcxFun.pageQuery();
		   });
		   
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {"search_beginDate":$("#search_startDate").val(),"search_endDate":$("#search_endDate").val()};
			csssdjzh.taxAdjustmentApplyPage(param,function(record, rowIndex, colIndex, options){
				//列
				if(colIndex==2){
					return (parseFloat(record["currTaxrate"])*100).toFixed(2)+"%";
				}
				//列
				if(colIndex==3){
					return (parseFloat(record["applyTaxrate"])*100).toFixed(2)+"%";
				}
				//状态列
				if(colIndex==4){
					return comm.lang("accountManage").taxStatusEnum[record["status"]];
				}
				
				var link1 =  $('<a tran-type="'+ record[4] +'" >查看</a>').click(function(e) {
					//查看
					sltzsqcxFun.chakan(record);
				});
				return link1 ;
				
			});
		},
		/**
		 * 查看申请详情
		 */
		chakan:function(record){
			var applyId = record["applyId"]; //获取申请编号
			csssdjzh.taxApplyDetail({"applyId":applyId},function(rsp){
				switch(record["status"]){
				case 0: 
					$('#sltzsqcx_dialog > p').html(_.template(sltzsqcx_dshTpl,rsp.data));
					$('#sltzsqcx_dialog').dialog({title:'税率调整申请详情',width:680 ,closeIcon:true}); 
					break;
				default: 
					$('#sltzsqcx_dialog > p').html(_.template(sltzsqcx_tg_bhTpl,rsp.data));
					$('#sltzsqcx_dialog').dialog({title:'税率调整申请详情',width:680 ,closeIcon:true}); 
					break;
				}
				
				//查看申请材料图片
				$("#applyImg a").click(function(){
					var imgRel=$(this).attr("rel");
					if(imgRel){
						var urlDownload=csssdjzh.getFsServerUrl(imgRel);
						window.open(urlDownload,'_blank') 
					}else{
						comm.alert({content:'图片不存在!'});
					}
				});
			});
		}
	}
	return sltzsqcxFun;
}); 

 