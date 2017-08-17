define(['text!infoManageTpl/zyml/xhzxxxx_yx.html' 
], function(xhzxxxx_yxTpl){
	return {
		showPage : function(obj){
			$('#infobox').html(_.template(xhzxxxx_yxTpl,{obj:obj} ));	
		 	
		 	//back
			$('#back_xfzzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyyl'),'#ckqyxxxx, #ck');
			});
		}	
	}	
});