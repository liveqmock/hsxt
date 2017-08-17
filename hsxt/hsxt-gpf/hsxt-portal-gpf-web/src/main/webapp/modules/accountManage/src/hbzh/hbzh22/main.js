define(['text!accountManageTpl/hbzh/hbzh22/hbzh22.html' ],function(hbzh22Tpl ){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(hbzh22Tpl)) ;
		    
		    
		    
		    //$('#btnNew').triggerTab(5);
		   	
		    
 		   	var testData =[
			{sn:'12341234132',date:'2015-07-09',tranType:'积分转货币',busiType:'支出',amount:123.00,balance:1234 },
			{sn:'12341234132',date:'2015-07-09',tranType:'消费积分分配',busiType:'收入',amount:123.00,balance:1234 } ];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('tableDetail', {				 
				url : comm.domainList['local']+ comm.UrlList["jfzhmxcx"] , 
				pageSize: 5 , 
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  //	localData: testData,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ gridObj.getCellRecordValue(rowIndex,3) +'" >查看</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							//alert(gridObj.getRecordIndexValue(record, 'ID'));
							//显示详情	
							var data_type=$(this).attr('data-type'); 
							if (data_type=='收入'){
								$('#jfzh_xq').click();								
							} else if (data_type=='支出'){
								$('#jfzh_mxcx_dialog > p').html(_.template(self.mxcx_zhbxqTpl));
								$('#jfzh_mxcx_dialog').dialog({title:'积分货币详情'}); 	
							} 
						});
						return   link1 ;
					}  
				} 			
			} ); 
		 	
			 
			
		   	
		 
 		   	
			
				
		}
	}
}); 

 