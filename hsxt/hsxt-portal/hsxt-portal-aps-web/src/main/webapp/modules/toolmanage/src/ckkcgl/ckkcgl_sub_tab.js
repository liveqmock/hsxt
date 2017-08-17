define(['text!toolmanageTpl/ckkcgl/ckkcgl_sub_tab.html',
		'toolmanageSrc/ckkcgl/gjrkls',
		'toolmanageSrc/ckkcgl/gjckls'
		], function(subTab, gjrkls, gjckls){
	return {
		showPage : function(){
			$('#busibox').html(_.template(subTab));
			
			$('#gjrkls').click(function(){
				gjrkls.showPage();
				comm.liActive($('#gjrkls'));
			}.bind(this)).click();
			
			$('#gjckls').click(function(){
				gjckls.showPage();
				comm.liActive($('#gjckls'));
			}.bind(this));
			
				
		}	
	}	
});
