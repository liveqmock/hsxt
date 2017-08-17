/*福利资格查询-互生意外伤害保障*/
define(['text!scoremanageTpl/flzgcx/hsywsh.html',
        'scoremanageDat/pointWelfare',
        'scoremanageLan'
        ],function(hsywshTpl,pointWelfare){
	
	var self = {
			showPage : function(){
				$('#busibox').html(_.template(hsywshTpl));
				/** 查询事件 */
				$("#sc_hsywsh_btn").click(function(){
					self.pageQuery();
				});
			},

		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam={
						"search_hsResNo":$("#hsResNo").val(),
						"search_welfareType":$("#welfareType").val()
						};
			var gridObj= pointWelfare.listWelfareQualify("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				if(colIndex==5){
					var ret = "";
					var dateStr="";
					if(record.allFailureDateList){
						
						for(var i=0;i<record.allFailureDateList.length;i++){
							dateStr += record.allFailureDateList[i];
							dateStr += ",";
						}
						dateStr = dateStr.substring(0,dateStr.length-1);
						
						ret += "<span title='"+dateStr+"'>"+dateStr+"</span>";
						
					}
					return ret;
				}
			});
		}
	};
	return self;
});