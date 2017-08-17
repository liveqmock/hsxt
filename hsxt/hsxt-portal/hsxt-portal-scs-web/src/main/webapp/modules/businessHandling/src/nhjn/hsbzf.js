define(['text!businessHandlingTpl/nhjn/hsbzf.html'],function( hsbzfTpl ){
	return {	 	
		showPage : function(){
			$('#contentWidth_2').html(_.template(hsbzfTpl)) ;
			//Todo...
		 	$('#hsbzf_xg').triggerWith('#nhjn_nhjn'); 
			$('#hsbzf_fk').click(function(){
					comm.alert({content:'缴纳系统使用年费成功！'})
			});
			 
 			
				
		}
	}
}); 

 