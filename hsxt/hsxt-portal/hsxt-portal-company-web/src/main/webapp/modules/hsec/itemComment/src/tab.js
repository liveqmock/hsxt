define(['text!hsec_itemCommentTpl/tab.html',
        'hsec_itemCommentSrc/itemComment',
        'hsec_itemCommentSrc/evaluate'
        ],
		function(tab,ajax,evaluateAjax){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			
			$("#evgoods").click(function(){
				comm.liActive($("#evgoods"));
				ajax.bindData();
			}.bind(this)).click();
			
			$("#evshop").click(function(){
				comm.liActive($("#evshop"));
				evaluateAjax.foodShopsListData();
			}.bind(this));
		}
	}
}); 