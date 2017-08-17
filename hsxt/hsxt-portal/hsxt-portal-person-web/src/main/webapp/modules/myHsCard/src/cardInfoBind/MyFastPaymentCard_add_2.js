define(['text!myHsCardTpl/cardInfoBind/MyFastPaymentCard_add_2.html'], function(MyFastPaymentCard_add_2Tpl){
	return {
		showPage : function(obj){
			$('#kxxbd_content').html(_.template(MyFastPaymentCard_add_2Tpl, obj));
			
			$('#return_card_add').click(function(){
				
				$('#myhs_kxxbd').children('a[data-id="4"]').trigger('click');	
				$('#btn_addCard').click();
				
			});
			
		}	
	}	
});