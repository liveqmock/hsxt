define(['text!callCenterTpl/ldgl/tab.html',
		'callCenterSrc/ldgl/ldgllb',
		'callCenterSrc/ldgl/zzthld',
		'callCenterSrc/ldgl/yjld',
		'callCenterSrc/ldgl/yyly'
		], function(tab, ldgllb, zzthld, yjld, yyly){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			$('#ldgllb').click(function(){
				ldgllb.showPage();
				comm.liActive($('#ldgllb'));
			}.bind(this)).click();
			
			$('#zzthld').click(function(){
				zzthld.showPage();
				comm.liActive($('#zzthld'));
			}.bind(this));
			
			$('#yjld').click(function(){
				yjld.showPage();
				comm.liActive($('#yjld'));
			}.bind(this));
			
			$('#yyly').click(function(){
				yyly.showPage();
				comm.liActive($('#yyly'));
			}.bind(this));
			
		}
	}	
});