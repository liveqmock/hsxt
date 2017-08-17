define(["text!hsec_complaintTpl/complaint/tab.html"
		,"hsec_complaintSrc/complaint"]
,function(tpl,complaintAjax){
	return {
		showPage : function(){
			$(".operationsInner").html(_.template(tpl));
			
			$("#complaint").click(function(){
				complaintAjax.bindData();
			}).click();
		}
	}
});