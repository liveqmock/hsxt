define(['text!infoManageTpl/zyzhgl/xhzxxxx_sj.html' 
], function(xhzxxxx_sjTpl){
	return {
		showPage : function(obj){
			$('#consumerBox').html(_.template(xhzxxxx_sjTpl,{obj:obj}));	
			$('#back_xfzzy').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzy'),'#ckqyxxxx, #ck');
			});
		}	
	}	
});