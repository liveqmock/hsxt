define(['text!coDeclarationTpl/htgl/tab.html',
		'coDeclarationSrc/htgl/htcx'/*,
		'coDeclarationSrc/htgl/htgz',
		'coDeclarationSrc/htgl/htff',
		'coDeclarationSrc/htgl/htmbwh',
		'coDeclarationSrc/htgl/htmbfh'*/
		], function(tab, htcx/*, htgz, htff, htmbwh, htmbfh*/){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#htcx').click(function(){
				htcx.showPage();
				comm.liActive($('#htcx'),'#xzmb, #xgmb, #fh, #ffht, #htffls');
			}.bind(this)).click();
			
			/*$('#htgz').click(function(){
				htgz.showPage();
				comm.liActive($('#htgz'),'#xzmb, #xgmb, #fh, #ffht, #htffls');
			}.bind(this));
			
			$('#htff').click(function(){
				htff.showPage();
				comm.liActive($('#htff'),'#xzmb, #xgmb, #fh, #ffht, #htffls');
			}.bind(this));
			
			$('#htmbwh').click(function(){
				htmbwh.showPage();
				comm.liActive($('#htmbwh'),'#xzmb, #xgmb, #fh, #ffht, #htffls');
			}.bind(this));
			
			$('#htmbfh').click(function(){
				htmbfh.showPage();
				comm.liActive($('#htmbfh'),'#xzmb, #xgmb, #fh, #ffht, #htffls');
			}.bind(this));*/
			
		}	
	}	
});