define(['text!infoManageTpl/zyxxbgsp/xfzzyxxbgspcx.html',
        'infoManageDat/infoManage'], function(xfzzyxxbgspcxTpl,infoManage){
	
	var xfzzyxxbgspcx = {
		gridObj : null,
		showPage : function(){
			$('#busibox').html(_.template(xfzzyxxbgspcxTpl));
			
			/*日期控件*/
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect($("#statues"),comm.lang("infoManage").importantSerchStatus,null,null);
			
			//初始化页面
			$("#searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				//查询参数
				var queryParam={
								"search_perResNo":$.trim($('#search_perResNo').val()),	
								"search_status":comm.removeNull($("#statues").attr('optionValue')),	
								"search_startDate":$("#search_startDate").val(),
								"search_endDate":$("#search_endDate").val(),
								"search_perName":$.trim($('#search_custName').val())	
							};
				gridObj= infoManage.queryPerImportSpInfo(queryParam, xfzzyxxbgspcx.detail);
			});
			
//			$("#searchBtn").click();
			/*end*/	
		},
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 4){//所在地区
				return comm.getRegionByCode(null,record.provinceNo,record.cityNo);
			}else if(colIndex == 6){//互生卡状态
				return comm.getNameByEnumId(record['status'], comm.lang("infoManage").importantSerchStatus);
			}else if(colIndex == 8){//查看
				return $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
					var data = {applyId:record.applyId};
					infoManage.queryPerImportantInfo(data,function(res){
						var obj = res.data;
						obj.status = record.status;
						obj.applyId = record.applyId;
						xfzzyxxbgspcx.chaKan(obj);
					});
				}.bind(this));
			}
		},
		chaKan : function(obj){
			require(['infoManageSrc/zyxxbgsp/sub_tab'], function(tab){
				obj.optType = 'query';
				tab.showPage(obj);
			});
		}
		
	};
	return xfzzyxxbgspcx
});