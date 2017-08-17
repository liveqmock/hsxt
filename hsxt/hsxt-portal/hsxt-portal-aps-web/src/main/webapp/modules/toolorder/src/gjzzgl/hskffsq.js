define(['text!toolorderTpl/gjzzgl/hskffsq.html',
		'toolorderDat/toolorder',
		"commDat/common",
		], function(hskffsqTpl, toolorder,commonAjax){
	var self = {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(hskffsqTpl);
			
			$("#hskffsqQueryBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_receiver : $("#receiver").val().trim(), //收货人
					search_mobile : $("#mobile").val().trim()	//收货人手机
				};
				comm.getCommBsGrid("searchTable","hscardtoolapply",params,comm.lang("toolorder"));

			});
		},
	};
	return self;
});