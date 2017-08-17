define(['text!toolmanageTpl/gjpzxx/gjlebxx.html',
		'text!toolmanageTpl/gjpzxx/gjlebxx_tj_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjlebxx_ck_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjlebxx_xg_dialog.html',
		'toolmanageDat/gjpzxx/gjlebxx',
        'toolmanageLan'
		], function(gjlebxxTpl, gjlebxx_tj_dialogTpl, gjlebxx_ck_dialogTpl, gjlebxx_xg_dialogTpl, dataModoule){
	return {
		showPage : function(){
			$('#busibox').html(_.template(gjlebxxTpl));
			
			dataModoule.listToolCategory(null, function(res){
				comm.getEasyBsGrid("searchTable", res.data, function(record, rowIndex, colIndex, options){
					if(colIndex == 1){
						return comm.getNameByEnumId(record['buyRules'], comm.lang("toolmanage").buyRules);
					}
					if(record['totalLimit'] == -1){
						return "不限制";
					}
					return record['totalLimit'];
				});
			});
		}
	}	
});