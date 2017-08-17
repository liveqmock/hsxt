define(['text!companyInfoTpl/yhzhxx/yhzhxx_xg.html' ],function(yhzhxxxgTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_4').html(_.template(yhzhxxxgTpl)) ;			 
			//Todo...
 	 
			$("#brandSelect_modify").combobox();
			
			$("#selectProvince").selectList({width:142 });
			$("#selectCity").selectList({width:142 });
			
			//取消
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
			
			
				
		}
	}
}); 

 