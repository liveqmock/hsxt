define(["text!coDeclarationTpl/sqmcx/sqmcx.html",
        'coDeclarationDat/sqmcx/sqmcx',
        'coDeclarationLan'],function(sqmcxTpl, dataModoule){
	return {
		sqmcx_self : null,
		showPage : function(){
			sqmcx_self = this;
			$('#busibox').html(_.template(sqmcxTpl));
			$('#queryBtn').click(function(){
				sqmcx_self.query();
			});
		},
		/**
		 * 数据查询
		 */
		query : function(){
			var jsonParam = {
	                search_entResNo:$("#search_entResNo").val(),
	                search_entName:$("#search_entName").val(),
	                search_linkman:$("#search_linkman").val(),
			};
			dataModoule.findAuthCodeList(jsonParam, this.detail);
		},
		/**
		 * 发送短信
		 */
		detail : function(record, rowIndex, colIndex, options){
			var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
			if(record.appStatus&&record.appStatus == 7&&!serRegex.test(record.entResNo)) {
				return $('<a title="发送短信">发送短信</a>').click(function(e){
					dataModoule.sendAuthCode({applyId : record.applyId}, function(){
						comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
							sqmcx_self.query();
						}});
					});
				});
			}
			return '';
		}
	} 
});