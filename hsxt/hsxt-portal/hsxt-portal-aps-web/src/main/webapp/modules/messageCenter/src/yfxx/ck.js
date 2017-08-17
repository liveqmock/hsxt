define(['text!messageCenterTpl/yfxx/ck.html' 
	],function(xxxqTpl ){
	return {
		showPage : function(record){
			$('#busibox').html(_.template(xxxqTpl,record));
			
			if(record.images!=null){
				$("#iamgeDiv").html("<img width='107' height='64' src='"+comm.getFsServerUrl(record.images)+"'/>");
			}
			comm.initPicPreview("#iamgeDiv", record.images);
			
			$('#btn_back').triggerWith('#052200000000_subNav_052202000000');
			comm.liActive_add($('#ck'));
		}
	}
}); 

 