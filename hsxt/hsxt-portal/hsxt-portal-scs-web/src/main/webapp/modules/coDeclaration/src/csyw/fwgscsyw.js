define(['text!coDeclarationTpl/csyw/fwgscsyw.html'], function(fwgscsywTpl){
	return{
		showPage : function(){
			$('#busibox').html(_.template(fwgscsywTpl));
			
			/*日期控件*/
			$("#timeRange_start_fw").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_end_fw").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/	
			
			var json= [{
							"th_1":"UAT服务公司",
							"th_2":"UAT",
							"th_3":"18929307299",
							"th_4":"中国-福建-厦门",
							"th_5":"待地区平台初审",
							"th_6":"2015-08-10"
						},
						{
							"th_1":"UAT服务公司",
							"th_2":"UAT",
							"th_3":"18929307299",
							"th_4":"中国-福建-厦门",
							"th_5":"待地区平台初审",
							"th_6":"2015-08-14"
						}];	

			var gridObj;
			 
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
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