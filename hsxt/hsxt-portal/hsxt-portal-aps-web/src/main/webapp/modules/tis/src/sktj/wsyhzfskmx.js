define(['text!tisTpl/sktj/wsyhzfskmx.html'], function(wsyhzfskmxTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(wsyhzfskmxTpl));	
			
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
			/*var json= [{
							"th_1":"xxx",
							"th_2":"1000.00",
							"th_3":"2015-08-20 10:09:00",
							"th_4":"手机"
						},
						{
							"th_1":"xxx",
							"th_2":"1000.00",
							"th_3":"2015-08-20 10:09:00",
							"th_4":"web"
						},
						{
							"th_1":"xxx",
							"th_2":"1000.00",
							"th_3":"2015-08-20 10:09:00",
							"th_4":"web"
						}];	*/

			/*var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
			});*/
			
			/*end*/	
		}	
	}	
});