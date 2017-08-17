define(['text!infoManageTpl/zyml/xhzxxxx_sj.html' 
], function(xhzxxxx_sjTpl){
	return {
		showPage : function(obj){
			$('#infobox').html(_.template(xhzxxxx_sjTpl,{obj:obj} ));
		 	
		 	//back
			$('#back_xfzzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyyl'),'#ckqyxxxx, #ck');
			});
		}	
	}	
});