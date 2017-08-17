define(['text!accountManageTpl/census/DetailList_1.html'], function(tpl){
	return {
		showPage : function(){
			$('#busibox').html(tpl);
			var self = this,
			searchTable = null;
			
			searchTable = $.fn.bsgrid.init('detailList_1_table', {
			  //url: '',
			  localData: [{
							"jylsh":"xxx",
							"jysj":"2015-11-11 11:11:11",
							"jylx":"xxx",
							"splx":"外卖",
							"zffs":"xxx",
							"zfje":"xxx",
							"hskh":"xxx",
							"ddh":"xxx",
							"ddje":"xxx",
							"kcsyfwf":"-",
							"kcjfje":"-",
							"jsje":"xxx",
							"jssj":"2015-12-12 12:12:12"
						},
						{
							"jylsh":"xxx",
							"jysj":"xxx",
							"jylx":"xxx",
							"splx":"零售",
							"zffs":"xxx",
							"zfje":"xxx",
							"hskh":"xxx",
							"ddh":"xxx",
							"ddje":"xxx",
							"kcsyfwf":"-",
							"kcjfje":"-",
							"jsje":"xxx",
							"jssj":"2015-12-12 12:12:12"
						},
						{
							"jylsh":"xxx",
							"jysj":"2015-11-11 11:11:11",
							"jylx":"xxx",
							"splx":"生活服务",
							"zffs":"xxx",
							"zfje":"xxx",
							"hskh":"xxx",
							"ddh":"xxx",
							"ddje":"xxx",
							"kcsyfwf":"-",
							"kcjfje":"-",
							"jsje":"xxx",
							"jssj":"2015-12-12 12:12:12"
						},
						{
							"jylsh":"xxx",
							"jysj":"2015-11-11 11:11:11",
							"jylx":"xxx",
							"splx":"餐饮",
							"zffs":"xxx",
							"zfje":"xxx",
							"hskh":"xxx",
							"ddh":"xxx",
							"ddje":"xxx",
							"kcsyfwf":"-",
							"kcjfje":"-",
							"jsje":"xxx",
							"jssj":"2015-12-12 12:12:12"
						},
						{
							"jylsh":"xxx",
							"jysj":"2015-11-11 11:11:11",
							"jylx":"xxx",
							"splx":"创业求职",
							"zffs":"xxx",
							"zfje":"xxx",
							"hskh":"xxx",
							"ddh":"xxx",
							"ddje":"xxx",
							"kcsyfwf":"-",
							"kcjfje":"-",
							"jsje":"xxx",
							"jssj":"2015-12-12 12:12:12"
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