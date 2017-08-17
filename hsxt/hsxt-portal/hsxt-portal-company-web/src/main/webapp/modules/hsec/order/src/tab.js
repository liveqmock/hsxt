define(['text!hsec_orderTpl/tab.html',
        'hsec_orderSrc/order/order'],
		function(tab,order){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			order.bindTabClick();
			order.bindData();
		}//end showPage;
	}
});