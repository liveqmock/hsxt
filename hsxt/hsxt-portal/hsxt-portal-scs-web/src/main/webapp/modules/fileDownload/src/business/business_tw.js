define(['text!fileDownloadTpl/business/business_tw.html' ],function(businessTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_2').html(_.template(businessTpl)) ;			 
			//Todo...
		 	
			
			var testData =[
				{lx:'DOC', wjmc:'互生系统应用合同(成员企业)',ms:'成员企业应用系统使用' },
				{lx:'DOC', wjmc:'互生系统应用合同(服务公司)',ms:'服务公司应用系统使用' },
				{lx:'DOC', wjmc:'互生系统应用合同(托管企业)',ms:'托管企业应用系统使用' } 
		 	];
			
			var gridObj , self =this ;			 
			gridObj = $.fn.bsgrid.init('tableDetail', {				 
				//url : comm.domainList['local']+ comm.UrlList["jfzhmxcx"] , 
				pageSize: 10 , 
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-file="'+ gridObj.getCellRecordValue(rowIndex,2) +'" >下载</a>').click(function(e) {				 
							// 
							 
							
						 
						});
						return   link1 ;
					}  
				} 			
			} );		
			
			
				
		}
	}
}); 

 