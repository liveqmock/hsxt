define(['text!hsec_orderFoodTpl/tab.html',
        'hsec_orderFoodSrc/order'],
		function(tab,order){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;

			$("#orderfood0").click(function(){
				comm.liActive($("#orderfood0"));
				order.bindData();
			}.bind(this)).click();	
			
			for ( var i = 1; i <=6; i++) {
				(function(c){
					$("#orderfood"+c).click(function(){
						comm.liActive($("#orderfood"+c));
						order.bindData();
					}.bind(this));	
				})(i)
			}
			var scrollVal = 0  ; 
			//设置加菜浮动框正常位置 
			$(window).scroll(function(e,f){ 
				  if ($('#order_diancai_box').length){ 
				  		$('#order_diancai_box').css( 'top', $(window).scrollTop()+'px'     );
				  } 
			});
			
			
			
		}

	}
}); 