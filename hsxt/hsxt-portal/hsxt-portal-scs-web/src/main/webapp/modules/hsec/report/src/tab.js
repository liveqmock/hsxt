define(["text!hsec_complaintTpl/report/tab.html"
		,"hsec_reportSrc/report"]
		,function(tpl,reportAjax){
	return {
		showPage : function(){
			$(".operationsInner").html(_.template(tpl));
			
			$("#report").click(function(){
				reportAjax.bindData();
			}).click();
		}
	}
});
