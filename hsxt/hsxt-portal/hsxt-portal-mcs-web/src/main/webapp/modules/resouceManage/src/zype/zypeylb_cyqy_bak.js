define(['text!resouceManageTpl/zype/zypeylb_cyqy.html' ],function(zypeylbTpl ){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(zypeylbTpl)) ;			 
			//Todo...
		    
		    var testData =[
			{area:'上海',hb:'人民币',resCount:'111',compCount:'10' },
			{area:'深圳',hb:'人民币',resCount:'111',compCount:'10' },
			{area:'长沙',hb:'人民币',resCount:'111',compCount:'10' },
			{area:'北京',hb:'人民币',resCount:'111',compCount:'10' } ];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('tableDetail', {				 
				//url : comm.domainList['local']+ comm.UrlList["test1"] , 
				pageSize: 5 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){					 
						var link1 =  $('<a>查看</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							//alert(gridObj.getRecordIndexValue(record, 'ID'));
							//显示详情	
						   $('#xtzy_zype_cyqy_xq').click();
							 
							
						});
						return   link1 ;
					}  
				} 			
			} );
		    
			
				
		}
	}
}); 

 