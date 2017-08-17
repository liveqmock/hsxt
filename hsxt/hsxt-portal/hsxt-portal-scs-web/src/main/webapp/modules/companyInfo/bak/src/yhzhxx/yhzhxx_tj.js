define(['text!companyInfoTpl/yhzhxx/yhzhxx_tj.html' ],function(yhzhxxtjTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_4').html(_.template(yhzhxxtjTpl)) ;			 
			//Todo...
 			//选币种 
			$('#selectBZ').selectList({width:182,optionWidth:182});
			//选银行
			$("#brandSelect_modify").combobox();
			
			
			$("#selectProvince").selectList({width:88 });
			$("#selectCity").selectList({width:88 });
			
			
			
			//$('#modifyBt_yinghang').triggerWith('#qyxx_yhzhxxxg');
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
			
				
		}
	}
}); 

 