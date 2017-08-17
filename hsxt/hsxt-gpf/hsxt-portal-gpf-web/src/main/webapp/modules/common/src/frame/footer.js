
define(["text!commTpl/frame/footer.html"],function(tpl){
	//加载脚部文件 
	$('#footer').html(tpl).addClass('footer-box');
 	if ( Config.showFooter ){   
 		$('#footer').removeClass('none');
 	} else {  
 		$('#footer').addClass('none');
 	}	  	 	
	return tpl;	
});	