define(['text!coDeclarationTpl/sqmcx/sqmcx.html',
        'coDeclarationDat/sqmcx/sqmcx'],function( sqmcxTpl ,dataModoule){
	return {	 	
		showPage : function(){
			var self = this;
			$('#contentWidth_2').html(_.template(sqmcxTpl));
			$('#queryBtn').click(function(){
				self.query();
			});
		},
		query : function(){
			var jsonParam = {
                    search_entResNo:$("#search_entResNo").val(),
                    search_entName:$("#search_entName").val(),
                    search_linkman:$("#search_linkman").val(),
			};
			dataModoule.findAuthCodeList(jsonParam, null);
		}
	}
}); 
