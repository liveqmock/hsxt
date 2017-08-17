define(["text!coDeclarationTpl/sqmcx/sqmcx.html",
        'coDeclarationDat/sqmcx/sqmcx'],function(sqmcxTpl, dataModoule){
	return {
		showPage : function(){
			$('#busibox').html(_.template(sqmcxTpl));
			var self = this;
			$('#queryBtn').click(function(){
				self.query();
			});
		},
		query : function(){
			var jsonParam = {
					search_entOwnerResNo:$("#search_entResNo").val(),
					search_entName:$("#search_entName").val(),
					search_linkman:$("#search_linkman").val(),
				};
			dataModoule.findAuthCodeList(jsonParam);
		}
	} 
});