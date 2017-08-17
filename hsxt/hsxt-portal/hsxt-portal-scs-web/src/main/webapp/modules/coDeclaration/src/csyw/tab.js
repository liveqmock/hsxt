define(['text!coDeclarationTpl/csyw/tab.html',
		'coDeclarationSrc/csyw/tgqycsyw',
		'coDeclarationSrc/csyw/cyqycsyw',
		'coDeclarationSrc/csyw/fwgscsyw'
		],function(tab,tgqycsyw,cyqycsyw,fwgscsyw){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;

			/*托管企业初审业务*/
			$('#tgqycsyw').click(function(e) {
                tgqycsyw.showPage();
				comm.liActive($('#tgqycsyw'));
            }.bind(this)).click(); 
			
			/*成员企业初审业务*/
			$('#cyqycsyw').click(function(e) {
                cyqycsyw.showPage();
				comm.liActive($('#cyqycsyw'));
            }.bind(this));
			
			/*服务公司初审业务*/
			$('#fwgscsyw').click(function(e) {
                fwgscsyw.showPage();
				comm.liActive($('#fwgscsyw'));
            }.bind(this));

		}

	}
}); 

