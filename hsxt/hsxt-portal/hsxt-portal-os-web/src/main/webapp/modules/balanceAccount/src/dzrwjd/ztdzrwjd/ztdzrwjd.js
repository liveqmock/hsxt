define(['text!balanceAccountTpl/dzrwjd/ztdzrwjd/ztdzrwjd.html',
		],function(ztdzrwjdTpl){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(ztdzrwjdTpl)) ;
			
			var self = this;
		    
		    /*下拉列表*/
			$("#ztdzrwjd_tb_dzxt").selectList({
				borderWidth : 1,
				borderColor : '#CCC',
				options:[
					{name:'所有',value:'',selected : true},
					{name:'支付系统与上海银联',value:'GP-CH'},
					{name:'业务服务与支付系统',value:'BS-GP'},
					{name:'业务服务与账务系统',value:'BS-AC'},
					{name:'消费积分与账务系统',value:'PS-AC'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			$("#ztdzrwjd_tb_dzzt").selectList({
				borderWidth : 1,
				borderColor : '#CCC',
				options:[
					{name:'所有',value:'',selected : true},
					{name:'成功',value:'0'},
					{name:'失败',value:'1'},
					{name:'处理中',value:'2'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			/*end*/
			
			/*日期控件*/
			$("#dz_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#dz_endDate").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
		   	
		   	
			/*表格数据模拟*/
			var json = [{
							"dsys":"支付系统与上海银联",
							"ddate":"2015-12-23",
							"drate":"预处理",
							"dstat":"成功",
							"dinfo":"成功"
						},
						{
							"dsys":"支付系统与上海银联",
							"ddate":"2015-12-22",
							"drate":"明细对账",
							"dstat":"成功",
							"dinfo":"成功"
						},
						{
							"dsys":"业务服务与支付系统",
							"ddate":"2015-12-21",
							"drate":"明细对账",
							"dstat":"成功",
							"dinfo":"成功"
						}];	
			
			var gridObj = $.fn.bsgrid.init('ztdzrwjd_ql', {				 
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