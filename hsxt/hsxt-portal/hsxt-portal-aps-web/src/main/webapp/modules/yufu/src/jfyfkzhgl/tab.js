define(['text!yufuTpl/jfyfkzhgl/tab.html',
		'yufuSrc/jfyfkzhgl/yjjl',
		'yufuSrc/jfyfkzhgl/yjsz'
		], function(tab, yjjl, yjsz){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));

			$('#yjjl').click(function(){
				yjjl.showPage();
				comm.liActive($('#yjjl'), '#xzgz, #xggz');
			}.bind(this)).click();
			
			$('#yjsz').click(function(){
				yjsz.showPage();
				comm.liActive($('#yjsz'), '#xzgz, #xggz');
			}.bind(this));
			
		}	
	}	
});