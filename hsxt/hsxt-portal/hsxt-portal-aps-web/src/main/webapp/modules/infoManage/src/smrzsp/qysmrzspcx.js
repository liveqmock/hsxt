define(['text!infoManageTpl/smrzsp/qysmrzspcx.html',
        'infoManageDat/infoManage'], function(qysmrzspcxTpl,infoManage){
	var qysmrzspcxPage = {
			
		showPage : function(){
			
			$('#busibox').html(qysmrzspcxTpl);	
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#statues",comm.lang("infoManage").realNameSerchStatus, null, null, {name:'全部', value:''});
			
			//初始化国家、省份、城市缓存
			cacheUtil.findProvCity();
			
			var gridObj;
			
			$("#searchBtn").click(function(){
				cacheUtil.findProvCity();
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						//search_entType : 3,			//企业互生号
						search_entResNo : $("#entResNo").val(),			//企业互生号
						search_entName : $("#entName").val(),			//企业名称
						search_endDate : $("#search_endDate").val(), 	//结束时间
						search_startDate : $("#search_startDate").val(), //开始时间
						search_status : comm.removeNull($("#statues").attr("optionvalue"))//状态
				};
				gridObj = comm.getCommBsGrid("searchTable","entrealnameidentific_pagelist",params,comm.lang("infoManage"),qysmrzspcxPage.detail);
				
			});
			
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				return comm.getRegionByCode(record.countryNo,record.provinceNo,record.cityNo,"-");
			}
			if(colIndex == 6){
				return comm.lang("infoManage").realNameSerchStatus[record.status];
			}
			if(colIndex  == 8){
				var	link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
					qysmrzspcxPage.chaKan(record);
				}) ;
				return link1;
			}
		},
		chaKan : function(obj){
			this.requireSubTab(obj);
		},
		requireSubTab : function(record){
			infoManage.queryDetailEntRealNameIdentific({applyId : record.applyId},function(res){
				var obj = res.data;
				obj.status = record.status;
				obj.applyId = record.applyId;
				require(['infoManageSrc/smrzsp/sub_tab'], function(tab){
					tab.showPage(obj);
				});	
			})
		}
	};
	return qysmrzspcxPage
});
