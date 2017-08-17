define(['text!accountPersonFckrTpl/hsbzh/tab.html',
		'accountPersonFckrSrc/hsbzh/mxcx',
		'accountPersonFckrLan'
		], function (tpl, mxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			mxcx.showPage();
			
			//明细查询
			$('#hsbzh_mxcx').click(function (e) {
				comm.liActive($(this));
				mxcx.showPage();
			});
		}
	}
});