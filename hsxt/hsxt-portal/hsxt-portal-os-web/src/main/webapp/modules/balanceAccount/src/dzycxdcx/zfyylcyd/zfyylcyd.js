define(['text!balanceAccountTpl/dzycxdcx/zfyylcyd/zfyylcyd.html',
		],function(zfyylcydTpl){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(zfyylcydTpl)) ;
			
			var self = this;
		    
		    /*下拉列表*/
			$("#zfyylcyd_tb_dzjg").selectList({
				borderWidth : 1,
				borderColor : '#CCC',
				options:[
					{name:'所有',value:'',selected : true},
					{name:'长款',value:'0'},
					{name:'短款',value:'1'},
					{name:'要素不一致',value:'2'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			/*end*/
			
			/*日期控件*/
			$("#zfyylcyd_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#zfyylcyd_endDate").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
		   	
		   	
			/*表格数据模拟*/
			var json = [{
							"zfyylcyd_date":"2015-12-23",
							"zfyylcyd_dzjg":"长款",
							"zfyylcyd_no":"AB000121213",
							"zfyylcyd_pay":"100.00",
							"zfyylcyd_stat":"成功",
							"zfyylcyd_ylje":"100.00",
							"zfyylcyd_ylzt":"成功",
							"zfyylcyd_ylsj":"2015-12-23 12:02:00",
							"zfyylcyd_yllsh":"CD000121213"
						},
						{
							"zfyylcyd_date":"2015-12-23",
							"zfyylcyd_dzjg":"短款",
							"zfyylcyd_no":"AB000121213",
							"zfyylcyd_pay":"100.00",
							"zfyylcyd_stat":"成功",
							"zfyylcyd_ylje":"100.00",
							"zfyylcyd_ylzt":"失败",
							"zfyylcyd_ylsj":"2015-12-23 12:02:00",
							"zfyylcyd_yllsh":"CD000121213"
						},
						{
							"zfyylcyd_date":"2015-12-23",
							"zfyylcyd_dzjg":"要素不一致",
							"zfyylcyd_no":"AB000121213",
							"zfyylcyd_pay":"100.00",
							"zfyylcyd_stat":"成功",
							"zfyylcyd_ylje":"100.00",
							"zfyylcyd_ylzt":"成功",
							"zfyylcyd_ylsj":"2015-12-23 12:02:00",
							"zfyylcyd_yllsh":"CD000121213"
						}];	
			
			var gridObj = $.fn.bsgrid.init('zfyylcyd_ql', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 100 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
			});
			
			/*end*/		
				
		}
	}
}); 