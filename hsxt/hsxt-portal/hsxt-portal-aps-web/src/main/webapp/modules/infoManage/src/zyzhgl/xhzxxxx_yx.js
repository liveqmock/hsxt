define(['text!infoManageTpl/zyzhgl/xhzxxxx_yx.html' 
], function(xhzxxxx_yxTpl){
	return {
		showPage : function(num,obj){
			$('#consumerBox').html(_.template(xhzxxxx_yxTpl,{obj:obj} ));	
		 	
			$('#back_xfzzy').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzy'),'#ckqyxxxx, #ck');
			});
		}	
	}	
});