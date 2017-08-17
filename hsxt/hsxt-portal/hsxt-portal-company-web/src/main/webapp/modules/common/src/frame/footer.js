define(["text!commTpl/frame/footer.html"],function(tpl){
	//加载脚部文件
	$('#footer').html(_.template(tpl, {
		year: new Date().getFullYear()
	}));
	
	/*右边模块折叠效果*/
	$('#serviceTab_btn').click(function(){
		var f = $('#service').toggleClass('none').hasClass('none');
		$(this).attr('title', !f ? '收起右栏' : '显示右栏').children('i').toggleClass('serviceTab_arrow_left');
		$('.content').css('margin-right', !f ? '248px' : '0');
		/*  
		 *  2016-03-31 万华成
		 *  电商  订单管理(餐饮) 加菜 -已选菜品区域改变
		 *   begin
		 */ 
		if ( $('#takeOrder_selectFoods').length ){
			if (!f){
				$('#takeOrder_selectFoods').css('width','62%');
			} else {
				$('#takeOrder_selectFoods').css('width','69%');
			} 
		} 
		/***  end  ***/ 
	}).bind('mouseover', function(){
		$(this).removeClass('ui-state-default').addClass('ui-state-active');
	}).bind('mouseout', function(){
		$(this).removeClass('ui-state-active').addClass('ui-state-default');
	});
	/*end*/
	
	/*sideBar自动改变高度*/
	$('.operationsArea').resize( function(){		 
		var operationsArea_height = $('.operationsArea').height();
		$('.sideBar_menu_h1').height(operationsArea_height + 112);
		$('#sideBarBox').children('div').css('height', 'auto');			
	})
	/*end*/

	return tpl;	
});	