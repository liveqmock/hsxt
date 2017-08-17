define(['text!accountManageTpl/census/DetailList_2.html'], function(tpl){
	return {
		showPage : function(){
			$('#busibox').html(tpl);
			var self = this,
			searchTable = null;
			
			searchTable = $.fn.bsgrid.init('detailList_2_table', {
			  //url: '',
			  localData: [{
							"jylsh":"W1111102156489645664894234",
							"jysj":"2014-08-28 10:11:53",
							"jylx":"线下销售结算",
							"hskh":"06032010000",
							"zffs":"xxx",
							"zfje":"194.2",
							"jfbl":"-",
							"kcjfje":"-",
							"jsje":"xxx",
							"jssj":"xxx"
						}],
			 
			  //不显示空白行
			  displayBlankRows: false,
			  //不显示无页可翻的提示
			  pageIncorrectTurnAlert: false,
			  //隔行变色
			  stripeRows: true,
			  //不显示选中行背景色
			  rowSelectedColor: false,
			  pageSize: 10
		  });
		},
	};
});