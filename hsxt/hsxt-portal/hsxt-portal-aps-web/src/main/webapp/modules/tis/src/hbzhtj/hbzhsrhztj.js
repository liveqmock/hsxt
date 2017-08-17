define(["text!tisTpl/hbzhtj/hbzhsrhztj.html"],function(hbzhsrhztjTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(hbzhsrhztjTpl));
			
			/*下拉列表*/
			$("#quickDate").selectList({
				options:[
					{name:'今天',value:'1'},
					{name:'本周',value:'2'},
					{name:'本月',value:'3'}
				]
			});
			
			$("#company_type").selectList({
				options:[
					{name:'xxx',value:'1'},
					{name:'xxx',value:'2'},
					{name:'xxx',value:'3'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"000514092300001009",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"xxx",
							"th_5":"xxx",
							"th_6":"xxx",
							"th_7":"xxx",
							"th_8":"xxx"
						},
						{
							"th_1":"000514092300001010",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"xxx",
							"th_5":"xxx",
							"th_6":"xxx",
							"th_7":"xxx",
							"th_8":"xxx"
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