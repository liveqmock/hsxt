define(['text!hsec_itemInfoFoodTpl/itemList/tab.html',
        'hsec_itemInfoSrc/itemList/itemListInfoHead',
        'hsec_itemInfoFoodSrc/itemList/itemListInfoHead'],
		function(tab,itemList,itemListFood){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;

			$("#itemList").click(function(){
				comm.liActive($("#itemList"));
				itemList.bindData();
			}.bind(this)).click();	
			
			$("#itemListFood").click(function(){
				comm.liActive($("#itemListFood"));
				itemListFood.bindData();
			}.bind(this));	
		}

	}
}); 