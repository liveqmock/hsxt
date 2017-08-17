define(['text!balanceAccountTpl/dzycxdcx/ywyzwbjhd/ywyzwbjhd.html',
		],function(ywyzwbjhdTpl){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(ywyzwbjhdTpl)) ;
			
			var self = this;
		    
		    /*下拉列表*/
			$("#ywyzwbjhd_tb_dzjg").selectList({
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
			$("#ywyzwbjhd_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#ywyzwbjhd_endDate").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
		   	
		   	
			/*表格数据模拟*/
			var json = [{
							"ywyzwbjhd_date":"2015-12-23",
							"ywyzwbjhd_dzjg":"长款",
							"ywyzwbjhd_no":"AB000121213",
							"ywyzwbjhd_jzfl":"0000",
							"ywyzwbjhd_ywkhh":"00001",
							"ywyzwbjhd_ywzxje":"100.00",
							"ywyzwbjhd_ywjxje":"100.00",
							"ywyzwbjhd_ywzhlx":"",
							"ywyzwbjhd_ywjzzt":"",
							"ywyzwbjhd_zwkhh":"00002",
							"ywyzwbjhd_zwzxje":"100.00",
							"ywyzwbjhd_zwjxje":"100.00",
							"ywyzwbjhd_zwzhlx":"",
							"ywyzwbjhd_zwjysj":"2015-12-23 12:02:00"
						},
						{
							"ywyzwbjhd_date":"2015-12-23",
							"ywyzwbjhd_dzjg":"短款",
							"ywyzwbjhd_no":"AB000121213",
							"ywyzwbjhd_jzfl":"0000",
							"ywyzwbjhd_ywkhh":"00001",
							"ywyzwbjhd_ywzxje":"100.00",
							"ywyzwbjhd_ywjxje":"100.00",
							"ywyzwbjhd_ywzhlx":"",
							"ywyzwbjhd_ywjzzt":"",
							"ywyzwbjhd_zwkhh":"00002",
							"ywyzwbjhd_zwzxje":"100.00",
							"ywyzwbjhd_zwjxje":"100.00",
							"ywyzwbjhd_zwzhlx":"",
							"ywyzwbjhd_zwjysj":"2015-12-23 12:02:00"
						},
						{
							"ywyzwbjhd_date":"2015-12-23",
							"ywyzwbjhd_dzjg":"要素不一致",
							"ywyzwbjhd_no":"AB000121213",
							"ywyzwbjhd_jzfl":"0000",
							"ywyzwbjhd_ywkhh":"00001",
							"ywyzwbjhd_ywzxje":"100.00",
							"ywyzwbjhd_ywjxje":"100.00",
							"ywyzwbjhd_ywzhlx":"",
							"ywyzwbjhd_ywjzzt":"",
							"ywyzwbjhd_zwkhh":"00002",
							"ywyzwbjhd_zwzxje":"100.00",
							"ywyzwbjhd_zwjxje":"100.00",
							"ywyzwbjhd_zwzhlx":"",
							"ywyzwbjhd_zwjysj":"2015-12-23 12:02:00"
						}];	
			
			var gridObj = $.fn.bsgrid.init('ywyzwbjhd_ql', {				 
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