define(['text!hsec_refundTpl/refundList/tab.html',
        'hsec_refundSrc/refundList/refundList'],
		function(tab,refund){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			refund.bindData();
		}//end showPage;
	}
});