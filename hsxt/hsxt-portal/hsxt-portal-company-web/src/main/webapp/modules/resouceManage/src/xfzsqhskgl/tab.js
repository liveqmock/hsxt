define(['text!resouceManageTpl/xfzsqhskgl/tab.html',
		'resouceManageSrc/xfzsqhskgl/xfzsqhskgl'
		],function(tpl, xfzsqhskgl){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			//消费者资源统计
			$('#xtzygl_xfzsqhskgl').click(function(e) {
                xfzsqhskgl.showPage();
            }).click();
		}
	}
});