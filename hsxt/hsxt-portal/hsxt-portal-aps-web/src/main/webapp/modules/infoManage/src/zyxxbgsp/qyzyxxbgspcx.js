define(['text!infoManageTpl/zyxxbgsp/qyzyxxbgspcx.html',
        'infoManageDat/infoManage'],
        function(qyzyxxbgspcxTpl,infoManage){
	var qyzyxxbgspcxPage = {
		showPage : function(){
			
			$('#busibox').html(_.template(qyzyxxbgspcxTpl));
			
			comm.initSelect($("#statues"),comm.lang("infoManage").importantSerchStatus,null,null);
			
			/*日期控件*/
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#statues",comm.lang("infoManage").importantSerchStatus);
			
			//初始化国家、省份、城市缓存
			cacheUtil.findProvCity();
			
			$("#searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				//查询参数
				var queryParam={
								"search_entResNo":$.trim($('#search_entResNo').val()),	
								"search_status":comm.removeNull($("#statues").attr('optionValue')),	
								"search_startDate":$("#search_startDate").val(),
								"search_endDate":$("#search_endDate").val(),
								"search_entName":$.trim($('#search_entName').val())	
							};
				var gridObj= infoManage.queryEntImportSpInfo(queryParam,qyzyxxbgspcxPage.detail);
			});
			
			/*end*/	
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){//所在地区
				//获取地区信息
				return comm.getRegionByCode(record.countryNo,record.provinceNo,record.cityNo,"-");
			}else if(colIndex == 6){//互生卡状态
				return comm.getNameByEnumId(record['status'], comm.lang("infoManage").importantSerchStatus);
			}else if(colIndex == 8){//查看
				return $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
					var data = {applyId:record.applyId};
					infoManage.queryEntimportantinfochange(data,function(res){
						var obj = res.data;
						obj.status = record.status;
						obj.applyId = record.applyId;
						qyzyxxbgspcxPage.chaKan(obj);
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
	return qyzyxxbgspcxPage
});