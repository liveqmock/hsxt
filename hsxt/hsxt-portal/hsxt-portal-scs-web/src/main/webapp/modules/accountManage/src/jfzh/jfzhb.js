define(['text!accountManageTpl/jfzh/jfzhb.html'],function( jfzhbTpl ){
	return {	 	
		showPage : function(){
			$('#busibox').html(_.template(jfzhbTpl)) ;
			//Todo...
		 	 
			//提交
 			$('#jfzhb_tj').click(function(){ 		 							 
 					$('#div_jfzhb_tj').addClass('none');
 					$('#div_jfzhb_qr').removeClass('none');
 					
 					
 			});
 			
 			//修改
 			$('#jfzhb_xg').click(function(){ 				 
 					$('#div_jfzhb_tj').removeClass('none');
 					$('#div_jfzhb_qr').addClass('none');
 					
 					
 					
 			});
 			
 			//确认
 			$('#jfzhb_qr').click(function(){ 				 
 					
 					
 					comm.alert({content:'积分转货币成功！'});
 					
 			});
 			
				
		}
	}
}); 

 