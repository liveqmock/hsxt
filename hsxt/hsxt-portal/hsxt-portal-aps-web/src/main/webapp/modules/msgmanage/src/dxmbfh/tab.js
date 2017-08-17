define(['text!msgmanageTpl/dxmbfh/tab.html',
		'msgmanageSrc/dxmbfh/dxmbfh'
		], function(tab, dxmbfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#dxmbfh').click(function(){
				dxmbfh.showPage();
				comm.liActive($('#dxmbfh'), '#xzdxmb, #xgdxmb, #fhnr');
				$('#fhDiv').addClass('none');
			}.bind(this)).click();
			
		}	
	}	
});