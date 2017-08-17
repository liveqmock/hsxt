define(['text!balanceAccountTpl/dzycxdcx/jfyzwbjhd/jfyzwbjhd.html',
		],function(jfyzwbjhdTpl){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(jfyzwbjhdTpl)) ;
			
			var self = this;
		    
		    /*下拉列表*/
			$("#jfyzwbjhd_tb_dzjg").selectList({
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
			$("#jfyzwbjhd_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#jfyzwbjhd_endDate").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
		   	
		   	
			/*表格数据模拟*/
			var json = [{
							"jfyzwbjhd_date":"2015-12-23",
							"jfyzwbjhd_dzjg":"长款",
							"jfyzwbjhd_no":"AB000121213",
							"jfyzwbjhd_jzfl":"0000",
							"jfyzwbjhd_jfkhh":"00001",
							"jfyzwbjhd_jfzxje":"100.00",
							"jfyzwbjhd_jfjxje":"100.00",
							"jfyzwbjhd_jfzhlx":"",
							"jfyzwbjhd_jfjzzt":"",
							"jfyzwbjhd_zwkhh":"00002",
							"jfyzwbjhd_zwzxje":"100.00",
							"jfyzwbjhd_zwjxje":"100.00",
							"jfyzwbjhd_zwzhlx":"",
							"jfyzwbjhd_zwjysj":"2015-12-23 12:02:00"
						},
						{
							"jfyzwbjhd_date":"2015-12-23",
							"jfyzwbjhd_dzjg":"短款",
							"jfyzwbjhd_no":"AB000121213",
							"jfyzwbjhd_jzfl":"0000",
							"jfyzwbjhd_jfkhh":"00001",
							"jfyzwbjhd_jfzxje":"100.00",
							"jfyzwbjhd_jfjxje":"100.00",
							"jfyzwbjhd_jfzhlx":"",
							"jfyzwbjhd_jfjzzt":"",
							"jfyzwbjhd_zwkhh":"00002",
							"jfyzwbjhd_zwzxje":"100.00",
							"jfyzwbjhd_zwjxje":"100.00",
							"jfyzwbjhd_zwzhlx":"",
							"jfyzwbjhd_zwjysj":"2015-12-23 12:02:00"
						},
						{
							"jfyzwbjhd_date":"2015-12-23",
							"jfyzwbjhd_dzjg":"要素不一致",
							"jfyzwbjhd_no":"AB000121213",
							"jfyzwbjhd_jzfl":"0000",
							"jfyzwbjhd_jfkhh":"00001",
							"jfyzwbjhd_jfzxje":"100.00",
							"jfyzwbjhd_jfjxje":"100.00",
							"jfyzwbjhd_jfzhlx":"",
							"jfyzwbjhd_jfjzzt":"",
							"jfyzwbjhd_zwkhh":"00002",
							"jfyzwbjhd_zwzxje":"100.00",
							"jfyzwbjhd_zwjxje":"100.00",
							"jfyzwbjhd_zwzhlx":"",
							"jfyzwbjhd_zwjysj":"2015-12-23 12:02:00"
						}];	
			
			var gridObj = $.fn.bsgrid.init('jfyzwbjhd_ql', {				 
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