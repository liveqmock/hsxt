define(['text!balanceAccountTpl/dzycxdcx/zfyylbjhd/zfyylbjhd.html',
		],function(zfyylbjhdTpl){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(zfyylbjhdTpl)) ;
			
			var self = this;
		    
		    /*下拉列表*/
			$("#zfyylbjhd_tb_dzjg").selectList({
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
			$("#zfyylbjhd_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#zfyylbjhd_endDate").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
		   	
		   	
			/*表格数据模拟*/
			var json = [{
							"zfyylbjhd_date":"2015-12-23",
							"zfyylbjhd_dzjg":"长款",
							"zfyylbjhd_no":"AB000121213",
							"zfyylbjhd_pay":"100.00",
							"zfyylbjhd_stat":"成功",
							"zfyylbjhd_ylje":"100.00",
							"zfyylbjhd_ylzt":"成功",
							"zfyylbjhd_ylsj":"2015-12-23 12:02:00",
							"zfyylbjhd_yllsh":"CD000121213"
						},
						{
							"zfyylbjhd_date":"2015-12-23",
							"zfyylbjhd_dzjg":"短款",
							"zfyylbjhd_no":"AB000121213",
							"zfyylbjhd_pay":"100.00",
							"zfyylbjhd_stat":"成功",
							"zfyylbjhd_ylje":"100.00",
							"zfyylbjhd_ylzt":"失败",
							"zfyylbjhd_ylsj":"2015-12-23 12:02:00",
							"zfyylbjhd_yllsh":"CD000121213"
						},
						{
							"zfyylbjhd_date":"2015-12-23",
							"zfyylbjhd_dzjg":"要素不一致",
							"zfyylbjhd_no":"AB000121213",
							"zfyylbjhd_pay":"100.00",
							"zfyylbjhd_stat":"成功",
							"zfyylbjhd_ylje":"100.00",
							"zfyylbjhd_ylzt":"成功",
							"zfyylbjhd_ylsj":"2015-12-23 12:02:00",
							"zfyylbjhd_yllsh":"CD000121213"
						}];	
			
			var gridObj = $.fn.bsgrid.init('zfyylbjhd_ql', {				 
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