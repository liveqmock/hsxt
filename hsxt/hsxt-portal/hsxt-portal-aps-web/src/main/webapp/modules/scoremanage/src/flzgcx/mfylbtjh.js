/*福利资格查询-互生医疗补贴计划*/
define([ 'text!scoremanageTpl/flzgcx/mfylbtjh.html', 'scoremanageDat/pointWelfare','scoremanageLan' ],
		function(mfylbtjhTpl,pointWelfare) {
	return {
		showPage : function(){
			$('#busibox').html(_.template(mfylbtjhTpl));	
			
			var self = this;
	
			/** 查询事件 */
			$("#sc_mfylbtjh_btn").click(function(){
				self.pageQuery();
			});
			
			/*end*/		
		},
		/** 分页查询 */
		pageQuery:function(){
			var self=this;
			//查询参数
			var queryParam={
						"search_hsResNo":$("#hsResNo").val(),
						"search_welfareType":$("#welfareType").val()
						};
			var gridObj= pointWelfare.listWelfareQualify("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				
			}.bind(self));
		}
	}	
});