define(['text!accountManageTpl/jfyfkzh/tab.html',
		'accountManageSrc/jfyfkzh/zhye',
		'accountManageSrc/jfyfkzh/yjzfyfk',
		'accountManageSrc/jfyfkzh/yfkth',
		'accountManageSrc/jfyfkzh/mxcx',
		'accountManageSrc/jfyfkzh/yfzfkctj',
		'accountManageSrc/jfyfkzh/yfzfkctjPOS',
		'accountManageSrc/jfyfkzh/yfzfkctjCZY',
		'accountManageSrc/jfyfkzh/sqfp',
		'accountManageLan'
		], function(tpl, zhye, yjzfyfk, yfkth, mxcx, yfzfkctj, yfzfkctjPOS, yfzfkctjCZY, sqfp){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			
			//按钮事件
			$('#yfzfk_zhye').click(function(){
				comm.liActive($(this));
				zhye.showPage();
			}).click();

			$('#yjzfyfk').click(function(){
				comm.liActive($(this));
				yjzfyfk.showPage();
			});

			$('#yfkth').click(function(){
				comm.liActive($(this));
				yfkth.showPage();
			});

			$('#yfzfk_mxcx').click(function(){
				comm.liActive($(this));
				mxcx.showPage();
			});

			$('#yfzfkctj').click(function(){
				comm.liActive($(this));
				yfzfkctj.showPage();
			});

			$('#yfzfkctjPOS').click(function(){
				comm.liActive($(this));
				yfzfkctjPOS.showPage();
			});

			$('#yfzfkctjCZY').click(function(){
				comm.liActive($(this));
				yfzfkctjCZY.showPage();
			});

			$('#yfzfk_sqfp').click(function(){
				comm.liActive($(this));
				sqfp.showPage();
			});
		}
	}	
});