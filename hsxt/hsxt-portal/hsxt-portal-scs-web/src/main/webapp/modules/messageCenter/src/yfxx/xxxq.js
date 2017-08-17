define(['text!messageCenterTpl/yfxx/xxxq.html' 
	],function(xxxqTpl ){
	return {
		 
		showPage : function(record){
			$('#xxzx_yfxx > a').removeClass('active');
			$('#xxzx_xxxq').css('display','').find('a').addClass('active');
			$('#xxzx_xxxq').siblings('li').not('#xxzx_yfxx').css('display','none');
			$('#busibox').html(_.template(xxxqTpl,record)) ;			 
			
			if(record.images!=null){
				$("#iamgeDiv").html("<img width='107' height='64' src='"+comm.getFsServerUrl(record.images)+"'/>");
			}
			comm.initPicPreview("#iamgeDiv", record.images);
			
			$('#btn_back').triggerWith('#xxzx_yfxx');
			
				
		}
	}
}); 

 