define(['text!debitTpl/lzgl/dhhsb.html'], function(dhhsbTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(dhhsbTpl));	
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"xxx",
							"th_2":"万科集团",
							"th_3":"张三",
							"th_4":"13800138000"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
			});
			
			/*end*/	
		}	
	}	
});