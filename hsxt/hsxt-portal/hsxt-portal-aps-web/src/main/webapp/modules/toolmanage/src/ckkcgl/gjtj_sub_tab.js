define(['text!toolmanageTpl/ckkcgl/gjtj_sub_tab.html',
		'toolmanageSrc/ckkcgl/qypzgjtj',
		'toolmanageSrc/ckkcgl/dqptgjgztj'
		], function(subTab, qypzgjtj, dqptgjgztj){
	return {
		showPage : function(){
			$('#busibox').html(_.template(subTab));
			
			$('#qypzgjtj').click(function(){
				qypzgjtj.showPage();
				comm.liActive($('#qypzgjtj'));
			}.bind(this));
			
			$('#dqptgjgztj').click(function(){
				dqptgjgztj.showPage();
				comm.liActive($('#dqptgjgztj'));
			}.bind(this)).click();
			
				
		}	
	}	
});
