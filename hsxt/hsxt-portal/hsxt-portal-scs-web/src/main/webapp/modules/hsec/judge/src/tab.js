define(['text!hsec_judgeTpl/judge.html',
        'hsec_judgeSrc/judgeActivity',
        'hsec_judgeSrc/evaluate'],
		function(tab,judgeAjax,evaluateAjax){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			//零售评价列表
			$("#retailEvaluate").click(function(){
				comm.liActive($("#retailEvaluate"));
				judgeAjax.bindData();
			}.bind(this));
			//餐饮评价列表
			$("#beverageEvaluate").click(function(){
				comm.liActive($("#beverageEvaluate"));
				evaluateAjax.foodShopsListData();
			}.bind(this)).click();
		}
	}
}); 