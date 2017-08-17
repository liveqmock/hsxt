define(['text!tisTpl/yfktj/yfkyjtj.html'], function(yfkyjtjTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(yfkyjtjTpl));	
			
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
							"th_4":"1000.00",
							"th_5":"1000.00",
							"th_6":"1000.00",
							"th_7":"1000.00",
							"th_8":"1000.00",
							"th_9":"1000.00"
						},
						{
							"th_1":"000514092300001010",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"1000.00",
							"th_5":"1000.00",
							"th_6":"1000.00",
							"th_7":"1000.00",
							"th_8":"1000.00",
							"th_9":"1000.00"
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