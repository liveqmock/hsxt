define(['text!infoManageTpl/smrzsp/xfzsmrzspcx.html',
        'infoManageDat/infoManage'], function(xfzsmrzspcxTpl,infoManage){
	var xfzsmrzspcxPage = {
			
		showPage : function(){

			$('#busibox').html(xfzsmrzspcxTpl);	
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#xfz_statues",comm.lang("infoManage").realNameSerchStatus, null, null, {name:'全部', value:''});
			
			var gridObj;
			
			$("#xfzcx_searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						search_pResNo : $("#pResNo").val(),			//消费者互生号
						search_pName : $("#p_name").val(),			//消费者名称
						search_endDate : $("#search_endDate").val(), 	//结束时间
						search_startDate : $("#search_startDate").val(), //开始时间
						search_status : comm.removeNull($("#xfz_statues").attr("optionvalue"))//状态
				};
				gridObj = comm.getCommBsGrid("searchTable","perrealnameidentific_pagelist",params,comm.lang("infoManage"),xfzsmrzspcxPage.detail);
				
			});
			
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4)
			{
				return comm.lang("infoManage").realNameSerchStatus[record.status];
			}
			if(colIndex == 6)
			{
				link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
					xfzsmrzspcxPage.chaKan(record);
				}) ;
				return link1;
			}
		},
		chaKan : function(obj){
			xfzsmrzspcxPage.requireSubTab(obj);
		},
		requireSubTab : function(record){
			infoManage.queryDetailPerRealNameIdentific({applyId : record.applyId},function(res){
				var obj = res.data;
				obj.status = record.status;
				obj.applyId = record.applyId;
				require(['infoManageSrc/smrzsp/sub_tab'], function(tab){
					
					tab.showPage(obj);
					
				});	
			})
		}
		
			
	};
	return xfzsmrzspcxPage
});
