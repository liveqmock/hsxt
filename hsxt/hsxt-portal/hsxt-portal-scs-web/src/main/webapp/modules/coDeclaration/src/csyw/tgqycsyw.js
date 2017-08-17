define(['text!coDeclarationTpl/csyw/tgqycsyw.html'], function(tgqycsywTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(tgqycsywTpl));
			
			/*日期控件*/
			$("#timeRange_start_tg").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_end_tg").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/	

			var json= [{
							"th_1":"05003240000",
							"th_2":"苏宁电器华强北店",
							"th_3":"深圳市华强北",
							"th_4":"张三",
							"th_5":"18929307299",
							"th_6":"待地区平台初审",
							"th_7":"2015-01-27" 
						},
						{
							"th_1":"05003240000",
							"th_2":"苏宁电器华强北店",
							"th_3":"深圳市华强北",
							"th_4":"李四",
							"th_5":"18929307299",
							"th_6":"待地区平台初审",
							"th_7":"2015-07-27" 
						}];	

			var gridObj;
			 
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >审批</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							this.shenpi();
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this)
					
				} 			
			});
 
			
			
			
		},
		
		shenpi : function(option){
			require(['coDeclarationSrc/csyw/sub_tab'],function(tab){
				tab.showPage();
			});	

		}
		
		
		
			
	}
});